package com.accessorydm.ui.progress;

import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;

public interface XUIProgressContract {

    public interface Presenter extends XUIBaseFullscreenContract.Presenter {
    }

    public interface View extends XUIBaseFullscreenContract.View {
        MiddleContentView.WithoutCaution getMiddleContentView();

        TopContentView.Progress getTopContentView();

        void runOnUiThread(Runnable runnable);
    }
}
