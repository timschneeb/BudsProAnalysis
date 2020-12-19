package c.c.a.a.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface a extends IInterface {

    /* renamed from: c.c.a.a.a.a$a  reason: collision with other inner class name */
    public static abstract class AbstractBinderC0035a extends Binder implements a {

        /* renamed from: c.c.a.a.a.a$a$a  reason: collision with other inner class name */
        private static class C0036a implements a {

            /* renamed from: a  reason: collision with root package name */
            private IBinder f1891a;

            C0036a(IBinder iBinder) {
                this.f1891a = iBinder;
            }

            @Override // c.c.a.a.a.a
            public int a(int i, String str, String str2, long j, String str3) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.f1891a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // c.c.a.a.a.a
            public int a(int i, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.f1891a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f1891a;
            }

            @Override // c.c.a.a.a.a
            public String b() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.sec.android.diagmonagent.sa.IDMAInterface");
                    this.f1891a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.sec.android.diagmonagent.sa.IDMAInterface");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0036a(iBinder) : (a) queryLocalInterface;
        }
    }

    int a(int i, String str, String str2, long j, String str3);

    int a(int i, String str, String str2, String str3);

    String b();
}
