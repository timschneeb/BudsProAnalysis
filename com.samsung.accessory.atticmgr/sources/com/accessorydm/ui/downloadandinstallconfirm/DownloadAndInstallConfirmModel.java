package com.accessorydm.ui.downloadandinstallconfirm;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.agent.fota.XFOTADl;
import com.accessorydm.agent.fota.XFOTADlAgentHandler;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;

public class DownloadAndInstallConfirmModel extends XUIBaseFullscreenModel {
    private static final DownloadAndInstallConfirmModel instance = new DownloadAndInstallConfirmModel();
    private boolean mEnabledBottomButtons = true;

    public static DownloadAndInstallConfirmModel getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    public void startDownloadAndInstall() {
        Log.I("");
        if (XDBFumoAdp.xdbGetFUMOStatus() == 200) {
            XFOTADl.xfotaDownloadMemoryCheck(1);
        } else {
            XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DL_CONNECT, null, null);
        }
    }

    /* access modifiers changed from: protected */
    public void cancel() {
        Log.I("");
        XDMDmUtils.getInstance().xdmSetWaitWifiConnectMode(0);
        XFOTADlAgentHandler.xfotaDlAgentHdlrStartOMADLAgent(XEventInterface.XEVENT.XEVENT_DL_USER_CANCEL_DOWNLOAD);
        XDBFumoAdp.xdbSetUiMode(0);
    }

    /* access modifiers changed from: package-private */
    public String getGuideTitle() {
        return FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getDownloadAndInstallConfirmTitleId());
    }

    /* access modifiers changed from: package-private */
    public String getGuideText() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_DOWNLOAD_CONFIRM_WIFI_SETTINGS_DETAILS);
    }

    /* access modifiers changed from: package-private */
    public String getCautionText() {
        String string = FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getCautionMainDescriptionId(false));
        int cautionSettingsTextId = DeviceType.get().getTextType().getCautionSettingsTextId();
        int cautionAccessoryUncoupledTextId = DeviceType.get().getTextType().getCautionAccessoryUncoupledTextId();
        if (cautionSettingsTextId != -1) {
            string = string + '\n' + FotaProviderInitializer.getContext().getString(cautionSettingsTextId);
        }
        if (cautionAccessoryUncoupledTextId == -1) {
            return string;
        }
        return string + '\n' + FotaProviderInitializer.getContext().getString(cautionAccessoryUncoupledTextId);
    }

    /* access modifiers changed from: package-private */
    public boolean isEnabledBottomButtons() {
        Log.I("Enabled Bottom Buttons: " + this.mEnabledBottomButtons);
        return this.mEnabledBottomButtons;
    }

    /* access modifiers changed from: package-private */
    public void enableBottomButtons() {
        this.mEnabledBottomButtons = true;
    }

    /* access modifiers changed from: package-private */
    public void disableBottomButtons() {
        this.mEnabledBottomButtons = false;
    }

    /* access modifiers changed from: package-private */
    public String getFirstButtonText() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_BTN_LATER);
    }

    /* access modifiers changed from: package-private */
    public String getSecondButtonText() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_BTN_UPDATE);
    }
}
