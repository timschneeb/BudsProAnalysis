package com.samsung.android.sdk.mobileservice.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.sdk.mobileservice.SeMobileService;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.auth.IAccessTokenResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IAuthCodeResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IAuthResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IDisclaimerAgreementForThirdPartyResultCallback;
import com.samsung.android.sdk.mobileservice.auth.IValidationResultCallback;
import com.samsung.android.sdk.mobileservice.auth.result.AccessTokenInfoResult;
import com.samsung.android.sdk.mobileservice.auth.result.AuthInfoResult;
import com.samsung.android.sdk.mobileservice.auth.result.DeviceAuthInfoResult;
import com.samsung.android.sdk.mobileservice.auth.result.ResultTokenInfo;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.CommonUtils;
import com.samsung.android.sdk.mobileservice.common.ErrorCodeConvertor;
import com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.BooleanResult;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.Constants;

public class AuthApi extends SeMobileServiceApi {
    public static final String API_NAME = "AuthApi";
    public static final int SERVICE_ID_GALLERY_CONTENT_SHARING = 32;
    public static final int SERVICE_ID_PROFILE_SHARING = 0;
    public static final String SERVICE_NAME = "AuthService";

    public interface AccessTokenResultCallback {
        void onResult(AccessTokenInfoResult accessTokenInfoResult);
    }

    public interface AuthCodeResultCallback {
        void onResult(ResultTokenInfo resultTokenInfo);
    }

    public interface AuthResultCallback {
        void onResult(AuthInfoResult authInfoResult);
    }

    private interface BundleKey {
        public static final String AGREEMENT_PROCEDURE = "AgreementProcedure";
        public static final String ENFORCE_FTU = "enforceFtu";
        public static final String IS_LEGAL_POPUP_SUPPORTED = "isLegalPopupSupported";
        public static final String IS_NEEDED = "isNeeded";
        public static final String LEGACY_LEGAL_POPUP = "LegacyLegalPopup";
        public static final String SOCIAL_ABOUT_PAGE = "SocialAboutPage";
        public static final String SOCIAL_DISCLAIMER = "SocialDisclaimer";
    }

    public interface DisclaimerAgreementResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface ValidationResultCallback {
        void onResult(BooleanResult booleanResult, boolean z, boolean z2, boolean z3, boolean z4);
    }

    public AuthApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "AuthApi");
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
            return new String[]{"AuthService", "SocialService"};
        }
        return new String[]{"AuthService"};
    }

    public Intent getIntentForAccountSignIn() {
        debugLog("getIntentForAccountSignIn");
        try {
            return getAuthService().getIntentForAccountSignIn();
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForAccountSignInPopup() {
        debugLog("getIntentForAccountSignInPopup ");
        try {
            return getAuthService().getIntentForAccountSignInPopup();
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForAccountPasswordConfirmation() {
        debugLog("getIntentForAccountPasswordConfirmation : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return null;
        }
        return getIntentForAccountPasswordConfirmation(getAppId());
    }

    public Intent getIntentForAccountPasswordConfirmationPopup() {
        debugLog("getIntentForAccountPasswordConfirmationPopup : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return null;
        }
        return getIntentForAccountPasswordConfirmationPopup(getAppId());
    }

    public Intent getIntentForAccountProfileModification() {
        debugLog("getIntentForAccountProfileModification : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return null;
        }
        return getIntentForAccountProfileModification(getAppId());
    }

    private Intent getIntentForAccountProfileModification(String str) {
        try {
            return getAuthService().getIntentForAccountProfileModification(str);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForAccountDisclaimerAgreement(boolean z) {
        debugLog("getIntentForAccountDisclaimerAgreement : accountAppId=[" + getAppId() + "], forThirdParty=[" + z + "] ");
        if (getAppId() == null) {
            return null;
        }
        return getIntentForAccountDisclaimerAgreement(getAppId(), z);
    }

    private Intent getIntentForAccountDisclaimerAgreement(String str, boolean z) {
        try {
            return getAuthService().getIntentForAccountDisclaimerAgreement(str, z);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForAccountSetupWizard(boolean z) {
        debugLog("getIntentForAccountSetupWizard : isReactivationLockSupported=[" + z + "]");
        try {
            return getAuthService().getIntentForAccountSetupWizard(z);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForAccountAccessTokenRequest(String str) {
        debugLog("getIntentForAccountDisclaimerAgreement : accountAppId=[" + getAppId() + "], expiredToken=[" + str + "] ");
        if (getAppId() == null) {
            return null;
        }
        return getIntentForAccountAccessTokenRequest(getAppId(), str);
    }

    private Intent getIntentForAccountAccessTokenRequest(String str, String str2) {
        try {
            return getAuthService().getIntentForAccountAccessTokenRequest(str, str2);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForAccountValidationRequest(boolean z, boolean z2) {
        debugLog("getIntentForAccountDisclaimerAgreement : accountAppId=[" + getAppId() + "], realNameCheck=[" + z + "], validationResultOnly=[" + z2 + "] ");
        if (getAppId() == null) {
            return null;
        }
        return getIntentForAccountValidationRequest(getAppId(), z, z2);
    }

    private Intent getIntentForAccountValidationRequest(String str, boolean z, boolean z2) {
        try {
            return getAuthService().getIntentForAccountValidationRequest(str, z, z2);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForSocialSignUp() {
        debugLog("getIntentForSocialSignUp ");
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                return getIntentToDisplay(BundleKey.LEGACY_LEGAL_POPUP);
            }
            return getAuthService().getIntentForSocialSignUp();
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForSocialRegistrationInformation() {
        debugLog("getIntentForSocialRegistrationInformation ");
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                return getIntentToDisplay(BundleKey.SOCIAL_ABOUT_PAGE);
            }
            return getAuthService().getIntentForSocialRegistrationInformation();
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForSocialDisclaimerAgreement(boolean z, boolean z2) {
        debugLog("getIntentForSocialDisclaimerAgreement : isServiceOnRequired=[" + z + "], isSyncNowRequired=[" + z2 + "] ");
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                return getIntentToDisplay(BundleKey.LEGACY_LEGAL_POPUP);
            }
            return getAuthService().getIntentForSocialDisclaimerAgreement(z, z2);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public Intent getIntentForSocialDisclaimerDisplay() {
        debugLog("getIntentForSocialDisclaimerDisplay ");
        if (!isSupportedSaAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return null;
        }
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                return getIntentToDisplay("SocialDisclaimer");
            }
            return getAuthService().getIntentForSocialTnC();
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public int requestAuthInfo(final AuthResultCallback authResultCallback) {
        debugLog("requestAuthInfo : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return -1;
        }
        try {
            getAuthService().requestAuthInfo(getAppId(), getContext().getPackageName(), null, new IAuthResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.auth.AuthApi.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.auth.IAuthResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    AuthApi.this.debugLog("requestAuthInfo onSuccess ");
                    if (authResultCallback != null) {
                        AuthInfo authInfo = null;
                        if (bundle != null) {
                            if (bundle.containsKey("account_id")) {
                                authInfo = new AuthInfo();
                                authInfo.setAccountId(bundle.getString("account_id"));
                            }
                            if (bundle.containsKey("guid")) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setGuid(bundle.getString("guid"));
                            }
                            if (bundle.containsKey("country_code")) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setCountryCode(bundle.getString("country_code"));
                            }
                            if (bundle.containsKey(XDBInterface.XDM_SQL_ACCESSORY_MCC)) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setMobileCountryCode(bundle.getString(XDBInterface.XDM_SQL_ACCESSORY_MCC));
                            }
                            if (bundle.containsKey("device_physical_address")) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setDevicePhysicalAddress(bundle.getString("device_physical_address"));
                            }
                            if (bundle.containsKey("is_email_authenticated")) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setEmailAddressAuthenticated(bundle.getBoolean("is_email_authenticated"));
                            }
                            if (bundle.containsKey("is_name_authenticated")) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setRealNameAuthenticated(bundle.getBoolean("is_name_authenticated"));
                            }
                            if (bundle.containsKey("is_account_disclaimer_agreed")) {
                                if (authInfo == null) {
                                    authInfo = new AuthInfo();
                                }
                                authInfo.setAccountDisclaimerAgreed(bundle.getBoolean("is_account_disclaimer_agreed"));
                            }
                        }
                        authResultCallback.onResult(new AuthInfoResult(new CommonResultStatus(1), authInfo));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.auth.IAuthResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    AuthApi authApi = AuthApi.this;
                    authApi.debugLog("requestAuthInfo onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    if (authResultCallback != null) {
                        authResultCallback.onResult(new AuthInfoResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(str), str2, str), null));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public BooleanResult isServiceRegistered(int i) {
        boolean z;
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                Bundle bundle = new Bundle();
                bundle.putInt("serviceId", i);
                if (getSocialService() == null) {
                    z = false;
                } else {
                    z = getSocialService().isServiceRegistered(bundle);
                }
            } else {
                z = getAuthService().isServiceRegistered(i);
            }
            if (SeMobileService.getAgentVersion(getContext()) < 420000000 && i == 32) {
                z = z && getDeviceAuthInfo().getResult() != null;
            }
            debugLog("isServiceRegistered serviceId=[" + i + "] " + z + " ");
            return new BooleanResult(new CommonResultStatus(1), z);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new BooleanResult(new CommonResultStatus(-1), false);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
    }

    public int requestAccessTokenForAccount(final AccessTokenResultCallback accessTokenResultCallback) {
        debugLog("requestAccessTokenForAccount : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return -1;
        }
        try {
            getAuthService().requestAccessTokenForAccount(getAppId(), getContext().getPackageName(), null, new IAccessTokenResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.auth.AuthApi.AnonymousClass2 */

                @Override // com.samsung.android.sdk.mobileservice.auth.IAccessTokenResultCallback
                public void onSuccess(Bundle bundle, Bundle bundle2) throws RemoteException {
                    AuthApi.this.debugLog("requestAccessTokenForAccount onSuccess ");
                    if (accessTokenResultCallback != null) {
                        TokenInfo tokenInfo = null;
                        if (bundle != null) {
                            tokenInfo = new TokenInfo();
                            if (bundle.containsKey(Constants.TokenResponseConstants.TOKEN)) {
                                tokenInfo.setToken(bundle.getString(Constants.TokenResponseConstants.TOKEN));
                            }
                            if (bundle.containsKey("token_type")) {
                                tokenInfo.setTokenType(bundle.getString("token_type"));
                            }
                            if (bundle.containsKey(AuthConstants.EXTRA_API_SERVER_URL)) {
                                tokenInfo.setApiServerUrl(bundle.getString(AuthConstants.EXTRA_API_SERVER_URL));
                            }
                            if (bundle.containsKey(AuthConstants.EXTRA_AUTH_SERVER_URL)) {
                                tokenInfo.setAuthServerUrl(bundle.getString(AuthConstants.EXTRA_AUTH_SERVER_URL));
                            }
                            if (bundle.containsKey("token_issued_time")) {
                                tokenInfo.setTokenIssuedTime(bundle.getLong("token_issued_time"));
                            }
                            if (bundle.containsKey("token_validity_period")) {
                                tokenInfo.setTokenValidityPeriod(bundle.getLong("token_validity_period"));
                            }
                        }
                        accessTokenResultCallback.onResult(new AccessTokenInfoResult(new CommonResultStatus(1), tokenInfo));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.auth.IAccessTokenResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    AuthApi authApi = AuthApi.this;
                    authApi.debugLog("requestAccessTokenForAccount onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    if (accessTokenResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(str);
                        AuthApi authApi2 = AuthApi.this;
                        authApi2.debugLog("converted error code = " + convertErrorcode + " - requestAccessTokenForAccount");
                        accessTokenResultCallback.onResult(new AccessTokenInfoResult(new CommonResultStatus(convertErrorcode, str2, str), null));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int requestRenewAccessTokenForAccount(String str, final AccessTokenResultCallback accessTokenResultCallback) {
        debugLog("requestRenewAccessTokenForAccount : accountAppId=[" + getAppId() + "], expiredAccessToken=[" + str + "] ");
        if (getAppId() == null) {
            return -1;
        }
        try {
            getAuthService().requestRenewAccessTokenForAccount(getAppId(), getContext().getPackageName(), null, str, new IAccessTokenResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.auth.AuthApi.AnonymousClass3 */

                @Override // com.samsung.android.sdk.mobileservice.auth.IAccessTokenResultCallback
                public void onSuccess(Bundle bundle, Bundle bundle2) throws RemoteException {
                    AuthApi.this.debugLog("requestRenewAccessTokenForAccount onSuccess ");
                    if (accessTokenResultCallback != null) {
                        TokenInfo tokenInfo = null;
                        if (bundle != null) {
                            tokenInfo = new TokenInfo();
                            if (bundle.containsKey(Constants.TokenResponseConstants.TOKEN)) {
                                tokenInfo.setToken(bundle.getString(Constants.TokenResponseConstants.TOKEN));
                            }
                            if (bundle.containsKey("token_type")) {
                                tokenInfo.setTokenType(bundle.getString("token_type"));
                            }
                            if (bundle.containsKey(AuthConstants.EXTRA_API_SERVER_URL)) {
                                tokenInfo.setApiServerUrl(bundle.getString(AuthConstants.EXTRA_API_SERVER_URL));
                            }
                            if (bundle.containsKey(AuthConstants.EXTRA_AUTH_SERVER_URL)) {
                                tokenInfo.setAuthServerUrl(bundle.getString(AuthConstants.EXTRA_AUTH_SERVER_URL));
                            }
                            if (bundle.containsKey("token_issued_time")) {
                                tokenInfo.setTokenIssuedTime(bundle.getLong("token_issued_time"));
                            }
                            if (bundle.containsKey("token_validity_period")) {
                                tokenInfo.setTokenValidityPeriod(bundle.getLong("token_validity_period"));
                            }
                        }
                        accessTokenResultCallback.onResult(new AccessTokenInfoResult(new CommonResultStatus(1), tokenInfo));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.auth.IAccessTokenResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    AuthApi authApi = AuthApi.this;
                    authApi.debugLog("requestRenewAccessTokenForAccount onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    if (accessTokenResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(str);
                        AuthApi authApi2 = AuthApi.this;
                        authApi2.debugLog("converted error code = " + convertErrorcode + " - requestRenewAccessTokenForAccount");
                        accessTokenResultCallback.onResult(new AccessTokenInfoResult(new CommonResultStatus(convertErrorcode, str2, str), null));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int requestDisclaimerAgreementForThirdParty(final DisclaimerAgreementResultCallback disclaimerAgreementResultCallback) {
        debugLog("requestDisclaimerAgreement : accountAppId=[" + getAppId() + "]");
        if (getAppId() == null) {
            return -1;
        }
        try {
            getAuthService().requestDisclaimerAgreementForThirdParty(getAppId(), getContext().getPackageName(), null, new IDisclaimerAgreementForThirdPartyResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.auth.AuthApi.AnonymousClass4 */

                @Override // com.samsung.android.sdk.mobileservice.auth.IDisclaimerAgreementForThirdPartyResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    AuthApi authApi = AuthApi.this;
                    authApi.debugLog("requestDisclaimerAgreementForThirdParty onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    if (disclaimerAgreementResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(str);
                        AuthApi authApi2 = AuthApi.this;
                        authApi2.debugLog("converted error code = " + convertErrorcode + " - requestDisclaimerAgreement");
                        disclaimerAgreementResultCallback.onResult(new BooleanResult(new CommonResultStatus(convertErrorcode, str2, str), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.auth.IDisclaimerAgreementForThirdPartyResultCallback
                public void onSuccess(boolean z) throws RemoteException {
                    AuthApi.this.debugLog("requestDisclaimerAgreement onSuccess ");
                    DisclaimerAgreementResultCallback disclaimerAgreementResultCallback = disclaimerAgreementResultCallback;
                    if (disclaimerAgreementResultCallback != null) {
                        disclaimerAgreementResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), z));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int requestAuthCodeForAccount(final AuthCodeResultCallback authCodeResultCallback) {
        debugLog("requestAuthCodeForAccount : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return -1;
        }
        try {
            getAuthService().requestAuthCode(getAppId(), getContext().getPackageName(), null, new IAuthCodeResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.auth.AuthApi.AnonymousClass5 */

                @Override // com.samsung.android.sdk.mobileservice.auth.IAuthCodeResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    AuthApi.this.debugLog("requestAuthCodeForAccount onSuccess ");
                    if (authCodeResultCallback != null) {
                        TokenInfo tokenInfo = null;
                        if (bundle != null) {
                            tokenInfo = new TokenInfo();
                            if (bundle.containsKey(Constants.TokenResponseConstants.TOKEN)) {
                                tokenInfo.setToken(bundle.getString(Constants.TokenResponseConstants.TOKEN));
                            }
                            if (bundle.containsKey("token_type")) {
                                tokenInfo.setTokenType(bundle.getString("token_type"));
                            }
                            if (bundle.containsKey(AuthConstants.EXTRA_API_SERVER_URL)) {
                                tokenInfo.setApiServerUrl(bundle.getString(AuthConstants.EXTRA_API_SERVER_URL));
                            }
                            if (bundle.containsKey(AuthConstants.EXTRA_AUTH_SERVER_URL)) {
                                tokenInfo.setAuthServerUrl(bundle.getString(AuthConstants.EXTRA_AUTH_SERVER_URL));
                            }
                            if (bundle.containsKey("token_issued_time")) {
                                tokenInfo.setTokenIssuedTime(bundle.getLong("token_issued_time"));
                            }
                            if (bundle.containsKey("token_validity_period")) {
                                tokenInfo.setTokenValidityPeriod(bundle.getLong("token_validity_period"));
                            }
                        }
                        authCodeResultCallback.onResult(new ResultTokenInfo(new CommonResultStatus(1), tokenInfo));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.auth.IAuthCodeResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    AuthApi authApi = AuthApi.this;
                    authApi.debugLog("requestAuthCodeForAccount onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    if (authCodeResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(str);
                        AuthApi authApi2 = AuthApi.this;
                        authApi2.debugLog("converted error code = " + convertErrorcode + " - requestAuthCode");
                        authCodeResultCallback.onResult(new ResultTokenInfo(new CommonResultStatus(convertErrorcode, str2, str), null));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public BooleanResult getDisclaimerAgreementForSocial() {
        boolean z = false;
        try {
            if (!CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                z = getAuthService().getDisclaimerAgreementForSocial();
            } else if (getSocialService() != null) {
                z = getSocialService().getDisclaimerAgreementForSocial();
            }
            debugLog("getDisclaimerAgreementForSocial " + z + " ");
            return new BooleanResult(new CommonResultStatus(1), z);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new BooleanResult(new CommonResultStatus(-1), false);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
    }

    public BooleanResult getDisclaimerAgreementForSocial(int i) {
        boolean z = false;
        if (!isSupportedSaAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
        try {
            if (!CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                z = getAuthService().getDisclaimerAgreementForService(i);
            } else if (getSocialService() != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("serviceId", i);
                z = getSocialService().getDisclaimerAgreementForService(bundle);
            }
            debugLog("getDisclaimerAgreementForSocial " + z + " ");
            return new BooleanResult(new CommonResultStatus(1), z);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new BooleanResult(new CommonResultStatus(-1), false);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
    }

    public BooleanResult getAccountValidation() {
        try {
            boolean accountValidation = getAuthService().getAccountValidation();
            debugLog("isAccountValid " + accountValidation + " ");
            return new BooleanResult(new CommonResultStatus(1), accountValidation);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new BooleanResult(new CommonResultStatus(-1), false);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
    }

    public int requestAccountValidation(final ValidationResultCallback validationResultCallback) {
        debugLog("requestAccountValidation : accountAppId=[" + getAppId() + "] ");
        if (getAppId() == null) {
            return -1;
        }
        try {
            getAuthService().requestValidation(getAppId(), getContext().getPackageName(), null, new IValidationResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.auth.AuthApi.AnonymousClass6 */

                @Override // com.samsung.android.sdk.mobileservice.auth.IValidationResultCallback
                public void onFailure(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4) throws RemoteException {
                    AuthApi authApi = AuthApi.this;
                    authApi.debugLog("requestAccountValidation onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    if (validationResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(str);
                        AuthApi authApi2 = AuthApi.this;
                        authApi2.debugLog("converted error code = " + convertErrorcode + " - requestAccountValidation");
                        validationResultCallback.onResult(new BooleanResult(new CommonResultStatus(convertErrorcode, str2, str), false), z, z2, z3, z4);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.auth.IValidationResultCallback
                public void onSuccess() throws RemoteException {
                    AuthApi.this.debugLog("requestAccountValidation : onSuccess ");
                    ValidationResultCallback validationResultCallback = validationResultCallback;
                    if (validationResultCallback != null) {
                        validationResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true), false, false, false, false);
                    }
                }
            });
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    private Intent getIntentForAccountPasswordConfirmation(String str) {
        debugLog("getIntentForAccountPasswordConfirmation : accountAppId=[" + str + "] ");
        try {
            return getAuthService().getIntentForAccountPasswordConfirmation(str);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    private Intent getIntentForAccountPasswordConfirmationPopup(String str) {
        debugLog("getIntentForAccountPasswordConfirmationPopup : accountAppId=[" + str + "] ");
        try {
            return getAuthService().getIntentForAccountPasswordConfirmationPopup(str);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    public AuthInfoResult getAuthInfo() {
        AuthInfo authInfo;
        AuthInfo authInfo2;
        debugLog("getAuthInfo ");
        try {
            if (getAuthService() == null) {
                debugLog("auth service is null ");
                return new AuthInfoResult(new CommonResultStatus(-1), null);
            }
            Bundle authInfoCached = getAuthService().getAuthInfoCached();
            if (authInfoCached != null) {
                if (authInfoCached.containsKey("account_id")) {
                    authInfo2 = new AuthInfo();
                    authInfo2.setAccountId(authInfoCached.getString("account_id"));
                } else {
                    authInfo2 = null;
                }
                if (authInfoCached.containsKey("guid")) {
                    if (authInfo2 == null) {
                        authInfo2 = new AuthInfo();
                    }
                    authInfo2.setGuid(authInfoCached.getString("guid"));
                }
                if (authInfoCached.containsKey("country_code")) {
                    if (authInfo2 == null) {
                        authInfo2 = new AuthInfo();
                    }
                    authInfo2.setCountryCode(authInfoCached.getString("country_code"));
                }
                if (authInfoCached.containsKey(XDBInterface.XDM_SQL_ACCESSORY_MCC)) {
                    if (authInfo2 == null) {
                        authInfo2 = new AuthInfo();
                    }
                    authInfo2.setMobileCountryCode(authInfoCached.getString(XDBInterface.XDM_SQL_ACCESSORY_MCC));
                }
                if (authInfoCached.containsKey("device_physical_address")) {
                    if (authInfo2 == null) {
                        authInfo2 = new AuthInfo();
                    }
                    authInfo2.setDevicePhysicalAddress(authInfoCached.getString("device_physical_address"));
                }
                if (authInfoCached.containsKey("is_email_authenticated")) {
                    if (authInfo2 == null) {
                        authInfo2 = new AuthInfo();
                    }
                    authInfo2.setEmailAddressAuthenticated(authInfoCached.getBoolean("is_email_authenticated"));
                }
                if (authInfoCached.containsKey("is_name_authenticated")) {
                    if (authInfo2 == null) {
                        authInfo2 = new AuthInfo();
                    }
                    authInfo2.setRealNameAuthenticated(authInfoCached.getBoolean("is_name_authenticated"));
                }
                if (authInfoCached.containsKey("is_account_disclaimer_agreed")) {
                    authInfo = authInfo2 == null ? new AuthInfo() : authInfo2;
                    authInfo.setAccountDisclaimerAgreed(authInfoCached.getBoolean("is_account_disclaimer_agreed"));
                } else {
                    authInfo = authInfo2;
                }
            } else {
                authInfo = null;
            }
            if (authInfo != null) {
                return new AuthInfoResult(new CommonResultStatus(1), authInfo);
            }
            debugLog("getAuthInfo is null");
            return new AuthInfoResult(new CommonResultStatus(1), null);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new AuthInfoResult(new CommonResultStatus(-1), null);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new AuthInfoResult(new CommonResultStatus(-1), null);
        }
    }

    public DeviceAuthInfoResult getDeviceAuthInfo() {
        Bundle bundle;
        DeviceAuthInfo deviceAuthInfo;
        DeviceAuthInfo deviceAuthInfo2;
        debugLog("getDeviceAuthInfo ");
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                if (getSocialService() != null) {
                    bundle = getSocialService().getDeviceAuthInfoCached();
                } else {
                    debugLog("social service is null ");
                    bundle = null;
                }
            } else if (getAuthService() == null) {
                debugLog("auth service is null ");
                return new DeviceAuthInfoResult(new CommonResultStatus(-1), null);
            } else {
                bundle = getAuthService().getDeviceAuthInfoCached();
            }
            if (bundle != null) {
                if (bundle.containsKey(XDBInterface.XDM_SQL_DB_SIMINFO_IMSI)) {
                    deviceAuthInfo2 = new DeviceAuthInfo();
                    deviceAuthInfo2.setImsi(bundle.getString(XDBInterface.XDM_SQL_DB_SIMINFO_IMSI));
                } else {
                    deviceAuthInfo2 = null;
                }
                if (bundle.containsKey("msisdn")) {
                    deviceAuthInfo = deviceAuthInfo2 == null ? new DeviceAuthInfo() : deviceAuthInfo2;
                    deviceAuthInfo.setMsisdn(bundle.getString("msisdn"));
                } else {
                    deviceAuthInfo = deviceAuthInfo2;
                }
            } else {
                deviceAuthInfo = null;
            }
            if (deviceAuthInfo != null) {
                return new DeviceAuthInfoResult(new CommonResultStatus(1), deviceAuthInfo);
            }
            debugLog("getDeviceAuthInfo is null");
            return new DeviceAuthInfoResult(new CommonResultStatus(1), null);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new DeviceAuthInfoResult(new CommonResultStatus(-1), null);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new DeviceAuthInfoResult(new CommonResultStatus(-1), null);
        }
    }

    public BooleanResult isSocialDisclaimerDisplayNeeded() {
        boolean z;
        debugLog("isSocialDisclaimerDisplayNeeded ");
        if (!isSupportedSaAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
        try {
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                Bundle isSomethingNeeded = isSomethingNeeded("SocialDisclaimer");
                z = isSomethingNeeded != null ? isSomethingNeeded.getBoolean("isNeeded", true) : true;
            } else {
                z = getAuthService().getNeedToShowSocialTncPopup();
            }
            return new BooleanResult(new CommonResultStatus(1), z);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return new BooleanResult(new CommonResultStatus(-1), false);
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new BooleanResult(new CommonResultStatus(-1), false);
        }
    }

    public int setDisclaimerAgreementForSocial(boolean z) {
        debugLog("setDisclaimerAgreementForSocial ");
        if (!isSupportedSaAgentVersion(1000000000)) {
            debugLog("Agent version is under ver. Need to use with Agent 10.0.00.00 or higher.");
            return -7;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean("agreed", z);
            if (CommonUtils.isStandAloneSamsungAccountSupported(getContext())) {
                if (!getSocialService().setDisclaimerAgreementForSocial(bundle)) {
                    return -1;
                }
            } else if (getAuthService().setDisclaimerAgreementForSocial(z)) {
                return 1;
            } else {
                return -1;
            }
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
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
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }

    private Bundle isSomethingNeeded(String str) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("what", str);
            return getSocialService().isSomethingNeeded(bundle);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return null;
        }
    }
}
