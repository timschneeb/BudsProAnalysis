package com.samsung.accessory.hearablemgr.common.soagent;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Xml;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.android.sdk.mobileservice.auth.AuthConstants;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import seccompat.android.util.Log;

public class AccessoryClient extends RestfulClient {
    private static final String ADD_ACCESSORY_API = "/api/v1/accessory";
    private static final String TAG = (Application.TAG_ + AccessoryClient.class.getSimpleName());

    public AccessoryClient(AccessoryObject accessoryObject, String str) {
        super("https://dir-apis.samsungdm.com/api/v1/accessory", HttpNetworkInterface.XTP_HTTP_METHOD_POST, accessoryObject, str);
    }

    @Override // com.samsung.accessory.hearablemgr.common.soagent.RestfulClient
    public String getID() {
        try {
            return getBody().getJSONObject(AccessoryObject.TAG_FUMOACCESSORY).getString(AccessoryObject.TAG_SERIALNUMBER);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.accessory.hearablemgr.common.soagent.RestfulClient
    public String getAuthorization() {
        String id = getID();
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        if (!TextUtils.equals(id, "TWID:") || id.length() != 11) {
            String str = TAG;
            Log.d(str, "id: " + id);
            StringBuilder sb = new StringBuilder();
            sb.append(new String(Base64.encode(getSHADigest(AccessoryUtil.getServerID() + ':' + AccessoryUtil.getServerPassword() + ':' + id + ':' + AccessoryUtil.convertId(id)), 2), Charset.defaultCharset()));
            sb.append(':');
            AccessoryInfo accInfo = ((AccessoryObject) getBody()).getAccInfo();
            if (!TextUtils.isEmpty(accInfo.getToken())) {
                sb.append(accInfo.getToken());
                sb.append(':');
            }
            sb.append(new String(Base64.encode(getSHADigest(ADD_ACCESSORY_API + ':' + getBody().toString()), 2), Charset.defaultCharset()));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("consumer_id=\"");
            sb2.append(id);
            if (!TextUtils.isEmpty(accInfo.getToken())) {
                sb2.append("\",access_token=\"");
                sb2.append(accInfo.getToken());
            }
            sb2.append("\",signature=\"");
            sb2.append(new String(Base64.encode(getSHADigest(sb.toString()), 2), Charset.defaultCharset()));
            sb2.append("\",auth_type=\"sha-256_v2\"");
            String str2 = TAG;
            Log.d(str2, "trytoken=" + accInfo.getToken());
            String str3 = TAG;
            Log.d(str3, "getAuthorization = " + sb2.toString());
            return sb2.toString();
        }
        String str4 = TAG;
        Log.d(str4, "id is invalid " + id);
        return null;
    }

    @Override // com.samsung.accessory.hearablemgr.common.soagent.RestfulClient
    public boolean onReponseHeader(HttpsURLConnection httpsURLConnection) {
        String headerField = httpsURLConnection.getHeaderField(AuthConstants.EXTRA_ACCESS_TOKEN);
        String str = TAG;
        Log.d(str, "token = " + headerField);
        if (TextUtils.isEmpty(headerField)) {
            return false;
        }
        ((AccessoryObject) getBody()).getAccInfo().setToken(headerField);
        return true;
    }

    @Override // com.samsung.accessory.hearablemgr.common.soagent.RestfulClient
    public void onResultBlock() {
        Log.d(TAG, "Receive result: block in NetActionRegisterDevice");
    }

    @Override // com.samsung.accessory.hearablemgr.common.soagent.RestfulClient
    public boolean onResult(HttpsURLConnection httpsURLConnection) throws XmlPullParserException, IOException {
        Log.d(TAG, "Receive result: success in NetActionRegisterDevice");
        return true;
    }

    @Override // com.samsung.accessory.hearablemgr.common.soagent.RestfulClient
    public void onError(int i, HttpsURLConnection httpsURLConnection) throws XmlPullParserException, IOException {
        String str = TAG;
        Log.d(str, "ResponseCode : " + i);
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(httpsURLConnection.getErrorStream(), HttpNetworkInterface.XTP_HTTP_UTF8);
        String str2 = "NO_CODE";
        String str3 = "";
        long j = -1;
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if ("code".equals(name)) {
                    str2 = newPullParser.nextText();
                }
                if ("messsage".equals(name)) {
                    str3 = newPullParser.nextText();
                }
                if ("retryPeriod".equals(name)) {
                    j = Long.valueOf(newPullParser.nextText()).longValue();
                }
            } else if (eventType == 3 && "errorVO".equals(newPullParser.getName())) {
                String str4 = TAG;
                Log.d(str4, "code:" + str2 + " message:" + str3 + " retryPeriod:" + j);
                return;
            }
        }
        Log.d(TAG, "Wrong Error Format");
    }
}
