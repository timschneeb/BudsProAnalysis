package seccompat.android.provider;

import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class Settings {

    public static final class Secure {
        public static final String ENABLED_NOTIFICATION_LISTENERS;

        static {
            if (SecCompatUtil.isSEPDevice()) {
                ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
            } else {
                ENABLED_NOTIFICATION_LISTENERS = (String) Reflection.getStaticField("android.provider.Settings$Secure.ENABLED_NOTIFICATION_LISTENERS");
            }
        }
    }
}
