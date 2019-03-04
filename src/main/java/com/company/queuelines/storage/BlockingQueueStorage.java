package com.company.queuelines.storage;

public interface BlockingQueueStorage<TYPE> {

    TYPE poll();

    boolean add(TYPE value);
}
