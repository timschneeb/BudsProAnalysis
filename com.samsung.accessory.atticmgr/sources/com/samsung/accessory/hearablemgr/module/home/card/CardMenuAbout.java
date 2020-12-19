package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.fota.util.FotaUtil;
import com.samsung.accessory.hearablemgr.module.aboutmenu.AboutEarbudsActivity;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.softwareupdate.SoftwareUpdateActivity;
import com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity;
import seccompat.android.util.Log;

public class CardMenuAbout extends Card {
    private static final String TAG = "Attic_CardMenuAbout";
    private final Activity mActivity;
    private ItemViewHolder mItemViewHolder;
    private View.OnClickListener mOnClickAboutEarBuds = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuAbout.AnonymousClass3 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.ABOUT_EARBUDS, SA.Screen.HOME);
            CardMenuAbout.this.mActivity.startActivity(new Intent(CardMenuAbout.this.mActivity, AboutEarbudsActivity.class));
        }
    };
    private View.OnClickListener mOnClickEarbudsSoftwareUpdate = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuAbout.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.UPDATE_EARBUDS_SOFTWARE, SA.Screen.HOME);
            CardMenuAbout.this.mActivity.startActivity(new Intent(CardMenuAbout.this.mActivity, SoftwareUpdateActivity.class));
        }
    };
    private View.OnClickListener mOnClickTipsAndUserManual = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuAbout.AnonymousClass2 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.TIPS_AND_USER_MANUAL, SA.Screen.HOME);
            CardMenuAbout.this.mActivity.startActivity(new Intent(CardMenuAbout.this.mActivity, TipsAndUserManualActivity.class));
        }
    };

    public CardMenuAbout(Activity activity) {
        super(3);
        this.mActivity = activity;
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void onBindItemViewHolder(Card.ItemViewHolder itemViewHolder) {
        this.mItemViewHolder = (ItemViewHolder) itemViewHolder;
        updateUI();
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void updateUI() {
        Log.d(TAG, "updateUI()");
        if (this.mItemViewHolder != null) {
            if (!Application.getCoreService().isConnected()) {
                UiUtil.setEnabledWithChildren(this.mItemViewHolder.earbudsSoftwareUpdate, false);
            } else {
                UiUtil.setEnabledWithChildren(this.mItemViewHolder.earbudsSoftwareUpdate, true);
            }
            this.mItemViewHolder.earbudsSoftwareUpdate.setOnClickListener(this.mOnClickEarbudsSoftwareUpdate);
            this.mItemViewHolder.tipsAndUserManual.setOnClickListener(this.mOnClickTipsAndUserManual);
            this.mItemViewHolder.aboutEarBuds.setOnClickListener(this.mOnClickAboutEarBuds);
            if (FotaUtil.getCheckFotaUpdate()) {
                this.mItemViewHolder.textFotaBadge.setVisibility(0);
            } else {
                this.mItemViewHolder.textFotaBadge.setVisibility(4);
            }
        }
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_menu_about, viewGroup, false));
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        View aboutEarBuds;
        View earbudsSoftwareUpdate;
        ImageView imageSwUpdate;
        TextView textFotaBadge;
        View tipsAndUserManual;

        ItemViewHolder(View view) {
            super(view);
            this.imageSwUpdate = (ImageView) view.findViewById(R.id.menu_list_earbuds_software_update_icon);
            this.earbudsSoftwareUpdate = view.findViewById(R.id.layout_earbuds_software_update);
            this.tipsAndUserManual = view.findViewById(R.id.layout_tips_and_user_manual);
            this.aboutEarBuds = view.findViewById(R.id.layout_about_earbuds);
            this.textFotaBadge = (TextView) view.findViewById(R.id.text_badge_fota);
        }
    }
}
