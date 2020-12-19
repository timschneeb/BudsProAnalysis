package androidx.appcompat.widget;

import android.database.DataSetObserver;

/* renamed from: androidx.appcompat.widget.n  reason: case insensitive filesystem */
class C0069n extends DataSetObserver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActivityChooserView f483a;

    C0069n(ActivityChooserView activityChooserView) {
        this.f483a = activityChooserView;
    }

    public void onChanged() {
        super.onChanged();
        this.f483a.d();
    }
}
