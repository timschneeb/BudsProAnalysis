package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import android.content.Context;
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
import com.samsung.accessory.hearablemgr.core.gamemode.GameModeManager;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.SpatialSensorManager;
import com.samsung.accessory.hearablemgr.module.aboutmenu.LabsActivity;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.mainmenu.AdvancedActivity;
import com.samsung.accessory.hearablemgr.module.mainmenu.FindMyEarbudsActivity;
import com.samsung.accessory.hearablemgr.module.mainmenu.GeneralActivity;
import com.samsung.accessory.hearablemgr.module.notification.NewNotificationActivity;
import seccompat.android.util.Log;

public class CardMenuMain extends Card {
    private static final String TAG = "Attic_CardMenuMain";
    private final Activity mActivity;
    private ItemViewHolder mItemViewHolder;
    private View.OnClickListener mOnClickAdvanced = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuMain.AnonymousClass2 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.ADVANCED, SA.Screen.HOME);
            CardMenuMain.this.mActivity.startActivity(new Intent(CardMenuMain.this.mActivity, AdvancedActivity.class));
        }
    };
    private View.OnClickListener mOnClickFindMyGear = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuMain.AnonymousClass4 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.FIND_MY_EARBUDS, SA.Screen.HOME);
            CardMenuMain.this.mActivity.startActivity(new Intent(CardMenuMain.this.mActivity, FindMyEarbudsActivity.class));
        }
    };
    private View.OnClickListener mOnClickGeneral = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuMain.AnonymousClass5 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.GENERAL, SA.Screen.HOME);
            CardMenuMain.this.mActivity.startActivity(new Intent(CardMenuMain.this.mActivity, GeneralActivity.class));
        }
    };
    private View.OnClickListener mOnClickLabs = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuMain.AnonymousClass3 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.LABS, SA.Screen.HOME);
            CardMenuMain.this.mActivity.startActivity(new Intent(CardMenuMain.this.mActivity, LabsActivity.class));
        }
    };
    private View.OnClickListener mOnClickNotification = new OnSingleClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardMenuMain.AnonymousClass1 */

        @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
        public void onSingleClick(View view) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.NOTIFICATIONS, SA.Screen.HOME);
            CardMenuMain.this.mActivity.startActivity(new Intent(CardMenuMain.this.mActivity, NewNotificationActivity.class));
        }
    };

    public CardMenuMain(Activity activity) {
        super(2);
        this.mActivity = activity;
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void onBindItemViewHolder(Card.ItemViewHolder itemViewHolder) {
        this.mItemViewHolder = (ItemViewHolder) itemViewHolder;
        updateUI();
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void updateUI() {
        String str;
        Log.d(TAG, "updateUI()");
        if (this.mItemViewHolder != null) {
            hideLabsMenu();
            ItemViewHolder itemViewHolder = this.mItemViewHolder;
            itemViewHolder.notification.setOnClickListener(this.mOnClickNotification);
            itemViewHolder.advanced.setOnClickListener(this.mOnClickAdvanced);
            itemViewHolder.labs.setOnClickListener(this.mOnClickLabs);
            itemViewHolder.findMyGear.setOnClickListener(this.mOnClickFindMyGear);
            itemViewHolder.general.setOnClickListener(this.mOnClickGeneral);
            Context context = itemViewHolder.itemView.getContext();
            if (Application.getAomManager().checkEnabledBixby()) {
                str = context.getString(R.string.settings_voice_wakeup_title) + "  •  " + context.getString(R.string.settings_seamless_connection);
            } else {
                str = context.getString(R.string.settings_seamless_connection);
            }
            if (SpatialSensorManager.isSupported(this.mActivity) && Application.getCoreService().getEarBudsInfo().extendedRevision > 1) {
                str = str + "  •  " + context.getString(R.string.advanced_spatial_audio);
            }
            String str2 = "";
            if (GameModeManager.isSupportDevice()) {
                str2 = str2 + context.getString(R.string.settings_game_mode_title);
            }
            itemViewHolder.textAdvancedDescription.setText(str);
            itemViewHolder.textLabsDesc.setText(str2);
            CoreService coreService = Application.getCoreService();
            if (coreService == null || !coreService.isConnected()) {
                UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, false);
            } else {
                UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, true);
            }
        }
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_menu_main, viewGroup, false));
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        View advanced;
        View dividerAdvanced;
        View dividerLabs;
        View findMyGear;
        View general;
        ImageView imageAdvancedIcon;
        ImageView imageFindMyEarbudIcon;
        ImageView imageGeneralIcon;
        ImageView imageLabsIcon;
        ImageView imageNotiIcon;
        View labs;
        View notification;
        TextView textAdvancedDescription;
        TextView textLabsDesc;

        ItemViewHolder(View view) {
            super(view);
            this.notification = view.findViewById(R.id.layout_notification);
            this.advanced = view.findViewById(R.id.layout_advanced);
            this.labs = view.findViewById(R.id.layout_labs);
            this.findMyGear = view.findViewById(R.id.layout_find_my_gear);
            this.general = view.findViewById(R.id.layout_general);
            this.dividerAdvanced = view.findViewById(R.id.divider_advanced);
            this.dividerLabs = view.findViewById(R.id.divider_labs);
            this.imageNotiIcon = (ImageView) view.findViewById(R.id.menu_list_notification_icon);
            this.imageAdvancedIcon = (ImageView) view.findViewById(R.id.menu_list_advanced_icon);
            this.imageLabsIcon = (ImageView) view.findViewById(R.id.menu_list_labs_icon);
            this.imageFindMyEarbudIcon = (ImageView) view.findViewById(R.id.menu_list_find_my_gear_icon);
            this.imageGeneralIcon = (ImageView) view.findViewById(R.id.menu_list_general_icon);
            this.textAdvancedDescription = (TextView) view.findViewById(R.id.menu_list_advanced_sub_txt);
            this.textLabsDesc = (TextView) view.findViewById(R.id.menu_list_labs_sub_txt);
        }
    }

    private void hideLabsMenu() {
        if (GameModeManager.isSupportDevice()) {
            this.mItemViewHolder.labs.setVisibility(0);
            this.mItemViewHolder.dividerLabs.setVisibility(0);
            return;
        }
        this.mItemViewHolder.labs.setVisibility(8);
        this.mItemViewHolder.dividerLabs.setVisibility(8);
    }
}
