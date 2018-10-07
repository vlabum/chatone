package ru.vlabum.chatone.server.event;

import lombok.Getter;

import java.net.Socket;

@Getter
public class ServerMessageReadEvent {

    private final Socket socket;

    public ServerMessageReadEvent(final Socket socket) {
        this.socket = socket;
    }

}
