package androidx.core.app;

import androidx.core.app.JobIntentService;
import com.samsung.android.fotaprovider.log.Log;

public abstract class SafeJobIntentService extends JobIntentService {
    /* access modifiers changed from: package-private */
    @Override // androidx.core.app.JobIntentService
    public JobIntentService.GenericWorkItem dequeueWork() {
        try {
            return super.dequeueWork();
        } catch (SecurityException unused) {
            Log.W("ignore JobIntentService.dequeueWork() SecurityException");
            return null;
        }
    }
}
