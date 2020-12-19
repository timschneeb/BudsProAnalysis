package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* access modifiers changed from: package-private */
public class p extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ViewGroup f780a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f781b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Fragment f782c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ r f783d;

    p(r rVar, ViewGroup viewGroup, View view, Fragment fragment) {
        this.f783d = rVar;
        this.f780a = viewGroup;
        this.f781b = view;
        this.f782c = fragment;
    }

    public void onAnimationEnd(Animator animator) {
        this.f780a.endViewTransition(this.f781b);
        animator.removeListener(this);
        View view = this.f782c.K;
        if (view != null) {
            view.setVisibility(8);
        }
    }
}
