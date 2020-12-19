package com.accessorydm.ui.notification.manager;

import com.accessorydm.ui.notification.manager.NotificationBuilderStrategy;
import com.samsung.android.fotaprovider.util.type.DeviceType;

/* renamed from: com.accessorydm.ui.notification.manager.-$$Lambda$NotificationBuilderStrategy$SmallIcon$TfpOHLNFu3xZ1HfmHH-0y_tXY1c  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$NotificationBuilderStrategy$SmallIcon$TfpOHLNFu3xZ1HfmHH0y_tXY1c implements NotificationBuilderStrategy.SmallIcon {
    public static final /* synthetic */ $$Lambda$NotificationBuilderStrategy$SmallIcon$TfpOHLNFu3xZ1HfmHH0y_tXY1c INSTANCE = new $$Lambda$NotificationBuilderStrategy$SmallIcon$TfpOHLNFu3xZ1HfmHH0y_tXY1c();

    private /* synthetic */ $$Lambda$NotificationBuilderStrategy$SmallIcon$TfpOHLNFu3xZ1HfmHH0y_tXY1c() {
    }

    @Override // com.accessorydm.ui.notification.manager.NotificationBuilderStrategy.SmallIcon
    public final int getSmallIcon() {
        return DeviceType.get().getNotificationIconType().getIndicatorPostpone();
    }
}
