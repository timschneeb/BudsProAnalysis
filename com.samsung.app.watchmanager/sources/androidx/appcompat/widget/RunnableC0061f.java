package androidx.appcompat.widget;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.f  reason: case insensitive filesystem */
public class RunnableC0061f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActionBarOverlayLayout f462a;

    RunnableC0061f(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.f462a = actionBarOverlayLayout;
    }

    public void run() {
        this.f462a.g();
        ActionBarOverlayLayout actionBarOverlayLayout = this.f462a;
        actionBarOverlayLayout.x = actionBarOverlayLayout.e.animate().translationY((float) (-this.f462a.e.getHeight())).setListener(this.f462a.y);
    }
}
