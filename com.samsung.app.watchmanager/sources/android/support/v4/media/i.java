package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

/* access modifiers changed from: package-private */
public class i {

    /* access modifiers changed from: package-private */
    public static class a {
        public static Object a() {
            return new MediaDescription.Builder();
        }

        public static Object a(Object obj) {
            return ((MediaDescription.Builder) obj).build();
        }

        public static void a(Object obj, Bitmap bitmap) {
            ((MediaDescription.Builder) obj).setIconBitmap(bitmap);
        }

        public static void a(Object obj, Uri uri) {
            ((MediaDescription.Builder) obj).setIconUri(uri);
        }

        public static void a(Object obj, Bundle bundle) {
            ((MediaDescription.Builder) obj).setExtras(bundle);
        }

        public static void a(Object obj, CharSequence charSequence) {
            ((MediaDescription.Builder) obj).setDescription(charSequence);
        }

        public static void a(Object obj, String str) {
            ((MediaDescription.Builder) obj).setMediaId(str);
        }

        public static void b(Object obj, CharSequence charSequence) {
            ((MediaDescription.Builder) obj).setSubtitle(charSequence);
        }

        public static void c(Object obj, CharSequence charSequence) {
            ((MediaDescription.Builder) obj).setTitle(charSequence);
        }
    }

    public static CharSequence a(Object obj) {
        return ((MediaDescription) obj).getDescription();
    }

    public static Object a(Parcel parcel) {
        return MediaDescription.CREATOR.createFromParcel(parcel);
    }

    public static void a(Object obj, Parcel parcel, int i) {
        ((MediaDescription) obj).writeToParcel(parcel, i);
    }

    public static Bundle b(Object obj) {
        return ((MediaDescription) obj).getExtras();
    }

    public static Bitmap c(Object obj) {
        return ((MediaDescription) obj).getIconBitmap();
    }

    public static Uri d(Object obj) {
        return ((MediaDescription) obj).getIconUri();
    }

    public static String e(Object obj) {
        return ((MediaDescription) obj).getMediaId();
    }

    public static CharSequence f(Object obj) {
        return ((MediaDescription) obj).getSubtitle();
    }

    public static CharSequence g(Object obj) {
        return ((MediaDescription) obj).getTitle();
    }
}
