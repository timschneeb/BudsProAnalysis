package com.samsung.accessory.hearablemgr.core.notification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.notification.NotificationAppData;
import com.samsung.accessory.hearablemgr.core.notification.NotificationConstants;
import com.samsung.accessory.hearablemgr.core.notification.NotificationManager;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import seccompat.android.util.Log;

public class PackageManagerReceiver extends BroadcastReceiver {
    private static final String ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
    private static final String ACTION_USER_REMOVED = "android.intent.action.USER_REMOVED";
    private static final String TAG = "Attic_PackageManagerReceiver";
    private static final PackageManagerReceiver mPackageReceiver = new PackageManagerReceiver(0);
    private static ArrayList<PackageManagerReceiver> mPackageReceiverForKnoxList = new ArrayList<>();
    private int mUserId;

    public PackageManagerReceiver(int i) {
        this.mUserId = i;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive:" + action + "myuid = " + this.mUserId);
            if (Application.getCoreService().getConnectionState() == 2) {
                if (intent.getData() == null || !intent.getData().getSchemeSpecificPart().equals(Application.getContext().getPackageName())) {
                    ArrayList<NotificationAppData> arrayList = null;
                    NotificationManager instance = NotificationManager.getInstance(context);
                    if (instance != null) {
                        arrayList = instance.getNotificationAppList();
                    }
                    if (arrayList == null) {
                        Log.e(TAG, "App list is empty, we can not handle this case");
                    } else if (action != null && action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        onPackageChanged(context, intent);
                    } else if (action != null && action.equals("android.intent.action.PACKAGE_ADDED")) {
                        onPackageAdded(context, intent);
                    } else if (action != null && action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        onPackageRemoved(context, intent);
                    } else if (action != null && action.equals("android.intent.action.PACKAGE_REPLACED")) {
                        onPackageReplaced(context, intent);
                    } else if (action != null && action.equals(ACTION_USER_ADDED)) {
                        onUserUpdate(context);
                    } else if (action != null && action.equals(ACTION_USER_REMOVED)) {
                        onUserUpdate(context);
                    }
                }
            }
        }
    }

    private void onPackageReplaced(Context context, Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            String schemeSpecificPart = data.getSchemeSpecificPart();
            Log.d(TAG, "ACTION_PACKAGE_REPLACED = " + schemeSpecificPart);
            schemeSpecificPart.equals(context.getPackageName());
        }
    }

    private void onUserUpdate(final Context context) {
        Log.d(TAG, "onUserUpdate()");
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.notification.receiver.PackageManagerReceiver.AnonymousClass1 */

            public void run() {
                NotificationManager.getInstance(context).notifyListUpdated(context);
                PackageManagerReceiver.unregisterReceivers(context);
                PackageManagerReceiver.registerReceivers(context);
            }
        }, 500);
    }

    private void onPackageChanged(Context context, Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            String schemeSpecificPart = data.getSchemeSpecificPart();
            Log.d(TAG, "ACTION_PACKAGE_CHANGED = " + schemeSpecificPart);
            ApplicationInfo applicationInfo = getApplicationInfo(context, schemeSpecificPart);
            if (applicationInfo == null) {
                Log.e(TAG, "AppInfo is null!");
            } else if (applicationInfo.enabled) {
                onPackageAdded(context, intent);
            } else {
                onPackageRemoved(context, intent);
            }
        }
    }

    private void onPackageRemoved(Context context, Intent intent) {
        Log.d(TAG, "onPackageRemoved()");
        NotificationManager instance = NotificationManager.getInstance(context);
        Uri data = intent.getData();
        if (data != null) {
            String schemeSpecificPart = data.getSchemeSpecificPart();
            if (isReplacing(intent)) {
                Log.e(TAG, "This package will be replaced = " + schemeSpecificPart);
                return;
            }
            if (NotificationUtil.isKnoxUserId(this.mUserId)) {
                schemeSpecificPart = schemeSpecificPart + NotificationConstants.DUAL;
            }
            Log.d(TAG, "ACTION_PACKAGE_REMOVED = " + schemeSpecificPart);
            Preferences.remove(NotificationConstants.PREFERENCE_VN_APP_ENABLE + schemeSpecificPart, UhmFwUtil.getLastLaunchDeviceId());
            Preferences.remove(NotificationConstants.PREFERENCE_VN_APP_DETAIL + schemeSpecificPart, UhmFwUtil.getLastLaunchDeviceId());
            if (!instance.removeFromList(instance.getNotificationAppList(), schemeSpecificPart)) {
                Log.d(TAG, "package was not involved");
            } else {
                instance.notifyListUpdated(context);
            }
        }
    }

    private void onPackageAdded(Context context, Intent intent) {
        Log.d(TAG, "onPackageAdded()");
        NotificationManager instance = NotificationManager.getInstance(context);
        Uri data = intent.getData();
        if (data != null) {
            String schemeSpecificPart = data.getSchemeSpecificPart();
            StringBuilder sb = new StringBuilder();
            sb.append("ACTION_PACKAGE_ADDED = ");
            sb.append(schemeSpecificPart);
            sb.append(NotificationUtil.isKnoxUserId(this.mUserId) ? NotificationConstants.DUAL : "");
            Log.d(TAG, sb.toString());
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = getApplicationInfo(context, schemeSpecificPart);
            if (applicationInfo == null) {
                Log.e(TAG, "AppInfo is null!");
                return;
            }
            String str = applicationInfo.packageName;
            if (NotificationUtil.isKnoxUserId(this.mUserId)) {
                str = str + NotificationConstants.DUAL;
            }
            String charSequence = applicationInfo.loadLabel(packageManager).toString();
            Log.d(TAG, "pkg [" + str + "], label [" + charSequence + "]");
            NotificationAppData notificationAppData = new NotificationAppData(NotificationUtil.isAppNotificationEnabled(str), charSequence, str, false);
            if (instance != null && instance.hasLauncherIntent(context, applicationInfo.packageName)) {
                if (NotificationUtil.isExcludeApp(str)) {
                    Log.e(TAG, "app is exlclude app");
                    return;
                }
                ArrayList<NotificationAppData> notificationAppList = instance.getNotificationAppList();
                if (instance.isExistApp(notificationAppList, notificationAppData.getPackageName())) {
                    Log.d(TAG, "already exist, do not add");
                } else {
                    instance.addToList(notificationAppList, notificationAppData);
                    Log.d(TAG, "added to notification list");
                    instance.notifyListUpdated(context);
                }
            }
            if ("com.samsung.SMT".equals(str)) {
                Log.d(TAG, "send ACTION_CHANGE_TTS");
                context.sendBroadcast(new Intent(NotificationConstants.ACTION_CHANGE_TTS));
            }
        }
    }

    private boolean isReplacing(Intent intent) {
        if (!intent.hasExtra("android.intent.extra.REPLACING") || !intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
            return false;
        }
        Log.e(TAG, "this package will be replaced, skip this package");
        return true;
    }

    public static void registerReceivers(Context context) {
        UserHandle userHandle;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(mPackageReceiver, intentFilter);
        if (Util.isSamsungDevice() && Build.VERSION.SDK_INT >= 21) {
            List<UserHandle> userProfiles = ((UserManager) context.getSystemService("user")).getUserProfiles();
            Integer[] numArr = NotificationConstants.KNOX_USERID;
            int length = numArr.length;
            int i = 0;
            int i2 = 0;
            while (i2 < length) {
                int intValue = numArr[i2].intValue();
                int i3 = i + 1;
                Log.d(TAG, "mPackageReceiverForKnox() i : " + i3);
                if (userProfiles != null) {
                    Iterator<UserHandle> it = userProfiles.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            userHandle = null;
                            break;
                        }
                        UserHandle next = it.next();
                        if (next.hashCode() == intValue) {
                            userHandle = next;
                            break;
                        }
                    }
                    if (userHandle != null) {
                        Log.d(TAG, "mPackageReceiverForKnox() Register receiver for user : " + userHandle);
                        PackageManagerReceiver packageManagerReceiver = new PackageManagerReceiver(intValue);
                        registerReceiverForKnox(context, packageManagerReceiver, userHandle, intentFilter, null, null);
                        mPackageReceiverForKnoxList.add(packageManagerReceiver);
                    }
                }
                i2++;
                i = i3;
            }
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(ACTION_USER_REMOVED);
        intentFilter2.addAction(ACTION_USER_ADDED);
        context.registerReceiver(mPackageReceiver, intentFilter2);
    }

    public static void unregisterReceivers(Context context) {
        context.unregisterReceiver(mPackageReceiver);
        Iterator<PackageManagerReceiver> it = mPackageReceiverForKnoxList.iterator();
        while (it.hasNext()) {
            context.unregisterReceiver(it.next());
        }
        mPackageReceiverForKnoxList.clear();
    }

    private static void registerReceiverForKnox(Context context, BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String str, Handler handler) {
        Log.v(TAG, "registerReceiverForKnox() userHandle : " + userHandle);
        try {
            context.getClass().getMethod("registerReceiverAsUser", BroadcastReceiver.class, UserHandle.class, IntentFilter.class, String.class, Handler.class).invoke(context, broadcastReceiver, userHandle, intentFilter, str, handler);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    private ApplicationInfo getApplicationInfo(Context context, String str) {
        if (this.mUserId == 0) {
            try {
                return context.getPackageManager().getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Unable to get package Info");
                e.printStackTrace();
                return null;
            }
        } else {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            List<ResolveInfo> proxyQueryIntentActivitiesAsUser = seccompat.android.content.pm.PackageManager.proxyQueryIntentActivitiesAsUser(context.getPackageManager(), intent, 0, this.mUserId);
            if (proxyQueryIntentActivitiesAsUser == null || proxyQueryIntentActivitiesAsUser.size() <= 0) {
                return null;
            }
            return proxyQueryIntentActivitiesAsUser.get(0).activityInfo.applicationInfo;
        }
    }
}
