package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

class h implements Parcelable.Creator<ParcelableVolumeInfo> {
    h() {
    }

    @Override // android.os.Parcelable.Creator
    public ParcelableVolumeInfo createFromParcel(Parcel parcel) {
        return new ParcelableVolumeInfo(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public ParcelableVolumeInfo[] newArray(int i) {
        return new ParcelableVolumeInfo[i];
    }
}
