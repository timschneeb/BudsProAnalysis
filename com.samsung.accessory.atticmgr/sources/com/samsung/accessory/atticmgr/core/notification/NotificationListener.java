package com.samsung.accessory.atticmgr.core.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.core.app.NotificationCompat;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.NotificationChannels;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationMessage;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.android.fotaagent.update.UpdateInterface;
import java.util.List;
import seccompat.android.util.Log;

public class NotificationListener extends NotificationListenerService {
    private static final int DELAYED_TIME = 3000;
    private static final String TAG = "Attic_NotificationListener";
    private Context mContext;

    public void onNotificationPosted(final StatusBarNotification statusBarNotification) {
        Log.d(TAG, "onNotificationPosted()::sbn.getPackageName() = " + statusBarNotification.getPackageName());
        if (!Application.getCoreService().isConnected()) {
            Log.d(TAG, "onNotificationPosted():: Not connected");
        } else if (statusBarNotification.isOngoing()) {
            Log.d(TAG, "This Notification is On-Going, let's skip it [ " + statusBarNotification.getPackageName() + "]");
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                /* class com.samsung.accessory.atticmgr.core.notification.NotificationListener.AnonymousClass1 */

                public void run() {
                    NotificationListener.this.sendAudioNotification(statusBarNotification);
                }
            });
        }
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        Log.d(TAG, "onNotificationRemoved()");
    }

    public void onCreate() {
        super.onCreate();
        this.mContext = Application.getContext();
        Log.d(TAG, "onCreate()");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.accessory.atticmgr.core.notification.NotificationListener.AnonymousClass2 */

            public void run() {
                if (NotificationUtil.isAccessibilityON()) {
                    return;
                }
                if (!NotificationUtil.isSamsungDevice() || NotificationUtil.getSDKVer() >= 27) {
                    NotificationListener.makeNotification4Permission(NotificationListener.this.mContext);
                } else if (NotificationUtil.isSamsungDevice() && Build.VERSION.SDK_INT < 27) {
                    NotificationUtil.enableNotificationService(true);
                }
            }
        }, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
    }

    public static void makeNotification4Permission(Context context) {
        Log.d(TAG, "makeNotification4Permission");
        if (context == null) {
            try {
                Log.d(TAG, "makeNotification4Permission - context is null");
            } catch (NullPointerException e) {
                Log.d(TAG, e.toString());
            }
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationChannels.ID_GENERAL_NOTIFICATIONS);
            String string = context.getString(R.string.sbn_notification_access);
            String string2 = context.getString(R.string.sbn_notification_access_desc);
            builder.setContentTitle(string);
            builder.setContentText(string2);
            builder.setSmallIcon(R.drawable.quickpanel_ic_gear_apps);
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(string2));
            PendingIntent activity = PendingIntent.getActivity(context, 0, convertImplicitToExplicitForActivity(context, new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")), 0);
            builder.setDefaults(-1);
            builder.setAutoCancel(true);
            builder.setContentIntent(activity);
            ((NotificationManager) context.getSystemService("notification")).notify(0, builder.build());
        }
    }

    public static Intent convertImplicitToExplicitForActivity(Context context, Intent intent) {
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
            return null;
        }
        ResolveInfo resolveInfo = queryIntentActivities.get(0);
        ComponentName componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        Intent intent2 = new Intent(intent);
        intent2.setComponent(componentName);
        return intent2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendAudioNotification(StatusBarNotification statusBarNotification) {
        String str;
        NotificationMessage makeNotificationTTSMessage;
        Log.d(TAG, "sendAudioNotification()");
        Log.d(TAG, "uid = " + statusBarNotification.getUser().hashCode());
        if (NotificationUtil.isKnoxUserId(statusBarNotification.getUser().hashCode())) {
            str = statusBarNotification.getPackageName() + NotificationConstants.DUAL;
        } else {
            str = statusBarNotification.getPackageName();
        }
        if (!NotificationConstants.ALARM_PACKAGENAME.equals(str) && !NotificationConstants.CALENDAR_PACKAGENAME.equals(str)) {
            NotificationListenerService.Ranking ranking = null;
            int i = 0;
            if (Build.VERSION.SDK_INT >= 26) {
                ranking = new NotificationListenerService.Ranking();
                getCurrentRanking().getRanking(statusBarNotification.getKey(), ranking);
                if (Build.VERSION.SDK_INT < 28 || !ranking.isSuspended()) {
                    try {
                        i = ranking.getChannel().getAudioAttributes().getUsage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "Usage = " + i);
                } else {
                    Log.d(TAG, str + " is suspended");
                    return;
                }
            }
            if (NotificationUtil.isAlarmNeed(statusBarNotification.getNotification(), str, ranking) && !NotificationUtil.isExcludeApp(str) && NotificationUtil.checkAllStatus(str) && (makeNotificationTTSMessage = makeNotificationTTSMessage(statusBarNotification, str)) != null) {
                Intent intent = new Intent(NotificationConstants.ACTION_UPDATE_VN_MESSAGE);
                intent.putExtra(NotificationConstants.VN_MESSAGE, makeNotificationTTSMessage);
                intent.putExtra(NotificationConstants.VN_USAGE, i);
                this.mContext.sendBroadcast(intent, "com.samsung.accessory.atticmgr.permission.SIGNATURE");
            }
        }
    }

    public NotificationMessage makeNotificationTTSMessage(StatusBarNotification statusBarNotification, String str) {
        ApplicationInfo applicationInfo;
        String str2;
        String str3;
        Log.d(TAG, "makeNotificationTTSMessage()");
        Notification notification = statusBarNotification.getNotification();
        long postTime = statusBarNotification.getPostTime();
        PackageManager packageManager = this.mContext.getApplicationContext().getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(statusBarNotification.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        if (NotificationConstants.EMAIL_PACKAGENAME.equals(str)) {
            Log.d(TAG, "email package name on Z should be switch -email");
            str2 = this.mContext.getResources().getString(R.string.notification_email);
        } else {
            try {
                str2 = packageManager.getApplicationLabel(applicationInfo).toString();
            } catch (Exception e2) {
                e2.printStackTrace();
                str2 = null;
            }
            if (str2 == null || str2.equals("")) {
                str2 = " ";
            }
        }
        Log.d(TAG, "appName = " + str2);
        Log.d(TAG, "pkgName = " + str);
        Log.d(TAG, "postTime = " + postTime);
        String parseBundle = parseBundle(notification, statusBarNotification.getPackageName());
        if (parseBundle != null) {
            Log.d(TAG, " ticker string length is " + parseBundle.length());
        }
        if (NotificationUtil.getAppNotificationDetails(str).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY)) {
            Log.d(TAG, "NOTIFICATION_TYPE_SUMMARY");
            str3 = null;
        } else {
            Log.d(TAG, "NOTIFICATION_TYPE_DETAIL");
            str3 = parseBundle;
        }
        return new NotificationMessage(NotificationMessage.TYPE_NORMAL, str, str2, str3, null, postTime);
    }

    private String parseBundle(Notification notification, String str) {
        String str2;
        String str3;
        CharSequence charSequence;
        CharSequence charSequence2;
        Log.d(TAG, "parseBundle()");
        try {
            Bundle bundle = notification.extras;
            if (!bundle.containsKey(NotificationCompat.EXTRA_TITLE) || (charSequence2 = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE)) == null) {
                str2 = "";
            } else {
                str2 = charSequence2.toString();
            }
            if (!bundle.containsKey(NotificationCompat.EXTRA_TEXT) || (charSequence = bundle.getCharSequence(NotificationCompat.EXTRA_TEXT)) == null) {
                str3 = "";
            } else {
                str3 = charSequence.toString();
            }
            if (NotificationConstants.MESSAGE_PACKAGENAME.equals(str)) {
                str2 = arrangeCaller(str2);
            }
            if (!str2.equals("") || !str3.equals("")) {
                return str2 + ", " + str3;
            }
            Log.d(TAG, "text is null");
            return (String) notification.tickerText;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String arrangeCaller(String str) {
        Log.d(TAG, "arrangeCaller()");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            try {
                char charAt = str.charAt(i);
                if (charAt != '-' || i <= 0) {
                    if (charAt != '+') {
                        sb.append(charAt);
                        if (Character.isDigit(charAt)) {
                            sb.append(' ');
                        }
                    } else if (!Character.isDigit(str.charAt(i + 1))) {
                        sb.append(charAt);
                    }
                } else if (!Character.isDigit(str.charAt(i - 1)) || !Character.isDigit(str.charAt(i + 1))) {
                    sb.append(charAt);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        }
        return sb.toString();
    }
}
