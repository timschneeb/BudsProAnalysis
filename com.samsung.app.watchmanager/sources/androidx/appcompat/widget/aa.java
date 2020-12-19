package androidx.appcompat.widget;

import androidx.appcompat.widget.SearchView;

/* access modifiers changed from: package-private */
public class aa implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SearchView.SearchAutoComplete f448a;

    aa(SearchView.SearchAutoComplete searchAutoComplete) {
        this.f448a = searchAutoComplete;
    }

    public void run() {
        this.f448a.b();
    }
}
