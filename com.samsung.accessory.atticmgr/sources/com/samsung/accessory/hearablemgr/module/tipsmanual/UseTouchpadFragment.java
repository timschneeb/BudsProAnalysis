package com.samsung.accessory.hearablemgr.module.tipsmanual;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.ui.VIListView;
import com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity;
import seccompat.android.util.Log;

public class UseTouchpadFragment extends Fragment implements TipsAndUserManualActivity.OnSelectedTipsFragment {
    private static final int NORMAL_COLOR = -2039584;
    private static final int PRIMARY_COLOR = Application.getContext().getResources().getColor(R.color.colorPrimary);
    private static final String TAG = "Attic_UseTouchpadFragment";
    private Context mContext;
    private TextView mDoubleTap;
    private NestedScrollView mNestedScrollView;
    private TextView mTap;
    private TextView mTapAndHold;
    private TextView mTripleTap;
    private VIListView mVIListView;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_tips_touchpad, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mVIListView = (VIListView) view.findViewById(R.id.vi_list_view);
        this.mVIListView.setMediaStartListener(new VIListView.MediaStartListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.UseTouchpadFragment.AnonymousClass1 */

            @Override // com.samsung.accessory.hearablemgr.common.ui.VIListView.MediaStartListener
            public void onMediaStart(VIListView vIListView, int i) {
                Log.d(UseTouchpadFragment.TAG, "onMediaStart() : " + i);
                if (i == 0) {
                    UseTouchpadFragment useTouchpadFragment = UseTouchpadFragment.this;
                    useTouchpadFragment.updateChildColor(useTouchpadFragment.mTap);
                    UseTouchpadFragment useTouchpadFragment2 = UseTouchpadFragment.this;
                    useTouchpadFragment2.scrollToChild(useTouchpadFragment2.mTap);
                } else if (i == 1) {
                    UseTouchpadFragment useTouchpadFragment3 = UseTouchpadFragment.this;
                    useTouchpadFragment3.updateChildColor(useTouchpadFragment3.mDoubleTap);
                    UseTouchpadFragment useTouchpadFragment4 = UseTouchpadFragment.this;
                    useTouchpadFragment4.scrollToChild(useTouchpadFragment4.mDoubleTap);
                } else if (i == 2) {
                    UseTouchpadFragment useTouchpadFragment5 = UseTouchpadFragment.this;
                    useTouchpadFragment5.updateChildColor(useTouchpadFragment5.mTripleTap);
                    UseTouchpadFragment useTouchpadFragment6 = UseTouchpadFragment.this;
                    useTouchpadFragment6.scrollToChild(useTouchpadFragment6.mTripleTap);
                } else if (i == 3) {
                    UseTouchpadFragment useTouchpadFragment7 = UseTouchpadFragment.this;
                    useTouchpadFragment7.updateChildColor(useTouchpadFragment7.mTapAndHold);
                    UseTouchpadFragment useTouchpadFragment8 = UseTouchpadFragment.this;
                    useTouchpadFragment8.scrollToChild(useTouchpadFragment8.mTapAndHold);
                }
            }
        });
        this.mVIListView.stop();
        this.mNestedScrollView = (NestedScrollView) view.findViewById(R.id.nested_scroll);
        this.mTap = (TextView) view.findViewById(R.id.text_tap);
        this.mDoubleTap = (TextView) view.findViewById(R.id.text_double_tap);
        this.mTripleTap = (TextView) view.findViewById(R.id.text_triple_tap);
        this.mTapAndHold = (TextView) view.findViewById(R.id.text_touch_and_hold);
        updateChildColor(this.mTap);
        view.setContentDescription(UiUtil.getAllTextWithChildView(getView()));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void scrollToChild(TextView textView) {
        int top = ((View) textView.getParent()).getTop() + textView.getTop();
        Log.d(TAG, "scrollTo : " + top);
        this.mNestedScrollView.smoothScrollTo(0, top);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateChildColor(TextView textView) {
        this.mTap.setTextColor(NORMAL_COLOR);
        this.mDoubleTap.setTextColor(NORMAL_COLOR);
        this.mTripleTap.setTextColor(NORMAL_COLOR);
        this.mTapAndHold.setTextColor(NORMAL_COLOR);
        textView.setTextColor(PRIMARY_COLOR);
    }

    @Override // com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity.OnSelectedTipsFragment
    public void onSelected(boolean z) {
        VIListView vIListView = this.mVIListView;
        if (vIListView != null) {
            if (z) {
                vIListView.start();
            } else {
                vIListView.reset();
            }
        }
    }
}
