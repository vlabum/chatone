package ru.vlabum.chatone.server.api;

import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.server.model.User;

import java.io.IOException;
import java.net.Socket;

public interface UserService {

    @Nullable User findByLogin(@Nullable String login);

    boolean check(@Nullable String login, @Nullable String password);

    boolean registry(@Nullable String login, @Nullable String password) throws IOException;

    boolean updateLogin(@Nullable Socket socket, @Nullable String oldLogin, @Nullable String newLogin) throws IOException;

    boolean exists(@Nullable String login);

}
