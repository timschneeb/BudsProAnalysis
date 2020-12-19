package com.samsung.accessory.hearablemgr.module.mainmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.fmm.utils.RingManager;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import com.samsung.accessory.hearablemgr.module.home.card.CardEarbuds;
import seccompat.android.util.Log;

public class FindMyEarbudsActivity extends PermissionCheckActivity {
    private static final int LEFT_SIDE = 0;
    private static final int RIGHT_SIDE = 1;
    private static final String TAG = (Application.TAG_ + FindMyEarbudsActivity.class.getSimpleName());
    private LottieAnimationView mAnimationView;
    private FrameLayout mButtonLayout;
    private View mButtonMuteLeft;
    private View mButtonMuteRight;
    private ConstraintLayout mDeviceInfo;
    private ImageView mImageFinding;
    private ImageView mImageMuteLeft;
    private ImageView mImageMuteRight;
    private ImageView mLeftEarbud;
    private ConstraintLayout mMuteLayout;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.mainmenu.FindMyEarbudsActivity.AnonymousClass3 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(FindMyEarbudsActivity.TAG, "onReceive() : " + intent.getAction());
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1854841232:
                    if (action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1436980304:
                    if (action.equals(CoreService.ACTION_MUTE_EARBUD_STATUS_UPDATED)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -302900700:
                    if (action.equals(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1335721824:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                FindMyEarbudsActivity.this.initView();
            } else if (c == 1) {
                FindMyEarbudsActivity.this.initMuteView();
            } else if (c == 2) {
                FindMyEarbudsActivity.this.initEarbuds();
                FindMyEarbudsActivity.this.initMuteView();
            } else if (c == 3) {
                if (RingManager.isFinding()) {
                    Toast.makeText(context, context.getResources().getString(Util.isTablet() ? R.string.settings_find_my_gear_disconnected_toast_tablet : R.string.settings_find_my_gear_disconnected_toast), 0).show();
                }
                Log.w(FindMyEarbudsActivity.TAG, "CoreService.ACTION_DEVICE_DISCONNECTED -> finish()");
                FindMyEarbudsActivity.this.finish();
            }
        }
    };
    private ImageView mRightEarbud;
    private TextView mTextButton;
    private TextView mTextDesc;
    private TextView mTextMuteLeft;
    private TextView mTextMuteRight;
    private View.OnClickListener setButtonOnClickListener = new View.OnClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.mainmenu.FindMyEarbudsActivity.AnonymousClass1 */

        public void onClick(View view) {
            if (RingManager.isFinding()) {
                Log.d(FindMyEarbudsActivity.TAG, "mStartButton onClick:: RingManager.ready()");
                RingManager.ready();
                SamsungAnalyticsUtil.sendEvent(SA.Event.FIND_MY_EARBUDS_STOP, SA.Screen.FIND_MY_EARBUDS_FINDING);
                return;
            }
            Log.d(FindMyEarbudsActivity.TAG, "mStartButton onClick:: RingManager.find()");
            int check = RingManager.check();
            if (check == 0) {
                Log.d(FindMyEarbudsActivity.TAG, "onClick:: RingManager.SUCCESS");
                RingManager.find();
                SamsungAnalyticsUtil.sendEvent(SA.Event.FIND_MY_EARBUDS_START, SA.Screen.FIND_MY_EARBUDS_READY);
            } else if (check == 2) {
                Log.d(FindMyEarbudsActivity.TAG, "onClick:: RingManager.DEVICE_BOTH_WEARING");
                Toast.makeText(Application.getContext(), FindMyEarbudsActivity.this.getString(R.string.settings_find_my_gear_both_wearing_toast), 0).show();
                SamsungAnalyticsUtil.sendEvent(SA.Event.FIND_MY_EARBUDS_START, SA.Screen.FIND_MY_EARBUDS_READY);
            } else if (check == 3) {
                Log.d(FindMyEarbudsActivity.TAG, "onClick:: RingManager.DEVICE_CALLING");
                Toast.makeText(Application.getContext(), FindMyEarbudsActivity.this.getString(R.string.settings_find_my_gear_call_toast), 0).show();
                SamsungAnalyticsUtil.sendEvent(SA.Event.FIND_MY_EARBUDS_START, SA.Screen.FIND_MY_EARBUDS_READY);
            }
        }
    };
    private View.OnClickListener setMuteOnClickListener = new View.OnClickListener() {
        /* class com.samsung.accessory.hearablemgr.module.mainmenu.FindMyEarbudsActivity.AnonymousClass2 */

        public void onClick(View view) {
            String str = "a";
            switch (view.getId()) {
                case R.id.button_mute_left /*{ENCODED_INT: 2131230852}*/:
                case R.id.image_mute_left /*{ENCODED_INT: 2131231022}*/:
                    boolean z = !FindMyEarbudsActivity.this.isLeftMute();
                    if (!z) {
                        str = "b";
                    }
                    SamsungAnalyticsUtil.sendEvent(SA.Event.LEFT_MUTE, SA.Screen.FIND_MY_EARBUDS_FINDING, str);
                    if (!FindMyEarbudsActivity.this.isWearLeftDevice()) {
                        RingManager.setLeftMute(z);
                        return;
                    } else {
                        Log.d(FindMyEarbudsActivity.TAG, "onClick:: isWearLeftDevice false");
                        return;
                    }
                case R.id.button_mute_right /*{ENCODED_INT: 2131230853}*/:
                case R.id.image_mute_right /*{ENCODED_INT: 2131231023}*/:
                    boolean z2 = !FindMyEarbudsActivity.this.isRightMute();
                    if (!z2) {
                        str = "b";
                    }
                    SamsungAnalyticsUtil.sendEvent(SA.Event.RIGHT_MUTE, SA.Screen.FIND_MY_EARBUDS_FINDING, str);
                    if (!FindMyEarbudsActivity.this.isWearRightDevice()) {
                        RingManager.setRightMute(z2);
                        return;
                    } else {
                        Log.d(FindMyEarbudsActivity.TAG, "onClick:: isWearRightDevice false");
                        return;
                    }
                default:
                    return;
            }
        }
    };

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_find_my_earbud);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getString(R.string.settings_find_my_gear));
        registerReceiver();
        init();
        initView();
        initEarbuds();
        setDeviceImage();
        registerListener();
    }

    private void init() {
        this.mAnimationView = (LottieAnimationView) findViewById(R.id.animation_finding);
        this.mImageFinding = (ImageView) findViewById(R.id.image_finding);
        this.mTextButton = (TextView) findViewById(R.id.text_button_name);
        this.mTextDesc = (TextView) findViewById(R.id.text_description);
        this.mImageMuteLeft = (ImageView) findViewById(R.id.image_mute_left);
        this.mImageMuteRight = (ImageView) findViewById(R.id.image_mute_right);
        this.mTextMuteLeft = (TextView) findViewById(R.id.text_mute_left);
        this.mTextMuteRight = (TextView) findViewById(R.id.text_mute_right);
        this.mMuteLayout = (ConstraintLayout) findViewById(R.id.mute_layout);
        this.mDeviceInfo = (ConstraintLayout) findViewById(R.id.device_layout);
        this.mButtonMuteLeft = findViewById(R.id.button_mute_left);
        this.mButtonMuteRight = findViewById(R.id.button_mute_right);
        this.mLeftEarbud = (ImageView) findViewById(R.id.image_bud_left);
        this.mRightEarbud = (ImageView) findViewById(R.id.image_bud_right);
        this.mButtonLayout = (FrameLayout) findViewById(R.id.button_layout);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initView() {
        if (RingManager.isFinding()) {
            setFindView();
        } else {
            setReadyView();
        }
    }

    private void setFindView() {
        this.mTextButton.setText(R.string.settings_find_my_gear_btn_stop);
        this.mButtonLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.find_my_earbuds_finding_button_color)));
        String string = getString(R.string.settings_find_my_gear_finding_desc, new Object[]{getString(R.string.app_name)});
        this.mTextDesc.setText(string + "\n" + getString(R.string.settings_find_my_gear_no_beep_in_case_desc));
        initMuteView();
        this.mMuteLayout.setVisibility(0);
        if (Util.isTalkBackEnabled()) {
            this.mButtonMuteLeft.setVisibility(0);
            this.mButtonMuteRight.setVisibility(0);
        } else {
            this.mButtonMuteLeft.setVisibility(8);
            this.mButtonMuteRight.setVisibility(8);
        }
        startAnimation();
        getWindow().addFlags(128);
    }

    private void setReadyView() {
        this.mTextButton.setText(R.string.settings_find_my_gear_btn_start);
        this.mButtonLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        String string = getString(R.string.settings_find_my_gear_ready_desc);
        this.mTextDesc.setText(string + "\n" + getString(R.string.settings_find_my_gear_warning_desc));
        this.mMuteLayout.setVisibility(4);
        stopAnimation();
        getWindow().clearFlags(128);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initEarbuds() {
        Log.d(TAG, "initEarbuds()");
        if (isConnectedLeftDevice()) {
            UiUtil.setAnimateAlpha(this.mLeftEarbud, 1.0f);
            ((TextView) findViewById(R.id.text_left_connection)).setText(R.string.settings_find_my_gear_connected);
        } else {
            UiUtil.setAnimateAlpha(this.mLeftEarbud, 0.4f);
            ((TextView) findViewById(R.id.text_left_connection)).setText(R.string.settings_find_my_gear_disconnected);
        }
        if (isConnectedRightDevice()) {
            UiUtil.setAnimateAlpha(this.mRightEarbud, 1.0f);
            ((TextView) findViewById(R.id.text_right_connection)).setText(R.string.settings_find_my_gear_connected);
        } else {
            UiUtil.setAnimateAlpha(this.mRightEarbud, 0.4f);
            ((TextView) findViewById(R.id.text_right_connection)).setText(R.string.settings_find_my_gear_disconnected);
        }
        String string = getString(R.string.earbud_info);
        if (isConnectedLeftDevice() && isConnectedRightDevice()) {
            string = String.format(getString(R.string.earbud_connected), getString(R.string.device_left)) + ", " + String.format(getString(R.string.earbud_connected), getString(R.string.device_right));
        } else if (isConnectedLeftDevice()) {
            string = String.format(getString(R.string.earbud_connected), getString(R.string.device_left)) + ", " + String.format(getString(R.string.earbud_disconnected), getString(R.string.device_right));
        } else if (isConnectedRightDevice()) {
            string = String.format(getString(R.string.earbud_disconnected), getString(R.string.device_left)) + ", " + String.format(getString(R.string.earbud_connected), getString(R.string.device_right));
        }
        this.mDeviceInfo.setContentDescription(string);
    }

    private void registerListener() {
        this.mTextButton.setOnClickListener(this.setButtonOnClickListener);
        this.mImageMuteLeft.setOnClickListener(this.setMuteOnClickListener);
        this.mImageMuteRight.setOnClickListener(this.setMuteOnClickListener);
        this.mButtonMuteLeft.setOnClickListener(this.setMuteOnClickListener);
        this.mButtonMuteRight.setOnClickListener(this.setMuteOnClickListener);
    }

    private void startAnimation() {
        UiUtil.setAnimateAlpha(this.mImageFinding, 0.0f);
        this.mImageFinding.setImageResource(R.drawable.finding_new);
        UiUtil.setAnimateAlpha(this.mImageFinding, 1.0f);
        this.mImageFinding.setVisibility(4);
        this.mAnimationView.setVisibility(0);
        this.mAnimationView.playAnimation();
        this.mAnimationView.loop(true);
    }

    private void stopAnimation() {
        this.mAnimationView.clearAnimation();
        this.mAnimationView.setVisibility(4);
        this.mImageFinding.setImageResource(R.drawable.start_finding_new);
        this.mImageFinding.setVisibility(0);
        UiUtil.setAnimateAlpha(this.mImageFinding, 1.0f);
    }

    private void setMute(int i) {
        if (i == 0) {
            this.mImageMuteLeft.setClickable(true);
            this.mImageMuteLeft.setImageResource(R.drawable.fd_settings_ic_mute);
            this.mTextMuteLeft.setText(R.string.settings_find_my_gear_mute);
            View view = this.mButtonMuteLeft;
            view.setContentDescription(getString(R.string.settings_find_my_gear_mute) + " " + getString(R.string.device_left));
            return;
        }
        this.mImageMuteRight.setClickable(true);
        this.mImageMuteRight.setImageResource(R.drawable.fd_settings_ic_mute);
        this.mTextMuteRight.setText(R.string.settings_find_my_gear_mute);
        View view2 = this.mButtonMuteRight;
        view2.setContentDescription(getString(R.string.settings_find_my_gear_mute) + " " + getString(R.string.device_right));
    }

    private void setUnMute(int i) {
        if (i == 0) {
            this.mImageMuteLeft.setClickable(true);
            this.mImageMuteLeft.setImageResource(R.drawable.fd_settings_ic_sound);
            this.mTextMuteLeft.setText(R.string.settings_find_my_gear_unmute);
            View view = this.mButtonMuteLeft;
            view.setContentDescription(getString(R.string.settings_find_my_gear_unmute) + " " + getString(R.string.device_left));
            return;
        }
        this.mImageMuteRight.setClickable(true);
        this.mImageMuteRight.setImageResource(R.drawable.fd_settings_ic_sound);
        this.mTextMuteRight.setText(R.string.settings_find_my_gear_unmute);
        View view2 = this.mButtonMuteRight;
        view2.setContentDescription(getString(R.string.settings_find_my_gear_unmute) + " " + getString(R.string.device_right));
    }

    private void setMuteDisconnect(int i) {
        if (i == 0) {
            this.mImageMuteLeft.setClickable(false);
            this.mImageMuteLeft.setImageResource(R.drawable.fd_settings_ic_disconnected);
            this.mTextMuteLeft.setText(R.string.settings_find_my_gear_disconnected);
            View view = this.mButtonMuteLeft;
            view.setContentDescription(getString(R.string.settings_find_my_gear_disconnected) + " " + getString(R.string.device_left));
            return;
        }
        this.mImageMuteRight.setClickable(false);
        this.mImageMuteRight.setImageResource(R.drawable.fd_settings_ic_disconnected);
        this.mTextMuteRight.setText(R.string.settings_find_my_gear_disconnected);
        View view2 = this.mButtonMuteRight;
        view2.setContentDescription(getString(R.string.settings_find_my_gear_disconnected) + " " + getString(R.string.device_right));
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_FIND_MY_EARBUDS_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MUTE_EARBUD_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        registerReceiver(this.mReceiver, intentFilter);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initMuteView() {
        String str = TAG;
        Log.d(str, "initMuteView() isLeftMute : " + isLeftMute() + ", isRightMute : " + isRightMute());
        setLeftMuteView(isLeftMute());
        setRightMuteView(isRightMute());
    }

    private void setLeftMuteView(boolean z) {
        if (!isConnectedLeftDevice()) {
            setMuteDisconnect(0);
        } else if (z) {
            setMute(0);
        } else {
            setUnMute(0);
        }
    }

    private void setRightMuteView(boolean z) {
        if (!isConnectedRightDevice()) {
            setMuteDisconnect(1);
        } else if (z) {
            setMute(1);
        } else {
            setUnMute(1);
        }
    }

    private boolean isConnectedLeftDevice() {
        return Application.getCoreService().getEarBudsInfo().batteryL > 0;
    }

    private boolean isConnectedRightDevice() {
        return Application.getCoreService().getEarBudsInfo().batteryR > 0;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isLeftMute() {
        return Application.getCoreService().getEarBudsInfo().leftMuteStatus;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isRightMute() {
        return Application.getCoreService().getEarBudsInfo().rightMuteStatus;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isWearLeftDevice() {
        return Application.getCoreService().getEarBudsInfo().wearingL;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isWearRightDevice() {
        return Application.getCoreService().getEarBudsInfo().wearingR;
    }

    private void setDeviceImage() {
        CardEarbuds.setDeviceImages(null, this.mLeftEarbud, this.mRightEarbud, null);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.FIND_MY_EARBUDS_READY);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onPause() {
        RingManager.ready();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onDestroy() {
        unregisterReceiver(this.mReceiver);
        RingManager.ready();
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, RingManager.isFinding() ? SA.Screen.FIND_MY_EARBUDS_FINDING : SA.Screen.FIND_MY_EARBUDS_READY);
        finish();
        return true;
    }
}
