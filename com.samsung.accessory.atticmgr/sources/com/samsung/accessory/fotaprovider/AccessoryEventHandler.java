package com.samsung.accessory.fotaprovider;

import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.samsung.accessory.fotaprovider.controller.AccessoryUtil;
import com.samsung.accessory.fotaprovider.controller.ConsumerInfo;

public final class AccessoryEventHandler {
    private static final AccessoryEventHandler instance = new AccessoryEventHandler();

    private AccessoryEventHandler() {
    }

    public static AccessoryEventHandler getInstance() {
        return instance;
    }

    public void reportUpdateResult(ConsumerInfo consumerInfo, boolean z) {
        if (!AccessoryUtil.isDifferentDevice(consumerInfo)) {
            AccessoryController.getInstance().getAccessoryStateHandler().setStateForReportUpdateResult(consumerInfo);
            new AccessoryInfoAdapter().updateAccessoryDB(consumerInfo.getAccessoryInfo());
            XDMSecReceiverApiCall.getInstance().xdmUpdateResults(z ? 200 : -1, AccessoryState.UPDATE_TO_REPORTING.getValue());
        }
    }
}
