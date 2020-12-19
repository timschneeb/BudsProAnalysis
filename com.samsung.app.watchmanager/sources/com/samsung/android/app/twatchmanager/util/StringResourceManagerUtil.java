package com.samsung.android.app.twatchmanager.util;

import com.samsung.android.app.watchmanager.R;

public class StringResourceManagerUtil {
    public static final String BAND_TYPE = "band";
    public static final String EARBUD_TYPE = "earbud";
    public static final String WATCH_TYPE = "watch";

    public static int pairingDesc(String str) {
        return BAND_TYPE.equalsIgnoreCase(str) ? R.string.pairing_desc_band : R.string.pairing_desc;
    }
}
