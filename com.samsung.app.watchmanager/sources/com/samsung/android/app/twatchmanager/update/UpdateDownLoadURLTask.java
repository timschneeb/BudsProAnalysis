package com.samsung.android.app.twatchmanager.update;

import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.BaseUpdateTask;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class UpdateDownLoadURLTask extends BaseUpdateTask {
    public static final String RESULT_SUCCESS = "1";
    private static final String TAG = ("tUHM:" + UpdateDownLoadURLTask.class.getSimpleName());
    private String mExtuk;
    private String mPackageString;

    public UpdateDownLoadURLTask(Set<String> set, BaseUpdateTask.IUpdateTaskCallback iUpdateTaskCallback, String str) {
        super(set, iUpdateTaskCallback);
        this.mExtuk = str;
    }

    private void handleDownloadCheckResult(ArrayList<StubAPIHelper.XMLResult> arrayList) {
        this.mResultMap.clear();
        this.mTotalContentSize = 0;
        if (arrayList != null) {
            Iterator<StubAPIHelper.XMLResult> it = arrayList.iterator();
            while (it.hasNext()) {
                StubAPIHelper.XMLResult next = it.next();
                String str = next.get(StubAPIHelper.XMLResultKey.APP_ID);
                String str2 = next.get(StubAPIHelper.XMLResultKey.CONTENT_SIZE);
                next.get(StubAPIHelper.XMLResultKey.SIGNATURE);
                String str3 = next.get(StubAPIHelper.XMLResultKey.RESULT_CODE);
                String str4 = next.get(StubAPIHelper.XMLResultKey.DOWNLOAD_URI);
                Log.d(TAG, "handleDownloadCheckResult() result..\n" + next.printAllData());
                if (!TextUtils.isEmpty(str4) && "1".equals(str3)) {
                    try {
                        this.mTotalContentSize += Integer.parseInt(str2);
                        this.mResultMap.put(str, next);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d(TAG, "handleDownloadCheckResult() Total update Size" + this.mTotalContentSize + " bytes");
        }
    }

    private String makePackageStringParams() {
        StringBuffer stringBuffer = new StringBuffer("");
        if (!this.mPackageNameSet.isEmpty()) {
            String[] strArr = new String[this.mPackageNameSet.size()];
            this.mPackageNameSet.toArray(strArr);
            stringBuffer.append(strArr[0]);
            for (int i = 1; i < strArr.length; i++) {
                stringBuffer.append('@');
                stringBuffer.append(strArr[i]);
            }
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... voidArr) {
        this.mPackageString = makePackageStringParams();
        if (!TextUtils.isEmpty(this.mPackageString)) {
            handleDownloadCheckResult(this.mHelper.stubDownloadCheck(this.mPackageNameSet.size(), this.mPackageString, this.mExtuk));
        }
        Log.d(TAG, "doInBackground() ends..");
        return null;
    }
}
