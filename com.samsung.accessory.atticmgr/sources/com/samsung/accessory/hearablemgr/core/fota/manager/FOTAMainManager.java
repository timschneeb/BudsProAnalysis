package com.samsung.accessory.hearablemgr.core.fota.manager;

import android.content.Intent;
import com.samsung.accessory.fotaprovider.controller.RequestError;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaUtil;
import seccompat.android.util.Log;

public class FOTAMainManager {
    private static final String TAG = "NeoBean_FOTAMainManager";
    private static FOTAMainManager mFOTAMainManagerInstance;

    public static synchronized FOTAMainManager getInstance() {
        FOTAMainManager fOTAMainManager;
        synchronized (FOTAMainManager.class) {
            if (mFOTAMainManagerInstance == null) {
                mFOTAMainManagerInstance = new FOTAMainManager();
                Log.d(TAG, "mFOTAMainManagerInstance is null");
            }
            fOTAMainManager = mFOTAMainManagerInstance;
        }
        return fOTAMainManager;
    }

    public void updateFOTACopyProcessResult(String str, int i) {
        if (((str.hashCode() == -117388702 && str.equals(FotaUtil.ACTION_FOTA_PROGRESS_COPY_RESULT)) ? (char) 0 : 65535) == 0) {
            Log.d(TAG, "sendBroadcast - ACTION_FOTA_PROGRESS_COPY_RESULT: " + i);
            if (i == 1) {
                sendBroadcastForFOTAResult(str, FotaUtil.FOTA_RESULT, 1);
            } else if (i == 2) {
                Log.d(TAG, "ACTION_FOTA_PROGRESS_COPY_RESULT : unCoupled");
                sendBroadcastForFOTAResult(str, FotaUtil.FOTA_RESULT, 3);
                try {
                    FotaRequestController.mRequestResultCallback.onFailure(RequestError.ERROR_FILE_TRANSFER_ACCESSORY_UNCOUPLED);
                    Log.d(TAG, "FotaRequestController.isInProgress false");
                    FotaRequestController.isInProgress = false;
                } catch (NullPointerException e) {
                    Log.d(TAG, e.toString());
                }
            } else if (i == 3) {
                Log.d(TAG, "ACTION_FOTA_PROGRESS_COPY_RESULT : fail");
                sendBroadcastForFOTAResult(str, FotaUtil.FOTA_RESULT, 3);
                try {
                    FotaRequestController.mRequestResultCallback.onFailure(RequestError.ERROR_FILE_TRANSFER);
                    Log.d(TAG, "FotaRequestController.isInProgress false");
                    FotaRequestController.isInProgress = false;
                } catch (NullPointerException e2) {
                    Log.d(TAG, e2.toString());
                }
            }
        }
    }

    private void sendBroadcastForFOTAResult(String str, String str2, int i) {
        Intent intent = new Intent(str);
        intent.setPackage(Application.getContext().getPackageName());
        if (!"".equals(str2)) {
            intent.putExtra(str2, i);
        }
        Application.getContext().sendBroadcast(intent);
    }
}
