package com.samsung.android.fotaprovider.util.galaxywearable;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BroadcastHelper {
    public static void sendBroadcast(Intent intent) {
        if (getPluginTargetSdkVersion() >= 26 && Build.VERSION.SDK_INT >= 26 && TextUtils.isEmpty(intent.getPackage())) {
            Iterator<Intent> it = convertImplicitToExplicitForBroadCast(intent).iterator();
            while (it.hasNext()) {
                FotaProviderInitializer.getContext().sendBroadcast(it.next());
            }
        }
        FotaProviderInitializer.getContext().sendBroadcast(intent);
    }

    public static void sendBroadcast(Intent intent, String str) {
        if (getPluginTargetSdkVersion() >= 26 && Build.VERSION.SDK_INT >= 26 && TextUtils.isEmpty(intent.getPackage())) {
            Iterator<Intent> it = convertImplicitToExplicitForBroadCast(intent).iterator();
            while (it.hasNext()) {
                FotaProviderInitializer.getContext().sendBroadcast(it.next(), str);
            }
        }
        FotaProviderInitializer.getContext().sendBroadcast(intent, str);
    }

    private static ArrayList<Intent> convertImplicitToExplicitForBroadCast(Intent intent) {
        List<ResolveInfo> queryBroadcastReceivers = FotaProviderInitializer.getContext().getPackageManager().queryBroadcastReceivers(intent, 0);
        ArrayList<Intent> arrayList = new ArrayList<>();
        if (queryBroadcastReceivers != null) {
            for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
                ComponentName componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                Intent intent2 = new Intent(intent);
                intent2.setComponent(componentName);
                arrayList.add(intent2);
            }
        }
        return arrayList;
    }

    private static int getPluginTargetSdkVersion() {
        try {
            return FotaProviderInitializer.getContext().getPackageManager().getPackageInfo(FotaProviderInitializer.getContext().getPackageName(), 0).applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 23;
        }
    }
}
