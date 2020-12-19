package com.sec.android.diagmonagent.log.provider.v1;

import android.os.Bundle;
import java.util.List;

public abstract class AbstractMasterLogProvider extends AbstractLogProvider {
    private Bundle a(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        return bundle;
    }

    private Bundle a(String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(str, z);
        return bundle;
    }

    private Bundle b(List<String> list) {
        Bundle bundle = new Bundle();
        for (String str : list) {
            bundle.putString(str, str);
        }
        return bundle;
    }

    private void o() {
        if (!g()) {
            throw new SecurityException("Permission Denial");
        }
    }

    @Override // com.sec.android.diagmonagent.log.provider.v1.AbstractLogProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        b();
        if (!"get".equals(str) || !"agreed".equals(str2)) {
            if ("get".equals(str) && "registered".equals(str2)) {
                o();
            }
            return super.call(str, str2, bundle);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("agreed", g());
        return bundle2;
    }

    /* access modifiers changed from: protected */
    public abstract boolean g();

    /* access modifiers changed from: protected */
    public abstract List<String> h();

    /* access modifiers changed from: protected */
    public abstract Bundle i();

    /* access modifiers changed from: protected */
    public String j() {
        a aVar = a.f1989a;
        return a.b();
    }

    /* access modifiers changed from: protected */
    public Bundle k() {
        return a.f1989a.a(getContext());
    }

    /* access modifiers changed from: protected */
    public abstract String l();

    /* access modifiers changed from: protected */
    public boolean m() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean n() {
        return true;
    }

    @Override // com.sec.android.diagmonagent.log.provider.v1.AbstractLogProvider
    public boolean onCreate() {
        if (!super.onCreate()) {
            return false;
        }
        this.f1988a.putBundle("registered", a("registered", false));
        this.f1988a.putBundle("pushRegistered", a("pushRegistered", false));
        this.f1988a.putBundle("tryRegistering", a("tryRegistering", true));
        this.f1988a.putBundle("nonce", a("nonce", ""));
        this.f1988a.putBundle("authorityList", b(h()));
        this.f1988a.putBundle("serviceName", a("serviceName", l()));
        this.f1988a.putBundle("deviceId", a("deviceId", j()));
        this.f1988a.putBundle("defaultMO", i());
        this.f1988a.putBundle("deviceInfo", k());
        this.f1988a.putBundle("uploadWifionly", a("uploadWifionly", n()));
        this.f1988a.putBundle("supportPush", a("supportPush", m()));
        return true;
    }
}
