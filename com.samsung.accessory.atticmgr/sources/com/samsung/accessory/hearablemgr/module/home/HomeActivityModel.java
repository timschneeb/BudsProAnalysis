package com.samsung.accessory.hearablemgr.module.home;

import com.samsung.accessory.fotaprovider.FotaProviderEventHandler;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.home.card.CardCleaning;
import com.samsung.accessory.hearablemgr.module.home.card.CardEarbuds;
import com.samsung.accessory.hearablemgr.module.home.card.CardEqualizer;
import com.samsung.accessory.hearablemgr.module.home.card.CardFota;
import com.samsung.accessory.hearablemgr.module.home.card.CardMenuAbout;
import com.samsung.accessory.hearablemgr.module.home.card.CardMenuMain;
import com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls;
import com.samsung.accessory.hearablemgr.module.home.card.CardOobeTips;
import com.samsung.accessory.hearablemgr.module.home.card.CardSeamlessConnection;
import com.samsung.accessory.hearablemgr.module.home.card.CardSmartThingsFind;
import com.samsung.accessory.hearablemgr.module.home.card.CardTouchControl;
import com.samsung.accessory.hearablemgr.module.home.card.CardUsingBigger;
import java.util.ArrayList;
import seccompat.android.util.Log;

/* access modifiers changed from: package-private */
public class HomeActivityModel {
    private static final String TAG = "Attic_HomeActivityModel";

    static void checkFotaStatus(HomeActivity homeActivity) {
    }

    static void onResumeForFota(HomeActivity homeActivity) {
    }

    HomeActivityModel() {
    }

    static ArrayList<Card> createCardList(HomeActivity homeActivity) {
        ArrayList<Card> arrayList = new ArrayList<>();
        arrayList.add(new CardEarbuds(homeActivity));
        arrayList.add(new CardNoiseControls(homeActivity));
        arrayList.add(new CardEqualizer());
        arrayList.add(new CardTouchControl(homeActivity));
        arrayList.add(new CardMenuMain(homeActivity));
        arrayList.add(new CardMenuAbout(homeActivity));
        return arrayList;
    }

    static Card newTipsCardInstance(HomeActivity homeActivity, int i) {
        Card newTipsCardInstance = HomeActivityModelCommon.newTipsCardInstance(homeActivity, i);
        return (newTipsCardInstance == null && i == 104) ? new CardUsingBigger(homeActivity) : newTipsCardInstance;
    }

    static int selectNextTipCard(HomeActivity homeActivity) {
        if (CardFota.needToShow()) {
            return 101;
        }
        if (CardOobeTips.needToShow(homeActivity)) {
            return 102;
        }
        if (CardSmartThingsFind.needToShow()) {
            return 106;
        }
        if (CardCleaning.needToShow()) {
            return 103;
        }
        if (CardUsingBigger.needToShow()) {
            return 104;
        }
        return CardSeamlessConnection.needToShow() ? 105 : 100;
    }

    static void emergencyFota(HomeActivity homeActivity) {
        FotaProviderEventHandler.softwareUpdate(Application.getContext());
    }

    static void initCardShowTime() {
        HomeActivityModelCommon.initCardShowTime();
        long j = 0;
        if (Preferences.getLong(PreferenceKey.USING_BIGGER_CARD_SHOW_TIME, 0) == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            if (!Application.DEBUG_MODE) {
                j = Card.MILLIS_A_WEEK;
            }
            long j2 = currentTimeMillis + j;
            Log.d(TAG, "CardUsingBigger : nextShowTime=" + UiUtil.MillisToString(j2));
            Preferences.putLong(PreferenceKey.USING_BIGGER_CARD_SHOW_TIME, Long.valueOf(j2));
        }
    }
}
