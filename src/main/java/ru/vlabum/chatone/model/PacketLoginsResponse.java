package ru.vlabum.chatone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketLoginsResponse extends Packet {

    {
        setType(PacketType.LOGINS_RESPONSE);
    }

    private List<String> users = new ArrayList<>();

}
