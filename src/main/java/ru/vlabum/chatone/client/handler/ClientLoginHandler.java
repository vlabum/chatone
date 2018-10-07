package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientBroadcastEvent;
import ru.vlabum.chatone.client.event.ClientLoginEvent;
import ru.vlabum.chatone.model.PacketBroadcast;
import ru.vlabum.chatone.model.PacketLogin;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

public class ClientLoginHandler {

    @Inject
    private Client client;

    @SneakyThrows
    public void send(@ObservesAsync final ClientLoginEvent event){
        @NotNull PacketLogin packet = event.getPacket();
        @NotNull ObjectMapper mapper = new ObjectMapper();
        client.send(mapper.writeValueAsString(packet));
    }

}
