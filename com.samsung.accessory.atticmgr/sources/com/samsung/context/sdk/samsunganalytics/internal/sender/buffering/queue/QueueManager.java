package com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue;

import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueManager {
    private static final int DEFAULT_QUEUE_SIZE = 25;
    protected LinkedBlockingQueue<SimpleLog> logQueue = new LinkedBlockingQueue<>(25);

    public void insert(SimpleLog simpleLog) {
        if (!this.logQueue.offer(simpleLog)) {
            Debug.LogD("QueueManager", "queue size over. remove oldest log");
            this.logQueue.poll();
            this.logQueue.offer(simpleLog);
        }
    }

    public Queue<SimpleLog> getAll() {
        return this.logQueue;
    }

    public long getDataSize() {
        return (long) this.logQueue.size();
    }

    public boolean isEmpty() {
        return this.logQueue.isEmpty();
    }
}
