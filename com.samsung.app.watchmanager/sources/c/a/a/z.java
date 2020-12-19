package c.a.a;

public class z extends RuntimeException {
    public z(String str) {
        super(str);
    }

    public z(String str, Throwable th) {
        super(str, th);
    }

    public z(Throwable th) {
        super(th);
    }
}
