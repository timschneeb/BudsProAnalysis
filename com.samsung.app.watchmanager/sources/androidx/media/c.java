package androidx.media;

import java.util.Arrays;

class c implements AbstractC0094a {

    /* renamed from: a  reason: collision with root package name */
    int f890a = 0;

    /* renamed from: b  reason: collision with root package name */
    int f891b = 0;

    /* renamed from: c  reason: collision with root package name */
    int f892c = 0;

    /* renamed from: d  reason: collision with root package name */
    int f893d = -1;

    c() {
    }

    public int a() {
        return this.f891b;
    }

    public int b() {
        int i = this.f892c;
        int c2 = c();
        if (c2 == 6) {
            i |= 4;
        } else if (c2 == 7) {
            i |= 1;
        }
        return i & 273;
    }

    public int c() {
        int i = this.f893d;
        return i != -1 ? i : AudioAttributesCompat.a(false, this.f892c, this.f890a);
    }

    public int d() {
        return this.f890a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return this.f891b == cVar.a() && this.f892c == cVar.b() && this.f890a == cVar.d() && this.f893d == cVar.f893d;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.f891b), Integer.valueOf(this.f892c), Integer.valueOf(this.f890a), Integer.valueOf(this.f893d)});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (this.f893d != -1) {
            sb.append(" stream=");
            sb.append(this.f893d);
            sb.append(" derived");
        }
        sb.append(" usage=");
        sb.append(AudioAttributesCompat.a(this.f890a));
        sb.append(" content=");
        sb.append(this.f891b);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.f892c).toUpperCase());
        return sb.toString();
    }
}
