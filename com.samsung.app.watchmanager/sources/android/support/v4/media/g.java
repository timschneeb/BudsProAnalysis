package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.v4.media.f;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.List;

class g {

    interface a extends f.d {
        void a(String str, Bundle bundle);

        void a(String str, List<?> list, Bundle bundle);
    }

    static class b<T extends a> extends f.e<T> {
        b(T t) {
            super(t);
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onChildrenLoaded(String str, List<MediaBrowser.MediaItem> list, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            ((a) this.f52a).a(str, list, bundle);
        }

        public void onError(String str, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            ((a) this.f52a).a(str, bundle);
        }
    }

    static Object a(a aVar) {
        return new b(aVar);
    }
}
