package com.accessorydm.tp;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.interfaces.XTPInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class XTPNetworkTimer {
    private int appId;
    private int nCurrentTimerCount;
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private XTPInterface.NetworkTimerMode timerMode;
    private NetworkTimerTask timerTask;

    static /* synthetic */ int access$208(XTPNetworkTimer xTPNetworkTimer) {
        int i = xTPNetworkTimer.nCurrentTimerCount;
        xTPNetworkTimer.nCurrentTimerCount = i + 1;
        return i;
    }

    public XTPNetworkTimer(int i, XTPInterface.NetworkTimerMode networkTimerMode) {
        this.timerMode = networkTimerMode;
        this.nCurrentTimerCount = 0;
        this.appId = i;
        this.timerTask = new NetworkTimerTask();
        networkStartTimer();
    }

    private void networkStartTimer() {
        Log.I("=================================>> startTimer(" + this.timerMode.getTimerMode() + ")");
        this.scheduledExecutorService.scheduleAtFixedRate(this.timerTask, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void networkEndTimer() {
        try {
            this.nCurrentTimerCount = 0;
            if (this.scheduledExecutorService == null) {
                return;
            }
            if (!this.scheduledExecutorService.isShutdown()) {
                Log.I("=================================>> endTimer(" + this.timerMode.getTimerMode() + ")");
                this.scheduledExecutorService.shutdownNow();
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    /* access modifiers changed from: private */
    public class NetworkTimerTask implements Runnable {
        private final int MAX_TIMER_COUNT;

        private NetworkTimerTask() {
            this.MAX_TIMER_COUNT = 70;
        }

        public void run() {
            Log.I("=============================================");
            Log.I("======= NetworkTimer " + XTPNetworkTimer.this.timerMode.getTimerMode() + " Timer[" + XTPNetworkTimer.this.nCurrentTimerCount + "] ========");
            Log.I("=============================================");
            if (XTPNetworkTimer.this.nCurrentTimerCount >= 70) {
                XTPNetworkTimer.this.nCurrentTimerCount = 0;
                Log.I("==============Network " + XTPNetworkTimer.this.timerMode.getTimerMode() + " Fail==============");
                XTPNetworkTimer.this.networkEndTimer();
                if (XTPNetworkTimer.this.appId == 0) {
                    XDMDmUtils.getInstance().g_Task.xdmAgentDmTpClose();
                } else {
                    XDMDmUtils.getInstance().g_Task.xdmAgentDlTpClose();
                }
            }
            XTPNetworkTimer.access$208(XTPNetworkTimer.this);
        }
    }
}
