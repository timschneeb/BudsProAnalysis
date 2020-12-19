package androidx.appcompat.widget;

import android.view.View;

/* access modifiers changed from: package-private */
public class T implements View.OnLayoutChangeListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f421a;

    T(SearchView searchView) {
        this.f421a = searchView;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.f421a.a();
    }
}
