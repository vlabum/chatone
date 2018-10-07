package ru.vlabum.chatone.server.model;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;
import java.util.UUID;

@Getter
@Setter
public class Connection {

    private final String id = UUID.randomUUID().toString();

    private final Socket socket;

    private String login;

    public Connection(Socket socket) {
        this.socket = socket;
    }

}
