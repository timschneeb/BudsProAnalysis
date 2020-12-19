package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.a;

public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new b();

    /* renamed from: a  reason: collision with root package name */
    final boolean f92a = false;

    /* renamed from: b  reason: collision with root package name */
    final Handler f93b = null;

    /* renamed from: c  reason: collision with root package name */
    a f94c;

    class a extends a.AbstractBinderC0006a {
        a() {
        }

        @Override // android.support.v4.os.a
        public void a(int i, Bundle bundle) {
            ResultReceiver resultReceiver = ResultReceiver.this;
            Handler handler = resultReceiver.f93b;
            if (handler != null) {
                handler.post(new b(i, bundle));
            } else {
                resultReceiver.a(i, bundle);
            }
        }
    }

    class b implements Runnable {

        /* renamed from: a  reason: collision with root package name */
        final int f96a;

        /* renamed from: b  reason: collision with root package name */
        final Bundle f97b;

        b(int i, Bundle bundle) {
            this.f96a = i;
            this.f97b = bundle;
        }

        public void run() {
            ResultReceiver.this.a(this.f96a, this.f97b);
        }
    }

    ResultReceiver(Parcel parcel) {
        this.f94c = a.AbstractBinderC0006a.a(parcel.readStrongBinder());
    }

    /* access modifiers changed from: protected */
    public void a(int i, Bundle bundle) {
    }

    public void b(int i, Bundle bundle) {
        if (this.f92a) {
            Handler handler = this.f93b;
            if (handler != null) {
                handler.post(new b(i, bundle));
            } else {
                a(i, bundle);
            }
        } else {
            a aVar = this.f94c;
            if (aVar != null) {
                try {
                    aVar.a(i, bundle);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.f94c == null) {
                this.f94c = new a();
            }
            parcel.writeStrongBinder(this.f94c.asBinder());
        }
    }
}
