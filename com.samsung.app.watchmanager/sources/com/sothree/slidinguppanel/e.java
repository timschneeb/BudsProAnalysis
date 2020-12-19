package com.sothree.slidinguppanel;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.core.widget.l;
import b.e.g.h;
import b.e.g.r;
import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import java.util.Arrays;

public class e {

    /* renamed from: a  reason: collision with root package name */
    private static final Interpolator f2003a = new c();

    /* renamed from: b  reason: collision with root package name */
    private int f2004b;

    /* renamed from: c  reason: collision with root package name */
    private int f2005c;

    /* renamed from: d  reason: collision with root package name */
    private int f2006d = -1;
    private float[] e;
    private float[] f;
    private float[] g;
    private float[] h;
    private int[] i;
    private int[] j;
    private int[] k;
    private int l;
    private VelocityTracker m;
    private float n;
    private float o;
    private int p;
    private int q;
    private l r;
    private final a s;
    private View t;
    private boolean u;
    private final ViewGroup v;
    private final Runnable w = new d(this);

    public static abstract class a {
        public int a(int i) {
            return i;
        }

        public int a(View view) {
            return 0;
        }

        public int a(View view, int i, int i2) {
            return 0;
        }

        public void a(int i, int i2) {
        }

        public abstract void a(View view, float f, float f2);

        public abstract void a(View view, int i);

        public abstract void a(View view, int i, int i2, int i3, int i4);

        public abstract int b(View view);

        public abstract int b(View view, int i, int i2);

        public void b(int i, int i2) {
        }

        public boolean b(int i) {
            return false;
        }

        public abstract boolean b(View view, int i);

        public abstract void c(int i);
    }

    private e(Context context, ViewGroup viewGroup, Interpolator interpolator, a aVar) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        } else if (aVar != null) {
            this.v = viewGroup;
            this.s = aVar;
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.p = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
            this.f2005c = viewConfiguration.getScaledTouchSlop();
            this.n = (float) viewConfiguration.getScaledMaximumFlingVelocity();
            this.o = (float) viewConfiguration.getScaledMinimumFlingVelocity();
            this.r = l.a(context, interpolator == null ? f2003a : interpolator);
        } else {
            throw new IllegalArgumentException("Callback may not be null");
        }
    }

    private float a(float f2, float f3, float f4) {
        float abs = Math.abs(f2);
        if (abs < f3) {
            return 0.0f;
        }
        return abs > f4 ? f2 > 0.0f ? f4 : -f4 : f2;
    }

    private int a(int i2, int i3, int i4) {
        int abs = Math.abs(i2);
        if (abs < i3) {
            return 0;
        }
        return abs > i4 ? i2 > 0 ? i4 : -i4 : i2;
    }

    private int a(View view, int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        float f4;
        float f5;
        int a2 = a(i4, (int) this.o, (int) this.n);
        int a3 = a(i5, (int) this.o, (int) this.n);
        int abs = Math.abs(i2);
        int abs2 = Math.abs(i3);
        int abs3 = Math.abs(a2);
        int abs4 = Math.abs(a3);
        int i6 = abs3 + abs4;
        int i7 = abs + abs2;
        if (a2 != 0) {
            f3 = (float) abs3;
            f2 = (float) i6;
        } else {
            f3 = (float) abs;
            f2 = (float) i7;
        }
        float f6 = f3 / f2;
        if (a3 != 0) {
            f5 = (float) abs4;
            f4 = (float) i6;
        } else {
            f5 = (float) abs2;
            f4 = (float) i7;
        }
        float f7 = f5 / f4;
        return (int) ((((float) b(i2, a2, this.s.a(view))) * f6) + (((float) b(i3, a3, this.s.b(view))) * f7));
    }

    public static e a(ViewGroup viewGroup, float f2, Interpolator interpolator, a aVar) {
        e a2 = a(viewGroup, interpolator, aVar);
        a2.f2005c = (int) (((float) a2.f2005c) * (1.0f / f2));
        return a2;
    }

    public static e a(ViewGroup viewGroup, Interpolator interpolator, a aVar) {
        return new e(viewGroup.getContext(), viewGroup, interpolator, aVar);
    }

    private void a(float f2, float f3) {
        this.u = true;
        this.s.a(this.t, f2, f3);
        this.u = false;
        if (this.f2004b == 1) {
            a(0);
        }
    }

    private void a(float f2, float f3, int i2) {
        int i3 = 1;
        if (!a(f2, f3, i2, 1)) {
            i3 = 0;
        }
        if (a(f3, f2, i2, 4)) {
            i3 |= 4;
        }
        if (a(f2, f3, i2, 2)) {
            i3 |= 2;
        }
        if (a(f3, f2, i2, 8)) {
            i3 |= 8;
        }
        if (i3 != 0) {
            int[] iArr = this.j;
            iArr[i2] = iArr[i2] | i3;
            this.s.a(i3, i2);
        }
    }

    private void a(int i2, int i3, int i4, int i5) {
        int left = this.t.getLeft();
        int top = this.t.getTop();
        if (i4 != 0) {
            i2 = this.s.a(this.t, i2, i4);
            this.t.offsetLeftAndRight(i2 - left);
        }
        if (i5 != 0) {
            i3 = this.s.b(this.t, i3, i5);
            this.t.offsetTopAndBottom(i3 - top);
        }
        if (i4 != 0 || i5 != 0) {
            this.s.a(this.t, i2, i3, i2 - left, i3 - top);
        }
    }

    private boolean a(float f2, float f3, int i2, int i3) {
        float abs = Math.abs(f2);
        float abs2 = Math.abs(f3);
        if ((this.i[i2] & i3) != i3 || (this.q & i3) == 0 || (this.k[i2] & i3) == i3 || (this.j[i2] & i3) == i3) {
            return false;
        }
        int i4 = this.f2005c;
        if (abs <= ((float) i4) && abs2 <= ((float) i4)) {
            return false;
        }
        if (abs >= abs2 * 0.5f || !this.s.b(i3)) {
            return (this.j[i2] & i3) == 0 && abs > ((float) this.f2005c);
        }
        int[] iArr = this.k;
        iArr[i2] = iArr[i2] | i3;
        return false;
    }

    private boolean a(View view, float f2, float f3) {
        if (view == null) {
            return false;
        }
        boolean z = this.s.a(view) > 0;
        boolean z2 = this.s.b(view) > 0;
        if (!z || !z2) {
            return z ? Math.abs(f2) > ((float) this.f2005c) : z2 && Math.abs(f3) > ((float) this.f2005c);
        }
        int i2 = this.f2005c;
        return (f2 * f2) + (f3 * f3) > ((float) (i2 * i2));
    }

    private float b(float f2) {
        double d2 = (double) (f2 - 0.5f);
        Double.isNaN(d2);
        return (float) Math.sin((double) ((float) (d2 * 0.4712389167638204d)));
    }

    private int b(int i2, int i3, int i4) {
        if (i2 == 0) {
            return 0;
        }
        int width = this.v.getWidth();
        float f2 = (float) (width / 2);
        float b2 = f2 + (b(Math.min(1.0f, ((float) Math.abs(i2)) / ((float) width))) * f2);
        int abs = Math.abs(i3);
        return Math.min(abs > 0 ? Math.round(Math.abs(b2 / ((float) abs)) * 1000.0f) * 4 : (int) (((((float) Math.abs(i2)) / ((float) i4)) + 1.0f) * 256.0f), (int) InstallationUtils.CURRENT_OPERATION_UNKNOWN);
    }

    private void b(float f2, float f3, int i2) {
        c(i2);
        float[] fArr = this.e;
        this.g[i2] = f2;
        fArr[i2] = f2;
        float[] fArr2 = this.f;
        this.h[i2] = f3;
        fArr2[i2] = f3;
        this.i[i2] = d((int) f2, (int) f3);
        this.l |= 1 << i2;
    }

    private void b(int i2) {
        float[] fArr = this.e;
        if (fArr != null && fArr.length > i2) {
            fArr[i2] = 0.0f;
            this.f[i2] = 0.0f;
            this.g[i2] = 0.0f;
            this.h[i2] = 0.0f;
            this.i[i2] = 0;
            this.j[i2] = 0;
            this.k[i2] = 0;
            this.l = ((1 << i2) ^ -1) & this.l;
        }
    }

    private boolean b(int i2, int i3, int i4, int i5) {
        int left = this.t.getLeft();
        int top = this.t.getTop();
        int i6 = i2 - left;
        int i7 = i3 - top;
        if (i6 == 0 && i7 == 0) {
            this.r.a();
            a(0);
            return false;
        }
        this.r.a(left, top, i6, i7, a(this.t, i6, i7, i4, i5));
        a(2);
        return true;
    }

    private void c(int i2) {
        float[] fArr = this.e;
        if (fArr == null || fArr.length <= i2) {
            int i3 = i2 + 1;
            float[] fArr2 = new float[i3];
            float[] fArr3 = new float[i3];
            float[] fArr4 = new float[i3];
            float[] fArr5 = new float[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            float[] fArr6 = this.e;
            if (fArr6 != null) {
                System.arraycopy(fArr6, 0, fArr2, 0, fArr6.length);
                float[] fArr7 = this.f;
                System.arraycopy(fArr7, 0, fArr3, 0, fArr7.length);
                float[] fArr8 = this.g;
                System.arraycopy(fArr8, 0, fArr4, 0, fArr8.length);
                float[] fArr9 = this.h;
                System.arraycopy(fArr9, 0, fArr5, 0, fArr9.length);
                int[] iArr4 = this.i;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.j;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.k;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.e = fArr2;
            this.f = fArr3;
            this.g = fArr4;
            this.h = fArr5;
            this.i = iArr;
            this.j = iArr2;
            this.k = iArr3;
        }
    }

    private void c(MotionEvent motionEvent) {
        float[] fArr;
        int c2 = h.c(motionEvent);
        for (int i2 = 0; i2 < c2; i2++) {
            int b2 = h.b(motionEvent, i2);
            float c3 = h.c(motionEvent, i2);
            float d2 = h.d(motionEvent, i2);
            float[] fArr2 = this.g;
            if (fArr2 != null && (fArr = this.h) != null && fArr2.length > b2 && fArr.length > b2) {
                fArr2[b2] = c3;
                fArr[b2] = d2;
            }
        }
    }

    private int d(int i2, int i3) {
        int i4 = i2 < this.v.getLeft() + this.p ? 1 : 0;
        if (i3 < this.v.getTop() + this.p) {
            i4 |= 4;
        }
        if (i2 > this.v.getRight() - this.p) {
            i4 |= 2;
        }
        return i3 > this.v.getBottom() - this.p ? i4 | 8 : i4;
    }

    private void f() {
        float[] fArr = this.e;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.f, 0.0f);
            Arrays.fill(this.g, 0.0f);
            Arrays.fill(this.h, 0.0f);
            Arrays.fill(this.i, 0);
            Arrays.fill(this.j, 0);
            Arrays.fill(this.k, 0);
            this.l = 0;
        }
    }

    private void g() {
        this.m.computeCurrentVelocity(SAGUIDHelper.GUID_REQUEST_ID, this.n);
        a(a(r.a(this.m, this.f2006d), this.o, this.n), a(r.b(this.m, this.f2006d), this.o, this.n));
    }

    public View a(int i2, int i3) {
        for (int childCount = this.v.getChildCount() - 1; childCount >= 0; childCount--) {
            ViewGroup viewGroup = this.v;
            this.s.a(childCount);
            View childAt = viewGroup.getChildAt(childCount);
            if (i2 >= childAt.getLeft() && i2 < childAt.getRight() && i3 >= childAt.getTop() && i3 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public void a() {
        b();
        if (this.f2004b == 2) {
            int c2 = this.r.c();
            int d2 = this.r.d();
            this.r.a();
            int c3 = this.r.c();
            int d3 = this.r.d();
            this.s.a(this.t, c3, d3, c3 - c2, d3 - d2);
        }
        a(0);
    }

    public void a(float f2) {
        this.o = f2;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (this.f2004b != i2) {
            this.f2004b = i2;
            this.s.c(i2);
            if (this.f2004b == 0) {
                this.t = null;
            }
        }
    }

    public void a(MotionEvent motionEvent) {
        int i2;
        int b2 = h.b(motionEvent);
        int a2 = h.a(motionEvent);
        if (b2 == 0) {
            b();
        }
        if (this.m == null) {
            this.m = VelocityTracker.obtain();
        }
        this.m.addMovement(motionEvent);
        int i3 = 0;
        if (b2 != 0) {
            if (b2 != 1) {
                if (b2 == 2) {
                    if (this.f2004b != 1) {
                        int c2 = h.c(motionEvent);
                        while (i3 < c2) {
                            int b3 = h.b(motionEvent, i3);
                            float c3 = h.c(motionEvent, i3);
                            float d2 = h.d(motionEvent, i3);
                            float f2 = c3 - this.e[b3];
                            float f3 = d2 - this.f[b3];
                            a(f2, f3, b3);
                            if (this.f2004b != 1) {
                                View a3 = a((int) this.e[b3], (int) this.f[b3]);
                                if (a(a3, f2, f3) && b(a3, b3)) {
                                    break;
                                }
                                i3++;
                            } else {
                                break;
                            }
                        }
                    } else {
                        int a4 = h.a(motionEvent, this.f2006d);
                        float c4 = h.c(motionEvent, a4);
                        float d3 = h.d(motionEvent, a4);
                        float[] fArr = this.g;
                        int i4 = this.f2006d;
                        int i5 = (int) (c4 - fArr[i4]);
                        int i6 = (int) (d3 - this.h[i4]);
                        a(this.t.getLeft() + i5, this.t.getTop() + i6, i5, i6);
                    }
                    c(motionEvent);
                    return;
                } else if (b2 != 3) {
                    if (b2 == 5) {
                        int b4 = h.b(motionEvent, a2);
                        float c5 = h.c(motionEvent, a2);
                        float d4 = h.d(motionEvent, a2);
                        b(c5, d4, b4);
                        if (this.f2004b == 0) {
                            b(a((int) c5, (int) d4), b4);
                            int i7 = this.i[b4];
                            int i8 = this.q;
                            if ((i7 & i8) != 0) {
                                this.s.b(i7 & i8, b4);
                                return;
                            }
                            return;
                        } else if (b((int) c5, (int) d4)) {
                            b(this.t, b4);
                            return;
                        } else {
                            return;
                        }
                    } else if (b2 == 6) {
                        int b5 = h.b(motionEvent, a2);
                        if (this.f2004b == 1 && b5 == this.f2006d) {
                            int c6 = h.c(motionEvent);
                            while (true) {
                                if (i3 >= c6) {
                                    i2 = -1;
                                    break;
                                }
                                int b6 = h.b(motionEvent, i3);
                                if (b6 != this.f2006d) {
                                    View a5 = a((int) h.c(motionEvent, i3), (int) h.d(motionEvent, i3));
                                    View view = this.t;
                                    if (a5 == view && b(view, b6)) {
                                        i2 = this.f2006d;
                                        break;
                                    }
                                }
                                i3++;
                            }
                            if (i2 == -1) {
                                g();
                            }
                        }
                        b(b5);
                        return;
                    } else {
                        return;
                    }
                } else if (this.f2004b == 1) {
                    a(0.0f, 0.0f);
                }
            } else if (this.f2004b == 1) {
                g();
            }
            b();
            return;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int b7 = h.b(motionEvent, 0);
        View a6 = a((int) x, (int) y);
        b(x, y, b7);
        b(a6, b7);
        int i9 = this.i[b7];
        int i10 = this.q;
        if ((i9 & i10) != 0) {
            this.s.b(i9 & i10, b7);
        }
    }

    public void a(View view, int i2) {
        if (view.getParent() == this.v) {
            this.t = view;
            this.f2006d = i2;
            this.s.a(view, i2);
            a(1);
            return;
        }
        throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.v + ")");
    }

    public boolean a(View view, int i2, int i3) {
        return view != null && i2 >= view.getLeft() && i2 < view.getRight() && i3 >= view.getTop() && i3 < view.getBottom();
    }

    public boolean a(boolean z) {
        if (this.t == null) {
            return false;
        }
        if (this.f2004b == 2) {
            boolean b2 = this.r.b();
            int c2 = this.r.c();
            int d2 = this.r.d();
            int left = c2 - this.t.getLeft();
            int top = d2 - this.t.getTop();
            if (b2 || top == 0) {
                if (left != 0) {
                    this.t.offsetLeftAndRight(left);
                }
                if (top != 0) {
                    this.t.offsetTopAndBottom(top);
                }
                if (!(left == 0 && top == 0)) {
                    this.s.a(this.t, c2, d2, left, top);
                }
                if (b2 && c2 == this.r.e() && d2 == this.r.f()) {
                    this.r.a();
                    b2 = this.r.g();
                }
                if (!b2) {
                    if (z) {
                        this.v.post(this.w);
                    } else {
                        a(0);
                    }
                }
            } else {
                this.t.setTop(0);
                return true;
            }
        }
        return this.f2004b == 2;
    }

    public void b() {
        this.f2006d = -1;
        f();
        VelocityTracker velocityTracker = this.m;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.m = null;
        }
    }

    public boolean b(int i2, int i3) {
        return a(this.t, i2, i3);
    }

    public boolean b(MotionEvent motionEvent) {
        View a2;
        int b2 = h.b(motionEvent);
        int a3 = h.a(motionEvent);
        if (b2 == 0) {
            b();
        }
        if (this.m == null) {
            this.m = VelocityTracker.obtain();
        }
        this.m.addMovement(motionEvent);
        if (b2 != 0) {
            if (b2 != 1) {
                if (b2 == 2) {
                    int c2 = h.c(motionEvent);
                    for (int i2 = 0; i2 < c2 && this.e != null && this.f != null; i2++) {
                        int b3 = h.b(motionEvent, i2);
                        if (b3 < this.e.length && b3 < this.f.length) {
                            float c3 = h.c(motionEvent, i2);
                            float d2 = h.d(motionEvent, i2);
                            float f2 = c3 - this.e[b3];
                            float f3 = d2 - this.f[b3];
                            a(f2, f3, b3);
                            if (this.f2004b == 1) {
                                break;
                            }
                            View a4 = a((int) this.e[b3], (int) this.f[b3]);
                            if (a4 != null && a(a4, f2, f3) && b(a4, b3)) {
                                break;
                            }
                        }
                    }
                    c(motionEvent);
                } else if (b2 != 3) {
                    if (b2 == 5) {
                        int b4 = h.b(motionEvent, a3);
                        float c4 = h.c(motionEvent, a3);
                        float d3 = h.d(motionEvent, a3);
                        b(c4, d3, b4);
                        int i3 = this.f2004b;
                        if (i3 == 0) {
                            int i4 = this.i[b4];
                            int i5 = this.q;
                            if ((i4 & i5) != 0) {
                                this.s.b(i4 & i5, b4);
                            }
                        } else if (i3 == 2 && (a2 = a((int) c4, (int) d3)) == this.t) {
                            b(a2, b4);
                        }
                    } else if (b2 == 6) {
                        b(h.b(motionEvent, a3));
                    }
                }
            }
            b();
        } else {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int b5 = h.b(motionEvent, 0);
            b(x, y, b5);
            View a5 = a((int) x, (int) y);
            if (a5 == this.t && this.f2004b == 2) {
                b(a5, b5);
            }
            int i6 = this.i[b5];
            int i7 = this.q;
            if ((i6 & i7) != 0) {
                this.s.b(i6 & i7, b5);
            }
        }
        return this.f2004b == 1;
    }

    /* access modifiers changed from: package-private */
    public boolean b(View view, int i2) {
        if (view == this.t && this.f2006d == i2) {
            return true;
        }
        if (view == null || !this.s.b(view, i2)) {
            return false;
        }
        this.f2006d = i2;
        a(view, i2);
        return true;
    }

    public boolean b(View view, int i2, int i3) {
        this.t = view;
        this.f2006d = -1;
        return b(i2, i3, 0, 0);
    }

    public int c() {
        return this.f2005c;
    }

    public boolean c(int i2, int i3) {
        if (this.u) {
            return b(i2, i3, (int) r.a(this.m, this.f2006d), (int) r.b(this.m, this.f2006d));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    public int d() {
        return this.f2004b;
    }

    public boolean e() {
        return this.f2004b == 1;
    }
}
