package com.samsung.android.sdk.mobileservice.social;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.common.ErrorCodeConvertor;
import com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.BooleanResult;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.social.buddy.IServiceActivationResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.IServiceDeactivationResultCallback;

public class SocialApi extends SeMobileServiceApi {
    public static final String API_NAME = "SocialApi";
    public static final int CONTACT_UPLOAD_BASE_SERVICE = 2;
    public static final int COUNTRY_TYPE_CHINA = 3;
    public static final int COUNTRY_TYPE_EUROPE = 2;
    public static final int COUNTRY_TYPE_GLOBAL = 0;
    public static final int COUNTRY_TYPE_KOREA = 1;
    public static final int DEVICE_AUTH_BASE_SERVICE = 1;
    public static final int SERVICE_ID_CERTIFICATE_SHARING = 1;
    public static final int SERVICE_ID_PROFILE_SHARING = 0;
    public static final String SERVICE_NAME = "SocialService";

    private interface BundleKey {
        public static final String AGREEMENT_PROCEDURE = "AgreementProcedure";
        public static final String FORCE_UPDATE = "ForceUpdate";
        public static final String GDPR = "GDPR";
        public static final String INTRODUCTION = "Introduction";
        public static final String IS_LEGAL_POPUP_SUPPORTED = "isLegalPopupSupported";
        public static final String IS_NEEDED = "isNeeded";
        public static final String PERSONAL_INFORMATION_COLLECTION_AGREEMENT = "PersonalInformationCollectionAgreement";
        public static final String SERVICE_TYPE = "serviceType";
        public static final String SOCIAL_DISCLAIMER = "SocialDisclaimer";
        public static final String TERMS_AND_CONDITION = "TermsAndCondition";
    }

    public interface ServiceActivationResultCallback {
        void onResult(BooleanResult booleanResult, int i);
    }

    public interface ServiceDeactivationResultCallback {
        void onResult(BooleanResult booleanResult, int i);
    }

    public SocialApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "SocialApi");
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        return new String[]{"SocialService"};
    }

    public BooleanResult isServiceActivated(int i) {
        if (!isAuthorized()) {
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
        try {
            if (getSocialService().isServiceActivated(i) == 1) {
                debugLog("isServiceActivated serviceId=[" + i + "] true ");
                return new BooleanResult(new CommonResultStatus(1), true);
            }
            debugLog("isServiceActivated serviceId=[" + i + "] false ");
            return new BooleanResult(new CommonResultStatus(1), false);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
    }

    public int requestServiceActivation(int i, final ServiceActivationResultCallback serviceActivationResultCallback) {
        debugLog("requestServiceActivation serviceId=[" + i + "] ");
        if (!isAuthorized()) {
            return -1;
        }
        try {
            getSocialService().requestServiceActivation(i, new IServiceActivationResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.SocialApi.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IServiceActivationResultCallback
                public void onSuccess(int i) throws RemoteException {
                    SocialApi socialApi = SocialApi.this;
                    socialApi.debugLog("requestServiceActivation serviceId=[" + i + "] onSuccess ");
                    ServiceActivationResultCallback serviceActivationResultCallback = serviceActivationResultCallback;
                    if (serviceActivationResultCallback != null) {
                        serviceActivationResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true), i);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IServiceActivationResultCallback
                public void onFailure(int i, String str) throws RemoteException {
                    SocialApi socialApi = SocialApi.this;
                    socialApi.debugLog("requestServiceActivation onFailure : code=[" + i + "], message=[" + str + "] ");
                    if (serviceActivationResultCallback != null) {
                        serviceActivationResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(i), str, Integer.toString(i)), false), -1);
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        }
    }

    public int requestServiceDeactivation(int i, final ServiceDeactivationResultCallback serviceDeactivationResultCallback) {
        debugLog("requestServiceDeactivation serviceId=[" + i + "] ");
        if (!isAuthorized()) {
            return -1;
        }
        try {
            getSocialService().requestServiceDeactivation(i, new IServiceDeactivationResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.SocialApi.AnonymousClass2 */

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IServiceDeactivationResultCallback
                public void onSuccess(int i) throws RemoteException {
                    SocialApi socialApi = SocialApi.this;
                    socialApi.debugLog("requestServiceDeactivation serviceId=[" + i + "] onSuccess ");
                    ServiceDeactivationResultCallback serviceDeactivationResultCallback = serviceDeactivationResultCallback;
                    if (serviceDeactivationResultCallback != null) {
                        serviceDeactivationResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true), i);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IServiceDeactivationResultCallback
                public void onFailure(int i, String str) throws RemoteException {
                    SocialApi socialApi = SocialApi.this;
                    socialApi.debugLog("requestServiceDeactivation onFailure : code=[" + i + "], message=[" + str + "] ");
                    if (serviceDeactivationResultCallback != null) {
                        serviceDeactivationResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(i), str, Integer.toString(i)), false), -1);
                    }
                }
            });
            return 1;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        }
    }

    public Intent getIntentForSocialDisclaimerDisplay() {
        debugLog("getIntentForSocialDisclaimerDisplay ");
        return getIntentToDisplay("SocialDisclaimer");
    }

    public Intent getIntentForGdprErrorPopup() {
        debugLog("getIntentForGdprErrorPopup ");
        return getIntentToDisplay(BundleKey.GDPR);
    }

    public Intent getIntentForTermsAndConditionDisplay() {
        debugLog("getIntentForTermsAndConditionDisplay ");
        return getIntentToDisplay(BundleKey.TERMS_AND_CONDITION);
    }

    public Intent getIntentForPersonalInformationCollectionAgreementDisplay() {
        debugLog("getIntentForPersonalInformationCollectionAgreementDisplay ");
        return getIntentToDisplay(BundleKey.PERSONAL_INFORMATION_COLLECTION_AGREEMENT);
    }

    public Intent getIntentForAgreementProcedure() {
        debugLog("getIntentForAgreementProcedure ");
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLegalPopupSupported", true);
        return getIntentToDisplay("AgreementProcedure", bundle);
    }

    public Intent getIntentForAgreementProcedure(int i) {
        debugLog("getIntentForAgreementProcedure ");
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLegalPopupSupported", true);
        bundle.putInt(BundleKey.SERVICE_TYPE, i);
        return getIntentToDisplay("AgreementProcedure", bundle);
    }

    public Intent getIntentForIntroductionDisplay() {
        debugLog("getIntentForIntroductionDisplay ");
        return getIntentToDisplay(BundleKey.INTRODUCTION);
    }

    public Intent getIntentForForceUpdate() {
        return getIntentToDisplay(BundleKey.FORCE_UPDATE);
    }

    public boolean isAgreementProcedureNeeded() {
        debugLog("isAgreementProcedureNeeded ");
        Bundle isSomethingNeeded = isSomethingNeeded("AgreementProcedure");
        boolean z = true;
        if (isSomethingNeeded != null) {
            z = isSomethingNeeded.getBoolean("isNeeded", true);
        }
        debugLog("isAgreementProcedureNeeded " + z + ", ");
        return z;
    }

    public boolean isAgreementProcedureNeeded(int i) {
        debugLog("isAgreementProcedureNeeded ");
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.SERVICE_TYPE, i);
        Bundle isSomethingNeeded = isSomethingNeeded("AgreementProcedure", bundle);
        boolean z = true;
        if (isSomethingNeeded != null) {
            z = isSomethingNeeded.getBoolean("isNeeded", true);
        }
        debugLog("isAgreementProcedureNeeded " + z);
        return z;
    }

    public int getCountryTypeForAgreement() {
        debugLog("getCountryTypeForAgreement ");
        try {
            return getSocialService().getCountryTypeForAgreement();
        } catch (Exception e) {
            secureLog(e);
            return -1;
        }
    }

    public SocialServiceState getServiceState() {
        debugLog("getServiceState ");
        try {
            return bundleToSocialServiceState(getSocialService().getServiceState());
        } catch (Exception e) {
            secureLog(e);
            return null;
        }
    }

    private SocialServiceState bundleToSocialServiceState(Bundle bundle) {
        if (bundle != null) {
            return new SocialServiceState(bundle.getInt("state", 1), (Intent) bundle.getParcelable("intent"));
        }
        debugLog("bundle is null : bundleToSocialServiceState");
        return null;
    }

    private boolean isAuthorized() {
        try {
            checkAuthorized(0, 1);
            return true;
        } catch (NotAuthorizedException unused) {
            return false;
        }
    }

    private Intent getIntentToDisplay(String str) {
        return getIntentToDisplay(str, new Bundle());
    }

    private Intent getIntentToDisplay(String str, Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.putString("what", str);
        try {
            return getSocialService().getIntentToDisplay(bundle);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        }
    }

    private Bundle isSomethingNeeded(String str) {
        return isSomethingNeeded(str, new Bundle());
    }

    private Bundle isSomethingNeeded(String str, Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        try {
            bundle.putString("what", str);
            return getSocialService().isSomethingNeeded(bundle);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        }
    }
}
