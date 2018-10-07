package ru.vlabum.chatone.client.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientCommandInputEvent;
import ru.vlabum.chatone.client.event.ClientMessageInputEvent;
import ru.vlabum.chatone.client.event.ClientMessageReadEvent;
import ru.vlabum.chatone.client.gui.ChatWindow;
import ru.vlabum.chatone.config.ChatConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

@Getter
@Setter
@NoArgsConstructor
@ApplicationScoped
public class ClientService implements Client {

    @Inject
    private Event<ClientMessageReadEvent> clientMessageReadEvent;

    @Inject
    private Event<ClientMessageInputEvent> clientMessageInputEvent;

    @Inject
    private Event<ClientCommandInputEvent> clientCommandInputEvent;

    @Inject
    private ChatConfig config;

    private ChatWindow window;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @Override
    @SneakyThrows
    public void run() {
        final String host = config.getHost();
        final Integer port = config.getPort();
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        clientMessageReadEvent.fireAsync(new ClientMessageReadEvent());
        window = new ChatWindow(clientCommandInputEvent);
        window.setVisible(true);
    }

    @Override
    @SneakyThrows
    public void send(String message) {
        out.writeUTF(message);
    }

    @Override
    @SneakyThrows
    public void exit() {
        socket.close();
        System.out.println("Chat client disconnected");
        System.exit(0);
    }

}
