package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.model.PacketLoginRequest;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.api.UserService;
import ru.vlabum.chatone.server.event.ServerLoginEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.net.Socket;

@ApplicationScoped
public class ServerLoginHandler {

    @Inject
    private UserService userService;

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void login(@ObservesAsync final ServerLoginEvent event) {
        @NotNull final Socket socket = event.getSocket();
        @NotNull final String message = event.getMessage();
        @NotNull final ObjectMapper objectMapper = new ObjectMapper();
        @NotNull final PacketLoginRequest packetLoginRequest = objectMapper.readValue(message, PacketLoginRequest.class);
        boolean check = userService.check(packetLoginRequest.getLogin(), packetLoginRequest.getPassword());
        if (check) connectionService.setLogin(socket, packetLoginRequest.getLogin());
        else System.out.println("Incorrect password or login");
        connectionService.sendResult(socket, PacketType.LOGIN_RESPONSE, check);
    }

}
