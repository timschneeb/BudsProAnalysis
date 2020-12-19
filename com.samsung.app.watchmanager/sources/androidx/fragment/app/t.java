package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;

class t implements Parcelable.Creator<FragmentManagerState> {
    t() {
    }

    @Override // android.os.Parcelable.Creator
    public FragmentManagerState createFromParcel(Parcel parcel) {
        return new FragmentManagerState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public FragmentManagerState[] newArray(int i) {
        return new FragmentManagerState[i];
    }
}
