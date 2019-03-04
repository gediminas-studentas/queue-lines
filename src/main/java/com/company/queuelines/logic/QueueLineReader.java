package com.company.queuelines.logic;

import com.company.queuelines.file.FileReader;
import com.company.queuelines.http.client.HttpQueueLinesClient;
import com.company.queuelines.support.Logger;
import java.io.IOException;

public class QueueLineReader {

    private final HttpQueueLinesClient queueLinesClient;
    private final Logger logger;

    public QueueLineReader(HttpQueueLinesClient queueLinesClient, Logger logger) {
        this.queueLinesClient = queueLinesClient;
        this.logger = logger;
    }

    public void startReadingFromFile(String fileName) throws IOException {
        FileReader.read(fileName).forEach(line -> {
            logger.log(String.format("Sending '%s' from file '%s' to queue", line, fileName));
            queueLinesClient.writeLine(line);
        });
    }
}
