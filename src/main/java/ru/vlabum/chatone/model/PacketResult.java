package ru.vlabum.chatone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketResult extends Packet {

    {
        setType(PacketType.RESULT);
    }

    @NotNull private boolean success = true;

    public PacketResult(@NotNull Boolean success) { this.success = success; }

    public PacketResult(
            @NotNull final PacketType packetType,
            @NotNull final Boolean success
    ) {
        setType(packetType);
        this.success = success;
    }

}
