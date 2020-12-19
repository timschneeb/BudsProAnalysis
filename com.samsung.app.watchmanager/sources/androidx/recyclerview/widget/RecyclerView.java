package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.N;
import androidx.recyclerview.widget.O;
import androidx.recyclerview.widget.p;
import b.e.g.C0111a;
import b.e.g.a.b;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.update.UpdateManager;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements b.e.g.p, b.e.g.i {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f978a = {16843830};

    /* renamed from: b  reason: collision with root package name */
    private static final int[] f979b = {16842987};

    /* renamed from: c  reason: collision with root package name */
    static final boolean f980c;

    /* renamed from: d  reason: collision with root package name */
    static final boolean f981d = (Build.VERSION.SDK_INT >= 23);
    static final boolean e = (Build.VERSION.SDK_INT >= 16);
    static final boolean f = (Build.VERSION.SDK_INT >= 21);
    private static final boolean g = (Build.VERSION.SDK_INT <= 15);
    private static final boolean h = (Build.VERSION.SDK_INT <= 15);
    private static final Class<?>[] i;
    static final Interpolator j = new A();
    private l A;
    private final int[] Aa;
    boolean B;
    private b.e.g.k Ba;
    boolean C;
    private final int[] Ca;
    boolean D;
    final int[] Da;
    boolean E;
    private final int[] Ea;
    private int F;
    final int[] Fa;
    boolean G;
    final List<v> Ga;
    boolean H;
    private Runnable Ha;
    private boolean I;
    private final O.b Ia;
    private int J;
    boolean K;
    private final AccessibilityManager L;
    private List<j> M;
    boolean N;
    boolean O;
    private int P;
    private int Q;
    private e R;
    private EdgeEffect S;
    private EdgeEffect T;
    private EdgeEffect U;
    private EdgeEffect V;
    f W;
    private int aa;
    private int ba;
    private VelocityTracker ca;
    private int da;
    private int ea;
    private int fa;
    private int ga;
    private int ha;
    private k ia;
    private final int ja;
    private final q k;
    private final int ka;
    final o l;
    private float la;
    private SavedState m;
    private float ma;
    C0096a n;
    private boolean na;
    C0097b o;
    final u oa;
    final O p;
    p pa;
    boolean q;
    p.a qa;
    final Runnable r;
    final s ra;
    final Rect s;
    private m sa;
    private final Rect t;
    private List<m> ta;
    final RectF u;
    boolean ua;
    a v;
    boolean va;
    i w;
    private f.b wa;
    p x;
    boolean xa;
    final ArrayList<h> y;
    H ya;
    private final ArrayList<l> z;
    private d za;

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        /* renamed from: a  reason: collision with root package name */
        v f982a;

        /* renamed from: b  reason: collision with root package name */
        final Rect f983b = new Rect();

        /* renamed from: c  reason: collision with root package name */
        boolean f984c = true;

        /* renamed from: d  reason: collision with root package name */
        boolean f985d = false;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
        }

        public int a() {
            return this.f982a.getLayoutPosition();
        }

        public boolean b() {
            return this.f982a.isUpdated();
        }

        public boolean c() {
            return this.f982a.isRemoved();
        }

        public boolean d() {
            return this.f982a.isInvalid();
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new G();

        /* renamed from: c  reason: collision with root package name */
        Parcelable f986c;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f986c = parcel.readParcelable(classLoader == null ? i.class.getClassLoader() : classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        /* access modifiers changed from: package-private */
        public void a(SavedState savedState) {
            this.f986c = savedState.f986c;
        }

        @Override // androidx.customview.view.AbsSavedState
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.f986c, 0);
        }
    }

    public static abstract class a<VH extends v> {
        private boolean mHasStableIds = false;
        private final b mObservable = new b();

        public final void bindViewHolder(VH vh, int i) {
            vh.mPosition = i;
            if (hasStableIds()) {
                vh.mItemId = getItemId(i);
            }
            vh.setFlags(1, 519);
            b.e.c.a.a("RV OnBindView");
            onBindViewHolder(vh, i, vh.getUnmodifiedPayloads());
            vh.clearPayload();
            ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).f984c = true;
            }
            b.e.c.a.a();
        }

        public final VH createViewHolder(ViewGroup viewGroup, int i) {
            try {
                b.e.c.a.a("RV CreateView");
                VH onCreateViewHolder = onCreateViewHolder(viewGroup, i);
                if (onCreateViewHolder.itemView.getParent() == null) {
                    onCreateViewHolder.mItemViewType = i;
                    return onCreateViewHolder;
                }
                throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            } finally {
                b.e.c.a.a();
            }
        }

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.a();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.b();
        }

        public final void notifyItemChanged(int i) {
            this.mObservable.b(i, 1);
        }

        public final void notifyItemChanged(int i, Object obj) {
            this.mObservable.a(i, 1, obj);
        }

        public final void notifyItemInserted(int i) {
            this.mObservable.c(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.a(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.mObservable.b(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            this.mObservable.a(i, i2, obj);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.c(i, i2);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.d(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.mObservable.d(i, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH vh, int i);

        public void onBindViewHolder(VH vh, int i, List<Object> list) {
            onBindViewHolder(vh, i);
        }

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public void onViewRecycled(VH vh) {
        }

        public void registerAdapterDataObserver(c cVar) {
            this.mObservable.registerObserver(cVar);
        }

        public void setHasStableIds(boolean z) {
            if (!hasObservers()) {
                this.mHasStableIds = z;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public void unregisterAdapterDataObserver(c cVar) {
            this.mObservable.unregisterObserver(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    public static class b extends Observable<c> {
        b() {
        }

        public void a(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((c) ((Observable) this).mObservers.get(size)).a(i, i2, 1);
            }
        }

        public void a(int i, int i2, Object obj) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((c) ((Observable) this).mObservers.get(size)).a(i, i2, obj);
            }
        }

        public boolean a() {
            return !((Observable) this).mObservers.isEmpty();
        }

        public void b() {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((c) ((Observable) this).mObservers.get(size)).a();
            }
        }

        public void b(int i, int i2) {
            a(i, i2, null);
        }

        public void c(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((c) ((Observable) this).mObservers.get(size)).b(i, i2);
            }
        }

        public void d(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                ((c) ((Observable) this).mObservers.get(size)).c(i, i2);
            }
        }
    }

    public static abstract class c {
        public void a() {
        }

        public void a(int i, int i2) {
        }

        public void a(int i, int i2, int i3) {
        }

        public void a(int i, int i2, Object obj) {
            a(i, i2);
        }

        public void b(int i, int i2) {
        }

        public void c(int i, int i2) {
        }
    }

    public interface d {
        int a(int i, int i2);
    }

    public static class e {
        /* access modifiers changed from: protected */
        public EdgeEffect a(RecyclerView recyclerView, int i) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class f {

        /* renamed from: a  reason: collision with root package name */
        private b f987a = null;

        /* renamed from: b  reason: collision with root package name */
        private ArrayList<a> f988b = new ArrayList<>();

        /* renamed from: c  reason: collision with root package name */
        private long f989c = 120;

        /* renamed from: d  reason: collision with root package name */
        private long f990d = 120;
        private long e = 250;
        private long f = 250;

        public interface a {
            void a();
        }

        /* access modifiers changed from: package-private */
        public interface b {
            void a(v vVar);
        }

        public static class c {

            /* renamed from: a  reason: collision with root package name */
            public int f991a;

            /* renamed from: b  reason: collision with root package name */
            public int f992b;

            /* renamed from: c  reason: collision with root package name */
            public int f993c;

            /* renamed from: d  reason: collision with root package name */
            public int f994d;

            public c a(v vVar) {
                a(vVar, 0);
                return this;
            }

            public c a(v vVar, int i) {
                View view = vVar.itemView;
                this.f991a = view.getLeft();
                this.f992b = view.getTop();
                this.f993c = view.getRight();
                this.f994d = view.getBottom();
                return this;
            }
        }

        static int a(v vVar) {
            int i = vVar.mFlags & 14;
            if (vVar.isInvalid()) {
                return 4;
            }
            if ((i & 4) != 0) {
                return i;
            }
            int oldPosition = vVar.getOldPosition();
            int adapterPosition = vVar.getAdapterPosition();
            return (oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition) ? i : i | 2048;
        }

        public c a(s sVar, v vVar) {
            c h = h();
            h.a(vVar);
            return h;
        }

        public c a(s sVar, v vVar, int i, List<Object> list) {
            c h = h();
            h.a(vVar);
            return h;
        }

        public final void a() {
            int size = this.f988b.size();
            for (int i = 0; i < size; i++) {
                this.f988b.get(i).a();
            }
            this.f988b.clear();
        }

        /* access modifiers changed from: package-private */
        public void a(b bVar) {
            this.f987a = bVar;
        }

        public abstract boolean a(v vVar, c cVar, c cVar2);

        public abstract boolean a(v vVar, v vVar2, c cVar, c cVar2);

        public boolean a(v vVar, List<Object> list) {
            return b(vVar);
        }

        public abstract void b();

        public abstract boolean b(v vVar);

        public abstract boolean b(v vVar, c cVar, c cVar2);

        public long c() {
            return this.f989c;
        }

        public final void c(v vVar) {
            e(vVar);
            b bVar = this.f987a;
            if (bVar != null) {
                bVar.a(vVar);
            }
        }

        public abstract boolean c(v vVar, c cVar, c cVar2);

        public long d() {
            return this.f;
        }

        public abstract void d(v vVar);

        public long e() {
            return this.e;
        }

        public void e(v vVar) {
        }

        public long f() {
            return this.f990d;
        }

        public abstract boolean g();

        public c h() {
            return new c();
        }

        public abstract void i();
    }

    private class g implements f.b {
        g() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.f.b
        public void a(v vVar) {
            vVar.setIsRecyclable(true);
            if (vVar.mShadowedHolder != null && vVar.mShadowingHolder == null) {
                vVar.mShadowedHolder = null;
            }
            vVar.mShadowingHolder = null;
            if (!vVar.shouldBeKeptAsChild() && !RecyclerView.this.m(vVar.itemView) && vVar.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(vVar.itemView, false);
            }
        }
    }

    public static abstract class h {
        @Deprecated
        public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, s sVar) {
            getItemOffsets(rect, ((LayoutParams) view.getLayoutParams()).a(), recyclerView);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, s sVar) {
            onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, s sVar) {
            onDrawOver(canvas, recyclerView);
        }
    }

    public static abstract class i {
        boolean mAutoMeasure = false;
        C0097b mChildHelper;
        private int mHeight;
        private int mHeightMode;
        N mHorizontalBoundCheck = new N(this.mHorizontalBoundCheckCallback);
        private final N.b mHorizontalBoundCheckCallback = new E(this);
        boolean mIsAttachedToWindow = false;
        private boolean mItemPrefetchEnabled = true;
        private boolean mMeasurementCacheEnabled = true;
        int mPrefetchMaxCountObserved;
        boolean mPrefetchMaxObservedInInitialPrefetch;
        RecyclerView mRecyclerView;
        boolean mRequestedSimpleAnimations = false;
        r mSmoothScroller;
        N mVerticalBoundCheck = new N(this.mVerticalBoundCheckCallback);
        private final N.b mVerticalBoundCheckCallback = new F(this);
        private int mWidth;
        private int mWidthMode;

        public interface a {
            void a(int i, int i2);
        }

        public static class b {

            /* renamed from: a  reason: collision with root package name */
            public int f996a;

            /* renamed from: b  reason: collision with root package name */
            public int f997b;

            /* renamed from: c  reason: collision with root package name */
            public boolean f998c;

            /* renamed from: d  reason: collision with root package name */
            public boolean f999d;
        }

        private void addViewInt(View view, int i, boolean z) {
            v i2 = RecyclerView.i(view);
            if (z || i2.isRemoved()) {
                this.mRecyclerView.p.a(i2);
            } else {
                this.mRecyclerView.p.g(i2);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (i2.wasReturnedFromScrap() || i2.isScrap()) {
                if (i2.isScrap()) {
                    i2.unScrap();
                } else {
                    i2.clearReturnedFromScrapFlag();
                }
                this.mChildHelper.a(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.mRecyclerView) {
                int b2 = this.mChildHelper.b(view);
                if (i == -1) {
                    i = this.mChildHelper.a();
                }
                if (b2 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.mRecyclerView.indexOfChild(view) + this.mRecyclerView.i());
                } else if (b2 != i) {
                    this.mRecyclerView.w.moveView(b2, i);
                }
            } else {
                this.mChildHelper.a(view, i, false);
                layoutParams.f984c = true;
                r rVar = this.mSmoothScroller;
                if (rVar != null && rVar.e()) {
                    this.mSmoothScroller.b(view);
                }
            }
            if (layoutParams.f985d) {
                i2.itemView.invalidate();
                layoutParams.f985d = false;
            }
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i2, i3) : size : Math.min(size, Math.max(i2, i3));
        }

        private void detachViewInternal(int i, View view) {
            this.mChildHelper.a(i);
        }

        public static int getChildMeasureSpec(int i, int i2, int i3, int i4, boolean z) {
            int i5;
            int i6 = i - i3;
            int i7 = 0;
            int max = Math.max(0, i6);
            if (z) {
                if (i4 < 0) {
                    if (i4 == -1) {
                        if (i2 == Integer.MIN_VALUE || (i2 != 0 && i2 == 1073741824)) {
                            i5 = max;
                        } else {
                            i2 = 0;
                            i5 = 0;
                        }
                        i7 = i2;
                        max = i5;
                        return View.MeasureSpec.makeMeasureSpec(max, i7);
                    }
                    max = 0;
                    return View.MeasureSpec.makeMeasureSpec(max, i7);
                }
            } else if (i4 < 0) {
                if (i4 == -1) {
                    i7 = i2;
                } else {
                    if (i4 == -2) {
                        if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                            i7 = LinearLayoutManager.INVALID_OFFSET;
                        }
                    }
                    max = 0;
                }
                return View.MeasureSpec.makeMeasureSpec(max, i7);
            }
            max = i4;
            i7 = 1073741824;
            return View.MeasureSpec.makeMeasureSpec(max, i7);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
            if (r3 >= 0) goto L_0x0011;
         */
        @java.lang.Deprecated
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getChildMeasureSpec(int r1, int r2, int r3, boolean r4) {
            /*
                int r1 = r1 - r2
                r2 = 0
                int r1 = java.lang.Math.max(r2, r1)
                r0 = 1073741824(0x40000000, float:2.0)
                if (r4 == 0) goto L_0x000f
                if (r3 < 0) goto L_0x000d
                goto L_0x0011
            L_0x000d:
                r1 = 0
                goto L_0x001e
            L_0x000f:
                if (r3 < 0) goto L_0x0015
            L_0x0011:
                r1 = r3
            L_0x0012:
                r2 = 1073741824(0x40000000, float:2.0)
                goto L_0x001e
            L_0x0015:
                r4 = -1
                if (r3 != r4) goto L_0x0019
                goto L_0x0012
            L_0x0019:
                r4 = -2
                if (r3 != r4) goto L_0x000d
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
            L_0x001e:
                int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.i.getChildMeasureSpec(int, int, int, boolean):int");
        }

        private int[] getChildRectangleOnScreenScrollAmount(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            int[] iArr = new int[2];
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width2 = rect.width() + left;
            int height2 = rect.height() + top;
            int i = left - paddingLeft;
            int min = Math.min(0, i);
            int i2 = top - paddingTop;
            int min2 = Math.min(0, i2);
            int i3 = width2 - width;
            int max = Math.max(0, i3);
            int max2 = Math.max(0, height2 - height);
            if (getLayoutDirection() != 1) {
                if (min == 0) {
                    min = Math.min(i, max);
                }
                max = min;
            } else if (max == 0) {
                max = Math.max(min, i3);
            }
            if (min2 == 0) {
                min2 = Math.min(i2, max2);
            }
            iArr[0] = max;
            iArr[1] = min2;
            return iArr;
        }

        public static b getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            b bVar = new b();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b.k.b.RecyclerView, i, i2);
            bVar.f996a = obtainStyledAttributes.getInt(b.k.b.RecyclerView_android_orientation, 1);
            bVar.f997b = obtainStyledAttributes.getInt(b.k.b.RecyclerView_spanCount, 1);
            bVar.f998c = obtainStyledAttributes.getBoolean(b.k.b.RecyclerView_reverseLayout, false);
            bVar.f999d = obtainStyledAttributes.getBoolean(b.k.b.RecyclerView_stackFromEnd, false);
            obtainStyledAttributes.recycle();
            return bVar;
        }

        private boolean isFocusedChildVisibleAfterScrolling(RecyclerView recyclerView, int i, int i2) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int height = getHeight() - getPaddingBottom();
            Rect rect = this.mRecyclerView.s;
            getDecoratedBoundsWithMargins(focusedChild, rect);
            return rect.left - i < width && rect.right - i > paddingLeft && rect.top - i2 < height && rect.bottom - i2 > paddingTop;
        }

        private static boolean isMeasurementUpToDate(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i;
            }
            return true;
        }

        private void scrapOrRecycleView(o oVar, int i, View view) {
            v i2 = RecyclerView.i(view);
            if (!i2.shouldIgnore()) {
                if (!i2.isInvalid() || i2.isRemoved() || this.mRecyclerView.v.hasStableIds()) {
                    detachViewAt(i);
                    oVar.c(view);
                    this.mRecyclerView.p.d(i2);
                    return;
                }
                removeViewAt(i);
                oVar.b(i2);
            }
        }

        public void addDisappearingView(View view) {
            addDisappearingView(view, -1);
        }

        public void addDisappearingView(View view, int i) {
            addViewInt(view, i, true);
        }

        public void addView(View view) {
            addView(view, -1);
        }

        public void addView(View view, int i) {
            addViewInt(view, i, false);
        }

        public void assertInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.a(str);
            }
        }

        public void assertNotInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.b(str);
            }
        }

        public void attachView(View view) {
            attachView(view, -1);
        }

        public void attachView(View view, int i) {
            attachView(view, i, (LayoutParams) view.getLayoutParams());
        }

        public void attachView(View view, int i, LayoutParams layoutParams) {
            v i2 = RecyclerView.i(view);
            if (i2.isRemoved()) {
                this.mRecyclerView.p.a(i2);
            } else {
                this.mRecyclerView.p.g(i2);
            }
            this.mChildHelper.a(view, i, layoutParams, i2.isRemoved());
        }

        public void calculateItemDecorationsForChild(View view, Rect rect) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.j(view));
            }
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, s sVar, a aVar) {
        }

        public void collectInitialPrefetchPositions(int i, a aVar) {
        }

        public int computeHorizontalScrollExtent(s sVar) {
            return 0;
        }

        public int computeHorizontalScrollOffset(s sVar) {
            return 0;
        }

        public int computeHorizontalScrollRange(s sVar) {
            return 0;
        }

        public int computeVerticalScrollExtent(s sVar) {
            return 0;
        }

        public int computeVerticalScrollOffset(s sVar) {
            return 0;
        }

        public int computeVerticalScrollRange(s sVar) {
            return 0;
        }

        public void detachAndScrapAttachedViews(o oVar) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                scrapOrRecycleView(oVar, childCount, getChildAt(childCount));
            }
        }

        public void detachAndScrapView(View view, o oVar) {
            scrapOrRecycleView(oVar, this.mChildHelper.b(view), view);
        }

        public void detachAndScrapViewAt(int i, o oVar) {
            scrapOrRecycleView(oVar, i, getChildAt(i));
        }

        public void detachView(View view) {
            int b2 = this.mChildHelper.b(view);
            if (b2 >= 0) {
                detachViewInternal(b2, view);
            }
        }

        public void detachViewAt(int i) {
            detachViewInternal(i, getChildAt(i));
        }

        /* access modifiers changed from: package-private */
        public void dispatchAttachedToWindow(RecyclerView recyclerView) {
            this.mIsAttachedToWindow = true;
            onAttachedToWindow(recyclerView);
        }

        /* access modifiers changed from: package-private */
        public void dispatchDetachedFromWindow(RecyclerView recyclerView, o oVar) {
            this.mIsAttachedToWindow = false;
            onDetachedFromWindow(recyclerView, oVar);
        }

        public void endAnimation(View view) {
            f fVar = this.mRecyclerView.W;
            if (fVar != null) {
                fVar.d(RecyclerView.i(view));
            }
        }

        public View findContainingItemView(View view) {
            View c2;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (c2 = recyclerView.c(view)) == null || this.mChildHelper.c(c2)) {
                return null;
            }
            return c2;
        }

        public View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                v i3 = RecyclerView.i(childAt);
                if (i3 != null && i3.getLayoutPosition() == i && !i3.shouldIgnore() && (this.mRecyclerView.ra.d() || !i3.isRemoved())) {
                    return childAt;
                }
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view) {
            return ((LayoutParams) view.getLayoutParams()).f983b.bottom;
        }

        public View getChildAt(int i) {
            C0097b bVar = this.mChildHelper;
            if (bVar != null) {
                return bVar.c(i);
            }
            return null;
        }

        public int getChildCount() {
            C0097b bVar = this.mChildHelper;
            if (bVar != null) {
                return bVar.a();
            }
            return 0;
        }

        public boolean getClipToPadding() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.q;
        }

        public int getColumnCountForAccessibility(o oVar, s sVar) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || recyclerView.v == null || !canScrollHorizontally()) {
                return 1;
            }
            return this.mRecyclerView.v.getItemCount();
        }

        public int getDecoratedBottom(View view) {
            return view.getBottom() + getBottomDecorationHeight(view);
        }

        public void getDecoratedBoundsWithMargins(View view, Rect rect) {
            RecyclerView.a(view, rect);
        }

        public int getDecoratedLeft(View view) {
            return view.getLeft() - getLeftDecorationWidth(view);
        }

        public int getDecoratedMeasuredHeight(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).f983b;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).f983b;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view) {
            return view.getRight() + getRightDecorationWidth(view);
        }

        public int getDecoratedTop(View view) {
            return view.getTop() - getTopDecorationHeight(view);
        }

        public View getFocusedChild() {
            View focusedChild;
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.mChildHelper.c(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getHeightMode() {
            return this.mHeightMode;
        }

        public int getItemCount() {
            RecyclerView recyclerView = this.mRecyclerView;
            a adapter = recyclerView != null ? recyclerView.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public int getItemViewType(View view) {
            return RecyclerView.i(view).getItemViewType();
        }

        public int getLayoutDirection() {
            return b.e.g.t.i(this.mRecyclerView);
        }

        public int getLeftDecorationWidth(View view) {
            return ((LayoutParams) view.getLayoutParams()).f983b.left;
        }

        public int getMinimumHeight() {
            return b.e.g.t.j(this.mRecyclerView);
        }

        public int getMinimumWidth() {
            return b.e.g.t.k(this.mRecyclerView);
        }

        public int getPaddingBottom() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingEnd() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return b.e.g.t.l(recyclerView);
            }
            return 0;
        }

        public int getPaddingLeft() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingRight() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingStart() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return b.e.g.t.m(recyclerView);
            }
            return 0;
        }

        public int getPaddingTop() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPosition(View view) {
            return ((LayoutParams) view.getLayoutParams()).a();
        }

        public int getRightDecorationWidth(View view) {
            return ((LayoutParams) view.getLayoutParams()).f983b.right;
        }

        public int getRowCountForAccessibility(o oVar, s sVar) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || recyclerView.v == null || !canScrollVertically()) {
                return 1;
            }
            return this.mRecyclerView.v.getItemCount();
        }

        public int getSelectionModeForAccessibility(o oVar, s sVar) {
            return 0;
        }

        public int getTopDecorationHeight(View view) {
            return ((LayoutParams) view.getLayoutParams()).f983b.top;
        }

        public void getTransformedBoundingBox(View view, boolean z, Rect rect) {
            Matrix matrix;
            if (z) {
                Rect rect2 = ((LayoutParams) view.getLayoutParams()).f983b;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (!(this.mRecyclerView == null || (matrix = view.getMatrix()) == null || matrix.isIdentity())) {
                RectF rectF = this.mRecyclerView.u;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getWidthMode() {
            return this.mWidthMode;
        }

        /* access modifiers changed from: package-private */
        public boolean hasFlexibleChildInBothOrientations() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasFocus() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.hasFocus();
        }

        public void ignoreView(View view) {
            ViewParent parent = view.getParent();
            RecyclerView recyclerView = this.mRecyclerView;
            if (parent != recyclerView || recyclerView.indexOfChild(view) == -1) {
                throw new IllegalArgumentException("View should be fully attached to be ignored" + this.mRecyclerView.i());
            }
            v i = RecyclerView.i(view);
            i.addFlags(128);
            this.mRecyclerView.p.h(i);
        }

        public boolean isAttachedToWindow() {
            return this.mIsAttachedToWindow;
        }

        public boolean isAutoMeasureEnabled() {
            return this.mAutoMeasure;
        }

        public boolean isFocused() {
            RecyclerView recyclerView = this.mRecyclerView;
            return recyclerView != null && recyclerView.isFocused();
        }

        public final boolean isItemPrefetchEnabled() {
            return this.mItemPrefetchEnabled;
        }

        public boolean isLayoutHierarchical(o oVar, s sVar) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.mMeasurementCacheEnabled;
        }

        public boolean isSmoothScrolling() {
            r rVar = this.mSmoothScroller;
            return rVar != null && rVar.e();
        }

        public boolean isViewPartiallyVisible(View view, boolean z, boolean z2) {
            boolean z3 = this.mHorizontalBoundCheck.a(view, 24579) && this.mVerticalBoundCheck.a(view, 24579);
            return z ? z3 : !z3;
        }

        public void layoutDecorated(View view, int i, int i2, int i3, int i4) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).f983b;
            view.layout(i + rect.left, i2 + rect.top, i3 - rect.right, i4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(View view, int i, int i2, int i3, int i4) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = layoutParams.f983b;
            view.layout(i + rect.left + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i2 + rect.top + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (i3 - rect.right) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, (i4 - rect.bottom) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }

        public void measureChild(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect j = this.mRecyclerView.j(view);
            int i3 = i + j.left + j.right;
            int i4 = i2 + j.top + j.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + i3, ((ViewGroup.MarginLayoutParams) layoutParams).width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + i4, ((ViewGroup.MarginLayoutParams) layoutParams).height, canScrollVertically());
            if (shouldMeasureChild(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        public void measureChildWithMargins(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect j = this.mRecyclerView.j(view);
            int i3 = i + j.left + j.right;
            int i4 = i2 + j.top + j.bottom;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + i3, ((ViewGroup.MarginLayoutParams) layoutParams).width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + i4, ((ViewGroup.MarginLayoutParams) layoutParams).height, canScrollVertically());
            if (shouldMeasureChild(view, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        public void moveView(int i, int i2) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                detachViewAt(i);
                attachView(childAt, i2);
                return;
            }
            throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i + this.mRecyclerView.toString());
        }

        public void offsetChildrenHorizontal(int i) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.e(i);
            }
        }

        public void offsetChildrenVertical(int i) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.f(i);
            }
        }

        public void onAdapterChanged(a aVar, a aVar2) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        public void onDetachedFromWindow(RecyclerView recyclerView, o oVar) {
            onDetachedFromWindow(recyclerView);
        }

        public View onFocusSearchFailed(View view, int i, o oVar, s sVar) {
            return null;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.mRecyclerView;
            onInitializeAccessibilityEvent(recyclerView.l, recyclerView.ra, accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(o oVar, s sVar, AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null && accessibilityEvent != null) {
                boolean z = true;
                if (!recyclerView.canScrollVertically(1) && !this.mRecyclerView.canScrollVertically(-1) && !this.mRecyclerView.canScrollHorizontally(-1) && !this.mRecyclerView.canScrollHorizontally(1)) {
                    z = false;
                }
                accessibilityEvent.setScrollable(z);
                a aVar = this.mRecyclerView.v;
                if (aVar != null) {
                    accessibilityEvent.setItemCount(aVar.getItemCount());
                }
            }
        }

        public void onInitializeAccessibilityNodeInfo(o oVar, s sVar, b.e.g.a.b bVar) {
            if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
                bVar.a(8192);
                bVar.h(true);
            }
            if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
                bVar.a(4096);
                bVar.h(true);
            }
            bVar.a(b.C0025b.a(getRowCountForAccessibility(oVar, sVar), getColumnCountForAccessibility(oVar, sVar), isLayoutHierarchical(oVar, sVar), getSelectionModeForAccessibility(oVar, sVar)));
        }

        /* access modifiers changed from: package-private */
        public void onInitializeAccessibilityNodeInfo(b.e.g.a.b bVar) {
            RecyclerView recyclerView = this.mRecyclerView;
            onInitializeAccessibilityNodeInfo(recyclerView.l, recyclerView.ra, bVar);
        }

        /* access modifiers changed from: package-private */
        public void onInitializeAccessibilityNodeInfoForItem(View view, b.e.g.a.b bVar) {
            v i = RecyclerView.i(view);
            if (i != null && !i.isRemoved() && !this.mChildHelper.c(i.itemView)) {
                RecyclerView recyclerView = this.mRecyclerView;
                onInitializeAccessibilityNodeInfoForItem(recyclerView.l, recyclerView.ra, view, bVar);
            }
        }

        public void onInitializeAccessibilityNodeInfoForItem(o oVar, s sVar, View view, b.e.g.a.b bVar) {
            bVar.b(b.c.a(canScrollVertically() ? getPosition(view) : 0, 1, canScrollHorizontally() ? getPosition(view) : 0, 1, false, false));
        }

        public View onInterceptFocusSearch(View view, int i) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
            onItemsUpdated(recyclerView, i, i2);
        }

        public void onLayoutChildren(o oVar, s sVar) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(s sVar) {
        }

        public void onMeasure(o oVar, s sVar, int i, int i2) {
            this.mRecyclerView.c(i, i2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
            return isSmoothScrolling() || recyclerView.n();
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, s sVar, View view, View view2) {
            return onRequestChildFocus(recyclerView, view, view2);
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int i) {
        }

        /* access modifiers changed from: package-private */
        public void onSmoothScrollerStopped(r rVar) {
            if (this.mSmoothScroller == rVar) {
                this.mSmoothScroller = null;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean performAccessibilityAction(int i, Bundle bundle) {
            RecyclerView recyclerView = this.mRecyclerView;
            return performAccessibilityAction(recyclerView.l, recyclerView.ra, i, bundle);
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x0070 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean performAccessibilityAction(androidx.recyclerview.widget.RecyclerView.o r2, androidx.recyclerview.widget.RecyclerView.s r3, int r4, android.os.Bundle r5) {
            /*
            // Method dump skipped, instructions count: 121
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.i.performAccessibilityAction(androidx.recyclerview.widget.RecyclerView$o, androidx.recyclerview.widget.RecyclerView$s, int, android.os.Bundle):boolean");
        }

        /* access modifiers changed from: package-private */
        public boolean performAccessibilityActionForItem(View view, int i, Bundle bundle) {
            RecyclerView recyclerView = this.mRecyclerView;
            return performAccessibilityActionForItem(recyclerView.l, recyclerView.ra, view, i, bundle);
        }

        public boolean performAccessibilityActionForItem(o oVar, s sVar, View view, int i, Bundle bundle) {
            return false;
        }

        public void postOnAnimation(Runnable runnable) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                b.e.g.t.a(recyclerView, runnable);
            }
        }

        public void removeAllViews() {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                this.mChildHelper.e(childCount);
            }
        }

        public void removeAndRecycleAllViews(o oVar) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!RecyclerView.i(getChildAt(childCount)).shouldIgnore()) {
                    removeAndRecycleViewAt(childCount, oVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void removeAndRecycleScrapInt(o oVar) {
            int e = oVar.e();
            for (int i = e - 1; i >= 0; i--) {
                View c2 = oVar.c(i);
                v i2 = RecyclerView.i(c2);
                if (!i2.shouldIgnore()) {
                    i2.setIsRecyclable(false);
                    if (i2.isTmpDetached()) {
                        this.mRecyclerView.removeDetachedView(c2, false);
                    }
                    f fVar = this.mRecyclerView.W;
                    if (fVar != null) {
                        fVar.d(i2);
                    }
                    i2.setIsRecyclable(true);
                    oVar.a(c2);
                }
            }
            oVar.c();
            if (e > 0) {
                this.mRecyclerView.invalidate();
            }
        }

        public void removeAndRecycleView(View view, o oVar) {
            removeView(view);
            oVar.b(view);
        }

        public void removeAndRecycleViewAt(int i, o oVar) {
            View childAt = getChildAt(i);
            removeViewAt(i);
            oVar.b(childAt);
        }

        public boolean removeCallbacks(Runnable runnable) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeDetachedView(View view) {
            this.mRecyclerView.removeDetachedView(view, false);
        }

        public void removeView(View view) {
            this.mChildHelper.d(view);
        }

        public void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                this.mChildHelper.e(i);
            }
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return requestChildRectangleOnScreen(recyclerView, view, rect, z, false);
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            int[] childRectangleOnScreenScrollAmount = getChildRectangleOnScreenScrollAmount(recyclerView, view, rect, z);
            int i = childRectangleOnScreenScrollAmount[0];
            int i2 = childRectangleOnScreenScrollAmount[1];
            if ((z2 && !isFocusedChildVisibleAfterScrolling(recyclerView, i, i2)) || (i == 0 && i2 == 0)) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(i, i2);
            } else {
                recyclerView.i(i, i2);
            }
            return true;
        }

        public void requestLayout() {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.mRequestedSimpleAnimations = true;
        }

        public int scrollHorizontallyBy(int i, o oVar, s sVar) {
            return 0;
        }

        public void scrollToPosition(int i) {
        }

        public int scrollVerticallyBy(int i, o oVar, s sVar) {
            return 0;
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean z) {
            this.mAutoMeasure = z;
        }

        /* access modifiers changed from: package-private */
        public void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
            setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        public final void setItemPrefetchEnabled(boolean z) {
            if (z != this.mItemPrefetchEnabled) {
                this.mItemPrefetchEnabled = z;
                this.mPrefetchMaxCountObserved = 0;
                RecyclerView recyclerView = this.mRecyclerView;
                if (recyclerView != null) {
                    recyclerView.l.j();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setMeasureSpecs(int i, int i2) {
            this.mWidth = View.MeasureSpec.getSize(i);
            this.mWidthMode = View.MeasureSpec.getMode(i);
            if (this.mWidthMode == 0 && !RecyclerView.f981d) {
                this.mWidth = 0;
            }
            this.mHeight = View.MeasureSpec.getSize(i2);
            this.mHeightMode = View.MeasureSpec.getMode(i2);
            if (this.mHeightMode == 0 && !RecyclerView.f981d) {
                this.mHeight = 0;
            }
        }

        public void setMeasuredDimension(int i, int i2) {
            this.mRecyclerView.setMeasuredDimension(i, i2);
        }

        public void setMeasuredDimension(Rect rect, int i, int i2) {
            setMeasuredDimension(chooseSize(i, rect.width() + getPaddingLeft() + getPaddingRight(), getMinimumWidth()), chooseSize(i2, rect.height() + getPaddingTop() + getPaddingBottom(), getMinimumHeight()));
        }

        /* access modifiers changed from: package-private */
        public void setMeasuredDimensionFromChildren(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.mRecyclerView.c(i, i2);
                return;
            }
            int i3 = Integer.MAX_VALUE;
            int i4 = Integer.MAX_VALUE;
            int i5 = LinearLayoutManager.INVALID_OFFSET;
            int i6 = LinearLayoutManager.INVALID_OFFSET;
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                Rect rect = this.mRecyclerView.s;
                getDecoratedBoundsWithMargins(childAt, rect);
                int i8 = rect.left;
                if (i8 < i3) {
                    i3 = i8;
                }
                int i9 = rect.right;
                if (i9 > i5) {
                    i5 = i9;
                }
                int i10 = rect.top;
                if (i10 < i4) {
                    i4 = i10;
                }
                int i11 = rect.bottom;
                if (i11 > i6) {
                    i6 = i11;
                }
            }
            this.mRecyclerView.s.set(i3, i4, i5, i6);
            setMeasuredDimension(this.mRecyclerView.s, i, i2);
        }

        public void setMeasurementCacheEnabled(boolean z) {
            this.mMeasurementCacheEnabled = z;
        }

        /* access modifiers changed from: package-private */
        public void setRecyclerView(RecyclerView recyclerView) {
            int i;
            if (recyclerView == null) {
                this.mRecyclerView = null;
                this.mChildHelper = null;
                i = 0;
                this.mWidth = 0;
            } else {
                this.mRecyclerView = recyclerView;
                this.mChildHelper = recyclerView.o;
                this.mWidth = recyclerView.getWidth();
                i = recyclerView.getHeight();
            }
            this.mHeight = i;
            this.mWidthMode = 1073741824;
            this.mHeightMode = 1073741824;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(view.getWidth(), i, ((ViewGroup.MarginLayoutParams) layoutParams).width) || !isMeasurementUpToDate(view.getHeight(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        }

        /* access modifiers changed from: package-private */
        public boolean shouldMeasureTwice() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldReMeasureChild(View view, int i, int i2, LayoutParams layoutParams) {
            return !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(view.getMeasuredWidth(), i, ((ViewGroup.MarginLayoutParams) layoutParams).width) || !isMeasurementUpToDate(view.getMeasuredHeight(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, s sVar, int i) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(r rVar) {
            r rVar2 = this.mSmoothScroller;
            if (!(rVar2 == null || rVar == rVar2 || !rVar2.e())) {
                this.mSmoothScroller.h();
            }
            this.mSmoothScroller = rVar;
            this.mSmoothScroller.a(this.mRecyclerView, this);
        }

        public void stopIgnoringView(View view) {
            v i = RecyclerView.i(view);
            i.stopIgnoring();
            i.resetInternal();
            i.addFlags(4);
        }

        /* access modifiers changed from: package-private */
        public void stopSmoothScroller() {
            r rVar = this.mSmoothScroller;
            if (rVar != null) {
                rVar.h();
            }
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

    public interface j {
        void a(View view);

        void b(View view);
    }

    public static abstract class k {
        public abstract boolean a(int i, int i2);
    }

    public interface l {
        void a(RecyclerView recyclerView, MotionEvent motionEvent);

        void a(boolean z);

        boolean b(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public static abstract class m {
        public void a(RecyclerView recyclerView, int i) {
        }

        public void a(RecyclerView recyclerView, int i, int i2) {
        }
    }

    public static class n {

        /* renamed from: a  reason: collision with root package name */
        SparseArray<a> f1000a = new SparseArray<>();

        /* renamed from: b  reason: collision with root package name */
        private int f1001b = 0;

        /* access modifiers changed from: package-private */
        public static class a {

            /* renamed from: a  reason: collision with root package name */
            final ArrayList<v> f1002a = new ArrayList<>();

            /* renamed from: b  reason: collision with root package name */
            int f1003b = 5;

            /* renamed from: c  reason: collision with root package name */
            long f1004c = 0;

            /* renamed from: d  reason: collision with root package name */
            long f1005d = 0;

            a() {
            }
        }

        private a b(int i) {
            a aVar = this.f1000a.get(i);
            if (aVar != null) {
                return aVar;
            }
            a aVar2 = new a();
            this.f1000a.put(i, aVar2);
            return aVar2;
        }

        /* access modifiers changed from: package-private */
        public long a(long j, long j2) {
            return j == 0 ? j2 : ((j / 4) * 3) + (j2 / 4);
        }

        public v a(int i) {
            a aVar = this.f1000a.get(i);
            if (aVar == null || aVar.f1002a.isEmpty()) {
                return null;
            }
            ArrayList<v> arrayList = aVar.f1002a;
            return arrayList.remove(arrayList.size() - 1);
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.f1001b++;
        }

        /* access modifiers changed from: package-private */
        public void a(int i, long j) {
            a b2 = b(i);
            b2.f1005d = a(b2.f1005d, j);
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar, a aVar2, boolean z) {
            if (aVar != null) {
                c();
            }
            if (!z && this.f1001b == 0) {
                b();
            }
            if (aVar2 != null) {
                a();
            }
        }

        public void a(v vVar) {
            int itemViewType = vVar.getItemViewType();
            ArrayList<v> arrayList = b(itemViewType).f1002a;
            if (this.f1000a.get(itemViewType).f1003b > arrayList.size()) {
                vVar.resetInternal();
                arrayList.add(vVar);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i, long j, long j2) {
            long j3 = b(i).f1005d;
            return j3 == 0 || j + j3 < j2;
        }

        public void b() {
            for (int i = 0; i < this.f1000a.size(); i++) {
                this.f1000a.valueAt(i).f1002a.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i, long j) {
            a b2 = b(i);
            b2.f1004c = a(b2.f1004c, j);
        }

        /* access modifiers changed from: package-private */
        public boolean b(int i, long j, long j2) {
            long j3 = b(i).f1004c;
            return j3 == 0 || j + j3 < j2;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.f1001b--;
        }
    }

    public final class o {

        /* renamed from: a  reason: collision with root package name */
        final ArrayList<v> f1006a = new ArrayList<>();

        /* renamed from: b  reason: collision with root package name */
        ArrayList<v> f1007b = null;

        /* renamed from: c  reason: collision with root package name */
        final ArrayList<v> f1008c = new ArrayList<>();

        /* renamed from: d  reason: collision with root package name */
        private final List<v> f1009d = Collections.unmodifiableList(this.f1006a);
        private int e = 2;
        int f = 2;
        n g;
        private t h;

        public o() {
        }

        private void a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                    return;
                }
                int visibility = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(visibility);
            }
        }

        private boolean a(v vVar, int i2, int i3, long j) {
            vVar.mOwnerRecyclerView = RecyclerView.this;
            int itemViewType = vVar.getItemViewType();
            long nanoTime = RecyclerView.this.getNanoTime();
            if (j != Long.MAX_VALUE && !this.g.a(itemViewType, nanoTime, j)) {
                return false;
            }
            RecyclerView.this.v.bindViewHolder(vVar, i2);
            this.g.a(vVar.getItemViewType(), RecyclerView.this.getNanoTime() - nanoTime);
            e(vVar);
            if (!RecyclerView.this.ra.d()) {
                return true;
            }
            vVar.mPreLayoutPosition = i3;
            return true;
        }

        private void e(v vVar) {
            if (RecyclerView.this.m()) {
                View view = vVar.itemView;
                if (b.e.g.t.g(view) == 0) {
                    b.e.g.t.c(view, 1);
                }
                if (!b.e.g.t.r(view)) {
                    vVar.addFlags(16384);
                    b.e.g.t.a(view, RecyclerView.this.ya.b());
                }
            }
        }

        private void f(v vVar) {
            View view = vVar.itemView;
            if (view instanceof ViewGroup) {
                a((ViewGroup) view, false);
            }
        }

        public int a(int i2) {
            if (i2 >= 0 && i2 < RecyclerView.this.ra.a()) {
                return !RecyclerView.this.ra.d() ? i2 : RecyclerView.this.n.b(i2);
            }
            throw new IndexOutOfBoundsException("invalid position " + i2 + ". State " + "item count is " + RecyclerView.this.ra.a() + RecyclerView.this.i());
        }

        /* access modifiers changed from: package-private */
        public v a(int i2, boolean z) {
            View b2;
            int size = this.f1006a.size();
            for (int i3 = 0; i3 < size; i3++) {
                v vVar = this.f1006a.get(i3);
                if (!vVar.wasReturnedFromScrap() && vVar.getLayoutPosition() == i2 && !vVar.isInvalid() && (RecyclerView.this.ra.h || !vVar.isRemoved())) {
                    vVar.addFlags(32);
                    return vVar;
                }
            }
            if (z || (b2 = RecyclerView.this.o.b(i2)) == null) {
                int size2 = this.f1008c.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    v vVar2 = this.f1008c.get(i4);
                    if (!vVar2.isInvalid() && vVar2.getLayoutPosition() == i2) {
                        if (!z) {
                            this.f1008c.remove(i4);
                        }
                        return vVar2;
                    }
                }
                return null;
            }
            v i5 = RecyclerView.i(b2);
            RecyclerView.this.o.f(b2);
            int b3 = RecyclerView.this.o.b(b2);
            if (b3 != -1) {
                RecyclerView.this.o.a(b3);
                c(b2);
                i5.addFlags(8224);
                return i5;
            }
            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + i5 + RecyclerView.this.i());
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x0037  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x005c  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x005f  */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x01a6  */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x01c9  */
        /* JADX WARNING: Removed duplicated region for block: B:94:0x0202  */
        /* JADX WARNING: Removed duplicated region for block: B:96:0x0210  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public androidx.recyclerview.widget.RecyclerView.v a(int r17, boolean r18, long r19) {
            /*
            // Method dump skipped, instructions count: 614
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.o.a(int, boolean, long):androidx.recyclerview.widget.RecyclerView$v");
        }

        /* access modifiers changed from: package-private */
        public v a(long j, int i2, boolean z) {
            for (int size = this.f1006a.size() - 1; size >= 0; size--) {
                v vVar = this.f1006a.get(size);
                if (vVar.getItemId() == j && !vVar.wasReturnedFromScrap()) {
                    if (i2 == vVar.getItemViewType()) {
                        vVar.addFlags(32);
                        if (vVar.isRemoved() && !RecyclerView.this.ra.d()) {
                            vVar.setFlags(2, 14);
                        }
                        return vVar;
                    } else if (!z) {
                        this.f1006a.remove(size);
                        RecyclerView.this.removeDetachedView(vVar.itemView, false);
                        a(vVar.itemView);
                    }
                }
            }
            int size2 = this.f1008c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                v vVar2 = this.f1008c.get(size2);
                if (vVar2.getItemId() == j) {
                    if (i2 == vVar2.getItemViewType()) {
                        if (!z) {
                            this.f1008c.remove(size2);
                        }
                        return vVar2;
                    } else if (!z) {
                        e(size2);
                        return null;
                    }
                }
            }
        }

        public void a() {
            this.f1006a.clear();
            i();
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3) {
            int size = this.f1008c.size();
            for (int i4 = 0; i4 < size; i4++) {
                v vVar = this.f1008c.get(i4);
                if (vVar != null && vVar.mPosition >= i2) {
                    vVar.offsetPosition(i3, true);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3, boolean z) {
            int i4 = i2 + i3;
            for (int size = this.f1008c.size() - 1; size >= 0; size--) {
                v vVar = this.f1008c.get(size);
                if (vVar != null) {
                    int i5 = vVar.mPosition;
                    if (i5 >= i4) {
                        vVar.offsetPosition(-i3, z);
                    } else if (i5 >= i2) {
                        vVar.addFlags(8);
                        e(size);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(View view) {
            v i2 = RecyclerView.i(view);
            i2.mScrapContainer = null;
            i2.mInChangeScrap = false;
            i2.clearReturnedFromScrapFlag();
            b(i2);
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar, a aVar2, boolean z) {
            a();
            d().a(aVar, aVar2, z);
        }

        /* access modifiers changed from: package-private */
        public void a(n nVar) {
            n nVar2 = this.g;
            if (nVar2 != null) {
                nVar2.c();
            }
            this.g = nVar;
            if (this.g != null && RecyclerView.this.getAdapter() != null) {
                this.g.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(t tVar) {
            this.h = tVar;
        }

        /* access modifiers changed from: package-private */
        public void a(v vVar) {
            p pVar = RecyclerView.this.x;
            if (pVar != null) {
                pVar.a(vVar);
            }
            a aVar = RecyclerView.this.v;
            if (aVar != null) {
                aVar.onViewRecycled(vVar);
            }
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.ra != null) {
                recyclerView.p.h(vVar);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(v vVar, boolean z) {
            RecyclerView.b(vVar);
            if (vVar.hasAnyOfTheFlags(16384)) {
                vVar.setFlags(0, 16384);
                b.e.g.t.a(vVar.itemView, (C0111a) null);
            }
            if (z) {
                a(vVar);
            }
            vVar.mOwnerRecyclerView = null;
            d().a(vVar);
        }

        /* access modifiers changed from: package-private */
        public View b(int i2, boolean z) {
            return a(i2, z, Long.MAX_VALUE).itemView;
        }

        /* access modifiers changed from: package-private */
        public v b(int i2) {
            int size;
            int b2;
            ArrayList<v> arrayList = this.f1007b;
            if (!(arrayList == null || (size = arrayList.size()) == 0)) {
                for (int i3 = 0; i3 < size; i3++) {
                    v vVar = this.f1007b.get(i3);
                    if (!vVar.wasReturnedFromScrap() && vVar.getLayoutPosition() == i2) {
                        vVar.addFlags(32);
                        return vVar;
                    }
                }
                if (RecyclerView.this.v.hasStableIds() && (b2 = RecyclerView.this.n.b(i2)) > 0 && b2 < RecyclerView.this.v.getItemCount()) {
                    long itemId = RecyclerView.this.v.getItemId(b2);
                    for (int i4 = 0; i4 < size; i4++) {
                        v vVar2 = this.f1007b.get(i4);
                        if (!vVar2.wasReturnedFromScrap() && vVar2.getItemId() == itemId) {
                            vVar2.addFlags(32);
                            return vVar2;
                        }
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            int size = this.f1008c.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.f1008c.get(i2).clearOldPosition();
            }
            int size2 = this.f1006a.size();
            for (int i3 = 0; i3 < size2; i3++) {
                this.f1006a.get(i3).clearOldPosition();
            }
            ArrayList<v> arrayList = this.f1007b;
            if (arrayList != null) {
                int size3 = arrayList.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    this.f1007b.get(i4).clearOldPosition();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            int i4;
            int i5;
            int i6;
            int i7;
            if (i2 < i3) {
                i6 = i2;
                i5 = i3;
                i4 = -1;
            } else {
                i5 = i2;
                i6 = i3;
                i4 = 1;
            }
            int size = this.f1008c.size();
            for (int i8 = 0; i8 < size; i8++) {
                v vVar = this.f1008c.get(i8);
                if (vVar != null && (i7 = vVar.mPosition) >= i6 && i7 <= i5) {
                    if (i7 == i2) {
                        vVar.offsetPosition(i3 - i2, false);
                    } else {
                        vVar.offsetPosition(i4, false);
                    }
                }
            }
        }

        public void b(View view) {
            v i2 = RecyclerView.i(view);
            if (i2.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (i2.isScrap()) {
                i2.unScrap();
            } else if (i2.wasReturnedFromScrap()) {
                i2.clearReturnedFromScrapFlag();
            }
            b(i2);
        }

        /* access modifiers changed from: package-private */
        public void b(v vVar) {
            boolean z;
            boolean z2 = false;
            if (vVar.isScrap() || vVar.itemView.getParent() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(vVar.isScrap());
                sb.append(" isAttached:");
                if (vVar.itemView.getParent() != null) {
                    z2 = true;
                }
                sb.append(z2);
                sb.append(RecyclerView.this.i());
                throw new IllegalArgumentException(sb.toString());
            } else if (vVar.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + vVar + RecyclerView.this.i());
            } else if (!vVar.shouldIgnore()) {
                boolean doesTransientStatePreventRecycling = vVar.doesTransientStatePreventRecycling();
                a aVar = RecyclerView.this.v;
                if ((aVar != null && doesTransientStatePreventRecycling && aVar.onFailedToRecycleView(vVar)) || vVar.isRecyclable()) {
                    if (this.f <= 0 || vVar.hasAnyOfTheFlags(526)) {
                        z = false;
                    } else {
                        int size = this.f1008c.size();
                        if (size >= this.f && size > 0) {
                            e(0);
                            size--;
                        }
                        if (RecyclerView.f && size > 0 && !RecyclerView.this.qa.a(vVar.mPosition)) {
                            int i2 = size - 1;
                            while (i2 >= 0) {
                                if (!RecyclerView.this.qa.a(this.f1008c.get(i2).mPosition)) {
                                    break;
                                }
                                i2--;
                            }
                            size = i2 + 1;
                        }
                        this.f1008c.add(size, vVar);
                        z = true;
                    }
                    if (!z) {
                        a(vVar, true);
                        z2 = true;
                    }
                } else {
                    z = false;
                }
                RecyclerView.this.p.h(vVar);
                if (!z && !z2 && doesTransientStatePreventRecycling) {
                    vVar.mOwnerRecyclerView = null;
                }
            } else {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + RecyclerView.this.i());
            }
        }

        /* access modifiers changed from: package-private */
        public View c(int i2) {
            return this.f1006a.get(i2).itemView;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.f1006a.clear();
            ArrayList<v> arrayList = this.f1007b;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void c(int i2, int i3) {
            int i4;
            int i5 = i3 + i2;
            for (int size = this.f1008c.size() - 1; size >= 0; size--) {
                v vVar = this.f1008c.get(size);
                if (vVar != null && (i4 = vVar.mPosition) >= i2 && i4 < i5) {
                    vVar.addFlags(2);
                    e(size);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void c(View view) {
            ArrayList<v> arrayList;
            v i2 = RecyclerView.i(view);
            if (!i2.hasAnyOfTheFlags(12) && i2.isUpdated() && !RecyclerView.this.a(i2)) {
                if (this.f1007b == null) {
                    this.f1007b = new ArrayList<>();
                }
                i2.setScrapContainer(this, true);
                arrayList = this.f1007b;
            } else if (!i2.isInvalid() || i2.isRemoved() || RecyclerView.this.v.hasStableIds()) {
                i2.setScrapContainer(this, false);
                arrayList = this.f1006a;
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + RecyclerView.this.i());
            }
            arrayList.add(i2);
        }

        /* access modifiers changed from: package-private */
        public void c(v vVar) {
            (vVar.mInChangeScrap ? this.f1007b : this.f1006a).remove(vVar);
            vVar.mScrapContainer = null;
            vVar.mInChangeScrap = false;
            vVar.clearReturnedFromScrapFlag();
        }

        public View d(int i2) {
            return b(i2, false);
        }

        /* access modifiers changed from: package-private */
        public n d() {
            if (this.g == null) {
                this.g = new n();
            }
            return this.g;
        }

        /* access modifiers changed from: package-private */
        public boolean d(v vVar) {
            if (vVar.isRemoved()) {
                return RecyclerView.this.ra.d();
            }
            int i2 = vVar.mPosition;
            if (i2 < 0 || i2 >= RecyclerView.this.v.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + vVar + RecyclerView.this.i());
            } else if (RecyclerView.this.ra.d() || RecyclerView.this.v.getItemViewType(vVar.mPosition) == vVar.getItemViewType()) {
                return !RecyclerView.this.v.hasStableIds() || vVar.getItemId() == RecyclerView.this.v.getItemId(vVar.mPosition);
            } else {
                return false;
            }
        }

        /* access modifiers changed from: package-private */
        public int e() {
            return this.f1006a.size();
        }

        /* access modifiers changed from: package-private */
        public void e(int i2) {
            a(this.f1008c.get(i2), true);
            this.f1008c.remove(i2);
        }

        public List<v> f() {
            return this.f1009d;
        }

        public void f(int i2) {
            this.e = i2;
            j();
        }

        /* access modifiers changed from: package-private */
        public void g() {
            int size = this.f1008c.size();
            for (int i2 = 0; i2 < size; i2++) {
                LayoutParams layoutParams = (LayoutParams) this.f1008c.get(i2).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.f984c = true;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void h() {
            int size = this.f1008c.size();
            for (int i2 = 0; i2 < size; i2++) {
                v vVar = this.f1008c.get(i2);
                if (vVar != null) {
                    vVar.addFlags(6);
                    vVar.addChangePayload(null);
                }
            }
            a aVar = RecyclerView.this.v;
            if (aVar == null || !aVar.hasStableIds()) {
                i();
            }
        }

        /* access modifiers changed from: package-private */
        public void i() {
            for (int size = this.f1008c.size() - 1; size >= 0; size--) {
                e(size);
            }
            this.f1008c.clear();
            if (RecyclerView.f) {
                RecyclerView.this.qa.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void j() {
            i iVar = RecyclerView.this.w;
            this.f = this.e + (iVar != null ? iVar.mPrefetchMaxCountObserved : 0);
            for (int size = this.f1008c.size() - 1; size >= 0 && this.f1008c.size() > this.f; size--) {
                e(size);
            }
        }
    }

    public interface p {
        void a(v vVar);
    }

    /* access modifiers changed from: private */
    public class q extends c {
        q() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void a() {
            RecyclerView.this.b((String) null);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.ra.g = true;
            recyclerView.b(true);
            if (!RecyclerView.this.n.c()) {
                RecyclerView.this.requestLayout();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void a(int i, int i2, int i3) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.n.a(i, i2, i3)) {
                b();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void a(int i, int i2, Object obj) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.n.a(i, i2, obj)) {
                b();
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (RecyclerView.e) {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.C && recyclerView.B) {
                    b.e.g.t.a(recyclerView, recyclerView.r);
                    return;
                }
            }
            RecyclerView recyclerView2 = RecyclerView.this;
            recyclerView2.K = true;
            recyclerView2.requestLayout();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void b(int i, int i2) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.n.b(i, i2)) {
                b();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void c(int i, int i2) {
            RecyclerView.this.b((String) null);
            if (RecyclerView.this.n.c(i, i2)) {
                b();
            }
        }
    }

    public static abstract class r {

        /* renamed from: a  reason: collision with root package name */
        private int f1011a = -1;

        /* renamed from: b  reason: collision with root package name */
        private RecyclerView f1012b;

        /* renamed from: c  reason: collision with root package name */
        private i f1013c;

        /* renamed from: d  reason: collision with root package name */
        private boolean f1014d;
        private boolean e;
        private View f;
        private final a g = new a(0, 0);
        private boolean h;

        public static class a {

            /* renamed from: a  reason: collision with root package name */
            private int f1015a;

            /* renamed from: b  reason: collision with root package name */
            private int f1016b;

            /* renamed from: c  reason: collision with root package name */
            private int f1017c;

            /* renamed from: d  reason: collision with root package name */
            private int f1018d;
            private Interpolator e;
            private boolean f;
            private int g;

            public a(int i, int i2) {
                this(i, i2, LinearLayoutManager.INVALID_OFFSET, null);
            }

            public a(int i, int i2, int i3, Interpolator interpolator) {
                this.f1018d = -1;
                this.f = false;
                this.g = 0;
                this.f1015a = i;
                this.f1016b = i2;
                this.f1017c = i3;
                this.e = interpolator;
            }

            private void b() {
                if (this.e != null && this.f1017c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.f1017c < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public void a(int i) {
                this.f1018d = i;
            }

            public void a(int i, int i2, int i3, Interpolator interpolator) {
                this.f1015a = i;
                this.f1016b = i2;
                this.f1017c = i3;
                this.e = interpolator;
                this.f = true;
            }

            /* access modifiers changed from: package-private */
            public void a(RecyclerView recyclerView) {
                int i = this.f1018d;
                if (i >= 0) {
                    this.f1018d = -1;
                    recyclerView.d(i);
                    this.f = false;
                } else if (this.f) {
                    b();
                    Interpolator interpolator = this.e;
                    if (interpolator == null) {
                        int i2 = this.f1017c;
                        if (i2 == Integer.MIN_VALUE) {
                            recyclerView.oa.b(this.f1015a, this.f1016b);
                        } else {
                            recyclerView.oa.a(this.f1015a, this.f1016b, i2);
                        }
                    } else {
                        recyclerView.oa.a(this.f1015a, this.f1016b, this.f1017c, interpolator);
                    }
                    this.g++;
                    if (this.g > 10) {
                        Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.f = false;
                } else {
                    this.g = 0;
                }
            }

            /* access modifiers changed from: package-private */
            public boolean a() {
                return this.f1018d >= 0;
            }
        }

        public interface b {
            PointF computeScrollVectorForPosition(int i);
        }

        public int a() {
            return this.f1012b.w.getChildCount();
        }

        public int a(View view) {
            return this.f1012b.g(view);
        }

        public PointF a(int i) {
            i b2 = b();
            if (b2 instanceof b) {
                return ((b) b2).computeScrollVectorForPosition(i);
            }
            Log.w("RecyclerView", "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + b.class.getCanonicalName());
            return null;
        }

        /* access modifiers changed from: package-private */
        public void a(int i, int i2) {
            PointF a2;
            RecyclerView recyclerView = this.f1012b;
            if (!this.e || this.f1011a == -1 || recyclerView == null) {
                h();
            }
            if (!(!this.f1014d || this.f != null || this.f1013c == null || (a2 = a(this.f1011a)) == null || (a2.x == 0.0f && a2.y == 0.0f))) {
                recyclerView.a((int) Math.signum(a2.x), (int) Math.signum(a2.y), (int[]) null);
            }
            this.f1014d = false;
            View view = this.f;
            if (view != null) {
                if (a(view) == this.f1011a) {
                    a(this.f, recyclerView.ra, this.g);
                    this.g.a(recyclerView);
                    h();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    this.f = null;
                }
            }
            if (this.e) {
                a(i, i2, recyclerView.ra, this.g);
                boolean a3 = this.g.a();
                this.g.a(recyclerView);
                if (!a3) {
                    return;
                }
                if (this.e) {
                    this.f1014d = true;
                    recyclerView.oa.a();
                    return;
                }
                h();
            }
        }

        /* access modifiers changed from: protected */
        public abstract void a(int i, int i2, s sVar, a aVar);

        /* access modifiers changed from: protected */
        public void a(PointF pointF) {
            float f2 = pointF.x;
            float f3 = pointF.y;
            float sqrt = (float) Math.sqrt((double) ((f2 * f2) + (f3 * f3)));
            pointF.x /= sqrt;
            pointF.y /= sqrt;
        }

        /* access modifiers changed from: protected */
        public abstract void a(View view, s sVar, a aVar);

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView, i iVar) {
            if (this.h) {
                Log.w("RecyclerView", "An instance of " + getClass().getSimpleName() + " was started " + "more than once. Each instance of" + getClass().getSimpleName() + " " + "is intended to only be used once. You should create a new instance for " + "each use.");
            }
            this.f1012b = recyclerView;
            this.f1013c = iVar;
            int i = this.f1011a;
            if (i != -1) {
                this.f1012b.ra.f1019a = i;
                this.e = true;
                this.f1014d = true;
                this.f = b(c());
                f();
                this.f1012b.oa.a();
                this.h = true;
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public View b(int i) {
            return this.f1012b.w.findViewByPosition(i);
        }

        public i b() {
            return this.f1013c;
        }

        /* access modifiers changed from: protected */
        public void b(View view) {
            if (a(view) == c()) {
                this.f = view;
            }
        }

        public int c() {
            return this.f1011a;
        }

        public void c(int i) {
            this.f1011a = i;
        }

        public boolean d() {
            return this.f1014d;
        }

        public boolean e() {
            return this.e;
        }

        /* access modifiers changed from: protected */
        public abstract void f();

        /* access modifiers changed from: protected */
        public abstract void g();

        /* access modifiers changed from: protected */
        public final void h() {
            if (this.e) {
                this.e = false;
                g();
                this.f1012b.ra.f1019a = -1;
                this.f = null;
                this.f1011a = -1;
                this.f1014d = false;
                this.f1013c.onSmoothScrollerStopped(this);
                this.f1013c = null;
                this.f1012b = null;
            }
        }
    }

    public static class s {

        /* renamed from: a  reason: collision with root package name */
        int f1019a = -1;

        /* renamed from: b  reason: collision with root package name */
        private SparseArray<Object> f1020b;

        /* renamed from: c  reason: collision with root package name */
        int f1021c = 0;

        /* renamed from: d  reason: collision with root package name */
        int f1022d = 0;
        int e = 1;
        int f = 0;
        boolean g = false;
        boolean h = false;
        boolean i = false;
        boolean j = false;
        boolean k = false;
        boolean l = false;
        int m;
        long n;
        int o;
        int p;
        int q;

        public int a() {
            return this.h ? this.f1021c - this.f1022d : this.f;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            if ((this.e & i2) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i2) + " but it is " + Integer.toBinaryString(this.e));
            }
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar) {
            this.e = 1;
            this.f = aVar.getItemCount();
            this.h = false;
            this.i = false;
            this.j = false;
        }

        public int b() {
            return this.f1019a;
        }

        public boolean c() {
            return this.f1019a != -1;
        }

        public boolean d() {
            return this.h;
        }

        public boolean e() {
            return this.l;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.f1019a + ", mData=" + this.f1020b + ", mItemCount=" + this.f + ", mIsMeasuring=" + this.j + ", mPreviousLayoutItemCount=" + this.f1021c + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.f1022d + ", mStructureChanged=" + this.g + ", mInPreLayout=" + this.h + ", mRunSimpleAnimations=" + this.k + ", mRunPredictiveAnimations=" + this.l + '}';
        }
    }

    public static abstract class t {
        public abstract View a(o oVar, int i, int i2);
    }

    /* access modifiers changed from: package-private */
    public class u implements Runnable {

        /* renamed from: a  reason: collision with root package name */
        private int f1023a;

        /* renamed from: b  reason: collision with root package name */
        private int f1024b;

        /* renamed from: c  reason: collision with root package name */
        OverScroller f1025c;

        /* renamed from: d  reason: collision with root package name */
        Interpolator f1026d = RecyclerView.j;
        private boolean e = false;
        private boolean f = false;

        u() {
            this.f1025c = new OverScroller(RecyclerView.this.getContext(), RecyclerView.j);
        }

        private float a(float f2) {
            return (float) Math.sin((double) ((f2 - 0.5f) * 0.47123894f));
        }

        private int b(int i, int i2, int i3, int i4) {
            int i5;
            int abs = Math.abs(i);
            int abs2 = Math.abs(i2);
            boolean z = abs > abs2;
            int sqrt = (int) Math.sqrt((double) ((i3 * i3) + (i4 * i4)));
            int sqrt2 = (int) Math.sqrt((double) ((i * i) + (i2 * i2)));
            int width = z ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            int i6 = width / 2;
            float f2 = (float) width;
            float f3 = (float) i6;
            float a2 = f3 + (a(Math.min(1.0f, (((float) sqrt2) * 1.0f) / f2)) * f3);
            if (sqrt > 0) {
                i5 = Math.round(Math.abs(a2 / ((float) sqrt)) * 1000.0f) * 4;
            } else {
                if (!z) {
                    abs = abs2;
                }
                i5 = (int) (((((float) abs) / f2) + 1.0f) * 300.0f);
            }
            return Math.min(i5, (int) UpdateManager.UPDATE_CHECK_TIMEOUT_PER_REQUESET);
        }

        private void c() {
            this.f = false;
            this.e = true;
        }

        private void d() {
            this.e = false;
            if (this.f) {
                a();
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            if (this.e) {
                this.f = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            b.e.g.t.a(RecyclerView.this, this);
        }

        public void a(int i, int i2) {
            RecyclerView.this.setScrollState(2);
            this.f1024b = 0;
            this.f1023a = 0;
            this.f1025c.fling(0, 0, i, i2, LinearLayoutManager.INVALID_OFFSET, Integer.MAX_VALUE, LinearLayoutManager.INVALID_OFFSET, Integer.MAX_VALUE);
            a();
        }

        public void a(int i, int i2, int i3) {
            a(i, i2, i3, RecyclerView.j);
        }

        public void a(int i, int i2, int i3, int i4) {
            a(i, i2, b(i, i2, i3, i4));
        }

        public void a(int i, int i2, int i3, Interpolator interpolator) {
            if (this.f1026d != interpolator) {
                this.f1026d = interpolator;
                this.f1025c = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            RecyclerView.this.setScrollState(2);
            this.f1024b = 0;
            this.f1023a = 0;
            this.f1025c.startScroll(0, 0, i, i2, i3);
            if (Build.VERSION.SDK_INT < 23) {
                this.f1025c.computeScrollOffset();
            }
            a();
        }

        public void a(int i, int i2, Interpolator interpolator) {
            int b2 = b(i, i2, 0, 0);
            if (interpolator == null) {
                interpolator = RecyclerView.j;
            }
            a(i, i2, b2, interpolator);
        }

        public void b() {
            RecyclerView.this.removeCallbacks(this);
            this.f1025c.abortAnimation();
        }

        public void b(int i, int i2) {
            a(i, i2, 0, 0);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e3, code lost:
            if (r8 > 0) goto L_0x00e7;
         */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00df  */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x00ef  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            // Method dump skipped, instructions count: 410
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.u.run():void");
        }
    }

    public static abstract class v {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        int mFlags;
        boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        o mScrapContainer = null;
        v mShadowedHolder = null;
        v mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public v(View view) {
            if (view != null) {
                this.itemView = view;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        /* access modifiers changed from: package-private */
        public void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(FLAG_ADAPTER_FULLUPDATE);
            } else if ((FLAG_ADAPTER_FULLUPDATE & this.mFlags) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(obj);
            }
        }

        /* access modifiers changed from: package-private */
        public void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        /* access modifiers changed from: package-private */
        public void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        /* access modifiers changed from: package-private */
        public void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        /* access modifiers changed from: package-private */
        public void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        /* access modifiers changed from: package-private */
        public void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        /* access modifiers changed from: package-private */
        public boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && b.e.g.t.u(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public void flagRemovedAndOffsetPosition(int i, int i2, boolean z) {
            addFlags(8);
            offsetPosition(i2, z);
            this.mPosition = i;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.c(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        /* access modifiers changed from: package-private */
        public List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & FLAG_ADAPTER_FULLUPDATE) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            List<Object> list = this.mPayloads;
            return (list == null || list.size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads;
        }

        /* access modifiers changed from: package-private */
        public boolean hasAnyOfTheFlags(int i) {
            return (i & this.mFlags) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isAdapterPositionUnknown() {
            return (this.mFlags & FLAG_ADAPTER_POSITION_UNKNOWN) != 0 || isInvalid();
        }

        /* access modifiers changed from: package-private */
        public boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !b.e.g.t.u(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isScrap() {
            return this.mScrapContainer != null;
        }

        /* access modifiers changed from: package-private */
        public boolean isTmpDetached() {
            return (this.mFlags & FLAG_TMP_DETACHED) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).f984c = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void onEnteredHiddenState(RecyclerView recyclerView) {
            int i = this.mPendingAccessibilityState;
            if (i == -1) {
                i = b.e.g.t.g(this.itemView);
            }
            this.mWasImportantForAccessibilityBeforeHidden = i;
            recyclerView.a(this, 4);
        }

        /* access modifiers changed from: package-private */
        public void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.a(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        /* access modifiers changed from: package-private */
        public void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.b(this);
        }

        /* access modifiers changed from: package-private */
        public void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        /* access modifiers changed from: package-private */
        public void setFlags(int i, int i2) {
            this.mFlags = (i & i2) | (this.mFlags & (i2 ^ -1));
        }

        public final void setIsRecyclable(boolean z) {
            int i;
            this.mIsRecyclableCount = z ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            int i2 = this.mIsRecyclableCount;
            if (i2 < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
                return;
            }
            if (!z && i2 == 1) {
                i = this.mFlags | 16;
            } else if (z && this.mIsRecyclableCount == 0) {
                i = this.mFlags & -17;
            } else {
                return;
            }
            this.mFlags = i;
        }

        /* access modifiers changed from: package-private */
        public void setScrapContainer(o oVar, boolean z) {
            this.mScrapContainer = oVar;
            this.mInChangeScrap = z;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldIgnore() {
            return (this.mFlags & FLAG_IGNORE) != 0;
        }

        /* access modifiers changed from: package-private */
        public void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                sb.append(" scrap ");
                sb.append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            if (needsUpdate()) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public void unScrap() {
            this.mScrapContainer.c(this);
        }

        /* access modifiers changed from: package-private */
        public boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        f980c = i2 == 18 || i2 == 19 || i2 == 20;
        Class<?> cls = Integer.TYPE;
        i = new Class[]{Context.class, AttributeSet.class, cls, cls};
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.k = new q();
        this.l = new o();
        this.p = new O();
        this.r = new y(this);
        this.s = new Rect();
        this.t = new Rect();
        this.u = new RectF();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
        this.F = 0;
        this.N = false;
        this.O = false;
        this.P = 0;
        this.Q = 0;
        this.R = new e();
        this.W = new C0106k();
        this.aa = 0;
        this.ba = -1;
        this.la = Float.MIN_VALUE;
        this.ma = Float.MIN_VALUE;
        boolean z2 = true;
        this.na = true;
        this.oa = new u();
        this.qa = f ? new p.a() : null;
        this.ra = new s();
        this.ua = false;
        this.va = false;
        this.wa = new g();
        this.xa = false;
        this.Aa = new int[2];
        this.Ca = new int[2];
        this.Da = new int[2];
        this.Ea = new int[2];
        this.Fa = new int[2];
        this.Ga = new ArrayList();
        this.Ha = new z(this);
        this.Ia = new B(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f979b, i2, 0);
            this.q = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        } else {
            this.q = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.ha = viewConfiguration.getScaledTouchSlop();
        this.la = b.e.g.u.a(viewConfiguration, context);
        this.ma = b.e.g.u.b(viewConfiguration, context);
        this.ja = viewConfiguration.getScaledMinimumFlingVelocity();
        this.ka = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.W.a(this.wa);
        k();
        G();
        F();
        if (b.e.g.t.g(this) == 0) {
            b.e.g.t.c(this, 1);
        }
        this.L = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new H(this));
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, b.k.b.RecyclerView, i2, 0);
            String string = obtainStyledAttributes2.getString(b.k.b.RecyclerView_layoutManager);
            if (obtainStyledAttributes2.getInt(b.k.b.RecyclerView_android_descendantFocusability, -1) == -1) {
                setDescendantFocusability(262144);
            }
            this.D = obtainStyledAttributes2.getBoolean(b.k.b.RecyclerView_fastScrollEnabled, false);
            if (this.D) {
                a((StateListDrawable) obtainStyledAttributes2.getDrawable(b.k.b.RecyclerView_fastScrollVerticalThumbDrawable), obtainStyledAttributes2.getDrawable(b.k.b.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable) obtainStyledAttributes2.getDrawable(b.k.b.RecyclerView_fastScrollHorizontalThumbDrawable), obtainStyledAttributes2.getDrawable(b.k.b.RecyclerView_fastScrollHorizontalTrackDrawable));
            }
            obtainStyledAttributes2.recycle();
            a(context, string, attributeSet, i2, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, f978a, i2, 0);
                boolean z3 = obtainStyledAttributes3.getBoolean(0, true);
                obtainStyledAttributes3.recycle();
                z2 = z3;
            }
        } else {
            setDescendantFocusability(262144);
        }
        setNestedScrollingEnabled(z2);
    }

    private void A() {
        boolean z2 = true;
        this.ra.a(1);
        a(this.ra);
        this.ra.j = false;
        w();
        this.p.a();
        q();
        I();
        N();
        s sVar = this.ra;
        if (!sVar.k || !this.va) {
            z2 = false;
        }
        sVar.i = z2;
        this.va = false;
        this.ua = false;
        s sVar2 = this.ra;
        sVar2.h = sVar2.l;
        sVar2.f = this.v.getItemCount();
        a(this.Aa);
        if (this.ra.k) {
            int a2 = this.o.a();
            for (int i2 = 0; i2 < a2; i2++) {
                v i3 = i(this.o.c(i2));
                if (!i3.shouldIgnore() && (!i3.isInvalid() || this.v.hasStableIds())) {
                    this.p.c(i3, this.W.a(this.ra, i3, f.a(i3), i3.getUnmodifiedPayloads()));
                    if (this.ra.i && i3.isUpdated() && !i3.isRemoved() && !i3.shouldIgnore() && !i3.isInvalid()) {
                        this.p.a(d(i3), i3);
                    }
                }
            }
        }
        if (this.ra.l) {
            v();
            s sVar3 = this.ra;
            boolean z3 = sVar3.g;
            sVar3.g = false;
            this.w.onLayoutChildren(this.l, sVar3);
            this.ra.g = z3;
            for (int i4 = 0; i4 < this.o.a(); i4++) {
                v i5 = i(this.o.c(i4));
                if (!i5.shouldIgnore() && !this.p.c(i5)) {
                    int a3 = f.a(i5);
                    boolean hasAnyOfTheFlags = i5.hasAnyOfTheFlags(8192);
                    if (!hasAnyOfTheFlags) {
                        a3 |= 4096;
                    }
                    f.c a4 = this.W.a(this.ra, i5, a3, i5.getUnmodifiedPayloads());
                    if (hasAnyOfTheFlags) {
                        a(i5, a4);
                    } else {
                        this.p.a(i5, a4);
                    }
                }
            }
        }
        a();
        r();
        c(false);
        this.ra.e = 2;
    }

    private void B() {
        w();
        q();
        this.ra.a(6);
        this.n.b();
        this.ra.f = this.v.getItemCount();
        s sVar = this.ra;
        sVar.f1022d = 0;
        sVar.h = false;
        this.w.onLayoutChildren(this.l, sVar);
        s sVar2 = this.ra;
        sVar2.g = false;
        this.m = null;
        sVar2.k = sVar2.k && this.W != null;
        this.ra.e = 4;
        r();
        c(false);
    }

    private void C() {
        this.ra.a(4);
        w();
        q();
        s sVar = this.ra;
        sVar.e = 1;
        if (sVar.k) {
            for (int a2 = this.o.a() - 1; a2 >= 0; a2--) {
                v i2 = i(this.o.c(a2));
                if (!i2.shouldIgnore()) {
                    long d2 = d(i2);
                    f.c a3 = this.W.a(this.ra, i2);
                    v a4 = this.p.a(d2);
                    if (a4 != null && !a4.shouldIgnore()) {
                        boolean b2 = this.p.b(a4);
                        boolean b3 = this.p.b(i2);
                        if (!b2 || a4 != i2) {
                            f.c f2 = this.p.f(a4);
                            this.p.b(i2, a3);
                            f.c e2 = this.p.e(i2);
                            if (f2 == null) {
                                a(d2, i2, a4);
                            } else {
                                a(a4, i2, f2, e2, b2, b3);
                            }
                        }
                    }
                    this.p.b(i2, a3);
                }
            }
            this.p.a(this.Ia);
        }
        this.w.removeAndRecycleScrapInt(this.l);
        s sVar2 = this.ra;
        sVar2.f1021c = sVar2.f;
        this.N = false;
        this.O = false;
        sVar2.k = false;
        sVar2.l = false;
        this.w.mRequestedSimpleAnimations = false;
        ArrayList<v> arrayList = this.l.f1007b;
        if (arrayList != null) {
            arrayList.clear();
        }
        i iVar = this.w;
        if (iVar.mPrefetchMaxObservedInInitialPrefetch) {
            iVar.mPrefetchMaxCountObserved = 0;
            iVar.mPrefetchMaxObservedInInitialPrefetch = false;
            this.l.j();
        }
        this.w.onLayoutCompleted(this.ra);
        r();
        c(false);
        this.p.a();
        int[] iArr = this.Aa;
        if (k(iArr[0], iArr[1])) {
            d(0, 0);
        }
        J();
        L();
    }

    private View D() {
        v b2;
        int i2 = this.ra.m;
        if (i2 == -1) {
            i2 = 0;
        }
        int a2 = this.ra.a();
        for (int i3 = i2; i3 < a2; i3++) {
            v b3 = b(i3);
            if (b3 == null) {
                break;
            } else if (b3.itemView.hasFocusable()) {
                return b3.itemView;
            }
        }
        int min = Math.min(a2, i2);
        while (true) {
            min--;
            if (min < 0 || (b2 = b(min)) == null) {
                return null;
            }
            if (b2.itemView.hasFocusable()) {
                return b2.itemView;
            }
        }
    }

    private boolean E() {
        int a2 = this.o.a();
        for (int i2 = 0; i2 < a2; i2++) {
            v i3 = i(this.o.c(i2));
            if (!(i3 == null || i3.shouldIgnore() || !i3.isUpdated())) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"InlinedApi"})
    private void F() {
        if (b.e.g.t.h(this) == 0) {
            b.e.g.t.d(this, 8);
        }
    }

    private void G() {
        this.o = new C0097b(new C(this));
    }

    private boolean H() {
        return this.W != null && this.w.supportsPredictiveItemAnimations();
    }

    private void I() {
        if (this.N) {
            this.n.f();
            if (this.O) {
                this.w.onItemsChanged(this);
            }
        }
        if (H()) {
            this.n.e();
        } else {
            this.n.b();
        }
        boolean z2 = false;
        boolean z3 = this.ua || this.va;
        this.ra.k = this.E && this.W != null && (this.N || z3 || this.w.mRequestedSimpleAnimations) && (!this.N || this.v.hasStableIds());
        s sVar = this.ra;
        if (sVar.k && z3 && !this.N && H()) {
            z2 = true;
        }
        sVar.l = z2;
    }

    private void J() {
        View view;
        if (this.na && this.v != null && hasFocus() && getDescendantFocusability() != 393216) {
            if (getDescendantFocusability() != 131072 || !isFocused()) {
                if (!isFocused()) {
                    View focusedChild = getFocusedChild();
                    if (!h || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                        if (!this.o.c(focusedChild)) {
                            return;
                        }
                    } else if (this.o.a() == 0) {
                        requestFocus();
                        return;
                    }
                }
                View view2 = null;
                v a2 = (this.ra.n == -1 || !this.v.hasStableIds()) ? null : a(this.ra.n);
                if (a2 != null && !this.o.c(a2.itemView) && a2.itemView.hasFocusable()) {
                    view2 = a2.itemView;
                } else if (this.o.a() > 0) {
                    view2 = D();
                }
                if (view2 != null) {
                    int i2 = this.ra.o;
                    if (((long) i2) == -1 || (view = view2.findViewById(i2)) == null || !view.isFocusable()) {
                        view = view2;
                    }
                    view.requestFocus();
                }
            }
        }
    }

    private void K() {
        boolean z2;
        EdgeEffect edgeEffect = this.S;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z2 = this.S.isFinished();
        } else {
            z2 = false;
        }
        EdgeEffect edgeEffect2 = this.T;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            z2 |= this.T.isFinished();
        }
        EdgeEffect edgeEffect3 = this.U;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            z2 |= this.U.isFinished();
        }
        EdgeEffect edgeEffect4 = this.V;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            z2 |= this.V.isFinished();
        }
        if (z2) {
            b.e.g.t.y(this);
        }
    }

    private void L() {
        s sVar = this.ra;
        sVar.n = -1;
        sVar.m = -1;
        sVar.o = -1;
    }

    private void M() {
        VelocityTracker velocityTracker = this.ca;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        h(0);
        K();
    }

    private void N() {
        v vVar = null;
        View focusedChild = (!this.na || !hasFocus() || this.v == null) ? null : getFocusedChild();
        if (focusedChild != null) {
            vVar = d(focusedChild);
        }
        if (vVar == null) {
            L();
            return;
        }
        this.ra.n = this.v.hasStableIds() ? vVar.getItemId() : -1;
        this.ra.m = this.N ? -1 : vVar.isRemoved() ? vVar.mOldPosition : vVar.getAdapterPosition();
        this.ra.o = n(vVar.itemView);
    }

    private void O() {
        this.oa.b();
        i iVar = this.w;
        if (iVar != null) {
            iVar.stopSmoothScroller();
        }
    }

    private String a(Context context, String str) {
        if (str.charAt(0) == '.') {
            return context.getPackageName() + str;
        } else if (str.contains(".")) {
            return str;
        } else {
            return RecyclerView.class.getPackage().getName() + '.' + str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(float r7, float r8, float r9, float r10) {
        /*
        // Method dump skipped, instructions count: 125
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.a(float, float, float, float):void");
    }

    private void a(long j2, v vVar, v vVar2) {
        int a2 = this.o.a();
        for (int i2 = 0; i2 < a2; i2++) {
            v i3 = i(this.o.c(i2));
            if (i3 != vVar && d(i3) == j2) {
                a aVar = this.v;
                if (aVar == null || !aVar.hasStableIds()) {
                    throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + i3 + " \n View Holder 2:" + vVar + i());
                }
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + i3 + " \n View Holder 2:" + vVar + i());
            }
        }
        Log.e("RecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + vVar2 + " cannot be found but it is necessary for " + vVar + i());
    }

    private void a(Context context, String str, AttributeSet attributeSet, int i2, int i3) {
        Constructor<? extends U> constructor;
        if (str != null) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                String a2 = a(context, trim);
                try {
                    Class<? extends U> asSubclass = (isInEditMode() ? getClass().getClassLoader() : context.getClassLoader()).loadClass(a2).asSubclass(i.class);
                    Object[] objArr = null;
                    try {
                        constructor = asSubclass.getConstructor(i);
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i2), Integer.valueOf(i3)};
                    } catch (NoSuchMethodException e2) {
                        try {
                            constructor = asSubclass.getConstructor(new Class[0]);
                        } catch (NoSuchMethodException e3) {
                            e3.initCause(e2);
                            throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + a2, e3);
                        }
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((i) constructor.newInstance(objArr));
                } catch (ClassNotFoundException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + a2, e4);
                } catch (InvocationTargetException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + a2, e5);
                } catch (InstantiationException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + a2, e6);
                } catch (IllegalAccessException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + a2, e7);
                } catch (ClassCastException e8) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + a2, e8);
                }
            }
        }
    }

    static void a(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect2 = layoutParams.f983b;
        rect.set((view.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, (view.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, view.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, view.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
    }

    private void a(View view, View view2) {
        View view3 = view2 != null ? view2 : view;
        this.s.set(0, 0, view3.getWidth(), view3.getHeight());
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.f984c) {
                Rect rect = layoutParams2.f983b;
                Rect rect2 = this.s;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.s);
            offsetRectIntoDescendantCoords(view, this.s);
        }
        this.w.requestChildRectangleOnScreen(this, view, this.s, !this.E, view2 == null);
    }

    private void a(a aVar, boolean z2, boolean z3) {
        a aVar2 = this.v;
        if (aVar2 != null) {
            aVar2.unregisterAdapterDataObserver(this.k);
            this.v.onDetachedFromRecyclerView(this);
        }
        if (!z2 || z3) {
            t();
        }
        this.n.f();
        a aVar3 = this.v;
        this.v = aVar;
        if (aVar != null) {
            aVar.registerAdapterDataObserver(this.k);
            aVar.onAttachedToRecyclerView(this);
        }
        i iVar = this.w;
        if (iVar != null) {
            iVar.onAdapterChanged(aVar3, this.v);
        }
        this.l.a(aVar3, this.v, z2);
        this.ra.g = true;
    }

    private void a(v vVar, v vVar2, f.c cVar, f.c cVar2, boolean z2, boolean z3) {
        vVar.setIsRecyclable(false);
        if (z2) {
            e(vVar);
        }
        if (vVar != vVar2) {
            if (z3) {
                e(vVar2);
            }
            vVar.mShadowedHolder = vVar2;
            e(vVar);
            this.l.c(vVar);
            vVar2.setIsRecyclable(false);
            vVar2.mShadowingHolder = vVar;
        }
        if (this.W.a(vVar, vVar2, cVar, cVar2)) {
            s();
        }
    }

    private void a(int[] iArr) {
        int a2 = this.o.a();
        if (a2 == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i2 = Integer.MAX_VALUE;
        int i3 = LinearLayoutManager.INVALID_OFFSET;
        for (int i4 = 0; i4 < a2; i4++) {
            v i5 = i(this.o.c(i4));
            if (!i5.shouldIgnore()) {
                int layoutPosition = i5.getLayoutPosition();
                if (layoutPosition < i2) {
                    i2 = layoutPosition;
                }
                if (layoutPosition > i3) {
                    i3 = layoutPosition;
                }
            }
        }
        iArr[0] = i2;
        iArr[1] = i3;
    }

    private boolean a(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        l lVar = this.A;
        if (lVar != null) {
            if (action == 0) {
                this.A = null;
            } else {
                lVar.a(this, motionEvent);
                if (action == 3 || action == 1) {
                    this.A = null;
                }
                return true;
            }
        }
        if (action != 0) {
            int size = this.z.size();
            for (int i2 = 0; i2 < size; i2++) {
                l lVar2 = this.z.get(i2);
                if (lVar2.b(this, motionEvent)) {
                    this.A = lVar2;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(View view, View view2, int i2) {
        int i3;
        if (view2 == null || view2 == this || c(view2) == null) {
            return false;
        }
        if (view == null || c(view) == null) {
            return true;
        }
        this.s.set(0, 0, view.getWidth(), view.getHeight());
        this.t.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.s);
        offsetDescendantRectToMyCoords(view2, this.t);
        char c2 = 65535;
        int i4 = this.w.getLayoutDirection() == 1 ? -1 : 1;
        Rect rect = this.s;
        int i5 = rect.left;
        int i6 = this.t.left;
        if ((i5 < i6 || rect.right <= i6) && this.s.right < this.t.right) {
            i3 = 1;
        } else {
            Rect rect2 = this.s;
            int i7 = rect2.right;
            int i8 = this.t.right;
            i3 = ((i7 > i8 || rect2.left >= i8) && this.s.left > this.t.left) ? -1 : 0;
        }
        Rect rect3 = this.s;
        int i9 = rect3.top;
        int i10 = this.t.top;
        if ((i9 < i10 || rect3.bottom <= i10) && this.s.bottom < this.t.bottom) {
            c2 = 1;
        } else {
            Rect rect4 = this.s;
            int i11 = rect4.bottom;
            int i12 = this.t.bottom;
            if ((i11 <= i12 && rect4.top < i12) || this.s.top <= this.t.top) {
                c2 = 0;
            }
        }
        if (i2 == 1) {
            return c2 < 0 || (c2 == 0 && i3 * i4 <= 0);
        }
        if (i2 == 2) {
            return c2 > 0 || (c2 == 0 && i3 * i4 >= 0);
        }
        if (i2 == 17) {
            return i3 < 0;
        }
        if (i2 == 33) {
            return c2 < 0;
        }
        if (i2 == 66) {
            return i3 > 0;
        }
        if (i2 == 130) {
            return c2 > 0;
        }
        throw new IllegalArgumentException("Invalid direction: " + i2 + i());
    }

    static void b(v vVar) {
        WeakReference<RecyclerView> weakReference = vVar.mNestedRecyclerView;
        if (weakReference != null) {
            ViewParent viewParent = weakReference.get();
            while (true) {
                View view = (View) viewParent;
                while (true) {
                    if (view == null) {
                        vVar.mNestedRecyclerView = null;
                        return;
                    } else if (view != vVar.itemView) {
                        viewParent = view.getParent();
                        if (!(viewParent instanceof View)) {
                            view = null;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private boolean b(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.A = null;
        }
        int size = this.z.size();
        for (int i2 = 0; i2 < size; i2++) {
            l lVar = this.z.get(i2);
            if (lVar.b(this, motionEvent) && action != 3) {
                this.A = lVar;
                return true;
            }
        }
        return false;
    }

    private void c(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.ba) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.ba = motionEvent.getPointerId(i2);
            int x2 = (int) (motionEvent.getX(i2) + 0.5f);
            this.fa = x2;
            this.da = x2;
            int y2 = (int) (motionEvent.getY(i2) + 0.5f);
            this.ga = y2;
            this.ea = y2;
        }
    }

    static RecyclerView e(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            RecyclerView e2 = e(viewGroup.getChildAt(i2));
            if (e2 != null) {
                return e2;
            }
        }
        return null;
    }

    private void e(v vVar) {
        View view = vVar.itemView;
        boolean z2 = view.getParent() == this;
        this.l.c(h(view));
        if (vVar.isTmpDetached()) {
            this.o.a(view, -1, view.getLayoutParams(), true);
        } else if (!z2) {
            this.o.a(view, true);
        } else {
            this.o.a(view);
        }
    }

    private b.e.g.k getScrollingChildHelper() {
        if (this.Ba == null) {
            this.Ba = new b.e.g.k(this);
        }
        return this.Ba;
    }

    static v i(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).f982a;
    }

    private boolean k(int i2, int i3) {
        a(this.Aa);
        int[] iArr = this.Aa;
        return (iArr[0] == i2 && iArr[1] == i3) ? false : true;
    }

    private int n(View view) {
        int id;
        loop0:
        while (true) {
            id = view.getId();
            while (true) {
                if (view.isFocused() || !(view instanceof ViewGroup) || !view.hasFocus()) {
                    return id;
                }
                view = ((ViewGroup) view).getFocusedChild();
                if (view.getId() != -1) {
                }
            }
        }
        return id;
    }

    private void y() {
        M();
        setScrollState(0);
    }

    private void z() {
        int i2 = this.J;
        this.J = 0;
        if (i2 != 0 && m()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            b.e.g.a.a.a(obtain, i2);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    /* access modifiers changed from: package-private */
    public v a(int i2, boolean z2) {
        int b2 = this.o.b();
        v vVar = null;
        for (int i3 = 0; i3 < b2; i3++) {
            v i4 = i(this.o.d(i3));
            if (i4 != null && !i4.isRemoved()) {
                if (z2) {
                    if (i4.mPosition != i2) {
                        continue;
                    }
                } else if (i4.getLayoutPosition() != i2) {
                    continue;
                }
                if (!this.o.c(i4.itemView)) {
                    return i4;
                }
                vVar = i4;
            }
        }
        return vVar;
    }

    public v a(long j2) {
        a aVar = this.v;
        v vVar = null;
        if (aVar != null && aVar.hasStableIds()) {
            int b2 = this.o.b();
            for (int i2 = 0; i2 < b2; i2++) {
                v i3 = i(this.o.d(i2));
                if (i3 != null && !i3.isRemoved() && i3.getItemId() == j2) {
                    if (!this.o.c(i3.itemView)) {
                        return i3;
                    }
                    vVar = i3;
                }
            }
        }
        return vVar;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        int b2 = this.o.b();
        for (int i2 = 0; i2 < b2; i2++) {
            v i3 = i(this.o.d(i2));
            if (!i3.shouldIgnore()) {
                i3.clearOldPosition();
            }
        }
        this.l.b();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        i iVar = this.w;
        if (iVar != null) {
            iVar.onScrollStateChanged(i2);
        }
        g(i2);
        m mVar = this.sa;
        if (mVar != null) {
            mVar.a(this, i2);
        }
        List<m> list = this.ta;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.ta.get(size).a(this, i2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        if (i2 < 0) {
            f();
            this.S.onAbsorb(-i2);
        } else if (i2 > 0) {
            g();
            this.U.onAbsorb(i2);
        }
        if (i3 < 0) {
            h();
            this.T.onAbsorb(-i3);
        } else if (i3 > 0) {
            e();
            this.V.onAbsorb(i3);
        }
        if (i2 != 0 || i3 != 0) {
            b.e.g.t.y(this);
        }
    }

    public void a(int i2, int i3, Interpolator interpolator) {
        i iVar = this.w;
        if (iVar == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.H) {
            if (!iVar.canScrollHorizontally()) {
                i2 = 0;
            }
            if (!this.w.canScrollVertically()) {
                i3 = 0;
            }
            if (i2 != 0 || i3 != 0) {
                this.oa.a(i2, i3, interpolator);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, Object obj) {
        int i4;
        int b2 = this.o.b();
        int i5 = i2 + i3;
        for (int i6 = 0; i6 < b2; i6++) {
            View d2 = this.o.d(i6);
            v i7 = i(d2);
            if (i7 != null && !i7.shouldIgnore() && (i4 = i7.mPosition) >= i2 && i4 < i5) {
                i7.addFlags(2);
                i7.addChangePayload(obj);
                ((LayoutParams) d2.getLayoutParams()).f984c = true;
            }
        }
        this.l.c(i2, i3);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, boolean z2) {
        int i4 = i2 + i3;
        int b2 = this.o.b();
        for (int i5 = 0; i5 < b2; i5++) {
            v i6 = i(this.o.d(i5));
            if (i6 != null && !i6.shouldIgnore()) {
                int i7 = i6.mPosition;
                if (i7 >= i4) {
                    i6.offsetPosition(-i3, z2);
                } else if (i7 >= i2) {
                    i6.flagRemovedAndOffsetPosition(i2 - 1, -i3, z2);
                }
                this.ra.g = true;
            }
        }
        this.l.a(i2, i3, z2);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, int[] iArr) {
        w();
        q();
        b.e.c.a.a("RV Scroll");
        a(this.ra);
        int scrollHorizontallyBy = i2 != 0 ? this.w.scrollHorizontallyBy(i2, this.l, this.ra) : 0;
        int scrollVerticallyBy = i3 != 0 ? this.w.scrollVerticallyBy(i3, this.l, this.ra) : 0;
        b.e.c.a.a();
        u();
        r();
        c(false);
        if (iArr != null) {
            iArr[0] = scrollHorizontallyBy;
            iArr[1] = scrollVerticallyBy;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + i());
        }
        Resources resources = getContext().getResources();
        new C0109n(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(b.k.a.fastscroll_default_thickness), resources.getDimensionPixelSize(b.k.a.fastscroll_minimum_range), resources.getDimensionPixelOffset(b.k.a.fastscroll_margin));
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        v i2 = i(view);
        k(view);
        a aVar = this.v;
        if (!(aVar == null || i2 == null)) {
            aVar.onViewAttachedToWindow(i2);
        }
        List<j> list = this.M;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.M.get(size).b(view);
            }
        }
    }

    public void a(h hVar) {
        a(hVar, -1);
    }

    public void a(h hVar, int i2) {
        i iVar = this.w;
        if (iVar != null) {
            iVar.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.y.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i2 < 0) {
            this.y.add(hVar);
        } else {
            this.y.add(i2, hVar);
        }
        o();
        requestLayout();
    }

    public void a(l lVar) {
        this.z.add(lVar);
    }

    public void a(m mVar) {
        if (this.ta == null) {
            this.ta = new ArrayList();
        }
        this.ta.add(mVar);
    }

    /* access modifiers changed from: package-private */
    public final void a(s sVar) {
        if (getScrollState() == 2) {
            OverScroller overScroller = this.oa.f1025c;
            sVar.p = overScroller.getFinalX() - overScroller.getCurrX();
            sVar.q = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        sVar.p = 0;
        sVar.q = 0;
    }

    /* access modifiers changed from: package-private */
    public void a(v vVar, f.c cVar) {
        vVar.setFlags(0, 8192);
        if (this.ra.i && vVar.isUpdated() && !vVar.isRemoved() && !vVar.shouldIgnore()) {
            this.p.a(d(vVar), vVar);
        }
        this.p.c(vVar, cVar);
    }

    /* access modifiers changed from: package-private */
    public void a(v vVar, f.c cVar, f.c cVar2) {
        vVar.setIsRecyclable(false);
        if (this.W.a(vVar, cVar, cVar2)) {
            s();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        if (n()) {
            return;
        }
        if (str == null) {
            throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling" + i());
        }
        throw new IllegalStateException(str + i());
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        this.P--;
        if (this.P < 1) {
            this.P = 0;
            if (z2) {
                z();
                d();
            }
        }
    }

    public boolean a(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        return getScrollingChildHelper().a(i2, i3, i4, i5, iArr, i6);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009b, code lost:
        if (r0 != 0) goto L_0x00a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(int r19, int r20, android.view.MotionEvent r21) {
        /*
        // Method dump skipped, instructions count: 178
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.a(int, int, android.view.MotionEvent):boolean");
    }

    public boolean a(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2, i4);
    }

    /* access modifiers changed from: package-private */
    public boolean a(AccessibilityEvent accessibilityEvent) {
        if (!n()) {
            return false;
        }
        int a2 = accessibilityEvent != null ? b.e.g.a.a.a(accessibilityEvent) : 0;
        if (a2 == 0) {
            a2 = 0;
        }
        this.J = a2 | this.J;
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(v vVar) {
        f fVar = this.W;
        return fVar == null || fVar.a(vVar, vVar.getUnmodifiedPayloads());
    }

    /* access modifiers changed from: package-private */
    public boolean a(v vVar, int i2) {
        if (n()) {
            vVar.mPendingAccessibilityState = i2;
            this.Ga.add(vVar);
            return false;
        }
        b.e.g.t.c(vVar.itemView, i2);
        return true;
    }

    @Override // android.view.View, android.view.ViewGroup
    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        i iVar = this.w;
        if (iVar == null || !iVar.onAddFocusables(this, arrayList, i2, i3)) {
            super.addFocusables(arrayList, i2, i3);
        }
    }

    public v b(int i2) {
        v vVar = null;
        if (this.N) {
            return null;
        }
        int b2 = this.o.b();
        for (int i3 = 0; i3 < b2; i3++) {
            v i4 = i(this.o.d(i3));
            if (i4 != null && !i4.isRemoved() && c(i4) == i2) {
                if (!this.o.c(i4.itemView)) {
                    return i4;
                }
                vVar = i4;
            }
        }
        return vVar;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        if (!this.E || this.N) {
            b.e.c.a.a("RV FullInvalidate");
            c();
            b.e.c.a.a();
        } else if (this.n.c()) {
            if (this.n.c(4) && !this.n.c(11)) {
                b.e.c.a.a("RV PartialInvalidate");
                w();
                q();
                this.n.e();
                if (!this.G) {
                    if (E()) {
                        c();
                    } else {
                        this.n.a();
                    }
                }
                c(true);
                r();
            } else if (this.n.c()) {
                b.e.c.a.a("RV FullInvalidate");
                c();
            } else {
                return;
            }
            b.e.c.a.a();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(int i2, int i3) {
        boolean z2;
        EdgeEffect edgeEffect = this.S;
        if (edgeEffect == null || edgeEffect.isFinished() || i2 <= 0) {
            z2 = false;
        } else {
            this.S.onRelease();
            z2 = this.S.isFinished();
        }
        EdgeEffect edgeEffect2 = this.U;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i2 < 0) {
            this.U.onRelease();
            z2 |= this.U.isFinished();
        }
        EdgeEffect edgeEffect3 = this.T;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i3 > 0) {
            this.T.onRelease();
            z2 |= this.T.isFinished();
        }
        EdgeEffect edgeEffect4 = this.V;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i3 < 0) {
            this.V.onRelease();
            z2 |= this.V.isFinished();
        }
        if (z2) {
            b.e.g.t.y(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(View view) {
        v i2 = i(view);
        l(view);
        a aVar = this.v;
        if (!(aVar == null || i2 == null)) {
            aVar.onViewDetachedFromWindow(i2);
        }
        List<j> list = this.M;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.M.get(size).a(view);
            }
        }
    }

    public void b(h hVar) {
        i iVar = this.w;
        if (iVar != null) {
            iVar.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.y.remove(hVar);
        if (this.y.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        o();
        requestLayout();
    }

    public void b(l lVar) {
        this.z.remove(lVar);
        if (this.A == lVar) {
            this.A = null;
        }
    }

    public void b(m mVar) {
        List<m> list = this.ta;
        if (list != null) {
            list.remove(mVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(v vVar, f.c cVar, f.c cVar2) {
        e(vVar);
        vVar.setIsRecyclable(false);
        if (this.W.b(vVar, cVar, cVar2)) {
            s();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(String str) {
        if (n()) {
            if (str == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + i());
            }
            throw new IllegalStateException(str);
        } else if (this.Q > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + i()));
        }
    }

    /* access modifiers changed from: package-private */
    public void b(boolean z2) {
        this.O = z2 | this.O;
        this.N = true;
        p();
    }

    /* access modifiers changed from: package-private */
    public int c(v vVar) {
        if (vVar.hasAnyOfTheFlags(524) || !vVar.isBound()) {
            return -1;
        }
        return this.n.a(vVar.mPosition);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0013 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View c(android.view.View r3) {
        /*
            r2 = this;
        L_0x0000:
            android.view.ViewParent r0 = r3.getParent()
            if (r0 == 0) goto L_0x0010
            if (r0 == r2) goto L_0x0010
            boolean r1 = r0 instanceof android.view.View
            if (r1 == 0) goto L_0x0010
            r3 = r0
            android.view.View r3 = (android.view.View) r3
            goto L_0x0000
        L_0x0010:
            if (r0 != r2) goto L_0x0013
            goto L_0x0014
        L_0x0013:
            r3 = 0
        L_0x0014:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.c(android.view.View):android.view.View");
    }

    /* access modifiers changed from: package-private */
    public void c() {
        String str;
        if (this.v == null) {
            str = "No adapter attached; skipping layout";
        } else if (this.w == null) {
            str = "No layout manager attached; skipping layout";
        } else {
            s sVar = this.ra;
            sVar.j = false;
            if (sVar.e == 1) {
                A();
            } else if (!this.n.d() && this.w.getWidth() == getWidth() && this.w.getHeight() == getHeight()) {
                this.w.setExactMeasureSpecsFrom(this);
                C();
                return;
            }
            this.w.setExactMeasureSpecsFrom(this);
            B();
            C();
            return;
        }
        Log.e("RecyclerView", str);
    }

    /* access modifiers changed from: package-private */
    public void c(int i2, int i3) {
        setMeasuredDimension(i.chooseSize(i2, getPaddingLeft() + getPaddingRight(), b.e.g.t.k(this)), i.chooseSize(i3, getPaddingTop() + getPaddingBottom(), b.e.g.t.j(this)));
    }

    /* access modifiers changed from: package-private */
    public void c(boolean z2) {
        if (this.F < 1) {
            this.F = 1;
        }
        if (!z2 && !this.H) {
            this.G = false;
        }
        if (this.F == 1) {
            if (z2 && this.G && !this.H && this.w != null && this.v != null) {
                c();
            }
            if (!this.H) {
                this.G = false;
            }
        }
        this.F--;
    }

    public boolean c(int i2) {
        return getScrollingChildHelper().a(i2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.w.checkLayoutParams((LayoutParams) layoutParams);
    }

    public int computeHorizontalScrollExtent() {
        i iVar = this.w;
        if (iVar != null && iVar.canScrollHorizontally()) {
            return this.w.computeHorizontalScrollExtent(this.ra);
        }
        return 0;
    }

    public int computeHorizontalScrollOffset() {
        i iVar = this.w;
        if (iVar != null && iVar.canScrollHorizontally()) {
            return this.w.computeHorizontalScrollOffset(this.ra);
        }
        return 0;
    }

    public int computeHorizontalScrollRange() {
        i iVar = this.w;
        if (iVar != null && iVar.canScrollHorizontally()) {
            return this.w.computeHorizontalScrollRange(this.ra);
        }
        return 0;
    }

    public int computeVerticalScrollExtent() {
        i iVar = this.w;
        if (iVar != null && iVar.canScrollVertically()) {
            return this.w.computeVerticalScrollExtent(this.ra);
        }
        return 0;
    }

    public int computeVerticalScrollOffset() {
        i iVar = this.w;
        if (iVar != null && iVar.canScrollVertically()) {
            return this.w.computeVerticalScrollOffset(this.ra);
        }
        return 0;
    }

    public int computeVerticalScrollRange() {
        i iVar = this.w;
        if (iVar != null && iVar.canScrollVertically()) {
            return this.w.computeVerticalScrollRange(this.ra);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public long d(v vVar) {
        return this.v.hasStableIds() ? vVar.getItemId() : (long) vVar.mPosition;
    }

    public v d(View view) {
        View c2 = c(view);
        if (c2 == null) {
            return null;
        }
        return h(c2);
    }

    /* access modifiers changed from: package-private */
    public void d() {
        int i2;
        for (int size = this.Ga.size() - 1; size >= 0; size--) {
            v vVar = this.Ga.get(size);
            if (vVar.itemView.getParent() == this && !vVar.shouldIgnore() && (i2 = vVar.mPendingAccessibilityState) != -1) {
                b.e.g.t.c(vVar.itemView, i2);
                vVar.mPendingAccessibilityState = -1;
            }
        }
        this.Ga.clear();
    }

    /* access modifiers changed from: package-private */
    public void d(int i2) {
        i iVar = this.w;
        if (iVar != null) {
            iVar.scrollToPosition(i2);
            awakenScrollBars();
        }
    }

    /* access modifiers changed from: package-private */
    public void d(int i2, int i3) {
        this.Q++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        h(i2, i3);
        m mVar = this.sa;
        if (mVar != null) {
            mVar.a(this, i2, i3);
        }
        List<m> list = this.ta;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.ta.get(size).a(this, i2, i3);
            }
        }
        this.Q--;
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return getScrollingChildHelper().a(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().a(f2, f3);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().a(i2, i3, i4, i5, iArr);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View, android.view.ViewGroup
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View, android.view.ViewGroup
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    public void draw(Canvas canvas) {
        boolean z2;
        boolean z3;
        int i2;
        float f2;
        super.draw(canvas);
        int size = this.y.size();
        boolean z4 = false;
        for (int i3 = 0; i3 < size; i3++) {
            this.y.get(i3).onDrawOver(canvas, this, this.ra);
        }
        EdgeEffect edgeEffect = this.S;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z2 = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.q ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float) ((-getHeight()) + paddingBottom), 0.0f);
            EdgeEffect edgeEffect2 = this.S;
            z2 = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(save);
        }
        EdgeEffect edgeEffect3 = this.T;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int save2 = canvas.save();
            if (this.q) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.T;
            z2 |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(save2);
        }
        EdgeEffect edgeEffect5 = this.U;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.q ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate((float) (-paddingTop), (float) (-width));
            EdgeEffect edgeEffect6 = this.U;
            z2 |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(save3);
        }
        EdgeEffect edgeEffect7 = this.V;
        if (edgeEffect7 == null || edgeEffect7.isFinished()) {
            z3 = z2;
        } else {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.q) {
                f2 = (float) ((-getWidth()) + getPaddingRight());
                i2 = (-getHeight()) + getPaddingBottom();
            } else {
                f2 = (float) (-getWidth());
                i2 = -getHeight();
            }
            canvas.translate(f2, (float) i2);
            EdgeEffect edgeEffect8 = this.V;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z4 = true;
            }
            z3 = z4 | z2;
            canvas.restoreToCount(save4);
        }
        if (!z3 && this.W != null && this.y.size() > 0 && this.W.g()) {
            z3 = true;
        }
        if (z3) {
            b.e.g.t.y(this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    /* access modifiers changed from: package-private */
    public void e() {
        int i2;
        int i3;
        EdgeEffect edgeEffect;
        if (this.V == null) {
            this.V = this.R.a(this, 3);
            if (this.q) {
                edgeEffect = this.V;
                i3 = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                i2 = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
            } else {
                edgeEffect = this.V;
                i3 = getMeasuredWidth();
                i2 = getMeasuredHeight();
            }
            edgeEffect.setSize(i3, i2);
        }
    }

    public void e(int i2) {
        int a2 = this.o.a();
        for (int i3 = 0; i3 < a2; i3++) {
            this.o.c(i3).offsetLeftAndRight(i2);
        }
    }

    public boolean e(int i2, int i3) {
        i iVar = this.w;
        int i4 = 0;
        if (iVar == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.H) {
            return false;
        } else {
            boolean canScrollHorizontally = iVar.canScrollHorizontally();
            boolean canScrollVertically = this.w.canScrollVertically();
            if (!canScrollHorizontally || Math.abs(i2) < this.ja) {
                i2 = 0;
            }
            if (!canScrollVertically || Math.abs(i3) < this.ja) {
                i3 = 0;
            }
            if (i2 == 0 && i3 == 0) {
                return false;
            }
            float f2 = (float) i2;
            float f3 = (float) i3;
            if (!dispatchNestedPreFling(f2, f3)) {
                boolean z2 = canScrollHorizontally || canScrollVertically;
                dispatchNestedFling(f2, f3, z2);
                k kVar = this.ia;
                if (kVar != null && kVar.a(i2, i3)) {
                    return true;
                }
                if (z2) {
                    if (canScrollHorizontally) {
                        i4 = 1;
                    }
                    if (canScrollVertically) {
                        i4 |= 2;
                    }
                    j(i4, 1);
                    int i5 = this.ka;
                    int max = Math.max(-i5, Math.min(i2, i5));
                    int i6 = this.ka;
                    this.oa.a(max, Math.max(-i6, Math.min(i3, i6)));
                    return true;
                }
            }
            return false;
        }
    }

    public int f(View view) {
        v i2 = i(view);
        if (i2 != null) {
            return i2.getAdapterPosition();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void f() {
        int i2;
        int i3;
        EdgeEffect edgeEffect;
        if (this.S == null) {
            this.S = this.R.a(this, 0);
            if (this.q) {
                edgeEffect = this.S;
                i3 = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
                i2 = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            } else {
                edgeEffect = this.S;
                i3 = getMeasuredHeight();
                i2 = getMeasuredWidth();
            }
            edgeEffect.setSize(i3, i2);
        }
    }

    public void f(int i2) {
        int a2 = this.o.a();
        for (int i3 = 0; i3 < a2; i3++) {
            this.o.c(i3).offsetTopAndBottom(i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void f(int i2, int i3) {
        int b2 = this.o.b();
        for (int i4 = 0; i4 < b2; i4++) {
            v i5 = i(this.o.d(i4));
            if (i5 != null && !i5.shouldIgnore() && i5.mPosition >= i2) {
                i5.offsetPosition(i3, false);
                this.ra.g = true;
            }
        }
        this.l.a(i2, i3);
        requestLayout();
    }

    public View focusSearch(View view, int i2) {
        View view2;
        boolean z2;
        View onInterceptFocusSearch = this.w.onInterceptFocusSearch(view, i2);
        if (onInterceptFocusSearch != null) {
            return onInterceptFocusSearch;
        }
        boolean z3 = this.v != null && this.w != null && !n() && !this.H;
        FocusFinder instance = FocusFinder.getInstance();
        if (!z3 || !(i2 == 2 || i2 == 1)) {
            View findNextFocus = instance.findNextFocus(this, view, i2);
            if (findNextFocus != null || !z3) {
                view2 = findNextFocus;
            } else {
                b();
                if (c(view) == null) {
                    return null;
                }
                w();
                view2 = this.w.onFocusSearchFailed(view, i2, this.l, this.ra);
                c(false);
            }
        } else {
            if (this.w.canScrollVertically()) {
                int i3 = i2 == 2 ? 130 : 33;
                z2 = instance.findNextFocus(this, view, i3) == null;
                if (g) {
                    i2 = i3;
                }
            } else {
                z2 = false;
            }
            if (!z2 && this.w.canScrollHorizontally()) {
                int i4 = (this.w.getLayoutDirection() == 1) ^ (i2 == 2) ? 66 : 17;
                z2 = instance.findNextFocus(this, view, i4) == null;
                if (g) {
                    i2 = i4;
                }
            }
            if (z2) {
                b();
                if (c(view) == null) {
                    return null;
                }
                w();
                this.w.onFocusSearchFailed(view, i2, this.l, this.ra);
                c(false);
            }
            view2 = instance.findNextFocus(this, view, i2);
        }
        if (view2 == null || view2.hasFocusable()) {
            return a(view, view2, i2) ? view2 : super.focusSearch(view, i2);
        }
        if (getFocusedChild() == null) {
            return super.focusSearch(view, i2);
        }
        a(view2, (View) null);
        return view;
    }

    public int g(View view) {
        v i2 = i(view);
        if (i2 != null) {
            return i2.getLayoutPosition();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        int i2;
        int i3;
        EdgeEffect edgeEffect;
        if (this.U == null) {
            this.U = this.R.a(this, 2);
            if (this.q) {
                edgeEffect = this.U;
                i3 = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
                i2 = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            } else {
                edgeEffect = this.U;
                i3 = getMeasuredHeight();
                i2 = getMeasuredWidth();
            }
            edgeEffect.setSize(i3, i2);
        }
    }

    public void g(int i2) {
    }

    /* access modifiers changed from: package-private */
    public void g(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int b2 = this.o.b();
        if (i2 < i3) {
            i6 = i2;
            i5 = i3;
            i4 = -1;
        } else {
            i5 = i2;
            i6 = i3;
            i4 = 1;
        }
        for (int i8 = 0; i8 < b2; i8++) {
            v i9 = i(this.o.d(i8));
            if (i9 != null && (i7 = i9.mPosition) >= i6 && i7 <= i5) {
                if (i7 == i2) {
                    i9.offsetPosition(i3 - i2, false);
                } else {
                    i9.offsetPosition(i4, false);
                }
                this.ra.g = true;
            }
        }
        this.l.b(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        i iVar = this.w;
        if (iVar != null) {
            return iVar.generateDefaultLayoutParams();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        i iVar = this.w;
        if (iVar != null) {
            return iVar.generateLayoutParams(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        i iVar = this.w;
        if (iVar != null) {
            return iVar.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    public a getAdapter() {
        return this.v;
    }

    public int getBaseline() {
        i iVar = this.w;
        return iVar != null ? iVar.getBaseline() : super.getBaseline();
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        d dVar = this.za;
        return dVar == null ? super.getChildDrawingOrder(i2, i3) : dVar.a(i2, i3);
    }

    public boolean getClipToPadding() {
        return this.q;
    }

    public H getCompatAccessibilityDelegate() {
        return this.ya;
    }

    public e getEdgeEffectFactory() {
        return this.R;
    }

    public f getItemAnimator() {
        return this.W;
    }

    public int getItemDecorationCount() {
        return this.y.size();
    }

    public i getLayoutManager() {
        return this.w;
    }

    public int getMaxFlingVelocity() {
        return this.ka;
    }

    public int getMinFlingVelocity() {
        return this.ja;
    }

    /* access modifiers changed from: package-private */
    public long getNanoTime() {
        if (f) {
            return System.nanoTime();
        }
        return 0;
    }

    public k getOnFlingListener() {
        return this.ia;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.na;
    }

    public n getRecycledViewPool() {
        return this.l.d();
    }

    public int getScrollState() {
        return this.aa;
    }

    public v h(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return i(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    /* access modifiers changed from: package-private */
    public void h() {
        int i2;
        int i3;
        EdgeEffect edgeEffect;
        if (this.T == null) {
            this.T = this.R.a(this, 1);
            if (this.q) {
                edgeEffect = this.T;
                i3 = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
                i2 = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
            } else {
                edgeEffect = this.T;
                i3 = getMeasuredWidth();
                i2 = getMeasuredHeight();
            }
            edgeEffect.setSize(i3, i2);
        }
    }

    public void h(int i2) {
        getScrollingChildHelper().c(i2);
    }

    public void h(int i2, int i3) {
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().a();
    }

    /* access modifiers changed from: package-private */
    public String i() {
        return " " + super.toString() + ", adapter:" + this.v + ", layout:" + this.w + ", context:" + getContext();
    }

    public void i(int i2, int i3) {
        a(i2, i3, (Interpolator) null);
    }

    public boolean isAttachedToWindow() {
        return this.B;
    }

    @Override // b.e.g.j
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().b();
    }

    /* access modifiers changed from: package-private */
    public Rect j(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.f984c) {
            return layoutParams.f983b;
        }
        if (this.ra.d() && (layoutParams.b() || layoutParams.d())) {
            return layoutParams.f983b;
        }
        Rect rect = layoutParams.f983b;
        rect.set(0, 0, 0, 0);
        int size = this.y.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.s.set(0, 0, 0, 0);
            this.y.get(i2).getItemOffsets(this.s, view, this, this.ra);
            int i3 = rect.left;
            Rect rect2 = this.s;
            rect.left = i3 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        layoutParams.f984c = false;
        return rect;
    }

    public boolean j() {
        return !this.E || this.N || this.n.c();
    }

    public boolean j(int i2, int i3) {
        return getScrollingChildHelper().a(i2, i3);
    }

    /* access modifiers changed from: package-private */
    public void k() {
        this.n = new C0096a(new D(this));
    }

    public void k(View view) {
    }

    /* access modifiers changed from: package-private */
    public void l() {
        this.V = null;
        this.T = null;
        this.U = null;
        this.S = null;
    }

    public void l(View view) {
    }

    /* access modifiers changed from: package-private */
    public boolean m() {
        AccessibilityManager accessibilityManager = this.L;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    /* access modifiers changed from: package-private */
    public boolean m(View view) {
        w();
        boolean e2 = this.o.e(view);
        if (e2) {
            v i2 = i(view);
            this.l.c(i2);
            this.l.b(i2);
        }
        c(!e2);
        return e2;
    }

    public boolean n() {
        return this.P > 0;
    }

    /* access modifiers changed from: package-private */
    public void o() {
        int b2 = this.o.b();
        for (int i2 = 0; i2 < b2; i2++) {
            ((LayoutParams) this.o.d(i2).getLayoutParams()).f984c = true;
        }
        this.l.g();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
        if (r0 >= 30.0f) goto L_0x0054;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onAttachedToWindow() {
        /*
        // Method dump skipped, instructions count: 104
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onAttachedToWindow():void");
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        p pVar;
        super.onDetachedFromWindow();
        f fVar = this.W;
        if (fVar != null) {
            fVar.b();
        }
        x();
        this.B = false;
        i iVar = this.w;
        if (iVar != null) {
            iVar.dispatchDetachedFromWindow(this, this.l);
        }
        this.Ga.clear();
        removeCallbacks(this.Ha);
        this.p.b();
        if (f && (pVar = this.pa) != null) {
            pVar.b(this);
            this.pa = null;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.y.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.y.get(i2).onDraw(canvas, this, this.ra);
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f2;
        float f3;
        if (this.w != null && !this.H && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f3 = this.w.canScrollVertically() ? -motionEvent.getAxisValue(9) : 0.0f;
                if (this.w.canScrollHorizontally()) {
                    f2 = motionEvent.getAxisValue(10);
                    if (!(f3 == 0.0f && f2 == 0.0f)) {
                        a((int) (f2 * this.la), (int) (f3 * this.ma), motionEvent);
                    }
                }
            } else {
                if ((motionEvent.getSource() & 4194304) != 0) {
                    float axisValue = motionEvent.getAxisValue(26);
                    if (this.w.canScrollVertically()) {
                        f3 = -axisValue;
                    } else if (this.w.canScrollHorizontally()) {
                        f2 = axisValue;
                        f3 = 0.0f;
                        a((int) (f2 * this.la), (int) (f3 * this.ma), motionEvent);
                    }
                }
                f3 = 0.0f;
            }
            f2 = 0.0f;
            a((int) (f2 * this.la), (int) (f3 * this.ma), motionEvent);
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        if (this.H) {
            return false;
        }
        if (b(motionEvent)) {
            y();
            return true;
        }
        i iVar = this.w;
        if (iVar == null) {
            return false;
        }
        boolean canScrollHorizontally = iVar.canScrollHorizontally();
        boolean canScrollVertically = this.w.canScrollVertically();
        if (this.ca == null) {
            this.ca = VelocityTracker.obtain();
        }
        this.ca.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.I) {
                this.I = false;
            }
            this.ba = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.fa = x2;
            this.da = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.ga = y2;
            this.ea = y2;
            if (this.aa == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
            }
            int[] iArr = this.Ea;
            iArr[1] = 0;
            iArr[0] = 0;
            int i2 = canScrollHorizontally ? 1 : 0;
            if (canScrollVertically) {
                i2 |= 2;
            }
            j(i2, 0);
        } else if (actionMasked == 1) {
            this.ca.clear();
            h(0);
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.ba);
            if (findPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.ba + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            if (this.aa != 1) {
                int i3 = x3 - this.da;
                int i4 = y3 - this.ea;
                if (!canScrollHorizontally || Math.abs(i3) <= this.ha) {
                    z2 = false;
                } else {
                    this.fa = x3;
                    z2 = true;
                }
                if (canScrollVertically && Math.abs(i4) > this.ha) {
                    this.ga = y3;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            y();
        } else if (actionMasked == 5) {
            this.ba = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.fa = x4;
            this.da = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.ga = y4;
            this.ea = y4;
        } else if (actionMasked == 6) {
            c(motionEvent);
        }
        return this.aa == 1;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        b.e.c.a.a("RV OnLayout");
        c();
        b.e.c.a.a();
        this.E = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        i iVar = this.w;
        if (iVar == null) {
            c(i2, i3);
            return;
        }
        boolean z2 = false;
        if (iVar.isAutoMeasureEnabled()) {
            int mode = View.MeasureSpec.getMode(i2);
            int mode2 = View.MeasureSpec.getMode(i3);
            this.w.onMeasure(this.l, this.ra, i2, i3);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z2 = true;
            }
            if (!z2 && this.v != null) {
                if (this.ra.e == 1) {
                    A();
                }
                this.w.setMeasureSpecs(i2, i3);
                this.ra.j = true;
                B();
                this.w.setMeasuredDimensionFromChildren(i2, i3);
                if (this.w.shouldMeasureTwice()) {
                    this.w.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.ra.j = true;
                    B();
                    this.w.setMeasuredDimensionFromChildren(i2, i3);
                }
            }
        } else if (this.C) {
            this.w.onMeasure(this.l, this.ra, i2, i3);
        } else {
            if (this.K) {
                w();
                q();
                I();
                r();
                s sVar = this.ra;
                if (sVar.l) {
                    sVar.h = true;
                } else {
                    this.n.b();
                    this.ra.h = false;
                }
                this.K = false;
                c(false);
            } else if (this.ra.l) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            a aVar = this.v;
            if (aVar != null) {
                this.ra.f = aVar.getItemCount();
            } else {
                this.ra.f = 0;
            }
            w();
            this.w.onMeasure(this.l, this.ra, i2, i3);
            c(false);
            this.ra.h = false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (n()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        this.m = (SavedState) parcelable;
        super.onRestoreInstanceState(this.m.a());
        i iVar = this.w;
        if (iVar != null && (parcelable2 = this.m.f986c) != null) {
            iVar.onRestoreInstanceState(parcelable2);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.m;
        if (savedState2 != null) {
            savedState.a(savedState2);
        } else {
            i iVar = this.w;
            savedState.f986c = iVar != null ? iVar.onSaveInstanceState() : null;
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4 || i3 != i5) {
            l();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        int i2;
        int i3;
        boolean z3 = false;
        if (this.H || this.I) {
            return false;
        }
        if (a(motionEvent)) {
            y();
            return true;
        }
        i iVar = this.w;
        if (iVar == null) {
            return false;
        }
        boolean canScrollHorizontally = iVar.canScrollHorizontally();
        boolean canScrollVertically = this.w.canScrollVertically();
        if (this.ca == null) {
            this.ca = VelocityTracker.obtain();
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            int[] iArr = this.Ea;
            iArr[1] = 0;
            iArr[0] = 0;
        }
        int[] iArr2 = this.Ea;
        obtain.offsetLocation((float) iArr2[0], (float) iArr2[1]);
        if (actionMasked == 0) {
            this.ba = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.fa = x2;
            this.da = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.ga = y2;
            this.ea = y2;
            int i4 = canScrollHorizontally ? 1 : 0;
            if (canScrollVertically) {
                i4 |= 2;
            }
            j(i4, 0);
        } else if (actionMasked == 1) {
            this.ca.addMovement(obtain);
            this.ca.computeCurrentVelocity(SAGUIDHelper.GUID_REQUEST_ID, (float) this.ka);
            float f2 = canScrollHorizontally ? -this.ca.getXVelocity(this.ba) : 0.0f;
            float f3 = canScrollVertically ? -this.ca.getYVelocity(this.ba) : 0.0f;
            if ((f2 == 0.0f && f3 == 0.0f) || !e((int) f2, (int) f3)) {
                setScrollState(0);
            }
            M();
            z3 = true;
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.ba);
            if (findPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.ba + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            int i5 = this.fa - x3;
            int i6 = this.ga - y3;
            if (a(i5, i6, this.Da, this.Ca, 0)) {
                int[] iArr3 = this.Da;
                i5 -= iArr3[0];
                i6 -= iArr3[1];
                int[] iArr4 = this.Ca;
                obtain.offsetLocation((float) iArr4[0], (float) iArr4[1]);
                int[] iArr5 = this.Ea;
                int i7 = iArr5[0];
                int[] iArr6 = this.Ca;
                iArr5[0] = i7 + iArr6[0];
                iArr5[1] = iArr5[1] + iArr6[1];
            }
            if (this.aa != 1) {
                if (!canScrollHorizontally || Math.abs(i5) <= (i3 = this.ha)) {
                    z2 = false;
                } else {
                    i5 = i5 > 0 ? i5 - i3 : i5 + i3;
                    z2 = true;
                }
                if (canScrollVertically && Math.abs(i6) > (i2 = this.ha)) {
                    i6 = i6 > 0 ? i6 - i2 : i6 + i2;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
            if (this.aa == 1) {
                int[] iArr7 = this.Ca;
                this.fa = x3 - iArr7[0];
                this.ga = y3 - iArr7[1];
                if (a(canScrollHorizontally ? i5 : 0, canScrollVertically ? i6 : 0, obtain)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (!(this.pa == null || (i5 == 0 && i6 == 0))) {
                    this.pa.a(this, i5, i6);
                }
            }
        } else if (actionMasked == 3) {
            y();
        } else if (actionMasked == 5) {
            this.ba = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.fa = x4;
            this.da = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.ga = y4;
            this.ea = y4;
        } else if (actionMasked == 6) {
            c(motionEvent);
        }
        if (!z3) {
            this.ca.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void p() {
        int b2 = this.o.b();
        for (int i2 = 0; i2 < b2; i2++) {
            v i3 = i(this.o.d(i2));
            if (i3 != null && !i3.shouldIgnore()) {
                i3.addFlags(6);
            }
        }
        o();
        this.l.h();
    }

    /* access modifiers changed from: package-private */
    public void q() {
        this.P++;
    }

    /* access modifiers changed from: package-private */
    public void r() {
        a(true);
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z2) {
        v i2 = i(view);
        if (i2 != null) {
            if (i2.isTmpDetached()) {
                i2.clearTmpDetachFlag();
            } else if (!i2.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + i2 + i());
            }
        }
        view.clearAnimation();
        b(view);
        super.removeDetachedView(view, z2);
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.w.onRequestChildFocus(this, this.ra, view, view2) && view2 != null) {
            a(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        return this.w.requestChildRectangleOnScreen(this, view, rect, z2);
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        int size = this.z.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.z.get(i2).a(z2);
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    public void requestLayout() {
        if (this.F != 0 || this.H) {
            this.G = true;
        } else {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void s() {
        if (!this.xa && this.B) {
            b.e.g.t.a(this, this.Ha);
            this.xa = true;
        }
    }

    public void scrollBy(int i2, int i3) {
        i iVar = this.w;
        if (iVar == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.H) {
            boolean canScrollHorizontally = iVar.canScrollHorizontally();
            boolean canScrollVertically = this.w.canScrollVertically();
            if (canScrollHorizontally || canScrollVertically) {
                if (!canScrollHorizontally) {
                    i2 = 0;
                }
                if (!canScrollVertically) {
                    i3 = 0;
                }
                a(i2, i3, (MotionEvent) null);
            }
        }
    }

    public void scrollTo(int i2, int i3) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!a(accessibilityEvent)) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public void setAccessibilityDelegateCompat(H h2) {
        this.ya = h2;
        b.e.g.t.a(this, this.ya);
    }

    public void setAdapter(a aVar) {
        setLayoutFrozen(false);
        a(aVar, false, true);
        b(false);
        requestLayout();
    }

    public void setChildDrawingOrderCallback(d dVar) {
        if (dVar != this.za) {
            this.za = dVar;
            setChildrenDrawingOrderEnabled(this.za != null);
        }
    }

    public void setClipToPadding(boolean z2) {
        if (z2 != this.q) {
            l();
        }
        this.q = z2;
        super.setClipToPadding(z2);
        if (this.E) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(e eVar) {
        b.e.f.h.a(eVar);
        this.R = eVar;
        l();
    }

    public void setHasFixedSize(boolean z2) {
        this.C = z2;
    }

    public void setItemAnimator(f fVar) {
        f fVar2 = this.W;
        if (fVar2 != null) {
            fVar2.b();
            this.W.a((f.b) null);
        }
        this.W = fVar;
        f fVar3 = this.W;
        if (fVar3 != null) {
            fVar3.a(this.wa);
        }
    }

    public void setItemViewCacheSize(int i2) {
        this.l.f(i2);
    }

    public void setLayoutFrozen(boolean z2) {
        if (z2 != this.H) {
            b("Do not setLayoutFrozen in layout or scroll");
            if (!z2) {
                this.H = false;
                if (!(!this.G || this.w == null || this.v == null)) {
                    requestLayout();
                }
                this.G = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
            this.H = true;
            this.I = true;
            x();
        }
    }

    public void setLayoutManager(i iVar) {
        if (iVar != this.w) {
            x();
            if (this.w != null) {
                f fVar = this.W;
                if (fVar != null) {
                    fVar.b();
                }
                this.w.removeAndRecycleAllViews(this.l);
                this.w.removeAndRecycleScrapInt(this.l);
                this.l.a();
                if (this.B) {
                    this.w.dispatchDetachedFromWindow(this, this.l);
                }
                this.w.setRecyclerView(null);
                this.w = null;
            } else {
                this.l.a();
            }
            this.o.c();
            this.w = iVar;
            if (iVar != null) {
                if (iVar.mRecyclerView == null) {
                    this.w.setRecyclerView(this);
                    if (this.B) {
                        this.w.dispatchAttachedToWindow(this);
                    }
                } else {
                    throw new IllegalArgumentException("LayoutManager " + iVar + " is already attached to a RecyclerView:" + iVar.mRecyclerView.i());
                }
            }
            this.l.j();
            requestLayout();
        }
    }

    public void setNestedScrollingEnabled(boolean z2) {
        getScrollingChildHelper().a(z2);
    }

    public void setOnFlingListener(k kVar) {
        this.ia = kVar;
    }

    @Deprecated
    public void setOnScrollListener(m mVar) {
        this.sa = mVar;
    }

    public void setPreserveFocusAfterLayout(boolean z2) {
        this.na = z2;
    }

    public void setRecycledViewPool(n nVar) {
        this.l.a(nVar);
    }

    public void setRecyclerListener(p pVar) {
        this.x = pVar;
    }

    /* access modifiers changed from: package-private */
    public void setScrollState(int i2) {
        if (i2 != this.aa) {
            this.aa = i2;
            if (i2 != 2) {
                O();
            }
            a(i2);
        }
    }

    public void setScrollingTouchSlop(int i2) {
        int i3;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i2 != 0) {
            if (i2 != 1) {
                Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i2 + "; using default value");
            } else {
                i3 = viewConfiguration.getScaledPagingTouchSlop();
                this.ha = i3;
            }
        }
        i3 = viewConfiguration.getScaledTouchSlop();
        this.ha = i3;
    }

    public void setViewCacheExtension(t tVar) {
        this.l.a(tVar);
    }

    public boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().b(i2);
    }

    @Override // b.e.g.j
    public void stopNestedScroll() {
        getScrollingChildHelper().c();
    }

    /* access modifiers changed from: package-private */
    public void t() {
        f fVar = this.W;
        if (fVar != null) {
            fVar.b();
        }
        i iVar = this.w;
        if (iVar != null) {
            iVar.removeAndRecycleAllViews(this.l);
            this.w.removeAndRecycleScrapInt(this.l);
        }
        this.l.a();
    }

    /* access modifiers changed from: package-private */
    public void u() {
        v vVar;
        int a2 = this.o.a();
        for (int i2 = 0; i2 < a2; i2++) {
            View c2 = this.o.c(i2);
            v h2 = h(c2);
            if (!(h2 == null || (vVar = h2.mShadowingHolder) == null)) {
                View view = vVar.itemView;
                int left = c2.getLeft();
                int top = c2.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void v() {
        int b2 = this.o.b();
        for (int i2 = 0; i2 < b2; i2++) {
            v i3 = i(this.o.d(i2));
            if (!i3.shouldIgnore()) {
                i3.saveOldPosition();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void w() {
        this.F++;
        if (this.F == 1 && !this.H) {
            this.G = false;
        }
    }

    public void x() {
        setScrollState(0);
        O();
    }
}
