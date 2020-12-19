package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/* access modifiers changed from: package-private */
public final class p implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    static final ThreadLocal<p> f1105a = new ThreadLocal<>();

    /* renamed from: b  reason: collision with root package name */
    static Comparator<b> f1106b = new C0110o();

    /* renamed from: c  reason: collision with root package name */
    ArrayList<RecyclerView> f1107c = new ArrayList<>();

    /* renamed from: d  reason: collision with root package name */
    long f1108d;
    long e;
    private ArrayList<b> f = new ArrayList<>();

    /* access modifiers changed from: package-private */
    public static class a implements RecyclerView.i.a {

        /* renamed from: a  reason: collision with root package name */
        int f1109a;

        /* renamed from: b  reason: collision with root package name */
        int f1110b;

        /* renamed from: c  reason: collision with root package name */
        int[] f1111c;

        /* renamed from: d  reason: collision with root package name */
        int f1112d;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int[] iArr = this.f1111c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.f1112d = 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.i.a
        public void a(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Layout positions must be non-negative");
            } else if (i2 >= 0) {
                int i3 = this.f1112d * 2;
                int[] iArr = this.f1111c;
                if (iArr == null) {
                    this.f1111c = new int[4];
                    Arrays.fill(this.f1111c, -1);
                } else if (i3 >= iArr.length) {
                    this.f1111c = new int[(i3 * 2)];
                    System.arraycopy(iArr, 0, this.f1111c, 0, iArr.length);
                }
                int[] iArr2 = this.f1111c;
                iArr2[i3] = i;
                iArr2[i3 + 1] = i2;
                this.f1112d++;
            } else {
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
        }

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView, boolean z) {
            this.f1112d = 0;
            int[] iArr = this.f1111c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            RecyclerView.i iVar = recyclerView.w;
            if (recyclerView.v != null && iVar != null && iVar.isItemPrefetchEnabled()) {
                if (z) {
                    if (!recyclerView.n.c()) {
                        iVar.collectInitialPrefetchPositions(recyclerView.v.getItemCount(), this);
                    }
                } else if (!recyclerView.j()) {
                    iVar.collectAdjacentPrefetchPositions(this.f1109a, this.f1110b, recyclerView.ra, this);
                }
                int i = this.f1112d;
                if (i > iVar.mPrefetchMaxCountObserved) {
                    iVar.mPrefetchMaxCountObserved = i;
                    iVar.mPrefetchMaxObservedInInitialPrefetch = z;
                    recyclerView.l.j();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i) {
            if (this.f1111c != null) {
                int i2 = this.f1112d * 2;
                for (int i3 = 0; i3 < i2; i3 += 2) {
                    if (this.f1111c[i3] == i) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void b(int i, int i2) {
            this.f1109a = i;
            this.f1110b = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        public boolean f1113a;

        /* renamed from: b  reason: collision with root package name */
        public int f1114b;

        /* renamed from: c  reason: collision with root package name */
        public int f1115c;

        /* renamed from: d  reason: collision with root package name */
        public RecyclerView f1116d;
        public int e;

        b() {
        }

        public void a() {
            this.f1113a = false;
            this.f1114b = 0;
            this.f1115c = 0;
            this.f1116d = null;
            this.e = 0;
        }
    }

    p() {
    }

    private RecyclerView.v a(RecyclerView recyclerView, int i, long j) {
        if (a(recyclerView, i)) {
            return null;
        }
        RecyclerView.o oVar = recyclerView.l;
        try {
            recyclerView.q();
            RecyclerView.v a2 = oVar.a(i, false, j);
            if (a2 != null) {
                if (!a2.isBound() || a2.isInvalid()) {
                    oVar.a(a2, false);
                } else {
                    oVar.b(a2.itemView);
                }
            }
            return a2;
        } finally {
            recyclerView.a(false);
        }
    }

    private void a() {
        b bVar;
        int size = this.f1107c.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            RecyclerView recyclerView = this.f1107c.get(i2);
            if (recyclerView.getWindowVisibility() == 0) {
                recyclerView.qa.a(recyclerView, false);
                i += recyclerView.qa.f1112d;
            }
        }
        this.f.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            RecyclerView recyclerView2 = this.f1107c.get(i4);
            if (recyclerView2.getWindowVisibility() == 0) {
                a aVar = recyclerView2.qa;
                int abs = Math.abs(aVar.f1109a) + Math.abs(aVar.f1110b);
                int i5 = i3;
                for (int i6 = 0; i6 < aVar.f1112d * 2; i6 += 2) {
                    if (i5 >= this.f.size()) {
                        bVar = new b();
                        this.f.add(bVar);
                    } else {
                        bVar = this.f.get(i5);
                    }
                    int i7 = aVar.f1111c[i6 + 1];
                    bVar.f1113a = i7 <= abs;
                    bVar.f1114b = abs;
                    bVar.f1115c = i7;
                    bVar.f1116d = recyclerView2;
                    bVar.e = aVar.f1111c[i6];
                    i5++;
                }
                i3 = i5;
            }
        }
        Collections.sort(this.f, f1106b);
    }

    private void a(RecyclerView recyclerView, long j) {
        if (recyclerView != null) {
            if (recyclerView.N && recyclerView.o.b() != 0) {
                recyclerView.t();
            }
            a aVar = recyclerView.qa;
            aVar.a(recyclerView, true);
            if (aVar.f1112d != 0) {
                try {
                    b.e.c.a.a("RV Nested Prefetch");
                    recyclerView.ra.a(recyclerView.v);
                    for (int i = 0; i < aVar.f1112d * 2; i += 2) {
                        a(recyclerView, aVar.f1111c[i], j);
                    }
                } finally {
                    b.e.c.a.a();
                }
            }
        }
    }

    private void a(b bVar, long j) {
        RecyclerView.v a2 = a(bVar.f1116d, bVar.e, bVar.f1113a ? Long.MAX_VALUE : j);
        if (a2 != null && a2.mNestedRecyclerView != null && a2.isBound() && !a2.isInvalid()) {
            a(a2.mNestedRecyclerView.get(), j);
        }
    }

    static boolean a(RecyclerView recyclerView, int i) {
        int b2 = recyclerView.o.b();
        for (int i2 = 0; i2 < b2; i2++) {
            RecyclerView.v i3 = RecyclerView.i(recyclerView.o.d(i2));
            if (i3.mPosition == i && !i3.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    private void b(long j) {
        for (int i = 0; i < this.f.size(); i++) {
            b bVar = this.f.get(i);
            if (bVar.f1116d != null) {
                a(bVar, j);
                bVar.a();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(long j) {
        a();
        b(j);
    }

    public void a(RecyclerView recyclerView) {
        this.f1107c.add(recyclerView);
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.f1108d == 0) {
            this.f1108d = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.qa.b(i, i2);
    }

    public void b(RecyclerView recyclerView) {
        this.f1107c.remove(recyclerView);
    }

    public void run() {
        try {
            b.e.c.a.a("RV Prefetch");
            if (!this.f1107c.isEmpty()) {
                int size = this.f1107c.size();
                long j = 0;
                for (int i = 0; i < size; i++) {
                    RecyclerView recyclerView = this.f1107c.get(i);
                    if (recyclerView.getWindowVisibility() == 0) {
                        j = Math.max(recyclerView.getDrawingTime(), j);
                    }
                }
                if (j != 0) {
                    a(TimeUnit.MILLISECONDS.toNanos(j) + this.e);
                    this.f1108d = 0;
                    b.e.c.a.a();
                }
            }
        } finally {
            this.f1108d = 0;
            b.e.c.a.a();
        }
    }
}
