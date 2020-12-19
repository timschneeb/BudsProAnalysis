package com.samsung.accessory.hearablemgr.module.home.card;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.SingleToast;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgAmbientSoundLevel;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgNoiseReductionLevel;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.accessory.hearablemgr.module.home.card.Card;
import com.samsung.accessory.hearablemgr.module.mainmenu.DetectConversationsActivity;
import com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar;
import com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlUtil;
import seccompat.android.util.Log;

public class CardNoiseControls extends Card {
    public static final int NOISE_CONTROL_STATE_AMBIENT_SOUND = 2;
    public static final int NOISE_CONTROL_STATE_NOISE_REDUCTION = 1;
    public static final int NOISE_CONTROL_STATE_OFF = 0;
    public static final int NOISE_REDUCTION_LEVEL_FULL = 1;
    public static final int NOISE_REDUCTION_LEVEL_MODERATE = 0;
    private static final String TAG = (Application.TAG_ + CardNoiseControls.class.getSimpleName());
    private KeyPath activateColorKeyPath = new KeyPath("selected_circle", "**");
    private SimpleLottieValueCallback<ColorFilter> colorDisabled = $$Lambda$CardNoiseControls$P5AehjQK_NrMkxYbnZs0JuyjDdM.INSTANCE;
    private SimpleLottieValueCallback<ColorFilter> colorOffBackground = $$Lambda$CardNoiseControls$AaDdUTCLudamhFRWNFQhj73TdGg.INSTANCE;
    private SimpleLottieValueCallback<ColorFilter> colorPrimary = $$Lambda$CardNoiseControls$1pZO3aF9ZZUpXpRnYCuNZoyvMdk.INSTANCE;
    private boolean isInitialized;
    private final Activity mActivity;
    private ItemViewHolder mItemViewHolder;

    static /* synthetic */ ColorFilter lambda$new$0(LottieFrameInfo lottieFrameInfo) {
        return new PorterDuffColorFilter(ContextCompat.getColor(Application.getContext(), R.color.color_noise_controls_icon_background_disabled), PorterDuff.Mode.SRC_ATOP);
    }

    static /* synthetic */ ColorFilter lambda$new$1(LottieFrameInfo lottieFrameInfo) {
        return new PorterDuffColorFilter(ContextCompat.getColor(Application.getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
    }

    static /* synthetic */ ColorFilter lambda$new$2(LottieFrameInfo lottieFrameInfo) {
        return new PorterDuffColorFilter(ContextCompat.getColor(Application.getContext(), R.color.color_noise_controls_icon_off_background), PorterDuff.Mode.SRC_ATOP);
    }

    public CardNoiseControls(Activity activity) {
        super(6);
        Log.d(TAG, "CardNoiseControls()");
        this.mActivity = activity;
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void onBindItemViewHolder(Card.ItemViewHolder itemViewHolder) {
        this.mItemViewHolder = (ItemViewHolder) itemViewHolder;
        Log.d(TAG, "CardNoiseControls onBindItemViewHolder");
        this.isInitialized = false;
        updateUI();
    }

    @Override // com.samsung.accessory.hearablemgr.module.home.card.Card
    public void updateUI() {
        Log.d(TAG, "updateUI()");
        if (this.mItemViewHolder != null) {
            CoreService coreService = Application.getCoreService();
            UiUtil.setEnabledWithChildren(this.mItemViewHolder.itemView, coreService.isConnected());
            unregisterListener();
            registerNoiseControlSeekBarListener();
            init();
            if (coreService.isConnected()) {
                registerListener();
                setConnectedCard();
                return;
            }
            setDisconnectedCard();
        }
    }

    private void init() {
        initNoiseControlView();
        initNoiseLevelView();
        initDetectConversationsView();
    }

    private void initNoiseControlView() {
        int i = Application.getCoreService().getEarBudsInfo().noiseControls;
        if (!this.isInitialized) {
            String str = TAG;
            Log.d(str, "initNoiseControlView() isInitialized state : " + i);
            clearTextColor();
            clearActivateAnimation();
            clearDeactivateAnimation();
            setActivateStateNoAnimation(i);
            this.mItemViewHolder.noiseControlSeekBar.setProgress(stateToIndex(i));
            this.isInitialized = true;
            this.mItemViewHolder.noiseControlSeekBar.setEnabledSeekBar(true);
        } else {
            this.mItemViewHolder.noiseControlSeekBar.setProgressWithAnimation(stateToIndex(i));
        }
        setState(i);
    }

    private void initNoiseLevelView() {
        initNoiseReductionLevelView(Application.getCoreService().getEarBudsInfo().noiseReductionLevel);
        initAmbientSoundLevelView();
    }

    public void initNoiseReductionLevelView(int i) {
        if (i == 0) {
            this.mItemViewHolder.noiseReductionFullRadioButton.setChecked(false);
            this.mItemViewHolder.noiseReductionModerateRadioButton.setChecked(true);
            return;
        }
        this.mItemViewHolder.noiseReductionFullRadioButton.setChecked(true);
        this.mItemViewHolder.noiseReductionModerateRadioButton.setChecked(false);
    }

    private void initAmbientSoundLevelView() {
        this.mItemViewHolder.ambientSoundLevelSeekBar.setProgress(Application.getCoreService().getEarBudsInfo().ambientSoundLevel);
        this.mItemViewHolder.ambientSoundLevel.setText(levelToText(2, Application.getCoreService().getEarBudsInfo().ambientSoundLevel));
    }

    private void initDetectConversationsView() {
        this.mItemViewHolder.detectConversationsSwitch.setChecked(Application.getCoreService().getEarBudsInfo().detectConversations);
    }

    private void setActivateStateNoAnimation(int i) {
        if (i == 1) {
            this.mItemViewHolder.activateNoiseReduction.setVisibility(0);
            this.mItemViewHolder.activateNoiseReduction.setProgress(1.0f);
            this.mItemViewHolder.noiseControlNoiseReductionText.setTextColor(ContextCompat.getColorStateList(Application.getContext(), R.color.selector_home_primary_color));
        } else if (i != 2) {
            this.mItemViewHolder.activateOffLeft.setVisibility(0);
            this.mItemViewHolder.activateOffLeft.setProgress(1.0f);
            this.mItemViewHolder.noiseControlOffText.setTextColor(ContextCompat.getColorStateList(Application.getContext(), R.color.selector_home_primary_color));
        } else {
            this.mItemViewHolder.activateAmbientSound.setVisibility(0);
            this.mItemViewHolder.activateAmbientSound.setProgress(1.0f);
            this.mItemViewHolder.noiseControlAmbientSoundText.setTextColor(ContextCompat.getColorStateList(Application.getContext(), R.color.selector_home_primary_color));
        }
    }

    private void setState(int i) {
        if (i == 1) {
            setNoiseReductionView();
        } else if (i != 2) {
            setOffView();
        } else {
            setAmbientSoundView();
        }
    }

    private void setOffView() {
        this.mItemViewHolder.noiseReductionLevelContainer.setVisibility(8);
        this.mItemViewHolder.ambientSoundLevelContainer.setVisibility(8);
    }

    private void setNoiseReductionView() {
        this.mItemViewHolder.noiseReductionLevelContainer.setVisibility(0);
        this.mItemViewHolder.ambientSoundLevelContainer.setVisibility(8);
    }

    private void setAmbientSoundView() {
        this.mItemViewHolder.noiseReductionLevelContainer.setVisibility(8);
        this.mItemViewHolder.ambientSoundLevelContainer.setVisibility(0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void playActivateAnimation(int i, int i2) {
        String str = TAG;
        Log.d(str, "playActivateAnimation() startIndex : " + i + ", endIndex : " + i2);
        if (i2 == 0) {
            if (i == 1) {
                this.mItemViewHolder.deactivateOffLeft.setVisibility(4);
                this.mItemViewHolder.deactivateOffRight.setVisibility(4);
                this.mItemViewHolder.activateOffLeft.setVisibility(0);
                this.mItemViewHolder.activateOffLeft.playAnimation();
            } else if (i == 2) {
                this.mItemViewHolder.deactivateOffLeft.setVisibility(4);
                this.mItemViewHolder.deactivateOffRight.setVisibility(4);
                this.mItemViewHolder.activateOffRight.setVisibility(0);
                this.mItemViewHolder.activateOffRight.playAnimation();
            }
            this.mItemViewHolder.noiseControlOffText.setTextColor(ContextCompat.getColorStateList(Application.getContext(), R.color.selector_home_primary_color));
        } else if (i2 == 1) {
            this.mItemViewHolder.deactivateNoiseReduction.setVisibility(4);
            this.mItemViewHolder.activateNoiseReduction.setVisibility(0);
            this.mItemViewHolder.activateNoiseReduction.playAnimation();
            this.mItemViewHolder.noiseControlNoiseReductionText.setTextColor(ContextCompat.getColorStateList(Application.getContext(), R.color.selector_home_primary_color));
        } else if (i2 == 2) {
            this.mItemViewHolder.deactivateAmbientSound.setVisibility(4);
            this.mItemViewHolder.activateAmbientSound.setVisibility(0);
            this.mItemViewHolder.activateAmbientSound.playAnimation();
            this.mItemViewHolder.noiseControlAmbientSoundText.setTextColor(ContextCompat.getColorStateList(Application.getContext(), R.color.selector_home_primary_color));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearActivateAnimation() {
        this.mItemViewHolder.activateOffLeft.setVisibility(4);
        this.mItemViewHolder.activateOffRight.setVisibility(4);
        this.mItemViewHolder.activateNoiseReduction.setVisibility(4);
        this.mItemViewHolder.activateAmbientSound.setVisibility(4);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void playDeactivateAnimation(int i, int i2) {
        String str = TAG;
        Log.d(str, "playDeactivateAnimation() startIndex : " + i + ", endIndex : " + i2);
        if (i != 0) {
            if (i == 1) {
                this.mItemViewHolder.deactivateNoiseReduction.setVisibility(0);
                this.mItemViewHolder.deactivateNoiseReduction.playAnimation();
            } else if (i == 2) {
                this.mItemViewHolder.deactivateAmbientSound.setVisibility(0);
                this.mItemViewHolder.deactivateAmbientSound.playAnimation();
            }
        } else if (i2 == 1) {
            this.mItemViewHolder.deactivateOffRight.setVisibility(4);
            this.mItemViewHolder.deactivateOffLeft.setVisibility(0);
            this.mItemViewHolder.deactivateOffLeft.playAnimation();
        } else if (i2 == 2) {
            this.mItemViewHolder.deactivateOffLeft.setVisibility(4);
            this.mItemViewHolder.deactivateOffRight.setVisibility(0);
            this.mItemViewHolder.deactivateOffRight.playAnimation();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearDeactivateAnimation() {
        this.mItemViewHolder.deactivateOffLeft.setVisibility(0);
        this.mItemViewHolder.deactivateOffRight.setVisibility(4);
        this.mItemViewHolder.deactivateNoiseReduction.setVisibility(0);
        this.mItemViewHolder.deactivateAmbientSound.setVisibility(0);
        this.mItemViewHolder.deactivateOffLeft.setProgress(1.0f);
        this.mItemViewHolder.deactivateOffRight.setProgress(1.0f);
        this.mItemViewHolder.deactivateNoiseReduction.setProgress(1.0f);
        this.mItemViewHolder.deactivateAmbientSound.setProgress(1.0f);
        setAnimationDeactivateOff(1.0f, 1.0f, 1.0f, 1.0f, 0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearTextColor() {
        ColorStateList colorStateList = ContextCompat.getColorStateList(Application.getContext(), R.color.selector_card_title_color);
        this.mItemViewHolder.noiseControlOffText.setTextColor(colorStateList);
        this.mItemViewHolder.noiseControlNoiseReductionText.setTextColor(colorStateList);
        this.mItemViewHolder.noiseControlAmbientSoundText.setTextColor(colorStateList);
    }

    private void registerNoiseControlSeekBarListener() {
        this.mItemViewHolder.noiseControlSeekBar.setOnNoiseControlChangeListener(new NoiseControlSeekBar.NoiseControlChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$NIup18lBPthoRrT6IbQrmLHUyUM */

            @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlChangeListener
            public final void onNoiseControlChanged(int i) {
                CardNoiseControls.this.lambda$registerNoiseControlSeekBarListener$3$CardNoiseControls(i);
            }
        });
        this.mItemViewHolder.noiseControlSeekBar.setOnNoiseControlMoveLineListener(new NoiseControlSeekBar.NoiseControlMoveLineListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls.AnonymousClass1 */

            @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
            public void onMoveStart(int i, int i2) {
                int indexToState = CardNoiseControls.this.indexToState(i);
                int indexToState2 = CardNoiseControls.this.indexToState(i2);
                String str = CardNoiseControls.TAG;
                Log.d(str, "onMoveStart() prevState : " + indexToState + ", nextState : " + indexToState2);
                CardNoiseControls.this.clearTextColor();
                CardNoiseControls.this.clearActivateAnimation();
                CardNoiseControls.this.clearDeactivateAnimation();
                CardNoiseControls.this.playDeactivateAnimation(indexToState, indexToState2);
                CardNoiseControls.this.mItemViewHolder.noiseControlSeekBar.setEnabledSeekBar(false);
            }

            @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
            public void onArrivedLineToTarget(int i, int i2) {
                CardNoiseControls.this.playActivateAnimation(CardNoiseControls.this.indexToState(i), CardNoiseControls.this.indexToState(i2));
            }

            @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
            public void onArrivedLineToCenter(int i, int i2) {
                if (CardNoiseControls.this.indexToState(i) != 0 && CardNoiseControls.this.indexToState(i2) != 0) {
                    CardNoiseControls.this.setAnimationDeactivateOff(1.0f, 0.5f, 1.0f, 0.9f, 100);
                }
            }

            @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.NoiseControlMoveLineListener
            public void onEscapedLineFromCenter(int i, int i2) {
                if (CardNoiseControls.this.indexToState(i) != 0 && CardNoiseControls.this.indexToState(i2) != 0) {
                    CardNoiseControls.this.setAnimationDeactivateOff(0.5f, 1.0f, 0.9f, 1.0f, 100);
                }
            }
        });
        this.mItemViewHolder.noiseControlSeekBar.setOnClickDisablePositionListener(new NoiseControlSeekBar.OnClickDisablePositionListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$UiecIGZDN9Qd_KWP3vYNXK3huBY */

            @Override // com.samsung.accessory.hearablemgr.module.noisecontrols.NoiseControlSeekBar.OnClickDisablePositionListener
            public final void onClick(int i) {
                CardNoiseControls.this.lambda$registerNoiseControlSeekBarListener$4$CardNoiseControls(i);
            }
        });
    }

    public /* synthetic */ void lambda$registerNoiseControlSeekBarListener$3$CardNoiseControls(int i) {
        int indexToState = indexToState(i);
        String str = TAG;
        Log.d(str, "onNoiseControlChanged() state : " + indexToState);
        sendNoiseControlsSppMessage(indexToState);
        setState(indexToState);
        sendSamsungAnalyticEvent(indexToState);
    }

    public /* synthetic */ void lambda$registerNoiseControlSeekBarListener$4$CardNoiseControls(int i) {
        int indexToState = indexToState(i);
        if (indexToState == 1) {
            SingleToast.show(Application.getContext(), Application.getContext().getString(R.string.settings_anc_toast_both_wearing), 0);
        } else if (indexToState == 2) {
            SingleToast.show(Application.getContext(), Application.getContext().getString(R.string.put_in_at_least_one_earbud), 0);
        }
        sendSamsungAnalyticEvent(indexToState);
    }

    private void registerListener() {
        this.mItemViewHolder.noiseControlNoiseReductionText.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$FVSU3tbOwVAYq6u1vXZ2Dc9_KA */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$5$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.noiseControlOffText.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$6Zad1emO3GD9G059wqwo80MyP8 */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$6$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.noiseControlAmbientSoundText.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$kHS8S697uX5U9AXCtPEQdoVFE */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$7$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.activateOffLeft.addAnimatorListener(new Animator.AnimatorListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                Log.d(CardNoiseControls.TAG, "onAnimationEnd() : activateOffLeft");
                CardNoiseControls.this.mItemViewHolder.noiseControlSeekBar.setEnabledSeekBar(true);
            }
        });
        this.mItemViewHolder.activateOffRight.addAnimatorListener(new Animator.AnimatorListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls.AnonymousClass3 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                Log.d(CardNoiseControls.TAG, "onAnimationEnd() : activateOffRight");
                CardNoiseControls.this.mItemViewHolder.noiseControlSeekBar.setEnabledSeekBar(true);
            }
        });
        this.mItemViewHolder.activateNoiseReduction.addAnimatorListener(new Animator.AnimatorListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                Log.d(CardNoiseControls.TAG, "onAnimationEnd() : activateNoiseReduction");
                CardNoiseControls.this.mItemViewHolder.noiseControlSeekBar.setEnabledSeekBar(true);
            }
        });
        this.mItemViewHolder.activateAmbientSound.addAnimatorListener(new Animator.AnimatorListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls.AnonymousClass5 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                Log.d(CardNoiseControls.TAG, "onAnimationEnd() : activateAmbientSound");
                CardNoiseControls.this.mItemViewHolder.noiseControlSeekBar.setEnabledSeekBar(true);
            }
        });
        this.mItemViewHolder.noiseReductionFullRadioButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$qBFM76oSzas8dEYDL40M1DFfUJ8 */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$8$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.noiseReductionModerateRadioButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$UzSCHaRFr_8WGFOp0kWsRPkAsks */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$9$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.noiseReductionFullContainer.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$1RYG_xF2GLdQTm5J3G3vfLCwgU4 */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$10$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.noiseReductionModerateContainer.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$mzeQixcziK1afUAtqA1Nco8pVOw */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$11$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.ambientSoundLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.CardNoiseControls.AnonymousClass6 */

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                CardNoiseControls.this.mItemViewHolder.ambientSoundLevel.setText(CardNoiseControls.this.levelToText(2, i));
                Application.getCoreService().getEarBudsInfo().ambientSoundLevel = i;
                Application.getCoreService().sendSppMessage(new MsgAmbientSoundLevel((byte) i));
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.AMBIENT_SOUND_LEVEL, SA.Screen.HOME);
                SamsungAnalyticsUtil.setStatusInt(SA.Status.AMBIENT_SOUND_VOLUME_STATUS, Application.getCoreService().getEarBudsInfo().ambientSoundLevel);
            }
        });
        this.mItemViewHolder.ambientSoundPlusImage.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$7_z6VkGlTPHLYS8bMPD4GI7kXWs */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$12$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.ambientSoundMinusImage.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$2fD1wjr7p6MRuuWfb_H5RaoyPg */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$13$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.detectConversationsLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$Wa_GuGt9dMDhTPvB7C74ms1gkjc */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$14$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.detectConversationsSwitchLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.home.card.$$Lambda$CardNoiseControls$y4rOtT6mQYzCzAAzK9DOGZUm1g */

            public final void onClick(View view) {
                CardNoiseControls.this.lambda$registerListener$15$CardNoiseControls(view);
            }
        });
        this.mItemViewHolder.detectConversationsSwitch.setOnCheckedChangeListener($$Lambda$CardNoiseControls$FJP2sOv0AAxVmtdCbzWCNIhStYU.INSTANCE);
    }

    public /* synthetic */ void lambda$registerListener$5$CardNoiseControls(View view) {
        if (this.mItemViewHolder.noiseControlSeekBar.isEnabledSeekBar()) {
            if (!NoiseControlUtil.isBothWearing()) {
                SingleToast.show(Application.getContext(), Application.getContext().getString(R.string.settings_anc_toast_both_wearing), 0);
                return;
            }
            this.mItemViewHolder.noiseControlSeekBar.setProgressWithAnimation(stateToIndex(1));
            sendNoiseControlsSppMessage(1);
            setState(1);
            sendSamsungAnalyticEvent(1);
        }
    }

    public /* synthetic */ void lambda$registerListener$6$CardNoiseControls(View view) {
        if (this.mItemViewHolder.noiseControlSeekBar.isEnabledSeekBar()) {
            this.mItemViewHolder.noiseControlSeekBar.setProgressWithAnimation(stateToIndex(0));
            sendNoiseControlsSppMessage(0);
            setState(0);
            sendSamsungAnalyticEvent(0);
        }
    }

    public /* synthetic */ void lambda$registerListener$7$CardNoiseControls(View view) {
        if (this.mItemViewHolder.noiseControlSeekBar.isEnabledSeekBar()) {
            if (!NoiseControlUtil.isWearing()) {
                SingleToast.show(Application.getContext(), Application.getContext().getString(R.string.put_in_at_least_one_earbud), 0);
                return;
            }
            this.mItemViewHolder.noiseControlSeekBar.setProgressWithAnimation(stateToIndex(2));
            sendNoiseControlsSppMessage(2);
            setState(2);
            sendSamsungAnalyticEvent(2);
        }
    }

    public /* synthetic */ void lambda$registerListener$8$CardNoiseControls(View view) {
        initNoiseReductionLevelView(1);
        if (Application.getCoreService().getEarBudsInfo().noiseReductionLevel != 1) {
            Application.getCoreService().getEarBudsInfo().noiseReductionLevel = 1;
            Application.getCoreService().sendSppMessage(new MsgNoiseReductionLevel((byte) 1));
            SamsungAnalyticsUtil.setStatusInt(SA.Status.ACTIVE_NOISE_CANCELLING_LEVEL_STATUS, 1);
        }
        SamsungAnalyticsUtil.sendEvent(SA.Event.ACTIVE_NOISE_CANCELLING_LEVEL, SA.Screen.HOME);
    }

    public /* synthetic */ void lambda$registerListener$9$CardNoiseControls(View view) {
        initNoiseReductionLevelView(0);
        if (Application.getCoreService().getEarBudsInfo().noiseReductionLevel != 0) {
            Application.getCoreService().getEarBudsInfo().noiseReductionLevel = 0;
            Application.getCoreService().sendSppMessage(new MsgNoiseReductionLevel((byte) 0));
            SamsungAnalyticsUtil.setStatusInt(SA.Status.ACTIVE_NOISE_CANCELLING_LEVEL_STATUS, 0);
        }
        SamsungAnalyticsUtil.sendEvent(SA.Event.ACTIVE_NOISE_CANCELLING_LEVEL, SA.Screen.HOME);
    }

    public /* synthetic */ void lambda$registerListener$10$CardNoiseControls(View view) {
        if (Util.isCalling()) {
            SingleToast.show(Application.getContext(), Application.getContext().getString(R.string.cant_change_settings_during_a_call), 0);
            SamsungAnalyticsUtil.sendEvent(SA.Event.ACTIVE_NOISE_CANCELLING_LEVEL, SA.Screen.HOME);
            return;
        }
        this.mItemViewHolder.noiseReductionFullRadioButton.performClick();
    }

    public /* synthetic */ void lambda$registerListener$11$CardNoiseControls(View view) {
        if (Util.isCalling()) {
            SingleToast.show(Application.getContext(), Application.getContext().getString(R.string.cant_change_settings_during_a_call), 0);
            SamsungAnalyticsUtil.sendEvent(SA.Event.ACTIVE_NOISE_CANCELLING_LEVEL, SA.Screen.HOME);
            return;
        }
        this.mItemViewHolder.noiseReductionModerateRadioButton.performClick();
    }

    public /* synthetic */ void lambda$registerListener$12$CardNoiseControls(View view) {
        this.mItemViewHolder.ambientSoundLevelSeekBar.setProgress(Math.min(this.mItemViewHolder.ambientSoundLevelSeekBar.getMax(), this.mItemViewHolder.ambientSoundLevelSeekBar.getProgress() + 1));
        SamsungAnalyticsUtil.sendEvent(SA.Event.AMBIENT_SOUND_LEVEL, SA.Screen.HOME);
    }

    public /* synthetic */ void lambda$registerListener$13$CardNoiseControls(View view) {
        this.mItemViewHolder.ambientSoundLevelSeekBar.setProgress(Math.max(0, this.mItemViewHolder.ambientSoundLevelSeekBar.getProgress() - 1));
        SamsungAnalyticsUtil.sendEvent(SA.Event.AMBIENT_SOUND_LEVEL, SA.Screen.HOME);
    }

    public /* synthetic */ void lambda$registerListener$14$CardNoiseControls(View view) {
        Activity activity = this.mActivity;
        activity.startActivity(new Intent(activity, DetectConversationsActivity.class));
    }

    public /* synthetic */ void lambda$registerListener$15$CardNoiseControls(View view) {
        this.mItemViewHolder.detectConversationsSwitch.performClick();
    }

    static /* synthetic */ void lambda$registerListener$16(CompoundButton compoundButton, boolean z) {
        Application.getCoreService().getEarBudsInfo().detectConversations = z;
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.SET_DETECT_CONVERSATIONS, Application.getCoreService().getEarBudsInfo().detectConversations ? (byte) 1 : 0));
        SamsungAnalyticsUtil.setStatusInt(SA.Status.VOICE_DETECT_STATUS, z ? 1 : 0);
    }

    private void unregisterListener() {
        this.mItemViewHolder.ambientSoundLevelSeekBar.setOnSeekBarChangeListener(null);
        this.mItemViewHolder.detectConversationsLayout.setOnClickListener(null);
        this.mItemViewHolder.detectConversationsSwitchLayout.setOnClickListener(null);
        this.mItemViewHolder.detectConversationsSwitch.setOnCheckedChangeListener(null);
    }

    private void setConnectedCard() {
        this.mItemViewHolder.activateNoiseReduction.setAlpha(1.0f);
        this.mItemViewHolder.activateOffLeft.setAlpha(1.0f);
        this.mItemViewHolder.activateOffRight.setAlpha(1.0f);
        this.mItemViewHolder.activateAmbientSound.setAlpha(1.0f);
        updateSelectedColor(this.activateColorKeyPath, true);
        setEnabledLevelImage(true);
        updateWearingState();
        if (Util.isCalling()) {
            setCallView();
        }
    }

    private void updateWearingState() {
        boolean z = false;
        boolean z2 = Application.getCoreService().getEarBudsInfo().wearingL && Application.getCoreService().getEarBudsInfo().wearingR;
        if (Application.getCoreService().getEarBudsInfo().wearingL || Application.getCoreService().getEarBudsInfo().wearingR) {
            z = true;
        }
        this.mItemViewHolder.noiseControlSeekBar.setEnabledPosition(stateToIndex(1), z2);
        this.mItemViewHolder.noiseControlSeekBar.setEnabledPosition(stateToIndex(2), z);
    }

    private void setCallView() {
        UiUtil.setEnabledWithChildren(this.mItemViewHolder.ambientSoundLevelContainer, false);
        this.mItemViewHolder.noiseReductionFullRadioButton.setEnabled(false);
        this.mItemViewHolder.noiseReductionModerateRadioButton.setEnabled(false);
        this.mItemViewHolder.ambientSoundLevel.setText(R.string.cant_change_settings_during_a_call);
        setEnabledLevelImage(false);
    }

    private void setDisconnectedCard() {
        this.mItemViewHolder.activateNoiseReduction.setAlpha(0.4f);
        this.mItemViewHolder.activateOffLeft.setAlpha(0.4f);
        this.mItemViewHolder.activateOffRight.setAlpha(0.4f);
        this.mItemViewHolder.activateAmbientSound.setAlpha(0.4f);
        updateSelectedColor(this.activateColorKeyPath, false);
        setEnabledLevelImage(false);
    }

    private void updateSelectedColor(KeyPath keyPath, boolean z) {
        this.mItemViewHolder.activateNoiseReduction.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, z ? this.colorPrimary : this.colorDisabled);
        this.mItemViewHolder.activateAmbientSound.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, z ? this.colorPrimary : this.colorDisabled);
        updateOffColor(keyPath, z);
    }

    private void updateOffColor(KeyPath keyPath, boolean z) {
        this.mItemViewHolder.activateOffLeft.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, z ? this.colorOffBackground : this.colorDisabled);
        this.mItemViewHolder.activateOffRight.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, z ? this.colorOffBackground : this.colorDisabled);
        this.mItemViewHolder.deactivateOffLeft.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, z ? this.colorOffBackground : this.colorDisabled);
        this.mItemViewHolder.deactivateOffRight.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, z ? this.colorOffBackground : this.colorDisabled);
    }

    private void setEnabledLevelImage(boolean z) {
        float f = 1.0f;
        this.mItemViewHolder.ambientSoundMinusImage.setAlpha(z ? 1.0f : 0.4f);
        ImageView imageView = this.mItemViewHolder.ambientSoundPlusImage;
        if (!z) {
            f = 0.4f;
        }
        imageView.setAlpha(f);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setAnimationDeactivateOff(float f, float f2, float f3, float f4, int i) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(new AlphaAnimation(f, f2));
        animationSet.addAnimation(new ScaleAnimation(f3, f4, f3, f4, 1, 0.5f, 1, 0.5f));
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setDuration((long) i);
        animationSet.setFillAfter(true);
        this.mItemViewHolder.deactivateOffLeft.startAnimation(animationSet);
    }

    private void sendNoiseControlsSppMessage(int i) {
        NoiseControlUtil.setNoiseControl(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String levelToText(int i, int i2) {
        return NoiseControlUtil.levelToText(i, i2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int indexToState(int i) {
        return NoiseControlUtil.indexToState(i);
    }

    private int stateToIndex(int i) {
        return NoiseControlUtil.stateToIndex(i);
    }

    static Card.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_noise_control, viewGroup, false));
    }

    private void sendSamsungAnalyticEvent(int i) {
        if (i == 0) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.NOISE_CONTROLS, SA.Screen.HOME, "b");
        } else if (i == 1) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.NOISE_CONTROLS, SA.Screen.HOME, "a");
        } else if (i == 2) {
            SamsungAnalyticsUtil.sendEvent(SA.Event.NOISE_CONTROLS, SA.Screen.HOME, "c");
        }
    }

    /* access modifiers changed from: package-private */
    public static class ItemViewHolder extends Card.ItemViewHolder {
        LottieAnimationView activateAmbientSound;
        LottieAnimationView activateNoiseReduction;
        LottieAnimationView activateOffLeft;
        LottieAnimationView activateOffRight;
        TextView ambientSoundLevel;
        ConstraintLayout ambientSoundLevelContainer;
        AppCompatSeekBar ambientSoundLevelSeekBar;
        ImageView ambientSoundMinusImage;
        ImageView ambientSoundPlusImage;
        LottieAnimationView deactivateAmbientSound;
        LottieAnimationView deactivateNoiseReduction;
        LottieAnimationView deactivateOffLeft;
        LottieAnimationView deactivateOffRight;
        ConstraintLayout detectConversationsLayout;
        SwitchCompat detectConversationsSwitch;
        LinearLayout detectConversationsSwitchLayout;
        TextView noiseControlAmbientSoundText;
        TextView noiseControlNoiseReductionText;
        TextView noiseControlOffText;
        NoiseControlSeekBar noiseControlSeekBar;
        LinearLayout noiseReductionFullContainer;
        RadioButton noiseReductionFullRadioButton;
        ConstraintLayout noiseReductionLevelContainer;
        LinearLayout noiseReductionModerateContainer;
        RadioButton noiseReductionModerateRadioButton;

        ItemViewHolder(View view) {
            super(view);
            this.noiseControlSeekBar = (NoiseControlSeekBar) view.findViewById(R.id.image_noise_control_background);
            this.deactivateNoiseReduction = (LottieAnimationView) view.findViewById(R.id.lottie_noise_reduction_deactivate);
            this.deactivateOffLeft = (LottieAnimationView) view.findViewById(R.id.lottie_off_left_deactivate);
            this.deactivateOffRight = (LottieAnimationView) view.findViewById(R.id.lottie_off_right_deactivate);
            this.deactivateAmbientSound = (LottieAnimationView) view.findViewById(R.id.lottie_ambient_sound_deactivate);
            this.activateNoiseReduction = (LottieAnimationView) view.findViewById(R.id.lottie_noise_reduction_activate);
            this.activateOffLeft = (LottieAnimationView) view.findViewById(R.id.lottie_off_left_activate);
            this.activateOffRight = (LottieAnimationView) view.findViewById(R.id.lottie_off_right_activate);
            this.activateAmbientSound = (LottieAnimationView) view.findViewById(R.id.lottie_ambient_sound_activate);
            this.noiseControlNoiseReductionText = (TextView) view.findViewById(R.id.text_noise_reduction);
            this.noiseControlOffText = (TextView) view.findViewById(R.id.text_noise_control_off);
            this.noiseControlAmbientSoundText = (TextView) view.findViewById(R.id.text_ambient_sound);
            this.noiseReductionLevelContainer = (ConstraintLayout) view.findViewById(R.id.layout_noise_reduction_level);
            this.ambientSoundLevelContainer = (ConstraintLayout) view.findViewById(R.id.layout_ambient_sound_level);
            this.detectConversationsLayout = (ConstraintLayout) view.findViewById(R.id.layout_detect_conversations);
            this.detectConversationsSwitchLayout = (LinearLayout) view.findViewById(R.id.layout_switch_detect_conversations);
            this.detectConversationsSwitch = (SwitchCompat) view.findViewById(R.id.switch_detect_conversations);
            this.noiseReductionFullContainer = (LinearLayout) view.findViewById(R.id.layout_noise_reduction_full);
            this.noiseReductionModerateContainer = (LinearLayout) view.findViewById(R.id.layout_noise_reduction_moderate);
            this.noiseReductionFullRadioButton = (RadioButton) view.findViewById(R.id.radio_noise_reduction_full);
            this.noiseReductionModerateRadioButton = (RadioButton) view.findViewById(R.id.radio_noise_reduction_moderate);
            this.ambientSoundLevelSeekBar = (AppCompatSeekBar) view.findViewById(R.id.seekbar_ambient_sound);
            this.ambientSoundLevel = (TextView) view.findViewById(R.id.text_ambient_sound_level);
            this.ambientSoundPlusImage = (ImageView) view.findViewById(R.id.image_ambient_sound_plus);
            this.ambientSoundMinusImage = (ImageView) view.findViewById(R.id.image_ambient_sound_minus);
        }
    }
}
