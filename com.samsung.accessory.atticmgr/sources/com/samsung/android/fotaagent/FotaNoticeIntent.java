package com.samsung.android.fotaagent;

import com.samsung.android.fotaprovider.FotaProviderInitializer;

public interface FotaNoticeIntent {
    public static final String INTENT_FOTA_BADGE_COUNT = ("com.sec.android.fotaprovider.FOTA_BADGECOUNT_" + FotaProviderInitializer.getContext().getPackageName());
    public static final String INTENT_LAST_CHECKED_DATE = ("com.sec.android.fotaprovider.FOTA_CHECKED_DATE_UPDATE_" + FotaProviderInitializer.getContext().getPackageName());
    public static final String INTENT_LAST_UPDATE_INFO = ("com.sec.android.fotaprovider.LAST_UPDATE_INFO_" + FotaProviderInitializer.getContext().getPackageName());
    public static final String INTENT_UPDATE_IN_PROGRESS = ("com.sec.android.fotaprovider.UPDATE_IN_PROGRESS_" + FotaProviderInitializer.getContext().getPackageName());
}
