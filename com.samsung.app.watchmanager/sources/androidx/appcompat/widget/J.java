package androidx.appcompat.widget;

import android.view.View;
import android.widget.AdapterView;

/* access modifiers changed from: package-private */
public class J implements AdapterView.OnItemSelectedListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ListPopupWindow f368a;

    J(ListPopupWindow listPopupWindow) {
        this.f368a = listPopupWindow;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        F f;
        if (i != -1 && (f = this.f368a.f) != null) {
            f.setListSelectionHidden(false);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
