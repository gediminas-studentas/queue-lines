package com.company.queuelines;

import com.company.queuelines.http.client.HttpQueueLinesClient;
import com.company.queuelines.http.server.HttpQueueLinesServer;
import com.company.queuelines.storage.SimpleBlockingBlockingQueueStorage;
import junit.framework.TestCase;

public class AppIntegrationTest extends TestCase {

    public void testGiven_ThreeLinesInQueue_Should_ReceiveThreeLinesFromQueueInOrder() {
        int port = 12345;
        HttpQueueLinesServer server = HttpQueueLinesServer
                .onPortWithStorage(port, new SimpleBlockingBlockingQueueStorage());
        server.start();

        HttpQueueLinesClient httpQueueLinesClient = new HttpQueueLinesClient("http://localhost:" + port);
        httpQueueLinesClient.writeLine("1");
        httpQueueLinesClient.writeLine("2");
        httpQueueLinesClient.writeLine("3");

        assertEquals("1", httpQueueLinesClient.readLine());
        assertEquals("2", httpQueueLinesClient.readLine());
        assertEquals("3", httpQueueLinesClient.readLine());

        server.stop(0);
    }

    public void testWhen_FetchingLineFromQueue_Then_GetThemAsSoonAsItAppear() throws InterruptedException {
        int port = 12346;
        HttpQueueLinesServer server = HttpQueueLinesServer
                .onPortWithStorage(port, new SimpleBlockingBlockingQueueStorage());
        server.start();

        HttpQueueLinesClient httpQueueLinesClient = new HttpQueueLinesClient("http://localhost:" + port);

        new Thread(() -> assertEquals("1", httpQueueLinesClient.readLine())).start();

        Thread.sleep(200); //simulate delay
        httpQueueLinesClient.writeLine("1");

        server.stop(0);
    }
}
