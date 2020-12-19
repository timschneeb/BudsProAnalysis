package androidx.appcompat.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import androidx.core.widget.k;
import b.a.j;

/* access modifiers changed from: package-private */
public class AppCompatPopupWindow extends PopupWindow {

    /* renamed from: a  reason: collision with root package name */
    private static final boolean f325a = (Build.VERSION.SDK_INT < 21);

    /* renamed from: b  reason: collision with root package name */
    private boolean f326b;

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet, i, 0);
    }

    public AppCompatPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(context, attributeSet, i, i2);
    }

    private void a(Context context, AttributeSet attributeSet, int i, int i2) {
        ia a2 = ia.a(context, attributeSet, j.PopupWindow, i, i2);
        if (a2.g(j.PopupWindow_overlapAnchor)) {
            a(a2.a(j.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable(a2.b(j.PopupWindow_android_popupBackground));
        a2.a();
    }

    private void a(boolean z) {
        if (f325a) {
            this.f326b = z;
        } else {
            k.a(this, z);
        }
    }

    public void showAsDropDown(View view, int i, int i2) {
        if (f325a && this.f326b) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2);
    }

    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (f325a && this.f326b) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2, i3);
    }

    @Override // android.widget.PopupWindow
    public void update(View view, int i, int i2, int i3, int i4) {
        if (f325a && this.f326b) {
            i2 -= view.getHeight();
        }
        super.update(view, i, i2, i3, i4);
    }
}
