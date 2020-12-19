package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import b.e.g.u;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class l implements b.e.b.a.a {

    /* renamed from: a  reason: collision with root package name */
    private static final int[] f253a = {1, 4, 5, 3, 2, 0};
    private boolean A;

    /* renamed from: b  reason: collision with root package name */
    private final Context f254b;

    /* renamed from: c  reason: collision with root package name */
    private final Resources f255c;

    /* renamed from: d  reason: collision with root package name */
    private boolean f256d;
    private boolean e;
    private a f;
    private ArrayList<p> g;
    private ArrayList<p> h;
    private boolean i;
    private ArrayList<p> j;
    private ArrayList<p> k;
    private boolean l;
    private int m = 0;
    private ContextMenu.ContextMenuInfo n;
    CharSequence o;
    Drawable p;
    View q;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v = false;
    private ArrayList<p> w = new ArrayList<>();
    private CopyOnWriteArrayList<WeakReference<v>> x = new CopyOnWriteArrayList<>();
    private p y;
    private boolean z = false;

    public interface a {
        void a(l lVar);

        boolean a(l lVar, MenuItem menuItem);
    }

    public interface b {
        boolean a(p pVar);
    }

    public l(Context context) {
        this.f254b = context;
        this.f255c = context.getResources();
        this.g = new ArrayList<>();
        this.h = new ArrayList<>();
        this.i = true;
        this.j = new ArrayList<>();
        this.k = new ArrayList<>();
        this.l = true;
        e(true);
    }

    private static int a(ArrayList<p> arrayList, int i2) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).c() <= i2) {
                return size + 1;
            }
        }
        return 0;
    }

    private p a(int i2, int i3, int i4, int i5, CharSequence charSequence, int i6) {
        return new p(this, i2, i3, i4, i5, charSequence, i6);
    }

    private void a(int i2, CharSequence charSequence, int i3, Drawable drawable, View view) {
        Resources l2 = l();
        if (view != null) {
            this.q = view;
            this.o = null;
            this.p = null;
        } else {
            if (i2 > 0) {
                this.o = l2.getText(i2);
            } else if (charSequence != null) {
                this.o = charSequence;
            }
            if (i3 > 0) {
                this.p = androidx.core.content.a.c(e(), i3);
            } else if (drawable != null) {
                this.p = drawable;
            }
            this.q = null;
        }
        b(false);
    }

    private void a(int i2, boolean z2) {
        if (i2 >= 0 && i2 < this.g.size()) {
            this.g.remove(i2);
            if (z2) {
                b(true);
            }
        }
    }

    private boolean a(D d2, v vVar) {
        boolean z2 = false;
        if (this.x.isEmpty()) {
            return false;
        }
        if (vVar != null) {
            z2 = vVar.a(d2);
        }
        Iterator<WeakReference<v>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<v> next = it.next();
            v vVar2 = next.get();
            if (vVar2 == null) {
                this.x.remove(next);
            } else if (!z2) {
                z2 = vVar2.a(d2);
            }
        }
        return z2;
    }

    private void d(boolean z2) {
        if (!this.x.isEmpty()) {
            s();
            Iterator<WeakReference<v>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<v> next = it.next();
                v vVar = next.get();
                if (vVar == null) {
                    this.x.remove(next);
                } else {
                    vVar.a(z2);
                }
            }
            r();
        }
    }

    private void e(boolean z2) {
        boolean z3 = true;
        if (!z2 || this.f255c.getConfiguration().keyboard == 1 || !u.c(ViewConfiguration.get(this.f254b), this.f254b)) {
            z3 = false;
        }
        this.e = z3;
    }

    private static int f(int i2) {
        int i3 = (-65536 & i2) >> 16;
        if (i3 >= 0) {
            int[] iArr = f253a;
            if (i3 < iArr.length) {
                return (i2 & 65535) | (iArr[i3] << 16);
            }
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    public int a(int i2) {
        return a(i2, 0);
    }

    public int a(int i2, int i3) {
        int size = size();
        if (i3 < 0) {
            i3 = 0;
        }
        while (i3 < size) {
            if (this.g.get(i3).getGroupId() == i2) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public MenuItem a(int i2, int i3, int i4, CharSequence charSequence) {
        int f2 = f(i4);
        p a2 = a(i2, i3, i4, f2, charSequence, this.m);
        ContextMenu.ContextMenuInfo contextMenuInfo = this.n;
        if (contextMenuInfo != null) {
            a2.a(contextMenuInfo);
        }
        ArrayList<p> arrayList = this.g;
        arrayList.add(a(arrayList, f2), a2);
        b(true);
        return a2;
    }

    /* access modifiers changed from: protected */
    public l a(Drawable drawable) {
        a(0, null, 0, drawable, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public l a(View view) {
        a(0, null, 0, null, view);
        return this;
    }

    /* access modifiers changed from: protected */
    public l a(CharSequence charSequence) {
        a(0, charSequence, 0, null, null);
        return this;
    }

    /* access modifiers changed from: package-private */
    public p a(int i2, KeyEvent keyEvent) {
        ArrayList<p> arrayList = this.w;
        arrayList.clear();
        a(arrayList, i2, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean p2 = p();
        for (int i3 = 0; i3 < size; i3++) {
            p pVar = arrayList.get(i3);
            char alphabeticShortcut = p2 ? pVar.getAlphabeticShortcut() : pVar.getNumericShortcut();
            if ((alphabeticShortcut == keyData.meta[0] && (metaState & 2) == 0) || ((alphabeticShortcut == keyData.meta[2] && (metaState & 2) != 0) || (p2 && alphabeticShortcut == '\b' && i2 == 67))) {
                return pVar;
            }
        }
        return null;
    }

    public void a() {
        a aVar = this.f;
        if (aVar != null) {
            aVar.a(this);
        }
    }

    public void a(Bundle bundle) {
        MenuItem findItem;
        if (bundle != null) {
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(d());
            int size = size();
            for (int i2 = 0; i2 < size; i2++) {
                MenuItem item = getItem(i2);
                View actionView = item.getActionView();
                if (!(actionView == null || actionView.getId() == -1)) {
                    actionView.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((D) item.getSubMenu()).a(bundle);
                }
            }
            int i3 = bundle.getInt("android:menu:expandedactionview");
            if (i3 > 0 && (findItem = findItem(i3)) != null) {
                findItem.expandActionView();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.g.size();
        s();
        for (int i2 = 0; i2 < size; i2++) {
            p pVar = this.g.get(i2);
            if (pVar.getGroupId() == groupId && pVar.i() && pVar.isCheckable()) {
                pVar.b(pVar == menuItem);
            }
        }
        r();
    }

    public void a(a aVar) {
        this.f = aVar;
    }

    public void a(v vVar) {
        a(vVar, this.f254b);
    }

    public void a(v vVar, Context context) {
        this.x.add(new WeakReference<>(vVar));
        vVar.a(context, this);
        this.l = true;
    }

    /* access modifiers changed from: package-private */
    public void a(List<p> list, int i2, KeyEvent keyEvent) {
        boolean p2 = p();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i2 == 67) {
            int size = this.g.size();
            for (int i3 = 0; i3 < size; i3++) {
                p pVar = this.g.get(i3);
                if (pVar.hasSubMenu()) {
                    ((l) pVar.getSubMenu()).a(list, i2, keyEvent);
                }
                char alphabeticShortcut = p2 ? pVar.getAlphabeticShortcut() : pVar.getNumericShortcut();
                if (((modifiers & 69647) == ((p2 ? pVar.getAlphabeticModifiers() : pVar.getNumericModifiers()) & 69647)) && alphabeticShortcut != 0) {
                    char[] cArr = keyData.meta;
                    if ((alphabeticShortcut == cArr[0] || alphabeticShortcut == cArr[2] || (p2 && alphabeticShortcut == '\b' && i2 == 67)) && pVar.isEnabled()) {
                        list.add(pVar);
                    }
                }
            }
        }
    }

    public final void a(boolean z2) {
        if (!this.v) {
            this.v = true;
            Iterator<WeakReference<v>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<v> next = it.next();
                v vVar = next.get();
                if (vVar == null) {
                    this.x.remove(next);
                } else {
                    vVar.a(this, z2);
                }
            }
            this.v = false;
        }
    }

    public boolean a(MenuItem menuItem, int i2) {
        return a(menuItem, (v) null, i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
        if (r1 != false) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003c, code lost:
        if ((r9 & 1) == 0) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0068, code lost:
        if (r1 == false) goto L_0x002d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.view.MenuItem r7, androidx.appcompat.view.menu.v r8, int r9) {
        /*
        // Method dump skipped, instructions count: 109
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.l.a(android.view.MenuItem, androidx.appcompat.view.menu.v, int):boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean a(l lVar, MenuItem menuItem) {
        a aVar = this.f;
        return aVar != null && aVar.a(lVar, menuItem);
    }

    public boolean a(p pVar) {
        boolean z2 = false;
        if (!this.x.isEmpty() && this.y == pVar) {
            s();
            Iterator<WeakReference<v>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<v> next = it.next();
                v vVar = next.get();
                if (vVar == null) {
                    this.x.remove(next);
                } else {
                    z2 = vVar.a(this, pVar);
                    if (z2) {
                        break;
                    }
                }
            }
            r();
            if (z2) {
                this.y = null;
            }
        }
        return z2;
    }

    @Override // android.view.Menu
    public MenuItem add(int i2) {
        return a(0, 0, 0, this.f255c.getString(i2));
    }

    @Override // android.view.Menu
    public MenuItem add(int i2, int i3, int i4, int i5) {
        return a(i2, i3, i4, this.f255c.getString(i5));
    }

    @Override // android.view.Menu
    public MenuItem add(int i2, int i3, int i4, CharSequence charSequence) {
        return a(i2, i3, i4, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return a(0, 0, 0, charSequence);
    }

    public int addIntentOptions(int i2, int i3, int i4, ComponentName componentName, Intent[] intentArr, Intent intent, int i5, MenuItem[] menuItemArr) {
        int i6;
        PackageManager packageManager = this.f254b.getPackageManager();
        List<ResolveInfo> queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i5 & 1) == 0) {
            removeGroup(i2);
        }
        for (int i7 = 0; i7 < size; i7++) {
            ResolveInfo resolveInfo = queryIntentActivityOptions.get(i7);
            int i8 = resolveInfo.specificIndex;
            Intent intent2 = new Intent(i8 < 0 ? intent : intentArr[i8]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = add(i2, i3, i4, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && (i6 = resolveInfo.specificIndex) >= 0) {
                menuItemArr[i6] = intent3;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i2) {
        return addSubMenu(0, 0, 0, this.f255c.getString(i2));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i2, int i3, int i4, int i5) {
        return addSubMenu(i2, i3, i4, this.f255c.getString(i5));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i2, int i3, int i4, CharSequence charSequence) {
        p pVar = (p) a(i2, i3, i4, charSequence);
        D d2 = new D(this.f254b, this, pVar);
        pVar.a(d2);
        return d2;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    public int b(int i2) {
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            if (this.g.get(i3).getItemId() == i2) {
                return i3;
            }
        }
        return -1;
    }

    public void b() {
        ArrayList<p> n2 = n();
        if (this.l) {
            Iterator<WeakReference<v>> it = this.x.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                WeakReference<v> next = it.next();
                v vVar = next.get();
                if (vVar == null) {
                    this.x.remove(next);
                } else {
                    z2 |= vVar.a();
                }
            }
            if (z2) {
                this.j.clear();
                this.k.clear();
                int size = n2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    p pVar = n2.get(i2);
                    (pVar.h() ? this.j : this.k).add(pVar);
                }
            } else {
                this.j.clear();
                this.k.clear();
                this.k.addAll(n());
            }
            this.l = false;
        }
    }

    public void b(Bundle bundle) {
        int size = size();
        SparseArray<? extends Parcelable> sparseArray = null;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = getItem(i2);
            View actionView = item.getActionView();
            if (!(actionView == null || actionView.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((D) item.getSubMenu()).b(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(d(), sparseArray);
        }
    }

    public void b(v vVar) {
        Iterator<WeakReference<v>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<v> next = it.next();
            v vVar2 = next.get();
            if (vVar2 == null || vVar2 == vVar) {
                this.x.remove(next);
            }
        }
    }

    public void b(boolean z2) {
        if (!this.r) {
            if (z2) {
                this.i = true;
                this.l = true;
            }
            d(z2);
            return;
        }
        this.s = true;
        if (z2) {
            this.t = true;
        }
    }

    public boolean b(p pVar) {
        boolean z2 = false;
        if (this.x.isEmpty()) {
            return false;
        }
        s();
        Iterator<WeakReference<v>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<v> next = it.next();
            v vVar = next.get();
            if (vVar == null) {
                this.x.remove(next);
            } else {
                z2 = vVar.b(this, pVar);
                if (z2) {
                    break;
                }
            }
        }
        r();
        if (z2) {
            this.y = pVar;
        }
        return z2;
    }

    public l c(int i2) {
        this.m = i2;
        return this;
    }

    public ArrayList<p> c() {
        b();
        return this.j;
    }

    /* access modifiers changed from: package-private */
    public void c(p pVar) {
        this.l = true;
        b(true);
    }

    public void c(boolean z2) {
        this.A = z2;
    }

    public void clear() {
        p pVar = this.y;
        if (pVar != null) {
            a(pVar);
        }
        this.g.clear();
        b(true);
    }

    public void clearHeader() {
        this.p = null;
        this.o = null;
        this.q = null;
        b(false);
    }

    public void close() {
        a(true);
    }

    /* access modifiers changed from: protected */
    public l d(int i2) {
        a(0, null, i2, null, null);
        return this;
    }

    /* access modifiers changed from: protected */
    public String d() {
        return "android:menu:actionviewstates";
    }

    /* access modifiers changed from: package-private */
    public void d(p pVar) {
        this.i = true;
        b(true);
    }

    public Context e() {
        return this.f254b;
    }

    /* access modifiers changed from: protected */
    public l e(int i2) {
        a(i2, null, 0, null, null);
        return this;
    }

    public p f() {
        return this.y;
    }

    public MenuItem findItem(int i2) {
        MenuItem findItem;
        int size = size();
        for (int i3 = 0; i3 < size; i3++) {
            p pVar = this.g.get(i3);
            if (pVar.getItemId() == i2) {
                return pVar;
            }
            if (pVar.hasSubMenu() && (findItem = pVar.getSubMenu().findItem(i2)) != null) {
                return findItem;
            }
        }
        return null;
    }

    public Drawable g() {
        return this.p;
    }

    public MenuItem getItem(int i2) {
        return this.g.get(i2);
    }

    public CharSequence h() {
        return this.o;
    }

    public boolean hasVisibleItems() {
        if (this.A) {
            return true;
        }
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.g.get(i2).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public View i() {
        return this.q;
    }

    public boolean isShortcutKey(int i2, KeyEvent keyEvent) {
        return a(i2, keyEvent) != null;
    }

    public ArrayList<p> j() {
        b();
        return this.k;
    }

    /* access modifiers changed from: package-private */
    public boolean k() {
        return this.u;
    }

    /* access modifiers changed from: package-private */
    public Resources l() {
        return this.f255c;
    }

    public l m() {
        return this;
    }

    public ArrayList<p> n() {
        if (!this.i) {
            return this.h;
        }
        this.h.clear();
        int size = this.g.size();
        for (int i2 = 0; i2 < size; i2++) {
            p pVar = this.g.get(i2);
            if (pVar.isVisible()) {
                this.h.add(pVar);
            }
        }
        this.i = false;
        this.l = true;
        return this.h;
    }

    public boolean o() {
        return this.z;
    }

    /* access modifiers changed from: package-private */
    public boolean p() {
        return this.f256d;
    }

    public boolean performIdentifierAction(int i2, int i3) {
        return a(findItem(i2), i3);
    }

    public boolean performShortcut(int i2, KeyEvent keyEvent, int i3) {
        p a2 = a(i2, keyEvent);
        boolean a3 = a2 != null ? a(a2, i3) : false;
        if ((i3 & 2) != 0) {
            a(true);
        }
        return a3;
    }

    public boolean q() {
        return this.e;
    }

    public void r() {
        this.r = false;
        if (this.s) {
            this.s = false;
            b(this.t);
        }
    }

    public void removeGroup(int i2) {
        int a2 = a(i2);
        if (a2 >= 0) {
            int size = this.g.size() - a2;
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                if (i3 >= size || this.g.get(a2).getGroupId() != i2) {
                    b(true);
                } else {
                    a(a2, false);
                    i3 = i4;
                }
            }
            b(true);
        }
    }

    public void removeItem(int i2) {
        a(b(i2), true);
    }

    public void s() {
        if (!this.r) {
            this.r = true;
            this.s = false;
            this.t = false;
        }
    }

    public void setGroupCheckable(int i2, boolean z2, boolean z3) {
        int size = this.g.size();
        for (int i3 = 0; i3 < size; i3++) {
            p pVar = this.g.get(i3);
            if (pVar.getGroupId() == i2) {
                pVar.c(z3);
                pVar.setCheckable(z2);
            }
        }
    }

    public void setGroupDividerEnabled(boolean z2) {
        this.z = z2;
    }

    public void setGroupEnabled(int i2, boolean z2) {
        int size = this.g.size();
        for (int i3 = 0; i3 < size; i3++) {
            p pVar = this.g.get(i3);
            if (pVar.getGroupId() == i2) {
                pVar.setEnabled(z2);
            }
        }
    }

    public void setGroupVisible(int i2, boolean z2) {
        int size = this.g.size();
        boolean z3 = false;
        for (int i3 = 0; i3 < size; i3++) {
            p pVar = this.g.get(i3);
            if (pVar.getGroupId() == i2 && pVar.e(z2)) {
                z3 = true;
            }
        }
        if (z3) {
            b(true);
        }
    }

    public void setQwertyMode(boolean z2) {
        this.f256d = z2;
        b(false);
    }

    public int size() {
        return this.g.size();
    }
}
