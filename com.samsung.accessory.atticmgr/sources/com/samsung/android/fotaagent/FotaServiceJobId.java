package com.samsung.android.fotaagent;

import com.samsung.android.fotaprovider.FotaProviderInitializer;

public enum FotaServiceJobId {
    INSTANCE;
    
    public int DM_SERVICE_JOB_ID = (getPackageNameHashCode() + 4);
    public int REGISTER_JOB_ID = (getPackageNameHashCode() + 2);
    public int UPDATE_JOB_ID = (getPackageNameHashCode() + 1);

    private FotaServiceJobId() {
    }

    private int getPackageNameHashCode() {
        return FotaProviderInitializer.getContext().getPackageName().hashCode();
    }
}
