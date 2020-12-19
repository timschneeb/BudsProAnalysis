package c.a.a.b.a;

import c.a.a.AbstractC0155j;
import c.a.a.D;
import c.a.a.J;
import c.a.a.K;
import c.a.a.b.C0143b;
import c.a.a.b.o;
import c.a.a.b.q;
import c.a.a.b.x;
import c.a.a.b.y;
import c.a.a.d.c;
import c.a.a.d.d;
import c.a.a.p;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: c.a.a.b.a.p  reason: case insensitive filesystem */
public final class C0133p implements K {

    /* renamed from: a  reason: collision with root package name */
    private final o f1584a;

    /* renamed from: b  reason: collision with root package name */
    private final AbstractC0155j f1585b;

    /* renamed from: c  reason: collision with root package name */
    private final q f1586c;

    /* renamed from: c.a.a.b.a.p$a */
    public static final class a<T> extends J<T> {

        /* renamed from: a  reason: collision with root package name */
        private final x<T> f1587a;

        /* renamed from: b  reason: collision with root package name */
        private final Map<String, b> f1588b;

        private a(x<T> xVar, Map<String, b> map) {
            this.f1587a = xVar;
            this.f1588b = map;
        }

        /* synthetic */ a(x xVar, Map map, C0132o oVar) {
            this(xVar, map);
        }

        @Override // c.a.a.J
        public T a(c.a.a.d.b bVar) {
            if (bVar.o() == c.NULL) {
                bVar.m();
                return null;
            }
            T a2 = this.f1587a.a();
            try {
                bVar.b();
                while (bVar.f()) {
                    b bVar2 = this.f1588b.get(bVar.l());
                    if (bVar2 != null) {
                        if (bVar2.f1591c) {
                            bVar2.a(bVar, a2);
                        }
                    }
                    bVar.p();
                }
                bVar.d();
                return a2;
            } catch (IllegalStateException e) {
                throw new D(e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // c.a.a.J
        public void a(d dVar, T t) {
            if (t == null) {
                dVar.h();
                return;
            }
            dVar.b();
            try {
                for (b bVar : this.f1588b.values()) {
                    if (bVar.a(t)) {
                        dVar.a(bVar.f1589a);
                        bVar.a(dVar, t);
                    }
                }
                dVar.d();
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c.a.a.b.a.p$b */
    public static abstract class b {

        /* renamed from: a  reason: collision with root package name */
        final String f1589a;

        /* renamed from: b  reason: collision with root package name */
        final boolean f1590b;

        /* renamed from: c  reason: collision with root package name */
        final boolean f1591c;

        protected b(String str, boolean z, boolean z2) {
            this.f1589a = str;
            this.f1590b = z;
            this.f1591c = z2;
        }

        /* access modifiers changed from: package-private */
        public abstract void a(c.a.a.d.b bVar, Object obj);

        /* access modifiers changed from: package-private */
        public abstract void a(d dVar, Object obj);

        /* access modifiers changed from: package-private */
        public abstract boolean a(Object obj);
    }

    public C0133p(o oVar, AbstractC0155j jVar, q qVar) {
        this.f1584a = oVar;
        this.f1585b = jVar;
        this.f1586c = qVar;
    }

    /* access modifiers changed from: private */
    public J<?> a(p pVar, Field field, c.a.a.c.a<?> aVar) {
        J<?> a2;
        c.a.a.a.b bVar = (c.a.a.a.b) field.getAnnotation(c.a.a.a.b.class);
        return (bVar == null || (a2 = C0123f.a(this.f1584a, pVar, aVar, bVar)) == null) ? pVar.a((c.a.a.c.a) aVar) : a2;
    }

    private b a(p pVar, Field field, String str, c.a.a.c.a<?> aVar, boolean z, boolean z2) {
        return new C0132o(this, str, z, z2, pVar, field, aVar, y.a((Type) aVar.a()));
    }

    static String a(AbstractC0155j jVar, Field field) {
        c.a.a.a.c cVar = (c.a.a.a.c) field.getAnnotation(c.a.a.a.c.class);
        return cVar == null ? jVar.a(field) : cVar.value();
    }

    private String a(Field field) {
        return a(this.f1585b, field);
    }

    private Map<String, b> a(p pVar, c.a.a.c.a<?> aVar, Class<?> cls) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type b2 = aVar.b();
        c.a.a.c.a<?> aVar2 = aVar;
        Class<?> cls2 = cls;
        while (cls2 != Object.class) {
            Field[] declaredFields = cls2.getDeclaredFields();
            for (Field field : declaredFields) {
                boolean a2 = a(field, true);
                boolean a3 = a(field, false);
                if (a2 || a3) {
                    field.setAccessible(true);
                    b a4 = a(pVar, field, a(field), c.a.a.c.a.a(C0143b.a(aVar2.b(), cls2, field.getGenericType())), a2, a3);
                    b bVar = (b) linkedHashMap.put(a4.f1589a, a4);
                    if (bVar != null) {
                        throw new IllegalArgumentException(b2 + " declares multiple JSON fields named " + bVar.f1589a);
                    }
                }
            }
            aVar2 = c.a.a.c.a.a(C0143b.a(aVar2.b(), cls2, cls2.getGenericSuperclass()));
            cls2 = aVar2.a();
        }
        return linkedHashMap;
    }

    static boolean a(Field field, boolean z, q qVar) {
        return !qVar.a(field.getType(), z) && !qVar.a(field, z);
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, c.a.a.c.a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        if (!Object.class.isAssignableFrom(a2)) {
            return null;
        }
        return new a(this.f1584a.a(aVar), a(pVar, (c.a.a.c.a<?>) aVar, (Class<?>) a2), null);
    }

    public boolean a(Field field, boolean z) {
        return a(field, z, this.f1586c);
    }
}
