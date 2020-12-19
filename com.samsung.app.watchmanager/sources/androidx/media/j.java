package androidx.media;

import android.os.Parcel;
import android.support.v4.media.MediaBrowserCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.v;
import java.util.ArrayList;
import java.util.List;

class j extends MediaBrowserServiceCompat.i<List<MediaBrowserCompat.MediaItem>> {
    final /* synthetic */ v.c f;
    final /* synthetic */ MediaBrowserServiceCompat.d g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    j(MediaBrowserServiceCompat.d dVar, Object obj, v.c cVar) {
        super(obj);
        this.g = dVar;
        this.f = cVar;
    }

    /* access modifiers changed from: package-private */
    public void a(List<MediaBrowserCompat.MediaItem> list) {
        ArrayList arrayList;
        if (list != null) {
            arrayList = new ArrayList();
            for (MediaBrowserCompat.MediaItem mediaItem : list) {
                Parcel obtain = Parcel.obtain();
                mediaItem.writeToParcel(obtain, 0);
                arrayList.add(obtain);
            }
        } else {
            arrayList = null;
        }
        this.f.a((Object) arrayList);
    }
}
