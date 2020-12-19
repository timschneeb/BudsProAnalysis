package com.accessorydm.ui.dialog.model;

import com.accessorydm.filetransfer.XDMFileTransferManager;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy;
import com.samsung.android.fotaprovider.util.type.DeviceType;

public final class AccessoryCopyExceptionModel extends XUIDialogModel.Base {

    public enum State {
        FAILED,
        RETRY_LATER,
        RETRY_LATER_ACCESSORY_UNCOUPLED,
        RETRY_CONFIRM
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AccessoryCopyExceptionModel(State state) {
        super(title(state), message(state), ButtonStrategy.Neutral.NONE, state == State.RETRY_CONFIRM ? new ButtonStrategy.StubCancel() : ButtonStrategy.Negative.NONE, state == State.RETRY_CONFIRM ? new RetryPositiveButtonStrategy() : new ButtonStrategy.StubOk());
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel$State[] r0 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State = r0
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.State.RETRY_LATER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.State.RETRY_LATER_ACCESSORY_UNCOUPLED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.State.RETRY_CONFIRM     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.State.FAILED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.ui.dialog.model.AccessoryCopyExceptionModel.AnonymousClass1.<clinit>():void");
        }
    }

    private static String title(State state) {
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State[state.ordinal()];
        if (i == 1 || i == 2) {
            return getString(DeviceType.get().getTextType().getCopyRetryLaterTitleId());
        }
        if (i != 3) {
            return getString(DeviceType.get().getTextType().getCopyFailedTitleId());
        }
        return getString(DeviceType.get().getTextType().getCopyRetryTitleId());
    }

    private static String message(State state) {
        int i = AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryCopyExceptionModel$State[state.ordinal()];
        if (i == 1) {
            return getString(DeviceType.get().getTextType().getCopyRetryLaterMessageId());
        }
        if (i == 2) {
            return getString(DeviceType.get().getTextType().getCopyRetryLaterAccessoryUncoupledMessageId());
        }
        if (i != 3) {
            return getString(DeviceType.get().getTextType().getCopyFailedMessageId());
        }
        return getString(DeviceType.get().getTextType().getCopyRetryMessageId());
    }

    private static class RetryPositiveButtonStrategy extends ButtonStrategy.Positive {
        RetryPositiveButtonStrategy() {
            super(XUIDialogModel.Base.getString(DeviceType.get().getTextType().getCopyRetryPositiveButtonId()));
        }

        /* access modifiers changed from: protected */
        @Override // com.accessorydm.ui.dialog.model.buttonstrategy.AbstractButtonStrategy
        public void doOnClick() {
            XDMFileTransferManager.checkDeviceInfo();
        }
    }
}
