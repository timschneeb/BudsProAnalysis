package com.accessorydm;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.SystemClock;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.agent.XDMTask;
import com.accessorydm.agent.XDMUITask;
import com.accessorydm.db.file.XDBFactoryBootstrap;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.ui.UIManager;
import com.accessorydm.ui.dialog.XUIDialog;
import com.accessorydm.ui.dialog.XUIDialogActivity;
import com.accessorydm.ui.progress.XUIProgressActivity;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import java.util.concurrent.TimeUnit;

public enum XDMDmUtils {
    INSTANCE;
    
    private static final long MIN_INTERVAL_BW_ACTIVITY_DIALOG_IN_MILLIS = 1000;
    private static long lastRequestTimeForActivity = 0;
    public boolean XDM_ROAMING_CHECK = true;
    public boolean XDM_VALIDATION_CHECK = true;
    public XDMTask g_Task = null;
    public XDMUITask g_UITask = null;
    private int g_nResumeStatus = 0;
    private PowerManager.WakeLock m_WakeLock = null;
    private WifiManager.WifiLock m_WifiLock = null;
    private int m_nWaitWifiConnectMode = 0;

    private XDMDmUtils() {
    }

    public static XDMDmUtils getInstance() {
        return INSTANCE;
    }

    public void xdmTaskInit() {
        if (this.g_Task == null) {
            this.g_Task = new XDMTask();
        }
        if (this.g_UITask == null) {
            this.g_UITask = new XDMUITask();
        }
    }

    public void xdmCallUiDialogActivity(int i) {
        if (i == XUIDialog.NONE.ordinal()) {
            Log.W("wrong dialog id");
            return;
        }
        Log.I("UI_id:" + XUIDialog.valueOf(i));
        long elapsedRealtime = 1000 - (SystemClock.elapsedRealtime() - lastRequestTimeForActivity);
        if (elapsedRealtime > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(elapsedRealtime);
            } catch (Exception e) {
                Log.W(e.toString());
            }
        }
        UIManager.getInstance().finishAllActivities();
        startDialogActivity(new Intent(String.valueOf(i), null, getContext(), XUIDialogActivity.class));
    }

    public void callActivity(Class<?> cls) {
        Log.I("");
        startFullscreenActivity(new Intent(getContext(), cls));
        UIManager.getInstance().finishAllActivitiesExcept(cls.getName());
    }

    public void xdmCallUiDownloadProgressActivity() {
        Log.I("");
        Intent intent = new Intent(getContext(), XUIProgressActivity.class);
        intent.putExtra("progressMode", 1);
        startFullscreenActivity(intent);
        UIManager.getInstance().finishAllActivitiesExcept(XUIProgressActivity.class.getName());
    }

    public void xdmCallUiCopyProgressActivity() {
        Log.I("");
        Intent intent = new Intent(FotaProviderInitializer.getContext(), XUIProgressActivity.class);
        intent.putExtra("progressMode", 2);
        startFullscreenActivity(intent);
        UIManager.getInstance().finishAllActivitiesExcept(XUIProgressActivity.class.getName());
    }

    private void startDialogActivity(Intent intent) {
        intent.setFlags(872415232);
        getContext().startActivity(intent);
    }

    private void startFullscreenActivity(Intent intent) {
        lastRequestTimeForActivity = SystemClock.elapsedRealtime();
        intent.setFlags(872415232);
        getContext().startActivity(intent);
    }

    public void xdmRegisterFactoryBootstrap() {
        XDMAgent.xdmAgentSaveBootstrapDateToFFS(XDBFactoryBootstrap.xdbFBGetFactoryBootstrapData(1));
    }

    public void xdmWakeLockAcquire(String str) {
        Log.I("");
        try {
            if (this.m_WakeLock == null) {
                Log.I("m_WakeLock is acquire!!");
                PowerManager powerManager = (PowerManager) xdmGetServiceManager("power");
                if (powerManager == null) {
                    Log.E("PowerManager is null!!");
                    return;
                }
                this.m_WakeLock = powerManager.newWakeLock(1, str);
                this.m_WakeLock.setReferenceCounted(false);
                this.m_WakeLock.acquire(XCommonInterface.WAKE_LOCK_TIMEOUT);
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public void xdmWakeLockRelease() {
        Log.I("");
        try {
            if (this.m_WakeLock != null) {
                Log.I("m_WakeLock is release!!");
                this.m_WakeLock.release();
                this.m_WakeLock = null;
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public void xdmWifiLockAcquire(String str) {
        if (this.m_WifiLock == null) {
            WifiManager wifiManager = (WifiManager) getInstance().xdmGetServiceManager("wifi");
            if (wifiManager == null) {
                Log.E("WifiManager is null!!");
                return;
            }
            this.m_WifiLock = wifiManager.createWifiLock(str);
            this.m_WifiLock.setReferenceCounted(false);
            this.m_WifiLock.acquire();
        }
    }

    public void xdmWifiLockRelease() {
        Log.I("");
        try {
            if (this.m_WifiLock != null) {
                Log.I("m_WifiLock is release!!");
                this.m_WifiLock.release();
                this.m_WifiLock = null;
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }

    public Object xdmGetServiceManager(String str) {
        Object obj = null;
        try {
            obj = getContext().getSystemService(str);
            if (obj == null) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.E(e.toString());
                    }
                    Log.I(str + " is null, retry...");
                    obj = getContext().getSystemService(str);
                    if (obj != null) {
                        break;
                    }
                }
            }
        } catch (Exception e2) {
            Log.E(e2.toString());
        }
        return obj;
    }

    public int xdmGetResumeStatus() {
        return this.g_nResumeStatus;
    }

    public void xdmSetResumeStatus(int i) {
        this.g_nResumeStatus = i;
    }

    public int xdmGetWaitWifiConnectMode() {
        return this.m_nWaitWifiConnectMode;
    }

    public void xdmSetWaitWifiConnectMode(int i) {
        Log.I("WaitWifiConnectMode = " + i);
        this.m_nWaitWifiConnectMode = i;
    }

    public String xdmGetAccessorydmPath() {
        return getContext().getApplicationInfo().dataDir + "/";
    }

    public static Context getContext() {
        return FotaProviderInitializer.getContext();
    }
}
