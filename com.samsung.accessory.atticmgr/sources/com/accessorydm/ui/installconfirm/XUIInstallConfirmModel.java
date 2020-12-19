package com.accessorydm.ui.installconfirm;

import android.text.format.DateFormat;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.postpone.PostponeType;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;
import java.util.Date;

public class XUIInstallConfirmModel extends XUIBaseFullscreenModel {
    private static final XUIInstallConfirmModel instance = new XUIInstallConfirmModel();
    private boolean mEnabledBottomButtons = true;

    public static XUIInstallConfirmModel getInstance() {
        return instance;
    }

    public boolean isNeededInstallCountdown() {
        if (isScheduleInstall()) {
            return isTimeToStartScheduleInstall();
        }
        return isForceInstall();
    }

    private boolean isScheduleInstall() {
        return XDBPostPoneAdp.xdbGetPostponeType() == PostponeType.SCHEDULE_INSTALL;
    }

    private boolean isTimeToStartScheduleInstall() {
        return XDBPostPoneAdp.xdbGetPostponeTime() <= System.currentTimeMillis();
    }

    private boolean isForceInstall() {
        return XDBPostPoneAdp.xdbGetForceInstall() == 1;
    }

    private boolean isUpdateInProgress() {
        return XDBFumoAdp.xdbGetFUMOStatus() == 60;
    }

    /* access modifiers changed from: package-private */
    public void startInstall() {
        AccessoryController.getInstance().getNotificationController().removeAccessoryNotification($$Lambda$XUIInstallConfirmModel$Ig4dPaAVfsPm04SLct3gpPJ2tSk.INSTANCE);
    }

    static /* synthetic */ void lambda$startInstall$0() {
        Log.I("");
        InstallCountdown.getInstance().stop();
        PostponeManager.cancelPostpone();
        XDMFileTransferManager.checkDeviceInfo();
    }

    /* access modifiers changed from: package-private */
    public void startOneDayPostpone() {
        if (InstallCountdown.getInstance().isCountingDown()) {
            Log.D("Do nothing if countdown");
        } else if (isScheduleInstall()) {
            Log.D("Do nothing if schedule install");
        } else if (isUpdateInProgress()) {
            Log.D("Do nothing if update in progress");
        } else {
            PostponeManager.startPostpone(PostponeType.INSTALL_BACK_KEY, System.currentTimeMillis() + 86400000);
        }
    }

    /* access modifiers changed from: package-private */
    public String getGuideTitle() {
        if (isForceInstall()) {
            return FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getForceInstallConfirmTitleId());
        }
        return FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getInstallConfirmTitleId());
    }

    /* access modifiers changed from: package-private */
    public String getGuideText() {
        Date date = new Date(XDBPostPoneAdp.xdbGetPostponeTime());
        String format = DateFormat.getLongDateFormat(FotaProviderInitializer.getContext()).format(date);
        String format2 = DateFormat.getTimeFormat(FotaProviderInitializer.getContext()).format(date);
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_INSTALL_CONFIRM_SCHEDULED), format2, format);
    }

    /* access modifiers changed from: package-private */
    public boolean isNeededToHideGuideText() {
        return !isScheduleInstall() && !isForceInstall();
    }

    /* access modifiers changed from: package-private */
    public String getCautionText() {
        boolean isNeededInstallCountdown = isNeededInstallCountdown();
        String string = FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getCautionMainDescriptionId(isNeededInstallCountdown));
        int cautionSettingsTextId = DeviceType.get().getTextType().getCautionSettingsTextId();
        int cautionBackupTextId = DeviceType.get().getTextType().getCautionBackupTextId(isNeededInstallCountdown);
        if (cautionSettingsTextId != -1) {
            string = string + '\n' + FotaProviderInitializer.getContext().getString(cautionSettingsTextId);
        }
        if (cautionBackupTextId == -1) {
            return string;
        }
        return string + '\n' + FotaProviderInitializer.getContext().getString(cautionBackupTextId);
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
        return FotaProviderInitializer.getContext().getString(R.string.STR_BTN_SCHEDULE_INSTALL);
    }

    /* access modifiers changed from: package-private */
    public String getSecondButtonText() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_BTN_INSTALL_NOW);
    }
}
