package ru.vlabum.chatone.client.event;

import ru.vlabum.chatone.model.PacketUnicast;

public class ClientUnicastEvent {

    private final PacketUnicast packet;

    public ClientUnicastEvent(final PacketUnicast packet) {
        this.packet = packet;
    }

}
