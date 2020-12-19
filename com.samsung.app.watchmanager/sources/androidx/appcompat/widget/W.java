package androidx.appcompat.widget;

import android.view.KeyEvent;
import android.widget.TextView;

/* access modifiers changed from: package-private */
public class W implements TextView.OnEditorActionListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f438a;

    W(SearchView searchView) {
        this.f438a = searchView;
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        this.f438a.f();
        return true;
    }
}
