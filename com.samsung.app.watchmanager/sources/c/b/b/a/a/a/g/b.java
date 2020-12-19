package c.b.b.a.a.a.g;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import c.b.b.a.a.a.i.b;
import c.b.b.a.a.a.i.c;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class b {

    /* renamed from: a  reason: collision with root package name */
    private Set<String> f1797a;

    /* renamed from: b  reason: collision with root package name */
    private Context f1798b;

    /* renamed from: c  reason: collision with root package name */
    private final String f1799c = b.a.TWO_DEPTH.c();

    /* renamed from: d  reason: collision with root package name */
    private final String f1800d = b.a.TWO_DEPTH.b();
    private final String e = b.a.THREE_DEPTH.b();

    public b(Context context) {
        this.f1798b = context;
        this.f1797a = c.a(context).getStringSet("AppPrefs", new HashSet());
    }

    private Set<String> a(String str) {
        return c.a(this.f1798b).getStringSet(str, new HashSet());
    }

    private SharedPreferences b(String str) {
        return this.f1798b.getSharedPreferences(str, 0);
    }

    private List<String> b() {
        StringBuilder sb;
        if (this.f1797a.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        String str = "";
        for (String str2 : this.f1797a) {
            SharedPreferences b2 = b(str2);
            Set<String> a2 = a(str2);
            for (Map.Entry<String, ?> entry : b2.getAll().entrySet()) {
                if (a2.contains(entry.getKey())) {
                    Class<?> cls = entry.getValue().getClass();
                    if (cls.equals(Integer.class) || cls.equals(Float.class) || cls.equals(Long.class) || cls.equals(String.class) || cls.equals(Boolean.class)) {
                        sb = new StringBuilder();
                        sb.append("");
                        sb.append(entry.getKey());
                        sb.append(this.f1799c);
                        sb.append(entry.getValue());
                    } else {
                        String str3 = "" + entry.getKey() + this.f1799c;
                        String str4 = null;
                        for (String str5 : (Set) entry.getValue()) {
                            if (!TextUtils.isEmpty(str4)) {
                                str4 = str4 + this.e;
                            }
                            str4 = str4 + str5;
                        }
                        sb = new StringBuilder();
                        sb.append(str3);
                        sb.append(str4);
                    }
                    String sb2 = sb.toString();
                    if (str.length() + sb2.length() > 512) {
                        arrayList.add(str);
                        str = "";
                    } else if (!TextUtils.isEmpty(str)) {
                        str = str + this.f1800d;
                    }
                    str = str + sb2;
                }
            }
        }
        if (str.length() != 0) {
            arrayList.add(str);
        }
        return arrayList;
    }

    public List<String> a() {
        return b();
    }
}
