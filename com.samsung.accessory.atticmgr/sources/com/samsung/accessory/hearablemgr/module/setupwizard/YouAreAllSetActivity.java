package com.samsung.accessory.hearablemgr.module.setupwizard;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.penfeizhou.animation.loader.AssetStreamLoader;
import com.github.penfeizhou.animation.webp.WebPDrawable;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.fotaprovider.FotaProviderEventHandler;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.ui.Interpolators;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.core.notification.NotificationUtil;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import com.samsung.android.fotaagent.update.UpdateInterface;
import seccompat.android.util.Log;

public class YouAreAllSetActivity extends PermissionCheckActivity {
    private static final String TAG = "Attic_YouAreAllSetActivity";
    private Handler mHandler = new Handler();
    private ImageView mImageSuccessAnimation;
    private ImageView mImageSuccessDotEffect;
    private TextView mTextTitle;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate()");
        super.onCreate(bundle);
        setContentView(R.layout.activity_you_are_all_set);
        this.mImageSuccessDotEffect = (ImageView) findViewById(R.id.image_success_vi_dot_effect);
        this.mImageSuccessAnimation = (ImageView) findViewById(R.id.image_success_vi_animation);
        this.mTextTitle = (TextView) findViewById(R.id.text_title);
        this.mHandler.postDelayed(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.YouAreAllSetActivity.AnonymousClass1 */

            public void run() {
                YouAreAllSetActivity.this.startVIAnimation();
            }
        }, 800);
        new Handler().post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.YouAreAllSetActivity.AnonymousClass2 */

            public void run() {
                NotificationUtil.initSettingDefaultApps();
            }
        });
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startVIAnimation() {
        this.mImageSuccessAnimation.setImageDrawable(new WebPDrawable(new AssetStreamLoader(this, "success_vi.webp")));
        AnimationSet animationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, UiUtil.DP_TO_PX(20.0f), 0.0f);
        translateAnimation.setStartOffset(1500);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(Interpolators.SineOut60Interpolator());
        translateAnimation.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setStartOffset(1500);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.YouAreAllSetActivity.AnonymousClass3 */

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                YouAreAllSetActivity.this.mTextTitle.setVisibility(0);
            }
        });
        animationSet.addAnimation(alphaAnimation);
        this.mTextTitle.startAnimation(animationSet);
        AnimationSet animationSet2 = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setStartOffset(1800);
        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(Interpolators.SineOut60Interpolator());
        scaleAnimation.setFillAfter(true);
        animationSet2.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation2.setStartOffset(1800);
        alphaAnimation2.setDuration(200);
        alphaAnimation2.setInterpolator(new LinearInterpolator());
        alphaAnimation2.setFillAfter(true);
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.YouAreAllSetActivity.AnonymousClass4 */

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                YouAreAllSetActivity.this.mImageSuccessDotEffect.setVisibility(0);
            }
        });
        animationSet2.addAnimation(alphaAnimation2);
        AlphaAnimation alphaAnimation3 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation3.setStartOffset(2300);
        alphaAnimation3.setDuration(200);
        alphaAnimation3.setInterpolator(new LinearInterpolator());
        alphaAnimation3.setFillAfter(true);
        alphaAnimation3.setAnimationListener(new Animation.AnimationListener() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.YouAreAllSetActivity.AnonymousClass5 */

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                YouAreAllSetActivity.this.mImageSuccessDotEffect.setVisibility(4);
            }
        });
        animationSet2.addAnimation(alphaAnimation3);
        this.mImageSuccessDotEffect.startAnimation(animationSet2);
        this.mHandler.postDelayed(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.module.setupwizard.YouAreAllSetActivity.AnonymousClass6 */

            public void run() {
                YouAreAllSetActivity.this.finishSetupWizard();
            }
        }, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void finishSetupWizard() {
        Log.d(TAG, "finishSetupWizard()");
        this.mHandler.removeCallbacksAndMessages(null);
        Preferences.putBoolean(PreferenceKey.SETUP_WIZARD_DONE, true);
        Application.getAomManager().setAomEnable(true);
        Log.d(TAG, "send setupwizard complete broadcast");
        FotaProviderEventHandler.setupWizardCompleted(Application.getContext());
        setResult(-1);
        finish();
    }
}
