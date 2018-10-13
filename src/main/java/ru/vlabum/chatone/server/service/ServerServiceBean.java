package ru.vlabum.chatone.server.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import ru.vlabum.chatone.config.ChatConfig;
import ru.vlabum.chatone.server.api.Server;
import ru.vlabum.chatone.server.event.ServerConnectionEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.net.ServerSocket;

@Getter
@NoArgsConstructor
@ApplicationScoped
public class ServerServiceBean implements Server {

    @Inject
    private ChatConfig config;

    @Inject
    private Event<ServerConnectionEvent> serverConnectionEvent;

    private ServerSocket serverSocket;

    @Override
    @SneakyThrows
    public void run() {
        serverSocket = new ServerSocket(config.getPort());
        serverConnectionEvent.fire(new ServerConnectionEvent());
    }

}
