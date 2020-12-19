package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.view.menu.w;
import java.util.ArrayList;

/* renamed from: androidx.appcompat.view.menu.b  reason: case insensitive filesystem */
public abstract class AbstractC0052b implements v {

    /* renamed from: a  reason: collision with root package name */
    protected Context f222a;

    /* renamed from: b  reason: collision with root package name */
    protected Context f223b;

    /* renamed from: c  reason: collision with root package name */
    protected l f224c;

    /* renamed from: d  reason: collision with root package name */
    protected LayoutInflater f225d;
    protected LayoutInflater e;
    private v.a f;
    private int g;
    private int h;
    protected w i;
    private int j;

    public AbstractC0052b(Context context, int i2, int i3) {
        this.f222a = context;
        this.f225d = LayoutInflater.from(context);
        this.g = i2;
        this.h = i3;
    }

    public View a(p pVar, View view, ViewGroup viewGroup) {
        w.a a2 = view instanceof w.a ? (w.a) view : a(viewGroup);
        a(pVar, a2);
        return (View) a2;
    }

    public w.a a(ViewGroup viewGroup) {
        return (w.a) this.f225d.inflate(this.h, viewGroup, false);
    }

    public void a(int i2) {
        this.j = i2;
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(Context context, l lVar) {
        this.f223b = context;
        this.e = LayoutInflater.from(this.f223b);
        this.f224c = lVar;
    }

    /* access modifiers changed from: protected */
    public void a(View view, int i2) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewGroup) this.i).addView(view, i2);
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(l lVar, boolean z) {
        v.a aVar = this.f;
        if (aVar != null) {
            aVar.a(lVar, z);
        }
    }

    public abstract void a(p pVar, w.a aVar);

    @Override // androidx.appcompat.view.menu.v
    public void a(v.a aVar) {
        this.f = aVar;
    }

    @Override // androidx.appcompat.view.menu.v
    public void a(boolean z) {
        ViewGroup viewGroup = (ViewGroup) this.i;
        if (viewGroup != null) {
            l lVar = this.f224c;
            int i2 = 0;
            if (lVar != null) {
                lVar.b();
                ArrayList<p> n = this.f224c.n();
                int size = n.size();
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    p pVar = n.get(i4);
                    if (a(i3, pVar)) {
                        View childAt = viewGroup.getChildAt(i3);
                        p itemData = childAt instanceof w.a ? ((w.a) childAt).getItemData() : null;
                        View a2 = a(pVar, childAt, viewGroup);
                        if (pVar != itemData) {
                            a2.setPressed(false);
                            a2.jumpDrawablesToCurrentState();
                        }
                        if (a2 != childAt) {
                            a(a2, i3);
                        }
                        i3++;
                    }
                }
                i2 = i3;
            }
            while (i2 < viewGroup.getChildCount()) {
                if (!a(viewGroup, i2)) {
                    i2++;
                }
            }
        }
    }

    public abstract boolean a(int i2, p pVar);

    /* access modifiers changed from: protected */
    public boolean a(ViewGroup viewGroup, int i2) {
        viewGroup.removeViewAt(i2);
        return true;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a(D d2) {
        v.a aVar = this.f;
        if (aVar != null) {
            return aVar.a(d2);
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a(l lVar, p pVar) {
        return false;
    }

    public v.a b() {
        return this.f;
    }

    public w b(ViewGroup viewGroup) {
        if (this.i == null) {
            this.i = (w) this.f225d.inflate(this.g, viewGroup, false);
            this.i.a(this.f224c);
            a(true);
        }
        return this.i;
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean b(l lVar, p pVar) {
        return false;
    }
}
