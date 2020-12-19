package androidx.recyclerview.widget;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import b.e.g.C0111a;
import b.e.g.a.b;

public class H extends C0111a {

    /* renamed from: c  reason: collision with root package name */
    final RecyclerView f947c;

    /* renamed from: d  reason: collision with root package name */
    final C0111a f948d = new a(this);

    public static class a extends C0111a {

        /* renamed from: c  reason: collision with root package name */
        final H f949c;

        public a(H h) {
            this.f949c = h;
        }

        @Override // b.e.g.C0111a
        public void a(View view, b bVar) {
            super.a(view, bVar);
            if (!this.f949c.c() && this.f949c.f947c.getLayoutManager() != null) {
                this.f949c.f947c.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(view, bVar);
            }
        }

        @Override // b.e.g.C0111a
        public boolean a(View view, int i, Bundle bundle) {
            if (super.a(view, i, bundle)) {
                return true;
            }
            if (this.f949c.c() || this.f949c.f947c.getLayoutManager() == null) {
                return false;
            }
            return this.f949c.f947c.getLayoutManager().performAccessibilityActionForItem(view, i, bundle);
        }
    }

    public H(RecyclerView recyclerView) {
        this.f947c = recyclerView;
    }

    @Override // b.e.g.C0111a
    public void a(View view, b bVar) {
        super.a(view, bVar);
        bVar.a((CharSequence) RecyclerView.class.getName());
        if (!c() && this.f947c.getLayoutManager() != null) {
            this.f947c.getLayoutManager().onInitializeAccessibilityNodeInfo(bVar);
        }
    }

    @Override // b.e.g.C0111a
    public boolean a(View view, int i, Bundle bundle) {
        if (super.a(view, i, bundle)) {
            return true;
        }
        if (c() || this.f947c.getLayoutManager() == null) {
            return false;
        }
        return this.f947c.getLayoutManager().performAccessibilityAction(i, bundle);
    }

    public C0111a b() {
        return this.f948d;
    }

    @Override // b.e.g.C0111a
    public void b(View view, AccessibilityEvent accessibilityEvent) {
        super.b(view, accessibilityEvent);
        accessibilityEvent.setClassName(RecyclerView.class.getName());
        if ((view instanceof RecyclerView) && !c()) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onInitializeAccessibilityEvent(accessibilityEvent);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.f947c.j();
    }
}
