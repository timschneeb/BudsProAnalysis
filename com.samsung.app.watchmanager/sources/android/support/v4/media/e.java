package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat;

class e implements Parcelable.Creator<MediaBrowserCompat.MediaItem> {
    e() {
    }

    @Override // android.os.Parcelable.Creator
    public MediaBrowserCompat.MediaItem createFromParcel(Parcel parcel) {
        return new MediaBrowserCompat.MediaItem(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public MediaBrowserCompat.MediaItem[] newArray(int i) {
        return new MediaBrowserCompat.MediaItem[i];
    }
}
