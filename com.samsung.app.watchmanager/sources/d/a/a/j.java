package d.a.a;

public class j extends IllegalArgumentException {

    /* renamed from: a  reason: collision with root package name */
    private final d f2159a;

    /* renamed from: b  reason: collision with root package name */
    private final i f2160b = null;

    /* renamed from: c  reason: collision with root package name */
    private final String f2161c;

    /* renamed from: d  reason: collision with root package name */
    private final Number f2162d;
    private final String e;
    private final Number f;
    private final Number g;
    private String h;

    public j(d dVar, Number number, Number number2, Number number3) {
        super(a(dVar.i(), number, number2, number3, null));
        this.f2159a = dVar;
        this.f2161c = dVar.i();
        this.f2162d = number;
        this.e = null;
        this.f = number2;
        this.g = number3;
        this.h = super.getMessage();
    }

    public j(d dVar, Number number, Number number2, Number number3, String str) {
        super(a(dVar.i(), number, number2, number3, str));
        this.f2159a = dVar;
        this.f2161c = dVar.i();
        this.f2162d = number;
        this.e = null;
        this.f = number2;
        this.g = number3;
        this.h = super.getMessage();
    }

    public j(d dVar, Number number, String str) {
        super(a(dVar.i(), number, null, null, str));
        this.f2159a = dVar;
        this.f2161c = dVar.i();
        this.f2162d = number;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = super.getMessage();
    }

    public j(d dVar, String str) {
        super(a(dVar.i(), str));
        this.f2159a = dVar;
        this.f2161c = dVar.i();
        this.e = str;
        this.f2162d = null;
        this.f = null;
        this.g = null;
        this.h = super.getMessage();
    }

    private static String a(String str, Number number, Number number2, Number number3, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Value ");
        sb.append(number);
        sb.append(" for ");
        sb.append(str);
        sb.append(' ');
        if (number2 == null) {
            if (number3 == null) {
                sb.append("is not supported");
            } else {
                sb.append("must not be larger than ");
                sb.append(number3);
            }
        } else if (number3 == null) {
            sb.append("must not be smaller than ");
            sb.append(number2);
        } else {
            sb.append("must be in the range [");
            sb.append(number2);
            sb.append(',');
            sb.append(number3);
            sb.append(']');
        }
        if (str2 != null) {
            sb.append(": ");
            sb.append(str2);
        }
        return sb.toString();
    }

    private static String a(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Value ");
        if (str2 == null) {
            stringBuffer.append("null");
        } else {
            stringBuffer.append('\"');
            stringBuffer.append(str2);
            stringBuffer.append('\"');
        }
        stringBuffer.append(" for ");
        stringBuffer.append(str);
        stringBuffer.append(' ');
        stringBuffer.append("is not supported");
        return stringBuffer.toString();
    }

    public void a(String str) {
        if (this.h != null) {
            if (str != null) {
                str = str + ": " + this.h;
            } else {
                return;
            }
        }
        this.h = str;
    }

    public String getMessage() {
        return this.h;
    }
}
