package com.samsung.android.SDK.routine;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import java.util.ArrayList;

public abstract class AbsRoutineActionProvider extends ContentProvider {
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public abstract String getCurrentParam(String str);

    public String getLabelParam(String str, String str2, boolean z) {
        return null;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int isSupport(String str) {
        return 1;
    }

    public int isValid(String str, String str2, boolean z) {
        return 0;
    }

    public int onAct(String str, String str2, ArrayList<String> arrayList, boolean z) {
        return 1;
    }

    public abstract int onAct(String str, String str2, boolean z);

    public int onAct(String str, String str2, boolean z, boolean z2) {
        return 0;
    }

    public boolean onCreate() {
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public Bundle call(String str, String str2, Bundle bundle) {
        char c;
        Binder.clearCallingIdentity();
        Bundle bundle2 = new Bundle();
        switch (str.hashCode()) {
            case -1961649681:
                if (str.equals("getLabelParam")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1213955830:
                if (str.equals("getCurrentParam")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 105853491:
                if (str.equals("onAct")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1860421445:
                if (str.equals("isSupport")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2073378034:
                if (str.equals("isValid")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            bundle2.putString(Constants.EXTRA_RETURN, getCurrentParam(bundle.getString(Constants.EXTRA_TAG)));
            return bundle2;
        } else if (c == 1) {
            String string = bundle.getString(Constants.EXTRA_TAG);
            String string2 = bundle.getString(Constants.EXTRA_PARAM);
            boolean z = bundle.getBoolean(Constants.EXTRA_IS_NEGATIVE);
            boolean z2 = bundle.getBoolean(Constants.EXTRA_IS_RECOVERY);
            ArrayList<String> stringArrayList = bundle.getStringArrayList(Constants.EXTRA_ALL_PARAM);
            if (stringArrayList != null) {
                bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(onAct(string, string2, stringArrayList, z)));
            } else {
                int onAct = onAct(string, string2, z, z2);
                if (onAct != 0) {
                    bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(onAct));
                } else {
                    bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(onAct(string, string2, z)));
                }
            }
            return bundle2;
        } else if (c == 2) {
            bundle2.putString(Constants.EXTRA_CONFIG_LABEL_PARAMS, getLabelParam(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBoolean(Constants.EXTRA_IS_NEGATIVE)));
            return bundle2;
        } else if (c == 3) {
            bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(isValid(bundle.getString(Constants.EXTRA_TAG), bundle.getString(Constants.EXTRA_PARAM), bundle.getBoolean(Constants.EXTRA_IS_NEGATIVE))));
            return bundle2;
        } else if (c != 4) {
            return super.call(str, str2, bundle);
        } else {
            bundle2.putString(Constants.EXTRA_RETURN, String.valueOf(isSupport(bundle.getString(Constants.EXTRA_TAG))));
            return bundle2;
        }
    }
}
