package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientMessageReadEvent;
import ru.vlabum.chatone.client.event.ClientMessageViewEvent;
import ru.vlabum.chatone.model.Packet;
import ru.vlabum.chatone.model.PacketType;

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
                clientMessageViewEvent.fireAsync(new ClientMessageViewEvent(
                        messageJson,
                        PacketType.BROADCAST_RESPONSE,
                        client.getWindow())
                );
                break;

            case UNICAST_RESPONSE:
                clientMessageViewEvent.fireAsync(new ClientMessageViewEvent(
                        messageJson,
                        PacketType.UNICAST_RESPONSE,
                        client.getWindow())
                );
                break;

        }
        clientMessageReadEvent.fireAsync(new ClientMessageReadEvent());

    }

}
