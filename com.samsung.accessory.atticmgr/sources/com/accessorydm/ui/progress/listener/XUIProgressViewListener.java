package com.accessorydm.ui.progress.listener;

import com.accessorydm.ui.progress.XUIProgressContract;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.accessorydm.ui.progress.listener.XUIProgressListener;
import com.samsung.android.fotaprovider.log.Log;

public class XUIProgressViewListener extends XUIProgressBaseListener {
    private XUIProgressContract.View view;

    public XUIProgressViewListener(XUIProgressContract.View view2) {
        super(XUIProgressListener.ListenerPriority.View);
        this.view = view2;
    }

    @Override // com.accessorydm.ui.progress.listener.XUIProgressListener
    public void onProgressInfoUpdated() {
        if (this.view == null) {
            Log.W("view is null, so not update progress ui");
        } else if (isOutOfRange()) {
            Log.W("not in progress range");
        } else {
            this.view.runOnUiThread(new Runnable() {
                /* class com.accessorydm.ui.progress.listener.$$Lambda$XUIProgressViewListener$lofB20_j_4Ldkl_Cghe6TWdRPkg */

                public final void run() {
                    XUIProgressViewListener.this.lambda$onProgressInfoUpdated$0$XUIProgressViewListener();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onProgressInfoUpdated$0$XUIProgressViewListener() {
        this.view.getTopContentView().setIndeterminateProgressbar(false);
        this.view.getTopContentView().setProgressbarProgress(XUIProgressModel.getInstance().getProgressPercent());
        this.view.getTopContentView().setSizeText(XUIProgressModel.getInstance().getProgressSizeText());
    }
}
