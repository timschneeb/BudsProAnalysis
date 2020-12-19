package androidx.media;

import android.os.Build;

public final class y {

    /* renamed from: a  reason: collision with root package name */
    z f935a;

    public y(String str, int i, int i2) {
        this.f935a = Build.VERSION.SDK_INT >= 28 ? new A(str, i, i2) : new B(str, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof y)) {
            return false;
        }
        return this.f935a.equals(((y) obj).f935a);
    }

    public int hashCode() {
        return this.f935a.hashCode();
    }
}
