package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketBroadcast;

@Getter
public class ClientBroadcastEvent {

    private final PacketBroadcast packet;

    public ClientBroadcastEvent(final PacketBroadcast packet) {
        this.packet = packet;
    }

}
