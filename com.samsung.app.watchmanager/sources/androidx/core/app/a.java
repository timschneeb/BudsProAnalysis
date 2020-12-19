package androidx.core.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

public class a extends androidx.core.content.a {

    /* renamed from: c  reason: collision with root package name */
    private static b f568c;

    /* renamed from: androidx.core.app.a$a  reason: collision with other inner class name */
    public interface AbstractC0010a {
    }

    public interface b {
        boolean a(Activity activity, int i, int i2, Intent intent);
    }

    public interface c {
    }

    public static b a() {
        return f568c;
    }

    public static void a(Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
        } else {
            activity.finish();
        }
    }
}
