package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableVolumeInfo implements Parcelable {
    public static final Parcelable.Creator<ParcelableVolumeInfo> CREATOR = new h();

    /* renamed from: a  reason: collision with root package name */
    public int f78a;

    /* renamed from: b  reason: collision with root package name */
    public int f79b;

    /* renamed from: c  reason: collision with root package name */
    public int f80c;

    /* renamed from: d  reason: collision with root package name */
    public int f81d;
    public int e;

    public ParcelableVolumeInfo(Parcel parcel) {
        this.f78a = parcel.readInt();
        this.f80c = parcel.readInt();
        this.f81d = parcel.readInt();
        this.e = parcel.readInt();
        this.f79b = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f78a);
        parcel.writeInt(this.f80c);
        parcel.writeInt(this.f81d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f79b);
    }
}
