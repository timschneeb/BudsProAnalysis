package androidx.appcompat.widget;

import android.view.KeyEvent;
import android.view.View;

/* access modifiers changed from: package-private */
public class V implements View.OnKeyListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f433a;

    V(SearchView searchView) {
        this.f433a = searchView;
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        SearchView searchView = this.f433a;
        if (searchView.ga == null) {
            return false;
        }
        if (searchView.q.isPopupShowing() && this.f433a.q.getListSelection() != -1) {
            return this.f433a.a(view, i, keyEvent);
        }
        if (this.f433a.q.a() || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i != 66) {
            return false;
        }
        view.cancelLongPress();
        SearchView searchView2 = this.f433a;
        searchView2.a(0, (String) null, searchView2.q.getText().toString());
        return true;
    }
}
