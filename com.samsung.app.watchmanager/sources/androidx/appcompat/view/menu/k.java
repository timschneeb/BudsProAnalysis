package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.appcompat.view.menu.w;
import java.util.ArrayList;

public class k extends BaseAdapter {

    /* renamed from: a  reason: collision with root package name */
    l f249a;

    /* renamed from: b  reason: collision with root package name */
    private int f250b = -1;

    /* renamed from: c  reason: collision with root package name */
    private boolean f251c;

    /* renamed from: d  reason: collision with root package name */
    private final boolean f252d;
    private final LayoutInflater e;
    private final int f;

    public k(l lVar, LayoutInflater layoutInflater, boolean z, int i) {
        this.f252d = z;
        this.e = layoutInflater;
        this.f249a = lVar;
        this.f = i;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        p f2 = this.f249a.f();
        if (f2 != null) {
            ArrayList<p> j = this.f249a.j();
            int size = j.size();
            for (int i = 0; i < size; i++) {
                if (j.get(i) == f2) {
                    this.f250b = i;
                    return;
                }
            }
        }
        this.f250b = -1;
    }

    public void a(boolean z) {
        this.f251c = z;
    }

    public l b() {
        return this.f249a;
    }

    public int getCount() {
        ArrayList<p> j = this.f252d ? this.f249a.j() : this.f249a.n();
        return this.f250b < 0 ? j.size() : j.size() - 1;
    }

    public p getItem(int i) {
        ArrayList<p> j = this.f252d ? this.f249a.j() : this.f249a.n();
        int i2 = this.f250b;
        if (i2 >= 0 && i >= i2) {
            i++;
        }
        return j.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.e.inflate(this.f, viewGroup, false);
        }
        int groupId = getItem(i).getGroupId();
        int i2 = i - 1;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        listMenuItemView.setGroupDividerEnabled(this.f249a.o() && groupId != (i2 >= 0 ? getItem(i2).getGroupId() : groupId));
        w.a aVar = (w.a) view;
        if (this.f251c) {
            listMenuItemView.setForceShowIcon(true);
        }
        aVar.a(getItem(i), 0);
        return view;
    }

    public void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
