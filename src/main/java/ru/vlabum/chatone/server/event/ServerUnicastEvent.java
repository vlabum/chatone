package ru.vlabum.chatone.server.event;

import lombok.Getter;

import java.net.Socket;

@Getter
public class ServerUnicastEvent {

    private final Socket socket;
    private final String message;

    public ServerUnicastEvent(final Socket socket, final String message) {
        this.socket = socket;
        this.message = message;
    }

}
