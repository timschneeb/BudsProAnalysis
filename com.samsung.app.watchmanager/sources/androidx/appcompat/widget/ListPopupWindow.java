package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.z;
import androidx.core.widget.k;
import b.a.j;
import b.e.g.t;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import java.lang.reflect.Method;

public class ListPopupWindow implements z {

    /* renamed from: a  reason: collision with root package name */
    private static Method f376a;

    /* renamed from: b  reason: collision with root package name */
    private static Method f377b = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);

    /* renamed from: c  reason: collision with root package name */
    private static Method f378c = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
    final e A;
    private final d B;
    private final c C;
    private final a D;
    private Runnable E;
    final Handler F;
    private final Rect G;
    private Rect H;
    private boolean I;
    PopupWindow J;

    /* renamed from: d  reason: collision with root package name */
    private Context f379d;
    private ListAdapter e;
    F f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int p;
    private boolean q;
    private boolean r;
    int s;
    private View t;
    private int u;
    private DataSetObserver v;
    private View w;
    private Drawable x;
    private AdapterView.OnItemClickListener y;
    private AdapterView.OnItemSelectedListener z;

    /* access modifiers changed from: private */
    public class a implements Runnable {
        a() {
        }

        public void run() {
            ListPopupWindow.this.a();
        }
    }

    /* access modifiers changed from: private */
    public class b extends DataSetObserver {
        b() {
        }

        public void onChanged() {
            if (ListPopupWindow.this.b()) {
                ListPopupWindow.this.c();
            }
        }

        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public class c implements AbsListView.OnScrollListener {
        c() {
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1 && !ListPopupWindow.this.j() && ListPopupWindow.this.J.getContentView() != null) {
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                listPopupWindow.F.removeCallbacks(listPopupWindow.A);
                ListPopupWindow.this.A.run();
            }
        }
    }

    /* access modifiers changed from: private */
    public class d implements View.OnTouchListener {
        d() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && (popupWindow = ListPopupWindow.this.J) != null && popupWindow.isShowing() && x >= 0 && x < ListPopupWindow.this.J.getWidth() && y >= 0 && y < ListPopupWindow.this.J.getHeight()) {
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                listPopupWindow.F.postDelayed(listPopupWindow.A, 250);
                return false;
            } else if (action != 1) {
                return false;
            } else {
                ListPopupWindow listPopupWindow2 = ListPopupWindow.this;
                listPopupWindow2.F.removeCallbacks(listPopupWindow2.A);
                return false;
            }
        }
    }

    /* access modifiers changed from: private */
    public class e implements Runnable {
        e() {
        }

        public void run() {
            F f = ListPopupWindow.this.f;
            if (f != null && t.v(f) && ListPopupWindow.this.f.getCount() > ListPopupWindow.this.f.getChildCount()) {
                int childCount = ListPopupWindow.this.f.getChildCount();
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                if (childCount <= listPopupWindow.s) {
                    listPopupWindow.J.setInputMethodMode(2);
                    ListPopupWindow.this.c();
                }
            }
        }
    }

    static {
        try {
            f376a = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
        } catch (NoSuchMethodException unused2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
        } catch (NoSuchMethodException unused3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(Context context) {
        this(context, null, b.a.a.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, b.a.a.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.g = -2;
        this.h = -2;
        this.k = GlobalConst.LAUNCH_MODE_BT_SETTING;
        this.m = true;
        this.p = 0;
        this.q = false;
        this.r = false;
        this.s = Integer.MAX_VALUE;
        this.u = 0;
        this.A = new e();
        this.B = new d();
        this.C = new c();
        this.D = new a();
        this.G = new Rect();
        this.f379d = context;
        this.F = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, j.ListPopupWindow, i2, i3);
        this.i = obtainStyledAttributes.getDimensionPixelOffset(j.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.j = obtainStyledAttributes.getDimensionPixelOffset(j.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.j != 0) {
            this.l = true;
        }
        obtainStyledAttributes.recycle();
        this.J = new AppCompatPopupWindow(context, attributeSet, i2, i3);
        this.J.setInputMethodMode(1);
    }

    private int a(View view, int i2, boolean z2) {
        Method method = f377b;
        if (method != null) {
            try {
                return ((Integer) method.invoke(this.J, view, Integer.valueOf(i2), Boolean.valueOf(z2))).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.J.getMaxAvailableHeight(view, i2);
    }

    private void c(boolean z2) {
        Method method = f376a;
        if (method != null) {
            try {
                method.invoke(this.J, Boolean.valueOf(z2));
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v3, resolved type: android.widget.LinearLayout */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0154  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int l() {
        /*
        // Method dump skipped, instructions count: 359
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ListPopupWindow.l():int");
    }

    private void m() {
        View view = this.t;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.t);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public F a(Context context, boolean z2) {
        return new F(context, z2);
    }

    public void a() {
        F f2 = this.f;
        if (f2 != null) {
            f2.setListSelectionHidden(true);
            f2.requestLayout();
        }
    }

    public void a(int i2) {
        this.J.setAnimationStyle(i2);
    }

    public void a(Rect rect) {
        this.H = rect;
    }

    public void a(Drawable drawable) {
        this.J.setBackgroundDrawable(drawable);
    }

    public void a(View view) {
        this.w = view;
    }

    public void a(AdapterView.OnItemClickListener onItemClickListener) {
        this.y = onItemClickListener;
    }

    public void a(ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.v;
        if (dataSetObserver == null) {
            this.v = new b();
        } else {
            ListAdapter listAdapter2 = this.e;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.e = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.v);
        }
        F f2 = this.f;
        if (f2 != null) {
            f2.setAdapter(this.e);
        }
    }

    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.J.setOnDismissListener(onDismissListener);
    }

    public void a(boolean z2) {
        this.I = z2;
        this.J.setFocusable(z2);
    }

    public void b(int i2) {
        Drawable background = this.J.getBackground();
        if (background != null) {
            background.getPadding(this.G);
            Rect rect = this.G;
            this.h = rect.left + rect.right + i2;
            return;
        }
        i(i2);
    }

    public void b(boolean z2) {
        this.o = true;
        this.n = z2;
    }

    @Override // androidx.appcompat.view.menu.z
    public boolean b() {
        return this.J.isShowing();
    }

    @Override // androidx.appcompat.view.menu.z
    public void c() {
        int l2 = l();
        boolean j2 = j();
        k.a(this.J, this.k);
        boolean z2 = true;
        if (!this.J.isShowing()) {
            int i2 = this.h;
            if (i2 == -1) {
                i2 = -1;
            } else if (i2 == -2) {
                i2 = e().getWidth();
            }
            int i3 = this.g;
            if (i3 == -1) {
                l2 = -1;
            } else if (i3 != -2) {
                l2 = i3;
            }
            this.J.setWidth(i2);
            this.J.setHeight(l2);
            c(true);
            this.J.setOutsideTouchable(!this.r && !this.q);
            this.J.setTouchInterceptor(this.B);
            if (this.o) {
                k.a(this.J, this.n);
            }
            Method method = f378c;
            if (method != null) {
                try {
                    method.invoke(this.J, this.H);
                } catch (Exception e2) {
                    Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
                }
            }
            k.a(this.J, e(), this.i, this.j, this.p);
            this.f.setSelection(-1);
            if (!this.I || this.f.isInTouchMode()) {
                a();
            }
            if (!this.I) {
                this.F.post(this.D);
            }
        } else if (t.v(e())) {
            int i4 = this.h;
            if (i4 == -1) {
                i4 = -1;
            } else if (i4 == -2) {
                i4 = e().getWidth();
            }
            int i5 = this.g;
            if (i5 == -1) {
                if (!j2) {
                    l2 = -1;
                }
                if (j2) {
                    this.J.setWidth(this.h == -1 ? -1 : 0);
                    this.J.setHeight(0);
                } else {
                    this.J.setWidth(this.h == -1 ? -1 : 0);
                    this.J.setHeight(-1);
                }
            } else if (i5 != -2) {
                l2 = i5;
            }
            PopupWindow popupWindow = this.J;
            if (this.r || this.q) {
                z2 = false;
            }
            popupWindow.setOutsideTouchable(z2);
            this.J.update(e(), this.i, this.j, i4 < 0 ? -1 : i4, l2 < 0 ? -1 : l2);
        }
    }

    public void c(int i2) {
        this.p = i2;
    }

    @Override // androidx.appcompat.view.menu.z
    public ListView d() {
        return this.f;
    }

    public void d(int i2) {
        this.i = i2;
    }

    @Override // androidx.appcompat.view.menu.z
    public void dismiss() {
        this.J.dismiss();
        m();
        this.J.setContentView(null);
        this.f = null;
        this.F.removeCallbacks(this.A);
    }

    public View e() {
        return this.w;
    }

    public void e(int i2) {
        this.J.setInputMethodMode(i2);
    }

    public Drawable f() {
        return this.J.getBackground();
    }

    public void f(int i2) {
        this.u = i2;
    }

    public int g() {
        return this.i;
    }

    public void g(int i2) {
        F f2 = this.f;
        if (b() && f2 != null) {
            f2.setListSelectionHidden(false);
            f2.setSelection(i2);
            if (f2.getChoiceMode() != 0) {
                f2.setItemChecked(i2, true);
            }
        }
    }

    public int h() {
        if (!this.l) {
            return 0;
        }
        return this.j;
    }

    public void h(int i2) {
        this.j = i2;
        this.l = true;
    }

    public int i() {
        return this.h;
    }

    public void i(int i2) {
        this.h = i2;
    }

    public boolean j() {
        return this.J.getInputMethodMode() == 2;
    }

    public boolean k() {
        return this.I;
    }
}
