package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

class i implements Parcelable.Creator<PlaybackStateCompat> {
    i() {
    }

    @Override // android.os.Parcelable.Creator
    public PlaybackStateCompat createFromParcel(Parcel parcel) {
        return new PlaybackStateCompat(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public PlaybackStateCompat[] newArray(int i) {
        return new PlaybackStateCompat[i];
    }
}
