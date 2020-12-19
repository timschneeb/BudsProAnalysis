package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import b.e.g.a.b;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends RecyclerView.i implements RecyclerView.r.b {

    /* renamed from: a  reason: collision with root package name */
    private int f1027a = -1;

    /* renamed from: b  reason: collision with root package name */
    b[] f1028b;

    /* renamed from: c  reason: collision with root package name */
    x f1029c;

    /* renamed from: d  reason: collision with root package name */
    x f1030d;
    private int e;
    private int f;
    private final r g;
    boolean h = false;
    boolean i = false;
    private BitSet j;
    int k = -1;
    int l = LinearLayoutManager.INVALID_OFFSET;
    LazySpanLookup m = new LazySpanLookup();
    private int n = 2;
    private boolean o;
    private boolean p;
    private SavedState q;
    private int r;
    private final Rect s = new Rect();
    private final a t = new a();
    private boolean u = false;
    private boolean v = true;
    private int[] w;
    private final Runnable x = new K(this);

    public static class LayoutParams extends RecyclerView.LayoutParams {
        b e;
        boolean f;

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

        public final int e() {
            b bVar = this.e;
            if (bVar == null) {
                return -1;
            }
            return bVar.e;
        }

        public boolean f() {
            return this.f;
        }
    }

    /* access modifiers changed from: package-private */
    public static class LazySpanLookup {

        /* renamed from: a  reason: collision with root package name */
        int[] f1031a;

        /* renamed from: b  reason: collision with root package name */
        List<FullSpanItem> f1032b;

        /* access modifiers changed from: package-private */
        public static class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new L();

            /* renamed from: a  reason: collision with root package name */
            int f1033a;

            /* renamed from: b  reason: collision with root package name */
            int f1034b;

            /* renamed from: c  reason: collision with root package name */
            int[] f1035c;

            /* renamed from: d  reason: collision with root package name */
            boolean f1036d;

            FullSpanItem() {
            }

            FullSpanItem(Parcel parcel) {
                this.f1033a = parcel.readInt();
                this.f1034b = parcel.readInt();
                this.f1036d = parcel.readInt() != 1 ? false : true;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.f1035c = new int[readInt];
                    parcel.readIntArray(this.f1035c);
                }
            }

            /* access modifiers changed from: package-private */
            public int a(int i) {
                int[] iArr = this.f1035c;
                if (iArr == null) {
                    return 0;
                }
                return iArr[i];
            }

            public int describeContents() {
                return 0;
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.f1033a + ", mGapDir=" + this.f1034b + ", mHasUnwantedGapAfter=" + this.f1036d + ", mGapPerSpan=" + Arrays.toString(this.f1035c) + '}';
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.f1033a);
                parcel.writeInt(this.f1034b);
                parcel.writeInt(this.f1036d ? 1 : 0);
                int[] iArr = this.f1035c;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(iArr.length);
                parcel.writeIntArray(this.f1035c);
            }
        }

        LazySpanLookup() {
        }

        private void c(int i, int i2) {
            List<FullSpanItem> list = this.f1032b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.f1032b.get(size);
                    int i3 = fullSpanItem.f1033a;
                    if (i3 >= i) {
                        fullSpanItem.f1033a = i3 + i2;
                    }
                }
            }
        }

        private void d(int i, int i2) {
            List<FullSpanItem> list = this.f1032b;
            if (list != null) {
                int i3 = i + i2;
                for (int size = list.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.f1032b.get(size);
                    int i4 = fullSpanItem.f1033a;
                    if (i4 >= i) {
                        if (i4 < i3) {
                            this.f1032b.remove(size);
                        } else {
                            fullSpanItem.f1033a = i4 - i2;
                        }
                    }
                }
            }
        }

        private int g(int i) {
            if (this.f1032b == null) {
                return -1;
            }
            FullSpanItem c2 = c(i);
            if (c2 != null) {
                this.f1032b.remove(c2);
            }
            int size = this.f1032b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (this.f1032b.get(i2).f1033a >= i) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                return -1;
            }
            this.f1032b.remove(i2);
            return this.f1032b.get(i2).f1033a;
        }

        public FullSpanItem a(int i, int i2, int i3, boolean z) {
            List<FullSpanItem> list = this.f1032b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                FullSpanItem fullSpanItem = this.f1032b.get(i4);
                int i5 = fullSpanItem.f1033a;
                if (i5 >= i2) {
                    return null;
                }
                if (i5 >= i && (i3 == 0 || fullSpanItem.f1034b == i3 || (z && fullSpanItem.f1036d))) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int[] iArr = this.f1031a;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.f1032b = null;
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            int[] iArr = this.f1031a;
            if (iArr == null) {
                this.f1031a = new int[(Math.max(i, 10) + 1)];
                Arrays.fill(this.f1031a, -1);
            } else if (i >= iArr.length) {
                this.f1031a = new int[f(i)];
                System.arraycopy(iArr, 0, this.f1031a, 0, iArr.length);
                int[] iArr2 = this.f1031a;
                Arrays.fill(iArr2, iArr.length, iArr2.length, -1);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i, int i2) {
            int[] iArr = this.f1031a;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                a(i3);
                int[] iArr2 = this.f1031a;
                System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
                Arrays.fill(this.f1031a, i, i3, -1);
                c(i, i2);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i, b bVar) {
            a(i);
            this.f1031a[i] = bVar.e;
        }

        public void a(FullSpanItem fullSpanItem) {
            if (this.f1032b == null) {
                this.f1032b = new ArrayList();
            }
            int size = this.f1032b.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem fullSpanItem2 = this.f1032b.get(i);
                if (fullSpanItem2.f1033a == fullSpanItem.f1033a) {
                    this.f1032b.remove(i);
                }
                if (fullSpanItem2.f1033a >= fullSpanItem.f1033a) {
                    this.f1032b.add(i, fullSpanItem);
                    return;
                }
            }
            this.f1032b.add(fullSpanItem);
        }

        /* access modifiers changed from: package-private */
        public int b(int i) {
            List<FullSpanItem> list = this.f1032b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this.f1032b.get(size).f1033a >= i) {
                        this.f1032b.remove(size);
                    }
                }
            }
            return e(i);
        }

        /* access modifiers changed from: package-private */
        public void b(int i, int i2) {
            int[] iArr = this.f1031a;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                a(i3);
                int[] iArr2 = this.f1031a;
                System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
                int[] iArr3 = this.f1031a;
                Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
                d(i, i2);
            }
        }

        public FullSpanItem c(int i) {
            List<FullSpanItem> list = this.f1032b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.f1032b.get(size);
                if (fullSpanItem.f1033a == i) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public int d(int i) {
            int[] iArr = this.f1031a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            return iArr[i];
        }

        /* access modifiers changed from: package-private */
        public int e(int i) {
            int[] iArr = this.f1031a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            int g = g(i);
            if (g == -1) {
                int[] iArr2 = this.f1031a;
                Arrays.fill(iArr2, i, iArr2.length, -1);
                return this.f1031a.length;
            }
            int i2 = g + 1;
            Arrays.fill(this.f1031a, i, i2, -1);
            return i2;
        }

        /* access modifiers changed from: package-private */
        public int f(int i) {
            int length = this.f1031a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new M();

        /* renamed from: a  reason: collision with root package name */
        int f1037a;

        /* renamed from: b  reason: collision with root package name */
        int f1038b;

        /* renamed from: c  reason: collision with root package name */
        int f1039c;

        /* renamed from: d  reason: collision with root package name */
        int[] f1040d;
        int e;
        int[] f;
        List<LazySpanLookup.FullSpanItem> g;
        boolean h;
        boolean i;
        boolean j;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.f1037a = parcel.readInt();
            this.f1038b = parcel.readInt();
            this.f1039c = parcel.readInt();
            int i2 = this.f1039c;
            if (i2 > 0) {
                this.f1040d = new int[i2];
                parcel.readIntArray(this.f1040d);
            }
            this.e = parcel.readInt();
            int i3 = this.e;
            if (i3 > 0) {
                this.f = new int[i3];
                parcel.readIntArray(this.f);
            }
            boolean z = false;
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            this.j = parcel.readInt() == 1 ? true : z;
            this.g = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.f1039c = savedState.f1039c;
            this.f1037a = savedState.f1037a;
            this.f1038b = savedState.f1038b;
            this.f1040d = savedState.f1040d;
            this.e = savedState.e;
            this.f = savedState.f;
            this.h = savedState.h;
            this.i = savedState.i;
            this.j = savedState.j;
            this.g = savedState.g;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.f1040d = null;
            this.f1039c = 0;
            this.f1037a = -1;
            this.f1038b = -1;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f1040d = null;
            this.f1039c = 0;
            this.e = 0;
            this.f = null;
            this.g = null;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.f1037a);
            parcel.writeInt(this.f1038b);
            parcel.writeInt(this.f1039c);
            if (this.f1039c > 0) {
                parcel.writeIntArray(this.f1040d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }
    }

    /* access modifiers changed from: package-private */
    public class a {

        /* renamed from: a  reason: collision with root package name */
        int f1041a;

        /* renamed from: b  reason: collision with root package name */
        int f1042b;

        /* renamed from: c  reason: collision with root package name */
        boolean f1043c;

        /* renamed from: d  reason: collision with root package name */
        boolean f1044d;
        boolean e;
        int[] f;

        a() {
            b();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.f1042b = this.f1043c ? StaggeredGridLayoutManager.this.f1029c.b() : StaggeredGridLayoutManager.this.f1029c.f();
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            this.f1042b = this.f1043c ? StaggeredGridLayoutManager.this.f1029c.b() - i : StaggeredGridLayoutManager.this.f1029c.f() + i;
        }

        /* access modifiers changed from: package-private */
        public void a(b[] bVarArr) {
            int length = bVarArr.length;
            int[] iArr = this.f;
            if (iArr == null || iArr.length < length) {
                this.f = new int[StaggeredGridLayoutManager.this.f1028b.length];
            }
            for (int i = 0; i < length; i++) {
                this.f[i] = bVarArr[i].b(LinearLayoutManager.INVALID_OFFSET);
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f1041a = -1;
            this.f1042b = LinearLayoutManager.INVALID_OFFSET;
            this.f1043c = false;
            this.f1044d = false;
            this.e = false;
            int[] iArr = this.f;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class b {

        /* renamed from: a  reason: collision with root package name */
        ArrayList<View> f1045a = new ArrayList<>();

        /* renamed from: b  reason: collision with root package name */
        int f1046b = LinearLayoutManager.INVALID_OFFSET;

        /* renamed from: c  reason: collision with root package name */
        int f1047c = LinearLayoutManager.INVALID_OFFSET;

        /* renamed from: d  reason: collision with root package name */
        int f1048d = 0;
        final int e;

        b(int i) {
            this.e = i;
        }

        /* access modifiers changed from: package-private */
        public int a(int i) {
            int i2 = this.f1047c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.f1045a.size() == 0) {
                return i;
            }
            a();
            return this.f1047c;
        }

        /* access modifiers changed from: package-private */
        public int a(int i, int i2, boolean z) {
            return a(i, i2, false, false, z);
        }

        /* access modifiers changed from: package-private */
        public int a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int f2 = StaggeredGridLayoutManager.this.f1029c.f();
            int b2 = StaggeredGridLayoutManager.this.f1029c.b();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view = this.f1045a.get(i);
                int d2 = StaggeredGridLayoutManager.this.f1029c.d(view);
                int a2 = StaggeredGridLayoutManager.this.f1029c.a(view);
                boolean z4 = false;
                boolean z5 = !z3 ? d2 < b2 : d2 <= b2;
                if (!z3 ? a2 > f2 : a2 >= f2) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (!z || !z2) {
                        if (!z2 && d2 >= f2 && a2 <= b2) {
                        }
                    } else if (d2 >= f2 && a2 <= b2) {
                    }
                    return StaggeredGridLayoutManager.this.getPosition(view);
                }
                i += i3;
            }
            return -1;
        }

        public View a(int i, int i2) {
            View view = null;
            if (i2 != -1) {
                int size = this.f1045a.size() - 1;
                while (size >= 0) {
                    View view2 = this.f1045a.get(size);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager.h && staggeredGridLayoutManager.getPosition(view2) >= i) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager2 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager2.h && staggeredGridLayoutManager2.getPosition(view2) <= i) || !view2.hasFocusable()) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.f1045a.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = this.f1045a.get(i3);
                    StaggeredGridLayoutManager staggeredGridLayoutManager3 = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager3.h && staggeredGridLayoutManager3.getPosition(view3) <= i) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager4 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager4.h && staggeredGridLayoutManager4.getPosition(view3) >= i) || !view3.hasFocusable()) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            LazySpanLookup.FullSpanItem c2;
            ArrayList<View> arrayList = this.f1045a;
            View view = arrayList.get(arrayList.size() - 1);
            LayoutParams b2 = b(view);
            this.f1047c = StaggeredGridLayoutManager.this.f1029c.a(view);
            if (b2.f && (c2 = StaggeredGridLayoutManager.this.m.c(b2.a())) != null && c2.f1034b == 1) {
                this.f1047c += c2.a(this.e);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(View view) {
            LayoutParams b2 = b(view);
            b2.e = this;
            this.f1045a.add(view);
            this.f1047c = LinearLayoutManager.INVALID_OFFSET;
            if (this.f1045a.size() == 1) {
                this.f1046b = LinearLayoutManager.INVALID_OFFSET;
            }
            if (b2.c() || b2.b()) {
                this.f1048d += StaggeredGridLayoutManager.this.f1029c.b(view);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z, int i) {
            int a2 = z ? a(LinearLayoutManager.INVALID_OFFSET) : b(LinearLayoutManager.INVALID_OFFSET);
            c();
            if (a2 != Integer.MIN_VALUE) {
                if (z && a2 < StaggeredGridLayoutManager.this.f1029c.b()) {
                    return;
                }
                if (z || a2 <= StaggeredGridLayoutManager.this.f1029c.f()) {
                    if (i != Integer.MIN_VALUE) {
                        a2 += i;
                    }
                    this.f1047c = a2;
                    this.f1046b = a2;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int b(int i) {
            int i2 = this.f1046b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.f1045a.size() == 0) {
                return i;
            }
            b();
            return this.f1046b;
        }

        /* access modifiers changed from: package-private */
        public LayoutParams b(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        /* access modifiers changed from: package-private */
        public void b() {
            LazySpanLookup.FullSpanItem c2;
            View view = this.f1045a.get(0);
            LayoutParams b2 = b(view);
            this.f1046b = StaggeredGridLayoutManager.this.f1029c.d(view);
            if (b2.f && (c2 = StaggeredGridLayoutManager.this.m.c(b2.a())) != null && c2.f1034b == -1) {
                this.f1046b -= c2.a(this.e);
            }
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.f1045a.clear();
            i();
            this.f1048d = 0;
        }

        /* access modifiers changed from: package-private */
        public void c(int i) {
            int i2 = this.f1046b;
            if (i2 != Integer.MIN_VALUE) {
                this.f1046b = i2 + i;
            }
            int i3 = this.f1047c;
            if (i3 != Integer.MIN_VALUE) {
                this.f1047c = i3 + i;
            }
        }

        /* access modifiers changed from: package-private */
        public void c(View view) {
            LayoutParams b2 = b(view);
            b2.e = this;
            this.f1045a.add(0, view);
            this.f1046b = LinearLayoutManager.INVALID_OFFSET;
            if (this.f1045a.size() == 1) {
                this.f1047c = LinearLayoutManager.INVALID_OFFSET;
            }
            if (b2.c() || b2.b()) {
                this.f1048d += StaggeredGridLayoutManager.this.f1029c.b(view);
            }
        }

        public int d() {
            int i;
            int i2;
            if (StaggeredGridLayoutManager.this.h) {
                i2 = this.f1045a.size() - 1;
                i = -1;
            } else {
                i2 = 0;
                i = this.f1045a.size();
            }
            return a(i2, i, true);
        }

        /* access modifiers changed from: package-private */
        public void d(int i) {
            this.f1046b = i;
            this.f1047c = i;
        }

        public int e() {
            int i;
            int i2;
            if (StaggeredGridLayoutManager.this.h) {
                i2 = 0;
                i = this.f1045a.size();
            } else {
                i2 = this.f1045a.size() - 1;
                i = -1;
            }
            return a(i2, i, true);
        }

        public int f() {
            return this.f1048d;
        }

        /* access modifiers changed from: package-private */
        public int g() {
            int i = this.f1047c;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            a();
            return this.f1047c;
        }

        /* access modifiers changed from: package-private */
        public int h() {
            int i = this.f1046b;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            b();
            return this.f1046b;
        }

        /* access modifiers changed from: package-private */
        public void i() {
            this.f1046b = LinearLayoutManager.INVALID_OFFSET;
            this.f1047c = LinearLayoutManager.INVALID_OFFSET;
        }

        /* access modifiers changed from: package-private */
        public void j() {
            int size = this.f1045a.size();
            View remove = this.f1045a.remove(size - 1);
            LayoutParams b2 = b(remove);
            b2.e = null;
            if (b2.c() || b2.b()) {
                this.f1048d -= StaggeredGridLayoutManager.this.f1029c.b(remove);
            }
            if (size == 1) {
                this.f1046b = LinearLayoutManager.INVALID_OFFSET;
            }
            this.f1047c = LinearLayoutManager.INVALID_OFFSET;
        }

        /* access modifiers changed from: package-private */
        public void k() {
            View remove = this.f1045a.remove(0);
            LayoutParams b2 = b(remove);
            b2.e = null;
            if (this.f1045a.size() == 0) {
                this.f1047c = LinearLayoutManager.INVALID_OFFSET;
            }
            if (b2.c() || b2.b()) {
                this.f1048d -= StaggeredGridLayoutManager.this.f1029c.b(remove);
            }
            this.f1046b = LinearLayoutManager.INVALID_OFFSET;
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        RecyclerView.i.b properties = RecyclerView.i.getProperties(context, attributeSet, i2, i3);
        setOrientation(properties.f996a);
        a(properties.f997b);
        setReverseLayout(properties.f998c);
        this.g = new r();
        i();
    }

    private int a(RecyclerView.o oVar, r rVar, RecyclerView.s sVar) {
        int i2;
        b bVar;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        View view;
        StaggeredGridLayoutManager staggeredGridLayoutManager;
        boolean z = false;
        this.j.set(0, this.f1027a, true);
        if (this.g.i) {
            i2 = rVar.e == 1 ? Integer.MAX_VALUE : LinearLayoutManager.INVALID_OFFSET;
        } else {
            i2 = rVar.e == 1 ? rVar.g + rVar.f1118b : rVar.f - rVar.f1118b;
        }
        a(rVar.e, i2);
        int b2 = this.i ? this.f1029c.b() : this.f1029c.f();
        boolean z2 = false;
        while (rVar.a(sVar) && (this.g.i || !this.j.isEmpty())) {
            View a2 = rVar.a(oVar);
            LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
            int a3 = layoutParams.a();
            int d2 = this.m.d(a3);
            boolean z3 = d2 == -1;
            if (z3) {
                if (layoutParams.f) {
                    b[] bVarArr = this.f1028b;
                    char c2 = z ? 1 : 0;
                    char c3 = z ? 1 : 0;
                    char c4 = z ? 1 : 0;
                    bVar = bVarArr[c2];
                } else {
                    bVar = a(rVar);
                }
                this.m.a(a3, bVar);
            } else {
                bVar = this.f1028b[d2];
            }
            layoutParams.e = bVar;
            if (rVar.e == 1) {
                addView(a2);
            } else {
                int i9 = z ? 1 : 0;
                int i10 = z ? 1 : 0;
                int i11 = z ? 1 : 0;
                addView(a2, i9);
            }
            a(a2, layoutParams, z);
            if (rVar.e == 1) {
                int h2 = layoutParams.f ? h(b2) : bVar.a(b2);
                int b3 = this.f1029c.b(a2) + h2;
                if (z3 && layoutParams.f) {
                    LazySpanLookup.FullSpanItem d3 = d(h2);
                    d3.f1034b = -1;
                    d3.f1033a = a3;
                    this.m.a(d3);
                }
                i3 = b3;
                i4 = h2;
            } else {
                int k2 = layoutParams.f ? k(b2) : bVar.b(b2);
                i4 = k2 - this.f1029c.b(a2);
                if (z3 && layoutParams.f) {
                    LazySpanLookup.FullSpanItem e2 = e(k2);
                    e2.f1034b = 1;
                    e2.f1033a = a3;
                    this.m.a(e2);
                }
                i3 = k2;
            }
            if (layoutParams.f && rVar.f1120d == -1) {
                if (!z3) {
                    if (!(rVar.e == 1 ? a() : b())) {
                        LazySpanLookup.FullSpanItem c5 = this.m.c(a3);
                        if (c5 != null) {
                            c5.f1036d = true;
                        }
                    }
                }
                this.u = true;
            }
            a(a2, layoutParams, rVar);
            if (!isLayoutRTL() || this.e != 1) {
                int f2 = layoutParams.f ? this.f1030d.f() : (bVar.e * this.f) + this.f1030d.f();
                i6 = f2;
                i5 = this.f1030d.b(a2) + f2;
            } else {
                int b4 = layoutParams.f ? this.f1030d.b() : this.f1030d.b() - (((this.f1027a - 1) - bVar.e) * this.f);
                i5 = b4;
                i6 = b4 - this.f1030d.b(a2);
            }
            if (this.e == 1) {
                staggeredGridLayoutManager = this;
                view = a2;
                i8 = i6;
                i6 = i4;
                i7 = i5;
            } else {
                staggeredGridLayoutManager = this;
                view = a2;
                i8 = i4;
                i7 = i3;
                i3 = i5;
            }
            staggeredGridLayoutManager.layoutDecoratedWithMargins(view, i8, i6, i7, i3);
            if (layoutParams.f) {
                a(this.g.e, i2);
            } else {
                a(bVar, this.g.e, i2);
            }
            a(oVar, this.g);
            if (this.g.h && a2.hasFocusable()) {
                if (layoutParams.f) {
                    this.j.clear();
                } else {
                    this.j.set(bVar.e, false);
                    z2 = true;
                    z = false;
                }
            }
            z2 = true;
            z = false;
        }
        if (!z2) {
            a(oVar, this.g);
        }
        int f3 = this.g.e == -1 ? this.f1029c.f() - k(this.f1029c.f()) : h(this.f1029c.b()) - this.f1029c.b();
        if (f3 > 0) {
            return Math.min(rVar.f1118b, f3);
        }
        return 0;
    }

    private b a(r rVar) {
        int i2;
        int i3;
        int i4 = -1;
        if (l(rVar.e)) {
            i3 = this.f1027a - 1;
            i2 = -1;
        } else {
            i3 = 0;
            i4 = this.f1027a;
            i2 = 1;
        }
        b bVar = null;
        if (rVar.e == 1) {
            int i5 = Integer.MAX_VALUE;
            int f2 = this.f1029c.f();
            while (i3 != i4) {
                b bVar2 = this.f1028b[i3];
                int a2 = bVar2.a(f2);
                if (a2 < i5) {
                    bVar = bVar2;
                    i5 = a2;
                }
                i3 += i2;
            }
            return bVar;
        }
        int i6 = LinearLayoutManager.INVALID_OFFSET;
        int b2 = this.f1029c.b();
        while (i3 != i4) {
            b bVar3 = this.f1028b[i3];
            int b3 = bVar3.b(b2);
            if (b3 > i6) {
                bVar = bVar3;
                i6 = b3;
            }
            i3 += i2;
        }
        return bVar;
    }

    private void a(int i2, int i3) {
        for (int i4 = 0; i4 < this.f1027a; i4++) {
            if (!this.f1028b[i4].f1045a.isEmpty()) {
                a(this.f1028b[i4], i2, i3);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0046  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(int r7, int r8, int r9) {
        /*
            r6 = this;
            boolean r0 = r6.i
            if (r0 == 0) goto L_0x0009
            int r0 = r6.f()
            goto L_0x000d
        L_0x0009:
            int r0 = r6.e()
        L_0x000d:
            r1 = 8
            if (r9 != r1) goto L_0x001b
            if (r7 >= r8) goto L_0x0016
            int r2 = r8 + 1
            goto L_0x001d
        L_0x0016:
            int r2 = r7 + 1
            r3 = r2
            r2 = r8
            goto L_0x001f
        L_0x001b:
            int r2 = r7 + r8
        L_0x001d:
            r3 = r2
            r2 = r7
        L_0x001f:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.m
            r4.e(r2)
            r4 = 1
            if (r9 == r4) goto L_0x003e
            r5 = 2
            if (r9 == r5) goto L_0x0038
            if (r9 == r1) goto L_0x002d
            goto L_0x0043
        L_0x002d:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.m
            r9.b(r7, r4)
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r7 = r6.m
            r7.a(r8, r4)
            goto L_0x0043
        L_0x0038:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.m
            r9.b(r7, r8)
            goto L_0x0043
        L_0x003e:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.m
            r9.a(r7, r8)
        L_0x0043:
            if (r3 > r0) goto L_0x0046
            return
        L_0x0046:
            boolean r7 = r6.i
            if (r7 == 0) goto L_0x004f
            int r7 = r6.e()
            goto L_0x0053
        L_0x004f:
            int r7 = r6.f()
        L_0x0053:
            if (r2 > r7) goto L_0x0058
            r6.requestLayout()
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.a(int, int, int):void");
    }

    private void a(View view) {
        for (int i2 = this.f1027a - 1; i2 >= 0; i2--) {
            this.f1028b[i2].a(view);
        }
    }

    private void a(View view, int i2, int i3, boolean z) {
        calculateItemDecorationsForChild(view, this.s);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i4 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        Rect rect = this.s;
        int b2 = b(i2, i4 + rect.left, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.right);
        int i5 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        Rect rect2 = this.s;
        int b3 = b(i3, i5 + rect2.top, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect2.bottom);
        if (z ? shouldReMeasureChild(view, b2, b3, layoutParams) : shouldMeasureChild(view, b2, b3, layoutParams)) {
            view.measure(b2, b3);
        }
    }

    private void a(View view, LayoutParams layoutParams, r rVar) {
        if (rVar.e == 1) {
            if (layoutParams.f) {
                a(view);
            } else {
                layoutParams.e.a(view);
            }
        } else if (layoutParams.f) {
            b(view);
        } else {
            layoutParams.e.c(view);
        }
    }

    private void a(View view, LayoutParams layoutParams, boolean z) {
        int i2;
        int i3;
        if (layoutParams.f) {
            if (this.e == 1) {
                i3 = this.r;
            } else {
                a(view, RecyclerView.i.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width, true), this.r, z);
                return;
            }
        } else if (this.e == 1) {
            i3 = RecyclerView.i.getChildMeasureSpec(this.f, getWidthMode(), 0, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
        } else {
            i3 = RecyclerView.i.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight(), ((ViewGroup.MarginLayoutParams) layoutParams).width, true);
            i2 = RecyclerView.i.getChildMeasureSpec(this.f, getHeightMode(), 0, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
            a(view, i3, i2, z);
        }
        i2 = RecyclerView.i.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom(), ((ViewGroup.MarginLayoutParams) layoutParams).height, true);
        a(view, i3, i2, z);
    }

    private void a(RecyclerView.o oVar, int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (this.f1029c.d(childAt) >= i2 && this.f1029c.f(childAt) >= i2) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.f) {
                    for (int i3 = 0; i3 < this.f1027a; i3++) {
                        if (this.f1028b[i3].f1045a.size() == 1) {
                            return;
                        }
                    }
                    for (int i4 = 0; i4 < this.f1027a; i4++) {
                        this.f1028b[i4].j();
                    }
                } else if (layoutParams.e.f1045a.size() != 1) {
                    layoutParams.e.j();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, oVar);
            } else {
                return;
            }
        }
    }

    private void a(RecyclerView.o oVar, RecyclerView.s sVar, boolean z) {
        int b2;
        int h2 = h(LinearLayoutManager.INVALID_OFFSET);
        if (h2 != Integer.MIN_VALUE && (b2 = this.f1029c.b() - h2) > 0) {
            int i2 = b2 - (-scrollBy(-b2, oVar, sVar));
            if (z && i2 > 0) {
                this.f1029c.a(i2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0010, code lost:
        if (r4.e == -1) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(androidx.recyclerview.widget.RecyclerView.o r3, androidx.recyclerview.widget.r r4) {
        /*
            r2 = this;
            boolean r0 = r4.f1117a
            if (r0 == 0) goto L_0x004d
            boolean r0 = r4.i
            if (r0 == 0) goto L_0x0009
            goto L_0x004d
        L_0x0009:
            int r0 = r4.f1118b
            r1 = -1
            if (r0 != 0) goto L_0x001e
            int r0 = r4.e
            if (r0 != r1) goto L_0x0018
        L_0x0012:
            int r4 = r4.g
        L_0x0014:
            r2.a(r3, r4)
            goto L_0x004d
        L_0x0018:
            int r4 = r4.f
        L_0x001a:
            r2.b(r3, r4)
            goto L_0x004d
        L_0x001e:
            int r0 = r4.e
            if (r0 != r1) goto L_0x0037
            int r0 = r4.f
            int r1 = r2.i(r0)
            int r0 = r0 - r1
            if (r0 >= 0) goto L_0x002c
            goto L_0x0012
        L_0x002c:
            int r1 = r4.g
            int r4 = r4.f1118b
            int r4 = java.lang.Math.min(r0, r4)
            int r4 = r1 - r4
            goto L_0x0014
        L_0x0037:
            int r0 = r4.g
            int r0 = r2.j(r0)
            int r1 = r4.g
            int r0 = r0 - r1
            if (r0 >= 0) goto L_0x0043
            goto L_0x0018
        L_0x0043:
            int r1 = r4.f
            int r4 = r4.f1118b
            int r4 = java.lang.Math.min(r0, r4)
            int r4 = r4 + r1
            goto L_0x001a
        L_0x004d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.a(androidx.recyclerview.widget.RecyclerView$o, androidx.recyclerview.widget.r):void");
    }

    private void a(a aVar) {
        boolean z;
        SavedState savedState = this.q;
        int i2 = savedState.f1039c;
        if (i2 > 0) {
            if (i2 == this.f1027a) {
                for (int i3 = 0; i3 < this.f1027a; i3++) {
                    this.f1028b[i3].c();
                    SavedState savedState2 = this.q;
                    int i4 = savedState2.f1040d[i3];
                    if (i4 != Integer.MIN_VALUE) {
                        i4 += savedState2.i ? this.f1029c.b() : this.f1029c.f();
                    }
                    this.f1028b[i3].d(i4);
                }
            } else {
                savedState.b();
                SavedState savedState3 = this.q;
                savedState3.f1037a = savedState3.f1038b;
            }
        }
        SavedState savedState4 = this.q;
        this.p = savedState4.j;
        setReverseLayout(savedState4.h);
        resolveShouldLayoutReverse();
        SavedState savedState5 = this.q;
        int i5 = savedState5.f1037a;
        if (i5 != -1) {
            this.k = i5;
            z = savedState5.i;
        } else {
            z = this.i;
        }
        aVar.f1043c = z;
        SavedState savedState6 = this.q;
        if (savedState6.e > 1) {
            LazySpanLookup lazySpanLookup = this.m;
            lazySpanLookup.f1031a = savedState6.f;
            lazySpanLookup.f1032b = savedState6.g;
        }
    }

    private void a(b bVar, int i2, int i3) {
        int f2 = bVar.f();
        if (i2 == -1) {
            if (bVar.h() + f2 > i3) {
                return;
            }
        } else if (bVar.g() - f2 < i3) {
            return;
        }
        this.j.set(bVar.e, false);
    }

    private boolean a(b bVar) {
        if (this.i) {
            if (bVar.g() < this.f1029c.b()) {
                ArrayList<View> arrayList = bVar.f1045a;
                return !bVar.b(arrayList.get(arrayList.size() - 1)).f;
            }
        } else if (bVar.h() > this.f1029c.f()) {
            return !bVar.b(bVar.f1045a.get(0)).f;
        }
        return false;
    }

    private int b(int i2, int i3, int i4) {
        if (i3 == 0 && i4 == 0) {
            return i2;
        }
        int mode = View.MeasureSpec.getMode(i2);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i2) - i3) - i4), mode) : i2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r5, androidx.recyclerview.widget.RecyclerView.s r6) {
        /*
        // Method dump skipped, instructions count: 119
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.b(int, androidx.recyclerview.widget.RecyclerView$s):void");
    }

    private void b(View view) {
        for (int i2 = this.f1027a - 1; i2 >= 0; i2--) {
            this.f1028b[i2].c(view);
        }
    }

    private void b(RecyclerView.o oVar, int i2) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.f1029c.a(childAt) <= i2 && this.f1029c.e(childAt) <= i2) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.f) {
                    for (int i3 = 0; i3 < this.f1027a; i3++) {
                        if (this.f1028b[i3].f1045a.size() == 1) {
                            return;
                        }
                    }
                    for (int i4 = 0; i4 < this.f1027a; i4++) {
                        this.f1028b[i4].k();
                    }
                } else if (layoutParams.e.f1045a.size() != 1) {
                    layoutParams.e.k();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, oVar);
            } else {
                return;
            }
        }
    }

    private void b(RecyclerView.o oVar, RecyclerView.s sVar, boolean z) {
        int f2;
        int k2 = k(Integer.MAX_VALUE);
        if (k2 != Integer.MAX_VALUE && (f2 = k2 - this.f1029c.f()) > 0) {
            int scrollBy = f2 - scrollBy(f2, oVar, sVar);
            if (z && scrollBy > 0) {
                this.f1029c.a(-scrollBy);
            }
        }
    }

    private int c(int i2) {
        if (getChildCount() == 0) {
            return this.i ? 1 : -1;
        }
        return (i2 < e()) != this.i ? -1 : 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:83:0x014b, code lost:
        if (c() != false) goto L_0x014f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(androidx.recyclerview.widget.RecyclerView.o r9, androidx.recyclerview.widget.RecyclerView.s r10, boolean r11) {
        /*
        // Method dump skipped, instructions count: 367
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.c(androidx.recyclerview.widget.RecyclerView$o, androidx.recyclerview.widget.RecyclerView$s, boolean):void");
    }

    private boolean c(RecyclerView.s sVar, a aVar) {
        aVar.f1041a = this.o ? g(sVar.a()) : f(sVar.a());
        aVar.f1042b = LinearLayoutManager.INVALID_OFFSET;
        return true;
    }

    private int computeScrollExtent(RecyclerView.s sVar) {
        if (getChildCount() == 0) {
            return 0;
        }
        return I.a(sVar, this.f1029c, b(!this.v), a(!this.v), this, this.v);
    }

    private int computeScrollOffset(RecyclerView.s sVar) {
        if (getChildCount() == 0) {
            return 0;
        }
        return I.a(sVar, this.f1029c, b(!this.v), a(!this.v), this, this.v, this.i);
    }

    private int computeScrollRange(RecyclerView.s sVar) {
        if (getChildCount() == 0) {
            return 0;
        }
        return I.b(sVar, this.f1029c, b(!this.v), a(!this.v), this, this.v);
    }

    private int convertFocusDirectionToLayoutDirection(int i2) {
        if (i2 == 1) {
            return (this.e != 1 && isLayoutRTL()) ? 1 : -1;
        }
        if (i2 == 2) {
            return (this.e != 1 && isLayoutRTL()) ? -1 : 1;
        }
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 66) {
                    if (i2 == 130 && this.e == 1) {
                        return 1;
                    }
                    return LinearLayoutManager.INVALID_OFFSET;
                } else if (this.e == 0) {
                    return 1;
                } else {
                    return LinearLayoutManager.INVALID_OFFSET;
                }
            } else if (this.e == 1) {
                return -1;
            } else {
                return LinearLayoutManager.INVALID_OFFSET;
            }
        } else if (this.e == 0) {
            return -1;
        } else {
            return LinearLayoutManager.INVALID_OFFSET;
        }
    }

    private LazySpanLookup.FullSpanItem d(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.f1035c = new int[this.f1027a];
        for (int i3 = 0; i3 < this.f1027a; i3++) {
            fullSpanItem.f1035c[i3] = i2 - this.f1028b[i3].a(i2);
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem e(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.f1035c = new int[this.f1027a];
        for (int i3 = 0; i3 < this.f1027a; i3++) {
            fullSpanItem.f1035c[i3] = this.f1028b[i3].b(i2) - i2;
        }
        return fullSpanItem;
    }

    private int f(int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            int position = getPosition(getChildAt(i3));
            if (position >= 0 && position < i2) {
                return position;
            }
        }
        return 0;
    }

    private int g(int i2) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            int position = getPosition(getChildAt(childCount));
            if (position >= 0 && position < i2) {
                return position;
            }
        }
        return 0;
    }

    private int h(int i2) {
        int a2 = this.f1028b[0].a(i2);
        for (int i3 = 1; i3 < this.f1027a; i3++) {
            int a3 = this.f1028b[i3].a(i2);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private int i(int i2) {
        int b2 = this.f1028b[0].b(i2);
        for (int i3 = 1; i3 < this.f1027a; i3++) {
            int b3 = this.f1028b[i3].b(i2);
            if (b3 > b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private void i() {
        this.f1029c = x.a(this, this.e);
        this.f1030d = x.a(this, 1 - this.e);
    }

    private int j(int i2) {
        int a2 = this.f1028b[0].a(i2);
        for (int i3 = 1; i3 < this.f1027a; i3++) {
            int a3 = this.f1028b[i3].a(i2);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private void j() {
        if (this.f1030d.d() != 1073741824) {
            int childCount = getChildCount();
            float f2 = 0.0f;
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                float b2 = (float) this.f1030d.b(childAt);
                if (b2 >= f2) {
                    if (((LayoutParams) childAt.getLayoutParams()).f()) {
                        b2 = (b2 * 1.0f) / ((float) this.f1027a);
                    }
                    f2 = Math.max(f2, b2);
                }
            }
            int i3 = this.f;
            int round = Math.round(f2 * ((float) this.f1027a));
            if (this.f1030d.d() == Integer.MIN_VALUE) {
                round = Math.min(round, this.f1030d.g());
            }
            b(round);
            if (this.f != i3) {
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt2 = getChildAt(i4);
                    LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    if (!layoutParams.f) {
                        if (!isLayoutRTL() || this.e != 1) {
                            int i5 = layoutParams.e.e;
                            int i6 = this.f * i5;
                            int i7 = i5 * i3;
                            if (this.e == 1) {
                                childAt2.offsetLeftAndRight(i6 - i7);
                            } else {
                                childAt2.offsetTopAndBottom(i6 - i7);
                            }
                        } else {
                            int i8 = this.f1027a;
                            int i9 = layoutParams.e.e;
                            childAt2.offsetLeftAndRight(((-((i8 - 1) - i9)) * this.f) - ((-((i8 - 1) - i9)) * i3));
                        }
                    }
                }
            }
        }
    }

    private int k(int i2) {
        int b2 = this.f1028b[0].b(i2);
        for (int i3 = 1; i3 < this.f1027a; i3++) {
            int b3 = this.f1028b[i3].b(i2);
            if (b3 < b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private boolean l(int i2) {
        if (this.e == 0) {
            return (i2 == -1) != this.i;
        }
        return ((i2 == -1) == this.i) == isLayoutRTL();
    }

    private void m(int i2) {
        r rVar = this.g;
        rVar.e = i2;
        int i3 = 1;
        if (this.i != (i2 == -1)) {
            i3 = -1;
        }
        rVar.f1120d = i3;
    }

    private void resolveShouldLayoutReverse() {
        this.i = (this.e == 1 || !isLayoutRTL()) ? this.h : !this.h;
    }

    /* access modifiers changed from: package-private */
    public View a(boolean z) {
        int f2 = this.f1029c.f();
        int b2 = this.f1029c.b();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int d2 = this.f1029c.d(childAt);
            int a2 = this.f1029c.a(childAt);
            if (a2 > f2 && d2 < b2) {
                if (a2 <= b2 || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    public void a(int i2) {
        assertNotInLayoutOrScroll(null);
        if (i2 != this.f1027a) {
            h();
            this.f1027a = i2;
            this.j = new BitSet(this.f1027a);
            this.f1028b = new b[this.f1027a];
            for (int i3 = 0; i3 < this.f1027a; i3++) {
                this.f1028b[i3] = new b(i3);
            }
            requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, RecyclerView.s sVar) {
        int i3;
        int i4;
        if (i2 > 0) {
            i4 = f();
            i3 = 1;
        } else {
            i4 = e();
            i3 = -1;
        }
        this.g.f1117a = true;
        b(i4, sVar);
        m(i3);
        r rVar = this.g;
        rVar.f1119c = i4 + rVar.f1120d;
        rVar.f1118b = Math.abs(i2);
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        int a2 = this.f1028b[0].a(LinearLayoutManager.INVALID_OFFSET);
        for (int i2 = 1; i2 < this.f1027a; i2++) {
            if (this.f1028b[i2].a(LinearLayoutManager.INVALID_OFFSET) != a2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(RecyclerView.s sVar, a aVar) {
        int i2;
        int i3;
        int i4;
        boolean z = false;
        if (!sVar.d() && (i2 = this.k) != -1) {
            if (i2 < 0 || i2 >= sVar.a()) {
                this.k = -1;
                this.l = LinearLayoutManager.INVALID_OFFSET;
            } else {
                SavedState savedState = this.q;
                if (savedState == null || savedState.f1037a == -1 || savedState.f1039c < 1) {
                    View findViewByPosition = findViewByPosition(this.k);
                    if (findViewByPosition != null) {
                        aVar.f1041a = this.i ? f() : e();
                        if (this.l != Integer.MIN_VALUE) {
                            if (aVar.f1043c) {
                                i4 = this.f1029c.b() - this.l;
                                i3 = this.f1029c.a(findViewByPosition);
                            } else {
                                i4 = this.f1029c.f() + this.l;
                                i3 = this.f1029c.d(findViewByPosition);
                            }
                            aVar.f1042b = i4 - i3;
                            return true;
                        } else if (this.f1029c.b(findViewByPosition) > this.f1029c.g()) {
                            aVar.f1042b = aVar.f1043c ? this.f1029c.b() : this.f1029c.f();
                            return true;
                        } else {
                            int d2 = this.f1029c.d(findViewByPosition) - this.f1029c.f();
                            if (d2 < 0) {
                                aVar.f1042b = -d2;
                                return true;
                            }
                            int b2 = this.f1029c.b() - this.f1029c.a(findViewByPosition);
                            if (b2 < 0) {
                                aVar.f1042b = b2;
                                return true;
                            }
                            aVar.f1042b = LinearLayoutManager.INVALID_OFFSET;
                        }
                    } else {
                        aVar.f1041a = this.k;
                        int i5 = this.l;
                        if (i5 == Integer.MIN_VALUE) {
                            if (c(aVar.f1041a) == 1) {
                                z = true;
                            }
                            aVar.f1043c = z;
                            aVar.a();
                        } else {
                            aVar.a(i5);
                        }
                        aVar.f1044d = true;
                    }
                } else {
                    aVar.f1042b = LinearLayoutManager.INVALID_OFFSET;
                    aVar.f1041a = this.k;
                }
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void assertNotInLayoutOrScroll(String str) {
        if (this.q == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    /* access modifiers changed from: package-private */
    public View b(boolean z) {
        int f2 = this.f1029c.f();
        int b2 = this.f1029c.b();
        int childCount = getChildCount();
        View view = null;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int d2 = this.f1029c.d(childAt);
            if (this.f1029c.a(childAt) > f2 && d2 < b2) {
                if (d2 >= f2 || !z) {
                    return childAt;
                }
                if (view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        this.f = i2 / this.f1027a;
        this.r = View.MeasureSpec.makeMeasureSpec(i2, this.f1030d.d());
    }

    /* access modifiers changed from: package-private */
    public void b(RecyclerView.s sVar, a aVar) {
        if (!a(sVar, aVar) && !c(sVar, aVar)) {
            aVar.a();
            aVar.f1041a = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        int b2 = this.f1028b[0].b(LinearLayoutManager.INVALID_OFFSET);
        for (int i2 = 1; i2 < this.f1027a; i2++) {
            if (this.f1028b[i2].b(LinearLayoutManager.INVALID_OFFSET) != b2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        int i2;
        int i3;
        if (getChildCount() == 0 || this.n == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (this.i) {
            i3 = f();
            i2 = e();
        } else {
            i3 = e();
            i2 = f();
        }
        if (i3 == 0 && g() != null) {
            this.m.a();
        } else if (!this.u) {
            return false;
        } else {
            int i4 = this.i ? -1 : 1;
            int i5 = i2 + 1;
            LazySpanLookup.FullSpanItem a2 = this.m.a(i3, i5, i4, true);
            if (a2 == null) {
                this.u = false;
                this.m.b(i5);
                return false;
            }
            LazySpanLookup.FullSpanItem a3 = this.m.a(i3, a2.f1033a, i4 * -1, true);
            if (a3 == null) {
                this.m.b(a2.f1033a);
            } else {
                this.m.b(a3.f1033a + 1);
            }
        }
        requestSimpleAnimationsInNextLayout();
        requestLayout();
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean canScrollHorizontally() {
        return this.e == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean canScrollVertically() {
        return this.e == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void collectAdjacentPrefetchPositions(int i2, int i3, RecyclerView.s sVar, RecyclerView.i.a aVar) {
        int i4;
        int i5;
        if (this.e != 0) {
            i2 = i3;
        }
        if (!(getChildCount() == 0 || i2 == 0)) {
            a(i2, sVar);
            int[] iArr = this.w;
            if (iArr == null || iArr.length < this.f1027a) {
                this.w = new int[this.f1027a];
            }
            int i6 = 0;
            for (int i7 = 0; i7 < this.f1027a; i7++) {
                r rVar = this.g;
                if (rVar.f1120d == -1) {
                    i5 = rVar.f;
                    i4 = this.f1028b[i7].b(i5);
                } else {
                    i5 = this.f1028b[i7].a(rVar.g);
                    i4 = this.g.g;
                }
                int i8 = i5 - i4;
                if (i8 >= 0) {
                    this.w[i6] = i8;
                    i6++;
                }
            }
            Arrays.sort(this.w, 0, i6);
            for (int i9 = 0; i9 < i6 && this.g.a(sVar); i9++) {
                aVar.a(this.g.f1119c, this.w[i9]);
                r rVar2 = this.g;
                rVar2.f1119c += rVar2.f1120d;
            }
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
    public PointF computeScrollVectorForPosition(int i2) {
        int c2 = c(i2);
        PointF pointF = new PointF();
        if (c2 == 0) {
            return null;
        }
        if (this.e == 0) {
            pointF.x = (float) c2;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = (float) c2;
        }
        return pointF;
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
    public int d() {
        View a2 = this.i ? a(true) : b(true);
        if (a2 == null) {
            return -1;
        }
        return getPosition(a2);
    }

    /* access modifiers changed from: package-private */
    public int e() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    /* access modifiers changed from: package-private */
    public int f() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0074, code lost:
        if (r10 == r11) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        if (r10 == r11) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008a, code lost:
        r10 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View g() {
        /*
        // Method dump skipped, instructions count: 173
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.g():android.view.View");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.e == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int getColumnCountForAccessibility(RecyclerView.o oVar, RecyclerView.s sVar) {
        return this.e == 1 ? this.f1027a : super.getColumnCountForAccessibility(oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int getRowCountForAccessibility(RecyclerView.o oVar, RecyclerView.s sVar) {
        return this.e == 0 ? this.f1027a : super.getRowCountForAccessibility(oVar, sVar);
    }

    public void h() {
        this.m.a();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean isAutoMeasureEnabled() {
        return this.n != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void offsetChildrenHorizontal(int i2) {
        super.offsetChildrenHorizontal(i2);
        for (int i3 = 0; i3 < this.f1027a; i3++) {
            this.f1028b[i3].c(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void offsetChildrenVertical(int i2) {
        super.offsetChildrenVertical(i2);
        for (int i3 = 0; i3 < this.f1027a; i3++) {
            this.f1028b[i3].c(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.o oVar) {
        super.onDetachedFromWindow(recyclerView, oVar);
        removeCallbacks(this.x);
        for (int i2 = 0; i2 < this.f1027a; i2++) {
            this.f1028b[i2].c();
        }
        recyclerView.requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public View onFocusSearchFailed(View view, int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        View findContainingItemView;
        View a2;
        if (getChildCount() == 0 || (findContainingItemView = findContainingItemView(view)) == null) {
            return null;
        }
        resolveShouldLayoutReverse();
        int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i2);
        if (convertFocusDirectionToLayoutDirection == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        boolean z = layoutParams.f;
        b bVar = layoutParams.e;
        int f2 = convertFocusDirectionToLayoutDirection == 1 ? f() : e();
        b(f2, sVar);
        m(convertFocusDirectionToLayoutDirection);
        r rVar = this.g;
        rVar.f1119c = rVar.f1120d + f2;
        rVar.f1118b = (int) (((float) this.f1029c.g()) * 0.33333334f);
        r rVar2 = this.g;
        rVar2.h = true;
        rVar2.f1117a = false;
        a(oVar, rVar2, sVar);
        this.o = this.i;
        if (!(z || (a2 = bVar.a(f2, convertFocusDirectionToLayoutDirection)) == null || a2 == findContainingItemView)) {
            return a2;
        }
        if (l(convertFocusDirectionToLayoutDirection)) {
            for (int i3 = this.f1027a - 1; i3 >= 0; i3--) {
                View a3 = this.f1028b[i3].a(f2, convertFocusDirectionToLayoutDirection);
                if (!(a3 == null || a3 == findContainingItemView)) {
                    return a3;
                }
            }
        } else {
            for (int i4 = 0; i4 < this.f1027a; i4++) {
                View a4 = this.f1028b[i4].a(f2, convertFocusDirectionToLayoutDirection);
                if (!(a4 == null || a4 == findContainingItemView)) {
                    return a4;
                }
            }
        }
        boolean z2 = (this.h ^ true) == (convertFocusDirectionToLayoutDirection == -1);
        if (!z) {
            View findViewByPosition = findViewByPosition(z2 ? bVar.d() : bVar.e());
            if (!(findViewByPosition == null || findViewByPosition == findContainingItemView)) {
                return findViewByPosition;
            }
        }
        if (l(convertFocusDirectionToLayoutDirection)) {
            for (int i5 = this.f1027a - 1; i5 >= 0; i5--) {
                if (i5 != bVar.e) {
                    View findViewByPosition2 = findViewByPosition(z2 ? this.f1028b[i5].d() : this.f1028b[i5].e());
                    if (!(findViewByPosition2 == null || findViewByPosition2 == findContainingItemView)) {
                        return findViewByPosition2;
                    }
                }
            }
        } else {
            for (int i6 = 0; i6 < this.f1027a; i6++) {
                View findViewByPosition3 = findViewByPosition(z2 ? this.f1028b[i6].d() : this.f1028b[i6].e());
                if (!(findViewByPosition3 == null || findViewByPosition3 == findContainingItemView)) {
                    return findViewByPosition3;
                }
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View b2 = b(false);
            View a2 = a(false);
            if (b2 != null && a2 != null) {
                int position = getPosition(b2);
                int position2 = getPosition(a2);
                if (position < position2) {
                    accessibilityEvent.setFromIndex(position);
                    accessibilityEvent.setToIndex(position2);
                    return;
                }
                accessibilityEvent.setFromIndex(position2);
                accessibilityEvent.setToIndex(position);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.o oVar, RecyclerView.s sVar, View view, b.e.g.a.b bVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, bVar);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (this.e == 0) {
            i5 = layoutParams2.e();
            i4 = layoutParams2.f ? this.f1027a : 1;
            i3 = -1;
            i2 = -1;
        } else {
            i5 = -1;
            i4 = -1;
            i3 = layoutParams2.e();
            i2 = layoutParams2.f ? this.f1027a : 1;
        }
        bVar.b(b.c.a(i5, i4, i3, i2, layoutParams2.f, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsAdded(RecyclerView recyclerView, int i2, int i3) {
        a(i2, i3, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsChanged(RecyclerView recyclerView) {
        this.m.a();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsMoved(RecyclerView recyclerView, int i2, int i3, int i4) {
        a(i2, i3, 8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsRemoved(RecyclerView recyclerView, int i2, int i3) {
        a(i2, i3, 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onItemsUpdated(RecyclerView recyclerView, int i2, int i3, Object obj) {
        a(i2, i3, 4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onLayoutChildren(RecyclerView.o oVar, RecyclerView.s sVar) {
        c(oVar, sVar, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onLayoutCompleted(RecyclerView.s sVar) {
        super.onLayoutCompleted(sVar);
        this.k = -1;
        this.l = LinearLayoutManager.INVALID_OFFSET;
        this.q = null;
        this.t.b();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.q = (SavedState) parcelable;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public Parcelable onSaveInstanceState() {
        int i2;
        int i3;
        int[] iArr;
        SavedState savedState = this.q;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        savedState2.h = this.h;
        savedState2.i = this.o;
        savedState2.j = this.p;
        LazySpanLookup lazySpanLookup = this.m;
        if (lazySpanLookup == null || (iArr = lazySpanLookup.f1031a) == null) {
            savedState2.e = 0;
        } else {
            savedState2.f = iArr;
            savedState2.e = savedState2.f.length;
            savedState2.g = lazySpanLookup.f1032b;
        }
        if (getChildCount() > 0) {
            savedState2.f1037a = this.o ? f() : e();
            savedState2.f1038b = d();
            int i4 = this.f1027a;
            savedState2.f1039c = i4;
            savedState2.f1040d = new int[i4];
            for (int i5 = 0; i5 < this.f1027a; i5++) {
                if (this.o) {
                    i2 = this.f1028b[i5].a(LinearLayoutManager.INVALID_OFFSET);
                    if (i2 != Integer.MIN_VALUE) {
                        i3 = this.f1029c.b();
                    } else {
                        savedState2.f1040d[i5] = i2;
                    }
                } else {
                    i2 = this.f1028b[i5].b(LinearLayoutManager.INVALID_OFFSET);
                    if (i2 != Integer.MIN_VALUE) {
                        i3 = this.f1029c.f();
                    } else {
                        savedState2.f1040d[i5] = i2;
                    }
                }
                i2 -= i3;
                savedState2.f1040d[i5] = i2;
            }
        } else {
            savedState2.f1037a = -1;
            savedState2.f1038b = -1;
            savedState2.f1039c = 0;
        }
        return savedState2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void onScrollStateChanged(int i2) {
        if (i2 == 0) {
            c();
        }
    }

    /* access modifiers changed from: package-private */
    public int scrollBy(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (getChildCount() == 0 || i2 == 0) {
            return 0;
        }
        a(i2, sVar);
        int a2 = a(oVar, this.g, sVar);
        if (this.g.f1118b >= a2) {
            i2 = i2 < 0 ? -a2 : a2;
        }
        this.f1029c.a(-i2);
        this.o = this.i;
        r rVar = this.g;
        rVar.f1118b = 0;
        a(oVar, rVar);
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int scrollHorizontallyBy(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        return scrollBy(i2, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void scrollToPosition(int i2) {
        SavedState savedState = this.q;
        if (!(savedState == null || savedState.f1037a == i2)) {
            savedState.a();
        }
        this.k = i2;
        this.l = LinearLayoutManager.INVALID_OFFSET;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int scrollVerticallyBy(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        return scrollBy(i2, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void setMeasuredDimension(Rect rect, int i2, int i3) {
        int i4;
        int i5;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.e == 1) {
            i5 = RecyclerView.i.chooseSize(i3, rect.height() + paddingTop, getMinimumHeight());
            i4 = RecyclerView.i.chooseSize(i2, (this.f * this.f1027a) + paddingLeft, getMinimumWidth());
        } else {
            i4 = RecyclerView.i.chooseSize(i2, rect.width() + paddingLeft, getMinimumWidth());
            i5 = RecyclerView.i.chooseSize(i3, (this.f * this.f1027a) + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i4, i5);
    }

    public void setOrientation(int i2) {
        if (i2 == 0 || i2 == 1) {
            assertNotInLayoutOrScroll(null);
            if (i2 != this.e) {
                this.e = i2;
                x xVar = this.f1029c;
                this.f1029c = this.f1030d;
                this.f1030d = xVar;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        SavedState savedState = this.q;
        if (!(savedState == null || savedState.h == z)) {
            savedState.h = z;
        }
        this.h = z;
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.s sVar, int i2) {
        t tVar = new t(recyclerView.getContext());
        tVar.c(i2);
        startSmoothScroll(tVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean supportsPredictiveItemAnimations() {
        return this.q == null;
    }
}
