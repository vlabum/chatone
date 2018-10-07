package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.model.Packet;
import ru.vlabum.chatone.model.PacketUnicast;
import ru.vlabum.chatone.server.event.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;

@ApplicationScoped
public class ServerMessageInputHandler {

    @Inject
    private Event<ServerPingEvent> serverPingEvent;

    @Inject
    private Event<ServerRegistryEvent> serverRegistryEventEvent;

    @Inject
    private Event<ServerLoginEvent> serverLoginEventEvent;

    @Inject
    private Event<ServerBroadcastEvent> serverBroadcastEvent;

    @Inject
    private Event<ServerLogoutEvent> serverLogoutEvent;

    @Inject
    private Event<ServerUnicastEvent> serverUnicastEvent;

    @SneakyThrows
    public void observe(@ObservesAsync final ServerMessageInputEvent event) {
        System.out.println("ServerMessageInputHandler");
        @NotNull final Socket socket = event.getSocket();
        @NotNull final String message = event.getMessage();
        System.out.println(message);
        @NotNull final ObjectMapper objectMapper = new ObjectMapper();
        @NotNull final Packet packet = objectMapper.readValue(message, Packet.class);

        switch (packet.getType()) {

            case PING:
                serverPingEvent.fireAsync(new ServerPingEvent(socket, message));
                break;

            case REGISTRY:
                serverRegistryEventEvent.fireAsync(new ServerRegistryEvent(socket, message));
                break;

            case LOGIN:
                serverLoginEventEvent.fireAsync(new ServerLoginEvent(socket, message));
                break;

            case BROADCAST:
                serverBroadcastEvent.fireAsync(new ServerBroadcastEvent(socket, message));
                break;

            case LOGOUT:
                serverLogoutEvent.fireAsync(new ServerLogoutEvent(socket, message));
                break;

            case UNICAST:
                serverUnicastEvent.fireAsync(new ServerUnicastEvent(socket, message));
                break;

        }
    }

}
