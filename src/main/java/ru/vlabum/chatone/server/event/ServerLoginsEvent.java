package ru.vlabum.chatone.server.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.Socket;

@Setter
@Getter
@NoArgsConstructor
public final class ServerLoginsEvent {

    private Socket socket;

    public ServerLoginsEvent(final Socket socket) {
        this.socket = socket;
    }

}
