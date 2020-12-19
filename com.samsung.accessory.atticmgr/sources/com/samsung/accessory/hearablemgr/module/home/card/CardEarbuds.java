package com.samsung.accessory.hearablemgr.module.home.card;

import android.animation.LayoutTransition;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.BatteryImageView;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.module.home.HomeActivity;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import seccompat.android.util.Log;

public class CardEarbuds extends Card {
    private static final int COMMON_BATTERY_RANGE = 15;
    private static final int EARBUD_COLOR_BLACK = 298;
    private static final int EARBUD_COLOR_NULL = -1;
    private static final int EARBUD_COLOR_SILVER = 299;
    private static final int EARBUD_COLOR_VIOLET = 300;
    private static final int EARBUD_PLACEMENT_IN_OPEN_CASE = 3;
    private static final String TAG = "Attic_CardEarbuds";
    private EarBudsInfo info;
    private Activity mActivity;
    private int mCurBetweenMarginDimenId = 0;
    private boolean mFirstUpdateUI = false;
    private ItemViewHolder mItemViewHolder;

    public CardEarbuds(Activity activity) {
        super(0);
        this.mActivity = activity;
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void onBindItemViewHolder(Card.ItemViewHolder itemViewHolder) {
        Log.d(TAG, "onBindItemViewHolder() : " + itemViewHolder);
        this.mItemViewHolder = (ItemViewHolder) itemViewHolder;
        setTransitionListener();
        this.mFirstUpdateUI = true;
        updateUI();
        this.mFirstUpdateUI = false;
    }

    private void setTransitionListener() {
        try {
            ((ViewGroup) this.mItemViewHolder.itemView).getLayoutTransition().addTransitionListener(new LayoutTransition.TransitionListener() {
                /* class com.samsung.accessory.hearablemgr.module.home.card.CardEarbuds.AnonymousClass1 */
                private int mPlayingTransitionCount = 0;

                public void startTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
                    HomeActivity homeActivity = (HomeActivity) CardEarbuds.this.mActivity;
                    boolean z = true;
                    int i2 = this.mPlayingTransitionCount + 1;
                    this.mPlayingTransitionCount = i2;
                    if (i2 != 0) {
                        z = false;
                    }
                    homeActivity.setScrollEnabled(z);
                }

                public void endTransition(LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {
                    int i2 = this.mPlayingTransitionCount;
                    if (i2 > 0) {
                        this.mPlayingTransitionCount = i2 - 1;
                    }
                    if (this.mPlayingTransitionCount == 0) {
                        ((HomeActivity) CardEarbuds.this.mActivity).setScrollEnabled(true);
                    }
                    RecyclerView recyclerView = ((HomeActivity) CardEarbuds.this.mActivity).getRecyclerView();
                    if (recyclerView != null) {
                        recyclerView.getClass();
                        recyclerView.post(new Runnable() {
                            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardEarbuds$1$ZFSMwQnUGMmXaAvguOtF8f0QFQQ */

                            public final void run() {
                                RecyclerView.this.requestLayout();
                            }
                        });
                    }
                }
            });
        } catch (Throwable th) {
            th.printStackTrace();
            Log.w(TAG, "setTransitionListener() : Exception : " + th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x03bd  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x03c1  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x03ce  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0406  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x040b  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0424  */
    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateUI() {
        /*
        // Method dump skipped, instructions count: 1242
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.module.home.card.CardEarbuds.updateUI():void");
    }

    private void setEarbudsBetweenMargin(int i) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mItemViewHolder.viewMarginBetween.getLayoutParams();
        layoutParams.width = (int) this.mActivity.getResources().getDimension(i);
        this.mItemViewHolder.viewMarginBetween.setLayoutParams(layoutParams);
        this.mCurBetweenMarginDimenId = i;
    }

    private void toggleViewMarginBetweenChildVisibility() {
        this.mItemViewHolder.viewMarginBetweenChild.setVisibility(this.mItemViewHolder.viewMarginBetweenChild.getVisibility() == 0 ? 8 : 0);
    }

    private Integer getCommonBatteryValue(EarBudsInfo earBudsInfo) {
        if (earBudsInfo.batteryI == -1) {
            return null;
        }
        return Integer.valueOf(earBudsInfo.batteryI);
    }

    private void setDeviceImage() {
        int earBudsColorCode = getEarBudsColorCode();
        setDeviceImages(Integer.valueOf(earBudsColorCode), this.mItemViewHolder.imageGearLeft, this.mItemViewHolder.imageGearRight, this.mItemViewHolder.imageCradle);
        setActivityBgGradationColor(earBudsColorCode);
    }

    public static int getEarBudsColorCode() {
        CoreService coreService = Application.getCoreService();
        int i = Preferences.getInt(PreferenceKey.LAST_DEVICE_COLOR, -1);
        if (coreService.isConnected() && coreService.isExtendedStatusReady()) {
            i = coreService.getEarBudsInfo().deviceColor;
            Preferences.putInt(PreferenceKey.LAST_DEVICE_COLOR, Integer.valueOf(i));
        }
        Log.d(TAG, "getEarBudsColorCode() : " + i);
        return i;
    }

    public static void setDeviceImages(Integer num, ImageView imageView, ImageView imageView2, ImageView imageView3) {
        Log.d(TAG, "setDeviceImages() : " + num);
        if (num == null) {
            num = Integer.valueOf(getEarBudsColorCode());
        }
        Util.isJapanModel();
        int intValue = num.intValue();
        if (intValue == EARBUD_COLOR_BLACK) {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.gw_buds_kv_left_black);
            }
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.gw_buds_kv_right_black);
            }
            if (imageView3 != null) {
                imageView3.setImageResource(R.drawable.gw_buds_kv_cradle_black);
            }
        } else if (intValue != EARBUD_COLOR_SILVER) {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.gw_buds_kv_left_violet);
            }
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.gw_buds_kv_right_violet);
            }
            if (imageView3 != null) {
                imageView3.setImageResource(R.drawable.gw_buds_kv_cradle_violet);
            }
        } else {
            if (imageView != null) {
                imageView.setImageResource(R.drawable.gw_buds_kv_left_silver);
            }
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.gw_buds_kv_right_silver);
            }
            if (imageView3 != null) {
                imageView3.setImageResource(R.drawable.gw_buds_kv_cradle_silver);
            }
        }
    }

    private void setActivityBgGradationColor(int i) {
        ((Card.CardOwnerActivity) this.mActivity).setBgGradationColor(Application.getContext().getResources().getColor(R.color.home_gradation_default));
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_earbuds, viewGroup, false));
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        View animateLayoutCradleBattery;
        BatteryImageView batteryCommon;
        BatteryImageView batteryCradle;
        BatteryImageView batteryLeft;
        BatteryImageView batteryRight;
        Button buttonConnect;
        View focusViewCaseBattery;
        View focusViewEarBudsStatus;
        View focusViewRemainingBattery;
        View frameImageCradle;
        View frameImageGearLeft;
        View frameImageGearRight;
        ImageView imageCradle;
        ImageView imageGearLeft;
        ImageView imageGearRight;
        LinearLayout layoutDisconnect;
        TextView textBatteryCommon;
        TextView textBatteryCradle;
        TextView textBatteryLeft;
        TextView textBatteryRight;
        TextView textDisconnect;
        View viewMarginBetween;
        View viewMarginBetweenChild;

        ItemViewHolder(View view) {
            super(view);
            this.textBatteryLeft = (TextView) view.findViewById(R.id.text_left_battery);
            this.textBatteryRight = (TextView) view.findViewById(R.id.text_right_battery);
            this.textBatteryCommon = (TextView) view.findViewById(R.id.text_common_battery);
            this.textBatteryCradle = (TextView) view.findViewById(R.id.text_cradle_battery);
            this.frameImageGearLeft = view.findViewById(R.id.frame_image_bud_left);
            this.imageGearLeft = (ImageView) view.findViewById(R.id.image_bud_left);
            this.viewMarginBetween = view.findViewById(R.id.view_margin_between);
            this.viewMarginBetweenChild = view.findViewById(R.id.view_margin_between_child);
            this.frameImageGearRight = view.findViewById(R.id.frame_image_bud_right);
            this.imageGearRight = (ImageView) view.findViewById(R.id.image_bud_right);
            this.frameImageCradle = view.findViewById(R.id.frame_image_cradle);
            this.imageCradle = (ImageView) view.findViewById(R.id.image_cradle);
            this.batteryLeft = (BatteryImageView) view.findViewById(R.id.image_left_battery);
            this.batteryRight = (BatteryImageView) view.findViewById(R.id.image_right_battery);
            this.batteryCommon = (BatteryImageView) view.findViewById(R.id.image_common_battery);
            this.batteryCradle = (BatteryImageView) view.findViewById(R.id.image_cradle_battery);
            this.textDisconnect = (TextView) view.findViewById(R.id.text_disconnect);
            this.buttonConnect = (Button) view.findViewById(R.id.button_connect);
            this.layoutDisconnect = (LinearLayout) view.findViewById(R.id.disconnect_layout);
            this.focusViewRemainingBattery = view.findViewById(R.id.focus_view_remaining_battery);
            this.focusViewCaseBattery = view.findViewById(R.id.focus_view_case_battery);
            this.focusViewEarBudsStatus = view.findViewById(R.id.focus_view_earbuds_status);
            this.animateLayoutCradleBattery = view.findViewById(R.id.animate_layout_cradle_battery);
            ((ViewGroup) view).setLayoutTransition(new BaseLayoutTransition());
            ((ViewGroup) view.findViewById(R.id.animate_layout_disconnect)).setLayoutTransition(new BaseLayoutTransition());
            ((ViewGroup) view.findViewById(R.id.animate_layout_left_battery)).setLayoutTransition(new BaseLayoutTransition());
            ((ViewGroup) view.findViewById(R.id.animate_layout_right_battery)).setLayoutTransition(new BaseLayoutTransition());
            ((ViewGroup) view.findViewById(R.id.animate_layout_common_battery)).setLayoutTransition(new BaseLayoutTransition());
            ((ViewGroup) view.findViewById(R.id.animate_layout_cradle_battery)).setLayoutTransition(new BaseLayoutTransition());
        }
    }

    static Interpolator SineOut60Interpolator() {
        return PathInterpolatorCompat.create(0.17f, 0.17f, 0.4f, 1.0f);
    }

    static Interpolator SineInOut70Interpolator() {
        return PathInterpolatorCompat.create(0.33f, 0.0f, 0.3f, 1.0f);
    }

    static class DelayLinearInterpolator implements TimeInterpolator {
        private float mDelay;

        DelayLinearInterpolator(float f) {
            this.mDelay = f;
        }

        public float getInterpolation(float f) {
            float f2 = this.mDelay;
            float f3 = (f - f2) / (1.0f - f2);
            if (f3 > 0.0f) {
                return f3;
            }
            return 0.0f;
        }
    }

    private static class BaseLayoutTransition extends TjLayoutTransition {
        BaseLayoutTransition() {
            setDuration(0, 600);
            setInterpolator(0, CardEarbuds.SineInOut70Interpolator());
            setDuration(1, 600);
            setInterpolator(1, CardEarbuds.SineInOut70Interpolator());
            setDuration(2, 600);
            setInterpolator(2, new DelayLinearInterpolator(0.5f));
            setDuration(3, 300);
            setInterpolator(3, new LinearInterpolator());
        }
    }

    static class TjLayoutTransition extends LayoutTransition {
        private static final boolean ENABLE_ANIMATION_LOG = false;
        private static final String[] TRANSITION_TYPE_NAME = {"CHANGE_APPEARING", "CHANGE_DISAPPEARING", "APPEARING", "DISAPPEARING", "CHANGING"};

        TjLayoutTransition() {
            for (int i = 0; i <= 4; i++) {
                setAnimator(i, getAnimator(i).clone());
            }
        }
    }
}
