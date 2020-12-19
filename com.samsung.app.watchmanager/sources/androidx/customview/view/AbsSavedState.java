package androidx.customview.view;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsSavedState implements Parcelable {
    public static final Parcelable.Creator<AbsSavedState> CREATOR = new a();

    /* renamed from: a  reason: collision with root package name */
    public static final AbsSavedState f664a = new AbsSavedState() {
        /* class androidx.customview.view.AbsSavedState.AnonymousClass1 */
    };

    /* renamed from: b  reason: collision with root package name */
    private final Parcelable f665b;

    private AbsSavedState() {
        this.f665b = null;
    }

    protected AbsSavedState(Parcel parcel, ClassLoader classLoader) {
        Parcelable readParcelable = parcel.readParcelable(classLoader);
        this.f665b = readParcelable == null ? f664a : readParcelable;
    }

    protected AbsSavedState(Parcelable parcelable) {
        if (parcelable != null) {
            this.f665b = parcelable == f664a ? null : parcelable;
            return;
        }
        throw new IllegalArgumentException("superState must not be null");
    }

    public final Parcelable a() {
        return this.f665b;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f665b, i);
    }
}
