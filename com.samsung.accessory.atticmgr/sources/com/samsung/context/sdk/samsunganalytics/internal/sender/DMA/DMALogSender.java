package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import java.util.Map;
import java.util.Queue;

public class DMALogSender extends BaseLogSender {
    private static final int TYPE_COMMON = 1;
    private static final int TYPE_LOG = 2;
    private DMABinder dmaBinder;
    private int dmaStatus = 0;
    private boolean isReset = false;

    public DMALogSender(Context context, Configuration configuration) {
        super(context, configuration);
        if (PolicyUtils.getSenderType() == 2) {
            this.dmaBinder = new DMABinder(context, new Callback<Void, String>() {
                /* class com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender.AnonymousClass1 */

                public Void onResult(String str) {
                    DMALogSender.this.sendCommon();
                    DMALogSender.this.sendAll();
                    return null;
                }
            });
            this.dmaBinder.bind();
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.LogSender
    public int send(Map<String, String> map) {
        if (PolicyUtils.getSenderType() == 3) {
            if (!Preferences.getPreferences(this.context).getBoolean(Preferences.PREFS_KEY_SEND_COMMON_SUCCESS, false)) {
                sendCommon();
            }
            ContentValues contentValues = new ContentValues();
            String str = map.get("pd");
            if (!TextUtils.isEmpty(str)) {
                contentValues.put("pd", str);
                map.remove("pd");
            }
            String str2 = map.get("ps");
            if (!TextUtils.isEmpty(str2)) {
                contentValues.put("ps", str2);
                map.remove("ps");
            }
            contentValues.put("tcType", Integer.valueOf(this.configuration.isEnableUseInAppLogging() ? 1 : 0));
            contentValues.put("agree", Integer.valueOf(this.configuration.getUserAgreement().isAgreement() ? 1 : 0));
            contentValues.put("tid", this.configuration.getTrackingId());
            contentValues.put("logType", getLogType(map).getAbbrev());
            contentValues.put("timeStamp", Long.valueOf(map.get("ts")));
            contentValues.put("body", makeBodyString(setCommonParamToLog(map)));
            this.executor.execute(new SendLogTaskV2(this.context, 2, contentValues));
        } else if (this.dmaBinder.isTokenfail()) {
            return -8;
        } else {
            int i = this.dmaStatus;
            if (i != 0) {
                return i;
            }
            insert(map);
            if (!this.dmaBinder.isBind()) {
                this.dmaBinder.bind();
            } else if (this.dmaBinder.getDmaInterface() != null) {
                sendAll();
                if (this.isReset) {
                    sendCommon();
                    this.isReset = false;
                }
            }
        }
        return this.dmaStatus;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendAll() {
        if (PolicyUtils.getSenderType() == 2 && this.dmaStatus == 0) {
            Queue<SimpleLog> queue = this.manager.get();
            while (!queue.isEmpty()) {
                this.executor.execute(new SendLogTask(this.dmaBinder.getDmaInterface(), this.configuration, queue.poll()));
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [int, boolean] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendCommon() {
        /*
        // Method dump skipped, instructions count: 178
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender.sendCommon():void");
    }

    public void reset() {
        this.isReset = true;
    }
}
