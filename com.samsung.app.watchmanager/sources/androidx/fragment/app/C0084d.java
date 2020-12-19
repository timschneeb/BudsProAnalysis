package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/* access modifiers changed from: package-private */
/* renamed from: androidx.fragment.app.d  reason: case insensitive filesystem */
public class C0084d extends AbstractC0088h {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Fragment f763a;

    C0084d(Fragment fragment) {
        this.f763a = fragment;
    }

    @Override // androidx.fragment.app.AbstractC0088h
    public View a(int i) {
        View view = this.f763a.K;
        if (view != null) {
            return view.findViewById(i);
        }
        throw new IllegalStateException("Fragment does not have a view");
    }

    @Override // androidx.fragment.app.AbstractC0088h
    public Fragment a(Context context, String str, Bundle bundle) {
        return this.f763a.u.a(context, str, bundle);
    }

    @Override // androidx.fragment.app.AbstractC0088h
    public boolean a() {
        return this.f763a.K != null;
    }
}
