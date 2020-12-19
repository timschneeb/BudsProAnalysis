package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.k;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public final class PlaybackStateCompat implements Parcelable {
    public static final Parcelable.Creator<PlaybackStateCompat> CREATOR = new i();

    /* renamed from: a  reason: collision with root package name */
    final int f82a;

    /* renamed from: b  reason: collision with root package name */
    final long f83b;

    /* renamed from: c  reason: collision with root package name */
    final long f84c;

    /* renamed from: d  reason: collision with root package name */
    final float f85d;
    final long e;
    final int f;
    final CharSequence g;
    final long h;
    List<CustomAction> i;
    final long j;
    final Bundle k;
    private Object l;

    public static final class CustomAction implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new j();

        /* renamed from: a  reason: collision with root package name */
        private final String f86a;

        /* renamed from: b  reason: collision with root package name */
        private final CharSequence f87b;

        /* renamed from: c  reason: collision with root package name */
        private final int f88c;

        /* renamed from: d  reason: collision with root package name */
        private final Bundle f89d;
        private Object e;

        CustomAction(Parcel parcel) {
            this.f86a = parcel.readString();
            this.f87b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.f88c = parcel.readInt();
            this.f89d = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        }

        CustomAction(String str, CharSequence charSequence, int i, Bundle bundle) {
            this.f86a = str;
            this.f87b = charSequence;
            this.f88c = i;
            this.f89d = bundle;
        }

        public static CustomAction a(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            CustomAction customAction = new CustomAction(k.a.a(obj), k.a.d(obj), k.a.c(obj), k.a.b(obj));
            customAction.e = obj;
            return customAction;
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "Action:mName='" + ((Object) this.f87b) + ", mIcon=" + this.f88c + ", mExtras=" + this.f89d;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.f86a);
            TextUtils.writeToParcel(this.f87b, parcel, i);
            parcel.writeInt(this.f88c);
            parcel.writeBundle(this.f89d);
        }
    }

    PlaybackStateCompat(int i2, long j2, long j3, float f2, long j4, int i3, CharSequence charSequence, long j5, List<CustomAction> list, long j6, Bundle bundle) {
        this.f82a = i2;
        this.f83b = j2;
        this.f84c = j3;
        this.f85d = f2;
        this.e = j4;
        this.f = i3;
        this.g = charSequence;
        this.h = j5;
        this.i = new ArrayList(list);
        this.j = j6;
        this.k = bundle;
    }

    PlaybackStateCompat(Parcel parcel) {
        this.f82a = parcel.readInt();
        this.f83b = parcel.readLong();
        this.f85d = parcel.readFloat();
        this.h = parcel.readLong();
        this.f84c = parcel.readLong();
        this.e = parcel.readLong();
        this.g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.i = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.j = parcel.readLong();
        this.k = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        this.f = parcel.readInt();
    }

    public static PlaybackStateCompat a(Object obj) {
        ArrayList arrayList;
        Bundle bundle = null;
        if (obj == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        List<Object> d2 = k.d(obj);
        if (d2 != null) {
            ArrayList arrayList2 = new ArrayList(d2.size());
            for (Object obj2 : d2) {
                arrayList2.add(CustomAction.a(obj2));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        if (Build.VERSION.SDK_INT >= 22) {
            bundle = l.a(obj);
        }
        PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(k.i(obj), k.h(obj), k.c(obj), k.g(obj), k.a(obj), 0, k.e(obj), k.f(obj), arrayList, k.b(obj), bundle);
        playbackStateCompat.l = obj;
        return playbackStateCompat;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "PlaybackState {" + "state=" + this.f82a + ", position=" + this.f83b + ", buffered position=" + this.f84c + ", speed=" + this.f85d + ", updated=" + this.h + ", actions=" + this.e + ", error code=" + this.f + ", error message=" + this.g + ", custom actions=" + this.i + ", active item id=" + this.j + "}";
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f82a);
        parcel.writeLong(this.f83b);
        parcel.writeFloat(this.f85d);
        parcel.writeLong(this.h);
        parcel.writeLong(this.f84c);
        parcel.writeLong(this.e);
        TextUtils.writeToParcel(this.g, parcel, i2);
        parcel.writeTypedList(this.i);
        parcel.writeLong(this.j);
        parcel.writeBundle(this.k);
        parcel.writeInt(this.f);
    }
}
