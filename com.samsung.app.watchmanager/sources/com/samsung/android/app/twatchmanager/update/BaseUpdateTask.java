package com.samsung.android.app.twatchmanager.update;

import android.os.AsyncTask;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.update.StubAPIHelper;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import java.util.HashMap;
import java.util.Set;

public abstract class BaseUpdateTask extends AsyncTask<Void, Void, Void> {
    public static final String TAG = ("tUHM:" + BaseUpdateTask.class.getSimpleName());
    protected IUpdateTaskCallback mCallback;
    protected StubAPIHelper mHelper;
    protected Set<String> mPackageNameSet;
    protected HashMap<String, StubAPIHelper.XMLResult> mResultMap = new HashMap<>();
    protected int mTotalContentSize;

    public interface IUpdateTaskCallback {
        void onResult(HashMap<String, StubAPIHelper.XMLResult> hashMap, int i);
    }

    public BaseUpdateTask(Set<String> set, IUpdateTaskCallback iUpdateTaskCallback) {
        this.mPackageNameSet = set;
        this.mHelper = new StubAPIHelper();
        this.mCallback = iUpdateTaskCallback;
    }

    public boolean initialCheck() {
        boolean isDataNetworkConnected = HostManagerUtilsNetwork.isDataNetworkConnected(TWatchManagerApplication.getAppContext());
        Set<String> set = this.mPackageNameSet;
        boolean z = set != null && !set.isEmpty();
        String str = TAG;
        Log.d(str, "initialCheck() starts.. dataNetworkAvailable : " + isDataNetworkConnected + " packageSetValid : " + z);
        return isDataNetworkConnected && z;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Void r3) {
        this.mCallback.onResult(this.mResultMap, this.mTotalContentSize);
    }
}
