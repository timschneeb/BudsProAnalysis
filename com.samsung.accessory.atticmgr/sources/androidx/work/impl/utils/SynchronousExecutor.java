package androidx.work.impl.utils;

import java.util.concurrent.Executor;

public class SynchronousExecutor implements Executor {
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
