package com.github.penfeizhou.animation.loader;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

public class ResourceStreamLoader extends StreamLoader {
    private final Context mContext;
    private final int mResId;

    public ResourceStreamLoader(Context context, int i) {
        this.mContext = context.getApplicationContext();
        this.mResId = i;
    }

    /* access modifiers changed from: protected */
    @Override // com.github.penfeizhou.animation.loader.StreamLoader
    public InputStream getInputStream() throws IOException {
        return this.mContext.getResources().openRawResource(this.mResId);
    }
}
