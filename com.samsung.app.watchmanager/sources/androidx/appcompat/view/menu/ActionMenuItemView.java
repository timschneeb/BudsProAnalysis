package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.w;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.H;
import androidx.appcompat.widget.qa;
import b.a.j;

public class ActionMenuItemView extends AppCompatTextView implements w.a, View.OnClickListener, ActionMenuView.a {

    /* renamed from: d  reason: collision with root package name */
    p f206d;
    private CharSequence e;
    private Drawable f;
    l.b g;
    private H h;
    b i;
    private boolean j;
    private boolean k;
    private int l;
    private int m;
    private int n;

    private class a extends H {
        public a() {
            super(ActionMenuItemView.this);
        }

        @Override // androidx.appcompat.widget.H
        public z a() {
            b bVar = ActionMenuItemView.this.i;
            if (bVar != null) {
                return bVar.a();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.widget.H
        public boolean b() {
            z a2;
            ActionMenuItemView actionMenuItemView = ActionMenuItemView.this;
            l.b bVar = actionMenuItemView.g;
            return bVar != null && bVar.a(actionMenuItemView.f206d) && (a2 = a()) != null && a2.b();
        }
    }

    public static abstract class b {
        public abstract z a();
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Resources resources = context.getResources();
        this.j = e();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, j.ActionMenuItemView, i2, 0);
        this.l = obtainStyledAttributes.getDimensionPixelSize(j.ActionMenuItemView_android_minWidth, 0);
        obtainStyledAttributes.recycle();
        this.n = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.m = -1;
        setSaveEnabled(false);
    }

    private boolean e() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i2 = configuration.screenWidthDp;
        return i2 >= 480 || (i2 >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    private void f() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.e);
        if (this.f != null && (!this.f206d.n() || (!this.j && !this.k))) {
            z = false;
        }
        boolean z3 = z2 & z;
        CharSequence charSequence = null;
        setText(z3 ? this.e : null);
        CharSequence contentDescription = this.f206d.getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            contentDescription = z3 ? null : this.f206d.getTitle();
        }
        setContentDescription(contentDescription);
        CharSequence tooltipText = this.f206d.getTooltipText();
        if (TextUtils.isEmpty(tooltipText)) {
            if (!z3) {
                charSequence = this.f206d.getTitle();
            }
            qa.a(this, charSequence);
            return;
        }
        qa.a(this, tooltipText);
    }

    @Override // androidx.appcompat.view.menu.w.a
    public void a(p pVar, int i2) {
        this.f206d = pVar;
        setIcon(pVar.getIcon());
        setTitle(pVar.a(this));
        setId(pVar.getItemId());
        setVisibility(pVar.isVisible() ? 0 : 8);
        setEnabled(pVar.isEnabled());
        if (pVar.hasSubMenu() && this.h == null) {
            this.h = new a();
        }
    }

    @Override // androidx.appcompat.widget.ActionMenuView.a
    public boolean a() {
        return d();
    }

    @Override // androidx.appcompat.widget.ActionMenuView.a
    public boolean b() {
        return d() && this.f206d.getIcon() == null;
    }

    @Override // androidx.appcompat.view.menu.w.a
    public boolean c() {
        return true;
    }

    public boolean d() {
        return !TextUtils.isEmpty(getText());
    }

    @Override // androidx.appcompat.view.menu.w.a
    public p getItemData() {
        return this.f206d;
    }

    public void onClick(View view) {
        l.b bVar = this.g;
        if (bVar != null) {
            bVar.a(this.f206d);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.j = e();
        f();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatTextView
    public void onMeasure(int i2, int i3) {
        int i4;
        boolean d2 = d();
        if (d2 && (i4 = this.m) >= 0) {
            super.setPadding(i4, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i2, i3);
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int measuredWidth = getMeasuredWidth();
        int min = mode == Integer.MIN_VALUE ? Math.min(size, this.l) : this.l;
        if (mode != 1073741824 && this.l > 0 && measuredWidth < min) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(min, 1073741824), i3);
        }
        if (!d2 && this.f != null) {
            super.setPadding((getMeasuredWidth() - this.f.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        H h2;
        if (!this.f206d.hasSubMenu() || (h2 = this.h) == null || !h2.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void setCheckable(boolean z) {
    }

    public void setChecked(boolean z) {
    }

    public void setExpandedFormat(boolean z) {
        if (this.k != z) {
            this.k = z;
            p pVar = this.f206d;
            if (pVar != null) {
                pVar.b();
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.f = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i2 = this.n;
            if (intrinsicWidth > i2) {
                intrinsicHeight = (int) (((float) intrinsicHeight) * (((float) i2) / ((float) intrinsicWidth)));
                intrinsicWidth = i2;
            }
            int i3 = this.n;
            if (intrinsicHeight > i3) {
                intrinsicWidth = (int) (((float) intrinsicWidth) * (((float) i3) / ((float) intrinsicHeight)));
                intrinsicHeight = i3;
            }
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        }
        setCompoundDrawables(drawable, null, null, null);
        f();
    }

    public void setItemInvoker(l.b bVar) {
        this.g = bVar;
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
        this.m = i2;
        super.setPadding(i2, i3, i4, i5);
    }

    public void setPopupCallback(b bVar) {
        this.i = bVar;
    }

    public void setShortcut(boolean z, char c2) {
    }

    public void setTitle(CharSequence charSequence) {
        this.e = charSequence;
        f();
    }
}
