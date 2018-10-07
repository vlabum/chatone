package ru.vlabum.chatone.client.event;

import ru.vlabum.chatone.model.Packet;

public class ClientMessageInputEvent {

    private final Packet packet;

    public ClientMessageInputEvent(final Packet packet) {
        this.packet = packet;
    }

}
