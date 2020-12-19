package com.sec.android.diagmonagent.log.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.context.sdk.samsunganalytics.BuildConfig;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class newLogProvider extends ContentProvider {
    private static final String AGREED = "agreed";
    private static final String AUTHORITY_LIST = "authorityList";
    private static final String DEFAULT_SERVICE_NAME = "Samsung Software";
    private static final String DEVICE_ID = "deviceId";
    private static final String DEVICE_INFO = "deviceInfo";
    private static final String DIAGMON_PREFERENCES = "diagmon_preferences";
    private static final String DIAGMON_SUPPORT_V1_VERSION_CODE = "diagmonSupportV1VersionCode";
    private static final String DIAGMON_SUPPORT_V1_VERSION_NAME = "diagmonSupportV1VersionName";
    private static final String LOG_LIST = "logList";
    private static final String NONCE = "nonce";
    private static final String OPERATION_NOT_SUPPORTED = "Operation not supported";
    private static final String PLAIN_LOG_LIST = "plainLogList";
    private static final String PUSH_REGISTERED = "pushRegistered";
    private static final String REGISTERED = "registered";
    private static final String SERVICE_NAME = "serviceName";
    private static final String SUPPORT_PUSH = "supportPush";
    private static final String TRY_REGISTERING = "tryRegistering";
    private static final String UPLOAD_WIFIONLY = "uploadWifionly";
    private String AUTHORITY;
    private Bundle data;

    public String getType(Uri uri) {
        return XDMInterface.MIMETYPE_TEXT_PLAIN;
    }

    public boolean onCreate() {
        this.AUTHORITY = "";
        this.data = new Bundle();
        this.data.putBundle(DIAGMON_SUPPORT_V1_VERSION_NAME, getDiagmonSupportV1VersionNameBundle());
        this.data.putBundle(DIAGMON_SUPPORT_V1_VERSION_CODE, getDiagmonSupportV1VersionCodeBundle());
        this.data.putBundle(REGISTERED, makeBundle(REGISTERED, false));
        this.data.putBundle(PUSH_REGISTERED, makeBundle(PUSH_REGISTERED, false));
        this.data.putBundle(TRY_REGISTERING, makeBundle(TRY_REGISTERING, true));
        this.data.putBundle(NONCE, makeBundle(NONCE, ""));
        this.data.putBundle(UPLOAD_WIFIONLY, makeBundle(UPLOAD_WIFIONLY, true));
        this.data.putBundle(SUPPORT_PUSH, makeBundle(SUPPORT_PUSH, true));
        this.data.putBundle(DEVICE_INFO, setDeviceInfo());
        this.data.putBundle(SERVICE_NAME, makeBundle(SERVICE_NAME, DEFAULT_SERVICE_NAME));
        this.data.putBundle(AUTHORITY_LIST, makeAuthorityListBundle(new ArrayList()));
        this.data.putBundle("deviceId", makeBundle("deviceId", setDeviceId()));
        this.data.putBundle(LOG_LIST, makeLogListBundle(new ArrayList()));
        this.data.putBundle(PLAIN_LOG_LIST, makeLogListBundle(new ArrayList()));
        return true;
    }

    private Bundle makeBundle(String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(str, z);
        return bundle;
    }

    private Bundle makeBundle(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        return bundle;
    }

    private Bundle makeAuthorityListBundle(List<String> list) {
        Bundle bundle = new Bundle();
        for (String str : list) {
            bundle.putString(str, str);
        }
        return bundle;
    }

    private String setDeviceId() {
        PackageInformation packageInformation = PackageInformation.instance;
        String twid = PackageInformation.getTWID();
        String str = DiagMonUtil.TAG;
        Log.d(str, "setDeviceId : " + twid);
        return twid;
    }

    private Bundle setDeviceInfo() {
        return PackageInformation.instance.getDeviceInfoBundle(getContext());
    }

    /* access modifiers changed from: protected */
    public Bundle init(Bundle bundle) {
        String string = bundle.getString("serviceId", "");
        this.AUTHORITY = string;
        ArrayList arrayList = new ArrayList();
        arrayList.add(string);
        this.data.putBundle(AUTHORITY_LIST, makeAuthorityListBundle(arrayList));
        String string2 = bundle.getString("deviceId", "");
        if (TextUtils.isEmpty(string2)) {
            string2 = setDeviceId();
        }
        this.data.putBundle("deviceId", makeBundle("deviceId", string2));
        this.data.putBundle(AGREED, makeBundle(AGREED, bundle.getBoolean("serviceAgreeType", false)));
        return Bundle.EMPTY;
    }

    /* access modifiers changed from: protected */
    public Bundle setLogPath(String str) {
        this.data.putBundle(LOG_LIST, makeLogListBundle(makeLogList(str)));
        return Bundle.EMPTY;
    }

    private List<String> makeLogList(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            File[] listFiles = new File(str).listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    arrayList.add(file.getPath());
                    AppLog.w("found file : " + file.getPath());
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    private Bundle getDiagmonSupportV1VersionNameBundle() {
        Bundle bundle = new Bundle();
        try {
            Object obj = BuildConfig.class.getDeclaredField("VERSION_NAME").get(null);
            if (obj instanceof String) {
                bundle.putString(DIAGMON_SUPPORT_V1_VERSION_NAME, (String) String.class.cast(obj));
            }
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException unused) {
        }
        return bundle;
    }

    private Bundle getDiagmonSupportV1VersionCodeBundle() {
        Bundle bundle = new Bundle();
        try {
            bundle.putInt(DIAGMON_SUPPORT_V1_VERSION_CODE, BuildConfig.class.getDeclaredField("VERSION_CODE").getInt(null));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException unused) {
        }
        return bundle;
    }

    /* access modifiers changed from: protected */
    public Bundle makeLogListBundle(List<String> list) {
        Bundle bundle = new Bundle();
        for (String str : list) {
            try {
                str = new File(str).getCanonicalPath();
            } catch (IOException unused) {
            }
            bundle.putParcelable(str, new Uri.Builder().scheme("content").authority(this.AUTHORITY).path(str).build());
        }
        return bundle;
    }

    public Bundle call(String str, String str2, Bundle bundle) {
        if ("service_registration".equals(str)) {
            return init(bundle);
        }
        if ("update_path".equals(str)) {
            return setLogPath(str2);
        }
        if ("clear".equals(str)) {
            return clear();
        }
        if ("set".equals(str)) {
            return set(str2, bundle);
        }
        if ("get".equals(str) && !contains(str2) && this.data.getBundle(str2) != null) {
            return this.data.getBundle(str2);
        }
        if ("get".equals(str)) {
            return get(str2);
        }
        return super.call(str, str2, bundle);
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        String path = uri.getPath();
        if (this.data.getBundle(LOG_LIST) == null || this.data.getBundle(PLAIN_LOG_LIST) == null) {
            throw new RuntimeException("Data is corrupted");
        } else if (this.data.getBundle(LOG_LIST).containsKey(path) || this.data.getBundle(PLAIN_LOG_LIST).containsKey(path)) {
            return openParcelFileDescriptor(path);
        } else {
            throw new FileNotFoundException();
        }
    }

    /* access modifiers changed from: protected */
    public ParcelFileDescriptor openParcelFileDescriptor(String str) throws FileNotFoundException {
        return ParcelFileDescriptor.open(new File(str), 268435456);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        throw new RuntimeException(OPERATION_NOT_SUPPORTED);
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new RuntimeException(OPERATION_NOT_SUPPORTED);
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new RuntimeException(OPERATION_NOT_SUPPORTED);
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new RuntimeException(OPERATION_NOT_SUPPORTED);
    }

    /* access modifiers changed from: protected */
    public Bundle set(String str, Bundle bundle) {
        SharedPreferences.Editor edit = getDiagMonSharedPreferences().edit();
        Object obj = bundle.get(str);
        if (obj instanceof Boolean) {
            edit.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        if (obj instanceof Float) {
            edit.putFloat(str, ((Float) obj).floatValue());
        }
        if (obj instanceof Integer) {
            edit.putInt(str, ((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            edit.putLong(str, ((Long) obj).longValue());
        }
        if (obj instanceof String) {
            edit.putString(str, (String) obj);
        }
        edit.apply();
        return Bundle.EMPTY;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0019 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle get(java.lang.String r5) {
        /*
            r4 = this;
            android.content.SharedPreferences r0 = r4.getDiagMonSharedPreferences()
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            r2 = 0
            boolean r3 = r0.getBoolean(r5, r2)     // Catch:{ ClassCastException -> 0x0011 }
            r1.putBoolean(r5, r3)     // Catch:{ ClassCastException -> 0x0011 }
        L_0x0011:
            r3 = 0
            float r3 = r0.getFloat(r5, r3)     // Catch:{ ClassCastException -> 0x0019 }
            r1.putFloat(r5, r3)     // Catch:{ ClassCastException -> 0x0019 }
        L_0x0019:
            int r2 = r0.getInt(r5, r2)     // Catch:{ ClassCastException -> 0x0020 }
            r1.putInt(r5, r2)     // Catch:{ ClassCastException -> 0x0020 }
        L_0x0020:
            r2 = 0
            long r2 = r0.getLong(r5, r2)     // Catch:{ ClassCastException -> 0x0029 }
            r1.putLong(r5, r2)     // Catch:{ ClassCastException -> 0x0029 }
        L_0x0029:
            r2 = 0
            java.lang.String r0 = r0.getString(r5, r2)     // Catch:{ ClassCastException -> 0x0031 }
            r1.putString(r5, r0)     // Catch:{ ClassCastException -> 0x0031 }
        L_0x0031:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.newLogProvider.get(java.lang.String):android.os.Bundle");
    }

    /* access modifiers changed from: protected */
    public boolean contains(String str) {
        return getDiagMonSharedPreferences().contains(str);
    }

    /* access modifiers changed from: protected */
    public SharedPreferences getDiagMonSharedPreferences() {
        return getContext().getSharedPreferences(DIAGMON_PREFERENCES, 0);
    }

    /* access modifiers changed from: protected */
    public Bundle clear() {
        SharedPreferences.Editor edit = getDiagMonSharedPreferences().edit();
        edit.clear();
        edit.apply();
        return Bundle.EMPTY;
    }
}
