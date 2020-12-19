package com.samsung.accessory.hearablemgr.core.bixbyroutine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import java.util.LinkedList;
import java.util.Queue;
import seccompat.android.util.Log;

public class RoutineQueue {
    private static final int QUEUE_REMOVED_TIME = 2000;
    private static final String TAG = (Application.TAG_ + RoutineQueue.class.getSimpleName());
    private static RoutineQueue sRoutineQueue;
    private boolean isRunningQueue = false;
    private BroadcastReceiver mRoutineQueueReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineQueue.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {
            String str = RoutineQueue.TAG;
            Log.d(str, "onReceive : " + intent.getAction());
            String action = intent.getAction();
            if (((action.hashCode() == -1553437513 && action.equals(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY)) ? (char) 0 : 65535) == 0) {
                RoutineQueue.this.startQueueAction();
            }
        }
    };
    private Queue<Action> queue = new LinkedList();

    public static synchronized RoutineQueue getInstance() {
        RoutineQueue routineQueue;
        synchronized (RoutineQueue.class) {
            if (sRoutineQueue == null) {
                sRoutineQueue = new RoutineQueue();
            }
            routineQueue = sRoutineQueue;
        }
        return routineQueue;
    }

    private RoutineQueue() {
        Log.d(TAG, "RoutineQueue() QUEUE_REMOVED_TIME : 2000millis");
        registerReceiver();
        new Thread(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineQueue.AnonymousClass1 */

            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (RoutineQueue.sRoutineQueue != null) {
                    Log.d(RoutineQueue.TAG, "QUEUE_REMOVED_TIME");
                    RoutineQueue.this.startQueueAction();
                }
            }
        }).start();
    }

    private void onDestroy() {
        Log.d(TAG, "onDestroy()");
        if (sRoutineQueue != null) {
            unregisterReceiver();
            sRoutineQueue = null;
            this.isRunningQueue = false;
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_DEVICE_EXTENDED_STATUS_READY);
        Application.getContext().registerReceiver(this.mRoutineQueueReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        try {
            Application.getContext().unregisterReceiver(this.mRoutineQueueReceiver);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        }
    }

    public synchronized void offer(Action action) {
        String str = TAG;
        Log.d(str, "offer() tag : " + action.getTag() + ", param : " + action.getParam() + ", isNegative : " + action.getIsNegative() + ", isRecovery : " + action.getIsRecovery());
        this.queue.offer(action);
        RoutineUtils.setActionResult(action.getTag(), action.getParam(), 0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startQueueAction() {
        Log.d(TAG, "startQueueAction()");
        if (!this.isRunningQueue) {
            this.isRunningQueue = true;
            nextAction();
        }
    }

    private void nextAction() {
        if (this.queue.isEmpty()) {
            Log.d(TAG, "queue end :: queue is empty");
            onDestroy();
            return;
        }
        Action poll = this.queue.poll();
        RoutineUtils.setActionResult(poll.getTag(), poll.getParam(), RoutineActionProvider.onAct(poll.getTag(), poll.getParam()));
        String str = TAG;
        Log.d(str, "action tag : " + poll.getTag() + ", param : " + poll.getParam() + ", isNegative : " + poll.getIsNegative() + ", isRecovery : " + poll.getIsRecovery());
        nextAction();
    }

    public static class Action {
        private boolean isNegative;
        private boolean isRecovery;
        private String param;
        private String tag;

        public Action(String str, String str2, boolean z, boolean z2) {
            this.tag = str;
            this.param = str2;
            this.isNegative = z;
            this.isRecovery = z2;
        }

        public String getTag() {
            return this.tag;
        }

        public String getParam() {
            return this.param;
        }

        public boolean getIsNegative() {
            return this.isNegative;
        }

        public boolean getIsRecovery() {
            return this.isRecovery;
        }
    }
}
