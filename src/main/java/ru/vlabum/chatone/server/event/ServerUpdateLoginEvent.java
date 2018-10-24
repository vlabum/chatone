package ru.vlabum.chatone.server.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketLoginRequest;
import ru.vlabum.chatone.model.PacketUpdateLoginRequest;

import java.net.Socket;

@Getter
public class ServerUpdateLoginEvent {

    private final Socket socket;

    private final PacketUpdateLoginRequest packet; // запрос, на который реагируем

    public ServerUpdateLoginEvent(final Socket socket, final PacketUpdateLoginRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
