package oreocompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;
import seccompat.android.util.Log;

public class OreoCompatUtil {
    private static final String TAG = "Attic_OreoCompatUtil";

    public static ComponentName startService(Context context, Intent intent) {
        Log.d(TAG, "startService() : " + intent);
        if (!isOreoSupportDevice()) {
            return context.startService(intent);
        }
        if (!canStartService(context)) {
            return startInstantForegroundService(context, intent);
        }
        try {
            return context.startService(intent);
        } catch (IllegalStateException unused) {
            Log.w(TAG, "startService() : IllegalStateException !!!");
            return startInstantForegroundService(context, intent);
        }
    }

    private static ComponentName startInstantForegroundService(Context context, Intent intent) {
        Log.d(TAG, "startInstantForegroundService() : " + intent);
        Intent intent2 = new Intent(context, InstantForegroundService.class);
        if (intent.getExtras() != null) {
            intent2.putExtras(intent.getExtras());
        }
        if (intent.getAction() != null) {
            Log.v(TAG, "startInstantForegroundService() : action=" + intent.getAction());
            intent2.putExtra(InstantForegroundService.EXTRA_START_ACTION, intent.getAction());
        }
        if (intent.getPackage() != null) {
            Log.v(TAG, "startInstantForegroundService() : package=" + intent.getPackage());
            intent2.putExtra(InstantForegroundService.EXTRA_START_PACKAGE_NAME, intent.getPackage());
        }
        if (intent.getComponent() != null) {
            Log.v(TAG, "startInstantForegroundService() : component=" + intent.getComponent().flattenToString());
            intent2.putExtra(InstantForegroundService.EXTRA_START_COMPONENT_NAME, intent.getComponent().flattenToString());
        }
        intent2.setAction(InstantForegroundService.ACTION_START_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            return context.startForegroundService(intent2);
        }
        return null;
    }

    public static boolean isOreoSupportDevice() {
        return Build.VERSION.SDK_INT >= 26;
    }

    private static boolean canStartService(Context context) {
        try {
            context.startService(new Intent(context, InstantForegroundService.class));
            return true;
        } catch (IllegalStateException unused) {
            Log.w(TAG, "canStartService() : IllegalStateException !!!");
            return false;
        }
    }

    public static void setNotificationChannel(Notification.Builder builder, String str) {
        if (isOreoSupportDevice()) {
            builder.setChannelId(str);
        }
    }

    public static void createNotificationChannel(NotificationManager notificationManager, NotificationChannel notificationChannel) {
        if (isOreoSupportDevice()) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static void showToastLong(Context context, String str) {
        Log.i(TAG, "Toast : \"" + str + "\"");
        Toast.makeText(context, str, 1).show();
    }
}
