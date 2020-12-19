package c.b.b.a.a.a.f.c;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import c.b.b.a.a.a.c.b;
import c.b.b.a.a.a.i.a;
import c.b.b.a.a.a.i.c;

public class f implements b {

    /* renamed from: a  reason: collision with root package name */
    Uri f1778a = Uri.parse("content://com.sec.android.log.diagmonagent.sa/common");

    /* renamed from: b  reason: collision with root package name */
    Uri f1779b = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");

    /* renamed from: c  reason: collision with root package name */
    private Context f1780c;

    /* renamed from: d  reason: collision with root package name */
    ContentValues f1781d;
    int e;
    Uri f = null;

    public f(Context context, int i, ContentValues contentValues) {
        this.f1780c = context;
        this.e = i;
        this.f1781d = contentValues;
    }

    @Override // c.b.b.a.a.a.c.b
    public int onFinish() {
        try {
            if (this.f != null) {
                int parseInt = Integer.parseInt(this.f.getLastPathSegment());
                a.a("SendLog Result = " + parseInt);
                boolean z = true;
                if (this.e == 1) {
                    if (parseInt != 0) {
                        z = false;
                    }
                    c.a(this.f1780c).edit().putBoolean("sendCommonSuccess", z).apply();
                    a.a("Save Result = " + z);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return 0;
    }

    @Override // c.b.b.a.a.a.c.b
    public void run() {
        Uri insert;
        try {
            if (this.e == 1) {
                insert = this.f1780c.getContentResolver().insert(this.f1778a, this.f1781d);
            } else if (this.e == 2) {
                insert = this.f1780c.getContentResolver().insert(this.f1779b, this.f1781d);
            } else {
                return;
            }
            this.f = insert;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
