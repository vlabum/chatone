package ru.vlabum.chatone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Пакет, пришедший с сервера
 * (устарел) TODO: выкинуть из проекта
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacketMessage extends Packet {

    {
        setType(PacketType.MESSAGE);
    }

    private String login; // от кого сообщение

    private String message; // сообщение

}
