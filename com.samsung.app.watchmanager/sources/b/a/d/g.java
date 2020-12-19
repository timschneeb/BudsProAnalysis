package b.a.d;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.view.menu.p;
import androidx.appcompat.view.menu.q;
import androidx.appcompat.widget.E;
import b.a.j;
import b.e.g.AbstractC0112b;
import com.samsung.android.app.twatchmanager.util.ResourceRulesParser;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class g extends MenuInflater {

    /* renamed from: a  reason: collision with root package name */
    static final Class<?>[] f1244a = {Context.class};

    /* renamed from: b  reason: collision with root package name */
    static final Class<?>[] f1245b = f1244a;

    /* renamed from: c  reason: collision with root package name */
    final Object[] f1246c;

    /* renamed from: d  reason: collision with root package name */
    final Object[] f1247d = this.f1246c;
    Context e;
    private Object f;

    /* access modifiers changed from: private */
    public static class a implements MenuItem.OnMenuItemClickListener {

        /* renamed from: a  reason: collision with root package name */
        private static final Class<?>[] f1248a = {MenuItem.class};

        /* renamed from: b  reason: collision with root package name */
        private Object f1249b;

        /* renamed from: c  reason: collision with root package name */
        private Method f1250c;

        public a(Object obj, String str) {
            this.f1249b = obj;
            Class<?> cls = obj.getClass();
            try {
                this.f1250c = cls.getMethod(str, f1248a);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.f1250c.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.f1250c.invoke(this.f1249b, menuItem)).booleanValue();
                }
                this.f1250c.invoke(this.f1249b, menuItem);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public class b {
        AbstractC0112b A;
        private CharSequence B;
        private CharSequence C;
        private ColorStateList D = null;
        private PorterDuff.Mode E = null;

        /* renamed from: a  reason: collision with root package name */
        private Menu f1251a;

        /* renamed from: b  reason: collision with root package name */
        private int f1252b;

        /* renamed from: c  reason: collision with root package name */
        private int f1253c;

        /* renamed from: d  reason: collision with root package name */
        private int f1254d;
        private int e;
        private boolean f;
        private boolean g;
        private boolean h;
        private int i;
        private int j;
        private CharSequence k;
        private CharSequence l;
        private int m;
        private char n;
        private int o;
        private char p;
        private int q;
        private int r;
        private boolean s;
        private boolean t;
        private boolean u;
        private int v;
        private int w;
        private String x;
        private String y;
        private String z;

        public b(Menu menu) {
            this.f1251a = menu;
            d();
        }

        private char a(String str) {
            if (str == null) {
                return 0;
            }
            return str.charAt(0);
        }

        private <T> T a(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = g.this.e.getClassLoader().loadClass(str).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e2) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e2);
                return null;
            }
        }

        private void a(MenuItem menuItem) {
            boolean z2 = false;
            menuItem.setChecked(this.s).setVisible(this.t).setEnabled(this.u).setCheckable(this.r >= 1).setTitleCondensed(this.l).setIcon(this.m);
            int i2 = this.v;
            if (i2 >= 0) {
                menuItem.setShowAsAction(i2);
            }
            if (this.z != null) {
                if (!g.this.e.isRestricted()) {
                    menuItem.setOnMenuItemClickListener(new a(g.this.a(), this.z));
                } else {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
            }
            boolean z3 = menuItem instanceof p;
            if (z3) {
                p pVar = (p) menuItem;
            }
            if (this.r >= 2) {
                if (z3) {
                    ((p) menuItem).c(true);
                } else if (menuItem instanceof q) {
                    ((q) menuItem).a(true);
                }
            }
            String str = this.x;
            if (str != null) {
                menuItem.setActionView((View) a(str, g.f1244a, g.this.f1246c));
                z2 = true;
            }
            int i3 = this.w;
            if (i3 > 0) {
                if (!z2) {
                    menuItem.setActionView(i3);
                } else {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            AbstractC0112b bVar = this.A;
            if (bVar != null) {
                b.e.g.g.a(menuItem, bVar);
            }
            b.e.g.g.a(menuItem, this.B);
            b.e.g.g.b(menuItem, this.C);
            b.e.g.g.a(menuItem, this.n, this.o);
            b.e.g.g.b(menuItem, this.p, this.q);
            PorterDuff.Mode mode = this.E;
            if (mode != null) {
                b.e.g.g.a(menuItem, mode);
            }
            ColorStateList colorStateList = this.D;
            if (colorStateList != null) {
                b.e.g.g.a(menuItem, colorStateList);
            }
        }

        public void a() {
            this.h = true;
            a(this.f1251a.add(this.f1252b, this.i, this.j, this.k));
        }

        public void a(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = g.this.e.obtainStyledAttributes(attributeSet, j.MenuGroup);
            this.f1252b = obtainStyledAttributes.getResourceId(j.MenuGroup_android_id, 0);
            this.f1253c = obtainStyledAttributes.getInt(j.MenuGroup_android_menuCategory, 0);
            this.f1254d = obtainStyledAttributes.getInt(j.MenuGroup_android_orderInCategory, 0);
            this.e = obtainStyledAttributes.getInt(j.MenuGroup_android_checkableBehavior, 0);
            this.f = obtainStyledAttributes.getBoolean(j.MenuGroup_android_visible, true);
            this.g = obtainStyledAttributes.getBoolean(j.MenuGroup_android_enabled, true);
            obtainStyledAttributes.recycle();
        }

        public SubMenu b() {
            this.h = true;
            SubMenu addSubMenu = this.f1251a.addSubMenu(this.f1252b, this.i, this.j, this.k);
            a(addSubMenu.getItem());
            return addSubMenu;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r0v64, resolved type: int */
        /* JADX WARN: Multi-variable type inference failed */
        public void b(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = g.this.e.obtainStyledAttributes(attributeSet, j.MenuItem);
            this.i = obtainStyledAttributes.getResourceId(j.MenuItem_android_id, 0);
            this.j = (obtainStyledAttributes.getInt(j.MenuItem_android_menuCategory, this.f1253c) & -65536) | (obtainStyledAttributes.getInt(j.MenuItem_android_orderInCategory, this.f1254d) & 65535);
            this.k = obtainStyledAttributes.getText(j.MenuItem_android_title);
            this.l = obtainStyledAttributes.getText(j.MenuItem_android_titleCondensed);
            this.m = obtainStyledAttributes.getResourceId(j.MenuItem_android_icon, 0);
            this.n = a(obtainStyledAttributes.getString(j.MenuItem_android_alphabeticShortcut));
            this.o = obtainStyledAttributes.getInt(j.MenuItem_alphabeticModifiers, 4096);
            this.p = a(obtainStyledAttributes.getString(j.MenuItem_android_numericShortcut));
            this.q = obtainStyledAttributes.getInt(j.MenuItem_numericModifiers, 4096);
            this.r = obtainStyledAttributes.hasValue(j.MenuItem_android_checkable) ? obtainStyledAttributes.getBoolean(j.MenuItem_android_checkable, false) : this.e ? 1 : 0;
            this.s = obtainStyledAttributes.getBoolean(j.MenuItem_android_checked, false);
            this.t = obtainStyledAttributes.getBoolean(j.MenuItem_android_visible, this.f);
            this.u = obtainStyledAttributes.getBoolean(j.MenuItem_android_enabled, this.g);
            this.v = obtainStyledAttributes.getInt(j.MenuItem_showAsAction, -1);
            this.z = obtainStyledAttributes.getString(j.MenuItem_android_onClick);
            this.w = obtainStyledAttributes.getResourceId(j.MenuItem_actionLayout, 0);
            this.x = obtainStyledAttributes.getString(j.MenuItem_actionViewClass);
            this.y = obtainStyledAttributes.getString(j.MenuItem_actionProviderClass);
            boolean z2 = this.y != null;
            if (z2 && this.w == 0 && this.x == null) {
                this.A = (AbstractC0112b) a(this.y, g.f1245b, g.this.f1247d);
            } else {
                if (z2) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.A = null;
            }
            this.B = obtainStyledAttributes.getText(j.MenuItem_contentDescription);
            this.C = obtainStyledAttributes.getText(j.MenuItem_tooltipText);
            if (obtainStyledAttributes.hasValue(j.MenuItem_iconTintMode)) {
                this.E = E.a(obtainStyledAttributes.getInt(j.MenuItem_iconTintMode, -1), this.E);
            } else {
                this.E = null;
            }
            if (obtainStyledAttributes.hasValue(j.MenuItem_iconTint)) {
                this.D = obtainStyledAttributes.getColorStateList(j.MenuItem_iconTint);
            } else {
                this.D = null;
            }
            obtainStyledAttributes.recycle();
            this.h = false;
        }

        public boolean c() {
            return this.h;
        }

        public void d() {
            this.f1252b = 0;
            this.f1253c = 0;
            this.f1254d = 0;
            this.e = 0;
            this.f = true;
            this.g = true;
        }
    }

    public g(Context context) {
        super(context);
        this.e = context;
        this.f1246c = new Object[]{context};
    }

    private Object a(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? a(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    private void a(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) {
        b bVar = new b(menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            if (eventType != 2) {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            } else {
                String name = xmlPullParser.getName();
                if (name.equals("menu")) {
                    eventType = xmlPullParser.next();
                } else {
                    throw new RuntimeException("Expecting menu, got " + name);
                }
            }
        }
        int i = eventType;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        String name2 = xmlPullParser.getName();
                        if (z2 && name2.equals(str)) {
                            str = null;
                            z2 = false;
                        } else if (name2.equals(ResourceRulesParser.XML_TAG_GROUP)) {
                            bVar.d();
                        } else if (name2.equals(ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM)) {
                            if (!bVar.c()) {
                                AbstractC0112b bVar2 = bVar.A;
                                if (bVar2 == null || !bVar2.a()) {
                                    bVar.a();
                                } else {
                                    bVar.b();
                                }
                            }
                        } else if (name2.equals("menu")) {
                            z = true;
                        }
                    }
                } else if (!z2) {
                    String name3 = xmlPullParser.getName();
                    if (name3.equals(ResourceRulesParser.XML_TAG_GROUP)) {
                        bVar.a(attributeSet);
                    } else if (name3.equals(ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM)) {
                        bVar.b(attributeSet);
                    } else if (name3.equals("menu")) {
                        a(xmlPullParser, attributeSet, bVar.b());
                    } else {
                        str = name3;
                        z2 = true;
                    }
                }
                i = xmlPullParser.next();
            } else {
                throw new RuntimeException("Unexpected end of document");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Object a() {
        if (this.f == null) {
            this.f = a(this.e);
        }
        return this.f;
    }

    public void inflate(int i, Menu menu) {
        if (!(menu instanceof b.e.b.a.a)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            xmlResourceParser = this.e.getResources().getLayout(i);
            a(xmlResourceParser, Xml.asAttributeSet(xmlResourceParser), menu);
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        } catch (XmlPullParserException e2) {
            throw new InflateException("Error inflating menu XML", e2);
        } catch (IOException e3) {
            throw new InflateException("Error inflating menu XML", e3);
        } catch (Throwable th) {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }
}
