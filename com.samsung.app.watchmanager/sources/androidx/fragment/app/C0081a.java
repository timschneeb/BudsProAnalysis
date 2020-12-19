package androidx.fragment.app;

import android.util.Log;
import androidx.fragment.app.AbstractC0091k;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.r;
import b.e.f.b;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
/* renamed from: androidx.fragment.app.a  reason: case insensitive filesystem */
public final class C0081a extends w implements AbstractC0091k.a, r.h {

    /* renamed from: a  reason: collision with root package name */
    final r f754a;

    /* renamed from: b  reason: collision with root package name */
    ArrayList<C0013a> f755b = new ArrayList<>();

    /* renamed from: c  reason: collision with root package name */
    int f756c;

    /* renamed from: d  reason: collision with root package name */
    int f757d;
    int e;
    int f;
    int g;
    int h;
    boolean i;
    boolean j = true;
    String k;
    boolean l;
    int m = -1;
    int n;
    CharSequence o;
    int p;
    CharSequence q;
    ArrayList<String> r;
    ArrayList<String> s;
    boolean t = false;
    ArrayList<Runnable> u;

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.fragment.app.a$a  reason: collision with other inner class name */
    public static final class C0013a {

        /* renamed from: a  reason: collision with root package name */
        int f758a;

        /* renamed from: b  reason: collision with root package name */
        Fragment f759b;

        /* renamed from: c  reason: collision with root package name */
        int f760c;

        /* renamed from: d  reason: collision with root package name */
        int f761d;
        int e;
        int f;

        C0013a() {
        }

        C0013a(int i, Fragment fragment) {
            this.f758a = i;
            this.f759b = fragment;
        }
    }

    public C0081a(r rVar) {
        this.f754a = rVar;
    }

    private void a(int i2, Fragment fragment, String str, int i3) {
        Class<?> cls = fragment.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from" + " instance state.");
        }
        fragment.t = this.f754a;
        if (str != null) {
            String str2 = fragment.B;
            if (str2 == null || str.equals(str2)) {
                fragment.B = str;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.B + " now " + str);
            }
        }
        if (i2 != 0) {
            if (i2 != -1) {
                int i4 = fragment.z;
                if (i4 == 0 || i4 == i2) {
                    fragment.z = i2;
                    fragment.A = i2;
                } else {
                    throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.z + " now " + i2);
                }
            } else {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + str + " to container view with no id");
            }
        }
        a(new C0013a(i3, fragment));
    }

    private static boolean b(C0013a aVar) {
        Fragment fragment = aVar.f759b;
        return fragment != null && fragment.m && fragment.K != null && !fragment.D && !fragment.C && fragment.E();
    }

    @Override // androidx.fragment.app.w
    public int a() {
        return a(false);
    }

    /* access modifiers changed from: package-private */
    public int a(boolean z) {
        if (!this.l) {
            if (r.f785a) {
                Log.v("FragmentManager", "Commit: " + this);
                PrintWriter printWriter = new PrintWriter(new b("FragmentManager"));
                a("  ", (FileDescriptor) null, printWriter, (String[]) null);
                printWriter.close();
            }
            this.l = true;
            this.m = this.i ? this.f754a.b(this) : -1;
            this.f754a.a(this, z);
            return this.m;
        }
        throw new IllegalStateException("commit already called");
    }

    /* access modifiers changed from: package-private */
    public Fragment a(ArrayList<Fragment> arrayList, Fragment fragment) {
        Fragment fragment2 = fragment;
        int i2 = 0;
        while (i2 < this.f755b.size()) {
            C0013a aVar = this.f755b.get(i2);
            int i3 = aVar.f758a;
            if (i3 != 1) {
                if (i3 == 2) {
                    Fragment fragment3 = aVar.f759b;
                    int i4 = fragment3.A;
                    Fragment fragment4 = fragment2;
                    int i5 = i2;
                    boolean z = false;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        Fragment fragment5 = arrayList.get(size);
                        if (fragment5.A == i4) {
                            if (fragment5 == fragment3) {
                                z = true;
                            } else {
                                if (fragment5 == fragment4) {
                                    this.f755b.add(i5, new C0013a(9, fragment5));
                                    i5++;
                                    fragment4 = null;
                                }
                                C0013a aVar2 = new C0013a(3, fragment5);
                                aVar2.f760c = aVar.f760c;
                                aVar2.e = aVar.e;
                                aVar2.f761d = aVar.f761d;
                                aVar2.f = aVar.f;
                                this.f755b.add(i5, aVar2);
                                arrayList.remove(fragment5);
                                i5++;
                            }
                        }
                    }
                    if (z) {
                        this.f755b.remove(i5);
                        i5--;
                    } else {
                        aVar.f758a = 1;
                        arrayList.add(fragment3);
                    }
                    i2 = i5;
                    fragment2 = fragment4;
                } else if (i3 == 3 || i3 == 6) {
                    arrayList.remove(aVar.f759b);
                    Fragment fragment6 = aVar.f759b;
                    if (fragment6 == fragment2) {
                        this.f755b.add(i2, new C0013a(9, fragment6));
                        i2++;
                        fragment2 = null;
                    }
                } else if (i3 != 7) {
                    if (i3 == 8) {
                        this.f755b.add(i2, new C0013a(9, fragment2));
                        i2++;
                        fragment2 = aVar.f759b;
                    }
                }
                i2++;
            }
            arrayList.add(aVar.f759b);
            i2++;
        }
        return fragment2;
    }

    @Override // androidx.fragment.app.w
    public w a(int i2, Fragment fragment, String str) {
        a(i2, fragment, str, 1);
        return this;
    }

    @Override // androidx.fragment.app.w
    public w a(Fragment fragment) {
        a(new C0013a(7, fragment));
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (this.i) {
            if (r.f785a) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + i2);
            }
            int size = this.f755b.size();
            for (int i3 = 0; i3 < size; i3++) {
                C0013a aVar = this.f755b.get(i3);
                Fragment fragment = aVar.f759b;
                if (fragment != null) {
                    fragment.s += i2;
                    if (r.f785a) {
                        Log.v("FragmentManager", "Bump nesting of " + aVar.f759b + " to " + aVar.f759b.s);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment.c cVar) {
        for (int i2 = 0; i2 < this.f755b.size(); i2++) {
            C0013a aVar = this.f755b.get(i2);
            if (b(aVar)) {
                aVar.f759b.a(cVar);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(C0013a aVar) {
        this.f755b.add(aVar);
        aVar.f760c = this.f756c;
        aVar.f761d = this.f757d;
        aVar.e = this.e;
        aVar.f = this.f;
    }

    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        a(str, printWriter, true);
    }

    public void a(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.k);
            printWriter.print(" mIndex=");
            printWriter.print(this.m);
            printWriter.print(" mCommitted=");
            printWriter.println(this.l);
            if (this.g != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.g));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.h));
            }
            if (!(this.f756c == 0 && this.f757d == 0)) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f756c));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.f757d));
            }
            if (!(this.e == 0 && this.f == 0)) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.e));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.f));
            }
            if (!(this.n == 0 && this.o == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.n));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.o);
            }
            if (!(this.p == 0 && this.q == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.p));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.q);
            }
        }
        if (!this.f755b.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Operations:");
            String str3 = str + "    ";
            int size = this.f755b.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0013a aVar = this.f755b.get(i2);
                switch (aVar.f758a) {
                    case 0:
                        str2 = "NULL";
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    case 8:
                        str2 = "SET_PRIMARY_NAV";
                        break;
                    case 9:
                        str2 = "UNSET_PRIMARY_NAV";
                        break;
                    default:
                        str2 = "cmd=" + aVar.f758a;
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.println(aVar.f759b);
                if (z) {
                    if (!(aVar.f760c == 0 && aVar.f761d == 0)) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(aVar.f760c));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(aVar.f761d));
                    }
                    if (aVar.e != 0 || aVar.f != 0) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(aVar.e));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(aVar.f));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(ArrayList<C0081a> arrayList, int i2, int i3) {
        if (i3 == i2) {
            return false;
        }
        int size = this.f755b.size();
        int i4 = -1;
        for (int i5 = 0; i5 < size; i5++) {
            Fragment fragment = this.f755b.get(i5).f759b;
            int i6 = fragment != null ? fragment.A : 0;
            if (!(i6 == 0 || i6 == i4)) {
                for (int i7 = i2; i7 < i3; i7++) {
                    C0081a aVar = arrayList.get(i7);
                    int size2 = aVar.f755b.size();
                    for (int i8 = 0; i8 < size2; i8++) {
                        Fragment fragment2 = aVar.f755b.get(i8).f759b;
                        if ((fragment2 != null ? fragment2.A : 0) == i6) {
                            return true;
                        }
                    }
                }
                i4 = i6;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.r.h
    public boolean a(ArrayList<C0081a> arrayList, ArrayList<Boolean> arrayList2) {
        if (r.f785a) {
            Log.v("FragmentManager", "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (!this.i) {
            return true;
        }
        this.f754a.a(this);
        return true;
    }

    /* access modifiers changed from: package-private */
    public Fragment b(ArrayList<Fragment> arrayList, Fragment fragment) {
        for (int i2 = 0; i2 < this.f755b.size(); i2++) {
            C0013a aVar = this.f755b.get(i2);
            int i3 = aVar.f758a;
            if (i3 != 1) {
                if (i3 != 3) {
                    switch (i3) {
                        case 8:
                            fragment = null;
                            break;
                        case 9:
                            fragment = aVar.f759b;
                            break;
                    }
                }
                arrayList.add(aVar.f759b);
            }
            arrayList.remove(aVar.f759b);
        }
        return fragment;
    }

    @Override // androidx.fragment.app.w
    public w b(Fragment fragment) {
        a(new C0013a(6, fragment));
        return this;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int size = this.f755b.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0013a aVar = this.f755b.get(i2);
            Fragment fragment = aVar.f759b;
            if (fragment != null) {
                fragment.a(this.g, this.h);
            }
            switch (aVar.f758a) {
                case 1:
                    fragment.a(aVar.f760c);
                    this.f754a.a(fragment, false);
                    break;
                case 2:
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + aVar.f758a);
                case 3:
                    fragment.a(aVar.f761d);
                    this.f754a.k(fragment);
                    break;
                case 4:
                    fragment.a(aVar.f761d);
                    this.f754a.e(fragment);
                    break;
                case 5:
                    fragment.a(aVar.f760c);
                    this.f754a.o(fragment);
                    break;
                case 6:
                    fragment.a(aVar.f761d);
                    this.f754a.c(fragment);
                    break;
                case 7:
                    fragment.a(aVar.f760c);
                    this.f754a.a(fragment);
                    break;
                case 8:
                    this.f754a.n(fragment);
                    break;
                case 9:
                    this.f754a.n(null);
                    break;
            }
            if (!(this.t || aVar.f758a == 1 || fragment == null)) {
                this.f754a.h(fragment);
            }
        }
        if (!this.t) {
            r rVar = this.f754a;
            rVar.a(rVar.r, true);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(boolean z) {
        for (int size = this.f755b.size() - 1; size >= 0; size--) {
            C0013a aVar = this.f755b.get(size);
            Fragment fragment = aVar.f759b;
            if (fragment != null) {
                fragment.a(r.d(this.g), this.h);
            }
            switch (aVar.f758a) {
                case 1:
                    fragment.a(aVar.f);
                    this.f754a.k(fragment);
                    break;
                case 2:
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + aVar.f758a);
                case 3:
                    fragment.a(aVar.e);
                    this.f754a.a(fragment, false);
                    break;
                case 4:
                    fragment.a(aVar.e);
                    this.f754a.o(fragment);
                    break;
                case 5:
                    fragment.a(aVar.f);
                    this.f754a.e(fragment);
                    break;
                case 6:
                    fragment.a(aVar.e);
                    this.f754a.a(fragment);
                    break;
                case 7:
                    fragment.a(aVar.f);
                    this.f754a.c(fragment);
                    break;
                case 8:
                    this.f754a.n(null);
                    break;
                case 9:
                    this.f754a.n(fragment);
                    break;
            }
            if (!(this.t || aVar.f758a == 3 || fragment == null)) {
                this.f754a.h(fragment);
            }
        }
        if (!this.t && z) {
            r rVar = this.f754a;
            rVar.a(rVar.r, true);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2) {
        int size = this.f755b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Fragment fragment = this.f755b.get(i3).f759b;
            int i4 = fragment != null ? fragment.A : 0;
            if (i4 != 0 && i4 == i2) {
                return true;
            }
        }
        return false;
    }

    public String c() {
        return this.k;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        for (int i2 = 0; i2 < this.f755b.size(); i2++) {
            if (b(this.f755b.get(i2))) {
                return true;
            }
        }
        return false;
    }

    public void e() {
        ArrayList<Runnable> arrayList = this.u;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.u.get(i2).run();
            }
            this.u = null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.m >= 0) {
            sb.append(" #");
            sb.append(this.m);
        }
        if (this.k != null) {
            sb.append(" ");
            sb.append(this.k);
        }
        sb.append("}");
        return sb.toString();
    }
}
