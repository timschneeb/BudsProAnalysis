package com.accessorydm.ui.noupdatable;

import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.sec.android.fotaprovider.R;

public class XUINoUpdatableVersionModel extends XUIBaseFullscreenModel {
    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getFirmwareSize() {
        return null;
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getWhatsNewText() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public String getGuideTitle() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_NO_UPDATABLE_VERSION);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getFirmwareVersion() {
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_CURRENT_VERSION), new AccessoryInfoAdapter().getFirmwareVersion());
    }
}
