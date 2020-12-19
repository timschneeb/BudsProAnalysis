package androidx.appcompat.widget;

import android.view.View;
import androidx.appcompat.view.menu.z;
import androidx.appcompat.widget.AppCompatSpinner;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.v  reason: case insensitive filesystem */
public class C0076v extends H {
    final /* synthetic */ AppCompatSpinner.b j;
    final /* synthetic */ AppCompatSpinner k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0076v(AppCompatSpinner appCompatSpinner, View view, AppCompatSpinner.b bVar) {
        super(view);
        this.k = appCompatSpinner;
        this.j = bVar;
    }

    @Override // androidx.appcompat.widget.H
    public z a() {
        return this.j;
    }

    @Override // androidx.appcompat.widget.H
    public boolean b() {
        if (this.k.g.b()) {
            return true;
        }
        this.k.g.c();
        return true;
    }
}
