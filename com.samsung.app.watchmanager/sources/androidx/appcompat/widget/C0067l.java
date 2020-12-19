package androidx.appcompat.widget;

import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import b.e.g.a.b;

/* renamed from: androidx.appcompat.widget.l  reason: case insensitive filesystem */
class C0067l extends View.AccessibilityDelegate {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ActivityChooserView f481a;

    C0067l(ActivityChooserView activityChooserView) {
        this.f481a = activityChooserView;
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        b.a(accessibilityNodeInfo).b(true);
    }
}
