package com.sec.android.diagmonagent.log.provider.exception;

public interface ExceptionParser {
    String getDescription(String str, Throwable th);
}
