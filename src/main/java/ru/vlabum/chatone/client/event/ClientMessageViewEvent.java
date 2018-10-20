package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.client.gui.ChatWindow;
import ru.vlabum.chatone.model.Packet;
import ru.vlabum.chatone.model.PacketType;

@Getter
public class ClientMessageViewEvent {

    private final Packet packet;

    private final ChatWindow window;

    private final PacketType packetType;

    public ClientMessageViewEvent(final Packet packet, final PacketType packetType, final ChatWindow window) {
        this.packet = packet;
        this.window = window;
        this.packetType = packetType;
    }
}
