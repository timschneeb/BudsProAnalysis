package com.accessorydm.ui.lastupdate;

import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;

class XUILastUpdateContract {

    interface Presenter extends XUIBaseFullscreenContract.Presenter {
    }

    interface View extends XUIBaseFullscreenContract.View {
        MiddleContentView.WithoutCaution getMiddleContentView();

        TopContentView.Guide getTopContentView();
    }

    XUILastUpdateContract() {
    }
}
