package c.c.b.a.a.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface a extends IInterface {

    /* renamed from: c.c.b.a.a.a.a$a  reason: collision with other inner class name */
    public static abstract class AbstractBinderC0037a extends Binder implements a {

        /* renamed from: c.c.b.a.a.a.a$a$a  reason: collision with other inner class name */
        private static class C0038a implements a {

            /* renamed from: a  reason: collision with root package name */
            private IBinder f1892a;

            C0038a(IBinder iBinder) {
                this.f1892a = iBinder;
            }

            @Override // c.c.b.a.a.a.a
            public int a(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.spp.push.dlc.api.IDlcService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    this.f1892a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f1892a;
            }
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.sec.spp.push.dlc.api.IDlcService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0038a(iBinder) : (a) queryLocalInterface;
        }
    }

    int a(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7);
}
