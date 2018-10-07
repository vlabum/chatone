package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.model.PacketLogin;

@Getter
public class ClientLoginEvent {

    private PacketLogin packet;

    public ClientLoginEvent(final PacketLogin packetLogin) {
        this.packet = packetLogin;
    }

}
