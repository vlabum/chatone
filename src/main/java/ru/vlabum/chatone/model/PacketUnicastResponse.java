package ru.vlabum.chatone.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class PacketUnicastResponse extends Packet {

    {
        setType(PacketType.UNICAST_RESPONSE);
    }

    @Nullable
    private String login = ""; // от кого сообщение

    @Nullable
    private String message = ""; // сообщение

}
