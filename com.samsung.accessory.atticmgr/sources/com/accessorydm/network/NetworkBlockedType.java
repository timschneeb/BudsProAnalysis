package com.accessorydm.network;

import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.ui.XUIAdapter;
import com.accessorydm.ui.handler.XDMToastHandler;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.OperatorUtil;

public enum NetworkBlockedType {
    NO_BLOCKING {
        @Override // com.accessorydm.network.NetworkBlockedType
        public boolean isBlocked() {
            return false;
        }
    },
    ROAMING {
        @Override // com.accessorydm.network.NetworkBlockedType
        public void networkOperation(ShowUiType showUiType) {
            Log.I("1-1 ROAMING : " + showUiType);
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_ROAMING_WIFI_DISCONNECTED);
        }
    },
    WIFI_DISCONNECTED {
        @Override // com.accessorydm.network.NetworkBlockedType
        public void networkOperation(ShowUiType showUiType) {
            Log.I("1-2 WIFI_DISCONNECTED : " + showUiType);
            int i = AnonymousClass5.$SwitchMap$com$accessorydm$network$NetworkBlockedType$ShowUiType[showUiType.ordinal()];
            if (i == 1) {
                XDMToastHandler.xdmShowToast(OperatorUtil.replaceToWLAN(XUIAdapter.xuiAdpGetStrNetworkDisable()), 0);
            } else if (i == 2) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_FAILED_WIFI_DISCONNECTED);
            }
        }
    },
    NETWORK_DISCONNECTED {
        @Override // com.accessorydm.network.NetworkBlockedType
        public void networkOperation(ShowUiType showUiType) {
            Log.I("1-3 NETWORK_DISCONNECTED : " + showUiType);
            int i = AnonymousClass5.$SwitchMap$com$accessorydm$network$NetworkBlockedType$ShowUiType[showUiType.ordinal()];
            if (i == 1) {
                XDMToastHandler.xdmShowToast(OperatorUtil.replaceToWLAN(XUIAdapter.xuiAdpGetStrNetworkDisable()), 0);
            } else if (i == 2) {
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_DOWNLOAD_FAILED_NETWORK_DISCONNECTED);
            }
        }
    };

    public enum ShowUiType {
        GENERAL_NETWORK_UI_BLOCK,
        DOWNLOAD_NETWORK_UI_BLOCK
    }

    public boolean isBlocked() {
        return true;
    }

    public void networkOperation(ShowUiType showUiType) {
    }

    /* renamed from: com.accessorydm.network.NetworkBlockedType$5  reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$network$NetworkBlockedType$ShowUiType = new int[ShowUiType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.accessorydm.network.NetworkBlockedType$ShowUiType[] r0 = com.accessorydm.network.NetworkBlockedType.ShowUiType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.network.NetworkBlockedType.AnonymousClass5.$SwitchMap$com$accessorydm$network$NetworkBlockedType$ShowUiType = r0
                int[] r0 = com.accessorydm.network.NetworkBlockedType.AnonymousClass5.$SwitchMap$com$accessorydm$network$NetworkBlockedType$ShowUiType     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.network.NetworkBlockedType$ShowUiType r1 = com.accessorydm.network.NetworkBlockedType.ShowUiType.GENERAL_NETWORK_UI_BLOCK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.network.NetworkBlockedType.AnonymousClass5.$SwitchMap$com$accessorydm$network$NetworkBlockedType$ShowUiType     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.network.NetworkBlockedType$ShowUiType r1 = com.accessorydm.network.NetworkBlockedType.ShowUiType.DOWNLOAD_NETWORK_UI_BLOCK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.network.NetworkBlockedType.AnonymousClass5.<clinit>():void");
        }
    }
}
