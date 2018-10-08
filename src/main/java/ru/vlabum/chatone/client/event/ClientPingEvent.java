package ru.vlabum.chatone.client.event;

import ru.vlabum.chatone.model.PacketPingRequest;

public class ClientPingEvent {

    private final PacketPingRequest packet;

    public ClientPingEvent(final PacketPingRequest packet) {
        this.packet = packet;
    }
}
