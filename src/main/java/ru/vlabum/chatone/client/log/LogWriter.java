package ru.vlabum.chatone.client.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Записывает в файл сообщения
 */
public class LogWriter {

    public static void writeMessage(final String message) {
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileLogData.FILE_NAME, true));
            bufferedWriter.write(message);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}