package com.samsung.accessory.hearablemgr.common.ui;

import android.os.Handler;
import android.view.View;
import seccompat.android.util.Log;

public abstract class OnSingleClickListener implements View.OnClickListener {
    private static final long BLOCK_TIME = 500;
    private static final String TAG = "Attic_OnSingleClickListener";
    private static final Runnable UNBLOCK_RUNNABLE = new Runnable() {
        /* class com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener.AnonymousClass1 */

        public void run() {
            Boolean unused = OnSingleClickListener.sBlocked = false;
        }
    };
    private static Boolean sBlocked = false;
    private static Handler sHandler = new Handler();

    public abstract void onSingleClick(View view);

    public void onClick(View view) {
        if (sBlocked.booleanValue()) {
            Log.w(TAG, "Blocked");
            return;
        }
        sBlocked = true;
        sHandler.postDelayed(UNBLOCK_RUNNABLE, BLOCK_TIME);
        onSingleClick(view);
    }
}
