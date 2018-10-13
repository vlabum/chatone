package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.client.event.*;
import ru.vlabum.chatone.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientCommandInputHandler {

    @Inject
    private Event<ClientCommandInputEvent> clientCommandInputEvent;

    @Inject
    private Event<ClientBroadcastEvent> clientBroadcastEvent;

    @Inject
    private Event<ClientLoginEvent> clientLoginEvent;

    @Inject
    private Event<ClientUnicastEvent> clientUnicastEvent;

    @Inject
    private Event<ClientRegistryEvent> clientRegistryEvent;

    @Inject
    private Event<ClientPingEvent> clientPingEvent;

    public void parse(@ObservesAsync final ClientCommandInputEvent event){
        System.out.println("ClientCommandInputHandler");
        @Nullable final String command = event.getCommand();
        ObjectMapper objectMapper = new ObjectMapper();

        if (isJson(command, "login")) {
            PacketLoginRequest packetLoginRequest;
            try {
                packetLoginRequest = objectMapper.readValue(command, PacketLoginRequest.class);
                clientLoginEvent.fireAsync(new ClientLoginEvent(packetLoginRequest));
            } catch (Exception e) {
                return;
            }
            return;
        }

        if (isJson(command, "unicast")) {
            try {
                final PacketUnicastRequest packetUnicastRequest = new PacketUnicastRequest();
                packetUnicastRequest.setMessage(getValueJson(command, "unicast"));
                clientUnicastEvent.fireAsync(new ClientUnicastEvent(packetUnicastRequest));
            } catch (Exception e) {
                //TODO нужно будет переделать, чтобы не отсылалось всем, чтобы не спалиться :)
                final PacketBroadcastRequest packetBroadcast = new PacketBroadcastRequest();
                packetBroadcast.setMessage(command);
                clientBroadcastEvent.fireAsync(new ClientBroadcastEvent(packetBroadcast));
            }
            return;
        }

        if (isJson(command, "registry")) {
            try {
                final PacketRegistryRequest packetRegistryRequest = objectMapper.readValue(command, PacketRegistryRequest.class);
                clientRegistryEvent.fireAsync(new ClientRegistryEvent(packetRegistryRequest));
            } catch (Exception e) {
                //TODO нужно будет переделать, чтобы не отсылалось всем, чтобы не спалиться :)
                final PacketBroadcastRequest packetBroadcast = new PacketBroadcastRequest();
                packetBroadcast.setMessage(command);
                clientBroadcastEvent.fireAsync(new ClientBroadcastEvent(packetBroadcast));
            }
            return;
        }

        if (isJson(command, "ping")) {
            try {
                final PacketPingRequest packetPingRequest = objectMapper.readValue(command, PacketPingRequest.class);
                clientPingEvent.fireAsync(new ClientPingEvent(packetPingRequest));
            } catch (Exception e) {
                return;
            }
            return;
        }

        final PacketBroadcastRequest packetBroadcast = new PacketBroadcastRequest();
        packetBroadcast.setMessage(command);
        clientBroadcastEvent.fireAsync(new ClientBroadcastEvent(packetBroadcast));

    }

    @SneakyThrows
    private boolean isJson(final String string, final String findNode) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode =  mapper.readTree(string);
            JsonNode node = jsonNode.findValue(findNode);
            return (node != null);
        } catch (final Exception e) {
            return false;
        }
    }

    @SneakyThrows
    private String getValueJson(final String string, final String findNode) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode =  mapper.readTree(string);
            JsonNode node = jsonNode.findValue(findNode);
            return node.asText();
        } catch (final Exception e) {
            return null;
        }
    }
}
