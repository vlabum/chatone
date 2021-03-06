package ru.vlabum.chatone.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @NotNull
    private static final Logger logger = LoggerFactory.getLogger(ServerLoginHandler.class);

    @Inject
    private UserService userService;

    @Inject
    private ConnectionService connectionService;

    @SneakyThrows
    public void login(@ObservesAsync final ServerLoginEvent event) {
        logger.info("Start login");
        @NotNull final Socket socket = event.getSocket();
        @NotNull final PacketLoginRequest loginRequest = event.getPacket();
        @NotNull boolean check = userService.check(loginRequest.getLogin(), loginRequest.getPassword());
        if (check) connectionService.setLogin(socket, loginRequest.getLogin());
        else System.out.println("Incorrect password or login");
        connectionService.sendResult(socket, PacketType.LOGIN_RESPONSE, check);
        logger.info("Finish login");
    }

}
