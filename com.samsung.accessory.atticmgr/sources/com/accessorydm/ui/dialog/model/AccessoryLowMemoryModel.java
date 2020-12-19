package com.accessorydm.ui.dialog.model;

import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.ui.dialog.model.XUIDialogModel;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import com.sec.android.fotaprovider.R;
import java.util.Locale;

public final class AccessoryLowMemoryModel extends XUIDialogModel.StubOk {

    public enum State {
        DOWNLOAD,
        COPY,
        INSTALL
    }

    public AccessoryLowMemoryModel(State state) {
        super(title(), message(state));
    }

    private static String title() {
        return getString(R.string.STR_ACCESSORY_LOW_MEMORY_TITLE);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$accessorydm$ui$dialog$model$AccessoryLowMemoryModel$State = new int[State.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel$State[] r0 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryLowMemoryModel$State = r0
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryLowMemoryModel$State     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.State.DOWNLOAD     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryLowMemoryModel$State     // Catch:{ NoSuchFieldError -> 0x001f }
                com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.State.COPY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryLowMemoryModel$State     // Catch:{ NoSuchFieldError -> 0x002a }
                com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel$State r1 = com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.State.INSTALL     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.ui.dialog.model.AccessoryLowMemoryModel.AnonymousClass1.<clinit>():void");
        }
    }

    private static String message(State state) {
        int i;
        long j;
        int i2 = AnonymousClass1.$SwitchMap$com$accessorydm$ui$dialog$model$AccessoryLowMemoryModel$State[state.ordinal()];
        if (i2 == 1) {
            i = DeviceType.get().getTextType().getDownloadAccessoryLowMemoryMessageId();
            j = GeneralUtil.bytesToMegabytes(AccessoryController.getInstance().getAccessoryUtil().getNeededFreeSpaceForDownload(XDBFumoAdp.xdbGetObjectSizeFUMO()));
        } else if (i2 != 2) {
            i = DeviceType.get().getTextType().getInstallAccessoryLowMemoryMessageId();
            j = GeneralUtil.bytesToMegabytes(AccessoryController.getInstance().getAccessoryUtil().getNeededFreeSpaceForInstall(XDBFumoAdp.xdbGetObjectSizeFUMO()));
        } else {
            i = DeviceType.get().getTextType().getCopyAccessoryLowMemoryMessageId();
            j = GeneralUtil.bytesToMegabytes(AccessoryController.getInstance().getAccessoryUtil().getNeededFreeSpaceForCopy(XDBFumoAdp.xdbGetObjectSizeFUMO()));
        }
        String format = String.format(Locale.getDefault(), "%d", Long.valueOf(j));
        return String.format(getString(i), format, getString(R.string.STR_COMMON_MEGA_BYTE));
    }
}
