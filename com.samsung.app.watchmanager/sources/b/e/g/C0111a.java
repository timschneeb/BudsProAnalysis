package b.e.g;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import b.e.g.a.b;
import b.e.g.a.c;

/* renamed from: b.e.g.a  reason: case insensitive filesystem */
public class C0111a {

    /* renamed from: a  reason: collision with root package name */
    private static final View.AccessibilityDelegate f1399a = new View.AccessibilityDelegate();

    /* renamed from: b  reason: collision with root package name */
    private final View.AccessibilityDelegate f1400b = new C0024a(this);

    /* renamed from: b.e.g.a$a  reason: collision with other inner class name */
    private static final class C0024a extends View.AccessibilityDelegate {

        /* renamed from: a  reason: collision with root package name */
        private final C0111a f1401a;

        C0024a(C0111a aVar) {
            this.f1401a = aVar;
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            return this.f1401a.a(view, accessibilityEvent);
        }

        public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
            c a2 = this.f1401a.a(view);
            if (a2 != null) {
                return (AccessibilityNodeProvider) a2.a();
            }
            return null;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            this.f1401a.b(view, accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            this.f1401a.a(view, b.a(accessibilityNodeInfo));
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            this.f1401a.c(view, accessibilityEvent);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return this.f1401a.a(viewGroup, view, accessibilityEvent);
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return this.f1401a.a(view, i, bundle);
        }

        public void sendAccessibilityEvent(View view, int i) {
            this.f1401a.a(view, i);
        }

        public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            this.f1401a.d(view, accessibilityEvent);
        }
    }

    /* access modifiers changed from: package-private */
    public View.AccessibilityDelegate a() {
        return this.f1400b;
    }

    public c a(View view) {
        AccessibilityNodeProvider accessibilityNodeProvider;
        if (Build.VERSION.SDK_INT < 16 || (accessibilityNodeProvider = f1399a.getAccessibilityNodeProvider(view)) == null) {
            return null;
        }
        return new c(accessibilityNodeProvider);
    }

    public void a(View view, int i) {
        f1399a.sendAccessibilityEvent(view, i);
    }

    public void a(View view, b bVar) {
        f1399a.onInitializeAccessibilityNodeInfo(view, bVar.u());
    }

    public boolean a(View view, int i, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return f1399a.performAccessibilityAction(view, i, bundle);
        }
        return false;
    }

    public boolean a(View view, AccessibilityEvent accessibilityEvent) {
        return f1399a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return f1399a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public void b(View view, AccessibilityEvent accessibilityEvent) {
        f1399a.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public void c(View view, AccessibilityEvent accessibilityEvent) {
        f1399a.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public void d(View view, AccessibilityEvent accessibilityEvent) {
        f1399a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }
}
