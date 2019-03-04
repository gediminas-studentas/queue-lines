package com.company.queuelines.http.client;

import com.company.queuelines.http.common.BaseHttpClient;

public class HttpQueueLinesClient {

    private final BaseHttpClient baseHttpClient;
    private final String baseUrl;

    private final static String LINES_PATH = "/lines";

    public HttpQueueLinesClient(String baseUrl) {
        this.baseHttpClient = new BaseHttpClient();
        this.baseUrl = baseUrl;
    }

    public void writeLine(String line) {
        try {
            baseHttpClient.sendPost(linesPath(), line);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write a line", e);
        }
    }

    public String readLine() {
        try {
            return baseHttpClient.sendGet(linesPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read a line", e);
        }
    }

    private String linesPath() {
        return baseUrl + LINES_PATH;
    }
}
