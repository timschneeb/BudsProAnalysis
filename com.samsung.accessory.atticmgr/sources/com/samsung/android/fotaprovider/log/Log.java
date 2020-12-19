package com.samsung.android.fotaprovider.log;

import android.content.Context;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.base.LogDescriptor;
import com.samsung.android.fotaprovider.log.base.LogFileDescriptor;
import com.samsung.android.fotaprovider.log.base.LogLineInfo;
import com.samsung.android.fotaprovider.log.base.Logger;
import com.samsung.android.fotaprovider.log.base.LoggerCollection;
import com.samsung.android.fotaprovider.log.base.LoggerData;
import com.samsung.android.fotaprovider.log.base.LoggerFile;

public class Log {
    public static final LoggerCollection ALL = new LoggerCollection(DATA, FILE);
    public static final LoggerData DATA = LoggerData.newInstance(FotaProviderInitializer.TAG_NAME);
    public static final Logger.Impl DEBUG;
    public static final LoggerFile FILE = new LoggerFile();
    public static final String LOGFILE_DUMPSTATE = ("dumpState_" + FotaProviderInitializer.TAG_NAME + "%d.log");
    public static final int LOGFILE_DUMPSTATE_COUNT = 2;
    public static final int LOGFILE_DUMPSTATE_SIZE = 5242880;
    public static final String LOGFILE_PATH = "log";

    static {
        setTagName();
        LogLineInfo.excludeClass(Log.class);
        if (getSecUtilAvailable()) {
            DEBUG = ALL;
            return;
        }
        DEBUG = new Logger.Impl() {
            /* class com.samsung.android.fotaprovider.log.Log.AnonymousClass2 */

            @Override // com.samsung.android.fotaprovider.log.base.Logger.Core
            public void println(int i, String str) {
                if (i == 3 || i == 4 || i == 5) {
                    Log.ALL.println(i, str);
                }
            }
        };
        LogLineInfo.excludeClass(DEBUG.getClass());
    }

    private static boolean getSecUtilAvailable() {
        try {
            Class.forName("android.util.secutil.Log");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static void H(String str) {
        DEBUG.H(str);
    }

    public static void V(String str) {
        DEBUG.V(str);
    }

    public static void D(String str) {
        DEBUG.D(str);
    }

    public static void I(String str) {
        DEBUG.I(str);
    }

    public static void W(String str) {
        DEBUG.W(str);
    }

    public static void E(String str) {
        DEBUG.E(str);
    }

    public static void printStackTrace(Throwable th) {
        DEBUG.printStackTrace(th);
    }

    private static void setTagName() {
        DATA.setTagName(FotaProviderInitializer.TAG_NAME);
    }

    private static void setLogDescriptor(LogDescriptor logDescriptor) {
        FILE.setLogDescriptor(logDescriptor);
    }

    public static void setDumpState(Context context) {
        if (context != null) {
            setLogDescriptor(new LogDescriptor.Limit(new LogFileDescriptor(context, LOGFILE_PATH, LOGFILE_DUMPSTATE, 2), 5242880));
        }
    }
}
