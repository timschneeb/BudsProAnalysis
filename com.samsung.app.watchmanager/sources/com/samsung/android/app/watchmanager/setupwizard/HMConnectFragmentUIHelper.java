package com.samsung.android.app.watchmanager.setupwizard;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import com.samsung.android.app.watchmanager.R;

public class HMConnectFragmentUIHelper {
    public static final String CURRENT_DOWNLOADED_SIZE = "2";
    public static final String DOWNLOAD_PERCENTAGE = "5";
    public static final String PLUGIN_DOWNLOAD_SIZE = "6";
    private static final String TAG = ("tUHM:" + HMConnectFragmentUIHelper.class.getSimpleName());
    public static final String TOTAL_DOWNLOAD_SIZE = "1";
    private boolean isDarkNavigation = true;
    private Context mContext;
    private RelativeLayout transitionLayout;
    private RelativeLayout transitionLayoutSwitching;
    private View view;

    public HMConnectFragmentUIHelper(Context context, View view2) {
        this.mContext = context;
        this.view = view2;
        this.transitionLayout = (RelativeLayout) view2.findViewById(R.id.transtition_view);
        this.transitionLayoutSwitching = (RelativeLayout) view2.findViewById(R.id.transtition_view_switching);
    }

    public View getView() {
        return this.view;
    }
}
