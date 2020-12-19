package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.customview.view.AbsSavedState;
import b.e.g.C0111a;
import b.e.g.t;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPager extends ViewGroup {

    /* renamed from: a  reason: collision with root package name */
    static final int[] f1184a = {16842931};

    /* renamed from: b  reason: collision with root package name */
    private static final Comparator<b> f1185b = new d();

    /* renamed from: c  reason: collision with root package name */
    private static final Interpolator f1186c = new e();

    /* renamed from: d  reason: collision with root package name */
    private static final h f1187d = new h();
    private boolean A;
    private int B = 1;
    private boolean C;
    private boolean D;
    private int E;
    private int F;
    private int G;
    private float H;
    private float I;
    private float J;
    private float K;
    private int L = -1;
    private VelocityTracker M;
    private int N;
    private int O;
    private int P;
    private int Q;
    private boolean R;
    private EdgeEffect S;
    private EdgeEffect T;
    private boolean U = true;
    private boolean V = false;
    private boolean W;
    private int aa;
    private List<e> ba;
    private e ca;
    private e da;
    private int e;
    private List<d> ea;
    private final ArrayList<b> f = new ArrayList<>();
    private f fa;
    private final b g = new b();
    private int ga;
    private final Rect h = new Rect();
    private int ha;
    a i;
    private ArrayList<View> ia;
    int j;
    private final Runnable ja = new f(this);
    private int k = -1;
    private int ka = 0;
    private Parcelable l = null;
    private ClassLoader m = null;
    private Scroller n;
    private boolean o;
    private g p;
    private int q;
    private Drawable r;
    private int s;
    private int t;
    private float u = -3.4028235E38f;
    private float v = Float.MAX_VALUE;
    private int w;
    private int x;
    private boolean y;
    private boolean z;

    public static class LayoutParams extends ViewGroup.LayoutParams {

        /* renamed from: a  reason: collision with root package name */
        public boolean f1188a;

        /* renamed from: b  reason: collision with root package name */
        public int f1189b;

        /* renamed from: c  reason: collision with root package name */
        float f1190c = 0.0f;

        /* renamed from: d  reason: collision with root package name */
        boolean f1191d;
        int e;
        int f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.f1184a);
            this.f1189b = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new h();

        /* renamed from: c  reason: collision with root package name */
        int f1192c;

        /* renamed from: d  reason: collision with root package name */
        Parcelable f1193d;
        ClassLoader e;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            classLoader = classLoader == null ? SavedState.class.getClassLoader() : classLoader;
            this.f1192c = parcel.readInt();
            this.f1193d = parcel.readParcelable(classLoader);
            this.e = classLoader;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.f1192c + "}";
        }

        @Override // androidx.customview.view.AbsSavedState
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f1192c);
            parcel.writeParcelable(this.f1193d, i);
        }
    }

    @Target({ElementType.TYPE})
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    public @interface a {
    }

    /* access modifiers changed from: package-private */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        Object f1194a;

        /* renamed from: b  reason: collision with root package name */
        int f1195b;

        /* renamed from: c  reason: collision with root package name */
        boolean f1196c;

        /* renamed from: d  reason: collision with root package name */
        float f1197d;
        float e;

        b() {
        }
    }

    /* access modifiers changed from: package-private */
    public class c extends C0111a {
        c() {
        }

        private boolean b() {
            a aVar = ViewPager.this.i;
            return aVar != null && aVar.getCount() > 1;
        }

        @Override // b.e.g.C0111a
        public void a(View view, b.e.g.a.b bVar) {
            super.a(view, bVar);
            bVar.a((CharSequence) ViewPager.class.getName());
            bVar.h(b());
            if (ViewPager.this.canScrollHorizontally(1)) {
                bVar.a(4096);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                bVar.a(8192);
            }
        }

        @Override // b.e.g.C0111a
        public boolean a(View view, int i, Bundle bundle) {
            ViewPager viewPager;
            int i2;
            if (super.a(view, i, bundle)) {
                return true;
            }
            if (i != 4096) {
                if (i != 8192 || !ViewPager.this.canScrollHorizontally(-1)) {
                    return false;
                }
                viewPager = ViewPager.this;
                i2 = viewPager.j - 1;
            } else if (!ViewPager.this.canScrollHorizontally(1)) {
                return false;
            } else {
                viewPager = ViewPager.this;
                i2 = viewPager.j + 1;
            }
            viewPager.setCurrentItem(i2);
            return true;
        }

        @Override // b.e.g.C0111a
        public void b(View view, AccessibilityEvent accessibilityEvent) {
            a aVar;
            super.b(view, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.class.getName());
            accessibilityEvent.setScrollable(b());
            if (accessibilityEvent.getEventType() == 4096 && (aVar = ViewPager.this.i) != null) {
                accessibilityEvent.setItemCount(aVar.getCount());
                accessibilityEvent.setFromIndex(ViewPager.this.j);
                accessibilityEvent.setToIndex(ViewPager.this.j);
            }
        }
    }

    public interface d {
        void a(ViewPager viewPager, a aVar, a aVar2);
    }

    public interface e {
        void a(int i);

        void a(int i, float f, int i2);

        void b(int i);
    }

    public interface f {
        void a(View view, float f);
    }

    private class g extends DataSetObserver {
        g() {
        }

        public void onChanged() {
            ViewPager.this.a();
        }

        public void onInvalidated() {
            ViewPager.this.a();
        }
    }

    /* access modifiers changed from: package-private */
    public static class h implements Comparator<View> {
        h() {
        }

        /* renamed from: a */
        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            boolean z = layoutParams.f1188a;
            return z != layoutParams2.f1188a ? z ? 1 : -1 : layoutParams.e - layoutParams2.e;
        }
    }

    public ViewPager(Context context) {
        super(context);
        b();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        b();
    }

    private int a(int i2, float f2, int i3, int i4) {
        if (Math.abs(i4) <= this.P || Math.abs(i3) <= this.N) {
            i2 += (int) (f2 + (i2 >= this.j ? 0.4f : 0.6f));
        } else if (i3 <= 0) {
            i2++;
        }
        if (this.f.size() <= 0) {
            return i2;
        }
        ArrayList<b> arrayList = this.f;
        return Math.max(this.f.get(0).f1195b, Math.min(i2, arrayList.get(arrayList.size() - 1).f1195b));
    }

    private Rect a(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        }
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect.left += viewGroup.getLeft();
            rect.right += viewGroup.getRight();
            rect.top += viewGroup.getTop();
            rect.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect;
    }

    private void a(int i2, int i3, int i4, int i5) {
        int min;
        if (i3 <= 0 || this.f.isEmpty()) {
            b b2 = b(this.j);
            min = (int) ((b2 != null ? Math.min(b2.e, this.v) : 0.0f) * ((float) ((i2 - getPaddingLeft()) - getPaddingRight())));
            if (min != getScrollX()) {
                a(false);
            } else {
                return;
            }
        } else if (!this.n.isFinished()) {
            this.n.setFinalX(getCurrentItem() * getClientWidth());
            return;
        } else {
            min = (int) ((((float) getScrollX()) / ((float) (((i3 - getPaddingLeft()) - getPaddingRight()) + i5))) * ((float) (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)));
        }
        scrollTo(min, getScrollY());
    }

    private void a(int i2, boolean z2, int i3, boolean z3) {
        b b2 = b(i2);
        int clientWidth = b2 != null ? (int) (((float) getClientWidth()) * Math.max(this.u, Math.min(b2.e, this.v))) : 0;
        if (z2) {
            a(clientWidth, 0, i3);
            if (z3) {
                d(i2);
                return;
            }
            return;
        }
        if (z3) {
            d(i2);
        }
        a(false);
        scrollTo(clientWidth, 0);
        f(clientWidth);
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.L) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.H = motionEvent.getX(i2);
            this.L = motionEvent.getPointerId(i2);
            VelocityTracker velocityTracker = this.M;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private void a(b bVar, int i2, b bVar2) {
        int i3;
        int i4;
        b bVar3;
        b bVar4;
        int count = this.i.getCount();
        int clientWidth = getClientWidth();
        float f2 = clientWidth > 0 ? ((float) this.q) / ((float) clientWidth) : 0.0f;
        if (bVar2 != null) {
            int i5 = bVar2.f1195b;
            int i6 = bVar.f1195b;
            if (i5 < i6) {
                float f3 = bVar2.e + bVar2.f1197d + f2;
                int i7 = i5 + 1;
                int i8 = 0;
                while (i7 <= bVar.f1195b && i8 < this.f.size()) {
                    while (true) {
                        bVar4 = this.f.get(i8);
                        if (i7 > bVar4.f1195b && i8 < this.f.size() - 1) {
                            i8++;
                        }
                    }
                    while (i7 < bVar4.f1195b) {
                        f3 += this.i.getPageWidth(i7) + f2;
                        i7++;
                    }
                    bVar4.e = f3;
                    f3 += bVar4.f1197d + f2;
                    i7++;
                }
            } else if (i5 > i6) {
                int size = this.f.size() - 1;
                float f4 = bVar2.e;
                while (true) {
                    i5--;
                    if (i5 < bVar.f1195b || size < 0) {
                        break;
                    }
                    while (true) {
                        bVar3 = this.f.get(size);
                        if (i5 < bVar3.f1195b && size > 0) {
                            size--;
                        }
                    }
                    while (i5 > bVar3.f1195b) {
                        f4 -= this.i.getPageWidth(i5) + f2;
                        i5--;
                    }
                    f4 -= bVar3.f1197d + f2;
                    bVar3.e = f4;
                }
            }
        }
        int size2 = this.f.size();
        float f5 = bVar.e;
        int i9 = bVar.f1195b;
        int i10 = i9 - 1;
        this.u = i9 == 0 ? f5 : -3.4028235E38f;
        int i11 = count - 1;
        this.v = bVar.f1195b == i11 ? (bVar.e + bVar.f1197d) - 1.0f : Float.MAX_VALUE;
        int i12 = i2 - 1;
        while (i12 >= 0) {
            b bVar5 = this.f.get(i12);
            while (true) {
                i4 = bVar5.f1195b;
                if (i10 <= i4) {
                    break;
                }
                f5 -= this.i.getPageWidth(i10) + f2;
                i10--;
            }
            f5 -= bVar5.f1197d + f2;
            bVar5.e = f5;
            if (i4 == 0) {
                this.u = f5;
            }
            i12--;
            i10--;
        }
        float f6 = bVar.e + bVar.f1197d + f2;
        int i13 = bVar.f1195b + 1;
        int i14 = i2 + 1;
        while (i14 < size2) {
            b bVar6 = this.f.get(i14);
            while (true) {
                i3 = bVar6.f1195b;
                if (i13 >= i3) {
                    break;
                }
                f6 += this.i.getPageWidth(i13) + f2;
                i13++;
            }
            if (i3 == i11) {
                this.v = (bVar6.f1197d + f6) - 1.0f;
            }
            bVar6.e = f6;
            f6 += bVar6.f1197d + f2;
            i14++;
            i13++;
        }
        this.V = false;
    }

    private void a(boolean z2) {
        boolean z3 = this.ka == 2;
        if (z3) {
            setScrollingCacheEnabled(false);
            if (!this.n.isFinished()) {
                this.n.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.n.getCurrX();
                int currY = this.n.getCurrY();
                if (!(scrollX == currX && scrollY == currY)) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        f(currX);
                    }
                }
            }
        }
        this.A = false;
        boolean z4 = z3;
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            b bVar = this.f.get(i2);
            if (bVar.f1196c) {
                bVar.f1196c = false;
                z4 = true;
            }
        }
        if (!z4) {
            return;
        }
        if (z2) {
            t.a(this, this.ja);
        } else {
            this.ja.run();
        }
    }

    private boolean a(float f2, float f3) {
        return (f2 < ((float) this.F) && f3 > 0.0f) || (f2 > ((float) (getWidth() - this.F)) && f3 < 0.0f);
    }

    private void b(int i2, float f2, int i3) {
        e eVar = this.ca;
        if (eVar != null) {
            eVar.a(i2, f2, i3);
        }
        List<e> list = this.ba;
        if (list != null) {
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                e eVar2 = this.ba.get(i4);
                if (eVar2 != null) {
                    eVar2.a(i2, f2, i3);
                }
            }
        }
        e eVar3 = this.da;
        if (eVar3 != null) {
            eVar3.a(i2, f2, i3);
        }
    }

    private void b(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).setLayerType(z2 ? this.ga : 0, null);
        }
    }

    private boolean b(float f2) {
        boolean z2;
        boolean z3;
        float f3 = this.H - f2;
        this.H = f2;
        float scrollX = ((float) getScrollX()) + f3;
        float clientWidth = (float) getClientWidth();
        float f4 = this.u * clientWidth;
        float f5 = this.v * clientWidth;
        boolean z4 = false;
        b bVar = this.f.get(0);
        ArrayList<b> arrayList = this.f;
        b bVar2 = arrayList.get(arrayList.size() - 1);
        if (bVar.f1195b != 0) {
            f4 = bVar.e * clientWidth;
            z2 = false;
        } else {
            z2 = true;
        }
        if (bVar2.f1195b != this.i.getCount() - 1) {
            f5 = bVar2.e * clientWidth;
            z3 = false;
        } else {
            z3 = true;
        }
        if (scrollX < f4) {
            if (z2) {
                this.S.onPull(Math.abs(f4 - scrollX) / clientWidth);
                z4 = true;
            }
            scrollX = f4;
        } else if (scrollX > f5) {
            if (z3) {
                this.T.onPull(Math.abs(scrollX - f5) / clientWidth);
                z4 = true;
            }
            scrollX = f5;
        }
        int i2 = (int) scrollX;
        this.H += scrollX - ((float) i2);
        scrollTo(i2, getScrollY());
        f(i2);
        return z4;
    }

    private void c(boolean z2) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z2);
        }
    }

    private static boolean c(View view) {
        return view.getClass().getAnnotation(a.class) != null;
    }

    private void d(int i2) {
        e eVar = this.ca;
        if (eVar != null) {
            eVar.b(i2);
        }
        List<e> list = this.ba;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                e eVar2 = this.ba.get(i3);
                if (eVar2 != null) {
                    eVar2.b(i2);
                }
            }
        }
        e eVar3 = this.da;
        if (eVar3 != null) {
            eVar3.b(i2);
        }
    }

    private void e(int i2) {
        e eVar = this.ca;
        if (eVar != null) {
            eVar.a(i2);
        }
        List<e> list = this.ba;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                e eVar2 = this.ba.get(i3);
                if (eVar2 != null) {
                    eVar2.a(i2);
                }
            }
        }
        e eVar3 = this.da;
        if (eVar3 != null) {
            eVar3.a(i2);
        }
    }

    private void f() {
        this.C = false;
        this.D = false;
        VelocityTracker velocityTracker = this.M;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.M = null;
        }
    }

    private boolean f(int i2) {
        if (this.f.size() != 0) {
            b g2 = g();
            int clientWidth = getClientWidth();
            int i3 = this.q;
            int i4 = clientWidth + i3;
            float f2 = (float) clientWidth;
            int i5 = g2.f1195b;
            float f3 = ((((float) i2) / f2) - g2.e) / (g2.f1197d + (((float) i3) / f2));
            this.W = false;
            a(i5, f3, (int) (((float) i4) * f3));
            if (this.W) {
                return true;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        } else if (this.U) {
            return false;
        } else {
            this.W = false;
            a(0, 0.0f, 0);
            if (this.W) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
    }

    private b g() {
        int i2;
        int clientWidth = getClientWidth();
        float scrollX = clientWidth > 0 ? ((float) getScrollX()) / ((float) clientWidth) : 0.0f;
        float f2 = clientWidth > 0 ? ((float) this.q) / ((float) clientWidth) : 0.0f;
        b bVar = null;
        int i3 = 0;
        boolean z2 = true;
        int i4 = -1;
        float f3 = 0.0f;
        float f4 = 0.0f;
        while (i3 < this.f.size()) {
            b bVar2 = this.f.get(i3);
            if (!z2 && bVar2.f1195b != (i2 = i4 + 1)) {
                bVar2 = this.g;
                bVar2.e = f3 + f4 + f2;
                bVar2.f1195b = i2;
                bVar2.f1197d = this.i.getPageWidth(bVar2.f1195b);
                i3--;
            }
            f3 = bVar2.e;
            float f5 = bVar2.f1197d + f3 + f2;
            if (!z2 && scrollX < f3) {
                return bVar;
            }
            if (scrollX < f5 || i3 == this.f.size() - 1) {
                return bVar2;
            }
            i4 = bVar2.f1195b;
            f4 = bVar2.f1197d;
            i3++;
            bVar = bVar2;
            z2 = false;
        }
        return bVar;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void h() {
        int i2 = 0;
        while (i2 < getChildCount()) {
            if (!((LayoutParams) getChildAt(i2).getLayoutParams()).f1188a) {
                removeViewAt(i2);
                i2--;
            }
            i2++;
        }
    }

    private boolean i() {
        this.L = -1;
        f();
        this.S.onRelease();
        this.T.onRelease();
        return this.S.isFinished() || this.T.isFinished();
    }

    private void j() {
        if (this.ha != 0) {
            ArrayList<View> arrayList = this.ia;
            if (arrayList == null) {
                this.ia = new ArrayList<>();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                this.ia.add(getChildAt(i2));
            }
            Collections.sort(this.ia, f1187d);
        }
    }

    private void setScrollingCacheEnabled(boolean z2) {
        if (this.z != z2) {
            this.z = z2;
        }
    }

    /* access modifiers changed from: package-private */
    public float a(float f2) {
        return (float) Math.sin((double) ((f2 - 0.5f) * 0.47123894f));
    }

    /* access modifiers changed from: package-private */
    public b a(int i2, int i3) {
        b bVar = new b();
        bVar.f1195b = i2;
        bVar.f1194a = this.i.instantiateItem((ViewGroup) this, i2);
        bVar.f1197d = this.i.getPageWidth(i2);
        if (i3 < 0 || i3 >= this.f.size()) {
            this.f.add(bVar);
        } else {
            this.f.add(i3, bVar);
        }
        return bVar;
    }

    /* access modifiers changed from: package-private */
    public b a(View view) {
        while (true) {
            ViewParent parent = view.getParent();
            if (parent == this) {
                return b(view);
            }
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
    }

    /* access modifiers changed from: package-private */
    public e a(e eVar) {
        e eVar2 = this.da;
        this.da = eVar;
        return eVar2;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        int count = this.i.getCount();
        this.e = count;
        boolean z2 = this.f.size() < (this.B * 2) + 1 && this.f.size() < count;
        int i2 = this.j;
        int i3 = 0;
        boolean z3 = false;
        while (i3 < this.f.size()) {
            b bVar = this.f.get(i3);
            int itemPosition = this.i.getItemPosition(bVar.f1194a);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.f.remove(i3);
                    i3--;
                    if (!z3) {
                        this.i.startUpdate((ViewGroup) this);
                        z3 = true;
                    }
                    this.i.destroyItem((ViewGroup) this, bVar.f1195b, bVar.f1194a);
                    int i4 = this.j;
                    if (i4 == bVar.f1195b) {
                        i2 = Math.max(0, Math.min(i4, count - 1));
                    }
                } else {
                    int i5 = bVar.f1195b;
                    if (i5 != itemPosition) {
                        if (i5 == this.j) {
                            i2 = itemPosition;
                        }
                        bVar.f1195b = itemPosition;
                    }
                }
                z2 = true;
            }
            i3++;
        }
        if (z3) {
            this.i.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.f, f1185b);
        if (z2) {
            int childCount = getChildCount();
            for (int i6 = 0; i6 < childCount; i6++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i6).getLayoutParams();
                if (!layoutParams.f1188a) {
                    layoutParams.f1190c = 0.0f;
                }
            }
            a(i2, false, true);
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r13, float r14, int r15) {
        /*
        // Method dump skipped, instructions count: 164
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.a(int, float, int):void");
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, int i4) {
        int i5;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        Scroller scroller = this.n;
        if (scroller != null && !scroller.isFinished()) {
            i5 = this.o ? this.n.getCurrX() : this.n.getStartX();
            this.n.abortAnimation();
            setScrollingCacheEnabled(false);
        } else {
            i5 = getScrollX();
        }
        int scrollY = getScrollY();
        int i6 = i2 - i5;
        int i7 = i3 - scrollY;
        if (i6 == 0 && i7 == 0) {
            a(false);
            e();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int clientWidth = getClientWidth();
        int i8 = clientWidth / 2;
        float f2 = (float) clientWidth;
        float f3 = (float) i8;
        float a2 = f3 + (a(Math.min(1.0f, (((float) Math.abs(i6)) * 1.0f) / f2)) * f3);
        int abs = Math.abs(i4);
        int min = Math.min(abs > 0 ? Math.round(Math.abs(a2 / ((float) abs)) * 1000.0f) * 4 : (int) (((((float) Math.abs(i6)) / ((f2 * this.i.getPageWidth(this.j)) + ((float) this.q))) + 1.0f) * 100.0f), (int) InstallationUtils.CURRENT_OPERATION_UNKNOWN);
        this.o = false;
        this.n.startScroll(i5, scrollY, i6, i7, min);
        t.y(this);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, boolean z2, boolean z3) {
        a(i2, z2, z3, 0);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, boolean z2, boolean z3, int i3) {
        a aVar = this.i;
        if (aVar == null || aVar.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z3 || this.j != i2 || this.f.size() == 0) {
            boolean z4 = true;
            if (i2 < 0) {
                i2 = 0;
            } else if (i2 >= this.i.getCount()) {
                i2 = this.i.getCount() - 1;
            }
            int i4 = this.B;
            int i5 = this.j;
            if (i2 > i5 + i4 || i2 < i5 - i4) {
                for (int i6 = 0; i6 < this.f.size(); i6++) {
                    this.f.get(i6).f1196c = true;
                }
            }
            if (this.j == i2) {
                z4 = false;
            }
            if (this.U) {
                this.j = i2;
                if (z4) {
                    d(i2);
                }
                requestLayout();
                return;
            }
            c(i2);
            a(i2, z2, i3, z4);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    public void a(d dVar) {
        if (this.ea == null) {
            this.ea = new ArrayList();
        }
        this.ea.add(dVar);
    }

    public boolean a(int i2) {
        boolean d2;
        boolean z2;
        View findFocus = findFocus();
        boolean z3 = false;
        View view = null;
        if (findFocus != this) {
            if (findFocus != null) {
                ViewParent parent = findFocus.getParent();
                while (true) {
                    if (!(parent instanceof ViewGroup)) {
                        z2 = false;
                        break;
                    } else if (parent == this) {
                        z2 = true;
                        break;
                    } else {
                        parent = parent.getParent();
                    }
                }
                if (!z2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(findFocus.getClass().getSimpleName());
                    for (ViewParent parent2 = findFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                        sb.append(" => ");
                        sb.append(parent2.getClass().getSimpleName());
                    }
                    Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                }
            }
            view = findFocus;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i2);
        if (findNextFocus != null && findNextFocus != view) {
            if (i2 == 17) {
                int i3 = a(this.h, findNextFocus).left;
                int i4 = a(this.h, view).left;
                if (view != null && i3 >= i4) {
                    d2 = c();
                    z3 = d2;
                }
            } else if (i2 == 66) {
                int i5 = a(this.h, findNextFocus).left;
                int i6 = a(this.h, view).left;
                if (view != null && i5 <= i6) {
                    d2 = d();
                    z3 = d2;
                }
            }
            d2 = findNextFocus.requestFocus();
            z3 = d2;
        } else if (i2 == 17 || i2 == 1) {
            z3 = c();
        } else if (i2 == 66 || i2 == 2) {
            z3 = d();
        }
        if (z3) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i2));
        }
        return z3;
    }

    public boolean a(KeyEvent keyEvent) {
        int i2;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 21) {
                if (keyCode != 22) {
                    if (keyCode == 61) {
                        if (keyEvent.hasNoModifiers()) {
                            return a(2);
                        }
                        if (keyEvent.hasModifiers(1)) {
                            return a(1);
                        }
                    }
                } else if (keyEvent.hasModifiers(2)) {
                    return d();
                } else {
                    i2 = 66;
                }
            } else if (keyEvent.hasModifiers(2)) {
                return c();
            } else {
                i2 = 17;
            }
            return a(i2);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean a(View view, boolean z2, int i2, int i3, int i4) {
        int i5;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i6 = i3 + scrollX;
                if (i6 >= childAt.getLeft() && i6 < childAt.getRight() && (i5 = i4 + scrollY) >= childAt.getTop() && i5 < childAt.getBottom() && a(childAt, true, i2, i6 - childAt.getLeft(), i5 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z2 && view.canScrollHorizontally(-i2);
    }

    @Override // android.view.View, android.view.ViewGroup
    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        b b2;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                if (childAt.getVisibility() == 0 && (b2 = b(childAt)) != null && b2.f1195b == this.j) {
                    childAt.addFocusables(arrayList, i2, i3);
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if (((i3 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
            arrayList.add(this);
        }
    }

    @Override // android.view.View, android.view.ViewGroup
    public void addTouchables(ArrayList<View> arrayList) {
        b b2;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (b2 = b(childAt)) != null && b2.f1195b == this.j) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (!checkLayoutParams(layoutParams)) {
            layoutParams = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        layoutParams2.f1188a |= c(view);
        if (!this.y) {
            super.addView(view, i2, layoutParams);
        } else if (layoutParams2 == null || !layoutParams2.f1188a) {
            layoutParams2.f1191d = true;
            addViewInLayout(view, i2, layoutParams);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    /* access modifiers changed from: package-private */
    public b b(int i2) {
        for (int i3 = 0; i3 < this.f.size(); i3++) {
            b bVar = this.f.get(i3);
            if (bVar.f1195b == i2) {
                return bVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public b b(View view) {
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            b bVar = this.f.get(i2);
            if (this.i.isViewFromObject(view, bVar.f1194a)) {
                return bVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.n = new Scroller(context, f1186c);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.G = viewConfiguration.getScaledPagingTouchSlop();
        this.N = (int) (400.0f * f2);
        this.O = viewConfiguration.getScaledMaximumFlingVelocity();
        this.S = new EdgeEffect(context);
        this.T = new EdgeEffect(context);
        this.P = (int) (25.0f * f2);
        this.Q = (int) (2.0f * f2);
        this.E = (int) (f2 * 16.0f);
        t.a(this, new c());
        if (t.g(this) == 0) {
            t.c(this, 1);
        }
        t.a(this, new g(this));
    }

    public void b(d dVar) {
        List<d> list = this.ea;
        if (list != null) {
            list.remove(dVar);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0060, code lost:
        if (r9 == r10) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0066, code lost:
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c3, code lost:
        if (r15 >= 0) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d1, code lost:
        if (r15 >= 0) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00df, code lost:
        if (r15 >= 0) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00ea, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x013f, code lost:
        if (r4 < r17.f.size()) goto L_0x0141;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x014a, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x015d, code lost:
        if (r4 < r17.f.size()) goto L_0x0141;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x016f, code lost:
        if (r4 < r17.f.size()) goto L_0x0141;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(int r18) {
        /*
        // Method dump skipped, instructions count: 586
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.c(int):void");
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        int i2 = this.j;
        if (i2 <= 0) {
            return false;
        }
        setCurrentItem(i2 - 1, true);
        return true;
    }

    public boolean canScrollHorizontally(int i2) {
        if (this.i == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        return i2 < 0 ? scrollX > ((int) (((float) clientWidth) * this.u)) : i2 > 0 && scrollX < ((int) (((float) clientWidth) * this.v));
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        this.o = true;
        if (this.n.isFinished() || !this.n.computeScrollOffset()) {
            a(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.n.getCurrX();
        int currY = this.n.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!f(currX)) {
                this.n.abortAnimation();
                scrollTo(0, currY);
            }
        }
        t.y(this);
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        a aVar = this.i;
        if (aVar == null || this.j >= aVar.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.j + 1, true);
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || a(keyEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        b b2;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (b2 = b(childAt)) != null && b2.f1195b == this.j && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    public void draw(Canvas canvas) {
        a aVar;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean z2 = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (aVar = this.i) != null && aVar.getCount() > 1)) {
            if (!this.S.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) ((-height) + getPaddingTop()), this.u * ((float) width));
                this.S.setSize(height, width);
                z2 = false | this.S.draw(canvas);
                canvas.restoreToCount(save);
            }
            if (!this.T.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.v + 1.0f)) * ((float) width2));
                this.T.setSize(height2, width2);
                z2 |= this.T.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.S.finish();
            this.T.finish();
        }
        if (z2) {
            t.y(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.r;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        c(this.j);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public a getAdapter() {
        return this.i;
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        if (this.ha == 2) {
            i3 = (i2 - 1) - i3;
        }
        return ((LayoutParams) this.ia.get(i3).getLayoutParams()).f;
    }

    public int getCurrentItem() {
        return this.j;
    }

    public int getOffscreenPageLimit() {
        return this.B;
    }

    public int getPageMargin() {
        return this.q;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.U = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        removeCallbacks(this.ja);
        Scroller scroller = this.n;
        if (scroller != null && !scroller.isFinished()) {
            this.n.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float f2;
        float f3;
        super.onDraw(canvas);
        if (this.q > 0 && this.r != null && this.f.size() > 0 && this.i != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float f4 = (float) width;
            float f5 = ((float) this.q) / f4;
            int i2 = 0;
            b bVar = this.f.get(0);
            float f6 = bVar.e;
            int size = this.f.size();
            int i3 = bVar.f1195b;
            int i4 = this.f.get(size - 1).f1195b;
            while (i3 < i4) {
                while (i3 > bVar.f1195b && i2 < size) {
                    i2++;
                    bVar = this.f.get(i2);
                }
                if (i3 == bVar.f1195b) {
                    float f7 = bVar.e;
                    float f8 = bVar.f1197d;
                    f2 = (f7 + f8) * f4;
                    f6 = f7 + f8 + f5;
                } else {
                    float pageWidth = this.i.getPageWidth(i3);
                    f2 = (f6 + pageWidth) * f4;
                    f6 += pageWidth + f5;
                }
                if (((float) this.q) + f2 > ((float) scrollX)) {
                    f3 = f5;
                    this.r.setBounds(Math.round(f2), this.s, Math.round(((float) this.q) + f2), this.t);
                    this.r.draw(canvas);
                } else {
                    f3 = f5;
                }
                if (f2 <= ((float) (scrollX + width))) {
                    i3++;
                    f5 = f3;
                } else {
                    return;
                }
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            i();
            return false;
        }
        if (action != 0) {
            if (this.C) {
                return true;
            }
            if (this.D) {
                return false;
            }
        }
        if (action == 0) {
            float x2 = motionEvent.getX();
            this.J = x2;
            this.H = x2;
            float y2 = motionEvent.getY();
            this.K = y2;
            this.I = y2;
            this.L = motionEvent.getPointerId(0);
            this.D = false;
            this.o = true;
            this.n.computeScrollOffset();
            if (this.ka != 2 || Math.abs(this.n.getFinalX() - this.n.getCurrX()) <= this.Q) {
                a(false);
                this.C = false;
            } else {
                this.n.abortAnimation();
                this.A = false;
                e();
                this.C = true;
                c(true);
                setScrollState(1);
            }
        } else if (action == 2) {
            int i2 = this.L;
            if (i2 != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(i2);
                float x3 = motionEvent.getX(findPointerIndex);
                float f2 = x3 - this.H;
                float abs = Math.abs(f2);
                float y3 = motionEvent.getY(findPointerIndex);
                float abs2 = Math.abs(y3 - this.K);
                if (f2 == 0.0f || a(this.H, f2) || !a(this, false, (int) f2, (int) x3, (int) y3)) {
                    if (abs > ((float) this.G) && abs * 0.5f > abs2) {
                        this.C = true;
                        c(true);
                        setScrollState(1);
                        this.H = f2 > 0.0f ? this.J + ((float) this.G) : this.J - ((float) this.G);
                        this.I = y3;
                        setScrollingCacheEnabled(true);
                    } else if (abs2 > ((float) this.G)) {
                        this.D = true;
                    }
                    if (this.C && b(x3)) {
                        t.y(this);
                    }
                } else {
                    this.H = x3;
                    this.I = y3;
                    this.D = true;
                    return false;
                }
            }
        } else if (action == 6) {
            a(motionEvent);
        }
        if (this.M == null) {
            this.M = VelocityTracker.obtain();
        }
        this.M.addMovement(motionEvent);
        return this.C;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        boolean z3;
        b b2;
        int i6;
        int i7;
        int childCount = getChildCount();
        int i8 = i4 - i2;
        int i9 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i10 = paddingBottom;
        int i11 = 0;
        int i12 = paddingTop;
        int i13 = paddingLeft;
        for (int i14 = 0; i14 < childCount; i14++) {
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.f1188a) {
                    int i15 = layoutParams.f1189b;
                    int i16 = i15 & 7;
                    int i17 = i15 & 112;
                    if (i16 == 1) {
                        i6 = Math.max((i8 - childAt.getMeasuredWidth()) / 2, i13);
                    } else if (i16 == 3) {
                        i6 = i13;
                        i13 = childAt.getMeasuredWidth() + i13;
                    } else if (i16 != 5) {
                        i6 = i13;
                    } else {
                        i6 = (i8 - paddingRight) - childAt.getMeasuredWidth();
                        paddingRight += childAt.getMeasuredWidth();
                    }
                    if (i17 == 16) {
                        i7 = Math.max((i9 - childAt.getMeasuredHeight()) / 2, i12);
                    } else if (i17 == 48) {
                        i7 = i12;
                        i12 = childAt.getMeasuredHeight() + i12;
                    } else if (i17 != 80) {
                        i7 = i12;
                    } else {
                        i7 = (i9 - i10) - childAt.getMeasuredHeight();
                        i10 += childAt.getMeasuredHeight();
                    }
                    int i18 = i6 + scrollX;
                    childAt.layout(i18, i7, childAt.getMeasuredWidth() + i18, i7 + childAt.getMeasuredHeight());
                    i11++;
                }
            }
        }
        int i19 = (i8 - i13) - paddingRight;
        for (int i20 = 0; i20 < childCount; i20++) {
            View childAt2 = getChildAt(i20);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.f1188a && (b2 = b(childAt2)) != null) {
                    float f2 = (float) i19;
                    int i21 = ((int) (b2.e * f2)) + i13;
                    if (layoutParams2.f1191d) {
                        layoutParams2.f1191d = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (f2 * layoutParams2.f1190c), 1073741824), View.MeasureSpec.makeMeasureSpec((i9 - i12) - i10, 1073741824));
                    }
                    childAt2.layout(i21, i12, childAt2.getMeasuredWidth() + i21, childAt2.getMeasuredHeight() + i12);
                }
            }
        }
        this.s = i12;
        this.t = i9 - i10;
        this.aa = i11;
        if (this.U) {
            z3 = false;
            a(this.j, false, 0, false);
        } else {
            z3 = false;
        }
        this.U = z3;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r14, int r15) {
        /*
        // Method dump skipped, instructions count: 246
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        int i3;
        int i4;
        b b2;
        int childCount = getChildCount();
        int i5 = -1;
        if ((i2 & 2) != 0) {
            i5 = childCount;
            i4 = 0;
            i3 = 1;
        } else {
            i4 = childCount - 1;
            i3 = -1;
        }
        while (i4 != i5) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() == 0 && (b2 = b(childAt)) != null && b2.f1195b == this.j && childAt.requestFocus(i2, rect)) {
                return true;
            }
            i4 += i3;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        a aVar = this.i;
        if (aVar != null) {
            aVar.restoreState(savedState.f1193d, savedState.e);
            a(savedState.f1192c, false, true);
            return;
        }
        this.k = savedState.f1192c;
        this.l = savedState.f1193d;
        this.m = savedState.e;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1192c = this.j;
        a aVar = this.i;
        if (aVar != null) {
            savedState.f1193d = aVar.saveState();
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            int i6 = this.q;
            a(i2, i4, i6, i6);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x0151  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
        // Method dump skipped, instructions count: 342
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager.widget.ViewPager.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void removeView(View view) {
        if (this.y) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setAdapter(a aVar) {
        a aVar2 = this.i;
        if (aVar2 != null) {
            aVar2.setViewPagerObserver(null);
            this.i.startUpdate((ViewGroup) this);
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                b bVar = this.f.get(i2);
                this.i.destroyItem((ViewGroup) this, bVar.f1195b, bVar.f1194a);
            }
            this.i.finishUpdate((ViewGroup) this);
            this.f.clear();
            h();
            this.j = 0;
            scrollTo(0, 0);
        }
        a aVar3 = this.i;
        this.i = aVar;
        this.e = 0;
        if (this.i != null) {
            if (this.p == null) {
                this.p = new g();
            }
            this.i.setViewPagerObserver(this.p);
            this.A = false;
            boolean z2 = this.U;
            this.U = true;
            this.e = this.i.getCount();
            if (this.k >= 0) {
                this.i.restoreState(this.l, this.m);
                a(this.k, false, true);
                this.k = -1;
                this.l = null;
                this.m = null;
            } else if (!z2) {
                e();
            } else {
                requestLayout();
            }
        }
        List<d> list = this.ea;
        if (!(list == null || list.isEmpty())) {
            int size = this.ea.size();
            for (int i3 = 0; i3 < size; i3++) {
                this.ea.get(i3).a(this, aVar3, aVar);
            }
        }
    }

    public void setCurrentItem(int i2) {
        this.A = false;
        a(i2, !this.U, false);
    }

    public void setCurrentItem(int i2, boolean z2) {
        this.A = false;
        a(i2, z2, false);
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + i2 + " too small; defaulting to " + 1);
            i2 = 1;
        }
        if (i2 != this.B) {
            this.B = i2;
            e();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(e eVar) {
        this.ca = eVar;
    }

    public void setPageMargin(int i2) {
        int i3 = this.q;
        this.q = i2;
        int width = getWidth();
        a(width, width, i2, i3);
        requestLayout();
    }

    public void setPageMarginDrawable(int i2) {
        setPageMarginDrawable(androidx.core.content.a.c(getContext(), i2));
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.r = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageTransformer(boolean z2, f fVar) {
        setPageTransformer(z2, fVar, 2);
    }

    public void setPageTransformer(boolean z2, f fVar, int i2) {
        int i3 = 1;
        boolean z3 = fVar != null;
        boolean z4 = z3 != (this.fa != null);
        this.fa = fVar;
        setChildrenDrawingOrderEnabled(z3);
        if (z3) {
            if (z2) {
                i3 = 2;
            }
            this.ha = i3;
            this.ga = i2;
        } else {
            this.ha = 0;
        }
        if (z4) {
            e();
        }
    }

    /* access modifiers changed from: package-private */
    public void setScrollState(int i2) {
        if (this.ka != i2) {
            this.ka = i2;
            if (this.fa != null) {
                b(i2 != 0);
            }
            e(i2);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.r;
    }
}
