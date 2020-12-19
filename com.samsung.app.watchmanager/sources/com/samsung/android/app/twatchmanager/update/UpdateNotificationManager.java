package com.samsung.android.app.twatchmanager.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.content.a;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.R;

public class UpdateNotificationManager {
    public static final String EXTRA_UPDATE_NOTIFICATION = "isFromUpdateNotification";
    private static final int N_ID = 909;
    private static final String TAG = ("tUHM:[Update]" + UpdateNotificationManager.class.getSimpleName());

    /* access modifiers changed from: private */
    public static class Holder {
        private static UpdateNotificationManager instance = new UpdateNotificationManager();

        private Holder() {
        }
    }

    public static UpdateNotificationManager getInstance() {
        return Holder.instance;
    }

    private PendingIntent getNotificationIntent(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(context.getPackageName());
                launchIntentForPackage.putExtra(EXTRA_UPDATE_NOTIFICATION, true);
                launchIntentForPackage.addFlags(268435456);
                return PendingIntent.getActivity(context, 0, launchIntentForPackage, 134217728);
            }
            Log.e(TAG, "getNotificationIntent, pm is null");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cancel() {
        Log.d(TAG, "cancel() starts...");
        ((NotificationManager) TWatchManagerApplication.getAppContext().getSystemService("notification")).cancel(N_ID);
    }

    public void showBackgroundInstallNotification(String str, int i, boolean z, boolean z2) {
        if (UpdateUtil.isBackgroundTestMode()) {
            Context appContext = TWatchManagerApplication.getAppContext();
            Notification.Builder showWhen = new Notification.Builder(appContext).setOnlyAlertOnce(true).setSmallIcon(R.drawable.tw_ic_gw_notification_24dp).setContentTitle(appContext.getResources().getString(R.string.app_name)).setContentText(str).setContentIntent(getNotificationIntent(appContext)).setOngoing(z2).setAutoCancel(false).setShowWhen(true);
            if (i > 0) {
                showWhen.setProgress(100, i, z);
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 26) {
                showWhen.setChannelId(GlobalConst.GW_APP_UPDATE_NOTICHANNEL_ID);
            } else if (i2 >= 21) {
                showWhen.setVisibility(1).setPriority(-2);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                showWhen.setColor(a.a(appContext, (int) R.color.gm_color_primary));
            }
            ((NotificationManager) appContext.getSystemService("notification")).notify(N_ID, showWhen.build());
        }
    }

    public void showNotification() {
        Context appContext = TWatchManagerApplication.getAppContext();
        PendingIntent notificationIntent = getNotificationIntent(appContext);
        String str = TAG;
        Log.d(str, "showNotification() starts... pi : " + notificationIntent);
        if (notificationIntent != null) {
            Notification.Builder showWhen = new Notification.Builder(appContext).setSmallIcon(R.drawable.tw_ic_gw_notification_24dp).setContentTitle(appContext.getResources().getString(R.string.app_name)).setContentText(appContext.getString(R.string.uhm_update_exist_notification_subtitle)).setContentIntent(notificationIntent).setOngoing(false).setAutoCancel(true).setShowWhen(true);
            int i = Build.VERSION.SDK_INT;
            if (i >= 26) {
                showWhen.setChannelId(GlobalConst.GW_APP_UPDATE_NOTICHANNEL_ID);
            } else if (i >= 21) {
                showWhen.setVisibility(1).setPriority(-2);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                showWhen.setColor(a.a(appContext, (int) R.color.gm_color_primary));
            }
            ((NotificationManager) appContext.getSystemService("notification")).notify(N_ID, showWhen.build());
        }
    }
}
