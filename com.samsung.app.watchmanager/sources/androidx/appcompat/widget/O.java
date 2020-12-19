package androidx.appcompat.widget;

import android.text.Editable;
import android.text.TextWatcher;

/* access modifiers changed from: package-private */
public class O implements TextWatcher {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f391a;

    O(SearchView searchView) {
        this.f391a = searchView;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.f391a.b(charSequence);
    }
}
