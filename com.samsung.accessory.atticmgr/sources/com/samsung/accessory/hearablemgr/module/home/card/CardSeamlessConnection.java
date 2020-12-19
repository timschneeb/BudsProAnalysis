package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.mainmenu.SeamlessConnectionActivity;
import seccompat.android.util.Log;

public class CardSeamlessConnection extends Card implements View.OnClickListener {
    private static final String TAG = "Attic_CardSeamlessConnection";
    private Activity mActivity;
    private ItemViewHolder mItemViewHolder;

    public CardSeamlessConnection(Activity activity) {
        super(105);
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
            this.mItemViewHolder.textDescription.setText(this.mActivity.getString(R.string.seamless_connection_desc1) + "\n\n" + this.mActivity.getString(R.string.seamless_connection_desc2));
            this.mItemViewHolder.layoutInlineCue.setContentDescription(this.mItemViewHolder.textDescription.getText());
            this.mItemViewHolder.textCancel.setOnClickListener(this);
            this.mItemViewHolder.textAction.setText(R.string.settings);
            this.mItemViewHolder.textAction.setOnClickListener(this);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.text_action) {
            Log.d(TAG, "R.id.text_action");
            Activity activity = this.mActivity;
            activity.startActivity(new Intent(activity, SeamlessConnectionActivity.class));
        } else if (id == R.id.text_cancel) {
            Log.d(TAG, "R.id.image_cancel");
            SamsungAnalyticsUtil.sendEvent(SA.Event.TIPS_CLOSE, SA.Screen.HOME);
            Preferences.putBoolean(PreferenceKey.SEAMLESS_CONNECTION_CARD_SHOW_AGAIN, false, Preferences.MODE_MANAGER);
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

    public static boolean isSupported() {
        boolean z = Application.getCoreService().isConnected() && Application.getCoreService().getEarBudsInfo().extendedRevision >= 0;
        Log.i(TAG, "isSupported() : " + z);
        return z;
    }

    public static boolean needToShow() {
        if (!isSupported() || !Preferences.getBoolean(PreferenceKey.SEAMLESS_CONNECTION_CARD_SHOW_AGAIN, true, Preferences.MODE_MANAGER)) {
            return false;
        }
        return true;
    }
}
