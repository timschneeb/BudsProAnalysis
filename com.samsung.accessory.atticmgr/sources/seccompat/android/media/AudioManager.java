package seccompat.android.media;

import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class AudioManager {
    public static boolean proxyIsStreamMute(android.media.AudioManager audioManager, int i) {
        Boolean bool = (Boolean) Reflection.callMethod(audioManager, "isStreamMute", Integer.valueOf(i));
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static boolean proxysemIsRecordActive(android.media.AudioManager audioManager, int i) {
        Boolean valueOf;
        if (SecCompatUtil.isSEPDevice() && (valueOf = Boolean.valueOf(audioManager.semIsRecordActive(i))) != null) {
            return valueOf.booleanValue();
        }
        return false;
    }
}
