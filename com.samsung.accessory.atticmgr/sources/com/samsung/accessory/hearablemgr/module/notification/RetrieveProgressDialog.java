package com.samsung.accessory.hearablemgr.module.notification;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.samsung.accessory.atticmgr.R;

public class RetrieveProgressDialog extends Dialog {
    private String message;

    public RetrieveProgressDialog(Context context, String str) {
        super(context);
        this.message = str;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_retrieve_progress);
        ((TextView) findViewById(R.id.progress_bar_text)).setText(this.message);
    }
}
