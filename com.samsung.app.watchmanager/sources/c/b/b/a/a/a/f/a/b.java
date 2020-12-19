package c.b.b.a.a.a.f.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import c.b.b.a.a.a.i.a;

/* access modifiers changed from: package-private */
public class b extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c f1748a;

    b(c cVar) {
        this.f1748a = cVar;
    }

    public void onReceive(Context context, Intent intent) {
        this.f1748a.h = false;
        if (intent == null) {
            a.a("DLC Sender", "dlc register reply fail");
            return;
        }
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        if (action == null || extras == null) {
            a.a("DLC Sender", "dlc register reply fail");
        } else if (action.equals(this.f1748a.e)) {
            String string = extras.getString("EXTRA_STR");
            int i = extras.getInt("EXTRA_RESULT_CODE");
            a.a("DLC Sender", "register DLC result:" + string);
            if (i < 0) {
                a.a("DLC Sender", "register DLC result fail:" + string);
                return;
            }
            this.f1748a.a((c) extras.getString("EXTRA_STR_ACTION"));
        }
    }
}
