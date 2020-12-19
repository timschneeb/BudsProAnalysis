package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable;

class b implements Parcelable.Creator<ResultReceiver> {
    b() {
    }

    @Override // android.os.Parcelable.Creator
    public ResultReceiver createFromParcel(Parcel parcel) {
        return new ResultReceiver(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public ResultReceiver[] newArray(int i) {
        return new ResultReceiver[i];
    }
}
