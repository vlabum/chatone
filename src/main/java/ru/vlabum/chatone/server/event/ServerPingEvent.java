package ru.vlabum.chatone.server.event;

import java.net.Socket;

public class ServerPingEvent {

    private final Socket socket;
    private final String message;

    public ServerPingEvent(final Socket socket, final String message) {
        this.socket = socket;
        this.message = message;
    }

}
