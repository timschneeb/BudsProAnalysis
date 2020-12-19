package com.samsung.android.fotaagent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.FotaProviderFileEncryptionUtil;

public abstract class SafeBroadcastReceiver extends BroadcastReceiver {
    protected String action;
    protected Context context;
    protected Intent intent;

    /* access modifiers changed from: protected */
    public abstract void handle();

    public final void onReceive(Context context2, Intent intent2) {
        Log.I(getClass().getName());
        try {
            checkContext(context2);
            checkAccessoryController();
            checkFileBasedEncryption(context2);
            checkIntent(intent2);
            checkAction(intent2.getAction());
            this.context = context2;
            this.intent = intent2;
            this.action = intent2.getAction();
            handle();
        } catch (IllegalArgumentException | IllegalStateException e) {
            Log.E(getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public final Context getContext() {
        return this.context;
    }

    /* access modifiers changed from: protected */
    public final Intent getIntent() {
        return this.intent;
    }

    /* access modifiers changed from: protected */
    public final String getAction() {
        return this.intent.getAction();
    }

    private void checkContext(Context context2) {
        if (context2 == null) {
            throw new IllegalArgumentException("context should not be null");
        }
    }

    private void checkIntent(Intent intent2) {
        if (intent2 == null) {
            throw new IllegalArgumentException("intent should not be null");
        }
    }

    private void checkAction(String str) {
        if (str == null) {
            throw new IllegalArgumentException("action should not be null");
        }
    }

    private void checkAccessoryController() {
        if (!AccessoryController.isAvailable()) {
            throw new IllegalStateException("AccessoryController is null. Do nothing.");
        }
    }

    private void checkFileBasedEncryption(Context context2) {
        if (!FotaProviderFileEncryptionUtil.isUserUnlocked(context2)) {
            throw new IllegalStateException("Device is locked. Do nothing.");
        }
    }
}
