package ru.vlabum.chatone.client.api;

import ru.vlabum.chatone.api.ChatApp;
import ru.vlabum.chatone.client.gui.ChatWindow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public interface Client extends ChatApp {

    Socket getSocket();
    DataInputStream getIn();
    DataOutputStream getOut();
    void send(String message);
    void run();
    ChatWindow getWindow();
    void exit();

}
