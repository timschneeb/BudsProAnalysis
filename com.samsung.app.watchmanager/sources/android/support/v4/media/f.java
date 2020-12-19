package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import java.util.List;

class f {

    interface a {
        void a();

        void b();

        void onConnected();
    }

    static class b<T extends a> extends MediaBrowser.ConnectionCallback {

        /* renamed from: a  reason: collision with root package name */
        protected final T f51a;

        public b(T t) {
            this.f51a = t;
        }

        public void onConnected() {
            this.f51a.onConnected();
        }

        public void onConnectionFailed() {
            this.f51a.b();
        }

        public void onConnectionSuspended() {
            this.f51a.a();
        }
    }

    static class c {
        public static Object a(Object obj) {
            return ((MediaBrowser.MediaItem) obj).getDescription();
        }

        public static int b(Object obj) {
            return ((MediaBrowser.MediaItem) obj).getFlags();
        }
    }

    interface d {
        void a(String str);

        void a(String str, List<?> list);
    }

    static class e<T extends d> extends MediaBrowser.SubscriptionCallback {

        /* renamed from: a  reason: collision with root package name */
        protected final T f52a;

        public e(T t) {
            this.f52a = t;
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onChildrenLoaded(String str, List<MediaBrowser.MediaItem> list) {
            this.f52a.a(str, list);
        }

        public void onError(String str) {
            this.f52a.a(str);
        }
    }

    public static Object a(Context context, ComponentName componentName, Object obj, Bundle bundle) {
        return new MediaBrowser(context, componentName, (MediaBrowser.ConnectionCallback) obj, bundle);
    }

    public static Object a(a aVar) {
        return new b(aVar);
    }

    public static Object a(d dVar) {
        return new e(dVar);
    }

    public static void a(Object obj) {
        ((MediaBrowser) obj).connect();
    }

    public static void b(Object obj) {
        ((MediaBrowser) obj).disconnect();
    }

    public static Bundle c(Object obj) {
        return ((MediaBrowser) obj).getExtras();
    }

    public static Object d(Object obj) {
        return ((MediaBrowser) obj).getSessionToken();
    }
}
