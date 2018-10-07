package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.vlabum.chatone.model.PacketRegistry;
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
        @NonNull final PacketRegistry packet = objectMapper.readValue(message, PacketRegistry.class);
        final boolean result = userService.registry(packet.getLogin(), packet.getPassword());
        connectionService.sendResult(socket, result);
    }


}
