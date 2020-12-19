package com.sec.android.diagmonagent.log.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class newAbstractLogProvider extends ContentProvider {

    /* renamed from: a  reason: collision with root package name */
    public static Bundle f1987a;

    /* JADX WARNING: Removed duplicated region for block: B:4:? A[ExcHandler: IllegalAccessException | IllegalArgumentException | NoSuchFieldException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:1:0x0009] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.os.Bundle g() {
        /*
            r3 = this;
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.Class<c.b.b.a.a.b> r1 = c.b.b.a.a.b.class
            java.lang.String r2 = "a"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0017, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0017 }
            r2 = 0
            int r1 = r1.getInt(r2)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0017, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0017 }
            java.lang.String r2 = "diagmonSupportV1VersionCode"
            r0.putInt(r2, r1)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0017, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0017 }
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.newAbstractLogProvider.g():android.os.Bundle");
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:? A[ExcHandler: IllegalAccessException | IllegalArgumentException | NoSuchFieldException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:1:0x0009] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.os.Bundle h() {
        /*
            r4 = this;
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.Class<c.b.b.a.a.b> r1 = c.b.b.a.a.b.class
            java.lang.String r2 = "b"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023 }
            r2 = 0
            java.lang.Object r1 = r1.get(r2)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023 }
            boolean r2 = r1 instanceof java.lang.String     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023 }
            if (r2 == 0) goto L_0x0023
            java.lang.String r2 = "diagmonSupportV1VersionName"
            java.lang.Class<java.lang.String> r3 = java.lang.String.class
            java.lang.Object r1 = r3.cast(r1)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023 }
            r0.putString(r2, r1)     // Catch:{ IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023, IllegalAccessException | IllegalArgumentException | NoSuchFieldException -> 0x0023 }
        L_0x0023:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.newAbstractLogProvider.h():android.os.Bundle");
    }

    /* access modifiers changed from: protected */
    public Bundle a() {
        SharedPreferences.Editor edit = d().edit();
        edit.clear();
        edit.apply();
        return Bundle.EMPTY;
    }

    /* access modifiers changed from: protected */
    public Bundle a(String str, Bundle bundle) {
        SharedPreferences.Editor edit = d().edit();
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
    public Bundle a(List<String> list) {
        Bundle bundle = new Bundle();
        for (String str : list) {
            try {
                str = new File(str).getCanonicalPath();
            } catch (IOException unused) {
            }
            bundle.putParcelable(str, new Uri.Builder().scheme("content").authority(c()).path(str).build());
        }
        return bundle;
    }

    /* access modifiers changed from: protected */
    public boolean a(String str) {
        return d().contains(str);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0019 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle b(java.lang.String r5) {
        /*
            r4 = this;
            android.content.SharedPreferences r0 = r4.d()
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
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.provider.newAbstractLogProvider.b(java.lang.String):android.os.Bundle");
    }

    /* access modifiers changed from: protected */
    public void b() {
    }

    /* access modifiers changed from: protected */
    public ParcelFileDescriptor c(String str) {
        return ParcelFileDescriptor.open(new File(str), 268435456);
    }

    /* access modifiers changed from: protected */
    public abstract String c();

    public Bundle call(String str, String str2, Bundle bundle) {
        b();
        return "clear".equals(str) ? a() : "set".equals(str) ? a(str2, bundle) : (!"get".equals(str) || a(str2) || f1987a.getBundle(str2) == null) ? "get".equals(str) ? b(str2) : super.call(str, str2, bundle) : f1987a.getBundle(str2);
    }

    /* access modifiers changed from: protected */
    public SharedPreferences d() {
        return getContext().getSharedPreferences("diagmon_preferences", 0);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        throw new RuntimeException("Operation not supported");
    }

    /* access modifiers changed from: protected */
    public abstract List<String> e();

    /* access modifiers changed from: protected */
    public List<String> f() {
        return Arrays.asList(new String[0]);
    }

    public String getType(Uri uri) {
        b();
        return "text/plain";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new RuntimeException("Operation not supported");
    }

    public boolean onCreate() {
        f1987a = new Bundle();
        f1987a.putBundle("diagmonSupportV1VersionName", h());
        f1987a.putBundle("diagmonSupportV1VersionCode", g());
        return true;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) {
        b();
        String path = uri.getPath();
        if (f1987a.getBundle("logList") == null || f1987a.getBundle("plainLogList") == null) {
            throw new RuntimeException("Data is corrupted");
        } else if (f1987a.getBundle("logList").containsKey(path) || f1987a.getBundle("plainLogList").containsKey(path)) {
            return c(path);
        } else {
            throw new FileNotFoundException();
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new RuntimeException("Operation not supported");
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new RuntimeException("Operation not supported");
    }
}
