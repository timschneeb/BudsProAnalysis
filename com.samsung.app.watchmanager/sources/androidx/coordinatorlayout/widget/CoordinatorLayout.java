package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.customview.view.AbsSavedState;
import b.e.f.g;
import b.e.g.C0113c;
import b.e.g.D;
import b.e.g.l;
import b.e.g.n;
import b.e.g.o;
import b.e.g.t;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout extends ViewGroup implements l {

    /* renamed from: a  reason: collision with root package name */
    static final String f533a;

    /* renamed from: b  reason: collision with root package name */
    static final Class<?>[] f534b = {Context.class, AttributeSet.class};

    /* renamed from: c  reason: collision with root package name */
    static final ThreadLocal<Map<String, Constructor<Behavior>>> f535c = new ThreadLocal<>();

    /* renamed from: d  reason: collision with root package name */
    static final Comparator<View> f536d;
    private static final b.e.f.e<Rect> e = new g(12);
    private final List<View> f;
    private final c<View> g;
    private final List<View> h;
    private final List<View> i;
    private final int[] j;
    private Paint k;
    private boolean l;
    private boolean m;
    private int[] n;
    private View o;
    private View p;
    private e q;
    private boolean r;
    private D s;
    private boolean t;
    private Drawable u;
    ViewGroup.OnHierarchyChangeListener v;
    private o w;
    private final n x;

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public D a(CoordinatorLayout coordinatorLayout, V v, D d2) {
            return d2;
        }

        public void a() {
        }

        public void a(d dVar) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i) {
            if (i == 0) {
                d(coordinatorLayout, v, view);
            }
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int i3, int i4, int i5) {
            if (i5 == 0) {
                a(coordinatorLayout, v, view, i, i2, i3, i4);
            }
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3) {
            if (i3 == 0) {
                a(coordinatorLayout, v, view, i, i2, iArr);
            }
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i, int i2) {
            if (i2 == 0) {
                a(coordinatorLayout, v, view, view2, i);
            }
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v) {
            return c(coordinatorLayout, v) > 0.0f;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, int i) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3, int i4) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, Rect rect) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean z) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, boolean z) {
            return false;
        }

        public int b(CoordinatorLayout coordinatorLayout, V v) {
            return -16777216;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        @Deprecated
        public boolean b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
            return false;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i, int i2) {
            if (i2 == 0) {
                return b(coordinatorLayout, v, view, view2, i);
            }
            return false;
        }

        public float c(CoordinatorLayout coordinatorLayout, V v) {
            return 0.0f;
        }

        public void c(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public Parcelable d(CoordinatorLayout coordinatorLayout, V v) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public void d(CoordinatorLayout coordinatorLayout, V v, View view) {
        }
    }

    /* access modifiers changed from: protected */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new b();

        /* renamed from: c  reason: collision with root package name */
        SparseArray<Parcelable> f537c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int readInt = parcel.readInt();
            int[] iArr = new int[readInt];
            parcel.readIntArray(iArr);
            Parcelable[] readParcelableArray = parcel.readParcelableArray(classLoader);
            this.f537c = new SparseArray<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.f537c.append(iArr[i], readParcelableArray[i]);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            SparseArray<Parcelable> sparseArray = this.f537c;
            int size = sparseArray != null ? sparseArray.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = this.f537c.keyAt(i2);
                parcelableArr[i2] = this.f537c.valueAt(i2);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i);
        }
    }

    public interface a {
        Behavior a();
    }

    @Deprecated
    @Retention(RetentionPolicy.RUNTIME)
    public @interface b {
        Class<? extends Behavior> value();
    }

    private class c implements ViewGroup.OnHierarchyChangeListener {
        c() {
        }

        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.v;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.a(2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.v;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class d extends ViewGroup.MarginLayoutParams {

        /* renamed from: a  reason: collision with root package name */
        Behavior f539a;

        /* renamed from: b  reason: collision with root package name */
        boolean f540b = false;

        /* renamed from: c  reason: collision with root package name */
        public int f541c = 0;

        /* renamed from: d  reason: collision with root package name */
        public int f542d = 0;
        public int e = -1;
        int f = -1;
        public int g = 0;
        public int h = 0;
        int i;
        int j;
        View k;
        View l;
        private boolean m;
        private boolean n;
        private boolean o;
        private boolean p;
        final Rect q = new Rect();
        Object r;

        public d(int i2, int i3) {
            super(i2, i3);
        }

        d(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b.d.c.CoordinatorLayout_Layout);
            this.f541c = obtainStyledAttributes.getInteger(b.d.c.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.f = obtainStyledAttributes.getResourceId(b.d.c.CoordinatorLayout_Layout_layout_anchor, -1);
            this.f542d = obtainStyledAttributes.getInteger(b.d.c.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.e = obtainStyledAttributes.getInteger(b.d.c.CoordinatorLayout_Layout_layout_keyline, -1);
            this.g = obtainStyledAttributes.getInt(b.d.c.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.h = obtainStyledAttributes.getInt(b.d.c.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            this.f540b = obtainStyledAttributes.hasValue(b.d.c.CoordinatorLayout_Layout_layout_behavior);
            if (this.f540b) {
                this.f539a = CoordinatorLayout.a(context, attributeSet, obtainStyledAttributes.getString(b.d.c.CoordinatorLayout_Layout_layout_behavior));
            }
            obtainStyledAttributes.recycle();
            Behavior behavior = this.f539a;
            if (behavior != null) {
                behavior.a(this);
            }
        }

        public d(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public d(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public d(d dVar) {
            super((ViewGroup.MarginLayoutParams) dVar);
        }

        private void a(View view, CoordinatorLayout coordinatorLayout) {
            this.k = coordinatorLayout.findViewById(this.f);
            View view2 = this.k;
            if (view2 != null) {
                if (view2 != coordinatorLayout) {
                    ViewParent parent = view2.getParent();
                    while (parent != coordinatorLayout && parent != null) {
                        if (parent != view) {
                            if (parent instanceof View) {
                                view2 = (View) parent;
                            }
                            parent = parent.getParent();
                        } else if (!coordinatorLayout.isInEditMode()) {
                            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                        }
                    }
                    this.l = view2;
                    return;
                } else if (!coordinatorLayout.isInEditMode()) {
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
            } else if (!coordinatorLayout.isInEditMode()) {
                throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(this.f) + " to anchor view " + view);
            }
            this.l = null;
            this.k = null;
        }

        private boolean a(View view, int i2) {
            int a2 = C0113c.a(((d) view.getLayoutParams()).g, i2);
            return a2 != 0 && (C0113c.a(this.h, i2) & a2) == a2;
        }

        private boolean b(View view, CoordinatorLayout coordinatorLayout) {
            if (this.k.getId() != this.f) {
                return false;
            }
            View view2 = this.k;
            for (ViewParent parent = view2.getParent(); parent != coordinatorLayout; parent = parent.getParent()) {
                if (parent == null || parent == view) {
                    this.l = null;
                    this.k = null;
                    return false;
                }
                if (parent instanceof View) {
                    view2 = (View) parent;
                }
            }
            this.l = view2;
            return true;
        }

        /* access modifiers changed from: package-private */
        public View a(CoordinatorLayout coordinatorLayout, View view) {
            if (this.f == -1) {
                this.l = null;
                this.k = null;
                return null;
            }
            if (this.k == null || !b(view, coordinatorLayout)) {
                a(view, coordinatorLayout);
            }
            return this.k;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, boolean z) {
            if (i2 == 0) {
                this.n = z;
            } else if (i2 == 1) {
                this.o = z;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(Rect rect) {
            this.q.set(rect);
        }

        public void a(Behavior behavior) {
            Behavior behavior2 = this.f539a;
            if (behavior2 != behavior) {
                if (behavior2 != null) {
                    behavior2.a();
                }
                this.f539a = behavior;
                this.r = null;
                this.f540b = true;
                if (behavior != null) {
                    behavior.a(this);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z) {
            this.p = z;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return this.k == null && this.f != -1;
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2) {
            if (i2 == 0) {
                return this.n;
            }
            if (i2 != 1) {
                return false;
            }
            return this.o;
        }

        /* access modifiers changed from: package-private */
        public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            Behavior behavior;
            return view2 == this.l || a(view2, t.i(coordinatorLayout)) || ((behavior = this.f539a) != null && behavior.a(coordinatorLayout, view, view2));
        }

        /* access modifiers changed from: package-private */
        public void b(int i2) {
            a(i2, false);
        }

        /* access modifiers changed from: package-private */
        public boolean b() {
            if (this.f539a == null) {
                this.m = false;
            }
            return this.m;
        }

        /* access modifiers changed from: package-private */
        public boolean b(CoordinatorLayout coordinatorLayout, View view) {
            boolean z = this.m;
            if (z) {
                return true;
            }
            Behavior behavior = this.f539a;
            boolean a2 = (behavior != null ? behavior.a(coordinatorLayout, view) : false) | z;
            this.m = a2;
            return a2;
        }

        public Behavior c() {
            return this.f539a;
        }

        /* access modifiers changed from: package-private */
        public boolean d() {
            return this.p;
        }

        /* access modifiers changed from: package-private */
        public Rect e() {
            return this.q;
        }

        /* access modifiers changed from: package-private */
        public void f() {
            this.p = false;
        }

        /* access modifiers changed from: package-private */
        public void g() {
            this.m = false;
        }
    }

    /* access modifiers changed from: package-private */
    public class e implements ViewTreeObserver.OnPreDrawListener {
        e() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.a(0);
            return true;
        }
    }

    static class f implements Comparator<View> {
        f() {
        }

        /* renamed from: a */
        public int compare(View view, View view2) {
            float q = t.q(view);
            float q2 = t.q(view2);
            if (q > q2) {
                return -1;
            }
            return q < q2 ? 1 : 0;
        }
    }

    static {
        Package r0 = CoordinatorLayout.class.getPackage();
        f533a = r0 != null ? r0.getName() : null;
        if (Build.VERSION.SDK_INT >= 21) {
            f536d = new f();
        } else {
            f536d = null;
        }
    }

    public CoordinatorLayout(Context context) {
        this(context, null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, b.d.a.coordinatorLayoutStyle);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f = new ArrayList();
        this.g = new c<>();
        this.h = new ArrayList();
        this.i = new ArrayList();
        this.j = new int[2];
        this.x = new n(this);
        TypedArray obtainStyledAttributes = i2 == 0 ? context.obtainStyledAttributes(attributeSet, b.d.c.CoordinatorLayout, 0, b.d.b.Widget_Support_CoordinatorLayout) : context.obtainStyledAttributes(attributeSet, b.d.c.CoordinatorLayout, i2, 0);
        int resourceId = obtainStyledAttributes.getResourceId(b.d.c.CoordinatorLayout_keylines, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            this.n = resources.getIntArray(resourceId);
            float f2 = resources.getDisplayMetrics().density;
            int length = this.n.length;
            for (int i3 = 0; i3 < length; i3++) {
                int[] iArr = this.n;
                iArr[i3] = (int) (((float) iArr[i3]) * f2);
            }
        }
        this.u = obtainStyledAttributes.getDrawable(b.d.c.CoordinatorLayout_statusBarBackground);
        obtainStyledAttributes.recycle();
        f();
        super.setOnHierarchyChangeListener(new c());
    }

    private static int a(int i2, int i3, int i4) {
        return i2 < i3 ? i3 : i2 > i4 ? i4 : i2;
    }

    static Behavior a(Context context, AttributeSet attributeSet, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(".")) {
            str = context.getPackageName() + str;
        } else if (str.indexOf(46) < 0 && !TextUtils.isEmpty(f533a)) {
            str = f533a + '.' + str;
        }
        try {
            Map<String, Constructor<Behavior>> map = f535c.get();
            if (map == null) {
                map = new HashMap<>();
                f535c.set(map);
            }
            Constructor<Behavior> constructor = map.get(str);
            if (constructor == null) {
                constructor = context.getClassLoader().loadClass(str).getConstructor(f534b);
                constructor.setAccessible(true);
                map.put(str, constructor);
            }
            return (Behavior) constructor.newInstance(context, attributeSet);
        } catch (Exception e2) {
            throw new RuntimeException("Could not inflate Behavior subclass " + str, e2);
        }
    }

    private static void a(Rect rect) {
        rect.setEmpty();
        e.a(rect);
    }

    private void a(View view, int i2, int i3) {
        d dVar = (d) view.getLayoutParams();
        int a2 = C0113c.a(e(dVar.f541c), i3);
        int i4 = a2 & 7;
        int i5 = a2 & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (i3 == 1) {
            i2 = width - i2;
        }
        int b2 = b(i2) - measuredWidth;
        int i6 = 0;
        if (i4 == 1) {
            b2 += measuredWidth / 2;
        } else if (i4 == 5) {
            b2 += measuredWidth;
        }
        if (i5 == 16) {
            i6 = 0 + (measuredHeight / 2);
        } else if (i5 == 80) {
            i6 = measuredHeight + 0;
        }
        int max = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) dVar).leftMargin, Math.min(b2, ((width - getPaddingRight()) - measuredWidth) - ((ViewGroup.MarginLayoutParams) dVar).rightMargin));
        int max2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) dVar).topMargin, Math.min(i6, ((height - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) dVar).bottomMargin));
        view.layout(max, max2, measuredWidth + max, measuredHeight + max2);
    }

    private void a(View view, int i2, Rect rect, Rect rect2, d dVar, int i3, int i4) {
        int a2 = C0113c.a(c(dVar.f541c), i2);
        int a3 = C0113c.a(d(dVar.f542d), i2);
        int i5 = a2 & 7;
        int i6 = a2 & 112;
        int i7 = a3 & 7;
        int i8 = a3 & 112;
        int width = i7 != 1 ? i7 != 5 ? rect.left : rect.right : rect.left + (rect.width() / 2);
        int height = i8 != 16 ? i8 != 80 ? rect.top : rect.bottom : rect.top + (rect.height() / 2);
        if (i5 == 1) {
            width -= i3 / 2;
        } else if (i5 != 5) {
            width -= i3;
        }
        if (i6 == 16) {
            height -= i4 / 2;
        } else if (i6 != 80) {
            height -= i4;
        }
        rect2.set(width, height, i3 + width, i4 + height);
    }

    private void a(View view, Rect rect, int i2) {
        boolean z;
        boolean z2;
        int width;
        int i3;
        int i4;
        int i5;
        int height;
        int i6;
        int i7;
        int i8;
        if (t.w(view) && view.getWidth() > 0 && view.getHeight() > 0) {
            d dVar = (d) view.getLayoutParams();
            Behavior c2 = dVar.c();
            Rect d2 = d();
            Rect d3 = d();
            d3.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (c2 == null || !c2.a(this, view, d2)) {
                d2.set(d3);
            } else if (!d3.contains(d2)) {
                throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + d2.toShortString() + " | Bounds:" + d3.toShortString());
            }
            a(d3);
            if (d2.isEmpty()) {
                a(d2);
                return;
            }
            int a2 = C0113c.a(dVar.h, i2);
            if ((a2 & 48) != 48 || (i7 = (d2.top - ((ViewGroup.MarginLayoutParams) dVar).topMargin) - dVar.j) >= (i8 = rect.top)) {
                z = false;
            } else {
                f(view, i8 - i7);
                z = true;
            }
            if ((a2 & 80) == 80 && (height = ((getHeight() - d2.bottom) - ((ViewGroup.MarginLayoutParams) dVar).bottomMargin) + dVar.j) < (i6 = rect.bottom)) {
                f(view, height - i6);
                z = true;
            }
            if (!z) {
                f(view, 0);
            }
            if ((a2 & 3) != 3 || (i4 = (d2.left - ((ViewGroup.MarginLayoutParams) dVar).leftMargin) - dVar.i) >= (i5 = rect.left)) {
                z2 = false;
            } else {
                e(view, i5 - i4);
                z2 = true;
            }
            if ((a2 & 5) == 5 && (width = ((getWidth() - d2.right) - ((ViewGroup.MarginLayoutParams) dVar).rightMargin) + dVar.i) < (i3 = rect.right)) {
                e(view, width - i3);
                z2 = true;
            }
            if (!z2) {
                e(view, 0);
            }
            a(d2);
        }
    }

    private void a(View view, View view2, int i2) {
        Rect d2 = d();
        Rect d3 = d();
        try {
            a(view2, d2);
            a(view, i2, d2, d3);
            view.layout(d3.left, d3.top, d3.right, d3.bottom);
        } finally {
            a(d2);
            a(d3);
        }
    }

    private void a(d dVar, Rect rect, int i2, int i3) {
        int width = getWidth();
        int height = getHeight();
        int max = Math.max(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) dVar).leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i2) - ((ViewGroup.MarginLayoutParams) dVar).rightMargin));
        int max2 = Math.max(getPaddingTop() + ((ViewGroup.MarginLayoutParams) dVar).topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i3) - ((ViewGroup.MarginLayoutParams) dVar).bottomMargin));
        rect.set(max, max2, i2 + max, i3 + max2);
    }

    private void a(List<View> list) {
        list.clear();
        boolean isChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            list.add(getChildAt(isChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i2) : i2));
        }
        Comparator<View> comparator = f536d;
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
    }

    private void a(boolean z) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            Behavior c2 = ((d) childAt.getLayoutParams()).c();
            if (c2 != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                if (z) {
                    c2.a(this, childAt, obtain);
                } else {
                    c2.b(this, childAt, obtain);
                }
                obtain.recycle();
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            ((d) getChildAt(i3).getLayoutParams()).g();
        }
        this.o = null;
        this.l = false;
    }

    private boolean a(MotionEvent motionEvent, int i2) {
        int actionMasked = motionEvent.getActionMasked();
        List<View> list = this.h;
        a(list);
        int size = list.size();
        MotionEvent motionEvent2 = null;
        boolean z = false;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view = list.get(i3);
            d dVar = (d) view.getLayoutParams();
            Behavior c2 = dVar.c();
            boolean z3 = true;
            if (!(z || z2) || actionMasked == 0) {
                if (!z && c2 != null) {
                    if (i2 == 0) {
                        z = c2.a(this, view, motionEvent);
                    } else if (i2 == 1) {
                        z = c2.b(this, view, motionEvent);
                    }
                    if (z) {
                        this.o = view;
                    }
                }
                boolean b2 = dVar.b();
                boolean b3 = dVar.b(this, view);
                if (!b3 || b2) {
                    z3 = false;
                }
                if (b3 && !z3) {
                    break;
                }
                z2 = z3;
            } else if (c2 != null) {
                if (motionEvent2 == null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    motionEvent2 = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                }
                if (i2 == 0) {
                    c2.a(this, view, motionEvent2);
                } else if (i2 == 1) {
                    c2.b(this, view, motionEvent2);
                }
            }
        }
        list.clear();
        return z;
    }

    private int b(int i2) {
        StringBuilder sb;
        int[] iArr = this.n;
        if (iArr == null) {
            sb = new StringBuilder();
            sb.append("No keylines defined for ");
            sb.append(this);
            sb.append(" - attempted index lookup ");
            sb.append(i2);
        } else if (i2 >= 0 && i2 < iArr.length) {
            return iArr[i2];
        } else {
            sb = new StringBuilder();
            sb.append("Keyline index ");
            sb.append(i2);
            sb.append(" out of range for ");
            sb.append(this);
        }
        Log.e("CoordinatorLayout", sb.toString());
        return 0;
    }

    private D b(D d2) {
        Behavior c2;
        if (d2.e()) {
            return d2;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (t.f(childAt) && (c2 = ((d) childAt.getLayoutParams()).c()) != null) {
                d2 = c2.a(this, childAt, d2);
                if (d2.e()) {
                    break;
                }
            }
        }
        return d2;
    }

    private boolean b(View view) {
        return this.g.c(view);
    }

    private static int c(int i2) {
        if (i2 == 0) {
            return 17;
        }
        return i2;
    }

    private static int d(int i2) {
        if ((i2 & 7) == 0) {
            i2 |= 8388611;
        }
        return (i2 & 112) == 0 ? i2 | 48 : i2;
    }

    private static Rect d() {
        Rect a2 = e.a();
        return a2 == null ? new Rect() : a2;
    }

    private void d(View view, int i2) {
        d dVar = (d) view.getLayoutParams();
        Rect d2 = d();
        d2.set(getPaddingLeft() + ((ViewGroup.MarginLayoutParams) dVar).leftMargin, getPaddingTop() + ((ViewGroup.MarginLayoutParams) dVar).topMargin, (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) dVar).rightMargin, (getHeight() - getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) dVar).bottomMargin);
        if (this.s != null && t.f(this) && !t.f(view)) {
            d2.left += this.s.b();
            d2.top += this.s.d();
            d2.right -= this.s.c();
            d2.bottom -= this.s.a();
        }
        Rect d3 = d();
        C0113c.a(d(dVar.f541c), view.getMeasuredWidth(), view.getMeasuredHeight(), d2, d3, i2);
        view.layout(d3.left, d3.top, d3.right, d3.bottom);
        a(d2);
        a(d3);
    }

    private static int e(int i2) {
        if (i2 == 0) {
            return 8388661;
        }
        return i2;
    }

    private void e() {
        this.f.clear();
        this.g.a();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            d a2 = a(childAt);
            a2.a(this, childAt);
            this.g.a(childAt);
            for (int i3 = 0; i3 < childCount; i3++) {
                if (i3 != i2) {
                    View childAt2 = getChildAt(i3);
                    if (a2.a(this, childAt, childAt2)) {
                        if (!this.g.b(childAt2)) {
                            this.g.a(childAt2);
                        }
                        this.g.a(childAt2, childAt);
                    }
                }
            }
        }
        this.f.addAll(this.g.b());
        Collections.reverse(this.f);
    }

    private void e(View view, int i2) {
        d dVar = (d) view.getLayoutParams();
        int i3 = dVar.i;
        if (i3 != i2) {
            t.a(view, i2 - i3);
            dVar.i = i2;
        }
    }

    private void f() {
        if (Build.VERSION.SDK_INT >= 21) {
            if (t.f(this)) {
                if (this.w == null) {
                    this.w = new a(this);
                }
                t.a(this, this.w);
                setSystemUiVisibility(1280);
                return;
            }
            t.a(this, (o) null);
        }
    }

    private void f(View view, int i2) {
        d dVar = (d) view.getLayoutParams();
        int i3 = dVar.j;
        if (i3 != i2) {
            t.b(view, i2 - i3);
            dVar.j = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public d a(View view) {
        d dVar = (d) view.getLayoutParams();
        if (!dVar.f540b) {
            if (view instanceof a) {
                Behavior a2 = ((a) view).a();
                if (a2 == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                dVar.a(a2);
            } else {
                b bVar = null;
                for (Class<?> cls = view.getClass(); cls != null; cls = cls.getSuperclass()) {
                    bVar = (b) cls.getAnnotation(b.class);
                    if (bVar != null) {
                        break;
                    }
                }
                if (bVar != null) {
                    try {
                        dVar.a((Behavior) bVar.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                    } catch (Exception e2) {
                        Log.e("CoordinatorLayout", "Default behavior class " + bVar.value().getName() + " could not be instantiated. Did you forget" + " a default constructor?", e2);
                    }
                }
            }
            dVar.f540b = true;
        }
        return dVar;
    }

    /* access modifiers changed from: package-private */
    public final D a(D d2) {
        if (b.e.f.c.a(this.s, d2)) {
            return d2;
        }
        this.s = d2;
        boolean z = true;
        this.t = d2 != null && d2.d() > 0;
        if (this.t || getBackground() != null) {
            z = false;
        }
        setWillNotDraw(z);
        D b2 = b(d2);
        requestLayout();
        return b2;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (this.m) {
            if (this.q == null) {
                this.q = new e();
            }
            getViewTreeObserver().addOnPreDrawListener(this.q);
        }
        this.r = true;
    }

    /* access modifiers changed from: package-private */
    public final void a(int i2) {
        boolean z;
        int i3 = t.i(this);
        int size = this.f.size();
        Rect d2 = d();
        Rect d3 = d();
        Rect d4 = d();
        for (int i4 = 0; i4 < size; i4++) {
            View view = this.f.get(i4);
            d dVar = (d) view.getLayoutParams();
            if (i2 != 0 || view.getVisibility() != 8) {
                for (int i5 = 0; i5 < i4; i5++) {
                    if (dVar.l == this.f.get(i5)) {
                        b(view, i3);
                    }
                }
                a(view, true, d3);
                if (dVar.g != 0 && !d3.isEmpty()) {
                    int a2 = C0113c.a(dVar.g, i3);
                    int i6 = a2 & 112;
                    if (i6 == 48) {
                        d2.top = Math.max(d2.top, d3.bottom);
                    } else if (i6 == 80) {
                        d2.bottom = Math.max(d2.bottom, getHeight() - d3.top);
                    }
                    int i7 = a2 & 7;
                    if (i7 == 3) {
                        d2.left = Math.max(d2.left, d3.right);
                    } else if (i7 == 5) {
                        d2.right = Math.max(d2.right, getWidth() - d3.left);
                    }
                }
                if (dVar.h != 0 && view.getVisibility() == 0) {
                    a(view, d2, i3);
                }
                if (i2 != 2) {
                    b(view, d4);
                    if (!d4.equals(d3)) {
                        c(view, d3);
                    }
                }
                for (int i8 = i4 + 1; i8 < size; i8++) {
                    View view2 = this.f.get(i8);
                    d dVar2 = (d) view2.getLayoutParams();
                    Behavior c2 = dVar2.c();
                    if (c2 != null && c2.a(this, view2, view)) {
                        if (i2 != 0 || !dVar2.d()) {
                            if (i2 != 2) {
                                z = c2.b(this, view2, view);
                            } else {
                                c2.c(this, view2, view);
                                z = true;
                            }
                            if (i2 == 1) {
                                dVar2.a(z);
                            }
                        } else {
                            dVar2.f();
                        }
                    }
                }
            }
        }
        a(d2);
        a(d3);
        a(d4);
    }

    @Override // b.e.g.l
    public void a(View view, int i2) {
        this.x.a(view, i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            d dVar = (d) childAt.getLayoutParams();
            if (dVar.a(i2)) {
                Behavior c2 = dVar.c();
                if (c2 != null) {
                    c2.a(this, childAt, view, i2);
                }
                dVar.b(i2);
                dVar.f();
            }
        }
        this.p = null;
    }

    public void a(View view, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    @Override // b.e.g.l
    public void a(View view, int i2, int i3, int i4, int i5, int i6) {
        Behavior c2;
        int childCount = getChildCount();
        boolean z = false;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                d dVar = (d) childAt.getLayoutParams();
                if (dVar.a(i6) && (c2 = dVar.c()) != null) {
                    c2.a(this, childAt, view, i2, i3, i4, i5, i6);
                    z = true;
                }
            }
        }
        if (z) {
            a(1);
        }
    }

    @Override // b.e.g.l
    public void a(View view, int i2, int i3, int[] iArr, int i4) {
        Behavior c2;
        int childCount = getChildCount();
        boolean z = false;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                d dVar = (d) childAt.getLayoutParams();
                if (dVar.a(i4) && (c2 = dVar.c()) != null) {
                    int[] iArr2 = this.j;
                    iArr2[1] = 0;
                    iArr2[0] = 0;
                    c2.a(this, childAt, view, i2, i3, iArr2, i4);
                    i5 = i2 > 0 ? Math.max(i5, this.j[0]) : Math.min(i5, this.j[0]);
                    i6 = i3 > 0 ? Math.max(i6, this.j[1]) : Math.min(i6, this.j[1]);
                    z = true;
                }
            }
        }
        iArr[0] = i5;
        iArr[1] = i6;
        if (z) {
            a(1);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i2, Rect rect, Rect rect2) {
        d dVar = (d) view.getLayoutParams();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        a(view, i2, rect, rect2, dVar, measuredWidth, measuredHeight);
        a(dVar, rect2, measuredWidth, measuredHeight);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, Rect rect) {
        d.a(this, view, rect);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            a(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    @Override // b.e.g.l
    public boolean a(View view, View view2, int i2, int i3) {
        int childCount = getChildCount();
        boolean z = false;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                d dVar = (d) childAt.getLayoutParams();
                Behavior c2 = dVar.c();
                if (c2 != null) {
                    boolean b2 = c2.b(this, childAt, view, view2, i2, i3);
                    dVar.a(i3, b2);
                    z |= b2;
                } else {
                    dVar.a(i3, false);
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int childCount = getChildCount();
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            } else if (b(getChildAt(i2))) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z == this.r) {
            return;
        }
        if (z) {
            a();
        } else {
            c();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(View view, int i2) {
        Behavior c2;
        d dVar = (d) view.getLayoutParams();
        if (dVar.k != null) {
            Rect d2 = d();
            Rect d3 = d();
            Rect d4 = d();
            a(dVar.k, d2);
            boolean z = false;
            a(view, false, d3);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            a(view, i2, d2, d4, dVar, measuredWidth, measuredHeight);
            if (!(d4.left == d3.left && d4.top == d3.top)) {
                z = true;
            }
            a(dVar, d4, measuredWidth, measuredHeight);
            int i3 = d4.left - d3.left;
            int i4 = d4.top - d3.top;
            if (i3 != 0) {
                t.a(view, i3);
            }
            if (i4 != 0) {
                t.b(view, i4);
            }
            if (z && (c2 = dVar.c()) != null) {
                c2.b(this, view, dVar.k);
            }
            a(d2);
            a(d3);
            a(d4);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(View view, Rect rect) {
        rect.set(((d) view.getLayoutParams()).e());
    }

    @Override // b.e.g.l
    public void b(View view, View view2, int i2, int i3) {
        Behavior c2;
        this.x.a(view, view2, i2, i3);
        this.p = view2;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            d dVar = (d) childAt.getLayoutParams();
            if (dVar.a(i3) && (c2 = dVar.c()) != null) {
                c2.a(this, childAt, view, view2, i2, i3);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (this.m && this.q != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.q);
        }
        this.r = false;
    }

    public void c(View view, int i2) {
        d dVar = (d) view.getLayoutParams();
        if (!dVar.a()) {
            View view2 = dVar.k;
            if (view2 != null) {
                a(view, view2, i2);
                return;
            }
            int i3 = dVar.e;
            if (i3 >= 0) {
                a(view, i3, i2);
            } else {
                d(view, i2);
            }
        } else {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
    }

    /* access modifiers changed from: package-private */
    public void c(View view, Rect rect) {
        ((d) view.getLayoutParams()).a(rect);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof d) && super.checkLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        d dVar = (d) view.getLayoutParams();
        Behavior behavior = dVar.f539a;
        if (behavior != null) {
            float c2 = behavior.c(this, view);
            if (c2 > 0.0f) {
                if (this.k == null) {
                    this.k = new Paint();
                }
                this.k.setColor(dVar.f539a.b(this, view));
                this.k.setAlpha(a(Math.round(c2 * 255.0f), 0, 255));
                int save = canvas.save();
                if (view.isOpaque()) {
                    canvas.clipRect((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom(), Region.Op.DIFFERENCE);
                }
                canvas.drawRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()), this.k);
                canvas.restoreToCount(save);
            }
        }
        return super.drawChild(canvas, view, j2);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.u;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public d generateDefaultLayoutParams() {
        return new d(-2, -2);
    }

    @Override // android.view.ViewGroup
    public d generateLayoutParams(AttributeSet attributeSet) {
        return new d(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public d generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof d ? new d((d) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new d((ViewGroup.MarginLayoutParams) layoutParams) : new d(layoutParams);
    }

    /* access modifiers changed from: package-private */
    public final List<View> getDependencySortedChildren() {
        e();
        return Collections.unmodifiableList(this.f);
    }

    public final D getLastWindowInsets() {
        return this.s;
    }

    public int getNestedScrollAxes() {
        return this.x.a();
    }

    public Drawable getStatusBarBackground() {
        return this.u;
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a(false);
        if (this.r) {
            if (this.q == null) {
                this.q = new e();
            }
            getViewTreeObserver().addOnPreDrawListener(this.q);
        }
        if (this.s == null && t.f(this)) {
            t.z(this);
        }
        this.m = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a(false);
        if (this.r && this.q != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.q);
        }
        View view = this.p;
        if (view != null) {
            onStopNestedScroll(view);
        }
        this.m = false;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.t && this.u != null) {
            D d2 = this.s;
            int d3 = d2 != null ? d2.d() : 0;
            if (d3 > 0) {
                this.u.setBounds(0, 0, getWidth(), d3);
                this.u.draw(canvas);
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            a(true);
        }
        boolean a2 = a(motionEvent, 0);
        if (actionMasked == 1 || actionMasked == 3) {
            a(true);
        }
        return a2;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        Behavior c2;
        int i6 = t.i(this);
        int size = this.f.size();
        for (int i7 = 0; i7 < size; i7++) {
            View view = this.f.get(i7);
            if (view.getVisibility() != 8 && ((c2 = ((d) view.getLayoutParams()).c()) == null || !c2.a(this, view, i6))) {
                c(view, i6);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0117, code lost:
        if (r0.a(r30, r20, r11, r21, r23, 0) == false) goto L_0x0126;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x011a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r31, int r32) {
        /*
        // Method dump skipped, instructions count: 388
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    @Override // b.e.g.m
    public boolean onNestedFling(View view, float f2, float f3, boolean z) {
        Behavior c2;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                d dVar = (d) childAt.getLayoutParams();
                if (dVar.a(0) && (c2 = dVar.c()) != null) {
                    z2 |= c2.a(this, childAt, view, f2, f3, z);
                }
            }
        }
        if (z2) {
            a(1);
        }
        return z2;
    }

    @Override // b.e.g.m
    public boolean onNestedPreFling(View view, float f2, float f3) {
        Behavior c2;
        int childCount = getChildCount();
        boolean z = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                d dVar = (d) childAt.getLayoutParams();
                if (dVar.a(0) && (c2 = dVar.c()) != null) {
                    z |= c2.a(this, childAt, view, f2, f3);
                }
            }
        }
        return z;
    }

    @Override // b.e.g.m
    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        a(view, i2, i3, iArr, 0);
    }

    @Override // b.e.g.m
    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        a(view, i2, i3, i4, i5, 0);
    }

    @Override // b.e.g.m
    public void onNestedScrollAccepted(View view, View view2, int i2) {
        b(view, view2, i2, 0);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        SparseArray<Parcelable> sparseArray = savedState.f537c;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            Behavior c2 = a(childAt).c();
            if (!(id == -1 || c2 == null || (parcelable2 = sparseArray.get(id)) == null)) {
                c2.a(this, childAt, parcelable2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable d2;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            Behavior c2 = ((d) childAt.getLayoutParams()).c();
            if (!(id == -1 || c2 == null || (d2 = c2.d(this, childAt)) == null)) {
                sparseArray.append(id, d2);
            }
        }
        savedState.f537c = sparseArray;
        return savedState;
    }

    @Override // b.e.g.m
    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return a(view, view2, i2, 0);
    }

    @Override // b.e.g.m
    public void onStopNestedScroll(View view) {
        a(view, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0012, code lost:
        if (r3 != false) goto L_0x0016;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            int r2 = r18.getActionMasked()
            android.view.View r3 = r0.o
            r4 = 1
            r5 = 0
            if (r3 != 0) goto L_0x0015
            boolean r3 = r0.a(r1, r4)
            if (r3 == 0) goto L_0x002b
            goto L_0x0016
        L_0x0015:
            r3 = 0
        L_0x0016:
            android.view.View r6 = r0.o
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            androidx.coordinatorlayout.widget.CoordinatorLayout$d r6 = (androidx.coordinatorlayout.widget.CoordinatorLayout.d) r6
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r6 = r6.c()
            if (r6 == 0) goto L_0x002b
            android.view.View r7 = r0.o
            boolean r6 = r6.b(r0, r7, r1)
            goto L_0x002c
        L_0x002b:
            r6 = 0
        L_0x002c:
            android.view.View r7 = r0.o
            r8 = 0
            if (r7 != 0) goto L_0x0037
            boolean r1 = super.onTouchEvent(r18)
            r6 = r6 | r1
            goto L_0x004a
        L_0x0037:
            if (r3 == 0) goto L_0x004a
            long r11 = android.os.SystemClock.uptimeMillis()
            r13 = 3
            r14 = 0
            r15 = 0
            r16 = 0
            r9 = r11
            android.view.MotionEvent r8 = android.view.MotionEvent.obtain(r9, r11, r13, r14, r15, r16)
            super.onTouchEvent(r8)
        L_0x004a:
            if (r8 == 0) goto L_0x004f
            r8.recycle()
        L_0x004f:
            if (r2 == r4) goto L_0x0054
            r1 = 3
            if (r2 != r1) goto L_0x0057
        L_0x0054:
            r0.a(r5)
        L_0x0057:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        Behavior c2 = ((d) view.getLayoutParams()).c();
        if (c2 == null || !c2.a(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z);
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z && !this.l) {
            a(false);
            this.l = true;
        }
    }

    public void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        f();
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.v = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(Drawable drawable) {
        Drawable drawable2 = this.u;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.u = drawable3;
            Drawable drawable4 = this.u;
            if (drawable4 != null) {
                if (drawable4.isStateful()) {
                    this.u.setState(getDrawableState());
                }
                androidx.core.graphics.drawable.a.a(this.u, t.i(this));
                this.u.setVisible(getVisibility() == 0, false);
                this.u.setCallback(this);
            }
            t.y(this);
        }
    }

    public void setStatusBarBackgroundColor(int i2) {
        setStatusBarBackground(new ColorDrawable(i2));
    }

    public void setStatusBarBackgroundResource(int i2) {
        setStatusBarBackground(i2 != 0 ? androidx.core.content.a.c(getContext(), i2) : null);
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z = i2 == 0;
        Drawable drawable = this.u;
        if (drawable != null && drawable.isVisible() != z) {
            this.u.setVisible(z, false);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.u;
    }
}
