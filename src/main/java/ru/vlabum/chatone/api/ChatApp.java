package ru.vlabum.chatone.api;

import ru.vlabum.chatone.config.ChatConfig;

import java.util.concurrent.ExecutorService;

/**
 * Интерфейс для сервера и клиента.
 */
public interface ChatApp extends Runnable {

    void run();
}
