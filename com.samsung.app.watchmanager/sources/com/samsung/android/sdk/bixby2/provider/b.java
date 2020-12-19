package com.samsung.android.sdk.bixby2.provider;

import android.os.Bundle;
import c.b.a.a.a.a.a;
import com.samsung.android.sdk.bixby2.provider.CapsuleProvider;

/* access modifiers changed from: package-private */
public class b implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ a f1901a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f1902b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Bundle f1903c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ CapsuleProvider.a f1904d;
    final /* synthetic */ CapsuleProvider e;

    b(CapsuleProvider capsuleProvider, a aVar, String str, Bundle bundle, CapsuleProvider.a aVar2) {
        this.e = capsuleProvider;
        this.f1901a = aVar;
        this.f1902b = str;
        this.f1903c = bundle;
        this.f1904d = aVar2;
    }

    public void run() {
        this.f1901a.executeAction(this.e.getContext(), this.f1902b, this.f1903c, this.f1904d);
    }
}
