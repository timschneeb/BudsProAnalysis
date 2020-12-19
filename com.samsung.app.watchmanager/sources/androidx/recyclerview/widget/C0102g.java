package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.g  reason: case insensitive filesystem */
public class C0102g extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView.v f1072a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ View f1073b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ViewPropertyAnimator f1074c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ C0106k f1075d;

    C0102g(C0106k kVar, RecyclerView.v vVar, View view, ViewPropertyAnimator viewPropertyAnimator) {
        this.f1075d = kVar;
        this.f1072a = vVar;
        this.f1073b = view;
        this.f1074c = viewPropertyAnimator;
    }

    public void onAnimationCancel(Animator animator) {
        this.f1073b.setAlpha(1.0f);
    }

    public void onAnimationEnd(Animator animator) {
        this.f1074c.setListener(null);
        this.f1075d.h(this.f1072a);
        this.f1075d.p.remove(this.f1072a);
        this.f1075d.j();
    }

    public void onAnimationStart(Animator animator) {
        this.f1075d.i(this.f1072a);
    }
}
