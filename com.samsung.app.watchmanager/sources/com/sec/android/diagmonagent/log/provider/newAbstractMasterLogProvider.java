package com.sec.android.diagmonagent.log.provider;

import android.os.Bundle;
import java.util.List;

public abstract class newAbstractMasterLogProvider extends newAbstractLogProvider {
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

    private void m() {
    }

    public void a(a aVar) {
        newAbstractLogProvider.f1987a.putBundle("authorityList", b(aVar.f().c()));
        newAbstractLogProvider.f1987a.putBundle("serviceName", a("serviceName", aVar.f().g()));
        newAbstractLogProvider.f1987a.putBundle("deviceId", a("deviceId", aVar.e()));
        newAbstractLogProvider.f1987a.putBundle("agreed", a("agreed", aVar.a()));
        newAbstractLogProvider.f1987a.putBundle("logList", a(aVar.f().e()));
        newAbstractLogProvider.f1987a.putBundle("plainLogList", a(f()));
    }

    @Override // com.sec.android.diagmonagent.log.provider.newAbstractLogProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        b();
        if ("get".equals(str) && "registered".equals(str2)) {
            m();
        }
        return super.call(str, str2, bundle);
    }

    /* access modifiers changed from: protected */
    public abstract List<String> g();

    /* access modifiers changed from: protected */
    public String h() {
        f fVar = f.f1986a;
        return f.b();
    }

    /* access modifiers changed from: protected */
    public Bundle i() {
        return f.f1986a.a(getContext());
    }

    /* access modifiers changed from: protected */
    public abstract String j();

    /* access modifiers changed from: protected */
    public boolean k() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean l() {
        return true;
    }

    @Override // com.sec.android.diagmonagent.log.provider.newAbstractLogProvider
    public boolean onCreate() {
        if (!super.onCreate()) {
            return false;
        }
        newAbstractLogProvider.f1987a.putBundle("registered", a("registered", false));
        newAbstractLogProvider.f1987a.putBundle("pushRegistered", a("pushRegistered", false));
        newAbstractLogProvider.f1987a.putBundle("tryRegistering", a("tryRegistering", true));
        newAbstractLogProvider.f1987a.putBundle("nonce", a("nonce", ""));
        newAbstractLogProvider.f1987a.putBundle("authorityList", b(g()));
        newAbstractLogProvider.f1987a.putBundle("serviceName", a("serviceName", j()));
        newAbstractLogProvider.f1987a.putBundle("deviceId", a("deviceId", h()));
        newAbstractLogProvider.f1987a.putBundle("deviceInfo", i());
        newAbstractLogProvider.f1987a.putBundle("uploadWifionly", a("uploadWifionly", l()));
        newAbstractLogProvider.f1987a.putBundle("supportPush", a("supportPush", k()));
        newAbstractLogProvider.f1987a.putBundle("logList", a(e()));
        newAbstractLogProvider.f1987a.putBundle("plainLogList", a(f()));
        return true;
    }
}
