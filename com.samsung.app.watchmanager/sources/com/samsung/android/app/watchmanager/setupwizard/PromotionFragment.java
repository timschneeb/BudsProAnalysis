package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.log.LoggerUtil;
import com.samsung.android.app.twatchmanager.util.AnimationHelper;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class PromotionFragment extends Fragment implements SlidingUpPanelLayout.b, OnBackKeyListener {
    private static final String TAG = ("tUHM:" + PromotionFragment.class.getSimpleName());
    private static boolean mExpanded = false;
    private AnimationHelper arrowAnimationHelper;
    private BackgroundFragment childBgFrag;
    private int connectCase = 1;
    private AnimationHelper logoAnimationHelper;
    private LinearLayout mActionbarLayout;
    private TextView mContactUs_Text;
    private SlidingUpPanelLayout mLayout;
    private PickGearFragment pickGearFragment;
    private TextView startJourneyButton;
    SlidingUpPanelLayout.c tempState = SlidingUpPanelLayout.c.COLLAPSED;

    private void initConnectingAnimation() {
        Log.d(TAG, "initConnectingAnimation() init connecting vi");
        this.arrowAnimationHelper = new AnimationHelper(getActivity());
        new Handler().postDelayed(new Runnable() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PromotionFragment.AnonymousClass4 */

            public void run() {
                View view = PromotionFragment.this.getView();
                if (view != null) {
                    PromotionFragment.this.arrowAnimationHelper.startArrowAnimation(view);
                }
            }
        }, 1000);
    }

    private void updateViews(View view, float f) {
        int i;
        TextView textView;
        Activity activity = getActivity();
        if (activity == null || !(activity instanceof SetupWizardWelcomeActivity) || !((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode()) {
            this.logoAnimationHelper.animate(f, this.connectCase);
        }
        if (f >= 0.25f) {
            textView = this.startJourneyButton;
            i = 4;
        } else {
            textView = this.startJourneyButton;
            i = 0;
        }
        textView.setVisibility(i);
        this.mContactUs_Text.setVisibility(i);
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        SlidingUpPanelLayout slidingUpPanelLayout;
        if (this.connectCase == 1 && (slidingUpPanelLayout = this.mLayout) != null && slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.c.EXPANDED) {
            this.mLayout.setPanelState(SlidingUpPanelLayout.c.COLLAPSED);
            return true;
        }
        if (this.connectCase == 2) {
            com.samsung.android.app.twatchmanager.log.Log.d(TAG, "case : PROMOTION_FRAGMENT_CONNECT_NEW_DEVICE");
            Activity activity = getActivity();
            if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
                boolean startLastLaunchedPlugin = ((SetupWizardWelcomeActivity) activity).startLastLaunchedPlugin(false, null);
                String str = TAG;
                com.samsung.android.app.twatchmanager.log.Log.d(str, "isPluginStarted" + startLastLaunchedPlugin + " mActivity:" + activity);
                if (activity != null) {
                    activity.overridePendingTransition(R.anim.activity_left_to_right_in, R.anim.activity_left_to_right_out);
                    activity.finish();
                }
            }
        }
        return false;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String str = TAG;
        com.samsung.android.app.twatchmanager.log.Log.d(str, "onConfigurationChanged ::tempState = " + this.tempState);
        mExpanded = this.tempState == SlidingUpPanelLayout.c.EXPANDED;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String str = TAG;
        com.samsung.android.app.twatchmanager.log.Log.d(str, "selected configuration : " + getActivity().getResources().getString(R.string.selected_configuration));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        Activity activity;
        com.samsung.android.app.twatchmanager.log.Log.d(TAG, "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.promotion, viewGroup, false);
        this.mActionbarLayout = (LinearLayout) inflate.findViewById(R.id.actionbar_layout);
        this.mLayout = (SlidingUpPanelLayout) inflate.findViewById(R.id.sliding_layout);
        int windowHeight = UIUtils.getWindowHeight(getActivity()) / 2;
        this.mLayout.setPanelHeight(windowHeight);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.panel_layout);
        View findViewById = inflate.findViewById(R.id.dragView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById.getLayoutParams();
        layoutParams.height = windowHeight;
        findViewById.setLayoutParams(layoutParams);
        SlidingUpPanelLayout.LayoutParams layoutParams2 = (SlidingUpPanelLayout.LayoutParams) linearLayout.getLayoutParams();
        getResources().getDimension(R.dimen.promotion_top_image_height);
        layoutParams2.setMargins(0, 0 - windowHeight, 0, 0);
        linearLayout.setLayoutParams(layoutParams2);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.logoImageView);
        this.mContactUs_Text = (TextView) inflate.findViewById(R.id.contact_us);
        if (HostManagerUtils.isSamsungDevice()) {
            activity = getActivity();
            i = R.string.welcome_to_samsung_gear_promotion_contact_us;
        } else {
            activity = getActivity();
            i = R.string.contact_us_faq;
        }
        SpannableString spannableString = new SpannableString(activity.getString(i));
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        this.mContactUs_Text.setText(spannableString);
        this.mContactUs_Text.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PromotionFragment.AnonymousClass1 */

            public void onClick(View view) {
                SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_WELCOME, SALogUtil.SA_LOG_EVENT_CONTACT_US, "Contact us");
                com.samsung.android.app.twatchmanager.log.Log.d(PromotionFragment.TAG, "OnClick Contact us");
                HostManagerUtils.startContactUsProcess(PromotionFragment.this.getActivity());
            }
        });
        this.logoAnimationHelper = new AnimationHelper(getActivity(), imageView, inflate.findViewById(R.id.logoFinalImageView), 4);
        this.mLayout.a(this);
        this.startJourneyButton = (TextView) inflate.findViewById(R.id.startJourneyButton);
        this.startJourneyButton.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.PromotionFragment.AnonymousClass2 */

            public void onClick(View view) {
                SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_WELCOME, SALogUtil.SA_LOG_EVENT_START_JOURNEY, "Start the journey");
                com.samsung.android.app.twatchmanager.log.Log.d(PromotionFragment.TAG, "launch mode = LAUNCHED_FROM_BT_SETTING, all permissions are granted");
                PromotionFragment.this.mLayout.setPanelState(SlidingUpPanelLayout.c.EXPANDED);
            }
        });
        this.childBgFrag = new BackgroundFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.background_container, this.childBgFrag).commit();
        Bundle arguments = getArguments();
        this.connectCase = arguments.getInt(SetupWizardWelcomeActivity.EXTRA_CONNECT_CASE, 1);
        this.childBgFrag.setArguments(arguments);
        this.mLayout.a(this.childBgFrag);
        this.pickGearFragment = new PickGearFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fragmentContainer, this.pickGearFragment).commit();
        return inflate;
    }

    public void onDestroy() {
        com.samsung.android.app.twatchmanager.log.Log.d(TAG, "onDestroy");
        super.onDestroy();
        mExpanded = false;
        this.mLayout.b(this);
        this.mLayout.b(this.childBgFrag);
        Runtime.getRuntime().gc();
    }

    @Override // com.sothree.slidinguppanel.SlidingUpPanelLayout.b
    public void onPanelSlide(View view, float f) {
        updateViews(view, f);
    }

    @Override // com.sothree.slidinguppanel.SlidingUpPanelLayout.b
    public void onPanelStateChanged(View view, SlidingUpPanelLayout.c cVar, SlidingUpPanelLayout.c cVar2) {
        SlidingUpPanelLayout.c cVar3;
        SlidingUpPanelLayout slidingUpPanelLayout;
        this.tempState = cVar2;
        SlidingUpPanelLayout.c cVar4 = SlidingUpPanelLayout.c.DRAGGING;
        if (cVar2 == cVar4 && cVar != cVar4) {
            Activity activity = getActivity();
            boolean currentMultiWindowMode = (activity == null || !(activity instanceof SetupWizardWelcomeActivity)) ? false : ((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode();
            this.pickGearFragment.updateTitleMargin(currentMultiWindowMode, cVar2);
            if (currentMultiWindowMode) {
                this.mActionbarLayout.setVisibility(0);
            }
        }
        if (!(cVar2 == SlidingUpPanelLayout.c.DRAGGING || cVar2 == SlidingUpPanelLayout.c.EXPANDED)) {
            this.mActionbarLayout.setVisibility(8);
        }
        if (cVar2 == SlidingUpPanelLayout.c.EXPANDED) {
            com.samsung.android.app.twatchmanager.log.Log.d(TAG, "enabling sliding");
            if (this.connectCase == 2) {
                this.mLayout.setTouchEnabled(false);
            } else {
                this.mLayout.setTouchEnabled(true);
            }
        } else if (cVar2 == SlidingUpPanelLayout.c.COLLAPSED) {
            this.mLayout.setTouchEnabled(true);
            this.logoAnimationHelper.animate(0.0f);
        } else if (cVar2 == SlidingUpPanelLayout.c.ANCHORED) {
            com.samsung.android.app.twatchmanager.log.Log.d(TAG, "anchored");
            this.mLayout.setTouchEnabled(false);
            if (this.mLayout.getSlideOffset() >= 0.4f) {
                slidingUpPanelLayout = this.mLayout;
                cVar3 = SlidingUpPanelLayout.c.EXPANDED;
            } else {
                slidingUpPanelLayout = this.mLayout;
                cVar3 = SlidingUpPanelLayout.c.COLLAPSED;
            }
            slidingUpPanelLayout.setPanelState(cVar3);
        }
    }

    public void onStart() {
        super.onStart();
        com.samsung.android.app.twatchmanager.log.Log.d(TAG, "onStart");
        LoggerUtil.insertLog(getActivity(), "G021", "Initial screen", null);
        if (mExpanded) {
            new Handler().postDelayed(new Runnable() {
                /* class com.samsung.android.app.watchmanager.setupwizard.PromotionFragment.AnonymousClass3 */

                public void run() {
                    PromotionFragment.this.mLayout.setEnabled(true);
                    PromotionFragment.this.mLayout.setPanelState(SlidingUpPanelLayout.c.EXPANDED);
                }
            }, 500);
        }
    }

    public void onStop() {
        com.samsung.android.app.twatchmanager.log.Log.d(TAG, "onStop");
        super.onStop();
    }

    public void onViewCreated(View view, Bundle bundle) {
        com.samsung.android.app.twatchmanager.log.Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, bundle);
        initConnectingAnimation();
        UIUtils.adjustLogoMargin(view.findViewById(R.id.logoFinalImageView));
    }

    /* access modifiers changed from: package-private */
    public void setScrollableView(View view) {
        SlidingUpPanelLayout slidingUpPanelLayout = this.mLayout;
        if (slidingUpPanelLayout != null) {
            slidingUpPanelLayout.setScrollableView(view);
        }
    }

    public void updatePanelState(SlidingUpPanelLayout.c cVar) {
        String str = TAG;
        com.samsung.android.app.twatchmanager.log.Log.d(str, "updatePanelState connectCase: " + this.connectCase);
        this.mLayout.setPanelState(cVar);
        updateViews(null, 1.0f);
    }
}
