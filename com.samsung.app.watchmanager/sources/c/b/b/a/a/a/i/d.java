package c.b.b.a.a.a.i;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import c.b.b.a.a.c;

/* access modifiers changed from: package-private */
public class d extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c f1809a;

    d(c cVar) {
        this.f1809a = cVar;
    }

    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("receive BR ");
        sb.append(intent != null ? intent.getAction() : "null");
        a.c(sb.toString());
        if (intent != null && "android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
            e.b(context, this.f1809a);
        }
    }
}
