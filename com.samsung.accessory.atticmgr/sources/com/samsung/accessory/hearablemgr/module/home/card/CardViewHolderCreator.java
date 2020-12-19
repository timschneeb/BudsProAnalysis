package com.samsung.accessory.hearablemgr.module.home.card;

import android.view.ViewGroup;
import com.samsung.accessory.hearablemgr.module.home.card.Card;

public class CardViewHolderCreator {
    public static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 6) {
            return CardNoiseControls.onCreateViewHolder(viewGroup);
        }
        if (i == 7) {
            return CardTouchControl.onCreateViewHolder(viewGroup);
        }
        if (i != 104) {
            return null;
        }
        return CardUsingBigger.onCreateViewHolder(viewGroup);
    }
}
