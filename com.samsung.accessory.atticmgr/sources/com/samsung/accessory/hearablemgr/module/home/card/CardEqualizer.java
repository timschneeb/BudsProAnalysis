package com.samsung.accessory.hearablemgr.module.home.card;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.DialRotationView;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSetEqualizerType;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import seccompat.android.util.Log;

public class CardEqualizer extends Card implements DialRotationView.DialEventListener {
    private static final String TAG = "Attic_CardEqualizer";
    private static final int TOTAL_PRESET_COUNT = 6;
    private boolean isRTL;
    private ItemViewHolder mItemViewHolder;
    private View.OnClickListener onPresetClickListener = new View.OnClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.home.card.CardEqualizer.AnonymousClass2 */

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.text_preset_0 /*{ENCODED_INT: 2131231525}*/:
                    SamsungAnalyticsUtil.sendEvent(SA.Event.NORMAL, SA.Screen.HOME);
                    CardEqualizer.this.setEqualizerType(0);
                    CardEqualizer.this.mItemViewHolder.mDialRotationView.smoothRotate(0);
                    return;
                case R.id.text_preset_1 /*{ENCODED_INT: 2131231526}*/:
                    SamsungAnalyticsUtil.sendEvent(SA.Event.BASE_BOOST, SA.Screen.HOME);
                    CardEqualizer.this.setEqualizerType(1);
                    CardEqualizer.this.mItemViewHolder.mDialRotationView.smoothRotate(1);
                    return;
                case R.id.text_preset_2 /*{ENCODED_INT: 2131231527}*/:
                    SamsungAnalyticsUtil.sendEvent(SA.Event.SOFT, SA.Screen.HOME);
                    CardEqualizer.this.setEqualizerType(2);
                    CardEqualizer.this.mItemViewHolder.mDialRotationView.smoothRotate(2);
                    return;
                case R.id.text_preset_3 /*{ENCODED_INT: 2131231528}*/:
                    SamsungAnalyticsUtil.sendEvent(SA.Event.DYNAMIC, SA.Screen.HOME);
                    CardEqualizer.this.setEqualizerType(3);
                    CardEqualizer.this.mItemViewHolder.mDialRotationView.smoothRotate(3);
                    return;
                case R.id.text_preset_4 /*{ENCODED_INT: 2131231529}*/:
                    SamsungAnalyticsUtil.sendEvent(SA.Event.CLEAR, SA.Screen.HOME);
                    CardEqualizer.this.setEqualizerType(4);
                    CardEqualizer.this.mItemViewHolder.mDialRotationView.smoothRotate(4);
                    return;
                case R.id.text_preset_5 /*{ENCODED_INT: 2131231530}*/:
                    SamsungAnalyticsUtil.sendEvent(SA.Event.TREBLE_BOOST, SA.Screen.HOME);
                    CardEqualizer.this.setEqualizerType(5);
                    CardEqualizer.this.mItemViewHolder.mDialRotationView.smoothRotate(5);
                    return;
                default:
                    return;
            }
        }
    };

    public CardEqualizer() {
        super(1);
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
            CoreService coreService = Application.getCoreService();
            initView();
            if (coreService == null || !coreService.isConnected()) {
                UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, false);
                this.mItemViewHolder.mDialSwitch.setVisibility(4);
                this.mItemViewHolder.mDialView.setBackgroundResource(R.drawable.gw_buds_eq_rail_dim);
                clearPresetText();
                enablePresetText(false);
                setDisconnectedVoiceAssistant();
                return;
            }
            UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, true);
            checkEqualizerOutOfRange();
            this.mItemViewHolder.mDialSwitch.setVisibility(0);
            this.mItemViewHolder.mDialRotationView.setPosition(coreService.getEarBudsInfo().equalizerType);
            setPresetPosition(coreService.getEarBudsInfo().equalizerType);
            enablePresetText(true);
            setConnectedVoiceAssistant();
        }
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_equalizer, viewGroup, false));
    }

    private void setConnectedVoiceAssistant() {
        this.mItemViewHolder.itemView.findViewById(R.id.equalizer_container).setImportantForAccessibility(1);
    }

    private void setDisconnectedVoiceAssistant() {
        this.mItemViewHolder.itemView.findViewById(R.id.equalizer_container).setImportantForAccessibility(4);
        this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer));
    }

    private void checkEqualizerOutOfRange() {
        EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
        if (earBudsInfo.equalizerType < 0 || earBudsInfo.equalizerType >= 6) {
            earBudsInfo.equalizerType = 0;
        }
    }

    private void initView() {
        this.isRTL = Util.isSystemLayoutDirectionRtl();
        this.mItemViewHolder.mDialRotationView.syncDialView(this.mItemViewHolder.mDialSwitch);
        this.mItemViewHolder.mDialRotationView.setDialEventListener(this);
        this.mItemViewHolder.mDialRotationView.setRTLConfiguration(this.isRTL);
        this.mItemViewHolder.mDialRotationView.setOnTouchListener(new View.OnTouchListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardEqualizer.AnonymousClass1 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0) {
                    return false;
                }
                SamsungAnalyticsUtil.sendEvent(SA.Event.EQUALIZER_SLIDER, SA.Screen.HOME);
                return false;
            }
        });
        this.mItemViewHolder.preset0.setOnClickListener(this.onPresetClickListener);
        this.mItemViewHolder.preset1.setOnClickListener(this.onPresetClickListener);
        this.mItemViewHolder.preset2.setOnClickListener(this.onPresetClickListener);
        this.mItemViewHolder.preset3.setOnClickListener(this.onPresetClickListener);
        this.mItemViewHolder.preset4.setOnClickListener(this.onPresetClickListener);
        this.mItemViewHolder.preset5.setOnClickListener(this.onPresetClickListener);
    }

    @Override // com.samsung.accessory.hearablemgr.common.ui.DialRotationView.DialEventListener
    public void onDialChanged(int i) {
        setEqualizerType(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setEqualizerType(int i) {
        Application.getCoreService().sendSppMessage(new MsgSetEqualizerType((byte) i));
        Application.getCoreService().getEarBudsInfo().equalizerType = i;
        SamsungAnalyticsUtil.setStatusString(SA.Status.EQUALIZER_STATUS, SamsungAnalyticsUtil.equalizerTypeToDetail(i));
        setPresetPosition(i);
        Util.performHapticFeedback(this.mItemViewHolder.mDialView, 41);
    }

    private void setPresetPosition(int i) {
        Log.d(TAG, "setPresetPosition :: " + i);
        clearPresetText();
        int i2 = R.drawable.gw_buds_eq_rail_6;
        if (i != 0) {
            int i3 = R.drawable.gw_buds_eq_rail_5;
            if (i != 1) {
                int i4 = R.drawable.gw_buds_eq_rail_4;
                if (i == 2) {
                    AppCompatImageView appCompatImageView = this.mItemViewHolder.mDialView;
                    if (!this.isRTL) {
                        i4 = R.drawable.gw_buds_eq_rail_3;
                    }
                    appCompatImageView.setBackgroundResource(i4);
                    this.mItemViewHolder.preset2.setTextColor(Application.getContext().getResources().getColor(R.color.colorPrimary));
                    this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer_content_description, Application.getContext().getString(R.string.eq_preset_soft)));
                } else if (i == 3) {
                    AppCompatImageView appCompatImageView2 = this.mItemViewHolder.mDialView;
                    if (this.isRTL) {
                        i4 = R.drawable.gw_buds_eq_rail_3;
                    }
                    appCompatImageView2.setBackgroundResource(i4);
                    this.mItemViewHolder.preset3.setTextColor(Application.getContext().getResources().getColor(R.color.colorPrimary));
                    this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer_content_description, Application.getContext().getString(R.string.eq_preset_dynamic)));
                } else if (i == 4) {
                    AppCompatImageView appCompatImageView3 = this.mItemViewHolder.mDialView;
                    if (this.isRTL) {
                        i3 = R.drawable.gw_buds_eq_rail_2;
                    }
                    appCompatImageView3.setBackgroundResource(i3);
                    this.mItemViewHolder.preset4.setTextColor(Application.getContext().getResources().getColor(R.color.colorPrimary));
                    this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer_content_description, Application.getContext().getString(R.string.eq_preset_clear)));
                } else if (i == 5) {
                    AppCompatImageView appCompatImageView4 = this.mItemViewHolder.mDialView;
                    if (this.isRTL) {
                        i2 = R.drawable.gw_buds_eq_rail_1;
                    }
                    appCompatImageView4.setBackgroundResource(i2);
                    this.mItemViewHolder.preset5.setTextColor(Application.getContext().getResources().getColor(R.color.colorPrimary));
                    this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer_content_description, Application.getContext().getString(R.string.eq_preset_treble_boost)));
                }
            } else {
                AppCompatImageView appCompatImageView5 = this.mItemViewHolder.mDialView;
                if (!this.isRTL) {
                    i3 = R.drawable.gw_buds_eq_rail_2;
                }
                appCompatImageView5.setBackgroundResource(i3);
                this.mItemViewHolder.preset1.setTextColor(Application.getContext().getResources().getColor(R.color.colorPrimary));
                this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer_content_description, Application.getContext().getString(R.string.eq_preset_bass_boost)));
            }
        } else {
            AppCompatImageView appCompatImageView6 = this.mItemViewHolder.mDialView;
            if (!this.isRTL) {
                i2 = R.drawable.gw_buds_eq_rail_1;
            }
            appCompatImageView6.setBackgroundResource(i2);
            this.mItemViewHolder.preset0.setTextColor(Application.getContext().getResources().getColor(R.color.colorPrimary));
            this.mItemViewHolder.itemView.setContentDescription(Application.getContext().getString(R.string.equalizer_content_description, Application.getContext().getString(R.string.eq_preset_normal)));
        }
    }

    private void enablePresetText(boolean z) {
        float f = z ? 1.0f : 0.4f;
        this.mItemViewHolder.preset0.setAlpha(f);
        this.mItemViewHolder.preset1.setAlpha(f);
        this.mItemViewHolder.preset2.setAlpha(f);
        this.mItemViewHolder.preset3.setAlpha(f);
        this.mItemViewHolder.preset4.setAlpha(f);
        this.mItemViewHolder.preset5.setAlpha(f);
    }

    private void clearPresetText() {
        int color = Application.getContext().getResources().getColor(R.color.color_equalizer_preset_normal);
        this.mItemViewHolder.preset0.setTextColor(color);
        this.mItemViewHolder.preset1.setTextColor(color);
        this.mItemViewHolder.preset2.setTextColor(color);
        this.mItemViewHolder.preset3.setTextColor(color);
        this.mItemViewHolder.preset4.setTextColor(color);
        this.mItemViewHolder.preset5.setTextColor(color);
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        DialRotationView mDialRotationView;
        AppCompatImageView mDialSwitch;
        AppCompatImageView mDialView;
        ImageView mEqualizerIcon;
        AppCompatTextView preset0;
        AppCompatTextView preset1;
        AppCompatTextView preset2;
        AppCompatTextView preset3;
        AppCompatTextView preset4;
        AppCompatTextView preset5;

        ItemViewHolder(View view) {
            super(view);
            this.mEqualizerIcon = (ImageView) view.findViewById(R.id.image_equalizer_icon);
            this.mDialRotationView = (DialRotationView) view.findViewById(R.id.dial_rotation_view);
            this.mDialView = (AppCompatImageView) view.findViewById(R.id.dialview);
            this.mDialSwitch = (AppCompatImageView) view.findViewById(R.id.equalizer_switch);
            this.preset0 = (AppCompatTextView) view.findViewById(R.id.text_preset_0);
            this.preset1 = (AppCompatTextView) view.findViewById(R.id.text_preset_1);
            this.preset2 = (AppCompatTextView) view.findViewById(R.id.text_preset_2);
            this.preset3 = (AppCompatTextView) view.findViewById(R.id.text_preset_3);
            this.preset4 = (AppCompatTextView) view.findViewById(R.id.text_preset_4);
            this.preset5 = (AppCompatTextView) view.findViewById(R.id.text_preset_5);
        }
    }
}
