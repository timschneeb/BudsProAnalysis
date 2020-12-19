package com.accessorydm.ui.notification.manager;

import com.accessorydm.ui.notification.manager.NotificationBuilderStrategy;
import com.samsung.android.fotaprovider.util.type.DeviceType;

/* renamed from: com.accessorydm.ui.notification.manager.-$$Lambda$NotificationBuilderStrategy$SmallIcon$s2CAQzGcEiRF6srC_6ZggTXsveU  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$NotificationBuilderStrategy$SmallIcon$s2CAQzGcEiRF6srC_6ZggTXsveU implements NotificationBuilderStrategy.SmallIcon {
    public static final /* synthetic */ $$Lambda$NotificationBuilderStrategy$SmallIcon$s2CAQzGcEiRF6srC_6ZggTXsveU INSTANCE = new $$Lambda$NotificationBuilderStrategy$SmallIcon$s2CAQzGcEiRF6srC_6ZggTXsveU();

    private /* synthetic */ $$Lambda$NotificationBuilderStrategy$SmallIcon$s2CAQzGcEiRF6srC_6ZggTXsveU() {
    }

    @Override // com.accessorydm.ui.notification.manager.NotificationBuilderStrategy.SmallIcon
    public final int getSmallIcon() {
        return DeviceType.get().getNotificationIconType().getIndicatorDmSession();
    }
}
