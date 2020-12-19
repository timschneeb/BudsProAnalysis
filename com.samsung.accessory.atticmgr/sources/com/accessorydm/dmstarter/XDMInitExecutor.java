package com.accessorydm.dmstarter;

import com.accessorydm.XDMSecReceiverApiCall;
import com.samsung.android.fotaprovider.log.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class XDMInitExecutor {
    private static final XDMInitExecutor INSTANCE = new XDMInitExecutor();
    private Future<?> futureTask;
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor($$Lambda$XDMInitExecutor$16VicOkiceFMYSJCnMqJi8K5hAQ.INSTANCE);

    private XDMInitExecutor() {
    }

    static /* synthetic */ Thread lambda$new$0(Runnable runnable) {
        Thread thread = new Thread(runnable, "THR:XDMInitExecutor");
        thread.setDaemon(true);
        return thread;
    }

    public static XDMInitExecutor getInstance() {
        return INSTANCE;
    }

    public void executeInitializeService() {
        Log.I("");
        doServiceExecute(new InitializationTask());
    }

    private synchronized void doServiceExecute(Runnable runnable) {
        Log.I("");
        try {
            this.futureTask = this.singleThreadExecutor.submit(runnable);
            waitForDmInitialization();
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    private void waitForDmInitialization() throws Exception {
        if (this.futureTask == null) {
            Log.W("unexpected situation");
            executeInitializeService();
        }
        int i = 1;
        while (true) {
            Future<?> future = this.futureTask;
            StringBuilder sb = new StringBuilder();
            sb.append("turn: ");
            int i2 = i + 1;
            sb.append(i);
            Log.I(sb.toString());
            future.get();
            if (future != this.futureTask) {
                i = i2;
            } else {
                return;
            }
        }
    }

    public boolean isDmInitializedSuccessfully() {
        Future<?> future = this.futureTask;
        if (future == null) {
            Log.I("DmInitialization has not done yet: FutureTask is null");
            return false;
        } else if (!future.isDone()) {
            Log.I("DmInitialization has not done yet");
            return false;
        } else {
            Log.I("DmInitialization has succeeded");
            return this.futureTask.isDone();
        }
    }

    /* access modifiers changed from: private */
    public static class InitializationTask implements Runnable {
        private InitializationTask() {
        }

        public void run() {
            XDMSecReceiverApiCall.getInstance().initializeXdmService();
        }
    }

    public void initExecutorStopIfRunning() {
        if (isDmInitializedSuccessfully()) {
            this.futureTask.cancel(true);
            this.futureTask = null;
            Log.I("InitExecutor has Stopped");
        }
    }
}
