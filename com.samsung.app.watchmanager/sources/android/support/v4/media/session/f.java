package android.support.v4.media.session;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;

class f implements Parcelable.Creator<MediaSessionCompat.Token> {
    f() {
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat.Token createFromParcel(Parcel parcel) {
        return new MediaSessionCompat.Token(Build.VERSION.SDK_INT >= 21 ? parcel.readParcelable(null) : parcel.readStrongBinder());
    }

    @Override // android.os.Parcelable.Creator
    public MediaSessionCompat.Token[] newArray(int i) {
        return new MediaSessionCompat.Token[i];
    }
}
