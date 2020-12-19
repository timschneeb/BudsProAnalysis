package androidx.core.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobServiceEngine;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class JobIntentService extends Service {
    static final boolean DEBUG = false;
    static final String TAG = "JobIntentService";
    static final HashMap<ComponentName, h> sClassWorkEnqueuer = new HashMap<>();
    static final Object sLock = new Object();
    final ArrayList<d> mCompatQueue;
    h mCompatWorkEnqueuer;
    a mCurProcessor;
    boolean mDestroyed = false;
    boolean mInterruptIfStopped = false;
    b mJobImpl;
    boolean mStopped = false;

    /* access modifiers changed from: package-private */
    public final class a extends AsyncTask<Void, Void, Void> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(Void... voidArr) {
            while (true) {
                e dequeueWork = JobIntentService.this.dequeueWork();
                if (dequeueWork == null) {
                    return null;
                }
                JobIntentService.this.onHandleWork(dequeueWork.getIntent());
                dequeueWork.a();
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onCancelled(Void r1) {
            JobIntentService.this.processorFinished();
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public void onPostExecute(Void r1) {
            JobIntentService.this.processorFinished();
        }
    }

    /* access modifiers changed from: package-private */
    public interface b {
        IBinder a();

        e b();
    }

    /* access modifiers changed from: package-private */
    public static final class c extends h {

        /* renamed from: d  reason: collision with root package name */
        private final Context f554d;
        private final PowerManager.WakeLock e;
        private final PowerManager.WakeLock f;
        boolean g;
        boolean h;

        c(Context context, ComponentName componentName) {
            super(context, componentName);
            this.f554d = context.getApplicationContext();
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            this.e = powerManager.newWakeLock(1, componentName.getClassName() + ":launch");
            this.e.setReferenceCounted(false);
            this.f = powerManager.newWakeLock(1, componentName.getClassName() + ":run");
            this.f.setReferenceCounted(false);
        }

        @Override // androidx.core.app.JobIntentService.h
        public void a() {
            synchronized (this) {
                if (this.h) {
                    if (this.g) {
                        this.e.acquire(60000);
                    }
                    this.h = false;
                    this.f.release();
                }
            }
        }

        /* access modifiers changed from: package-private */
        @Override // androidx.core.app.JobIntentService.h
        public void a(Intent intent) {
            Intent intent2 = new Intent(intent);
            intent2.setComponent(this.f564a);
            if (this.f554d.startService(intent2) != null) {
                synchronized (this) {
                    if (!this.g) {
                        this.g = true;
                        if (!this.h) {
                            this.e.acquire(60000);
                        }
                    }
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.h
        public void b() {
            synchronized (this) {
                if (!this.h) {
                    this.h = true;
                    this.f.acquire(600000);
                    this.e.release();
                }
            }
        }

        @Override // androidx.core.app.JobIntentService.h
        public void c() {
            synchronized (this) {
                this.g = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final class d implements e {

        /* renamed from: a  reason: collision with root package name */
        final Intent f555a;

        /* renamed from: b  reason: collision with root package name */
        final int f556b;

        d(Intent intent, int i) {
            this.f555a = intent;
            this.f556b = i;
        }

        @Override // androidx.core.app.JobIntentService.e
        public void a() {
            JobIntentService.this.stopSelf(this.f556b);
        }

        @Override // androidx.core.app.JobIntentService.e
        public Intent getIntent() {
            return this.f555a;
        }
    }

    /* access modifiers changed from: package-private */
    public interface e {
        void a();

        Intent getIntent();
    }

    static final class f extends JobServiceEngine implements b {

        /* renamed from: a  reason: collision with root package name */
        final JobIntentService f558a;

        /* renamed from: b  reason: collision with root package name */
        final Object f559b = new Object();

        /* renamed from: c  reason: collision with root package name */
        JobParameters f560c;

        final class a implements e {

            /* renamed from: a  reason: collision with root package name */
            final JobWorkItem f561a;

            a(JobWorkItem jobWorkItem) {
                this.f561a = jobWorkItem;
            }

            @Override // androidx.core.app.JobIntentService.e
            public void a() {
                synchronized (f.this.f559b) {
                    if (f.this.f560c != null) {
                        f.this.f560c.completeWork(this.f561a);
                    }
                }
            }

            @Override // androidx.core.app.JobIntentService.e
            public Intent getIntent() {
                return this.f561a.getIntent();
            }
        }

        f(JobIntentService jobIntentService) {
            super(jobIntentService);
            this.f558a = jobIntentService;
        }

        @Override // androidx.core.app.JobIntentService.b
        public IBinder a() {
            return getBinder();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
            r1.getIntent().setExtrasClassLoader(r3.f558a.getClassLoader());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0025, code lost:
            return new androidx.core.app.JobIntentService.f.a(r3, r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
            return null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
            if (r1 == null) goto L_0x0026;
         */
        @Override // androidx.core.app.JobIntentService.b
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public androidx.core.app.JobIntentService.e b() {
            /*
                r3 = this;
                java.lang.Object r0 = r3.f559b
                monitor-enter(r0)
                android.app.job.JobParameters r1 = r3.f560c     // Catch:{ all -> 0x0027 }
                r2 = 0
                if (r1 != 0) goto L_0x000a
                monitor-exit(r0)     // Catch:{ all -> 0x0027 }
                return r2
            L_0x000a:
                android.app.job.JobParameters r1 = r3.f560c     // Catch:{ all -> 0x0027 }
                android.app.job.JobWorkItem r1 = r1.dequeueWork()     // Catch:{ all -> 0x0027 }
                monitor-exit(r0)     // Catch:{ all -> 0x0027 }
                if (r1 == 0) goto L_0x0026
                android.content.Intent r0 = r1.getIntent()
                androidx.core.app.JobIntentService r2 = r3.f558a
                java.lang.ClassLoader r2 = r2.getClassLoader()
                r0.setExtrasClassLoader(r2)
                androidx.core.app.JobIntentService$f$a r0 = new androidx.core.app.JobIntentService$f$a
                r0.<init>(r1)
                return r0
            L_0x0026:
                return r2
            L_0x0027:
                r1 = move-exception
                monitor-exit(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.JobIntentService.f.b():androidx.core.app.JobIntentService$e");
        }

        public boolean onStartJob(JobParameters jobParameters) {
            this.f560c = jobParameters;
            this.f558a.ensureProcessorRunningLocked(false);
            return true;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            boolean doStopCurrentWork = this.f558a.doStopCurrentWork();
            synchronized (this.f559b) {
                this.f560c = null;
            }
            return doStopCurrentWork;
        }
    }

    /* access modifiers changed from: package-private */
    public static final class g extends h {

        /* renamed from: d  reason: collision with root package name */
        private final JobInfo f563d;
        private final JobScheduler e;

        g(Context context, ComponentName componentName, int i) {
            super(context, componentName);
            a(i);
            this.f563d = new JobInfo.Builder(i, this.f564a).setOverrideDeadline(0).build();
            this.e = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler");
        }

        /* access modifiers changed from: package-private */
        @Override // androidx.core.app.JobIntentService.h
        public void a(Intent intent) {
            this.e.enqueue(this.f563d, new JobWorkItem(intent));
        }
    }

    /* access modifiers changed from: package-private */
    public static abstract class h {

        /* renamed from: a  reason: collision with root package name */
        final ComponentName f564a;

        /* renamed from: b  reason: collision with root package name */
        boolean f565b;

        /* renamed from: c  reason: collision with root package name */
        int f566c;

        h(Context context, ComponentName componentName) {
            this.f564a = componentName;
        }

        public void a() {
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            if (!this.f565b) {
                this.f565b = true;
                this.f566c = i;
            } else if (this.f566c != i) {
                throw new IllegalArgumentException("Given job ID " + i + " is different than previous " + this.f566c);
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void a(Intent intent);

        public void b() {
        }

        public void c() {
        }
    }

    public JobIntentService() {
        this.mCompatQueue = Build.VERSION.SDK_INT >= 26 ? null : new ArrayList<>();
    }

    public static void enqueueWork(Context context, ComponentName componentName, int i, Intent intent) {
        if (intent != null) {
            synchronized (sLock) {
                h workEnqueuer = getWorkEnqueuer(context, componentName, true, i);
                workEnqueuer.a(i);
                workEnqueuer.a(intent);
            }
            return;
        }
        throw new IllegalArgumentException("work must not be null");
    }

    public static void enqueueWork(Context context, Class cls, int i, Intent intent) {
        enqueueWork(context, new ComponentName(context, cls), i, intent);
    }

    static h getWorkEnqueuer(Context context, ComponentName componentName, boolean z, int i) {
        h hVar;
        h hVar2 = sClassWorkEnqueuer.get(componentName);
        if (hVar2 != null) {
            return hVar2;
        }
        if (Build.VERSION.SDK_INT < 26) {
            hVar = new c(context, componentName);
        } else if (z) {
            hVar = new g(context, componentName, i);
        } else {
            throw new IllegalArgumentException("Can't be here without a job id");
        }
        sClassWorkEnqueuer.put(componentName, hVar);
        return hVar;
    }

    /* access modifiers changed from: package-private */
    public e dequeueWork() {
        b bVar = this.mJobImpl;
        if (bVar != null) {
            return bVar.b();
        }
        synchronized (this.mCompatQueue) {
            if (this.mCompatQueue.size() <= 0) {
                return null;
            }
            return this.mCompatQueue.remove(0);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean doStopCurrentWork() {
        a aVar = this.mCurProcessor;
        if (aVar != null) {
            aVar.cancel(this.mInterruptIfStopped);
        }
        this.mStopped = true;
        return onStopCurrentWork();
    }

    /* access modifiers changed from: package-private */
    public void ensureProcessorRunningLocked(boolean z) {
        if (this.mCurProcessor == null) {
            this.mCurProcessor = new a();
            h hVar = this.mCompatWorkEnqueuer;
            if (hVar != null && z) {
                hVar.b();
            }
            this.mCurProcessor.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    public boolean isStopped() {
        return this.mStopped;
    }

    public IBinder onBind(Intent intent) {
        b bVar = this.mJobImpl;
        if (bVar != null) {
            return bVar.a();
        }
        return null;
    }

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            this.mJobImpl = new f(this);
            this.mCompatWorkEnqueuer = null;
            return;
        }
        this.mJobImpl = null;
        this.mCompatWorkEnqueuer = getWorkEnqueuer(this, new ComponentName(this, getClass()), false, 0);
    }

    public void onDestroy() {
        super.onDestroy();
        ArrayList<d> arrayList = this.mCompatQueue;
        if (arrayList != null) {
            synchronized (arrayList) {
                this.mDestroyed = true;
                this.mCompatWorkEnqueuer.a();
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onHandleWork(Intent intent);

    public int onStartCommand(Intent intent, int i, int i2) {
        if (this.mCompatQueue == null) {
            return 2;
        }
        this.mCompatWorkEnqueuer.c();
        synchronized (this.mCompatQueue) {
            ArrayList<d> arrayList = this.mCompatQueue;
            if (intent == null) {
                intent = new Intent();
            }
            arrayList.add(new d(intent, i2));
            ensureProcessorRunningLocked(true);
        }
        return 3;
    }

    public boolean onStopCurrentWork() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void processorFinished() {
        ArrayList<d> arrayList = this.mCompatQueue;
        if (arrayList != null) {
            synchronized (arrayList) {
                this.mCurProcessor = null;
                if (this.mCompatQueue != null && this.mCompatQueue.size() > 0) {
                    ensureProcessorRunningLocked(false);
                } else if (!this.mDestroyed) {
                    this.mCompatWorkEnqueuer.a();
                }
            }
        }
    }

    public void setInterruptIfStopped(boolean z) {
        this.mInterruptIfStopped = z;
    }
}
