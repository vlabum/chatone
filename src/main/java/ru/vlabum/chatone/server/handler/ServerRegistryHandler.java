package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.vlabum.chatone.model.PacketRegistryRequest;
import ru.vlabum.chatone.model.PacketRegistryResponse;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.api.UserService;
import ru.vlabum.chatone.server.event.ServerRegistryEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;

@ApplicationScoped
public class ServerRegistryHandler {

    @Inject
    private UserService userService;

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void registry(@ObservesAsync final ServerRegistryEvent event){
        @NonNull final Socket socket = event.getSocket();
        @NonNull final String message = event.getMessage();
        @NonNull final ObjectMapper objectMapper = new ObjectMapper();
        @NonNull final PacketRegistryRequest packet = objectMapper.readValue(message, PacketRegistryRequest.class);
        final boolean result = userService.registry(packet.getLogin(), packet.getPassword());
        @NonNull final PacketRegistryResponse packetResp = new PacketRegistryResponse();
        connectionService.sendResult(socket, PacketType.REGISTRY_RESPONSE, result);
    }

}
