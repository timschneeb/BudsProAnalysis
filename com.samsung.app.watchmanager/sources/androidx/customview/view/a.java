package androidx.customview.view;

import android.os.Parcel;
import android.os.Parcelable;

class a implements Parcelable.ClassLoaderCreator<AbsSavedState> {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    public AbsSavedState createFromParcel(Parcel parcel) {
        return createFromParcel(parcel, (ClassLoader) null);
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        if (parcel.readParcelable(classLoader) == null) {
            return AbsSavedState.f664a;
        }
        throw new IllegalStateException("superState must be null");
    }

    @Override // android.os.Parcelable.Creator
    public AbsSavedState[] newArray(int i) {
        return new AbsSavedState[i];
    }
}
