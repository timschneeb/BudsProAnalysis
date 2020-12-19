package androidx.appcompat.widget;

import android.view.View;

/* access modifiers changed from: package-private */
public class U implements View.OnClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f432a;

    U(SearchView searchView) {
        this.f432a = searchView;
    }

    public void onClick(View view) {
        SearchView searchView = this.f432a;
        if (view == searchView.u) {
            searchView.e();
        } else if (view == searchView.w) {
            searchView.d();
        } else if (view == searchView.v) {
            searchView.f();
        } else if (view == searchView.x) {
            searchView.h();
        } else if (view == searchView.q) {
            searchView.b();
        }
    }
}
