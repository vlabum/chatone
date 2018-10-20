package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.Packet;
import ru.vlabum.chatone.model.PacketType;

@Getter
public class ClientCommandInputEvent {

    private final String command;

    private final PacketType packetType = PacketType.NONE;

    public ClientCommandInputEvent(final String command) {
        this.command = command;
    }

}
