package com.accessorydm.ui.progress.listener;

import com.accessorydm.agent.fota.XFOTADlAgentHandler;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.ui.dialog.XUIDialog;
import com.accessorydm.ui.handler.XDMServiceHandler;
import com.accessorydm.ui.progress.XUIProgressModel;
import com.accessorydm.ui.progress.listener.XUIProgressListener;
import com.samsung.android.fotaprovider.log.Log;

public class XUIProgressRangeCheckListener extends XUIProgressBaseListener {
    public XUIProgressRangeCheckListener() {
        super(XUIProgressListener.ListenerPriority.RangeCheckIsAlwaysLast);
    }

    @Override // com.accessorydm.ui.progress.listener.XUIProgressListener
    public void onProgressInfoUpdated() {
        if (isDoneProgress()) {
            XUIProgressModel.getInstance().setProgressMode(0);
        }
        if (isOutOfRange()) {
            Log.W("not in progress range, so finish progress");
            int xdbGetFUMOStatus = XDBFumoAdp.xdbGetFUMOStatus();
            if (xdbGetFUMOStatus == 30) {
                XFOTADlAgentHandler.xfotaDlAgentHdlrStartOMADLAgent(XEventInterface.XEVENT.XEVENT_DL_DELTA_SIZE_ERROR_DOWNLOAD);
                if (XDBFumoAdp.xdbGetUiMode() == 1) {
                    XDMServiceHandler.xdmSendMessageDmHandler(XUIDialog.DL_INVALID_DELTA);
                }
            } else if (xdbGetFUMOStatus == 250) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_COPY_FAILED);
                XDBFumoAdp.xdbSetFUMOInitiatedType(0);
            }
        }
    }

    private boolean isDoneProgress() {
        return XUIProgressModel.getInstance().getProgressPercent() >= 100;
    }
}
