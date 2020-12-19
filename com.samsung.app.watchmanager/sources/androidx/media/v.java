package androidx.media;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.ArrayList;
import java.util.List;

class v {

    static class a {

        /* renamed from: a  reason: collision with root package name */
        final String f929a;

        /* renamed from: b  reason: collision with root package name */
        final Bundle f930b;
    }

    static class b extends MediaBrowserService {

        /* renamed from: a  reason: collision with root package name */
        final d f931a;

        b(Context context, d dVar) {
            attachBaseContext(context);
            this.f931a = dVar;
        }

        public MediaBrowserService.BrowserRoot onGetRoot(String str, int i, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            a a2 = this.f931a.a(str, i, bundle == null ? null : new Bundle(bundle));
            if (a2 == null) {
                return null;
            }
            return new MediaBrowserService.BrowserRoot(a2.f929a, a2.f930b);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
            this.f931a.b(str, new c<>(result));
        }
    }

    /* access modifiers changed from: package-private */
    public static class c<T> {

        /* renamed from: a  reason: collision with root package name */
        MediaBrowserService.Result f932a;

        c(MediaBrowserService.Result result) {
            this.f932a = result;
        }

        /* access modifiers changed from: package-private */
        public List<MediaBrowser.MediaItem> a(List<Parcel> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcel parcel : list) {
                parcel.setDataPosition(0);
                arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            }
            return arrayList;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r0v4, resolved type: android.service.media.MediaBrowserService$Result */
        /* JADX DEBUG: Multi-variable search result rejected for r0v5, resolved type: android.service.media.MediaBrowserService$Result */
        /* JADX WARN: Multi-variable type inference failed */
        public void a(T t) {
            if (t instanceof List) {
                this.f932a.sendResult(a((List<Parcel>) t));
            } else if (t instanceof Parcel) {
                T t2 = t;
                t2.setDataPosition(0);
                this.f932a.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(t2));
                t2.recycle();
            } else {
                this.f932a.sendResult(null);
            }
        }
    }

    public interface d {
        a a(String str, int i, Bundle bundle);

        void b(String str, c<List<Parcel>> cVar);
    }

    public static IBinder a(Object obj, Intent intent) {
        return ((MediaBrowserService) obj).onBind(intent);
    }

    public static Object a(Context context, d dVar) {
        return new b(context, dVar);
    }

    public static void a(Object obj) {
        ((MediaBrowserService) obj).onCreate();
    }
}
