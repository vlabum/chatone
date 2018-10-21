package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.PacketRegistryRequest;
import ru.vlabum.chatone.model.PacketRegistryResponse;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.model.PacketUpdateLoginRequest;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.api.UserService;
import ru.vlabum.chatone.server.event.ServerRegistryEvent;
import ru.vlabum.chatone.server.event.ServerUpdateLoginEvent;
import ru.vlabum.chatone.server.model.Connection;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;

public class ServerUpdateLoginHandler {

    @Inject
    private UserService userService;

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void update(@ObservesAsync final ServerUpdateLoginEvent event){
        @NotNull final Socket socket = event.getSocket();
        @Nullable final Connection connection = connectionService.get(socket);
        if (connection == null) return;
        @Nullable final String oldLogin = connection.getLogin();
        if (oldLogin == null || oldLogin.isEmpty()) return;
        @NotNull final PacketUpdateLoginRequest packet = event.getPacket();
        @NotNull final boolean result = userService.updateLogin(socket, oldLogin, packet.getLogin());
        connectionService.sendResult(socket, PacketType.UPDATELOGIN_RESPONSE, result);
    }

}
