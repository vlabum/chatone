package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientBroadcastEvent;
import ru.vlabum.chatone.model.PacketBroadcast;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientBroadcastHandler {

    @Inject
    private Client client;

    @SneakyThrows
    public void send(@ObservesAsync final ClientBroadcastEvent event){
        @NotNull PacketBroadcast packet = event.getPacket();
        @NotNull ObjectMapper mapper = new ObjectMapper();
        client.send(mapper.writeValueAsString(packet));
    }
}
