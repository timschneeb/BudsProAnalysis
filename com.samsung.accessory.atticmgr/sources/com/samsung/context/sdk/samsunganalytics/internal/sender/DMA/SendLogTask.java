package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.sa.IDMAInterface;

public class SendLogTask implements AsyncTaskClient {
    private Configuration configuration;
    private IDMAInterface dmaInterface;
    private SimpleLog log;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public int onFinish() {
        return 0;
    }

    SendLogTask(IDMAInterface iDMAInterface, Configuration configuration2, SimpleLog simpleLog) {
        this.log = simpleLog;
        this.dmaInterface = iDMAInterface;
        this.configuration = configuration2;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public void run() {
        try {
            this.dmaInterface.sendLog(this.configuration.isEnableUseInAppLogging() ? 1 : 0, this.configuration.getTrackingId(), this.log.getType().getAbbrev(), this.log.getTimestamp(), this.log.getData());
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }
}
