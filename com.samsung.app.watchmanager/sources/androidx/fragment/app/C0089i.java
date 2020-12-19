package androidx.fragment.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/* renamed from: androidx.fragment.app.i  reason: case insensitive filesystem */
public class C0089i {

    /* renamed from: a  reason: collision with root package name */
    private final AbstractC0090j<?> f766a;

    private C0089i(AbstractC0090j<?> jVar) {
        this.f766a = jVar;
    }

    public static C0089i a(AbstractC0090j<?> jVar) {
        return new C0089i(jVar);
    }

    public View a(View view, String str, Context context, AttributeSet attributeSet) {
        return this.f766a.e.onCreateView(view, str, context, attributeSet);
    }

    public Fragment a(String str) {
        return this.f766a.e.b(str);
    }

    public void a() {
        this.f766a.e.f();
    }

    public void a(Configuration configuration) {
        this.f766a.e.a(configuration);
    }

    public void a(Parcelable parcelable, s sVar) {
        this.f766a.e.a(parcelable, sVar);
    }

    public void a(Menu menu) {
        this.f766a.e.a(menu);
    }

    public void a(Fragment fragment) {
        AbstractC0090j<?> jVar = this.f766a;
        jVar.e.a(jVar, jVar, fragment);
    }

    public void a(boolean z) {
        this.f766a.e.a(z);
    }

    public boolean a(Menu menu, MenuInflater menuInflater) {
        return this.f766a.e.a(menu, menuInflater);
    }

    public boolean a(MenuItem menuItem) {
        return this.f766a.e.a(menuItem);
    }

    public void b() {
        this.f766a.e.g();
    }

    public void b(boolean z) {
        this.f766a.e.b(z);
    }

    public boolean b(Menu menu) {
        return this.f766a.e.b(menu);
    }

    public boolean b(MenuItem menuItem) {
        return this.f766a.e.b(menuItem);
    }

    public void c() {
        this.f766a.e.h();
    }

    public void d() {
        this.f766a.e.j();
    }

    public void e() {
        this.f766a.e.k();
    }

    public void f() {
        this.f766a.e.l();
    }

    public void g() {
        this.f766a.e.m();
    }

    public void h() {
        this.f766a.e.n();
    }

    public boolean i() {
        return this.f766a.e.p();
    }

    public AbstractC0091k j() {
        return this.f766a.d();
    }

    public void k() {
        this.f766a.e.s();
    }

    public s l() {
        return this.f766a.e.u();
    }

    public Parcelable m() {
        return this.f766a.e.v();
    }
}
