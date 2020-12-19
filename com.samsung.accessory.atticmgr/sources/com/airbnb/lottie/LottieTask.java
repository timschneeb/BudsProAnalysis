package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import com.airbnb.lottie.utils.Logger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class LottieTask<T> {
    public static Executor EXECUTOR = Executors.newCachedThreadPool();
    private final Set<LottieListener<Throwable>> failureListeners;
    private final Handler handler;
    private volatile LottieResult<T> result;
    private final Set<LottieListener<T>> successListeners;

    public LottieTask(Callable<LottieResult<T>> callable) {
        this(callable, false);
    }

    LottieTask(Callable<LottieResult<T>> callable, boolean z) {
        this.successListeners = new LinkedHashSet(1);
        this.failureListeners = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        if (z) {
            try {
                setResult(callable.call());
            } catch (Throwable th) {
                setResult(new LottieResult<>(th));
            }
        } else {
            EXECUTOR.execute(new LottieFutureTask(callable));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setResult(LottieResult<T> lottieResult) {
        if (this.result == null) {
            this.result = lottieResult;
            notifyListeners();
            return;
        }
        throw new IllegalStateException("A task may only be set once.");
    }

    public synchronized LottieTask<T> addListener(LottieListener<T> lottieListener) {
        if (!(this.result == null || this.result.getValue() == null)) {
            lottieListener.onResult(this.result.getValue());
        }
        this.successListeners.add(lottieListener);
        return this;
    }

    public synchronized LottieTask<T> removeListener(LottieListener<T> lottieListener) {
        this.successListeners.remove(lottieListener);
        return this;
    }

    public synchronized LottieTask<T> addFailureListener(LottieListener<Throwable> lottieListener) {
        if (!(this.result == null || this.result.getException() == null)) {
            lottieListener.onResult(this.result.getException());
        }
        this.failureListeners.add(lottieListener);
        return this;
    }

    public synchronized LottieTask<T> removeFailureListener(LottieListener<Throwable> lottieListener) {
        this.failureListeners.remove(lottieListener);
        return this;
    }

    private void notifyListeners() {
        this.handler.post(new Runnable() {
            /* class com.airbnb.lottie.LottieTask.AnonymousClass1 */

            public void run() {
                if (LottieTask.this.result != null) {
                    LottieResult lottieResult = LottieTask.this.result;
                    if (lottieResult.getValue() != null) {
                        LottieTask.this.notifySuccessListeners(lottieResult.getValue());
                    } else {
                        LottieTask.this.notifyFailureListeners(lottieResult.getException());
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void notifySuccessListeners(T t) {
        for (LottieListener lottieListener : new ArrayList(this.successListeners)) {
            lottieListener.onResult(t);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void notifyFailureListeners(Throwable th) {
        ArrayList<LottieListener> arrayList = new ArrayList(this.failureListeners);
        if (arrayList.isEmpty()) {
            Logger.warning("Lottie encountered an error but no failure listener was added:", th);
            return;
        }
        for (LottieListener lottieListener : arrayList) {
            lottieListener.onResult(th);
        }
    }

    private class LottieFutureTask extends FutureTask<LottieResult<T>> {
        LottieFutureTask(Callable<LottieResult<T>> callable) {
            super(callable);
        }

        /* access modifiers changed from: protected */
        public void done() {
            if (!isCancelled()) {
                try {
                    LottieTask.this.setResult((LottieResult) get());
                } catch (InterruptedException | ExecutionException e) {
                    LottieTask.this.setResult(new LottieResult(e));
                }
            }
        }
    }
}
