package com.samsung.android.app.watchmanager.setupwizard.contactus.connection;

import android.content.Intent;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class SAWebChromeClient extends WebChromeClient {
    private static final String TAG = "SAWebChromeClient";
    private int mState;

    public SAWebChromeClient(int i) {
        this.mState = i;
    }

    public String extractTextMsgfromHTML(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\<.*?>", "");
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String message = consoleMessage.message();
        String str = TAG;
        Log.d(str, "onConsoleMessage() starts... consoleMsg : " + message);
        if (TextUtils.isEmpty(message)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(extractTextMsgfromHTML(message));
            if (jSONObject.has("state")) {
                boolean z = this.mState == Integer.parseInt(jSONObject.getString("state"));
                String str2 = TAG;
                Log.d(str2, "getCommonInformation() isCorrectResponse : " + z);
                if (!z) {
                    return false;
                }
                Intent intent = new Intent(SAWebViewActivity.ACTION_SA_WEBVIEW_LOGIN_SUCCESS);
                intent.putExtra(GlobalConst.AUTH_SERVER_URL, jSONObject.getString(GlobalConst.AUTH_SERVER_URL));
                intent.putExtra(GlobalConst.USER_ID, jSONObject.getString(GlobalConst.USER_ID));
                intent.putExtra(GlobalConst.TOKEN_SA, jSONObject.getString("access_token"));
                intent.putExtra("refresh_token_sa", jSONObject.getString("refresh_token"));
                TWatchManagerApplication.getAppContext().sendBroadcast(intent);
                Log.d(TAG, "onConsoleMessage() send broadcasts...");
                return false;
            }
            Log.d(TAG, "getCommonInformation() status code is not included in server response, can't compare it");
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
