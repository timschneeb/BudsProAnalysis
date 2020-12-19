package b.a.d;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import b.a.i;

public class d extends ContextWrapper {

    /* renamed from: a  reason: collision with root package name */
    private int f1232a;

    /* renamed from: b  reason: collision with root package name */
    private Resources.Theme f1233b;

    /* renamed from: c  reason: collision with root package name */
    private LayoutInflater f1234c;

    /* renamed from: d  reason: collision with root package name */
    private Configuration f1235d;
    private Resources e;

    public d() {
        super(null);
    }

    public d(Context context, int i) {
        super(context);
        this.f1232a = i;
    }

    public d(Context context, Resources.Theme theme) {
        super(context);
        this.f1233b = theme;
    }

    private Resources b() {
        Resources resources;
        if (this.e == null) {
            Configuration configuration = this.f1235d;
            if (configuration == null) {
                resources = super.getResources();
            } else if (Build.VERSION.SDK_INT >= 17) {
                resources = createConfigurationContext(configuration).getResources();
            }
            this.e = resources;
        }
        return this.e;
    }

    private void c() {
        boolean z = this.f1233b == null;
        if (z) {
            this.f1233b = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.f1233b.setTo(theme);
            }
        }
        a(this.f1233b, this.f1232a, z);
    }

    public int a() {
        return this.f1232a;
    }

    /* access modifiers changed from: protected */
    public void a(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, true);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    public Resources getResources() {
        return b();
    }

    @Override // android.content.Context, android.content.ContextWrapper
    public Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.f1234c == null) {
            this.f1234c = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.f1234c;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.f1233b;
        if (theme != null) {
            return theme;
        }
        if (this.f1232a == 0) {
            this.f1232a = i.Theme_AppCompat_Light;
        }
        c();
        return this.f1233b;
    }

    public void setTheme(int i) {
        if (this.f1232a != i) {
            this.f1232a = i;
            c();
        }
    }
}
