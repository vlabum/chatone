package ru.vlabum.chatone.server.handler;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @NotNull
    private static final Logger logger = LoggerFactory.getLogger(ServerMessageReadHandler.class);

    @Inject
    private ConnectionService connectionService;

    @Inject
    private Event<ServerMessageReadEvent> serverMessageReadEventEvent;

    @Inject
    private Event<ServerMessageInputEvent> serverMessageInputEventEvent;

    public void read(@ObservesAsync final ServerMessageReadEvent event) {
        logger.info("Start message read");
        System.out.println("ServerNessageReadHandler");
        try {
            @NotNull final InputStream inputStream = event.getSocket().getInputStream();
            @NotNull final DataInputStream dataInputStream = new DataInputStream(inputStream);
            @NotNull final String message = dataInputStream.readUTF();
            serverMessageReadEventEvent.fireAsync(new ServerMessageReadEvent(event.getSocket()));
            serverMessageInputEventEvent.fireAsync(new ServerMessageInputEvent(event.getSocket(), message));
        } catch (@NotNull final Exception e) {
            logger.error("Error message read: " + e);
            connectionService.remove(event.getSocket());
        }
        logger.info("Finish message read");
    }

}
