package androidx.appcompat.widget;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.widget.ActionMenuPresenter;

/* renamed from: androidx.appcompat.widget.h  reason: case insensitive filesystem */
class C0063h implements Parcelable.Creator<ActionMenuPresenter.SavedState> {
    C0063h() {
    }

    @Override // android.os.Parcelable.Creator
    public ActionMenuPresenter.SavedState createFromParcel(Parcel parcel) {
        return new ActionMenuPresenter.SavedState(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public ActionMenuPresenter.SavedState[] newArray(int i) {
        return new ActionMenuPresenter.SavedState[i];
    }
}
