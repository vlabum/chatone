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

    private final static String DELIMITER;

    static {
        DELIMITER = "\\^";
    }

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

    @Inject
    private Event<ClientUpdateLoginEvent> clientUpdateLoginEvent;

    @SneakyThrows
    public void parse(@ObservesAsync final ClientCommandInputEvent event){
        System.out.println("ClientCommandInputHandler");
        @NotNull final String commandUser = event.getCommand();
        final ObjectMapper objectMapper = new ObjectMapper();

        @NotNull final String command = getJSON(commandUser);
        final String typePacketStr = getValueJson(command, "type");

        switch (typePacketStr) {

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
                return;

            case "REGISTRY_REQUEST":
                final PacketRegistryRequest packetRegistryRequest = objectMapper.readValue(command, PacketRegistryRequest.class);
                clientRegistryEvent.fireAsync(new ClientRegistryEvent(packetRegistryRequest));
                return;

            case "UPDATELOGIN_REQUEST":
                final PacketUpdateLoginRequest packetUpdateLoginRequest = objectMapper.readValue(command, PacketUpdateLoginRequest.class);
                clientUpdateLoginEvent.fireAsync(new ClientUpdateLoginEvent(packetUpdateLoginRequest));
                return;

        }

        final PacketBroadcastRequest packetBroadcast = new PacketBroadcastRequest();
        packetBroadcast.setMessage(command);
        clientBroadcastEvent.fireAsync(new ClientBroadcastEvent(packetBroadcast));
    }

    /**
     * :ping                        - пинг сервера
     * :logins                      - запрос списка всех пользователей
     * :login^{login}^{password}    - идентификация пользователя
     * :unicast^{login}^{message}   - отправка сообщения {message} конкретному пользователю {login}
     * :registry^{login}^{password} - регистрация нового пользователя
     * :updatelogin^{newlogin}      - смена логина пользователя
     * @param commandUser
     * @return
     */
    @SneakyThrows
    private String getJSON(final String commandUser) {
        if (commandUser.startsWith(":ping")) return getPingRequest(commandUser);
        if (commandUser.startsWith(":logins")) return getLoginsRequest(commandUser);
        if (commandUser.startsWith(":login")) return getLoginRequest(commandUser);
        if (commandUser.startsWith(":unicast")) return getUnicastRequest(commandUser);
        if (commandUser.startsWith(":registry")) return getRegistryRequest(commandUser);
        if (commandUser.startsWith(":updatelogin")) return getUpdateLoginRequest(commandUser);
        return commandUser;
    }

    @SneakyThrows
    private String getUpdateLoginRequest(final String commandUser) {
        @NotNull final String[] splits = commandUser.split(DELIMITER);
        if (splits.length < 2)
            return "";
        final ObjectMapper mapper = new ObjectMapper();
        final PacketUpdateLoginRequest updateLoginRequest = new PacketUpdateLoginRequest();
        updateLoginRequest.setLogin(splits[1]);
        return mapper.writeValueAsString(updateLoginRequest);
    }

    @SneakyThrows
    private String getRegistryRequest(final String commandUser) {
        @NotNull final String[] splits = commandUser.split(DELIMITER);
        if (splits.length < 3)
            return "";
        final ObjectMapper mapper = new ObjectMapper();
        final PacketRegistryRequest registryRequest = new PacketRegistryRequest();
        registryRequest.setLogin(splits[1]);
        registryRequest.setPassword(splits[2]);
        return mapper.writeValueAsString(registryRequest);
    }

    @SneakyThrows
    private String getUnicastRequest(final String commandUser) {
        @NotNull final String[] splits = commandUser.split(DELIMITER);
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
        @NotNull final String[] splits = commandUser.split(DELIMITER);
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
            final JsonNode jsonNode =  mapper.readTree(string);
            final JsonNode node = jsonNode.findValue(findNode);
            return (node != null);
        } catch (final Exception e) {
            return false;
        }
    }

    @SneakyThrows
    private String getValueJson(final String string, final String findNode) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode jsonNode =  mapper.readTree(string);
            final JsonNode node = jsonNode.findValue(findNode);
            return node.asText();
        } catch (final Exception e) {
            return "";
        }
    }
}
