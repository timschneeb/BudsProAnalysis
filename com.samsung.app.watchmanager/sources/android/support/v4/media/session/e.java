package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;

class e implements Parcelable.Creator<MediaSessionCompat.ResultReceiverWrapper> {
    e() {
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat.ResultReceiverWrapper createFromParcel(Parcel parcel) {
        return new MediaSessionCompat.ResultReceiverWrapper(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat.ResultReceiverWrapper[] newArray(int i) {
        return new MediaSessionCompat.ResultReceiverWrapper[i];
    }
}
