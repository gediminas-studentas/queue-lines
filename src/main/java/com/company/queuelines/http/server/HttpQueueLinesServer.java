package com.company.queuelines.http.server;

import com.company.queuelines.storage.BlockingQueueStorage;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpQueueLinesServer {

    private final HttpServer server;

    public static HttpQueueLinesServer onPortWithStorage(int port, BlockingQueueStorage<byte[]> storage) {
        return new HttpQueueLinesServer(port, storage);
    }

    private HttpQueueLinesServer(int port, BlockingQueueStorage<byte[]> storage) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new RuntimeException("Can't start http queue server", e);
        }
        server.createContext("/lines", new HttpQueueLinesHandler(storage));
        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(2));
    }

    public void start() {
        server.start();
    }

    public void stop(int delay) {
        server.stop(delay);
    }
}
