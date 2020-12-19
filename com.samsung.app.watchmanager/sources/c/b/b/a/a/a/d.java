package c.b.b.a.a.a;

import android.content.SharedPreferences;
import c.b.b.a.a.a.c.a;

/* access modifiers changed from: package-private */
public class d extends a {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ SharedPreferences f1728a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f1729b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f1730c;

    d(e eVar, SharedPreferences sharedPreferences, String str) {
        this.f1730c = eVar;
        this.f1728a = sharedPreferences;
        this.f1729b = str;
    }

    @Override // c.b.b.a.a.a.c.a
    public void a(int i, String str, String str2, String str3) {
    }

    @Override // c.b.b.a.a.a.c.a
    public void b(int i, String str, String str2, String str3) {
        this.f1728a.edit().remove(this.f1729b).apply();
    }
}
