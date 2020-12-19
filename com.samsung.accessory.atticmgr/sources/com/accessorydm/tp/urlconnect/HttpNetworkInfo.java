package com.accessorydm.tp.urlconnect;

import java.net.HttpURLConnection;

public class HttpNetworkInfo {
    private static HttpNetworkInfo INSTANCE = new HttpNetworkInfo();
    private boolean XDM_SSL_CHECK = true;
    private HttpURLConnection urlConnect;

    public static HttpNetworkInfo getInstance() {
        return INSTANCE;
    }

    public HttpURLConnection getURLConnect() {
        return this.urlConnect;
    }

    public void setURLConnect(HttpURLConnection httpURLConnection) {
        this.urlConnect = httpURLConnection;
    }

    public void setSSLCheck(boolean z) {
        this.XDM_SSL_CHECK = z;
    }

    public boolean isSSLCheck() {
        return this.XDM_SSL_CHECK;
    }
}
