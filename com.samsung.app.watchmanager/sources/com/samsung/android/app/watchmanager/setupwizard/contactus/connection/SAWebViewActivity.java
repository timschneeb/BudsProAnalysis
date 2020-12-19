package com.samsung.android.app.watchmanager.setupwizard.contactus.connection;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.watchmanager.R;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Random;

public class SAWebViewActivity extends Activity {
    public static final String ACTION_SA_WEBVIEW_LOGIN_SUCCESS = "com.samsung.android.app.watchmanager.ACTION_SA_WEBVIEW_LOGIN_SUCCESS";
    public static final String SCS_TOKEN_APP_ALIAS = "wmanager";
    public static final String SCS_TOKEN_REQUEST_HTTPGET_URL = "https://account.samsung.com/accounts/wmanager/signInGate?";
    public static final String SCS_TOKEN_REQUEST_HTTPGET_URL_CHN = "https://account.samsung.cn/accounts/wmanager/signInGate?";
    public static final String SCS_TOKEN_REQUEST_HTTPGET_URL_US = "https://us.account.samsung.com/accounts/wmanager/signInGate?";
    public static final int SCS_WEBVIEW_NETWORK_ERROR = -3;
    private static final String TAG = "SAWebViewActivity";
    private SAWebChromeClient mSAWebChromeClient;
    private String mSerial = "";
    private String mServerUrl;
    private int mState;
    private String mUid;
    private WebView wv;

    private String encodeURLParam(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    private void initWebViewInfo() {
        this.mSerial = Settings.Secure.getString(getContentResolver(), "android_id");
        String locale = Locale.getDefault().toString();
        this.mUid = Settings.Secure.getString(getContentResolver(), "android_id");
        this.mState = new Random().nextInt(100000000);
        String mcc = HostManagerUtilsNetwork.getMCC(this);
        String str = "460".equals(mcc) ? SCS_TOKEN_REQUEST_HTTPGET_URL_CHN : ("310".equals(mcc) || "450".equals(mcc)) ? SCS_TOKEN_REQUEST_HTTPGET_URL_US : SCS_TOKEN_REQUEST_HTTPGET_URL;
        String str2 = TAG;
        Log.d(str2, "initWebViewInfo() selected requestUrl : " + str);
        this.mServerUrl = str + "&clientId=" + GlobalConst.SCS_CLIENT_ID_OF_HM + "&tokenType=TOKEN&state=" + this.mState + "&deviceType=APP&deviceModelID=" + encodeURLParam(Build.MODEL) + "&deviceUniqueID=" + encodeURLParam(this.mUid) + "&devicePhysicalAddressText=" + encodeURLParam(this.mSerial) + "&deviceOSVersion=" + Build.VERSION.SDK_INT + "&competitorDeviceYNFlag=Y&rgtRtnHttpMethod=GET&locale=" + encodeURLParam(locale);
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("RL::countryCode = ");
        sb.append(locale);
        Log.d(str3, sb.toString());
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        initWebViewInfo();
        setContentView(R.layout.sa_webview);
        this.wv = (WebView) findViewById(R.id.webv);
        WebSettings settings = this.wv.getSettings();
        settings.setJavaScriptEnabled(true);
        this.wv.addJavascriptInterface(this, "android");
        this.wv.setLayerType(1, null);
        settings.setCacheMode(2);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.mSAWebChromeClient = new SAWebChromeClient(this.mState);
        this.wv.setWebChromeClient(this.mSAWebChromeClient);
        this.wv.setWebViewClient(new WebViewClient() {
            /* class com.samsung.android.app.watchmanager.setupwizard.contactus.connection.SAWebViewActivity.AnonymousClass1 */

            public void onPageFinished(WebView webView, String str) {
                Log.d(SAWebViewActivity.TAG, "onPageFinished() called..");
                if (!TextUtils.isEmpty(str) && str.contains("closedAction=signInSuccess")) {
                    webView.loadUrl("javascript:console.log(document.getElementsByTagName('html')[0].innerHTML);");
                    Log.d(SAWebViewActivity.TAG, "RL::SCS::chrome::onConsoleMsg : finish webview activity!!");
                    SAWebViewActivity.this.setResult(-1);
                    SAWebViewActivity.this.finish();
                    SAWebViewActivity.this.overridePendingTransition(0, 0);
                }
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                SAWebViewActivity.this.setResult(-3);
                SAWebViewActivity.this.finish();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (!str.contains("close=true")) {
                    return false;
                }
                if (str.contains("closedAction")) {
                    Log.d(SAWebViewActivity.TAG, "url contains closed action");
                } else {
                    webView.loadUrl(SAWebViewActivity.this.mServerUrl);
                }
                return false;
            }
        });
        this.wv.resumeTimers();
        this.wv.loadUrl(this.mServerUrl);
    }
}
