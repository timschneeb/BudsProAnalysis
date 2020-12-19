package b.e.g;

import android.view.View;
import android.view.ViewGroup;

public class n {

    /* renamed from: a  reason: collision with root package name */
    private final ViewGroup f1424a;

    /* renamed from: b  reason: collision with root package name */
    private int f1425b;

    public n(ViewGroup viewGroup) {
        this.f1424a = viewGroup;
    }

    public int a() {
        return this.f1425b;
    }

    public void a(View view) {
        a(view, 0);
    }

    public void a(View view, int i) {
        this.f1425b = 0;
    }

    public void a(View view, View view2, int i) {
        a(view, view2, i, 0);
    }

    public void a(View view, View view2, int i, int i2) {
        this.f1425b = i;
    }
}
