package androidx.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import androidx.media.w;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
public class x {

    /* renamed from: a  reason: collision with root package name */
    static Field f933a;

    static class a extends w.a {
        a(Context context, c cVar) {
            super(context, cVar);
        }

        @Override // android.service.media.MediaBrowserService
        public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle bundle) {
            MediaSessionCompat.a(bundle);
            ((c) this.f931a).a(str, new b(result), bundle);
        }
    }

    /* access modifiers changed from: package-private */
    public static class b {

        /* renamed from: a  reason: collision with root package name */
        MediaBrowserService.Result f934a;

        b(MediaBrowserService.Result result) {
            this.f934a = result;
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

        public void a(List<Parcel> list, int i) {
            try {
                x.f933a.setInt(this.f934a, i);
            } catch (IllegalAccessException e) {
                Log.w("MBSCompatApi26", e);
            }
            this.f934a.sendResult(a(list));
        }
    }

    public interface c extends w.b {
        void a(String str, b bVar, Bundle bundle);
    }

    static {
        try {
            f933a = MediaBrowserService.Result.class.getDeclaredField("mFlags");
            f933a.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.w("MBSCompatApi26", e);
        }
    }

    public static Object a(Context context, c cVar) {
        return new a(context, cVar);
    }
}
