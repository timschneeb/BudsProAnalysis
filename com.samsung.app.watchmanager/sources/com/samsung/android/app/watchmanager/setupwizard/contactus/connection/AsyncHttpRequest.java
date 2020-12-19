package com.samsung.android.app.watchmanager.setupwizard.contactus.connection;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;

public class AsyncHttpRequest extends AsyncTask<Object, Integer, String> {
    public static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
    public static final int DELETE_FEEDBACK = 4;
    public static final int GET_ACCESS_TOKEN = 1;
    public static final int GET_ANSWER_FILE_URL = 6;
    public static final int GET_FEEDBACK_DETAIL = 5;
    public static final int GET_FEEDBACK_LIST = 3;
    public static final int NEW_SA_TOKEN = 10;
    public static final int NEW_SM_TOKEN = 11;
    public static final int RATE_RESPONSE = 8;
    public static final int SEND_FEEDBACK = 2;
    private static final String SERVER = "https://api.samsungmembers.com/";
    private static String TAG = "AsyncHttpRequest";
    protected static final int TIMEOUT_CONNECTION = 60000;
    public static final int UPLOAD_ATTACHMENTS = 7;
    public static final int VALIDATE_SA_TOKEN = 9;
    int BUFFER_LIMIT = 4096;
    String LINE_END = "\r\n";
    int STATUS_BAD_REQUEST = 3;
    int STATUS_NETWORK_UNAVAILABLE = 1;
    int STATUS_NO_RESPONSE = 2;
    String _response;
    int _statusCode;
    private String buildModel;
    public AsyncResponse delegate = null;
    private String mPackageName = "com.samsung.android.app.watchmanager";
    private String mVersionName = "2.2.17073161";
    ProgressDialog progressDialog;

    public interface AsyncResponse {
        void processFinish(String str);
    }

    public AsyncHttpRequest(AsyncResponse asyncResponse) {
        this.delegate = asyncResponse;
    }

    public AsyncHttpRequest(AsyncResponse asyncResponse, ProgressDialog progressDialog2) {
        this.delegate = asyncResponse;
        this.progressDialog = progressDialog2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String deleteFeedBack(java.lang.Object[] r11) {
        /*
        // Method dump skipped, instructions count: 401
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.deleteFeedBack(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x026f A[SYNTHETIC, Splitter:B:57:0x026f] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0280  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0285  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0291  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0296  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x029f  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x02a4 A[SYNTHETIC, Splitter:B:81:0x02a4] */
    /* JADX WARNING: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:93:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getAccessToken(java.lang.String r10, java.lang.String r11) {
        /*
        // Method dump skipped, instructions count: 687
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.getAccessToken(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x0243  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0248 A[SYNTHETIC, Splitter:B:53:0x0248] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x026f  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0278  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x027d A[SYNTHETIC, Splitter:B:77:0x027d] */
    /* JADX WARNING: Removed duplicated region for block: B:87:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getAccessToken2(java.lang.String r9, java.lang.String r10) {
        /*
        // Method dump skipped, instructions count: 648
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.getAccessToken2(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0148, code lost:
        if (r3 != null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x014a, code lost:
        r3.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0153, code lost:
        if (r3 != null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x015b, code lost:
        if (r3 != null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x015e, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0161  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getAllFeedBacks(java.lang.Object[] r11) {
        /*
        // Method dump skipped, instructions count: 359
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.getAllFeedBacks(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getAnswerFileUrl(java.lang.Object[] r15) {
        /*
        // Method dump skipped, instructions count: 418
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.getAnswerFileUrl(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0160, code lost:
        if (r3 != null) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0162, code lost:
        r3.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016b, code lost:
        if (r3 != null) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0173, code lost:
        if (r3 != null) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0176, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0179  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getFeedBackDetail(java.lang.Object[] r11) {
        /*
        // Method dump skipped, instructions count: 383
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.getFeedBackDetail(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x022a  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0235  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String rateResponse(java.lang.Object[] r14) {
        /*
        // Method dump skipped, instructions count: 579
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.rateResponse(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:106:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0245  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x024a A[SYNTHETIC, Splitter:B:63:0x024a] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x025b  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0260  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x026c  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0271  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0282  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x028b  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0290 A[SYNTHETIC, Splitter:B:94:0x0290] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String reissueAccessToken(java.lang.String r11, java.lang.String r12) {
        /*
        // Method dump skipped, instructions count: 667
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.reissueAccessToken(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x01db, code lost:
        r3.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String sendFeedBack(java.lang.Object[] r13) {
        /*
        // Method dump skipped, instructions count: 519
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.sendFeedBack(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:120:0x05e3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x05e4, code lost:
        r2 = r0;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x05ea, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x05eb, code lost:
        r3 = r0;
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x05fd, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x05fe, code lost:
        r2 = r0;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0604, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0605, code lost:
        r3 = r0;
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0617, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0618, code lost:
        r2 = r0;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x061e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x061f, code lost:
        r3 = r0;
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x052f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0530, code lost:
        r2 = r0;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0536, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0537, code lost:
        r3 = r0;
        r2 = r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x05fd A[ExcHandler: all (r0v39 'th' java.lang.Throwable A[CUSTOM_DECLARE])] */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0617 A[ExcHandler: all (r0v35 'th' java.lang.Throwable A[CUSTOM_DECLARE])] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x03a5  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x03ab  */
    @android.annotation.TargetApi(19)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String upload(java.lang.Object[] r25) {
        /*
        // Method dump skipped, instructions count: 1606
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.upload(java.lang.Object[]):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String uploadtattchments(java.lang.Object[] r13) {
        /*
        // Method dump skipped, instructions count: 453
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.uploadtattchments(java.lang.Object[]):java.lang.String");
    }

    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public String doInBackground(Object[] objArr) {
        PackageInfo packageInfo;
        Integer num = (Integer) objArr[0];
        this.buildModel = "Android";
        this.mPackageName = TWatchManagerApplication.getAppContext().getPackageName();
        try {
            packageInfo = TWatchManagerApplication.getAppContext().getPackageManager().getPackageInfo(this.mPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo != null) {
            this.mVersionName = packageInfo.versionName.split("_")[0];
        }
        switch (num.intValue()) {
            case 1:
                return getAccessToken((String) objArr[1], (String) objArr[2]);
            case 2:
                return sendFeedBack(objArr);
            case 3:
                return getAllFeedBacks(objArr);
            case 4:
                return deleteFeedBack(objArr);
            case 5:
                return getFeedBackDetail(objArr);
            case 6:
                return getAnswerFileUrl(objArr);
            case 7:
                return upload(objArr);
            case 8:
                return rateResponse(objArr);
            case 9:
                try {
                    return String.valueOf(SamsungAccountUtils.getTokenValidation(TWatchManagerApplication.getAppContext(), (String) objArr[1], (String) objArr[2]));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return null;
                }
            case 10:
                return SamsungAccountUtils.getNewAccessToken((String) objArr[1], (SharedPreferences) objArr[2]);
            default:
                return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getResponseBody(java.net.HttpURLConnection r4) {
        /*
            r3 = this;
            java.lang.String r0 = ""
            int r1 = r4.getResponseCode()     // Catch:{ IOException -> 0x0046 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r1 == r2) goto L_0x0019
            int r1 = r4.getResponseCode()     // Catch:{ IOException -> 0x0046 }
            r2 = 201(0xc9, float:2.82E-43)
            if (r1 != r2) goto L_0x0013
            goto L_0x0019
        L_0x0013:
            java.io.InputStream r4 = r4.getErrorStream()     // Catch:{ IOException -> 0x0046 }
            r1 = r0
            goto L_0x001f
        L_0x0019:
            java.lang.String r1 = "OK"
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ IOException -> 0x0043 }
        L_0x001f:
            if (r4 == 0) goto L_0x0041
            java.io.BufferedReader r1 = new java.io.BufferedReader
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            r2.<init>(r4)
            r1.<init>(r2)
        L_0x002b:
            java.lang.String r4 = r1.readLine()
            if (r4 == 0) goto L_0x004a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r4)
            java.lang.String r0 = r2.toString()
            goto L_0x002b
        L_0x0041:
            r0 = r1
            goto L_0x004a
        L_0x0043:
            r4 = move-exception
            r0 = r1
            goto L_0x0047
        L_0x0046:
            r4 = move-exception
        L_0x0047:
            r4.printStackTrace()
        L_0x004a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.watchmanager.setupwizard.contactus.connection.AsyncHttpRequest.getResponseBody(java.net.HttpURLConnection):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        AsyncResponse asyncResponse = this.delegate;
        if (asyncResponse != null) {
            asyncResponse.processFinish(str);
        }
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(Integer... numArr) {
        String str = TAG;
        Log.d(str, numArr[0] + "/" + numArr[1]);
        this.progressDialog.setProgress(numArr[0].intValue());
        super.onProgressUpdate((Object[]) numArr);
    }
}
