package com.accessorydm.ui.dialog.model;

import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;
import com.samsung.android.fotaprovider.util.UiUtil;
import com.samsung.android.fotaprovider.util.type.HostDeviceTextType;
import com.sec.android.fotaprovider.R;
import java.util.Locale;

public final class DownloadLowMemoryModel extends XUIDialogModel.Base {
    public DownloadLowMemoryModel() {
        super(title(), message(), ButtonStrategy.Neutral.NONE, new ButtonStrategy.StubCancel(), new GoToStorageButtonStrategy());
    }

    private static String title() {
        return getString(R.string.STR_ACCESSORY_LOW_MEMORY_TITLE);
    }

    private static String message() {
        String format = String.format(Locale.getDefault(), "%d", Long.valueOf((long) Math.ceil(((double) (XDBFumoAdp.xdbGetObjectSizeFUMO() * 2)) / 1048576.0d)));
        return String.format(getString(HostDeviceTextType.get().getDownloadFailedLowMemoryMessageId()), format);
    }

    private static class GoToStorageButtonStrategy extends ButtonStrategy.Positive {
        private GoToStorageButtonStrategy() {
            super(XUIDialogModel.Base.getString(R.string.STR_BTN_GO_TO_STORAGE));
        }

        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
            UiUtil.showStorageSetting();
        }
    }
}
