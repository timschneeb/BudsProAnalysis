package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;

/* access modifiers changed from: package-private */
public final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new t();

    /* renamed from: a  reason: collision with root package name */
    FragmentState[] f724a;

    /* renamed from: b  reason: collision with root package name */
    int[] f725b;

    /* renamed from: c  reason: collision with root package name */
    BackStackState[] f726c;

    /* renamed from: d  reason: collision with root package name */
    int f727d = -1;
    int e;

    public FragmentManagerState() {
    }

    public FragmentManagerState(Parcel parcel) {
        this.f724a = (FragmentState[]) parcel.createTypedArray(FragmentState.CREATOR);
        this.f725b = parcel.createIntArray();
        this.f726c = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
        this.f727d = parcel.readInt();
        this.e = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.f724a, i);
        parcel.writeIntArray(this.f725b);
        parcel.writeTypedArray(this.f726c, i);
        parcel.writeInt(this.f727d);
        parcel.writeInt(this.e);
    }
}
