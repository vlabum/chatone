package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.event.ClientMessageViewEvent;
import ru.vlabum.chatone.client.gui.ChatWindow;
import ru.vlabum.chatone.model.PacketBroadcastResponse;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.model.PacketUnicastResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class ClientMessageViewHandler {

    @SneakyThrows
    public void view(@ObservesAsync final ClientMessageViewEvent event) {
        final ChatWindow window = event.getWindow();
        @NotNull final String messageJson = event.getMessage();
        @NotNull final PacketType packetType = event.getPacketType();
        final ObjectMapper mapper = new ObjectMapper();
        switch (packetType) {
            case BROADCAST_RESPONSE:
                PacketBroadcastResponse packetBroadcast = mapper.readValue(messageJson, PacketBroadcastResponse.class);
                window.appendMessages(packetBroadcast.getLogin() + ": " + packetBroadcast.getMessage());
                break;
            case UNICAST_RESPONSE:
                PacketUnicastResponse packetUnicast = mapper.readValue(messageJson, PacketUnicastResponse.class);
                window.appendMessages(packetUnicast.getLogin() + ": " + packetUnicast.getMessage());
                break;
        }
    }

}
