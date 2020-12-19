package com.samsung.android.sdk.cover;

import android.content.Context;
import com.samsung.android.sdk.SsdkInterface;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.SsdkVendorCheck;

public final class Scover implements SsdkInterface {
    private Context mContext;

    @Override // com.samsung.android.sdk.SsdkInterface
    public int getVersionCode() {
        return 16842752;
    }

    @Override // com.samsung.android.sdk.SsdkInterface
    public String getVersionName() {
        return String.format("%d.%d.%d", 1, 1, 0);
    }

    @Override // com.samsung.android.sdk.SsdkInterface
    public void initialize(Context context) throws SsdkUnsupportedException, IllegalArgumentException {
        this.mContext = context;
        if (context == null) {
            throw new IllegalArgumentException("context may not be null!!");
        } else if (!SsdkVendorCheck.isSamsungDevice()) {
            throw new SsdkUnsupportedException("This is not Samsung device!!!", 0);
        } else if (!new ScoverManager(this.mContext).isSupportCover()) {
            throw new SsdkUnsupportedException("This device is not supported Scover!!!", 1);
        }
    }

    @Override // com.samsung.android.sdk.SsdkInterface
    public boolean isFeatureEnabled(int i) {
        return new ScoverManager(this.mContext).isSupportTypeOfCover(i);
    }
}
