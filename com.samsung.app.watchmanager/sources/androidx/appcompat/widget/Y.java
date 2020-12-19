package androidx.appcompat.widget;

import android.view.View;
import android.widget.AdapterView;

/* access modifiers changed from: package-private */
public class Y implements AdapterView.OnItemSelectedListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f440a;

    Y(SearchView searchView) {
        this.f440a = searchView;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.f440a.d(i);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
