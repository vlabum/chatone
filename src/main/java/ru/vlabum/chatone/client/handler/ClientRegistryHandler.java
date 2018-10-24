package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientLoginEvent;
import ru.vlabum.chatone.client.event.ClientRegistryEvent;
import ru.vlabum.chatone.model.PacketLoginRequest;
import ru.vlabum.chatone.model.PacketRegistryRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientRegistryHandler {

    @Inject
    private Client client;

    @SneakyThrows
    public void registry(@ObservesAsync final ClientRegistryEvent event){
        @NotNull final PacketRegistryRequest packet = event.getPacket();
        @NotNull final ObjectMapper mapper = new ObjectMapper();
        client.send(mapper.writeValueAsString(packet));
    }

}
