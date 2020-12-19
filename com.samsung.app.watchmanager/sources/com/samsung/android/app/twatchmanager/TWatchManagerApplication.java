package com.samsung.android.app.twatchmanager;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import c.b.b.a.a.c;
import c.b.b.a.a.h;
import com.samsung.android.app.twatchmanager.bixby.BixbyActionHandler;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.FeatureUtil;
import com.samsung.android.app.twatchmanager.util.Foreground;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.watchmanager.BuildConfig;

public class TWatchManagerApplication extends Application {
    private static final String TAG = ("tUHM:" + TWatchManagerApplication.class.getSimpleName());
    private static Context mAppContext;

    public static void createNotificationChannel() {
        NotificationManager notificationManager;
        String str = TAG;
        Log.d(str, "createNotificationChannel() starts... mAppContext : " + mAppContext + " sdkVer : " + Build.VERSION.SDK_INT);
        if (mAppContext != null && Build.VERSION.SDK_INT >= 26 && (notificationManager = (NotificationManager) mAppContext.getSystemService("notification")) != null) {
            notificationManager.deleteNotificationChannel(GlobalConst.GW_APP_UPDATE_NOTICHANNEL_ID);
        }
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    private void setupStrictMode() {
        if (HostManagerUtils.DEBUGGABLE()) {
            Log.w(TAG, "setupStrictMode");
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().permitDiskReads().penaltyLog().penaltyDeath().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public void onCreate() {
        Log.v(TAG, "onCreate");
        mAppContext = getApplicationContext();
        createNotificationChannel();
        c cVar = new c();
        cVar.b(SALogUtil.SA_TRACKING_ID);
        cVar.c(SALogUtil.SA_WATCHMANMAGER_UI_VER);
        cVar.a();
        h.a(this, cVar);
        com.sec.android.diagmonagent.log.provider.c.a(this, BuildConfig.SA_DIAGMON_APP_ID);
        com.sec.android.diagmonagent.log.provider.c.a(this);
        c.b.a.a.a.c.a(this);
        c.b.a.a.a.c b2 = c.b.a.a.a.c.b();
        b2.a(BixbyActionHandler.Actions.ACTION_CHECK_PLUGIN_AVAILABLE, new BixbyActionHandler());
        b2.a(BixbyActionHandler.Actions.ACTION_CHECK_AVAILABLE_DEVICES, new BixbyActionHandler());
        super.onCreate();
        FeatureUtil.init(mAppContext);
        Foreground.init(this);
        String str = TAG;
        Log.d(str, "AppVersion : " + HostManagerUtils.getVersionName(this, getPackageName()));
    }
}
