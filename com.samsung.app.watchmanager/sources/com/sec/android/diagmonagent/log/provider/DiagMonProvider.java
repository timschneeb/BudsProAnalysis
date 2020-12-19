package com.sec.android.diagmonagent.log.provider;

import android.util.Log;
import com.sec.android.diagmonagent.log.provider.a.a;
import java.util.ArrayList;
import java.util.List;

public class DiagMonProvider extends newAbstractMasterLogProvider {

    /* renamed from: b  reason: collision with root package name */
    public static String f1955b = "";

    @Override // com.sec.android.diagmonagent.log.provider.newAbstractMasterLogProvider
    public void a(a aVar) {
        f1955b = "com.sec.android.log." + aVar.g();
        super.a(aVar);
        Log.d(a.f1964a, "LogProvider is set");
    }

    /* access modifiers changed from: protected */
    @Override // com.sec.android.diagmonagent.log.provider.newAbstractLogProvider
    public String c() {
        return f1955b;
    }

    /* access modifiers changed from: protected */
    @Override // com.sec.android.diagmonagent.log.provider.newAbstractLogProvider
    public List<String> e() {
        return new ArrayList();
    }

    /* access modifiers changed from: protected */
    @Override // com.sec.android.diagmonagent.log.provider.newAbstractLogProvider
    public List<String> f() {
        return new ArrayList();
    }

    /* access modifiers changed from: protected */
    @Override // com.sec.android.diagmonagent.log.provider.newAbstractMasterLogProvider
    public List<String> g() {
        return new ArrayList();
    }

    /* access modifiers changed from: protected */
    @Override // com.sec.android.diagmonagent.log.provider.newAbstractMasterLogProvider
    public String h() {
        return "";
    }

    /* access modifiers changed from: protected */
    @Override // com.sec.android.diagmonagent.log.provider.newAbstractMasterLogProvider
    public String j() {
        return "";
    }

    @Override // com.sec.android.diagmonagent.log.provider.newAbstractMasterLogProvider, com.sec.android.diagmonagent.log.provider.newAbstractLogProvider
    public boolean onCreate() {
        return super.onCreate();
    }
}
