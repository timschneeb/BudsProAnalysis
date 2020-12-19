package com.samsung.accessory.hearablemgr.common.soagent;

import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import seccompat.android.util.Log;

public class SOAgentService {
    private static final String TAG = (Application.TAG_ + SOAgentService.class.getSimpleName());
    private static SOAgentService mSOAgentService = null;
    private AccessoryInfo mAccInfo = null;

    public static synchronized SOAgentService getInstance() {
        SOAgentService sOAgentService;
        synchronized (SOAgentService.class) {
            if (mSOAgentService == null) {
                mSOAgentService = new SOAgentService();
            }
            sOAgentService = mSOAgentService;
        }
        return sOAgentService;
    }

    public boolean sendAccessoryInfo(String str, String str2, String str3) {
        if (this.mAccInfo == null) {
            this.mAccInfo = new AccessoryInfo();
        }
        this.mAccInfo.setType(AccessoryInfo.TYPE_ICON_X);
        this.mAccInfo.setModelName(str);
        this.mAccInfo.setSerialNumber(str2);
        this.mAccInfo.setBTAddress(str3);
        this.mAccInfo.setHDMCC(Util.getMcc());
        this.mAccInfo.setHDMNC(Util.getMnc());
        AccessoryClient accessoryClient = new AccessoryClient(new AccessoryObject(this.mAccInfo), this.mAccInfo.getType());
        int execute = accessoryClient.execute();
        if (execute == 3) {
            Log.d(TAG, "Retry");
            execute = accessoryClient.execute();
        }
        if (execute != 2) {
            return true;
        }
        Log.d(TAG, "Fail to send");
        return false;
    }
}
