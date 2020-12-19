package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import b.e.f.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* renamed from: androidx.fragment.app.j  reason: case insensitive filesystem */
public abstract class AbstractC0090j<E> extends AbstractC0088h {

    /* renamed from: a  reason: collision with root package name */
    private final Activity f767a;

    /* renamed from: b  reason: collision with root package name */
    private final Context f768b;

    /* renamed from: c  reason: collision with root package name */
    private final Handler f769c;

    /* renamed from: d  reason: collision with root package name */
    private final int f770d;
    final r e;

    AbstractC0090j(Activity activity, Context context, Handler handler, int i) {
        this.e = new r();
        this.f767a = activity;
        h.a(context, "context == null");
        this.f768b = context;
        h.a(handler, "handler == null");
        this.f769c = handler;
        this.f770d = i;
    }

    AbstractC0090j(FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, fragmentActivity.f719c, 0);
    }

    /* access modifiers changed from: package-private */
    public abstract void a(Fragment fragment);

    public abstract void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    /* access modifiers changed from: package-private */
    public Activity b() {
        return this.f767a;
    }

    public abstract boolean b(Fragment fragment);

    /* access modifiers changed from: package-private */
    public Context c() {
        return this.f768b;
    }

    /* access modifiers changed from: package-private */
    public r d() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public Handler e() {
        return this.f769c;
    }

    public abstract LayoutInflater f();

    public abstract int g();

    public abstract boolean h();

    public abstract void i();
}
