package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @NotNull
    private static final Logger logger = LoggerFactory.getLogger(ServerRegistryHandler.class);

    @Inject
    private UserService userService;

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void registry(@ObservesAsync final ServerRegistryEvent event){
        logger.info("Start registry");
        @NotNull final Socket socket = event.getSocket();
        @NotNull final ObjectMapper objectMapper = new ObjectMapper();
        @NotNull final PacketRegistryRequest packet = event.getPacket();
        @NotNull final boolean result = userService.registry(packet.getLogin(), packet.getPassword());
        @NotNull final PacketRegistryResponse packetResp = new PacketRegistryResponse();
        connectionService.sendResult(socket, PacketType.REGISTRY_RESPONSE, result);
        logger.info("Finish registry");
    }

}
