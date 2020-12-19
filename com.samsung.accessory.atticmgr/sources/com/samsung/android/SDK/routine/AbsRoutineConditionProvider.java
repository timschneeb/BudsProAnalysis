package com.samsung.android.SDK.routine;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;

public abstract class AbsRoutineConditionProvider extends ContentProvider {
    protected static final Object mLock = new Object();

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getLabelParam(String str, String str2, boolean z) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public abstract boolean isSatisfied(String str, String str2, boolean z);

    public int isSupport(String str) {
        return 1;
    }

    public int isValid(String str, String str2, boolean z) {
        return 0;
    }

    public boolean onCreate() {
        return true;
    }

    public abstract void onDisabled(String str, String str2, Bundle bundle);

    public abstract void onEnabled(String str, String str2, Bundle bundle);

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public Bundle call(String str, String str2, Bundle bundle) {
        Binder.clearCallingIdentity();
        Bundle bundle2 = new Bundle();
        synchronized (mLock) {
            char c = 65535;
            switch (str.hashCode()) {
                case -1961649681:
                    if (str.equals("getLabelParam")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1254072965:
                    if (str.equals("onDisabled")) {
                        c = 2;
                        break;
                    }
                    break;
                case -279882200:
                    if (str.equals("isSatisfied")) {
                        c = 0;
                        break;
                    }
                    break;
                case -273314718:
                    if (str.equals("onEnabled")) {
                        c = 1;
                        break;
                    }
                    break;
                case 1860421445:
                    if (str.equals("isSupport")) {
                        c = 5;
                        break;
                    }
                    break;
                case 2073378034:
                    if (str.equals("isValid")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            if (c != 0) {
                if (c == 1) {
                    onEnabled(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBundle("data"));
                } else if (c == 2) {
                    onDisabled(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBundle("data"));
                } else if (c == 3) {
                    bundle2.putString(Constants.EXTRA_CONFIG_LABEL_PARAMS, getLabelParam(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBoolean(Constants.EXTRA_IS_NEGATIVE)));
                    return bundle2;
                } else if (c == 4) {
                    bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(isValid(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBoolean(Constants.EXTRA_IS_NEGATIVE))));
                    return bundle2;
                } else if (c == 5) {
                    bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(isSupport(bundle.getString(Constants.EXTRA_TAG))));
                    return bundle2;
                }
                return super.call(str, str2, bundle);
            }
            bundle2.putBoolean(Constants.EXTRA_RETURN, isSatisfied(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBoolean(Constants.EXTRA_IS_NEGATIVE)));
            return bundle2;
        }
    }
}
