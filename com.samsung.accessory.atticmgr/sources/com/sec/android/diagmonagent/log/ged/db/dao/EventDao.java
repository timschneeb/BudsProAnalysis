package com.sec.android.diagmonagent.log.ged.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.sec.android.diagmonagent.log.ged.db.dao.Contracts;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.status.ERStatus;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventDao {
    public static final String CREATE_TABLE_EVENT = "CREATE TABLE Event(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, serviceId TEXT, deviceId TEXT, serviceVersion TEXT, serviceAgreeType TEXT, sdkVersion TEXT, sdkType TEXT, serviceDefinedKey TEXT, errorCode TEXT, logPath TEXT, description TEXT, relayClientVersion TEXT, relayClientType TEXT, extension TEXT, networkMode INTEGER NOT NULL, memory TEXT, storage TEXT, status INTEGER NOT NULL, retryCount INTEGER NOT NULL, eventId TEXT, s3Path TEXT, timestamp INTEGER NOT NULL, expirationTime INTEGER NOT NULL)";
    public final long MAX_KEEP_TIME = TimeUnit.DAYS.toMillis(30);
    private SQLiteDatabase db;

    public EventDao(SQLiteDatabase sQLiteDatabase) {
        this.db = sQLiteDatabase;
    }

    public void insert(Event event) {
        this.db.insert(Contracts.EventContract.TABLE_EVENT, null, getContentValues(event));
    }

    public void update(Event event) {
        this.db.update(Contracts.EventContract.TABLE_EVENT, getContentValues(event), "id=?", new String[]{String.valueOf(event.getId())});
    }

    private ContentValues getContentValues(Event event) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("serviceId", event.getServiceId());
        contentValues.put("deviceId", event.getDeviceId());
        contentValues.put("serviceVersion", event.getServiceVersion());
        contentValues.put("serviceAgreeType", event.getServiceAgreeType());
        contentValues.put("sdkVersion", event.getSdkVersion());
        contentValues.put("sdkType", event.getSdkType());
        contentValues.put("serviceDefinedKey", event.getServiceDefinedKey());
        contentValues.put("errorCode", event.getErrorCode());
        contentValues.put(Contracts.EventContract.COLUMN_LOG_PATH, event.getLogPath());
        contentValues.put("description", event.getDescription());
        contentValues.put("relayClientVersion", event.getRelayClientVersion());
        contentValues.put("relayClientType", event.getRelayClientType());
        contentValues.put("extension", event.getExtension());
        contentValues.put(Contracts.EventContract.COLUMN_NETWORK_MODE, Integer.valueOf(event.isNetworkMode() ? 1 : 0));
        contentValues.put("memory", event.getMemory());
        contentValues.put("storage", event.getStorage());
        contentValues.put("status", Integer.valueOf(event.getStatus()));
        contentValues.put(Contracts.EventContract.COLUMN_RETRY_COUNT, Integer.valueOf(event.getRetryCount()));
        contentValues.put("eventId", event.getEventId());
        contentValues.put(Contracts.EventContract.COLUMN_S3_PATH, event.getS3Path());
        contentValues.put("timestamp", Long.valueOf(event.getTimestamp()));
        contentValues.put(Contracts.EventContract.COLUMN_EXPIRATION_TIME, Long.valueOf(event.getExpirationTime()));
        return contentValues;
    }

    public boolean existUnreportedEvents() {
        return getUnreportedAllEvents().size() > 0;
    }

    public List<Event> getUnreportedAllEvents() {
        return getEvents("status=?", new String[]{String.valueOf(100)});
    }

    public List<Event> getUnreportedCellularEvents() {
        return getEvents("status=? AND networkMode=?", new String[]{String.valueOf(100), String.valueOf(0)});
    }

    public void resetExpiredS3PathEvents() {
        for (Event event : getExpiredS3PathEvents(System.currentTimeMillis())) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serviceId", event.getServiceId());
            contentValues.put("eventId", event.getEventId());
            contentValues.put(Contracts.ResultContract.COLUMN_CLIENT_STATUS_CODE, Integer.valueOf((int) ERStatus.EVENT_ID_EXPIRED));
            contentValues.put("timestamp", Long.valueOf(event.getTimestamp()));
            this.db.insert(Contracts.ResultContract.TABLE_RESULT, null, contentValues);
            event.setEventId("");
            event.setS3Path("");
            event.setExpirationTime(0);
            update(event);
        }
    }

    private List<Event> getExpiredS3PathEvents(long j) {
        return getEvents("expirationTime>? AND expirationTime<=? AND status=?", new String[]{String.valueOf(0), String.valueOf(j), String.valueOf(100)});
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0168, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0169, code lost:
        if (r10 != null) goto L_0x016b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x016f, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0170, code lost:
        r11.addSuppressed(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0173, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<com.sec.android.diagmonagent.log.ged.db.model.Event> getEvents(java.lang.String r10, java.lang.String[] r11) {
        /*
        // Method dump skipped, instructions count: 378
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.db.dao.EventDao.getEvents(java.lang.String, java.lang.String[]):java.util.List");
    }

    public void deleteEventsByTime(long j) {
        this.db.delete(Contracts.EventContract.TABLE_EVENT, "timestamp<=?", new String[]{String.valueOf(j)});
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x01da, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x01db, code lost:
        if (r0 != null) goto L_0x01dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x01e1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x01e2, code lost:
        r1.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x01e5, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void printAllEvent() {
        /*
        // Method dump skipped, instructions count: 492
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.diagmonagent.log.ged.db.dao.EventDao.printAllEvent():void");
    }
}
