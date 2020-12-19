package c.a.a.b.a;

import c.a.a.J;
import c.a.a.K;
import c.a.a.b.C0143b;
import c.a.a.b.o;
import c.a.a.b.x;
import c.a.a.d.b;
import c.a.a.d.c;
import c.a.a.d.d;
import c.a.a.p;
import java.lang.reflect.Type;
import java.util.Collection;

/* renamed from: c.a.a.b.a.c  reason: case insensitive filesystem */
public final class C0120c implements K {

    /* renamed from: a  reason: collision with root package name */
    private final o f1560a;

    /* renamed from: c.a.a.b.a.c$a */
    private static final class a<E> extends J<Collection<E>> {

        /* renamed from: a  reason: collision with root package name */
        private final J<E> f1561a;

        /* renamed from: b  reason: collision with root package name */
        private final x<? extends Collection<E>> f1562b;

        public a(p pVar, Type type, J<E> j, x<? extends Collection<E>> xVar) {
            this.f1561a = new C0137u(pVar, j, type);
            this.f1562b = xVar;
        }

        @Override // c.a.a.J
        public Collection<E> a(b bVar) {
            if (bVar.o() == c.NULL) {
                bVar.m();
                return null;
            }
            Collection<E> collection = (Collection) this.f1562b.a();
            bVar.a();
            while (bVar.f()) {
                collection.add(this.f1561a.a(bVar));
            }
            bVar.c();
            return collection;
        }

        @Override // c.a.a.J
        public /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
            a(dVar, (Collection) ((Collection) obj));
        }

        public void a(d dVar, Collection<E> collection) {
            if (collection == null) {
                dVar.h();
                return;
            }
            dVar.a();
            for (E e : collection) {
                this.f1561a.a(dVar, e);
            }
            dVar.c();
        }
    }

    public C0120c(o oVar) {
        this.f1560a = oVar;
    }

    @Override // c.a.a.K
    public <T> J<T> a(p pVar, c.a.a.c.a<T> aVar) {
        Type b2 = aVar.b();
        Class<? super T> a2 = aVar.a();
        if (!Collection.class.isAssignableFrom(a2)) {
            return null;
        }
        Type a3 = C0143b.a(b2, (Class<?>) a2);
        return new a(pVar, a3, pVar.a((c.a.a.c.a) c.a.a.c.a.a(a3)), this.f1560a.a(aVar));
    }
}
