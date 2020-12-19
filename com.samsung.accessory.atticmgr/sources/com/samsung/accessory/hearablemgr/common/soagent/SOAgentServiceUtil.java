package com.samsung.accessory.hearablemgr.common.soagent;

import android.os.AsyncTask;
import android.os.Handler;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import seccompat.android.util.Log;

public class SOAgentServiceUtil {
    private static int LEFT = 0;
    private static int RIGHT = 1;
    private static final String TAG = (Application.TAG_ + SOAgentServiceUtil.class.getSimpleName());
    private static boolean mIsSelloutTaskRunning = false;
    private static String testDeviceSN = "00000000000";

    public static synchronized void checkSellOutInfoUpdate(final String str, final String str2, final String str3) {
        synchronized (SOAgentServiceUtil.class) {
            String str4 = TAG;
            Log.d(str4, "leftSN : " + str + ", rightSN : " + str2 + ", btAddress : " + str3);
            if (mIsSelloutTaskRunning) {
                Log.d(TAG, "sell out is running...");
            } else if (Util.getActiveNetworkInfo() < 0) {
                Log.d(TAG, "<<checkSellOutInfoUpdate>> Network is not available");
                mIsSelloutTaskRunning = false;
            } else {
                mIsSelloutTaskRunning = true;
                new Handler().post(new Runnable() {
                    /* class com.samsung.accessory.hearablemgr.common.soagent.SOAgentServiceUtil.AnonymousClass1 */

                    public void run() {
                        if (!SOAgentServiceUtil.checkSNValidation(str) || !SOAgentServiceUtil.checkSNValidation(str2)) {
                            boolean unused = SOAgentServiceUtil.mIsSelloutTaskRunning = false;
                        } else if (str.equals(SOAgentServiceUtil.testDeviceSN) || str2.equals(SOAgentServiceUtil.testDeviceSN)) {
                            Log.d(SOAgentServiceUtil.TAG, "you are using test devices");
                            boolean unused2 = SOAgentServiceUtil.mIsSelloutTaskRunning = false;
                        } else {
                            String str = str3;
                            if (str == null || str.equals("")) {
                                Log.d(SOAgentServiceUtil.TAG, "device address is null");
                                boolean unused3 = SOAgentServiceUtil.mIsSelloutTaskRunning = false;
                            } else if (SOAgentServiceUtil.isLastSelloutSerialNumber(str, str2).booleanValue()) {
                                Log.d(SOAgentServiceUtil.TAG, "already registered device");
                                boolean unused4 = SOAgentServiceUtil.mIsSelloutTaskRunning = false;
                            } else {
                                new SOAgentServiceTask().execute(str, str2, str3);
                            }
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean checkSNValidation(String str) {
        if (str == null) {
            Log.d(TAG, "checkSNValidation :: input is null");
            return false;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] <= 31) {
                Log.d(TAG, "SN has wrong character - inputCharArray[" + i + "]: " + charArray[i]);
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void setLastSellOutSerialNumber(String str, String str2) {
        Preferences.putString(PreferenceKey.LAST_SELL_OUT_SERIAL_NUMBER + LEFT, str, Preferences.MODE_MANAGER);
        Preferences.putString(PreferenceKey.LAST_SELL_OUT_SERIAL_NUMBER + RIGHT, str2, Preferences.MODE_MANAGER);
    }

    /* access modifiers changed from: private */
    public static Boolean isLastSelloutSerialNumber(String str, String str2) {
        String string = Preferences.getString(PreferenceKey.LAST_SELL_OUT_SERIAL_NUMBER + LEFT, testDeviceSN, Preferences.MODE_MANAGER);
        StringBuilder sb = new StringBuilder();
        sb.append(PreferenceKey.LAST_SELL_OUT_SERIAL_NUMBER);
        sb.append(RIGHT);
        return Boolean.valueOf(string.equals(str) && Preferences.getString(sb.toString(), testDeviceSN, Preferences.MODE_MANAGER).equals(str2));
    }

    private static class SOAgentServiceTask extends AsyncTask<String, Void, Boolean> {
        String btAddress;
        String leftSN;
        String rightSN;

        private SOAgentServiceTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            this.leftSN = strArr[0];
            this.rightSN = strArr[1];
            this.btAddress = strArr[2];
            boolean z = false;
            for (int i = 0; i < 3 && !(z = SOAgentService.getInstance().sendAccessoryInfo("SM-R190", this.leftSN, this.btAddress)); i++) {
            }
            if (!z) {
                return false;
            }
            for (int i2 = 0; i2 < 3; i2++) {
                z = SOAgentService.getInstance().sendAccessoryInfo("SM-R190", this.rightSN, this.btAddress);
                if (z) {
                    break;
                }
            }
            return Boolean.valueOf(z);
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            Log.d(SOAgentServiceUtil.TAG, "SOAgentServiceTask Start");
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            String str = SOAgentServiceUtil.TAG;
            Log.d(str, "SOAgentServiceTask End - result: " + bool);
            if (bool.booleanValue()) {
                SOAgentServiceUtil.setLastSellOutSerialNumber(this.leftSN, this.rightSN);
            }
            super.onPostExecute((Object) bool);
            boolean unused = SOAgentServiceUtil.mIsSelloutTaskRunning = false;
        }
    }
}
