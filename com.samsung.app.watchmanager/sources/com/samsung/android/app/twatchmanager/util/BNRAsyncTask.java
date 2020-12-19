package com.samsung.android.app.twatchmanager.util;

import android.os.AsyncTask;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.BNRHelper;
import java.util.ArrayList;
import java.util.List;

public class BNRAsyncTask extends AsyncTask<Void, Void, Void> {
    private String TAG = ("tUHM:[Conn]" + BNRAsyncTask.class.getSimpleName());
    private List<String> mApplicationList;
    private IBNRTaskCallback mCallback;

    public interface IBNRTaskCallback {
        void onFinished();
    }

    public BNRAsyncTask(List<String> list, IBNRTaskCallback iBNRTaskCallback) {
        this.mApplicationList = list;
        this.mCallback = iBNRTaskCallback;
    }

    /* access modifiers changed from: protected */
    public Void doInBackground(Void... voidArr) {
        new BNRHelper().requestBackup(new ArrayList<>(this.mApplicationList));
        return null;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Void r2) {
        Log.d(this.TAG, "onPostExecute");
        IBNRTaskCallback iBNRTaskCallback = this.mCallback;
        if (iBNRTaskCallback != null) {
            iBNRTaskCallback.onFinished();
            this.mCallback = null;
        }
    }
}
