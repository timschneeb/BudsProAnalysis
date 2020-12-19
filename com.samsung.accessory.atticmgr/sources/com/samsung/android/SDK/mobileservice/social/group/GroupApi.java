package com.samsung.android.sdk.mobileservice.social.group;

import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
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
import com.samsung.android.sdk.mobileservice.social.group.IGroupCoverImageDownloadingResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupListResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupListWithInvitationResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IMemberListResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.provider.GroupInvitationContract;
import com.samsung.android.sdk.mobileservice.social.group.result.GroupImageDownloadResult;
import com.samsung.android.sdk.mobileservice.social.group.result.GroupInvitationListResult;
import com.samsung.android.sdk.mobileservice.social.group.result.GroupInvitationResult;
import com.samsung.android.sdk.mobileservice.social.group.result.GroupListResult;
import com.samsung.android.sdk.mobileservice.social.group.result.GroupMemberResult;
import com.samsung.android.sdk.mobileservice.social.group.result.GroupResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GroupApi extends SeMobileServiceApi {
    public static final String API_NAME = "GroupApi";

    public interface GroupListResultCallback {
        void onResult(GroupListResult groupListResult);
    }

    public interface GroupResultCallback<T> {
        void onResult(T t);
    }

    public interface GroupSyncResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    public interface ImageDownloadingResultCallback {
        void onResult(GroupImageDownloadResult groupImageDownloadResult);
    }

    public GroupApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "GroupApi");
        checkAuthorized(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        return new String[]{"SocialService"};
    }

    public int requestGroupCoverImageDownload(String str, final ImageDownloadingResultCallback imageDownloadingResultCallback) {
        debugLog("requestOriginalImageDownload groupId=[" + str + "] ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestOriginalGroupImageDownload(getAppId(), str, new IGroupCoverImageDownloadingResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupCoverImageDownloadingResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    GroupApi.this.debugLog("requestOriginalImageDownload onSuccess ");
                    if (imageDownloadingResultCallback != null) {
                        Uri uri = null;
                        String string = bundle.getString("group_id", null);
                        String string2 = bundle.getString("downloaded_uri", null);
                        if (string2 != null) {
                            uri = Uri.parse(string2);
                        }
                        GroupImageDownloadResult.DownloadedImage downloadedImage = new GroupImageDownloadResult.DownloadedImage(string, uri);
                        GroupApi groupApi = GroupApi.this;
                        groupApi.debugLog("- groupId=[" + string + "], uriString=[" + string2 + "] ");
                        imageDownloadingResultCallback.onResult(new GroupImageDownloadResult(new CommonResultStatus(1), downloadedImage));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupCoverImageDownloadingResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupCoverImageDownload onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && imageDownloadingResultCallback != null) {
                        GroupApi groupApi2 = GroupApi.this;
                        groupApi2.debugLog("requestGroupCoverImageDownload Error Message [" + str + "]");
                        imageDownloadingResultCallback.onResult(new GroupImageDownloadResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupCoverImageDownloadingResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupCoverImageDownload onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (imageDownloadingResultCallback != null) {
                        GroupApi groupApi2 = GroupApi.this;
                        groupApi2.debugLog("requestGroupCoverImageDownload Error Message [" + string + "]");
                        imageDownloadingResultCallback.onResult(new GroupImageDownloadResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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

    public int requestGroup(String str, final GroupResultCallback<GroupResult> groupResultCallback) {
        debugLog("requestGroup : groupId=[" + str + "] ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroup(getAppId(), str, new IGroupResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass2 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    GroupApi.this.debugLog("requestGroup onSuccess ");
                    if (groupResultCallback != null) {
                        Uri uri = null;
                        String string = bundle.getString("group_id", null);
                        String string2 = bundle.getString("group_name", null);
                        GroupApi groupApi = GroupApi.this;
                        groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                        String string3 = bundle.getString("group_type", null);
                        String string4 = bundle.getString("owner_id", null);
                        String string5 = bundle.getString("cover_thumbnail_uri", null);
                        if (string5 != null) {
                            uri = Uri.parse(string5);
                        }
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(1), new Group(string, string2, string3, string4, uri, bundle.getLong("created_time", 0), bundle.getInt("max_member_count", 0), bundle.getInt("active_member_count", 0), bundle.getLong("group_update_time", 0), (HashMap) bundle.getSerializable("meta_data"), bundle.getLong("contents_update_time", 0))));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroup onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        GroupApi groupApi2 = GroupApi.this;
                        groupApi2.debugLog("requestGroup Error Message [" + str + "]");
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroup onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        GroupApi groupApi2 = GroupApi.this;
                        groupApi2.debugLog("requestGroup Error Message [" + string + "]");
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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

    public int requestGroupList(final GroupListResultCallback groupListResultCallback) {
        debugLog("requestGroupList ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupList(getAppId(), new IGroupListResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass3 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupListResultCallback
                public void onSuccess(List<Bundle> list) throws RemoteException {
                    GroupApi.this.debugLog("requestGroupList onSuccess ");
                    if (groupListResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            Uri uri = null;
                            String string = bundle.getString("group_id", null);
                            String string2 = bundle.getString("group_name", null);
                            GroupApi groupApi = GroupApi.this;
                            groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                            String string3 = bundle.getString("group_type", null);
                            String string4 = bundle.getString("owner_id", null);
                            String string5 = bundle.getString("cover_thumbnail_uri", null);
                            if (string5 != null) {
                                uri = Uri.parse(string5);
                            }
                            arrayList.add(new Group(string, string2, string3, string4, uri, bundle.getLong("created_time", 0), bundle.getInt("max_member_count", 0), bundle.getInt("active_member_count", 0), bundle.getLong("group_update_time", 0), (HashMap) bundle.getSerializable("meta_data"), bundle.getLong("contents_update_time", 0)));
                        }
                        groupListResultCallback.onResult(new GroupListResult(new CommonResultStatus(1), arrayList));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupListResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupList onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupListResultCallback != null) {
                        groupListResultCallback.onResult(new GroupListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupListResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupList onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupListResultCallback != null) {
                        groupListResultCallback.onResult(new GroupListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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

    public int requestSync(final GroupSyncResultCallback groupSyncResultCallback) {
        debugLog("requestSync ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_4_1)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupSync(getAppId(), new IGroupSyncResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass4 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestSync onSuccess ");
                    GroupSyncResultCallback groupSyncResultCallback = groupSyncResultCallback;
                    if (groupSyncResultCallback != null) {
                        groupSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestSync onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupSyncResultCallback != null) {
                        groupSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestSync onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupSyncResultCallback != null) {
                        groupSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestSyncWithoutImage(final GroupSyncResultCallback groupSyncResultCallback) {
        debugLog("requestSyncWithoutImage ");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_10_5)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupSyncWithoutImage(getAppId(), new IGroupSyncResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass5 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestSyncWithoutImage onSuccess ");
                    GroupSyncResultCallback groupSyncResultCallback = groupSyncResultCallback;
                    if (groupSyncResultCallback != null) {
                        groupSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestSyncWithoutImage onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupSyncResultCallback != null) {
                        groupSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestSyncWithoutImage onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupSyncResultCallback != null) {
                        groupSyncResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public GroupListResult getGroupList() {
        debugLog("getGroupList started");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return new GroupListResult(new CommonResultStatus(-1), null);
        }
        try {
            List<Bundle> groupList = getSocialService().getGroupList(getAppId());
            ArrayList arrayList = new ArrayList();
            for (Bundle bundle : groupList) {
                String string = bundle.getString("group_id", null);
                String string2 = bundle.getString("group_name", null);
                String string3 = bundle.getString("group_type", null);
                String string4 = bundle.getString("owner_id", null);
                String string5 = bundle.getString("cover_thumbnail_uri", null);
                arrayList.add(new Group(string, string2, string3, string4, string5 != null ? Uri.parse(string5) : null, bundle.getLong("created_time", 0), bundle.getInt("max_member_count", 0), bundle.getInt("active_member_count", 0), bundle.getLong("group_update_time", 0), (HashMap) bundle.getSerializable("meta_data"), bundle.getLong("contents_update_time", 0)));
            }
            return new GroupListResult(new CommonResultStatus(1), arrayList);
        } catch (RemoteException | NullPointerException e) {
            secureLog(e);
            return new GroupListResult(new CommonResultStatus(-1), null);
        } catch (NotConnectedException e2) {
            secureLog(e2);
            return new GroupListResult(new CommonResultStatus(-8), null);
        }
    }

    public int requestGroupCreation(GroupRequest groupRequest, InvitationRequest invitationRequest, final GroupResultCallback<GroupInvitationResult> groupResultCallback) {
        debugLog("requestGroupCreation ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        Bundle bundle = new Bundle();
        bundle.putString("group_name", groupRequest.getGroupName());
        bundle.putString("group_type", groupRequest.getGroupType());
        bundle.putString("mime_type", groupRequest.getMimeType());
        if (groupRequest.getCoverImageUri() != null) {
            bundle.putString("cover_thumbnail_uri", groupRequest.getCoverImageUri().toString());
        }
        Bundle bundle2 = null;
        if (invitationRequest != null) {
            bundle2 = new Bundle();
            bundle2.putInt("invitation_type", invitationRequest.getIdType());
            bundle2.putString("invitation_message", invitationRequest.getInvitationMessage());
            bundle2.putStringArrayList("id", new ArrayList<>(invitationRequest.getIds()));
            bundle2.putStringArrayList("optionalId", new ArrayList<>(invitationRequest.getOptionalIds()));
        }
        try {
            getSocialService().requestGroupCreation(getAppId(), bundle, bundle2, new IGroupInvitationResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass6 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback
                public void onSuccess(Bundle bundle, List<Bundle> list) throws RemoteException {
                    GroupApi.this.debugLog("requestGroupCreation onSuccess ");
                    if (groupResultCallback != null) {
                        String string = bundle.getString("group_id", null);
                        String string2 = bundle.getString("group_name", null);
                        GroupApi groupApi = GroupApi.this;
                        groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                        String string3 = bundle.getString("group_type", null);
                        String string4 = bundle.getString("owner_id", null);
                        String string5 = bundle.getString("cover_thumbnail_uri", null);
                        Uri parse = string5 != null ? Uri.parse(string5) : null;
                        long j = bundle.getLong("created_time", 0);
                        int i = bundle.getInt("max_member_count", 0);
                        int i2 = bundle.getInt("active_member_count", 0);
                        long j2 = bundle.getLong("group_update_time", 0);
                        HashMap hashMap = (HashMap) bundle.getSerializable("meta_data");
                        long j3 = bundle.getLong("contents_update_time", 0);
                        String string6 = bundle.getString("error_string", "");
                        ArrayList arrayList = new ArrayList();
                        Iterator<Bundle> it = list.iterator();
                        while (it.hasNext()) {
                            Bundle next = it.next();
                            arrayList.add(new GroupInvitationResult.ExcludedMember(next.getString("id", null), next.getString("optionalId", null), next.getString("reason", null)));
                            it = it;
                            string6 = string6;
                        }
                        groupResultCallback.onResult(new GroupInvitationResult(new CommonResultStatus(1), new Group(string, string2, string3, string4, parse, j, i, i2, j2, hashMap, j3), arrayList, string6));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupCreation onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupInvitationResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null, null, null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupCreation onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupInvitationResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null, null, null));
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

    public static class GroupRequest {
        public static final String GROUP_TYPE_FAMILY = "FMLY";
        public static final String GROUP_TYPE_GENERAL = "GNRL";
        public static final String GROUP_TYPE_LOCAL = "UNM1";
        private Uri mCoverImageUri;
        private String mGroupName;
        private String mGroupType;
        private String mMimeType;

        public GroupRequest(String str, String str2, Uri uri, String str3) {
            this.mGroupName = str;
            this.mGroupType = str2;
            this.mCoverImageUri = uri;
            this.mMimeType = str3;
        }

        public GroupRequest(String str, Uri uri, String str2) {
            this.mGroupName = str;
            this.mCoverImageUri = uri;
            this.mMimeType = str2;
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        public String getGroupType() {
            return this.mGroupType;
        }

        public Uri getCoverImageUri() {
            return this.mCoverImageUri;
        }

        public String getMimeType() {
            return this.mMimeType;
        }
    }

    public static class InvitationRequest {
        public static final int ID_TYPE_ACCOUNT_ID = 3;
        public static final int ID_TYPE_DUID = 2;
        public static final int ID_TYPE_GUID = 0;
        public static final int ID_TYPE_MSISDN = 1;
        private int mIdType;
        private List<String> mIds;
        private String mInvitationMessage;
        private List<String> mOptionalIds;

        public InvitationRequest(String str, int i, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
            this.mInvitationMessage = str;
            this.mIdType = i;
            this.mIds = arrayList;
            this.mOptionalIds = arrayList2;
        }

        public String getInvitationMessage() {
            return this.mInvitationMessage;
        }

        public int getIdType() {
            return this.mIdType;
        }

        public List<String> getIds() {
            return this.mIds;
        }

        public List<String> getOptionalIds() {
            return this.mOptionalIds;
        }
    }

    public int requestGroupUpdate(String str, GroupRequest groupRequest, final GroupResultCallback<GroupResult> groupResultCallback) {
        debugLog("requestGroupUpdate ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        Bundle bundle = new Bundle();
        bundle.putString("group_name", groupRequest.getGroupName());
        bundle.putString("mime_type", groupRequest.getMimeType());
        if (groupRequest.getCoverImageUri() != null) {
            bundle.putString("cover_thumbnail_uri", groupRequest.getCoverImageUri().toString());
        }
        try {
            getSocialService().requestGroupUpdate(getAppId(), str, bundle, new IGroupResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass7 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    GroupApi.this.debugLog("requestGroupUpdate onSuccess ");
                    if (groupResultCallback != null) {
                        Uri uri = null;
                        String string = bundle.getString("group_id", null);
                        String string2 = bundle.getString("group_name", null);
                        GroupApi groupApi = GroupApi.this;
                        groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                        String string3 = bundle.getString("group_type", null);
                        String string4 = bundle.getString("owner_id", null);
                        String string5 = bundle.getString("cover_thumbnail_uri", null);
                        if (string5 != null) {
                            uri = Uri.parse(string5);
                        }
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(1), new Group(string, string2, string3, string4, uri, bundle.getLong("created_time", 0), bundle.getInt("max_member_count", 0), bundle.getInt("active_member_count", 0), bundle.getLong("group_update_time", 0), (HashMap) bundle.getSerializable("meta_data"), bundle.getLong("contents_update_time", 0))));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupUpdate onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupUpdate onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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

    public int requestGroupDeletion(String str, final GroupResultCallback<BooleanResult> groupResultCallback) {
        debugLog("requestGroupDeletion ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupDeletion(getAppId(), str, new IGroupRequestResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass8 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestGroupDeletion onSuccess ");
                    GroupResultCallback groupResultCallback = groupResultCallback;
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupDeletion onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupDeletion onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestGroupMemberInvitation(String str, InvitationRequest invitationRequest, final GroupResultCallback<GroupInvitationResult> groupResultCallback) {
        debugLog("requestGroupMemberInvitation ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("invitation_type", invitationRequest.getIdType());
        bundle.putString("invitation_message", invitationRequest.getInvitationMessage());
        bundle.putStringArrayList("id", new ArrayList<>(invitationRequest.getIds()));
        bundle.putStringArrayList("optionalId", new ArrayList<>(invitationRequest.getOptionalIds()));
        try {
            getSocialService().requestGroupMemberInvitation(getAppId(), str, bundle, new IGroupInvitationResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass9 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback
                public void onSuccess(Bundle bundle, List<Bundle> list) throws RemoteException {
                    GroupApi.this.debugLog("requestGroupMemberInvitation onSuccess ");
                    if (groupResultCallback != null) {
                        String string = bundle.getString("group_id", null);
                        String string2 = bundle.getString("group_name", null);
                        GroupApi groupApi = GroupApi.this;
                        groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                        String string3 = bundle.getString("group_type", null);
                        String string4 = bundle.getString("owner_id", null);
                        String string5 = bundle.getString("cover_thumbnail_uri", null);
                        Uri parse = string5 != null ? Uri.parse(string5) : null;
                        long j = bundle.getLong("created_time", 0);
                        int i = bundle.getInt("max_member_count", 0);
                        int i2 = bundle.getInt("active_member_count", 0);
                        long j2 = bundle.getLong("group_update_time", 0);
                        HashMap hashMap = (HashMap) bundle.getSerializable("meta_data");
                        long j3 = bundle.getLong("contents_update_time", 0);
                        String string6 = bundle.getString("error_string", "");
                        ArrayList arrayList = new ArrayList();
                        Iterator<Bundle> it = list.iterator();
                        while (it.hasNext()) {
                            Bundle next = it.next();
                            arrayList.add(new GroupInvitationResult.ExcludedMember(next.getString("id", null), next.getString("optionalId", null), next.getString("reason", null)));
                            it = it;
                            string6 = string6;
                        }
                        groupResultCallback.onResult(new GroupInvitationResult(new CommonResultStatus(1), new Group(string, string2, string3, string4, parse, j, i, i2, j2, hashMap, j3), arrayList, string6));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberInvitation onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupInvitationResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null, null, null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberInvitation onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupInvitationResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null, null, null));
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

    public int requestGroupInvitationAcceptance(String str, final GroupResultCallback<BooleanResult> groupResultCallback) {
        debugLog("requestGroupInvitationAcceptance ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupInvitationAcceptance(getAppId(), str, new IGroupRequestResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass10 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestGroupInvitationAcceptance onSuccess ");
                    GroupResultCallback groupResultCallback = groupResultCallback;
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupInvitationAcceptance onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupInvitationAcceptance onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestGroupInvitationRejection(String str, final GroupResultCallback<BooleanResult> groupResultCallback) {
        debugLog("requestGroupInvitationRejection ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupInvitationRejection(getAppId(), str, new IGroupRequestResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass11 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestGroupInvitationRejection onSuccess ");
                    GroupResultCallback groupResultCallback = groupResultCallback;
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupInvitationRejection onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupInvitationRejection onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestGroupMemberList(String str, final GroupResultCallback<GroupMemberResult> groupResultCallback) {
        debugLog("requestGroupMemberList ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupMemberList(getAppId(), str, new IMemberListResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass12 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IMemberListResultCallback
                public void onSuccess(List<Bundle> list) throws RemoteException {
                    GroupApi.this.debugLog("requestGroupMemberList onSuccess ");
                    if (groupResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            Uri uri = null;
                            String string = bundle.getString("name", null);
                            String string2 = bundle.getString("id", null);
                            String string3 = bundle.getString("optionalId", null);
                            int i = bundle.getInt("status", 0);
                            int i2 = bundle.getInt("invitation_type", 0);
                            String string4 = bundle.getString("thumbnail_uri", null);
                            boolean z = bundle.getBoolean("owner", false);
                            if (string4 != null) {
                                uri = Uri.parse(string4);
                            }
                            arrayList.add(new GroupMember(i2, string2, string3, i, string, z, uri));
                        }
                        GroupMemberResult groupMemberResult = new GroupMemberResult(new CommonResultStatus(1), arrayList);
                        groupMemberResult.setTotalCountWithInvitation(list.get(0).getInt("total", 0));
                        groupResultCallback.onResult(groupMemberResult);
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IMemberListResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberList onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupMemberResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IMemberListResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberList onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupMemberResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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

    public int requestMyInvitationList(final GroupResultCallback<GroupInvitationListResult> groupResultCallback) {
        debugLog("requestGroupWithInvitationList ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupWithInvitationList(getAppId(), new IGroupListWithInvitationResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass13 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupListWithInvitationResultCallback
                public void onSuccess(List<Bundle> list) throws RemoteException {
                    GroupApi.this.debugLog("requestGroupWithInvitationList onSuccess ");
                    if (groupResultCallback != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Bundle bundle : list) {
                            String string = bundle.getString("group_id", null);
                            String string2 = bundle.getString("group_name", null);
                            GroupApi groupApi = GroupApi.this;
                            groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                            arrayList.add(new GroupInvitationListResult.GroupInvitation(string, string2, bundle.getString("group_cover_thumbnail_url", null), bundle.getString("invitation_message", null), bundle.getString(GroupInvitationContract.Invitation.REQUESTER_ID, null), bundle.getString(GroupInvitationContract.Invitation.REQUESTER_NAME, null), bundle.getString("requesterImageUrl", null), bundle.getLong(GroupInvitationContract.Invitation.REQUESTED_TIME, 0), bundle.getLong("expired_time", 0)));
                        }
                        groupResultCallback.onResult(new GroupInvitationListResult(new CommonResultStatus(1), arrayList));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupListWithInvitationResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupWithInvitationList onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupInvitationListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupListWithInvitationResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupWithInvitationList onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupInvitationListResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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

    public int requestGroupMemberRemoval(String str, String str2, final GroupResultCallback<BooleanResult> groupResultCallback) {
        debugLog("requestGroupMemberRemoval ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupMemberRemoval(getAppId(), str, str2, new IGroupRequestResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass14 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestGroupMemberRemoval onSuccess ");
                    GroupResultCallback groupResultCallback = groupResultCallback;
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberRemoval onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberRemoval onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestLeave(String str, final GroupResultCallback<BooleanResult> groupResultCallback) {
        debugLog("requestGroupMemberRemoval ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestLeave(getAppId(), str, new IGroupRequestResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass15 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestGroupMemberRemoval onSuccess ");
                    GroupResultCallback groupResultCallback = groupResultCallback;
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberRemoval onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupMemberRemoval onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestGroupPendingInvitationCancellation(String str, String str2, final GroupResultCallback<BooleanResult> groupResultCallback) {
        debugLog("requestGroupPendingInvitationCancellation ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestGroupPendingInvitationCancellation(getAppId(), str, str2, new IGroupRequestResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass16 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onSuccess() throws RemoteException {
                    GroupApi.this.debugLog("requestGroupPendingInvitationCancellation onSuccess ");
                    GroupResultCallback groupResultCallback = groupResultCallback;
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupPendingInvitationCancellation onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (GroupApi.this.getSemsAgentVersion() < 1050000000 && groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestGroupPendingInvitationCancellation onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new BooleanResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), false));
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

    public int requestLeaderAssignment(String str, String str2, final GroupResultCallback<GroupResult> groupResultCallback) {
        debugLog("requestLeaderAssignment ");
        if (getAppId() == null) {
            debugLog("app id is null ");
            return -1;
        }
        try {
            getSocialService().requestDelegateAuthorityOfOwner(getAppId(), str, str2, new IGroupResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.group.GroupApi.AnonymousClass17 */

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    GroupApi.this.debugLog("requestLeaderAssignment onSuccess ");
                    if (groupResultCallback != null) {
                        Uri uri = null;
                        String string = bundle.getString("group_id", null);
                        String string2 = bundle.getString("group_name", null);
                        GroupApi groupApi = GroupApi.this;
                        groupApi.debugLog("- groupId=[" + string + "], groupName=[" + string2 + "] ");
                        String string3 = bundle.getString("group_type", null);
                        String string4 = bundle.getString("owner_id", null);
                        String string5 = bundle.getString("cover_thumbnail_uri", null);
                        if (string5 != null) {
                            uri = Uri.parse(string5);
                        }
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(1), new Group(string, string2, string3, string4, uri, bundle.getLong("created_time", 0), bundle.getInt("max_member_count", 0), bundle.getInt("active_member_count", 0))));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestLeaderAssignment onFailure : code=[" + j + "], message=[" + str + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback
                public void onFailureWithBundle(Bundle bundle) throws RemoteException {
                    long j = bundle.getLong(AuthConstants.EXTRA_ERROR_CODE);
                    String string = bundle.getString(AuthConstants.EXTRA_ERROR_MESSAGE);
                    String string2 = bundle.getString("error_string", null);
                    GroupApi groupApi = GroupApi.this;
                    groupApi.debugLog("requestLeaderAssignment onFailureWithBundle : code=[" + j + "], message=[" + string + "] ");
                    if (groupResultCallback != null) {
                        groupResultCallback.onResult(new GroupResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), string, Long.toString(j), string2), null));
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
