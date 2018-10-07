package ru.vlabum.chatone.client.handler;

import ru.vlabum.chatone.client.event.ClientMessageViewEvent;
import ru.vlabum.chatone.client.gui.ChatWindow;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientMessageViewHandler {

    public void view(@ObservesAsync final ClientMessageViewEvent event) {
        final ChatWindow window = event.getWindow();
        window.appendMessages(event.getMessage());
    }

}
