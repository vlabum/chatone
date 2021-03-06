package ru.vlabum.chatone.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Пакет-сообщение для одного адресата
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketUnicastRequest extends Packet {

    {
        setType(PacketType.UNICAST_REQUEST);
    }

    private String login; // кому сообщение

    private String message; // сообщение

}
