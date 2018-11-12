package ru.vlabum.chatone.server.handler;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.api.Server;
import ru.vlabum.chatone.server.event.ServerConnectionEvent;
import ru.vlabum.chatone.server.event.ServerMessageReadEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.net.Socket;

@ApplicationScoped
public class ServerConnectionHandler  {

    @NotNull
    private static final Logger logger = LoggerFactory.getLogger(ServerConnectionHandler.class);

    @Inject
    private Server server;

    @Inject
    private ConnectionService connectionService;

    @Inject
    private Event<ServerConnectionEvent> serverConnectionEvent;

    @Inject
    private Event<ServerMessageReadEvent> serverMessageEventEvent;

    @SneakyThrows
    public void connect(@Observes final ServerConnectionEvent event) {
        logger.info("Start connect");
        System.out.println("ServerConnectionHandler");
        final Socket socket = server.getServerSocket().accept();
        connectionService.add(socket);
        serverMessageEventEvent.fireAsync(new ServerMessageReadEvent(socket));
        serverConnectionEvent.fire(new ServerConnectionEvent());
        logger.info("Finish connect");
    }

}
