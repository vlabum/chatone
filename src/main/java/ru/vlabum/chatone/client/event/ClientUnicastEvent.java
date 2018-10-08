package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketUnicastRequest;

@Getter
public class ClientUnicastEvent {

    private final PacketUnicastRequest packet;

    public ClientUnicastEvent(final PacketUnicastRequest packet) {
        this.packet = packet;
    }

}
