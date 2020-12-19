package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.AbstractC0052b;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.D;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.view.menu.u;
import androidx.appcompat.view.menu.v;
import androidx.appcompat.view.menu.w;
import androidx.appcompat.view.menu.z;
import androidx.appcompat.widget.ActionMenuView;
import b.a.g;
import b.e.g.AbstractC0112b;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
public class ActionMenuPresenter extends AbstractC0052b implements AbstractC0112b.a {
    a A;
    c B;
    private b C;
    final f D = new f();
    int E;
    d k;
    private Drawable l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int p;
    private int q;
    private int r;
    private boolean s;
    private boolean t;
    private boolean u;
    private boolean v;
    private int w;
    private final SparseBooleanArray x = new SparseBooleanArray();
    private View y;
    e z;

    /* access modifiers changed from: private */
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new C0063h();

        /* renamed from: a  reason: collision with root package name */
        public int f288a;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.f288a = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.f288a);
        }
    }

    /* access modifiers changed from: private */
    public class a extends u {
        public a(Context context, D d2, View view) {
            super(context, d2, view, false, b.a.a.actionOverflowMenuStyle);
            if (!((p) d2.getItem()).h()) {
                View view2 = ActionMenuPresenter.this.k;
                a(view2 == null ? (View) ((AbstractC0052b) ActionMenuPresenter.this).i : view2);
            }
            a(ActionMenuPresenter.this.D);
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.u
        public void d() {
            ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
            actionMenuPresenter.A = null;
            actionMenuPresenter.E = 0;
            super.d();
        }
    }

    private class b extends ActionMenuItemView.b {
        b() {
        }

        @Override // androidx.appcompat.view.menu.ActionMenuItemView.b
        public z a() {
            a aVar = ActionMenuPresenter.this.A;
            if (aVar != null) {
                return aVar.b();
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public class c implements Runnable {

        /* renamed from: a  reason: collision with root package name */
        private e f290a;

        public c(e eVar) {
            this.f290a = eVar;
        }

        public void run() {
            if (((AbstractC0052b) ActionMenuPresenter.this).f224c != null) {
                ((AbstractC0052b) ActionMenuPresenter.this).f224c.a();
            }
            View view = (View) ((AbstractC0052b) ActionMenuPresenter.this).i;
            if (!(view == null || view.getWindowToken() == null || !this.f290a.f())) {
                ActionMenuPresenter.this.z = this.f290a;
            }
            ActionMenuPresenter.this.B = null;
        }
    }

    /* access modifiers changed from: private */
    public class d extends AppCompatImageView implements ActionMenuView.a {

        /* renamed from: c  reason: collision with root package name */
        private final float[] f292c = new float[2];

        public d(Context context) {
            super(context, null, b.a.a.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            qa.a(this, getContentDescription());
            setOnTouchListener(new C0062g(this, this, ActionMenuPresenter.this));
        }

        @Override // androidx.appcompat.widget.ActionMenuView.a
        public boolean a() {
            return false;
        }

        @Override // androidx.appcompat.widget.ActionMenuView.a
        public boolean b() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.i();
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int max = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                androidx.core.graphics.drawable.a.a(background, paddingLeft - max, paddingTop - max, paddingLeft + max, paddingTop + max);
            }
            return frame;
        }
    }

    /* access modifiers changed from: private */
    public class e extends u {
        public e(Context context, l lVar, View view, boolean z) {
            super(context, lVar, view, z, b.a.a.actionOverflowMenuStyle);
            a(8388613);
            a(ActionMenuPresenter.this.D);
        }

        /* access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.u
        public void d() {
            if (((AbstractC0052b) ActionMenuPresenter.this).f224c != null) {
                ((AbstractC0052b) ActionMenuPresenter.this).f224c.close();
            }
            ActionMenuPresenter.this.z = null;
            super.d();
        }
    }

    private class f implements v.a {
        f() {
        }

        @Override // androidx.appcompat.view.menu.v.a
        public void a(l lVar, boolean z) {
            if (lVar instanceof D) {
                lVar.m().a(false);
            }
            v.a b2 = ActionMenuPresenter.this.b();
            if (b2 != null) {
                b2.a(lVar, z);
            }
        }

        @Override // androidx.appcompat.view.menu.v.a
        public boolean a(l lVar) {
            if (lVar == null) {
                return false;
            }
            ActionMenuPresenter.this.E = ((D) lVar).getItem().getItemId();
            v.a b2 = ActionMenuPresenter.this.b();
            if (b2 != null) {
                return b2.a(lVar);
            }
            return false;
        }
    }

    public ActionMenuPresenter(Context context) {
        super(context, g.abc_action_menu_layout, g.abc_action_menu_item_layout);
    }

    private View a(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.i;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof w.a) && ((w.a) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    @Override // androidx.appcompat.view.menu.AbstractC0052b
    public View a(p pVar, View view, ViewGroup viewGroup) {
        View actionView = pVar.getActionView();
        if (actionView == null || pVar.f()) {
            actionView = super.a(pVar, view, viewGroup);
        }
        actionView.setVisibility(pVar.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // androidx.appcompat.view.menu.v, androidx.appcompat.view.menu.AbstractC0052b
    public void a(Context context, l lVar) {
        super.a(context, lVar);
        Resources resources = context.getResources();
        b.a.d.a a2 = b.a.d.a.a(context);
        if (!this.o) {
            this.n = a2.g();
        }
        if (!this.u) {
            this.p = a2.b();
        }
        if (!this.s) {
            this.r = a2.c();
        }
        int i = this.p;
        if (this.n) {
            if (this.k == null) {
                this.k = new d(this.f222a);
                if (this.m) {
                    this.k.setImageDrawable(this.l);
                    this.l = null;
                    this.m = false;
                }
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.k.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.k.getMeasuredWidth();
        } else {
            this.k = null;
        }
        this.q = i;
        this.w = (int) (resources.getDisplayMetrics().density * 56.0f);
        this.y = null;
    }

    public void a(Configuration configuration) {
        if (!this.s) {
            this.r = b.a.d.a.a(this.f223b).c();
        }
        l lVar = this.f224c;
        if (lVar != null) {
            lVar.b(true);
        }
    }

    public void a(Drawable drawable) {
        d dVar = this.k;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
            return;
        }
        this.m = true;
        this.l = drawable;
    }

    @Override // androidx.appcompat.view.menu.v, androidx.appcompat.view.menu.AbstractC0052b
    public void a(l lVar, boolean z2) {
        c();
        super.a(lVar, z2);
    }

    @Override // androidx.appcompat.view.menu.AbstractC0052b
    public void a(p pVar, w.a aVar) {
        aVar.a(pVar, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) aVar;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.i);
        if (this.C == null) {
            this.C = new b();
        }
        actionMenuItemView.setPopupCallback(this.C);
    }

    public void a(ActionMenuView actionMenuView) {
        this.i = actionMenuView;
        actionMenuView.a(this.f224c);
    }

    @Override // androidx.appcompat.view.menu.v, androidx.appcompat.view.menu.AbstractC0052b
    public void a(boolean z2) {
        w wVar;
        super.a(z2);
        ((View) this.i).requestLayout();
        l lVar = this.f224c;
        boolean z3 = false;
        if (lVar != null) {
            ArrayList<p> c2 = lVar.c();
            int size = c2.size();
            for (int i = 0; i < size; i++) {
                AbstractC0112b a2 = c2.get(i).a();
                if (a2 != null) {
                    a2.a(this);
                }
            }
        }
        l lVar2 = this.f224c;
        ArrayList<p> j = lVar2 != null ? lVar2.j() : null;
        if (this.n && j != null) {
            int size2 = j.size();
            if (size2 == 1) {
                z3 = !j.get(0).isActionViewExpanded();
            } else if (size2 > 0) {
                z3 = true;
            }
        }
        if (z3) {
            if (this.k == null) {
                this.k = new d(this.f222a);
            }
            ViewGroup viewGroup = (ViewGroup) this.k.getParent();
            if (viewGroup != this.i) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.k);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.i;
                actionMenuView.addView(this.k, actionMenuView.b());
            }
        } else {
            d dVar = this.k;
            if (dVar != null && dVar.getParent() == (wVar = this.i)) {
                ((ViewGroup) wVar).removeView(this.k);
            }
        }
        ((ActionMenuView) this.i).setOverflowReserved(this.n);
    }

    @Override // androidx.appcompat.view.menu.v
    public boolean a() {
        int i;
        ArrayList<p> arrayList;
        int i2;
        int i3;
        int i4;
        boolean z2;
        ActionMenuPresenter actionMenuPresenter = this;
        l lVar = actionMenuPresenter.f224c;
        int i5 = 0;
        if (lVar != null) {
            arrayList = lVar.n();
            i = arrayList.size();
        } else {
            arrayList = null;
            i = 0;
        }
        int i6 = actionMenuPresenter.r;
        int i7 = actionMenuPresenter.q;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) actionMenuPresenter.i;
        int i8 = i6;
        boolean z3 = false;
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < i; i11++) {
            p pVar = arrayList.get(i11);
            if (pVar.k()) {
                i9++;
            } else if (pVar.j()) {
                i10++;
            } else {
                z3 = true;
            }
            if (actionMenuPresenter.v && pVar.isActionViewExpanded()) {
                i8 = 0;
            }
        }
        if (actionMenuPresenter.n && (z3 || i10 + i9 > i8)) {
            i8--;
        }
        int i12 = i8 - i9;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.x;
        sparseBooleanArray.clear();
        if (actionMenuPresenter.t) {
            int i13 = actionMenuPresenter.w;
            i2 = i7 / i13;
            i3 = i13 + ((i7 % i13) / i2);
        } else {
            i3 = 0;
            i2 = 0;
        }
        int i14 = i7;
        int i15 = 0;
        int i16 = 0;
        while (i15 < i) {
            p pVar2 = arrayList.get(i15);
            if (pVar2.k()) {
                View a2 = actionMenuPresenter.a(pVar2, actionMenuPresenter.y, viewGroup);
                if (actionMenuPresenter.y == null) {
                    actionMenuPresenter.y = a2;
                }
                if (actionMenuPresenter.t) {
                    i2 -= ActionMenuView.a(a2, i3, i2, makeMeasureSpec, i5);
                } else {
                    a2.measure(makeMeasureSpec, makeMeasureSpec);
                }
                int measuredWidth = a2.getMeasuredWidth();
                i14 -= measuredWidth;
                if (i16 != 0) {
                    measuredWidth = i16;
                }
                int groupId = pVar2.getGroupId();
                if (groupId != 0) {
                    z2 = true;
                    sparseBooleanArray.put(groupId, true);
                } else {
                    z2 = true;
                }
                pVar2.d(z2);
                i4 = i;
                i16 = measuredWidth;
            } else if (pVar2.j()) {
                int groupId2 = pVar2.getGroupId();
                boolean z4 = sparseBooleanArray.get(groupId2);
                boolean z5 = (i12 > 0 || z4) && i14 > 0 && (!actionMenuPresenter.t || i2 > 0);
                if (z5) {
                    boolean z6 = z5;
                    View a3 = actionMenuPresenter.a(pVar2, actionMenuPresenter.y, viewGroup);
                    i4 = i;
                    if (actionMenuPresenter.y == null) {
                        actionMenuPresenter.y = a3;
                    }
                    if (actionMenuPresenter.t) {
                        int a4 = ActionMenuView.a(a3, i3, i2, makeMeasureSpec, 0);
                        i2 -= a4;
                        if (a4 == 0) {
                            z6 = false;
                        }
                    } else {
                        a3.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                    int measuredWidth2 = a3.getMeasuredWidth();
                    i14 -= measuredWidth2;
                    if (i16 == 0) {
                        i16 = measuredWidth2;
                    }
                    z5 = z6 & (!actionMenuPresenter.t ? i14 + i16 > 0 : i14 >= 0);
                } else {
                    i4 = i;
                }
                if (z5 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z4) {
                    sparseBooleanArray.put(groupId2, false);
                    for (int i17 = 0; i17 < i15; i17++) {
                        p pVar3 = arrayList.get(i17);
                        if (pVar3.getGroupId() == groupId2) {
                            if (pVar3.h()) {
                                i12++;
                            }
                            pVar3.d(false);
                        }
                    }
                }
                if (z5) {
                    i12--;
                }
                pVar2.d(z5);
            } else {
                i4 = i;
                pVar2.d(false);
                i15++;
                i5 = 0;
                actionMenuPresenter = this;
                i = i4;
            }
            i15++;
            i5 = 0;
            actionMenuPresenter = this;
            i = i4;
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.AbstractC0052b
    public boolean a(int i, p pVar) {
        return pVar.h();
    }

    @Override // androidx.appcompat.view.menu.AbstractC0052b
    public boolean a(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.k) {
            return false;
        }
        return super.a(viewGroup, i);
    }

    @Override // androidx.appcompat.view.menu.v, androidx.appcompat.view.menu.AbstractC0052b
    public boolean a(D d2) {
        boolean z2 = false;
        if (!d2.hasVisibleItems()) {
            return false;
        }
        D d3 = d2;
        while (d3.t() != this.f224c) {
            d3 = (D) d3.t();
        }
        View a2 = a(d3.getItem());
        if (a2 == null) {
            return false;
        }
        this.E = d2.getItem().getItemId();
        int size = d2.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            MenuItem item = d2.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z2 = true;
                break;
            }
            i++;
        }
        this.A = new a(this.f223b, d2, a2);
        this.A.a(z2);
        this.A.e();
        super.a(d2);
        return true;
    }

    @Override // androidx.appcompat.view.menu.AbstractC0052b
    public w b(ViewGroup viewGroup) {
        w wVar = this.i;
        w b2 = super.b(viewGroup);
        if (wVar != b2) {
            ((ActionMenuView) b2).setPresenter(this);
        }
        return b2;
    }

    @Override // b.e.g.AbstractC0112b.a
    public void b(boolean z2) {
        if (z2) {
            super.a((D) null);
            return;
        }
        l lVar = this.f224c;
        if (lVar != null) {
            lVar.a(false);
        }
    }

    public void c(boolean z2) {
        this.v = z2;
    }

    public boolean c() {
        return e() | f();
    }

    public Drawable d() {
        d dVar = this.k;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (this.m) {
            return this.l;
        }
        return null;
    }

    public void d(boolean z2) {
        this.n = z2;
        this.o = true;
    }

    public boolean e() {
        w wVar;
        c cVar = this.B;
        if (cVar == null || (wVar = this.i) == null) {
            e eVar = this.z;
            if (eVar == null) {
                return false;
            }
            eVar.a();
            return true;
        }
        ((View) wVar).removeCallbacks(cVar);
        this.B = null;
        return true;
    }

    public boolean f() {
        a aVar = this.A;
        if (aVar == null) {
            return false;
        }
        aVar.a();
        return true;
    }

    public boolean g() {
        return this.B != null || h();
    }

    public boolean h() {
        e eVar = this.z;
        return eVar != null && eVar.c();
    }

    public boolean i() {
        l lVar;
        if (!this.n || h() || (lVar = this.f224c) == null || this.i == null || this.B != null || lVar.j().isEmpty()) {
            return false;
        }
        this.B = new c(new e(this.f223b, this.f224c, this.k, true));
        ((View) this.i).post(this.B);
        super.a((D) null);
        return true;
    }
}
