package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.Map;
import java.util.Set;

public class SamsungAnalytics {
    public static final String SDK_VERSION = "6.05.033";
    private static SamsungAnalytics instance;
    private Tracker tracker = null;

    private SamsungAnalytics(Application application, Configuration configuration) {
        if (!Validation.isValidConfig(application, configuration)) {
            return;
        }
        if (configuration.isEnableUseInAppLogging() || Validation.isLoggingEnableDevice(application)) {
            this.tracker = new Tracker(application, configuration);
        }
    }

    private static SamsungAnalytics getInstanceAndConfig(Application application, Configuration configuration) {
        SamsungAnalytics samsungAnalytics = instance;
        if (samsungAnalytics == null || samsungAnalytics.tracker == null) {
            synchronized (SamsungAnalytics.class) {
                instance = new SamsungAnalytics(application, configuration);
            }
        }
        return instance;
    }

    public static void setConfiguration(Application application, Configuration configuration) {
        getInstanceAndConfig(application, configuration);
    }

    public static Configuration getConfiguration() {
        Tracker tracker2;
        SamsungAnalytics samsungAnalytics = instance;
        if (samsungAnalytics == null || (tracker2 = samsungAnalytics.tracker) == null) {
            return null;
        }
        return tracker2.getConfiguration();
    }

    public static SamsungAnalytics getInstance() {
        if (instance == null) {
            Utils.throwException("call after setConfiguration() method");
            if (!Utils.isEngBin()) {
                return getInstanceAndConfig(null, null);
            }
        }
        return instance;
    }

    public SamsungAnalytics enableAutoActivityTracking() {
        try {
            this.tracker.enableAutoActivityTracking();
        } catch (NullPointerException e) {
            Debug.LogException(getClass(), e);
        }
        return this;
    }

    public int sendLog(Map<String, String> map) {
        try {
            return this.tracker.sendLog(map);
        } catch (NullPointerException unused) {
            return -100;
        }
    }

    public void registerSettingPref(Map<String, Set<String>> map) {
        try {
            this.tracker.registerSettingPref(map);
        } catch (NullPointerException e) {
            Debug.LogException(getClass(), e);
        }
    }

    public void deleteLogData() {
        try {
            this.tracker.changeUserAgreementState(false);
        } catch (NullPointerException e) {
            Debug.LogException(getClass(), e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        if (r4 != null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005b, code lost:
        if (r4 != null) goto L_0x004e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getDeviceId(android.content.Context r4) {
        /*
        // Method dump skipped, instructions count: 114
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.getDeviceId(android.content.Context):java.lang.String");
    }
}
