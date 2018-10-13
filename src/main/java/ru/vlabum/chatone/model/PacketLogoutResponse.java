package ru.vlabum.chatone.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketLogoutResponse extends PacketResult {

    {
        setType(PacketType.LOGOUT_RESPONSE);
    }

}
