package com.samsung.accessory.hearablemgr.core.bixby;

import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import seccompat.android.util.Log;

public class BixbyBatteryStatus {
    private static final int EARBUD_PLACEMENT_IN_OPEN_CASE = 3;
    public static final String TAG = (Application.TAG_ + BixbyBatteryStatus.class.getSimpleName());

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d1, code lost:
        if (r1.batteryR > 0) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x019f, code lost:
        if (r1.batteryR > 0) goto L_0x01a3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0192  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x019d  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executeAction(android.os.Bundle r17, com.samsung.android.sdk.bixby2.action.ResponseCallback r18) {
        /*
        // Method dump skipped, instructions count: 472
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.bixby.BixbyBatteryStatus.executeAction(android.os.Bundle, com.samsung.android.sdk.bixby2.action.ResponseCallback):void");
    }

    private String getAdjustValue(EarBudsInfo earBudsInfo) {
        String str = TAG;
        Log.d(str, "getAdjustValue() : " + earBudsInfo.batteryI);
        return earBudsInfo.batteryI == -1 ? "" : Integer.toString(earBudsInfo.batteryI);
    }
}
