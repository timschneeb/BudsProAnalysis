package com.samsung.accessory.fotaprovider.controller;

import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;

public abstract class AccessoryUtil {
    public abstract int getMinimumBatteryLevel();

    public abstract long getNeededFreeSpaceForCopy(long j);

    public abstract long getNeededFreeSpaceForDownload(long j);

    public abstract long getNeededFreeSpaceForInstall(long j);

    public abstract boolean isAvailableBatteryLevel();

    public abstract boolean isAvailableFreeSpaceForCopy(long j);

    public abstract boolean isAvailableFreeSpaceForDownload(long j);

    public abstract boolean isAvailableFreeSpaceForInstall(long j);

    public static boolean isDifferentDevice(ConsumerInfo consumerInfo) {
        AccessoryInfoAdapter accessoryInfoAdapter = new AccessoryInfoAdapter();
        if (!accessoryInfoAdapter.getDeviceId().equals(consumerInfo.getDeviceId()) || !accessoryInfoAdapter.getModelNumber().equals(consumerInfo.getModelNumber()) || !accessoryInfoAdapter.getSalesCode().equals(consumerInfo.getSalesCode())) {
            Log.W("received different device info");
            Log.H("device id:" + GeneralUtil.maskDeviceId(consumerInfo.getDeviceId()));
            Log.H("model name:" + consumerInfo.getModelNumber());
            Log.H("sales code:" + consumerInfo.getSalesCode());
            return true;
        }
        Log.I("received correct device info");
        return false;
    }
}
