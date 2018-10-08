package ru.vlabum.chatone.model;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class PacketUnicastResponse extends Packet {

    {
        setType(PacketType.UNICAST_RESPONSE);
    }

    @Nullable
    private String login = ""; // от кого сообщение

    @Nullable
    private String message = ""; // сообщение
}
