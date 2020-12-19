package com.samsung.android.sdk.mobileservice.social.buddy;

import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.ErrorCodeConvertor;
import com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.BooleanResult;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.social.buddy.IBuddyInfoResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.IPublicBuddyInfoResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.ISyncResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.PublicBuddyInfo;
import com.samsung.android.sdk.mobileservice.social.buddy.request.BuddyInfoRequest;
import com.samsung.android.sdk.mobileservice.social.buddy.result.Buddy;
import com.samsung.android.sdk.mobileservice.social.buddy.result.BuddyResult;
import com.samsung.android.sdk.mobileservice.social.buddy.result.Certificate;
import com.samsung.android.sdk.mobileservice.social.buddy.result.PublicBuddyInfoResult;
import com.samsung.android.sdk.mobileservice.social.buddy.result.SyncedContact;
import java.util.ArrayList;

public class BuddyApi extends SeMobileServiceApi {
    public static final String API_NAME = "BuddyApi";
    private static final String KEY_QUERY_RESULT = "QUERY_RESULT";
    private static final String KEY_QUERY_SELECTION = "QUERY_SELECTION";
    private static final int QUERY_TYPE_CERTIFICATE = 0;
    private static final int QUERY_TYPE_SYNCED_CONTACT_ID = 1;

    public interface BuddyInfoCallback {
        void onResult(BuddyResult<Buddy> buddyResult);
    }

    public interface PublicBuddyInfoResultCallback {
        void onResult(PublicBuddyInfoResult publicBuddyInfoResult);
    }

    public interface SyncResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean checkItem(int i, int i2) {
        return (i & i2) == i2;
    }

    public BuddyApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "BuddyApi");
        checkAuthorized(0, 1);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        return new String[]{"SocialService"};
    }

    @Deprecated
    public int requestSync(final SyncResultCallback syncResultCallback) {
        debugLog("requestSync ");
        try {
            getSocialService().requestSync(new ISyncResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.buddy.BuddyApi.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.ISyncResultCallback
                public void onSuccess() throws RemoteException {
                    BuddyApi.this.debugLog("requestSync onSuccess ");
                    SyncResultCallback syncResultCallback = syncResultCallback;
                    if (syncResultCallback != null) {
                        syncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.ISyncResultCallback
                public void onFailure(int i, String str) throws RemoteException {
                    BuddyApi buddyApi = BuddyApi.this;
                    buddyApi.debugLog("requestSync onFailure : code=[" + i + "], message=[" + str + "] ");
                    if (syncResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(i);
                        BuddyApi buddyApi2 = BuddyApi.this;
                        buddyApi2.debugLog("requestSync onFailure (" + convertErrorcode + ", " + i + ", " + str + ")");
                        syncResultCallback.onResult(new BooleanResult(new CommonResultStatus(convertErrorcode, str, Integer.toString(i)), false));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    public int requestSync(int i, final SyncResultCallback syncResultCallback) {
        debugLog("requestSync ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_11_1)) {
            return -7;
        }
        try {
            getSocialService().requestBuddySync(i, new ISyncResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.buddy.BuddyApi.AnonymousClass2 */

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.ISyncResultCallback
                public void onSuccess() {
                    BuddyApi.this.debugLog("requestSync onSuccess ");
                    SyncResultCallback syncResultCallback = syncResultCallback;
                    if (syncResultCallback != null) {
                        syncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.ISyncResultCallback
                public void onFailure(int i, String str) {
                    BuddyApi buddyApi = BuddyApi.this;
                    buddyApi.debugLog("requestSync onFailure : code=[" + i + "], message=[" + str + "] ");
                    if (syncResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(i);
                        BuddyApi buddyApi2 = BuddyApi.this;
                        buddyApi2.debugLog("requestSync onFailure (" + convertErrorcode + ", " + i + ", " + str + ")");
                        syncResultCallback.onResult(new BooleanResult(new CommonResultStatus(convertErrorcode, str, Integer.toString(i)), false));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    public void getBuddyInfo(final BuddyInfoRequest buddyInfoRequest, final BuddyInfoCallback buddyInfoCallback) {
        try {
            debugLog("getBuddyInfo");
            getSocialService().getBuddyInfo(buddyInfoRequest.toBundle(), new IBuddyInfoResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.buddy.BuddyApi.AnonymousClass3 */

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IBuddyInfoResultCallback
                public void onSuccess(Bundle bundle) {
                    Buddy buddy;
                    long j = bundle.getLong(BuddyApi.KEY_QUERY_RESULT, 1000);
                    CommonResultStatus commonResultStatus = new CommonResultStatus(j == 0 ? 1 : ErrorCodeConvertor.convertErrorcode(j));
                    SyncedContact syncedContact = null;
                    if (commonResultStatus.getCode() == 1) {
                        String string = bundle.getString("contact_id");
                        Certificate certificate = BuddyApi.this.checkItem(buddyInfoRequest.getItemFlags(), 1) ? new Certificate(bundle.getString("certificate")) : null;
                        if (BuddyApi.this.checkItem(buddyInfoRequest.getItemFlags(), 2)) {
                            syncedContact = new SyncedContact(bundle.getString("raw_contact_id"));
                        }
                        buddy = new Buddy(string, certificate, syncedContact);
                    } else {
                        buddy = null;
                    }
                    buddyInfoCallback.onResult(new BuddyResult<>(commonResultStatus, buddy));
                }

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IBuddyInfoResultCallback
                public void onFailure(int i, String str) {
                    BuddyApi buddyApi = BuddyApi.this;
                    buddyApi.debugLog("getBuddyInfo onFailure : code=[" + i + "], message=[" + str + "] ");
                    CommonResultStatus commonResultStatus = new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(i), str, String.valueOf(i));
                    BuddyInfoCallback buddyInfoCallback = buddyInfoCallback;
                    if (buddyInfoCallback != null) {
                        buddyInfoCallback.onResult(new BuddyResult<>(commonResultStatus, null));
                    }
                }
            });
        } catch (RemoteException e) {
            secureLog(e);
            buddyInfoCallback.onResult(new BuddyResult<>(new CommonResultStatus(301), null));
        } catch (NotConnectedException e2) {
            secureLog(e2);
            buddyInfoCallback.onResult(new BuddyResult<>(new CommonResultStatus(-8), null));
        }
    }

    public int requestPublicBuddyInfo(String str, final PublicBuddyInfoResultCallback publicBuddyInfoResultCallback) {
        debugLog("requestPublicBuddyInfo ");
        secureLog(" - phoneNumber = [%s]", str);
        try {
            getSocialService().requestPublicBuddyInfo(str, new IPublicBuddyInfoResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.buddy.BuddyApi.AnonymousClass4 */

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IPublicBuddyInfoResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    BuddyApi.this.debugLog("requestPublicBuddyInfo onSuccess ");
                    if (publicBuddyInfoResultCallback != null) {
                        String string = bundle.getString("extra_phone_number", null);
                        String string2 = bundle.getString("extra_name", null);
                        String string3 = bundle.getString("extra_status_msg", null);
                        byte[] byteArray = bundle.getByteArray("extra_image");
                        String string4 = bundle.getString("extra_image_type");
                        ArrayList<String> stringArrayList = bundle.getStringArrayList("extra_app_list");
                        ArrayList<String> stringArrayList2 = bundle.getStringArrayList("extra_svc_list");
                        BuddyApi.this.secureLog("requestPublicBuddyInfo extra_phone_number : [%s]", new String[]{string});
                        BuddyApi.this.secureLog("requestPublicBuddyInfo extra_status_msg : [%s]", new String[]{string3});
                        ArrayList arrayList = new ArrayList();
                        if (!(stringArrayList == null || stringArrayList2 == null)) {
                            for (int i = 0; i < stringArrayList.size(); i++) {
                                arrayList.add(new PublicBuddyInfo.Capability(stringArrayList.get(i), stringArrayList2.get(i)));
                            }
                        }
                        publicBuddyInfoResultCallback.onResult(new PublicBuddyInfoResult(new CommonResultStatus(1), new PublicBuddyInfo(string, string2, string3, byteArray, string4, arrayList)));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.buddy.IPublicBuddyInfoResultCallback
                public void onFailure(int i, String str) throws RemoteException {
                    BuddyApi buddyApi = BuddyApi.this;
                    buddyApi.debugLog("requestPublicBuddyInfo onFailure : code=[" + i + "], message=[" + str + "] ");
                    if (publicBuddyInfoResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(i);
                        BuddyApi buddyApi2 = BuddyApi.this;
                        buddyApi2.debugLog("requestPublicBuddyInfo onFailure (" + convertErrorcode + ", " + i + ", " + str + ")");
                        publicBuddyInfoResultCallback.onResult(new PublicBuddyInfoResult(new CommonResultStatus(convertErrorcode, str, Integer.toString(i)), null));
                    }
                }
            });
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }
}
