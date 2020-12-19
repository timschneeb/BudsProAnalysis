package com.samsung.android.app.twatchmanager.update;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class QAPasswordCheckThread extends Thread {
    private static final String NEED_TO_SUBSTRING = "SAMSUNG-";
    private static final int REQUEST_SERVER_CONNECT_TIMEOUT = 5000;
    private static final int REQUEST_SERVER_READ_TIMEOUT = 15000;
    private static final String REQUEST_URL = "https://qa-odc.samsungapps.com/ods.as";
    public static final String TAG = "QAPasswordCheckThread";
    private IQAPasswordCallback mCallback;
    private String mGUID;
    private String mPassword;

    public interface IQAPasswordCallback {
        void resultCallback(boolean z);
    }

    public enum ReqType {
        REQ_CHECK_PASSWORD("verificationAuthority", "2233", "authority", 3);
        
        public int mNumParam;
        public String mReqId;
        public String mReqName;
        public String mResultValueTag;

        private ReqType(String str, String str2, String str3, int i) {
            this.mReqName = str;
            this.mReqId = str2;
            this.mNumParam = i;
            this.mResultValueTag = str3;
        }
    }

    public QAPasswordCheckThread(String str, String str2, IQAPasswordCallback iQAPasswordCallback) {
        this.mPassword = str;
        this.mGUID = str2;
        this.mCallback = iQAPasswordCallback;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f6 A[SYNTHETIC, Splitter:B:42:0x00f6] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x011b A[SYNTHETIC, Splitter:B:59:0x011b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String galaxyStoreAPIRequest(com.samsung.android.app.twatchmanager.update.QAPasswordCheckThread.ReqType r6, java.lang.String r7, android.content.ContentValues r8) {
        /*
        // Method dump skipped, instructions count: 292
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.update.QAPasswordCheckThread.galaxyStoreAPIRequest(com.samsung.android.app.twatchmanager.update.QAPasswordCheckThread$ReqType, java.lang.String, android.content.ContentValues):java.lang.String");
    }

    private String getParamsTag(ReqType reqType, ContentValues contentValues) {
        StringBuilder sb = new StringBuilder();
        if (contentValues == null || contentValues.size() != reqType.mNumParam) {
            Log.d(TAG, "getParamsTag() parameter count doesn't match..");
        } else {
            for (String str : contentValues.keySet()) {
                sb.append("<param name=\"" + str + "\">" + contentValues.get(str) + "</param>");
            }
        }
        sb.append("</request>");
        sb.append("</SamsungProtocol>");
        return sb.toString();
    }

    private String getRequestCommonHeader(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        int type = activeNetworkInfo.getType();
        String str = Build.MODEL;
        if (str.startsWith(NEED_TO_SUBSTRING) && 8 < str.length()) {
            str = str.substring(8);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<SamsungProtocol odcVersion=\"4.2.04-5\" version=\"6.4\"");
        sb.append(" odcType=\"04\"");
        sb.append(" version2=\"3\"");
        sb.append(" networkType=\"" + type + "\"");
        sb.append(" mnc=\"" + HostManagerUtilsNetwork.getMNC(context) + "\"");
        sb.append(" mcc=\"" + HostManagerUtilsNetwork.getMCC(context) + "\"");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" deviceModel=\"");
        if (!HostManagerUtils.isSamsungDevice()) {
            str = "1920x1080";
        }
        sb2.append(str);
        sb2.append("\"");
        sb.append(sb2.toString());
        sb.append(" csc=\"" + HostManagerUtilsNetwork.getCSC() + "\"");
        sb.append(" openApiVersion=\"" + Build.VERSION.SDK_INT + "\"");
        sb.append(" lang=\"" + Locale.getDefault().getDisplayLanguage() + "\"");
        sb.append(" filter=\"1\">");
        return sb.toString();
    }

    private String getRequestTag(ReqType reqType) {
        StringBuilder sb = new StringBuilder();
        sb.append("<request ");
        sb.append("name=\"" + reqType.mReqName + "\" id=\"" + reqType.mReqId + "\"");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" numParam=\"");
        sb2.append(reqType.mNumParam);
        sb2.append("\"");
        sb.append(sb2.toString());
        sb.append(" transactionId=\"" + getTransactionID() + "\">");
        return sb.toString();
    }

    private String getResultFromXML(ReqType reqType, XmlPullParser xmlPullParser) {
        Log.d(TAG, "getResultFromXML() starts...");
        String str = null;
        try {
            int eventType = xmlPullParser.getEventType();
            while (true) {
                if (eventType == 1) {
                    break;
                }
                String name = xmlPullParser.getName();
                if (eventType == 2 && "value".equalsIgnoreCase(name) && TextUtils.equals(xmlPullParser.getAttributeValue(0), reqType.mResultValueTag) && xmlPullParser.next() == 4) {
                    Log.d(TAG, "getResultFromXML() found result value tag");
                    str = xmlPullParser.getText();
                    break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (HostManagerUtils.DEBUGGABLE()) {
            String str2 = TAG;
            Log.d(str2, "getResultFromXML() result : " + str);
        }
        return str;
    }

    private String getTransactionID() {
        Context appContext = TWatchManagerApplication.getAppContext();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String string = Settings.Secure.getString(appContext.getContentResolver(), "android_id");
        String obj = new SimpleDateFormat("yyyyMMddHH").toString();
        String str = string + obj + "SamsungGM";
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b2 : digest) {
                sb.append(Integer.toString((b2 & 255) + 256, 16).substring(1));
            }
            String sb2 = sb.toString();
            String str2 = format + (sb2.length() > 8 ? sb2.substring(0, 7) : null) + new SimpleDateFormat("SSS").format(date);
            Log.d(TAG, "getTransactionID - result : " + str2);
            return str2;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return obj;
        }
    }

    public SSLContext createSystemCertificates() {
        KeyStoreException e;
        CertificateException e2;
        NoSuchAlgorithmException e3;
        IOException e4;
        KeyManagementException e5;
        SSLContext sSLContext = null;
        try {
            KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
            instance.load(null, null);
            KeyStore instance2 = KeyStore.getInstance("AndroidCAStore");
            instance2.load(null, null);
            Enumeration<String> aliases = instance2.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                X509Certificate x509Certificate = (X509Certificate) instance2.getCertificate(nextElement);
                if (nextElement.startsWith("system:")) {
                    instance.setCertificateEntry(nextElement, x509Certificate);
                }
            }
            TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance3.init(instance);
            SSLContext instance4 = SSLContext.getInstance("TLS");
            try {
                instance4.init(null, instance3.getTrustManagers(), null);
                return instance4;
            } catch (KeyStoreException e6) {
                e = e6;
                sSLContext = instance4;
                e.printStackTrace();
                return sSLContext;
            } catch (CertificateException e7) {
                e2 = e7;
                sSLContext = instance4;
                e2.printStackTrace();
                return sSLContext;
            } catch (NoSuchAlgorithmException e8) {
                e3 = e8;
                sSLContext = instance4;
                e3.printStackTrace();
                return sSLContext;
            } catch (IOException e9) {
                e4 = e9;
                sSLContext = instance4;
                e4.printStackTrace();
                return sSLContext;
            } catch (KeyManagementException e10) {
                e5 = e10;
                sSLContext = instance4;
                e5.printStackTrace();
                return sSLContext;
            }
        } catch (KeyStoreException e11) {
            e = e11;
            e.printStackTrace();
            return sSLContext;
        } catch (CertificateException e12) {
            e2 = e12;
            e2.printStackTrace();
            return sSLContext;
        } catch (NoSuchAlgorithmException e13) {
            e3 = e13;
            e3.printStackTrace();
            return sSLContext;
        } catch (IOException e14) {
            e4 = e14;
            e4.printStackTrace();
            return sSLContext;
        } catch (KeyManagementException e15) {
            e5 = e15;
            e5.printStackTrace();
            return sSLContext;
        }
    }

    public String createXmlForRequest(ReqType reqType, ContentValues contentValues) {
        String str = getRequestCommonHeader(TWatchManagerApplication.getAppContext()) + getRequestTag(reqType) + getParamsTag(reqType, contentValues);
        Log.d(TAG, "createXmlForGetUrl() XML request format =\n" + str);
        return str;
    }

    public void run() {
        String str = TAG;
        Log.d(str, "run() starts... inputPassword : " + this.mPassword);
        ContentValues contentValues = new ContentValues();
        contentValues.put("latestCountryCode", HostManagerUtilsNetwork.getMCC(TWatchManagerApplication.getAppContext()));
        contentValues.put("whoAmI", "odc");
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("password", this.mPassword);
        contentValues2.put(GroupInfo.ImageInfo.ATTR_TYPE, "1");
        contentValues2.put("guid", this.mGUID);
        this.mCallback.resultCallback(TextUtils.equals("1", galaxyStoreAPIRequest(ReqType.REQ_CHECK_PASSWORD, REQUEST_URL, contentValues2)));
    }
}
