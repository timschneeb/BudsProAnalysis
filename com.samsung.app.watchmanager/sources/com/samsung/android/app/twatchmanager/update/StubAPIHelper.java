package com.samsung.android.app.twatchmanager.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class StubAPIHelper {
    private static final String PREFIX_SAMSUNG = "SAMSUNG-";
    public static HashSet<String> RESULT_KEYSET = new HashSet<>();
    private static final String SERVER_URL_CHECK = "https://vas.samsungapps.com/stub/stubUpdateCheck.as";
    private static final String SERVER_URL_DEFAULT = "vas.samsungapps.com";
    private static final String SERVER_URL_DOWNLOAD = "https://vas.samsungapps.com/stub/stubDownload.as";
    private static final String SERVER_URL_GETCNVAS = "https://cn-ms.galaxyappstore.com/getCNVasURL.as";
    public static final String TAG = ("tUHM:[Update]" + StubAPIHelper.class.getSimpleName());
    private static final long cnUrlCheckInterval = 604800000;
    private String mAbiType;
    private int mApiLevel = Build.VERSION.SDK_INT;
    private String mCSC;
    private Context mContext = TWatchManagerApplication.getAppContext();
    private String mDeviceName = Build.MODEL;
    private String mMCC = "000";
    private String mMNC = "00";
    private String mPD;
    private String mResolution;

    public static class XMLResult {
        private HashMap<String, String> mDataMap = new HashMap<>();

        public String get(XMLResultKey xMLResultKey) {
            return get(xMLResultKey.toString());
        }

        public String get(String str) {
            String str2 = this.mDataMap.get(str);
            return TextUtils.isEmpty(str2) ? "" : str2;
        }

        public String printAllData() {
            String str;
            StringBuilder sb;
            StringBuffer stringBuffer = new StringBuffer("");
            stringBuffer.append("[Print XML result for StubAPI]====================================\n");
            Iterator<String> it = StubAPIHelper.RESULT_KEYSET.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (HostManagerUtils.DEBUGGABLE()) {
                    sb = new StringBuilder();
                } else if (XMLResultKey.SIGNATURE.toString().equals(next) || XMLResultKey.DOWNLOAD_URI.toString().equals(next)) {
                    str = "** " + next + " : " + (TextUtils.isEmpty(this.mDataMap.get(next)) ? "[null]" : "#") + "\n";
                    stringBuffer.append(str);
                } else {
                    sb = new StringBuilder();
                }
                sb.append("** ");
                sb.append(next);
                sb.append(" : ");
                sb.append(this.mDataMap.get(next));
                sb.append("\n");
                str = sb.toString();
                stringBuffer.append(str);
            }
            stringBuffer.append("==================================================================\n");
            return stringBuffer.toString();
        }

        public void put(String str, String str2) {
            this.mDataMap.put(str, str2);
        }
    }

    public enum XMLResultKey {
        APP_ID("appId"),
        RESULT_CODE("resultCode"),
        VERSION_NAME("versionName"),
        DOWNLOAD_URI("downloadURI"),
        SIGNATURE("signature"),
        CONTENT_SIZE("contentSize"),
        VERSION_CODE("versionCode"),
        UPDATE_DESCRIPTION("updateDescription");
        
        private String mKey;

        private XMLResultKey(String str) {
            this.mKey = str;
        }

        public String toString() {
            return this.mKey;
        }
    }

    static {
        for (XMLResultKey xMLResultKey : XMLResultKey.values()) {
            RESULT_KEYSET.add(xMLResultKey.toString());
        }
    }

    public StubAPIHelper() {
        if (PREFIX_SAMSUNG.startsWith(this.mDeviceName) && 8 < this.mDeviceName.length()) {
            this.mDeviceName = this.mDeviceName.substring(8);
        }
        if (!UpdateUtil.isTestMode4Update()) {
            String mnc = HostManagerUtilsNetwork.getMNC(this.mContext);
            String mcc = HostManagerUtilsNetwork.getMCC(this.mContext);
            if (!TextUtils.isEmpty(mcc)) {
                this.mMCC = mcc;
            }
            if (!TextUtils.isEmpty(mnc)) {
                this.mMNC = mnc;
            }
        }
        this.mCSC = HostManagerUtilsNetwork.getCSC();
        this.mPD = UpdateUtil.getPD();
        this.mResolution = HostManagerUtils.getResolution(this.mContext);
        this.mAbiType = UpdateUtil.getAbiType();
    }

    private Uri.Builder addCommonParamsForStubAPI(String str, String str2) {
        Uri.Builder buildUpon = Uri.parse(str).buildUpon();
        buildUpon.appendQueryParameter("appId", str2);
        buildUpon.appendQueryParameter("deviceId", HostManagerUtils.isSamsungDevice() ? this.mDeviceName : this.mResolution);
        buildUpon.appendQueryParameter("mcc", this.mMCC);
        buildUpon.appendQueryParameter("mnc", this.mMNC);
        buildUpon.appendQueryParameter("csc", this.mCSC);
        buildUpon.appendQueryParameter("sdkVer", String.valueOf(this.mApiLevel));
        buildUpon.appendQueryParameter(UpdateHistoryManager.PREF_KEY_STORE_SETTING, this.mPD);
        buildUpon.appendQueryParameter("systemId", String.valueOf(System.currentTimeMillis() - SystemClock.elapsedRealtime()));
        buildUpon.appendQueryParameter("callerId", "com.samsung.android.app.watchmanager");
        buildUpon.appendQueryParameter("abiType", this.mAbiType);
        return buildUpon;
    }

    private void closeConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    private void closeStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:62:0x00e4 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:9:0x004a */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.samsung.android.app.twatchmanager.update.StubAPIHelper] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v19, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v31 */
    /* JADX WARN: Type inference failed for: r0v32 */
    /* JADX WARN: Type inference failed for: r0v33 */
    /* JADX WARN: Type inference failed for: r0v34 */
    /* JADX WARN: Type inference failed for: r0v35 */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x006b A[Catch:{ XmlPullParserException -> 0x00ac, SocketException -> 0x00aa, UnknownHostException -> 0x00a8, IOException -> 0x00a6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getCNVasURL() {
        /*
        // Method dump skipped, instructions count: 245
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.StubAPIHelper.getCNVasURL():java.lang.String");
    }

    private String getVasURL(String str) {
        if (!"460".equals(this.mMCC) && !"461".equals(this.mMCC)) {
            return str;
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0);
        String string = sharedPreferences.getString("cnVasURL", null);
        long j = sharedPreferences.getLong("cnVasTime", 0);
        if (string == null || System.currentTimeMillis() - j > cnUrlCheckInterval) {
            string = getCNVasURL();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("cnVasURL", string);
            edit.putLong("cnVasTime", System.currentTimeMillis());
            edit.commit();
        }
        return !TextUtils.isEmpty(string) ? str.replaceAll(SERVER_URL_DEFAULT, string) : str;
    }

    private ArrayList<XMLResult> handleStubAPIRequest(URL url, String str) {
        Throwable th;
        HttpURLConnection httpURLConnection;
        BufferedInputStream bufferedInputStream;
        Exception e;
        BufferedInputStream bufferedInputStream2 = null;
        ArrayList<XMLResult> arrayList = null;
        bufferedInputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            try {
                bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                try {
                    arrayList = parseAppInfoXML(bufferedInputStream, str);
                } catch (IOException | NullPointerException | SecurityException e2) {
                    e = e2;
                }
            } catch (IOException | NullPointerException | SecurityException e3) {
                e = e3;
                bufferedInputStream = null;
                try {
                    e.printStackTrace();
                    closeStream(bufferedInputStream);
                    closeConnection(httpURLConnection);
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream2 = bufferedInputStream;
                    closeStream(bufferedInputStream2);
                    closeConnection(httpURLConnection);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                closeStream(bufferedInputStream2);
                closeConnection(httpURLConnection);
                throw th;
            }
        } catch (IOException | NullPointerException | SecurityException e4) {
            e = e4;
            httpURLConnection = null;
            bufferedInputStream = null;
            e.printStackTrace();
            closeStream(bufferedInputStream);
            closeConnection(httpURLConnection);
            return arrayList;
        } catch (Throwable th4) {
            th = th4;
            httpURLConnection = null;
            closeStream(bufferedInputStream2);
            closeConnection(httpURLConnection);
            throw th;
        }
        closeStream(bufferedInputStream);
        closeConnection(httpURLConnection);
        return arrayList;
    }

    private String makeStubDownloadAPIURL(String str, String str2, String str3) {
        Uri.Builder addCommonParamsForStubAPI = addCommonParamsForStubAPI(str, str2);
        addCommonParamsForStubAPI.appendQueryParameter("extuk", str3);
        return addCommonParamsForStubAPI.toString();
    }

    private String makeStubUpdateCheckAPIURL(String str, String str2, String str3) {
        Uri.Builder addCommonParamsForStubAPI = addCommonParamsForStubAPI(str, str2);
        addCommonParamsForStubAPI.appendQueryParameter("versionCode", str3);
        addCommonParamsForStubAPI.appendQueryParameter("installInfo", "Y");
        addCommonParamsForStubAPI.appendQueryParameter("locale", this.mContext.getResources().getConfiguration().locale.toString());
        return addCommonParamsForStubAPI.toString();
    }

    private ArrayList<XMLResult> parseAppInfoXML(InputStream inputStream, String str) {
        ArrayList<XMLResult> arrayList = new ArrayList<>();
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(inputStream, null);
            XMLResult xMLResult = new XMLResult();
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                String name = newPullParser.getName();
                if (eventType == 2) {
                    if (RESULT_KEYSET.contains(name)) {
                        xMLResult.put(name, readXMLText(newPullParser));
                    }
                } else if (eventType == 3 && str.equalsIgnoreCase(name)) {
                    arrayList.add(xMLResult);
                    xMLResult = new XMLResult();
                }
            }
            return arrayList;
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readXMLText(XmlPullParser xmlPullParser) {
        if (xmlPullParser.next() != 4) {
            return "";
        }
        String text = xmlPullParser.getText();
        xmlPullParser.nextTag();
        return text;
    }

    public ArrayList<XMLResult> stubDownloadCheck(int i, String str, String str2) {
        String str3;
        ArrayList<XMLResult> arrayList;
        MalformedURLException e;
        String makeStubDownloadAPIURL = makeStubDownloadAPIURL(SERVER_URL_DOWNLOAD, str, str2);
        ArrayList<XMLResult> arrayList2 = new ArrayList<>();
        if (i == 1) {
            Log.d(TAG, "only one package is needed to Update; changing the infoEndTAG to <result>");
            str3 = "result";
        } else {
            str3 = "appInfo";
        }
        try {
            String str4 = TAG;
            Log.d(str4, "stubDownloadCheck() request URL: " + makeStubDownloadAPIURL);
            URL url = new URL(makeStubDownloadAPIURL);
            arrayList = handleStubAPIRequest(url, str3);
            if (arrayList == null) {
                for (int i2 = 0; i2 < 4; i2++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e2) {
                        try {
                            e2.printStackTrace();
                        } catch (MalformedURLException e3) {
                            e = e3;
                            e.printStackTrace();
                            return arrayList;
                        }
                    }
                    arrayList = handleStubAPIRequest(url, str3);
                    if (arrayList != null) {
                        break;
                    }
                }
            }
        } catch (MalformedURLException e4) {
            e = e4;
            arrayList = arrayList2;
            e.printStackTrace();
            return arrayList;
        }
        return arrayList;
    }

    public ArrayList<XMLResult> stubUpdateCheck(String str, String str2) {
        MalformedURLException e;
        String makeStubUpdateCheckAPIURL = makeStubUpdateCheckAPIURL(getVasURL(SERVER_URL_CHECK), str, str2);
        ArrayList<XMLResult> arrayList = new ArrayList<>();
        try {
            String str3 = TAG;
            Log.d(str3, "stubUpdateCheck() request URL: " + makeStubUpdateCheckAPIURL);
            URL url = new URL(makeStubUpdateCheckAPIURL);
            ArrayList<XMLResult> handleStubAPIRequest = handleStubAPIRequest(url, "result");
            if (handleStubAPIRequest != null) {
                return handleStubAPIRequest;
            }
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e2) {
                    try {
                        e2.printStackTrace();
                    } catch (MalformedURLException e3) {
                        arrayList = handleStubAPIRequest;
                        e = e3;
                        e.printStackTrace();
                        return arrayList;
                    }
                }
                handleStubAPIRequest = handleStubAPIRequest(url, "appInfo");
                if (handleStubAPIRequest != null) {
                    return handleStubAPIRequest;
                }
            }
            return handleStubAPIRequest;
        } catch (MalformedURLException e4) {
            e = e4;
            e.printStackTrace();
            return arrayList;
        }
    }
}
