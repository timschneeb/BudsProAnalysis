package androidx.appcompat.widget;

import android.view.View;

/* access modifiers changed from: package-private */
public class N implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ View f389a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ScrollingTabContainerView f390b;

    N(ScrollingTabContainerView scrollingTabContainerView, View view) {
        this.f390b = scrollingTabContainerView;
        this.f389a = view;
    }

    public void run() {
        this.f390b.smoothScrollTo(this.f389a.getLeft() - ((this.f390b.getWidth() - this.f389a.getWidth()) / 2), 0);
        this.f390b.f396b = null;
    }
}
