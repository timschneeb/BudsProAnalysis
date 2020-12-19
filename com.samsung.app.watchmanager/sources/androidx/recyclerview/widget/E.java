package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.N;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class E implements N.b {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView.i f939a;

    E(RecyclerView.i iVar) {
        this.f939a = iVar;
    }

    @Override // androidx.recyclerview.widget.N.b
    public int a() {
        return this.f939a.getPaddingLeft();
    }

    @Override // androidx.recyclerview.widget.N.b
    public int a(View view) {
        return this.f939a.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).leftMargin;
    }

    @Override // androidx.recyclerview.widget.N.b
    public View a(int i) {
        return this.f939a.getChildAt(i);
    }

    @Override // androidx.recyclerview.widget.N.b
    public int b() {
        return this.f939a.getWidth() - this.f939a.getPaddingRight();
    }

    @Override // androidx.recyclerview.widget.N.b
    public int b(View view) {
        return this.f939a.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).rightMargin;
    }
}
