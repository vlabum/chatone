package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.PacketUnicastRequest;
import ru.vlabum.chatone.model.PacketUnicastResponse;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.event.ServerUnicastEvent;
import ru.vlabum.chatone.server.model.Connection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;

@ApplicationScoped
public class ServerUnicastHandler {

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void unicast(@ObservesAsync final ServerUnicastEvent event){
        @NotNull final Socket socket = event.getSocket();
        @Nullable Connection connection = connectionService.get(socket);
        if (connection == null) return;
        final ObjectMapper objectMapper = new ObjectMapper();
        @Nullable PacketUnicastRequest unicastRequest = event.getPacket();
        if (unicastRequest == null) return;
        @Nullable final String loginDst = unicastRequest.getLogin();
        if (loginDst == null || loginDst.isEmpty()) return;
        @Nullable final String login = connection.getLogin();
        if (login == null || login.isEmpty()) return;
        @NotNull final String message = unicastRequest.getMessage();

        final PacketUnicastResponse unicastResponse = new PacketUnicastResponse();
        unicastResponse.setMessage(message);
        unicastResponse.setLogin(login);

        final String retMessage = objectMapper.writeValueAsString(unicastResponse);

        for (final Connection item: connectionService.connections()){
            if (login.equals(item.getLogin()) || loginDst.equals(item.getLogin()))
                connectionService.sendMessage(item.getSocket(), login, objectMapper.writeValueAsString(unicastResponse));
        }
    }

}
