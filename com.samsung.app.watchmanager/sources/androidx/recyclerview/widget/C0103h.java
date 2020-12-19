package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.h  reason: case insensitive filesystem */
public class C0103h extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView.v f1076a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f1077b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ View f1078c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ int f1079d;
    final /* synthetic */ ViewPropertyAnimator e;
    final /* synthetic */ C0106k f;

    C0103h(C0106k kVar, RecyclerView.v vVar, int i, View view, int i2, ViewPropertyAnimator viewPropertyAnimator) {
        this.f = kVar;
        this.f1076a = vVar;
        this.f1077b = i;
        this.f1078c = view;
        this.f1079d = i2;
        this.e = viewPropertyAnimator;
    }

    public void onAnimationCancel(Animator animator) {
        if (this.f1077b != 0) {
            this.f1078c.setTranslationX(0.0f);
        }
        if (this.f1079d != 0) {
            this.f1078c.setTranslationY(0.0f);
        }
    }

    public void onAnimationEnd(Animator animator) {
        this.e.setListener(null);
        this.f.j(this.f1076a);
        this.f.q.remove(this.f1076a);
        this.f.j();
    }

    public void onAnimationStart(Animator animator) {
        this.f.k(this.f1076a);
    }
}
