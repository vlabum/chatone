package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.model.*;
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

    @Inject
    private Event<ServerLoginsEvent> serverLoginsEvent;

    @SneakyThrows
    public void observe(@ObservesAsync final ServerMessageInputEvent event) {
        System.out.println("ServerMessageInputHandler");
        @NotNull final Socket socket = event.getSocket();
        @NotNull final String message = event.getMessage();
        System.out.println(message);
        @NotNull final ObjectMapper objectMapper = new ObjectMapper();
        @NotNull final Packet packet = objectMapper.readValue(message, Packet.class);

        switch (packet.getType()) {

            case PING_REQUEST:
                @NotNull final PacketPingRequest pingRequest = objectMapper.readValue(message, PacketPingRequest.class);
                serverPingEvent.fireAsync(new ServerPingEvent(socket, pingRequest));
                break;

            case REGISTRY_REQUEST:
                @NotNull final PacketRegistryRequest registryRequest = objectMapper.readValue(message, PacketRegistryRequest.class);
                serverRegistryEventEvent.fireAsync(new ServerRegistryEvent(socket, registryRequest));
                break;

            case LOGIN_REQUEST:
                @NotNull final PacketLoginRequest loginRequest = objectMapper.readValue(message, PacketLoginRequest.class);
                serverLoginEventEvent.fireAsync(new ServerLoginEvent(socket, loginRequest));
                break;

            case BROADCAST_REQUEST:
                @NotNull final PacketBroadcastRequest broadcastRequest = objectMapper.readValue(message, PacketBroadcastRequest.class);
                serverBroadcastEvent.fireAsync(new ServerBroadcastEvent(socket, broadcastRequest));
                break;

            case LOGOUT_REQUEST:
                @NotNull final PacketLogoutRequest logoutRequest = objectMapper.readValue(message, PacketLogoutRequest.class);
                serverLogoutEvent.fireAsync(new ServerLogoutEvent(socket, logoutRequest));
                break;

            case UNICAST_REQUEST:
                @NotNull final PacketUnicastRequest unicastRequest = objectMapper.readValue(message, PacketUnicastRequest.class);
                serverUnicastEvent.fireAsync(new ServerUnicastEvent(socket, unicastRequest));
                break;

            case LOGINS_REQUEST:
                @NotNull final PacketLoginsRequest loginsRequest = objectMapper.readValue(message, PacketLoginsRequest.class);
                serverLoginsEvent.fireAsync(new ServerLoginsEvent(socket));
                break;

        }
    }

}
