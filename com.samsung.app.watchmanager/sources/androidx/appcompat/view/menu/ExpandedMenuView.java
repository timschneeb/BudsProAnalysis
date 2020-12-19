package androidx.appcompat.view.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.ia;

public final class ExpandedMenuView extends ListView implements l.b, w, AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f211a = {16842964, 16843049};

    /* renamed from: b  reason: collision with root package name */
    private l f212b;

    /* renamed from: c  reason: collision with root package name */
    private int f213c;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public ExpandedMenuView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        ia a2 = ia.a(context, attributeSet, f211a, i, 0);
        if (a2.g(0)) {
            setBackgroundDrawable(a2.b(0));
        }
        if (a2.g(1)) {
            setDivider(a2.b(1));
        }
        a2.a();
    }

    @Override // androidx.appcompat.view.menu.w
    public void a(l lVar) {
        this.f212b = lVar;
    }

    @Override // androidx.appcompat.view.menu.l.b
    public boolean a(p pVar) {
        return this.f212b.a(pVar, 0);
    }

    public int getWindowAnimations() {
        return this.f213c;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        a((p) getAdapter().getItem(i));
    }
}
