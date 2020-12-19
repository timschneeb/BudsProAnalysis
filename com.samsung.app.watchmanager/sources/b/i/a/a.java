package b.i.a;

import androidx.lifecycle.h;
import androidx.lifecycle.u;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class a {
    public static <T extends h & u> a a(T t) {
        return new b(t, t.b());
    }

    public abstract void a();

    @Deprecated
    public abstract void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);
}
