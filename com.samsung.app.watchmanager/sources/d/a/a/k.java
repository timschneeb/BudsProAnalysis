package d.a.a;

import d.a.a.d.a;

public class k extends IllegalArgumentException {
    public k(long j, String str) {
        super(a(j, str));
    }

    public k(String str) {
        super(str);
    }

    private static String a(long j, String str) {
        String str2;
        String a2 = a.a("yyyy-MM-dd'T'HH:mm:ss.SSS").a(new l(j));
        if (str != null) {
            str2 = " (" + str + ")";
        } else {
            str2 = "";
        }
        return "Illegal instant due to time zone offset transition (daylight savings time 'gap'): " + a2 + str2;
    }
}
