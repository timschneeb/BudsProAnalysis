package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.Constants;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* access modifiers changed from: package-private */
public final class zzay {
    private static final long zzdn = TimeUnit.DAYS.toMillis(7);
    private final long timestamp;
    final String zzbv;
    private final String zzdo;

    private zzay(String str, String str2, long j) {
        this.zzbv = str;
        this.zzdo = str2;
        this.timestamp = j;
    }

    static zzay zzi(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.startsWith("{")) {
            return new zzay(str, null, 0);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzay(jSONObject.getString(Constants.TokenResponseConstants.TOKEN), jSONObject.getString(Preferences.PREFS_KEY_APP_VERSION), jSONObject.getLong("timestamp"));
        } catch (JSONException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
            sb.append("Failed to parse token: ");
            sb.append(valueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return null;
        }
    }

    static String zza(String str, String str2, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(Constants.TokenResponseConstants.TOKEN, str);
            jSONObject.put(Preferences.PREFS_KEY_APP_VERSION, str2);
            jSONObject.put("timestamp", j);
            return jSONObject.toString();
        } catch (JSONException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
            sb.append("Failed to encode token: ");
            sb.append(valueOf);
            Log.w("FirebaseInstanceId", sb.toString());
            return null;
        }
    }

    static String zzb(zzay zzay) {
        if (zzay == null) {
            return null;
        }
        return zzay.zzbv;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzj(String str) {
        return System.currentTimeMillis() > this.timestamp + zzdn || !str.equals(this.zzdo);
    }
}
