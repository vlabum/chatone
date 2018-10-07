package ru.vlabum.chatone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketResult extends Packet {

    {
        setType(PacketType.RESULT);
    }

    private boolean success = true;

    public PacketResult(boolean success) { this.success = success; }

}
