package com.samsung.android.fotaprovider.log.base;

import com.samsung.android.fotaprovider.FotaProviderInitializer;
import java.util.ArrayList;
import java.util.List;

public class LogLineInfo {
    private static List<String> classNameList = new ArrayList();

    static {
        try {
            excludeClass(Class.forName("dalvik.system.VMStack"));
        } catch (ClassNotFoundException unused) {
        }
        excludeClass(Thread.class, LogLineInfo.class);
    }

    public static void excludeClass(Class<?>... clsArr) {
        for (Class<?> cls : clsArr) {
            classNameList.add(cls.getName());
        }
    }

    /* access modifiers changed from: protected */
    public StackTraceElement peekStack() {
        StackTraceElement[] stackTrace = getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!getClassNameList().contains(stackTraceElement.getClassName())) {
                return stackTraceElement;
            }
        }
        return new StackTraceElement("<getStackTrace() failed>", "<getStackTrace() failed>", "<getStackTrace() failed>", -1);
    }

    /* access modifiers changed from: protected */
    public List<String> getClassNameList() {
        return classNameList;
    }

    /* access modifiers changed from: protected */
    public StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    public String makeLogLine(String str) {
        StackTraceElement peekStack = peekStack();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(peekStack.getClassName());
        sb.append("(");
        sb.append(peekStack.getLineNumber());
        sb.append("/");
        sb.append(peekStack.getMethodName());
        sb.append(")] ");
        if (FotaProviderInitializer.getContext() != null) {
            str = str.replace(FotaProviderInitializer.getContext().getPackageName(), "####");
        }
        sb.append(str);
        return sb.toString();
    }
}
