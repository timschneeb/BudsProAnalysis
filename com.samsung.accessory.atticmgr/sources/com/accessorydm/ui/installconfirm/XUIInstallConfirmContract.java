package com.accessorydm.ui.installconfirm;

import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.accessorydm.ui.fullscreen.content.BottomContentView;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;

public interface XUIInstallConfirmContract {

    public interface Presenter extends XUIBaseFullscreenContract.Presenter {
        void doFirstButtonAction();

        void doSecondButtonAction();

        void onResume();

        void onUserLeaveHint();
    }

    public interface View extends XUIBaseFullscreenContract.View {
        void finish();

        BottomContentView.TwoButtons getBottomContentView();

        MiddleContentView.WithCaution getMiddleContentView();

        TopContentView.Guide getTopContentView();

        void runOnUiThread(Runnable runnable);

        void xuiSetBottomButtonsClickListeners();

        void xuiShowScheduleInstallDialog();
    }
}
