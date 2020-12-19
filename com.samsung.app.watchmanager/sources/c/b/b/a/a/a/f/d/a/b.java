package c.b.b.a.a.a.f.d.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import c.b.b.a.a.d;

public class b extends SQLiteOpenHelper implements d {
    public b(Context context) {
        super(context, "SamsungAnalytics.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // c.b.b.a.a.d
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override // c.b.b.a.a.d
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER, logtype TEXT, data TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
