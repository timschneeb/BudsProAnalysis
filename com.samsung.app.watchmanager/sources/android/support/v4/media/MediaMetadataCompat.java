package android.support.v4.media;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import b.c.b;

public final class MediaMetadataCompat implements Parcelable {
    public static final Parcelable.Creator<MediaMetadataCompat> CREATOR = new k();

    /* renamed from: a  reason: collision with root package name */
    static final b<String, Integer> f38a = new b<>();

    /* renamed from: b  reason: collision with root package name */
    private static final String[] f39b = {"android.media.metadata.TITLE", "android.media.metadata.ARTIST", "android.media.metadata.ALBUM", "android.media.metadata.ALBUM_ARTIST", "android.media.metadata.WRITER", "android.media.metadata.AUTHOR", "android.media.metadata.COMPOSER"};

    /* renamed from: c  reason: collision with root package name */
    private static final String[] f40c = {"android.media.metadata.DISPLAY_ICON", "android.media.metadata.ART", "android.media.metadata.ALBUM_ART"};

    /* renamed from: d  reason: collision with root package name */
    private static final String[] f41d = {"android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.ART_URI", "android.media.metadata.ALBUM_ART_URI"};
    final Bundle e;
    private Object f;

    static {
        f38a.put("android.media.metadata.TITLE", 1);
        f38a.put("android.media.metadata.ARTIST", 1);
        f38a.put("android.media.metadata.DURATION", 0);
        f38a.put("android.media.metadata.ALBUM", 1);
        f38a.put("android.media.metadata.AUTHOR", 1);
        f38a.put("android.media.metadata.WRITER", 1);
        f38a.put("android.media.metadata.COMPOSER", 1);
        f38a.put("android.media.metadata.COMPILATION", 1);
        f38a.put("android.media.metadata.DATE", 1);
        f38a.put("android.media.metadata.YEAR", 0);
        f38a.put("android.media.metadata.GENRE", 1);
        f38a.put("android.media.metadata.TRACK_NUMBER", 0);
        f38a.put("android.media.metadata.NUM_TRACKS", 0);
        f38a.put("android.media.metadata.DISC_NUMBER", 0);
        f38a.put("android.media.metadata.ALBUM_ARTIST", 1);
        f38a.put("android.media.metadata.ART", 2);
        f38a.put("android.media.metadata.ART_URI", 1);
        f38a.put("android.media.metadata.ALBUM_ART", 2);
        f38a.put("android.media.metadata.ALBUM_ART_URI", 1);
        f38a.put("android.media.metadata.USER_RATING", 3);
        f38a.put("android.media.metadata.RATING", 3);
        f38a.put("android.media.metadata.DISPLAY_TITLE", 1);
        f38a.put("android.media.metadata.DISPLAY_SUBTITLE", 1);
        f38a.put("android.media.metadata.DISPLAY_DESCRIPTION", 1);
        f38a.put("android.media.metadata.DISPLAY_ICON", 2);
        f38a.put("android.media.metadata.DISPLAY_ICON_URI", 1);
        f38a.put("android.media.metadata.MEDIA_ID", 1);
        f38a.put("android.media.metadata.BT_FOLDER_TYPE", 0);
        f38a.put("android.media.metadata.MEDIA_URI", 1);
        f38a.put("android.media.metadata.ADVERTISEMENT", 0);
        f38a.put("android.media.metadata.DOWNLOAD_STATUS", 0);
    }

    MediaMetadataCompat(Parcel parcel) {
        this.e = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
    }

    public static MediaMetadataCompat a(Object obj) {
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        l.a(obj, obtain, 0);
        obtain.setDataPosition(0);
        MediaMetadataCompat createFromParcel = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        createFromParcel.f = obj;
        return createFromParcel;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.e);
    }
}
