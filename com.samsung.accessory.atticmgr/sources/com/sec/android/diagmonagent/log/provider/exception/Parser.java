package com.sec.android.diagmonagent.log.provider.exception;

public class Parser {

    public enum Type {
        FULL,
        SIMPLE
    }

    private Parser() {
    }

    public static ExceptionParser get(Type type) {
        if (type == Type.FULL) {
            return new FullStackExceptionParser();
        }
        if (type == Type.SIMPLE) {
            return new SimpleExceptionParser();
        }
        return new SimpleExceptionParser();
    }
}
