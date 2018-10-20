package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
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
    private Event<ClientLoginsEvent> clientLoginsEvent;

    @Inject
    private Event<ClientUnicastEvent> clientUnicastEvent;

    @Inject
    private Event<ClientRegistryEvent> clientRegistryEvent;

    @Inject
    private Event<ClientPingEvent> clientPingEvent;

    @SneakyThrows
    public void parse(@ObservesAsync final ClientCommandInputEvent event){
        System.out.println("ClientCommandInputHandler");
        @NotNull final String commandUser = event.getCommand();
        ObjectMapper objectMapper = new ObjectMapper();

        @NotNull String command;
        if (commandUser.startsWith(":"))
            command = getJSON(commandUser);
        else
            command = commandUser;

        final String json = getValueJson(command, "type");

        switch (json) {
            case "LOGINS_REQUEST":
                final PacketLoginsRequest loginsRequest = objectMapper.readValue(command, PacketLoginsRequest.class);
                clientLoginsEvent.fireAsync(new ClientLoginsEvent(loginsRequest));
                return;

            case "LOGIN_REQUEST":
                final PacketLoginRequest loginRequest = objectMapper.readValue(command, PacketLoginRequest.class);
                clientLoginEvent.fireAsync(new ClientLoginEvent(loginRequest));
                return;

            case "PING_REQUEST":
                final PacketPingRequest packetPingRequest = objectMapper.readValue(command, PacketPingRequest.class);
                clientPingEvent.fireAsync(new ClientPingEvent(packetPingRequest));
                return;

            case "UNICAST_REQUEST":
                final PacketUnicastRequest packetUnicastRequest = objectMapper.readValue(command, PacketUnicastRequest.class);
                clientUnicastEvent.fireAsync(new ClientUnicastEvent(packetUnicastRequest));
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

        final PacketBroadcastRequest packetBroadcast = new PacketBroadcastRequest();
        packetBroadcast.setMessage(command);
        clientBroadcastEvent.fireAsync(new ClientBroadcastEvent(packetBroadcast));

    }

    @SneakyThrows
    private String getJSON(final String commandUser) {
        if (commandUser.startsWith(":logins"))  return getLoginsRequest(commandUser);
        if (commandUser.startsWith(":login"))   return getLoginRequest(commandUser);
        if (commandUser.startsWith(":ping"))    return getPingRequest(commandUser);
        if (commandUser.startsWith(":unicast")) return getUnicastRequest(commandUser);

        return "";
    }

    @SneakyThrows
    private String getUnicastRequest(final String commandUser) {
        @NotNull final String[] splits = commandUser.split("\\^");
        if (splits.length < 3)
            return "";
        final ObjectMapper mapper = new ObjectMapper();
        final PacketUnicastRequest unicastRequest = new PacketUnicastRequest();
        unicastRequest.setLogin(splits[1]);
        unicastRequest.setMessage(splits[2]);
        return mapper.writeValueAsString(unicastRequest);
    }

    @SneakyThrows
    private String getPingRequest(final String commandUser) {
        final ObjectMapper mapper = new ObjectMapper();
        final PacketPingRequest pingRequest = new PacketPingRequest();
        return mapper.writeValueAsString(pingRequest);
    }

    @SneakyThrows
    private String getLoginRequest(final String commandUser) {
        @NotNull final String[] splits = commandUser.split("\\^");
        if (splits.length < 3)
            return "";
        final ObjectMapper mapper = new ObjectMapper();
        final PacketLoginRequest loginRequest = new PacketLoginRequest();
        loginRequest.setLogin(splits[1]);
        loginRequest.setPassword(splits[2]);
        return mapper.writeValueAsString(loginRequest);
    }

    @SneakyThrows
    private String getLoginsRequest(final String commandUser) {
        final ObjectMapper mapper = new ObjectMapper();
        final PacketLoginsRequest loginsRequest = new PacketLoginsRequest();
        return mapper.writeValueAsString(loginsRequest);
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
            return "";
        }
    }
}
