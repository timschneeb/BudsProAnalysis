package androidx.work;

public interface RunnableScheduler {
    void cancel(Runnable runnable);

    void scheduleWithDelay(long j, Runnable runnable);
}
