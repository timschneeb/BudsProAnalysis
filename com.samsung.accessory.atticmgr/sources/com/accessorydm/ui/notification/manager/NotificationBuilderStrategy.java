package com.accessorydm.ui.notification.manager;

import android.app.Notification;
import android.os.Build;
import androidx.core.app.NotificationCompat;

interface NotificationBuilderStrategy {

    public interface SmallIcon {
        public static final SmallIcon FOTA_COMPLETION = $$Lambda$NotificationBuilderStrategy$SmallIcon$MbCEIwkHRQ0UH5R7hZFhuvTo.INSTANCE;
        public static final SmallIcon FOTA_CONNECTING = $$Lambda$NotificationBuilderStrategy$SmallIcon$s2CAQzGcEiRF6srC_6ZggTXsveU.INSTANCE;
        public static final SmallIcon FOTA_POSTPONE = $$Lambda$NotificationBuilderStrategy$SmallIcon$TfpOHLNFu3xZ1HfmHH0y_tXY1c.INSTANCE;

        int getSmallIcon();
    }

    public interface BigText {
        public static final BigText BIG_TEXT = $$Lambda$NotificationBuilderStrategy$BigText$whrqwhcDTjmQcVCzElkxPWOkqAA.INSTANCE;
        public static final BigText DO_NOTHING = $$Lambda$NotificationBuilderStrategy$BigText$rxMFjE541h_zAegTRsLQ1zKxnNQ.INSTANCE;

        Object getBigTextStyle(NotificationType notificationType);

        static /* synthetic */ default Object lambda$static$0(NotificationType notificationType) {
            if (Build.VERSION.SDK_INT >= 26) {
                return new Notification.BigTextStyle();
            }
            return new NotificationCompat.BigTextStyle();
        }

        static /* synthetic */ default Object lambda$static$1(NotificationType notificationType) {
            if (Build.VERSION.SDK_INT >= 26) {
                return new Notification.BigTextStyle().bigText(notificationType.getContentText());
            }
            return new NotificationCompat.BigTextStyle().bigText(notificationType.getContentText());
        }
    }
}
