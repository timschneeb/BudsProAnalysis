package androidx.viewpager.widget;

import android.graphics.Rect;
import android.view.View;
import b.e.g.D;
import b.e.g.o;
import b.e.g.t;

/* access modifiers changed from: package-private */
public class g implements o {

    /* renamed from: a  reason: collision with root package name */
    private final Rect f1203a = new Rect();

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ViewPager f1204b;

    g(ViewPager viewPager) {
        this.f1204b = viewPager;
    }

    @Override // b.e.g.o
    public D a(View view, D d2) {
        D b2 = t.b(view, d2);
        if (b2.e()) {
            return b2;
        }
        Rect rect = this.f1203a;
        rect.left = b2.b();
        rect.top = b2.d();
        rect.right = b2.c();
        rect.bottom = b2.a();
        int childCount = this.f1204b.getChildCount();
        for (int i = 0; i < childCount; i++) {
            D a2 = t.a(this.f1204b.getChildAt(i), b2);
            rect.left = Math.min(a2.b(), rect.left);
            rect.top = Math.min(a2.d(), rect.top);
            rect.right = Math.min(a2.c(), rect.right);
            rect.bottom = Math.min(a2.a(), rect.bottom);
        }
        return b2.a(rect.left, rect.top, rect.right, rect.bottom);
    }
}
