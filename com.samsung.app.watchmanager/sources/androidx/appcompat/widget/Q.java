package androidx.appcompat.widget;

import android.database.Cursor;
import b.f.a.a;

/* access modifiers changed from: package-private */
public class Q implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView f393a;

    Q(SearchView searchView) {
        this.f393a = searchView;
    }

    public void run() {
        a aVar = this.f393a.S;
        if (aVar != null && (aVar instanceof ba)) {
            aVar.a((Cursor) null);
        }
    }
}
