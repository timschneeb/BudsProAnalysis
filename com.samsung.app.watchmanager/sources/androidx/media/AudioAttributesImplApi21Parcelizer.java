package androidx.media;

import android.media.AudioAttributes;
import androidx.versionedparcelable.b;

public final class AudioAttributesImplApi21Parcelizer {
    public static C0095b read(b bVar) {
        C0095b bVar2 = new C0095b();
        bVar2.f888a = (AudioAttributes) bVar.a(bVar2.f888a, 1);
        bVar2.f889b = bVar.a(bVar2.f889b, 2);
        return bVar2;
    }

    public static void write(C0095b bVar, b bVar2) {
        bVar2.a(false, false);
        bVar2.b(bVar.f888a, 1);
        bVar2.b(bVar.f889b, 2);
    }
}
