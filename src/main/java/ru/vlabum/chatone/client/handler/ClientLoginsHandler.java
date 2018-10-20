package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientLoginsEvent;
import ru.vlabum.chatone.model.PacketLoginsRequest;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

public class ClientLoginsHandler {

    @Inject
    private Client client;

    @SneakyThrows
    public void send(@ObservesAsync final ClientLoginsEvent event){
        @NotNull PacketLoginsRequest packet = event.getPacket();
        @NotNull ObjectMapper mapper = new ObjectMapper();
        client.send(mapper.writeValueAsString(packet));
    }
}
