package ru.vlabum.chatone.client.event;

import lombok.Getter;
import ru.vlabum.chatone.client.gui.ChatWindow;

@Getter
public class ClientMessageViewEvent {

    private final String message;
    private final ChatWindow window;

    public ClientMessageViewEvent(final String message, final ChatWindow window) {
        this.message = message;
        this.window = window;
    }
}
