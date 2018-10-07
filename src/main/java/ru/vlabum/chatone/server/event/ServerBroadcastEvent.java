package ru.vlabum.chatone.server.event;

import lombok.Getter;

import java.net.Socket;

@Getter
public class ServerBroadcastEvent {

    private final Socket socket;
    private final String message;

    public ServerBroadcastEvent(final Socket socket, final String message) {
        this.socket = socket;
        this.message = message;
    }

}
