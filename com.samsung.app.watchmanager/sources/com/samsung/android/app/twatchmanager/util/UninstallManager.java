package com.samsung.android.app.twatchmanager.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import java.util.List;

public class UninstallManager {
    private String TAG = ("tUHM:[Conn]" + UninstallManager.class.getSimpleName());
    private Handler mHandler = new Handler() {
        /* class com.samsung.android.app.twatchmanager.util.UninstallManager.AnonymousClass1 */

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 203) {
                Log.i(UninstallManager.this.TAG, "InstallationUtils.UNINSTALLATION_PROGRESS");
                Bundle data = message.getData();
                int i2 = data.getInt(InstallationUtils.MSG_INSTALLED_PACKAGE_INDEX);
                String string = data.getString("packageName");
                String str = UninstallManager.this.TAG;
                Log.i(str, "InstallationUtils.UNINSTALLATION_PROGRESS, index [" + i2 + "], packageName [" + string + "]");
            } else if (i == 204) {
                Log.i(UninstallManager.this.TAG, "InstallationUtils.UNINSTALLATION_COMPLETE");
                UninstallManager.this.onFinishUninstallation();
            }
        }
    };
    private UninstallationListener mListener;
    private List<String> mPackages;

    public interface UninstallationListener {
        void onFinished();
    }

    public UninstallManager(List<String> list, UninstallationListener uninstallationListener) {
        this.mPackages = list;
        this.mListener = uninstallationListener;
    }

    private void destroy() {
        Log.d(this.TAG, "destroy");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.mHandler = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onFinishUninstallation() {
        Log.d(this.TAG, "onFinishUninstallation");
        UninstallationListener uninstallationListener = this.mListener;
        if (uninstallationListener != null) {
            uninstallationListener.onFinished();
        }
        destroy();
    }

    private void startUninstallation() {
        Log.d(this.TAG, "startUninstallation");
        new UninstallAsyncTask(TWatchManagerApplication.getAppContext(), this.mPackages, this.mHandler).execute(new String[0]);
    }

    public void start() {
        Log.d(this.TAG, "start");
        startUninstallation();
    }
}
