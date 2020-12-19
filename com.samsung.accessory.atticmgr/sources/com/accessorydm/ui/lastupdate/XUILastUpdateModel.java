package com.accessorydm.ui.lastupdate;

import android.text.TextUtils;
import android.text.format.DateFormat;
import com.accessorydm.db.file.XDBLastUpdateAdp;
import com.accessorydm.db.file.XDBLastUpdateInfo;
import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.sec.android.fotaprovider.R;
import java.util.Locale;

public class XUILastUpdateModel extends XUIBaseFullscreenModel {
    private final long lastUpdateDate;
    private final String lastUpdateDescription;
    private final long lastUpdateSize;
    private final String lastUpdateVersion;

    XUILastUpdateModel() {
        XDBLastUpdateInfo lastUpdateInfo = XDBLastUpdateAdp.getLastUpdateInfo();
        if (lastUpdateInfo == null) {
            Log.E("lastUpdateInfo is null, set default info");
            lastUpdateInfo = new XDBLastUpdateInfo();
        }
        this.lastUpdateDate = lastUpdateInfo.getLastUpdateDate();
        this.lastUpdateVersion = lastUpdateInfo.getLastUpdateVersion();
        this.lastUpdateSize = lastUpdateInfo.getLastUpdateDeltaSize();
        this.lastUpdateDescription = lastUpdateInfo.getLastUpdateDescription();
    }

    /* access modifiers changed from: package-private */
    public String getGuideTitle() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_LAST_SUCCESSFUL_UPDATE);
    }

    /* access modifiers changed from: package-private */
    public String getGuideText() {
        if (this.lastUpdateDate == 0) {
            return null;
        }
        String format = DateFormat.getLongDateFormat(FotaProviderInitializer.getContext()).format(Long.valueOf(this.lastUpdateDate));
        String format2 = DateFormat.getTimeFormat(FotaProviderInitializer.getContext()).format(Long.valueOf(this.lastUpdateDate));
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_LAST_UPDATE_DAY_TIME), format, format2);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getActionBarTitleText() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_LAST_UPDATE_TITLE);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getFirmwareVersion() {
        if (TextUtils.isEmpty(this.lastUpdateVersion)) {
            return null;
        }
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_UPDATE_VERSION), this.lastUpdateVersion);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getFirmwareSize() {
        long j = this.lastUpdateSize;
        if (j == 0) {
            return null;
        }
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_UPDATE_SIZE_MB), String.format(Locale.getDefault(), "%.2f", Double.valueOf(((double) j) / 1048576.0d)));
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenModel
    public String getWhatsNewText() {
        return this.lastUpdateDescription;
    }
}
