package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {

    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new d();

        /* renamed from: a  reason: collision with root package name */
        private final MediaDescriptionCompat f71a;

        /* renamed from: b  reason: collision with root package name */
        private final long f72b;

        /* renamed from: c  reason: collision with root package name */
        private Object f73c;

        QueueItem(Parcel parcel) {
            this.f71a = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.f72b = parcel.readLong();
        }

        private QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if (j != -1) {
                this.f71a = mediaDescriptionCompat;
                this.f72b = j;
                this.f73c = obj;
            } else {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
        }

        public static QueueItem a(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new QueueItem(obj, MediaDescriptionCompat.a(g.a.a(obj)), g.a.b(obj));
        }

        public static List<QueueItem> a(List<?> list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(a(it.next()));
            }
            return arrayList;
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.f71a + ", Id=" + this.f72b + " }";
        }

        public void writeToParcel(Parcel parcel, int i) {
            this.f71a.writeToParcel(parcel, i);
            parcel.writeLong(this.f72b);
        }
    }

    public static final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new e();

        /* renamed from: a  reason: collision with root package name */
        ResultReceiver f74a;

        ResultReceiverWrapper(Parcel parcel) {
            this.f74a = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            this.f74a.writeToParcel(parcel, i);
        }
    }

    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new f();

        /* renamed from: a  reason: collision with root package name */
        private final Object f75a;

        /* renamed from: b  reason: collision with root package name */
        private b f76b;

        /* renamed from: c  reason: collision with root package name */
        private Bundle f77c;

        Token(Object obj) {
            this(obj, null, null);
        }

        Token(Object obj, b bVar) {
            this(obj, bVar, null);
        }

        Token(Object obj, b bVar, Bundle bundle) {
            this.f75a = obj;
            this.f76b = bVar;
            this.f77c = bundle;
        }

        public static Token a(Object obj) {
            return a(obj, null);
        }

        public static Token a(Object obj, b bVar) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            g.a(obj);
            return new Token(obj, bVar);
        }

        public b a() {
            return this.f76b;
        }

        public void a(Bundle bundle) {
            this.f77c = bundle;
        }

        public void a(b bVar) {
            this.f76b = bVar;
        }

        public Object b() {
            return this.f75a;
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            Object obj2 = this.f75a;
            if (obj2 == null) {
                return token.f75a == null;
            }
            Object obj3 = token.f75a;
            if (obj3 == null) {
                return false;
            }
            return obj2.equals(obj3);
        }

        public int hashCode() {
            Object obj = this.f75a;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public void writeToParcel(Parcel parcel, int i) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable) this.f75a, i);
            } else {
                parcel.writeStrongBinder((IBinder) this.f75a);
            }
        }
    }

    public static void a(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MediaSessionCompat.class.getClassLoader());
        }
    }
}
