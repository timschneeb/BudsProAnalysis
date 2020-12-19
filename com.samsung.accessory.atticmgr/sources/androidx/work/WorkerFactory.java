package androidx.work;

import android.content.Context;

public abstract class WorkerFactory {
    private static final String TAG = Logger.tagWithPrefix("WorkerFactory");

    public abstract ListenableWorker createWorker(Context context, String str, WorkerParameters workerParameters);

    public final ListenableWorker createWorkerWithDefaultFallback(Context context, String str, WorkerParameters workerParameters) {
        ListenableWorker listenableWorker;
        ListenableWorker createWorker = createWorker(context, str, workerParameters);
        if (createWorker == null) {
            Class<? extends U> cls = null;
            try {
                cls = Class.forName(str).asSubclass(ListenableWorker.class);
            } catch (Throwable th) {
                Logger logger = Logger.get();
                String str2 = TAG;
                logger.error(str2, "Invalid class: " + str, th);
            }
            if (cls != null) {
                try {
                    listenableWorker = (ListenableWorker) cls.getDeclaredConstructor(Context.class, WorkerParameters.class).newInstance(context, workerParameters);
                } catch (Throwable th2) {
                    Logger logger2 = Logger.get();
                    String str3 = TAG;
                    logger2.error(str3, "Could not instantiate " + str, th2);
                }
                if (listenableWorker != null || !listenableWorker.isUsed()) {
                    return listenableWorker;
                }
                throw new IllegalStateException(String.format("WorkerFactory (%s) returned an instance of a ListenableWorker (%s) which has already been invoked. createWorker() must always return a new instance of a ListenableWorker.", getClass().getName(), str));
            }
        }
        listenableWorker = createWorker;
        if (listenableWorker != null) {
        }
        return listenableWorker;
    }

    public static WorkerFactory getDefaultWorkerFactory() {
        return new WorkerFactory() {
            /* class androidx.work.WorkerFactory.AnonymousClass1 */

            @Override // androidx.work.WorkerFactory
            public ListenableWorker createWorker(Context context, String str, WorkerParameters workerParameters) {
                return null;
            }
        };
    }
}
