package com.samsung.android.sdk.bixby2.util;

import android.os.Bundle;
import com.samsung.android.sdk.bixby2.LogUtil;

public class BixbyUtils {
    private static final String TAG = "BixbyUtils";

    public static BixbyContextInfo getBixbyContextInfo(Bundle bundle) {
        LogUtil.d(TAG, "getBixbyContextInfo()");
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = bundle.getBundle("contextInfo");
        if (bundle2 == null || bundle2.isEmpty()) {
            return new BixbyContextInfo();
        }
        return new BixbyContextInfo(bundle2);
    }
}
