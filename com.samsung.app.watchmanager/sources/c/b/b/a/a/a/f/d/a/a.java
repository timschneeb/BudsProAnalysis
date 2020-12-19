package c.b.b.a.a.a.f.d.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import c.b.b.a.a.a.f.c;
import c.b.b.a.a.a.f.e;
import c.b.b.a.a.d;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private d f1787a;

    /* renamed from: b  reason: collision with root package name */
    private Queue<e> f1788b;

    public a(Context context) {
        this(new b(context));
    }

    public a(d dVar) {
        this.f1788b = new LinkedBlockingQueue();
        if (dVar != null) {
            this.f1787a = dVar;
            dVar.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS logs_v2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp INTEGER, logtype TEXT, data TEXT)");
        }
        a(5L);
    }

    private Queue<e> a(String str) {
        this.f1788b.clear();
        Cursor rawQuery = this.f1787a.getReadableDatabase().rawQuery(str, null);
        while (rawQuery.moveToNext()) {
            e eVar = new e();
            eVar.b(rawQuery.getString(rawQuery.getColumnIndex("_id")));
            eVar.a(rawQuery.getString(rawQuery.getColumnIndex("data")));
            eVar.a(rawQuery.getLong(rawQuery.getColumnIndex("timestamp")));
            eVar.a(rawQuery.getString(rawQuery.getColumnIndex("logtype")).equals(c.DEVICE.b()) ? c.DEVICE : c.UIX);
            this.f1788b.add(eVar);
        }
        rawQuery.close();
        return this.f1788b;
    }

    public Queue<e> a() {
        return a("select * from logs_v2");
    }

    public Queue<e> a(int i) {
        return a("select * from logs_v2 LIMIT " + i);
    }

    public void a(long j) {
        SQLiteDatabase writableDatabase = this.f1787a.getWritableDatabase();
        writableDatabase.delete("logs_v2", "timestamp <= " + j, null);
    }

    public void a(e eVar) {
        SQLiteDatabase writableDatabase = this.f1787a.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timestamp", Long.valueOf(eVar.c()));
        contentValues.put("data", eVar.a());
        contentValues.put("logtype", eVar.d().b());
        writableDatabase.insert("logs_v2", null, contentValues);
    }

    public void a(List<String> list) {
        SQLiteDatabase writableDatabase = this.f1787a.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            int size = list.size();
            int i = 0;
            while (size > 0) {
                int i2 = 900;
                if (size < 900) {
                    i2 = size;
                }
                int i3 = i + i2;
                List<String> subList = list.subList(i, i3);
                writableDatabase.delete("logs_v2", ("_id IN(" + new String(new char[(subList.size() - 1)]).replaceAll("\u0000", "?,")) + "?)", (String[]) subList.toArray(new String[0]));
                size -= i2;
                i = i3;
            }
            list.clear();
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
        writableDatabase.endTransaction();
    }
}
