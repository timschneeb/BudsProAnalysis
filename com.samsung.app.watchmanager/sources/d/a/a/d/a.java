package d.a.a.d;

import d.a.a.b;
import d.a.a.g;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private static final ConcurrentHashMap<String, b> f2067a = new ConcurrentHashMap<>();

    /* renamed from: b  reason: collision with root package name */
    private static final AtomicReferenceArray<b> f2068b = new AtomicReferenceArray<>(25);

    public static b a(String str) {
        return b(str);
    }

    private static String a(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i = iArr[0];
        int length = str.length();
        char charAt = str.charAt(i);
        if ((charAt >= 'A' && charAt <= 'Z') || (charAt >= 'a' && charAt <= 'z')) {
            sb.append(charAt);
            while (true) {
                int i2 = i + 1;
                if (i2 >= length || str.charAt(i2) != charAt) {
                    break;
                }
                sb.append(charAt);
                i = i2;
            }
        } else {
            sb.append('\'');
            boolean z = false;
            while (true) {
                if (i >= length) {
                    break;
                }
                char charAt2 = str.charAt(i);
                if (charAt2 == '\'') {
                    int i3 = i + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z = !z;
                    } else {
                        sb.append(charAt2);
                        i = i3;
                    }
                } else if (z || ((charAt2 < 'A' || charAt2 > 'Z') && (charAt2 < 'a' || charAt2 > 'z'))) {
                    sb.append(charAt2);
                }
                i++;
            }
            i--;
        }
        iArr[0] = i;
        return sb.toString();
    }

    private static void a(c cVar, String str) {
        boolean z;
        String str2;
        boolean z2;
        int length = str.length();
        int[] iArr = new int[1];
        int i = 0;
        while (i < length) {
            iArr[0] = i;
            String a2 = a(str, iArr);
            int i2 = iArr[0];
            int length2 = a2.length();
            if (length2 != 0) {
                char charAt = a2.charAt(0);
                if (charAt == '\'') {
                    String substring = a2.substring(1);
                    if (substring.length() == 1) {
                        cVar.a(substring.charAt(0));
                    } else {
                        cVar.a(new String(substring));
                    }
                } else if (charAt == 'K') {
                    cVar.g(length2);
                } else if (charAt != 'M') {
                    if (charAt == 'S') {
                        cVar.d(length2, length2);
                    } else if (charAt == 'a') {
                        cVar.d();
                    } else if (charAt == 'h') {
                        cVar.b(length2);
                    } else if (charAt == 'k') {
                        cVar.a(length2);
                    } else if (charAt == 'm') {
                        cVar.h(length2);
                    } else if (charAt == 's') {
                        cVar.j(length2);
                    } else if (charAt == 'G') {
                        cVar.c();
                    } else if (charAt != 'H') {
                        if (charAt != 'Y') {
                            if (charAt == 'Z') {
                                if (length2 == 1) {
                                    str2 = null;
                                    z2 = false;
                                } else if (length2 == 2) {
                                    str2 = null;
                                    z2 = true;
                                } else {
                                    cVar.g();
                                }
                                cVar.a(str2, "Z", z2, 2, 2);
                            } else if (charAt == 'd') {
                                cVar.c(length2);
                            } else if (charAt != 'e') {
                                switch (charAt) {
                                    case 'C':
                                        cVar.a(length2, length2);
                                        break;
                                    case 'D':
                                        cVar.e(length2);
                                        break;
                                    case 'E':
                                        if (length2 < 4) {
                                            cVar.a();
                                            break;
                                        } else {
                                            cVar.b();
                                            break;
                                        }
                                    default:
                                        switch (charAt) {
                                            case 'w':
                                                cVar.k(length2);
                                                break;
                                            case 'x':
                                            case 'y':
                                                break;
                                            case 'z':
                                                if (length2 < 4) {
                                                    cVar.a((Map<String, g>) null);
                                                    break;
                                                } else {
                                                    cVar.h();
                                                    break;
                                                }
                                            default:
                                                throw new IllegalArgumentException("Illegal pattern component: " + a2);
                                        }
                                }
                            } else {
                                cVar.d(length2);
                            }
                        }
                        if (length2 == 2) {
                            if (i2 + 1 < length) {
                                iArr[0] = iArr[0] + 1;
                                z = !c(a(str, iArr));
                                iArr[0] = iArr[0] - 1;
                            } else {
                                z = true;
                            }
                            if (charAt != 'x') {
                                cVar.b(new b().g() - 30, z);
                            } else {
                                cVar.a(new b().f() - 30, z);
                            }
                        } else {
                            int i3 = 9;
                            if (i2 + 1 < length) {
                                iArr[0] = iArr[0] + 1;
                                if (c(a(str, iArr))) {
                                    i3 = length2;
                                }
                                iArr[0] = iArr[0] - 1;
                            }
                            if (charAt == 'Y') {
                                cVar.g(length2, i3);
                            } else if (charAt == 'x') {
                                cVar.e(length2, i3);
                            } else if (charAt == 'y') {
                                cVar.f(length2, i3);
                            }
                        }
                    } else {
                        cVar.f(length2);
                    }
                } else if (length2 < 3) {
                    cVar.i(length2);
                } else if (length2 >= 4) {
                    cVar.f();
                } else {
                    cVar.e();
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private static b b(String str) {
        b putIfAbsent;
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        b bVar = f2067a.get(str);
        if (bVar != null) {
            return bVar;
        }
        c cVar = new c();
        a(cVar, str);
        b i = cVar.i();
        return (f2067a.size() >= 500 || (putIfAbsent = f2067a.putIfAbsent(str, i)) == null) ? i : putIfAbsent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0013 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean c(java.lang.String r3) {
        /*
            int r0 = r3.length()
            r1 = 0
            if (r0 <= 0) goto L_0x0014
            char r3 = r3.charAt(r1)
            r2 = 1
            switch(r3) {
                case 67: goto L_0x0013;
                case 68: goto L_0x0013;
                case 70: goto L_0x0013;
                case 72: goto L_0x0013;
                case 75: goto L_0x0013;
                case 77: goto L_0x0010;
                case 83: goto L_0x0013;
                case 87: goto L_0x0013;
                case 89: goto L_0x0013;
                case 99: goto L_0x0013;
                case 100: goto L_0x0013;
                case 101: goto L_0x0013;
                case 104: goto L_0x0013;
                case 107: goto L_0x0013;
                case 109: goto L_0x0013;
                case 115: goto L_0x0013;
                case 119: goto L_0x0013;
                case 120: goto L_0x0013;
                case 121: goto L_0x0013;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0014
        L_0x0010:
            r3 = 2
            if (r0 > r3) goto L_0x0014
        L_0x0013:
            return r2
        L_0x0014:
            return r1
            switch-data {67->0x0013, 68->0x0013, 70->0x0013, 72->0x0013, 75->0x0013, 77->0x0010, 83->0x0013, 87->0x0013, 89->0x0013, 99->0x0013, 100->0x0013, 101->0x0013, 104->0x0013, 107->0x0013, 109->0x0013, 115->0x0013, 119->0x0013, 120->0x0013, 121->0x0013, }
        */
        throw new UnsupportedOperationException("Method not decompiled: d.a.a.d.a.c(java.lang.String):boolean");
    }
}
