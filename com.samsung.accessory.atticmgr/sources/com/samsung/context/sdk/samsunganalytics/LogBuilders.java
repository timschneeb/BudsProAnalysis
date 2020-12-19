package com.samsung.context.sdk.samsunganalytics;

import android.app.Activity;
import android.text.TextUtils;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LogBuilders {

    public @interface CustomDimension {
        public static final String DETAIL = "det";
    }

    public @interface EventType {
        public static final int BACKGROUND = 1;
        public static final int NORMAL = 0;
        public static final int R_BACKGROUND = 11;
        public static final int R_NORMAL = 10;
    }

    public @interface Property {
        public static final String AGE = "ag";
        public static final String APP_LANGUAGE = "al";
        public static final String COUNTRY_CODE = "cc";
        public static final String GENDER = "gd";
        public static final String SAMSUNG_ACCOUNT_ID = "guid";
    }

    /* access modifiers changed from: protected */
    public static abstract class LogBuilder<T extends LogBuilder> {
        protected Map<String, String> logs = new HashMap();

        /* access modifiers changed from: protected */
        public abstract T getThis();

        protected LogBuilder() {
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.logs.put(str, str2);
            }
            return getThis();
        }

        public final T setSessionStart() {
            set("sc", "s");
            return getThis();
        }

        public final T setSessionEnd() {
            set("sc", "e");
            return getThis();
        }

        public final T setSessionUpdate() {
            set("sc", "u");
            return getThis();
        }

        public long getTimeStamp() {
            return System.currentTimeMillis();
        }

        public Map<String, String> build() {
            set("ts", String.valueOf(getTimeStamp()));
            return this.logs;
        }
    }

    public static class ScreenViewBuilder extends LogBuilder<ScreenViewBuilder> {
        /* access modifiers changed from: protected */
        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public ScreenViewBuilder getThis() {
            return this;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public /* bridge */ /* synthetic */ long getTimeStamp() {
            return super.getTimeStamp();
        }

        public ScreenViewBuilder setScreenValue(int i) {
            set("pv", String.valueOf(i));
            return this;
        }

        public ScreenViewBuilder setScreenView(String str) {
            if (TextUtils.isEmpty(str)) {
                Utils.throwException("Failure to set Screen View : Screen name cannot be null.");
            } else {
                set("pn", str);
            }
            return getThis();
        }

        public ScreenViewBuilder setScreenView(Activity activity) {
            try {
                setScreenView(activity.getComponentName().getShortClassName());
            } catch (NullPointerException e) {
                Debug.LogException(getClass(), e);
            }
            return getThis();
        }

        public ScreenViewBuilder setPersonalizedData(Map<String, String[]> map) {
            HashMap hashMap = new HashMap();
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                sb.setLength(0);
                String[] value = entry.getValue();
                for (String str : value) {
                    if (sb.length() != 0) {
                        sb.append(Utils.Depth.THREE_DEPTH.getCollectionDLM());
                    }
                    sb.append(str);
                }
                hashMap.put(entry.getKey(), sb.toString());
            }
            set("pd", Utils.makeDelimiterString(hashMap, Utils.Depth.TWO_DEPTH));
            return this;
        }

        public ScreenViewBuilder setPersonalize(String[] strArr) {
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                if (sb.length() != 0) {
                    sb.append(Utils.Depth.TWO_DEPTH.getCollectionDLM());
                }
                sb.append(str);
            }
            set("ps", sb.toString());
            return this;
        }

        public ScreenViewBuilder setDimension(Map<String, String> map) {
            set("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map), Utils.Depth.TWO_DEPTH));
            return getThis();
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public Map<String, String> build() {
            if (TextUtils.isEmpty((CharSequence) this.logs.get("pn"))) {
                Utils.throwException("Failure to build Log : Screen name cannot be null");
            } else {
                set("t", "pv");
            }
            return super.build();
        }
    }

    public static class EventBuilder extends LogBuilder<EventBuilder> {
        /* access modifiers changed from: protected */
        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public EventBuilder getThis() {
            return this;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public /* bridge */ /* synthetic */ long getTimeStamp() {
            return super.getTimeStamp();
        }

        public EventBuilder setEventName(String str) {
            if (TextUtils.isEmpty(str)) {
                Utils.throwException("Failure to build Log : Event name cannot be null");
            }
            set(HttpNetworkInterface.XTP_HTTP_LANGUAGE, str);
            return this;
        }

        public EventBuilder setEventId(String str) {
            return setEventName(str);
        }

        public EventBuilder setScreenView(String str) {
            if (!TextUtils.isEmpty(str)) {
                set("pn", str);
            }
            return getThis();
        }

        public EventBuilder setScreenView(Activity activity) {
            try {
                setScreenView(activity.getComponentName().getShortClassName());
            } catch (NullPointerException e) {
                Debug.LogException(getClass(), e);
            }
            return getThis();
        }

        public EventBuilder setEventValue(long j) {
            set("ev", String.valueOf(j));
            return this;
        }

        public EventBuilder setEventType(int i) {
            set("et", String.valueOf(i));
            return this;
        }

        public EventBuilder setPersonalizedData(Map<String, String[]> map) {
            HashMap hashMap = new HashMap();
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                sb.setLength(0);
                String[] value = entry.getValue();
                for (String str : value) {
                    if (sb.length() != 0) {
                        sb.append(Utils.Depth.THREE_DEPTH.getCollectionDLM());
                    }
                    sb.append(str);
                }
                hashMap.put(entry.getKey(), sb.toString());
            }
            set("pd", Utils.makeDelimiterString(hashMap, Utils.Depth.TWO_DEPTH));
            return this;
        }

        public EventBuilder setPersonalize(String[] strArr) {
            StringBuilder sb = new StringBuilder();
            for (String str : strArr) {
                if (sb.length() != 0) {
                    sb.append(Utils.Depth.TWO_DEPTH.getCollectionDLM());
                }
                sb.append(str);
            }
            set("ps", sb.toString());
            return this;
        }

        public EventBuilder setDimension(Map<String, String> map) {
            set("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map), Utils.Depth.TWO_DEPTH));
            return this;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public Map<String, String> build() {
            if (!this.logs.containsKey(HttpNetworkInterface.XTP_HTTP_LANGUAGE)) {
                Utils.throwException("Failure to build Log : Event name cannot be null");
            }
            set("t", "ev");
            return super.build();
        }
    }

    public static class QuickSettingBuilder {
        private Map<String, String> map = new HashMap();

        public final QuickSettingBuilder set(String str, int i) {
            return set(str, Integer.toString(i));
        }

        public final QuickSettingBuilder set(String str, float f) {
            return set(str, Float.toString(f));
        }

        public final QuickSettingBuilder set(String str, boolean z) {
            return set(str, Boolean.toString(z));
        }

        public final QuickSettingBuilder set(String str, Set<String> set) {
            String str2 = "";
            for (String str3 : set) {
                if (!TextUtils.isEmpty(str2)) {
                    str2 = str2 + Utils.Depth.THREE_DEPTH.getCollectionDLM();
                }
                str2 = str2 + str3;
            }
            return set(str, str2);
        }

        public final QuickSettingBuilder set(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                Utils.throwException("Failure to build logs [setting] : Key cannot be null.");
            } else if (str.equalsIgnoreCase("t")) {
                Utils.throwException("Failure to build logs [setting] : 't' is reserved word, choose another word.");
            } else {
                this.map.put(str, str2);
            }
            return this;
        }

        public Map<String, String> build() {
            if (!this.map.isEmpty()) {
                String makeDelimiterString = Utils.makeDelimiterString(this.map, Utils.Depth.TWO_DEPTH);
                this.map.clear();
                this.map.put("sti", makeDelimiterString);
                this.map.put("ts", String.valueOf(System.currentTimeMillis()));
                this.map.put("t", "st");
            }
            return this.map;
        }
    }

    public static class SettingPrefBuilder {
        private Map<String, Set<String>> map = new HashMap();

        private SettingPrefBuilder addAppPref(String str) {
            if (!this.map.containsKey(str) && !TextUtils.isEmpty(str)) {
                this.map.put(str, new HashSet());
            } else if (TextUtils.isEmpty(str)) {
                Utils.throwException("Failure to build logs [setting preference] : Preference name cannot be null.");
            }
            return this;
        }

        public SettingPrefBuilder addKey(String str, String str2) {
            if (TextUtils.isEmpty(str2)) {
                Utils.throwException("Failure to build logs [setting preference] : Setting key cannot be null.");
            }
            addAppPref(str);
            this.map.get(str).add(str2);
            return this;
        }

        public SettingPrefBuilder addKeys(String str, Set<String> set) {
            if (set == null || set.isEmpty()) {
                Utils.throwException("Failure to build logs [setting preference] : Setting keys cannot be null.");
            }
            addAppPref(str);
            Set<String> set2 = this.map.get(str);
            for (String str2 : set) {
                if (!TextUtils.isEmpty(str2)) {
                    set2.add(str2);
                }
            }
            return this;
        }

        public Map<String, Set<String>> build() {
            Debug.LogENG(this.map.toString());
            return this.map;
        }
    }

    public static class DeviceBuilder extends LogBuilder<DeviceBuilder> {
        /* access modifiers changed from: protected */
        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public DeviceBuilder getThis() {
            return this;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public /* bridge */ /* synthetic */ long getTimeStamp() {
            return super.getTimeStamp();
        }

        public DeviceBuilder setDimension(Map<String, String> map) {
            set("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map), Utils.Depth.TWO_DEPTH));
            return getThis();
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public Map<String, String> build() {
            set("t", "dl");
            return super.build();
        }
    }

    public static class PropertyBuilder {
        private Map<String, String> property = new HashMap();

        public PropertyBuilder set(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                Utils.throwException("Failed to build [PropertyBuilder] : Key cannot be null.");
            } else if (str.equalsIgnoreCase("t")) {
                Utils.throwException("Failure to build logs [PropertyBuilder] : 't' is reserved word, choose another word.");
            } else {
                this.property.put(str, str2);
            }
            return this;
        }

        public Map<String, String> build() {
            if (this.property.isEmpty()) {
                return null;
            }
            this.property.put("t", "pp");
            return this.property;
        }
    }

    public static class CustomBuilder extends LogBuilder<CustomBuilder> {
        /* access modifiers changed from: protected */
        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public CustomBuilder getThis() {
            return this;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        @Override // com.samsung.context.sdk.samsunganalytics.LogBuilders.LogBuilder
        public /* bridge */ /* synthetic */ long getTimeStamp() {
            return super.getTimeStamp();
        }
    }
}
