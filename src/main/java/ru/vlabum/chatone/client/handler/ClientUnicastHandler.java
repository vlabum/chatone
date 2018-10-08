package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientUnicastEvent;
import ru.vlabum.chatone.model.PacketUnicastRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientUnicastHandler {

    @Inject
    private Client client;

    @SneakyThrows
    public void send(@ObservesAsync final ClientUnicastEvent event){
        @NotNull PacketUnicastRequest packet = event.getPacket();
        @NotNull ObjectMapper mapper = new ObjectMapper();
        client.send(mapper.writeValueAsString(packet));
    }

}
