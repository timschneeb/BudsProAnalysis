package com.samsung.android.app.twatchmanager.model;

import java.util.ArrayList;
import java.util.List;

public class CommonInfo {
    protected List<CommonPackageItem> mCommonPackageItemList;
    protected List<String> mDiscoveryKeywords;
    protected List<String> mSkipDevices;

    public void addCommonPackageInfo(CommonPackageItem commonPackageItem) {
        if (this.mCommonPackageItemList == null) {
            this.mCommonPackageItemList = new ArrayList();
        }
        this.mCommonPackageItemList.add(commonPackageItem);
    }

    public void addDiscoveryKeyword(String str) {
        if (this.mDiscoveryKeywords == null) {
            this.mDiscoveryKeywords = new ArrayList();
        }
        this.mDiscoveryKeywords.add(str);
    }

    public void addSkipDevice(String str) {
        if (this.mSkipDevices == null) {
            this.mSkipDevices = new ArrayList();
        }
        this.mSkipDevices.add(str);
    }

    public List<CommonPackageItem> getCommonPackageItemList() {
        return this.mCommonPackageItemList;
    }

    public List<String> getDiscoveryKeywords() {
        return this.mDiscoveryKeywords;
    }

    public List<String> getSkipDevices() {
        return this.mSkipDevices;
    }
}
