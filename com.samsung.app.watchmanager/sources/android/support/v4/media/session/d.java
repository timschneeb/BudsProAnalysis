package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;

class d implements Parcelable.Creator<MediaSessionCompat.QueueItem> {
    d() {
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat.QueueItem createFromParcel(Parcel parcel) {
        return new MediaSessionCompat.QueueItem(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat.QueueItem[] newArray(int i) {
        return new MediaSessionCompat.QueueItem[i];
    }
}
