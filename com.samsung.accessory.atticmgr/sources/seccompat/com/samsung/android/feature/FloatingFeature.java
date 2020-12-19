package seccompat.com.samsung.android.feature;

import com.samsung.android.feature.SemFloatingFeature;
import seccompat.Reflection;
import seccompat.SecCompatUtil;

public class FloatingFeature {
    private static FloatingFeature sInstance;
    private Object mFloatingFeatureObject;
    private SemFloatingFeature mSemFloatingFeature;

    public static FloatingFeature getInstance() {
        if (sInstance == null) {
            sInstance = new FloatingFeature();
        }
        return sInstance;
    }

    private FloatingFeature() {
        if (SecCompatUtil.isSEPDevice()) {
            this.mSemFloatingFeature = SemFloatingFeature.getInstance();
        } else {
            this.mFloatingFeatureObject = Reflection.callStaticMethod("com.samsung.android.feature.FloatingFeature", "getInstance", new Object[0]);
        }
    }

    public boolean getEnableStatus(String str) {
        if (SecCompatUtil.isSEPDevice()) {
            return this.mSemFloatingFeature.getBoolean(str);
        }
        return ((Boolean) Reflection.callMethod(this.mFloatingFeatureObject, "getEnableStatus", str)).booleanValue();
    }

    public boolean getSupportBixby(String str) {
        if (SecCompatUtil.isSEPDevice()) {
            return this.mSemFloatingFeature.getBoolean(str);
        }
        return false;
    }
}
