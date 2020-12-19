package com.accessorydm.ui.progress;

import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel;
import com.accessorydm.ui.progress.listener.XUIProgressListener;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.sec.android.fotaprovider.R;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

public class XUIProgressModel extends XUIBaseFullscreenModel {
    private static final XUIProgressModel instance = new XUIProgressModel();
    private boolean mIncreasePercentage = false;
    private int mProgressMode = 0;
    private int mProgressPercent = 0;
    private long mProgressedDeltaSize = 0;
    private long mTotalDeltaSize = 0;
    private final TreeSet<XUIProgressListener> progressListeners = new TreeSet<>();

    public static XUIProgressModel getInstance() {
        return instance;
    }

    public synchronized void addProgressListener(XUIProgressListener xUIProgressListener) {
        try {
            if (this.progressListeners.add(xUIProgressListener)) {
                Log.D("listener is added: " + xUIProgressListener.getClass().getSimpleName());
            } else {
                Log.D("listener already exists: " + xUIProgressListener.getClass().getSimpleName());
            }
        } catch (Exception e) {
            Log.W(e.toString());
        }
        return;
    }

    /* access modifiers changed from: package-private */
    public synchronized void removeProgressListener(XUIProgressListener xUIProgressListener) {
        try {
            if (this.progressListeners.remove(xUIProgressListener)) {
                Log.D("listener is removed: " + xUIProgressListener.getClass().getSimpleName());
            } else {
                Log.D("listener does not exist: " + xUIProgressListener.getClass().getSimpleName());
            }
        } catch (Exception e) {
            Log.W(e.toString());
        }
        return;
    }

    public void setProgressMode(int i) {
        this.mProgressMode = i;
    }

    public int getProgressMode() {
        return this.mProgressMode;
    }

    public int getProgressPercent() {
        return this.mProgressPercent;
    }

    public void setTotalDeltaSize(long j) {
        this.mTotalDeltaSize = j;
        Log.I("Total delta size = " + this.mTotalDeltaSize);
    }

    public boolean isIncreasePercentage() {
        return this.mIncreasePercentage;
    }

    public void setIncreasePercentage(boolean z) {
        this.mIncreasePercentage = z;
    }

    public void initializeProgress() {
        if (this.mProgressMode == 1) {
            XFOTADl.xfotaSetDeltaDownState(0);
        }
        initializeProgressInfo();
    }

    /* access modifiers changed from: package-private */
    public void initializeProgressInfo() {
        this.mProgressPercent = 0;
        this.mProgressedDeltaSize = 0;
        this.mTotalDeltaSize = 0;
    }

    public void updateProgressInfoForDownload(long j) {
        this.mProgressMode = 1;
        if (j > 0 && this.mTotalDeltaSize > 0) {
            updateProgressInfo(calculateCurrentPercent(j), j);
        }
    }

    public void updateProgressInfoForCopy(int i) {
        this.mProgressMode = 2;
        if (i > 0 && this.mTotalDeltaSize > 0) {
            updateProgressInfo(i, calculateCurrentDeltaSize(i));
        }
    }

    private int calculateCurrentPercent(long j) {
        return (int) ((j * 100) / this.mTotalDeltaSize);
    }

    private long calculateCurrentDeltaSize(int i) {
        return (this.mTotalDeltaSize * ((long) i)) / 100;
    }

    private synchronized void updateProgressInfo(int i, long j) {
        if (this.mProgressPercent != i) {
            Log.I("[percent] " + i + " / [size] " + j);
            this.mIncreasePercentage = true;
            this.mProgressedDeltaSize = j;
            this.mProgressPercent = i;
            Iterator<XUIProgressListener> it = this.progressListeners.iterator();
            while (it.hasNext()) {
                XUIProgressListener next = it.next();
                if (next != null) {
                    next.onProgressInfoUpdated();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public String getProgressTitle() {
        if (this.mProgressMode != 2) {
            return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_DOWNLOAD_PROGRESS);
        }
        return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_COPY_PROGRESS);
    }

    public String getProgressSizeText() {
        try {
            String string = FotaProviderInitializer.getContext().getString(R.string.STR_COMMON_MEGA_BYTE);
            String format = String.format(Locale.getDefault(), "%.2f", Double.valueOf(((double) this.mProgressedDeltaSize) / 1048576.0d));
            String format2 = String.format(Locale.getDefault(), "%.2f", Double.valueOf(((double) this.mTotalDeltaSize) / 1048576.0d));
            return String.format(Locale.getDefault(), FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_PROGRESS_MB_PER_MB), format, string, format2, string);
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }
}
