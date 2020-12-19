package com.accessorydm.ui.dialog;

import android.content.Context;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel;
import com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel;
import com.accessorydm.ui.dialog.model.DownloadFailedModel;
import com.accessorydm.ui.dialog.model.DownloadLowMemoryModel;
import com.accessorydm.ui.dialog.model.DownloadRetryConfirmModel;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.OperatorUtil;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;

public enum XUIDialog {
    NONE {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            Log.W("should not be called");
            return new XUIDialogModel.StubOk(null, null);
        }
    },
    DL_CONNECTION_FAILED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_ACCESSORY_DOWNLOAD_COULDNT_DOWNLOAD_UPDATE_TITLE), getString(R.string.STR_DM_CONNECTION_FAILED));
        }
    },
    DL_MEMORY_FULL {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new DownloadLowMemoryModel();
        }
    },
    DL_INVALID_DELTA {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_DM_GENERAL_UPDATE_EXCEPTION_TITLE), getString(R.string.STR_ACCESSORY_UPDATE_FAILED_INVALID_DELTA));
        }
    },
    DL_DOWNLOAD_FAILED_NETWORK_DISCONNECTED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new DownloadFailedModel();
        }
    },
    DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new DownloadFailedModel();
        }
    },
    DL_DOWNLOAD_RETRY_CONFIRM {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new DownloadRetryConfirmModel();
        }
    },
    DM_ACCESSORY_SYSSCOPE_MODIFIED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(DeviceType.get().getTextType().getAccessoryModifiedTitleId()), getString(R.string.STR_ACCESSORY_MODIFIED));
        }
    },
    DM_ACCESSORY_BLOCKED_BY_POLICY_FAILED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_ACCESSORY_DOWNLOAD_COULDNT_DOWNLOAD_UPDATE_TITLE), getString(DeviceType.get().getTextType().getPolicyBlockedMessageId()));
        }
    },
    DM_ACCESSORY_CONNECTION_FAILED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(null, getString(DeviceType.get().getTextType().getConnectionFailedMessageId()));
        }
    },
    DM_ACCESSORY_LOW_BATTERY_WATCH {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_ACCESSORY_LOW_BATTERY_TITLE), String.format(getString(DeviceType.get().getTextType().getCopyAccessoryLowBatteryMessageId()), Integer.valueOf(AccessoryController.getInstance().getAccessoryUtil().getMinimumBatteryLevel())));
        }
    },
    DM_ACCESSORY_LOW_MEMORY_DOWNLOAD_WATCH {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryLowMemoryModel(AccessoryLowMemoryModel.State.DOWNLOAD);
        }
    },
    DM_ACCESSORY_LOW_MEMORY_COPY_WATCH {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryLowMemoryModel(AccessoryLowMemoryModel.State.COPY);
        }
    },
    DM_ACCESSORY_LOW_MEMORY_INSTALL_WATCH {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryLowMemoryModel(AccessoryLowMemoryModel.State.INSTALL);
        }
    },
    DM_ACCESSORY_COPY_RETRY_CONFIRM {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryCopyExceptionModel(AccessoryCopyExceptionModel.State.RETRY_CONFIRM);
        }
    },
    DM_ACCESSORY_COPY_RETRY_LATER {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryCopyExceptionModel(AccessoryCopyExceptionModel.State.RETRY_LATER);
        }
    },
    DM_ACCESSORY_COPY_RETRY_LATER_ACCESSORY_UNCOUPLED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryCopyExceptionModel(AccessoryCopyExceptionModel.State.RETRY_LATER_ACCESSORY_UNCOUPLED);
        }
    },
    DM_ACCESSORY_COPY_FAILED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new AccessoryCopyExceptionModel(AccessoryCopyExceptionModel.State.FAILED);
        }
    },
    DM_ACCESSORY_INSTALL_FAILED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(null, getString(R.string.STR_ACCESSORY_UPDATE_FAILED_TRY_LATER));
        }
    },
    DM_CONNECTION_FAILED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_DM_GENERAL_UPDATE_EXCEPTION_TITLE), getString(R.string.STR_DM_CONNECTION_FAILED));
        }
    },
    DM_SERVER_PARTIALLY_OPEN {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_DM_GENERAL_UPDATE_EXCEPTION_TITLE), getString(R.string.STR_ACCESSORY_SERVER_PARTIALLY_OPEN));
        }
    },
    DM_ROAMING_WIFI_DISCONNECTED {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.ui.dialog.XUIDialog
        public XUIDialogModel getDialogModel() {
            return new XUIDialogModel.StubOk(getString(R.string.STR_ROAMING_WIFI_DISCONNECTED_TITLE), OperatorUtil.replaceToWLAN(R.string.STR_ROAMING_WIFI_DISCONNECTED));
        }
    };
    
    private static final XUIDialog[] values = values();

    /* access modifiers changed from: package-private */
    public abstract XUIDialogModel getDialogModel();

    public static XUIDialog valueOf(int i) {
        try {
            return values[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    static String getString(int i) {
        return getContext().getString(i);
    }

    private static Context getContext() {
        return XDMDmUtils.getContext();
    }
}
