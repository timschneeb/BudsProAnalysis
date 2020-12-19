package com.accessorydm.ui.downloadconfirm;

import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.accessorydm.ui.fullscreen.content.BottomContentView;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;

public interface XUIDownloadConfirmContract {

    public interface Presenter extends XUIBaseFullscreenContract.Presenter {
        void doFirstButtonAction();

        void doSecondButtonAction();
    }

    public interface View extends XUIBaseFullscreenContract.View {
        void finish();

        BottomContentView.TwoButtons getBottomContentView();

        MiddleContentView.WithoutCaution getMiddleContentView();

        TopContentView.Guide getTopContentView();

        void xuiSetBottomButtonsClickListeners();
    }
}
