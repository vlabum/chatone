package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketLoginRequest;

@Getter
public class ClientLoginEvent {

    private PacketLoginRequest packet;

    public ClientLoginEvent(final PacketLoginRequest packetLoginRequest) {
        this.packet = packetLoginRequest;
    }

}
