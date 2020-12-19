package com.samsung.accessory.hearablemgr.module.tipsmanual;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.ui.PageIndicatorView;
import com.samsung.accessory.hearablemgr.common.ui.UiUtil;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity;
import seccompat.android.util.Log;

public class TipsAndUserManualActivity extends PermissionCheckActivity {
    private static final String ONLINE_USER_MANUAL_URL = "http://www.samsung.com/m-manual/mod/SM-R190/nos";
    private static final String TAG = (Application.TAG_ + TipsAndUserManualActivity.class.getSimpleName());
    PageIndicatorView mPageIndicatorView;
    private int prevPosition = 0;
    private ViewPager.OnPageChangeListener setViewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        /* class com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity.AnonymousClass2 */

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            String str = TipsAndUserManualActivity.TAG;
            Log.d(str, "onPageSelected() prevPosition : " + TipsAndUserManualActivity.this.prevPosition + ", position  : " + i);
            TipsAndUserManualActivity.this.setNavigationFocus(i);
            UiUtil.awakeScrollbarWidthChildView(TipsAndUserManualActivity.this.viewPager.getChildAt(i));
            if (TipsAndUserManualActivity.this.tipsAdapter.getItem(TipsAndUserManualActivity.this.prevPosition) instanceof OnSelectedTipsFragment) {
                ((OnSelectedTipsFragment) TipsAndUserManualActivity.this.tipsAdapter.getItem(TipsAndUserManualActivity.this.prevPosition)).onSelected(false);
            }
            if (TipsAndUserManualActivity.this.tipsAdapter.getItem(i) instanceof OnSelectedTipsFragment) {
                ((OnSelectedTipsFragment) TipsAndUserManualActivity.this.tipsAdapter.getItem(i)).onSelected(true);
            }
            TipsAndUserManualActivity.this.prevPosition = i;
        }
    };
    TipsAdapter tipsAdapter;
    TextView userManual;
    ViewPager viewPager;

    interface OnSelectedTipsFragment {
        void onSelected(boolean z);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, com.samsung.accessory.hearablemgr.module.base.OrientationPolicyActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate()");
        super.onCreate(null);
        setContentView(R.layout.activity_tips_and_user_manual);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.tipsAdapter = new TipsAdapter(getSupportFragmentManager());
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.viewPager.setOffscreenPageLimit(5);
        this.viewPager.setAdapter(this.tipsAdapter);
        this.viewPager.addOnPageChangeListener(this.setViewPagerOnPageChangeListener);
        this.mPageIndicatorView = (PageIndicatorView) findViewById(R.id.page_indicator);
        this.mPageIndicatorView.setPageMax(this.tipsAdapter.getCount());
        this.userManual = (TextView) findViewById(R.id.user_manual);
        this.userManual.setOnClickListener(new View.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity.AnonymousClass1 */

            public void onClick(View view) {
                SamsungAnalyticsUtil.sendEvent(SA.Event.USER_MANUAL, SA.Screen.TIPS_AND_USER_MANUAL);
                TipsAndUserManualActivity.startUserManual(TipsAndUserManualActivity.this);
            }
        });
        this.viewPager.setCurrentItem(UiUtil.rtlCompatIndex(0, this.tipsAdapter.getCount()));
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.samsung.accessory.hearablemgr.module.base.PermissionCheckActivity
    public void onResume() {
        super.onResume();
        SamsungAnalyticsUtil.sendPage(SA.Screen.TIPS_AND_USER_MANUAL);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        SamsungAnalyticsUtil.sendEvent(SA.Event.UP_BUTTON, SA.Screen.TIPS_AND_USER_MANUAL);
        onBackPressed();
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setNavigationFocus(int i) {
        this.mPageIndicatorView.setPageSelect(i);
    }

    public static void startUserManual(Activity activity) {
        Log.d(TAG, "startUserManual()");
        if (Util.getActiveNetworkInfo() >= 0) {
            UiUtil.startWebBrowser(activity, ONLINE_USER_MANUAL_URL);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.no_network_connect));
        builder.setMessage(activity.getString(Util.isChinaModel() ? R.string.no_network_connect_description_chn : R.string.no_network_connect_description));
        builder.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            /* class com.samsung.accessory.hearablemgr.module.tipsmanual.TipsAndUserManualActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
