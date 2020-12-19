package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class v extends x {
    v(RecyclerView.i iVar) {
        super(iVar, null);
    }

    @Override // androidx.recyclerview.widget.x
    public int a() {
        return this.f1122a.getWidth();
    }

    @Override // androidx.recyclerview.widget.x
    public int a(View view) {
        return this.f1122a.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).rightMargin;
    }

    @Override // androidx.recyclerview.widget.x
    public void a(int i) {
        this.f1122a.offsetChildrenHorizontal(i);
    }

    @Override // androidx.recyclerview.widget.x
    public int b() {
        return this.f1122a.getWidth() - this.f1122a.getPaddingRight();
    }

    @Override // androidx.recyclerview.widget.x
    public int b(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return this.f1122a.getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
    }

    @Override // androidx.recyclerview.widget.x
    public int c() {
        return this.f1122a.getPaddingRight();
    }

    @Override // androidx.recyclerview.widget.x
    public int c(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return this.f1122a.getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }

    @Override // androidx.recyclerview.widget.x
    public int d() {
        return this.f1122a.getWidthMode();
    }

    @Override // androidx.recyclerview.widget.x
    public int d(View view) {
        return this.f1122a.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).leftMargin;
    }

    @Override // androidx.recyclerview.widget.x
    public int e() {
        return this.f1122a.getHeightMode();
    }

    @Override // androidx.recyclerview.widget.x
    public int e(View view) {
        this.f1122a.getTransformedBoundingBox(view, true, this.f1124c);
        return this.f1124c.right;
    }

    @Override // androidx.recyclerview.widget.x
    public int f() {
        return this.f1122a.getPaddingLeft();
    }

    @Override // androidx.recyclerview.widget.x
    public int f(View view) {
        this.f1122a.getTransformedBoundingBox(view, true, this.f1124c);
        return this.f1124c.left;
    }

    @Override // androidx.recyclerview.widget.x
    public int g() {
        return (this.f1122a.getWidth() - this.f1122a.getPaddingLeft()) - this.f1122a.getPaddingRight();
    }
}
