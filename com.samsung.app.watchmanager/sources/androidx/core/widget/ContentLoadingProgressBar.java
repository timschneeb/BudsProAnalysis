package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar extends ProgressBar {

    /* renamed from: a  reason: collision with root package name */
    long f632a;

    /* renamed from: b  reason: collision with root package name */
    boolean f633b;

    /* renamed from: c  reason: collision with root package name */
    boolean f634c;

    /* renamed from: d  reason: collision with root package name */
    boolean f635d;
    private final Runnable e;
    private final Runnable f;

    public ContentLoadingProgressBar(Context context) {
        this(context, null);
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.f632a = -1;
        this.f633b = false;
        this.f634c = false;
        this.f635d = false;
        this.e = new d(this);
        this.f = new e(this);
    }

    private void a() {
        removeCallbacks(this.e);
        removeCallbacks(this.f);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }
}
