package com.sec.android.diagmonagent.log.provider.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class FullStackExceptionParser implements ExceptionParser {
    @Override // com.sec.android.diagmonagent.log.provider.exception.ExceptionParser
    public String getDescription(String str, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        String obj = stringWriter.toString();
        printWriter.close();
        return "thread(" + str + ") : " + obj;
    }
}
