package androidx.media;

import android.text.TextUtils;
import b.e.f.c;

class B implements z {

    /* renamed from: a  reason: collision with root package name */
    private String f863a;

    /* renamed from: b  reason: collision with root package name */
    private int f864b;

    /* renamed from: c  reason: collision with root package name */
    private int f865c;

    B(String str, int i, int i2) {
        this.f863a = str;
        this.f864b = i;
        this.f865c = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof B)) {
            return false;
        }
        B b2 = (B) obj;
        return TextUtils.equals(this.f863a, b2.f863a) && this.f864b == b2.f864b && this.f865c == b2.f865c;
    }

    public int hashCode() {
        return c.a(this.f863a, Integer.valueOf(this.f864b), Integer.valueOf(this.f865c));
    }
}
