package com.samsung.context.sdk.samsunganalytics.internal.sender.DLS;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.BaseLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class DLSLogSender extends BaseLogSender {
    public static final int DB_SELECT_LIMIT = 200;

    public DLSLogSender(Context context, Configuration configuration) {
        super(context, configuration);
    }

    private int getNetworkType() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return -4;
        }
        return activeNetworkInfo.getType();
    }

    private int checkAvailableLogging(int i) {
        if (i == -4) {
            Debug.LogD("DLS Sender", "Network unavailable.");
            return -4;
        } else if (!PolicyUtils.isPolicyExpired(this.context)) {
            return 0;
        } else {
            Debug.LogD("DLS Sender", "policy expired. request policy");
            return -6;
        }
    }

    private void sendSum(int i, LogType logType, Queue<SimpleLog> queue, int i2, AsyncTaskCallback asyncTaskCallback) {
        PolicyUtils.useQuota(this.context, i, i2);
        this.executor.execute(new DLSAPIClient(logType, queue, this.configuration.getTrackingId(), asyncTaskCallback));
    }

    private int flushBufferedLogs(int i, LogType logType, Queue<SimpleLog> queue, AsyncTaskCallback asyncTaskCallback) {
        ArrayList arrayList = new ArrayList();
        Iterator<SimpleLog> it = queue.iterator();
        while (true) {
            int i2 = 0;
            if (!it.hasNext()) {
                return 0;
            }
            LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
            int remainingQuota = PolicyUtils.getRemainingQuota(this.context, i);
            if (51200 <= remainingQuota) {
                remainingQuota = 51200;
            }
            while (it.hasNext()) {
                SimpleLog next = it.next();
                if (next.getType() == logType) {
                    if (next.getData().getBytes().length + i2 > remainingQuota) {
                        break;
                    }
                    i2 += next.getData().getBytes().length;
                    linkedBlockingQueue.add(next);
                    it.remove();
                    arrayList.add(next.getId());
                    if (queue.isEmpty()) {
                        this.manager.remove(arrayList);
                        queue = this.manager.get(200);
                        it = queue.iterator();
                    }
                }
            }
            if (linkedBlockingQueue.isEmpty()) {
                return -1;
            }
            this.manager.remove(arrayList);
            sendSum(i, logType, linkedBlockingQueue, i2, asyncTaskCallback);
            Debug.LogD("DLSLogSender", "send packet : num(" + linkedBlockingQueue.size() + ") size(" + i2 + ")");
        }
    }

    private int sendOne(int i, SimpleLog simpleLog, AsyncTaskCallback asyncTaskCallback, boolean z) {
        if (simpleLog == null) {
            return -100;
        }
        int length = simpleLog.getData().getBytes().length;
        int hasQuota = PolicyUtils.hasQuota(this.context, i, length);
        if (hasQuota != 0) {
            return hasQuota;
        }
        PolicyUtils.useQuota(this.context, i, length);
        DLSAPIClient dLSAPIClient = new DLSAPIClient(simpleLog, this.configuration.getTrackingId(), asyncTaskCallback);
        if (z) {
            Debug.LogENG("sync send");
            dLSAPIClient.run();
            return dLSAPIClient.onFinish();
        }
        this.executor.execute(dLSAPIClient);
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x006b A[LOOP:0: B:12:0x006b->B:15:0x007b, LOOP_START, PHI: r2 
      PHI: (r2v3 int) = (r2v1 int), (r2v6 int) binds: [B:10:0x005e, B:15:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.samsung.context.sdk.samsunganalytics.internal.sender.LogSender
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int send(java.util.Map<java.lang.String, java.lang.String> r7) {
        /*
        // Method dump skipped, instructions count: 126
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.internal.sender.DLS.DLSLogSender.send(java.util.Map):int");
    }
}
