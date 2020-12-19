package com.accessorydm.ui.progress.listener;

import com.accessorydm.ui.progress.XUIProgressModel;
import com.accessorydm.ui.progress.listener.XUIProgressListener;

public abstract class XUIProgressBaseListener implements XUIProgressListener, Comparable<XUIProgressListener> {
    private final XUIProgressListener.ListenerPriority priority;

    XUIProgressBaseListener(XUIProgressListener.ListenerPriority listenerPriority) {
        this.priority = listenerPriority;
    }

    @Override // com.accessorydm.ui.progress.listener.XUIProgressListener
    public XUIProgressListener.ListenerPriority getPriority() {
        return this.priority;
    }

    @Override // com.accessorydm.ui.progress.listener.XUIProgressListener
    public boolean isOutOfRange() {
        int progressPercent = XUIProgressModel.getInstance().getProgressPercent();
        return progressPercent < 0 || progressPercent > 100;
    }

    public int compareTo(XUIProgressListener xUIProgressListener) {
        return this.priority.compareTo((Enum) xUIProgressListener.getPriority());
    }
}
