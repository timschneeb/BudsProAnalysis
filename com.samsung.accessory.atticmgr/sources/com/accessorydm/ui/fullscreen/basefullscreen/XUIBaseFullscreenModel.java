package com.accessorydm.ui.fullscreen.basefullscreen;

import android.text.TextUtils;
import com.accessorydm.db.file.XDBFumoAdp;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;
import java.util.Locale;

public abstract class XUIBaseFullscreenModel {
    public String getActionBarTitleText() {
        return FotaProviderInitializer.getContext().getString(DeviceType.get().getTextType().getTitleId());
    }

    public String getFirmwareVersion() {
        String xdbGetUpdateFWVer = XDBFumoAdp.xdbGetUpdateFWVer();
        if (TextUtils.isEmpty(xdbGetUpdateFWVer)) {
            return null;
        }
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_UPDATE_VERSION), xdbGetUpdateFWVer);
    }

    public String getFirmwareSize() {
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_UPDATE_SIZE_MB), String.format(Locale.getDefault(), "%.2f", Double.valueOf(((double) XDBFumoAdp.xdbGetObjectSizeFUMO()) / 1048576.0d)));
    }

    public String getWhatsNewText() {
        return XDBFumoAdp.xdbGetFUMODescription();
    }
}
