package com.samsung.android.sdk.mobileservice.place;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback;
import java.util.List;

public interface IMobileServicePlace extends IInterface {
    int getCurrentCount() throws RemoteException;

    int getMaxCount() throws RemoteException;

    List<Bundle> getPlaceList() throws RemoteException;

    void requestPlaceCreate(Bundle bundle, IPlaceResultCallback iPlaceResultCallback) throws RemoteException;

    void requestPlaceDelete(String str, IPlaceResultCallback iPlaceResultCallback) throws RemoteException;

    void requestPlaceUpdate(Bundle bundle, IPlaceResultCallback iPlaceResultCallback) throws RemoteException;

    void requestSync(IPlaceResultCallback iPlaceResultCallback) throws RemoteException;

    List<Bundle> searchPlaces(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMobileServicePlace {
        private static final String DESCRIPTOR = "com.samsung.android.sdk.mobileservice.place.IMobileServicePlace";
        static final int TRANSACTION_getCurrentCount = 7;
        static final int TRANSACTION_getMaxCount = 8;
        static final int TRANSACTION_getPlaceList = 1;
        static final int TRANSACTION_requestPlaceCreate = 3;
        static final int TRANSACTION_requestPlaceDelete = 4;
        static final int TRANSACTION_requestPlaceUpdate = 5;
        static final int TRANSACTION_requestSync = 2;
        static final int TRANSACTION_searchPlaces = 6;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMobileServicePlace asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMobileServicePlace)) {
                return new Proxy(iBinder);
            }
            return (IMobileServicePlace) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Bundle bundle = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        List<Bundle> placeList = getPlaceList();
                        parcel2.writeNoException();
                        parcel2.writeTypedList(placeList);
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestSync(IPlaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestPlaceCreate(bundle, IPlaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestPlaceDelete(parcel.readString(), IPlaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestPlaceUpdate(bundle, IPlaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        List<Bundle> searchPlaces = searchPlaces(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeTypedList(searchPlaces);
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        int currentCount = getCurrentCount();
                        parcel2.writeNoException();
                        parcel2.writeInt(currentCount);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        int maxCount = getMaxCount();
                        parcel2.writeNoException();
                        parcel2.writeInt(maxCount);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }

        /* access modifiers changed from: private */
        public static class Proxy implements IMobileServicePlace {
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

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public List<Bundle> getPlaceList() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public void requestSync(IPlaceResultCallback iPlaceResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iPlaceResultCallback != null ? iPlaceResultCallback.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public void requestPlaceCreate(Bundle bundle, IPlaceResultCallback iPlaceResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iPlaceResultCallback != null ? iPlaceResultCallback.asBinder() : null);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public void requestPlaceDelete(String str, IPlaceResultCallback iPlaceResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iPlaceResultCallback != null ? iPlaceResultCallback.asBinder() : null);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public void requestPlaceUpdate(Bundle bundle, IPlaceResultCallback iPlaceResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iPlaceResultCallback != null ? iPlaceResultCallback.asBinder() : null);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public List<Bundle> searchPlaces(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public int getCurrentCount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.place.IMobileServicePlace
            public int getMaxCount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
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
