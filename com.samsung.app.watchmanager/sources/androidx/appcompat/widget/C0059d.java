package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.d  reason: case insensitive filesystem */
public class C0059d extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActionBarOverlayLayout f456a;

    C0059d(ActionBarOverlayLayout actionBarOverlayLayout) {
        this.f456a = actionBarOverlayLayout;
    }

    public void onAnimationCancel(Animator animator) {
        ActionBarOverlayLayout actionBarOverlayLayout = this.f456a;
        actionBarOverlayLayout.x = null;
        actionBarOverlayLayout.l = false;
    }

    public void onAnimationEnd(Animator animator) {
        ActionBarOverlayLayout actionBarOverlayLayout = this.f456a;
        actionBarOverlayLayout.x = null;
        actionBarOverlayLayout.l = false;
    }
}
