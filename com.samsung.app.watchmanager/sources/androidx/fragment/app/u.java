package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;

class u implements Parcelable.Creator<FragmentState> {
    u() {
    }

    @Override // android.os.Parcelable.Creator
    public FragmentState createFromParcel(Parcel parcel) {
        return new FragmentState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public FragmentState[] newArray(int i) {
        return new FragmentState[i];
    }
}
