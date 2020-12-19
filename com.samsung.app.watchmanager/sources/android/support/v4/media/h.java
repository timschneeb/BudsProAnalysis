package android.support.v4.media;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

class h implements Parcelable.Creator<MediaDescriptionCompat> {
    h() {
    }

    @Override // android.os.Parcelable.Creator
    public MediaDescriptionCompat createFromParcel(Parcel parcel) {
        return Build.VERSION.SDK_INT < 21 ? new MediaDescriptionCompat(parcel) : MediaDescriptionCompat.a(i.a(parcel));
    }

    @Override // android.os.Parcelable.Creator
    public MediaDescriptionCompat[] newArray(int i) {
        return new MediaDescriptionCompat[i];
    }
}
