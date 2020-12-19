package androidx.work.impl.utils.futures;

import java.util.concurrent.Executor;

/* access modifiers changed from: package-private */
public enum DirectExecutor implements Executor {
    INSTANCE;

    public String toString() {
        return "DirectExecutor";
    }

    public void execute(Runnable runnable) {
        runnable.run();
    }
}
