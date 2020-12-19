package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;

class m implements Parcelable.Creator<RatingCompat> {
    m() {
    }

    @Override // android.os.Parcelable.Creator
    public RatingCompat createFromParcel(Parcel parcel) {
        return new RatingCompat(parcel.readInt(), parcel.readFloat());
    }

    @Override // android.os.Parcelable.Creator
    public RatingCompat[] newArray(int i) {
        return new RatingCompat[i];
    }
}
