package com.samsung.context.sdk.samsunganalytics.internal.property;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PropertyLogBuildClient implements AsyncTaskClient {
    private Configuration mConfig;
    private Context mContext;
    private Map<String, String> mMap;

    public PropertyLogBuildClient(Context context, Configuration configuration) {
        this.mContext = context;
        this.mConfig = configuration;
    }

    /* JADX DEBUG: Type inference failed for r0v2. Raw type applied. Possible types: java.util.Map<java.lang.String, ?>, java.util.Map<java.lang.String, java.lang.String> */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public void run() {
        this.mMap = Preferences.getPropertyPreferences(this.mContext).getAll();
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public int onFinish() {
        boolean isAgreement = this.mConfig.getUserAgreement().isAgreement();
        if (Utils.isDMADataProvideVersion(this.mContext) || isAgreement) {
            Map<String, String> map = this.mMap;
            if (map == null || map.isEmpty()) {
                Debug.LogD("PropertyLogBuildClient", "No Property log");
                return 0;
            }
            if (this.mConfig.isAlwaysRunningApp()) {
                Utils.registerReceiver(this.mContext, this.mConfig);
            }
            if (!Utils.isSendProperty(this.mContext)) {
                return 0;
            }
            Debug.LogD("send Property Logs");
            HashMap hashMap = new HashMap();
            String valueOf = String.valueOf(System.currentTimeMillis());
            hashMap.put("ts", valueOf);
            hashMap.put("t", "pp");
            hashMap.put("cp", Utils.makeDelimiterString(Validation.checkSizeLimit(this.mMap), Utils.Depth.TWO_DEPTH));
            if (PolicyUtils.getSenderType() >= 3) {
                Uri parse = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
                hashMap.put("v", "6.05.033");
                hashMap.put("tz", String.valueOf(TimeUnit.MILLISECONDS.toMinutes((long) TimeZone.getDefault().getRawOffset())));
                ContentValues contentValues = new ContentValues();
                contentValues.put("tcType", Integer.valueOf(this.mConfig.isEnableUseInAppLogging() ? 1 : 0));
                contentValues.put("tid", this.mConfig.getTrackingId());
                contentValues.put("logType", LogType.UIX.getAbbrev());
                contentValues.put("timeStamp", valueOf);
                contentValues.put("agree", Integer.valueOf(isAgreement ? 1 : 0));
                contentValues.put("body", Utils.makeDelimiterString(hashMap, Utils.Depth.ONE_DEPTH));
                try {
                    this.mContext.getContentResolver().insert(parse, contentValues);
                } catch (IllegalArgumentException unused) {
                    Debug.LogD("Property send fail");
                }
            } else {
                Sender.get(this.mContext, PolicyUtils.getSenderType(), this.mConfig).send(hashMap);
            }
            return 0;
        }
        Debug.LogD("user do not agree Property");
        return 0;
    }
}
