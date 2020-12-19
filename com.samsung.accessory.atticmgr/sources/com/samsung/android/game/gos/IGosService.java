package com.samsung.android.game.gos;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGosService extends IInterface {

    public static class Default implements IGosService {
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.game.gos.IGosService
        public String requestWithJson(String str, String str2) throws RemoteException {
            return null;
        }
    }

    String requestWithJson(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGosService {
        private static final String DESCRIPTOR = "com.samsung.android.game.gos.IGosService";
        static final int TRANSACTION_requestWithJson = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGosService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IGosService)) {
                return new Proxy(iBinder);
            }
            return (IGosService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                String requestWithJson = requestWithJson(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(requestWithJson);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public static class Proxy implements IGosService {
            public static IGosService sDefaultImpl;
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

            @Override // com.samsung.android.game.gos.IGosService
            public String requestWithJson(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestWithJson(str, str2);
                    }
                    obtain2.readException();
                    String readString = obtain2.readString();
                    obtain2.recycle();
                    obtain.recycle();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IGosService iGosService) {
            if (Proxy.sDefaultImpl != null || iGosService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iGosService;
            return true;
        }

        public static IGosService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
