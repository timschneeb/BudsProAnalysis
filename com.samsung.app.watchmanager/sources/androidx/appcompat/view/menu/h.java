package androidx.appcompat.view.menu;

import android.os.SystemClock;
import android.view.MenuItem;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.widget.K;

/* access modifiers changed from: package-private */
public class h implements K {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ i f236a;

    h(i iVar) {
        this.f236a = iVar;
    }

    @Override // androidx.appcompat.widget.K
    public void a(l lVar, MenuItem menuItem) {
        i.a aVar = null;
        this.f236a.h.removeCallbacksAndMessages(null);
        int size = this.f236a.j.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (lVar == this.f236a.j.get(i).f241b) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1) {
            int i2 = i + 1;
            if (i2 < this.f236a.j.size()) {
                aVar = this.f236a.j.get(i2);
            }
            this.f236a.h.postAtTime(new g(this, aVar, menuItem, lVar), lVar, SystemClock.uptimeMillis() + 200);
        }
    }

    @Override // androidx.appcompat.widget.K
    public void b(l lVar, MenuItem menuItem) {
        this.f236a.h.removeCallbacksAndMessages(lVar);
    }
}
