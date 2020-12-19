package seccompat.com.samsung.android.feature;

import com.samsung.android.feature.SemCscFeature;
import seccompat.SecCompatUtil;

public class CscFeature {
    private static CscFeature sInstance;
    private SemCscFeature mSemCscFeature;

    public static CscFeature getInstance() {
        if (sInstance == null) {
            sInstance = new CscFeature();
        }
        return sInstance;
    }

    private CscFeature() {
        if (SecCompatUtil.isSEPDevice()) {
            this.mSemCscFeature = SemCscFeature.getInstance();
        }
    }

    public boolean getBoolean(String str) {
        if (SecCompatUtil.isSEPDevice()) {
            return this.mSemCscFeature.getBoolean(str, false);
        }
        return false;
    }
}
