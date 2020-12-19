package com.sec.android.diagmonagent.sa;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDMAInterface extends IInterface {
    String checkToken() throws RemoteException;

    int sendCommon(int i, String str, String str2, String str3) throws RemoteException;

    int sendLog(int i, String str, String str2, long j, String str3) throws RemoteException;

    public static abstract class Stub extends Binder implements IDMAInterface {
        private static final String DESCRIPTOR = "com.sec.android.diagmonagent.sa.IDMAInterface";
        static final int TRANSACTION_checkToken = 1;
        static final int TRANSACTION_sendCommon = 2;
        static final int TRANSACTION_sendLog = 3;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDMAInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IDMAInterface)) {
                return new Proxy(iBinder);
            }
            return (IDMAInterface) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                String checkToken = checkToken();
                parcel2.writeNoException();
                parcel2.writeString(checkToken);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                int sendCommon = sendCommon(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(sendCommon);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                int sendLog = sendLog(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(sendLog);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IDMAInterface {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public String checkToken() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public int sendCommon(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.diagmonagent.sa.IDMAInterface
            public int sendLog(int i, String str, String str2, long j, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeString(str3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
