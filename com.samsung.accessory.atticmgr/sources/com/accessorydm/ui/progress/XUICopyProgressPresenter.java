package com.accessorydm.ui.progress;

import com.accessorydm.ui.progress.XUIProgressContract;

public class XUICopyProgressPresenter extends XUIProgressPresenter {
    XUICopyProgressPresenter(XUIProgressContract.View view, XUIProgressModel xUIProgressModel) {
        super(view, xUIProgressModel);
        xUIProgressModel.setProgressMode(2);
    }

    @Override // com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract.Presenter, com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenPresenter
    public void onCreate() {
        super.onCreate();
        this.view.getTopContentView().setIndeterminateProgressbar(true);
    }
}
