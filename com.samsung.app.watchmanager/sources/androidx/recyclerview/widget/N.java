package androidx.recyclerview.widget;

import android.view.View;

/* access modifiers changed from: package-private */
public class N {

    /* renamed from: a  reason: collision with root package name */
    final b f966a;

    /* renamed from: b  reason: collision with root package name */
    a f967b = new a();

    /* access modifiers changed from: package-private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        int f968a = 0;

        /* renamed from: b  reason: collision with root package name */
        int f969b;

        /* renamed from: c  reason: collision with root package name */
        int f970c;

        /* renamed from: d  reason: collision with root package name */
        int f971d;
        int e;

        a() {
        }

        /* access modifiers changed from: package-private */
        public int a(int i, int i2) {
            if (i > i2) {
                return 1;
            }
            return i == i2 ? 2 : 4;
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            this.f968a = i | this.f968a;
        }

        /* access modifiers changed from: package-private */
        public void a(int i, int i2, int i3, int i4) {
            this.f969b = i;
            this.f970c = i2;
            this.f971d = i3;
            this.e = i4;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            int i = this.f968a;
            if ((i & 7) != 0 && (i & (a(this.f971d, this.f969b) << 0)) == 0) {
                return false;
            }
            int i2 = this.f968a;
            if ((i2 & 112) != 0 && (i2 & (a(this.f971d, this.f970c) << 4)) == 0) {
                return false;
            }
            int i3 = this.f968a;
            if ((i3 & 1792) != 0 && (i3 & (a(this.e, this.f969b) << 8)) == 0) {
                return false;
            }
            int i4 = this.f968a;
            return (i4 & 28672) == 0 || (i4 & (a(this.e, this.f970c) << 12)) != 0;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f968a = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public interface b {
        int a();

        int a(View view);

        View a(int i);

        int b();

        int b(View view);
    }

    N(b bVar) {
        this.f966a = bVar;
    }

    /* access modifiers changed from: package-private */
    public View a(int i, int i2, int i3, int i4) {
        int a2 = this.f966a.a();
        int b2 = this.f966a.b();
        int i5 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View a3 = this.f966a.a(i);
            this.f967b.a(a2, b2, this.f966a.a(a3), this.f966a.b(a3));
            if (i3 != 0) {
                this.f967b.b();
                this.f967b.a(i3);
                if (this.f967b.a()) {
                    return a3;
                }
            }
            if (i4 != 0) {
                this.f967b.b();
                this.f967b.a(i4);
                if (this.f967b.a()) {
                    view = a3;
                }
            }
            i += i5;
        }
        return view;
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, int i) {
        this.f967b.a(this.f966a.a(), this.f966a.b(), this.f966a.a(view), this.f966a.b(view));
        if (i == 0) {
            return false;
        }
        this.f967b.b();
        this.f967b.a(i);
        return this.f967b.a();
    }
}
