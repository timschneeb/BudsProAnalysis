package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;

class k implements Parcelable.Creator<MediaMetadataCompat> {
    k() {
    }

    @Override // android.os.Parcelable.Creator
    public MediaMetadataCompat createFromParcel(Parcel parcel) {
        return new MediaMetadataCompat(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public MediaMetadataCompat[] newArray(int i) {
        return new MediaMetadataCompat[i];
    }
}
