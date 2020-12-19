package com.sec.android.diagmonagent.log.ged.servreinterface.model.client;

import android.content.Context;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenClient {
    private HttpURLConnection mURLConnection = null;
    private Response response;

    public TokenClient(Context context, String str) {
        try {
            URL url = new URL(RestUtils.getServerAddress(context).concat(str));
            this.response = new Response();
            this.mURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection.setRequestMethod(HttpNetworkInterface.XTP_HTTP_METHOD_GET);
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE, "application/json");
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_ACCEPT, "application/json");
            this.mURLConnection.setRequestProperty("Authorization", RestUtils.getAuth(context, str));
            this.mURLConnection.setConnectTimeout(2000);
            this.mURLConnection.setReadTimeout(2000);
            this.mURLConnection.setDoInput(true);
        } catch (IOException e) {
            AppLog.e(e + " constructor?");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00c1 A[SYNTHETIC, Splitter:B:39:0x00c1] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c6 A[Catch:{ IOException -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00d3 A[SYNTHETIC, Splitter:B:49:0x00d3] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d8 A[Catch:{ IOException -> 0x00dc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response execute() {
        /*
        // Method dump skipped, instructions count: 224
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.servreinterface.model.client.TokenClient.execute():com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response");
    }
}
