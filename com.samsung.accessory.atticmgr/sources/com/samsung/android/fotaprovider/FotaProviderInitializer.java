package com.samsung.android.fotaprovider;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.accessorydm.db.XDMDbManager;
import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.accessorydm.dmstarter.XDMInitExecutor;
import com.accessorydm.resume.XDMResumeStarter;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.accessory.fotaprovider.controller.ConnectionController;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.log.base.LoggerData;
import com.samsung.android.fotaprovider.receiver.BroadcastRegister;
import com.samsung.android.fotaprovider.util.FotaProviderFileEncryptionUtil;
import com.samsung.android.fotaprovider.util.FotaProviderUtil;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import com.samsung.android.fotaprovider.util.firebase.FotaFirebaseInfo;
import com.samsung.android.fotaprovider.util.galaxywearable.SALogUtil;

public abstract class FotaProviderInitializer {
    public static String TAG_NAME = "FOTA_PROVIDER";
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void initializeFotaProviderWithAccessoryController(Application application, AccessoryController accessoryController) {
        mContext = application;
        initializePackageInfo(application);
        FotaFirebaseInfo.initializeFirebaseInfo(application);
        initializeAccessoryController(accessoryController);
        initializeApplication();
        Log.D("app is initialized");
    }

    public static void terminateFotaProvider(Application application) {
        Log.D("app is terminated");
        AccessoryController.getInstance().getConnectionController().releaseConnection();
    }

    private static void initializePackageInfo(Application application) {
        TAG_NAME = FotaProviderUtil.generateLogTagByPackageName(mContext, TAG_NAME);
        printPackageInfo();
        SALogUtil.setConfiguration(application);
    }

    private static void initializeAccessoryController(AccessoryController accessoryController) {
        AccessoryController.injectAccessoryController(accessoryController);
    }

    public static void initializeApplication() {
        BroadcastRegister.getInstance().registerSystemEventReceiverForUserUnlockedFilter(mContext);
        if (!AccessoryController.isAvailable()) {
            Log.E("AccessoryController is null. Do nothing.");
        } else if (!FotaProviderFileEncryptionUtil.isUserUnlocked(mContext)) {
            Log.W("Device is locked, do nothing");
        } else if (!XDMDbManager.initializeDatabase()) {
            Log.W("Database is not available, do nothing");
        } else {
            AccessoryController.getInstance().getConnectionController().makeConnection(new ConnectionController.ConnectionResultCallback() {
                /* class com.samsung.android.fotaprovider.FotaProviderInitializer.AnonymousClass1 */

                @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController.ConnectionResultCallback
                public void onSuccess() {
                    Log.I("");
                    if (XDMDbSqlQuery.xdmDbsqlGetFUMOStatus() != 0 || XDMDbSqlQuery.xdmdbsqlGetNotiSaveState() != 0) {
                        if (!XDMInitExecutor.getInstance().isDmInitializedSuccessfully()) {
                            Log.I("Service Start ! - startServiceIfNeeded");
                            XDMInitExecutor.getInstance().executeInitializeService();
                        }
                        XDMResumeStarter.DMINIT_RESUME.resumeExecute();
                    }
                }

                @Override // com.samsung.accessory.fotaprovider.controller.ConnectionController.ConnectionResultCallback
                public void onFailure() {
                    Log.W("");
                }
            });
            BroadcastRegister.getInstance().unregisterSystemEventReceiverWithAllFilters(mContext);
        }
    }

    private static void printPackageInfo() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            Log.D("versionName: " + packageInfo.versionName);
            LoggerData loggerData = Log.DATA;
            loggerData.D("firstInstallTime: " + GeneralUtil.convertMillisToDateTime(packageInfo.firstInstallTime));
            LoggerData loggerData2 = Log.DATA;
            loggerData2.D("lastUpdateTime: " + GeneralUtil.convertMillisToDateTime(packageInfo.lastUpdateTime));
        } catch (PackageManager.NameNotFoundException e) {
            Log.W(e.toString());
        }
    }
}
