package seccompat.com.samsung.android.widget;

import android.content.Context;
import android.os.Build;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.android.widget.SemLockPatternUtils;
import seccompat.Reflection;
import seccompat.SecCompatUtil;
import seccompat.android.os.UserHandle;

public class LockPatternUtils {
    public static boolean isLockScreenDisabled() {
        if (SecCompatUtil.isSEPDevice()) {
            return new SemLockPatternUtils(Application.getContext()).isLockScreenDisabled(UserHandle.myUserId());
        }
        try {
            Object newInstance = Reflection.getConstructor("com.android.internal.widget.LockPatternUtils", Context.class).newInstance(Application.getContext());
            if (Build.VERSION.SDK_INT < 23) {
                return ((Boolean) Reflection.callMethod(newInstance, "isLockScreenDisabled", new Object[0])).booleanValue();
            }
            return ((Boolean) Reflection.callMethod(newInstance, "isLockScreenDisabled", Integer.valueOf(UserHandle.myUserId()))).booleanValue();
        } catch (Error e) {
            e.printStackTrace();
            return false;
        }
    }
}
