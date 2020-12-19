package com.samsung.android.app.twatchmanager.update;

import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.model.UHMPackageInfo;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UpdatePackageSet {
    public static final String TAG = ("tUHM:[Update]" + UpdatePackageSet.class.getSimpleName());
    private GearInfo mInfo;
    private Set<String> mTotalPackageSet;

    public UpdatePackageSet(GearInfo gearInfo) {
        this.mInfo = gearInfo;
        init();
    }

    private void init() {
        ArrayList<UHMPackageInfo> additionalPackageList;
        this.mTotalPackageSet = new HashSet();
        this.mTotalPackageSet.add(this.mInfo.getContainerPackageName());
        if (!HostManagerUtils.isSamsungDevice() && (additionalPackageList = GearRulesManager.getInstance().getAdditionalPackageList(this.mInfo.deviceName)) != null) {
            Iterator<UHMPackageInfo> it = additionalPackageList.iterator();
            while (it.hasNext()) {
                UHMPackageInfo next = it.next();
                String str = TAG;
                Log.d(str, "getUpdateCheckablePackageSet() add provider package for nonsamsung = " + next.packageName);
                this.mTotalPackageSet.add(next.packageName);
            }
        }
    }

    public Set<String> get() {
        return this.mTotalPackageSet;
    }
}
