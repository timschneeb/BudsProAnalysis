package androidx.appcompat.widget;

import android.database.DataSetObserver;

/* renamed from: androidx.appcompat.widget.j  reason: case insensitive filesystem */
class C0065j extends DataSetObserver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActivityChooserView f477a;

    C0065j(ActivityChooserView activityChooserView) {
        this.f477a = activityChooserView;
    }

    public void onChanged() {
        super.onChanged();
        this.f477a.f298a.notifyDataSetChanged();
    }

    public void onInvalidated() {
        super.onInvalidated();
        this.f477a.f298a.notifyDataSetInvalidated();
    }
}
