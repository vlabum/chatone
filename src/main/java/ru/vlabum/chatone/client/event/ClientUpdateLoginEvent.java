package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketUpdateLoginRequest;

@Getter
public class ClientUpdateLoginEvent {

    private final PacketUpdateLoginRequest packet;

    public ClientUpdateLoginEvent(final PacketUpdateLoginRequest packet) {
        this.packet = packet;
    }

}
