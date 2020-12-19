package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: androidx.fragment.app.b  reason: case insensitive filesystem */
class C0082b implements Parcelable.Creator<BackStackState> {
    C0082b() {
    }

    @Override // android.os.Parcelable.Creator
    public BackStackState createFromParcel(Parcel parcel) {
        return new BackStackState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public BackStackState[] newArray(int i) {
        return new BackStackState[i];
    }
}
