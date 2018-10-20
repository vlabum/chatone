package ru.vlabum.chatone.server.api;

import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.server.model.User;

public interface UserService {

    @Nullable User findByLogin(@Nullable String login);

    boolean check(@Nullable String login, @Nullable String password);

    boolean registry(@Nullable String login, @Nullable String password);

    boolean exists(@Nullable String login);

}
