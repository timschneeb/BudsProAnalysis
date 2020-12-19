package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.C0097b;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class C implements C0097b.AbstractC0016b {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView f937a;

    C(RecyclerView recyclerView) {
        this.f937a = recyclerView;
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public int a() {
        return this.f937a.getChildCount();
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public View a(int i) {
        return this.f937a.getChildAt(i);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void a(View view) {
        RecyclerView.v i = RecyclerView.i(view);
        if (i != null) {
            i.onEnteredHiddenState(this.f937a);
        }
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void a(View view, int i) {
        this.f937a.addView(view, i);
        this.f937a.a(view);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void a(View view, int i, ViewGroup.LayoutParams layoutParams) {
        RecyclerView.v i2 = RecyclerView.i(view);
        if (i2 != null) {
            if (i2.isTmpDetached() || i2.shouldIgnore()) {
                i2.clearTmpDetachFlag();
            } else {
                throw new IllegalArgumentException("Called attach on a child which is not detached: " + i2 + this.f937a.i());
            }
        }
        this.f937a.attachViewToParent(view, i, layoutParams);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public int b(View view) {
        return this.f937a.indexOfChild(view);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void b() {
        int a2 = a();
        for (int i = 0; i < a2; i++) {
            View a3 = a(i);
            this.f937a.b(a3);
            a3.clearAnimation();
        }
        this.f937a.removeAllViews();
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void b(int i) {
        RecyclerView.v i2;
        View a2 = a(i);
        if (!(a2 == null || (i2 = RecyclerView.i(a2)) == null)) {
            if (!i2.isTmpDetached() || i2.shouldIgnore()) {
                i2.addFlags(256);
            } else {
                throw new IllegalArgumentException("called detach on an already detached child " + i2 + this.f937a.i());
            }
        }
        this.f937a.detachViewFromParent((RecyclerView) i);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public RecyclerView.v c(View view) {
        return RecyclerView.i(view);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void c(int i) {
        View childAt = this.f937a.getChildAt(i);
        if (childAt != null) {
            this.f937a.b(childAt);
            childAt.clearAnimation();
        }
        this.f937a.removeViewAt(i);
    }

    @Override // androidx.recyclerview.widget.C0097b.AbstractC0016b
    public void d(View view) {
        RecyclerView.v i = RecyclerView.i(view);
        if (i != null) {
            i.onLeftHiddenState(this.f937a);
        }
    }
}
