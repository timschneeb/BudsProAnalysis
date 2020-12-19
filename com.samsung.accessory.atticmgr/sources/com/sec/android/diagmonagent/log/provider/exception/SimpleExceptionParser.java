package com.sec.android.diagmonagent.log.provider.exception;

public class SimpleExceptionParser implements ExceptionParser {
    private static final String NEWLINE = "\u0007";

    @Override // com.sec.android.diagmonagent.log.provider.exception.ExceptionParser
    public String getDescription(String str, Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        if (th != null) {
            sb.append(th.getClass().getName());
            sb.append(":");
            sb.append(th.getLocalizedMessage());
            sb.append(NEWLINE);
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                sb.append(stackTraceElement.toString());
                sb.append(NEWLINE);
                if (sb.length() > 700) {
                    break;
                }
            }
        }
        return sb.toString();
    }
}
