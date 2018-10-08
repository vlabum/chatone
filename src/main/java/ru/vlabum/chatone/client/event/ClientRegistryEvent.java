package ru.vlabum.chatone.client.event;

import ru.vlabum.chatone.model.PacketRegistryRequest;

public class ClientRegistryEvent {

    private final PacketRegistryRequest packet;

    public ClientRegistryEvent(final PacketRegistryRequest packet) {
        this.packet = packet;
    }

}
