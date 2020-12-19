package com.samsung.android.app.watchmanager.setupwizard;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.StringResourceManagerUtil;
import com.samsung.android.app.watchmanager.R;
import com.samsung.td.particlesystem.watch_oobe.ParticleViewManager_Watch_OOBE;
import com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base;
import java.util.Locale;

public class PairingFragmentUIHelper {
    public static final int BOND_SUCCESSFUL = 3;
    public static final int BOND_UNSUCCESSFUL = 5;
    public static final int PASSKEY_GENERATED = 1;
    public static final int SET_PROGRESS_AFTER_START = 2;
    public static final int START_VI_ANIMATION = 4;
    private static final String TAG = ("tUHM:" + PairingFragmentUIHelper.class.getSimpleName());
    private static final long TIME_TO_START_ANIMATION = 2000;
    private RelativeLayout actionbarLayout;
    private boolean bondProcessCompleted = false;
    private LinearLayout cancel_bottom_layout;
    private TextView cancel_btn;
    final Handler handler = new Handler(Looper.getMainLooper());
    private RelativeLayout logoHeaderLayout;
    private Context mContext;
    private ImageView mOKButtonImage;
    private ParticleViewManager_Watch_OOBE mWatchParticleView;
    String mWearableType = StringResourceManagerUtil.WATCH_TYPE;
    private TextView messageText;
    private Button ok_btn;
    private LinearLayout ok_button_layout;
    private ParticleView_Watch_OOBE_Base.a particleAnimatorListener = new ParticleView_Watch_OOBE_Base.a() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragmentUIHelper.AnonymousClass1 */

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onEndParticleState(int i) {
            if (!PairingFragmentUIHelper.this.bondProcessCompleted) {
                int i2 = PairingFragmentUIHelper.this.scene_state;
                if (i2 != 0 && i2 == 1) {
                    PairingFragmentUIHelper.this.scene_state = 2;
                    PairingFragmentUIHelper.this.mWatchParticleView.getParticleView().a(PairingFragmentUIHelper.this.scene_state);
                    PairingFragmentUIHelper.this.particle_animation_text.setVisibility(0);
                    return;
                }
                return;
            }
            int i3 = PairingFragmentUIHelper.this.scene_state;
            if (i3 != 0) {
                if (i3 == 1) {
                    PairingFragmentUIHelper.this.scene_state = 2;
                } else if (i3 == 2) {
                    PairingFragmentUIHelper.this.scene_state = 4;
                } else if (i3 == 4) {
                    PairingFragmentUIHelper.this.mWatchParticleView.getParticleView().b();
                } else {
                    return;
                }
                PairingFragmentUIHelper.this.mWatchParticleView.getParticleView().a(PairingFragmentUIHelper.this.scene_state);
                return;
            }
            PairingFragmentUIHelper pairingFragmentUIHelper = PairingFragmentUIHelper.this;
            pairingFragmentUIHelper.handler.post(pairingFragmentUIHelper.runnable);
        }

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onParticleState(int i) {
            if (i != 0 && i != 1 && i != 2) {
            }
        }

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onProgressOpacityUpdate(float f) {
        }

        @Override // com.samsung.td.particlesystem.watch_oobe.ParticleView_Watch_OOBE_Base.a
        public void onWatchOpacityUpdate(float f) {
            PairingFragmentUIHelper.this.mOKButtonImage.setAlpha(f);
        }
    };
    private TextView particle_animation_text;
    private TextView passkey;
    private int pinCode;
    private ProgressBar progress_circle;
    private ProgressBar progress_circle_scanning;
    private Runnable r = new Runnable() {
        /* class com.samsung.android.app.watchmanager.setupwizard.PairingFragmentUIHelper.AnonymousClass2 */

        public void run() {
            PairingFragmentUIHelper pairingFragmentUIHelper;
            int i = 4;
            if (PairingFragmentUIHelper.this.bondProcessCompleted) {
                int i2 = PairingFragmentUIHelper.this.scene_state;
                if (i2 == 0) {
                    PairingFragmentUIHelper pairingFragmentUIHelper2 = PairingFragmentUIHelper.this;
                    pairingFragmentUIHelper2.handler.post(pairingFragmentUIHelper2.runnable);
                    return;
                } else if (i2 == 2) {
                    pairingFragmentUIHelper = PairingFragmentUIHelper.this;
                } else {
                    return;
                }
            } else if (PairingFragmentUIHelper.this.scene_state == 0) {
                PairingFragmentUIHelper.this.setOperation(4);
                pairingFragmentUIHelper = PairingFragmentUIHelper.this;
                i = 1;
            } else {
                return;
            }
            pairingFragmentUIHelper.scene_state = i;
            PairingFragmentUIHelper.this.mWatchParticleView.getParticleView().a(PairingFragmentUIHelper.this.scene_state);
        }
    };
    private Runnable runnable;
    private int scene_state = 0;
    private TextView tvPairUpTitle;
    private RelativeLayout vi_container_device_pairing;
    private View view;

    public PairingFragmentUIHelper(Context context, View view2) {
        this.mContext = context;
        this.view = view2;
        this.cancel_btn = (TextView) view2.findViewById(R.id.cancel_btn);
        this.ok_button_layout = (LinearLayout) view2.findViewById(R.id.ok_button_layout);
        this.passkey = (TextView) view2.findViewById(R.id.passkey);
        this.ok_btn = (Button) view2.findViewById(R.id.ok_btn);
        this.progress_circle = (ProgressBar) view2.findViewById(R.id.progress_circle);
        this.mWatchParticleView = (ParticleViewManager_Watch_OOBE) view2.findViewById(R.id.galaxy_container);
        this.cancel_bottom_layout = (LinearLayout) view2.findViewById(R.id.cancel_bottom_layout);
        this.vi_container_device_pairing = (RelativeLayout) view2.findViewById(R.id.vi_container_device_pairing);
        this.messageText = (TextView) view2.findViewById(R.id.messageText);
        this.progress_circle_scanning = (ProgressBar) view2.findViewById(R.id.progress_circle_scanning);
        this.particle_animation_text = (TextView) view2.findViewById(R.id.particle_animation_text);
        this.mOKButtonImage = (ImageView) view2.findViewById(R.id.pincode_ok_button_image);
        this.actionbarLayout = (RelativeLayout) view2.findViewById(R.id.top_actionbar_title);
        this.logoHeaderLayout = (RelativeLayout) view2.findViewById(R.id.top_image_title);
        this.tvPairUpTitle = (TextView) view2.findViewById(R.id.pairUpTitle);
        ParticleViewManager_Watch_OOBE particleViewManager_Watch_OOBE = this.mWatchParticleView;
        if (particleViewManager_Watch_OOBE != null) {
            particleViewManager_Watch_OOBE.a((int) context.getResources().getDimension(R.dimen.pairing_watch_vi_width), (int) context.getResources().getDimension(R.dimen.pairing_watch_vi_height));
            this.mWatchParticleView.getParticleView().setListener(this.particleAnimatorListener);
            return;
        }
        Log.d(TAG, "mWatchParticleView is null");
    }

    public void setCancelButtonListener(View.OnClickListener onClickListener) {
        this.cancel_btn.setOnClickListener(onClickListener);
    }

    public void setOkBtnListener(View.OnClickListener onClickListener) {
        this.ok_btn.setOnClickListener(onClickListener);
    }

    public void setOperation(int i) {
        String str = TAG;
        Log.d(str, "setOperation condition = " + i);
        if (i == 1) {
            this.progress_circle_scanning.setVisibility(8);
            this.passkey.setVisibility(0);
            this.passkey.setText(String.format(Locale.US, "%06d", Integer.valueOf(this.pinCode)));
            this.ok_button_layout.setVisibility(0);
            this.progress_circle.setVisibility(8);
            this.cancel_bottom_layout.setVisibility(0);
        } else if (i != 2) {
            if (i == 3) {
                this.bondProcessCompleted = true;
                int i2 = this.scene_state;
                if (!(i2 == 0 || i2 == 2)) {
                    return;
                }
            } else if (i == 4) {
                this.vi_container_device_pairing.setVisibility(0);
                this.messageText.setVisibility(8);
                this.passkey.setVisibility(8);
                this.ok_button_layout.setVisibility(8);
                this.cancel_bottom_layout.setVisibility(8);
                return;
            } else if (i != 5) {
                Log.d(TAG, "unusual case");
                return;
            } else {
                this.bondProcessCompleted = true;
                int i3 = this.scene_state;
                if (!(i3 == 0 || i3 == 2)) {
                    return;
                }
            }
            this.handler.post(this.r);
        } else {
            this.ok_btn.setVisibility(8);
            this.progress_circle.setVisibility(0);
            this.cancel_btn.setEnabled(false);
            this.cancel_btn.setAlpha(0.6f);
            if (StringResourceManagerUtil.WATCH_TYPE.equalsIgnoreCase(this.mWearableType)) {
                this.handler.postDelayed(this.r, TIME_TO_START_ANIMATION);
            }
            this.mWatchParticleView.getParticleView().a();
        }
    }

    public void setOperation(int i, String str) {
        this.mWearableType = str;
        this.messageText.setText(this.mContext.getResources().getString(StringResourceManagerUtil.pairingDesc(str)));
        setOperation(i);
    }

    public void setPairingImage(int i) {
        ViewGroup.LayoutParams layoutParams;
        int i2;
        Resources resources;
        this.mOKButtonImage.setBackgroundResource(i);
        switch (i) {
            case R.drawable.oobe_pairing_img_watch_select_01:
                this.mOKButtonImage.requestLayout();
                this.mOKButtonImage.getLayoutParams().height = (int) this.mContext.getResources().getDimension(R.dimen.pairing_watch_image_01_height);
                layoutParams = this.mOKButtonImage.getLayoutParams();
                resources = this.mContext.getResources();
                i2 = R.dimen.pairing_watch_image_01_width;
                break;
            case R.drawable.oobe_pairing_img_watch_select_02:
                this.mOKButtonImage.requestLayout();
                this.mOKButtonImage.getLayoutParams().height = (int) this.mContext.getResources().getDimension(R.dimen.pairing_watch_image_02_height);
                layoutParams = this.mOKButtonImage.getLayoutParams();
                resources = this.mContext.getResources();
                i2 = R.dimen.pairing_watch_image_02_width;
                break;
            default:
                return;
        }
        layoutParams.width = (int) resources.getDimension(i2);
        this.mOKButtonImage.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void setPincode(int i) {
        this.pinCode = i;
    }

    public void setRunnable(Runnable runnable2) {
        this.runnable = runnable2;
    }

    public void updateViewsByMultiWindowMode(boolean z) {
        int dimension = (int) this.mContext.getResources().getDimension(R.dimen.action_bar_h);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.tvPairUpTitle.getLayoutParams();
        if (z) {
            this.actionbarLayout.setVisibility(0);
            this.logoHeaderLayout.setVisibility(8);
            marginLayoutParams.topMargin = dimension;
            this.tvPairUpTitle.setLayoutParams(marginLayoutParams);
            return;
        }
        this.actionbarLayout.setVisibility(8);
        this.logoHeaderLayout.setVisibility(0);
    }
}
