package com.accessorydm.ui.noupdatable;

import com.accessorydm.ui.fullscreen.basefullscreen.XUIBaseFullscreenContract;
import com.accessorydm.ui.fullscreen.content.MiddleContentView;
import com.accessorydm.ui.fullscreen.content.TopContentView;

public interface XUINoUpdatableVersionContract {

    public interface Presenter extends XUIBaseFullscreenContract.Presenter {
    }

    public interface View extends XUIBaseFullscreenContract.View {
        MiddleContentView.WithoutCaution getMiddleContentView();

        TopContentView.Guide getTopContentView();
    }
}
