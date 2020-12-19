package com.samsung.accessory.hearablemgr.core.notification;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import seccompat.android.util.Log;

public class NotificationManager {
    private static final Object SYNCHRONIZED_OBJECT = new Object();
    private static final String TAG = "Attic_NotificationManager";
    private static NotificationManager instance;
    private static ArrayList<NotificationAppData> mNotificationAppList = new ArrayList<>();
    private boolean inBandEnabled = false;
    private boolean isCreated = false;
    private initAppListThread mInitApp = null;

    public static synchronized NotificationManager getInstance(Context context) {
        NotificationManager notificationManager;
        synchronized (NotificationManager.class) {
            synchronized (SYNCHRONIZED_OBJECT) {
                if (instance == null) {
                    instance = new NotificationManager(context);
                }
                notificationManager = instance;
            }
        }
        return notificationManager;
    }

    public static synchronized boolean hasInstance() {
        boolean z;
        synchronized (NotificationManager.class) {
            synchronized (SYNCHRONIZED_OBJECT) {
                z = instance != null;
            }
        }
        return z;
    }

    public void destroy() {
        synchronized (SYNCHRONIZED_OBJECT) {
            instance = null;
        }
        this.isCreated = false;
        Log.d(TAG, "destroy()");
    }

    private NotificationManager(Context context) {
        Log.d(TAG, "NotificationManager()");
        synchronized (this) {
            this.mInitApp = new initAppListThread(context);
            this.mInitApp.start();
        }
    }

    /* access modifiers changed from: private */
    public class initAppListThread extends Thread {
        private Context mCt;

        public initAppListThread(Context context) {
            this.mCt = context;
        }

        public void run() {
            NotificationManager.this.initializeAppList(this.mCt);
            Log.d(NotificationManager.TAG, "send update activity list Intent3");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void initializeAppList(Context context) {
        Log.d(TAG, "initializeAppList()");
        ArrayList arrayList = new ArrayList();
        ArrayList<NotificationAppData> arrayList2 = new ArrayList<>();
        mNotificationAppList.clear();
        this.inBandEnabled = NotificationUtil.getInBandRingtone();
        Log.d(TAG, "support IBR = " + this.inBandEnabled);
        for (int i = 0; i < NotificationConstants.mAlertApps.length; i++) {
            try {
                Log.d(TAG, "mAlertAppList() = " + i);
                if (!this.inBandEnabled && i == 0) {
                    Log.d(TAG, "mAlertAppList() = " + i + " | skip");
                } else if (Util.isSamsungDevice() || i != 3) {
                    if ((i == 0 || i == 1 || i == 4) && !hasTelephonySupport(context)) {
                        String string = Settings.Global.getString(context.getContentResolver(), "cmc_device_type");
                        Log.d(TAG, "cmc type = " + string);
                        if (string == null || string.equals("")) {
                            if (i != 4) {
                                Log.d(TAG, "mAlertAppList() = " + i + " | skip");
                            } else if (!NotificationUtil.isInstalledPackage(context, "com.samsung.android.messaging")) {
                                Log.d(TAG, "mAlertAppList() = " + i + " | skip");
                            }
                        }
                    }
                    if (i > 1) {
                        context.getPackageManager().getApplicationIcon(NotificationConstants.mAlertApps[i]);
                    }
                    arrayList.add(new NotificationAppData(NotificationUtil.isAppNotificationEnabled(NotificationConstants.mAlertApps[i]), context.getResources().getString(NotificationConstants.mAlertApps_Name[i]), NotificationConstants.mAlertApps[i], false));
                } else {
                    Log.d(TAG, "mAlertAppList() = other company calendar | skip");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e2) {
                Log.d(TAG, "noti initialize OutOfMemoryError");
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        List<ResolveInfo> applicableApps = getApplicableApps(context);
        Log.d(TAG, "installed App, applicableAppsList.size = " + applicableApps.size());
        for (ResolveInfo resolveInfo : applicableApps) {
            String str = resolveInfo.activityInfo.applicationInfo.packageName;
            boolean isAppNotificationEnabled = NotificationUtil.isAppNotificationEnabled(str);
            if (!isPreDefinedApps(str) && !NotificationUtil.isExcludeApp(str) && !isExistApp(mNotificationAppList, str)) {
                if (Util.isSamsungDevice() || (!str.contains("clock") && !str.contains(NotificationCompat.CATEGORY_ALARM))) {
                    Log.d(TAG, "applicationInfo:pkgName() = " + str);
                    try {
                        String charSequence = resolveInfo.activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                        if (isContainApplist(arrayList2, str)) {
                            arrayList2.add(new NotificationAppData(isAppNotificationEnabled, charSequence, str, false));
                        }
                    } catch (OutOfMemoryError e4) {
                        Log.d(TAG, "noti initialize OutOfMemoryError");
                        e4.printStackTrace();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            }
        }
        boolean isInstalledPackage = NotificationUtil.isInstalledPackage(context, NotificationConstants.PEOPLE_STRIPE_APP_PACKAGENAME);
        boolean isAppNotificationEnabled2 = NotificationUtil.isAppNotificationEnabled(NotificationConstants.PEOPLE_STRIPE_APP_PACKAGENAME);
        if (isInstalledPackage) {
            Log.d(TAG, "coreAppsExist = " + isInstalledPackage);
            try {
                String str2 = (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(NotificationConstants.PEOPLE_STRIPE_APP_PACKAGENAME, 8192));
                if (isContainApplist(arrayList2, NotificationConstants.PEOPLE_STRIPE_APP_PACKAGENAME)) {
                    arrayList2.add(new NotificationAppData(isAppNotificationEnabled2, str2, NotificationConstants.PEOPLE_STRIPE_APP_PACKAGENAME, false));
                }
            } catch (OutOfMemoryError e6) {
                Log.d(TAG, "noti initialize OutOfMemoryError");
                e6.printStackTrace();
            } catch (PackageManager.NameNotFoundException e7) {
                e7.printStackTrace();
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        }
        if (Util.isSamsungDevice() && Build.VERSION.SDK_INT >= 21) {
            for (Integer num : NotificationConstants.KNOX_USERID) {
                int intValue = num.intValue();
                Log.d(TAG, "Get notificatios for user : " + intValue);
                List<ResolveInfo> listOfApplicableApps = getListOfApplicableApps(context, intValue);
                if (listOfApplicableApps != null) {
                    for (ResolveInfo resolveInfo2 : listOfApplicableApps) {
                        String str3 = resolveInfo2.activityInfo.applicationInfo.packageName;
                        String charSequence2 = resolveInfo2.activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                        NotificationAppData notificationAppData = new NotificationAppData(NotificationUtil.isAppNotificationEnabled(str3 + NotificationConstants.DUAL), charSequence2, str3 + NotificationConstants.DUAL, true);
                        notificationAppData.setuId(intValue);
                        arrayList2.add(notificationAppData);
                    }
                }
            }
        }
        this.isCreated = true;
        if (this.mInitApp != null) {
            this.mInitApp = null;
        }
        Collections.sort(arrayList, new AppListComparator());
        mNotificationAppList.addAll(arrayList);
        Collections.sort(arrayList2, new AppListComparator());
        mNotificationAppList.addAll(arrayList2);
    }

    public boolean hasTelephonySupport(Context context) {
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        Log.d(TAG, "hasTelephonySupport = " + hasSystemFeature);
        return hasSystemFeature;
    }

    public boolean isContainApplist(ArrayList<NotificationAppData> arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getPackageName() != null && arrayList.get(i).getPackageName().equals(str)) {
                return false;
            }
        }
        return true;
    }

    public boolean isListCreated() {
        return this.isCreated;
    }

    public void addToList(ArrayList<NotificationAppData> arrayList, NotificationAppData notificationAppData) {
        arrayList.add(notificationAppData);
    }

    public boolean removeFromList(ArrayList<NotificationAppData> arrayList, String str) {
        try {
            Iterator<NotificationAppData> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().getPackageName().equals(str)) {
                    it.remove();
                    Log.d(TAG, "removeFromList():app = " + str);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPreDefinedApps(String str) {
        for (int i = 0; i < NotificationConstants.mAlertApps.length; i++) {
            if (NotificationConstants.mAlertApps[i].equalsIgnoreCase(str)) {
                Log.d(TAG, "applicationInfo:pkgName() = " + str + " is PreDefinedApp | skip ");
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isExistApp(ArrayList<NotificationAppData> arrayList, String str) {
        boolean z;
        z = false;
        if (str != null && arrayList != null) {
            try {
                if (arrayList.size() > 0) {
                    Iterator<NotificationAppData> it = arrayList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().getPackageName().equalsIgnoreCase(str)) {
                                z = true;
                                Log.d(TAG, "applicationInfo:pkgName() = " + str + " is ExistApp | skip ");
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return z;
    }

    public boolean hasLauncherIntent(Context context, String str) {
        try {
            if (context.getPackageManager().getLaunchIntentForPackage(str) != null) {
                return true;
            }
            throw new PackageManager.NameNotFoundException();
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public synchronized ArrayList<NotificationAppData> getNotificationAppList() {
        return mNotificationAppList;
    }

    public synchronized void setCheckIncomingCallStatus(boolean z) {
        int i = 0;
        while (true) {
            if (i >= mNotificationAppList.size()) {
                break;
            } else if (mNotificationAppList.get(i).getPackageName().equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
                mNotificationAppList.get(i).setEnable(z);
                break;
            } else {
                i++;
            }
        }
    }

    public synchronized ArrayList<NotificationAppData> getAllowedNotificationList(boolean z) {
        ArrayList<NotificationAppData> arrayList;
        arrayList = new ArrayList<>();
        for (int i = 0; i < mNotificationAppList.size(); i++) {
            boolean semAreNotificationsEnabledForPackage = NotificationUtil.semAreNotificationsEnabledForPackage(mNotificationAppList.get(i).getPackageName(), mNotificationAppList.get(i).isDual(), mNotificationAppList.get(i).getuId());
            if (z) {
                if (mNotificationAppList.get(i).isEnable() && semAreNotificationsEnabledForPackage) {
                    arrayList.add(mNotificationAppList.get(i));
                }
            } else if (!mNotificationAppList.get(i).isEnable() || !semAreNotificationsEnabledForPackage) {
                arrayList.add(mNotificationAppList.get(i));
            }
        }
        return arrayList;
    }

    public synchronized void setAppAllowed(int i, String str, boolean z) {
        if (mNotificationAppList.get(i).getPackageName().equals(str)) {
            mNotificationAppList.get(i).setEnable(z);
        }
    }

    public synchronized NotificationAppData getNotificationAppData(String str) {
        for (int i = 0; i < mNotificationAppList.size(); i++) {
            if (mNotificationAppList.get(i).getPackageName().equals(str)) {
                return mNotificationAppList.get(i);
            }
        }
        return null;
    }

    private static List<ResolveInfo> getApplicableApps(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        Intent intent2 = new Intent("android.intent.action.MAIN", (Uri) null);
        intent2.addCategory("android.intent.category.INFO");
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent2, 0)) {
            queryIntentActivities.add(resolveInfo);
        }
        return queryIntentActivities;
    }

    public void updateAppNameApp(Context context) {
        ArrayList<NotificationAppData> notificationAppList = getNotificationAppList();
        Log.d(TAG, "updateAppNameApp() " + notificationAppList.size());
        if (notificationAppList != null && notificationAppList.size() > 0) {
            Iterator<NotificationAppData> it = notificationAppList.iterator();
            while (it.hasNext()) {
                NotificationAppData next = it.next();
                if (next.getPackageName().equals(NotificationConstants.mAlertApps[0])) {
                    next.setAppName(context.getResources().getString(NotificationConstants.mAlertApps_Name[0]));
                } else if (next.getPackageName().equals(NotificationConstants.mAlertApps[1])) {
                    next.setAppName(context.getResources().getString(NotificationConstants.mAlertApps_Name[1]));
                } else if (next.getPackageName().equals(NotificationConstants.mAlertApps[2])) {
                    next.setAppName(context.getResources().getString(NotificationConstants.mAlertApps_Name[2]));
                } else if (next.getPackageName().equals(NotificationConstants.mAlertApps[3])) {
                    next.setAppName(context.getResources().getString(NotificationConstants.mAlertApps_Name[3]));
                } else if (next.getPackageName().equals(NotificationConstants.mAlertApps[4])) {
                    next.setAppName(context.getResources().getString(NotificationConstants.mAlertApps_Name[4]));
                } else if (next.getPackageName().equals(NotificationConstants.mAlertApps[5])) {
                    next.setAppName(context.getResources().getString(NotificationConstants.mAlertApps_Name[5]));
                } else {
                    PackageManager packageManager = context.getPackageManager();
                    try {
                        String packageName = next.getPackageName();
                        if (next.isDual()) {
                            packageName = packageName.substring(0, packageName.indexOf(NotificationConstants.DUAL));
                        }
                        next.setAppName(packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0)).toString());
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void notifyListUpdated(Context context) {
        this.isCreated = false;
        if (this.mInitApp == null) {
            Log.d(TAG, "notifyListUpdated(), start thread");
            this.mInitApp = new initAppListThread(context);
            this.mInitApp.start();
        }
        Util.sendPermissionBroadcast(context, new Intent(NotificationConstants.ACTION_NOTIFICATION_LIST_UPDATE));
    }

    public void notifyOnlyListUpdated(Context context) {
        this.isCreated = false;
        Log.d(TAG, "notifyOnlyListUpdated()");
        if (this.mInitApp == null) {
            Log.d(TAG, "notifyListUpdated(), start thread");
            this.mInitApp = new initAppListThread(context);
            this.mInitApp.start();
        }
    }

    public void notifySettingChanged(Context context) {
        this.isCreated = false;
        if (this.mInitApp == null) {
            Log.d(TAG, "notifySettingChanged(), start thread");
            this.mInitApp = new initAppListThread(context);
            this.mInitApp.start();
        }
        context.sendBroadcast(new Intent(NotificationConstants.ACTION_NOTIFICATION_SETTING_UPDATE));
    }

    /* access modifiers changed from: private */
    public static class AppListComparator implements Comparator<NotificationAppData> {
        private AppListComparator() {
        }

        public int compare(NotificationAppData notificationAppData, NotificationAppData notificationAppData2) {
            Collator instance = Collator.getInstance();
            instance.setStrength(0);
            return instance.compare(notificationAppData.getAppName(), notificationAppData2.getAppName());
        }
    }

    public static List<ResolveInfo> getListOfApplicableApps(Context context, int i) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        return seccompat.android.content.pm.PackageManager.proxyQueryIntentActivitiesAsUser(context.getPackageManager(), intent, 0, i);
    }

    private static Drawable getBitmapWithUserBadge(Context context, int i, Drawable drawable) {
        UserHandle userHandle;
        List<UserHandle> userProfiles = ((UserManager) context.getSystemService("user")).getUserProfiles();
        if (userProfiles == null) {
            return drawable;
        }
        Iterator<UserHandle> it = userProfiles.iterator();
        while (true) {
            if (!it.hasNext()) {
                userHandle = null;
                break;
            }
            userHandle = it.next();
            if (userHandle.hashCode() == i) {
                break;
            }
        }
        return userHandle != null ? context.getPackageManager().getUserBadgedIcon(drawable, userHandle) : drawable;
    }

    public static int getAppCheckedCount(Context context) {
        Iterator<NotificationAppData> it = getInstance(context).getNotificationAppList().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isEnable()) {
                i++;
            }
        }
        return i;
    }

    public static Drawable getImageIcon(Context context, String str, boolean z, int i) {
        Log.d(TAG, "getImageIcon for user : " + i);
        if (str.equals(NotificationConstants.INCOMING_CALL_PACKAGENAME)) {
            return context.getResources().getDrawable(R.drawable.gm_list_ic_incomimgcall);
        }
        if (str.equals(NotificationConstants.MISSED_CALL_PACKAGENAME)) {
            return context.getResources().getDrawable(R.drawable.gm_list_ic_missedcall);
        }
        Drawable drawable = null;
        if (!z) {
            try {
                Drawable applicationIcon = context.getPackageManager().getApplicationIcon(str);
                if (applicationIcon instanceof BitmapDrawable) {
                    return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(((BitmapDrawable) applicationIcon).getBitmap(), 100, 100, true));
                }
                Log.e(TAG, "not bitmap");
                return applicationIcon;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else if (Util.isSamsungDevice() && Build.VERSION.SDK_INT >= 21) {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str.substring(0, str.indexOf(NotificationConstants.DUAL)));
            List<ResolveInfo> proxyQueryIntentActivitiesAsUser = seccompat.android.content.pm.PackageManager.proxyQueryIntentActivitiesAsUser(context.getPackageManager(), intent, 0, i);
            Log.d(TAG, "getBitmapByPackageName() packageName : " + str);
            if (proxyQueryIntentActivitiesAsUser != null && proxyQueryIntentActivitiesAsUser.size() > 0) {
                drawable = getBitmapWithUserBadge(context, i, proxyQueryIntentActivitiesAsUser.get(0).loadIcon(context.getPackageManager()));
            }
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                int dimension = (int) context.getResources().getDimension(R.dimen.main_menu_icon_size);
                return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, dimension, dimension, true));
            }
            Log.e(TAG, "not bitmap");
            return drawable;
        }
        return null;
    }

    public static void updateSAStatusNotificationAppToSelect() {
        Context context = Application.getContext();
        NotificationManager instance2 = getInstance(context);
        if (instance2 != null && instance2.isListCreated()) {
            SamsungAnalyticsUtil.setStatusInt(SA.Status.APP_TO_SELECT, getAppCheckedCount(context));
        }
    }
}
