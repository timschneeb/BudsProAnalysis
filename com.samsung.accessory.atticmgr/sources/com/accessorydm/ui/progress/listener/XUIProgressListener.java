package com.accessorydm.ui.progress.listener;

public interface XUIProgressListener {

    public enum ListenerPriority {
        Notification,
        View,
        RangeCheckIsAlwaysLast
    }

    ListenerPriority getPriority();

    boolean isOutOfRange();

    void onProgressInfoUpdated();
}
