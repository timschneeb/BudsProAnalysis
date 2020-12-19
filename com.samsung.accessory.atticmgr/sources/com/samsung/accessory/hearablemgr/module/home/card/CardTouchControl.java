package com.samsung.accessory.hearablemgr.module.home.card;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener;
import com.samsung.accessory.hearablemgr.common.ui.RecycleAnimationSwitchCompat;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgLockTouchpad;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.touchcontrols.TouchAndHoldActivity;
import seccompat.android.util.Log;

public class CardTouchControl extends Card {
    private static final String TAG = "Attic_CardTouchControl";
    private final Activity mActivity;
    private ItemViewHolder mItemViewHolder;
    private CoreService service;

    public CardTouchControl(Activity activity) {
        super(7);
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
            this.service = Application.getCoreService();
            UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, this.service.isConnected());
            TouchAndHoldActivity.checkApp2App(Application.getContext());
            unregisterListener();
            initView();
            if (this.service.isConnected()) {
                registerListener();
                setConnectedCard();
            } else {
                setDisconnectedCard();
            }
            updateVoiceAssistant();
        }
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_touch_control, viewGroup, false));
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        ImageView blockTouchesImageView;
        ConstraintLayout blockTouchesLayout;
        TextView leftOption;
        TextView rightOption;
        RecycleAnimationSwitchCompat switchCompat;
        ImageView touchAndHoldImageView;
        RelativeLayout touchAndHoldLayout;

        ItemViewHolder(View view) {
            super(view);
            this.switchCompat = (RecycleAnimationSwitchCompat) view.findViewById(R.id.switch_block_touches);
            this.blockTouchesLayout = (ConstraintLayout) view.findViewById(R.id.layout_block_touches);
            this.touchAndHoldLayout = (RelativeLayout) view.findViewById(R.id.layout_touch_and_hold);
            this.blockTouchesImageView = (ImageView) view.findViewById(R.id.block_touches_icon);
            this.touchAndHoldImageView = (ImageView) view.findViewById(R.id.touch_and_hold_icon);
            this.leftOption = (TextView) view.findViewById(R.id.touch_and_hold_sub_txt1);
            this.rightOption = (TextView) view.findViewById(R.id.touch_and_hold_sub_txt2);
        }
    }

    private void initView() {
        this.mItemViewHolder.switchCompat.setChecked(this.service.getEarBudsInfo().touchpadLocked);
        int i = Application.getCoreService().getEarBudsInfo().touchpadOptionLeft == 0 ? Preferences.getInt(PreferenceKey.TOUCHPAD_OPTION_LEFT, 2) : Application.getCoreService().getEarBudsInfo().touchpadOptionLeft;
        int i2 = Application.getCoreService().getEarBudsInfo().touchpadOptionRight == 0 ? Preferences.getInt(PreferenceKey.TOUCHPAD_OPTION_RIGHT, 2) : Application.getCoreService().getEarBudsInfo().touchpadOptionRight;
        this.mItemViewHolder.leftOption.setText(Application.getContext().getString(R.string.routine_label_touchpad_left, TouchAndHoldActivity.getOptionsText(Application.getContext(), 0, i)));
        this.mItemViewHolder.rightOption.setText(Application.getContext().getString(R.string.routine_label_touchpad_right, TouchAndHoldActivity.getOptionsText(Application.getContext(), 1, i2)));
        SamsungAnalyticsUtil.setStatusString(SA.Status.TOUCH_AND_HOLD_STATUS, i + ", " + i2);
    }

    private void registerListener() {
        this.mItemViewHolder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardTouchControl.AnonymousClass1 */

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CardTouchControl.this.service.getEarBudsInfo().touchpadLocked = z;
                CardTouchControl.this.service.sendSppMessage(new MsgLockTouchpad(z));
                SamsungAnalyticsUtil.sendEvent(SA.Event.LOCK_TOUCHPAD, z ? "b" : "a");
            }
        });
        this.mItemViewHolder.blockTouchesLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardTouchControl.AnonymousClass2 */

            public void onClick(View view) {
                CardTouchControl.this.mItemViewHolder.switchCompat.performClick();
            }
        });
        this.mItemViewHolder.touchAndHoldLayout.setOnClickListener(new OnSingleClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardTouchControl.AnonymousClass3 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.OnSingleClickListener
            public void onSingleClick(View view) {
                CardTouchControl.this.mActivity.startActivity(new Intent(CardTouchControl.this.mActivity, TouchAndHoldActivity.class));
                SamsungAnalyticsUtil.sendEvent(SA.Event.TOUCH_AND_HOLD);
            }
        });
    }

    private void unregisterListener() {
        this.mItemViewHolder.switchCompat.setOnCheckedChangeListener(null);
        this.mItemViewHolder.blockTouchesLayout.setOnClickListener(null);
    }

    private void setConnectedCard() {
        setTouchControlCardView();
    }

    private void setDisconnectedCard() {
        UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, false);
    }

    private void updateVoiceAssistant() {
        if (Util.isTalkBackEnabled()) {
            this.mItemViewHolder.switchCompat.setFocusable(false);
            this.mItemViewHolder.switchCompat.setClickable(false);
            if (this.service.isConnected()) {
                setConnectedVoiceAssistant();
            } else {
                setDisconnectedVoiceAssistant();
            }
        } else {
            this.mItemViewHolder.switchCompat.setFocusable(true);
            this.mItemViewHolder.switchCompat.setClickable(true);
        }
    }

    private void setTouchControlCardView() {
        Log.d(TAG, "setTouchControlCardView() : ");
        boolean z = true;
        UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, true);
        UiUtil.setEnabledWithChildren(this.mItemViewHolder.leftOption, this.service.getEarBudsInfo().batteryL > 0);
        TextView textView = this.mItemViewHolder.rightOption;
        if (this.service.getEarBudsInfo().batteryR <= 0) {
            z = false;
        }
        UiUtil.setEnabledWithChildren(textView, z);
    }

    private void setConnectedVoiceAssistant() {
        this.mItemViewHolder.itemView.findViewById(R.id.layout_block_touches).setImportantForAccessibility(1);
        this.mItemViewHolder.itemView.setImportantForAccessibility(2);
    }

    private void setDisconnectedVoiceAssistant() {
        this.mItemViewHolder.itemView.findViewById(R.id.layout_block_touches).setImportantForAccessibility(4);
        this.mItemViewHolder.itemView.setImportantForAccessibility(1);
    }
}
