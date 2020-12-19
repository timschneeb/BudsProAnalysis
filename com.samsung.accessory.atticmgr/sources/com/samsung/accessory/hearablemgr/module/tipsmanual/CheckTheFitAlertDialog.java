package com.samsung.accessory.hearablemgr.module.tipsmanual;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.accessorydm.interfaces.XCommonInterface;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.android.fotaagent.update.UpdateInterface;
import seccompat.android.util.Log;

public class CheckTheFitAlertDialog extends AlertDialog {
    private static final int PAGE_CHECKING = 1;
    private static final int PAGE_READY = 0;
    private static final int PAGE_RESULT = 2;
    private final int CHECK_LIMIT_TIME = 5000;
    private final String TAG = (Application.TAG_ + CheckTheFitAlertDialog.class.getSimpleName());
    private int currentPage;
    private View mCheckFitButtonDivider;
    private TextView mCheckFitCancelButton;
    private TextView mCheckFitCheckDescription;
    private TextView mCheckFitCheckingText;
    private ImageView mCheckFitEarbudsLeft;
    private TextView mCheckFitEarbudsLeftText;
    private ImageView mCheckFitEarbudsRight;
    private TextView mCheckFitEarbudsRightText;
    private TextView mCheckFitLeftStatusText;
    private TextView mCheckFitOkButton;
    private ProgressBar mCheckFitProgress;
    private TextView mCheckFitReadyDescription;
    private TextView mCheckFitResultDescription;
    private TextView mCheckFitRightStatusText;
    private TextView mCheckFitStartButton;
    private TextView mCheckFitTitle;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass6 */

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public void onReceive(Context context, Intent intent) {
            char c;
            Log.d(CheckTheFitAlertDialog.this.TAG, "onReceive : " + intent.getAction() + ", currentPage : " + CheckTheFitAlertDialog.this.currentPage);
            String action = intent.getAction();
            switch (action.hashCode()) {
                case -1854841232:
                    if (action.equals(CoreService.ACTION_MSG_ID_STATUS_UPDATED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -654353038:
                    if (action.equals(CoreService.ACTION_MSG_ID_CALL_STATE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1158069655:
                    if (action.equals(CoreService.ACTION_MSG_ID_CHECK_THE_FIT_OF_EARBUDS_RESULT)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1335721824:
                    if (action.equals(CoreService.ACTION_DEVICE_DISCONNECTED)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1882440235:
                    if (action.equals(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                int i = CheckTheFitAlertDialog.this.currentPage;
                if (i == 0) {
                    CheckTheFitAlertDialog.this.updateWearingUI();
                } else if (i == 1 && !CheckTheFitAlertDialog.this.isWearingBothEarbuds()) {
                    CheckTheFitAlertDialog.this.setPage(0);
                }
            } else if (c == 1) {
                CheckTheFitAlertDialog.this.dismiss();
            } else if (c == 2) {
                CheckTheFitAlertDialog.this.timeOutHandler.removeCallbacksAndMessages(null);
                CheckTheFitAlertDialog.this.setPage(2);
            } else if ((c == 3 || c == 4) && Util.isCalling()) {
                Toast.makeText(context, context.getResources().getString(R.string.tips_cant_check_during_call), 0).show();
                CheckTheFitAlertDialog.this.dismiss();
            }
        }
    };
    private Handler timeOutHandler = new Handler();
    private ValueAnimator valueAnimator;

    public CheckTheFitAlertDialog(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(this.TAG, "onCreate()");
        setContentView(R.layout.alert_dialog_tips_check_the_fit);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(80);
        }
        registerReceiver();
        init();
        initView();
        initListener();
    }

    public void onDetachedFromWindow() {
        Log.d(this.TAG, "onDetachedFromWindow()");
        clearProgress();
        unregisterReceiver();
        super.onDetachedFromWindow();
    }

    private void init() {
        this.mCheckFitStartButton = (TextView) findViewById(R.id.btn_check_fit_start);
        this.mCheckFitCancelButton = (TextView) findViewById(R.id.btn_check_fit_cancel);
        this.mCheckFitButtonDivider = findViewById(R.id.view_check_fit_btn_divider);
        this.mCheckFitOkButton = (TextView) findViewById(R.id.btn_check_fit_ok);
        this.mCheckFitTitle = (TextView) findViewById(R.id.text_check_fit_dialog_title);
        this.mCheckFitReadyDescription = (TextView) findViewById(R.id.text_check_fit_dialog_ready_desc);
        this.mCheckFitCheckDescription = (TextView) findViewById(R.id.text_check_fit_dialog_check_desc);
        this.mCheckFitResultDescription = (TextView) findViewById(R.id.text_check_fit_dialog_result_desc);
        this.mCheckFitEarbudsLeft = (ImageView) findViewById(R.id.image_check_fit_earbuds_left);
        this.mCheckFitEarbudsRight = (ImageView) findViewById(R.id.image_check_fit_earbuds_right);
        this.mCheckFitEarbudsLeftText = (TextView) findViewById(R.id.text_check_fit_earbuds_left);
        this.mCheckFitEarbudsRightText = (TextView) findViewById(R.id.text_check_fit_earbuds_right);
        this.mCheckFitLeftStatusText = (TextView) findViewById(R.id.text_check_fit_earbuds_left_status);
        this.mCheckFitRightStatusText = (TextView) findViewById(R.id.text_check_fit_earbuds_right_status);
        this.mCheckFitCheckingText = (TextView) findViewById(R.id.text_checking);
        this.mCheckFitProgress = (ProgressBar) findViewById(R.id.progress_check_fit);
    }

    private void initView() {
        setPage(0);
    }

    private void initListener() {
        this.mCheckFitCancelButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass1 */

            public void onClick(View view) {
                Log.d(CheckTheFitAlertDialog.this.TAG, "mCheckFitCancelButton onClick() : finished");
                CheckTheFitAlertDialog.this.dismiss();
            }
        });
        this.mCheckFitStartButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass2 */

            public void onClick(View view) {
                Log.d(CheckTheFitAlertDialog.this.TAG, "mCheckFitStartButton onClick() : checking start");
                CheckTheFitAlertDialog.this.setPage(1);
                CheckTheFitAlertDialog.this.startFitCheck();
            }
        });
        this.mCheckFitOkButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass3 */

            public void onClick(View view) {
                int i = CheckTheFitAlertDialog.this.currentPage;
                if (i == 1) {
                    Log.d(CheckTheFitAlertDialog.this.TAG, "mCheckFitOkButton onClick() : checking cancel");
                    CheckTheFitAlertDialog.this.setPage(0);
                } else if (i == 2) {
                    Log.d(CheckTheFitAlertDialog.this.TAG, "mCheckFitOkButton onClick() : result ok");
                    CheckTheFitAlertDialog.this.dismiss();
                }
            }
        });
    }

    public void setPage(int i) {
        clearProgress();
        if (i == 1) {
            checkUI();
        } else if (i != 2) {
            readyUI();
        } else {
            resultUI();
        }
    }

    private void clearProgress() {
        ValueAnimator valueAnimator2 = this.valueAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            Log.d(this.TAG, "clearProgress() checking is canceled");
            this.valueAnimator.removeAllListeners();
            this.valueAnimator.cancel();
            sendCheckFitSppMessage(false);
        }
        this.mCheckFitProgress.setProgress(0);
    }

    private void readyUI() {
        setTitle(R.string.tips_check_the_fit_of_your_earbuds);
        setReadyMessage();
        showOkButton(false);
        showCheckProgress(false);
        updateWearingUI();
        this.mCheckFitEarbudsLeft.setImageDrawable(getContext().getResources().getDrawable(R.drawable.fit_default_left));
        this.mCheckFitEarbudsRight.setImageDrawable(getContext().getResources().getDrawable(R.drawable.fit_default_right));
        this.mCheckFitEarbudsLeftText.setTextColor(getContext().getResources().getColor(R.color.tips_check_fit_good_color));
        this.mCheckFitEarbudsRightText.setTextColor(getContext().getResources().getColor(R.color.tips_check_fit_good_color));
        this.mCheckFitLeftStatusText.setTextColor(getContext().getResources().getColor(R.color.tips_check_fit_good_color));
        this.mCheckFitRightStatusText.setTextColor(getContext().getResources().getColor(R.color.tips_check_fit_good_color));
        this.mCheckFitLeftStatusText.setText(R.string.tips_not_in_ear);
        this.mCheckFitRightStatusText.setText(R.string.tips_not_in_ear);
        this.currentPage = 0;
    }

    private void showCheckProgress(boolean z) {
        int i = 0;
        this.mCheckFitCheckingText.setVisibility(z ? 0 : 4);
        this.mCheckFitProgress.setVisibility(z ? 0 : 4);
        this.mCheckFitEarbudsLeftText.setVisibility(z ? 4 : 0);
        this.mCheckFitEarbudsRightText.setVisibility(z ? 4 : 0);
        this.mCheckFitLeftStatusText.setVisibility(z ? 4 : 0);
        TextView textView = this.mCheckFitRightStatusText;
        if (z) {
            i = 4;
        }
        textView.setVisibility(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startFitCheck() {
        this.valueAnimator = ValueAnimator.ofInt(0, this.mCheckFitProgress.getMax());
        this.valueAnimator.setDuration(XCommonInterface.WAKE_LOCK_TIMEOUT);
        this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass4 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CheckTheFitAlertDialog.this.mCheckFitProgress.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        this.valueAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass5 */

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                CheckTheFitAlertDialog.this.sendCheckFitSppMessage(true);
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                CheckTheFitAlertDialog.this.timeOutHandler.postDelayed(new Runnable() {
                    /* class com.samsung.accessory.hearablemgr.module.tipsmanual.CheckTheFitAlertDialog.AnonymousClass5.AnonymousClass1 */

                    public void run() {
                        Log.d(CheckTheFitAlertDialog.this.TAG, "Time out!!! It doesn't receive result about check the fit. Go to ready page");
                        CheckTheFitAlertDialog.this.setPage(0);
                    }
                }, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
            }
        });
        this.valueAnimator.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendCheckFitSppMessage(boolean z) {
        String str = this.TAG;
        Log.d(str, "sendCheckFitSppMessage : " + z);
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.CHECK_THE_FIT_OF_EARBUDS, z ? (byte) 1 : 0));
    }

    private void showOkButton(boolean z) {
        int i = 4;
        this.mCheckFitStartButton.setVisibility(z ? 4 : 0);
        this.mCheckFitCancelButton.setVisibility(z ? 4 : 0);
        this.mCheckFitButtonDivider.setVisibility(z ? 4 : 0);
        TextView textView = this.mCheckFitOkButton;
        if (z) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateWearingUI() {
        boolean isConnected = Application.getCoreService().isConnected();
        boolean z = true;
        boolean z2 = isConnected && Application.getCoreService().getEarBudsInfo().wearingL;
        boolean z3 = isConnected && Application.getCoreService().getEarBudsInfo().wearingR;
        float f = 1.0f;
        this.mCheckFitEarbudsLeft.setAlpha(z2 ? 1.0f : 0.3f);
        this.mCheckFitEarbudsRight.setAlpha(z3 ? 1.0f : 0.3f);
        this.mCheckFitEarbudsLeftText.setAlpha(z2 ? 1.0f : 0.3f);
        this.mCheckFitEarbudsRightText.setAlpha(z3 ? 1.0f : 0.3f);
        this.mCheckFitLeftStatusText.setAlpha(z2 ? 1.0f : 0.3f);
        this.mCheckFitRightStatusText.setAlpha(z3 ? 1.0f : 0.3f);
        int i = 4;
        this.mCheckFitLeftStatusText.setVisibility(z2 ? 4 : 0);
        TextView textView = this.mCheckFitRightStatusText;
        if (!z3) {
            i = 0;
        }
        textView.setVisibility(i);
        TextView textView2 = this.mCheckFitStartButton;
        if (!z2 || !z3) {
            z = false;
        }
        textView2.setEnabled(z);
        TextView textView3 = this.mCheckFitStartButton;
        if (!textView3.isEnabled()) {
            f = 0.3f;
        }
        textView3.setAlpha(f);
        Log.d(this.TAG, "updateWearingUI() isConnected : " + isConnected + ", isLeftWearing : " + z2 + ", isRightWearing : " + z3);
    }

    private void checkUI() {
        setTitle(R.string.tips_check_the_fit_of_your_earbuds);
        setCheckMessage();
        showCheckProgress(true);
        showOkButton(true);
        this.mCheckFitEarbudsLeft.setImageDrawable(getContext().getResources().getDrawable(R.drawable.fit_default_left));
        this.mCheckFitEarbudsRight.setImageDrawable(getContext().getResources().getDrawable(R.drawable.fit_default_right));
        this.mCheckFitEarbudsLeft.setAlpha(1.0f);
        this.mCheckFitEarbudsRight.setAlpha(1.0f);
        this.mCheckFitOkButton.setText(R.string.cancel);
        this.currentPage = 1;
    }

    public void resultUI() {
        setTitle(R.string.tips_earbuds_fit_results);
        showCheckProgress(false);
        showOkButton(true);
        updateResultUI();
        this.mCheckFitOkButton.setText(R.string.ok);
        this.currentPage = 2;
    }

    private void updateResultUI() {
        boolean z = Application.getCoreService().getEarBudsInfo().leftCheckTheFitResult;
        boolean z2 = Application.getCoreService().getEarBudsInfo().rightCheckTheFitResult;
        if (z && z2) {
            setResultMessage(R.string.tips_you_have_gor_a_good_fit);
        } else if (z) {
            setResultMessage(R.string.tips_try_adjusting_your_right_earbud);
        } else if (z2) {
            setResultMessage(R.string.tips_try_adjusting_your_left_earbud);
        } else {
            setResultMessage(R.string.tips_try_adjusting_your_earbuds);
        }
        this.mCheckFitEarbudsLeft.setImageDrawable(getContext().getResources().getDrawable(z ? R.drawable.fit_good_left : R.drawable.fit_loose_left));
        this.mCheckFitEarbudsRight.setImageDrawable(getContext().getResources().getDrawable(z2 ? R.drawable.fit_good_right : R.drawable.fit_loose_right));
        TextView textView = this.mCheckFitEarbudsLeftText;
        Resources resources = getContext().getResources();
        int i = R.color.tips_check_fit_good_color;
        textView.setTextColor(resources.getColor(z ? R.color.tips_check_fit_good_color : R.color.tips_check_fit_loose_color));
        this.mCheckFitEarbudsRightText.setTextColor(getContext().getResources().getColor(z2 ? R.color.tips_check_fit_good_color : R.color.tips_check_fit_loose_color));
        this.mCheckFitLeftStatusText.setTextColor(getContext().getResources().getColor(z ? R.color.tips_check_fit_good_color : R.color.tips_check_fit_loose_color));
        TextView textView2 = this.mCheckFitRightStatusText;
        Resources resources2 = getContext().getResources();
        if (!z2) {
            i = R.color.tips_check_fit_loose_color;
        }
        textView2.setTextColor(resources2.getColor(i));
        TextView textView3 = this.mCheckFitLeftStatusText;
        int i2 = R.string.tips_good_fit;
        textView3.setText(z ? R.string.tips_good_fit : R.string.tips_loose);
        TextView textView4 = this.mCheckFitRightStatusText;
        if (!z2) {
            i2 = R.string.tips_loose;
        }
        textView4.setText(i2);
    }

    @Override // android.app.Dialog, androidx.appcompat.app.AppCompatDialog
    public void setTitle(int i) {
        this.mCheckFitTitle.setText(i);
    }

    private void setReadyMessage() {
        this.mCheckFitReadyDescription.setVisibility(0);
        this.mCheckFitCheckDescription.setVisibility(4);
        this.mCheckFitResultDescription.setVisibility(4);
    }

    private void setCheckMessage() {
        this.mCheckFitReadyDescription.setVisibility(4);
        this.mCheckFitCheckDescription.setVisibility(0);
        this.mCheckFitResultDescription.setVisibility(4);
    }

    private void setResultMessage(int i) {
        this.mCheckFitReadyDescription.setVisibility(4);
        this.mCheckFitCheckDescription.setVisibility(4);
        this.mCheckFitResultDescription.setVisibility(0);
        this.mCheckFitResultDescription.setText(i);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CoreService.ACTION_MSG_ID_STATUS_UPDATED);
        intentFilter.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_CHECK_THE_FIT_OF_EARBUDS_RESULT);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_CALL_STATE);
        intentFilter.addAction(CoreService.ACTION_MSG_ID_SCO_STATE_UPDATED);
        getContext().registerReceiver(this.mReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        getContext().unregisterReceiver(this.mReceiver);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isWearingBothEarbuds() {
        return Application.getCoreService().getEarBudsInfo().wearingL && Application.getCoreService().getEarBudsInfo().wearingR;
    }
}
