package ru.vlabum.chatone.server.event;

import java.net.Socket;

public class ServerLogoutEvent {

    private final Socket socket;
    private final String message;

    public ServerLogoutEvent(final Socket socket, final String message) {
        this.socket = socket;
        this.message = message;
    }

}
