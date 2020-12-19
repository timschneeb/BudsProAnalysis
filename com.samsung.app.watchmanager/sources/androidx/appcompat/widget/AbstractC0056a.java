package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import b.a.a;
import b.a.j;
import b.e.g.A;
import b.e.g.t;
import b.e.g.z;

/* access modifiers changed from: package-private */
/* renamed from: androidx.appcompat.widget.a  reason: case insensitive filesystem */
public abstract class AbstractC0056a extends ViewGroup {

    /* renamed from: a  reason: collision with root package name */
    protected final C0009a f441a;

    /* renamed from: b  reason: collision with root package name */
    protected final Context f442b;

    /* renamed from: c  reason: collision with root package name */
    protected ActionMenuView f443c;

    /* renamed from: d  reason: collision with root package name */
    protected ActionMenuPresenter f444d;
    protected int e;
    protected z f;
    private boolean g;
    private boolean h;

    /* access modifiers changed from: protected */
    /* renamed from: androidx.appcompat.widget.a$a  reason: collision with other inner class name */
    public class C0009a implements A {

        /* renamed from: a  reason: collision with root package name */
        private boolean f445a = false;

        /* renamed from: b  reason: collision with root package name */
        int f446b;

        protected C0009a() {
        }

        public C0009a a(z zVar, int i) {
            AbstractC0056a.this.f = zVar;
            this.f446b = i;
            return this;
        }

        @Override // b.e.g.A
        public void a(View view) {
            this.f445a = true;
        }

        @Override // b.e.g.A
        public void b(View view) {
            if (!this.f445a) {
                AbstractC0056a aVar = AbstractC0056a.this;
                aVar.f = null;
                AbstractC0056a.super.setVisibility(this.f446b);
            }
        }

        @Override // b.e.g.A
        public void c(View view) {
            AbstractC0056a.super.setVisibility(0);
            this.f445a = false;
        }
    }

    AbstractC0056a(Context context) {
        this(context, null);
    }

    AbstractC0056a(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    AbstractC0056a(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        this.f441a = new C0009a();
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(a.actionBarPopupTheme, typedValue, true) || (i2 = typedValue.resourceId) == 0) {
            this.f442b = context;
        } else {
            this.f442b = new ContextThemeWrapper(context, i2);
        }
    }

    protected static int a(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    /* access modifiers changed from: protected */
    public int a(View view, int i, int i2, int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i, LinearLayoutManager.INVALID_OFFSET), i2);
        return Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    /* access modifiers changed from: protected */
    public int a(View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = i2 + ((i3 - measuredHeight) / 2);
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    public z a(int i, long j) {
        z zVar = this.f;
        if (zVar != null) {
            zVar.a();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
            }
            z a2 = t.a(this);
            a2.a(1.0f);
            a2.a(j);
            C0009a aVar = this.f441a;
            aVar.a(a2, i);
            a2.a(aVar);
            return a2;
        }
        z a3 = t.a(this);
        a3.a(0.0f);
        a3.a(j);
        C0009a aVar2 = this.f441a;
        aVar2.a(a3, i);
        a3.a(aVar2);
        return a3;
    }

    public int getAnimatedVisibility() {
        return this.f != null ? this.f441a.f446b : getVisibility();
    }

    public int getContentHeight() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, j.ActionBar, a.actionBarStyle, 0);
        setContentHeight(obtainStyledAttributes.getLayoutDimension(j.ActionBar_height, 0));
        obtainStyledAttributes.recycle();
        ActionMenuPresenter actionMenuPresenter = this.f444d;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.a(configuration);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.h = false;
        }
        if (!this.h) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.h = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.h = false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.g = false;
        }
        if (!this.g) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.g = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.g = false;
        }
        return true;
    }

    public abstract void setContentHeight(int i);

    public void setVisibility(int i) {
        if (i != getVisibility()) {
            z zVar = this.f;
            if (zVar != null) {
                zVar.a();
            }
            super.setVisibility(i);
        }
    }
}
