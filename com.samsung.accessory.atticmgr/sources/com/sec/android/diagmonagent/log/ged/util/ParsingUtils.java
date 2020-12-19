package com.sec.android.diagmonagent.log.ged.util;

import android.util.Log;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.Constants;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.EventReportResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyVersionResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.ServiceRegistrationResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.TokenResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParsingUtils {
    public static TokenResponse parseTokenResponse(String str) {
        TokenResponse tokenResponse = new TokenResponse();
        try {
            tokenResponse.setToken(new JSONObject(str).getString(Constants.TokenResponseConstants.TOKEN));
        } catch (JSONException unused) {
            Log.e(DeviceUtils.TAG, "JSONException occurred while parsing token response");
        }
        return tokenResponse;
    }

    public static PolicyVersionResponse parsePolicyVersionResponse(String str) {
        PolicyVersionResponse policyVersionResponse = new PolicyVersionResponse();
        try {
            JSONObject jSONObject = new JSONObject(str);
            policyVersionResponse.setUrl(jSONObject.getString("url"));
            policyVersionResponse.setLatestDefault(jSONObject.getString(Constants.PolicyResponseConstants.LATEST_DEFAULT));
        } catch (JSONException unused) {
            Log.e(DeviceUtils.TAG, "JSONException occurred while parsing policy version info");
        }
        return policyVersionResponse;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0138  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyResponse parsePolicyResponse(java.lang.String r17, java.lang.String r18) {
        /*
        // Method dump skipped, instructions count: 394
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.util.ParsingUtils.parsePolicyResponse(java.lang.String, java.lang.String):com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyResponse");
    }

    public static ServiceRegistrationResponse parseServiceRegistrationResponse(String str, String str2) {
        ServiceRegistrationResponse serviceRegistrationResponse = new ServiceRegistrationResponse();
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("service");
            int i = 0;
            while (true) {
                if (i >= jSONArray.length()) {
                    break;
                } else if (str2.equals(jSONArray.getJSONObject(i).getString("id"))) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject.has("documentId")) {
                        serviceRegistrationResponse.setDocumentId(jSONObject.getString("documentId"));
                    }
                    if (jSONObject.has("id")) {
                        serviceRegistrationResponse.setId(jSONObject.getString("id"));
                    }
                    if (jSONObject.has(Constants.ServiceResponseConstants.STATUS_CODE)) {
                        serviceRegistrationResponse.setStatusCode(jSONObject.getString(Constants.ServiceResponseConstants.STATUS_CODE));
                    }
                    if (jSONObject.has("errorCode")) {
                        serviceRegistrationResponse.setErrorCode(jSONObject.getString("errorCode"));
                    }
                    if (jSONObject.has("errorMessage")) {
                        serviceRegistrationResponse.setErrorMessage(jSONObject.getString("errorMessage"));
                    }
                } else {
                    i++;
                }
            }
        } catch (JSONException unused) {
            Log.e(DeviceUtils.TAG, "JSONException occurred while parsing service response");
        }
        return serviceRegistrationResponse;
    }

    public static EventReportResponse parseEventReportResponse(String str) {
        EventReportResponse eventReportResponse = new EventReportResponse();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("eventId")) {
                eventReportResponse.setEventId(jSONObject.getString("eventId"));
            }
            if (jSONObject.has(Constants.EventResponseConstants.PRE_SIGNED_URL)) {
                eventReportResponse.setPreSignedURL(jSONObject.getString(Constants.EventResponseConstants.PRE_SIGNED_URL));
            }
        } catch (JSONException unused) {
            Log.e(DeviceUtils.TAG, "JSONException occurred while parsing event response");
        }
        return eventReportResponse;
    }
}
