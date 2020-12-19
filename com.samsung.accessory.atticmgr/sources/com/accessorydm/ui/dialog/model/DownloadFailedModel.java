package com.accessorydm.ui.dialog.model;

import com.accessorydm.agent.XDMTask;
import com.accessorydm.network.NetworkBlockedType;
import com.accessorydm.network.NetworkChecker;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;
import com.samsung.android.fotaprovider.util.OperatorUtil;
import com.samsung.android.fotaprovider.util.UiUtil;
import com.samsung.android.fotaprovider.util.type.HostDeviceTextType;
import com.sec.android.fotaprovider.R;

public final class DownloadFailedModel extends XUIDialogModel.Base {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DownloadFailedModel() {
        super(title(), message(), ButtonStrategy.Neutral.NONE, isNetworkDisconnected() ? ButtonStrategy.Negative.NONE : new NegativeStubOkButtonStrategy(), isNetworkDisconnected() ? new ButtonStrategy.StubOk() : new WifiSettingButtonStrategy());
    }

    private static String title() {
        return getString(R.string.STR_ACCESSORY_DOWNLOAD_COULDNT_COMPLETE_DOWNLOAD_TITLE);
    }

    private static String message() {
        if (isNetworkDisconnected()) {
            return getString(HostDeviceTextType.get().getDownloadFailedNetworkDisconnectedMessageId());
        }
        return OperatorUtil.replaceToWLAN(R.string.STR_ACCESSORY_DOWNLOAD_FAILED_WIFI_DISCONNECTED);
    }

    private static boolean isNetworkDisconnected() {
        return NetworkChecker.get().getNetworkBlockType() == NetworkBlockedType.NETWORK_DISCONNECTED;
    }

    @Override // com.accessorydm.ui.dialog.model.XUIDialogModel.Base, com.accessorydm.ui.dialog.model.XUIDialogModel
    public void preExecute() {
        XDMTask.xdmAgentFlagOffWhenDownloadFailed();
    }

    private static class NegativeStubOkButtonStrategy extends ButtonStrategy.Negative {
        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
        }

        NegativeStubOkButtonStrategy() {
            super(XUIDialogModel.Base.getString(R.string.STR_BTN_OK));
        }
    }

    private static class WifiSettingButtonStrategy extends ButtonStrategy.Positive {
        WifiSettingButtonStrategy() {
            super(OperatorUtil.replaceToWLAN(R.string.STR_BTN_WIFI_SETTINGS));
        }

        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
            UiUtil.showWiFiSetting();
        }
    }
}
