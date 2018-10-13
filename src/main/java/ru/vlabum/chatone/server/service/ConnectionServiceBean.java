package ru.vlabum.chatone.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.model.Packet;
import ru.vlabum.chatone.model.PacketMessage;
import ru.vlabum.chatone.model.PacketResult;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.server.api.ConnectionService;
import ru.vlabum.chatone.server.model.Connection;

import javax.enterprise.context.ApplicationScoped;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ApplicationScoped
public class ConnectionServiceBean implements ConnectionService {

    @NotNull private final List<Connection> connections = new ArrayList<>();
    @NotNull public List<Connection> connections() { return connections; }

    @Override
    public void add(@Nullable final Socket socket){
        if (socket == null) return;
        @NotNull final Connection connection = new Connection(socket);
        connections.add(connection);
        System.out.println("Added conndection " + connection.getId());
    }

    @Nullable
    @Override
    public Connection get(@Nullable final Socket socket){
        if (socket == null) return null;
        for (final Connection connection: connections){
            if (connection.getSocket().equals(socket)) return connection;
        }
        return null;
    }

    @Override
    public void setLogin(@Nullable final Socket socket, @Nullable final String login){
        if (login == null || login.isEmpty()) return;
        @Nullable final Connection connection = get(socket);
        if (connection == null) return;
        connection.setLogin(login);
        System.out.println("Session created for login");
    }

    @Override
    public void remove(@Nullable final Socket socket){
        if (socket == null) return;
        connections.remove(socket);
    }

    @SneakyThrows
    public void sendResult(
            @Nullable final Socket socket,
            @Nullable final PacketType packetType,
            @Nullable final Boolean result
    ){
        if (socket == null) return;
        if (result == null) return;
        final PacketResult packetResult = new PacketResult(packetType, result);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String message = objectMapper.writeValueAsString(packetResult);
        final DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        stream.writeUTF(message);
    }

    @SneakyThrows
    @Override
    public void sendMessage(
            @Nullable final Socket socket,
            @Nullable final String login,
            @Nullable final String message
    ){
        if (socket == null) return;
        if (login == null || login.isEmpty()) return;
        if (message == null || message.isEmpty()) return;
        final DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        stream.writeUTF(message);
    }

    @SneakyThrows
    @Override
    public void sendMessage(
            @Nullable final Socket socket,
            @Nullable final String login,
            @Nullable final String message,
            @Nullable final PacketType packetType
    ){
        if (socket == null) return;
        if (login == null || login.isEmpty()) return;
        if (message == null || message.isEmpty()) return;

        switch (packetType){
            case BROADCAST_RESPONSE:

                break;
        }
        final PacketMessage packetMessage = new PacketMessage();

        packetMessage.setLogin(login);
        packetMessage.setMessage(message);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String messageJs = objectMapper.writeValueAsString(packetMessage);
        final DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        stream.writeUTF(messageJs);
    }

    @SneakyThrows
    @Override
    public void disconnect(@Nullable final Socket socket){
        if (socket == null) return;
        connections.remove(socket);
        socket.close();
    }
}
