package com.accessorydm.ui;

import android.app.Activity;
import com.accessorydm.ui.dialog.XUIDialogActivity;
import com.samsung.android.fotaprovider.log.Log;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UIManager {
    private static final UIManager instance = new UIManager();
    private final Map<String, Activity> activityMap = new ConcurrentHashMap();

    public static UIManager getInstance() {
        return instance;
    }

    public void put(Activity activity) {
        String str;
        if (activity instanceof XUIDialogActivity) {
            str = XUIDialogActivity.class.getName();
        } else {
            str = activity.getClass().getName();
        }
        Activity put = this.activityMap.put(str, activity);
        if (allowsToFinish(put)) {
            Log.W("previous activity[" + put + "] still exists, finish it");
            put.finish();
        }
    }

    public void remove(Activity activity) {
        String str;
        if (activity instanceof XUIDialogActivity) {
            str = XUIDialogActivity.class.getName();
        } else {
            str = activity.getClass().getName();
        }
        Activity activity2 = this.activityMap.get(str);
        if (activity != activity2) {
            Log.W("[" + activity + "] is different from [" + activity2 + "] in map; do not remove.");
            return;
        }
        this.activityMap.remove(str);
    }

    public void finishAllActivities() {
        Log.I("");
        finishIfNot("");
    }

    public void finishAllActivitiesExcept(String str) {
        Log.I("except " + str);
        finishIfNot(str);
    }

    private void finishIfNot(String str) {
        for (String str2 : this.activityMap.keySet()) {
            if (!str2.equals(str)) {
                finish(str2);
            }
        }
    }

    public void finish(String str) {
        Activity activity = this.activityMap.get(str);
        if (allowsToFinish(activity)) {
            activity.finish();
        }
    }

    private static boolean allowsToFinish(Activity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }
}
