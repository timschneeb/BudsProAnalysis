package androidx.slidingpanelayout.widget;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

class a implements Parcelable.ClassLoaderCreator<SlidingPaneLayout.SavedState> {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    public SlidingPaneLayout.SavedState createFromParcel(Parcel parcel) {
        return new SlidingPaneLayout.SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public SlidingPaneLayout.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return new SlidingPaneLayout.SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public SlidingPaneLayout.SavedState[] newArray(int i) {
        return new SlidingPaneLayout.SavedState[i];
    }
}
