package ru.vlabum.chatone.client.event;

import ru.vlabum.chatone.model.PacketPing;

public class ClientPingEvent {

    private final PacketPing packet;

    public ClientPingEvent(final PacketPing packet) {
        this.packet = packet;
    }
}
