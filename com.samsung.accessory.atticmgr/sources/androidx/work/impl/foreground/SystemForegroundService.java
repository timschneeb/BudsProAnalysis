package androidx.work.impl.foreground;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LifecycleService;
import androidx.work.Logger;
import androidx.work.impl.foreground.SystemForegroundDispatcher;

public class SystemForegroundService extends LifecycleService implements SystemForegroundDispatcher.Callback {
    private static final String TAG = Logger.tagWithPrefix("SystemFgService");
    private static SystemForegroundService sForegroundService = null;
    SystemForegroundDispatcher mDispatcher;
    private Handler mHandler;
    private boolean mIsShutdown;
    NotificationManager mNotificationManager;

    @Override // androidx.lifecycle.LifecycleService
    public void onCreate() {
        super.onCreate();
        sForegroundService = this;
        initializeDispatcher();
    }

    @Override // androidx.lifecycle.LifecycleService
    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        if (this.mIsShutdown) {
            Logger.get().info(TAG, "Re-initializing SystemForegroundService after a request to shut-down.", new Throwable[0]);
            this.mDispatcher.onDestroy();
            initializeDispatcher();
            this.mIsShutdown = false;
        }
        if (intent == null) {
            return 3;
        }
        this.mDispatcher.onStartCommand(intent);
        return 3;
    }

    @Override // androidx.lifecycle.LifecycleService
    public void onDestroy() {
        super.onDestroy();
        this.mDispatcher.onDestroy();
    }

    private void initializeDispatcher() {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mNotificationManager = (NotificationManager) getApplicationContext().getSystemService("notification");
        this.mDispatcher = new SystemForegroundDispatcher(getApplicationContext());
        this.mDispatcher.setCallback(this);
    }

    public void stopForegroundService() {
        this.mHandler.post(new Runnable() {
            /* class androidx.work.impl.foreground.SystemForegroundService.AnonymousClass1 */

            public void run() {
                SystemForegroundService.this.mDispatcher.handleStop();
            }
        });
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.Callback
    public void stop() {
        this.mIsShutdown = true;
        Logger.get().debug(TAG, "All commands completed.", new Throwable[0]);
        if (Build.VERSION.SDK_INT >= 26) {
            stopForeground(true);
        }
        sForegroundService = null;
        stopSelf();
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.Callback
    public void startForeground(final int i, final int i2, final Notification notification) {
        this.mHandler.post(new Runnable() {
            /* class androidx.work.impl.foreground.SystemForegroundService.AnonymousClass2 */

            public void run() {
                if (Build.VERSION.SDK_INT >= 29) {
                    SystemForegroundService.this.startForeground(i, notification, i2);
                } else {
                    SystemForegroundService.this.startForeground(i, notification);
                }
            }
        });
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.Callback
    public void notify(final int i, final Notification notification) {
        this.mHandler.post(new Runnable() {
            /* class androidx.work.impl.foreground.SystemForegroundService.AnonymousClass3 */

            public void run() {
                SystemForegroundService.this.mNotificationManager.notify(i, notification);
            }
        });
    }

    @Override // androidx.work.impl.foreground.SystemForegroundDispatcher.Callback
    public void cancelNotification(final int i) {
        this.mHandler.post(new Runnable() {
            /* class androidx.work.impl.foreground.SystemForegroundService.AnonymousClass4 */

            public void run() {
                SystemForegroundService.this.mNotificationManager.cancel(i);
            }
        });
    }

    public static SystemForegroundService getInstance() {
        return sForegroundService;
    }
}
