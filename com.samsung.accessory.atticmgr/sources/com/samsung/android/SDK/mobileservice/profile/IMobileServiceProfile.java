package com.samsung.android.sdk.mobileservice.profile;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.profile.IPrivacyUpdateResultCallback;
import com.samsung.android.sdk.mobileservice.profile.IProfileUpdateResultCallback;
import com.samsung.android.sdk.mobileservice.profile.ISyncResultCallback;

public interface IMobileServiceProfile extends IInterface {
    int exchangeProfileVersion(int i) throws RemoteException;

    Bundle getPrivacy() throws RemoteException;

    Profile getProfile() throws RemoteException;

    String getProfileBirthdayType() throws RemoteException;

    String getProfileImageUrl() throws RemoteException;

    boolean requestPrivacyUpdate(Bundle bundle, IPrivacyUpdateResultCallback iPrivacyUpdateResultCallback) throws RemoteException;

    boolean requestProfileBirthdayTypeUpdate(String str, IProfileUpdateResultCallback iProfileUpdateResultCallback) throws RemoteException;

    boolean requestProfileUpdate(Profile profile, IProfileUpdateResultCallback iProfileUpdateResultCallback) throws RemoteException;

    void requestSync(ISyncResultCallback iSyncResultCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IMobileServiceProfile {
        private static final String DESCRIPTOR = "com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile";
        static final int TRANSACTION_exchangeProfileVersion = 4;
        static final int TRANSACTION_getPrivacy = 5;
        static final int TRANSACTION_getProfile = 1;
        static final int TRANSACTION_getProfileBirthdayType = 8;
        static final int TRANSACTION_getProfileImageUrl = 7;
        static final int TRANSACTION_requestPrivacyUpdate = 6;
        static final int TRANSACTION_requestProfileBirthdayTypeUpdate = 9;
        static final int TRANSACTION_requestProfileUpdate = 3;
        static final int TRANSACTION_requestSync = 2;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMobileServiceProfile asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMobileServiceProfile)) {
                return new Proxy(iBinder);
            }
            return (IMobileServiceProfile) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Profile profile = null;
                Bundle bundle = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        Profile profile2 = getProfile();
                        parcel2.writeNoException();
                        if (profile2 != null) {
                            parcel2.writeInt(1);
                            profile2.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestSync(ISyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            profile = Profile.CREATOR.createFromParcel(parcel);
                        }
                        boolean requestProfileUpdate = requestProfileUpdate(profile, IProfileUpdateResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestProfileUpdate ? 1 : 0);
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        int exchangeProfileVersion = exchangeProfileVersion(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(exchangeProfileVersion);
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        Bundle privacy = getPrivacy();
                        parcel2.writeNoException();
                        if (privacy != null) {
                            parcel2.writeInt(1);
                            privacy.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        boolean requestPrivacyUpdate = requestPrivacyUpdate(bundle, IPrivacyUpdateResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestPrivacyUpdate ? 1 : 0);
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        String profileImageUrl = getProfileImageUrl();
                        parcel2.writeNoException();
                        parcel2.writeString(profileImageUrl);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        String profileBirthdayType = getProfileBirthdayType();
                        parcel2.writeNoException();
                        parcel2.writeString(profileBirthdayType);
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean requestProfileBirthdayTypeUpdate = requestProfileBirthdayTypeUpdate(parcel.readString(), IProfileUpdateResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestProfileBirthdayTypeUpdate ? 1 : 0);
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
        public static class Proxy implements IMobileServiceProfile {
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

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public Profile getProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? Profile.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public void requestSync(ISyncResultCallback iSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iSyncResultCallback != null ? iSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public boolean requestProfileUpdate(Profile profile, IProfileUpdateResultCallback iProfileUpdateResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = true;
                    if (profile != null) {
                        obtain.writeInt(1);
                        profile.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iProfileUpdateResultCallback != null ? iProfileUpdateResultCallback.asBinder() : null);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public int exchangeProfileVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public Bundle getPrivacy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public boolean requestPrivacyUpdate(Bundle bundle, IPrivacyUpdateResultCallback iPrivacyUpdateResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = true;
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iPrivacyUpdateResultCallback != null ? iPrivacyUpdateResultCallback.asBinder() : null);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public String getProfileImageUrl() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public String getProfileBirthdayType() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile
            public boolean requestProfileBirthdayTypeUpdate(String str, IProfileUpdateResultCallback iProfileUpdateResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iProfileUpdateResultCallback != null ? iProfileUpdateResultCallback.asBinder() : null);
                    boolean z = false;
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
