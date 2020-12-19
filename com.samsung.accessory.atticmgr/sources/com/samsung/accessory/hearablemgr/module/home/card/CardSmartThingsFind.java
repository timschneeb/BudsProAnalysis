package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmUtils;
import com.samsung.accessory.hearablemgr.module.home.SmartThingsUtil;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import seccompat.android.util.Log;

public class CardSmartThingsFind extends Card implements View.OnClickListener {
    private static final String TAG = "Attic_CardSmartThingsFind";
    private Activity mActivity;
    private ItemViewHolder mItemViewHolder;

    public CardSmartThingsFind(Activity activity) {
        super(106);
        this.mActivity = activity;
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void onBindItemViewHolder(Card.ItemViewHolder itemViewHolder) {
        this.mItemViewHolder = (ItemViewHolder) itemViewHolder;
        updateUI();
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void updateUI() {
        if (this.mItemViewHolder != null) {
            Activity activity = this.mActivity;
            String string = activity.getString(R.string.if_you_lose_your_earbuds, new Object[]{activity.getString(R.string.smartthings_find)});
            this.mItemViewHolder.imageIcon.setImageResource(R.drawable.icon_smart_things);
            this.mItemViewHolder.layoutImageIcon.setVisibility(0);
            this.mItemViewHolder.textDescription.setText(string);
            this.mItemViewHolder.layoutInlineCue.setContentDescription(this.mItemViewHolder.textDescription.getText());
            this.mItemViewHolder.textCancel.setOnClickListener(this);
            this.mItemViewHolder.textAction.setText(R.string.get_started);
            this.mItemViewHolder.textAction.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.text_action) {
            Log.d(TAG, "R.id.text_action");
            setShowAgain(false);
            ((Card.CardOwnerActivity) this.mActivity).removeTipCard();
            SmartThingsUtil.startSmartThingsFind(this.mActivity, null);
        } else if (id == R.id.text_cancel) {
            Log.d(TAG, "R.id.image_cancel");
            SamsungAnalyticsUtil.sendEvent(SA.Event.TIPS_CLOSE, SA.Screen.HOME);
            setShowAgain(false);
            ((Card.CardOwnerActivity) this.mActivity).removeTipCard();
        }
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_inline_cue, viewGroup, false));
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        ImageView imageIcon;
        View layoutImageIcon;
        View layoutInlineCue;
        TextView textAction;
        TextView textCancel;
        TextView textDescription;

        ItemViewHolder(View view) {
            super(view);
            this.textCancel = (TextView) view.findViewById(R.id.text_cancel);
            this.layoutImageIcon = view.findViewById(R.id.layout_image_icon);
            this.imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            this.textDescription = (TextView) view.findViewById(R.id.text_description);
            this.textAction = (TextView) view.findViewById(R.id.text_action);
            this.layoutInlineCue = view.findViewById(R.id.layout_inline_cue);
        }
    }

    public static boolean getShowAgain() {
        return Preferences.getBoolean(PreferenceKey.SMART_THINGS_FIND_CARD_SHOW_AGAIN, true);
    }

    private static void setShowAgain(boolean z) {
        Preferences.putBoolean(PreferenceKey.SMART_THINGS_FIND_CARD_SHOW_AGAIN, Boolean.valueOf(z));
    }

    public static boolean needToShow() {
        if (!FmmUtils.isSupportedOfflineFinding().booleanValue() || !getShowAgain() || !SmartThingsUtil.getFmeServiceReady(false)) {
            return false;
        }
        return true;
    }
}
