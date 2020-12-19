package b.a.d;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.x;
import b.a.d.b;
import b.c.i;
import java.util.ArrayList;

public class f extends ActionMode {

    /* renamed from: a  reason: collision with root package name */
    final Context f1238a;

    /* renamed from: b  reason: collision with root package name */
    final b f1239b;

    public static class a implements b.a {

        /* renamed from: a  reason: collision with root package name */
        final ActionMode.Callback f1240a;

        /* renamed from: b  reason: collision with root package name */
        final Context f1241b;

        /* renamed from: c  reason: collision with root package name */
        final ArrayList<f> f1242c = new ArrayList<>();

        /* renamed from: d  reason: collision with root package name */
        final i<Menu, Menu> f1243d = new i<>();

        public a(Context context, ActionMode.Callback callback) {
            this.f1241b = context;
            this.f1240a = callback;
        }

        private Menu a(Menu menu) {
            Menu menu2 = this.f1243d.get(menu);
            if (menu2 != null) {
                return menu2;
            }
            Menu a2 = x.a(this.f1241b, (b.e.b.a.a) menu);
            this.f1243d.put(menu, a2);
            return a2;
        }

        @Override // b.a.d.b.a
        public void a(b bVar) {
            this.f1240a.onDestroyActionMode(b(bVar));
        }

        @Override // b.a.d.b.a
        public boolean a(b bVar, Menu menu) {
            return this.f1240a.onCreateActionMode(b(bVar), a(menu));
        }

        @Override // b.a.d.b.a
        public boolean a(b bVar, MenuItem menuItem) {
            return this.f1240a.onActionItemClicked(b(bVar), x.a(this.f1241b, (b.e.b.a.b) menuItem));
        }

        public ActionMode b(b bVar) {
            int size = this.f1242c.size();
            for (int i = 0; i < size; i++) {
                f fVar = this.f1242c.get(i);
                if (fVar != null && fVar.f1239b == bVar) {
                    return fVar;
                }
            }
            f fVar2 = new f(this.f1241b, bVar);
            this.f1242c.add(fVar2);
            return fVar2;
        }

        @Override // b.a.d.b.a
        public boolean b(b bVar, Menu menu) {
            return this.f1240a.onPrepareActionMode(b(bVar), a(menu));
        }
    }

    public f(Context context, b bVar) {
        this.f1238a = context;
        this.f1239b = bVar;
    }

    public void finish() {
        this.f1239b.a();
    }

    public View getCustomView() {
        return this.f1239b.b();
    }

    public Menu getMenu() {
        return x.a(this.f1238a, (b.e.b.a.a) this.f1239b.c());
    }

    public MenuInflater getMenuInflater() {
        return this.f1239b.d();
    }

    public CharSequence getSubtitle() {
        return this.f1239b.e();
    }

    public Object getTag() {
        return this.f1239b.f();
    }

    public CharSequence getTitle() {
        return this.f1239b.g();
    }

    public boolean getTitleOptionalHint() {
        return this.f1239b.h();
    }

    public void invalidate() {
        this.f1239b.i();
    }

    public boolean isTitleOptional() {
        return this.f1239b.j();
    }

    public void setCustomView(View view) {
        this.f1239b.a(view);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
        this.f1239b.a(i);
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        this.f1239b.a(charSequence);
    }

    public void setTag(Object obj) {
        this.f1239b.a(obj);
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
        this.f1239b.b(i);
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        this.f1239b.b(charSequence);
    }

    public void setTitleOptionalHint(boolean z) {
        this.f1239b.a(z);
    }
}
