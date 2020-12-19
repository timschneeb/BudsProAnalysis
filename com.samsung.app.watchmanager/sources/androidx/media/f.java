package androidx.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.os.ResultReceiver;
import androidx.media.MediaBrowserServiceCompat;

class f extends MediaBrowserServiceCompat.i<MediaBrowserCompat.MediaItem> {
    final /* synthetic */ ResultReceiver f;
    final /* synthetic */ MediaBrowserServiceCompat g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    f(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
        super(obj);
        this.g = mediaBrowserServiceCompat;
        this.f = resultReceiver;
    }

    /* access modifiers changed from: package-private */
    public void a(MediaBrowserCompat.MediaItem mediaItem) {
        if ((a() & 2) != 0) {
            this.f.b(-1, null);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("media_item", mediaItem);
        this.f.b(0, bundle);
    }
}
