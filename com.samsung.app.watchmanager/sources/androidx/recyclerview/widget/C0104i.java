package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.C0106k;

/* access modifiers changed from: package-private */
/* renamed from: androidx.recyclerview.widget.i  reason: case insensitive filesystem */
public class C0104i extends AnimatorListenerAdapter {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ C0106k.a f1080a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ ViewPropertyAnimator f1081b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ View f1082c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ C0106k f1083d;

    C0104i(C0106k kVar, C0106k.a aVar, ViewPropertyAnimator viewPropertyAnimator, View view) {
        this.f1083d = kVar;
        this.f1080a = aVar;
        this.f1081b = viewPropertyAnimator;
        this.f1082c = view;
    }

    public void onAnimationEnd(Animator animator) {
        this.f1081b.setListener(null);
        this.f1082c.setAlpha(1.0f);
        this.f1082c.setTranslationX(0.0f);
        this.f1082c.setTranslationY(0.0f);
        this.f1083d.a(this.f1080a.f1088a, true);
        this.f1083d.s.remove(this.f1080a.f1088a);
        this.f1083d.j();
    }

    public void onAnimationStart(Animator animator) {
        this.f1083d.b(this.f1080a.f1088a, true);
    }
}
