package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;

/* renamed from: androidx.fragment.app.f  reason: case insensitive filesystem */
class C0086f implements Parcelable.ClassLoaderCreator<Fragment.SavedState> {
    C0086f() {
    }

    @Override // android.os.Parcelable.Creator
    public Fragment.SavedState createFromParcel(Parcel parcel) {
        return new Fragment.SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public Fragment.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new Fragment.SavedState(parcel, classLoader);
    }

    @Override // android.os.Parcelable.Creator
    public Fragment.SavedState[] newArray(int i) {
        return new Fragment.SavedState[i];
    }
}
