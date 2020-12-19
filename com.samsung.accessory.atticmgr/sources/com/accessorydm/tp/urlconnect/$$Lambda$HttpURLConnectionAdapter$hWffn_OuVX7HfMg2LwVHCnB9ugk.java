package com.accessorydm.tp.urlconnect;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* renamed from: com.accessorydm.tp.urlconnect.-$$Lambda$HttpURLConnectionAdapter$hWffn_OuVX7HfMg2LwVHCnB9ugk  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$HttpURLConnectionAdapter$hWffn_OuVX7HfMg2LwVHCnB9ugk implements HostnameVerifier {
    public static final /* synthetic */ $$Lambda$HttpURLConnectionAdapter$hWffn_OuVX7HfMg2LwVHCnB9ugk INSTANCE = new $$Lambda$HttpURLConnectionAdapter$hWffn_OuVX7HfMg2LwVHCnB9ugk();

    private /* synthetic */ $$Lambda$HttpURLConnectionAdapter$hWffn_OuVX7HfMg2LwVHCnB9ugk() {
    }

    public final boolean verify(String str, SSLSession sSLSession) {
        return HttpURLConnectionAdapter.lambda$createHttpUrlConnection$0(str, sSLSession);
    }
}
