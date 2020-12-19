package androidx.room.util;

import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.os.Build;
import android.os.CancellationSignal;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.ArrayList;

public class DBUtil {
    @Deprecated
    public static Cursor query(RoomDatabase roomDatabase, SupportSQLiteQuery supportSQLiteQuery, boolean z) {
        return query(roomDatabase, supportSQLiteQuery, z, null);
    }

    public static Cursor query(RoomDatabase roomDatabase, SupportSQLiteQuery supportSQLiteQuery, boolean z, CancellationSignal cancellationSignal) {
        Cursor query = roomDatabase.query(supportSQLiteQuery, cancellationSignal);
        if (!z || !(query instanceof AbstractWindowedCursor)) {
            return query;
        }
        AbstractWindowedCursor abstractWindowedCursor = (AbstractWindowedCursor) query;
        int count = abstractWindowedCursor.getCount();
        return (Build.VERSION.SDK_INT < 23 || (abstractWindowedCursor.hasWindow() ? abstractWindowedCursor.getWindow().getNumRows() : count) < count) ? CursorUtil.copyAndClose(abstractWindowedCursor) : query;
    }

    /* JADX INFO: finally extract failed */
    public static void dropFtsSyncTriggers(SupportSQLiteDatabase supportSQLiteDatabase) {
        ArrayList<String> arrayList = new ArrayList();
        Cursor query = supportSQLiteDatabase.query("SELECT name FROM sqlite_master WHERE type = 'trigger'");
        while (query.moveToNext()) {
            try {
                arrayList.add(query.getString(0));
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        query.close();
        for (String str : arrayList) {
            if (str.startsWith("room_fts_content_sync_")) {
                supportSQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS " + str);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int readVersion(java.io.File r10) throws java.io.IOException {
        /*
            r0 = 4
            r1 = 0
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r0)     // Catch:{ all -> 0x003a }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ all -> 0x003a }
            r3.<init>(r10)     // Catch:{ all -> 0x003a }
            java.nio.channels.FileChannel r10 = r3.getChannel()     // Catch:{ all -> 0x003a }
            r5 = 60
            r7 = 4
            r9 = 1
            r4 = r10
            r4.tryLock(r5, r7, r9)     // Catch:{ all -> 0x0038 }
            r3 = 60
            r10.position(r3)     // Catch:{ all -> 0x0038 }
            int r1 = r10.read(r2)     // Catch:{ all -> 0x0038 }
            if (r1 != r0) goto L_0x0030
            r2.rewind()     // Catch:{ all -> 0x0038 }
            int r0 = r2.getInt()     // Catch:{ all -> 0x0038 }
            if (r10 == 0) goto L_0x002f
            r10.close()
        L_0x002f:
            return r0
        L_0x0030:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Bad database header, unable to read 4 bytes at offset 60"
            r0.<init>(r1)
            throw r0
        L_0x0038:
            r0 = move-exception
            goto L_0x003c
        L_0x003a:
            r0 = move-exception
            r10 = r1
        L_0x003c:
            if (r10 == 0) goto L_0x0041
            r10.close()
        L_0x0041:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.util.DBUtil.readVersion(java.io.File):int");
    }

    public static CancellationSignal createCancellationSignal() {
        if (Build.VERSION.SDK_INT >= 16) {
            return new CancellationSignal();
        }
        return null;
    }

    private DBUtil() {
    }
}
