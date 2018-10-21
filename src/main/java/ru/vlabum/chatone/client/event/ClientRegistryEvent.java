package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketRegistryRequest;

@Getter
public class ClientRegistryEvent {

    private final PacketRegistryRequest packet;

    public ClientRegistryEvent(final PacketRegistryRequest packet) {
        this.packet = packet;
    }

}
