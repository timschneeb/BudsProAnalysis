package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import androidx.fragment.app.AbstractC0091k;
import androidx.fragment.app.Fragment;
import b.e.g.t;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* access modifiers changed from: package-private */
public final class r extends AbstractC0091k implements LayoutInflater.Factory2 {

    /* renamed from: a  reason: collision with root package name */
    static boolean f785a = false;

    /* renamed from: b  reason: collision with root package name */
    static Field f786b;

    /* renamed from: c  reason: collision with root package name */
    static final Interpolator f787c = new DecelerateInterpolator(2.5f);

    /* renamed from: d  reason: collision with root package name */
    static final Interpolator f788d = new DecelerateInterpolator(1.5f);
    static final Interpolator e = new AccelerateInterpolator(2.5f);
    static final Interpolator f = new AccelerateInterpolator(1.5f);
    String A;
    boolean B;
    ArrayList<C0081a> C;
    ArrayList<Boolean> D;
    ArrayList<Fragment> E;
    Bundle F = null;
    SparseArray<Parcelable> G = null;
    ArrayList<i> H;
    s I;
    Runnable J = new RunnableC0092l(this);
    ArrayList<h> g;
    boolean h;
    int i = 0;
    final ArrayList<Fragment> j = new ArrayList<>();
    SparseArray<Fragment> k;
    ArrayList<C0081a> l;
    ArrayList<Fragment> m;
    ArrayList<C0081a> n;
    ArrayList<Integer> o;
    ArrayList<AbstractC0091k.c> p;
    private final CopyOnWriteArrayList<f> q = new CopyOnWriteArrayList<>();
    int r = 0;
    AbstractC0090j s;
    AbstractC0088h t;
    Fragment u;
    Fragment v;
    boolean w;
    boolean x;
    boolean y;
    boolean z;

    /* access modifiers changed from: private */
    public static class a extends b {

        /* renamed from: b  reason: collision with root package name */
        View f789b;

        a(View view, Animation.AnimationListener animationListener) {
            super(animationListener);
            this.f789b = view;
        }

        @Override // androidx.fragment.app.r.b
        public void onAnimationEnd(Animation animation) {
            if (t.v(this.f789b) || Build.VERSION.SDK_INT >= 24) {
                this.f789b.post(new q(this));
            } else {
                this.f789b.setLayerType(0, null);
            }
            super.onAnimationEnd(animation);
        }
    }

    /* access modifiers changed from: private */
    public static class b implements Animation.AnimationListener {

        /* renamed from: a  reason: collision with root package name */
        private final Animation.AnimationListener f790a;

        b(Animation.AnimationListener animationListener) {
            this.f790a = animationListener;
        }

        public void onAnimationEnd(Animation animation) {
            Animation.AnimationListener animationListener = this.f790a;
            if (animationListener != null) {
                animationListener.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            Animation.AnimationListener animationListener = this.f790a;
            if (animationListener != null) {
                animationListener.onAnimationRepeat(animation);
            }
        }

        public void onAnimationStart(Animation animation) {
            Animation.AnimationListener animationListener = this.f790a;
            if (animationListener != null) {
                animationListener.onAnimationStart(animation);
            }
        }
    }

    /* access modifiers changed from: private */
    public static class c {

        /* renamed from: a  reason: collision with root package name */
        public final Animation f791a;

        /* renamed from: b  reason: collision with root package name */
        public final Animator f792b;

        c(Animator animator) {
            this.f791a = null;
            this.f792b = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }

        c(Animation animation) {
            this.f791a = animation;
            this.f792b = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }
    }

    /* access modifiers changed from: private */
    public static class d extends AnimatorListenerAdapter {

        /* renamed from: a  reason: collision with root package name */
        View f793a;

        d(View view) {
            this.f793a = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.f793a.setLayerType(0, null);
            animator.removeListener(this);
        }

        public void onAnimationStart(Animator animator) {
            this.f793a.setLayerType(2, null);
        }
    }

    /* access modifiers changed from: private */
    public static class e extends AnimationSet implements Runnable {

        /* renamed from: a  reason: collision with root package name */
        private final ViewGroup f794a;

        /* renamed from: b  reason: collision with root package name */
        private final View f795b;

        /* renamed from: c  reason: collision with root package name */
        private boolean f796c;

        /* renamed from: d  reason: collision with root package name */
        private boolean f797d;
        private boolean e = true;

        e(Animation animation, ViewGroup viewGroup, View view) {
            super(false);
            this.f794a = viewGroup;
            this.f795b = view;
            addAnimation(animation);
            this.f794a.post(this);
        }

        public boolean getTransformation(long j, Transformation transformation) {
            this.e = true;
            if (this.f796c) {
                return !this.f797d;
            }
            if (!super.getTransformation(j, transformation)) {
                this.f796c = true;
                L.a(this.f794a, this);
            }
            return true;
        }

        public boolean getTransformation(long j, Transformation transformation, float f) {
            this.e = true;
            if (this.f796c) {
                return !this.f797d;
            }
            if (!super.getTransformation(j, transformation, f)) {
                this.f796c = true;
                L.a(this.f794a, this);
            }
            return true;
        }

        public void run() {
            if (this.f796c || !this.e) {
                this.f794a.endViewTransition(this.f795b);
                this.f797d = true;
                return;
            }
            this.e = false;
            this.f794a.post(this);
        }
    }

    /* access modifiers changed from: private */
    public static final class f {

        /* renamed from: a  reason: collision with root package name */
        final AbstractC0091k.b f798a;

        /* renamed from: b  reason: collision with root package name */
        final boolean f799b;
    }

    /* access modifiers changed from: package-private */
    public static class g {

        /* renamed from: a  reason: collision with root package name */
        public static final int[] f800a = {16842755, 16842960, 16842961};
    }

    /* access modifiers changed from: package-private */
    public interface h {
        boolean a(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2);
    }

    /* access modifiers changed from: package-private */
    public static class i implements Fragment.c {

        /* renamed from: a  reason: collision with root package name */
        final boolean f801a;

        /* renamed from: b  reason: collision with root package name */
        final C0081a f802b;

        /* renamed from: c  reason: collision with root package name */
        private int f803c;

        i(C0081a aVar, boolean z) {
            this.f801a = z;
            this.f802b = aVar;
        }

        @Override // androidx.fragment.app.Fragment.c
        public void a() {
            this.f803c++;
        }

        @Override // androidx.fragment.app.Fragment.c
        public void b() {
            this.f803c--;
            if (this.f803c == 0) {
                this.f802b.f754a.x();
            }
        }

        public void c() {
            C0081a aVar = this.f802b;
            aVar.f754a.a(aVar, this.f801a, false, false);
        }

        public void d() {
            boolean z = this.f803c > 0;
            r rVar = this.f802b.f754a;
            int size = rVar.j.size();
            for (int i = 0; i < size; i++) {
                Fragment fragment = rVar.j.get(i);
                fragment.a((Fragment.c) null);
                if (z && fragment.E()) {
                    fragment.Z();
                }
            }
            C0081a aVar = this.f802b;
            aVar.f754a.a(aVar, this.f801a, !z, true);
        }

        public boolean e() {
            return this.f803c == 0;
        }
    }

    r() {
    }

    private void A() {
        if (d()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.A != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.A);
        }
    }

    private void B() {
        this.h = false;
        this.D.clear();
        this.C.clear();
    }

    private void C() {
        SparseArray<Fragment> sparseArray = this.k;
        int size = sparseArray == null ? 0 : sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment valueAt = this.k.valueAt(i2);
            if (valueAt != null) {
                if (valueAt.g() != null) {
                    int x2 = valueAt.x();
                    View g2 = valueAt.g();
                    Animation animation = g2.getAnimation();
                    if (animation != null) {
                        animation.cancel();
                        g2.clearAnimation();
                    }
                    valueAt.a((View) null);
                    a(valueAt, x2, 0, 0, false);
                } else if (valueAt.h() != null) {
                    valueAt.h().end();
                }
            }
        }
    }

    private void D() {
        if (this.H != null) {
            while (!this.H.isEmpty()) {
                this.H.remove(0).d();
            }
        }
    }

    private int a(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3, b.c.d<Fragment> dVar) {
        int i4 = i3;
        for (int i5 = i3 - 1; i5 >= i2; i5--) {
            C0081a aVar = arrayList.get(i5);
            boolean booleanValue = arrayList2.get(i5).booleanValue();
            if (aVar.d() && !aVar.a(arrayList, i5 + 1, i3)) {
                if (this.H == null) {
                    this.H = new ArrayList<>();
                }
                i iVar = new i(aVar, booleanValue);
                this.H.add(iVar);
                aVar.a(iVar);
                if (booleanValue) {
                    aVar.b();
                } else {
                    aVar.b(false);
                }
                i4--;
                if (i5 != i4) {
                    arrayList.remove(i5);
                    arrayList.add(i4, aVar);
                }
                a(dVar);
            }
        }
        return i4;
    }

    private static Animation.AnimationListener a(Animation animation) {
        Throwable e2;
        String str;
        try {
            if (f786b == null) {
                f786b = Animation.class.getDeclaredField("mListener");
                f786b.setAccessible(true);
            }
            return (Animation.AnimationListener) f786b.get(animation);
        } catch (NoSuchFieldException e3) {
            e2 = e3;
            str = "No field with the name mListener is found in Animation class";
            Log.e("FragmentManager", str, e2);
            return null;
        } catch (IllegalAccessException e4) {
            e2 = e4;
            str = "Cannot access Animation's mListener field";
            Log.e("FragmentManager", str, e2);
            return null;
        }
    }

    static c a(Context context, float f2, float f3) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f3);
        alphaAnimation.setInterpolator(f788d);
        alphaAnimation.setDuration(220);
        return new c(alphaAnimation);
    }

    static c a(Context context, float f2, float f3, float f4, float f5) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(f787c);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f4, f5);
        alphaAnimation.setInterpolator(f788d);
        alphaAnimation.setDuration(220);
        animationSet.addAnimation(alphaAnimation);
        return new c(animationSet);
    }

    private void a(Fragment fragment, c cVar, int i2) {
        View view = fragment.K;
        ViewGroup viewGroup = fragment.J;
        viewGroup.startViewTransition(view);
        fragment.b(i2);
        Animation animation = cVar.f791a;
        if (animation != null) {
            e eVar = new e(animation, viewGroup, view);
            fragment.a(fragment.K);
            eVar.setAnimationListener(new n(this, a(eVar), viewGroup, fragment));
            b(view, cVar);
            fragment.K.startAnimation(eVar);
            return;
        }
        Animator animator = cVar.f792b;
        fragment.a(animator);
        animator.addListener(new o(this, viewGroup, view, fragment));
        animator.setTarget(fragment.K);
        b(fragment.K, cVar);
        animator.start();
    }

    private static void a(s sVar) {
        if (sVar != null) {
            List<Fragment> b2 = sVar.b();
            if (b2 != null) {
                for (Fragment fragment : b2) {
                    fragment.F = true;
                }
            }
            List<s> a2 = sVar.a();
            if (a2 != null) {
                for (s sVar2 : a2) {
                    a(sVar2);
                }
            }
        }
    }

    private void a(b.c.d<Fragment> dVar) {
        int i2 = this.r;
        if (i2 >= 1) {
            int min = Math.min(i2, 3);
            int size = this.j.size();
            for (int i3 = 0; i3 < size; i3++) {
                Fragment fragment = this.j.get(i3);
                if (fragment.f712c < min) {
                    a(fragment, min, fragment.p(), fragment.q(), false);
                    if (fragment.K != null && !fragment.C && fragment.P) {
                        dVar.add(fragment);
                    }
                }
            }
        }
    }

    private void a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new b.e.f.b("FragmentManager"));
        AbstractC0090j jVar = this.s;
        if (jVar != null) {
            try {
                jVar.a("  ", null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e("FragmentManager", "Failed dumping state", e2);
            }
        } else {
            a("  ", (FileDescriptor) null, printWriter, new String[0]);
        }
        throw runtimeException;
    }

    private void a(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2) {
        int indexOf;
        int indexOf2;
        ArrayList<i> arrayList3 = this.H;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i2 = 0;
        while (i2 < size) {
            i iVar = this.H.get(i2);
            if (arrayList == null || iVar.f801a || (indexOf2 = arrayList.indexOf(iVar.f802b)) == -1 || !arrayList2.get(indexOf2).booleanValue()) {
                if (iVar.e() || (arrayList != null && iVar.f802b.a(arrayList, 0, arrayList.size()))) {
                    this.H.remove(i2);
                    i2--;
                    size--;
                    if (arrayList == null || iVar.f801a || (indexOf = arrayList.indexOf(iVar.f802b)) == -1 || !arrayList2.get(indexOf).booleanValue()) {
                        iVar.d();
                    }
                }
                i2++;
            }
            iVar.c();
            i2++;
        }
    }

    private static void a(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        while (i2 < i3) {
            C0081a aVar = arrayList.get(i2);
            boolean z2 = true;
            if (arrayList2.get(i2).booleanValue()) {
                aVar.a(-1);
                if (i2 != i3 - 1) {
                    z2 = false;
                }
                aVar.b(z2);
            } else {
                aVar.a(1);
                aVar.b();
            }
            i2++;
        }
    }

    static boolean a(Animator animator) {
        PropertyValuesHolder[] values;
        if (animator == null) {
            return false;
        }
        if (animator instanceof ValueAnimator) {
            for (PropertyValuesHolder propertyValuesHolder : ((ValueAnimator) animator).getValues()) {
                if ("alpha".equals(propertyValuesHolder.getPropertyName())) {
                    return true;
                }
            }
        } else if (animator instanceof AnimatorSet) {
            ArrayList<Animator> childAnimations = ((AnimatorSet) animator).getChildAnimations();
            for (int i2 = 0; i2 < childAnimations.size(); i2++) {
                if (a(childAnimations.get(i2))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean a(View view, c cVar) {
        return view != null && cVar != null && Build.VERSION.SDK_INT >= 19 && view.getLayerType() == 0 && t.t(view) && a(cVar);
    }

    static boolean a(c cVar) {
        Animation animation = cVar.f791a;
        if (animation instanceof AlphaAnimation) {
            return true;
        }
        if (!(animation instanceof AnimationSet)) {
            return a(cVar.f792b);
        }
        List<Animation> animations = ((AnimationSet) animation).getAnimations();
        for (int i2 = 0; i2 < animations.size(); i2++) {
            if (animations.get(i2) instanceof AlphaAnimation) {
                return true;
            }
        }
        return false;
    }

    private boolean a(String str, int i2, int i3) {
        AbstractC0091k P;
        p();
        c(true);
        Fragment fragment = this.v;
        if (fragment != null && i2 < 0 && str == null && (P = fragment.P()) != null && P.e()) {
            return true;
        }
        boolean a2 = a(this.C, this.D, str, i2, i3);
        if (a2) {
            this.h = true;
            try {
                c(this.C, this.D);
            } finally {
                B();
            }
        }
        o();
        z();
        return a2;
    }

    public static int b(int i2, boolean z2) {
        if (i2 == 4097) {
            return z2 ? 1 : 2;
        }
        if (i2 == 4099) {
            return z2 ? 5 : 6;
        }
        if (i2 != 8194) {
            return -1;
        }
        return z2 ? 3 : 4;
    }

    private static void b(View view, c cVar) {
        if (view != null && cVar != null && a(view, cVar)) {
            Animator animator = cVar.f792b;
            if (animator != null) {
                animator.addListener(new d(view));
                return;
            }
            Animation.AnimationListener a2 = a(cVar.f791a);
            view.setLayerType(2, null);
            cVar.f791a.setAnimationListener(new a(view, a2));
        }
    }

    private void b(b.c.d<Fragment> dVar) {
        int size = dVar.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment c2 = dVar.c(i2);
            if (!c2.m) {
                View y2 = c2.y();
                c2.R = y2.getAlpha();
                y2.setAlpha(0.0f);
            }
        }
    }

    private void b(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        int i4;
        int i5;
        int i6 = i2;
        boolean z2 = arrayList.get(i6).t;
        ArrayList<Fragment> arrayList3 = this.E;
        if (arrayList3 == null) {
            this.E = new ArrayList<>();
        } else {
            arrayList3.clear();
        }
        this.E.addAll(this.j);
        Fragment r2 = r();
        boolean z3 = false;
        for (int i7 = i6; i7 < i3; i7++) {
            C0081a aVar = arrayList.get(i7);
            r2 = !arrayList2.get(i7).booleanValue() ? aVar.a(this.E, r2) : aVar.b(this.E, r2);
            z3 = z3 || aVar.i;
        }
        this.E.clear();
        if (!z2) {
            B.a(this, arrayList, arrayList2, i2, i3, false);
        }
        a(arrayList, arrayList2, i2, i3);
        if (z2) {
            b.c.d<Fragment> dVar = new b.c.d<>();
            a(dVar);
            int a2 = a(arrayList, arrayList2, i2, i3, dVar);
            b(dVar);
            i4 = a2;
        } else {
            i4 = i3;
        }
        if (i4 != i6 && z2) {
            B.a(this, arrayList, arrayList2, i2, i4, true);
            a(this.r, true);
        }
        while (i6 < i3) {
            C0081a aVar2 = arrayList.get(i6);
            if (arrayList2.get(i6).booleanValue() && (i5 = aVar2.m) >= 0) {
                b(i5);
                aVar2.m = -1;
            }
            aVar2.e();
            i6++;
        }
        if (z3) {
            t();
        }
    }

    private boolean b(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2) {
        synchronized (this) {
            if (this.g != null) {
                if (this.g.size() != 0) {
                    int size = this.g.size();
                    boolean z2 = false;
                    for (int i2 = 0; i2 < size; i2++) {
                        z2 |= this.g.get(i2).a(arrayList, arrayList2);
                    }
                    this.g.clear();
                    this.s.e().removeCallbacks(this.J);
                    return z2;
                }
            }
            return false;
        }
    }

    private void c(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2) {
        if (arrayList != null && !arrayList.isEmpty()) {
            if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
                throw new IllegalStateException("Internal error with the back stack records");
            }
            a(arrayList, arrayList2);
            int size = arrayList.size();
            int i2 = 0;
            int i3 = 0;
            while (i2 < size) {
                if (!arrayList.get(i2).t) {
                    if (i3 != i2) {
                        b(arrayList, arrayList2, i3, i2);
                    }
                    i3 = i2 + 1;
                    if (arrayList2.get(i2).booleanValue()) {
                        while (i3 < size && arrayList2.get(i3).booleanValue() && !arrayList.get(i3).t) {
                            i3++;
                        }
                    }
                    b(arrayList, arrayList2, i2, i3);
                    i2 = i3 - 1;
                }
                i2++;
            }
            if (i3 != size) {
                b(arrayList, arrayList2, i3, size);
            }
        }
    }

    private void c(boolean z2) {
        if (this.h) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.s == null) {
            throw new IllegalStateException("Fragment host has been destroyed");
        } else if (Looper.myLooper() == this.s.e().getLooper()) {
            if (!z2) {
                A();
            }
            if (this.C == null) {
                this.C = new ArrayList<>();
                this.D = new ArrayList<>();
            }
            this.h = true;
            try {
                a((ArrayList<C0081a>) null, (ArrayList<Boolean>) null);
            } finally {
                this.h = false;
            }
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    public static int d(int i2) {
        if (i2 == 4097) {
            return 8194;
        }
        if (i2 != 4099) {
            return i2 != 8194 ? 0 : 4097;
        }
        return 4099;
    }

    /* JADX INFO: finally extract failed */
    private void e(int i2) {
        try {
            this.h = true;
            a(i2, false);
            this.h = false;
            p();
        } catch (Throwable th) {
            this.h = false;
            throw th;
        }
    }

    private Fragment p(Fragment fragment) {
        ViewGroup viewGroup = fragment.J;
        View view = fragment.K;
        if (!(viewGroup == null || view == null)) {
            for (int indexOf = this.j.indexOf(fragment) - 1; indexOf >= 0; indexOf--) {
                Fragment fragment2 = this.j.get(indexOf);
                if (fragment2.J == viewGroup && fragment2.K != null) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    private void z() {
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray != null) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                if (this.k.valueAt(size) == null) {
                    SparseArray<Fragment> sparseArray2 = this.k;
                    sparseArray2.delete(sparseArray2.keyAt(size));
                }
            }
        }
    }

    public Fragment a(int i2) {
        for (int size = this.j.size() - 1; size >= 0; size--) {
            Fragment fragment = this.j.get(size);
            if (fragment != null && fragment.z == i2) {
                return fragment;
            }
        }
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            Fragment valueAt = this.k.valueAt(size2);
            if (valueAt != null && valueAt.z == i2) {
                return valueAt;
            }
        }
        return null;
    }

    public Fragment a(Bundle bundle, String str) {
        int i2 = bundle.getInt(str, -1);
        if (i2 == -1) {
            return null;
        }
        Fragment fragment = this.k.get(i2);
        if (fragment != null) {
            return fragment;
        }
        a(new IllegalStateException("Fragment no longer exists for key " + str + ": index " + i2));
        throw null;
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public Fragment a(String str) {
        if (str != null) {
            for (int size = this.j.size() - 1; size >= 0; size--) {
                Fragment fragment = this.j.get(size);
                if (fragment != null && str.equals(fragment.B)) {
                    return fragment;
                }
            }
        }
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            Fragment valueAt = this.k.valueAt(size2);
            if (valueAt != null && str.equals(valueAt.B)) {
                return valueAt;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public c a(Fragment fragment, int i2, boolean z2, int i3) {
        int b2;
        int p2 = fragment.p();
        Animation a2 = fragment.a(i2, z2, p2);
        if (a2 != null) {
            return new c(a2);
        }
        Animator b3 = fragment.b(i2, z2, p2);
        if (b3 != null) {
            return new c(b3);
        }
        if (p2 != 0) {
            boolean equals = "anim".equals(this.s.c().getResources().getResourceTypeName(p2));
            boolean z3 = false;
            if (equals) {
                try {
                    Animation loadAnimation = AnimationUtils.loadAnimation(this.s.c(), p2);
                    if (loadAnimation != null) {
                        return new c(loadAnimation);
                    }
                    z3 = true;
                } catch (Resources.NotFoundException e2) {
                    throw e2;
                } catch (RuntimeException unused) {
                }
            }
            if (!z3) {
                try {
                    Animator loadAnimator = AnimatorInflater.loadAnimator(this.s.c(), p2);
                    if (loadAnimator != null) {
                        return new c(loadAnimator);
                    }
                } catch (RuntimeException e3) {
                    if (!equals) {
                        Animation loadAnimation2 = AnimationUtils.loadAnimation(this.s.c(), p2);
                        if (loadAnimation2 != null) {
                            return new c(loadAnimation2);
                        }
                    } else {
                        throw e3;
                    }
                }
            }
        }
        if (i2 == 0 || (b2 = b(i2, z2)) < 0) {
            return null;
        }
        switch (b2) {
            case 1:
                return a(this.s.c(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return a(this.s.c(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return a(this.s.c(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return a(this.s.c(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return a(this.s.c(), 0.0f, 1.0f);
            case 6:
                return a(this.s.c(), 1.0f, 0.0f);
            default:
                if (i3 == 0 && this.s.h()) {
                    i3 = this.s.g();
                }
                if (i3 == 0) {
                }
                return null;
        }
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public w a() {
        return new C0081a(this);
    }

    public void a(int i2, C0081a aVar) {
        synchronized (this) {
            if (this.n == null) {
                this.n = new ArrayList<>();
            }
            int size = this.n.size();
            if (i2 < size) {
                if (f785a) {
                    Log.v("FragmentManager", "Setting back stack index " + i2 + " to " + aVar);
                }
                this.n.set(i2, aVar);
            } else {
                while (size < i2) {
                    this.n.add(null);
                    if (this.o == null) {
                        this.o = new ArrayList<>();
                    }
                    if (f785a) {
                        Log.v("FragmentManager", "Adding available back stack index " + size);
                    }
                    this.o.add(Integer.valueOf(size));
                    size++;
                }
                if (f785a) {
                    Log.v("FragmentManager", "Adding back stack index " + i2 + " with " + aVar);
                }
                this.n.add(aVar);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, boolean z2) {
        AbstractC0090j jVar;
        if (this.s == null && i2 != 0) {
            throw new IllegalStateException("No activity");
        } else if (z2 || i2 != this.r) {
            this.r = i2;
            if (this.k != null) {
                int size = this.j.size();
                for (int i3 = 0; i3 < size; i3++) {
                    h(this.j.get(i3));
                }
                int size2 = this.k.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    Fragment valueAt = this.k.valueAt(i4);
                    if (valueAt != null && ((valueAt.n || valueAt.D) && !valueAt.P)) {
                        h(valueAt);
                    }
                }
                y();
                if (this.w && (jVar = this.s) != null && this.r == 4) {
                    jVar.i();
                    this.w = false;
                }
            }
        }
    }

    public void a(Configuration configuration) {
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null) {
                fragment.a(configuration);
            }
        }
    }

    public void a(Bundle bundle, String str, Fragment fragment) {
        int i2 = fragment.g;
        if (i2 >= 0) {
            bundle.putInt(str, i2);
            return;
        }
        a(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        throw null;
    }

    /* access modifiers changed from: package-private */
    public void a(Parcelable parcelable, s sVar) {
        List<androidx.lifecycle.t> list;
        List<s> list2;
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.f724a != null) {
                if (sVar != null) {
                    List<Fragment> b2 = sVar.b();
                    list2 = sVar.a();
                    list = sVar.c();
                    int size = b2 != null ? b2.size() : 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        Fragment fragment = b2.get(i2);
                        if (f785a) {
                            Log.v("FragmentManager", "restoreAllState: re-attaching retained " + fragment);
                        }
                        int i3 = 0;
                        while (true) {
                            FragmentState[] fragmentStateArr = fragmentManagerState.f724a;
                            if (i3 >= fragmentStateArr.length || fragmentStateArr[i3].f729b == fragment.g) {
                                FragmentState[] fragmentStateArr2 = fragmentManagerState.f724a;
                            } else {
                                i3++;
                            }
                        }
                        FragmentState[] fragmentStateArr22 = fragmentManagerState.f724a;
                        if (i3 != fragmentStateArr22.length) {
                            FragmentState fragmentState = fragmentStateArr22[i3];
                            fragmentState.l = fragment;
                            fragment.e = null;
                            fragment.s = 0;
                            fragment.p = false;
                            fragment.m = false;
                            fragment.j = null;
                            Bundle bundle = fragmentState.k;
                            if (bundle != null) {
                                bundle.setClassLoader(this.s.c().getClassLoader());
                                fragment.e = fragmentState.k.getSparseParcelableArray("android:view_state");
                                fragment.f713d = fragmentState.k;
                            }
                        } else {
                            a(new IllegalStateException("Could not find active fragment with index " + fragment.g));
                            throw null;
                        }
                    }
                } else {
                    list2 = null;
                    list = null;
                }
                this.k = new SparseArray<>(fragmentManagerState.f724a.length);
                int i4 = 0;
                while (true) {
                    FragmentState[] fragmentStateArr3 = fragmentManagerState.f724a;
                    if (i4 >= fragmentStateArr3.length) {
                        break;
                    }
                    FragmentState fragmentState2 = fragmentStateArr3[i4];
                    if (fragmentState2 != null) {
                        Fragment a2 = fragmentState2.a(this.s, this.t, this.u, (list2 == null || i4 >= list2.size()) ? null : list2.get(i4), (list == null || i4 >= list.size()) ? null : list.get(i4));
                        if (f785a) {
                            Log.v("FragmentManager", "restoreAllState: active #" + i4 + ": " + a2);
                        }
                        this.k.put(a2.g, a2);
                        fragmentState2.l = null;
                    }
                    i4++;
                }
                if (sVar != null) {
                    List<Fragment> b3 = sVar.b();
                    int size2 = b3 != null ? b3.size() : 0;
                    for (int i5 = 0; i5 < size2; i5++) {
                        Fragment fragment2 = b3.get(i5);
                        int i6 = fragment2.k;
                        if (i6 >= 0) {
                            fragment2.j = this.k.get(i6);
                            if (fragment2.j == null) {
                                Log.w("FragmentManager", "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.k);
                            }
                        }
                    }
                }
                this.j.clear();
                if (fragmentManagerState.f725b != null) {
                    int i7 = 0;
                    while (true) {
                        int[] iArr = fragmentManagerState.f725b;
                        if (i7 >= iArr.length) {
                            break;
                        }
                        Fragment fragment3 = this.k.get(iArr[i7]);
                        if (fragment3 != null) {
                            fragment3.m = true;
                            if (f785a) {
                                Log.v("FragmentManager", "restoreAllState: added #" + i7 + ": " + fragment3);
                            }
                            if (!this.j.contains(fragment3)) {
                                synchronized (this.j) {
                                    this.j.add(fragment3);
                                }
                                i7++;
                            } else {
                                throw new IllegalStateException("Already added!");
                            }
                        } else {
                            a(new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.f725b[i7]));
                            throw null;
                        }
                    }
                }
                BackStackState[] backStackStateArr = fragmentManagerState.f726c;
                if (backStackStateArr != null) {
                    this.l = new ArrayList<>(backStackStateArr.length);
                    int i8 = 0;
                    while (true) {
                        BackStackState[] backStackStateArr2 = fragmentManagerState.f726c;
                        if (i8 >= backStackStateArr2.length) {
                            break;
                        }
                        C0081a a3 = backStackStateArr2[i8].a(this);
                        if (f785a) {
                            Log.v("FragmentManager", "restoreAllState: back stack #" + i8 + " (index " + a3.m + "): " + a3);
                            PrintWriter printWriter = new PrintWriter(new b.e.f.b("FragmentManager"));
                            a3.a("  ", printWriter, false);
                            printWriter.close();
                        }
                        this.l.add(a3);
                        int i9 = a3.m;
                        if (i9 >= 0) {
                            a(i9, a3);
                        }
                        i8++;
                    }
                } else {
                    this.l = null;
                }
                int i10 = fragmentManagerState.f727d;
                if (i10 >= 0) {
                    this.v = this.k.get(i10);
                }
                this.i = fragmentManagerState.e;
            }
        }
    }

    public void a(Menu menu) {
        if (this.r >= 1) {
            for (int i2 = 0; i2 < this.j.size(); i2++) {
                Fragment fragment = this.j.get(i2);
                if (fragment != null) {
                    fragment.c(menu);
                }
            }
        }
    }

    public void a(Fragment fragment) {
        if (f785a) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.D) {
            fragment.D = false;
            if (fragment.m) {
                return;
            }
            if (!this.j.contains(fragment)) {
                if (f785a) {
                    Log.v("FragmentManager", "add from attach: " + fragment);
                }
                synchronized (this.j) {
                    this.j.add(fragment);
                }
                fragment.m = true;
                if (fragment.G && fragment.H) {
                    this.w = true;
                    return;
                }
                return;
            }
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0072, code lost:
        if (r0 != 3) goto L_0x041a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0294  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x02b4  */
    /* JADX WARNING: Removed duplicated region for block: B:214:0x041f  */
    /* JADX WARNING: Removed duplicated region for block: B:216:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.fragment.app.Fragment r17, int r18, int r19, int r20, boolean r21) {
        /*
        // Method dump skipped, instructions count: 1101
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.r.a(androidx.fragment.app.Fragment, int, int, int, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, Context context, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).a(fragment, context, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.a(this, fragment, context);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).a(fragment, bundle, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.a(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, View view, Bundle bundle, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).a(fragment, view, bundle, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.a(this, fragment, view, bundle);
            }
        }
    }

    public void a(Fragment fragment, boolean z2) {
        if (f785a) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        f(fragment);
        if (fragment.D) {
            return;
        }
        if (!this.j.contains(fragment)) {
            synchronized (this.j) {
                this.j.add(fragment);
            }
            fragment.m = true;
            fragment.n = false;
            if (fragment.K == null) {
                fragment.Q = false;
            }
            if (fragment.G && fragment.H) {
                this.w = true;
            }
            if (z2) {
                i(fragment);
                return;
            }
            return;
        }
        throw new IllegalStateException("Fragment already added: " + fragment);
    }

    /* access modifiers changed from: package-private */
    public void a(C0081a aVar) {
        if (this.l == null) {
            this.l = new ArrayList<>();
        }
        this.l.add(aVar);
    }

    /* access modifiers changed from: package-private */
    public void a(C0081a aVar, boolean z2, boolean z3, boolean z4) {
        if (z2) {
            aVar.b(z4);
        } else {
            aVar.b();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(aVar);
        arrayList2.add(Boolean.valueOf(z2));
        if (z3) {
            B.a(this, (ArrayList<C0081a>) arrayList, (ArrayList<Boolean>) arrayList2, 0, 1, true);
        }
        if (z4) {
            a(this.r, true);
        }
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray != null) {
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                Fragment valueAt = this.k.valueAt(i2);
                if (valueAt != null && valueAt.K != null && valueAt.P && aVar.b(valueAt.A)) {
                    float f2 = valueAt.R;
                    if (f2 > 0.0f) {
                        valueAt.K.setAlpha(f2);
                    }
                    if (z4) {
                        valueAt.R = 0.0f;
                    } else {
                        valueAt.R = -1.0f;
                        valueAt.P = false;
                    }
                }
            }
        }
    }

    public void a(AbstractC0090j jVar, AbstractC0088h hVar, Fragment fragment) {
        if (this.s == null) {
            this.s = jVar;
            this.t = hVar;
            this.u = fragment;
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void a(h hVar, boolean z2) {
        if (!z2) {
            A();
        }
        synchronized (this) {
            if (!this.z) {
                if (this.s != null) {
                    if (this.g == null) {
                        this.g = new ArrayList<>();
                    }
                    this.g.add(hVar);
                    x();
                    return;
                }
            }
            if (!z2) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        String str2 = str + "    ";
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray != null && (size5 = sparseArray.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i2 = 0; i2 < size5; i2++) {
                Fragment valueAt = this.k.valueAt(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(valueAt);
                if (valueAt != null) {
                    valueAt.a(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        int size6 = this.j.size();
        if (size6 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i3 = 0; i3 < size6; i3++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(this.j.get(i3).toString());
            }
        }
        ArrayList<Fragment> arrayList = this.m;
        if (arrayList != null && (size4 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i4 = 0; i4 < size4; i4++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(this.m.get(i4).toString());
            }
        }
        ArrayList<C0081a> arrayList2 = this.l;
        if (arrayList2 != null && (size3 = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i5 = 0; i5 < size3; i5++) {
                C0081a aVar = this.l.get(i5);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i5);
                printWriter.print(": ");
                printWriter.println(aVar.toString());
                aVar.a(str2, fileDescriptor, printWriter, strArr);
            }
        }
        synchronized (this) {
            if (this.n != null && (size2 = this.n.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i6 = 0; i6 < size2; i6++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i6);
                    printWriter.print(": ");
                    printWriter.println((C0081a) this.n.get(i6));
                }
            }
            if (this.o != null && this.o.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.o.toArray()));
            }
        }
        ArrayList<h> arrayList3 = this.g;
        if (arrayList3 != null && (size = arrayList3.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i7 = 0; i7 < size; i7++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i7);
                printWriter.print(": ");
                printWriter.println((h) this.g.get(i7));
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.s);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.t);
        if (this.u != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.u);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.r);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.x);
        printWriter.print(" mStopped=");
        printWriter.print(this.y);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.z);
        if (this.w) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.w);
        }
        if (this.A != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.A);
        }
    }

    public void a(boolean z2) {
        for (int size = this.j.size() - 1; size >= 0; size--) {
            Fragment fragment = this.j.get(size);
            if (fragment != null) {
                fragment.d(z2);
            }
        }
    }

    public boolean a(Menu menu, MenuInflater menuInflater) {
        if (this.r < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z2 = false;
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null && fragment.b(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
                z2 = true;
            }
        }
        if (this.m != null) {
            for (int i3 = 0; i3 < this.m.size(); i3++) {
                Fragment fragment2 = this.m.get(i3);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.I();
                }
            }
        }
        this.m = arrayList;
        return z2;
    }

    public boolean a(MenuItem menuItem) {
        if (this.r < 1) {
            return false;
        }
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null && fragment.c(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2, String str, int i2, int i3) {
        int i4;
        ArrayList<C0081a> arrayList3 = this.l;
        if (arrayList3 == null) {
            return false;
        }
        if (str == null && i2 < 0 && (i3 & 1) == 0) {
            int size = arrayList3.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.l.remove(size));
            arrayList2.add(true);
        } else {
            if (str != null || i2 >= 0) {
                i4 = this.l.size() - 1;
                while (i4 >= 0) {
                    C0081a aVar = this.l.get(i4);
                    if ((str != null && str.equals(aVar.c())) || (i2 >= 0 && i2 == aVar.m)) {
                        break;
                    }
                    i4--;
                }
                if (i4 < 0) {
                    return false;
                }
                if ((i3 & 1) != 0) {
                    while (true) {
                        i4--;
                        if (i4 < 0) {
                            break;
                        }
                        C0081a aVar2 = this.l.get(i4);
                        if ((str == null || !str.equals(aVar2.c())) && (i2 < 0 || i2 != aVar2.m)) {
                            break;
                        }
                    }
                }
            } else {
                i4 = -1;
            }
            if (i4 == this.l.size() - 1) {
                return false;
            }
            for (int size2 = this.l.size() - 1; size2 > i4; size2--) {
                arrayList.add(this.l.remove(size2));
                arrayList2.add(true);
            }
        }
        return true;
    }

    public int b(C0081a aVar) {
        synchronized (this) {
            if (this.o != null) {
                if (this.o.size() > 0) {
                    int intValue = this.o.remove(this.o.size() - 1).intValue();
                    if (f785a) {
                        Log.v("FragmentManager", "Adding back stack index " + intValue + " with " + aVar);
                    }
                    this.n.set(intValue, aVar);
                    return intValue;
                }
            }
            if (this.n == null) {
                this.n = new ArrayList<>();
            }
            int size = this.n.size();
            if (f785a) {
                Log.v("FragmentManager", "Setting back stack index " + size + " to " + aVar);
            }
            this.n.add(aVar);
            return size;
        }
    }

    public Fragment b(String str) {
        Fragment a2;
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray == null || str == null) {
            return null;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            Fragment valueAt = this.k.valueAt(size);
            if (!(valueAt == null || (a2 = valueAt.a(str)) == null)) {
                return a2;
            }
        }
        return null;
    }

    public void b(int i2) {
        synchronized (this) {
            this.n.set(i2, null);
            if (this.o == null) {
                this.o = new ArrayList<>();
            }
            if (f785a) {
                Log.v("FragmentManager", "Freeing back stack index " + i2);
            }
            this.o.add(Integer.valueOf(i2));
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment) {
        Animator animator;
        if (fragment.K != null) {
            c a2 = a(fragment, fragment.q(), !fragment.C, fragment.r());
            if (a2 == null || (animator = a2.f792b) == null) {
                if (a2 != null) {
                    b(fragment.K, a2);
                    fragment.K.startAnimation(a2.f791a);
                    a2.f791a.start();
                }
                fragment.K.setVisibility((!fragment.C || fragment.C()) ? 0 : 8);
                if (fragment.C()) {
                    fragment.f(false);
                }
            } else {
                animator.setTarget(fragment.K);
                if (!fragment.C) {
                    fragment.K.setVisibility(0);
                } else if (fragment.C()) {
                    fragment.f(false);
                } else {
                    ViewGroup viewGroup = fragment.J;
                    View view = fragment.K;
                    viewGroup.startViewTransition(view);
                    a2.f792b.addListener(new p(this, viewGroup, view, fragment));
                }
                b(fragment.K, a2);
                a2.f792b.start();
            }
        }
        if (fragment.m && fragment.G && fragment.H) {
            this.w = true;
        }
        fragment.Q = false;
        fragment.a(fragment.C);
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, Context context, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).b(fragment, context, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.b(this, fragment, context);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).b(fragment, bundle, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.b(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).b(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.a(this, fragment);
            }
        }
    }

    public void b(boolean z2) {
        for (int size = this.j.size() - 1; size >= 0; size--) {
            Fragment fragment = this.j.get(size);
            if (fragment != null) {
                fragment.e(z2);
            }
        }
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public boolean b() {
        boolean p2 = p();
        D();
        return p2;
    }

    public boolean b(Menu menu) {
        if (this.r < 1) {
            return false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null && fragment.d(menu)) {
                z2 = true;
            }
        }
        return z2;
    }

    public boolean b(MenuItem menuItem) {
        if (this.r < 1) {
            return false;
        }
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null && fragment.d(menuItem)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public List<Fragment> c() {
        List<Fragment> list;
        if (this.j.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.j) {
            list = (List) this.j.clone();
        }
        return list;
    }

    public void c(Fragment fragment) {
        if (f785a) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (!fragment.D) {
            fragment.D = true;
            if (fragment.m) {
                if (f785a) {
                    Log.v("FragmentManager", "remove from detach: " + fragment);
                }
                synchronized (this.j) {
                    this.j.remove(fragment);
                }
                if (fragment.G && fragment.H) {
                    this.w = true;
                }
                fragment.m = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).c(fragment, bundle, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.c(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).c(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.b(this, fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i2) {
        return this.r >= i2;
    }

    /* access modifiers changed from: package-private */
    public void d(Fragment fragment) {
        if (fragment.o && !fragment.r) {
            fragment.b(fragment.i(fragment.f713d), (ViewGroup) null, fragment.f713d);
            View view = fragment.K;
            if (view != null) {
                fragment.L = view;
                view.setSaveFromParentEnabled(false);
                if (fragment.C) {
                    fragment.K.setVisibility(8);
                }
                fragment.a(fragment.K, fragment.f713d);
                a(fragment, fragment.K, fragment.f713d, false);
                return;
            }
            fragment.L = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void d(Fragment fragment, Bundle bundle, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).d(fragment, bundle, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.d(this, fragment, bundle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).d(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.c(this, fragment);
            }
        }
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public boolean d() {
        return this.x || this.y;
    }

    public void e(Fragment fragment) {
        if (f785a) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (!fragment.C) {
            fragment.C = true;
            fragment.Q = true ^ fragment.Q;
        }
    }

    /* access modifiers changed from: package-private */
    public void e(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).e(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.d(this, fragment);
            }
        }
    }

    @Override // androidx.fragment.app.AbstractC0091k
    public boolean e() {
        A();
        return a((String) null, -1, 0);
    }

    public void f() {
        this.x = false;
        this.y = false;
        e(2);
    }

    /* access modifiers changed from: package-private */
    public void f(Fragment fragment) {
        if (fragment.g < 0) {
            int i2 = this.i;
            this.i = i2 + 1;
            fragment.a(i2, this.u);
            if (this.k == null) {
                this.k = new SparseArray<>();
            }
            this.k.put(fragment.g, fragment);
            if (f785a) {
                Log.v("FragmentManager", "Allocated fragment index " + fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void f(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).f(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.e(this, fragment);
            }
        }
    }

    public void g() {
        this.x = false;
        this.y = false;
        e(1);
    }

    /* access modifiers changed from: package-private */
    public void g(Fragment fragment) {
        if (fragment.g >= 0) {
            if (f785a) {
                Log.v("FragmentManager", "Freeing fragment index " + fragment);
            }
            this.k.put(fragment.g, null);
            fragment.z();
        }
    }

    /* access modifiers changed from: package-private */
    public void g(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).g(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.f(this, fragment);
            }
        }
    }

    public void h() {
        this.z = true;
        p();
        e(0);
        this.s = null;
        this.t = null;
        this.u = null;
    }

    /* access modifiers changed from: package-private */
    public void h(Fragment fragment) {
        ViewGroup viewGroup;
        int indexOfChild;
        int indexOfChild2;
        if (fragment != null) {
            int i2 = this.r;
            if (fragment.n) {
                i2 = fragment.D() ? Math.min(i2, 1) : Math.min(i2, 0);
            }
            a(fragment, i2, fragment.q(), fragment.r(), false);
            if (fragment.K != null) {
                Fragment p2 = p(fragment);
                if (p2 != null && (indexOfChild2 = viewGroup.indexOfChild(fragment.K)) < (indexOfChild = (viewGroup = fragment.J).indexOfChild(p2.K))) {
                    viewGroup.removeViewAt(indexOfChild2);
                    viewGroup.addView(fragment.K, indexOfChild);
                }
                if (fragment.P && fragment.J != null) {
                    float f2 = fragment.R;
                    if (f2 > 0.0f) {
                        fragment.K.setAlpha(f2);
                    }
                    fragment.R = 0.0f;
                    fragment.P = false;
                    c a2 = a(fragment, fragment.q(), true, fragment.r());
                    if (a2 != null) {
                        b(fragment.K, a2);
                        Animation animation = a2.f791a;
                        if (animation != null) {
                            fragment.K.startAnimation(animation);
                        } else {
                            a2.f792b.setTarget(fragment.K);
                            a2.f792b.start();
                        }
                    }
                }
            }
            if (fragment.Q) {
                b(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void h(Fragment fragment, boolean z2) {
        Fragment fragment2 = this.u;
        if (fragment2 != null) {
            AbstractC0091k o2 = fragment2.o();
            if (o2 instanceof r) {
                ((r) o2).h(fragment, true);
            }
        }
        Iterator<f> it = this.q.iterator();
        while (it.hasNext()) {
            f next = it.next();
            if (!z2 || next.f799b) {
                next.f798a.g(this, fragment);
            }
        }
    }

    public void i() {
        e(1);
    }

    /* access modifiers changed from: package-private */
    public void i(Fragment fragment) {
        a(fragment, this.r, 0, 0, false);
    }

    public void j() {
        for (int i2 = 0; i2 < this.j.size(); i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null) {
                fragment.T();
            }
        }
    }

    public void j(Fragment fragment) {
        if (!fragment.M) {
            return;
        }
        if (this.h) {
            this.B = true;
            return;
        }
        fragment.M = false;
        a(fragment, this.r, 0, 0, false);
    }

    public void k() {
        e(3);
    }

    public void k(Fragment fragment) {
        if (f785a) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.s);
        }
        boolean z2 = !fragment.D();
        if (!fragment.D || z2) {
            synchronized (this.j) {
                this.j.remove(fragment);
            }
            if (fragment.G && fragment.H) {
                this.w = true;
            }
            fragment.m = false;
            fragment.n = true;
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle l(Fragment fragment) {
        Bundle bundle;
        if (this.F == null) {
            this.F = new Bundle();
        }
        fragment.j(this.F);
        d(fragment, this.F, false);
        if (!this.F.isEmpty()) {
            bundle = this.F;
            this.F = null;
        } else {
            bundle = null;
        }
        if (fragment.K != null) {
            m(fragment);
        }
        if (fragment.e != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.e);
        }
        if (!fragment.N) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", fragment.N);
        }
        return bundle;
    }

    public void l() {
        this.x = false;
        this.y = false;
        e(4);
    }

    public void m() {
        this.x = false;
        this.y = false;
        e(3);
    }

    /* access modifiers changed from: package-private */
    public void m(Fragment fragment) {
        if (fragment.L != null) {
            SparseArray<Parcelable> sparseArray = this.G;
            if (sparseArray == null) {
                this.G = new SparseArray<>();
            } else {
                sparseArray.clear();
            }
            fragment.L.saveHierarchyState(this.G);
            if (this.G.size() > 0) {
                fragment.e = this.G;
                this.G = null;
            }
        }
    }

    public void n() {
        this.y = true;
        e(2);
    }

    public void n(Fragment fragment) {
        if (fragment == null || (this.k.get(fragment.g) == fragment && (fragment.u == null || fragment.o() == this))) {
            this.v = fragment;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    /* access modifiers changed from: package-private */
    public void o() {
        if (this.B) {
            this.B = false;
            y();
        }
    }

    public void o(Fragment fragment) {
        if (f785a) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.C) {
            fragment.C = false;
            fragment.Q = !fragment.Q;
        }
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Fragment fragment;
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, g.f800a);
        int i2 = 0;
        if (attributeValue == null) {
            attributeValue = obtainStyledAttributes.getString(0);
        }
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (!Fragment.a(this.s.c(), attributeValue)) {
            return null;
        }
        if (view != null) {
            i2 = view.getId();
        }
        if (i2 == -1 && resourceId == -1 && string == null) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
        }
        Fragment a2 = resourceId != -1 ? a(resourceId) : null;
        if (a2 == null && string != null) {
            a2 = a(string);
        }
        if (a2 == null && i2 != -1) {
            a2 = a(i2);
        }
        if (f785a) {
            Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + attributeValue + " existing=" + a2);
        }
        if (a2 == null) {
            Fragment a3 = this.t.a(context, attributeValue, null);
            a3.o = true;
            a3.z = resourceId != 0 ? resourceId : i2;
            a3.A = i2;
            a3.B = string;
            a3.p = true;
            a3.t = this;
            AbstractC0090j jVar = this.s;
            a3.u = jVar;
            a3.a(jVar.c(), attributeSet, a3.f713d);
            a(a3, true);
            fragment = a3;
        } else if (!a2.p) {
            a2.p = true;
            AbstractC0090j jVar2 = this.s;
            a2.u = jVar2;
            if (!a2.F) {
                a2.a(jVar2.c(), attributeSet, a2.f713d);
            }
            fragment = a2;
        } else {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(i2) + " with another fragment for " + attributeValue);
        }
        if (this.r >= 1 || !fragment.o) {
            i(fragment);
        } else {
            a(fragment, 1, 0, 0, false);
        }
        View view2 = fragment.K;
        if (view2 != null) {
            if (resourceId != 0) {
                view2.setId(resourceId);
            }
            if (fragment.K.getTag() == null) {
                fragment.K.setTag(string);
            }
            return fragment.K;
        }
        throw new IllegalStateException("Fragment " + attributeValue + " did not create a view.");
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    /* JADX INFO: finally extract failed */
    public boolean p() {
        c(true);
        boolean z2 = false;
        while (b(this.C, this.D)) {
            this.h = true;
            try {
                c(this.C, this.D);
                B();
                z2 = true;
            } catch (Throwable th) {
                B();
                throw th;
            }
        }
        o();
        z();
        return z2;
    }

    /* access modifiers changed from: package-private */
    public LayoutInflater.Factory2 q() {
        return this;
    }

    public Fragment r() {
        return this.v;
    }

    public void s() {
        this.I = null;
        this.x = false;
        this.y = false;
        int size = this.j.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment fragment = this.j.get(i2);
            if (fragment != null) {
                fragment.G();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void t() {
        if (this.p != null) {
            for (int i2 = 0; i2 < this.p.size(); i2++) {
                this.p.get(i2).onBackStackChanged();
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Object obj = this.u;
        if (obj == null) {
            obj = this.s;
        }
        b.e.f.a.a(obj, sb);
        sb.append("}}");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public s u() {
        a(this.I);
        return this.I;
    }

    /* access modifiers changed from: package-private */
    public Parcelable v() {
        int[] iArr;
        int size;
        D();
        C();
        p();
        this.x = true;
        BackStackState[] backStackStateArr = null;
        this.I = null;
        SparseArray<Fragment> sparseArray = this.k;
        if (sparseArray == null || sparseArray.size() <= 0) {
            return null;
        }
        int size2 = this.k.size();
        FragmentState[] fragmentStateArr = new FragmentState[size2];
        boolean z2 = false;
        for (int i2 = 0; i2 < size2; i2++) {
            Fragment valueAt = this.k.valueAt(i2);
            if (valueAt != null) {
                if (valueAt.g >= 0) {
                    FragmentState fragmentState = new FragmentState(valueAt);
                    fragmentStateArr[i2] = fragmentState;
                    if (valueAt.f712c <= 0 || fragmentState.k != null) {
                        fragmentState.k = valueAt.f713d;
                    } else {
                        fragmentState.k = l(valueAt);
                        Fragment fragment = valueAt.j;
                        if (fragment != null) {
                            if (fragment.g >= 0) {
                                if (fragmentState.k == null) {
                                    fragmentState.k = new Bundle();
                                }
                                a(fragmentState.k, "android:target_state", valueAt.j);
                                int i3 = valueAt.l;
                                if (i3 != 0) {
                                    fragmentState.k.putInt("android:target_req_state", i3);
                                }
                            } else {
                                a(new IllegalStateException("Failure saving state: " + valueAt + " has target not in fragment manager: " + valueAt.j));
                                throw null;
                            }
                        }
                    }
                    if (f785a) {
                        Log.v("FragmentManager", "Saved state of " + valueAt + ": " + fragmentState.k);
                    }
                    z2 = true;
                } else {
                    a(new IllegalStateException("Failure saving state: active " + valueAt + " has cleared index: " + valueAt.g));
                    throw null;
                }
            }
        }
        if (!z2) {
            if (f785a) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        int size3 = this.j.size();
        if (size3 > 0) {
            iArr = new int[size3];
            for (int i4 = 0; i4 < size3; i4++) {
                iArr[i4] = this.j.get(i4).g;
                if (iArr[i4] >= 0) {
                    if (f785a) {
                        Log.v("FragmentManager", "saveAllState: adding fragment #" + i4 + ": " + this.j.get(i4));
                    }
                } else {
                    a(new IllegalStateException("Failure saving state: active " + this.j.get(i4) + " has cleared index: " + iArr[i4]));
                    throw null;
                }
            }
        } else {
            iArr = null;
        }
        ArrayList<C0081a> arrayList = this.l;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i5 = 0; i5 < size; i5++) {
                backStackStateArr[i5] = new BackStackState(this.l.get(i5));
                if (f785a) {
                    Log.v("FragmentManager", "saveAllState: adding back stack #" + i5 + ": " + this.l.get(i5));
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.f724a = fragmentStateArr;
        fragmentManagerState.f725b = iArr;
        fragmentManagerState.f726c = backStackStateArr;
        Fragment fragment2 = this.v;
        if (fragment2 != null) {
            fragmentManagerState.f727d = fragment2.g;
        }
        fragmentManagerState.e = this.i;
        w();
        return fragmentManagerState;
    }

    /* access modifiers changed from: package-private */
    public void w() {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        s sVar;
        if (this.k != null) {
            arrayList3 = null;
            arrayList2 = null;
            arrayList = null;
            for (int i2 = 0; i2 < this.k.size(); i2++) {
                Fragment valueAt = this.k.valueAt(i2);
                if (valueAt != null) {
                    if (valueAt.E) {
                        if (arrayList3 == null) {
                            arrayList3 = new ArrayList();
                        }
                        arrayList3.add(valueAt);
                        Fragment fragment = valueAt.j;
                        valueAt.k = fragment != null ? fragment.g : -1;
                        if (f785a) {
                            Log.v("FragmentManager", "retainNonConfig: keeping retained " + valueAt);
                        }
                    }
                    r rVar = valueAt.v;
                    if (rVar != null) {
                        rVar.w();
                        sVar = valueAt.v.I;
                    } else {
                        sVar = valueAt.w;
                    }
                    if (arrayList2 == null && sVar != null) {
                        arrayList2 = new ArrayList(this.k.size());
                        for (int i3 = 0; i3 < i2; i3++) {
                            arrayList2.add(null);
                        }
                    }
                    if (arrayList2 != null) {
                        arrayList2.add(sVar);
                    }
                    if (arrayList == null && valueAt.x != null) {
                        arrayList = new ArrayList(this.k.size());
                        for (int i4 = 0; i4 < i2; i4++) {
                            arrayList.add(null);
                        }
                    }
                    if (arrayList != null) {
                        arrayList.add(valueAt.x);
                    }
                }
            }
        } else {
            arrayList3 = null;
            arrayList2 = null;
            arrayList = null;
        }
        if (arrayList3 == null && arrayList2 == null && arrayList == null) {
            this.I = null;
        } else {
            this.I = new s(arrayList3, arrayList2, arrayList);
        }
    }

    /* access modifiers changed from: package-private */
    public void x() {
        synchronized (this) {
            boolean z2 = false;
            boolean z3 = this.H != null && !this.H.isEmpty();
            if (this.g != null && this.g.size() == 1) {
                z2 = true;
            }
            if (z3 || z2) {
                this.s.e().removeCallbacks(this.J);
                this.s.e().post(this.J);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void y() {
        if (this.k != null) {
            for (int i2 = 0; i2 < this.k.size(); i2++) {
                Fragment valueAt = this.k.valueAt(i2);
                if (valueAt != null) {
                    j(valueAt);
                }
            }
        }
    }
}
