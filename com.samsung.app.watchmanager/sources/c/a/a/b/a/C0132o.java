package c.a.a.b.a;

import c.a.a.J;
import c.a.a.b.a.C0133p;
import c.a.a.c.a;
import c.a.a.d.b;
import c.a.a.d.d;
import c.a.a.p;
import java.lang.reflect.Field;

/* access modifiers changed from: package-private */
/* renamed from: c.a.a.b.a.o  reason: case insensitive filesystem */
public class C0132o extends C0133p.b {

    /* renamed from: d  reason: collision with root package name */
    final J<?> f1583d = this.i.a(this.e, this.f, this.g);
    final /* synthetic */ p e;
    final /* synthetic */ Field f;
    final /* synthetic */ a g;
    final /* synthetic */ boolean h;
    final /* synthetic */ C0133p i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0132o(C0133p pVar, String str, boolean z, boolean z2, p pVar2, Field field, a aVar, boolean z3) {
        super(str, z, z2);
        this.i = pVar;
        this.e = pVar2;
        this.f = field;
        this.g = aVar;
        this.h = z3;
    }

    /* access modifiers changed from: package-private */
    @Override // c.a.a.b.a.C0133p.b
    public void a(b bVar, Object obj) {
        Object a2 = this.f1583d.a(bVar);
        if (a2 != null || !this.h) {
            this.f.set(obj, a2);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // c.a.a.b.a.C0133p.b
    public void a(d dVar, Object obj) {
        new C0137u(this.e, this.f1583d, this.g.b()).a(dVar, this.f.get(obj));
    }

    @Override // c.a.a.b.a.C0133p.b
    public boolean a(Object obj) {
        return this.f1590b && this.f.get(obj) != obj;
    }
}
