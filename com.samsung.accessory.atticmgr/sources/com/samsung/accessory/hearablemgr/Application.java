package com.samsung.accessory.hearablemgr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Build;
import android.os.IBinder;
import androidx.core.os.EnvironmentCompat;
import com.samsung.accessory.hearablemgr.core.aom.AomManager;
import com.samsung.accessory.hearablemgr.core.appwidget.WidgetManager;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.bixby.BixbyActionHandler;
import com.samsung.accessory.hearablemgr.core.bixbyroutine.RoutineReceiverManager;
import com.samsung.accessory.hearablemgr.core.bluetooth.BluetoothManager;
import com.samsung.accessory.hearablemgr.core.fmm.utils.RingManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService;
import com.samsung.accessory.hearablemgr.core.service.BudsLogManager;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.MainService;
import com.samsung.accessory.hearablemgr.core.uhmdb.UhmDatabase;
import seccompat.android.os.SystemProperties;
import seccompat.android.util.Log;

public class Application extends android.app.Application {
    public static final boolean DEBUG_MODE = (SystemProperties.getInt("ro.debuggable", 0) == 1);
    public static final String DEVICE_NAME = "Galaxy Buds Pro";
    public static final String DEVICE_NAME_COMPAT = "Galaxy Buds Pro";
    public static final boolean EMULATOR_MODE;
    public static final String MODEL_NAME = "SM-R190";
    public static final String PERMISSION_SIGNATURE = "com.samsung.accessory.atticmgr.permission.SIGNATURE";
    public static final String S_MODEL_NAME = "R190";
    private static final String TAG = "Attic_Application";
    public static final String TAG_ = "Attic_";
    private static AomManager sAomManager;
    private static BluetoothManager sBluetoothManager;
    private static Context sContext;
    private static CoreService sCoreService;
    private static MainService sMainService;
    private static NotificationCoreService sNotificationCoreService;
    private static UhmDatabase sUhmDatabase;
    private static WidgetManager sWidgetManager;
    private final ServiceConnection mMainServiceConnection = new ServiceConnection() {
        /* class com.samsung.accessory.hearablemgr.Application.AnonymousClass1 */

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainService unused = Application.sMainService = ((MainService.Binder) iBinder).getService();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            MainService unused = Application.sMainService = null;
        }
    };
    private RoutineReceiverManager sRoutineReceiverManager;

    static {
        boolean z = false;
        if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith(EnvironmentCompat.MEDIA_UNKNOWN) || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || ((Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT))) {
            z = true;
        }
        EMULATOR_MODE = z;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate()");
        sContext = this;
        super.onCreate();
        sBluetoothManager = new BluetoothManager(this);
        sCoreService = new CoreService(this);
        sNotificationCoreService = new NotificationCoreService(this);
        sUhmDatabase = new UhmDatabase(this);
        this.sRoutineReceiverManager = new RoutineReceiverManager(this);
        sWidgetManager = new WidgetManager(this);
        sAomManager = ApplicationModel.getAomManager(this);
        RingManager.registerReceiver(this);
        NotificationChannels.register(this);
        MainService.startService();
        bindMainService();
        SamsungAnalyticsUtil.init(this);
        BixbyActionHandler.initialize(this);
        BudsLogManager.getInstance();
        ApplicationModel.initFotaProviderApplication(this);
        Log.d(TAG, "onCreate()_end");
    }

    public void onTerminate() {
        Log.d(TAG, "onTerminate()");
        sCoreService.onDestroy();
        sBluetoothManager.destroy();
        sNotificationCoreService.onDestroy();
        sUhmDatabase.destroy();
        this.sRoutineReceiverManager.onDestroy();
        sWidgetManager.onDestroy();
        AomManager aomManager = sAomManager;
        if (aomManager != null) {
            aomManager.destroy();
        }
        RingManager.unregisterReceiver(this);
        unbindMainService();
        BudsLogManager.getInstance().destroy();
        ApplicationModel.terminateFotaProviderApplication(this);
        super.onTerminate();
    }

    public void onConfigurationChanged(Configuration configuration) {
        Log.d(TAG, "onConfigurationChanged()");
        super.onConfigurationChanged(configuration);
    }

    public void onLowMemory() {
        Log.d(TAG, "onLowMemory()");
        super.onLowMemory();
    }

    public void onTrimMemory(int i) {
        Log.d(TAG, "onTrimMemory() : level=" + i);
        super.onTrimMemory(i);
    }

    public static Context getContext() {
        return sContext;
    }

    public static BluetoothManager getBluetoothManager() {
        return sBluetoothManager;
    }

    public static CoreService getCoreService() {
        return sCoreService;
    }

    public static NotificationCoreService getNotificationCoreService() {
        return sNotificationCoreService;
    }

    public static MainService getMainService() {
        return sMainService;
    }

    public static UhmDatabase getUhmDatabase() {
        return sUhmDatabase;
    }

    public static AomManager getAomManager() {
        return sAomManager;
    }

    private void bindMainService() {
        Log.d(TAG, "bindMainService()");
        bindService(new Intent(this, MainService.class), this.mMainServiceConnection, 65);
    }

    private void unbindMainService() {
        Log.d(TAG, "unbindMainService()");
        unbindService(this.mMainServiceConnection);
    }
}
