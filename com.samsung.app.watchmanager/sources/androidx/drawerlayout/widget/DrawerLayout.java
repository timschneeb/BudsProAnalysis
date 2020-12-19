package androidx.drawerlayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.customview.view.AbsSavedState;
import b.e.g.C0111a;
import b.e.g.C0113c;
import b.e.g.a.b;
import b.e.g.t;
import b.g.a.c;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f666a = {16843828};

    /* renamed from: b  reason: collision with root package name */
    static final int[] f667b = {16842931};

    /* renamed from: c  reason: collision with root package name */
    static final boolean f668c = (Build.VERSION.SDK_INT >= 19);

    /* renamed from: d  reason: collision with root package name */
    private static final boolean f669d;
    private float A;
    private Drawable B;
    private Drawable C;
    private Drawable D;
    private CharSequence E;
    private CharSequence F;
    private Object G;
    private boolean H;
    private Drawable I;
    private Drawable J;
    private Drawable K;
    private Drawable L;
    private final ArrayList<View> M;
    private Rect N;
    private Matrix O;
    private final b e;
    private float f;
    private int g;
    private int h;
    private float i;
    private Paint j;
    private final b.g.a.c k;
    private final b.g.a.c l;
    private final d m;
    private final d n;
    private int o;
    private boolean p;
    private boolean q;
    private int r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    private c x;
    private List<c> y;
    private float z;

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        /* renamed from: a  reason: collision with root package name */
        public int f670a = 0;

        /* renamed from: b  reason: collision with root package name */
        float f671b;

        /* renamed from: c  reason: collision with root package name */
        boolean f672c;

        /* renamed from: d  reason: collision with root package name */
        int f673d;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.f667b);
            this.f670a = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.f670a = layoutParams.f670a;
        }
    }

    /* access modifiers changed from: protected */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new b();

        /* renamed from: c  reason: collision with root package name */
        int f674c = 0;

        /* renamed from: d  reason: collision with root package name */
        int f675d;
        int e;
        int f;
        int g;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f674c = parcel.readInt();
            this.f675d = parcel.readInt();
            this.e = parcel.readInt();
            this.f = parcel.readInt();
            this.g = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f674c);
            parcel.writeInt(this.f675d);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f);
            parcel.writeInt(this.g);
        }
    }

    class a extends C0111a {

        /* renamed from: c  reason: collision with root package name */
        private final Rect f676c = new Rect();

        a() {
        }

        private void a(b.e.g.a.b bVar, ViewGroup viewGroup) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (DrawerLayout.g(childAt)) {
                    bVar.a(childAt);
                }
            }
        }

        private void a(b.e.g.a.b bVar, b.e.g.a.b bVar2) {
            Rect rect = this.f676c;
            bVar2.a(rect);
            bVar.c(rect);
            bVar2.b(rect);
            bVar.d(rect);
            bVar.j(bVar2.s());
            bVar.c(bVar2.e());
            bVar.a(bVar2.b());
            bVar.b(bVar2.c());
            bVar.d(bVar2.l());
            bVar.c(bVar2.k());
            bVar.e(bVar2.m());
            bVar.f(bVar2.n());
            bVar.a(bVar2.h());
            bVar.i(bVar2.r());
            bVar.g(bVar2.o());
            bVar.a(bVar2.a());
        }

        @Override // b.e.g.C0111a
        public void a(View view, b.e.g.a.b bVar) {
            if (DrawerLayout.f668c) {
                super.a(view, bVar);
            } else {
                b.e.g.a.b a2 = b.e.g.a.b.a(bVar);
                super.a(view, a2);
                bVar.c(view);
                ViewParent n = t.n(view);
                if (n instanceof View) {
                    bVar.b((View) n);
                }
                a(bVar, a2);
                a2.t();
                a(bVar, (ViewGroup) view);
            }
            bVar.a((CharSequence) DrawerLayout.class.getName());
            bVar.e(false);
            bVar.f(false);
            bVar.a(b.a.f1404a);
            bVar.a(b.a.f1405b);
        }

        @Override // b.e.g.C0111a
        public boolean a(View view, AccessibilityEvent accessibilityEvent) {
            CharSequence c2;
            if (accessibilityEvent.getEventType() != 32) {
                return super.a(view, accessibilityEvent);
            }
            List<CharSequence> text = accessibilityEvent.getText();
            View d2 = DrawerLayout.this.d();
            if (d2 == null || (c2 = DrawerLayout.this.c(DrawerLayout.this.e(d2))) == null) {
                return true;
            }
            text.add(c2);
            return true;
        }

        @Override // b.e.g.C0111a
        public boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.f668c || DrawerLayout.g(view)) {
                return super.a(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        @Override // b.e.g.C0111a
        public void b(View view, AccessibilityEvent accessibilityEvent) {
            super.b(view, accessibilityEvent);
            accessibilityEvent.setClassName(DrawerLayout.class.getName());
        }
    }

    static final class b extends C0111a {
        b() {
        }

        @Override // b.e.g.C0111a
        public void a(View view, b.e.g.a.b bVar) {
            super.a(view, bVar);
            if (!DrawerLayout.g(view)) {
                bVar.b((View) null);
            }
        }
    }

    public interface c {
        void a(int i);

        void a(View view);

        void a(View view, float f);

        void b(View view);
    }

    /* access modifiers changed from: private */
    public class d extends c.a {

        /* renamed from: a  reason: collision with root package name */
        private final int f678a;

        /* renamed from: b  reason: collision with root package name */
        private b.g.a.c f679b;

        /* renamed from: c  reason: collision with root package name */
        private final Runnable f680c = new c(this);

        d(int i) {
            this.f678a = i;
        }

        private void c() {
            int i = 3;
            if (this.f678a == 3) {
                i = 5;
            }
            View a2 = DrawerLayout.this.a(i);
            if (a2 != null) {
                DrawerLayout.this.a(a2);
            }
        }

        @Override // b.g.a.c.a
        public int a(View view) {
            if (DrawerLayout.this.j(view)) {
                return view.getWidth();
            }
            return 0;
        }

        @Override // b.g.a.c.a
        public int a(View view, int i, int i2) {
            int width;
            int width2;
            if (DrawerLayout.this.a(view, 3)) {
                width2 = -view.getWidth();
                width = 0;
            } else {
                width = DrawerLayout.this.getWidth();
                width2 = width - view.getWidth();
            }
            return Math.max(width2, Math.min(i, width));
        }

        /* access modifiers changed from: package-private */
        public void a() {
            View view;
            int i;
            int d2 = this.f679b.d();
            int i2 = 0;
            boolean z = this.f678a == 3;
            if (z) {
                view = DrawerLayout.this.a(3);
                if (view != null) {
                    i2 = -view.getWidth();
                }
                i = i2 + d2;
            } else {
                view = DrawerLayout.this.a(5);
                i = DrawerLayout.this.getWidth() - d2;
            }
            if (view == null) {
                return;
            }
            if (((z && view.getLeft() < i) || (!z && view.getLeft() > i)) && DrawerLayout.this.d(view) == 0) {
                this.f679b.b(view, i, view.getTop());
                ((LayoutParams) view.getLayoutParams()).f672c = true;
                DrawerLayout.this.invalidate();
                c();
                DrawerLayout.this.a();
            }
        }

        @Override // b.g.a.c.a
        public void a(int i, int i2) {
            DrawerLayout drawerLayout;
            int i3;
            if ((i & 1) == 1) {
                drawerLayout = DrawerLayout.this;
                i3 = 3;
            } else {
                drawerLayout = DrawerLayout.this;
                i3 = 5;
            }
            View a2 = drawerLayout.a(i3);
            if (a2 != null && DrawerLayout.this.d(a2) == 0) {
                this.f679b.a(a2, i2);
            }
        }

        @Override // b.g.a.c.a
        public void a(View view, float f, float f2) {
            int i;
            float f3 = DrawerLayout.this.f(view);
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                i = (f > 0.0f || (f == 0.0f && f3 > 0.5f)) ? 0 : -width;
            } else {
                int width2 = DrawerLayout.this.getWidth();
                if (f < 0.0f || (f == 0.0f && f3 > 0.5f)) {
                    width2 -= width;
                }
                i = width2;
            }
            this.f679b.d(i, view.getTop());
            DrawerLayout.this.invalidate();
        }

        @Override // b.g.a.c.a
        public void a(View view, int i) {
            ((LayoutParams) view.getLayoutParams()).f672c = false;
            c();
        }

        @Override // b.g.a.c.a
        public void a(View view, int i, int i2, int i3, int i4) {
            int width = view.getWidth();
            float width2 = (DrawerLayout.this.a(view, 3) ? (float) (i + width) : (float) (DrawerLayout.this.getWidth() - i)) / ((float) width);
            DrawerLayout.this.c(view, width2);
            view.setVisibility(width2 == 0.0f ? 4 : 0);
            DrawerLayout.this.invalidate();
        }

        public void a(b.g.a.c cVar) {
            this.f679b = cVar;
        }

        @Override // b.g.a.c.a
        public int b(View view, int i, int i2) {
            return view.getTop();
        }

        public void b() {
            DrawerLayout.this.removeCallbacks(this.f680c);
        }

        @Override // b.g.a.c.a
        public void b(int i, int i2) {
            DrawerLayout.this.postDelayed(this.f680c, 160);
        }

        @Override // b.g.a.c.a
        public boolean b(int i) {
            return false;
        }

        @Override // b.g.a.c.a
        public boolean b(View view, int i) {
            return DrawerLayout.this.j(view) && DrawerLayout.this.a(view, this.f678a) && DrawerLayout.this.d(view) == 0;
        }

        @Override // b.g.a.c.a
        public void c(int i) {
            DrawerLayout.this.a(this.f678a, i, this.f679b.c());
        }
    }

    static {
        boolean z2 = true;
        if (Build.VERSION.SDK_INT < 21) {
            z2 = false;
        }
        f669d = z2;
    }

    public DrawerLayout(Context context) {
        this(context, null);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = new b();
        this.h = -1728053248;
        this.j = new Paint();
        this.q = true;
        this.r = 3;
        this.s = 3;
        this.t = 3;
        this.u = 3;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        setDescendantFocusability(262144);
        float f2 = getResources().getDisplayMetrics().density;
        this.g = (int) ((64.0f * f2) + 0.5f);
        float f3 = 400.0f * f2;
        this.m = new d(3);
        this.n = new d(5);
        this.k = b.g.a.c.a(this, 1.0f, this.m);
        this.k.d(1);
        this.k.a(f3);
        this.m.a(this.k);
        this.l = b.g.a.c.a(this, 1.0f, this.n);
        this.l.d(2);
        this.l.a(f3);
        this.n.a(this.l);
        setFocusableInTouchMode(true);
        t.c(this, 1);
        t.a(this, new a());
        setMotionEventSplittingEnabled(false);
        if (t.f(this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new a(this));
                setSystemUiVisibility(1280);
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(f666a);
                try {
                    this.B = obtainStyledAttributes.getDrawable(0);
                } finally {
                    obtainStyledAttributes.recycle();
                }
            } else {
                this.B = null;
            }
        }
        this.f = f2 * 10.0f;
        this.M = new ArrayList<>();
    }

    private boolean a(float f2, float f3, View view) {
        if (this.N == null) {
            this.N = new Rect();
        }
        view.getHitRect(this.N);
        return this.N.contains((int) f2, (int) f3);
    }

    private boolean a(Drawable drawable, int i2) {
        if (drawable == null || !androidx.core.graphics.drawable.a.e(drawable)) {
            return false;
        }
        androidx.core.graphics.drawable.a.a(drawable, i2);
        return true;
    }

    private boolean a(MotionEvent motionEvent, View view) {
        if (!view.getMatrix().isIdentity()) {
            MotionEvent b2 = b(motionEvent, view);
            boolean dispatchGenericMotionEvent = view.dispatchGenericMotionEvent(b2);
            b2.recycle();
            return dispatchGenericMotionEvent;
        }
        float scrollX = (float) (getScrollX() - view.getLeft());
        float scrollY = (float) (getScrollY() - view.getTop());
        motionEvent.offsetLocation(scrollX, scrollY);
        boolean dispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-scrollX, -scrollY);
        return dispatchGenericMotionEvent2;
    }

    private MotionEvent b(MotionEvent motionEvent, View view) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.offsetLocation((float) (getScrollX() - view.getLeft()), (float) (getScrollY() - view.getTop()));
        Matrix matrix = view.getMatrix();
        if (!matrix.isIdentity()) {
            if (this.O == null) {
                this.O = new Matrix();
            }
            matrix.invert(this.O);
            obtain.transform(this.O);
        }
        return obtain;
    }

    private void c(View view, boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            t.c(childAt, ((z2 || j(childAt)) && (!z2 || childAt != view)) ? 4 : 1);
        }
    }

    static String d(int i2) {
        return (i2 & 3) == 3 ? "LEFT" : (i2 & 5) == 5 ? "RIGHT" : Integer.toHexString(i2);
    }

    private boolean e() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (((LayoutParams) getChildAt(i2).getLayoutParams()).f672c) {
                return true;
            }
        }
        return false;
    }

    private boolean f() {
        return d() != null;
    }

    private Drawable g() {
        int i2 = t.i(this);
        if (i2 == 0) {
            Drawable drawable = this.I;
            if (drawable != null) {
                a(drawable, i2);
                return this.I;
            }
        } else {
            Drawable drawable2 = this.J;
            if (drawable2 != null) {
                a(drawable2, i2);
                return this.J;
            }
        }
        return this.K;
    }

    static boolean g(View view) {
        return (t.g(view) == 4 || t.g(view) == 2) ? false : true;
    }

    private Drawable h() {
        int i2 = t.i(this);
        if (i2 == 0) {
            Drawable drawable = this.J;
            if (drawable != null) {
                a(drawable, i2);
                return this.J;
            }
        } else {
            Drawable drawable2 = this.I;
            if (drawable2 != null) {
                a(drawable2, i2);
                return this.I;
            }
        }
        return this.L;
    }

    private void i() {
        if (!f669d) {
            this.C = g();
            this.D = h();
        }
    }

    private static boolean m(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    /* access modifiers changed from: package-private */
    public View a(int i2) {
        int a2 = C0113c.a(i2, t.i(this)) & 7;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if ((e(childAt) & 7) == a2) {
                return childAt;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (!this.w) {
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).dispatchTouchEvent(obtain);
            }
            obtain.recycle();
            this.w = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, View view) {
        int f2 = this.k.f();
        int f3 = this.l.f();
        int i4 = 2;
        if (f2 == 1 || f3 == 1) {
            i4 = 1;
        } else if (!(f2 == 2 || f3 == 2)) {
            i4 = 0;
        }
        if (view != null && i3 == 0) {
            float f4 = ((LayoutParams) view.getLayoutParams()).f671b;
            if (f4 == 0.0f) {
                b(view);
            } else if (f4 == 1.0f) {
                c(view);
            }
        }
        if (i4 != this.o) {
            this.o = i4;
            List<c> list = this.y;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.y.get(size).a(i4);
                }
            }
        }
    }

    public void a(View view) {
        a(view, true);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, float f2) {
        List<c> list = this.y;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.y.get(size).a(view, f2);
            }
        }
    }

    public void a(View view, boolean z2) {
        b.g.a.c cVar;
        int i2;
        if (j(view)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.q) {
                layoutParams.f671b = 0.0f;
                layoutParams.f673d = 0;
            } else if (z2) {
                layoutParams.f673d |= 4;
                if (a(view, 3)) {
                    cVar = this.k;
                    i2 = -view.getWidth();
                } else {
                    cVar = this.l;
                    i2 = getWidth();
                }
                cVar.b(view, i2, view.getTop());
            } else {
                b(view, 0.0f);
                a(layoutParams.f670a, 0, view);
                view.setVisibility(4);
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public void a(c cVar) {
        if (cVar != null) {
            if (this.y == null) {
                this.y = new ArrayList();
            }
            this.y.add(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        int childCount = getChildCount();
        boolean z3 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (j(childAt) && (!z2 || layoutParams.f672c)) {
                z3 |= a(childAt, 3) ? this.k.b(childAt, -childAt.getWidth(), childAt.getTop()) : this.l.b(childAt, getWidth(), childAt.getTop());
                layoutParams.f672c = false;
            }
        }
        this.m.b();
        this.n.b();
        if (z3) {
            invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, int i2) {
        return (e(view) & i2) == i2;
    }

    @Override // android.view.View, android.view.ViewGroup
    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        if (getDescendantFocusability() != 393216) {
            int childCount = getChildCount();
            boolean z2 = false;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (!j(childAt)) {
                    this.M.add(childAt);
                } else if (i(childAt)) {
                    childAt.addFocusables(arrayList, i2, i3);
                    z2 = true;
                }
            }
            if (!z2) {
                int size = this.M.size();
                for (int i5 = 0; i5 < size; i5++) {
                    View view = this.M.get(i5);
                    if (view.getVisibility() == 0) {
                        view.addFocusables(arrayList, i2, i3);
                    }
                }
            }
            this.M.clear();
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i2, layoutParams);
        t.c(view, (c() != null || j(view)) ? 4 : 1);
        if (!f668c) {
            t.a(view, this.e);
        }
    }

    public int b(int i2) {
        int i3 = t.i(this);
        if (i2 == 3) {
            int i4 = this.r;
            if (i4 != 3) {
                return i4;
            }
            int i5 = i3 == 0 ? this.t : this.u;
            if (i5 != 3) {
                return i5;
            }
            return 0;
        } else if (i2 == 5) {
            int i6 = this.s;
            if (i6 != 3) {
                return i6;
            }
            int i7 = i3 == 0 ? this.u : this.t;
            if (i7 != 3) {
                return i7;
            }
            return 0;
        } else if (i2 == 8388611) {
            int i8 = this.t;
            if (i8 != 3) {
                return i8;
            }
            int i9 = i3 == 0 ? this.r : this.s;
            if (i9 != 3) {
                return i9;
            }
            return 0;
        } else if (i2 != 8388613) {
            return 0;
        } else {
            int i10 = this.u;
            if (i10 != 3) {
                return i10;
            }
            int i11 = i3 == 0 ? this.s : this.r;
            if (i11 != 3) {
                return i11;
            }
            return 0;
        }
    }

    public void b() {
        a(false);
    }

    /* access modifiers changed from: package-private */
    public void b(View view) {
        View rootView;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.f673d & 1) == 1) {
            layoutParams.f673d = 0;
            List<c> list = this.y;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.y.get(size).b(view);
                }
            }
            c(view, false);
            if (hasWindowFocus() && (rootView = getRootView()) != null) {
                rootView.sendAccessibilityEvent(32);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(View view, float f2) {
        float f3 = f(view);
        float width = (float) view.getWidth();
        int i2 = ((int) (width * f2)) - ((int) (f3 * width));
        if (!a(view, 3)) {
            i2 = -i2;
        }
        view.offsetLeftAndRight(i2);
        c(view, f2);
    }

    public void b(View view, boolean z2) {
        if (j(view)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.q) {
                layoutParams.f671b = 1.0f;
                layoutParams.f673d = 1;
                c(view, true);
            } else if (z2) {
                layoutParams.f673d |= 2;
                if (a(view, 3)) {
                    this.k.b(view, 0, view.getTop());
                } else {
                    this.l.b(view, getWidth() - view.getWidth(), view.getTop());
                }
            } else {
                b(view, 1.0f);
                a(layoutParams.f670a, 0, view);
                view.setVisibility(0);
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public void b(c cVar) {
        List<c> list;
        if (cVar != null && (list = this.y) != null) {
            list.remove(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    public View c() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((((LayoutParams) childAt.getLayoutParams()).f673d & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    public CharSequence c(int i2) {
        int a2 = C0113c.a(i2, t.i(this));
        if (a2 == 3) {
            return this.E;
        }
        if (a2 == 5) {
            return this.F;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void c(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.f673d & 1) == 0) {
            layoutParams.f673d = 1;
            List<c> list = this.y;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.y.get(size).a(view);
                }
            }
            c(view, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(View view, float f2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f2 != layoutParams.f671b) {
            layoutParams.f671b = f2;
            a(view, f2);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        int childCount = getChildCount();
        float f2 = 0.0f;
        for (int i2 = 0; i2 < childCount; i2++) {
            f2 = Math.max(f2, ((LayoutParams) getChildAt(i2).getLayoutParams()).f671b);
        }
        this.i = f2;
        boolean a2 = this.k.a(true);
        boolean a3 = this.l.a(true);
        if (a2 || a3) {
            t.y(this);
        }
    }

    public int d(View view) {
        if (j(view)) {
            return b(((LayoutParams) view.getLayoutParams()).f670a);
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    /* access modifiers changed from: package-private */
    public View d() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (j(childAt) && k(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.i <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            View childAt = getChildAt(i2);
            if (a(x2, y2, childAt) && !h(childAt) && a(motionEvent, childAt)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        int i2;
        Drawable drawable;
        int height = getHeight();
        boolean h2 = h(view);
        int width = getWidth();
        int save = canvas.save();
        int i3 = 0;
        if (h2) {
            int childCount = getChildCount();
            i2 = width;
            int i4 = 0;
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (childAt != view && childAt.getVisibility() == 0 && m(childAt) && j(childAt) && childAt.getHeight() >= height) {
                    if (a(childAt, 3)) {
                        int right = childAt.getRight();
                        if (right > i4) {
                            i4 = right;
                        }
                    } else {
                        int left = childAt.getLeft();
                        if (left < i2) {
                            i2 = left;
                        }
                    }
                }
            }
            canvas.clipRect(i4, 0, i2, getHeight());
            i3 = i4;
        } else {
            i2 = width;
        }
        boolean drawChild = super.drawChild(canvas, view, j2);
        canvas.restoreToCount(save);
        float f2 = this.i;
        if (f2 <= 0.0f || !h2) {
            if (this.C != null && a(view, 3)) {
                int intrinsicWidth = this.C.getIntrinsicWidth();
                int right2 = view.getRight();
                float max = Math.max(0.0f, Math.min(((float) right2) / ((float) this.k.d()), 1.0f));
                this.C.setBounds(right2, view.getTop(), intrinsicWidth + right2, view.getBottom());
                this.C.setAlpha((int) (max * 255.0f));
                drawable = this.C;
            } else if (this.D != null && a(view, 5)) {
                int intrinsicWidth2 = this.D.getIntrinsicWidth();
                int left2 = view.getLeft();
                float max2 = Math.max(0.0f, Math.min(((float) (getWidth() - left2)) / ((float) this.l.d()), 1.0f));
                this.D.setBounds(left2 - intrinsicWidth2, view.getTop(), left2, view.getBottom());
                this.D.setAlpha((int) (max2 * 255.0f));
                drawable = this.D;
            }
            drawable.draw(canvas);
        } else {
            int i6 = this.h;
            this.j.setColor((i6 & 16777215) | (((int) (((float) ((-16777216 & i6) >>> 24)) * f2)) << 24));
            canvas.drawRect((float) i3, 0.0f, (float) i2, (float) getHeight(), this.j);
        }
        return drawChild;
    }

    /* access modifiers changed from: package-private */
    public int e(View view) {
        return C0113c.a(((LayoutParams) view.getLayoutParams()).f670a, t.i(this));
    }

    /* access modifiers changed from: package-private */
    public float f(View view) {
        return ((LayoutParams) view.getLayoutParams()).f671b;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public float getDrawerElevation() {
        if (f669d) {
            return this.f;
        }
        return 0.0f;
    }

    public Drawable getStatusBarBackgroundDrawable() {
        return this.B;
    }

    /* access modifiers changed from: package-private */
    public boolean h(View view) {
        return ((LayoutParams) view.getLayoutParams()).f670a == 0;
    }

    public boolean i(View view) {
        if (j(view)) {
            return (((LayoutParams) view.getLayoutParams()).f673d & 1) == 1;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    /* access modifiers changed from: package-private */
    public boolean j(View view) {
        int a2 = C0113c.a(((LayoutParams) view.getLayoutParams()).f670a, t.i(view));
        return ((a2 & 3) == 0 && (a2 & 5) == 0) ? false : true;
    }

    public boolean k(View view) {
        if (j(view)) {
            return ((LayoutParams) view.getLayoutParams()).f671b > 0.0f;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public void l(View view) {
        b(view, true);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.q = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.q = true;
    }

    public void onDraw(Canvas canvas) {
        Object obj;
        super.onDraw(canvas);
        if (this.H && this.B != null) {
            int systemWindowInsetTop = (Build.VERSION.SDK_INT < 21 || (obj = this.G) == null) ? 0 : ((WindowInsets) obj).getSystemWindowInsetTop();
            if (systemWindowInsetTop > 0) {
                this.B.setBounds(0, 0, getWidth(), systemWindowInsetTop);
                this.B.draw(canvas);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if (r0 != 3) goto L_0x0038;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
        // Method dump skipped, instructions count: 117
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4 || !f()) {
            return super.onKeyDown(i2, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            return super.onKeyUp(i2, keyEvent);
        }
        View d2 = d();
        if (d2 != null && d(d2) == 0) {
            b();
        }
        return d2 != null;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        int i6;
        int measuredHeight;
        int i7;
        int i8;
        this.p = true;
        int i9 = i4 - i2;
        int childCount = getChildCount();
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (h(childAt)) {
                    int i11 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
                    childAt.layout(i11, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, childAt.getMeasuredWidth() + i11, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + childAt.getMeasuredHeight());
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight2 = childAt.getMeasuredHeight();
                    if (a(childAt, 3)) {
                        float f3 = (float) measuredWidth;
                        i6 = (-measuredWidth) + ((int) (layoutParams.f671b * f3));
                        f2 = ((float) (measuredWidth + i6)) / f3;
                    } else {
                        float f4 = (float) measuredWidth;
                        int i12 = i9 - ((int) (layoutParams.f671b * f4));
                        f2 = ((float) (i9 - i12)) / f4;
                        i6 = i12;
                    }
                    boolean z3 = f2 != layoutParams.f671b;
                    int i13 = layoutParams.f670a & 112;
                    if (i13 != 16) {
                        if (i13 != 80) {
                            measuredHeight = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                            i7 = measuredWidth + i6;
                            i8 = measuredHeight2 + measuredHeight;
                        } else {
                            int i14 = i5 - i3;
                            measuredHeight = (i14 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin) - childAt.getMeasuredHeight();
                            i7 = measuredWidth + i6;
                            i8 = i14 - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                        }
                        childAt.layout(i6, measuredHeight, i7, i8);
                    } else {
                        int i15 = i5 - i3;
                        int i16 = (i15 - measuredHeight2) / 2;
                        int i17 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
                        if (i16 < i17) {
                            i16 = i17;
                        } else {
                            int i18 = i16 + measuredHeight2;
                            int i19 = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                            if (i18 > i15 - i19) {
                                i16 = (i15 - i19) - measuredHeight2;
                            }
                        }
                        childAt.layout(i6, i16, measuredWidth + i6, measuredHeight2 + i16);
                    }
                    if (z3) {
                        c(childAt, f2);
                    }
                    int i20 = layoutParams.f671b > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i20) {
                        childAt.setVisibility(i20);
                    }
                }
            }
        }
        this.p = false;
        this.q = false;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"WrongConstant"})
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        if (!(mode == 1073741824 && mode2 == 1073741824)) {
            if (isInEditMode()) {
                if (mode != Integer.MIN_VALUE && mode == 0) {
                    size = 300;
                }
                if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                    size2 = 300;
                }
            } else {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension(size, size2);
        int i4 = 0;
        boolean z2 = this.G != null && t.f(this);
        int i5 = t.i(this);
        int childCount = getChildCount();
        int i6 = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (i6 < childCount) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z2) {
                    int a2 = C0113c.a(layoutParams.f670a, i5);
                    if (t.f(childAt)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            WindowInsets windowInsets = (WindowInsets) this.G;
                            if (a2 == 3) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), i4, windowInsets.getSystemWindowInsetBottom());
                            } else if (a2 == 5) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(i4, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                            }
                            childAt.dispatchApplyWindowInsets(windowInsets);
                        }
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        WindowInsets windowInsets2 = (WindowInsets) this.G;
                        if (a2 == 3) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), i4, windowInsets2.getSystemWindowInsetBottom());
                        } else if (a2 == 5) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(i4, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                        }
                        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = windowInsets2.getSystemWindowInsetLeft();
                        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = windowInsets2.getSystemWindowInsetTop();
                        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = windowInsets2.getSystemWindowInsetRight();
                        ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = windowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (h(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, 1073741824));
                } else if (j(childAt)) {
                    if (f669d) {
                        float e2 = t.e(childAt);
                        float f2 = this.f;
                        if (e2 != f2) {
                            t.a(childAt, f2);
                        }
                    }
                    int e3 = e(childAt) & 7;
                    boolean z5 = e3 == 3;
                    if ((!z5 || !z3) && (z5 || !z4)) {
                        if (z5) {
                            z3 = true;
                        } else {
                            z4 = true;
                        }
                        childAt.measure(ViewGroup.getChildMeasureSpec(i2, this.g + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, ((ViewGroup.MarginLayoutParams) layoutParams).width), ViewGroup.getChildMeasureSpec(i3, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin, ((ViewGroup.MarginLayoutParams) layoutParams).height));
                        i6++;
                        i4 = 0;
                    } else {
                        throw new IllegalStateException("Child drawer has absolute gravity " + d(e3) + " but this " + "DrawerLayout" + " already has a " + "drawer view along that edge");
                    }
                } else {
                    throw new IllegalStateException("Child " + childAt + " at index " + i6 + " does not have a valid layout_gravity - must be Gravity.LEFT, " + "Gravity.RIGHT or Gravity.NO_GRAVITY");
                }
            }
            i6++;
            i4 = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        View a2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        int i2 = savedState.f674c;
        if (!(i2 == 0 || (a2 = a(i2)) == null)) {
            l(a2);
        }
        int i3 = savedState.f675d;
        if (i3 != 3) {
            setDrawerLockMode(i3, 3);
        }
        int i4 = savedState.e;
        if (i4 != 3) {
            setDrawerLockMode(i4, 5);
        }
        int i5 = savedState.f;
        if (i5 != 3) {
            setDrawerLockMode(i5, 8388611);
        }
        int i6 = savedState.g;
        if (i6 != 3) {
            setDrawerLockMode(i6, 8388613);
        }
    }

    public void onRtlPropertiesChanged(int i2) {
        i();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            LayoutParams layoutParams = (LayoutParams) getChildAt(i2).getLayoutParams();
            boolean z2 = true;
            boolean z3 = layoutParams.f673d == 1;
            if (layoutParams.f673d != 2) {
                z2 = false;
            }
            if (z3 || z2) {
                savedState.f674c = layoutParams.f670a;
            } else {
                i2++;
            }
        }
        savedState.f675d = this.r;
        savedState.e = this.s;
        savedState.f = this.t;
        savedState.g = this.u;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        View c2;
        this.k.a(motionEvent);
        this.l.a(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action == 1) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                View b2 = this.k.b((int) x2, (int) y2);
                if (b2 != null && h(b2)) {
                    float f2 = x2 - this.z;
                    float f3 = y2 - this.A;
                    int e2 = this.k.e();
                    if (!((f2 * f2) + (f3 * f3) >= ((float) (e2 * e2)) || (c2 = c()) == null || d(c2) == 2)) {
                        z2 = false;
                        a(z2);
                        this.v = false;
                    }
                }
                z2 = true;
                a(z2);
                this.v = false;
            } else if (action == 3) {
                a(true);
            }
            return true;
        }
        float x3 = motionEvent.getX();
        float y3 = motionEvent.getY();
        this.z = x3;
        this.A = y3;
        this.v = false;
        this.w = false;
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        super.requestDisallowInterceptTouchEvent(z2);
        this.v = z2;
        if (z2) {
            a(true);
        }
    }

    public void requestLayout() {
        if (!this.p) {
            super.requestLayout();
        }
    }

    public void setChildInsets(Object obj, boolean z2) {
        this.G = obj;
        this.H = z2;
        setWillNotDraw(!z2 && getBackground() == null);
        requestLayout();
    }

    public void setDrawerElevation(float f2) {
        this.f = f2;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (j(childAt)) {
                t.a(childAt, this.f);
            }
        }
    }

    @Deprecated
    public void setDrawerListener(c cVar) {
        c cVar2 = this.x;
        if (cVar2 != null) {
            b(cVar2);
        }
        if (cVar != null) {
            a(cVar);
        }
        this.x = cVar;
    }

    public void setDrawerLockMode(int i2) {
        setDrawerLockMode(i2, 3);
        setDrawerLockMode(i2, 5);
    }

    public void setDrawerLockMode(int i2, int i3) {
        View a2;
        int a3 = C0113c.a(i3, t.i(this));
        if (i3 == 3) {
            this.r = i2;
        } else if (i3 == 5) {
            this.s = i2;
        } else if (i3 == 8388611) {
            this.t = i2;
        } else if (i3 == 8388613) {
            this.u = i2;
        }
        if (i2 != 0) {
            (a3 == 3 ? this.k : this.l).b();
        }
        if (i2 == 1) {
            View a4 = a(a3);
            if (a4 != null) {
                a(a4);
            }
        } else if (i2 == 2 && (a2 = a(a3)) != null) {
            l(a2);
        }
    }

    public void setDrawerLockMode(int i2, View view) {
        if (j(view)) {
            setDrawerLockMode(i2, ((LayoutParams) view.getLayoutParams()).f670a);
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a " + "drawer with appropriate layout_gravity");
    }

    public void setDrawerShadow(int i2, int i3) {
        setDrawerShadow(androidx.core.content.a.c(getContext(), i2), i3);
    }

    public void setDrawerShadow(Drawable drawable, int i2) {
        if (!f669d) {
            if ((i2 & 8388611) == 8388611) {
                this.I = drawable;
            } else if ((i2 & 8388613) == 8388613) {
                this.J = drawable;
            } else if ((i2 & 3) == 3) {
                this.K = drawable;
            } else if ((i2 & 5) == 5) {
                this.L = drawable;
            } else {
                return;
            }
            i();
            invalidate();
        }
    }

    public void setDrawerTitle(int i2, CharSequence charSequence) {
        int a2 = C0113c.a(i2, t.i(this));
        if (a2 == 3) {
            this.E = charSequence;
        } else if (a2 == 5) {
            this.F = charSequence;
        }
    }

    public void setScrimColor(int i2) {
        this.h = i2;
        invalidate();
    }

    public void setStatusBarBackground(int i2) {
        this.B = i2 != 0 ? androidx.core.content.a.c(getContext(), i2) : null;
        invalidate();
    }

    public void setStatusBarBackground(Drawable drawable) {
        this.B = drawable;
        invalidate();
    }

    public void setStatusBarBackgroundColor(int i2) {
        this.B = new ColorDrawable(i2);
        invalidate();
    }
}
