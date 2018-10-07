package ru.vlabum.chatone.server.handler;

import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.event.ServerMessageInputEvent;
import ru.vlabum.chatone.server.event.ServerMessageReadEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.InputStream;

@ApplicationScoped
public class ServerMessageReadHandler {

    @Inject
    private ConnectionService connectionService;

    @Inject
    private Event<ServerMessageReadEvent> serverMessageReadEventEvent;

    @Inject
    private Event<ServerMessageInputEvent> serverMessageInputEventEvent;

    public void read(@ObservesAsync final ServerMessageReadEvent event) {
        System.out.println("ServerNessageReadHandler");
        try {
            @NotNull final InputStream inputStream = event.getSocket().getInputStream();
            @NotNull final DataInputStream dataInputStream = new DataInputStream(inputStream);
            @NotNull final String message = dataInputStream.readUTF();
            serverMessageReadEventEvent.fireAsync(new ServerMessageReadEvent(event.getSocket()));
            serverMessageInputEventEvent.fireAsync(new ServerMessageInputEvent(event.getSocket(), message));
        } catch (@NotNull final Exception e) {
            connectionService.remove(event.getSocket());
        }
    }

}
