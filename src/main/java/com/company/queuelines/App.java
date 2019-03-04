package com.company.queuelines;

import com.company.queuelines.http.client.HttpQueueLinesClient;
import com.company.queuelines.logic.QueueLineWriter;
import com.company.queuelines.storage.SimpleBlockingBlockingQueueStorage;
import com.company.queuelines.support.StdOutLogger;
import com.company.queuelines.http.server.HttpQueueLinesServer;
import com.company.queuelines.logic.QueueLineReader;
import com.company.queuelines.support.Logger;
import java.io.IOException;

public class App {
    private static Logger logger = new StdOutLogger();

    private static int DEFAULT_PORT = 8888;

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            logger.log("Input file is not provided");
            return;
        }

        if (args.length < 2) {
            logger.log("Output file is not provided");
            return;
        }

        int port = DEFAULT_PORT;
        if (args.length > 2) {
            port = Integer.valueOf(args[2]);
        }

        HttpQueueLinesServer
                .onPortWithStorage(port, new SimpleBlockingBlockingQueueStorage())
                .start();

        logger.log(String.format("Server started on port %d", port));

        HttpQueueLinesClient httpQueueLinesClient = new HttpQueueLinesClient("http://localhost:" + port);

        QueueLineReader reader = new QueueLineReader(httpQueueLinesClient, logger);
        Thread readerThread = new Thread(() -> {
            try {
                reader.startReadingFromFile(args[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        readerThread.start();

        QueueLineWriter writer = new QueueLineWriter(httpQueueLinesClient, logger);
        writer.startWritingToFile(args[1]);

    }

}
