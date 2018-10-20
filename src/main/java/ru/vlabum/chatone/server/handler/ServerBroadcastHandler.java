package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.PacketBroadcastRequest;
import ru.vlabum.chatone.model.PacketBroadcastResponse;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.event.ServerBroadcastEvent;
import ru.vlabum.chatone.server.model.Connection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;

@ApplicationScoped
public class ServerBroadcastHandler {

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void broadcast(@ObservesAsync final ServerBroadcastEvent event){
        @NotNull final Socket socket = event.getSocket();
        @Nullable Connection connection = connectionService.get(socket);
        if (connection == null) return;
        @Nullable String login = connection.getLogin();
        if (login == null || login.isEmpty()) login = "(anonim)";
        @NotNull ObjectMapper mapper = new ObjectMapper();
        @NotNull final PacketBroadcastRequest packet = event.getPacket();
        @NotNull final PacketBroadcastResponse response = new PacketBroadcastResponse();
        response.setLogin(login);
        response.setMessage(packet.getMessage());
        for (final Connection item: connectionService.connections()){
            connectionService.sendMessage(item.getSocket(), login, mapper.writeValueAsString(response));
        }
    }

}
