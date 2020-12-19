package androidx.appcompat.widget;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.e  reason: case insensitive filesystem */
public class RunnableC0060e implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActionBarOverlayLayout f461a;

    RunnableC0060e(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.f461a = actionBarOverlayLayout;
    }

    public void run() {
        this.f461a.g();
        ActionBarOverlayLayout actionBarOverlayLayout = this.f461a;
        actionBarOverlayLayout.x = actionBarOverlayLayout.e.animate().translationY(0.0f).setListener(this.f461a.y);
    }
}
