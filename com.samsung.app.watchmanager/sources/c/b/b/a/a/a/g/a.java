package c.b.b.a.a.a.g;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import c.b.b.a.a.a.c.b;
import c.b.b.a.a.a.f.d;
import c.b.b.a.a.a.i.b;
import c.b.b.a.a.a.i.e;
import c.b.b.a.a.c;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class a implements b {

    /* renamed from: a  reason: collision with root package name */
    private Context f1794a;

    /* renamed from: b  reason: collision with root package name */
    private c f1795b;

    /* renamed from: c  reason: collision with root package name */
    private List<String> f1796c;

    public a(Context context, c cVar) {
        this.f1794a = context;
        this.f1795b = cVar;
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        Uri uri;
        if (!this.f1795b.f().a()) {
            c.b.b.a.a.a.i.a.a("user do not agree setting");
            return 0;
        }
        List<String> list = this.f1796c;
        if (list == null || list.isEmpty()) {
            c.b.b.a.a.a.i.a.a("Setting Sender", "No status log");
            return 0;
        }
        if (this.f1795b.h()) {
            e.a(this.f1794a, this.f1795b);
        }
        if (!e.a(7, Long.valueOf(c.b.b.a.a.a.i.c.a(this.f1794a).getLong("status_sent_date", 0)))) {
            c.b.b.a.a.a.i.a.a("do not send setting < 7days");
            return 0;
        }
        c.b.b.a.a.a.i.a.a("send setting");
        Boolean bool = false;
        String valueOf = String.valueOf(System.currentTimeMillis());
        HashMap hashMap = new HashMap();
        hashMap.put("ts", valueOf);
        hashMap.put("t", "st");
        if (c.b.b.a.a.a.d.b.b() >= 3) {
            Uri parse = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
            c.b.b.a.a.a.i.b bVar = new c.b.b.a.a.a.i.b();
            hashMap.put("v", c.b.b.a.a.b.f1812b);
            hashMap.put("tz", String.valueOf(TimeUnit.MILLISECONDS.toMinutes((long) TimeZone.getDefault().getRawOffset())));
            ContentValues contentValues = new ContentValues();
            contentValues.put("tcType", Integer.valueOf(this.f1795b.k() ? 1 : 0));
            contentValues.put("tid", this.f1795b.e());
            contentValues.put("logType", c.b.b.a.a.a.f.c.UIX.b());
            contentValues.put("timeStamp", valueOf);
            for (String str : this.f1796c) {
                hashMap.put("sti", str);
                contentValues.put("body", bVar.a(hashMap, b.a.ONE_DEPTH));
                try {
                    uri = this.f1794a.getContentResolver().insert(parse, contentValues);
                } catch (IllegalArgumentException unused) {
                    uri = null;
                }
                if (uri != null) {
                    int parseInt = Integer.parseInt(uri.getLastPathSegment());
                    c.b.b.a.a.a.i.a.a("Send SettingLog Result = " + parseInt);
                    if (parseInt == 0) {
                        bool = true;
                    }
                }
            }
        } else {
            for (String str2 : this.f1796c) {
                hashMap.put("sti", str2);
                if (d.a(this.f1794a, c.b.b.a.a.a.d.b.b(), this.f1795b).a(hashMap) == 0) {
                    c.b.b.a.a.a.i.a.a("Setting Sender", "Send success");
                    bool = true;
                } else {
                    c.b.b.a.a.a.i.a.a("Setting Sender", "Send fail");
                }
            }
        }
        (bool.booleanValue() ? c.b.b.a.a.a.i.c.a(this.f1794a).edit().putLong("status_sent_date", System.currentTimeMillis()) : c.b.b.a.a.a.i.c.a(this.f1794a).edit().putLong("status_sent_date", 0)).apply();
        c.b.b.a.a.a.i.a.a("Save Setting Result = " + bool);
        return 0;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        this.f1796c = new b(this.f1794a).a();
    }
}
