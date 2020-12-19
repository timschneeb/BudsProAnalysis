package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;

public class SendLogTaskV2 implements AsyncTaskClient {
    private static final String AUTHORITY = "com.sec.android.log.diagmonagent.sa";
    int Type;
    Uri commonUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/common");
    ContentValues cv;
    Uri logUri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
    private Context mContext;
    Uri returnUri = null;

    public SendLogTaskV2(Context context, int i, ContentValues contentValues) {
        this.mContext = context;
        this.Type = i;
        this.cv = contentValues;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public void run() {
        try {
            if (this.Type == 1) {
                this.returnUri = this.mContext.getContentResolver().insert(this.commonUri, this.cv);
            } else if (this.Type == 2) {
                this.returnUri = this.mContext.getContentResolver().insert(this.logUri, this.cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public int onFinish() {
        try {
            if (this.returnUri != null) {
                int parseInt = Integer.parseInt(this.returnUri.getLastPathSegment());
                Debug.LogD("SendLog Result = " + parseInt);
                boolean z = true;
                if (this.Type == 1) {
                    if (parseInt != 0) {
                        z = false;
                    }
                    Preferences.getPreferences(this.mContext).edit().putBoolean(Preferences.PREFS_KEY_SEND_COMMON_SUCCESS, z).apply();
                    Debug.LogD("Save Result = " + z);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
