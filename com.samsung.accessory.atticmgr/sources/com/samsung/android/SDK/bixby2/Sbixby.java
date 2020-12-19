package com.samsung.android.sdk.bixby2;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.samsung.android.sdk.bixby2.action.ActionHandler;
import com.samsung.android.sdk.bixby2.provider.CapsuleProvider;
import com.samsung.android.sdk.bixby2.state.StateHandler;
import java.util.HashMap;
import java.util.Map;

public class Sbixby {
    private static final String BIXBY_VERSION_1_0 = "1";
    private static final String TAG = (Sbixby.class.getSimpleName() + "_" + BuildConfig.VERSION_NAME);
    private static Map<String, AppMetaInfo> appMetaInfoMap;
    private static Context mContext;
    private static Sbixby mInstance;
    private static String mPackageName;

    private Sbixby(Context context) {
        mContext = context;
    }

    public static void initialize(Context context) {
        if (context != null) {
            if (mInstance == null) {
                mInstance = new Sbixby(context);
            }
            mInstance.setPackageName(context.getPackageName());
            CapsuleProvider.setAppInitialized(true);
            String str = TAG;
            LogUtil.d(str, "initialized in package " + mPackageName);
            return;
        }
        throw new IllegalArgumentException("App Context is NULL. pass valid context.");
    }

    public static synchronized Sbixby getInstance() throws IllegalStateException {
        Sbixby sbixby;
        synchronized (Sbixby.class) {
            if (mInstance != null) {
                LogUtil.d(TAG, " getInstance()");
                sbixby = mInstance;
            } else {
                throw new IllegalStateException("The Sbixby instance is NULL. do initialize Sbixby before accessing instance.");
            }
        }
        return sbixby;
    }

    public static StateHandler getStateHandler() {
        LogUtil.d(TAG, " getStateHandler()");
        return StateHandler.getInstance();
    }

    public static boolean isPathRuleBased(Context context) {
        if (context == null) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.samsung.android.bixby.agent", 0);
            if (packageInfo != null && !TextUtils.isEmpty(packageInfo.versionName)) {
                String[] split = packageInfo.versionName.split("\\.");
                if (TextUtils.isEmpty(split[0]) || !"1".equals(split[0])) {
                    return false;
                }
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            String str = TAG;
            LogUtil.d(str, "NameNotFoundException" + e);
        }
        return false;
    }

    public void addActionHandler(String str, ActionHandler actionHandler) {
        if (TextUtils.isEmpty(str) || actionHandler == null) {
            throw new IllegalArgumentException("Action handler is NULL. pass valid app action handler implementation.");
        }
        String str2 = TAG;
        LogUtil.d(str2, " addActionHandler: action Id --> " + str);
        CapsuleProvider.addActionHandler(str, actionHandler);
    }

    public void removeActionHandler(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = TAG;
            LogUtil.d(str2, "removing actionHandler with actionId --> " + str);
            CapsuleProvider.removeActionHandler(str);
            return;
        }
        throw new IllegalArgumentException("Action id is NULL. pass valid app action id");
    }

    public void removeAllActionHandlers() {
        LogUtil.d(TAG, "Removing all action handlers");
        CapsuleProvider.removeAllActionHandlers();
    }

    private void setPackageName(String str) {
        if (!TextUtils.isEmpty(str)) {
            mPackageName = str;
            return;
        }
        throw new IllegalArgumentException("package name is null or empty.");
    }

    public Map<String, AppMetaInfo> getAppMetaInfoMap() {
        return appMetaInfoMap;
    }

    public void addAppMetaInfo(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            if (appMetaInfoMap == null) {
                appMetaInfoMap = new HashMap();
            }
            appMetaInfoMap.put(str, new AppMetaInfo(str, i));
            return;
        }
        LogUtil.d(TAG, "capsuleId cannot be null or empty");
        throw new IllegalArgumentException("capsuleId cannot be null or empty");
    }

    public void removeAppMetaInfo(String str) {
        if (str != null && appMetaInfoMap.containsKey(str)) {
            appMetaInfoMap.remove(str);
        }
    }
}
