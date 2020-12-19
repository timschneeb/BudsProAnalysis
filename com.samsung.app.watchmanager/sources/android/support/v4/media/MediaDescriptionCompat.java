package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.i;
import android.support.v4.media.j;
import android.text.TextUtils;

public final class MediaDescriptionCompat implements Parcelable {
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new h();

    /* renamed from: a  reason: collision with root package name */
    private final String f30a;

    /* renamed from: b  reason: collision with root package name */
    private final CharSequence f31b;

    /* renamed from: c  reason: collision with root package name */
    private final CharSequence f32c;

    /* renamed from: d  reason: collision with root package name */
    private final CharSequence f33d;
    private final Bitmap e;
    private final Uri f;
    private final Bundle g;
    private final Uri h;
    private Object i;

    public static final class a {

        /* renamed from: a  reason: collision with root package name */
        private String f34a;

        /* renamed from: b  reason: collision with root package name */
        private CharSequence f35b;

        /* renamed from: c  reason: collision with root package name */
        private CharSequence f36c;

        /* renamed from: d  reason: collision with root package name */
        private CharSequence f37d;
        private Bitmap e;
        private Uri f;
        private Bundle g;
        private Uri h;

        public a a(Bitmap bitmap) {
            this.e = bitmap;
            return this;
        }

        public a a(Uri uri) {
            this.f = uri;
            return this;
        }

        public a a(Bundle bundle) {
            this.g = bundle;
            return this;
        }

        public a a(CharSequence charSequence) {
            this.f37d = charSequence;
            return this;
        }

        public a a(String str) {
            this.f34a = str;
            return this;
        }

        public MediaDescriptionCompat a() {
            return new MediaDescriptionCompat(this.f34a, this.f35b, this.f36c, this.f37d, this.e, this.f, this.g, this.h);
        }

        public a b(Uri uri) {
            this.h = uri;
            return this;
        }

        public a b(CharSequence charSequence) {
            this.f36c = charSequence;
            return this;
        }

        public a c(CharSequence charSequence) {
            this.f35b = charSequence;
            return this;
        }
    }

    MediaDescriptionCompat(Parcel parcel) {
        this.f30a = parcel.readString();
        this.f31b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.f32c = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.f33d = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        ClassLoader classLoader = MediaDescriptionCompat.class.getClassLoader();
        this.e = (Bitmap) parcel.readParcelable(classLoader);
        this.f = (Uri) parcel.readParcelable(classLoader);
        this.g = parcel.readBundle(classLoader);
        this.h = (Uri) parcel.readParcelable(classLoader);
    }

    MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.f30a = str;
        this.f31b = charSequence;
        this.f32c = charSequence2;
        this.f33d = charSequence3;
        this.e = bitmap;
        this.f = uri;
        this.g = bundle;
        this.h = uri2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.v4.media.MediaDescriptionCompat a(java.lang.Object r8) {
        /*
        // Method dump skipped, instructions count: 129
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaDescriptionCompat.a(java.lang.Object):android.support.v4.media.MediaDescriptionCompat");
    }

    public Object a() {
        if (this.i != null || Build.VERSION.SDK_INT < 21) {
            return this.i;
        }
        Object a2 = i.a.a();
        i.a.a(a2, this.f30a);
        i.a.c(a2, this.f31b);
        i.a.b(a2, this.f32c);
        i.a.a(a2, this.f33d);
        i.a.a(a2, this.e);
        i.a.a(a2, this.f);
        Bundle bundle = this.g;
        if (Build.VERSION.SDK_INT < 23 && this.h != null) {
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putBoolean("android.support.v4.media.description.NULL_BUNDLE_FLAG", true);
            }
            bundle.putParcelable("android.support.v4.media.description.MEDIA_URI", this.h);
        }
        i.a.a(a2, bundle);
        if (Build.VERSION.SDK_INT >= 23) {
            j.a.a(a2, this.h);
        }
        this.i = i.a.a(a2);
        return this.i;
    }

    public String b() {
        return this.f30a;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return ((Object) this.f31b) + ", " + ((Object) this.f32c) + ", " + ((Object) this.f33d);
    }

    public void writeToParcel(Parcel parcel, int i2) {
        if (Build.VERSION.SDK_INT < 21) {
            parcel.writeString(this.f30a);
            TextUtils.writeToParcel(this.f31b, parcel, i2);
            TextUtils.writeToParcel(this.f32c, parcel, i2);
            TextUtils.writeToParcel(this.f33d, parcel, i2);
            parcel.writeParcelable(this.e, i2);
            parcel.writeParcelable(this.f, i2);
            parcel.writeBundle(this.g);
            parcel.writeParcelable(this.h, i2);
            return;
        }
        i.a(a(), parcel, i2);
    }
}
