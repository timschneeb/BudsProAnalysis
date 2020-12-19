package b.e.d;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.core.content.a.h;
import b.c.g;
import b.c.i;
import b.e.d.k;
import b.e.f.h;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class f {

    /* renamed from: a  reason: collision with root package name */
    static final g<String, Typeface> f1352a = new g<>(16);

    /* renamed from: b  reason: collision with root package name */
    private static final k f1353b = new k("fonts", 10, 10000);

    /* renamed from: c  reason: collision with root package name */
    static final Object f1354c = new Object();

    /* renamed from: d  reason: collision with root package name */
    static final i<String, ArrayList<k.a<c>>> f1355d = new i<>();
    private static final Comparator<byte[]> e = new e();

    public static class a {

        /* renamed from: a  reason: collision with root package name */
        private final int f1356a;

        /* renamed from: b  reason: collision with root package name */
        private final b[] f1357b;

        public a(int i, b[] bVarArr) {
            this.f1356a = i;
            this.f1357b = bVarArr;
        }

        public b[] a() {
            return this.f1357b;
        }

        public int b() {
            return this.f1356a;
        }
    }

    public static class b {

        /* renamed from: a  reason: collision with root package name */
        private final Uri f1358a;

        /* renamed from: b  reason: collision with root package name */
        private final int f1359b;

        /* renamed from: c  reason: collision with root package name */
        private final int f1360c;

        /* renamed from: d  reason: collision with root package name */
        private final boolean f1361d;
        private final int e;

        public b(Uri uri, int i, int i2, boolean z, int i3) {
            h.a(uri);
            this.f1358a = uri;
            this.f1359b = i;
            this.f1360c = i2;
            this.f1361d = z;
            this.e = i3;
        }

        public int a() {
            return this.e;
        }

        public int b() {
            return this.f1359b;
        }

        public Uri c() {
            return this.f1358a;
        }

        public int d() {
            return this.f1360c;
        }

        public boolean e() {
            return this.f1361d;
        }
    }

    /* access modifiers changed from: private */
    public static final class c {

        /* renamed from: a  reason: collision with root package name */
        final Typeface f1362a;

        /* renamed from: b  reason: collision with root package name */
        final int f1363b;

        c(Typeface typeface, int i) {
            this.f1362a = typeface;
            this.f1363b = i;
        }
    }

    public static ProviderInfo a(PackageManager packageManager, a aVar, Resources resources) {
        String d2 = aVar.d();
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(d2, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + d2);
        } else if (resolveContentProvider.packageName.equals(aVar.e())) {
            List<byte[]> a2 = a(packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures);
            Collections.sort(a2, e);
            List<List<byte[]>> a3 = a(aVar, resources);
            for (int i = 0; i < a3.size(); i++) {
                ArrayList arrayList = new ArrayList(a3.get(i));
                Collections.sort(arrayList, e);
                if (a(a2, arrayList)) {
                    return resolveContentProvider;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + d2 + ", but package was not " + aVar.e());
        }
    }

    public static Typeface a(Context context, a aVar, h.a aVar2, Handler handler, boolean z, int i, int i2) {
        String str = aVar.c() + "-" + i2;
        Typeface b2 = f1352a.b(str);
        if (b2 != null) {
            if (aVar2 != null) {
                aVar2.a(b2);
            }
            return b2;
        } else if (!z || i != -1) {
            b bVar = new b(context, aVar, i2, str);
            if (z) {
                try {
                    return ((c) f1353b.a(bVar, i)).f1362a;
                } catch (InterruptedException unused) {
                    return null;
                }
            } else {
                c cVar = aVar2 == null ? null : new c(aVar2, handler);
                synchronized (f1354c) {
                    if (f1355d.containsKey(str)) {
                        if (cVar != null) {
                            f1355d.get(str).add(cVar);
                        }
                        return null;
                    }
                    if (cVar != null) {
                        ArrayList<k.a<c>> arrayList = new ArrayList<>();
                        arrayList.add(cVar);
                        f1355d.put(str, arrayList);
                    }
                    f1353b.a(bVar, new d(str));
                    return null;
                }
            }
        } else {
            c a2 = a(context, aVar, i2);
            if (aVar2 != null) {
                int i3 = a2.f1363b;
                if (i3 == 0) {
                    aVar2.a(a2.f1362a, handler);
                } else {
                    aVar2.a(i3, handler);
                }
            }
            return a2.f1362a;
        }
    }

    public static a a(Context context, CancellationSignal cancellationSignal, a aVar) {
        ProviderInfo a2 = a(context.getPackageManager(), aVar, context.getResources());
        return a2 == null ? new a(1, null) : new a(0, a(context, aVar, a2.authority, cancellationSignal));
    }

    static c a(Context context, a aVar, int i) {
        try {
            a a2 = a(context, (CancellationSignal) null, aVar);
            int i2 = -3;
            if (a2.b() == 0) {
                Typeface a3 = b.e.a.c.a(context, null, a2.a(), i);
                if (a3 != null) {
                    i2 = 0;
                }
                return new c(a3, i2);
            }
            if (a2.b() == 1) {
                i2 = -2;
            }
            return new c(null, i2);
        } catch (PackageManager.NameNotFoundException unused) {
            return new c(null, -1);
        }
    }

    private static List<List<byte[]>> a(a aVar, Resources resources) {
        return aVar.a() != null ? aVar.a() : androidx.core.content.a.c.a(resources, aVar.b());
    }

    private static List<byte[]> a(Signature[] signatureArr) {
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        return arrayList;
    }

    public static Map<Uri, ByteBuffer> a(Context context, b[] bVarArr, CancellationSignal cancellationSignal) {
        HashMap hashMap = new HashMap();
        for (b bVar : bVarArr) {
            if (bVar.a() == 0) {
                Uri c2 = bVar.c();
                if (!hashMap.containsKey(c2)) {
                    hashMap.put(c2, b.e.a.k.a(context, cancellationSignal, c2));
                }
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    private static boolean a(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x013b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static b.e.d.f.b[] a(android.content.Context r19, b.e.d.a r20, java.lang.String r21, android.os.CancellationSignal r22) {
        /*
        // Method dump skipped, instructions count: 321
        */
        throw new UnsupportedOperationException("Method not decompiled: b.e.d.f.a(android.content.Context, b.e.d.a, java.lang.String, android.os.CancellationSignal):b.e.d.f$b[]");
    }
}
