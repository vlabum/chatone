package ru.vlabum.chatone.server.event;

import ru.vlabum.chatone.model.PacketPingRequest;

import java.net.Socket;

public class ServerPingEvent {

    private final Socket socket;

    private final PacketPingRequest packet;

    public ServerPingEvent(final Socket socket, final PacketPingRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
