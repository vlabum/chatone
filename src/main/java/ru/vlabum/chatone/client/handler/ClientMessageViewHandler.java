package ru.vlabum.chatone.client.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.client.event.ClientMessageViewEvent;
import ru.vlabum.chatone.client.gui.ChatWindow;
import ru.vlabum.chatone.model.PacketBroadcastResponse;
import ru.vlabum.chatone.model.PacketLoginsResponse;
import ru.vlabum.chatone.model.PacketType;
import ru.vlabum.chatone.model.PacketUnicastResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;

@ApplicationScoped
public class ClientMessageViewHandler {

    @SneakyThrows
    public void view(@ObservesAsync final ClientMessageViewEvent event) {
        final ChatWindow window = event.getWindow();
        @NotNull final PacketType packetType = event.getPacketType();
        switch (packetType) {
            case BROADCAST_RESPONSE:
                PacketBroadcastResponse packetBroadcast = (PacketBroadcastResponse)event.getPacket();
                window.appendMessages(packetBroadcast.getLogin() + ": " + packetBroadcast.getMessage());
                break;
            case UNICAST_RESPONSE:
                PacketUnicastResponse packetUnicast = (PacketUnicastResponse)event.getPacket();
                window.appendMessages(packetUnicast.getLogin() + ": " + packetUnicast.getMessage());
                break;
            case LOGINS_RESPONSE:
                PacketLoginsResponse loginsResponse = (PacketLoginsResponse)event.getPacket();
                window.appendMessages(loginsResponse.getUsers().toString());
        }
    }

}
