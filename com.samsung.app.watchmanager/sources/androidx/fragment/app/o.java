package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* access modifiers changed from: package-private */
public class o extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ViewGroup f776a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f777b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Fragment f778c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ r f779d;

    o(r rVar, ViewGroup viewGroup, View view, Fragment fragment) {
        this.f779d = rVar;
        this.f776a = viewGroup;
        this.f777b = view;
        this.f778c = fragment;
    }

    public void onAnimationEnd(Animator animator) {
        this.f776a.endViewTransition(this.f777b);
        Animator h = this.f778c.h();
        this.f778c.a((Animator) null);
        if (h != null && this.f776a.indexOfChild(this.f777b) < 0) {
            r rVar = this.f779d;
            Fragment fragment = this.f778c;
            rVar.a(fragment, fragment.x(), 0, 0, false);
        }
    }
}
