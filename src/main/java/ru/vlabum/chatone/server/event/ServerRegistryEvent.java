package ru.vlabum.chatone.server.event;

import lombok.Getter;

import java.net.Socket;

@Getter
public class ServerRegistryEvent {

    private final Socket socket;
    private final String message;

    public ServerRegistryEvent(final Socket socket, final String message) {
        this.socket = socket;
        this.message = message;
    }

}
