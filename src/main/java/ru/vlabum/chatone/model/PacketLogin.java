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
public class PacketLogin extends Packet {

    {
        setType(PacketType.LOGIN);
    }

    @Nullable
    private String login;

    @Nullable
    private String password;
}
