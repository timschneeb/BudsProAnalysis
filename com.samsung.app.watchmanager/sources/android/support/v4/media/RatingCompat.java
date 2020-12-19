package android.support.v4.media;

import android.os.Parcel;
import android.os.Parcelable;

public final class RatingCompat implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new m();

    /* renamed from: a  reason: collision with root package name */
    private final int f42a;

    /* renamed from: b  reason: collision with root package name */
    private final float f43b;

    RatingCompat(int i, float f) {
        this.f42a = i;
        this.f43b = f;
    }

    public int describeContents() {
        return this.f42a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rating:style=");
        sb.append(this.f42a);
        sb.append(" rating=");
        float f = this.f43b;
        sb.append(f < 0.0f ? "unrated" : String.valueOf(f));
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f42a);
        parcel.writeFloat(this.f43b);
    }
}
