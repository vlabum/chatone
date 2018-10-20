package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketLoginsRequest;

@Getter
public final class ClientLoginsEvent {

    private PacketLoginsRequest packet;

    public ClientLoginsEvent(final PacketLoginsRequest packetLoginsRequest) {
        this.packet = packetLoginsRequest;
    }

}
