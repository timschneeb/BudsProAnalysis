package com.samsung.android.sdk.mobileservice.social.share;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.BooleanResult;
import com.samsung.android.sdk.mobileservice.social.group.GroupApi;
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
import java.util.List;
import java.util.Map;

public class ShareApi {
    public static final String API_NAME = "ShareApi";
    public static final String EXTRA_DOWNLOAD_NOTI_ALL_ITEMS_DOWNLOADED = "multi_downloaded";
    public static final String EXTRA_DOWNLOAD_NOTI_ALL_ITEMS_DOWNLOAD_FAILED = "download_multi_failed";
    public static final String EXTRA_DOWNLOAD_NOTI_GROUP_STATUS_CHANGED = "group_status_changed";
    public static final String EXTRA_DOWNLOAD_NOTI_ITEMS_DELETED = "items_deleted";
    public static final String EXTRA_DOWNLOAD_NOTI_ITEMS_DOWNLOADED_WITH_ITEMS_FAILED = "multi_download_multi_failed";
    public static final String EXTRA_DOWNLOAD_NOTI_ITEMS_DOWNLOADED_WITH_ONE_ITEM_FAILED = "multi_download_1_failed";
    public static final String EXTRA_DOWNLOAD_NOTI_ITEMS_DOWNLOADING = "downloading_multi";
    public static final String EXTRA_DOWNLOAD_NOTI_ITEMS_DOWNLOAD_PREPARING = "preparing_download_multi";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_ITEM_DELETED = "item_deleted";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_ITEM_DOWNLOADED = "1_downloaded";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_ITEM_DOWNLOADED_WITH_ITEMS_FAILED = "1_download_multi_failed";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_ITEM_DOWNLOADING = "downloading_1";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_ITEM_DOWNLOAD_FAILED = "download_1_failed";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_ITEM_DOWNLOAD_PREPARING = "preparing_download_1";
    public static final String EXTRA_DOWNLOAD_NOTI_ONE_SPACE_DELETED = "space_deleted";
    public static final String EXTRA_DOWNLOAD_NOTI_SPACES_DELETED = "spaces_deleted";
    public static final String EXTRA_SHARE_NOTI_ALL_ITEMS_UPLOADED = "multi_uploaded";
    public static final String EXTRA_SHARE_NOTI_ALL_ITEMS_UPLOAD_FAILED = "upload_multi_failed";
    public static final String EXTRA_SHARE_NOTI_GROUP_STATUS_CHANGED = "group_status_changed";
    public static final String EXTRA_SHARE_NOTI_ITEMS_DELETED = "items_deleted";
    public static final String EXTRA_SHARE_NOTI_ITEMS_UPLOADED_WITH_ITEMS_FAILED = "multi_uploaded_multi_failed";
    public static final String EXTRA_SHARE_NOTI_ITEMS_UPLOADED_WITH_ONE_ITEM_FAILED = "multi_uploaded_1_failed";
    public static final String EXTRA_SHARE_NOTI_ITEMS_UPLOADING = "uploading_multi";
    public static final String EXTRA_SHARE_NOTI_ITEMS_UPLOAD_PREPARING = "preparing_upload_multi";
    public static final String EXTRA_SHARE_NOTI_ONE_ITEM_DELETED = "item_deleted";
    public static final String EXTRA_SHARE_NOTI_ONE_ITEM_UPLOADED = "1_uploaded";
    public static final String EXTRA_SHARE_NOTI_ONE_ITEM_UPLOADED_WITH_ITEMS_FAILED = "1_uploaded_multi_failed";
    public static final String EXTRA_SHARE_NOTI_ONE_ITEM_UPLOADING = "uploading_1";
    public static final String EXTRA_SHARE_NOTI_ONE_ITEM_UPLOAD_FAILED = "upload_failed";
    public static final String EXTRA_SHARE_NOTI_ONE_ITEM_UPLOAD_PREPARING = "preparing_upload_1";
    public static final String EXTRA_SHARE_NOTI_ONE_SPACE_DELETED = "space_deleted";
    public static final String EXTRA_SHARE_NOTI_SPACES_DELETED = "spaces_deleted";
    public static final String HD_1280_SIZE_IMAGE = "hd";
    public static final String HQVGA_240_SIZE_IMAGE = "low";
    public static final String ORIGINAL_SIZE_IMAGE = "original";
    private static final String TAG = "ShareApi";
    public static final String WQHD_2560_SIZE_IMAGE = "wqhd";
    private String mCid;
    private ShareApiImpl mShareApiImpl;

    public interface ContentDownloadingResultCallback {
        void onProgress(SharedContentDownloadSnapshot sharedContentDownloadSnapshot);

        void onResult(ContentDownloadResult contentDownloadResult);
    }

    public interface ImageDownloadingResultCallback {
        void onResult(SpaceImageDownloadResult spaceImageDownloadResult);
    }

    public interface ShareBaseResultCallback<T> {
        void onResult(T t);
    }

    public interface ShareResultCallback {
        void onProgress(ShareSnapshot shareSnapshot);

        void onResult(SharedItemListResult sharedItemListResult);

        void onUploadComplete(ShareSnapshot shareSnapshot);
    }

    public interface ShareSyncResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface ShareUploadResultCallback<T> {
        void onProgress(ShareSnapshot shareSnapshot);

        void onResult(T t);

        void onUploadComplete(ShareSnapshot shareSnapshot);
    }

    public interface SharedItemDeletionListResultCallback {
        void onResult(SharedItemListDeletionResult sharedItemListDeletionResult);
    }

    public interface SharedItemDeletionResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface SharedItemListResultCallback {
        void onResult(SharedItemListResult sharedItemListResult);
    }

    public interface SharedItemListWithContentListResultCallback {
        void onResult(SharedItemListWithContentListResult sharedItemListWithContentListResult);
    }

    public interface SharedItemResultCallback {
        void onResult(SharedItemResult sharedItemResult);
    }

    public interface SharedItemSyncResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface SharedItemUpdateResultCallback {
        void onProgress(ShareSnapshot shareSnapshot);

        void onResult(SharedItemResult sharedItemResult);

        void onUploadComplete(ShareSnapshot shareSnapshot);
    }

    public interface SharedItemWithContentListResultCallback {
        void onProgress(ShareSnapshot shareSnapshot);

        void onResult(SharedItemWithUriListResult sharedItemWithUriListResult);

        void onUploadComplete(ShareSnapshot shareSnapshot);
    }

    public interface SpaceDeletionResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface SpaceListResultCallback {
        void onResult(SpaceListResult spaceListResult);
    }

    public interface SpaceListSyncResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface SpaceResultCallback {
        void onResult(SpaceResult spaceResult);
    }

    public ShareApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        this.mCid = null;
        this.mShareApiImpl = new ShareApiImpl(seMobileServiceSession);
    }

    public ShareApi(SeMobileServiceSession seMobileServiceSession, String str) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        this(seMobileServiceSession);
        this.mCid = str;
    }

    public int requestSync(ShareSyncResultCallback shareSyncResultCallback) {
        return this.mShareApiImpl.requestSync(this.mCid, shareSyncResultCallback);
    }

    public int requestSpaceListSync(SpaceListSyncResultCallback spaceListSyncResultCallback) {
        return this.mShareApiImpl.requestSpaceListSync(this.mCid, spaceListSyncResultCallback);
    }

    public int requestSharedItemSync(String str, String str2, SharedItemSyncResultCallback sharedItemSyncResultCallback) {
        return this.mShareApiImpl.requestSharedItemSync(str, str2, this.mCid, sharedItemSyncResultCallback);
    }

    public int requestSpaceCreation(String str, SpaceRequest spaceRequest, SpaceResultCallback spaceResultCallback) {
        return this.mShareApiImpl.requestSpaceCreation(str, spaceRequest, this.mCid, spaceResultCallback);
    }

    public int requestSpaceList(String str, SpaceListResultCallback spaceListResultCallback) {
        return this.mShareApiImpl.requestSpaceList(str, null, spaceListResultCallback);
    }

    public int requestSpaceList(SpaceListResultCallback spaceListResultCallback) {
        return this.mShareApiImpl.requestSpaceList(this.mCid, spaceListResultCallback);
    }

    public int requestSpace(String str, SpaceResultCallback spaceResultCallback) {
        return this.mShareApiImpl.requestSpace(str, this.mCid, spaceResultCallback);
    }

    public int requestSpaceUpdate(String str, Map map, SpaceResultCallback spaceResultCallback) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("meta_data", (HashMap) map);
        return this.mShareApiImpl.requestSpaceUpdate(str, bundle, this.mCid, spaceResultCallback);
    }

    public int requestSpaceUpdate(String str, String str2, String str3, Uri uri, String str4, Map map, SpaceResultCallback spaceResultCallback) {
        String str5;
        Bundle bundle = new Bundle();
        bundle.putString("title", str2);
        bundle.putString("memo", str3);
        if (uri == null) {
            str5 = "";
        } else {
            str5 = uri.toString();
        }
        bundle.putString("content_uri", str5);
        bundle.putString("mime_type", str4);
        bundle.putSerializable("meta_data", (HashMap) map);
        return this.mShareApiImpl.requestSpaceUpdate(str, bundle, this.mCid, spaceResultCallback);
    }

    public int requestSpaceUpdate(String str, String str2, String str3, SpaceResultCallback spaceResultCallback) {
        Bundle bundle = new Bundle();
        bundle.putString("title", str2);
        bundle.putString("memo", str3);
        return this.mShareApiImpl.requestSpaceUpdate(str, bundle, this.mCid, spaceResultCallback);
    }

    public int requestSpaceUpdate(String str, Uri uri, String str2, SpaceResultCallback spaceResultCallback) {
        String str3;
        Bundle bundle = new Bundle();
        if (uri == null) {
            str3 = "";
        } else {
            str3 = uri.toString();
        }
        bundle.putString("content_uri", str3);
        bundle.putString("mime_type", str2);
        return this.mShareApiImpl.requestSpaceUpdate(str, bundle, this.mCid, spaceResultCallback);
    }

    public int requestSpaceUpdate(String str, String str2, String str3, String str4, long j, SpaceResultCallback spaceResultCallback) {
        Bundle bundle = new Bundle();
        bundle.putString("content_hash", str2);
        bundle.putString("mime_type", str3);
        bundle.putString("file_name", str4);
        bundle.putLong("file_size", j);
        return this.mShareApiImpl.requestSpaceUpdate(str, bundle, this.mCid, spaceResultCallback);
    }

    public int requestSpaceDeletion(String str, SpaceDeletionResultCallback spaceDeletionResultCallback) {
        return this.mShareApiImpl.requestSpaceDeletion(str, this.mCid, spaceDeletionResultCallback);
    }

    public ShareController requestShare(String str, List<SharedItemRequest> list, ShareResultCallback shareResultCallback) {
        return this.mShareApiImpl.requestShare(str, list, shareResultCallback, (PendingIntent) null, (Bundle) null);
    }

    public ShareController requestShare(String str, List<SharedItemRequest> list, ShareResultCallback shareResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestShare(str, list, shareResultCallback, pendingIntent, bundle);
    }

    public ShareController requestShare(String str, List<SharedItemWithUriListRequest> list, ShareUploadResultCallback<SharedItemListWithUriListResult> shareUploadResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestShare(str, list, shareUploadResultCallback, pendingIntent, bundle, this.mCid);
    }

    public int requestSharedItemDeletion(String str, List<String> list, ShareBaseResultCallback<ItemListResult> shareBaseResultCallback) {
        return this.mShareApiImpl.requestSharedItemDeletion(str, list, this.mCid, shareBaseResultCallback);
    }

    public int requestSpaceCoverImageDownload(String str, ImageDownloadingResultCallback imageDownloadingResultCallback) {
        return this.mShareApiImpl.requestSpaceCoverImageDownload(str, imageDownloadingResultCallback);
    }

    public int requestSharedContentDownload(String str, List<String> list, ContentDownloadingResultCallback contentDownloadingResultCallback) {
        return this.mShareApiImpl.requestSharedContentDownload(str, list, contentDownloadingResultCallback, null, null, null);
    }

    public int requestSharedContentDownload(String str, List<String> list, ContentDownloadingResultCallback contentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str2) {
        return this.mShareApiImpl.requestSharedContentDownload(str, list, contentDownloadingResultCallback, pendingIntent, bundle, str2);
    }

    public int requestSharedContentDownload(String str, String str2, List<String> list, ContentDownloadingResultCallback contentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str3) {
        return this.mShareApiImpl.requestSharedContentDownload(str, str2, list, null, contentDownloadingResultCallback, pendingIntent, bundle, str3);
    }

    public void clearUnreadCount(String str) {
        this.mShareApiImpl.clearUnreadCount(str, this.mCid);
    }

    public int requestSharedItem(String str, String str2, SharedItemResultCallback sharedItemResultCallback) {
        return requestSharedItem(null, str, str2, sharedItemResultCallback);
    }

    public int requestSharedItem(String str, String str2, String str3, SharedItemResultCallback sharedItemResultCallback) {
        return this.mShareApiImpl.requestSharedItem(str, str2, str3, sharedItemResultCallback);
    }

    public int requestSharedItemList(String str, String str2, String str3, SharedItemListResultCallback sharedItemListResultCallback) {
        return this.mShareApiImpl.requestSharedItemList(str, str2, str3, sharedItemListResultCallback);
    }

    public int requestSharedItemList(String str, String str2, String str3, SharedItemListWithContentListResultCallback sharedItemListWithContentListResultCallback) {
        return this.mShareApiImpl.requestSharedItemList(str, str2, str3, this.mCid, sharedItemListWithContentListResultCallback);
    }

    public int requestInstantShare(GroupApi.InvitationRequest invitationRequest, List<SharedItemRequest> list, ShareResultCallback shareResultCallback) {
        return this.mShareApiImpl.requestInstantShare(invitationRequest, list, shareResultCallback);
    }

    public ShareController requestSharedItemUpdate(String str, List<SharedItemRequest> list, ShareUploadResultCallback<SharedItemListResult> shareUploadResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestSharedItemUpdate(str, list, shareUploadResultCallback, pendingIntent, bundle);
    }

    public ShareController requestSharedItemWithUriListUpdate(String str, List<SharedItemUpdateWithUriListRequest> list, ShareUploadResultCallback<SharedItemListWithUriListResult> shareUploadResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestSharedItemWithUriListUpdate(str, list, this.mCid, shareUploadResultCallback, pendingIntent, bundle);
    }

    public ShareController requestShare(String str, SharedItemWithUriListRequest sharedItemWithUriListRequest, SharedItemWithContentListResultCallback sharedItemWithContentListResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestShare(str, sharedItemWithUriListRequest, sharedItemWithContentListResultCallback, pendingIntent, bundle);
    }

    public int requestSharedItemDeletion(String str, String str2, SharedItemDeletionResultCallback sharedItemDeletionResultCallback) {
        return this.mShareApiImpl.requestSharedItemDeletion(str, str2, sharedItemDeletionResultCallback);
    }

    public int requestSharedItemDeletion(String str, List<String> list, SharedItemDeletionListResultCallback sharedItemDeletionListResultCallback) {
        return this.mShareApiImpl.requestSharedItemDeletion(str, list, sharedItemDeletionListResultCallback);
    }

    public int requestSharedContentDownload(String str, List<String> list, ContentDownloadingResultCallback contentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestSharedContentDownload(str, list, contentDownloadingResultCallback, pendingIntent, bundle, null);
    }

    public int requestSharedContentDownload(String str, String str2, List<String> list, ContentDownloadingResultCallback contentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestSharedContentDownload(str, str2, list, this.mCid, contentDownloadingResultCallback, pendingIntent, bundle, null);
    }

    public int requestSpaceUpdateWithMediaServiceContentId(String str, String str2, String str3, String str4, long j, SpaceResultCallback spaceResultCallback) {
        Bundle bundle = new Bundle();
        bundle.putString("media_service_content_id", str2);
        bundle.putString("mime_type", str3);
        bundle.putString("file_name", str4);
        bundle.putLong("file_size", j);
        return this.mShareApiImpl.requestSpaceUpdate(str, bundle, null, spaceResultCallback);
    }

    public ShareController requestSharedItemUpdate(String str, String str2, SharedItemRequest sharedItemRequest, SharedItemUpdateResultCallback sharedItemUpdateResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestSharedItemUpdate(str, str2, sharedItemRequest, sharedItemUpdateResultCallback, pendingIntent, bundle);
    }

    public ShareController requestSharedItemUpdate(String str, String str2, SharedItemWithUriListRequest sharedItemWithUriListRequest, SharedItemWithContentListResultCallback sharedItemWithContentListResultCallback, PendingIntent pendingIntent, Bundle bundle) {
        return this.mShareApiImpl.requestSharedItemUpdate(str, str2, sharedItemWithUriListRequest, sharedItemWithContentListResultCallback, pendingIntent, bundle);
    }

    public int requestSharedItemSync(String str, SharedItemSyncResultCallback sharedItemSyncResultCallback) {
        return this.mShareApiImpl.requestSharedItemSync(str, sharedItemSyncResultCallback);
    }

    public int requestThumbnailDownload(String str, String str2, String str3, String str4, String str5, ShareBaseResultCallback<DownloadImageResult> shareBaseResultCallback) {
        return this.mShareApiImpl.requestThumbnailDownload(str, str2, str3, str4, str5, shareBaseResultCallback);
    }

    public static abstract class SharedItemRequest {
        public static final int REQUEST_CONTENT_ID = 2;
        public static final int REQUEST_DATA = 3;
        public static final int REQUEST_HASH = 1;
        public static final int REQUEST_URI = 0;
        public static final int REQUEST_URIS = 4;
        private String mContentMimeType;
        private String mItemId;
        private String mMemo;
        private Map mMetaData;
        protected int mRequestType;
        private String mTitle;

        public int getRequestType() {
            return this.mRequestType;
        }

        public String getItemId() {
            return this.mItemId;
        }

        public void setItemId(String str) {
            this.mItemId = str;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public void setTitle(String str) {
            this.mTitle = str;
        }

        public String getMemo() {
            return this.mMemo;
        }

        public void setMemo(String str) {
            this.mMemo = str;
        }

        public String getContentMimeType() {
            return this.mContentMimeType;
        }

        public void setContentMimeType(String str) {
            this.mContentMimeType = str;
        }

        public Map getMetaData() {
            return this.mMetaData;
        }

        public void setMetaData(Map map) {
            this.mMetaData = map;
        }
    }

    public static class SharedItemWithUriRequest extends SharedItemRequest {
        private final String FILE_URI = "file:///";
        private Uri mUri;

        public SharedItemWithUriRequest(String str) {
            setTitle(str);
            this.mRequestType = 0;
        }

        public SharedItemWithUriRequest(Uri uri, String str) {
            setContentMimeType(str);
            setUri(uri);
        }

        public Uri getUri() {
            return this.mUri;
        }

        public void setUri(Uri uri) {
            this.mUri = uri;
        }

        public boolean isFileUri() {
            return this.mUri.toString().startsWith("file:///");
        }

        @Deprecated
        public Uri getContentUri() {
            return this.mUri;
        }

        @Deprecated
        public void setContentUri(Uri uri) {
            this.mUri = uri;
        }
    }

    public static class SharedItemWithUriListRequest extends SharedItemRequest {
        private List<String> mMimeTypeList = new ArrayList();
        private List<Uri> mUris = new ArrayList();

        public SharedItemWithUriListRequest(String str) {
            setTitle(str);
            this.mRequestType = 4;
        }

        public List<Uri> getUris() {
            return this.mUris;
        }

        public List<String> getMimeTypeList() {
            return this.mMimeTypeList;
        }

        public void addUri(Uri uri, String str) {
            this.mUris.add(uri);
            this.mMimeTypeList.add(str);
        }
    }

    public static class SharedItemUpdateWithUriListRequest extends SharedItemWithUriListRequest {
        private boolean mFileReplaceRequired = false;
        private String mItemId;

        public SharedItemUpdateWithUriListRequest(String str, String str2) {
            super(str2);
            this.mItemId = str;
        }

        @Override // com.samsung.android.sdk.mobileservice.social.share.ShareApi.SharedItemRequest
        public String getItemId() {
            return this.mItemId;
        }

        public boolean isFileReplaceRequired() {
            return this.mFileReplaceRequired;
        }

        public void setFileReplaceRequired(boolean z) {
            this.mFileReplaceRequired = z;
        }
    }

    public static class SharedItemWithDataRequest extends SharedItemRequest {
        public SharedItemWithDataRequest(Map map) {
            setMetaData(map);
            this.mRequestType = 3;
        }
    }

    public static class SharedItemWithSCloudHashRequest extends SharedItemRequest {
        private String mContentName;
        private long mContentSize;
        private String mHash;

        public SharedItemWithSCloudHashRequest(String str) {
            setTitle(str);
            this.mRequestType = 1;
        }

        public SharedItemWithSCloudHashRequest(String str, String str2) {
            setContentMimeType(str2);
            setHash(str);
        }

        public SharedItemWithSCloudHashRequest(String str, String str2, long j, String str3) {
            setContentMimeType(str2);
            setHash(str);
            setContentSize(j);
            setContentName(str3);
        }

        public String getHash() {
            return this.mHash;
        }

        public void setHash(String str) {
            this.mHash = str;
        }

        public long getContentSize() {
            return this.mContentSize;
        }

        public void setContentSize(long j) {
            this.mContentSize = j;
        }

        public String getContentName() {
            return this.mContentName;
        }

        public void setContentName(String str) {
            this.mContentName = str;
        }
    }

    public static class SharedItemWithMediaServiceContentIdRequest extends SharedItemRequest {
        private String mContentName;
        private long mContentSize;
        private String mMediaServiceContentId;

        public SharedItemWithMediaServiceContentIdRequest(String str) {
            setTitle(str);
            this.mRequestType = 2;
        }

        public SharedItemWithMediaServiceContentIdRequest(String str, String str2) {
            setContentMimeType(str2);
            setMediaServiceContentId(str);
        }

        public SharedItemWithMediaServiceContentIdRequest(String str, String str2, long j, String str3) {
            setContentMimeType(str2);
            setMediaServiceContentId(str);
            setContentSize(j);
            setContentName(str3);
        }

        public String getMediaServiceContentId() {
            return this.mMediaServiceContentId;
        }

        public void setMediaServiceContentId(String str) {
            this.mMediaServiceContentId = str;
        }

        public long getContentSize() {
            return this.mContentSize;
        }

        public void setContentSize(long j) {
            this.mContentSize = j;
        }

        public String getContentName() {
            return this.mContentName;
        }

        public void setContentName(String str) {
            this.mContentName = str;
        }
    }

    public static abstract class SpaceRequest {
        private String mMemo;
        private Map mMetaData;
        private String mMimeType;
        private String mTitle;

        public SpaceRequest(String str) {
            this.mTitle = str;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public void setTitle(String str) {
            this.mTitle = str;
        }

        public String getMemo() {
            return this.mMemo;
        }

        public void setMemo(String str) {
            this.mMemo = str;
        }

        public Map getMetaData() {
            return this.mMetaData;
        }

        public void setMetaData(Map map) {
            this.mMetaData = map;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public void setMimeType(String str) {
            this.mMimeType = str;
        }
    }

    public static class SpaceWithUriRequest extends SpaceRequest {
        private Uri mCoverImageUri;

        public SpaceWithUriRequest(String str) {
            super(str);
        }

        public Uri getCoverImageUri() {
            return this.mCoverImageUri;
        }

        public void setCoverImageUri(Uri uri) {
            this.mCoverImageUri = uri;
        }
    }

    public static class SpaceWithSCloudHashRequest extends SpaceRequest {
        private String mCoverImageName;
        private long mCoverImageSize;
        private String mHash;

        public SpaceWithSCloudHashRequest(String str) {
            super(str);
        }

        public String getHash() {
            return this.mHash;
        }

        public void setHash(String str) {
            this.mHash = str;
        }

        public long getCoverImageSize() {
            return this.mCoverImageSize;
        }

        public void setCoverImageSize(long j) {
            this.mCoverImageSize = j;
        }

        public String getCoverImageName() {
            return this.mCoverImageName;
        }

        public void setCoverImageName(String str) {
            this.mCoverImageName = str;
        }
    }

    public static class SpaceWithMediaServiceContentIdRequest extends SpaceRequest {
        private String mCoverImageName;
        private long mCoverImageSize;
        private String mMediaServiceContentId;

        public SpaceWithMediaServiceContentIdRequest(String str) {
            super(str);
        }

        public String getMediaServiceContentId() {
            return this.mMediaServiceContentId;
        }

        public void setMediaServiceContentId(String str) {
            this.mMediaServiceContentId = str;
        }

        public long getCoverImageSize() {
            return this.mCoverImageSize;
        }

        public void setCoverImageSize(long j) {
            this.mCoverImageSize = j;
        }

        public String getCoverImageName() {
            return this.mCoverImageName;
        }

        public void setCoverImageName(String str) {
            this.mCoverImageName = str;
        }
    }
}
