package ru.vlabum.chatone.server.event;

import ru.vlabum.chatone.model.PacketLogoutRequest;

import java.net.Socket;

public class ServerLogoutEvent {

    private final Socket socket;

    private final PacketLogoutRequest packet;

    public ServerLogoutEvent(final Socket socket, final PacketLogoutRequest packet) {
        this.socket = socket;
        this.packet = packet;
    }

}
