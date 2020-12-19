package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.f  reason: case insensitive filesystem */
public class C0101f extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView.v f1068a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ViewPropertyAnimator f1069b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ View f1070c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ C0106k f1071d;

    C0101f(C0106k kVar, RecyclerView.v vVar, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.f1071d = kVar;
        this.f1068a = vVar;
        this.f1069b = viewPropertyAnimator;
        this.f1070c = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.f1069b.setListener(null);
        this.f1070c.setAlpha(1.0f);
        this.f1071d.l(this.f1068a);
        this.f1071d.r.remove(this.f1068a);
        this.f1071d.j();
    }

    public void onAnimationStart(Animator animator) {
        this.f1071d.m(this.f1068a);
    }
}
