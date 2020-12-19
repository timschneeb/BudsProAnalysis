package com.samsung.sht;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;
import com.samsung.accessory.hearablemgr.library.SpatialSensorInterface;
import com.samsung.sht.log.ShtLog;

public class ShtCore implements SpatialSensorInterface {
    private static final String TAG = "ShtCore";
    private ShtCoreImpl mImpl = new ShtCoreImpl();

    public static boolean isSupported(Context context) {
        if (isSamsungDevice() && isOSVersionSupported() && isSensorSupported(context)) {
            return ShtCoreImpl.isFeatureSupported(context);
        }
        ShtLog.e("Not supported : samsung device(" + isSamsungDevice() + "),os(" + isOSVersionSupported() + "),sensor(" + isSensorSupported(context) + ")");
        return false;
    }

    private static boolean isSamsungDevice() {
        if (isSamsungGEDModel()) {
            return false;
        }
        return Build.MANUFACTURER.equalsIgnoreCase("SAMSUNG");
    }

    private static boolean isSamsungGEDModel() {
        String str = Build.MODEL;
        if (str == null) {
            return false;
        }
        if (!str.contains("SM-G900FG") && !str.contains("GT-I9505G")) {
            return false;
        }
        ShtLog.d(TAG, "SamsungGEDModel : " + str);
        return true;
    }

    private static boolean isOSVersionSupported() {
        return Build.VERSION.SDK_INT >= 30;
    }

    private static boolean isSensorSupported(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        return (sensorManager.getDefaultSensor(4) == null || sensorManager.getDefaultSensor(15) == null) ? false : true;
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onCreate(SpatialSensorInterface.SupportApi supportApi, boolean z) {
        ShtLog.i(TAG, "onCreate : ");
        this.mImpl.onCreate(supportApi, z);
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSettingUpdated(boolean z) {
        this.mImpl.onSettingUpdated(z);
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSppConnected(String str) {
        this.mImpl.onSppConnected(str);
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSppMessageReceived(byte b, byte[] bArr) {
        this.mImpl.onSppMessageReceived(b, bArr);
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onSppDisconnected(String str) {
        this.mImpl.onSppDisconnected(str);
    }

    @Override // com.samsung.accessory.hearablemgr.library.SpatialSensorInterface
    public void onDestroy() {
        ShtLog.i(TAG, "onDestroy");
        this.mImpl.onDestroy();
    }
}
