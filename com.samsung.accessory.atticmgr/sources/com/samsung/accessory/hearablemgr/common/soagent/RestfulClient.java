package com.samsung.accessory.hearablemgr.common.soagent;

import com.samsung.accessory.hearablemgr.Application;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

public abstract class RestfulClient {
    public static final int RESPONSE_FAIL = 2;
    public static final int RESPONSE_RETRY = 3;
    public static final int RESPONSE_SUCCESS = 1;
    private static final String TAG = (Application.TAG_ + RestfulClient.class.getSimpleName());
    private JSONObject mBody;
    private String mMethod;
    private String mType;
    private String mUrl;

    public abstract String getAuthorization();

    public abstract String getID();

    public abstract void onError(int i, HttpsURLConnection httpsURLConnection) throws XmlPullParserException, IOException;

    public boolean onReponseHeader(HttpsURLConnection httpsURLConnection) {
        return true;
    }

    public abstract boolean onResult(HttpsURLConnection httpsURLConnection) throws XmlPullParserException, IOException;

    public void onResultBlock() {
    }

    public RestfulClient(String str, String str2, JSONObject jSONObject, String str3) {
        this.mUrl = str;
        this.mMethod = str2;
        this.mBody = jSONObject;
        this.mType = str3;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getMethod() {
        return this.mMethod;
    }

    public JSONObject getBody() {
        return this.mBody;
    }

    public String getType() {
        return this.mType;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01ac  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x01b6 A[SYNTHETIC, Splitter:B:103:0x01b6] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0173 A[SYNTHETIC, Splitter:B:76:0x0173] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x017e  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0188  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x019d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int execute() {
        /*
        // Method dump skipped, instructions count: 447
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.common.soagent.RestfulClient.execute():int");
    }

    public byte[] getSHADigest(String str) {
        try {
            byte[] bytes = str.getBytes(Charset.defaultCharset());
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.reset();
            return instance.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
