package com.sec.android.diagmonagent.log.ged.servreinterface.model.client;

import android.util.Log;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class FileUploadClient {
    private HttpURLConnection mURLConnection = null;

    public FileUploadClient(String str) {
        try {
            this.mURLConnection = (HttpURLConnection) new URL(URLDecoder.decode(str, "utf-8")).openConnection();
            this.mURLConnection.setRequestMethod("PUT");
            this.mURLConnection.setRequestProperty(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE, XDMInterface.SYNCML_MIME_TYPE_DOWNLOAD_TYPE);
            this.mURLConnection.setConnectTimeout(30000);
            this.mURLConnection.setReadTimeout(30000);
            this.mURLConnection.setDoOutput(true);
        } catch (IOException e) {
            String str2 = DeviceUtils.TAG;
            Log.d(str2, "constructor?" + e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x008c A[Catch:{ IOException -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0091 A[Catch:{ IOException -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0096 A[Catch:{ IOException -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x009b A[Catch:{ IOException -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00c5 A[Catch:{ IOException -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ca A[Catch:{ IOException -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00cf A[Catch:{ IOException -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00d4 A[Catch:{ IOException -> 0x00dd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int execute(java.lang.String r11) {
        /*
        // Method dump skipped, instructions count: 243
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.servreinterface.model.client.FileUploadClient.execute(java.lang.String):int");
    }
}
