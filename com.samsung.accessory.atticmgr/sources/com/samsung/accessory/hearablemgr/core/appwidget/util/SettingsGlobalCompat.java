package com.samsung.accessory.hearablemgr.core.appwidget.util;

import android.content.ContentResolver;
import android.provider.Settings;

public class SettingsGlobalCompat {

    public static class Global {
        public static final String FONT_SIZE = "font_size";
    }

    private SettingsGlobalCompat() {
        throw new AssertionError("No instances");
    }

    public static class System {
        public static final String ACCESS_CONTROL_ENABLED = "access_control_enabled";
        private static final SystemImpl sImpl = new SystemImpl2402();

        /* access modifiers changed from: package-private */
        public interface SystemImpl {
            int getIntForUser(ContentResolver contentResolver, String str, int i, int i2);
        }

        public static int getIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
            return sImpl.getIntForUser(contentResolver, str, i, i2);
        }

        static class SystemImpl2402 implements SystemImpl {
            SystemImpl2402() {
            }

            @Override // com.samsung.accessory.hearablemgr.core.appwidget.util.SettingsGlobalCompat.System.SystemImpl
            public int getIntForUser(ContentResolver contentResolver, String str, int i, int i2) {
                return Settings.System.semGetIntForUser(contentResolver, str, i, i2);
            }
        }
    }
}
