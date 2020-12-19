package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class m {

    /* renamed from: a  reason: collision with root package name */
    private static int f191a = -1;

    m() {
    }

    public static m a(Activity activity, l lVar) {
        return new AppCompatDelegateImpl(activity, activity.getWindow(), lVar);
    }

    public static m a(Dialog dialog, l lVar) {
        return new AppCompatDelegateImpl(dialog.getContext(), dialog.getWindow(), lVar);
    }

    public static int b() {
        return f191a;
    }

    public abstract <T extends View> T a(int i);

    public abstract void a(Configuration configuration);

    public abstract void a(Bundle bundle);

    public abstract void a(View view);

    public abstract void a(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void a(CharSequence charSequence);

    public abstract boolean a();

    public abstract void b(Bundle bundle);

    public abstract void b(View view, ViewGroup.LayoutParams layoutParams);

    public abstract boolean b(int i);

    public abstract MenuInflater c();

    public abstract void c(int i);

    public abstract void c(Bundle bundle);

    public abstract ActionBar d();

    public abstract void e();

    public abstract void f();

    public abstract void g();

    public abstract void h();

    public abstract void i();

    public abstract void j();
}
