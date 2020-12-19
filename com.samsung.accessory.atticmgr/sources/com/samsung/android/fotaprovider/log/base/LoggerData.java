package com.samsung.android.fotaprovider.log.base;

import android.os.Build;
import android.util.Log;
import com.samsung.android.fotaprovider.log.base.Logger;
import com.samsung.android.fotaprovider.log.cipher.AESCrypt;

public class LoggerData extends Logger.Impl implements Logger.Core {
    private static final int[] priorities = {3, 2, 3, 4, 5, 6, 7};
    private String tagName;

    static {
        LogLineInfo.excludeClass(LoggerData.class);
    }

    private synchronized String getTagName() {
        return this.tagName;
    }

    public synchronized void setTagName(String str) {
        this.tagName = str;
    }

    private static boolean isEngBinary() {
        return "eng".equals(Build.TYPE);
    }

    public static LoggerData newInstance(String str) {
        if (isEngBinary()) {
            return new LoggerData(str);
        }
        return new DebugOff(str);
    }

    protected LoggerData(String str) {
        setTagName(str);
    }

    @Override // com.samsung.android.fotaprovider.log.base.Logger.Core
    public void println(int i, String str) {
        if (i == 0) {
            str = encrypt(str);
        }
        String makeLogLine = new LogLineInfo().makeLogLine(str);
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                Log.println(priorities[i], getTagName(), makeLogLine);
                return;
            default:
                int i2 = priorities[4];
                String tagName2 = getTagName();
                Log.println(i2, tagName2, "<Logger: Invalid priority: " + i + "> " + makeLogLine);
                return;
        }
    }

    /* access modifiers changed from: protected */
    public String encrypt(String str) {
        return AESCrypt.encrypt(str);
    }

    public static class DebugOff extends LoggerData {
        static {
            LogLineInfo.excludeClass(DebugOff.class);
        }

        protected DebugOff(String str) {
            super(str);
        }

        @Override // com.samsung.android.fotaprovider.log.base.Logger.Core, com.samsung.android.fotaprovider.log.base.LoggerData
        public void println(int i, String str) {
            if (i != 0 && i != 1 && i != 2) {
                LoggerData.super.println(i, str);
            }
        }
    }
}
