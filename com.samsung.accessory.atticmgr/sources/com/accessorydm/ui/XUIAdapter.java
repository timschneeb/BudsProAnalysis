package com.accessorydm.ui;

import com.accessorydm.XDMDmUtils;
import com.accessorydm.adapter.XDMFeature;
import com.accessorydm.adapter.XDMInitAdapter;
import com.accessorydm.db.file.XDB;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XNOTIInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.tp.XTPAdapter;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NetworkUtil;
import com.sec.android.fotaprovider.R;

public class XUIAdapter implements XDMInterface, XNOTIInterface, XEventInterface, XFOTAInterface {
    private static boolean isReportingToServer = false;
    private static boolean isUserClick = false;

    public static void xuiAdpRequestNoti(XUIEventInterface.DM_UIEVENT dm_uievent) {
        Log.I("");
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT[dm_uievent.ordinal()];
        if (i != 1 && i != 2) {
            int i2 = 4;
            if ((i == 3 || i == 4) && XDB.xdbCheckProfileListExist()) {
                Log.I("XUI_DM_NOTI_INTERACTIVE");
                if (dm_uievent == XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_INFORMATIVE) {
                    i2 = 3;
                }
                XDBProfileListAdp.xdbSetNotiEvent(i2);
                if (!xuiAdpStartSession()) {
                    return;
                }
                if (NetworkUtil.isWiFiNetworkConnected(XDMDmUtils.getContext())) {
                    XDBFumoAdp.xdbSetUiMode(2);
                } else {
                    XDBFumoAdp.xdbSetUiMode(1);
                }
            }
        } else if (XDB.xdbCheckProfileListExist()) {
            XDBProfileListAdp.xdbSetNotiEvent(2);
            if (xuiAdpStartSession()) {
                XDBFumoAdp.xdbSetUiMode(2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.ui.XUIAdapter$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT = new int[XUIEventInterface.DM_UIEVENT.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.accessorydm.interfaces.XUIEventInterface$DM_UIEVENT[] r0 = com.accessorydm.interfaces.XUIEventInterface.DM_UIEVENT.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.ui.XUIAdapter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT = r0
                int[] r0 = com.accessorydm.ui.XUIAdapter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XUIEventInterface$DM_UIEVENT r1 = com.accessorydm.interfaces.XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_NOT_SPECIFIED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.ui.XUIAdapter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XUIEventInterface$DM_UIEVENT r1 = com.accessorydm.interfaces.XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_BACKGROUND     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.ui.XUIAdapter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XUIEventInterface$DM_UIEVENT r1 = com.accessorydm.interfaces.XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_INFORMATIVE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = com.accessorydm.ui.XUIAdapter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XUIEventInterface$DM_UIEVENT     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.accessorydm.interfaces.XUIEventInterface$DM_UIEVENT r1 = com.accessorydm.interfaces.XUIEventInterface.DM_UIEVENT.XUI_DM_NOTI_INTERACTIVE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.ui.XUIAdapter.AnonymousClass1.<clinit>():void");
        }
    }

    public static boolean xuiAdpStartSession() {
        Log.I("");
        if (XDBFumoAdp.xdbGetFUMOCheckRooting()) {
            Log.E("Watch Rooting, return");
            XDMEvent.XDMSetEvent(null, XUIEventInterface.ACCESSORY_UIEVENT.XUI_DM_ACCESSORY_SYSSCOPE_MODIFIED);
            return false;
        }
        int xdmInitAdpCheckNetworkReady = XDMInitAdapter.xdmInitAdpCheckNetworkReady();
        if (xdmInitAdpCheckNetworkReady != 0) {
            if (xdmInitAdpCheckNetworkReady == 3) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
            }
            return false;
        }
        xuiAdpSetReportingToServer(false);
        xuiAdpSetUserClick(false);
        XTPAdapter.xtpAdpResetWBXMLLog();
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_CONNECT, null, null);
        return true;
    }

    public static void xuiAdpSetReportingToServer(boolean z) {
        isReportingToServer = z;
        Log.I("isReportingToServer = " + isReportingToServer);
    }

    public static boolean xuiAdpIsReportingToServer() {
        Log.I("isReportingToServer = " + isReportingToServer);
        return isReportingToServer;
    }

    public static int xuiAdpGetStrNetworkDisable() {
        return XDMFeature.XDM_FEATURE_WIFI_ONLY_MODEL ? R.string.STR_ACCESSORY_DOWNLOAD_FAILED_WIFI_DISCONNECTED : R.string.STR_DM_UNABLE_NETWORK;
    }

    public static void xuiAdpSetUserClick(boolean z) {
        isUserClick = z;
        Log.I("isUserClick = " + isUserClick);
    }

    public static boolean xuiAdpIsUserClick() {
        Log.I("isUserClick = " + isUserClick);
        return isUserClick;
    }
}
