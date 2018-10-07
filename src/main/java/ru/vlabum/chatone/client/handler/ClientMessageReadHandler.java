package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import ru.vlabum.chatone.client.api.Client;
import ru.vlabum.chatone.client.event.ClientMessageReadEvent;
import ru.vlabum.chatone.client.event.ClientMessageViewEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientMessageReadHandler {

    @Inject
    private Client client;

    @Inject
    private Event<ClientMessageViewEvent> clientMessageViewEvent;

    @Inject
    private Event<ClientMessageReadEvent> clientMessageReadEvent;

    @SneakyThrows
    public void read(@ObservesAsync final ClientMessageReadEvent event) {
        final String messageJson = client.getIn().readUTF();
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node =  mapper.readTree(messageJson);
        @Nullable JsonNode message = node.findValue("message");
        if (message == null) {
            clientMessageReadEvent.fireAsync(new ClientMessageReadEvent());
            return;
        }
        @Nullable JsonNode loginNode = node.findValue("login");
        final String sendText = (loginNode == null ? "" : loginNode.asText()+": ");
        clientMessageViewEvent.fireAsync(new ClientMessageViewEvent(sendText + message.asText(), client.getWindow()));
        clientMessageReadEvent.fireAsync(new ClientMessageReadEvent());
    }

}
