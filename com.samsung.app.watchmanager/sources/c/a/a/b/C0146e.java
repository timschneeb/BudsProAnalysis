package c.a.a.b;

import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
/* renamed from: c.a.a.b.e  reason: case insensitive filesystem */
public class C0146e implements x<T> {

    /* renamed from: a  reason: collision with root package name */
    private final F f1608a = F.a();

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ Class f1609b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Type f1610c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ o f1611d;

    C0146e(o oVar, Class cls, Type type) {
        this.f1611d = oVar;
        this.f1609b = cls;
        this.f1610c = type;
    }

    @Override // c.a.a.b.x
    public T a() {
        try {
            return (T) this.f1608a.a(this.f1609b);
        } catch (Exception e) {
            throw new RuntimeException("Unable to invoke no-args constructor for " + this.f1610c + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e);
        }
    }
}
