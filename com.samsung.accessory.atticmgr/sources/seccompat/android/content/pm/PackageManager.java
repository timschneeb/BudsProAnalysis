package seccompat.android.content.pm;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import java.util.List;
import seccompat.Reflection;

public class PackageManager {
    public static List<ResolveInfo> proxyQueryIntentActivitiesAsUser(android.content.pm.PackageManager packageManager, Intent intent, int i, int i2) {
        return (List) Reflection.callMethod(packageManager, "queryIntentActivitiesAsUser", intent, Integer.valueOf(i), Integer.valueOf(i2));
    }
}
