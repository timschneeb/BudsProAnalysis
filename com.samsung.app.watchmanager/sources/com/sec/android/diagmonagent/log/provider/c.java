package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.sec.android.diagmonagent.log.provider.a.b;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static final Uri f1970a = Uri.parse("content://com.sec.android.log.diagmonagent/");

    /* renamed from: b  reason: collision with root package name */
    private static DiagMonProvider f1971b;

    /* renamed from: c  reason: collision with root package name */
    private static a f1972c = null;

    /* renamed from: d  reason: collision with root package name */
    private static Bundle f1973d;
    private static Thread.UncaughtExceptionHandler e;
    private static boolean f = false;
    private static boolean g = false;

    protected static class a {
        public static void a(Context context, a aVar, d dVar) {
            a unused = c.f1972c = aVar;
            if (com.sec.android.diagmonagent.log.provider.a.a.a(context) == 0) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "not installed");
            } else if (com.sec.android.diagmonagent.log.provider.a.a.a(context) == 1) {
                Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "LEGACY DMA");
                a(aVar);
                c.c(context, dVar);
            } else if (com.sec.android.diagmonagent.log.provider.a.a.a(context) == 2) {
                Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "NEW DMA");
                Bundle unused2 = c.f1973d = new Bundle();
                Bundle unused3 = c.f1973d = com.sec.android.diagmonagent.log.provider.a.a.a(c.f1972c);
                if (c.g) {
                    c.b(c.f1973d);
                }
                c.d(context, dVar);
            } else {
                Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Wrong Status");
            }
        }

        public static void a(a aVar) {
            synchronized (c.class) {
                Log.i(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "SetConfiguration");
                a unused = c.f1972c = aVar;
                if (aVar == null) {
                    Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "DiagMonConfiguration is null");
                    return;
                }
                Bundle unused2 = c.f1973d = new Bundle();
                Bundle unused3 = c.f1973d = com.sec.android.diagmonagent.log.provider.a.a.a(c.f1972c);
                c.c(c.f1973d);
                ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
                newSingleThreadExecutor.submit(new e(c.f1972c));
                newSingleThreadExecutor.shutdown();
            }
        }
    }

    public static void a(Context context) {
        String str;
        boolean z;
        boolean d2;
        try {
            if (f) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "UncaughtExceptionLogging is already enabled");
            } else if (f1972c == null) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "UncaughtExceptionLogging Can't be enabled because Configuration is null");
            } else if (!f1972c.j() || !g) {
                String str2 = "D";
                if (g) {
                    d2 = f1972c.d();
                } else if (f1972c.j()) {
                    d2 = f1972c.k() ? f1972c.d() : true;
                    str2 = f1972c.b();
                } else {
                    Log.i(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "value for uncaughtException will be default");
                    str = str2;
                    z = true;
                    f = true;
                    e = Thread.getDefaultUncaughtExceptionHandler();
                    Thread.setDefaultUncaughtExceptionHandler(new b(context, e, f1972c, z, str));
                }
                z = d2;
                str = str2;
                f = true;
                e = Thread.getDefaultUncaughtExceptionHandler();
                Thread.setDefaultUncaughtExceptionHandler(new b(context, e, f1972c, z, str));
            } else {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "UncaughtException Logging and SetConfiguration can't be used at the same time");
            }
        } catch (Exception e2) {
            String str3 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
            Log.e(str3, "failed to enableUncaughtExceptionLogging" + e2);
        }
    }

    public static void a(Context context, String str) {
        String str2;
        String str3;
        try {
            if (f1972c != null) {
                if (f1972c.j()) {
                    str2 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
                    str3 = "setDefaultConfiguration can't be used because CustomLogging is using";
                } else {
                    str2 = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
                    str3 = "setDefaultConfiguration is already set";
                }
                Log.w(str2, str3);
                return;
            }
            a aVar = new a(context);
            aVar.b(str);
            aVar.a("D");
            f1972c = aVar;
            a(false);
        } catch (Exception | NullPointerException unused) {
        }
    }

    protected static void a(boolean z) {
        String str;
        String str2;
        a aVar = f1972c;
        if (aVar == null) {
            Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "can't handle toggleConfigurationStatus");
            return;
        }
        if (z) {
            g = false;
            aVar.a(true);
            str = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
            str2 = "Status is chaged to CustomLogging";
        } else {
            g = true;
            aVar.a(false);
            str = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
            str2 = "Status is chaged to UncaughtException";
        }
        Log.d(str, str2);
    }

    public static boolean b(Bundle bundle) {
        try {
            if (b.a(f1972c.c(), bundle)) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Invalid SR object");
                f1972c = null;
                return false;
            }
            Log.i(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Valid SR object");
            Log.i(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Request Service Registration");
            com.sec.android.diagmonagent.log.provider.a.a.a(f1972c.c().getContentResolver().call(f1970a, "register_service", "registration", bundle));
            return true;
        } catch (Exception | NullPointerException unused) {
            return false;
        }
    }

    protected static void c(Bundle bundle) {
        f1973d = bundle;
    }

    /* access modifiers changed from: private */
    public static boolean c(Context context, d dVar) {
        try {
            if (b.a(f1972c)) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Invalid DiagMonConfiguration");
                return false;
            } else if (b.a(dVar)) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Invalid EventBuilder");
                return false;
            } else {
                Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Valid EventBuilder");
                context.sendBroadcast(com.sec.android.diagmonagent.log.provider.a.a.b(context, f1972c, dVar));
                Log.i(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Report your logs");
                return true;
            }
        } catch (Exception | NullPointerException unused) {
            return false;
        }
    }

    public static a d() {
        try {
            return f1972c;
        } catch (Exception | NullPointerException unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static boolean d(Context context, d dVar) {
        try {
            new Bundle();
            Bundle a2 = com.sec.android.diagmonagent.log.provider.a.a.a(context, f1972c, dVar);
            if (a2 == null) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "No EventObject");
                return false;
            } else if (f1972c == null) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "No Configuration");
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "You have to set DiagMonConfiguration");
                return false;
            } else if (b.a(f1972c.c(), f1973d)) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Invalid SR object");
                return false;
            } else if (b.a(f1972c.c(), a2, f1973d)) {
                Log.w(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Invalid ER object");
                return false;
            } else {
                Log.d(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Valid SR, ER object");
                Log.i(com.sec.android.diagmonagent.log.provider.a.a.f1964a, "Report your logs");
                String str = com.sec.android.diagmonagent.log.provider.a.a.f1964a;
                Log.i(str, "networkMode : " + dVar.h());
                com.sec.android.diagmonagent.log.provider.a.a.a(context.getContentResolver().call(f1970a, "event_report", "eventReport", a2));
                String l = dVar.l();
                if (l.isEmpty()) {
                    return true;
                }
                com.sec.android.diagmonagent.log.provider.a.a.a(l);
                return true;
            }
        } catch (Exception | NullPointerException unused) {
            return false;
        }
    }

    public static DiagMonProvider e() {
        try {
            return f1971b;
        } catch (Exception | NullPointerException unused) {
            return null;
        }
    }

    public static String f() {
        try {
            return String.valueOf((int) c.b.b.a.a.b.f1811a);
        } catch (Exception | NullPointerException unused) {
            return "";
        }
    }

    public static String g() {
        return "S";
    }

    public static boolean h() {
        try {
            return g;
        } catch (Exception | NullPointerException unused) {
            return false;
        }
    }
}
