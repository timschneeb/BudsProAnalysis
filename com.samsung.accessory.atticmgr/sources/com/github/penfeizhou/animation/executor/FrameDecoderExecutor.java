package com.github.penfeizhou.animation.executor;

import android.os.HandlerThread;
import android.os.Looper;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FrameDecoderExecutor {
    private static int sPoolNumber = 4;
    private AtomicInteger counter;
    private ArrayList<HandlerThread> mHandlerThreadGroup;

    private FrameDecoderExecutor() {
        this.mHandlerThreadGroup = new ArrayList<>();
        this.counter = new AtomicInteger(0);
    }

    /* access modifiers changed from: package-private */
    public static class Inner {
        static final FrameDecoderExecutor sInstance = new FrameDecoderExecutor();

        Inner() {
        }
    }

    public void setPoolSize(int i) {
        sPoolNumber = i;
    }

    public static FrameDecoderExecutor getInstance() {
        return Inner.sInstance;
    }

    public Looper getLooper(int i) {
        int i2 = i % sPoolNumber;
        if (i2 >= this.mHandlerThreadGroup.size()) {
            HandlerThread handlerThread = new HandlerThread("FrameDecoderExecutor-" + i2);
            handlerThread.start();
            this.mHandlerThreadGroup.add(handlerThread);
            Looper looper = handlerThread.getLooper();
            if (looper != null) {
                return looper;
            }
            return Looper.getMainLooper();
        } else if (this.mHandlerThreadGroup.get(i2) == null) {
            return Looper.getMainLooper();
        } else {
            Looper looper2 = this.mHandlerThreadGroup.get(i2).getLooper();
            if (looper2 != null) {
                return looper2;
            }
            return Looper.getMainLooper();
        }
    }

    public int generateTaskId() {
        return this.counter.getAndIncrement();
    }
}
