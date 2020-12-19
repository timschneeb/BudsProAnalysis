package c.b.a.a.a.b;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import c.b.a.a.a.b;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private static final String f1693a = "a";

    /* renamed from: b  reason: collision with root package name */
    private static a f1694b;

    /* renamed from: c  reason: collision with root package name */
    private AbstractC0033a f1695c = null;

    /* renamed from: c.b.a.a.a.b.a$a  reason: collision with other inner class name */
    public static abstract class AbstractC0033a {
        public abstract String a();

        public abstract String b();
    }

    private a() {
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (f1694b == null) {
                f1694b = new a();
            }
            aVar = f1694b;
        }
        return aVar;
    }

    private c.b.a.a.a.a b(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            String packageName = context.getPackageName();
            Bundle bundle = packageManager.getApplicationInfo(packageName, 128).metaData;
            if (bundle != null && bundle.containsKey("com.samsung.android.bixby.capsuleId")) {
                return new c.b.a.a.a.a(bundle.getString("com.samsung.android.bixby.capsuleId"), packageManager.getPackageInfo(packageName, 0).versionCode);
            }
            String str = f1693a;
            b.b(str, "Can't get Capsule ID from Meta data:" + packageName);
            return null;
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            String str2 = f1693a;
            b.b(str2, "Failed to get Meta data info: " + e.getMessage());
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x009d A[SYNTHETIC, Splitter:B:33:0x009d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String a(android.content.Context r6) {
        /*
        // Method dump skipped, instructions count: 231
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.a.a.a.b.a.a(android.content.Context):java.lang.String");
    }
}
