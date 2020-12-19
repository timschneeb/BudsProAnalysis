package b.e.g;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/* access modifiers changed from: package-private */
public class x extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ A f1436a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f1437b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ z f1438c;

    x(z zVar, A a2, View view) {
        this.f1438c = zVar;
        this.f1436a = a2;
        this.f1437b = view;
    }

    public void onAnimationCancel(Animator animator) {
        this.f1436a.a(this.f1437b);
    }

    public void onAnimationEnd(Animator animator) {
        this.f1436a.b(this.f1437b);
    }

    public void onAnimationStart(Animator animator) {
        this.f1436a.c(this.f1437b);
    }
}
