package com.company.queuelines.logic;

import com.company.queuelines.file.FileWriter;
import com.company.queuelines.http.client.HttpQueueLinesClient;
import com.company.queuelines.support.Logger;

import java.io.IOException;

public class QueueLineWriter {

    private final HttpQueueLinesClient queueLinesClient;
    private final Logger logger;

    private boolean enabled = false;

    public QueueLineWriter(HttpQueueLinesClient queueLinesClient, Logger logger) {
        this.queueLinesClient = queueLinesClient;
        this.logger = logger;
    }

    public synchronized void startWritingToFile(String fileName) throws IOException {
        if (enabled) {
            return;
        }

        enabled = true;
        while (enabled) {
            String line = queueLinesClient.readLine();
            logger.log(String.format("Writing '%s' from queue to file '%s'", line, fileName));
            FileWriter.writeLine(fileName, line);
        }
    }

    public void stop() {
        enabled = false;
    }
}
