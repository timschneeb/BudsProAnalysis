package com.samsung.accessory.hearablemgr.module.home;

import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.home.card.CardCleaning;
import com.samsung.accessory.hearablemgr.module.home.card.CardFota;
import com.samsung.accessory.hearablemgr.module.home.card.CardOobeTips;
import com.samsung.accessory.hearablemgr.module.home.card.CardSeamlessConnection;
import com.samsung.accessory.hearablemgr.module.home.card.CardSmartThingsFind;
import seccompat.android.util.Log;

/* access modifiers changed from: package-private */
public class HomeActivityModelCommon {
    private static final String TAG = "Attic_HomeActivityModelCommon";

    HomeActivityModelCommon() {
    }

    static Card newTipsCardInstance(HomeActivity homeActivity, int i) {
        switch (i) {
            case 101:
                return new CardFota(homeActivity);
            case 102:
                return new CardOobeTips(homeActivity);
            case 103:
                return new CardCleaning(homeActivity);
            case 104:
            default:
                return null;
            case 105:
                return new CardSeamlessConnection(homeActivity);
            case 106:
                return new CardSmartThingsFind(homeActivity);
        }
    }

    static void initCardShowTime() {
        Log.d(TAG, "initCardShowTime()");
        long j = 0;
        if (Preferences.getLong(PreferenceKey.CLEANING_CARD_SHOW_TIME, 0) == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            if (!Application.DEBUG_MODE) {
                j = Card.MILLIS_A_WEEK;
            }
            long j2 = currentTimeMillis + j;
            Log.d(TAG, "CardCleaning : nextShowTime=" + UiUtil.MillisToString(j2));
            Preferences.putLong(PreferenceKey.CLEANING_CARD_SHOW_TIME, Long.valueOf(j2));
        }
    }
}
