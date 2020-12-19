package com.samsung.android.sdk.mobileservice.auth;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.auth.IAccessTokenResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IAuthCodeResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IAuthResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IDisclaimerAgreementForThirdPartyResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IValidationResultCallback;

public interface IMobileServiceAuth extends IInterface {
    boolean getAccountValidation() throws RemoteException;

    Bundle getAuthInfoCached() throws RemoteException;

    Bundle getDeviceAuthInfoCached() throws RemoteException;

    boolean getDisclaimerAgreementForService(int i) throws RemoteException;

    boolean getDisclaimerAgreementForSocial() throws RemoteException;

    Intent getIntentForAccountAccessTokenRequest(String str, String str2) throws RemoteException;

    Intent getIntentForAccountDisclaimerAgreement(String str, boolean z) throws RemoteException;

    Intent getIntentForAccountPasswordConfirmation(String str) throws RemoteException;

    Intent getIntentForAccountPasswordConfirmationPopup(String str) throws RemoteException;

    Intent getIntentForAccountProfileModification(String str) throws RemoteException;

    Intent getIntentForAccountSetupWizard(boolean z) throws RemoteException;

    Intent getIntentForAccountSignIn() throws RemoteException;

    Intent getIntentForAccountSignInPopup() throws RemoteException;

    Intent getIntentForAccountValidationRequest(String str, boolean z, boolean z2) throws RemoteException;

    Intent getIntentForSocialDisclaimerAgreement(boolean z, boolean z2) throws RemoteException;

    Intent getIntentForSocialDisclaimerDisplay() throws RemoteException;

    Intent getIntentForSocialRegistrationInformation() throws RemoteException;

    Intent getIntentForSocialSignUp() throws RemoteException;

    Intent getIntentForSocialTnC() throws RemoteException;

    boolean getNeedToShowSocialTncPopup() throws RemoteException;

    boolean isServiceRegistered(int i) throws RemoteException;

    void requestAccessTokenForAccount(String str, String str2, Bundle bundle, IAccessTokenResultCallback iAccessTokenResultCallback) throws RemoteException;

    void requestAuthCode(String str, String str2, Bundle bundle, IAuthCodeResultCallback iAuthCodeResultCallback) throws RemoteException;

    void requestAuthInfo(String str, String str2, Bundle bundle, IAuthResultCallback iAuthResultCallback) throws RemoteException;

    void requestDisclaimerAgreementForThirdParty(String str, String str2, Bundle bundle, IDisclaimerAgreementForThirdPartyResultCallback iDisclaimerAgreementForThirdPartyResultCallback) throws RemoteException;

    void requestRenewAccessTokenForAccount(String str, String str2, Bundle bundle, String str3, IAccessTokenResultCallback iAccessTokenResultCallback) throws RemoteException;

    void requestValidation(String str, String str2, Bundle bundle, IValidationResultCallback iValidationResultCallback) throws RemoteException;

    boolean setDisclaimerAgreementForSocial(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IMobileServiceAuth {
        private static final String DESCRIPTOR = "com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth";
        static final int TRANSACTION_getAccountValidation = 23;
        static final int TRANSACTION_getAuthInfoCached = 18;
        static final int TRANSACTION_getDeviceAuthInfoCached = 19;
        static final int TRANSACTION_getDisclaimerAgreementForService = 25;
        static final int TRANSACTION_getDisclaimerAgreementForSocial = 24;
        static final int TRANSACTION_getIntentForAccountAccessTokenRequest = 1;
        static final int TRANSACTION_getIntentForAccountDisclaimerAgreement = 2;
        static final int TRANSACTION_getIntentForAccountPasswordConfirmation = 3;
        static final int TRANSACTION_getIntentForAccountPasswordConfirmationPopup = 4;
        static final int TRANSACTION_getIntentForAccountProfileModification = 5;
        static final int TRANSACTION_getIntentForAccountSetupWizard = 6;
        static final int TRANSACTION_getIntentForAccountSignIn = 7;
        static final int TRANSACTION_getIntentForAccountSignInPopup = 8;
        static final int TRANSACTION_getIntentForAccountValidationRequest = 9;
        static final int TRANSACTION_getIntentForSocialDisclaimerAgreement = 10;
        static final int TRANSACTION_getIntentForSocialDisclaimerDisplay = 11;
        static final int TRANSACTION_getIntentForSocialRegistrationInformation = 12;
        static final int TRANSACTION_getIntentForSocialSignUp = 13;
        static final int TRANSACTION_getIntentForSocialTnC = 27;
        static final int TRANSACTION_getNeedToShowSocialTncPopup = 26;
        static final int TRANSACTION_isServiceRegistered = 14;
        static final int TRANSACTION_requestAccessTokenForAccount = 15;
        static final int TRANSACTION_requestAuthCode = 16;
        static final int TRANSACTION_requestAuthInfo = 17;
        static final int TRANSACTION_requestDisclaimerAgreementForThirdParty = 22;
        static final int TRANSACTION_requestRenewAccessTokenForAccount = 20;
        static final int TRANSACTION_requestValidation = 21;
        static final int TRANSACTION_setDisclaimerAgreementForSocial = 28;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMobileServiceAuth asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMobileServiceAuth)) {
                return new Proxy(iBinder);
            }
            return (IMobileServiceAuth) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Bundle bundle = null;
                boolean z = false;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountAccessTokenRequest = getIntentForAccountAccessTokenRequest(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        if (intentForAccountAccessTokenRequest != null) {
                            parcel2.writeInt(1);
                            intentForAccountAccessTokenRequest.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountDisclaimerAgreement = getIntentForAccountDisclaimerAgreement(parcel.readString(), parcel.readInt() != 0);
                        parcel2.writeNoException();
                        if (intentForAccountDisclaimerAgreement != null) {
                            parcel2.writeInt(1);
                            intentForAccountDisclaimerAgreement.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountPasswordConfirmation = getIntentForAccountPasswordConfirmation(parcel.readString());
                        parcel2.writeNoException();
                        if (intentForAccountPasswordConfirmation != null) {
                            parcel2.writeInt(1);
                            intentForAccountPasswordConfirmation.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountPasswordConfirmationPopup = getIntentForAccountPasswordConfirmationPopup(parcel.readString());
                        parcel2.writeNoException();
                        if (intentForAccountPasswordConfirmationPopup != null) {
                            parcel2.writeInt(1);
                            intentForAccountPasswordConfirmationPopup.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountProfileModification = getIntentForAccountProfileModification(parcel.readString());
                        parcel2.writeNoException();
                        if (intentForAccountProfileModification != null) {
                            parcel2.writeInt(1);
                            intentForAccountProfileModification.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountSetupWizard = getIntentForAccountSetupWizard(parcel.readInt() != 0);
                        parcel2.writeNoException();
                        if (intentForAccountSetupWizard != null) {
                            parcel2.writeInt(1);
                            intentForAccountSetupWizard.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountSignIn = getIntentForAccountSignIn();
                        parcel2.writeNoException();
                        if (intentForAccountSignIn != null) {
                            parcel2.writeInt(1);
                            intentForAccountSignIn.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountSignInPopup = getIntentForAccountSignInPopup();
                        parcel2.writeNoException();
                        if (intentForAccountSignInPopup != null) {
                            parcel2.writeInt(1);
                            intentForAccountSignInPopup.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForAccountValidationRequest = getIntentForAccountValidationRequest(parcel.readString(), parcel.readInt() != 0, parcel.readInt() != 0);
                        parcel2.writeNoException();
                        if (intentForAccountValidationRequest != null) {
                            parcel2.writeInt(1);
                            intentForAccountValidationRequest.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForSocialDisclaimerAgreement = getIntentForSocialDisclaimerAgreement(parcel.readInt() != 0, parcel.readInt() != 0);
                        parcel2.writeNoException();
                        if (intentForSocialDisclaimerAgreement != null) {
                            parcel2.writeInt(1);
                            intentForSocialDisclaimerAgreement.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForSocialDisclaimerDisplay = getIntentForSocialDisclaimerDisplay();
                        parcel2.writeNoException();
                        if (intentForSocialDisclaimerDisplay != null) {
                            parcel2.writeInt(1);
                            intentForSocialDisclaimerDisplay.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForSocialRegistrationInformation = getIntentForSocialRegistrationInformation();
                        parcel2.writeNoException();
                        if (intentForSocialRegistrationInformation != null) {
                            parcel2.writeInt(1);
                            intentForSocialRegistrationInformation.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForSocialSignUp = getIntentForSocialSignUp();
                        parcel2.writeNoException();
                        if (intentForSocialSignUp != null) {
                            parcel2.writeInt(1);
                            intentForSocialSignUp.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean isServiceRegistered = isServiceRegistered(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(isServiceRegistered ? 1 : 0);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString = parcel.readString();
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestAccessTokenForAccount(readString, readString2, bundle, IAccessTokenResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString3 = parcel.readString();
                        String readString4 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestAuthCode(readString3, readString4, bundle, IAuthCodeResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString5 = parcel.readString();
                        String readString6 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestAuthInfo(readString5, readString6, bundle, IAuthResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        Bundle authInfoCached = getAuthInfoCached();
                        parcel2.writeNoException();
                        if (authInfoCached != null) {
                            parcel2.writeInt(1);
                            authInfoCached.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        Bundle deviceAuthInfoCached = getDeviceAuthInfoCached();
                        parcel2.writeNoException();
                        if (deviceAuthInfoCached != null) {
                            parcel2.writeInt(1);
                            deviceAuthInfoCached.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString7 = parcel.readString();
                        String readString8 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestRenewAccessTokenForAccount(readString7, readString8, bundle, parcel.readString(), IAccessTokenResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString9 = parcel.readString();
                        String readString10 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestValidation(readString9, readString10, bundle, IValidationResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString11 = parcel.readString();
                        String readString12 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestDisclaimerAgreementForThirdParty(readString11, readString12, bundle, IDisclaimerAgreementForThirdPartyResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean accountValidation = getAccountValidation();
                        parcel2.writeNoException();
                        parcel2.writeInt(accountValidation ? 1 : 0);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean disclaimerAgreementForSocial = getDisclaimerAgreementForSocial();
                        parcel2.writeNoException();
                        parcel2.writeInt(disclaimerAgreementForSocial ? 1 : 0);
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean disclaimerAgreementForService = getDisclaimerAgreementForService(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(disclaimerAgreementForService ? 1 : 0);
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean needToShowSocialTncPopup = getNeedToShowSocialTncPopup();
                        parcel2.writeNoException();
                        parcel2.writeInt(needToShowSocialTncPopup ? 1 : 0);
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        Intent intentForSocialTnC = getIntentForSocialTnC();
                        parcel2.writeNoException();
                        if (intentForSocialTnC != null) {
                            parcel2.writeInt(1);
                            intentForSocialTnC.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            z = true;
                        }
                        boolean disclaimerAgreementForSocial2 = setDisclaimerAgreementForSocial(z);
                        parcel2.writeNoException();
                        parcel2.writeInt(disclaimerAgreementForSocial2 ? 1 : 0);
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
        public static class Proxy implements IMobileServiceAuth {
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

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountAccessTokenRequest(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountDisclaimerAgreement(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountPasswordConfirmation(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountPasswordConfirmationPopup(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountProfileModification(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountSetupWizard(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountSignIn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountSignInPopup() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForAccountValidationRequest(String str, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    int i = 1;
                    obtain.writeInt(z ? 1 : 0);
                    if (!z2) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForSocialDisclaimerAgreement(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int i = 1;
                    obtain.writeInt(z ? 1 : 0);
                    if (!z2) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForSocialDisclaimerDisplay() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForSocialRegistrationInformation() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForSocialSignUp() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public boolean isServiceRegistered(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    this.mRemote.transact(14, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public void requestAccessTokenForAccount(String str, String str2, Bundle bundle, IAccessTokenResultCallback iAccessTokenResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iAccessTokenResultCallback != null ? iAccessTokenResultCallback.asBinder() : null);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public void requestAuthCode(String str, String str2, Bundle bundle, IAuthCodeResultCallback iAuthCodeResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iAuthCodeResultCallback != null ? iAuthCodeResultCallback.asBinder() : null);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public void requestAuthInfo(String str, String str2, Bundle bundle, IAuthResultCallback iAuthResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iAuthResultCallback != null ? iAuthResultCallback.asBinder() : null);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Bundle getAuthInfoCached() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Bundle getDeviceAuthInfoCached() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public void requestRenewAccessTokenForAccount(String str, String str2, Bundle bundle, String str3, IAccessTokenResultCallback iAccessTokenResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iAccessTokenResultCallback != null ? iAccessTokenResultCallback.asBinder() : null);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public void requestValidation(String str, String str2, Bundle bundle, IValidationResultCallback iValidationResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iValidationResultCallback != null ? iValidationResultCallback.asBinder() : null);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public void requestDisclaimerAgreementForThirdParty(String str, String str2, Bundle bundle, IDisclaimerAgreementForThirdPartyResultCallback iDisclaimerAgreementForThirdPartyResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iDisclaimerAgreementForThirdPartyResultCallback != null ? iDisclaimerAgreementForThirdPartyResultCallback.asBinder() : null);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public boolean getAccountValidation() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(23, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public boolean getDisclaimerAgreementForSocial() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(24, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public boolean getDisclaimerAgreementForService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = false;
                    this.mRemote.transact(25, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public boolean getNeedToShowSocialTncPopup() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(26, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public Intent getIntentForSocialTnC() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth
            public boolean setDisclaimerAgreementForSocial(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z2 = true;
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z2 = false;
                    }
                    return z2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
