package androidx.fragment.app;

import android.animation.Animator;
import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.lifecycle.f;
import androidx.lifecycle.h;
import androidx.lifecycle.j;
import androidx.lifecycle.o;
import androidx.lifecycle.t;
import androidx.lifecycle.u;
import b.c.i;
import b.e.g.e;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, h, u {

    /* renamed from: a  reason: collision with root package name */
    private static final i<String, Class<?>> f710a = new i<>();

    /* renamed from: b  reason: collision with root package name */
    static final Object f711b = new Object();
    int A;
    String B;
    boolean C;
    boolean D;
    boolean E;
    boolean F;
    boolean G;
    boolean H = true;
    boolean I;
    ViewGroup J;
    View K;
    View L;
    boolean M;
    boolean N = true;
    a O;
    boolean P;
    boolean Q;
    float R;
    LayoutInflater S;
    boolean T;
    j U = new j(this);
    j V;
    h W;
    o<h> X = new o<>();

    /* renamed from: c  reason: collision with root package name */
    int f712c = 0;

    /* renamed from: d  reason: collision with root package name */
    Bundle f713d;
    SparseArray<Parcelable> e;
    Boolean f;
    int g = -1;
    String h;
    Bundle i;
    Fragment j;
    int k = -1;
    int l;
    boolean m;
    boolean n;
    boolean o;
    boolean p;
    boolean q;
    boolean r;
    int s;
    r t;
    AbstractC0090j u;
    r v;
    s w;
    t x;
    Fragment y;
    int z;

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new C0086f();

        /* renamed from: a  reason: collision with root package name */
        final Bundle f714a;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Bundle bundle;
            this.f714a = parcel.readBundle();
            if (classLoader != null && (bundle = this.f714a) != null) {
                bundle.setClassLoader(classLoader);
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBundle(this.f714a);
        }
    }

    /* access modifiers changed from: package-private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        View f715a;

        /* renamed from: b  reason: collision with root package name */
        Animator f716b;

        /* renamed from: c  reason: collision with root package name */
        int f717c;

        /* renamed from: d  reason: collision with root package name */
        int f718d;
        int e;
        int f;
        Object g = null;
        Object h;
        Object i;
        Object j;
        Object k;
        Object l;
        Boolean m;
        Boolean n;
        androidx.core.app.j o;
        androidx.core.app.j p;
        boolean q;
        c r;
        boolean s;

        a() {
            Object obj = Fragment.f711b;
            this.h = obj;
            this.i = null;
            this.j = obj;
            this.k = null;
            this.l = obj;
            this.o = null;
            this.p = null;
        }
    }

    public static class b extends RuntimeException {
        public b(String str, Exception exc) {
            super(str, exc);
        }
    }

    /* access modifiers changed from: package-private */
    public interface c {
        void a();

        void b();
    }

    public static Fragment a(Context context, String str, Bundle bundle) {
        try {
            Class<?> cls = f710a.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                f710a.put(str, cls);
            }
            Fragment fragment = (Fragment) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.m(bundle);
            }
            return fragment;
        } catch (ClassNotFoundException e2) {
            throw new b("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e2);
        } catch (InstantiationException e3) {
            throw new b("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e3);
        } catch (IllegalAccessException e4) {
            throw new b("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an" + " empty constructor that is public", e4);
        } catch (NoSuchMethodException e5) {
            throw new b("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e5);
        } catch (InvocationTargetException e6) {
            throw new b("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e6);
        }
    }

    static boolean a(Context context, String str) {
        try {
            Class<?> cls = f710a.get(str);
            if (cls == null) {
                cls = context.getClassLoader().loadClass(str);
                f710a.put(str, cls);
            }
            return Fragment.class.isAssignableFrom(cls);
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private a aa() {
        if (this.O == null) {
            this.O = new a();
        }
        return this.O;
    }

    /* access modifiers changed from: package-private */
    public void A() {
        if (this.u != null) {
            this.v = new r();
            this.v.a(this.u, new C0084d(this), this);
            return;
        }
        throw new IllegalStateException("Fragment has not been attached yet.");
    }

    public final boolean B() {
        return this.D;
    }

    /* access modifiers changed from: package-private */
    public boolean C() {
        a aVar = this.O;
        if (aVar == null) {
            return false;
        }
        return aVar.s;
    }

    /* access modifiers changed from: package-private */
    public final boolean D() {
        return this.s > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean E() {
        a aVar = this.O;
        if (aVar == null) {
            return false;
        }
        return aVar.q;
    }

    public final boolean F() {
        r rVar = this.t;
        if (rVar == null) {
            return false;
        }
        return rVar.d();
    }

    /* access modifiers changed from: package-private */
    public void G() {
        r rVar = this.v;
        if (rVar != null) {
            rVar.s();
        }
    }

    public void H() {
        boolean z2 = true;
        this.I = true;
        FragmentActivity d2 = d();
        if (d2 == null || !d2.isChangingConfigurations()) {
            z2 = false;
        }
        t tVar = this.x;
        if (tVar != null && !z2) {
            tVar.a();
        }
    }

    public void I() {
    }

    public void J() {
        this.I = true;
    }

    public void K() {
        this.I = true;
    }

    public void L() {
        this.I = true;
    }

    public void M() {
        this.I = true;
    }

    public void N() {
        this.I = true;
    }

    public void O() {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public AbstractC0091k P() {
        return this.v;
    }

    /* access modifiers changed from: package-private */
    public void Q() {
        this.U.b(f.a.ON_DESTROY);
        r rVar = this.v;
        if (rVar != null) {
            rVar.h();
        }
        this.f712c = 0;
        this.I = false;
        this.T = false;
        H();
        if (this.I) {
            this.v = null;
            return;
        }
        throw new M("Fragment " + this + " did not call through to super.onDestroy()");
    }

    /* access modifiers changed from: package-private */
    public void R() {
        if (this.K != null) {
            this.V.b(f.a.ON_DESTROY);
        }
        r rVar = this.v;
        if (rVar != null) {
            rVar.i();
        }
        this.f712c = 1;
        this.I = false;
        J();
        if (this.I) {
            b.i.a.a.a(this).a();
            this.r = false;
            return;
        }
        throw new M("Fragment " + this + " did not call through to super.onDestroyView()");
    }

    /* access modifiers changed from: package-private */
    public void S() {
        this.I = false;
        K();
        this.S = null;
        if (this.I) {
            r rVar = this.v;
            if (rVar == null) {
                return;
            }
            if (this.F) {
                rVar.h();
                this.v = null;
                return;
            }
            throw new IllegalStateException("Child FragmentManager of " + this + " was not " + " destroyed and this fragment is not retaining instance");
        }
        throw new M("Fragment " + this + " did not call through to super.onDetach()");
    }

    /* access modifiers changed from: package-private */
    public void T() {
        onLowMemory();
        r rVar = this.v;
        if (rVar != null) {
            rVar.j();
        }
    }

    /* access modifiers changed from: package-private */
    public void U() {
        if (this.K != null) {
            this.V.b(f.a.ON_PAUSE);
        }
        this.U.b(f.a.ON_PAUSE);
        r rVar = this.v;
        if (rVar != null) {
            rVar.k();
        }
        this.f712c = 3;
        this.I = false;
        L();
        if (!this.I) {
            throw new M("Fragment " + this + " did not call through to super.onPause()");
        }
    }

    /* access modifiers changed from: package-private */
    public void V() {
        r rVar = this.v;
        if (rVar != null) {
            rVar.s();
            this.v.p();
        }
        this.f712c = 4;
        this.I = false;
        M();
        if (this.I) {
            r rVar2 = this.v;
            if (rVar2 != null) {
                rVar2.l();
                this.v.p();
            }
            this.U.b(f.a.ON_RESUME);
            if (this.K != null) {
                this.V.b(f.a.ON_RESUME);
                return;
            }
            return;
        }
        throw new M("Fragment " + this + " did not call through to super.onResume()");
    }

    /* access modifiers changed from: package-private */
    public void W() {
        r rVar = this.v;
        if (rVar != null) {
            rVar.s();
            this.v.p();
        }
        this.f712c = 3;
        this.I = false;
        N();
        if (this.I) {
            r rVar2 = this.v;
            if (rVar2 != null) {
                rVar2.m();
            }
            this.U.b(f.a.ON_START);
            if (this.K != null) {
                this.V.b(f.a.ON_START);
                return;
            }
            return;
        }
        throw new M("Fragment " + this + " did not call through to super.onStart()");
    }

    /* access modifiers changed from: package-private */
    public void X() {
        if (this.K != null) {
            this.V.b(f.a.ON_STOP);
        }
        this.U.b(f.a.ON_STOP);
        r rVar = this.v;
        if (rVar != null) {
            rVar.n();
        }
        this.f712c = 2;
        this.I = false;
        O();
        if (!this.I) {
            throw new M("Fragment " + this + " did not call through to super.onStop()");
        }
    }

    public final Context Y() {
        Context j2 = j();
        if (j2 != null) {
            return j2;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to a context.");
    }

    public void Z() {
        r rVar = this.t;
        if (rVar == null || rVar.s == null) {
            aa().q = false;
        } else if (Looper.myLooper() != this.t.s.e().getLooper()) {
            this.t.s.e().postAtFrontOfQueue(new RunnableC0083c(this));
        } else {
            c();
        }
    }

    @Deprecated
    public LayoutInflater a(Bundle bundle) {
        AbstractC0090j jVar = this.u;
        if (jVar != null) {
            LayoutInflater f2 = jVar.f();
            i();
            r rVar = this.v;
            rVar.q();
            e.a(f2, rVar);
            return f2;
        }
        throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
    }

    public View a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public Animation a(int i2, boolean z2, int i3) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public Fragment a(String str) {
        if (str.equals(this.h)) {
            return this;
        }
        r rVar = this.v;
        if (rVar != null) {
            return rVar.b(str);
        }
        return null;
    }

    @Override // androidx.lifecycle.h
    public f a() {
        return this.U;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (this.O != null || i2 != 0) {
            aa().f718d = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        if (this.O != null || i2 != 0 || i3 != 0) {
            aa();
            a aVar = this.O;
            aVar.e = i2;
            aVar.f = i3;
        }
    }

    public void a(int i2, int i3, Intent intent) {
    }

    /* access modifiers changed from: package-private */
    public final void a(int i2, Fragment fragment) {
        String str;
        StringBuilder sb;
        this.g = i2;
        if (fragment != null) {
            sb = new StringBuilder();
            sb.append(fragment.h);
            str = ":";
        } else {
            sb = new StringBuilder();
            str = "android:fragment:";
        }
        sb.append(str);
        sb.append(this.g);
        this.h = sb.toString();
    }

    public void a(int i2, String[] strArr, int[] iArr) {
    }

    /* access modifiers changed from: package-private */
    public void a(Animator animator) {
        aa().f716b = animator;
    }

    @Deprecated
    public void a(Activity activity) {
        this.I = true;
    }

    @Deprecated
    public void a(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.I = true;
    }

    public void a(Context context) {
        this.I = true;
        AbstractC0090j jVar = this.u;
        Activity b2 = jVar == null ? null : jVar.b();
        if (b2 != null) {
            this.I = false;
            a(b2);
        }
    }

    public void a(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.I = true;
        AbstractC0090j jVar = this.u;
        Activity b2 = jVar == null ? null : jVar.b();
        if (b2 != null) {
            this.I = false;
            a(b2, attributeSet, bundle);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Configuration configuration) {
        onConfigurationChanged(configuration);
        r rVar = this.v;
        if (rVar != null) {
            rVar.a(configuration);
        }
    }

    public void a(Menu menu) {
    }

    public void a(Menu menu, MenuInflater menuInflater) {
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        aa().f715a = view;
    }

    public void a(View view, Bundle bundle) {
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar) {
        aa();
        c cVar2 = this.O.r;
        if (cVar != cVar2) {
            if (cVar == null || cVar2 == null) {
                a aVar = this.O;
                if (aVar.q) {
                    aVar.r = cVar;
                }
                if (cVar != null) {
                    cVar.a();
                    return;
                }
                return;
            }
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
    }

    public void a(Fragment fragment) {
    }

    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.z));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.A));
        printWriter.print(" mTag=");
        printWriter.println(this.B);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.f712c);
        printWriter.print(" mIndex=");
        printWriter.print(this.g);
        printWriter.print(" mWho=");
        printWriter.print(this.h);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.s);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.m);
        printWriter.print(" mRemoving=");
        printWriter.print(this.n);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.o);
        printWriter.print(" mInLayout=");
        printWriter.println(this.p);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.C);
        printWriter.print(" mDetached=");
        printWriter.print(this.D);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.H);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.G);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.E);
        printWriter.print(" mRetaining=");
        printWriter.print(this.F);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.N);
        if (this.t != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.t);
        }
        if (this.u != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.u);
        }
        if (this.y != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.y);
        }
        if (this.i != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.i);
        }
        if (this.f713d != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.f713d);
        }
        if (this.e != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.e);
        }
        if (this.j != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(this.j);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.l);
        }
        if (p() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(p());
        }
        if (this.J != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.J);
        }
        if (this.K != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.K);
        }
        if (this.L != null) {
            printWriter.print(str);
            printWriter.print("mInnerView=");
            printWriter.println(this.K);
        }
        if (g() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(g());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(x());
        }
        if (j() != null) {
            b.i.a.a.a(this).a(str, fileDescriptor, printWriter, strArr);
        }
        if (this.v != null) {
            printWriter.print(str);
            printWriter.println("Child " + this.v + ":");
            r rVar = this.v;
            rVar.a(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }

    public void a(boolean z2) {
    }

    public boolean a(MenuItem menuItem) {
        return false;
    }

    public Animator b(int i2, boolean z2, int i3) {
        return null;
    }

    @Override // androidx.lifecycle.u
    public t b() {
        if (j() != null) {
            if (this.x == null) {
                this.x = new t();
            }
            return this.x;
        }
        throw new IllegalStateException("Can't access ViewModels from detached fragment");
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        aa().f717c = i2;
    }

    public void b(Bundle bundle) {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public void b(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        r rVar = this.v;
        if (rVar != null) {
            rVar.s();
        }
        this.r = true;
        this.W = new C0085e(this);
        this.V = null;
        this.K = a(layoutInflater, viewGroup, bundle);
        if (this.K != null) {
            this.W.a();
            this.X.a(this.W);
        } else if (this.V == null) {
            this.W = null;
        } else {
            throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
        }
    }

    public void b(Menu menu) {
    }

    public void b(boolean z2) {
    }

    /* access modifiers changed from: package-private */
    public boolean b(Menu menu, MenuInflater menuInflater) {
        boolean z2 = false;
        if (this.C) {
            return false;
        }
        if (this.G && this.H) {
            a(menu, menuInflater);
            z2 = true;
        }
        r rVar = this.v;
        return rVar != null ? z2 | rVar.a(menu, menuInflater) : z2;
    }

    public boolean b(MenuItem menuItem) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void c() {
        a aVar = this.O;
        c cVar = null;
        if (aVar != null) {
            aVar.q = false;
            c cVar2 = aVar.r;
            aVar.r = null;
            cVar = cVar2;
        }
        if (cVar != null) {
            cVar.b();
        }
    }

    public void c(Bundle bundle) {
        this.I = true;
        k(bundle);
        r rVar = this.v;
        if (rVar != null && !rVar.c(1)) {
            this.v.g();
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Menu menu) {
        if (!this.C) {
            if (this.G && this.H) {
                a(menu);
            }
            r rVar = this.v;
            if (rVar != null) {
                rVar.a(menu);
            }
        }
    }

    public void c(boolean z2) {
    }

    /* access modifiers changed from: package-private */
    public boolean c(MenuItem menuItem) {
        if (this.C) {
            return false;
        }
        if (a(menuItem)) {
            return true;
        }
        r rVar = this.v;
        return rVar != null && rVar.a(menuItem);
    }

    public LayoutInflater d(Bundle bundle) {
        return a(bundle);
    }

    public final FragmentActivity d() {
        AbstractC0090j jVar = this.u;
        if (jVar == null) {
            return null;
        }
        return (FragmentActivity) jVar.b();
    }

    /* access modifiers changed from: package-private */
    public void d(boolean z2) {
        b(z2);
        r rVar = this.v;
        if (rVar != null) {
            rVar.a(z2);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d(Menu menu) {
        boolean z2 = false;
        if (this.C) {
            return false;
        }
        if (this.G && this.H) {
            b(menu);
            z2 = true;
        }
        r rVar = this.v;
        return rVar != null ? z2 | rVar.b(menu) : z2;
    }

    /* access modifiers changed from: package-private */
    public boolean d(MenuItem menuItem) {
        if (this.C) {
            return false;
        }
        if (this.G && this.H && b(menuItem)) {
            return true;
        }
        r rVar = this.v;
        return rVar != null && rVar.b(menuItem);
    }

    public void e(Bundle bundle) {
    }

    /* access modifiers changed from: package-private */
    public void e(boolean z2) {
        c(z2);
        r rVar = this.v;
        if (rVar != null) {
            rVar.b(z2);
        }
    }

    public boolean e() {
        Boolean bool;
        a aVar = this.O;
        if (aVar == null || (bool = aVar.n) == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void f(Bundle bundle) {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public void f(boolean z2) {
        aa().s = z2;
    }

    public boolean f() {
        Boolean bool;
        a aVar = this.O;
        if (aVar == null || (bool = aVar.m) == null) {
            return true;
        }
        return bool.booleanValue();
    }

    /* access modifiers changed from: package-private */
    public View g() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.f715a;
    }

    /* access modifiers changed from: package-private */
    public void g(Bundle bundle) {
        r rVar = this.v;
        if (rVar != null) {
            rVar.s();
        }
        this.f712c = 2;
        this.I = false;
        b(bundle);
        if (this.I) {
            r rVar2 = this.v;
            if (rVar2 != null) {
                rVar2.f();
                return;
            }
            return;
        }
        throw new M("Fragment " + this + " did not call through to super.onActivityCreated()");
    }

    /* access modifiers changed from: package-private */
    public Animator h() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.f716b;
    }

    /* access modifiers changed from: package-private */
    public void h(Bundle bundle) {
        r rVar = this.v;
        if (rVar != null) {
            rVar.s();
        }
        this.f712c = 1;
        this.I = false;
        c(bundle);
        this.T = true;
        if (this.I) {
            this.U.b(f.a.ON_CREATE);
            return;
        }
        throw new M("Fragment " + this + " did not call through to super.onCreate()");
    }

    public final int hashCode() {
        return super.hashCode();
    }

    /* access modifiers changed from: package-private */
    public LayoutInflater i(Bundle bundle) {
        this.S = d(bundle);
        return this.S;
    }

    public final AbstractC0091k i() {
        if (this.v == null) {
            A();
            int i2 = this.f712c;
            if (i2 >= 4) {
                this.v.l();
            } else if (i2 >= 3) {
                this.v.m();
            } else if (i2 >= 2) {
                this.v.f();
            } else if (i2 >= 1) {
                this.v.g();
            }
        }
        return this.v;
    }

    public Context j() {
        AbstractC0090j jVar = this.u;
        if (jVar == null) {
            return null;
        }
        return jVar.c();
    }

    /* access modifiers changed from: package-private */
    public void j(Bundle bundle) {
        Parcelable v2;
        e(bundle);
        r rVar = this.v;
        if (rVar != null && (v2 = rVar.v()) != null) {
            bundle.putParcelable("android:support:fragments", v2);
        }
    }

    public Object k() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.g;
    }

    /* access modifiers changed from: package-private */
    public void k(Bundle bundle) {
        Parcelable parcelable;
        if (bundle != null && (parcelable = bundle.getParcelable("android:support:fragments")) != null) {
            if (this.v == null) {
                A();
            }
            this.v.a(parcelable, this.w);
            this.w = null;
            this.v.g();
        }
    }

    /* access modifiers changed from: package-private */
    public androidx.core.app.j l() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.o;
    }

    /* access modifiers changed from: package-private */
    public final void l(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = this.e;
        if (sparseArray != null) {
            this.L.restoreHierarchyState(sparseArray);
            this.e = null;
        }
        this.I = false;
        f(bundle);
        if (!this.I) {
            throw new M("Fragment " + this + " did not call through to super.onViewStateRestored()");
        } else if (this.K != null) {
            this.V.b(f.a.ON_CREATE);
        }
    }

    public Object m() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.i;
    }

    public void m(Bundle bundle) {
        if (this.g < 0 || !F()) {
            this.i = bundle;
            return;
        }
        throw new IllegalStateException("Fragment already active and state has been saved");
    }

    /* access modifiers changed from: package-private */
    public androidx.core.app.j n() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.p;
    }

    public final AbstractC0091k o() {
        return this.t;
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.I = true;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        d().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onLowMemory() {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public int p() {
        a aVar = this.O;
        if (aVar == null) {
            return 0;
        }
        return aVar.f718d;
    }

    /* access modifiers changed from: package-private */
    public int q() {
        a aVar = this.O;
        if (aVar == null) {
            return 0;
        }
        return aVar.e;
    }

    /* access modifiers changed from: package-private */
    public int r() {
        a aVar = this.O;
        if (aVar == null) {
            return 0;
        }
        return aVar.f;
    }

    public Object s() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        Object obj = aVar.j;
        return obj == f711b ? m() : obj;
    }

    public final Resources t() {
        return Y().getResources();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        b.e.f.a.a(this, sb);
        if (this.g >= 0) {
            sb.append(" #");
            sb.append(this.g);
        }
        if (this.z != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.z));
        }
        if (this.B != null) {
            sb.append(" ");
            sb.append(this.B);
        }
        sb.append('}');
        return sb.toString();
    }

    public Object u() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        Object obj = aVar.h;
        return obj == f711b ? k() : obj;
    }

    public Object v() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        return aVar.k;
    }

    public Object w() {
        a aVar = this.O;
        if (aVar == null) {
            return null;
        }
        Object obj = aVar.l;
        return obj == f711b ? v() : obj;
    }

    /* access modifiers changed from: package-private */
    public int x() {
        a aVar = this.O;
        if (aVar == null) {
            return 0;
        }
        return aVar.f717c;
    }

    public View y() {
        return this.K;
    }

    /* access modifiers changed from: package-private */
    public void z() {
        this.g = -1;
        this.h = null;
        this.m = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.q = false;
        this.s = 0;
        this.t = null;
        this.v = null;
        this.u = null;
        this.z = 0;
        this.A = 0;
        this.B = null;
        this.C = false;
        this.D = false;
        this.F = false;
    }
}
