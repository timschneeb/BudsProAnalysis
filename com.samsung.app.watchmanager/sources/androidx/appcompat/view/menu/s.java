package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

/* access modifiers changed from: package-private */
public abstract class s implements z, v, AdapterView.OnItemClickListener {

    /* renamed from: a  reason: collision with root package name */
    private Rect f270a;

    s() {
    }

    protected static int a(ListAdapter listAdapter, ViewGroup viewGroup, Context context, int i) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = listAdapter.getCount();
        ViewGroup viewGroup2 = viewGroup;
        View view = null;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < count; i4++) {
            int itemViewType = listAdapter.getItemViewType(i4);
            if (itemViewType != i3) {
                view = null;
                i3 = itemViewType;
            }
            if (viewGroup2 == null) {
                viewGroup2 = new FrameLayout(context);
            }
            view = listAdapter.getView(i4, view, viewGroup2);
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth >= i) {
                return i;
            }
            if (measuredWidth > i2) {
                i2 = measuredWidth;
            }
        }
        return i2;
    }

    protected static k a(ListAdapter listAdapter) {
        return listAdapter instanceof HeaderViewListAdapter ? (k) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (k) listAdapter;
    }

    protected static boolean b(l lVar) {
        int size = lVar.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = lVar.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                return true;
            }
        }
        return false;
    }

    public abstract void a(int i);

    @Override // androidx.appcompat.view.menu.v
    public void a(Context context, l lVar) {
    }

    public void a(Rect rect) {
        this.f270a = rect;
    }

    public abstract void a(View view);

    public abstract void a(PopupWindow.OnDismissListener onDismissListener);

    public abstract void a(l lVar);

    @Override // androidx.appcompat.view.menu.v
    public boolean a(l lVar, p pVar) {
        return false;
    }

    public abstract void b(int i);

    public abstract void b(boolean z);

    @Override // androidx.appcompat.view.menu.v
    public boolean b(l lVar, p pVar) {
        return false;
    }

    public abstract void c(int i);

    public abstract void c(boolean z);

    /* access modifiers changed from: protected */
    public boolean e() {
        return true;
    }

    public Rect f() {
        return this.f270a;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter();
        a(listAdapter).f249a.a((MenuItem) listAdapter.getItem(i), this, e() ? 0 : 4);
    }
}
