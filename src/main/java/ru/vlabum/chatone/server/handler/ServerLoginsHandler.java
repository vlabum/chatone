package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.PacketBroadcastRequest;
import ru.vlabum.chatone.model.PacketBroadcastResponse;
import ru.vlabum.chatone.model.PacketLoginsRequest;
import ru.vlabum.chatone.model.PacketLoginsResponse;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.event.ServerBroadcastEvent;
import ru.vlabum.chatone.server.event.ServerLoginsEvent;
import ru.vlabum.chatone.server.model.Connection;
import ru.vlabum.chatone.server.service.UserServiceBean;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerLoginsHandler {

    @Inject
    private ConnectionService connectionService;

    @Inject
    private UserServiceBean userServiceBean;

    @SneakyThrows
    public void sendLogins(@ObservesAsync final ServerLoginsEvent event){
        @NotNull final Socket socket = event.getSocket();
        @Nullable Connection connection = connectionService.get(socket);
        if (connection == null) return;
        @NotNull ObjectMapper mapper = new ObjectMapper();
        @NotNull final PacketLoginsResponse response = new PacketLoginsResponse();
        @NotNull List<String> users = new ArrayList<>(userServiceBean.getUsers().keySet());
        response.setUsers(users);
        connectionService.sendMessage(socket, "RequestLogins", mapper.writeValueAsString(response));
    }

}
