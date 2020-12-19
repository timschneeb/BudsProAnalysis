package com.github.penfeizhou.animation.webp;

import android.content.Context;
import com.github.penfeizhou.animation.FrameAnimationDrawable;
import com.github.penfeizhou.animation.decode.FrameSeqDecoder;
import com.github.penfeizhou.animation.loader.AssetStreamLoader;
import com.github.penfeizhou.animation.loader.FileLoader;
import com.github.penfeizhou.animation.loader.Loader;
import com.github.penfeizhou.animation.loader.ResourceStreamLoader;
import com.github.penfeizhou.animation.webp.decode.WebPDecoder;

public class WebPDrawable extends FrameAnimationDrawable<WebPDecoder> {
    public WebPDrawable(Loader loader) {
        super(loader);
    }

    public WebPDrawable(WebPDecoder webPDecoder) {
        super(webPDecoder);
    }

    /* access modifiers changed from: protected */
    @Override // com.github.penfeizhou.animation.FrameAnimationDrawable
    public WebPDecoder createFrameSeqDecoder(Loader loader, FrameSeqDecoder.RenderListener renderListener) {
        return new WebPDecoder(loader, renderListener);
    }

    public static WebPDrawable fromAsset(Context context, String str) {
        return new WebPDrawable(new AssetStreamLoader(context, str));
    }

    public static WebPDrawable fromFile(String str) {
        return new WebPDrawable(new FileLoader(str));
    }

    public static WebPDrawable fromResource(Context context, int i) {
        return new WebPDrawable(new ResourceStreamLoader(context, i));
    }
}
