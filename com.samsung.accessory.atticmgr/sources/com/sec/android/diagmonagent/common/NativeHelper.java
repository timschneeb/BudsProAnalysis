package com.sec.android.diagmonagent.common;

public class NativeHelper {
    public static native char[] getRandomId();

    static {
        System.loadLibrary("DiagMonKey");
    }
}
