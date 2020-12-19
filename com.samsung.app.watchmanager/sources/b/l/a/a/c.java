package b.l.a.a;

import android.graphics.drawable.Drawable;

/* access modifiers changed from: package-private */
public class c implements Drawable.Callback {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ d f1486a;

    c(d dVar) {
        this.f1486a = dVar;
    }

    public void invalidateDrawable(Drawable drawable) {
        this.f1486a.invalidateSelf();
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        this.f1486a.scheduleSelf(runnable, j);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        this.f1486a.unscheduleSelf(runnable);
    }
}
