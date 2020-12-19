package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaUtil;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import seccompat.android.util.Log;

public class CardFota extends Card implements View.OnClickListener {
    private static final String TAG = "Attic_CardFota";
    private Activity mActivity;
    private ItemViewHolder mItemViewHolder;

    public CardFota(Activity activity) {
        super(101);
        this.mActivity = activity;
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void onBindItemViewHolder(Card.ItemViewHolder itemViewHolder) {
        this.mItemViewHolder = (ItemViewHolder) itemViewHolder;
        updateUI();
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void updateUI() {
        ItemViewHolder itemViewHolder = this.mItemViewHolder;
        if (itemViewHolder != null) {
            itemViewHolder.textDescription.setText(R.string.fota_card_content);
            this.mItemViewHolder.layoutInlineCue.setContentDescription(this.mItemViewHolder.textDescription.getText());
            this.mItemViewHolder.textAction.setText(R.string.fota_card_view_update_button);
            this.mItemViewHolder.textCancel.setOnClickListener(this);
            this.mItemViewHolder.textAction.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.text_action) {
            Log.d(TAG, "Click FOTA card OK button.");
            SamsungAnalyticsUtil.sendEvent(SA.Event.TIPS_VIEW_UPDATE, SA.Screen.HOME);
            CardFotaUtil.actionOk(this.mActivity);
            ((Card.CardOwnerActivity) this.mActivity).removeTipCard();
        } else if (id == R.id.text_cancel) {
            Log.d(TAG, "Click FOTA card cancel image.");
            SamsungAnalyticsUtil.sendEvent(SA.Event.TIPS_CLOSE, SA.Screen.HOME);
            Preferences.putBoolean(PreferenceKey.FOTA_CARD_SHOW_AGAIN, false, UhmFwUtil.getLastLaunchDeviceId());
            ((Card.CardOwnerActivity) this.mActivity).removeTipCard();
        }
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_inline_cue, viewGroup, false));
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        View layoutInlineCue;
        TextView textAction;
        TextView textCancel;
        TextView textDescription;

        ItemViewHolder(View view) {
            super(view);
            this.textCancel = (TextView) view.findViewById(R.id.text_cancel);
            this.textDescription = (TextView) view.findViewById(R.id.text_description);
            this.textAction = (TextView) view.findViewById(R.id.text_action);
            this.layoutInlineCue = view.findViewById(R.id.layout_inline_cue);
        }
    }

    public static boolean needToShow() {
        if (!FotaUtil.getCheckFotaUpdate() || !Preferences.getBoolean(PreferenceKey.FOTA_CARD_SHOW_AGAIN, true, UhmFwUtil.getLastLaunchDeviceId())) {
            return false;
        }
        return true;
    }
}
