package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientLoginsEvent;
import ru.vlabum.chatone.client.event.ClientMessageReadEvent;
import ru.vlabum.chatone.client.event.ClientMessageViewEvent;
import ru.vlabum.chatone.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientMessageReadHandler {

    @Inject
    private Client client;

    @Inject
    private Event<ClientMessageViewEvent> clientMessageViewEvent;

    @Inject
    private Event<ClientMessageReadEvent> clientMessageReadEvent;

    @Inject
    private Event<ClientLoginsEvent> clientLoginsEvent;

    @SneakyThrows
    public void read(@ObservesAsync final ClientMessageReadEvent event) {
        final String messageJson = client.getIn().readUTF();
        System.out.println(messageJson);

        final ObjectMapper mapper = new ObjectMapper();
        @NotNull final Packet packet = mapper.readValue(messageJson, Packet.class);
        if (packet.getType() == null) packet.setType(PacketType.NONE);

        switch (packet.getType()){

            case PING_RESPONSE:
                break;

            case LOGIN_RESPONSE:
                break;

            case LOGOUT_RESPONSE:
                break;

            case REGISTRY_RESPONSE:
                break;

            case BROADCAST_RESPONSE:
                @NotNull PacketBroadcastResponse broadcastResponse = mapper.readValue(messageJson, PacketBroadcastResponse.class);
                clientMessageViewEvent.fireAsync(new ClientMessageViewEvent(
                        broadcastResponse,
                        PacketType.BROADCAST_RESPONSE,
                        client.getWindow())
                );
                break;

            case UNICAST_RESPONSE:
                @NotNull PacketUnicastResponse unicastResponse = mapper.readValue(messageJson, PacketUnicastResponse.class);
                clientMessageViewEvent.fireAsync(new ClientMessageViewEvent(
                        unicastResponse,
                        PacketType.UNICAST_RESPONSE,
                        client.getWindow())
                );
                break;

            case LOGINS_RESPONSE:
                @NotNull PacketLoginsResponse loginsResponse = mapper.readValue(messageJson, PacketLoginsResponse.class);
                clientMessageViewEvent.fireAsync(new ClientMessageViewEvent(
                        loginsResponse,
                        PacketType.LOGINS_RESPONSE,
                        client.getWindow())
                );

        }
        clientMessageReadEvent.fireAsync(new ClientMessageReadEvent());

    }

}
