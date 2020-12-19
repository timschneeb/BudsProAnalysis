package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.N;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class F implements N.b {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ RecyclerView.i f940a;

    F(RecyclerView.i iVar) {
        this.f940a = iVar;
    }

    @Override // androidx.recyclerview.widget.N.b
    public int a() {
        return this.f940a.getPaddingTop();
    }

    @Override // androidx.recyclerview.widget.N.b
    public int a(View view) {
        return this.f940a.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).topMargin;
    }

    @Override // androidx.recyclerview.widget.N.b
    public View a(int i) {
        return this.f940a.getChildAt(i);
    }

    @Override // androidx.recyclerview.widget.N.b
    public int b() {
        return this.f940a.getHeight() - this.f940a.getPaddingBottom();
    }

    @Override // androidx.recyclerview.widget.N.b
    public int b(View view) {
        return this.f940a.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).bottomMargin;
    }
}
