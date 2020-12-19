package com.sec.android.diagmonagent.log.ged.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.sec.android.diagmonagent.log.ged.db.dao.Contracts;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.Result;
import java.util.concurrent.TimeUnit;

public class ResultDao {
    public static final String CREATE_TABLE_RESULT = "CREATE TABLE Result(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, eventId TEXT, serviceId TEXT, clientStatusCode INTEGER NOT NULL, timestamp INTEGER NOT NULL)";
    public final long MAX_KEEP_TIME = TimeUnit.DAYS.toMillis(30);
    private SQLiteDatabase db;

    public ResultDao(SQLiteDatabase sQLiteDatabase) {
        this.db = sQLiteDatabase;
    }

    public void insert(Result result) {
        this.db.insert(Contracts.ResultContract.TABLE_RESULT, null, getContentValues(result));
    }

    private ContentValues getContentValues(Result result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventId", result.getEventId());
        contentValues.put("serviceId", result.getServiceId());
        contentValues.put(Contracts.ResultContract.COLUMN_CLIENT_STATUS_CODE, Integer.valueOf(result.getClientStatusCode()));
        contentValues.put("timestamp", Long.valueOf(result.getTimestamp()));
        return contentValues;
    }

    public void delete(Result result) {
        this.db.delete(Contracts.ResultContract.TABLE_RESULT, "id=?", new String[]{String.valueOf(result.getId())});
    }

    public void deleteResultsByTime(long j) {
        this.db.delete(Contracts.ResultContract.TABLE_RESULT, "timestamp<=?", new String[]{String.valueOf(j)});
    }

    public boolean existUnreportedResults() {
        return getUnreportedResults().size() > 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0078, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0079, code lost:
        if (r1 != null) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0080, code lost:
        r2.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.sec.android.diagmonagent.log.ged.db.model.Result> getUnreportedResults() {
        /*
        // Method dump skipped, instructions count: 138
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.db.dao.ResultDao.getUnreportedResults():java.util.List");
    }

    public Result makeResult(Event event) {
        Result result = new Result();
        result.setServiceId(event.getServiceId());
        result.setEventId(event.getEventId());
        result.setClientStatusCode(event.getStatus());
        result.setTimestamp(event.getTimestamp());
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0095, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0096, code lost:
        if (r0 != null) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009d, code lost:
        r1.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a0, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void printAllResult() {
        /*
        // Method dump skipped, instructions count: 167
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.db.dao.ResultDao.printAllResult():void");
    }
}
