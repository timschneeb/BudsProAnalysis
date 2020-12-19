package androidx.work.impl.foreground;

import androidx.work.ForegroundInfo;

public interface ForegroundProcessor {
    void startForeground(String str, ForegroundInfo foregroundInfo);

    void stopForeground(String str);
}
