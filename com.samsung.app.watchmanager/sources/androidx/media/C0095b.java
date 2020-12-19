package androidx.media;

import android.annotation.TargetApi;
import android.media.AudioAttributes;

@TargetApi(21)
/* renamed from: androidx.media.b  reason: case insensitive filesystem */
class C0095b implements AbstractC0094a {

    /* renamed from: a  reason: collision with root package name */
    AudioAttributes f888a;

    /* renamed from: b  reason: collision with root package name */
    int f889b = -1;

    C0095b() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0095b)) {
            return false;
        }
        return this.f888a.equals(((C0095b) obj).f888a);
    }

    public int hashCode() {
        return this.f888a.hashCode();
    }

    public String toString() {
        return "AudioAttributesCompat: audioattributes=" + this.f888a;
    }
}
