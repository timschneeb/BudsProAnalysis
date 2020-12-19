package com.sec.android.diagmonagent.log.provider.threadExecutor;

import android.content.Context;
import android.os.Bundle;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;

public class ANRExecutor implements Runnable {
    private boolean anrLogging;
    private Context context;
    private String serviceId;

    public ANRExecutor(Context context2, String str, boolean z) {
        this.context = context2;
        this.serviceId = str;
        this.anrLogging = z;
    }

    public void run() {
        if (2 == DiagMonUtil.checkDMA(this.context)) {
            Bundle bundle = new Bundle();
            bundle.putString("serviceId", this.serviceId);
            DiagMonUtil.printResultfromDMA(this.context.getContentResolver().call(DiagMonUtil.URI, "anr_logging", String.valueOf(this.anrLogging), bundle));
            return;
        }
        AppLog.w("ANR Logging does not support");
    }
}
