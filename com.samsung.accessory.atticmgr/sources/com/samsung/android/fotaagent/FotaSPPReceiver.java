package com.samsung.android.fotaagent;

import android.text.TextUtils;
import com.accessorydm.db.file.XDBRegistrationAdp;
import com.samsung.android.fotaagent.push.SPP;
import com.samsung.android.fotaagent.push.SPPConfig;
import com.samsung.android.fotaprovider.appstate.FotaProviderState;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.OperatorUtil;

public class FotaSPPReceiver extends SafeBroadcastReceiver {
    @Override // com.samsung.android.fotaagent.SafeBroadcastReceiver
    public void handle() {
        Log.D("Receive broadcast message:" + this.action);
        if (!FotaProviderState.isDeviceRegisteredDB()) {
            Log.I("No action is available before device registered");
        } else if (!OperatorUtil.isSPP()) {
            Log.I("No action is available before SPP is not Push type");
        } else if (SPPConfig.PUSH_REGISTRATION_CHANGED_ACTION.equals(this.action)) {
            if (SPPConfig.APP_ID.equals(this.intent.getStringExtra("appId"))) {
                SPP spp = SPP.getSPP();
                if (spp.getSPPReceiver() != null) {
                    spp.sendResult(this.intent);
                } else if (FotaProviderState.isPushRegisteredDB()) {
                    Log.W("SPP needs re-register");
                    XDBRegistrationAdp.setPushRegistrationStatus(0);
                }
            }
        } else if ("android.intent.action.PACKAGE_ADDED".equals(this.action) || SPPConfig.SERVICE_DATA_DELETED_ACTION.equals(this.action)) {
            if (this.intent.getData() != null && SPPConfig.SPP_PACKAGENAME.equals(this.intent.getData().getSchemeSpecificPart()) && FotaProviderState.isPushRegisteredDB()) {
                Log.W("SPP needs re-register");
                XDBRegistrationAdp.setPushRegistrationStatus(0);
            }
        } else if (SPPConfig.APP_ID.equals(this.action)) {
            String stringExtra = this.intent.getStringExtra("msg");
            if (!TextUtils.isEmpty(stringExtra)) {
                new ProcessFOTA().updateOnBackgroundByPush(stringExtra);
            }
        }
    }
}
