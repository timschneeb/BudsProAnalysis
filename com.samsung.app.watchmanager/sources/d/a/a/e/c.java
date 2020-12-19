package d.a.a.e;

import d.a.a.e;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class c implements e {

    /* renamed from: a  reason: collision with root package name */
    private HashMap<Locale, Map<String, Map<String, Object>>> f2140a = a();

    /* renamed from: b  reason: collision with root package name */
    private HashMap<Locale, Map<String, Map<Boolean, Object>>> f2141b = a();

    private HashMap a() {
        return new HashMap(7);
    }

    private synchronized String[] c(Locale locale, String str, String str2) {
        Object[] objArr;
        Object[] objArr2 = null;
        if (locale == null || str == null || str2 == null) {
            return null;
        }
        Map<String, Map<String, Object>> map = this.f2140a.get(locale);
        if (map == null) {
            HashMap<Locale, Map<String, Map<String, Object>>> hashMap = this.f2140a;
            HashMap a2 = a();
            hashMap.put(locale, a2);
            map = a2;
        }
        Map<String, Object> map2 = map.get(str);
        if (map2 == null) {
            map2 = a();
            map.put(str, map2);
            Object[][] zoneStrings = e.a(Locale.ENGLISH).getZoneStrings();
            int length = zoneStrings.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    objArr = null;
                    break;
                }
                objArr = zoneStrings[i];
                if (objArr != null && objArr.length >= 5 && str.equals(objArr[0])) {
                    break;
                }
                i++;
            }
            Object[][] zoneStrings2 = e.a(locale).getZoneStrings();
            int length2 = zoneStrings2.length;
            int i2 = 0;
            while (true) {
                if (i2 < length2) {
                    Object[] objArr3 = zoneStrings2[i2];
                    if (objArr3 != null && objArr3.length >= 5 && str.equals(objArr3[0])) {
                        objArr2 = objArr3;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (!(objArr == null || objArr2 == null)) {
                map2.put(objArr[2], new String[]{objArr2[2], objArr2[1]});
                if (objArr[2].equals(objArr[4])) {
                    map2.put(objArr[4] + "-Summer", new String[]{objArr2[4], objArr2[3]});
                } else {
                    map2.put(objArr[4], new String[]{objArr2[4], objArr2[3]});
                }
            }
        }
        return (String[]) map2.get(str2);
    }

    private synchronized String[] c(Locale locale, String str, String str2, boolean z) {
        String[] strArr;
        String[] strArr2 = null;
        if (locale == null || str == null || str2 == null) {
            return null;
        }
        if (str.startsWith("Etc/")) {
            str = str.substring(4);
        }
        Map<String, Map<Boolean, Object>> map = this.f2141b.get(locale);
        if (map == null) {
            HashMap<Locale, Map<String, Map<Boolean, Object>>> hashMap = this.f2141b;
            HashMap a2 = a();
            hashMap.put(locale, a2);
            map = a2;
        }
        Map<Boolean, Object> map2 = map.get(str);
        if (map2 == null) {
            map2 = a();
            map.put(str, map2);
            String[][] zoneStrings = e.a(Locale.ENGLISH).getZoneStrings();
            int length = zoneStrings.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    strArr = null;
                    break;
                }
                strArr = zoneStrings[i];
                if (strArr != null && strArr.length >= 5 && str.equals(strArr[0])) {
                    break;
                }
                i++;
            }
            String[][] zoneStrings2 = e.a(locale).getZoneStrings();
            int length2 = zoneStrings2.length;
            int i2 = 0;
            while (true) {
                if (i2 < length2) {
                    String[] strArr3 = zoneStrings2[i2];
                    if (strArr3 != null && strArr3.length >= 5 && str.equals(strArr3[0])) {
                        strArr2 = strArr3;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            if (!(strArr == null || strArr2 == null)) {
                map2.put(Boolean.TRUE, new String[]{strArr2[2], strArr2[1]});
                map2.put(Boolean.FALSE, new String[]{strArr2[4], strArr2[3]});
            }
        }
        return (String[]) map2.get(Boolean.valueOf(z));
    }

    @Override // d.a.a.e.e
    public String a(Locale locale, String str, String str2) {
        String[] c2 = c(locale, str, str2);
        if (c2 == null) {
            return null;
        }
        return c2[1];
    }

    public String a(Locale locale, String str, String str2, boolean z) {
        String[] c2 = c(locale, str, str2, z);
        if (c2 == null) {
            return null;
        }
        return c2[1];
    }

    @Override // d.a.a.e.e
    public String b(Locale locale, String str, String str2) {
        String[] c2 = c(locale, str, str2);
        if (c2 == null) {
            return null;
        }
        return c2[0];
    }

    public String b(Locale locale, String str, String str2, boolean z) {
        String[] c2 = c(locale, str, str2, z);
        if (c2 == null) {
            return null;
        }
        return c2[0];
    }
}
