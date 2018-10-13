package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.client.gui.ChatWindow;
import ru.vlabum.chatone.model.Packet;
import ru.vlabum.chatone.model.PacketType;

@Getter
public class ClientMessageViewEvent {

    private final String message;
    private final ChatWindow window;
    private final PacketType packetType;

    public ClientMessageViewEvent(final String message, final PacketType packetType, final ChatWindow window) {
        this.message = message;
        this.window = window;
        this.packetType = packetType;
    }
}
