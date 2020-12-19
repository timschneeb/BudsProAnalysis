package androidx.appcompat.view.menu;

import android.content.DialogInterface;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.k;
import androidx.appcompat.view.menu.v;
import b.a.g;
import com.samsung.android.app.twatchmanager.util.GlobalConst;

class m implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, v.a {

    /* renamed from: a  reason: collision with root package name */
    private l f257a;

    /* renamed from: b  reason: collision with root package name */
    private k f258b;

    /* renamed from: c  reason: collision with root package name */
    j f259c;

    /* renamed from: d  reason: collision with root package name */
    private v.a f260d;

    public m(l lVar) {
        this.f257a = lVar;
    }

    public void a() {
        k kVar = this.f258b;
        if (kVar != null) {
            kVar.dismiss();
        }
    }

    public void a(IBinder iBinder) {
        l lVar = this.f257a;
        k.a aVar = new k.a(lVar.e());
        this.f259c = new j(aVar.b(), g.abc_list_menu_item_layout);
        this.f259c.a(this);
        this.f257a.a(this.f259c);
        aVar.a(this.f259c.b(), this);
        View i = lVar.i();
        if (i != null) {
            aVar.a(i);
        } else {
            aVar.a(lVar.g());
            aVar.a(lVar.h());
        }
        aVar.a(this);
        this.f258b = aVar.a();
        this.f258b.setOnDismissListener(this);
        WindowManager.LayoutParams attributes = this.f258b.getWindow().getAttributes();
        attributes.type = GlobalConst.LAUNCH_MODE_DEVICE_LIST;
        if (iBinder != null) {
            attributes.token = iBinder;
        }
        attributes.flags |= 131072;
        this.f258b.show();
    }

    @Override // androidx.appcompat.view.menu.v.a
    public void a(l lVar, boolean z) {
        if (z || lVar == this.f257a) {
            a();
        }
        v.a aVar = this.f260d;
        if (aVar != null) {
            aVar.a(lVar, z);
        }
    }

    @Override // androidx.appcompat.view.menu.v.a
    public boolean a(l lVar) {
        v.a aVar = this.f260d;
        if (aVar != null) {
            return aVar.a(lVar);
        }
        return false;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.f257a.a((p) this.f259c.b().getItem(i), 0);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        this.f259c.a(this.f257a, true);
    }

    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        Window window;
        View decorView;
        KeyEvent.DispatcherState keyDispatcherState;
        View decorView2;
        KeyEvent.DispatcherState keyDispatcherState2;
        if (i == 82 || i == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                Window window2 = this.f258b.getWindow();
                if (!(window2 == null || (decorView2 = window2.getDecorView()) == null || (keyDispatcherState2 = decorView2.getKeyDispatcherState()) == null)) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (window = this.f258b.getWindow()) != null && (decorView = window.getDecorView()) != null && (keyDispatcherState = decorView.getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent)) {
                this.f257a.a(true);
                dialogInterface.dismiss();
                return true;
            }
        }
        return this.f257a.performShortcut(i, keyEvent, 0);
    }
}
