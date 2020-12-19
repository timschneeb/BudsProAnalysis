package com.accessorydm.dmstarter;

import com.accessorydm.XDMSecReceiverApiCall;
import com.accessorydm.adapter.XDMCommonUtils;
import com.accessorydm.agent.XDMDebug;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBProfileListAdp;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.network.NetworkBlockedType;
import com.accessorydm.network.NetworkChecker;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.resume.XDMResumeStarter;
import com.accessorydm.ui.XUIAdapter;
import com.samsung.android.fotaprovider.log.Log;

public abstract class XDMSessionStarter implements XDMInitiator {
    private static int tempInitType;

    /* access modifiers changed from: package-private */
    public abstract boolean isDmStartAvailable();

    /* access modifiers changed from: package-private */
    public abstract void setUiModeWithInitType();

    /* access modifiers changed from: package-private */
    public void showUiForDmConnecting() {
    }

    public static XDMSessionStarter forInitiateType(XCommonInterface.INIT_TYPE init_type) {
        if (!XDMInitExecutor.getInstance().isDmInitializedSuccessfully()) {
            XDMInitExecutor.getInstance().executeInitializeService();
        }
        XDMSecReceiverApiCall.getInstance().xdmSetDeviceCheckInfoState(init_type);
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE[init_type.ordinal()];
        if (i == 1) {
            return new UserInit(null);
        }
        if (i == 2) {
            return new DeviceInit(null);
        }
        if (i == 3) {
            return new ServerInit(null);
        }
        Log.I("unexpected type : " + init_type + " handle as : " + XCommonInterface.INIT_TYPE.INIT_TYPE_PULL);
        return new UserInit(null);
    }

    /* renamed from: com.accessorydm.dmstarter.XDMSessionStarter$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE = new int[XCommonInterface.INIT_TYPE.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE[] r0 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.dmstarter.XDMSessionStarter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE = r0
                int[] r0 = com.accessorydm.dmstarter.XDMSessionStarter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE r1 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.INIT_TYPE_PULL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.dmstarter.XDMSessionStarter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE r1 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.INIT_TYPE_POLLING     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.dmstarter.XDMSessionStarter.AnonymousClass1.$SwitchMap$com$accessorydm$interfaces$XCommonInterface$INIT_TYPE     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.interfaces.XCommonInterface$INIT_TYPE r1 = com.accessorydm.interfaces.XCommonInterface.INIT_TYPE.INIT_TYPE_PUSH     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.dmstarter.XDMSessionStarter.AnonymousClass1.<clinit>():void");
        }
    }

    /* access modifiers changed from: package-private */
    public void setTemporaryInitType(int i) {
        tempInitType = i;
    }

    public static void onStartSession() {
        PostponeManager.cancelPostpone();
        XDBProfileListAdp.xdbSetProfileIndex(1);
        XDBFumoAdp.xdbSetFUMOInitiatedType(tempInitType);
        if (XUIAdapter.xuiAdpStartSession()) {
            Log.I(">>>>>>>>>>   Session Start   <<<<<<<<<<");
        }
    }

    @Override // com.accessorydm.dmstarter.XDMInitiator
    public void dmInitExecute() {
        Log.I("DM Execute Starter : " + getClass().getSimpleName());
        setUiModeWithInitType();
        NetworkBlockedType networkBlockType = NetworkChecker.get().getNetworkBlockType();
        if (networkBlockType.isBlocked()) {
            showUiFor(networkBlockType);
        } else if (resumeRemainingSession()) {
            Log.I("resume successfully");
        } else if (!isDmStartAvailable()) {
            Log.I("DM can not start");
        } else {
            XDMCommonUtils.xdmLoadlogflag();
            if (XDMFileTransferManager.checkDeviceInfo()) {
                showUiForDmConnecting();
            }
            XDMDebug.xdmSetBooting(false);
        }
    }

    /* access modifiers changed from: package-private */
    public void showUiFor(NetworkBlockedType networkBlockedType) {
        Log.I(networkBlockedType + " not Show anything in background mode");
    }

    private boolean resumeRemainingSession() {
        Log.I("");
        return XDMResumeStarter.USERINIT_RESUME.resumeExecute();
    }

    private static class UserInit extends XDMSessionStarter {
        private UserInit() {
        }

        /* synthetic */ UserInit(AnonymousClass1 r1) {
            this();
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public void setUiModeWithInitType() {
            XDBFumoAdp.xdbSetUiMode(1);
            setTemporaryInitType(1);
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public boolean isDmStartAvailable() {
            return XDMSecReceiverApiCall.getInstance().isForegroundAvailable();
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public void showUiForDmConnecting() {
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DM_UIEVENT.XUI_DM_CHECKING_FOR_UPDATE);
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public void showUiFor(NetworkBlockedType networkBlockedType) {
            networkBlockedType.networkOperation(NetworkBlockedType.ShowUiType.GENERAL_NETWORK_UI_BLOCK);
        }
    }

    private static class DeviceInit extends XDMSessionStarter {
        private DeviceInit() {
        }

        /* synthetic */ DeviceInit(AnonymousClass1 r1) {
            this();
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public void setUiModeWithInitType() {
            XDBFumoAdp.xdbSetUiMode(2);
            setTemporaryInitType(2);
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public boolean isDmStartAvailable() {
            return XDMSecReceiverApiCall.getInstance().isBackgroundAvailable();
        }
    }

    private static class ServerInit extends XDMSessionStarter {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public void setUiModeWithInitType() {
        }

        private ServerInit() {
        }

        /* synthetic */ ServerInit(AnonymousClass1 r1) {
            this();
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.dmstarter.XDMSessionStarter
        public boolean isDmStartAvailable() {
            return XDMSecReceiverApiCall.getInstance().isBackgroundAvailable();
        }
    }
}
