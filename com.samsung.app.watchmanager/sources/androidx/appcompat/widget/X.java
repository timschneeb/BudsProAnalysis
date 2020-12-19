package androidx.appcompat.widget;

import android.view.View;
import android.widget.AdapterView;

/* access modifiers changed from: package-private */
public class X implements AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f439a;

    X(SearchView searchView) {
        this.f439a = searchView;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.f439a.a(i, 0, (String) null);
    }
}
