package ru.vlabum.chatone.server.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketRegistryRequest;

import java.net.Socket;

@Getter
public class ServerRegistryEvent {

    private final Socket socket;

    private final PacketRegistryRequest packet; // запрос, на который отвечаем

    public ServerRegistryEvent(final Socket socket, final PacketRegistryRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
