package b.i.a;

import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.h;
import androidx.lifecycle.o;
import androidx.lifecycle.p;
import androidx.lifecycle.r;
import androidx.lifecycle.s;
import androidx.lifecycle.t;
import b.c.j;
import b.i.b.a;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* access modifiers changed from: package-private */
public class b extends a {

    /* renamed from: a  reason: collision with root package name */
    static boolean f1465a = false;

    /* renamed from: b  reason: collision with root package name */
    private final h f1466b;

    /* renamed from: c  reason: collision with root package name */
    private final c f1467c;

    public static class a<D> extends o<D> implements a.AbstractC0029a<D> {
        private final int k;
        private final Bundle l;
        private final b.i.b.a<D> m;
        private h n;
        private C0028b<D> o;
        private b.i.b.a<D> p;

        /* access modifiers changed from: package-private */
        public b.i.b.a<D> a(boolean z) {
            if (b.f1465a) {
                Log.v("LoaderManager", "  Destroying: " + this);
            }
            this.m.a();
            throw null;
        }

        /* access modifiers changed from: protected */
        @Override // androidx.lifecycle.LiveData
        public void a() {
            if (b.f1465a) {
                Log.v("LoaderManager", "  Starting: " + this);
            }
            this.m.c();
            throw null;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: androidx.lifecycle.p<? super D> */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.lifecycle.LiveData
        public void a(p<? super D> pVar) {
            super.a((p) pVar);
            this.n = null;
            this.o = null;
        }

        @Override // androidx.lifecycle.LiveData, androidx.lifecycle.o
        public void a(D d2) {
            super.a((Object) d2);
            b.i.b.a<D> aVar = this.p;
            if (aVar != null) {
                aVar.b();
                throw null;
            }
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mId=");
            printWriter.print(this.k);
            printWriter.print(" mArgs=");
            printWriter.println(this.l);
            printWriter.print(str);
            printWriter.print("mLoader=");
            printWriter.println(this.m);
            b.i.b.a<D> aVar = this.m;
            aVar.a(str + "  ", fileDescriptor, printWriter, strArr);
            throw null;
        }

        /* access modifiers changed from: protected */
        @Override // androidx.lifecycle.LiveData
        public void b() {
            if (b.f1465a) {
                Log.v("LoaderManager", "  Stopping: " + this);
            }
            this.m.d();
            throw null;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            h hVar = this.n;
            C0028b<D> bVar = this.o;
            if (hVar != null && bVar != null) {
                super.a((p) bVar);
                a(hVar, bVar);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.k);
            sb.append(" : ");
            b.e.f.a.a(this.m, sb);
            sb.append("}}");
            return sb.toString();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b.i.a.b$b  reason: collision with other inner class name */
    public static class C0028b<D> implements p<D> {
    }

    static class c extends r {

        /* renamed from: a  reason: collision with root package name */
        private static final s.a f1468a = new c();

        /* renamed from: b  reason: collision with root package name */
        private j<a> f1469b = new j<>();

        /* renamed from: c  reason: collision with root package name */
        private boolean f1470c = false;

        c() {
        }

        static c a(t tVar) {
            return (c) new s(tVar, f1468a).a(c.class);
        }

        /* access modifiers changed from: protected */
        @Override // androidx.lifecycle.r
        public void a() {
            super.a();
            if (this.f1469b.b() <= 0) {
                this.f1469b.a();
            } else {
                this.f1469b.e(0).a(true);
                throw null;
            }
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (this.f1469b.b() > 0) {
                printWriter.print(str);
                printWriter.println("Loaders:");
                String str2 = str + "    ";
                if (this.f1469b.b() > 0) {
                    a e = this.f1469b.e(0);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(this.f1469b.c(0));
                    printWriter.print(": ");
                    printWriter.println(e.toString());
                    e.a(str2, fileDescriptor, printWriter, strArr);
                    throw null;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            int b2 = this.f1469b.b();
            for (int i = 0; i < b2; i++) {
                this.f1469b.e(i).c();
            }
        }
    }

    b(h hVar, t tVar) {
        this.f1466b = hVar;
        this.f1467c = c.a(tVar);
    }

    @Override // b.i.a.a
    public void a() {
        this.f1467c.b();
    }

    @Override // b.i.a.a
    @Deprecated
    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.f1467c.a(str, fileDescriptor, printWriter, strArr);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        b.e.f.a.a(this.f1466b, sb);
        sb.append("}}");
        return sb.toString();
    }
}
