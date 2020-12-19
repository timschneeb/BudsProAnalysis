package oreocompat;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.NotificationChannels;
import seccompat.android.util.Log;

public class InstantForegroundService extends IntentService {
    public static final String ACTION_START_SERVICE = "oreocompat.InstantForegroundService.action.START_SERVICE";
    public static final String EXTRA_START_ACTION = "start_action";
    public static final String EXTRA_START_COMPONENT_NAME = "start_component_name";
    public static final String EXTRA_START_PACKAGE_NAME = "start_package_name";
    private static final String TAG = "Attic_InstantForegroundService";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public InstantForegroundService() {
        super(InstantForegroundService.class.getSimpleName());
        Log.d(TAG, "InstantForegroundService()");
    }

    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        stopForeground();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent() : " + intent);
        if (intent == null) {
            Log.e(TAG, "onHandleIntent() : intent == null");
        }
        if (intent.getAction() == null || !intent.getAction().equals(ACTION_START_SERVICE)) {
            Log.e(TAG, "onHandleIntent() : intent.getAction()=" + intent.getAction());
            return;
        }
        startForegroundWithNotification();
        forwardIntent(intent);
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        stopForeground();
        super.onDestroy();
    }

    private void forwardIntent(Intent intent) {
        Log.d(TAG, "forwardIntent()");
        Intent intent2 = new Intent();
        if (intent.getExtras() != null) {
            intent2.putExtras(intent.getExtras());
        }
        if (intent2.getStringExtra(EXTRA_START_ACTION) != null) {
            Log.v(TAG, "forwardIntent() : action=" + intent2.getAction());
            intent2.setAction(intent2.getStringExtra(EXTRA_START_ACTION));
            intent2.removeExtra(EXTRA_START_ACTION);
        }
        if (intent2.getStringExtra(EXTRA_START_PACKAGE_NAME) != null) {
            Log.v(TAG, "forwardIntent() : package_name=" + intent2.getStringExtra(EXTRA_START_PACKAGE_NAME));
            intent2.setPackage(intent.getStringExtra(EXTRA_START_PACKAGE_NAME));
            intent2.removeExtra(EXTRA_START_PACKAGE_NAME);
        }
        if (intent2.getStringExtra(EXTRA_START_COMPONENT_NAME) != null) {
            Log.v(TAG, "forwardIntent() : component_name=" + intent2.getStringExtra(EXTRA_START_COMPONENT_NAME));
            intent2.setComponent(ComponentName.unflattenFromString(intent2.getStringExtra(EXTRA_START_COMPONENT_NAME)));
            intent2.removeExtra(EXTRA_START_COMPONENT_NAME);
        }
        Log.d(TAG, "forwardIntent() : startService()=" + intent2);
        startService(intent2);
    }

    private void startForegroundWithNotification() {
        Log.d(TAG, "startForegroundWithNotification()");
        startForeground(R.id.notification_instant_foreground_service, new NotificationCompat.Builder(this, NotificationChannels.ID_NOTIFICATION_DELAYS).build());
    }

    private void stopForeground() {
        Log.d(TAG, "stopForeground()");
        stopForeground(true);
    }
}
