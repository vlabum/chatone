package ru.vlabum.chatone.client.log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * Вычитывает из истории последние 10 строк
 */
public class LogReader {

    private static int COUNT_LAST_ROWS = 10;

    private static int SYMBOL_NEW_ROW = 10;

    /**
     * Возвращает последние COUNT_LAST_ROWS строк из файла FileLogData.FILE_NAME
     * @return
     */
    public static String getLastRows() {
        try {
            final File historyLog = new File(FileLogData.FILE_NAME);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(historyLog, "r");
            final long startSeek = getStartSeek(historyLog, randomAccessFile);
            final long endSeek = historyLog.length()-1;
            final int lenBuffer = (int)(endSeek + 1 - startSeek);
            final byte[] buffer = new byte[lenBuffer];
            randomAccessFile.read(buffer);
            randomAccessFile.close();
            final String lastHistory = new String(buffer, Charset.forName("UTF-8"));
            System.out.println(lastHistory);
            return lastHistory;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * находит стартовую позицию для считывания
     * @param historyLog
     * @param randomAccessFile
     * @return
     * @throws IOException
     */
    private static long getStartSeek(final File historyLog, final RandomAccessFile randomAccessFile)
    throws IOException {
        long indexSeek = historyLog.length() - 1;
        if (indexSeek < 0) return 0;
        int count = 0;
        for (; indexSeek >= 0; indexSeek--) {
            randomAccessFile.seek(indexSeek);
            if (randomAccessFile.read() == SYMBOL_NEW_ROW) count++;
            if (count == COUNT_LAST_ROWS) break;
        }
        return (indexSeek < 0 ? 0 : indexSeek);
    }

}