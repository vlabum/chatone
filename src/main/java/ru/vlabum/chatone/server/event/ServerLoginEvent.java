package ru.vlabum.chatone.server.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketLoginRequest;

import java.net.Socket;

@Getter
public class ServerLoginEvent {

    private final Socket socket;

    private final PacketLoginRequest packet; // запрос, на который реагируем

    public ServerLoginEvent(final Socket socket, final PacketLoginRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
