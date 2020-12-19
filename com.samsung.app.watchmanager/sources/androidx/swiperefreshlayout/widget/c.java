package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import androidx.swiperefreshlayout.widget.d;

/* access modifiers changed from: package-private */
public class c implements Animator.AnimatorListener {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ d.a f1152a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ d f1153b;

    c(d dVar, d.a aVar) {
        this.f1153b = dVar;
        this.f1152a = aVar;
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationEnd(Animator animator) {
    }

    public void onAnimationRepeat(Animator animator) {
        this.f1153b.a(1.0f, this.f1152a, true);
        this.f1152a.l();
        this.f1152a.j();
        d dVar = this.f1153b;
        if (dVar.i) {
            dVar.i = false;
            animator.cancel();
            animator.setDuration(1332);
            animator.start();
            this.f1152a.a(false);
            return;
        }
        dVar.h += 1.0f;
    }

    public void onAnimationStart(Animator animator) {
        this.f1153b.h = 0.0f;
    }
}
