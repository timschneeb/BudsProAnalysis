package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.C0106k;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.j  reason: case insensitive filesystem */
public class C0105j extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ C0106k.a f1084a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ViewPropertyAnimator f1085b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ View f1086c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ C0106k f1087d;

    C0105j(C0106k kVar, C0106k.a aVar, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.f1087d = kVar;
        this.f1084a = aVar;
        this.f1085b = viewPropertyAnimator;
        this.f1086c = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.f1085b.setListener(null);
        this.f1086c.setAlpha(1.0f);
        this.f1086c.setTranslationX(0.0f);
        this.f1086c.setTranslationY(0.0f);
        this.f1087d.a(this.f1084a.f1089b, false);
        this.f1087d.s.remove(this.f1084a.f1089b);
        this.f1087d.j();
    }

    public void onAnimationStart(Animator animator) {
        this.f1087d.b(this.f1084a.f1089b, false);
    }
}
