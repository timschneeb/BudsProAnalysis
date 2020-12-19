package androidx.appcompat.app;

import androidx.appcompat.widget.ContentFrameLayout;

class r implements ContentFrameLayout.a {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ AppCompatDelegateImpl f196a;

    r(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.f196a = appCompatDelegateImpl;
    }

    @Override // androidx.appcompat.widget.ContentFrameLayout.a
    public void a() {
    }

    @Override // androidx.appcompat.widget.ContentFrameLayout.a
    public void onDetachedFromWindow() {
        this.f196a.k();
    }
}
