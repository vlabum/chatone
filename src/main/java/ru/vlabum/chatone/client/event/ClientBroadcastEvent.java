package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketBroadcastRequest;

@Getter
public class ClientBroadcastEvent {

    private final PacketBroadcastRequest packet;

    public ClientBroadcastEvent(final PacketBroadcastRequest packet) {
        this.packet = packet;
    }

}
