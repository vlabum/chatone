package ru.vlabum.chatone.client.event;

import ru.vlabum.chatone.model.PacketRegistry;

public class ClientRegistryEvent {

    private final PacketRegistry packet;

    public ClientRegistryEvent(final PacketRegistry packet) {
        this.packet = packet;
    }

}
