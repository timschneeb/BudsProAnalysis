package c.b.a.a.a;

import android.content.Context;
import android.text.TextUtils;
import c.b.a.a.a.b.a;
import com.samsung.android.sdk.bixby2.provider.CapsuleProvider;
import java.util.Map;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static final String f1696a = (c.class.getSimpleName() + "_" + "1.0.7");

    /* renamed from: b  reason: collision with root package name */
    private static c f1697b;

    /* renamed from: c  reason: collision with root package name */
    private static Context f1698c;

    /* renamed from: d  reason: collision with root package name */
    private static String f1699d;
    private static Map<String, a> e;

    private c(Context context) {
        f1698c = context;
    }

    public static void a(Context context) {
        if (context != null) {
            if (f1697b == null) {
                f1697b = new c(context);
            }
            f1697b.a(context.getPackageName());
            CapsuleProvider.b(true);
            String str = f1696a;
            b.a(str, "initialized in package " + f1699d);
            return;
        }
        throw new IllegalArgumentException("App Context is NULL. pass valid context.");
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            f1699d = str;
            return;
        }
        throw new IllegalArgumentException("package name is null or empty.");
    }

    public static synchronized c b() {
        c cVar;
        synchronized (c.class) {
            if (f1697b != null) {
                b.a(f1696a, " getInstance()");
                cVar = f1697b;
            } else {
                throw new IllegalStateException("The Sbixby instance is NULL. do initialize Sbixby before accessing instance.");
            }
        }
        return cVar;
    }

    public static a c() {
        b.a(f1696a, " getStateHandler()");
        return a.a();
    }

    public Map<String, a> a() {
        return e;
    }

    public void a(String str, c.b.a.a.a.a.a aVar) {
        if (TextUtils.isEmpty(str) || aVar == null) {
            throw new IllegalArgumentException("Action handler is NULL. pass valid app action handler implementation.");
        }
        String str2 = f1696a;
        b.a(str2, " addActionHandler: action Id --> " + str);
        CapsuleProvider.a(str, aVar);
    }
}
