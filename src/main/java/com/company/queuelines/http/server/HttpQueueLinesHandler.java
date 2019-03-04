package com.company.queuelines.http.server;

import com.company.queuelines.storage.BlockingQueueStorage;
import com.company.queuelines.http.common.HttpMethod;
import com.company.queuelines.http.common.HttpStatus;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpQueueLinesHandler implements HttpHandler {

    private final BlockingQueueStorage<byte[]> blockingQueueStorage;

    private static final int NO_RESPONSE_BODY_WRITTEN = -1;

    private static final Object WRITE_LOCK = new Object();

    HttpQueueLinesHandler(BlockingQueueStorage<byte[]> blockingQueueStorage) {
        this.blockingQueueStorage = blockingQueueStorage;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(HttpMethod.GET)) {
            byte[] response = blockingQueueStorage.poll();
            exchange.sendResponseHeaders(HttpStatus.SUCCESS_CODE, response.length);
            OutputStream output = exchange.getResponseBody();
            output.write(response);
            output.close();
            return;
        }

        if (exchange.getRequestMethod().equals(HttpMethod.POST)) {
            synchronized (WRITE_LOCK) {
                byte[] requestBody = getBytesFromInputStream(exchange.getRequestBody());
                blockingQueueStorage.add(requestBody);

                exchange.sendResponseHeaders(HttpStatus.SUCCESS_CODE, requestBody.length);
                OutputStream os = exchange.getResponseBody();
                os.write(requestBody);
                os.close();
                return;
            }
        }

        exchange.sendResponseHeaders(HttpStatus.METHOD_NOT_ALLOWED_CODE, NO_RESPONSE_BODY_WRITTEN);
        OutputStream os = exchange.getResponseBody();
        os.close();
    }


    private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }
}