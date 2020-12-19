package com.samsung.android.sdk.mobileservice.social.share;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.auth.AuthConstants;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.ErrorCodeConvertor;
import com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.BooleanResult;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial;
import com.samsung.android.sdk.mobileservice.social.group.GroupApi;
import com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IDownloadThumbnailResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback;
import com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISharedItemDeletionResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISharedItemListDeletionResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISharedItemListResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISharedItemResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISharedItemUpdateResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISpaceCoverImageDownloadingResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISpaceDeletionResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISpaceListResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.ShareApi;
import com.samsung.android.sdk.mobileservice.social.share.ShareController;
import com.samsung.android.sdk.mobileservice.social.share.provider.SharedItemContract;
import com.samsung.android.sdk.mobileservice.social.share.result.ContentDownloadResult;
import com.samsung.android.sdk.mobileservice.social.share.result.DownloadImageResult;
import com.samsung.android.sdk.mobileservice.social.share.result.ItemListResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SharedItemListDeletionResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SharedItemListResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SharedItemListWithContentListResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SharedItemListWithUriListResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SharedItemResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SharedItemWithUriListResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SpaceImageDownloadResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SpaceListResult;
import com.samsung.android.sdk.mobileservice.social.share.result.SpaceResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class ShareApiImpl extends SeMobileServiceApi {
    private static final String TAG = "ShareApiImpl";
    private final String APP_ID_REMINDER = "8o8b82h22a";
    private final ShareController.ShareControllerApiPicker mApiPicker = new ShareController.ShareControllerApiPicker() {
        /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass1 */

        @Override // com.samsung.android.sdk.mobileservice.social.share.ShareController.ShareControllerApiPicker
        public IMobileServiceSocial getSocialService() throws NotConnectedException {
            return ShareApiImpl.this.getSocialService();
        }

        @Override // com.samsung.android.sdk.mobileservice.social.share.ShareController.ShareControllerApiPicker
        public String getReference() {
            return ShareApiImpl.this.getReference();
        }

        @Override // com.samsung.android.sdk.mobileservice.social.share.ShareController.ShareControllerApiPicker
        public String getAppId() {
            return ShareApiImpl.this.getAppId();
        }
    };

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String getTag() {
        return TAG;
    }

    ShareApiImpl(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "ShareApi");
        checkAuthorized(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        return new String[]{"SocialService"};
    }

    /* access modifiers changed from: package-private */
    public int requestSync(String str, final ShareApi.ShareSyncResultCallback shareSyncResultCallback) {
        debugLog("requestSync ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass2 r0 = new IShareSyncResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass2 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
            public void onSuccess() throws RemoteException {
                ShareApiImpl.this.debugLog("requestSync onSuccess ");
                ShareApi.ShareSyncResultCallback shareSyncResultCallback = shareSyncResultCallback;
                if (shareSyncResultCallback != null) {
                    shareSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSync onFailure : code=[" + j + "], message=[" + str + "] ");
                if (shareSyncResultCallback != null) {
                    shareSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str)) {
                getSocialService().requestShareSync(getAppId(), r0);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str);
            getSocialService().requestShareSyncWithData(getAppId(), bundle, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpaceCreation(String str, ShareApi.SpaceRequest spaceRequest, String str2, final ShareApi.SpaceResultCallback spaceResultCallback) {
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass3 r8 = new ISpaceResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass3 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpaceCreation onSuccess ");
                ShareApi.SpaceResultCallback spaceResultCallback = spaceResultCallback;
                if (spaceResultCallback != null) {
                    spaceResultCallback.onResult(new SpaceResult(new CommonResultStatus(1), ShareApiImpl.this.createSpaceResult(bundle)));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpaceCreation onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceResultCallback != null) {
                    spaceResultCallback.onResult(new SpaceResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                }
            }
        };
        try {
            Bundle bundle = new Bundle();
            bundle.putString("title", spaceRequest.getTitle());
            bundle.putString("memo", spaceRequest.getMemo());
            debugLog("requestSpaceCreation : groupId=[" + str + "] , title=[" + spaceRequest.getTitle() + "], memo=[" + spaceRequest.getMemo() + "] ");
            bundle.putString("mime_type", spaceRequest.getMimeType());
            if (spaceRequest instanceof ShareApi.SpaceWithUriRequest) {
                ShareApi.SpaceWithUriRequest spaceWithUriRequest = (ShareApi.SpaceWithUriRequest) spaceRequest;
                if (spaceWithUriRequest.getCoverImageUri() != null) {
                    bundle.putString("content_uri", spaceWithUriRequest.getCoverImageUri().toString());
                    debugLog("requestSpaceCreation : coverImageUriString=[" + spaceWithUriRequest.getCoverImageUri().toString() + "] ");
                }
            } else if (spaceRequest instanceof ShareApi.SpaceWithSCloudHashRequest) {
                ShareApi.SpaceWithSCloudHashRequest spaceWithSCloudHashRequest = (ShareApi.SpaceWithSCloudHashRequest) spaceRequest;
                if (spaceWithSCloudHashRequest.getHash() != null) {
                    bundle.putString("content_hash", spaceWithSCloudHashRequest.getHash());
                    bundle.putLong("file_size", spaceWithSCloudHashRequest.getCoverImageSize());
                    bundle.putString("file_name", spaceWithSCloudHashRequest.getCoverImageName());
                }
            } else if (spaceRequest instanceof ShareApi.SpaceWithMediaServiceContentIdRequest) {
                ShareApi.SpaceWithMediaServiceContentIdRequest spaceWithMediaServiceContentIdRequest = (ShareApi.SpaceWithMediaServiceContentIdRequest) spaceRequest;
                if (spaceWithMediaServiceContentIdRequest.getMediaServiceContentId() != null) {
                    bundle.putString("media_service_content_id", spaceWithMediaServiceContentIdRequest.getMediaServiceContentId());
                    bundle.putLong("file_size", spaceWithMediaServiceContentIdRequest.getCoverImageSize());
                    bundle.putString("file_name", spaceWithMediaServiceContentIdRequest.getCoverImageName());
                }
            }
            bundle.putSerializable("meta_data", (HashMap) spaceRequest.getMetaData());
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestSpaceCreation(getAppId(), str, bundle, r8);
            } else {
                Bundle bundle2 = new Bundle();
                bundle2.putString("extra_cid", str2);
                getSocialService().requestSpaceCreationWithData(getAppId(), str, bundle, bundle2, r8);
            }
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpaceList(String str, String str2, final ShareApi.SpaceListResultCallback spaceListResultCallback) {
        debugLog("requestSpaceList : groupId=[" + str + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass4 r0 = new ISpaceListResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass4 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceListResultCallback
            public void onSuccess(List<Bundle> list) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpaceList onSuccess ");
                if (spaceListResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : list) {
                        String string = bundle.getString("space_id");
                        String string2 = bundle.getString("group_id");
                        String string3 = bundle.getString("owner_id");
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- groupId=[" + string2 + "], spaceId=[" + string + "], ownerId=[" + string3 + "] ");
                        if (string == null || string2 == null || string3 == null) {
                            ShareApiImpl.this.debugLog("requestSpaceList is error (one of spaceId, ownerId and groupId is erro)");
                            spaceListResultCallback.onResult(new SpaceListResult(new CommonResultStatus(-1, "Invalid space information", ""), null));
                            return;
                        }
                        arrayList.add(ShareApiImpl.this.createSpaceResult(bundle));
                    }
                    spaceListResultCallback.onResult(new SpaceListResult(new CommonResultStatus(1), arrayList));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceListResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpaceList onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceListResultCallback != null) {
                    spaceListResultCallback.onResult(new SpaceListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestSpaceList(getAppId(), str, r0);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str2);
            getSocialService().requestSpaceListWithData(getAppId(), str, bundle, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpace(String str, String str2, final ShareApi.SpaceResultCallback spaceResultCallback) {
        debugLog("requestSpace : spaceId=[" + str + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass5 r0 = new ISpaceResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass5 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpace onSuccess ");
                ShareApi.SpaceResultCallback spaceResultCallback = spaceResultCallback;
                if (spaceResultCallback != null) {
                    spaceResultCallback.onResult(new SpaceResult(new CommonResultStatus(1), ShareApiImpl.this.createSpaceResult(bundle)));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestSpace Error Message [" + str + "]");
                    spaceResultCallback.onResult(new SpaceResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestSpace(getAppId(), str, r0);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str2);
            getSocialService().requestSpaceWithData(getAppId(), str, bundle, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpaceUpdate(String str, Bundle bundle, String str2, final ShareApi.SpaceResultCallback spaceResultCallback) {
        debugLog("requestSpaceUpdate : spaceId=[" + str + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass6 r7 = new ISpaceResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass6 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpaceUpdate onSuccess ");
                ShareApi.SpaceResultCallback spaceResultCallback = spaceResultCallback;
                if (spaceResultCallback != null) {
                    spaceResultCallback.onResult(new SpaceResult(new CommonResultStatus(1), ShareApiImpl.this.createSpaceResult(bundle)));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpaceUpdate onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceResultCallback != null) {
                    spaceResultCallback.onResult(new SpaceResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestSpaceUpdate(getAppId(), str, bundle, r7);
                return 1;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("extra_cid", str2);
            getSocialService().requestSpaceUpdateWithData(getAppId(), str, bundle2, bundle, r7);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpaceDeletion(String str, String str2, final ShareApi.SpaceDeletionResultCallback spaceDeletionResultCallback) {
        debugLog("requestSpaceDeletion : spaceId=[" + str + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass7 r0 = new ISpaceDeletionResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass7 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceDeletionResultCallback
            public void onSuccess() throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpaceDeletion onSuccess ");
                ShareApi.SpaceDeletionResultCallback spaceDeletionResultCallback = spaceDeletionResultCallback;
                if (spaceDeletionResultCallback != null) {
                    spaceDeletionResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceDeletionResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpaceDeletion onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceDeletionResultCallback != null) {
                    spaceDeletionResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestSpaceDeletion(getAppId(), str, r0);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str2);
            getSocialService().requestSpaceDeletionWithData(getAppId(), str, bundle, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpaceList(String str, final ShareApi.SpaceListResultCallback spaceListResultCallback) {
        debugLog("requestSpaceList ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass8 r0 = new ISpaceListResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass8 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceListResultCallback
            public void onSuccess(List<Bundle> list) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpaceList onSuccess ");
                if (spaceListResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : list) {
                        String string = bundle.getString("space_id");
                        String string2 = bundle.getString("group_id");
                        String string3 = bundle.getString("owner_id");
                        if (string == null || string2 == null || string3 == null) {
                            spaceListResultCallback.onResult(new SpaceListResult(new CommonResultStatus(-1, "Invalid space information", ""), null));
                            return;
                        }
                        arrayList.add(ShareApiImpl.this.createSpaceResult(bundle));
                    }
                    spaceListResultCallback.onResult(new SpaceListResult(new CommonResultStatus(1), arrayList));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceListResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpaceList onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceListResultCallback != null) {
                    spaceListResultCallback.onResult(new SpaceListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str)) {
                getSocialService().requestAllSpaceList(getAppId(), r0);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str);
            getSocialService().requestAllSpaceListWithData(getAppId(), bundle, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public void clearUnreadCount(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().clearSpaceUnreadCount(getAppId(), str);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str2);
            getSocialService().clearSpaceUnreadCountWithData(getAppId(), bundle, str);
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSpaceListSync(String str, final ShareApi.SpaceListSyncResultCallback spaceListSyncResultCallback) {
        debugLog("requestSpaceListSync ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass9 r0 = new IShareSyncResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass9 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
            public void onSuccess() throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpaceListSync onSuccess ");
                ShareApi.SpaceListSyncResultCallback spaceListSyncResultCallback = spaceListSyncResultCallback;
                if (spaceListSyncResultCallback != null) {
                    spaceListSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpaceListSync onFailure : code=[" + j + "], message=[" + str + "] ");
                if (spaceListSyncResultCallback != null) {
                    spaceListSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str)) {
                getSocialService().requestSpaceListSync(getAppId(), r0);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str);
            getSocialService().requestSpaceListSyncWithData(getAppId(), bundle, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedItemSync(String str, String str2, String str3, final ShareApi.SharedItemSyncResultCallback sharedItemSyncResultCallback) {
        debugLog("requestSharedItemSync : spaceId=[" + str + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_10_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass10 r0 = new IShareSyncResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass10 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
            public void onSuccess() throws RemoteException {
                ShareApiImpl.this.debugLog("requestSharedItemSync onSuccess ");
                ShareApi.SharedItemSyncResultCallback sharedItemSyncResultCallback = sharedItemSyncResultCallback;
                if (sharedItemSyncResultCallback != null) {
                    sharedItemSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSharedItemSync onFailure : code=[" + j + "], message=[" + str + "] ");
                if (sharedItemSyncResultCallback != null) {
                    sharedItemSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                }
            }
        };
        Bundle bundle = new Bundle();
        bundle.putString("space_id", str);
        bundle.putString("thumbnail_resolution", str2);
        try {
            if (TextUtils.isEmpty(str3)) {
                getSocialService().requestSharedItemSyncWithResolution(getAppId(), bundle, r0);
                return 1;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("extra_cid", str3);
            getSocialService().requestSharedItemSyncWithResolutionWithData(getAppId(), bundle, bundle2, r0);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedItemDeletion(String str, List<String> list, String str2, final ShareApi.ShareBaseResultCallback<ItemListResult> shareBaseResultCallback) {
        debugLog("requestSharedItemDeletion : spaceId=[" + str + "], itemId size=" + list.size());
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass11 r7 = new ISharedItemListDeletionResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass11 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListDeletionResultCallback
            public void onSuccess(List<Bundle> list) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSharedItemDeletion onSuccess ");
                if (shareBaseResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (Bundle bundle : list) {
                        String string = bundle.getString("space_id");
                        String string2 = bundle.getString("item_id");
                        if (bundle.getBoolean("result")) {
                            arrayList.add(new ItemListResult.SharedItemListSuccessResult(string, string2));
                        } else {
                            arrayList2.add(new ItemListResult.SharedItemListFailureResult(string, string2, Long.valueOf(bundle.getLong(AuthConstants.EXTRA_ERROR_CODE))));
                        }
                    }
                    shareBaseResultCallback.onResult(new ItemListResult(new CommonResultStatus(1), arrayList, arrayList2));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListDeletionResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSharedItemDeletion onFailure : code=[" + j + "], message=[" + str + "] ");
                if (shareBaseResultCallback != null) {
                    shareBaseResultCallback.onResult(new ItemListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), new ArrayList(), new ArrayList()));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestSharedItemListDeletion(getAppId(), str, list, r7);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str2);
            getSocialService().requestSharedItemListDeletionWithData(getAppId(), str, list, bundle, r7);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedContentDownload(String str, List<String> list, final ShareApi.ContentDownloadingResultCallback contentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str2) {
        debugLog("requestSharedContentDownload spaceId=[" + str + "] itemIdList=[" + list + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        debugLog("requestSharedContentDownload spaceId=[" + str + "] itemIds=[" + list + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass12 r6 = new IContentDownloadingResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass12 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback
            public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                String str;
                String str2;
                ShareApiImpl.this.debugLog("requestSharedContentDownload onSuccess ");
                if (contentDownloadingResultCallback != null) {
                    String str3 = null;
                    if (!list.isEmpty() || !list2.isEmpty()) {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        Iterator<Bundle> it = list.iterator();
                        while (true) {
                            str = "item_id";
                            str2 = "space_id";
                            if (!it.hasNext()) {
                                break;
                            }
                            Bundle next = it.next();
                            String string = next.getString(str2, str3);
                            String string2 = next.getString(str, str3);
                            String string3 = next.getString("downloaded_uri", str3);
                            String string4 = next.getString("mime_type", str3);
                            arrayList.add(new ContentDownloadResult.DownloadedContent(string, string2, string3 != null ? Uri.parse(string3) : null, string4, next.getLong("file_size", -1)));
                            ShareApiImpl shareApiImpl = ShareApiImpl.this;
                            shareApiImpl.debugLog("- successList : space_id=[" + string + "], item_id=[" + string2 + "], downloaded_uri=[" + string3 + "], mime_type=[" + string4 + "] ");
                            arrayList2 = arrayList2;
                            str3 = null;
                        }
                        Iterator<Bundle> it2 = list2.iterator();
                        while (it2.hasNext()) {
                            Bundle next2 = it2.next();
                            String string5 = next2.getString(str2, null);
                            String string6 = next2.getString(str, null);
                            String string7 = next2.getString("downloaded_uri", null);
                            String string8 = next2.getString("mime_type", null);
                            arrayList2.add(new ContentDownloadResult.DownloadedContent(string5, string6, string7 != null ? Uri.parse(string7) : null, string8, next2.getLong("file_size", -1)));
                            ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                            shareApiImpl2.debugLog("- failureList : space_id=[" + string5 + "], item_id=[" + string6 + "], downloaded_uri=[" + string7 + "], mime_type=[" + string8 + "] ");
                            str = str;
                            it2 = it2;
                            str2 = str2;
                        }
                        contentDownloadingResultCallback.onResult(new ContentDownloadResult(new CommonResultStatus(1), arrayList, arrayList2));
                        return;
                    }
                    ShareApiImpl.this.debugLog("requestSharedContentDownload bundle is empty!!");
                    contentDownloadingResultCallback.onResult(new ContentDownloadResult(new CommonResultStatus(1), null, null));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback
            public void onProgress(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSharedContentDownload onProgress ");
                if (contentDownloadingResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    SharedContentDownloadSnapshot sharedContentDownloadSnapshot = new SharedContentDownloadSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    contentDownloadingResultCallback.onProgress(sharedContentDownloadSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSharedContentDownload onFailure : code=[" + j + "], message=[" + str + "] ");
                if (contentDownloadingResultCallback != null) {
                    contentDownloadingResultCallback.onResult(new ContentDownloadResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null, null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str2)) {
                getSocialService().requestOriginalSharedContentsDownload(getAppId(), str, (String[]) list.toArray(new String[list.size()]), r6, pendingIntent, bundle);
                return 1;
            }
            getSocialService().requestOriginalSharedContentsDownloadWithPath(getAppId(), str, (String[]) list.toArray(new String[list.size()]), r6, pendingIntent, bundle, str2);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public ShareController requestShare(String str, List<ShareApi.SharedItemWithUriListRequest> list, final ShareApi.ShareUploadResultCallback<SharedItemListWithUriListResult> shareUploadResultCallback, PendingIntent pendingIntent, Bundle bundle, String str2) {
        String str3;
        debugLog("requestShare : spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        } else if (!TextUtils.equals("8o8b82h22a", getAppId())) {
            debugLog("app id is not reminder. this api use only reminder");
            return null;
        } else {
            AnonymousClass13 r9 = new IShareResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass13 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestShare onSuccess ");
                    if (shareUploadResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            arrayList.add(new SharedItemWithUriList(bundle));
                        }
                        ArrayList arrayList2 = new ArrayList();
                        for (Bundle bundle2 : list2) {
                            arrayList2.add(ShareApiImpl.this.createShareFailedItemWithUriListResult(bundle2));
                        }
                        shareUploadResultCallback.onResult(new SharedItemListWithUriListResult(new CommonResultStatus(1), arrayList, arrayList2));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onProgress(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestShare onProgress ");
                    if (shareUploadResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        shareUploadResultCallback.onProgress(shareSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onUploadComplete(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                    if (shareUploadResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        shareUploadResultCallback.onUploadComplete(shareSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestShare onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (shareUploadResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                        ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                        shareApiImpl2.debugLog("requestShare Error Message [" + str + "]");
                        shareUploadResultCallback.onResult(new SharedItemListWithUriListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null, null));
                    }
                }
            };
            try {
                ArrayList arrayList = new ArrayList();
                for (ShareApi.SharedItemWithUriListRequest sharedItemWithUriListRequest : list) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("title", sharedItemWithUriListRequest.getTitle());
                    bundle2.putString("memo", sharedItemWithUriListRequest.getMemo());
                    bundle2.putString("mime_type", sharedItemWithUriListRequest.getContentMimeType());
                    bundle2.putInt("request_type", 0);
                    bundle2.putSerializable("meta_data", (HashMap) sharedItemWithUriListRequest.getMetaData());
                    ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                    for (int i = 0; i < sharedItemWithUriListRequest.getUris().size(); i++) {
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("content_uri", sharedItemWithUriListRequest.getUris().get(i).toString());
                        bundle3.putString("mime_type", sharedItemWithUriListRequest.getMimeTypeList().get(i));
                        arrayList2.add(bundle3);
                    }
                    bundle2.putParcelableArrayList("share_file_list", arrayList2);
                    debugLog("- title=[" + sharedItemWithUriListRequest.getTitle() + "], memo=[" + sharedItemWithUriListRequest.getMemo() + "], mime_type=[" + sharedItemWithUriListRequest.getContentMimeType() + "] ");
                    arrayList.add(bundle2);
                }
                if (TextUtils.isEmpty(str2)) {
                    str3 = getSocialService().requestShareWithItemFileList(getAppId(), str, arrayList, r9, pendingIntent, bundle);
                } else {
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("extra_cid", str2);
                    str3 = getSocialService().requestShareWithItemFileListWithData(getAppId(), str, arrayList, bundle4, r9, pendingIntent, bundle);
                }
                return new ShareController(this.mApiPicker, str3);
            } catch (RemoteException | NotConnectedException | NullPointerException e) {
                secureLog(e);
                return null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ShareController requestSharedItemWithUriListUpdate(String str, List<ShareApi.SharedItemUpdateWithUriListRequest> list, String str2, final ShareApi.ShareUploadResultCallback<SharedItemListWithUriListResult> shareUploadResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        String str3;
        debugLog("requestSpace : spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        } else if (!TextUtils.equals("8o8b82h22a", getAppId())) {
            debugLog("app id is not reminder. this api use only reminder");
            return null;
        } else {
            AnonymousClass14 r8 = new IShareResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass14 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemUpdate onSuccess ");
                    if (shareUploadResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            arrayList.add(new SharedItemWithUriList(bundle));
                        }
                        ArrayList arrayList2 = new ArrayList();
                        for (Bundle bundle2 : list2) {
                            arrayList2.add(ShareApiImpl.this.createShareFailedItemWithUriListResult(bundle2));
                        }
                        shareUploadResultCallback.onResult(new SharedItemListWithUriListResult(new CommonResultStatus(1), arrayList, arrayList2));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onProgress(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemUpdate onProgress ");
                    if (shareUploadResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        shareUploadResultCallback.onProgress(shareSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onUploadComplete(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemUpdate onUploadComplete ");
                    if (shareUploadResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        shareUploadResultCallback.onUploadComplete(shareSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestSharedItemUpdate onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (shareUploadResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                        ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                        shareApiImpl2.debugLog("requestSharedItemUpdate Error Message [" + str + "]");
                        shareUploadResultCallback.onResult(new SharedItemListWithUriListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null, null));
                    }
                }
            };
            ArrayList arrayList = new ArrayList();
            for (ShareApi.SharedItemUpdateWithUriListRequest sharedItemUpdateWithUriListRequest : list) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("item_id", sharedItemUpdateWithUriListRequest.getItemId());
                bundle2.putString("title", sharedItemUpdateWithUriListRequest.getTitle());
                bundle2.putString("memo", sharedItemUpdateWithUriListRequest.getMemo());
                bundle2.putString("mime_type", sharedItemUpdateWithUriListRequest.getContentMimeType());
                bundle2.putInt("request_type", 0);
                bundle2.putSerializable("meta_data", (HashMap) sharedItemUpdateWithUriListRequest.getMetaData());
                bundle2.putBoolean("file_replace_required", sharedItemUpdateWithUriListRequest.isFileReplaceRequired());
                ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                for (int i = 0; i < sharedItemUpdateWithUriListRequest.getUris().size(); i++) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("content_uri", sharedItemUpdateWithUriListRequest.getUris().get(i).toString());
                    bundle3.putString("mime_type", sharedItemUpdateWithUriListRequest.getMimeTypeList().get(i));
                    arrayList2.add(bundle3);
                }
                bundle2.putParcelableArrayList("share_file_list", arrayList2);
                debugLog("- title=[" + sharedItemUpdateWithUriListRequest.getTitle() + "], memo=[" + sharedItemUpdateWithUriListRequest.getMemo() + "], mime_type=[" + sharedItemUpdateWithUriListRequest.getContentMimeType() + "] ");
                arrayList.add(bundle2);
            }
            try {
                if (TextUtils.isEmpty(str2)) {
                    str3 = getSocialService().requestShareListUpdateWithItemFileList(getAppId(), str, arrayList, r8, pendingIntent, bundle);
                } else {
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("extra_cid", str2);
                    str3 = getSocialService().requestShareListUpdateWithItemFileListWithData(getAppId(), str, arrayList, bundle4, r8, pendingIntent, bundle);
                }
                return new ShareController(this.mApiPicker, str3);
            } catch (RemoteException | NotConnectedException | NullPointerException e) {
                secureLog(e);
                return null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ShareController requestShare(String str, List<ShareApi.SharedItemRequest> list, final ShareApi.ShareResultCallback shareResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        debugLog("requestShare : spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        }
        AnonymousClass15 r8 = new IShareResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass15 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onSuccess ");
                if (shareResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : list) {
                        arrayList.add(ShareApiImpl.this.createSharedItemResult(bundle));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    for (Bundle bundle2 : list2) {
                        arrayList2.add(ShareApiImpl.this.createShareFailedItemResult(bundle2));
                    }
                    shareResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(1), arrayList, arrayList2));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onProgress(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onProgress ");
                if (shareResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    shareResultCallback.onProgress(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onUploadComplete(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                if (shareResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    shareResultCallback.onUploadComplete(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestShare onFailure : code=[" + j + "], message=[" + str + "] ");
                if (shareResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestShare Error Message [" + str + "]");
                    shareResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null, null));
                }
            }
        };
        try {
            ArrayList arrayList = new ArrayList();
            for (ShareApi.SharedItemRequest sharedItemRequest : list) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("title", sharedItemRequest.getTitle());
                bundle2.putString("memo", sharedItemRequest.getMemo());
                bundle2.putString("mime_type", sharedItemRequest.getContentMimeType());
                bundle2.putInt("request_type", 0);
                if (sharedItemRequest instanceof ShareApi.SharedItemWithUriRequest) {
                    ShareApi.SharedItemWithUriRequest sharedItemWithUriRequest = (ShareApi.SharedItemWithUriRequest) sharedItemRequest;
                    bundle2.putString(sharedItemWithUriRequest.isFileUri() ? "content_file_uri" : "content_uri", sharedItemWithUriRequest.getUri() == null ? "" : sharedItemWithUriRequest.getUri().toString());
                    bundle2.putInt("request_type", 0);
                } else if (sharedItemRequest instanceof ShareApi.SharedItemWithSCloudHashRequest) {
                    ShareApi.SharedItemWithSCloudHashRequest sharedItemWithSCloudHashRequest = (ShareApi.SharedItemWithSCloudHashRequest) sharedItemRequest;
                    bundle2.putString("content_hash", sharedItemWithSCloudHashRequest.getHash());
                    bundle2.putLong("file_size", sharedItemWithSCloudHashRequest.getContentSize());
                    bundle2.putString("file_name", sharedItemWithSCloudHashRequest.getContentName());
                    bundle2.putInt("request_type", 1);
                } else if (sharedItemRequest instanceof ShareApi.SharedItemWithMediaServiceContentIdRequest) {
                    ShareApi.SharedItemWithMediaServiceContentIdRequest sharedItemWithMediaServiceContentIdRequest = (ShareApi.SharedItemWithMediaServiceContentIdRequest) sharedItemRequest;
                    bundle2.putString("media_service_content_id", sharedItemWithMediaServiceContentIdRequest.getMediaServiceContentId());
                    bundle2.putLong("file_size", sharedItemWithMediaServiceContentIdRequest.getContentSize());
                    bundle2.putString("file_name", sharedItemWithMediaServiceContentIdRequest.getContentName());
                    bundle2.putInt("request_type", 2);
                }
                bundle2.putSerializable("meta_data", (HashMap) sharedItemRequest.getMetaData());
                arrayList.add(bundle2);
                debugLog("- title=[" + sharedItemRequest.getTitle() + "], memo=[" + sharedItemRequest.getMemo() + "], mime_type=[" + sharedItemRequest.getContentMimeType() + "] ");
            }
            return new ShareController(this.mApiPicker, getSocialService().requestShareWithPendingIntent(getAppId(), str, arrayList, r8, pendingIntent, bundle));
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedContentDownload(String str, String str2, List<String> list, String str3, final ShareApi.ContentDownloadingResultCallback contentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str4) {
        debugLog("requestSharedContentDownload spaceId=[" + str + "] itemId=[" + str2 + "] hash = [" + list + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        } else if (!TextUtils.equals("8o8b82h22a", getAppId())) {
            debugLog("app id is not reminder. this api use only reminder");
            return -1;
        } else {
            AnonymousClass16 r9 = new IContentDownloadingResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass16 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback
                public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                    String str;
                    String str2;
                    String str3;
                    String str4;
                    ShareApiImpl.this.debugLog("requestSharedContentDownload onSuccess ");
                    if (contentDownloadingResultCallback != null) {
                        String str5 = null;
                        if (!list.isEmpty() || !list2.isEmpty()) {
                            ArrayList arrayList = new ArrayList();
                            ArrayList arrayList2 = new ArrayList();
                            Iterator<Bundle> it = list.iterator();
                            while (true) {
                                str = "file_size";
                                str2 = "content_hash";
                                str3 = "item_id";
                                str4 = "space_id";
                                if (!it.hasNext()) {
                                    break;
                                }
                                Bundle next = it.next();
                                String string = next.getString(str4, str5);
                                String string2 = next.getString(str3, str5);
                                String string3 = next.getString(str2, str5);
                                String string4 = next.getString("downloaded_uri", str5);
                                String string5 = next.getString("mime_type", str5);
                                arrayList.add(new ContentDownloadResult.DownloadedContent(string, string2, string3, string4 != null ? Uri.parse(string4) : null, string5, next.getLong(str, -1)));
                                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                                shareApiImpl.debugLog("- successList : space_id=[" + string + "], item_id=[" + string2 + "], downloaded_uri=[" + string4 + "], mime_type=[" + string5 + "], hash_list =[" + string3 + "] ");
                                arrayList2 = arrayList2;
                                str5 = null;
                            }
                            Iterator<Bundle> it2 = list2.iterator();
                            while (it2.hasNext()) {
                                Bundle next2 = it2.next();
                                String string6 = next2.getString(str4, null);
                                String string7 = next2.getString(str3, null);
                                String string8 = next2.getString(str2, null);
                                String string9 = next2.getString("downloaded_uri", null);
                                String string10 = next2.getString("mime_type", null);
                                arrayList2.add(new ContentDownloadResult.DownloadedContent(string6, string7, string8, string9 != null ? Uri.parse(string9) : null, string10, next2.getLong(str, -1)));
                                ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                                shareApiImpl2.debugLog("- failureList : space_id=[" + string6 + "], item_id=[" + string7 + "], downloaded_uri=[" + string9 + "], mime_type=[" + string10 + "] hash_list =[" + string8 + "] ");
                                it2 = it2;
                                str = str;
                                str3 = str3;
                                str2 = str2;
                                str4 = str4;
                            }
                            contentDownloadingResultCallback.onResult(new ContentDownloadResult(new CommonResultStatus(1), arrayList, arrayList2));
                            return;
                        }
                        ShareApiImpl.this.debugLog("requestSharedContentDownload bundle is empty!!");
                        contentDownloadingResultCallback.onResult(new ContentDownloadResult(new CommonResultStatus(1), null, null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback
                public void onProgress(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedContentDownload onProgress ");
                    if (contentDownloadingResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        SharedContentDownloadSnapshot sharedContentDownloadSnapshot = new SharedContentDownloadSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        contentDownloadingResultCallback.onProgress(sharedContentDownloadSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestSharedContentDownload onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (contentDownloadingResultCallback != null) {
                        contentDownloadingResultCallback.onResult(new ContentDownloadResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null, null));
                    }
                }
            };
            try {
                if (!TextUtils.isEmpty(str3)) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("extra_cid", str3);
                    if (TextUtils.isEmpty(str4)) {
                        getSocialService().requestOriginalSharedContentWithFileListDownloadWithData(getAppId(), str, str2, list, bundle2, r9, pendingIntent, bundle);
                        return 1;
                    }
                    getSocialService().requestOriginalSharedContentWithItemFileListDownloadWithPathWithData(getAppId(), str, str2, list, bundle2, r9, pendingIntent, bundle, str4);
                    return 1;
                } else if (TextUtils.isEmpty(str4)) {
                    getSocialService().requestOriginalSharedContentWithFileListDownload(getAppId(), str, str2, list, r9, pendingIntent, bundle);
                    return 1;
                } else {
                    getSocialService().requestOriginalSharedContentWithItemFileListDownloadWithPath(getAppId(), str, str2, list, r9, pendingIntent, bundle, str4);
                    return 1;
                }
            } catch (RemoteException | NullPointerException e) {
                secureLog(e);
                return -1;
            } catch (NotConnectedException e2) {
                secureLog(e2);
                return -8;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ShareController requestShare(String str, ShareApi.SharedItemWithUriListRequest sharedItemWithUriListRequest, final ShareApi.SharedItemWithContentListResultCallback sharedItemWithContentListResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        debugLog("requestShare : spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        } else if (!TextUtils.equals("8o8b82h22a", getAppId())) {
            debugLog("app id is not reminder. this api use only reminder");
            return null;
        } else {
            AnonymousClass17 r8 = new IShareResultWithFileListCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass17 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestShare IShareResultWithAttachedFilesCallback onSuccess ");
                    sharedItemWithContentListResultCallback.onResult(new SharedItemWithUriListResult(new CommonResultStatus(1), new SharedItemWithUriList(bundle)));
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
                public void onProgress(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestShare onProgress ");
                    if (sharedItemWithContentListResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        sharedItemWithContentListResultCallback.onProgress(shareSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
                public void onUploadComplete(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                    if (sharedItemWithContentListResultCallback != null) {
                        long j = bundle.getLong("totalBytes", 0);
                        long j2 = bundle.getLong("totalBytesTransferred", 0);
                        int i = bundle.getInt("totalFileCount", 0);
                        int i2 = bundle.getInt("totalFileCountTransferred", 0);
                        long j3 = bundle.getLong("currentFileBytes", 0);
                        long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                        int i3 = bundle.getInt("currentFileIndex", 0);
                        ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                        sharedItemWithContentListResultCallback.onUploadComplete(shareSnapshot);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestShare onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (sharedItemWithContentListResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                        ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                        shareApiImpl2.debugLog("requestShare Error Message [" + str + "]");
                        sharedItemWithContentListResultCallback.onResult(new SharedItemWithUriListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null));
                    }
                }
            };
            try {
                Bundle bundle2 = new Bundle();
                bundle2.putString("title", sharedItemWithUriListRequest.getTitle());
                bundle2.putString("memo", sharedItemWithUriListRequest.getMemo());
                bundle2.putString("mime_type", sharedItemWithUriListRequest.getContentMimeType());
                bundle2.putInt("request_type", 0);
                bundle2.putSerializable("meta_data", (HashMap) sharedItemWithUriListRequest.getMetaData());
                ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
                for (int i = 0; i < sharedItemWithUriListRequest.getUris().size(); i++) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("content_uri", sharedItemWithUriListRequest.getUris().get(i).toString());
                    bundle3.putString("mime_type", sharedItemWithUriListRequest.getMimeTypeList().get(i));
                    arrayList.add(bundle3);
                }
                bundle2.putParcelableArrayList("share_file_list", arrayList);
                debugLog("- title=[" + sharedItemWithUriListRequest.getTitle() + "], memo=[" + sharedItemWithUriListRequest.getMemo() + "], mime_type=[" + sharedItemWithUriListRequest.getContentMimeType() + "] ");
                return new ShareController(this.mApiPicker, getSocialService().requestShareWithFileList(getAppId(), str, bundle2, r8, pendingIntent, bundle));
            } catch (RemoteException | NotConnectedException | NullPointerException e) {
                secureLog(e);
                return null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedItemDeletion(String str, String str2, final ShareApi.SharedItemDeletionResultCallback sharedItemDeletionResultCallback) {
        debugLog("requestSharedItemDeletion : spaceId=[" + str + "], itemId=[" + str2 + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestSharedItemDeletion(getAppId(), str, str2, new ISharedItemDeletionResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass18 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemDeletionResultCallback
                public void onSuccess() throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemDeletion onSuccess ");
                    ShareApi.SharedItemDeletionResultCallback sharedItemDeletionResultCallback = sharedItemDeletionResultCallback;
                    if (sharedItemDeletionResultCallback != null) {
                        sharedItemDeletionResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemDeletionResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestSharedItemDeletion onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (sharedItemDeletionResultCallback != null) {
                        sharedItemDeletionResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
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

    /* access modifiers changed from: package-private */
    public int requestSharedItemDeletion(String str, List<String> list, final ShareApi.SharedItemDeletionListResultCallback sharedItemDeletionListResultCallback) {
        debugLog("requestSharedItemDeletion : spaceId=[" + str + "], itemId size=" + list.size());
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestSharedItemListDeletion(getAppId(), str, list, new ISharedItemListDeletionResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass19 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListDeletionResultCallback
                public void onSuccess(List<Bundle> list) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemDeletion onSuccess ");
                    if (sharedItemDeletionListResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            arrayList.add(new SharedItemListDeletionResult.SharedItemDeletionResult(bundle.getString("space_id"), bundle.getString("item_id"), bundle.getBoolean("result")));
                        }
                        sharedItemDeletionListResultCallback.onResult(new SharedItemListDeletionResult(new CommonResultStatus(1), arrayList));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListDeletionResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestSharedItemDeletion onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (sharedItemDeletionListResultCallback != null) {
                        sharedItemDeletionListResultCallback.onResult(new SharedItemListDeletionResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), new ArrayList()));
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

    /* access modifiers changed from: package-private */
    public int requestSpaceCoverImageDownload(String str, final ShareApi.ImageDownloadingResultCallback imageDownloadingResultCallback) {
        debugLog("requestOriginalSpaceImageDownload spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestOriginalSpaceImageDownload(getAppId(), str, new ISpaceCoverImageDownloadingResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass20 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceCoverImageDownloadingResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestOriginalSpaceImageDownload onSuccess ");
                    if (imageDownloadingResultCallback != null) {
                        Uri uri = null;
                        String string = bundle.getString("space_id", null);
                        String string2 = bundle.getString("downloaded_uri", null);
                        if (string2 != null) {
                            uri = Uri.parse(string2);
                        }
                        SpaceImageDownloadResult.DownloadedImage downloadedImage = new SpaceImageDownloadResult.DownloadedImage(string, uri);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- space_id=[" + string + "], downloaded_uri=[" + string2 + "] ");
                        imageDownloadingResultCallback.onResult(new SpaceImageDownloadResult(new CommonResultStatus(1), downloadedImage));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISpaceCoverImageDownloadingResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestOriginalSpaceImageDownload onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (imageDownloadingResultCallback != null) {
                        imageDownloadingResultCallback.onResult(new SpaceImageDownloadResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
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

    /* access modifiers changed from: package-private */
    public int requestThumbnailDownload(String str, String str2, String str3, String str4, String str5, final ShareApi.ShareBaseResultCallback<DownloadImageResult> shareBaseResultCallback) {
        debugLog("requestThumbnailDownload. groupId=[" + str + "] spaceId=[" + str2 + "] itemId=[" + str3 + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_11_2)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestThumbnailDownload(getAppId(), str, str2, str3, str4, str5, new IDownloadThumbnailResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass21 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IDownloadThumbnailResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestThumbnailDownload onSuccess ");
                    if (shareBaseResultCallback != null) {
                        Uri uri = null;
                        String string = bundle.getString("item_id", null);
                        String string2 = bundle.getString("content_hash", null);
                        String string3 = bundle.getString("item_thumbnail_local_path", null);
                        if (string3 != null) {
                            uri = Uri.parse(string3);
                        }
                        DownloadImageResult.DownloadedImage downloadedImage = new DownloadImageResult.DownloadedImage(string, string2, uri);
                        ShareApiImpl shareApiImpl = ShareApiImpl.this;
                        shareApiImpl.debugLog("- item_id = [" + string + "], thumbnail local path = [" + string3 + "] ");
                        shareBaseResultCallback.onResult(new DownloadImageResult(new CommonResultStatus(1), downloadedImage));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IDownloadThumbnailResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestThumbnailDownload onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (shareBaseResultCallback != null) {
                        shareBaseResultCallback.onResult(new DownloadImageResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
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

    /* access modifiers changed from: package-private */
    public int requestSharedItemSync(String str, final ShareApi.SharedItemSyncResultCallback sharedItemSyncResultCallback) {
        debugLog("requestSharedItemSync : spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestSharedItemSync(getAppId(), str, new IShareSyncResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass22 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
                public void onSuccess() throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemSync onSuccess ");
                    ShareApi.SharedItemSyncResultCallback sharedItemSyncResultCallback = sharedItemSyncResultCallback;
                    if (sharedItemSyncResultCallback != null) {
                        sharedItemSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareSyncResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestSharedItemSync onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (sharedItemSyncResultCallback != null) {
                        sharedItemSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
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

    /* access modifiers changed from: package-private */
    public ShareController requestSharedItemUpdate(String str, String str2, ShareApi.SharedItemWithUriListRequest sharedItemWithUriListRequest, final ShareApi.SharedItemWithContentListResultCallback sharedItemWithContentListResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        debugLog("requestSpace : spaceId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        }
        AnonymousClass23 r8 = new IShareResultWithFileListCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass23 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpace onSuccess ");
                ShareApi.SharedItemWithContentListResultCallback sharedItemWithContentListResultCallback = sharedItemWithContentListResultCallback;
                if (sharedItemWithContentListResultCallback != null) {
                    sharedItemWithContentListResultCallback.onResult(new SharedItemWithUriListResult(new CommonResultStatus(1), new SharedItemWithUriList(bundle)));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
            public void onProgress(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onProgress ");
                if (sharedItemWithContentListResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    sharedItemWithContentListResultCallback.onProgress(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
            public void onUploadComplete(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                if (sharedItemWithContentListResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    sharedItemWithContentListResultCallback.onUploadComplete(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                if (sharedItemWithContentListResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestSpace Error Message [" + str + "]");
                    sharedItemWithContentListResultCallback.onResult(new SharedItemWithUriListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null));
                }
            }
        };
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", sharedItemWithUriListRequest.getTitle());
        bundle2.putString("memo", sharedItemWithUriListRequest.getMemo());
        bundle2.putString("mime_type", sharedItemWithUriListRequest.getContentMimeType());
        bundle2.putInt("request_type", 0);
        bundle2.putSerializable("meta_data", (HashMap) sharedItemWithUriListRequest.getMetaData());
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (int i = 0; i < sharedItemWithUriListRequest.getUris().size(); i++) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("content_uri", sharedItemWithUriListRequest.getUris().get(i).toString());
            bundle3.putString("mime_type", sharedItemWithUriListRequest.getMimeTypeList().get(i));
            arrayList.add(bundle3);
        }
        bundle2.putParcelableArrayList("share_file_list", arrayList);
        debugLog("- title=[" + sharedItemWithUriListRequest.getTitle() + "], memo=[" + sharedItemWithUriListRequest.getMemo() + "], mime_type=[" + sharedItemWithUriListRequest.getContentMimeType() + "] ");
        try {
            return new ShareController(this.mApiPicker, getSocialService().requestShareUpdateWithUriList(getAppId(), str, str2, bundle2, r8, pendingIntent, bundle));
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public ShareController requestSharedItemUpdate(String str, List<ShareApi.SharedItemRequest> list, final ShareApi.ShareUploadResultCallback<SharedItemListResult> shareUploadResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        debugLog("requestSpace : spaceId=[" + str + "], request size =[" + list.size() + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        }
        AnonymousClass24 r8 = new IShareResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass24 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSharedItemUpdate onSuccess ");
                if (shareUploadResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : list) {
                        arrayList.add(ShareApiImpl.this.createSharedItemResult(bundle));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    for (Bundle bundle2 : list2) {
                        arrayList2.add(ShareApiImpl.this.createShareFailedItemResult(bundle2));
                    }
                    shareUploadResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(1), arrayList, arrayList2));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onProgress(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onProgress ");
                if (shareUploadResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    shareUploadResultCallback.onProgress(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onUploadComplete(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                if (shareUploadResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    shareUploadResultCallback.onUploadComplete(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                if (shareUploadResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestShare Error Message [" + str + "]");
                    shareUploadResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null, null));
                }
            }
        };
        try {
            ArrayList arrayList = new ArrayList();
            for (ShareApi.SharedItemRequest sharedItemRequest : list) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("item_id", sharedItemRequest.getItemId());
                bundle2.putString("title", sharedItemRequest.getTitle());
                bundle2.putString("memo", sharedItemRequest.getMemo());
                bundle2.putString("mime_type", sharedItemRequest.getContentMimeType());
                bundle2.putInt("request_type", 0);
                if (sharedItemRequest instanceof ShareApi.SharedItemWithUriRequest) {
                    ShareApi.SharedItemWithUriRequest sharedItemWithUriRequest = (ShareApi.SharedItemWithUriRequest) sharedItemRequest;
                    bundle2.putString(sharedItemWithUriRequest.isFileUri() ? "content_file_uri" : "content_uri", sharedItemWithUriRequest.getUri() == null ? "" : sharedItemWithUriRequest.getUri().toString());
                    bundle2.putInt("request_type", 0);
                } else if (sharedItemRequest instanceof ShareApi.SharedItemWithSCloudHashRequest) {
                    ShareApi.SharedItemWithSCloudHashRequest sharedItemWithSCloudHashRequest = (ShareApi.SharedItemWithSCloudHashRequest) sharedItemRequest;
                    bundle2.putString("content_hash", sharedItemWithSCloudHashRequest.getHash());
                    bundle2.putLong("file_size", sharedItemWithSCloudHashRequest.getContentSize());
                    bundle2.putString("file_name", sharedItemWithSCloudHashRequest.getContentName());
                    bundle2.putInt("request_type", 1);
                } else if (sharedItemRequest instanceof ShareApi.SharedItemWithMediaServiceContentIdRequest) {
                    ShareApi.SharedItemWithMediaServiceContentIdRequest sharedItemWithMediaServiceContentIdRequest = (ShareApi.SharedItemWithMediaServiceContentIdRequest) sharedItemRequest;
                    bundle2.putString("media_service_content_id", sharedItemWithMediaServiceContentIdRequest.getMediaServiceContentId());
                    bundle2.putLong("file_size", sharedItemWithMediaServiceContentIdRequest.getContentSize());
                    bundle2.putString("file_name", sharedItemWithMediaServiceContentIdRequest.getContentName());
                    bundle2.putInt("request_type", 2);
                }
                bundle2.putSerializable("meta_data", (HashMap) sharedItemRequest.getMetaData());
                debugLog("- title=[" + sharedItemRequest.getTitle() + "], itemId=[" + sharedItemRequest.getItemId() + "], memo=[" + sharedItemRequest.getMemo() + "], mime_type=[" + sharedItemRequest.getContentMimeType() + "] ");
                arrayList.add(bundle2);
            }
            return new ShareController(this.mApiPicker, getSocialService().requestSharedItemListUpdate(getAppId(), str, arrayList, r8, pendingIntent, bundle));
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public ShareController requestSharedItemUpdate(String str, String str2, ShareApi.SharedItemRequest sharedItemRequest, final ShareApi.SharedItemUpdateResultCallback sharedItemUpdateResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        debugLog("requestSpace : spaceId=[" + str + "], itemId=[" + str2 + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return null;
        }
        AnonymousClass25 r9 = new ISharedItemUpdateResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass25 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemUpdateResultCallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpace onSuccess ");
                ShareApi.SharedItemUpdateResultCallback sharedItemUpdateResultCallback = sharedItemUpdateResultCallback;
                if (sharedItemUpdateResultCallback != null) {
                    sharedItemUpdateResultCallback.onResult(new SharedItemResult(new CommonResultStatus(1), ShareApiImpl.this.createSharedItemResult(bundle)));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemUpdateResultCallback
            public void onProgress(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onProgress ");
                if (sharedItemUpdateResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    sharedItemUpdateResultCallback.onProgress(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemUpdateResultCallback
            public void onUploadComplete(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                if (sharedItemUpdateResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    sharedItemUpdateResultCallback.onUploadComplete(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemUpdateResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                if (sharedItemUpdateResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestSpace Error Message [" + str + "]");
                    sharedItemUpdateResultCallback.onResult(new SharedItemResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null));
                }
            }
        };
        Bundle bundle2 = new Bundle();
        bundle2.putString("title", sharedItemRequest.getTitle());
        bundle2.putString("memo", sharedItemRequest.getMemo());
        bundle2.putString("mime_type", sharedItemRequest.getContentMimeType());
        bundle2.putInt("request_type", 0);
        if (sharedItemRequest instanceof ShareApi.SharedItemWithUriRequest) {
            ShareApi.SharedItemWithUriRequest sharedItemWithUriRequest = (ShareApi.SharedItemWithUriRequest) sharedItemRequest;
            bundle2.putString(sharedItemWithUriRequest.isFileUri() ? "content_file_uri" : "content_uri", sharedItemWithUriRequest.getUri() == null ? "" : sharedItemWithUriRequest.getUri().toString());
            bundle2.putInt("request_type", 0);
        } else if (sharedItemRequest instanceof ShareApi.SharedItemWithSCloudHashRequest) {
            ShareApi.SharedItemWithSCloudHashRequest sharedItemWithSCloudHashRequest = (ShareApi.SharedItemWithSCloudHashRequest) sharedItemRequest;
            bundle2.putString("content_hash", sharedItemWithSCloudHashRequest.getHash());
            bundle2.putLong("file_size", sharedItemWithSCloudHashRequest.getContentSize());
            bundle2.putString("file_name", sharedItemWithSCloudHashRequest.getContentName());
            bundle2.putInt("request_type", 1);
        } else if (sharedItemRequest instanceof ShareApi.SharedItemWithMediaServiceContentIdRequest) {
            ShareApi.SharedItemWithMediaServiceContentIdRequest sharedItemWithMediaServiceContentIdRequest = (ShareApi.SharedItemWithMediaServiceContentIdRequest) sharedItemRequest;
            bundle2.putString("media_service_content_id", sharedItemWithMediaServiceContentIdRequest.getMediaServiceContentId());
            bundle2.putLong("file_size", sharedItemWithMediaServiceContentIdRequest.getContentSize());
            bundle2.putString("file_name", sharedItemWithMediaServiceContentIdRequest.getContentName());
            bundle2.putInt("request_type", 2);
        }
        bundle2.putSerializable("meta_data", (HashMap) sharedItemRequest.getMetaData());
        debugLog("- title=[" + sharedItemRequest.getTitle() + "], memo=[" + sharedItemRequest.getMemo() + "], mime_type=[" + sharedItemRequest.getContentMimeType() + "] ");
        try {
            return new ShareController(this.mApiPicker, getSocialService().requestSharedItemUpdate(getAppId(), str, str2, bundle2, r9, pendingIntent, bundle));
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestInstantShare(GroupApi.InvitationRequest invitationRequest, List<ShareApi.SharedItemRequest> list, final ShareApi.ShareResultCallback shareResultCallback) {
        debugLog("requestInstantShare");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        AnonymousClass26 r1 = new IShareResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass26 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onSuccess(List<Bundle> list, List<Bundle> list2) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onSuccess ");
                if (shareResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : list) {
                        arrayList.add(ShareApiImpl.this.createSharedItemResult(bundle));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    for (Bundle bundle2 : list2) {
                        arrayList2.add(ShareApiImpl.this.createShareFailedItemResult(bundle2));
                    }
                    shareResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(1), arrayList, arrayList2));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onProgress(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onProgress ");
                if (shareResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    shareResultCallback.onProgress(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onUploadComplete(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestShare onUploadComplete ");
                if (shareResultCallback != null) {
                    long j = bundle.getLong("totalBytes", 0);
                    long j2 = bundle.getLong("totalBytesTransferred", 0);
                    int i = bundle.getInt("totalFileCount", 0);
                    int i2 = bundle.getInt("totalFileCountTransferred", 0);
                    long j3 = bundle.getLong("currentFileBytes", 0);
                    long j4 = bundle.getLong("currentFileBytesTransferred", 0);
                    int i3 = bundle.getInt("currentFileIndex", 0);
                    ShareSnapshot shareSnapshot = new ShareSnapshot(j, j2, i, i2, j3, j4, i3);
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("- totalBytes=[" + j + "], totalBytesTransferred=[" + j2 + "], totalFileCount=[" + i + "], totalFileCountTransferred=[" + i2 + "], currentFileBytes=[" + j3 + "], currentFileBytesTransferred=[" + j4 + "], currentFileIndex=[" + i3 + "] ");
                    shareResultCallback.onUploadComplete(shareSnapshot);
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestShare onFailure : code=[" + j + "], message=[" + str + "] ");
                if (shareResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestShare Error Message [" + str + "]");
                    shareResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null, null));
                }
            }
        };
        Bundle bundle = new Bundle();
        bundle.putInt("invitation_type", invitationRequest.getIdType());
        bundle.putString("invitation_message", invitationRequest.getInvitationMessage());
        bundle.putStringArrayList("id", new ArrayList<>(invitationRequest.getIds()));
        bundle.putStringArrayList("optionalId", new ArrayList<>(invitationRequest.getOptionalIds()));
        ArrayList arrayList = new ArrayList();
        try {
            for (ShareApi.SharedItemRequest sharedItemRequest : list) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("title", sharedItemRequest.getTitle());
                bundle2.putString("memo", sharedItemRequest.getMemo());
                bundle2.putString("mime_type", sharedItemRequest.getContentMimeType());
                bundle2.putInt("request_type", 0);
                if (sharedItemRequest instanceof ShareApi.SharedItemWithUriRequest) {
                    ShareApi.SharedItemWithUriRequest sharedItemWithUriRequest = (ShareApi.SharedItemWithUriRequest) sharedItemRequest;
                    bundle2.putString(sharedItemWithUriRequest.isFileUri() ? "content_file_uri" : "content_uri", sharedItemWithUriRequest.getUri() == null ? "" : sharedItemWithUriRequest.getUri().toString());
                    bundle2.putInt("request_type", 0);
                } else if (sharedItemRequest instanceof ShareApi.SharedItemWithDataRequest) {
                    ShareApi.SharedItemWithDataRequest sharedItemWithDataRequest = (ShareApi.SharedItemWithDataRequest) sharedItemRequest;
                    bundle2.putInt("request_type", 3);
                }
                bundle2.putSerializable("meta_data", (HashMap) sharedItemRequest.getMetaData());
                arrayList.add(bundle2);
                debugLog("- title=[" + sharedItemRequest.getTitle() + "], memo=[" + sharedItemRequest.getMemo() + "], mime_type=[" + sharedItemRequest.getContentMimeType() + "] ");
            }
            getSocialService().requestInstantShare(getAppId(), bundle, arrayList, r1);
            return 1;
        } catch (RemoteException | NotConnectedException | NullPointerException e) {
            secureLog(e);
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedItemList(String str, String str2, String str3, String str4, final ShareApi.SharedItemListWithContentListResultCallback sharedItemListWithContentListResultCallback) {
        debugLog("requestSharedItemList : groupId=[" + str + "], spaceId=[" + str2 + "], resolution=[" + str3 + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass27 r8 = new ISharedItemListResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass27 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListResultCallback
            public void onSuccess(List<Bundle> list) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSharedItemList(with content list) onSuccess");
                if (sharedItemListWithContentListResultCallback != null) {
                    ArrayList arrayList = new ArrayList();
                    for (Bundle bundle : list) {
                        arrayList.add(new SharedItemWithUriList(bundle));
                    }
                    sharedItemListWithContentListResultCallback.onResult(new SharedItemListWithContentListResult(new CommonResultStatus(1), arrayList));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                if (sharedItemListWithContentListResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestSpace Error Message [" + str + "]");
                    sharedItemListWithContentListResultCallback.onResult(new SharedItemListWithContentListResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str4)) {
                getSocialService().requestSharedItemListWithFileList(getAppId(), str, str2, str3, r8);
                return 1;
            }
            Bundle bundle = new Bundle();
            bundle.putString("extra_cid", str4);
            getSocialService().requestSharedItemListWithFileListWithData(getAppId(), str, str2, str3, bundle, r8);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: package-private */
    public int requestSharedItemList(String str, String str2, String str3, final ShareApi.SharedItemListResultCallback sharedItemListResultCallback) {
        debugLog("requestSharedItemList : groupId=[" + str + "], spaceId=[" + str2 + "], resolution=[" + str3 + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestSharedItemList(getAppId(), str, str2, str3, new ISharedItemListResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass28 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListResultCallback
                public void onSuccess(List<Bundle> list) throws RemoteException {
                    ShareApiImpl.this.debugLog("requestSharedItemList(without content list) onSuccess ");
                    if (sharedItemListResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            arrayList.add(ShareApiImpl.this.createSharedItemResult(bundle));
                        }
                        sharedItemListResultCallback.onResult(new SharedItemListResult(new CommonResultStatus(1), arrayList, null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemListResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ShareApiImpl shareApiImpl = ShareApiImpl.this;
                    shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (sharedItemListResultCallback != null) {
                        ErrorCodeConvertor.convertErrorcode(j);
                        ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                        shareApiImpl2.debugLog("requestSpace Error Message [" + str + "]");
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

    /* access modifiers changed from: package-private */
    public int requestSharedItem(String str, String str2, String str3, final ShareApi.SharedItemResultCallback sharedItemResultCallback) {
        debugLog("requestSpace : spaceId=[" + str2 + "], itemId=[" + str3 + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        AnonymousClass29 r7 = new ISharedItemResultCallback.Stub() {
            /* class com.samsung.android.sdk.mobileservice.social.share.ShareApiImpl.AnonymousClass29 */

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemResultCallback
            public void onSuccess(Bundle bundle) throws RemoteException {
                ShareApiImpl.this.debugLog("requestSpace onSuccess ");
                ShareApi.SharedItemResultCallback sharedItemResultCallback = sharedItemResultCallback;
                if (sharedItemResultCallback != null) {
                    sharedItemResultCallback.onResult(new SharedItemResult(new CommonResultStatus(1), ShareApiImpl.this.createSharedItemResult(bundle)));
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.share.ISharedItemResultCallback
            public void onFailure(long j, String str) throws RemoteException {
                ShareApiImpl shareApiImpl = ShareApiImpl.this;
                shareApiImpl.debugLog("requestSpace onFailure : code=[" + j + "], message=[" + str + "] ");
                if (sharedItemResultCallback != null) {
                    int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                    ShareApiImpl shareApiImpl2 = ShareApiImpl.this;
                    shareApiImpl2.debugLog("requestSpace Error Message [" + str + "]");
                    sharedItemResultCallback.onResult(new SharedItemResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), null));
                }
            }
        };
        try {
            if (TextUtils.isEmpty(str)) {
                getSocialService().requestSharedItem(getAppId(), str2, str3, r7);
                return 1;
            }
            getSocialService().requestSharedItemWithGroupId(getAppId(), str, str2, str3, r7);
            return 1;
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return -1;
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return -8;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Space createSpaceResult(Bundle bundle) {
        String string = bundle.getString("space_id", null);
        String string2 = bundle.getString("group_id", null);
        String string3 = bundle.getString("owner_id", null);
        Space space = new Space(string2, string, string3);
        space.setTitle(bundle.getString("title", ""));
        space.setMemo(bundle.getString("memo", ""));
        space.setCreatedTime(bundle.getLong("created_time", 0));
        space.setModifiedTime(bundle.getLong("modified_time", 0));
        String string4 = bundle.getString("thumbnail_uri", null);
        if (string4 != null) {
            space.setCoverThumbnailFileUri(Uri.parse(string4));
        }
        space.setSourceCid(bundle.getString(SharedItemContract.Item.SOURCE_CID, null));
        space.setUnreadCount(bundle.getInt("uread_count", 0));
        space.setUnreadCount(bundle.getInt("item_count", 0));
        space.setOwnedByMe(bundle.getBoolean("is_owned_by_me", false));
        space.setMetaData((HashMap) bundle.getSerializable("meta_data"));
        space.setSize(bundle.getLong("file_size", 0));
        space.setContentUpdatedTime(bundle.getLong("contents_update_time", 0));
        debugLog("- spaceId=[" + string + "], groupId=[" + string2 + "], ownerId=[" + string3 + "], title=[" + space.getTitle() + "], memo=[" + space.getMemo() + "], coverImageUri=[" + string4 + " ]");
        return space;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private ShareApi.SharedItemWithUriListRequest createShareFailedItemWithUriListResult(Bundle bundle) {
        ShareApi.SharedItemWithUriListRequest sharedItemWithUriListRequest = new ShareApi.SharedItemWithUriListRequest(bundle.getString("title", ""));
        sharedItemWithUriListRequest.setMemo(bundle.getString("memo", ""));
        sharedItemWithUriListRequest.setMetaData((Map) bundle.getSerializable("meta_data"));
        ArrayList<Bundle> parcelableArrayList = bundle.getParcelableArrayList("share_file_list");
        if (parcelableArrayList != null) {
            for (Bundle bundle2 : parcelableArrayList) {
                String string = bundle2.getString("content_uri", "");
                sharedItemWithUriListRequest.addUri(Uri.parse(string), bundle2.getString("mime_type", ""));
            }
        }
        return sharedItemWithUriListRequest;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private SharedItem createSharedItemResult(Bundle bundle) {
        String string = bundle.getString("item_id");
        String string2 = bundle.getString("space_id");
        String string3 = bundle.getString("owner_id");
        SharedItem sharedItem = new SharedItem(string, string2, string3);
        sharedItem.setTitle(bundle.getString("title", ""));
        sharedItem.setMemo(bundle.getString("memo", ""));
        sharedItem.setCreatedTime(bundle.getLong("created_time", 0));
        sharedItem.setModifiedTime(bundle.getLong("modified_time", 0));
        sharedItem.setMimeType(bundle.getString("mime_type", ""));
        String string4 = bundle.getString("thumbnail_uri", "");
        if (string4 != null) {
            sharedItem.setThumbnailFileUri(Uri.parse(string4));
        }
        sharedItem.setOwnedByMe(bundle.getBoolean("is_owned_by_me", false));
        sharedItem.setMetaData((HashMap) bundle.getSerializable("meta_data"));
        sharedItem.setSize(bundle.getLong("file_size", 0));
        sharedItem.setOriginalContentPath(bundle.getString(SharedItemContract.Item.CONTENT_LOCAL_PATH, ""));
        sharedItem.setStreamingUrl(bundle.getString("streaming_url", ""));
        sharedItem.setSourceCid(bundle.getString(SharedItemContract.Item.SOURCE_CID, ""));
        debugLog("- itemId=[" + string + "], spaceId=[" + string2 + "], ownerId=[" + string3 + "], title=[" + sharedItem.getTitle() + "], memo=[" + sharedItem.getMemo() + "], createdTime=[" + sharedItem.getCreatedTime() + "], modifiedTime=[" + sharedItem.getModifiedTime() + "], mimeType=[" + sharedItem.getMimeType() + "], thumbnailUri=[" + string4 + "]");
        return sharedItem;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v4, resolved type: com.samsung.android.sdk.mobileservice.social.share.ShareApi$SharedItemWithSCloudHashRequest */
    /* JADX DEBUG: Multi-variable search result rejected for r3v5, resolved type: com.samsung.android.sdk.mobileservice.social.share.ShareApi$SharedItemWithMediaServiceContentIdRequest */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private ShareApi.SharedItemRequest createShareFailedItemResult(Bundle bundle) {
        String string = bundle.getString("title", "");
        int i = bundle.getInt("request_type", -1);
        ShareApi.SharedItemWithUriRequest sharedItemWithUriRequest = null;
        if (i == 0) {
            ShareApi.SharedItemWithUriRequest sharedItemWithUriRequest2 = new ShareApi.SharedItemWithUriRequest(string);
            sharedItemWithUriRequest2.setUri(Uri.parse(bundle.getString("content_uri", null)));
            sharedItemWithUriRequest = sharedItemWithUriRequest2;
        } else if (i == 1) {
            ShareApi.SharedItemWithSCloudHashRequest sharedItemWithSCloudHashRequest = new ShareApi.SharedItemWithSCloudHashRequest(string);
            sharedItemWithSCloudHashRequest.setHash(bundle.getString("content_hash", ""));
            sharedItemWithSCloudHashRequest.setContentSize(bundle.getLong("file_size", -1));
            sharedItemWithSCloudHashRequest.setContentName(bundle.getString("file_name", ""));
            sharedItemWithUriRequest = sharedItemWithSCloudHashRequest;
        } else if (i == 2) {
            ShareApi.SharedItemWithMediaServiceContentIdRequest sharedItemWithMediaServiceContentIdRequest = new ShareApi.SharedItemWithMediaServiceContentIdRequest(string);
            sharedItemWithMediaServiceContentIdRequest.setMediaServiceContentId(bundle.getString("media_service_content_id", ""));
            sharedItemWithMediaServiceContentIdRequest.setContentSize(bundle.getLong("file_size", -1));
            sharedItemWithMediaServiceContentIdRequest.setContentName(bundle.getString("file_name", ""));
            sharedItemWithUriRequest = sharedItemWithMediaServiceContentIdRequest;
        }
        if (sharedItemWithUriRequest != null) {
            sharedItemWithUriRequest.setMemo(bundle.getString("memo", ""));
            sharedItemWithUriRequest.setContentMimeType(bundle.getString("mime_type", ""));
            sharedItemWithUriRequest.setMetaData((HashMap) bundle.getSerializable("meta_data"));
            debugLog(" request tyep=[" + i + "], title=[" + sharedItemWithUriRequest.getTitle() + "], memo=[" + sharedItemWithUriRequest.getMemo() + "], mimeType=[" + sharedItemWithUriRequest.getContentMimeType() + "] ");
        }
        return sharedItemWithUriRequest;
    }
}
