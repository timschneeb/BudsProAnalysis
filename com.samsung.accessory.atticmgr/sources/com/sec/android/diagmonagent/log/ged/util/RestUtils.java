package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.sec.android.diagmonagent.common.NativeHelper;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.dao.Contracts;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.Result;
import com.sec.android.diagmonagent.log.ged.db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestUtils {
    private static final String API_VERSION = "/v2";
    private static final String AUTH_KEY = "ALLNEWDIAGMON-AUTH";
    private static final String DEVICE_KEY = String.copyValueOf(NativeHelper.getRandomId());
    private static final String DIAGMON_CHINA_SERVER_DOMAIN = "https://diagmon-apigw.samsung.com.cn";
    private static final String DIAGMON_SERVER_DOMAIN = "https://diagmon-serviceapi.samsungdm.com";
    private static final String DMA_PACKAGE_NAME = "com.sec.android.diagmonagent";
    private static final String DMA_SERVICE_ID = "x6g1q14r77";
    private static final String EVENT_REPORT = "/eventreport";
    private static final String KEY_AUTH_ID = "auth_identifier";
    private static final String KEY_BEARER = "Bearer";
    private static final String KEY_DEVICE_INFO = "deviceInfo";
    private static final String KEY_EVENT_INFO = "eventInfo";
    private static final String KEY_IDENTIFIER = "identifier";
    private static final String KEY_RESULT_INFO = "resultInfo";
    private static final String KEY_SERVER_ID = "server_id";
    private static final String KEY_SERVICE = "service";
    private static final String KEY_SERVICE_ID = "service_id";
    private static final String KEY_SIGNATURE = "signature";
    private static final String POLICY_DOWNLOAD = "/policy";
    private static final String POLICY_VERSION_INFO_CHINA_SERVER_DOMAIN = "https://diagmon-policy.samsung.com.cn";
    private static final String POLICY_VERSION_INFO_SERVER_DOMAIN = "https://diagmon-policy.samsungdm.com";
    private static final String RESULT_REPORT = "/eventreport/result";
    private static final String SERVER_KEY = "181nu26w3j";
    private static final String SERVER_KEY_PRD = "s7gna8vjt1";
    private static final String SERVER_PASS = "C8351E0E32662AF977BA575179629B46";
    private static final String SERVICE_REGISTRATION = "/common/serviceregistration";
    private static final String TOKEN_REFRESH = "/common/authtoken";
    public static final String URI_EVENT_REPORT = "/v2/eventreport";
    public static final String URI_POLICY_DOWNLOAD = "/v2/policy";
    public static final String URI_RESULT_REPORT = "/v2/eventreport/result";
    public static final String URI_SERVICE_REGISTRATION = "/v2/common/serviceregistration";
    public static final String URI_TOKEN_RERESH = "/v2/common/authtoken";
    private static final String URI_VERSION_CHECK = "/policy/version.json";

    public static final class ApiErrorCode {
        public static final String ERROR_CODE_UNAUTHORIZED = "4403";
        public static final String SERVICE_ID_RETIRED = "1101";
        public static final String SERVICE_ID_UNAUTHORIZED = "1100";
        public static final String TOKEN_EXPIRED = "4412";
        public static final String TOKEN_FORGED = "4411";
        public static final String TOKEN_NOT_VALID = "4410";
    }

    public static String getDmaServiceId() {
        return DMA_SERVICE_ID;
    }

    public static String getServerAddress(Context context) {
        return DIAGMON_SERVER_DOMAIN;
    }

    public static String getVersionCheckDomain(Context context) {
        return POLICY_VERSION_INFO_SERVER_DOMAIN;
    }

    public static String getAuth(Context context, String str, String str2, String str3, String str4) {
        AppLog.d("getAuth(): " + makeAuth(context, str, str2, str3, str4));
        return makeAuth(context, str, str2, str3, str4);
    }

    public static String getAuth(Context context, String str) {
        AppLog.d("getJwtAuth(): " + makeAuth(context, str));
        return makeAuth(context, str);
    }

    private static String makeAuth(Context context, String str, String str2, String str3, String str4) {
        String signature = getSignature(context, str2, str, str3, str4);
        AppLog.d("hash = " + signature);
        return "Bearer=\"" + str4 + "\"," + KEY_AUTH_ID + "=\"" + AUTH_KEY + "\"," + KEY_SERVER_ID + "=\"" + SERVER_KEY_PRD + "\"," + "service_id" + "=\"" + str2 + "\"," + KEY_IDENTIFIER + "=\"" + getIdentifier(context) + "\"," + KEY_SIGNATURE + "=\"" + signature + "\"";
    }

    private static String makeAuth(Context context, String str) {
        String signature = getSignature(context, str);
        AppLog.d("hash = " + signature);
        return "auth_identifier=\"ALLNEWDIAGMON-AUTH\",server_id=\"s7gna8vjt1\",service_id=\"x6g1q14r77\",identifier=\"" + getIdentifier(context) + "\"," + KEY_SIGNATURE + "=\"" + signature + "\"";
    }

    public static String getSignature(Context context, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        String str5 = "s7gna8vjt1:com.sec.android.diagmonagent:" + getIdentifier(context) + ":" + calculateKey(context);
        if (!str3.isEmpty()) {
            str2 = str2 + ":" + str3;
        }
        AppLog.d("part1 = " + str5);
        AppLog.d("part2 = " + str2);
        String str6 = new String(Base64.encode(getSHADigest(str5), 2));
        String str7 = new String(Base64.encode(getSHADigest(str2), 2));
        sb.append(str6);
        sb.append(":");
        sb.append(str);
        sb.append(":");
        sb.append(str4);
        sb.append(":");
        sb.append(str7);
        AppLog.d("signature = " + ((Object) sb));
        return new String(Base64.encode(getSHADigest(sb.toString()), 2));
    }

    public static String getSignature(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        String str2 = "s7gna8vjt1:com.sec.android.diagmonagent:" + getIdentifier(context) + ":" + calculateKey(context);
        AppLog.d("part1 = " + str2);
        AppLog.d("part2 = " + str);
        String str3 = new String(Base64.encode(getSHADigest(str2), 2));
        String str4 = new String(Base64.encode(getSHADigest(str), 2));
        sb.append(str3);
        sb.append(":");
        sb.append(DMA_SERVICE_ID);
        sb.append(":");
        sb.append(str4);
        AppLog.d("signature = " + ((Object) sb));
        return new String(Base64.encode(getSHADigest(sb.toString()), 2));
    }

    private static String getIdentifier(Context context) {
        return getRandomDeviceId(context);
    }

    private static String getRandomDeviceId(Context context) {
        String randomDeviceId = PreferenceUtils.getRandomDeviceId(context);
        if (TextUtils.isEmpty(randomDeviceId)) {
            randomDeviceId = generateRandomDeviceId(context);
            PreferenceUtils.removeRandomDeviceId(context);
            PreferenceUtils.setRandomDeviceId(context, randomDeviceId);
        }
        AppLog.d("Stored randomId : " + randomDeviceId);
        return randomDeviceId;
    }

    public static String getVersionCheckUri(Context context) {
        return PreferenceUtils.getPolicyVersionInfoUrl(context, URI_VERSION_CHECK);
    }

    private static String generateRandomDeviceId(Context context) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr = new byte[16];
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            secureRandom.nextBytes(bArr);
            try {
                sb.append("0123456789abcdefghijklmjopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.abs(new BigInteger(bArr).longValue()) % ((long) 62))));
            } catch (Exception e) {
                AppLog.e("failed to generate RandomDeviceId : " + e.getMessage());
                return "";
            }
        }
        String sb2 = sb.toString();
        AppLog.d("Generated randomId : " + sb2);
        return sb2;
    }

    private static byte[] getSHADigest(String str) {
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

    private static String calculateKey(Context context) {
        String str = DEVICE_KEY;
        return ((((((((((((((((((((((((((((((("" + str.charAt(4)) + str.charAt(14)) + str.charAt(13)) + str.charAt(33)) + str.charAt(0)) + str.charAt(27)) + str.charAt(19)) + str.charAt(31)) + str.charAt(26)) + str.charAt(36)) + str.charAt(34)) + str.charAt(30)) + str.charAt(25)) + str.charAt(2)) + str.charAt(1)) + str.charAt(41)) + str.charAt(35)) + str.charAt(28)) + str.charAt(24)) + str.charAt(6)) + str.charAt(32)) + str.charAt(37)) + str.charAt(38)) + str.charAt(42)) + str.charAt(18)) + str.charAt(12)) + str.charAt(8)) + str.charAt(5)) + str.charAt(17)) + str.charAt(43)) + str.charAt(11)) + str.charAt(7);
    }

    public static JSONObject makeSRObject(Context context, ServiceInfo serviceInfo) {
        JSONObject deviceInfo = DeviceUtils.getDeviceInfo(context);
        if (deviceInfo != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                jSONObject.put("serviceId", serviceInfo.getServiceId());
                jSONObject.put("deviceId", serviceInfo.getDeviceId());
                jSONObject.put("serviceVersion", serviceInfo.getServiceVersion());
                jSONObject.put("serviceAgreeType", serviceInfo.getServiceAgreeType());
                jSONObject.put("sdkType", serviceInfo.getSdkType());
                jSONObject.put("sdkVersion", serviceInfo.getSdkVersion());
                jSONObject.put("status", serviceInfo.getStatus());
                jSONObject.put("trackingId", serviceInfo.getTrackingId());
                jSONArray.put(jSONObject);
                deviceInfo.put("service", jSONArray);
                return new JSONObject().put(KEY_DEVICE_INFO, deviceInfo);
            } catch (JSONException unused) {
                AppLog.e("JSONException occurred making service object");
            }
        }
        return null;
    }

    public static JSONObject makeERObject(Context context, Event event) {
        JSONObject deviceInfo = DeviceUtils.getDeviceInfo(context);
        if (deviceInfo != null) {
            try {
                deviceInfo.put("deviceId", event.getDeviceId());
                deviceInfo.put("eventId", event.getEventId());
                deviceInfo.put(Contracts.EventContract.COLUMN_NETWORK_MODE, event.isNetworkMode());
                deviceInfo.put("status", event.getStatus());
                deviceInfo.put(Contracts.EventContract.COLUMN_RETRY_COUNT, event.getRetryCount());
                deviceInfo.put("serviceId", event.getServiceId());
                deviceInfo.put("serviceVersion", event.getServiceVersion());
                deviceInfo.put("serviceAgreeType", event.getServiceAgreeType());
                deviceInfo.put(Contracts.EventContract.COLUMN_LOG_PATH, event.getLogPath());
                deviceInfo.put(Contracts.EventContract.COLUMN_S3_PATH, event.getS3Path());
                deviceInfo.put("sdkType", event.getSdkType());
                deviceInfo.put("sdkVersion", event.getSdkVersion());
                deviceInfo.put("serviceDefinedKey", event.getServiceDefinedKey());
                deviceInfo.put("errorCode", event.getErrorCode());
                deviceInfo.put("description", event.getDescription());
                deviceInfo.put("relayClientType", event.getRelayClientType());
                deviceInfo.put("relayClientVersion", event.getRelayClientVersion());
                deviceInfo.put("extension", event.getExtension());
                deviceInfo.put("timestamp", event.getTimestamp());
                deviceInfo.put(Contracts.EventContract.COLUMN_EXPIRATION_TIME, event.getExpirationTime());
                return new JSONObject().put(KEY_EVENT_INFO, deviceInfo);
            } catch (JSONException unused) {
                AppLog.e("JSONException occurred making event object");
            }
        }
        return null;
    }

    public static JSONObject makeRRObject(Event event) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("eventId", event.getEventId());
            jSONObject.put(Contracts.ResultContract.COLUMN_CLIENT_STATUS_CODE, event.getStatus());
            return new JSONObject().put(KEY_RESULT_INFO, jSONObject);
        } catch (JSONException unused) {
            AppLog.e("JSONException occurred making result object");
            return null;
        }
    }

    public static JSONObject makeRRObject(Result result) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("eventId", result.getEventId());
            jSONObject.put(Contracts.ResultContract.COLUMN_CLIENT_STATUS_CODE, result.getClientStatusCode());
            return new JSONObject().put(KEY_RESULT_INFO, jSONObject);
        } catch (JSONException unused) {
            AppLog.e("JSONException occurred making result object");
            return null;
        }
    }

    public static boolean isTokenNeedToBeUpdated(Context context, Response response) {
        if (isTokenExist(context) && !isTokenExpired(response)) {
            return false;
        }
        AppLog.d("Token need to be updated.");
        return true;
    }

    private static boolean isTokenExist(Context context) {
        return !PreferenceUtils.getJwtToken(context).isEmpty();
    }

    private static boolean isTokenExpired(Response response) {
        AppLog.d("Check token expired : " + response.getCode() + response.getBody());
        if (response.getBody() != null) {
            return response.getCode() == 401 && (response.getBody().contains(ApiErrorCode.TOKEN_NOT_VALID) || response.getBody().contains(ApiErrorCode.TOKEN_FORGED) || response.getBody().contains(ApiErrorCode.TOKEN_EXPIRED));
        }
        AppLog.w("Response body is null");
        return response.getCode() == 401;
    }
}
