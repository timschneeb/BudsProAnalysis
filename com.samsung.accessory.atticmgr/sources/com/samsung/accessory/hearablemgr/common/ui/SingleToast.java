package com.samsung.accessory.hearablemgr.common.ui;

import android.content.Context;
import android.widget.Toast;

public class SingleToast {
    private static Toast mToast;

    public static void show(Context context, String str, int i) {
        Toast toast = mToast;
        if (toast != null) {
            toast.cancel();
        }
        mToast = Toast.makeText(context, str, i);
        mToast.show();
    }
}
