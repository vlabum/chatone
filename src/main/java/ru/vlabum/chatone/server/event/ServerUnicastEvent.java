package ru.vlabum.chatone.server.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketUnicastRequest;

import java.net.Socket;

@Getter
public class ServerUnicastEvent {

    private final Socket socket;

    private final PacketUnicastRequest packet;

    public ServerUnicastEvent(final Socket socket, final PacketUnicastRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
