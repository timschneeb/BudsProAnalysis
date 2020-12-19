package com.samsung.android.fotaagent.network.rest;

import android.text.TextUtils;
import android.util.Base64;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RestHeader {
    private static final String OAUTH_SIGNATURE_METHOD = "HmacSHA1";
    private static final String OAUTH_VERSION = "1.0";
    public static RestHeader instance = new RestHeader();

    private static class HeaderKey {
        private static final String ACCEPT = "Accept";
        private static final String ACCEPT_ENCODING = "Accept-Encoding";
        private static final String AUTH = "Authorization";
        private static final String CONNECTION = "Connection";
        private static final String CONTENT_TYPE = "Content-Type";
        private static final String OSP_VERSION = "x-osp-version";
        private static final String USER_AGENT = "User-Agent";

        private HeaderKey() {
        }
    }

    private static class HeaderValue {
        private static final String ACCEPT_ALL = "*, */*";
        private static final String ACCEPT_ENCODING = "identity";
        private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
        private static final String OSP_VERSION_V1 = "v1";
        private static final String TEXT_XML = "text/xml";
        private static final String USER_AGENT = "SAMSUNG-Android";

        private HeaderValue() {
        }
    }

    private static class AuthKey {
        private static final String KEY = "oauth_consumer_key";
        private static final String METHOD = "oauth_signature_method";
        private static final String NONCE = "oauth_nonce";
        private static final String SIGNATURE = "oauth_signature";
        private static final String TIMESTAMP = "oauth_timestamp";
        private static final String VERSION = "oauth_version";

        private AuthKey() {
        }
    }

    /* access modifiers changed from: package-private */
    public Map<String, String> getHeaders(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(HttpNetworkInterface.XTP_HTTP_USER_AGENT, "SAMSUNG-Android");
        hashMap.put(HttpNetworkInterface.XTP_HTTP_ACCEPT, "*, */*");
        hashMap.put("Accept-Encoding", "identity");
        hashMap.put(HttpNetworkInterface.XTP_HTTP_CONTENT_TYPE, "text/xml");
        hashMap.put(HttpNetworkInterface.XTP_HTTP_CONNECTION, "keep-alive");
        hashMap.put("x-osp-version", "v1");
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("Authorization", str);
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public String generateAuth(String str, String str2, String str3, String str4, String str5, long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("oauth_consumer_key", str);
        hashMap.put("oauth_nonce", generateRandomToken(10));
        hashMap.put("oauth_signature_method", OAUTH_SIGNATURE_METHOD);
        hashMap.put("oauth_timestamp", Long.toString(j));
        hashMap.put("oauth_version", "1.0");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue() + ",");
        }
        String generateSignature = generateSignature(str2, str3, str4, str5, hashMap);
        if (generateSignature != null) {
            sb.append("oauth_signature=" + generateSignature);
        }
        return sb.toString();
    }

    private String generateRandomToken(int i) {
        try {
            byte[] bArr = new byte[(i / 2)];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(bArr);
            return toHex(bArr);
        } catch (NoSuchAlgorithmException e) {
            Log.printStackTrace(e);
            return null;
        }
    }

    private String toHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & 255;
            stringBuffer.append("0123456789abcdef".charAt(i >>> 4));
            stringBuffer.append("0123456789abcdef".charAt(i & 15));
        }
        return stringBuffer.toString();
    }

    private String generateSignature(String str, String str2, String str3, String str4, Map<String, String> map) {
        try {
            return new String(Base64.encode(computeSignature(str, generateSignatureSource(str2, str3, str4, map)), 2), HttpNetworkInterface.XTP_HTTP_UTF8);
        } catch (Exception e) {
            Log.printStackTrace(e);
            return null;
        }
    }

    private String generateSignatureSource(String str, String str2, String str3, Map<String, String> map) throws URISyntaxException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.toUpperCase(Locale.getDefault()));
        stringBuffer.append("&");
        stringBuffer.append(normalizeUrlWithOAuthSpec(str2));
        stringBuffer.append("&");
        stringBuffer.append(normalizeParameters(map));
        if (str3 != null && str3.length() > 0) {
            stringBuffer.append("&");
            stringBuffer.append(str3);
        }
        return stringBuffer.toString();
    }

    private byte[] computeSignature(String str, String str2) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(HttpNetworkInterface.XTP_HTTP_UTF8), OAUTH_SIGNATURE_METHOD);
        Mac instance2 = Mac.getInstance(OAUTH_SIGNATURE_METHOD);
        instance2.init(secretKeySpec);
        return instance2.doFinal(str2.getBytes(HttpNetworkInterface.XTP_HTTP_UTF8));
    }

    private String normalizeUrlWithOAuthSpec(String str) throws URISyntaxException {
        int lastIndexOf;
        URI uri = new URI(str);
        String lowerCase = uri.getScheme().toLowerCase(Locale.getDefault());
        String lowerCase2 = uri.getAuthority().toLowerCase(Locale.getDefault());
        if (((lowerCase.equals(HttpNetworkInterface.XTP_NETWORK_TYPE_HTTP) && uri.getPort() == 80) || (lowerCase.equals(HttpNetworkInterface.XTP_NETWORK_TYPE_HTTPS) && uri.getPort() == 443)) && (lastIndexOf = lowerCase2.lastIndexOf(58)) >= 0) {
            lowerCase2 = lowerCase2.substring(0, lastIndexOf);
        }
        String rawPath = uri.getRawPath();
        if (rawPath == null || rawPath.length() <= 0) {
            rawPath = "/";
        }
        return urlEncodeWithOAuthSpec(lowerCase + "://" + lowerCase2 + rawPath);
    }

    private String normalizeParameters(Map<String, String> map) {
        TreeMap treeMap = new TreeMap(map);
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            stringBuffer.append(((String) entry.getKey()) + "=" + ((String) entry.getValue()).replaceAll("\"", "").replaceAll("&quot;", ""));
            if (it.hasNext()) {
                stringBuffer.append("&");
            }
        }
        return urlEncodeWithOAuthSpec(stringBuffer.toString());
    }

    private String urlEncodeWithOAuthSpec(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, HttpNetworkInterface.XTP_HTTP_UTF8).replace("+", "%20").replace("*", "%2A").replace("%7E", "~").replace("%25", "%");
        } catch (Exception e) {
            Log.printStackTrace(e);
            return "";
        }
    }
}
