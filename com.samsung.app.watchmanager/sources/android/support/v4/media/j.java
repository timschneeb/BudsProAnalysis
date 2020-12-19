package android.support.v4.media;

import android.media.MediaDescription;
import android.net.Uri;

/* access modifiers changed from: package-private */
public class j {

    /* access modifiers changed from: package-private */
    public static class a {
        public static void a(Object obj, Uri uri) {
            ((MediaDescription.Builder) obj).setMediaUri(uri);
        }
    }

    public static Uri a(Object obj) {
        return ((MediaDescription) obj).getMediaUri();
    }
}
