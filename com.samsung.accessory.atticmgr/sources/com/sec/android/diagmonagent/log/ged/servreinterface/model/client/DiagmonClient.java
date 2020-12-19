package com.sec.android.diagmonagent.log.ged.servreinterface.model.client;

import android.content.Context;
import android.util.Log;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class DiagmonClient {
    private JSONObject mBody;
    private String mMethod;
    private HttpURLConnection mURLConnection = null;
    private Response response;

    public DiagmonClient(String str, String str2) {
        try {
            AppLog.d("URL : " + str);
            URL url = new URL(str);
            this.response = new Response();
            this.mMethod = str2;
            this.mURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection.setRequestMethod(str2);
            this.mURLConnection.setConnectTimeout(2000);
            this.mURLConnection.setReadTimeout(2000);
            this.mURLConnection.setDoInput(true);
        } catch (IOException e) {
            String str3 = DeviceUtils.TAG;
            Log.e(str3, e + "constructor?");
        }
    }

    public DiagmonClient(Context context, String str, String str2, String str3, String str4) {
        try {
            URL url = new URL(RestUtils.getServerAddress(context).concat(str).concat(str2));
            this.response = new Response();
            this.mMethod = str3;
            this.mURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection.setRequestMethod(this.mMethod);
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE, "application/json");
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_ACCEPT, "application/json");
            this.mURLConnection.setRequestProperty("Authorization", RestUtils.getAuth(context, str, str4, "", PreferenceUtils.getJwtToken(context)));
            this.mURLConnection.setConnectTimeout(2000);
            this.mURLConnection.setReadTimeout(2000);
            if (this.mMethod.equals(HttpNetworkInterface.XTP_HTTP_METHOD_GET)) {
                this.mURLConnection.setDoInput(true);
            } else {
                this.mURLConnection.setDoOutput(true);
            }
        } catch (IOException unused) {
            AppLog.e(" constructor?");
        }
    }

    public DiagmonClient(Context context, String str, String str2, String str3, JSONObject jSONObject) {
        try {
            URL url = new URL(RestUtils.getServerAddress(context).concat(str));
            this.response = new Response();
            this.mBody = jSONObject;
            this.mMethod = str2;
            this.mURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection.setRequestMethod(this.mMethod);
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE, "application/json");
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_ACCEPT, "application/json");
            this.mURLConnection.setRequestProperty("Authorization", RestUtils.getAuth(context, str, str3, jSONObject.toString(), PreferenceUtils.getJwtToken(context)));
            this.mURLConnection.setConnectTimeout(2000);
            this.mURLConnection.setReadTimeout(2000);
            if (this.mMethod.equals(HttpNetworkInterface.XTP_HTTP_METHOD_GET)) {
                this.mURLConnection.setDoInput(true);
            } else {
                this.mURLConnection.setDoOutput(true);
            }
        } catch (IOException e) {
            AppLog.e(e + " constructor?");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01b6 A[SYNTHETIC, Splitter:B:100:0x01b6] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x026f  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0276  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0285 A[SYNTHETIC, Splitter:B:149:0x0285] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x028a A[Catch:{ IOException -> 0x028e }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0156 A[SYNTHETIC, Splitter:B:71:0x0156] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x015e A[Catch:{ IOException -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0163 A[Catch:{ IOException -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0168 A[Catch:{ IOException -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0187 A[SYNTHETIC, Splitter:B:86:0x0187] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x018f A[Catch:{ IOException -> 0x018b }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0194 A[Catch:{ IOException -> 0x018b }] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0199 A[Catch:{ IOException -> 0x018b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response execute() {
        /*
        // Method dump skipped, instructions count: 658
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.servreinterface.model.client.DiagmonClient.execute():com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response");
    }
}
