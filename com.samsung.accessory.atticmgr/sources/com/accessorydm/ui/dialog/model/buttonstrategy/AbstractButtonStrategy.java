package com.accessorydm.ui.dialog.model.buttonstrategy;

import android.content.Context;
import com.accessorydm.XDMDmUtils;
import com.samsung.android.fotaprovider.log.Log;

/* access modifiers changed from: package-private */
public abstract class AbstractButtonStrategy implements ButtonStrategy {
    private final int id;
    private final String text;

    /* access modifiers changed from: protected */
    public abstract void doOnClick();

    AbstractButtonStrategy(String str, int i) {
        this.text = str;
        this.id = i;
    }

    @Override // com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy
    public final String getText() {
        return this.text;
    }

    @Override // com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy
    public final int getId() {
        return this.id;
    }

    @Override // com.accessorydm.ui.dialog.model.buttonstrategy.ButtonStrategy
    public void onClick() {
        Log.I(getClass().getSimpleName() + " - getText(): " + getText() + ", getId(): " + getId());
        doOnClick();
    }

    static String getString(int i) {
        return getContext().getString(i);
    }

    static Context getContext() {
        return XDMDmUtils.getContext();
    }
}
