package com.samsung.android.app.watchmanager.setupwizard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ViewFlipper;
import com.samsung.android.app.twatchmanager.manager.ResourceRulesManager;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import com.samsung.android.app.twatchmanager.util.AnimationHelper;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import com.samsung.android.app.watchmanager.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BackgroundFragment extends Fragment implements SlidingUpPanelLayout.b {
    private static final int DURATION = 3000;
    private static final String TAG = ("tUHM:" + BackgroundFragment.class.getSimpleName());
    private int connectCase = 1;
    private ArrayList<AnimationHelper> helperList = new ArrayList<>();
    private SlidingUpPanelLayout.c mPanelState = SlidingUpPanelLayout.c.COLLAPSED;
    private ShowNextHandler mShowNextHandler;
    float prevOffset = 0.0f;
    private ViewFlipper viewFlipper;

    public interface ConnectCase {
        void sendPanelState(SlidingUpPanelLayout.c cVar);
    }

    /* access modifiers changed from: private */
    public class ShowNextHandler extends Handler {
        private ShowNextHandler() {
        }

        public void handleMessage(Message message) {
            BackgroundFragment.this.viewFlipper.showNext();
        }
    }

    private void addResourceToView(String str, HashMap<String, String> hashMap) {
        int drawableIdFromFileName = UIUtils.getDrawableIdFromFileName(getActivity(), str);
        if (drawableIdFromFileName > 0) {
            int ySlideValue = getYSlideValue(hashMap);
            int bgHeight = getBgHeight(hashMap);
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(drawableIdFromFileName);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);
            this.viewFlipper.addView(imageView);
            this.helperList.add(new AnimationHelper(getActivity(), this.viewFlipper, bgHeight, ySlideValue));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void connectNewDevice() {
        stopAnimation();
        Activity activity = getActivity();
        if (activity != null) {
            ((ConnectCase) activity).sendPanelState(SlidingUpPanelLayout.c.EXPANDED);
        }
        updateViews(null, 1.0f);
    }

    private int getBgHeight(HashMap<String, String> hashMap) {
        try {
            return Integer.parseInt(hashMap.get(GroupInfo.ImageInfo.ATTR_HEIGHT));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getYSlideValue(HashMap<String, String> hashMap) {
        try {
            return Integer.parseInt(hashMap.get(GroupInfo.ImageInfo.ATTR_YSLIDE_VALUE));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void updateViews(View view, float f) {
        int displayedChild = this.viewFlipper.getDisplayedChild();
        if (this.helperList.size() > displayedChild) {
            this.helperList.get(displayedChild).animate(f, this.connectCase);
        }
    }

    public void initBackgroundResources() {
        if (ResourceRulesManager.getInstance().isResourceInfoAvailable()) {
            Iterator<GroupInfo.ImageInfo> it = ResourceRulesManager.getInstance().getImageListByType(GroupInfo.InfoType.BACKGROUND).iterator();
            while (it.hasNext()) {
                GroupInfo.ImageInfo next = it.next();
                addResourceToView(next.name, next.attributes);
            }
            startAnimationWithDelay();
        }
    }

    @Override // android.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_background, viewGroup, false);
        this.viewFlipper = (ViewFlipper) inflate.findViewById(R.id.view_flipper);
        ((ScrollView) inflate.findViewById(R.id.scroll_view)).setOnTouchListener(new View.OnTouchListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment.AnonymousClass1 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        if (UIUtils.isLandScapeMode(getActivity())) {
            UIUtils.setWidthForTablet(getActivity(), this.viewFlipper);
        }
        this.mShowNextHandler = new ShowNextHandler();
        this.connectCase = getArguments().getInt(SetupWizardWelcomeActivity.EXTRA_CONNECT_CASE, 1);
        this.helperList.clear();
        Animation loadAnimation = AnimationUtils.loadAnimation(getActivity(), 17432576);
        Animation loadAnimation2 = AnimationUtils.loadAnimation(getActivity(), 17432577);
        loadAnimation2.setAnimationListener(new Animation.AnimationListener() {
            /* class com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment.AnonymousClass2 */

            public void onAnimationEnd(Animation animation) {
                if (BackgroundFragment.this.connectCase != 2) {
                    BackgroundFragment.this.startAnimationWithDelay();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                if (BackgroundFragment.this.connectCase == 2) {
                    BackgroundFragment.this.connectNewDevice();
                }
            }
        });
        this.viewFlipper.setInAnimation(loadAnimation);
        this.viewFlipper.setOutAnimation(loadAnimation2);
        return inflate;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    @Override // com.sothree.slidinguppanel.SlidingUpPanelLayout.b
    public void onPanelSlide(View view, float f) {
        stopAnimation();
        if (Math.abs(this.prevOffset - f) <= 0.5f) {
            this.prevOffset = f;
            updateViews(view, f);
        }
    }

    @Override // com.sothree.slidinguppanel.SlidingUpPanelLayout.b
    public void onPanelStateChanged(View view, SlidingUpPanelLayout.c cVar, SlidingUpPanelLayout.c cVar2) {
        int displayedChild;
        this.mPanelState = cVar2;
        if (cVar2 == SlidingUpPanelLayout.c.EXPANDED) {
            stopAnimation();
        } else if (cVar2 == SlidingUpPanelLayout.c.COLLAPSED && this.helperList.size() > (displayedChild = this.viewFlipper.getDisplayedChild())) {
            this.helperList.get(displayedChild).animate(0.0f);
            startAnimationWithDelay();
        }
    }

    public void onPause() {
        super.onPause();
        stopAnimation();
    }

    public void onResume() {
        super.onResume();
        if (this.mPanelState == SlidingUpPanelLayout.c.COLLAPSED) {
            startAnimationWithDelay();
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (ResourceRulesManager.getInstance().isResourceInfoAvailable()) {
            ResourceRulesManager.getInstance().initImageMapArray();
            initBackgroundResources();
            return;
        }
        ResourceRulesManager.getInstance().syncGearInfo(1, new ResourceRulesManager.ISyncCallback() {
            /* class com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment.AnonymousClass3 */

            @Override // com.samsung.android.app.twatchmanager.manager.ResourceRulesManager.ISyncCallback
            public void onSyncComplete(int i, boolean z) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment.AnonymousClass3.AnonymousClass1 */

                    public void run() {
                        if (BackgroundFragment.this.getActivity() != null && BackgroundFragment.this.isAdded()) {
                            BackgroundFragment.this.initBackgroundResources();
                        }
                    }
                });
            }
        });
    }

    public void startAnimationWithDelay() {
        ViewFlipper viewFlipper2 = this.viewFlipper;
        if (viewFlipper2 == null || viewFlipper2.getChildCount() > 0) {
            ViewFlipper viewFlipper3 = this.viewFlipper;
            if (viewFlipper3 == null || viewFlipper3.getChildCount() != 1) {
                if (this.connectCase == 1 && !this.mShowNextHandler.hasMessages(0)) {
                    this.mShowNextHandler.sendEmptyMessageDelayed(0, 3000);
                } else if (this.connectCase == 2) {
                    this.mShowNextHandler.sendEmptyMessage(0);
                }
            } else if (HostManagerUtils.isTablet() && this.connectCase == 2) {
                new Handler().post(new Runnable() {
                    /* class com.samsung.android.app.watchmanager.setupwizard.BackgroundFragment.AnonymousClass4 */

                    public void run() {
                        BackgroundFragment.this.connectNewDevice();
                    }
                });
            }
        }
    }

    public void stopAnimation() {
        this.mShowNextHandler.removeCallbacksAndMessages(null);
    }
}
