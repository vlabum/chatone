package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.PacketBroadcast;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.event.ServerBroadcastEvent;
import ru.vlabum.chatone.server.model.Connection;

import javax.enterprise.context.ApplicationScoped;
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
        @NotNull final String message = event.getMessage();
        @NotNull ObjectMapper mapper = new ObjectMapper();
        @NotNull final PacketBroadcast packet = mapper.readValue(event.getMessage(), PacketBroadcast.class);
        for (final Connection item: connectionService.connections()){
            connectionService.sendMessage(item.getSocket(), login, packet.getMessage());
        }
    }

}
