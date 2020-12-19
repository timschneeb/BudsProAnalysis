package com.accessorydm.ui.notification.manager;

import com.accessorydm.ui.notification.manager.NotificationBuilderStrategy;
import com.samsung.android.fotaprovider.util.type.DeviceType;

/* renamed from: com.accessorydm.ui.notification.manager.-$$Lambda$NotificationBuilderStrategy$SmallIcon$MbCEI-wkHRQ0U-H5-R7hZFhuvTo  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$NotificationBuilderStrategy$SmallIcon$MbCEIwkHRQ0UH5R7hZFhuvTo implements NotificationBuilderStrategy.SmallIcon {
    public static final /* synthetic */ $$Lambda$NotificationBuilderStrategy$SmallIcon$MbCEIwkHRQ0UH5R7hZFhuvTo INSTANCE = new $$Lambda$NotificationBuilderStrategy$SmallIcon$MbCEIwkHRQ0UH5R7hZFhuvTo();

    private /* synthetic */ $$Lambda$NotificationBuilderStrategy$SmallIcon$MbCEIwkHRQ0UH5R7hZFhuvTo() {
    }

    @Override // com.accessorydm.ui.notification.manager.NotificationBuilderStrategy.SmallIcon
    public final int getSmallIcon() {
        return DeviceType.get().getNotificationIconType().getIndicatorCompletion();
    }
}
