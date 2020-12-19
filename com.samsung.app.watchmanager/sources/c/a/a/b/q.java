package c.a.a.b;

import c.a.a.AbstractC0116b;
import c.a.a.C0148c;
import c.a.a.J;
import c.a.a.K;
import c.a.a.a.d;
import c.a.a.a.e;
import c.a.a.c.a;
import c.a.a.p;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public final class q implements K, Cloneable {

    /* renamed from: a  reason: collision with root package name */
    public static final q f1632a = new q();

    /* renamed from: b  reason: collision with root package name */
    private double f1633b = -1.0d;

    /* renamed from: c  reason: collision with root package name */
    private int f1634c = 136;

    /* renamed from: d  reason: collision with root package name */
    private boolean f1635d = true;
    private boolean e;
    private List<AbstractC0116b> f = Collections.emptyList();
    private List<AbstractC0116b> g = Collections.emptyList();

    private boolean a(d dVar) {
        return dVar == null || dVar.value() <= this.f1633b;
    }

    private boolean a(d dVar, e eVar) {
        return a(dVar) && a(eVar);
    }

    private boolean a(e eVar) {
        return eVar == null || eVar.value() > this.f1633b;
    }

    private boolean a(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean b(Class<?> cls) {
        return cls.isMemberClass() && !c(cls);
    }

    private boolean c(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        boolean a3 = a((Class<?>) a2, true);
        boolean a4 = a((Class<?>) a2, false);
        if (a3 || a4) {
            return new p(this, a4, a3, pVar, aVar);
        }
        return null;
    }

    public boolean a(Class<?> cls, boolean z) {
        if (!(this.f1633b == -1.0d || a((d) cls.getAnnotation(d.class), (e) cls.getAnnotation(e.class)))) {
            return true;
        }
        if ((!this.f1635d && b(cls)) || a(cls)) {
            return true;
        }
        for (AbstractC0116b bVar : z ? this.f : this.g) {
            if (bVar.a(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean a(Field field, boolean z) {
        c.a.a.a.a aVar;
        if ((this.f1634c & field.getModifiers()) != 0) {
            return true;
        }
        if (!(this.f1633b == -1.0d || a((d) field.getAnnotation(d.class), (e) field.getAnnotation(e.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.e && ((aVar = (c.a.a.a.a) field.getAnnotation(c.a.a.a.a.class)) == null || (!z ? !aVar.deserialize() : !aVar.serialize()))) {
            return true;
        }
        if ((!this.f1635d && b(field.getType())) || a(field.getType())) {
            return true;
        }
        List<AbstractC0116b> list = z ? this.f : this.g;
        if (list.isEmpty()) {
            return false;
        }
        C0148c cVar = new C0148c(field);
        for (AbstractC0116b bVar : list) {
            if (bVar.a(cVar)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // java.lang.Object
    public q clone() {
        try {
            return (q) super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }
}
