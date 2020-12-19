package d.a.a.e;

import d.a.a.g;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class i implements f {

    /* renamed from: a  reason: collision with root package name */
    private final File f2145a;

    /* renamed from: b  reason: collision with root package name */
    private final String f2146b;

    /* renamed from: c  reason: collision with root package name */
    private final ClassLoader f2147c;

    /* renamed from: d  reason: collision with root package name */
    private final Map<String, Object> f2148d;
    private final Set<String> e;

    public i(File file) {
        if (file == null) {
            throw new IllegalArgumentException("No file directory provided");
        } else if (!file.exists()) {
            throw new IOException("File directory doesn't exist: " + file);
        } else if (file.isDirectory()) {
            this.f2145a = file;
            this.f2146b = null;
            this.f2147c = null;
            this.f2148d = a(c("ZoneInfoMap"));
            this.e = Collections.unmodifiableSortedSet(new TreeSet(this.f2148d.keySet()));
        } else {
            throw new IOException("File doesn't refer to a directory: " + file);
        }
    }

    public i(String str) {
        this(str, null, false);
    }

    private i(String str, ClassLoader classLoader, boolean z) {
        if (str != null) {
            if (!str.endsWith("/")) {
                str = str + '/';
            }
            this.f2145a = null;
            this.f2146b = str;
            if (classLoader == null && !z) {
                classLoader = i.class.getClassLoader();
            }
            this.f2147c = classLoader;
            this.f2148d = a(c("ZoneInfoMap"));
            this.e = Collections.unmodifiableSortedSet(new TreeSet(this.f2148d.keySet()));
            return;
        }
        throw new IllegalArgumentException("No resource path provided");
    }

    private static Map<String, Object> a(InputStream inputStream) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            a(dataInputStream, concurrentHashMap);
            concurrentHashMap.put("UTC", new SoftReference(g.f2149a));
            return concurrentHashMap;
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private static void a(DataInputStream dataInputStream, Map<String, Object> map) {
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        String[] strArr = new String[readUnsignedShort];
        for (int i = 0; i < readUnsignedShort; i++) {
            strArr[i] = dataInputStream.readUTF().intern();
        }
        int readUnsignedShort2 = dataInputStream.readUnsignedShort();
        for (int i2 = 0; i2 < readUnsignedShort2; i2++) {
            try {
                map.put(strArr[dataInputStream.readUnsignedShort()], strArr[dataInputStream.readUnsignedShort()]);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Corrupt zone info map");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002c A[SYNTHETIC, Splitter:B:19:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0032 A[SYNTHETIC, Splitter:B:24:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private d.a.a.g b(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            java.io.InputStream r1 = r5.c(r6)     // Catch:{ IOException -> 0x0020, all -> 0x001d }
            d.a.a.g r2 = d.a.a.e.b.a(r1, r6)     // Catch:{ IOException -> 0x001b }
            java.util.Map<java.lang.String, java.lang.Object> r3 = r5.f2148d     // Catch:{ IOException -> 0x001b }
            java.lang.ref.SoftReference r4 = new java.lang.ref.SoftReference     // Catch:{ IOException -> 0x001b }
            r4.<init>(r2)     // Catch:{ IOException -> 0x001b }
            r3.put(r6, r4)     // Catch:{ IOException -> 0x001b }
            if (r1 == 0) goto L_0x0018
            r1.close()     // Catch:{ IOException -> 0x0018 }
        L_0x0018:
            return r2
        L_0x0019:
            r6 = move-exception
            goto L_0x0030
        L_0x001b:
            r2 = move-exception
            goto L_0x0022
        L_0x001d:
            r6 = move-exception
            r1 = r0
            goto L_0x0030
        L_0x0020:
            r2 = move-exception
            r1 = r0
        L_0x0022:
            r5.a(r2)     // Catch:{ all -> 0x0019 }
            java.util.Map<java.lang.String, java.lang.Object> r2 = r5.f2148d     // Catch:{ all -> 0x0019 }
            r2.remove(r6)     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ IOException -> 0x002f }
        L_0x002f:
            return r0
        L_0x0030:
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ IOException -> 0x0035 }
        L_0x0035:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: d.a.a.e.i.b(java.lang.String):d.a.a.g");
    }

    private InputStream c(String str) {
        File file = this.f2145a;
        if (file != null) {
            return new FileInputStream(new File(file, str));
        }
        String concat = this.f2146b.concat(str);
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new h(this, concat));
        if (inputStream != null) {
            return inputStream;
        }
        StringBuilder sb = new StringBuilder(40);
        sb.append("Resource not found: \"");
        sb.append(concat);
        sb.append("\" ClassLoader: ");
        ClassLoader classLoader = this.f2147c;
        sb.append(classLoader != null ? classLoader.toString() : "system");
        throw new IOException(sb.toString());
    }

    @Override // d.a.a.e.f
    public g a(String str) {
        Object obj;
        if (str == null || (obj = this.f2148d.get(str)) == null) {
            return null;
        }
        if (!(obj instanceof SoftReference)) {
            return str.equals(obj) ? b(str) : a((String) obj);
        }
        g gVar = (g) ((SoftReference) obj).get();
        return gVar != null ? gVar : b(str);
    }

    @Override // d.a.a.e.f
    public Set<String> a() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void a(Exception exc) {
        exc.printStackTrace();
    }
}
