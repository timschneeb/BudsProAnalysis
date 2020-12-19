package com.samsung.android.app.twatchmanager.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class BaseContentProvider extends ContentProvider {
    static final String APPS_TABLE_NAME = "Apps";
    public static final Uri APP_CONTENT_URI = Uri.parse(APP_URL);
    public static final String APP_NAME = "app_name";
    static final String APP_URL = "content://com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider/Apps";
    public static final String AUTHORITY = "com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider";
    static final String CREATE_DB_TABLE1 = " CREATE TABLE Apps (_id INTEGER PRIMARY KEY AUTOINCREMENT, package_name  TEXT NOT NULL, app_name TEXT NOT NULL, bt_id TEXT NOT NULL, update_cancel_count INTEGER DEFAULT 0,version TEXT NOT NULL, image blob);";
    static final String CREATE_DB_TABLE2 = " CREATE TABLE Device (_id INTEGER PRIMARY KEY AUTOINCREMENT, device_name  TEXT NOT NULL, device_fixed_name  TEXT NOT NULL, bt_id TEXT NOT NULL, package_name  TEXT NOT NULL, last_launch INTEGER TEXT NOT NULL, connected  INTEGER TEXT NOT NULL, necklet_auto_connection  TEXT NOT NULL, model_name TEXT NOT NULL DEFAULT 'No model name', auto_switch INTEGER DEFAULT 0, reserved_a TEXT, reserved_b TEXT, supports_pairing INTEGER DEFAULT 1);";
    static final String DATABASE_NAME = "uhm.db";
    static final int DATABASE_VERSION = 8;
    public static final String DEVICE_AUTO_SWITCH = "auto_switch";
    public static final String DEVICE_BT_ID = "bt_id";
    public static final String DEVICE_CONNECTED = "connected";
    public static final Uri DEVICE_CONTENT_URI = Uri.parse(DEVICE_URL);
    public static final String DEVICE_FEATURE_NECKLETAUTOCONNECTION = "necklet_auto_connection";
    public static final String DEVICE_FIXED_NAME = "device_fixed_name";
    public static final String DEVICE_MODEL_NAME = "model_name";
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_RESERVED_A = "reserved_a";
    public static final String DEVICE_RESERVED_B = "reserved_b";
    public static final String DEVICE_SUPPORTS_PAIRING = "supports_pairing";
    static final String DEVICE_TABLE_NAME = "Device";
    static final String DEVICE_URL = "content://com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider/Device";
    public static final String IMAGE = "image";
    public static final String LAST_LAUNCH = "last_launch";
    public static final String LOCAL = "local";
    static final String METADATA_TABLE_NAME = "android_metadata";
    public static final String PACKAGE_NAME = "package_name";
    public static final Uri SETTINGS_CONTENT_URI = Uri.parse(SETTINGS_URL);
    static final String SETTINGS_URL = "content://com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider/Settings";
    private static final String TAG = ("thinUHM/" + BaseContentProvider.class.getSimpleName());
    public static final String UPDATE_CANCEL_COUNT = "update_cancel_count";
    private static final int URI_TYPE_APP_REGISTRY = 1036;
    private static final int URI_TYPE_APP_REGISTRY_ID = 1037;
    private static final int URI_TYPE_DEVICE_REGISTRY = 1038;
    private static final int URI_TYPE_DEVICE_REGISTRY_ID = 1039;
    private static final int URI_TYPE_SETTINGS = 1040;
    public static final String VERSION = "version";
    static final String _ID = "_id";
    private static final UriMatcher sURIMatcher = new UriMatcher(-1);
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static String TAG = "DatabaseHelper";

        DatabaseHelper(Context context) {
            super(context, BaseContentProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 8);
        }

        private void dropTables(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS Apps");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS Device");
            sQLiteDatabase.execSQL(SettingsEventQuery.DROP_TABLE);
        }

        private void upgradeToVersion4(SQLiteDatabase sQLiteDatabase) {
            Log.d(TAG, "upgradeToVersion4 starts");
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN connected INTEGER DEFAULT 1;");
            } catch (SQLException unused) {
                Log.d(TAG, "connected column already exists");
            }
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN device_fixed_name TEXT NOT NULL DEFAULT 'No name';");
            } catch (SQLException unused2) {
                Log.d(TAG, "device_fixed_name  column already exists");
            }
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN necklet_auto_connection TEXT NOT NULL DEFAULT 'null';");
            } catch (SQLException unused3) {
                Log.d(TAG, "necklet_auto_connection column already exists");
            }
            Log.d(TAG, "upgradeToVersion4 ends");
        }

        private void upgradeToVersion5(SQLiteDatabase sQLiteDatabase) {
            Log.d(TAG, "upgradeToVersion5 starts");
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN model_name TEXT NOT NULL DEFAULT 'No model name';");
            } catch (SQLException unused) {
                Log.d(TAG, "model_name  column already exists");
            }
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN auto_switch INTEGER DEFAULT 0;");
            } catch (SQLException unused2) {
                Log.d(TAG, "auto_switch column already exists");
            }
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN reserved_a TEXT;");
            } catch (SQLException unused3) {
                Log.d(TAG, "reserved_a  column already exists");
            }
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN reserved_b TEXT;");
            } catch (SQLException unused4) {
                Log.d(TAG, "reserved_b  column already exists");
            }
            Log.d(TAG, "upgradeToVersion5 ends");
        }

        private void upgradeToVersion6(SQLiteDatabase sQLiteDatabase) {
            Log.d(TAG, "upgradeToVersion6 starts");
            sQLiteDatabase.execSQL(SettingsEventQuery.CREATE_TABLE);
            Log.d(TAG, "upgradeToVersion6 ends");
        }

        private void upgradeToVersion7(SQLiteDatabase sQLiteDatabase) {
            Log.d(TAG, "upgradeToVersion7 starts");
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Device ADD COLUMN supports_pairing INTEGER DEFAULT 1;");
            } catch (SQLException unused) {
                Log.d(TAG, "supports_pairing column already exists");
            }
            Log.d(TAG, "upgradeToVersion7 ends");
        }

        private void upgradeToVersion8(SQLiteDatabase sQLiteDatabase) {
            Log.d(TAG, "upgradeToVersion8 starts");
            try {
                sQLiteDatabase.execSQL("ALTER TABLE Apps ADD COLUMN update_cancel_count INTEGER DEFAULT 0;");
            } catch (SQLException unused) {
                Log.d(TAG, "update_cancel_count column already exists");
            }
            Log.d(TAG, "upgradeToVersion8 ends");
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.d(TAG, "onCreate starts");
            sQLiteDatabase.execSQL(BaseContentProvider.CREATE_DB_TABLE1);
            sQLiteDatabase.execSQL(BaseContentProvider.CREATE_DB_TABLE2);
            sQLiteDatabase.execSQL(SettingsEventQuery.CREATE_TABLE);
            Log.d(TAG, "onCreate ends");
        }

        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            String str = TAG;
            Log.d(str, "onDowngrade, oldVersion = " + i + ", newVersion = " + i2);
            onUpgrade(sQLiteDatabase, i, i2);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            long nanoTime = System.nanoTime();
            String str = TAG;
            Log.d(str, "onUpgrade, oldVersion = " + i + ", newVersion = " + i2);
            if (i2 > i) {
                if (i == 3) {
                    try {
                        upgradeToVersion4(sQLiteDatabase);
                        i++;
                    } catch (SQLiteException e) {
                        String str2 = TAG;
                        Log.e(str2, "onUpgrade: SQLiteException, recreating db. " + e);
                        String str3 = TAG;
                        Log.e(str3, "(oldVersion was " + i + ")");
                        dropTables(sQLiteDatabase);
                        onCreate(sQLiteDatabase);
                        return;
                    }
                }
                if (i == 4) {
                    upgradeToVersion5(sQLiteDatabase);
                    i++;
                }
                if (i == 5) {
                    upgradeToVersion6(sQLiteDatabase);
                    i++;
                }
                if (i == 6) {
                    upgradeToVersion7(sQLiteDatabase);
                    i++;
                }
                if (i == 7) {
                    upgradeToVersion8(sQLiteDatabase);
                }
            } else {
                Log.e(TAG, "Don't know how to downgrade. Will not touch database and hope they are compatible.");
            }
            long nanoTime2 = System.nanoTime();
            String str4 = TAG;
            Log.d(str4, "UHM upgrade took " + ((nanoTime2 - nanoTime) / 1000000) + "ms");
        }
    }

    /* access modifiers changed from: package-private */
    public interface SettingsEventQuery {
        public static final String[] COLUMNS = {"keyField", "keyValue"};
        public static final String CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS Settings(" + COLUMNS[0] + " TEXT, " + COLUMNS[1] + " TEXT )");
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS Settings";
        public static final int KEY_FIELD = 0;
        public static final int KEY_VALUE = 1;
        public static final String TABLE = "Settings";
    }

    static {
        sURIMatcher.addURI(AUTHORITY, APPS_TABLE_NAME, URI_TYPE_APP_REGISTRY);
        sURIMatcher.addURI(AUTHORITY, "Apps/#", URI_TYPE_APP_REGISTRY_ID);
        sURIMatcher.addURI(AUTHORITY, DEVICE_TABLE_NAME, URI_TYPE_DEVICE_REGISTRY);
        sURIMatcher.addURI(AUTHORITY, "Device/#", URI_TYPE_DEVICE_REGISTRY_ID);
        sURIMatcher.addURI(AUTHORITY, SettingsEventQuery.TABLE, URI_TYPE_SETTINGS);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int i;
        StringBuilder sb;
        String str2;
        int i2;
        this.db = this.dbHelper.getWritableDatabase();
        String str3 = "";
        switch (sURIMatcher.match(uri)) {
            case URI_TYPE_APP_REGISTRY /*{ENCODED_INT: 1036}*/:
                i = this.db.delete(APPS_TABLE_NAME, str, strArr);
                break;
            case URI_TYPE_APP_REGISTRY_ID /*{ENCODED_INT: 1037}*/:
                SQLiteDatabase sQLiteDatabase = this.db;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("_id = ");
                sb2.append(uri.getPathSegments().get(1));
                if (!TextUtils.isEmpty(str)) {
                    str3 = " AND (" + str + ')';
                }
                sb2.append(str3);
                i = sQLiteDatabase.delete(APPS_TABLE_NAME, sb2.toString(), strArr);
                break;
            case URI_TYPE_DEVICE_REGISTRY /*{ENCODED_INT: 1038}*/:
                i2 = this.db.delete(DEVICE_TABLE_NAME, str, strArr);
                str2 = TAG;
                sb = new StringBuilder();
                sb.append("delete() db delete for device table, count : ");
                sb.append(i2);
                sb.append("selection : ");
                sb.append(str);
                sb.append(" selectionArg : ");
                sb.append(Arrays.toString(strArr));
                Log.d(str2, sb.toString());
                i = i2;
                break;
            case URI_TYPE_DEVICE_REGISTRY_ID /*{ENCODED_INT: 1039}*/:
                SQLiteDatabase sQLiteDatabase2 = this.db;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("_id = ");
                sb3.append(uri.getPathSegments().get(1));
                if (!TextUtils.isEmpty(str)) {
                    str3 = " AND (" + str + ')';
                }
                sb3.append(str3);
                i2 = sQLiteDatabase2.delete(DEVICE_TABLE_NAME, sb3.toString(), strArr);
                str2 = TAG;
                sb = new StringBuilder();
                sb.append("delete() db delete for device table, count : ");
                sb.append(i2);
                sb.append("selection : ");
                sb.append(str);
                sb.append(" selectionArg : ");
                sb.append(Arrays.toString(strArr));
                Log.d(str2, sb.toString());
                i = i2;
                break;
            case URI_TYPE_SETTINGS /*{ENCODED_INT: 1040}*/:
                i = this.db.delete(SettingsEventQuery.TABLE, str, strArr);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(fileDescriptor, printWriter, strArr);
    }

    public String getType(Uri uri) {
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        if (r4 > 0) goto L_0x001f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.net.Uri insert(android.net.Uri r7, android.content.ContentValues r8) {
        /*
        // Method dump skipped, instructions count: 138
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.contentprovider.BaseContentProvider.insert(android.net.Uri, android.content.ContentValues):android.net.Uri");
    }

    public boolean onCreate() {
        this.dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        StringBuilder sb;
        this.db = this.dbHelper.getWritableDatabase();
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        HashMap hashMap = new HashMap();
        sQLiteQueryBuilder.setTables(METADATA_TABLE_NAME);
        hashMap.clear();
        hashMap.put(LOCAL, LOCAL);
        sQLiteQueryBuilder.setProjectionMap(hashMap);
        boolean z = true;
        sQLiteQueryBuilder.setStrict(true);
        sQLiteQueryBuilder.setTables(uri.equals(SETTINGS_CONTENT_URI) ? SettingsEventQuery.TABLE : uri.equals(APP_CONTENT_URI) ? APPS_TABLE_NAME : DEVICE_TABLE_NAME);
        sQLiteQueryBuilder.setStrict(true);
        switch (sURIMatcher.match(uri)) {
            case URI_TYPE_APP_REGISTRY /*{ENCODED_INT: 1036}*/:
                hashMap.clear();
                hashMap.put(_ID, _ID);
                hashMap.put("package_name", "package_name");
                hashMap.put(APP_NAME, APP_NAME);
                hashMap.put(DEVICE_BT_ID, DEVICE_BT_ID);
                hashMap.put(UPDATE_CANCEL_COUNT, UPDATE_CANCEL_COUNT);
                hashMap.put(VERSION, VERSION);
                hashMap.put(IMAGE, IMAGE);
                sQLiteQueryBuilder.setProjectionMap(hashMap);
                break;
            case URI_TYPE_APP_REGISTRY_ID /*{ENCODED_INT: 1037}*/:
                sb = new StringBuilder();
                sb.append("_id=");
                sb.append(uri.getPathSegments().get(1));
                sQLiteQueryBuilder.appendWhere(sb.toString());
                break;
            case URI_TYPE_DEVICE_REGISTRY /*{ENCODED_INT: 1038}*/:
                hashMap.clear();
                hashMap.put(_ID, _ID);
                hashMap.put(DEVICE_NAME, DEVICE_NAME);
                hashMap.put(DEVICE_FIXED_NAME, DEVICE_FIXED_NAME);
                hashMap.put(DEVICE_BT_ID, DEVICE_BT_ID);
                hashMap.put("package_name", "package_name");
                hashMap.put(LAST_LAUNCH, LAST_LAUNCH);
                hashMap.put(DEVICE_CONNECTED, DEVICE_CONNECTED);
                hashMap.put(DEVICE_FEATURE_NECKLETAUTOCONNECTION, DEVICE_FEATURE_NECKLETAUTOCONNECTION);
                hashMap.put(DEVICE_MODEL_NAME, DEVICE_MODEL_NAME);
                hashMap.put(DEVICE_AUTO_SWITCH, DEVICE_AUTO_SWITCH);
                hashMap.put(DEVICE_RESERVED_A, DEVICE_RESERVED_A);
                hashMap.put(DEVICE_RESERVED_B, DEVICE_RESERVED_B);
                hashMap.put(DEVICE_SUPPORTS_PAIRING, DEVICE_SUPPORTS_PAIRING);
                sQLiteQueryBuilder.setProjectionMap(hashMap);
                break;
            case URI_TYPE_DEVICE_REGISTRY_ID /*{ENCODED_INT: 1039}*/:
                sb = new StringBuilder();
                sb.append("_id=");
                sb.append(uri.getPathSegments().get(1));
                sQLiteQueryBuilder.appendWhere(sb.toString());
                break;
            case URI_TYPE_SETTINGS /*{ENCODED_INT: 1040}*/:
                hashMap.clear();
                hashMap.put("keyField", "keyField");
                hashMap.put("keyValue", "keyValue");
                z = false;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = _ID;
        }
        SQLiteDatabase sQLiteDatabase = this.db;
        if (!z) {
            str2 = null;
        }
        Cursor query = sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, strArr2, null, null, str2);
        if (query != null) {
            query.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return query;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int i;
        SQLiteDatabase sQLiteDatabase;
        String str2;
        int i2;
        StringBuilder sb;
        this.db = this.dbHelper.getWritableDatabase();
        String str3 = "";
        switch (sURIMatcher.match(uri)) {
            case URI_TYPE_APP_REGISTRY /*{ENCODED_INT: 1036}*/:
                sQLiteDatabase = this.db;
                i = sQLiteDatabase.update(APPS_TABLE_NAME, contentValues, str, strArr);
                break;
            case URI_TYPE_APP_REGISTRY_ID /*{ENCODED_INT: 1037}*/:
                sQLiteDatabase = this.db;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("_id = ");
                sb2.append(uri.getPathSegments().get(1));
                if (!TextUtils.isEmpty(str)) {
                    str3 = " AND (" + str + ')';
                }
                sb2.append(str3);
                str = sb2.toString();
                i = sQLiteDatabase.update(APPS_TABLE_NAME, contentValues, str, strArr);
                break;
            case URI_TYPE_DEVICE_REGISTRY /*{ENCODED_INT: 1038}*/:
                i2 = this.db.update(DEVICE_TABLE_NAME, contentValues, str, strArr);
                str2 = TAG;
                sb = new StringBuilder();
                sb.append("update() db update for device table, count : ");
                sb.append(i2);
                sb.append(" valuus : ");
                sb.append(contentValues);
                Log.d(str2, sb.toString());
                i = i2;
                break;
            case URI_TYPE_DEVICE_REGISTRY_ID /*{ENCODED_INT: 1039}*/:
                SQLiteDatabase sQLiteDatabase2 = this.db;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("_id = ");
                sb3.append(uri.getPathSegments().get(1));
                if (!TextUtils.isEmpty(str)) {
                    str3 = " AND (" + str + ')';
                }
                sb3.append(str3);
                i2 = sQLiteDatabase2.update(DEVICE_TABLE_NAME, contentValues, sb3.toString(), strArr);
                str2 = TAG;
                sb = new StringBuilder();
                sb.append("update() db update for device table, count : ");
                sb.append(i2);
                sb.append(" valuus : ");
                sb.append(contentValues);
                Log.d(str2, sb.toString());
                i = i2;
                break;
            case URI_TYPE_SETTINGS /*{ENCODED_INT: 1040}*/:
                i = this.db.update(SettingsEventQuery.TABLE, contentValues, str, strArr);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }
}
