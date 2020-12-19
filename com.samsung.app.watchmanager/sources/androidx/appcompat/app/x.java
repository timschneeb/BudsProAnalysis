package androidx.appcompat.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatDelegateImpl;

class x implements Parcelable.ClassLoaderCreator<AppCompatDelegateImpl.PanelFeatureState.SavedState> {
    x() {
    }

    @Override // android.os.Parcelable.Creator
    public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel parcel) {
        return AppCompatDelegateImpl.PanelFeatureState.SavedState.a(parcel, null);
    }

    @Override // android.os.Parcelable.ClassLoaderCreator
    public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return AppCompatDelegateImpl.PanelFeatureState.SavedState.a(parcel, classLoader);
    }

    @Override // android.os.Parcelable.Creator
    public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int i) {
        return new AppCompatDelegateImpl.PanelFeatureState.SavedState[i];
    }
}
