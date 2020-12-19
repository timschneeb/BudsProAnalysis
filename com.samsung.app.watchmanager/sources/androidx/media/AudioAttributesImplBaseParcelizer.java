package androidx.media;

import androidx.versionedparcelable.b;

public final class AudioAttributesImplBaseParcelizer {
    public static c read(b bVar) {
        c cVar = new c();
        cVar.f890a = bVar.a(cVar.f890a, 1);
        cVar.f891b = bVar.a(cVar.f891b, 2);
        cVar.f892c = bVar.a(cVar.f892c, 3);
        cVar.f893d = bVar.a(cVar.f893d, 4);
        return cVar;
    }

    public static void write(c cVar, b bVar) {
        bVar.a(false, false);
        bVar.b(cVar.f890a, 1);
        bVar.b(cVar.f891b, 2);
        bVar.b(cVar.f892c, 3);
        bVar.b(cVar.f893d, 4);
    }
}
