package com.accessorydm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.samsung.android.fotaprovider.log.Log;

/* access modifiers changed from: package-private */
/* compiled from: XDMDynamicReceivers */
public abstract class ReceiverWithIntentFilter extends BroadcastReceiver {
    private Intent intent;

    /* access modifiers changed from: package-private */
    public abstract void doWork();

    /* access modifiers changed from: package-private */
    public abstract IntentFilter getIntentFilter();

    ReceiverWithIntentFilter() {
    }

    public void onReceive(Context context, Intent intent2) {
        try {
            checkIntent(intent2);
            this.intent = intent2;
            doWork();
        } catch (IllegalArgumentException e) {
            Log.E(e.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public final Intent getIntent() {
        return this.intent;
    }

    /* access modifiers changed from: package-private */
    public final String getAction() {
        return this.intent.getAction();
    }

    private void checkIntent(Intent intent2) {
        if (intent2 == null) {
            throw new IllegalArgumentException("intent is null");
        } else if (TextUtils.isEmpty(intent2.getAction())) {
            throw new IllegalArgumentException("intent action is null");
        }
    }
}
