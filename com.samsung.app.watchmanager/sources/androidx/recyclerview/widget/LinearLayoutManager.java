package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LinearLayoutManager extends RecyclerView.i implements q, RecyclerView.r.b {
    static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final a mAnchorInfo;
    private int mInitialPrefetchItemCount;
    private boolean mLastStackFromEnd;
    private final b mLayoutChunkResult;
    private c mLayoutState;
    int mOrientation;
    x mOrientationHelper;
    SavedState mPendingSavedState;
    int mPendingScrollPosition;
    int mPendingScrollPositionOffset;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout;
    boolean mShouldReverseLayout;
    private boolean mSmoothScrollbarEnabled;
    private boolean mStackFromEnd;

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new s();

        /* renamed from: a  reason: collision with root package name */
        int f951a;

        /* renamed from: b  reason: collision with root package name */
        int f952b;

        /* renamed from: c  reason: collision with root package name */
        boolean f953c;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.f951a = parcel.readInt();
            this.f952b = parcel.readInt();
            this.f953c = parcel.readInt() != 1 ? false : true;
        }

        public SavedState(SavedState savedState) {
            this.f951a = savedState.f951a;
            this.f952b = savedState.f952b;
            this.f953c = savedState.f953c;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return this.f951a >= 0;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f951a = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.f951a);
            parcel.writeInt(this.f952b);
            parcel.writeInt(this.f953c ? 1 : 0);
        }
    }

    /* access modifiers changed from: package-private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        x f954a;

        /* renamed from: b  reason: collision with root package name */
        int f955b;

        /* renamed from: c  reason: collision with root package name */
        int f956c;

        /* renamed from: d  reason: collision with root package name */
        boolean f957d;
        boolean e;

        a() {
            b();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.f956c = this.f957d ? this.f954a.b() : this.f954a.f();
        }

        public void a(View view, int i) {
            this.f956c = this.f957d ? this.f954a.a(view) + this.f954a.h() : this.f954a.d(view);
            this.f955b = i;
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, RecyclerView.s sVar) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            return !layoutParams.c() && layoutParams.a() >= 0 && layoutParams.a() < sVar.a();
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f955b = -1;
            this.f956c = LinearLayoutManager.INVALID_OFFSET;
            this.f957d = false;
            this.e = false;
        }

        public void b(View view, int i) {
            int h = this.f954a.h();
            if (h >= 0) {
                a(view, i);
                return;
            }
            this.f955b = i;
            if (this.f957d) {
                int b2 = (this.f954a.b() - h) - this.f954a.a(view);
                this.f956c = this.f954a.b() - b2;
                if (b2 > 0) {
                    int b3 = this.f956c - this.f954a.b(view);
                    int f = this.f954a.f();
                    int min = b3 - (f + Math.min(this.f954a.d(view) - f, 0));
                    if (min < 0) {
                        this.f956c += Math.min(b2, -min);
                        return;
                    }
                    return;
                }
                return;
            }
            int d2 = this.f954a.d(view);
            int f2 = d2 - this.f954a.f();
            this.f956c = d2;
            if (f2 > 0) {
                int b4 = (this.f954a.b() - Math.min(0, (this.f954a.b() - h) - this.f954a.a(view))) - (d2 + this.f954a.b(view));
                if (b4 < 0) {
                    this.f956c -= Math.min(f2, -b4);
                }
            }
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.f955b + ", mCoordinate=" + this.f956c + ", mLayoutFromEnd=" + this.f957d + ", mValid=" + this.e + '}';
        }
    }

    /* access modifiers changed from: protected */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        public int f958a;

        /* renamed from: b  reason: collision with root package name */
        public boolean f959b;

        /* renamed from: c  reason: collision with root package name */
        public boolean f960c;

        /* renamed from: d  reason: collision with root package name */
        public boolean f961d;

        protected b() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.f958a = 0;
            this.f959b = false;
            this.f960c = false;
            this.f961d = false;
        }
    }

    /* access modifiers changed from: package-private */
    public static class c {

        /* renamed from: a  reason: collision with root package name */
        boolean f962a = true;

        /* renamed from: b  reason: collision with root package name */
        int f963b;

        /* renamed from: c  reason: collision with root package name */
        int f964c;

        /* renamed from: d  reason: collision with root package name */
        int f965d;
        int e;
        int f;
        int g;
        int h = 0;
        boolean i = false;
        int j;
        List<RecyclerView.v> k = null;
        boolean l;

        c() {
        }

        private View b() {
            int size = this.k.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = this.k.get(i2).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (!layoutParams.c() && this.f965d == layoutParams.a()) {
                    a(view);
                    return view;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public View a(RecyclerView.o oVar) {
            if (this.k != null) {
                return b();
            }
            View d2 = oVar.d(this.f965d);
            this.f965d += this.e;
            return d2;
        }

        public void a() {
            a((View) null);
        }

        public void a(View view) {
            View b2 = b(view);
            this.f965d = b2 == null ? -1 : ((RecyclerView.LayoutParams) b2.getLayoutParams()).a();
        }

        /* access modifiers changed from: package-private */
        public boolean a(RecyclerView.s sVar) {
            int i2 = this.f965d;
            return i2 >= 0 && i2 < sVar.a();
        }

        public View b(View view) {
            int a2;
            int size = this.k.size();
            View view2 = null;
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < size; i3++) {
                View view3 = this.k.get(i3).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.c() && (a2 = (layoutParams.a() - this.f965d) * this.e) >= 0 && a2 < i2) {
                    if (a2 == 0) {
                        return view3;
                    }
                    view2 = view3;
                    i2 = a2;
                }
            }
            return view2;
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        this.mOrientation = 1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = INVALID_OFFSET;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new a();
        this.mLayoutChunkResult = new b();
        this.mInitialPrefetchItemCount = 2;
        setOrientation(i);
        setReverseLayout(z);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mOrientation = 1;
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = INVALID_OFFSET;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new a();
        this.mLayoutChunkResult = new b();
        this.mInitialPrefetchItemCount = 2;
        RecyclerView.i.b properties = RecyclerView.i.getProperties(context, attributeSet, i, i2);
        setOrientation(properties.f996a);
        setReverseLayout(properties.f998c);
        setStackFromEnd(properties.f999d);
    }

    private int computeScrollExtent(RecyclerView.s sVar) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return I.a(sVar, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.s sVar) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return I.a(sVar, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollRange(RecyclerView.s sVar) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        return I.b(sVar, this.mOrientationHelper, findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true), findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    private View findFirstPartiallyOrCompletelyInvisibleChild(RecyclerView.o oVar, RecyclerView.s sVar) {
        return findOnePartiallyOrCompletelyInvisibleChild(0, getChildCount());
    }

    private View findFirstReferenceChild(RecyclerView.o oVar, RecyclerView.s sVar) {
        return findReferenceChild(oVar, sVar, 0, getChildCount(), sVar.a());
    }

    private View findFirstVisibleChildClosestToEnd(boolean z, boolean z2) {
        int childCount;
        int i;
        if (this.mShouldReverseLayout) {
            childCount = 0;
            i = getChildCount();
        } else {
            childCount = getChildCount() - 1;
            i = -1;
        }
        return findOneVisibleChild(childCount, i, z, z2);
    }

    private View findFirstVisibleChildClosestToStart(boolean z, boolean z2) {
        int i;
        int childCount;
        if (this.mShouldReverseLayout) {
            i = getChildCount() - 1;
            childCount = -1;
        } else {
            i = 0;
            childCount = getChildCount();
        }
        return findOneVisibleChild(i, childCount, z, z2);
    }

    private View findLastPartiallyOrCompletelyInvisibleChild(RecyclerView.o oVar, RecyclerView.s sVar) {
        return findOnePartiallyOrCompletelyInvisibleChild(getChildCount() - 1, -1);
    }

    private View findLastReferenceChild(RecyclerView.o oVar, RecyclerView.s sVar) {
        return findReferenceChild(oVar, sVar, getChildCount() - 1, -1, sVar.a());
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToEnd(RecyclerView.o oVar, RecyclerView.s sVar) {
        return this.mShouldReverseLayout ? findFirstPartiallyOrCompletelyInvisibleChild(oVar, sVar) : findLastPartiallyOrCompletelyInvisibleChild(oVar, sVar);
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToStart(RecyclerView.o oVar, RecyclerView.s sVar) {
        return this.mShouldReverseLayout ? findLastPartiallyOrCompletelyInvisibleChild(oVar, sVar) : findFirstPartiallyOrCompletelyInvisibleChild(oVar, sVar);
    }

    private View findReferenceChildClosestToEnd(RecyclerView.o oVar, RecyclerView.s sVar) {
        return this.mShouldReverseLayout ? findFirstReferenceChild(oVar, sVar) : findLastReferenceChild(oVar, sVar);
    }

    private View findReferenceChildClosestToStart(RecyclerView.o oVar, RecyclerView.s sVar) {
        return this.mShouldReverseLayout ? findLastReferenceChild(oVar, sVar) : findFirstReferenceChild(oVar, sVar);
    }

    private int fixLayoutEndGap(int i, RecyclerView.o oVar, RecyclerView.s sVar, boolean z) {
        int b2;
        int b3 = this.mOrientationHelper.b() - i;
        if (b3 <= 0) {
            return 0;
        }
        int i2 = -scrollBy(-b3, oVar, sVar);
        int i3 = i + i2;
        if (!z || (b2 = this.mOrientationHelper.b() - i3) <= 0) {
            return i2;
        }
        this.mOrientationHelper.a(b2);
        return b2 + i2;
    }

    private int fixLayoutStartGap(int i, RecyclerView.o oVar, RecyclerView.s sVar, boolean z) {
        int f;
        int f2 = i - this.mOrientationHelper.f();
        if (f2 <= 0) {
            return 0;
        }
        int i2 = -scrollBy(f2, oVar, sVar);
        int i3 = i + i2;
        if (!z || (f = i3 - this.mOrientationHelper.f()) <= 0) {
            return i2;
        }
        this.mOrientationHelper.a(-f);
        return i2 - f;
    }

    private View getChildClosestToEnd() {
        return getChildAt(this.mShouldReverseLayout ? 0 : getChildCount() - 1);
    }

    private View getChildClosestToStart() {
        return getChildAt(this.mShouldReverseLayout ? getChildCount() - 1 : 0);
    }

    private void layoutForPredictiveAnimations(RecyclerView.o oVar, RecyclerView.s sVar, int i, int i2) {
        if (sVar.e() && getChildCount() != 0 && !sVar.d() && supportsPredictiveItemAnimations()) {
            List<RecyclerView.v> f = oVar.f();
            int size = f.size();
            int position = getPosition(getChildAt(0));
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                RecyclerView.v vVar = f.get(i5);
                if (!vVar.isRemoved()) {
                    char c2 = 1;
                    if ((vVar.getLayoutPosition() < position) != this.mShouldReverseLayout) {
                        c2 = 65535;
                    }
                    if (c2 == 65535) {
                        i3 += this.mOrientationHelper.b(vVar.itemView);
                    } else {
                        i4 += this.mOrientationHelper.b(vVar.itemView);
                    }
                }
            }
            this.mLayoutState.k = f;
            if (i3 > 0) {
                updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), i);
                c cVar = this.mLayoutState;
                cVar.h = i3;
                cVar.f964c = 0;
                cVar.a();
                fill(oVar, this.mLayoutState, sVar, false);
            }
            if (i4 > 0) {
                updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), i2);
                c cVar2 = this.mLayoutState;
                cVar2.h = i4;
                cVar2.f964c = 0;
                cVar2.a();
                fill(oVar, this.mLayoutState, sVar, false);
            }
            this.mLayoutState.k = null;
        }
    }

    private void logChildren() {
        Log.d(TAG, "internal representation of views on the screen");
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Log.d(TAG, "item " + getPosition(childAt) + ", coord:" + this.mOrientationHelper.d(childAt));
        }
        Log.d(TAG, "==============");
    }

    private void recycleByLayoutState(RecyclerView.o oVar, c cVar) {
        if (cVar.f962a && !cVar.l) {
            if (cVar.f == -1) {
                recycleViewsFromEnd(oVar, cVar.g);
            } else {
                recycleViewsFromStart(oVar, cVar.g);
            }
        }
    }

    private void recycleChildren(RecyclerView.o oVar, int i, int i2) {
        if (i != i2) {
            if (i2 > i) {
                for (int i3 = i2 - 1; i3 >= i; i3--) {
                    removeAndRecycleViewAt(i3, oVar);
                }
                return;
            }
            while (i > i2) {
                removeAndRecycleViewAt(i, oVar);
                i--;
            }
        }
    }

    private void recycleViewsFromEnd(RecyclerView.o oVar, int i) {
        int childCount = getChildCount();
        if (i >= 0) {
            int a2 = this.mOrientationHelper.a() - i;
            if (this.mShouldReverseLayout) {
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    if (this.mOrientationHelper.d(childAt) < a2 || this.mOrientationHelper.f(childAt) < a2) {
                        recycleChildren(oVar, 0, i2);
                        return;
                    }
                }
                return;
            }
            int i3 = childCount - 1;
            for (int i4 = i3; i4 >= 0; i4--) {
                View childAt2 = getChildAt(i4);
                if (this.mOrientationHelper.d(childAt2) < a2 || this.mOrientationHelper.f(childAt2) < a2) {
                    recycleChildren(oVar, i3, i4);
                    return;
                }
            }
        }
    }

    private void recycleViewsFromStart(RecyclerView.o oVar, int i) {
        if (i >= 0) {
            int childCount = getChildCount();
            if (this.mShouldReverseLayout) {
                int i2 = childCount - 1;
                for (int i3 = i2; i3 >= 0; i3--) {
                    View childAt = getChildAt(i3);
                    if (this.mOrientationHelper.a(childAt) > i || this.mOrientationHelper.e(childAt) > i) {
                        recycleChildren(oVar, i2, i3);
                        return;
                    }
                }
                return;
            }
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt2 = getChildAt(i4);
                if (this.mOrientationHelper.a(childAt2) > i || this.mOrientationHelper.e(childAt2) > i) {
                    recycleChildren(oVar, 0, i4);
                    return;
                }
            }
        }
    }

    private void resolveShouldLayoutReverse() {
        this.mShouldReverseLayout = (this.mOrientation == 1 || !isLayoutRTL()) ? this.mReverseLayout : !this.mReverseLayout;
    }

    private boolean updateAnchorFromChildren(RecyclerView.o oVar, RecyclerView.s sVar, a aVar) {
        boolean z = false;
        if (getChildCount() == 0) {
            return false;
        }
        View focusedChild = getFocusedChild();
        if (focusedChild != null && aVar.a(focusedChild, sVar)) {
            aVar.b(focusedChild, getPosition(focusedChild));
            return true;
        } else if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        } else {
            View findReferenceChildClosestToEnd = aVar.f957d ? findReferenceChildClosestToEnd(oVar, sVar) : findReferenceChildClosestToStart(oVar, sVar);
            if (findReferenceChildClosestToEnd == null) {
                return false;
            }
            aVar.a(findReferenceChildClosestToEnd, getPosition(findReferenceChildClosestToEnd));
            if (!sVar.d() && supportsPredictiveItemAnimations()) {
                if (this.mOrientationHelper.d(findReferenceChildClosestToEnd) >= this.mOrientationHelper.b() || this.mOrientationHelper.a(findReferenceChildClosestToEnd) < this.mOrientationHelper.f()) {
                    z = true;
                }
                if (z) {
                    aVar.f956c = aVar.f957d ? this.mOrientationHelper.b() : this.mOrientationHelper.f();
                }
            }
            return true;
        }
    }

    private boolean updateAnchorFromPendingData(RecyclerView.s sVar, a aVar) {
        int i;
        boolean z = false;
        if (!sVar.d() && (i = this.mPendingScrollPosition) != -1) {
            if (i < 0 || i >= sVar.a()) {
                this.mPendingScrollPosition = -1;
                this.mPendingScrollPositionOffset = INVALID_OFFSET;
            } else {
                aVar.f955b = this.mPendingScrollPosition;
                SavedState savedState = this.mPendingSavedState;
                if (savedState != null && savedState.a()) {
                    aVar.f957d = this.mPendingSavedState.f953c;
                    aVar.f956c = aVar.f957d ? this.mOrientationHelper.b() - this.mPendingSavedState.f952b : this.mOrientationHelper.f() + this.mPendingSavedState.f952b;
                    return true;
                } else if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                    View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
                    if (findViewByPosition == null) {
                        if (getChildCount() > 0) {
                            if ((this.mPendingScrollPosition < getPosition(getChildAt(0))) == this.mShouldReverseLayout) {
                                z = true;
                            }
                            aVar.f957d = z;
                        }
                        aVar.a();
                    } else if (this.mOrientationHelper.b(findViewByPosition) > this.mOrientationHelper.g()) {
                        aVar.a();
                        return true;
                    } else if (this.mOrientationHelper.d(findViewByPosition) - this.mOrientationHelper.f() < 0) {
                        aVar.f956c = this.mOrientationHelper.f();
                        aVar.f957d = false;
                        return true;
                    } else if (this.mOrientationHelper.b() - this.mOrientationHelper.a(findViewByPosition) < 0) {
                        aVar.f956c = this.mOrientationHelper.b();
                        aVar.f957d = true;
                        return true;
                    } else {
                        aVar.f956c = aVar.f957d ? this.mOrientationHelper.a(findViewByPosition) + this.mOrientationHelper.h() : this.mOrientationHelper.d(findViewByPosition);
                    }
                    return true;
                } else {
                    boolean z2 = this.mShouldReverseLayout;
                    aVar.f957d = z2;
                    aVar.f956c = z2 ? this.mOrientationHelper.b() - this.mPendingScrollPositionOffset : this.mOrientationHelper.f() + this.mPendingScrollPositionOffset;
                    return true;
                }
            }
        }
        return false;
    }

    private void updateAnchorInfoForLayout(RecyclerView.o oVar, RecyclerView.s sVar, a aVar) {
        if (!updateAnchorFromPendingData(sVar, aVar) && !updateAnchorFromChildren(oVar, sVar, aVar)) {
            aVar.a();
            aVar.f955b = this.mStackFromEnd ? sVar.a() - 1 : 0;
        }
    }

    private void updateLayoutState(int i, int i2, boolean z, RecyclerView.s sVar) {
        int i3;
        this.mLayoutState.l = resolveIsInfinite();
        this.mLayoutState.h = getExtraLayoutSpace(sVar);
        c cVar = this.mLayoutState;
        cVar.f = i;
        int i4 = -1;
        if (i == 1) {
            cVar.h += this.mOrientationHelper.c();
            View childClosestToEnd = getChildClosestToEnd();
            c cVar2 = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                i4 = 1;
            }
            cVar2.e = i4;
            c cVar3 = this.mLayoutState;
            int position = getPosition(childClosestToEnd);
            c cVar4 = this.mLayoutState;
            cVar3.f965d = position + cVar4.e;
            cVar4.f963b = this.mOrientationHelper.a(childClosestToEnd);
            i3 = this.mOrientationHelper.a(childClosestToEnd) - this.mOrientationHelper.b();
        } else {
            View childClosestToStart = getChildClosestToStart();
            this.mLayoutState.h += this.mOrientationHelper.f();
            c cVar5 = this.mLayoutState;
            if (this.mShouldReverseLayout) {
                i4 = 1;
            }
            cVar5.e = i4;
            c cVar6 = this.mLayoutState;
            int position2 = getPosition(childClosestToStart);
            c cVar7 = this.mLayoutState;
            cVar6.f965d = position2 + cVar7.e;
            cVar7.f963b = this.mOrientationHelper.d(childClosestToStart);
            i3 = (-this.mOrientationHelper.d(childClosestToStart)) + this.mOrientationHelper.f();
        }
        c cVar8 = this.mLayoutState;
        cVar8.f964c = i2;
        if (z) {
            cVar8.f964c -= i3;
        }
        this.mLayoutState.g = i3;
    }

    private void updateLayoutStateToFillEnd(int i, int i2) {
        this.mLayoutState.f964c = this.mOrientationHelper.b() - i2;
        this.mLayoutState.e = this.mShouldReverseLayout ? -1 : 1;
        c cVar = this.mLayoutState;
        cVar.f965d = i;
        cVar.f = 1;
        cVar.f963b = i2;
        cVar.g = INVALID_OFFSET;
    }

    private void updateLayoutStateToFillEnd(a aVar) {
        updateLayoutStateToFillEnd(aVar.f955b, aVar.f956c);
    }

    private void updateLayoutStateToFillStart(int i, int i2) {
        this.mLayoutState.f964c = i2 - this.mOrientationHelper.f();
        c cVar = this.mLayoutState;
        cVar.f965d = i;
        cVar.e = this.mShouldReverseLayout ? 1 : -1;
        c cVar2 = this.mLayoutState;
        cVar2.f = -1;
        cVar2.f963b = i2;
        cVar2.g = INVALID_OFFSET;
    }

    private void updateLayoutStateToFillStart(a aVar) {
        updateLayoutStateToFillStart(aVar.f955b, aVar.f956c);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.s sVar, RecyclerView.i.a aVar) {
        if (this.mOrientation != 0) {
            i = i2;
        }
        if (getChildCount() != 0 && i != 0) {
            ensureLayoutState();
            updateLayoutState(i > 0 ? 1 : -1, Math.abs(i), true, sVar);
            collectPrefetchPositionsForLayoutState(sVar, this.mLayoutState, aVar);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void collectInitialPrefetchPositions(int i, RecyclerView.i.a aVar) {
        boolean z;
        int i2;
        SavedState savedState = this.mPendingSavedState;
        int i3 = -1;
        if (savedState == null || !savedState.a()) {
            resolveShouldLayoutReverse();
            z = this.mShouldReverseLayout;
            i2 = this.mPendingScrollPosition;
            if (i2 == -1) {
                i2 = z ? i - 1 : 0;
            }
        } else {
            SavedState savedState2 = this.mPendingSavedState;
            z = savedState2.f953c;
            i2 = savedState2.f951a;
        }
        if (!z) {
            i3 = 1;
        }
        int i4 = i2;
        for (int i5 = 0; i5 < this.mInitialPrefetchItemCount && i4 >= 0 && i4 < i; i5++) {
            aVar.a(i4, 0);
            i4 += i3;
        }
    }

    /* access modifiers changed from: package-private */
    public void collectPrefetchPositionsForLayoutState(RecyclerView.s sVar, c cVar, RecyclerView.i.a aVar) {
        int i = cVar.f965d;
        if (i >= 0 && i < sVar.a()) {
            aVar.a(i, Math.max(0, cVar.g));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int computeHorizontalScrollExtent(RecyclerView.s sVar) {
        return computeScrollExtent(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int computeHorizontalScrollOffset(RecyclerView.s sVar) {
        return computeScrollOffset(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int computeHorizontalScrollRange(RecyclerView.s sVar) {
        return computeScrollRange(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.r.b
    public PointF computeScrollVectorForPosition(int i) {
        if (getChildCount() == 0) {
            return null;
        }
        boolean z = false;
        int i2 = 1;
        if (i < getPosition(getChildAt(0))) {
            z = true;
        }
        if (z != this.mShouldReverseLayout) {
            i2 = -1;
        }
        return this.mOrientation == 0 ? new PointF((float) i2, 0.0f) : new PointF(0.0f, (float) i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int computeVerticalScrollExtent(RecyclerView.s sVar) {
        return computeScrollExtent(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int computeVerticalScrollOffset(RecyclerView.s sVar) {
        return computeScrollOffset(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int computeVerticalScrollRange(RecyclerView.s sVar) {
        return computeScrollRange(sVar);
    }

    /* access modifiers changed from: package-private */
    public int convertFocusDirectionToLayoutDirection(int i) {
        if (i == 1) {
            return (this.mOrientation != 1 && isLayoutRTL()) ? 1 : -1;
        }
        if (i == 2) {
            return (this.mOrientation != 1 && isLayoutRTL()) ? -1 : 1;
        }
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i == 130 && this.mOrientation == 1) {
                        return 1;
                    }
                    return INVALID_OFFSET;
                } else if (this.mOrientation == 0) {
                    return 1;
                } else {
                    return INVALID_OFFSET;
                }
            } else if (this.mOrientation == 1) {
                return -1;
            } else {
                return INVALID_OFFSET;
            }
        } else if (this.mOrientation == 0) {
            return -1;
        } else {
            return INVALID_OFFSET;
        }
    }

    /* access modifiers changed from: package-private */
    public c createLayoutState() {
        return new c();
    }

    /* access modifiers changed from: package-private */
    public void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = createLayoutState();
        }
    }

    /* access modifiers changed from: package-private */
    public int fill(RecyclerView.o oVar, c cVar, RecyclerView.s sVar, boolean z) {
        int i = cVar.f964c;
        int i2 = cVar.g;
        if (i2 != Integer.MIN_VALUE) {
            if (i < 0) {
                cVar.g = i2 + i;
            }
            recycleByLayoutState(oVar, cVar);
        }
        int i3 = cVar.f964c + cVar.h;
        b bVar = this.mLayoutChunkResult;
        while (true) {
            if ((!cVar.l && i3 <= 0) || !cVar.a(sVar)) {
                break;
            }
            bVar.a();
            layoutChunk(oVar, sVar, cVar, bVar);
            if (!bVar.f959b) {
                cVar.f963b += bVar.f958a * cVar.f;
                if (!bVar.f960c || this.mLayoutState.k != null || !sVar.d()) {
                    int i4 = cVar.f964c;
                    int i5 = bVar.f958a;
                    cVar.f964c = i4 - i5;
                    i3 -= i5;
                }
                int i6 = cVar.g;
                if (i6 != Integer.MIN_VALUE) {
                    cVar.g = i6 + bVar.f958a;
                    int i7 = cVar.f964c;
                    if (i7 < 0) {
                        cVar.g += i7;
                    }
                    recycleByLayoutState(oVar, cVar);
                }
                if (z && bVar.f961d) {
                    break;
                }
            } else {
                break;
            }
        }
        return i - cVar.f964c;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), true, false);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    public int findFirstVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), false, true);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    public int findLastVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, false, true);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    /* access modifiers changed from: package-private */
    public View findOnePartiallyOrCompletelyInvisibleChild(int i, int i2) {
        int i3;
        int i4;
        ensureLayoutState();
        if ((i2 > i ? 1 : i2 < i ? (char) 65535 : 0) == 0) {
            return getChildAt(i);
        }
        if (this.mOrientationHelper.d(getChildAt(i)) < this.mOrientationHelper.f()) {
            i4 = 16644;
            i3 = 16388;
        } else {
            i4 = 4161;
            i3 = 4097;
        }
        return (this.mOrientation == 0 ? this.mHorizontalBoundCheck : this.mVerticalBoundCheck).a(i, i2, i4, i3);
    }

    /* access modifiers changed from: package-private */
    public View findOneVisibleChild(int i, int i2, boolean z, boolean z2) {
        ensureLayoutState();
        int i3 = 320;
        int i4 = z ? 24579 : 320;
        if (!z2) {
            i3 = 0;
        }
        return (this.mOrientation == 0 ? this.mHorizontalBoundCheck : this.mVerticalBoundCheck).a(i, i2, i4, i3);
    }

    /* access modifiers changed from: package-private */
    public View findReferenceChild(RecyclerView.o oVar, RecyclerView.s sVar, int i, int i2, int i3) {
        ensureLayoutState();
        int f = this.mOrientationHelper.f();
        int b2 = this.mOrientationHelper.b();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).c()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.d(childAt) < b2 && this.mOrientationHelper.a(childAt) >= f) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public View findViewByPosition(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i - getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (getPosition(childAt) == i) {
                return childAt;
            }
        }
        return super.findViewByPosition(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public int getExtraLayoutSpace(RecyclerView.s sVar) {
        if (sVar.c()) {
            return this.mOrientationHelper.g();
        }
        return 0;
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    /* access modifiers changed from: package-private */
    public void layoutChunk(RecyclerView.o oVar, RecyclerView.s sVar, c cVar, b bVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        View a2 = cVar.a(oVar);
        if (a2 == null) {
            bVar.f959b = true;
            return;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) a2.getLayoutParams();
        if (cVar.k == null) {
            if (this.mShouldReverseLayout == (cVar.f == -1)) {
                addView(a2);
            } else {
                addView(a2, 0);
            }
        } else {
            if (this.mShouldReverseLayout == (cVar.f == -1)) {
                addDisappearingView(a2);
            } else {
                addDisappearingView(a2, 0);
            }
        }
        measureChildWithMargins(a2, 0, 0);
        bVar.f958a = this.mOrientationHelper.b(a2);
        if (this.mOrientation == 1) {
            if (isLayoutRTL()) {
                i5 = getWidth() - getPaddingRight();
                i4 = i5 - this.mOrientationHelper.c(a2);
            } else {
                i4 = getPaddingLeft();
                i5 = this.mOrientationHelper.c(a2) + i4;
            }
            if (cVar.f == -1) {
                int i6 = cVar.f963b;
                i = i6;
                i2 = i5;
                i3 = i6 - bVar.f958a;
            } else {
                int i7 = cVar.f963b;
                i3 = i7;
                i2 = i5;
                i = bVar.f958a + i7;
            }
        } else {
            int paddingTop = getPaddingTop();
            int c2 = this.mOrientationHelper.c(a2) + paddingTop;
            if (cVar.f == -1) {
                int i8 = cVar.f963b;
                i2 = i8;
                i3 = paddingTop;
                i = c2;
                i4 = i8 - bVar.f958a;
            } else {
                int i9 = cVar.f963b;
                i3 = paddingTop;
                i2 = bVar.f958a + i9;
                i = c2;
                i4 = i9;
            }
        }
        layoutDecoratedWithMargins(a2, i4, i3, i2, i);
        if (layoutParams.c() || layoutParams.b()) {
            bVar.f960c = true;
        }
        bVar.f961d = a2.hasFocusable();
    }

    /* access modifiers changed from: package-private */
    public void onAnchorReady(RecyclerView.o oVar, RecyclerView.s sVar, a aVar, int i) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.o oVar) {
        super.onDetachedFromWindow(recyclerView, oVar);
        if (this.mRecycleChildrenOnDetach) {
            removeAndRecycleAllViews(oVar);
            oVar.a();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public View onFocusSearchFailed(View view, int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        int convertFocusDirectionToLayoutDirection;
        resolveShouldLayoutReverse();
        if (getChildCount() == 0 || (convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i)) == Integer.MIN_VALUE) {
            return null;
        }
        ensureLayoutState();
        ensureLayoutState();
        updateLayoutState(convertFocusDirectionToLayoutDirection, (int) (((float) this.mOrientationHelper.g()) * MAX_SCROLL_FACTOR), false, sVar);
        c cVar = this.mLayoutState;
        cVar.g = INVALID_OFFSET;
        cVar.f962a = false;
        fill(oVar, cVar, sVar, true);
        View findPartiallyOrCompletelyInvisibleChildClosestToStart = convertFocusDirectionToLayoutDirection == -1 ? findPartiallyOrCompletelyInvisibleChildClosestToStart(oVar, sVar) : findPartiallyOrCompletelyInvisibleChildClosestToEnd(oVar, sVar);
        View childClosestToStart = convertFocusDirectionToLayoutDirection == -1 ? getChildClosestToStart() : getChildClosestToEnd();
        if (!childClosestToStart.hasFocusable()) {
            return findPartiallyOrCompletelyInvisibleChildClosestToStart;
        }
        if (findPartiallyOrCompletelyInvisibleChildClosestToStart == null) {
            return null;
        }
        return childClosestToStart;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(findLastVisibleItemPosition());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onLayoutChildren(RecyclerView.o oVar, RecyclerView.s sVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        View findViewByPosition;
        int i8;
        int i9;
        int i10 = -1;
        if (!(this.mPendingSavedState == null && this.mPendingScrollPosition == -1) && sVar.a() == 0) {
            removeAndRecycleAllViews(oVar);
            return;
        }
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null && savedState.a()) {
            this.mPendingScrollPosition = this.mPendingSavedState.f951a;
        }
        ensureLayoutState();
        this.mLayoutState.f962a = false;
        resolveShouldLayoutReverse();
        View focusedChild = getFocusedChild();
        if (!this.mAnchorInfo.e || this.mPendingScrollPosition != -1 || this.mPendingSavedState != null) {
            this.mAnchorInfo.b();
            a aVar = this.mAnchorInfo;
            aVar.f957d = this.mShouldReverseLayout ^ this.mStackFromEnd;
            updateAnchorInfoForLayout(oVar, sVar, aVar);
            this.mAnchorInfo.e = true;
        } else if (focusedChild != null && (this.mOrientationHelper.d(focusedChild) >= this.mOrientationHelper.b() || this.mOrientationHelper.a(focusedChild) <= this.mOrientationHelper.f())) {
            this.mAnchorInfo.b(focusedChild, getPosition(focusedChild));
        }
        int extraLayoutSpace = getExtraLayoutSpace(sVar);
        if (this.mLayoutState.j >= 0) {
            i = extraLayoutSpace;
            extraLayoutSpace = 0;
        } else {
            i = 0;
        }
        int f = extraLayoutSpace + this.mOrientationHelper.f();
        int c2 = i + this.mOrientationHelper.c();
        if (!(!sVar.d() || (i7 = this.mPendingScrollPosition) == -1 || this.mPendingScrollPositionOffset == Integer.MIN_VALUE || (findViewByPosition = findViewByPosition(i7)) == null)) {
            if (this.mShouldReverseLayout) {
                i8 = this.mOrientationHelper.b() - this.mOrientationHelper.a(findViewByPosition);
                i9 = this.mPendingScrollPositionOffset;
            } else {
                i9 = this.mOrientationHelper.d(findViewByPosition) - this.mOrientationHelper.f();
                i8 = this.mPendingScrollPositionOffset;
            }
            int i11 = i8 - i9;
            if (i11 > 0) {
                f += i11;
            } else {
                c2 -= i11;
            }
        }
        if (!this.mAnchorInfo.f957d ? !this.mShouldReverseLayout : this.mShouldReverseLayout) {
            i10 = 1;
        }
        onAnchorReady(oVar, sVar, this.mAnchorInfo, i10);
        detachAndScrapAttachedViews(oVar);
        this.mLayoutState.l = resolveIsInfinite();
        this.mLayoutState.i = sVar.d();
        a aVar2 = this.mAnchorInfo;
        if (aVar2.f957d) {
            updateLayoutStateToFillStart(aVar2);
            c cVar = this.mLayoutState;
            cVar.h = f;
            fill(oVar, cVar, sVar, false);
            c cVar2 = this.mLayoutState;
            i3 = cVar2.f963b;
            int i12 = cVar2.f965d;
            int i13 = cVar2.f964c;
            if (i13 > 0) {
                c2 += i13;
            }
            updateLayoutStateToFillEnd(this.mAnchorInfo);
            c cVar3 = this.mLayoutState;
            cVar3.h = c2;
            cVar3.f965d += cVar3.e;
            fill(oVar, cVar3, sVar, false);
            c cVar4 = this.mLayoutState;
            i2 = cVar4.f963b;
            int i14 = cVar4.f964c;
            if (i14 > 0) {
                updateLayoutStateToFillStart(i12, i3);
                c cVar5 = this.mLayoutState;
                cVar5.h = i14;
                fill(oVar, cVar5, sVar, false);
                i3 = this.mLayoutState.f963b;
            }
        } else {
            updateLayoutStateToFillEnd(aVar2);
            c cVar6 = this.mLayoutState;
            cVar6.h = c2;
            fill(oVar, cVar6, sVar, false);
            c cVar7 = this.mLayoutState;
            i2 = cVar7.f963b;
            int i15 = cVar7.f965d;
            int i16 = cVar7.f964c;
            if (i16 > 0) {
                f += i16;
            }
            updateLayoutStateToFillStart(this.mAnchorInfo);
            c cVar8 = this.mLayoutState;
            cVar8.h = f;
            cVar8.f965d += cVar8.e;
            fill(oVar, cVar8, sVar, false);
            c cVar9 = this.mLayoutState;
            i3 = cVar9.f963b;
            int i17 = cVar9.f964c;
            if (i17 > 0) {
                updateLayoutStateToFillEnd(i15, i2);
                c cVar10 = this.mLayoutState;
                cVar10.h = i17;
                fill(oVar, cVar10, sVar, false);
                i2 = this.mLayoutState.f963b;
            }
        }
        if (getChildCount() > 0) {
            if (this.mShouldReverseLayout ^ this.mStackFromEnd) {
                int fixLayoutEndGap = fixLayoutEndGap(i2, oVar, sVar, true);
                i5 = i3 + fixLayoutEndGap;
                i4 = i2 + fixLayoutEndGap;
                i6 = fixLayoutStartGap(i5, oVar, sVar, false);
            } else {
                int fixLayoutStartGap = fixLayoutStartGap(i3, oVar, sVar, true);
                i5 = i3 + fixLayoutStartGap;
                i4 = i2 + fixLayoutStartGap;
                i6 = fixLayoutEndGap(i4, oVar, sVar, false);
            }
            i3 = i5 + i6;
            i2 = i4 + i6;
        }
        layoutForPredictiveAnimations(oVar, sVar, i3, i2);
        if (!sVar.d()) {
            this.mOrientationHelper.i();
        } else {
            this.mAnchorInfo.b();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onLayoutCompleted(RecyclerView.s sVar) {
        super.onLayoutCompleted(sVar);
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = INVALID_OFFSET;
        this.mAnchorInfo.b();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState) parcelable;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public Parcelable onSaveInstanceState() {
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        if (getChildCount() > 0) {
            ensureLayoutState();
            boolean z = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            savedState2.f953c = z;
            if (z) {
                View childClosestToEnd = getChildClosestToEnd();
                savedState2.f952b = this.mOrientationHelper.b() - this.mOrientationHelper.a(childClosestToEnd);
                savedState2.f951a = getPosition(childClosestToEnd);
            } else {
                View childClosestToStart = getChildClosestToStart();
                savedState2.f951a = getPosition(childClosestToStart);
                savedState2.f952b = this.mOrientationHelper.d(childClosestToStart) - this.mOrientationHelper.f();
            }
        } else {
            savedState2.b();
        }
        return savedState2;
    }

    public void prepareForDrop(View view, View view2, int i, int i2) {
        int i3;
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        ensureLayoutState();
        resolveShouldLayoutReverse();
        int position = getPosition(view);
        int position2 = getPosition(view2);
        char c2 = position < position2 ? (char) 1 : 65535;
        if (this.mShouldReverseLayout) {
            if (c2 == 1) {
                scrollToPositionWithOffset(position2, this.mOrientationHelper.b() - (this.mOrientationHelper.d(view2) + this.mOrientationHelper.b(view)));
                return;
            }
            i3 = this.mOrientationHelper.b() - this.mOrientationHelper.a(view2);
        } else if (c2 == 65535) {
            i3 = this.mOrientationHelper.d(view2);
        } else {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.a(view2) - this.mOrientationHelper.b(view));
            return;
        }
        scrollToPositionWithOffset(position2, i3);
    }

    /* access modifiers changed from: package-private */
    public boolean resolveIsInfinite() {
        return this.mOrientationHelper.d() == 0 && this.mOrientationHelper.a() == 0;
    }

    /* access modifiers changed from: package-private */
    public int scrollBy(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        this.mLayoutState.f962a = true;
        ensureLayoutState();
        int i2 = i > 0 ? 1 : -1;
        int abs = Math.abs(i);
        updateLayoutState(i2, abs, true, sVar);
        c cVar = this.mLayoutState;
        int fill = cVar.g + fill(oVar, cVar, sVar, false);
        if (fill < 0) {
            return 0;
        }
        if (abs > fill) {
            i = i2 * fill;
        }
        this.mOrientationHelper.a(-i);
        this.mLayoutState.j = i;
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int scrollHorizontallyBy(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.mOrientation == 1) {
            return 0;
        }
        return scrollBy(i, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void scrollToPosition(int i) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = INVALID_OFFSET;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.b();
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = i2;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.b();
        }
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int scrollVerticallyBy(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.mOrientation == 0) {
            return 0;
        }
        return scrollBy(i, oVar, sVar);
    }

    public void setInitialPrefetchItemCount(int i) {
        this.mInitialPrefetchItemCount = i;
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll(null);
            if (i != this.mOrientation || this.mOrientationHelper == null) {
                this.mOrientationHelper = x.a(this, i);
                this.mAnchorInfo.f954a = this.mOrientationHelper;
                this.mOrientation = i;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + i);
    }

    public void setRecycleChildrenOnDetach(boolean z) {
        this.mRecycleChildrenOnDetach = z;
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (z != this.mReverseLayout) {
            this.mReverseLayout = z;
            requestLayout();
        }
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd != z) {
            this.mStackFromEnd = z;
            requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean shouldMeasureTwice() {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !hasFlexibleChildInBothOrientations()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.s sVar, int i) {
        t tVar = new t(recyclerView.getContext());
        tVar.c(i);
        startSmoothScroll(tVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    /* access modifiers changed from: package-private */
    public void validateChildOrder() {
        Log.d(TAG, "validating child count " + getChildCount());
        if (getChildCount() >= 1) {
            boolean z = false;
            int position = getPosition(getChildAt(0));
            int d2 = this.mOrientationHelper.d(getChildAt(0));
            if (this.mShouldReverseLayout) {
                for (int i = 1; i < getChildCount(); i++) {
                    View childAt = getChildAt(i);
                    int position2 = getPosition(childAt);
                    int d3 = this.mOrientationHelper.d(childAt);
                    if (position2 < position) {
                        logChildren();
                        StringBuilder sb = new StringBuilder();
                        sb.append("detected invalid position. loc invalid? ");
                        if (d3 < d2) {
                            z = true;
                        }
                        sb.append(z);
                        throw new RuntimeException(sb.toString());
                    } else if (d3 > d2) {
                        logChildren();
                        throw new RuntimeException("detected invalid location");
                    }
                }
                return;
            }
            for (int i2 = 1; i2 < getChildCount(); i2++) {
                View childAt2 = getChildAt(i2);
                int position3 = getPosition(childAt2);
                int d4 = this.mOrientationHelper.d(childAt2);
                if (position3 < position) {
                    logChildren();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("detected invalid position. loc invalid? ");
                    if (d4 < d2) {
                        z = true;
                    }
                    sb2.append(z);
                    throw new RuntimeException(sb2.toString());
                } else if (d4 < d2) {
                    logChildren();
                    throw new RuntimeException("detected invalid location");
                }
            }
        }
    }
}
