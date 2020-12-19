package androidx.viewpager.widget;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.viewpager.widget.ViewPager;

class h implements Parcelable.ClassLoaderCreator<ViewPager.SavedState> {
    h() {
    }

    @Override // android.os.Parcelable.Creator
    public ViewPager.SavedState createFromParcel(Parcel parcel) {
        return new ViewPager.SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public ViewPager.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new ViewPager.SavedState(parcel, classLoader);
    }

    @Override // android.os.Parcelable.Creator
    public ViewPager.SavedState[] newArray(int i) {
        return new ViewPager.SavedState[i];
    }
}
