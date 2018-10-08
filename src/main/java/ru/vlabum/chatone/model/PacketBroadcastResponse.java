package ru.vlabum.chatone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketBroadcastResponse extends Packet {

    {
        setType(PacketType.BROADCAST_RESPONSE);
    }


    @Nullable
    private String login = ""; // от кого

    @Nullable
    private String message = "";

}
