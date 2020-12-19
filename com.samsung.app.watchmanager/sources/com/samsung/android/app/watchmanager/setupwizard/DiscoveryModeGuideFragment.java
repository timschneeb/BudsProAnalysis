package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.SALogUtil;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity;

public class DiscoveryModeGuideFragment extends Fragment implements OnBackKeyListener, SetupWizardWelcomeActivity.IMultiWindowListener {
    private static final String TAG = ("tUHM:" + DiscoveryModeGuideFragment.class.getSimpleName());
    private String currentClickGroup = "";
    private RelativeLayout mActionbarLayout;
    private Activity mActivity;
    private Bundle mBundle;
    private TextView mContactUs;
    private RelativeLayout mItemContainer;
    private RelativeLayout mLogoHeaderLayout;
    private ImageView mPreviewImage;
    private TextView mTitleTextView;

    private void popCurrentFragment() {
        Log.d(TAG, "popCurrentFragment");
        try {
            if (getActivity() == null) {
                return;
            }
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else if (getActivity() != null && (getActivity() instanceof SetupWizardWelcomeActivity)) {
                getActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startDeviceList() {
        Activity activity = getActivity();
        if (activity instanceof SetupWizardWelcomeActivity) {
            SetupWizardWelcomeActivity setupWizardWelcomeActivity = (SetupWizardWelcomeActivity) activity;
            this.mBundle.putBoolean(GlobalConst.EXTRA_FROM_DEVICE_DISCOVERY_GUIDE_FRAGMENT, true);
            setupWizardWelcomeActivity.setLaunchMode(GlobalConst.LAUNCH_MODE_DEVICE_LIST);
            setupWizardWelcomeActivity.updateFragment(4, this.mBundle);
        }
        popCurrentFragment();
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.OnBackKeyListener
    public boolean onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        startDeviceList();
        return true;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_discovery_mode_guide, viewGroup, false);
        UIUtils.adjustLogoMargin(inflate.findViewById(R.id.gearManagerLogo));
        return inflate;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        ((FragmentLifecycleCallbacks) getActivity()).onFragmentDetached(1);
    }

    public void onResume() {
        super.onResume();
    }

    public void onViewCreated(View view, Bundle bundle) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, bundle);
        if (bundle == null) {
            bundle = getArguments() != null ? getArguments() : getActivity().getIntent().getExtras();
        }
        this.mBundle = bundle;
        UIUtils.adjustLogoMargin(view.findViewById(R.id.gearManagerLogo));
        this.mPreviewImage = (ImageView) view.findViewById(R.id.topView);
        this.mActionbarLayout = (RelativeLayout) view.findViewById(R.id.top_actionbar_title);
        this.mLogoHeaderLayout = (RelativeLayout) view.findViewById(R.id.top_image_title);
        this.mTitleTextView = (TextView) view.findViewById(R.id.no_device_found_title);
        this.mItemContainer = (RelativeLayout) view.findViewById(R.id.items_layout_container);
        this.mContactUs = (TextView) view.findViewById(R.id.contact_us);
        this.mContactUs.setText(!HostManagerUtils.isSamsungDevice() ? getActivity().getString(R.string.contact_us_faq) : "");
        TextView textView = this.mContactUs;
        textView.setPaintFlags(textView.getPaintFlags() | 8);
        this.mContactUs.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.DiscoveryModeGuideFragment.AnonymousClass1 */

            public void onClick(View view) {
                SALogUtil.insertSALog(SALogUtil.SA_LOG_SCREEN_WELCOME, SALogUtil.SA_LOG_EVENT_CONTACT_US, "Contact us");
                Log.d(DiscoveryModeGuideFragment.TAG, "OnClick Contact us");
                HostManagerUtils.startContactUsProcess(DiscoveryModeGuideFragment.this.getActivity());
            }
        });
        UIUtils.setProperWidth(getActivity(), this.mItemContainer);
        this.currentClickGroup = this.mBundle.getString(GlobalConst.GROUP_NAME_ARG);
        String str = TAG;
        Log.d(str, "currentClickGroup = " + this.currentClickGroup);
        UIUtils.setHeaderImageWithRules(getActivity(), this.currentClickGroup, this.mPreviewImage, view.findViewById(R.id.gearManagerLogo));
        this.mActivity = getActivity();
        boolean z = false;
        Activity activity = this.mActivity;
        if (activity != null && (activity instanceof SetupWizardWelcomeActivity)) {
            z = ((SetupWizardWelcomeActivity) getActivity()).getCurrentMultiWindowMode();
        }
        updateAfterMultiWindowChanged(z);
    }

    @Override // com.samsung.android.app.watchmanager.setupwizard.SetupWizardWelcomeActivity.IMultiWindowListener
    public void updateAfterMultiWindowChanged(boolean z) {
        Activity activity = getActivity();
        if (activity != null) {
            int dimension = (int) activity.getResources().getDimension(R.dimen.action_bar_h);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTitleTextView.getLayoutParams();
            if (z) {
                this.mActionbarLayout.setVisibility(0);
                this.mLogoHeaderLayout.setVisibility(8);
                marginLayoutParams.topMargin = dimension;
                this.mTitleTextView.setLayoutParams(marginLayoutParams);
                return;
            }
            this.mActionbarLayout.setVisibility(8);
            this.mLogoHeaderLayout.setVisibility(0);
        }
    }
}
