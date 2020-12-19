package c.a.a.b;

import c.a.a.w;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;

/* access modifiers changed from: package-private */
public class j implements x<T> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Type f1621a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ o f1622b;

    j(o oVar, Type type) {
        this.f1622b = oVar;
        this.f1621a = type;
    }

    @Override // c.a.a.b.x
    public T a() {
        Type type = this.f1621a;
        if (type instanceof ParameterizedType) {
            Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            if (type2 instanceof Class) {
                return (T) EnumSet.noneOf((Class) type2);
            }
            throw new w("Invalid EnumSet type: " + this.f1621a.toString());
        }
        throw new w("Invalid EnumSet type: " + this.f1621a.toString());
    }
}
