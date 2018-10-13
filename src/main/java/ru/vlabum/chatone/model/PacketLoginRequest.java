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
public class PacketLoginRequest extends Packet {

    {
        setType(PacketType.LOGIN_REQUEST);
    }

    @Nullable
    private String login;

    @Nullable
    private String password;

}
