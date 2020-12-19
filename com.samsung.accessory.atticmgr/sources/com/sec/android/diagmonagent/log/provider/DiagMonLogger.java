package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;

public class DiagMonLogger implements Thread.UncaughtExceptionHandler {
    private static final String LOG_CRASH_FILE_NAME = "diagmon.log";
    private static final String LOG_EVENT_FILE_NAME = "diagmon_event.log";
    private static final String LOG_MEMORY_FILE_NAME = "diagmon_memory.log";
    private static final String LOG_STORAGE_FILE_NAME = "diagmon_storage.log";
    private static final String LOG_THREAD_STACK_FILE_NAME = "diagmon_thread.log";
    private static final String TAG = DiagMonLogger.class.getSimpleName();
    private final String DIRECTORY;
    private Context context;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private DiagMonConfig diagmonConfig;
    private EventBuilder eventBuilder;
    private final String[] logcatCmd = {"logcat -b events -v threadtime -v printable -v uid -d *:v", "cat /proc/meminfo", "df"};
    private boolean mIsAppend = false;

    public DiagMonLogger(Context context2, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, DiagMonConfig diagMonConfig) {
        this.context = context2;
        this.defaultUncaughtExceptionHandler = uncaughtExceptionHandler;
        this.diagmonConfig = diagMonConfig;
        this.DIRECTORY = context2.getApplicationInfo().dataDir + "/exception/";
        createEventBuilder();
    }

    private void createEventBuilder() {
        AppLog.d("Diagmon Logger Init");
        AppLog.d("CRASH_LOG_PATH : " + this.DIRECTORY + LOG_CRASH_FILE_NAME);
        AppLog.d("EVENT_LOG_PATH : " + this.DIRECTORY + LOG_EVENT_FILE_NAME);
        AppLog.d("THREAD_STACK_LOG_PATH : " + this.DIRECTORY + LOG_THREAD_STACK_FILE_NAME);
        AppLog.d("MEMORY_LOG_PATH : " + this.DIRECTORY + LOG_MEMORY_FILE_NAME);
        AppLog.d("STORAGE_LOG_PATH : " + this.DIRECTORY + LOG_STORAGE_FILE_NAME);
        int checkDMA = DiagMonUtil.checkDMA(this.context);
        if (checkDMA == 1) {
            this.eventBuilder = new EventBuilder(this.context).setNetworkMode(this.diagmonConfig.getDefaultNetworkMode()).setErrorCode("fatal exception");
        } else if (checkDMA == 2 || checkDMA == 3) {
            this.eventBuilder = new EventBuilder(this.context).setLogPath(this.DIRECTORY).setNetworkMode(this.diagmonConfig.getDefaultNetworkMode()).setErrorCode("fatal exception");
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0109 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void uncaughtException(java.lang.Thread r6, java.lang.Throwable r7) {
        /*
        // Method dump skipped, instructions count: 293
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.DiagMonLogger.uncaughtException(java.lang.Thread, java.lang.Throwable):void");
    }

    private void showEnrichedData() {
        File file = new File(this.DIRECTORY);
        File[] listFiles = file.listFiles();
        if (!file.exists()) {
            AppLog.d("The directory doesn't exist.");
            return;
        }
        for (int i = 0; i < listFiles.length; i++) {
            AppLog.d("[Falcon_DiagMonSDK][2][" + TAG + "]" + listFiles[i].getName());
        }
    }

    private void eventReport() {
        DiagMonSDK.sendUncaughtExceptionEvent(this.eventBuilder);
        AppLog.d("[Falcon_DiagMonSDK][3][" + TAG + "]");
    }

    private void removeLogs() {
        File file = new File(this.DIRECTORY);
        if (!file.exists()) {
            AppLog.d("The directory doesn't exist.");
            return;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    removeLogs();
                } else {
                    file2.delete();
                }
            }
        }
    }

    private String getLogByThreads() {
        String str = "";
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            StackTraceElement[] stackTrace = thread.getStackTrace();
            if (stackTrace.length < 1) {
                AppLog.d("no StackTraceElement");
            } else {
                String str2 = str + "Thread ID : " + thread.getId() + ", Thread's name : " + thread.getName() + "\n";
                for (StackTraceElement stackTraceElement : stackTrace) {
                    str2 = str2 + "\t at " + stackTraceElement.toString() + "\n";
                }
                str = str2 + "\n";
            }
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        return str + "No data";
    }

    private File makeDir(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private File makeFile(String str, String str2) {
        if (!makeDir(str).isDirectory()) {
            return null;
        }
        File file = new File(str + "/" + str2);
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            Debug.LogENG(e.getLocalizedMessage());
            return file;
        }
    }

    private String getLogFromBuffer(Context context2, String str) {
        try {
            PackageInfo packageInfo = context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0);
            if (packageInfo == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder("=========================================\nService version   : " + packageInfo.versionName + "\nDiagMonSA SDK version : " + "6.05.033" + "\n=========================================\n");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(str).getInputStream()));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append("\n");
                }
            } catch (IOException unused) {
                AppLog.e("IOException occurred during getCrashLog");
            }
            return sb.toString();
        } catch (PackageManager.NameNotFoundException unused2) {
            AppLog.e("NameNotFoundException occurred during getAddtionalLog");
            return "";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0032, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0037, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
        r5.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003b, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003e, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0043, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0044, code lost:
        r4.addSuppressed(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0047, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void makeLogFile(java.io.File r4, java.lang.Throwable r5, java.lang.String r6) {
        /*
        // Method dump skipped, instructions count: 106
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.DiagMonLogger.makeLogFile(java.io.File, java.lang.Throwable, java.lang.String):void");
    }
}
