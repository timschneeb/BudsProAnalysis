package com.accessorydm.ui.installconfirm;

import com.accessorydm.ui.installconfirm.XUIInstallConfirmContract;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InstallCountdown {
    private static final int COUNT_DOWN_START = 30;
    private static final int COUNT_DOWN_STEP = 1;
    private static final InstallCountdown INSTANCE = new InstallCountdown();
    private boolean isRunning = false;
    private WeakReference<XUIInstallConfirmModel> modelReference;
    private int remainingTime;
    private ScheduledExecutorService scheduledExecutorService;
    private WeakReference<XUIInstallConfirmContract.View> viewReference;

    private InstallCountdown() {
    }

    public static InstallCountdown getInstance() {
        return INSTANCE;
    }

    public synchronized void startOrKeepGoing(XUIInstallConfirmContract.View view, XUIInstallConfirmModel xUIInstallConfirmModel) {
        Log.I("");
        this.viewReference = new WeakReference<>(view);
        this.modelReference = new WeakReference<>(xUIInstallConfirmModel);
        if (this.isRunning) {
            Log.I("Already running, so just keep going countdown");
            showCountdownUI(this.remainingTime);
            return;
        }
        this.isRunning = true;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.remainingTime = 30;
        this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            /* class com.accessorydm.ui.installconfirm.$$Lambda$InstallCountdown$TyncX1lO5jtFMaV4YVQ5o8lBsY */

            public final void run() {
                InstallCountdown.this.onCountdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /* access modifiers changed from: private */
    public synchronized void onCountdown() {
        Log.I("remainingTime: " + this.remainingTime);
        if (!this.isRunning) {
            Log.I("not running, so do nothing");
        } else if (this.viewReference == null || this.modelReference == null) {
            Log.W("viewReference: " + this.viewReference + ", modelReference: " + this.modelReference);
            stop();
        } else if (this.remainingTime <= 0) {
            Log.I("countdown is done, so try to install and stop countdown");
            tryInstall();
            stop();
        } else {
            showCountdownUI(this.remainingTime);
            this.remainingTime--;
        }
    }

    private void tryInstall() {
        WeakReference<XUIInstallConfirmContract.View> weakReference = this.viewReference;
        if (weakReference == null || this.modelReference == null) {
            Log.W("viewReference: " + this.viewReference + ", modelReference: " + this.modelReference);
            stop();
            return;
        }
        XUIInstallConfirmContract.View view = weakReference.get();
        XUIInstallConfirmModel xUIInstallConfirmModel = this.modelReference.get();
        Log.I("view: " + view + ", model: " + xUIInstallConfirmModel);
        if (xUIInstallConfirmModel != null) {
            xUIInstallConfirmModel.startInstall();
        }
        if (view != null) {
            view.finish();
        }
    }

    private void showCountdownUI(int i) {
        setGuideTextWithCount(i);
        showCountdownNotification();
    }

    private void setGuideTextWithCount(int i) {
        WeakReference<XUIInstallConfirmContract.View> weakReference = this.viewReference;
        if (weakReference == null) {
            Log.W("viewReference is null, so cannot update UI");
            return;
        }
        XUIInstallConfirmContract.View view = weakReference.get();
        if (view == null) {
            Log.W("view is null, so cannot update UI");
        } else {
            view.runOnUiThread(new Runnable(view, i) {
                /* class com.accessorydm.ui.installconfirm.$$Lambda$InstallCountdown$56XAdhllZRMRuthV8MfqFqGLzA */
                private final /* synthetic */ XUIInstallConfirmContract.View f$1;
                private final /* synthetic */ int f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    InstallCountdown.this.lambda$setGuideTextWithCount$0$InstallCountdown(this.f$1, this.f$2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$setGuideTextWithCount$0$InstallCountdown(XUIInstallConfirmContract.View view, int i) {
        view.getTopContentView().setText(getGuideTextForInstallCountdown(i));
    }

    public synchronized void stop() {
        Log.I("");
        if (!this.isRunning) {
            Log.I("not running, so do nothing");
            return;
        }
        this.isRunning = false;
        clearCountdownNotification();
        this.scheduledExecutorService.shutdownNow();
        this.scheduledExecutorService = null;
        this.viewReference = null;
        this.modelReference = null;
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isCountingDown() {
        return this.isRunning;
    }

    public synchronized int getRemainingTime() {
        return this.remainingTime;
    }

    private String getGuideTextForInstallCountdown(int i) {
        return String.format(FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getInstallConfirmCountdownTextId()), Integer.valueOf(i));
    }

    private void showCountdownNotification() {
        XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_FOTA_UPDATE_COUNTDOWN);
    }

    private void clearCountdownNotification() {
        XUINotificationManager.getInstance().xuiRemoveNotification(NotificationType.XUI_INDICATOR_FOTA_UPDATE_COUNTDOWN);
    }
}
