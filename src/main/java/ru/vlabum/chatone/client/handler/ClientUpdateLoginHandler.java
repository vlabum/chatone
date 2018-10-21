package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientUpdateLoginEvent;
import ru.vlabum.chatone.model.PacketUpdateLoginRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientUpdateLoginHandler {

    @Inject
    private Client client;

    @SneakyThrows
    public void update(@ObservesAsync final ClientUpdateLoginEvent event) {
        @NotNull final PacketUpdateLoginRequest packet = event.getPacket();
        @NotNull final ObjectMapper mapper = new ObjectMapper();
        client.send(mapper.writeValueAsString(packet));
    }

}
