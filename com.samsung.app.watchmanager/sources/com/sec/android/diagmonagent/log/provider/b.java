package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import c.b.b.a.a.a.i.a;
import com.sec.android.diagmonagent.log.provider.c;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.util.ArrayList;

public class b implements Thread.UncaughtExceptionHandler {

    /* renamed from: a  reason: collision with root package name */
    private static a f1966a;

    /* renamed from: b  reason: collision with root package name */
    private static d f1967b;

    /* renamed from: c  reason: collision with root package name */
    private final String f1968c;

    /* renamed from: d  reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f1969d;
    private Context e;
    private String f;
    private boolean g = true;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private boolean n = false;
    private final String[] o = {"logcat -b events -v threadtime -v printable -v uid -d *:v", "cat /proc/meminfo", "df"};

    public b(Context context, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, a aVar, boolean z, String str) {
        this.e = context;
        this.f1968c = context.getApplicationInfo().dataDir + "/exception/";
        this.f1969d = uncaughtExceptionHandler;
        this.f = str;
        this.g = z;
        f1966a = aVar;
        d();
    }

    private File a(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private File a(String str, String str2) {
        if (!a(str).isDirectory()) {
            return null;
        }
        File file = new File(str + "/" + str2);
        try {
            file.createNewFile();
            return file;
        } catch (IOException e2) {
            a.c(e2.getLocalizedMessage());
            return file;
        }
    }

    private String a(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder("=========================================\nService version   : " + packageInfo.versionName + "\nDiagMonSA SDK version : " + c.b.b.a.a.b.f1812b + "\n=========================================\n");
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
                Log.e(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "IOException occurred during getCrashLog");
            }
            return sb.toString();
        } catch (PackageManager.NameNotFoundException unused2) {
            Log.e(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "NameNotFoundException occurred during getAddtionalLog");
            return "";
        }
    }

    private void a() {
        c.a.a(this.e.getApplicationContext(), f1966a, f1967b);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0034, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0035, code lost:
        r7 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0047, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x004b, code lost:
        if (r5 != null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0051, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0052, code lost:
        r5.addSuppressed(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0056, code lost:
        r0.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.io.File r5, java.lang.Throwable r6, java.lang.String r7) {
        /*
        // Method dump skipped, instructions count: 106
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.b.a(java.io.File, java.lang.Throwable, java.lang.String):void");
    }

    private String b() {
        String str = "";
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            StackTraceElement[] stackTrace = thread.getStackTrace();
            if (stackTrace.length < 1) {
                Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "no StackTraceElement");
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

    private void c() {
        File file = new File(this.f1968c);
        if (!file.exists()) {
            Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "The directory doesn't exist.");
            return;
        }
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                c();
            } else {
                file2.delete();
            }
        }
    }

    private void d() {
        d dVar;
        if (c.h()) {
            c.a.a(f1966a);
        }
        this.h = this.f1968c + "diagmon_main.log";
        this.i = this.f1968c + "diagmon.log";
        this.j = this.f1968c + "diagmon_event.log";
        this.k = this.f1968c + "diagmon_thread.log";
        this.l = this.f1968c + "diagmon_memory.log";
        this.m = this.f1968c + "diagmon_storage.log";
        Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Diagmon Logger Init");
        String str = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str, "MAIN_LOG_PATH : " + this.h);
        String str2 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str2, "CRASH_LOG_PATH : " + this.i);
        String str3 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str3, "EVENT_LOG_PATH : " + this.j);
        String str4 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str4, "THREAD_STACK_LOG_PATH : " + this.k);
        String str5 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str5, "MEMORY_LOG_PATH : " + this.l);
        String str6 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
        Log.d(str6, "STORAGE_LOG_PATH : " + this.m);
        if (com.sec.android.diagmonagent.log.provider.a.a.a(this.e) == 1) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.f1968c + "/" + "diagmon.log");
            dVar = new d(this.e);
        } else if (com.sec.android.diagmonagent.log.provider.a.a.a(this.e) == 2) {
            dVar = new d(this.e);
            dVar.c(this.f1968c);
        } else {
            return;
        }
        dVar.a(this.g);
        dVar.b("fatal exception");
        f1967b = dVar;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x00e4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void uncaughtException(java.lang.Thread r6, java.lang.Throwable r7) {
        /*
        // Method dump skipped, instructions count: 237
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.b.uncaughtException(java.lang.Thread, java.lang.Throwable):void");
    }
}
