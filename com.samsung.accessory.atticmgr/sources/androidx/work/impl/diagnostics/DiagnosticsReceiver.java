package androidx.work.impl.diagnostics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.work.Logger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.impl.workers.DiagnosticsWorker;

public class DiagnosticsReceiver extends BroadcastReceiver {
    private static final String TAG = Logger.tagWithPrefix("DiagnosticsRcvr");

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Logger.get().debug(TAG, "Requesting diagnostics", new Throwable[0]);
            try {
                WorkManager.getInstance(context).enqueue(OneTimeWorkRequest.from(DiagnosticsWorker.class));
            } catch (IllegalStateException e) {
                Logger.get().error(TAG, "WorkManager is not initialized", e);
            }
        }
    }
}
