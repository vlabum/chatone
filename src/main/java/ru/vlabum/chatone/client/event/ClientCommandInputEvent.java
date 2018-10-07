package ru.vlabum.chatone.client.event;

import lombok.Getter;

@Getter
public class ClientCommandInputEvent {

    private final String command;

    public ClientCommandInputEvent(final String command) {
        this.command = command;
    }

}
