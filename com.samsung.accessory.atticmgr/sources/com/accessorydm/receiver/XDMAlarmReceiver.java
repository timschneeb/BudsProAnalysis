package com.accessorydm.receiver;

import com.accessorydm.dmstarter.XDMInitExecutor;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.postpone.PostponeManager;
import com.samsung.android.fotaagent.SafeBroadcastReceiver;
import com.samsung.android.fotaprovider.log.Log;

public class XDMAlarmReceiver extends SafeBroadcastReceiver {
    @Override // com.samsung.android.fotaagent.SafeBroadcastReceiver
    public void handle() {
        Log.D("Receive broadcast message:" + this.action);
        if (!XDMInitExecutor.getInstance().isDmInitializedSuccessfully()) {
            Log.E("DM Not Initialized!!");
        } else if (XCommonInterface.XCOMMON_INTENT_POSTPONE_ACTION.equals(this.action)) {
            Log.I("receive postpone intent");
            PostponeManager.executePostpone();
        }
    }
}
