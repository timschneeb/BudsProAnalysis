package c.b.b.a.a.a.f.c;

import android.content.ContentValues;
import android.content.Context;
import c.b.b.a.a.a.d.b;
import c.b.b.a.a.a.f.a;
import c.b.b.a.a.a.f.e;
import c.b.b.a.a.c;
import java.util.Map;
import java.util.Queue;

public class d extends a {
    private b g;
    private boolean h = false;
    private int i = 0;

    public d(Context context, c cVar) {
        super(context, cVar);
        if (b.b() == 2) {
            this.g = new b(context, new c(this));
            this.g.a();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (b.b() == 2 && this.i == 0) {
            Queue<e> b2 = this.e.b();
            while (!b2.isEmpty()) {
                this.f.a(new e(this.g.b(), this.f1744b, b2.poll()));
            }
        }
    }

    @Override // c.b.b.a.a.a.f.b
    public int a(Map<String, String> map) {
        if (b.b() == 3) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tcType", Integer.valueOf(this.f1744b.k() ? 1 : 0));
            contentValues.put("tid", this.f1744b.e());
            contentValues.put("logType", b(map).b());
            contentValues.put("timeStamp", Long.valueOf(map.get("ts")));
            e(map);
            contentValues.put("body", d(map));
            this.f.a(new f(this.f1743a, 2, contentValues));
            return 0;
        } else if (this.g.d()) {
            return -8;
        } else {
            int i2 = this.i;
            if (i2 != 0) {
                return i2;
            }
            c(map);
            if (!this.g.c()) {
                this.g.a();
            } else if (this.g.b() != null) {
                b();
                if (this.h) {
                    a();
                    this.h = false;
                }
            }
            return this.i;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [int, boolean] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
        // Method dump skipped, instructions count: 183
        */
        throw new UnsupportedOperationException("Method not decompiled: c.b.b.a.a.a.f.c.d.a():void");
    }
}
