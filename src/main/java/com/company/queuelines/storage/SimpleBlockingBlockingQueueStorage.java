package com.company.queuelines.storage;

import java.util.concurrent.LinkedBlockingQueue;

public class SimpleBlockingBlockingQueueStorage implements BlockingQueueStorage<byte[]> {
    private LinkedBlockingQueue<byte[]> blockingQueue = new LinkedBlockingQueue<>();

    @Override
    public byte[] poll() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean add(byte[] value) {
        return blockingQueue.add(value);
    }
}
