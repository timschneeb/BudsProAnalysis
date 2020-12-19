package com.samsung.android.app.twatchmanager.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import java.util.List;

public class RegistryAppsDBManager {
    public static final String TAG = "RegistryAppsDBManager";

    private static synchronized void appsDBUpdateInternal(String str, ContentValues contentValues, Context context) {
        int i;
        synchronized (RegistryAppsDBManager.class) {
            try {
                i = context.getContentResolver().update(BaseContentProvider.APP_CONTENT_URI, contentValues, "package_name = ?", new String[]{str});
            } catch (RuntimeException e) {
                e.printStackTrace();
                i = 0;
            }
            String str2 = TAG;
            Log.d(str2, "appsDBUpdateInternal() update result = " + i);
            Uri uri = null;
            if (i == 0) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("package_name", str);
                contentValues2.put(BaseContentProvider.APP_NAME, "");
                contentValues2.put(BaseContentProvider.VERSION, Integer.valueOf(HostManagerUtils.getVersionCode(context, str)));
                contentValues2.put(BaseContentProvider.UPDATE_CANCEL_COUNT, (Integer) 0);
                contentValues2.put(BaseContentProvider.DEVICE_BT_ID, "");
                contentValues2.putAll(contentValues);
                try {
                    uri = context.getContentResolver().insert(BaseContentProvider.APP_CONTENT_URI, contentValues2);
                } catch (RuntimeException e2) {
                    e2.printStackTrace();
                }
            }
            printAllAppData("appsDBUpdateInternal", context);
            String str3 = TAG;
            Log.d(str3, "appsDBUpdateInternal, insert result = " + uri);
        }
    }

    public static synchronized int deleteAppRegistryData(String str, Context context) {
        int i;
        int i2;
        synchronized (RegistryAppsDBManager.class) {
            String str2 = TAG;
            Log.d(str2, "deleteAppRegistryData() starts..." + str);
            i = 0;
            String[] strArr = {""};
            strArr[0] = str;
            try {
                i2 = context.getContentResolver().delete(BaseContentProvider.DEVICE_CONTENT_URI, "package_name=?", strArr);
            } catch (RuntimeException e) {
                e.printStackTrace();
                i2 = 0;
            }
            String str3 = TAG;
            Log.d(str3, "deleteAppRegistryData() remove deviceDB items from DEVICE table:" + i2);
            try {
                i = context.getContentResolver().delete(BaseContentProvider.APP_CONTENT_URI, "package_name=?", strArr);
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            }
            String str4 = TAG;
            Log.d(str4, "deleteAppRegistryData() remove app DB items from APP table:" + i);
        }
        return i;
    }

    public static synchronized void printAllAppData(String str, Context context) {
        synchronized (RegistryAppsDBManager.class) {
            List<AppRegistryData> queryAllAppRegistryData = queryAllAppRegistryData(context);
            String str2 = TAG;
            Log.d(str2, "printAllAppData() DB:: called from " + str + "appList : " + queryAllAppRegistryData);
            if (queryAllAppRegistryData != null) {
                int size = queryAllAppRegistryData.size();
                for (int i = 0; i < size; i++) {
                    String str3 = TAG;
                    Log.d(str3, "[indx : " + i + "]" + queryAllAppRegistryData.get(i));
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0076  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.List<com.samsung.android.app.twatchmanager.contentprovider.AppRegistryData> queryAllAppRegistryData(android.content.Context r9) {
        /*
        // Method dump skipped, instructions count: 154
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager.queryAllAppRegistryData(android.content.Context):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.List<com.samsung.android.app.twatchmanager.contentprovider.AppRegistryData> queryAppRegistryDataByPackageName(java.lang.String r9, android.content.Context r10) {
        /*
        // Method dump skipped, instructions count: 172
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.contentprovider.RegistryAppsDBManager.queryAppRegistryDataByPackageName(java.lang.String, android.content.Context):java.util.List");
    }

    public static synchronized void updateAppUpdateCancelCount(String str, int i, Context context) {
        synchronized (RegistryAppsDBManager.class) {
            String str2 = TAG;
            Log.d(str2, "updateAppUpdateCancelCount() packageName : " + str);
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseContentProvider.UPDATE_CANCEL_COUNT, Integer.valueOf(i));
            appsDBUpdateInternal(str, contentValues, context);
        }
    }

    public static synchronized void updateAppVersion(String str, int i, Context context) {
        synchronized (RegistryAppsDBManager.class) {
            ContentValues contentValues = new ContentValues();
            String valueOf = String.valueOf(i);
            String str2 = TAG;
            Log.d(str2, "updateAppVersion() packageName : " + str + " strVersionCode : " + valueOf);
            contentValues.put(BaseContentProvider.VERSION, valueOf);
            appsDBUpdateInternal(str, contentValues, context);
        }
    }
}
