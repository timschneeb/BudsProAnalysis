package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;

public class DiagMonConfig {
    public boolean globalNetworkMode;
    private String mAgreeAsString = "";
    private boolean mAgreement;
    private Context mContext;
    private String mDeviceId = "";
    public boolean mIsDefaultNetwork;
    private String mServiceId = "";
    private String mServiceVer = "";
    private String mTrackingId = "";
    private oldDiagMonConfig oldConf;

    public DiagMonConfig(Context context) {
        this.mContext = context;
        this.mAgreement = false;
        this.globalNetworkMode = true;
        this.mIsDefaultNetwork = false;
        try {
            this.mServiceVer = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (DiagMonUtil.checkDMA(this.mContext) == 1) {
            this.oldConf = new oldDiagMonConfig();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public DiagMonConfig setServiceId(String str) {
        this.mServiceId = str;
        return this;
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public String getServiceVer() {
        return this.mServiceVer;
    }

    public DiagMonConfig setAgree(String str) {
        this.mAgreeAsString = str;
        if (this.mAgreeAsString == null) {
            Log.e(DiagMonUtil.TAG, "You can't use agreement as null");
            return this;
        }
        if (DiagMonUtil.checkDMA(this.mContext) == 1) {
            this.oldConf.setAgree(this.mAgreeAsString);
        } else if (DiagMonUtil.AGREE_TYPE_DIAGNOSTIC.equals(this.mAgreeAsString) || "S".equals(this.mAgreeAsString) || "G".equals(this.mAgreeAsString)) {
            this.mAgreement = true;
        } else {
            this.mAgreement = false;
        }
        return this;
    }

    public String getAgreeAsString() {
        if (DiagMonUtil.checkDMA(this.mContext) == 1) {
            return this.oldConf.getAgreeAsString();
        }
        return this.mAgreeAsString;
    }

    public boolean getAgree() {
        if (DiagMonUtil.checkDMA(this.mContext) == 1) {
            return this.oldConf.getAgree();
        }
        return this.mAgreement;
    }

    public DiagMonConfig setDeviceId(String str) {
        this.mDeviceId = str;
        return this;
    }

    public String getDeviceId() {
        return this.mDeviceId;
    }

    public DiagMonConfig setTrackingId(String str) {
        this.mTrackingId = str;
        return this;
    }

    public String getTrackingId() {
        return this.mTrackingId;
    }

    public DiagMonConfig setDefaultNetwork(boolean z) {
        this.mIsDefaultNetwork = true;
        this.globalNetworkMode = z;
        String str = DiagMonUtil.TAG;
        Log.i(str, "DefaultNetwork : " + this.mIsDefaultNetwork);
        String str2 = DiagMonUtil.TAG;
        Log.i(str2, "globalNetworkMode : " + this.globalNetworkMode);
        return this;
    }

    public boolean isEnabledDefaultNetwork() {
        return this.mIsDefaultNetwork;
    }

    public boolean getDefaultNetworkMode() {
        return this.globalNetworkMode;
    }

    /* access modifiers changed from: package-private */
    public class oldDiagMonConfig {
        private String mAgreeAsString = "";
        private boolean mAgreement = false;

        oldDiagMonConfig() {
        }

        public void setAgree(String str) {
            this.mAgreeAsString = str;
            if ("S".equals(this.mAgreeAsString) || "G".equals(this.mAgreeAsString)) {
                this.mAgreeAsString = "Y";
            }
            if (this.mAgreeAsString.isEmpty()) {
                Log.w(DiagMonUtil.TAG, "Empty agreement");
                this.mAgreement = false;
            } else if ("Y".equals(this.mAgreeAsString) || DiagMonUtil.AGREE_TYPE_DIAGNOSTIC.equals(this.mAgreeAsString)) {
                this.mAgreement = true;
            } else {
                String str2 = DiagMonUtil.TAG;
                Log.w(str2, "Wrong agreement : " + str);
                this.mAgreement = false;
            }
        }

        public boolean getAgree() {
            return this.mAgreement;
        }

        public String getAgreeAsString() {
            return this.mAgreeAsString;
        }
    }
}
