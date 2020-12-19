package androidx.media;

import android.media.session.MediaSessionManager;
import b.e.f.c;

final class A implements z {

    /* renamed from: a  reason: collision with root package name */
    final MediaSessionManager.RemoteUserInfo f859a;

    A(String str, int i, int i2) {
        this.f859a = new MediaSessionManager.RemoteUserInfo(str, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof A)) {
            return false;
        }
        return this.f859a.equals(((A) obj).f859a);
    }

    public int hashCode() {
        return c.a(this.f859a);
    }
}
