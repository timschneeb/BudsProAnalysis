package c.a.a;

import c.a.a.b.C0117a;
import c.a.a.b.s;
import java.math.BigInteger;

public final class A extends v {

    /* renamed from: a  reason: collision with root package name */
    private static final Class<?>[] f1520a = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};

    /* renamed from: b  reason: collision with root package name */
    private Object f1521b;

    public A(Boolean bool) {
        a(bool);
    }

    public A(Number number) {
        a(number);
    }

    public A(String str) {
        a(str);
    }

    private static boolean a(A a2) {
        Object obj = a2.f1521b;
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    private static boolean b(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class<?> cls = obj.getClass();
        for (Class<?> cls2 : f1520a) {
            if (cls2.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a(Object obj) {
        if (obj instanceof Character) {
            obj = String.valueOf(((Character) obj).charValue());
        } else {
            C0117a.a((obj instanceof Number) || b(obj));
        }
        this.f1521b = obj;
    }

    @Override // c.a.a.v
    public String d() {
        return p() ? n().toString() : o() ? j().toString() : (String) this.f1521b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || A.class != obj.getClass()) {
            return false;
        }
        A a2 = (A) obj;
        if (this.f1521b == null) {
            return a2.f1521b == null;
        }
        if (a(this) && a(a2)) {
            return n().longValue() == a2.n().longValue();
        }
        if (!(this.f1521b instanceof Number) || !(a2.f1521b instanceof Number)) {
            return this.f1521b.equals(a2.f1521b);
        }
        double doubleValue = n().doubleValue();
        double doubleValue2 = a2.n().doubleValue();
        if (doubleValue != doubleValue2) {
            return Double.isNaN(doubleValue) && Double.isNaN(doubleValue2);
        }
        return true;
    }

    public int hashCode() {
        long doubleToLongBits;
        if (this.f1521b == null) {
            return 31;
        }
        if (a(this)) {
            doubleToLongBits = n().longValue();
        } else {
            Object obj = this.f1521b;
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            doubleToLongBits = Double.doubleToLongBits(n().doubleValue());
        }
        return (int) ((doubleToLongBits >>> 32) ^ doubleToLongBits);
    }

    public boolean i() {
        return o() ? j().booleanValue() : Boolean.parseBoolean(d());
    }

    /* access modifiers changed from: package-private */
    public Boolean j() {
        return (Boolean) this.f1521b;
    }

    public double k() {
        return p() ? n().doubleValue() : Double.parseDouble(d());
    }

    public int l() {
        return p() ? n().intValue() : Integer.parseInt(d());
    }

    public long m() {
        return p() ? n().longValue() : Long.parseLong(d());
    }

    public Number n() {
        Object obj = this.f1521b;
        return obj instanceof String ? new s((String) obj) : (Number) obj;
    }

    public boolean o() {
        return this.f1521b instanceof Boolean;
    }

    public boolean p() {
        return this.f1521b instanceof Number;
    }

    public boolean q() {
        return this.f1521b instanceof String;
    }
}
