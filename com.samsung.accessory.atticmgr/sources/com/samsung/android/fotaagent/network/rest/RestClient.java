package com.samsung.android.fotaagent.network.rest;

import com.samsung.android.fotaprovider.log.Log;
import java.util.List;
import java.util.Map;

public class RestClient {
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0171  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.samsung.android.fotaagent.network.rest.RestResponse execute(com.samsung.android.fotaagent.network.rest.RestRequest r9) {
        /*
        // Method dump skipped, instructions count: 383
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.network.rest.RestClient.execute(com.samsung.android.fotaagent.network.rest.RestRequest):com.samsung.android.fotaagent.network.rest.RestResponse");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0027, code lost:
        r5.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002e, code lost:
        if (r4 != null) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0034, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0035, code lost:
        r5.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0038, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendStream(java.net.HttpURLConnection r4, java.lang.String r5) {
        /*
            r3 = this;
            java.io.OutputStream r4 = r4.getOutputStream()     // Catch:{ Exception -> 0x0039 }
            java.io.BufferedWriter r0 = new java.io.BufferedWriter     // Catch:{ all -> 0x002b }
            java.io.OutputStreamWriter r1 = new java.io.OutputStreamWriter     // Catch:{ all -> 0x002b }
            java.lang.String r2 = "UTF-8"
            r1.<init>(r4, r2)     // Catch:{ all -> 0x002b }
            r0.<init>(r1)     // Catch:{ all -> 0x002b }
            r0.write(r5)     // Catch:{ all -> 0x001f }
            r0.flush()     // Catch:{ all -> 0x001f }
            r0.close()
            if (r4 == 0) goto L_0x003d
            r4.close()
            goto L_0x003d
        L_0x001f:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0021 }
        L_0x0021:
            r1 = move-exception
            r0.close()     // Catch:{ all -> 0x0026 }
            goto L_0x002a
        L_0x0026:
            r0 = move-exception
            r5.addSuppressed(r0)
        L_0x002a:
            throw r1
        L_0x002b:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x002d }
        L_0x002d:
            r0 = move-exception
            if (r4 == 0) goto L_0x0038
            r4.close()     // Catch:{ all -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r4 = move-exception
            r5.addSuppressed(r4)
        L_0x0038:
            throw r0
        L_0x0039:
            r4 = move-exception
            com.samsung.android.fotaprovider.log.Log.printStackTrace(r4)
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.network.rest.RestClient.sendStream(java.net.HttpURLConnection, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0037, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003d, code lost:
        r0.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0040, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0043, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0044, code lost:
        if (r3 != null) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x004b, code lost:
        r4.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x004e, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String receiveStream(int r3, java.net.HttpURLConnection r4) {
        /*
            r2 = this;
            r0 = 200(0xc8, float:2.8E-43)
            if (r0 != r3) goto L_0x0009
            java.io.InputStream r3 = r4.getInputStream()     // Catch:{ Exception -> 0x004f }
            goto L_0x000d
        L_0x0009:
            java.io.InputStream r3 = r4.getErrorStream()     // Catch:{ Exception -> 0x004f }
        L_0x000d:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ all -> 0x0041 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ all -> 0x0041 }
            java.lang.String r1 = "UTF-8"
            r0.<init>(r3, r1)     // Catch:{ all -> 0x0041 }
            r4.<init>(r0)     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0035 }
            r0.<init>()     // Catch:{ all -> 0x0035 }
        L_0x001e:
            java.lang.String r1 = r4.readLine()     // Catch:{ all -> 0x0035 }
            if (r1 == 0) goto L_0x0028
            r0.append(r1)     // Catch:{ all -> 0x0035 }
            goto L_0x001e
        L_0x0028:
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0035 }
            r4.close()
            if (r3 == 0) goto L_0x0034
            r3.close()
        L_0x0034:
            return r0
        L_0x0035:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0037 }
        L_0x0037:
            r1 = move-exception
            r4.close()     // Catch:{ all -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r4 = move-exception
            r0.addSuppressed(r4)
        L_0x0040:
            throw r1
        L_0x0041:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0043 }
        L_0x0043:
            r0 = move-exception
            if (r3 == 0) goto L_0x004e
            r3.close()     // Catch:{ all -> 0x004a }
            goto L_0x004e
        L_0x004a:
            r3 = move-exception
            r4.addSuppressed(r3)
        L_0x004e:
            throw r0
        L_0x004f:
            r3 = move-exception
            com.samsung.android.fotaprovider.log.Log.printStackTrace(r3)
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fotaagent.network.rest.RestClient.receiveStream(int, java.net.HttpURLConnection):java.lang.String");
    }

    private void logcatHeaders(Map<String, List<String>> map) {
        Log.H("<< Headers:");
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < entry.getValue().size(); i++) {
                stringBuffer.append(i == 0 ? "" : ";" + entry.getValue().get(i));
            }
            Log.H(entry.getKey() + "=" + stringBuffer.toString());
        }
    }
}
