package com.samsung.android.app.twatchmanager.autoswitch;

import android.content.Intent;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import androidx.core.app.JobIntentService;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AutoSwitchIntentService extends JobIntentService {
    public static final String BROADCAST_ACTION_AUTO_SWITCH_COMPLETE = "com.samsung.uhm.action.AUTO_SWITCH_COMPLETE";
    private static final String TAG = ("tUHM:" + AutoSwitchIntentService.class.getSimpleName());
    private static boolean isRunning;
    private HandlerThread autoSwitchHandlerThread;
    ConditionVariable conditionVariable;
    private HMConnectionManager.HMConnectionManagerCallback mCallback = new HMConnectionManager.HMConnectionManagerCallback() {
        /* class com.samsung.android.app.twatchmanager.autoswitch.AutoSwitchIntentService.AnonymousClass2 */

        @Override // com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.HMConnectionManagerCallback
        public void onFinished(boolean z) {
            if (z) {
                AutoSwitchIntentService.this.notifyPlugin();
            } else {
                AutoSwitchIntentService.this.stopSelfInternal();
            }
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.HMConnectionManagerCallback
        public void onInitEnd(String str) {
            if (TextUtils.isEmpty(str)) {
                AutoSwitchIntentService.this.stopSelfInternal();
            }
        }

        @Override // com.samsung.android.app.watchmanager.setupwizard.HMConnectionManager.HMConnectionManagerCallback
        public void onRulesSyncEnd(boolean z, boolean z2) {
            if (!z || z2) {
                AutoSwitchIntentService.this.stopSelfInternal();
            }
        }
    };
    private HMConnectionManager mHMConnManager;
    private Handler mHandler;
    private final Lock mLock = new ReentrantLock();

    public static boolean isRunning() {
        String str = TAG;
        Log.d(str, " isRunning() returns : " + isRunning);
        return isRunning;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyPlugin() {
        String pluginPackageNameToConnect = this.mHMConnManager.getPluginPackageNameToConnect();
        String btAddressToConnect = this.mHMConnManager.getBtAddressToConnect();
        String deviceNameToConnect = this.mHMConnManager.getDeviceNameToConnect();
        String str = TAG;
        Log.d(str, "notifyPlugin() currentPluginPackage : " + pluginPackageNameToConnect + " btAddress : " + btAddressToConnect + " deviceName : " + deviceNameToConnect);
        if (HostManagerUtils.verifyPluginActivity(this, pluginPackageNameToConnect)) {
            HostManagerUtils.sendAutoSwitchBroadCast(this, pluginPackageNameToConnect, btAddressToConnect, deviceNameToConnect);
        } else {
            Log.e(TAG, "notifyPlugin(), abnormal case. Plugin could not be launched");
        }
        stopSelfInternal();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopSelfInternal() {
        ConditionVariable conditionVariable2 = this.conditionVariable;
        if (conditionVariable2 != null) {
            conditionVariable2.open();
        }
    }

    public void init(Intent intent) {
        this.mHMConnManager = new HMConnectionManager(intent.getExtras(), this.mCallback);
        this.mHMConnManager.init();
    }

    @Override // androidx.core.app.JobIntentService
    public void onCreate() {
        super.onCreate();
        this.mLock.lock();
        isRunning = true;
        this.mLock.unlock();
        this.autoSwitchHandlerThread = new HandlerThread("AUTO_SWITCH_THREAD", 5);
        this.autoSwitchHandlerThread.start();
        this.mHandler = new Handler(this.autoSwitchHandlerThread.getLooper());
        Log.d(TAG, "onCreate()");
    }

    @Override // androidx.core.app.JobIntentService
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        this.mLock.lock();
        sendBroadcast(new Intent(BROADCAST_ACTION_AUTO_SWITCH_COMPLETE));
        Log.d(TAG, "onDestroy() send  BROADCAST_ACTION_AUTO_SWITCH_COMPLETE");
        isRunning = false;
        this.mLock.unlock();
        GearRulesManager.getInstance().setSyncCallback(null);
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.core.app.JobIntentService
    public void onHandleWork(final Intent intent) {
        String str = TAG;
        Log.d(str, "onHandleWork() " + intent);
        if (!SetupWizardWelcomeActivity.isGuiRunning() && !isRunning()) {
            this.mHandler.post(new Runnable() {
                /* class com.samsung.android.app.twatchmanager.autoswitch.AutoSwitchIntentService.AnonymousClass1 */

                public void run() {
                    AutoSwitchIntentService.this.init(intent);
                }
            });
            this.conditionVariable = new ConditionVariable();
            this.conditionVariable.block();
            Log.d(TAG, "onHandleWork() Worker Thread released");
        }
    }
}
