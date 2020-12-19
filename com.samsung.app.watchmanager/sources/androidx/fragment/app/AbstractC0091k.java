package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* renamed from: androidx.fragment.app.k  reason: case insensitive filesystem */
public abstract class AbstractC0091k {

    /* renamed from: androidx.fragment.app.k$a */
    public interface a {
    }

    /* renamed from: androidx.fragment.app.k$b */
    public static abstract class b {
        public abstract void a(AbstractC0091k kVar, Fragment fragment);

        public abstract void a(AbstractC0091k kVar, Fragment fragment, Context context);

        public abstract void a(AbstractC0091k kVar, Fragment fragment, Bundle bundle);

        public abstract void a(AbstractC0091k kVar, Fragment fragment, View view, Bundle bundle);

        public abstract void b(AbstractC0091k kVar, Fragment fragment);

        public abstract void b(AbstractC0091k kVar, Fragment fragment, Context context);

        public abstract void b(AbstractC0091k kVar, Fragment fragment, Bundle bundle);

        public abstract void c(AbstractC0091k kVar, Fragment fragment);

        public abstract void c(AbstractC0091k kVar, Fragment fragment, Bundle bundle);

        public abstract void d(AbstractC0091k kVar, Fragment fragment);

        public abstract void d(AbstractC0091k kVar, Fragment fragment, Bundle bundle);

        public abstract void e(AbstractC0091k kVar, Fragment fragment);

        public abstract void f(AbstractC0091k kVar, Fragment fragment);

        public abstract void g(AbstractC0091k kVar, Fragment fragment);
    }

    /* renamed from: androidx.fragment.app.k$c */
    public interface c {
        void onBackStackChanged();
    }

    public abstract Fragment a(String str);

    public abstract w a();

    public abstract void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean b();

    public abstract List<Fragment> c();

    public abstract boolean d();

    public abstract boolean e();
}
