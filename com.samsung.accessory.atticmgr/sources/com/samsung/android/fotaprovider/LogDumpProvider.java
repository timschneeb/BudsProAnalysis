package com.samsung.android.fotaprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.FotaProviderUtil;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class LogDumpProvider extends ContentProvider {
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        return false;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        new Thread("T[LogDumpProvider] dump") {
            /* class com.samsung.android.fotaprovider.LogDumpProvider.AnonymousClass1 */

            public void run() {
                Log.D("copy log to sdcard by dumpstate");
                FotaProviderUtil.copyLogToSdcard(LogDumpProvider.this.getContext(), getClass().getSimpleName());
            }
        }.start();
        super.dump(fileDescriptor, printWriter, strArr);
    }
}
