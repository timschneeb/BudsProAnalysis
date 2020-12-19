package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;

class a implements Parcelable.Creator<ParcelImpl> {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    public ParcelImpl createFromParcel(Parcel parcel) {
        return new ParcelImpl(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public ParcelImpl[] newArray(int i) {
        return new ParcelImpl[i];
    }
}
