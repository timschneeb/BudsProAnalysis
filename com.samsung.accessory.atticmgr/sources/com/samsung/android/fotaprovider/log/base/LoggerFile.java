package com.samsung.android.fotaprovider.log.base;

import android.os.Process;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.log.base.Logger;
import com.samsung.android.fotaprovider.log.cipher.AESWithRSA;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.util.Calendar;
import java.util.Locale;

public class LoggerFile extends Logger.Impl implements Logger.Core {
    private static final String[] priorities = {"H", "V", DiagMonUtil.AGREE_TYPE_DIAGNOSTIC, "I", "W", "E", "F"};
    private LogDescriptor fd;

    static {
        LogLineInfo.excludeClass(LoggerFile.class);
    }

    public synchronized LogDescriptor getLogFileDescriptor() {
        return this.fd;
    }

    public synchronized void setLogDescriptor(LogDescriptor logDescriptor) {
        this.fd = logDescriptor;
    }

    public LoggerFile() {
        setLogDescriptor(LogDescriptor.NULL);
    }

    @Override // com.samsung.android.fotaprovider.log.base.Logger.Core
    public void println(int i, String str) {
        if (i == 0) {
            str = encrypt(str);
        }
        writeLog(priorities[i], str);
    }

    /* access modifiers changed from: protected */
    public String encrypt(String str) {
        try {
            return AESWithRSA.encrypt(str);
        } catch (Exception e) {
            Log.printStackTrace(e);
            return "[!] Log encryption failed: " + str;
        }
    }

    public synchronized void shift() {
        getLogFileDescriptor().shift();
    }

    private synchronized void writeLog(String str, String str2) {
        getLogFileDescriptor().onBefore();
        getLogFileDescriptor().println(makeLogLine(str, str2));
    }

    private String makeLogLine(String str, String str2) {
        return String.format(Locale.US, "%s [%s] [%5d %5d] %s\n", getDate(), str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()), new LogLineInfo().makeLogLine(str2));
    }

    private static String getDate() {
        Calendar instance = Calendar.getInstance();
        return String.format(Locale.US, "%02d-%02d %02d:%02d:%02d.%03d", Integer.valueOf(instance.get(2) + 1), Integer.valueOf(instance.get(5)), Integer.valueOf(instance.get(11)), Integer.valueOf(instance.get(12)), Integer.valueOf(instance.get(13)), Integer.valueOf(instance.get(14)));
    }
}
