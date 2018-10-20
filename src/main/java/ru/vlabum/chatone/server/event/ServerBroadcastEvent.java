package ru.vlabum.chatone.server.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketBroadcastRequest;
import ru.vlabum.chatone.model.PacketBroadcastResponse;

import java.net.Socket;

@Getter
public class ServerBroadcastEvent {

    private final Socket socket;

    private final PacketBroadcastRequest packet; // запрос, на который реагируем

    public ServerBroadcastEvent(final Socket socket, final PacketBroadcastRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
