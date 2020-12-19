package seccompat.com.samsung.android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SemBluetoothCastProfile;
import java.util.List;
import seccompat.android.util.Log;

public class SemBluetoothAudioCast {
    public static final String ACTION_AUDIO_SHARING_MODE_CHANGED = "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_MODE_CHANGED";
    public static final String ACTION_CAST_DEVICE_CONNECTION_STATE_CHANGED = "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED";
    private static final boolean ENABLED = true;
    public static final String EXTRA_AUDIO_SHARING_MODE = "com.samsung.android.bluetooth.cast.extra.AUDIO_SHARING_MODE";
    public static final String EXTRA_CAST_DEVICE_REMOTE_ROLE = "com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE";
    public static final String EXTRA_CONNECTION_STATE = "com.samsung.android.bluetooth.cast.extra.STATE";
    public static final String TAG = "SemBluetoothAudioCast";
    private static Context sAppContext;
    private static com.samsung.android.bluetooth.SemBluetoothAudioCast sBluetoothAudioCastProxy;

    public static void init(Context context) {
        Log.d(TAG, "init()");
        sAppContext = context.getApplicationContext();
        boolean isSupported = isSupported();
        if (isSupported) {
            getProxy();
        }
        Log.d(TAG, "init() : " + isSupported);
    }

    public static boolean isSupported() {
        try {
            return com.samsung.android.bluetooth.SemBluetoothAudioCast.isAudioSharingSupported();
        } catch (Throwable unused) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void getProxy() {
        Log.d(TAG, "getProxy()");
        try {
            if (sAppContext != null) {
                closeProxy();
                com.samsung.android.bluetooth.SemBluetoothAudioCast.getProxy(sAppContext, new SemBluetoothCastProfile.BluetoothCastProfileListener() {
                    /* class seccompat.com.samsung.android.bluetooth.SemBluetoothAudioCast.AnonymousClass1 */

                    public void onServiceConnected(SemBluetoothCastProfile semBluetoothCastProfile) {
                        Log.d(SemBluetoothAudioCast.TAG, "getProxy() : onServiceConnected() : " + semBluetoothCastProfile);
                        SemBluetoothAudioCast.closeProxy();
                        com.samsung.android.bluetooth.SemBluetoothAudioCast unused = SemBluetoothAudioCast.sBluetoothAudioCastProxy = (com.samsung.android.bluetooth.SemBluetoothAudioCast) semBluetoothCastProfile;
                    }

                    public void onServiceDisconnected() {
                        Log.d(SemBluetoothAudioCast.TAG, "getProxy() : onServiceDisconnected()");
                        SemBluetoothAudioCast.closeProxy();
                        new Handler().post(new Runnable() {
                            /* class seccompat.com.samsung.android.bluetooth.SemBluetoothAudioCast.AnonymousClass1.AnonymousClass1 */

                            public void run() {
                                Log.d(SemBluetoothAudioCast.TAG, "getProxy() : run()");
                                if (BluetoothAdapter.getDefaultAdapter() == null || !BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                                    Log.w(SemBluetoothAudioCast.TAG, "getProxy() : BluetoothAdapter.isEnabled() == false");
                                } else {
                                    SemBluetoothAudioCast.getProxy();
                                }
                            }
                        });
                    }
                });
                return;
            }
            throw new Exception("sAppContext == null");
        } catch (Throwable th) {
            th.printStackTrace();
            Log.e(TAG, "getProxy() : Exception : " + th);
        }
    }

    /* access modifiers changed from: private */
    public static void closeProxy() {
        if (sBluetoothAudioCastProxy != null) {
            Log.d(TAG, "closeProxy() : " + sBluetoothAudioCastProxy);
            sBluetoothAudioCastProxy.closeProxy();
            sBluetoothAudioCastProxy = null;
        }
    }

    public static boolean isAudioSharingEnabled() {
        try {
            if (!isSupported()) {
                return false;
            }
            if (sBluetoothAudioCastProxy != null) {
                return sBluetoothAudioCastProxy.isAudioSharingEnabled();
            }
            Log.e(TAG, "isAudioSharingEnabled() : sBluetoothAudioCastProxy == null");
            getProxy();
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static boolean isMusicShareEnabled() {
        try {
            if (!isSupported()) {
                return false;
            }
            if (sBluetoothAudioCastProxy != null) {
                List<SemBluetoothCastDevice> connectedDevices = sBluetoothAudioCastProxy.getConnectedDevices();
                if (connectedDevices == null) {
                    return false;
                }
                for (SemBluetoothCastDevice semBluetoothCastDevice : connectedDevices) {
                    if (semBluetoothCastDevice.getRemoteDeviceRole() == 1) {
                        return true;
                    }
                }
                return false;
            }
            Log.e(TAG, "isMusicShareEnabled() : sBluetoothAudioCastProxy == null");
            getProxy();
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}
