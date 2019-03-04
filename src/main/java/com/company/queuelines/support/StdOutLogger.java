package com.company.queuelines.support;

public class StdOutLogger implements Logger {

    public void log(String message) {
        System.out.println(message);
    }
}
