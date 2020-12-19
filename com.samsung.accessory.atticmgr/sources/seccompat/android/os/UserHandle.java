package seccompat.android.os;

import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class UserHandle {
    public static int myUserId() {
        if (SecCompatUtil.isSEPDevice()) {
            return android.os.UserHandle.semGetMyUserId();
        }
        try {
            return ((Integer) Reflection.callStaticMethod("android.os.UserHandle", "myUserId", new Object[0])).intValue();
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }
}
