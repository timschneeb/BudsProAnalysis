package com.samsung.android.app.twatchmanager.update;

import android.content.SharedPreferences;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import java.util.Set;

public class UpdateHistoryManager {
    public static final String PREF_KEY_CONTENT_SIZE = StubAPIHelper.XMLResultKey.CONTENT_SIZE.toString();
    public static final String PREF_KEY_PACKAGE_NAME = StubAPIHelper.XMLResultKey.APP_ID.toString();
    public static final String PREF_KEY_RESULT_CODE = StubAPIHelper.XMLResultKey.RESULT_CODE.toString();
    public static final String PREF_KEY_STORE_SETTING = "pd";
    public static final String PREF_KEY_UPDATE_DESCRIPTION = StubAPIHelper.XMLResultKey.UPDATE_DESCRIPTION.toString();
    public static final String PREF_KEY_UPDATE_STATUS = "update_status";
    public static final String PREF_KEY_VERSION_CODE = StubAPIHelper.XMLResultKey.VERSION_CODE.toString();
    public static final String PREF_KEY_VERSION_NAME = StubAPIHelper.XMLResultKey.VERSION_NAME.toString();
    public static final int STATUS_AVAILABLE = 1;
    public static final int STATUS_COMPLETED = 0;
    public static final int STATUS_FAILED = 2;
    private static final String TAG = ("tUHM:[Update]" + UpdateHistoryManager.class.getSimpleName());
    private static UpdateHistoryManager mInstance = null;

    public static UpdateHistoryManager getInstance() {
        if (mInstance == null) {
            mInstance = new UpdateHistoryManager();
        }
        return mInstance;
    }

    public String getLastCheckedTime() {
        return TWatchManagerApplication.getAppContext().getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).getString(GlobalConst.PREV_UPDATE_TIME, "");
    }

    public boolean getUpdateAvailable(String str) {
        SharedPreferences sharedPreferences = TWatchManagerApplication.getAppContext().getSharedPreferences("update_info[" + str + "]", 0);
        String string = sharedPreferences.getString(PREF_KEY_RESULT_CODE, "");
        int i = sharedPreferences.getInt(PREF_KEY_UPDATE_STATUS, 2);
        String str2 = TAG;
        Log.d(str2, "getUpdateAvailable() packageName : " + str + " result_code : " + string + " updateStatus : " + i);
        return "2".equals(string) && i == 1;
    }

    public String getUpdateHistoryData(String str, String str2) {
        return TWatchManagerApplication.getAppContext().getSharedPreferences("update_info[" + str + "]", 0).getString(str2, "");
    }

    public void setLastCheckedTime(String str) {
        SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences(GlobalConst.XML_AUTO_UPDATE, 0).edit();
        edit.putString(GlobalConst.PREV_UPDATE_TIME, str);
        edit.apply();
    }

    public void setUpdateHistory(StubAPIHelper.XMLResult xMLResult) {
        if (xMLResult != null) {
            String str = xMLResult.get(StubAPIHelper.XMLResultKey.APP_ID);
            String str2 = xMLResult.get(StubAPIHelper.XMLResultKey.RESULT_CODE);
            String str3 = xMLResult.get(StubAPIHelper.XMLResultKey.VERSION_NAME);
            String str4 = xMLResult.get(StubAPIHelper.XMLResultKey.VERSION_CODE);
            String str5 = xMLResult.get(StubAPIHelper.XMLResultKey.CONTENT_SIZE);
            String str6 = xMLResult.get(StubAPIHelper.XMLResultKey.UPDATE_DESCRIPTION);
            SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences("update_info[" + str + "]", 0).edit();
            edit.putString(PREF_KEY_PACKAGE_NAME, str);
            edit.putString(PREF_KEY_STORE_SETTING, UpdateUtil.getPD());
            edit.putString(PREF_KEY_RESULT_CODE, str2);
            edit.putString(PREF_KEY_VERSION_NAME, str3);
            edit.putString(PREF_KEY_VERSION_CODE, str4);
            edit.putString(PREF_KEY_CONTENT_SIZE, str5);
            edit.putString(PREF_KEY_UPDATE_DESCRIPTION, str6);
            edit.putInt(PREF_KEY_UPDATE_STATUS, 1);
            edit.apply();
            return;
        }
        Log.d(TAG, "setUpdateHistory() stub result is null");
    }

    public void updateStatus(String str, int i) {
        String str2 = TAG;
        Log.d(str2, "updateStatus() packageName : " + str + " set status to : " + i);
        StringBuilder sb = new StringBuilder();
        sb.append("update_info[");
        sb.append(str);
        sb.append("]");
        SharedPreferences.Editor edit = TWatchManagerApplication.getAppContext().getSharedPreferences(sb.toString(), 0).edit();
        edit.putInt(PREF_KEY_UPDATE_STATUS, i);
        edit.apply();
    }

    public void updateStatus(Set<String> set, int i) {
        if (set != null) {
            for (String str : set) {
                updateStatus(str, i);
            }
        }
    }
}
