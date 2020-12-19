package com.accessorydm.ui.progress;

import com.accessorydm.ui.progress.XUIProgressContract;

public class XUIDownloadProgressPresenter extends XUIProgressPresenter {
    XUIDownloadProgressPresenter(XUIProgressContract.View view, XUIProgressModel xUIProgressModel) {
        super(view, xUIProgressModel);
        xUIProgressModel.setProgressMode(1);
    }
}
