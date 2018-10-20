package ru.vlabum.chatone.server.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.PacketResult;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.server.model.Connection;

import java.net.Socket;
import java.util.List;

public interface ConnectionService {

    @NotNull
    List<Connection> connections();

    @Nullable
    Connection get(final Socket socket);

    void add(@Nullable final Socket socket);

    void remove(@Nullable final Socket socket);

    void setLogin(@Nullable final Socket socket, @Nullable final String login);

    void sendResult(
            @Nullable final Socket socket,
            @Nullable final PacketType packetType,
            @Nullable final Boolean success
    );

    void sendResult(
            @Nullable final Socket socket,
            @Nullable final PacketResult packetResult
    );

    void sendMessage(
            @Nullable final Socket socket,
            @Nullable final String login,
            @Nullable final String message
    );

    void sendMessage(
            @Nullable final Socket socket,
            @Nullable final String login,
            @Nullable final String message,
            @Nullable final PacketType packetType
    );

    void disconnect(@Nullable final Socket socket);
}
