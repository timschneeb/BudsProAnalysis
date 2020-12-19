package androidx.appcompat.widget;

import android.view.View;

/* access modifiers changed from: package-private */
public class S implements View.OnFocusChangeListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f394a;

    S(SearchView searchView) {
        this.f394a = searchView;
    }

    public void onFocusChange(View view, boolean z) {
        SearchView searchView = this.f394a;
        View.OnFocusChangeListener onFocusChangeListener = searchView.N;
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(searchView, z);
        }
    }
}
