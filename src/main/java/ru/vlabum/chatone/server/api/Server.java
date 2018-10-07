package ru.vlabum.chatone.server.api;

import org.jetbrains.annotations.NotNull;
import ru.vlabum.chatone.api.ChatApp;

import java.net.ServerSocket;

public interface Server extends ChatApp {

    @NotNull
    ServerSocket getServerSocket();

    void run();

}
