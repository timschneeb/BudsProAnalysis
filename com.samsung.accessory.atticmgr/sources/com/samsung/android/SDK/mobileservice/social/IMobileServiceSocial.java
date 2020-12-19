package com.samsung.android.sdk.mobileservice.social;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.IActivityResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.IDeleteAllActivityResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.IBuddyInfoResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.IPublicBuddyInfoResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.IServiceActivationResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.IServiceDeactivationResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.ISyncResultCallback;
import com.samsung.android.sdk.mobileservice.social.common.IBundleProgressResultCallback;
import com.samsung.android.sdk.mobileservice.social.feedback.IFeedbackBundlePartialResultCallback;
import com.samsung.android.sdk.mobileservice.social.feedback.IFeedbackBundleResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupCoverImageDownloadingResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupInvitationResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupListResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupListWithInvitationResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupRequestResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IGroupSyncResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.IMemberListResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IContentDownloadingResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IDownloadThumbnailResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IShareResultCallback;
import com.samsung.android.sdk.mobileservice.social.share.IShareResultWithFileListCallback;
import com.samsung.android.sdk.mobileservice.social.share.IShareStatusCallback;
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
import java.util.ArrayList;
import java.util.List;

public interface IMobileServiceSocial extends IInterface {
    int cancelShare(String str, String str2) throws RemoteException;

    void clearSpaceUnreadCount(String str, String str2) throws RemoteException;

    void clearSpaceUnreadCountWithData(String str, Bundle bundle, String str2) throws RemoteException;

    Bundle getBuddyActivityCount(Bundle bundle) throws RemoteException;

    Bundle getBuddyActivityList(Bundle bundle) throws RemoteException;

    void getBuddyInfo(Bundle bundle, IBuddyInfoResultCallback iBuddyInfoResultCallback) throws RemoteException;

    int getCountryTypeForAgreement() throws RemoteException;

    Bundle getDeviceAuthInfoCached() throws RemoteException;

    boolean getDisclaimerAgreementForService(Bundle bundle) throws RemoteException;

    boolean getDisclaimerAgreementForSocial() throws RemoteException;

    List<Bundle> getGroupList(String str) throws RemoteException;

    Intent getIntentToDisplay(Bundle bundle) throws RemoteException;

    Bundle getNotification(Bundle bundle) throws RemoteException;

    Bundle getServiceState() throws RemoteException;

    int getShareStatus(String str, String str2) throws RemoteException;

    int isServiceActivated(int i) throws RemoteException;

    boolean isServiceRegistered(Bundle bundle) throws RemoteException;

    Bundle isSomethingNeeded(Bundle bundle) throws RemoteException;

    int pauseShare(String str, String str2) throws RemoteException;

    void requestActivity(Bundle bundle, IActivityBundlePartialResultCallback iActivityBundlePartialResultCallback) throws RemoteException;

    void requestActivityChanges(IActivityBundlePartialResultCallback iActivityBundlePartialResultCallback) throws RemoteException;

    void requestActivityContent(Bundle bundle, IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException;

    void requestActivityContentStreamingUrl(Bundle bundle, IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException;

    void requestActivityDeletion(Bundle bundle, IActivityResultCallback iActivityResultCallback) throws RemoteException;

    void requestActivityImageList(Bundle bundle, IBundleProgressResultCallback iBundleProgressResultCallback) throws RemoteException;

    void requestActivityList(Bundle bundle, IActivityBundlePartialResultCallback iActivityBundlePartialResultCallback) throws RemoteException;

    Bundle requestActivityPosting(Bundle bundle, PendingIntent pendingIntent, IBundleProgressResultCallback iBundleProgressResultCallback) throws RemoteException;

    void requestActivitySync(IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException;

    int requestAllSpaceList(String str, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException;

    int requestAllSpaceListWithData(String str, Bundle bundle, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException;

    void requestBuddySync(int i, ISyncResultCallback iSyncResultCallback) throws RemoteException;

    void requestCommentCreation(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException;

    void requestCommentDeletion(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException;

    void requestCommentList(Bundle bundle, IFeedbackBundlePartialResultCallback iFeedbackBundlePartialResultCallback) throws RemoteException;

    void requestCommentUpdate(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException;

    Bundle requestContentsController(Bundle bundle) throws RemoteException;

    int requestDelegateAuthorityOfOwner(String str, String str2, String str3, IGroupResultCallback iGroupResultCallback) throws RemoteException;

    void requestDeleteAllActivity(IDeleteAllActivityResultCallback iDeleteAllActivityResultCallback) throws RemoteException;

    void requestEmotionMemberList(Bundle bundle, IFeedbackBundlePartialResultCallback iFeedbackBundlePartialResultCallback) throws RemoteException;

    void requestEmotionUpdate(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException;

    void requestFeedback(Bundle bundle, IFeedbackBundlePartialResultCallback iFeedbackBundlePartialResultCallback) throws RemoteException;

    int requestGroup(String str, String str2, IGroupResultCallback iGroupResultCallback) throws RemoteException;

    int requestGroupCreation(String str, Bundle bundle, Bundle bundle2, IGroupInvitationResultCallback iGroupInvitationResultCallback) throws RemoteException;

    int requestGroupDeletion(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException;

    int requestGroupInvitationAcceptance(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException;

    int requestGroupInvitationRejection(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException;

    int requestGroupList(String str, IGroupListResultCallback iGroupListResultCallback) throws RemoteException;

    int requestGroupMemberInvitation(String str, String str2, Bundle bundle, IGroupInvitationResultCallback iGroupInvitationResultCallback) throws RemoteException;

    int requestGroupMemberList(String str, String str2, IMemberListResultCallback iMemberListResultCallback) throws RemoteException;

    int requestGroupMemberRemoval(String str, String str2, String str3, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException;

    int requestGroupPendingInvitationCancellation(String str, String str2, String str3, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException;

    int requestGroupSync(String str, IGroupSyncResultCallback iGroupSyncResultCallback) throws RemoteException;

    int requestGroupSyncWithoutImage(String str, IGroupSyncResultCallback iGroupSyncResultCallback) throws RemoteException;

    int requestGroupUpdate(String str, String str2, Bundle bundle, IGroupResultCallback iGroupResultCallback) throws RemoteException;

    int requestGroupWithInvitationList(String str, IGroupListWithInvitationResultCallback iGroupListWithInvitationResultCallback) throws RemoteException;

    int requestInstantShare(String str, Bundle bundle, List<Bundle> list, IShareResultCallback iShareResultCallback) throws RemoteException;

    int requestLeave(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException;

    void requestMyActivityPrivacy(IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException;

    void requestMyActivityPrivacyUpdate(Bundle bundle, IActivityResultCallback iActivityResultCallback) throws RemoteException;

    int requestOriginalGroupImageDownload(String str, String str2, IGroupCoverImageDownloadingResultCallback iGroupCoverImageDownloadingResultCallback) throws RemoteException;

    int requestOriginalSharedContentWithFileListDownload(String str, String str2, String str3, List<String> list, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException;

    int requestOriginalSharedContentWithFileListDownloadWithData(String str, String str2, String str3, List<String> list, Bundle bundle, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException;

    int requestOriginalSharedContentWithItemFileListDownloadWithPath(String str, String str2, String str3, List<String> list, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str4) throws RemoteException;

    int requestOriginalSharedContentWithItemFileListDownloadWithPathWithData(String str, String str2, String str3, List<String> list, Bundle bundle, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle2, String str4) throws RemoteException;

    int requestOriginalSharedContentsDownload(String str, String str2, String[] strArr, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException;

    int requestOriginalSharedContentsDownloadWithPath(String str, String str2, String[] strArr, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str3) throws RemoteException;

    int requestOriginalSpaceImageDownload(String str, String str2, ISpaceCoverImageDownloadingResultCallback iSpaceCoverImageDownloadingResultCallback) throws RemoteException;

    void requestProfileImageList(Bundle bundle, IBundleProgressResultCallback iBundleProgressResultCallback) throws RemoteException;

    void requestPublicBuddyInfo(String str, IPublicBuddyInfoResultCallback iPublicBuddyInfoResultCallback) throws RemoteException;

    void requestServiceActivation(int i, IServiceActivationResultCallback iServiceActivationResultCallback) throws RemoteException;

    void requestServiceDeactivation(int i, IServiceDeactivationResultCallback iServiceDeactivationResultCallback) throws RemoteException;

    String requestShareListUpdateWithItemFileList(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException;

    String requestShareListUpdateWithItemFileListWithData(String str, String str2, List<Bundle> list, Bundle bundle, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException;

    int requestShareSync(String str, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    int requestShareSyncWithData(String str, Bundle bundle, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    String requestShareUpdateWithUriList(String str, String str2, String str3, Bundle bundle, IShareResultWithFileListCallback iShareResultWithFileListCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException;

    String requestShareWithFileList(String str, String str2, Bundle bundle, IShareResultWithFileListCallback iShareResultWithFileListCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException;

    String requestShareWithItemFileList(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException;

    String requestShareWithItemFileListWithData(String str, String str2, List<Bundle> list, Bundle bundle, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException;

    String requestShareWithPendingIntent(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException;

    int requestSharedItem(String str, String str2, String str3, ISharedItemResultCallback iSharedItemResultCallback) throws RemoteException;

    int requestSharedItemDeletion(String str, String str2, String str3, ISharedItemDeletionResultCallback iSharedItemDeletionResultCallback) throws RemoteException;

    int requestSharedItemList(String str, String str2, String str3, String str4, ISharedItemListResultCallback iSharedItemListResultCallback) throws RemoteException;

    int requestSharedItemListDeletion(String str, String str2, List<String> list, ISharedItemListDeletionResultCallback iSharedItemListDeletionResultCallback) throws RemoteException;

    int requestSharedItemListDeletionWithData(String str, String str2, List<String> list, Bundle bundle, ISharedItemListDeletionResultCallback iSharedItemListDeletionResultCallback) throws RemoteException;

    String requestSharedItemListUpdate(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException;

    int requestSharedItemListWithFileList(String str, String str2, String str3, String str4, ISharedItemListResultCallback iSharedItemListResultCallback) throws RemoteException;

    int requestSharedItemListWithFileListWithData(String str, String str2, String str3, String str4, Bundle bundle, ISharedItemListResultCallback iSharedItemListResultCallback) throws RemoteException;

    int requestSharedItemSync(String str, String str2, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    void requestSharedItemSyncWithResolution(String str, Bundle bundle, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    int requestSharedItemSyncWithResolutionWithData(String str, Bundle bundle, Bundle bundle2, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    String requestSharedItemUpdate(String str, String str2, String str3, Bundle bundle, ISharedItemUpdateResultCallback iSharedItemUpdateResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException;

    int requestSharedItemWithGroupId(String str, String str2, String str3, String str4, ISharedItemResultCallback iSharedItemResultCallback) throws RemoteException;

    int requestSpace(String str, String str2, ISpaceResultCallback iSpaceResultCallback) throws RemoteException;

    int requestSpaceCreation(String str, String str2, Bundle bundle, ISpaceResultCallback iSpaceResultCallback) throws RemoteException;

    int requestSpaceCreationWithData(String str, String str2, Bundle bundle, Bundle bundle2, ISpaceResultCallback iSpaceResultCallback) throws RemoteException;

    int requestSpaceDeletion(String str, String str2, ISpaceDeletionResultCallback iSpaceDeletionResultCallback) throws RemoteException;

    int requestSpaceDeletionWithData(String str, String str2, Bundle bundle, ISpaceDeletionResultCallback iSpaceDeletionResultCallback) throws RemoteException;

    int requestSpaceList(String str, String str2, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException;

    int requestSpaceListSync(String str, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    int requestSpaceListSyncWithData(String str, Bundle bundle, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException;

    int requestSpaceListWithData(String str, String str2, Bundle bundle, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException;

    int requestSpaceUpdate(String str, String str2, Bundle bundle, ISpaceResultCallback iSpaceResultCallback) throws RemoteException;

    int requestSpaceUpdateWithData(String str, String str2, Bundle bundle, Bundle bundle2, ISpaceResultCallback iSpaceResultCallback) throws RemoteException;

    int requestSpaceWithData(String str, String str2, Bundle bundle, ISpaceResultCallback iSpaceResultCallback) throws RemoteException;

    void requestSync(ISyncResultCallback iSyncResultCallback) throws RemoteException;

    int requestThumbnailDownload(String str, String str2, String str3, String str4, String str5, String str6, IDownloadThumbnailResultCallback iDownloadThumbnailResultCallback) throws RemoteException;

    int resumeShare(String str, String str2) throws RemoteException;

    Bundle setBuddyActivityListRead(Bundle bundle) throws RemoteException;

    boolean setDisclaimerAgreementForSocial(Bundle bundle) throws RemoteException;

    int setShareStatusListener(String str, String str2, IShareStatusCallback iShareStatusCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IMobileServiceSocial {
        private static final String DESCRIPTOR = "com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial";
        static final int TRANSACTION_cancelShare = 26;
        static final int TRANSACTION_clearSpaceUnreadCount = 29;
        static final int TRANSACTION_clearSpaceUnreadCountWithData = 95;
        static final int TRANSACTION_getBuddyActivityCount = 81;
        static final int TRANSACTION_getBuddyActivityList = 46;
        static final int TRANSACTION_getBuddyInfo = 110;
        static final int TRANSACTION_getCountryTypeForAgreement = 61;
        static final int TRANSACTION_getDeviceAuthInfoCached = 107;
        static final int TRANSACTION_getDisclaimerAgreementForService = 106;
        static final int TRANSACTION_getDisclaimerAgreementForSocial = 105;
        static final int TRANSACTION_getGroupList = 10;
        static final int TRANSACTION_getIntentToDisplay = 43;
        static final int TRANSACTION_getNotification = 59;
        static final int TRANSACTION_getServiceState = 68;
        static final int TRANSACTION_getShareStatus = 27;
        static final int TRANSACTION_isServiceActivated = 2;
        static final int TRANSACTION_isServiceRegistered = 108;
        static final int TRANSACTION_isSomethingNeeded = 67;
        static final int TRANSACTION_pauseShare = 24;
        static final int TRANSACTION_requestActivity = 51;
        static final int TRANSACTION_requestActivityChanges = 74;
        static final int TRANSACTION_requestActivityContent = 86;
        static final int TRANSACTION_requestActivityContentStreamingUrl = 84;
        static final int TRANSACTION_requestActivityDeletion = 45;
        static final int TRANSACTION_requestActivityImageList = 69;
        static final int TRANSACTION_requestActivityList = 44;
        static final int TRANSACTION_requestActivityPosting = 83;
        static final int TRANSACTION_requestActivitySync = 50;
        static final int TRANSACTION_requestAllSpaceList = 20;
        static final int TRANSACTION_requestAllSpaceListWithData = 94;
        static final int TRANSACTION_requestBuddySync = 109;
        static final int TRANSACTION_requestCommentCreation = 54;
        static final int TRANSACTION_requestCommentDeletion = 56;
        static final int TRANSACTION_requestCommentList = 53;
        static final int TRANSACTION_requestCommentUpdate = 55;
        static final int TRANSACTION_requestContentsController = 85;
        static final int TRANSACTION_requestDelegateAuthorityOfOwner = 71;
        static final int TRANSACTION_requestDeleteAllActivity = 6;
        static final int TRANSACTION_requestEmotionMemberList = 58;
        static final int TRANSACTION_requestEmotionUpdate = 57;
        static final int TRANSACTION_requestFeedback = 52;
        static final int TRANSACTION_requestGroup = 9;
        static final int TRANSACTION_requestGroupCreation = 32;
        static final int TRANSACTION_requestGroupDeletion = 33;
        static final int TRANSACTION_requestGroupInvitationAcceptance = 35;
        static final int TRANSACTION_requestGroupInvitationRejection = 36;
        static final int TRANSACTION_requestGroupList = 8;
        static final int TRANSACTION_requestGroupMemberInvitation = 34;
        static final int TRANSACTION_requestGroupMemberList = 37;
        static final int TRANSACTION_requestGroupMemberRemoval = 39;
        static final int TRANSACTION_requestGroupPendingInvitationCancellation = 41;
        static final int TRANSACTION_requestGroupSync = 7;
        static final int TRANSACTION_requestGroupSyncWithoutImage = 87;
        static final int TRANSACTION_requestGroupUpdate = 73;
        static final int TRANSACTION_requestGroupWithInvitationList = 38;
        static final int TRANSACTION_requestInstantShare = 42;
        static final int TRANSACTION_requestLeave = 40;
        static final int TRANSACTION_requestMyActivityPrivacy = 49;
        static final int TRANSACTION_requestMyActivityPrivacyUpdate = 48;
        static final int TRANSACTION_requestOriginalGroupImageDownload = 21;
        static final int TRANSACTION_requestOriginalSharedContentWithFileListDownload = 63;
        static final int TRANSACTION_requestOriginalSharedContentWithFileListDownloadWithData = 102;
        static final int TRANSACTION_requestOriginalSharedContentWithItemFileListDownloadWithPath = 80;
        static final int TRANSACTION_requestOriginalSharedContentWithItemFileListDownloadWithPathWithData = 101;
        static final int TRANSACTION_requestOriginalSharedContentsDownload = 23;
        static final int TRANSACTION_requestOriginalSharedContentsDownloadWithPath = 75;
        static final int TRANSACTION_requestOriginalSpaceImageDownload = 22;
        static final int TRANSACTION_requestProfileImageList = 70;
        static final int TRANSACTION_requestPublicBuddyInfo = 5;
        static final int TRANSACTION_requestServiceActivation = 3;
        static final int TRANSACTION_requestServiceDeactivation = 4;
        static final int TRANSACTION_requestShareListUpdateWithItemFileList = 79;
        static final int TRANSACTION_requestShareListUpdateWithItemFileListWithData = 100;
        static final int TRANSACTION_requestShareSync = 11;
        static final int TRANSACTION_requestShareSyncWithData = 88;
        static final int TRANSACTION_requestShareUpdateWithUriList = 64;
        static final int TRANSACTION_requestShareWithFileList = 62;
        static final int TRANSACTION_requestShareWithItemFileList = 77;
        static final int TRANSACTION_requestShareWithItemFileListWithData = 99;
        static final int TRANSACTION_requestShareWithPendingIntent = 12;
        static final int TRANSACTION_requestSharedItem = 13;
        static final int TRANSACTION_requestSharedItemDeletion = 14;
        static final int TRANSACTION_requestSharedItemList = 65;
        static final int TRANSACTION_requestSharedItemListDeletion = 76;
        static final int TRANSACTION_requestSharedItemListDeletionWithData = 98;
        static final int TRANSACTION_requestSharedItemListUpdate = 78;
        static final int TRANSACTION_requestSharedItemListWithFileList = 66;
        static final int TRANSACTION_requestSharedItemListWithFileListWithData = 103;
        static final int TRANSACTION_requestSharedItemSync = 31;
        static final int TRANSACTION_requestSharedItemSyncWithResolution = 82;
        static final int TRANSACTION_requestSharedItemSyncWithResolutionWithData = 97;
        static final int TRANSACTION_requestSharedItemUpdate = 60;
        static final int TRANSACTION_requestSharedItemWithGroupId = 72;
        static final int TRANSACTION_requestSpace = 15;
        static final int TRANSACTION_requestSpaceCreation = 16;
        static final int TRANSACTION_requestSpaceCreationWithData = 90;
        static final int TRANSACTION_requestSpaceDeletion = 17;
        static final int TRANSACTION_requestSpaceDeletionWithData = 91;
        static final int TRANSACTION_requestSpaceList = 18;
        static final int TRANSACTION_requestSpaceListSync = 30;
        static final int TRANSACTION_requestSpaceListSyncWithData = 96;
        static final int TRANSACTION_requestSpaceListWithData = 92;
        static final int TRANSACTION_requestSpaceUpdate = 19;
        static final int TRANSACTION_requestSpaceUpdateWithData = 93;
        static final int TRANSACTION_requestSpaceWithData = 89;
        static final int TRANSACTION_requestSync = 1;
        static final int TRANSACTION_requestThumbnailDownload = 111;
        static final int TRANSACTION_resumeShare = 25;
        static final int TRANSACTION_setBuddyActivityListRead = 47;
        static final int TRANSACTION_setDisclaimerAgreementForSocial = 104;
        static final int TRANSACTION_setShareStatusListener = 28;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMobileServiceSocial asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMobileServiceSocial)) {
                return new Proxy(iBinder);
            }
            return (IMobileServiceSocial) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1598968902) {
                Bundle bundle = null;
                Bundle bundle2 = null;
                Bundle bundle3 = null;
                Bundle bundle4 = null;
                Bundle bundle5 = null;
                Bundle bundle6 = null;
                Bundle bundle7 = null;
                Bundle bundle8 = null;
                Bundle bundle9 = null;
                Bundle bundle10 = null;
                Bundle bundle11 = null;
                Bundle bundle12 = null;
                Bundle bundle13 = null;
                Bundle bundle14 = null;
                Bundle bundle15 = null;
                Bundle bundle16 = null;
                Bundle bundle17 = null;
                Bundle bundle18 = null;
                Bundle bundle19 = null;
                PendingIntent pendingIntent = null;
                Bundle bundle20 = null;
                Bundle bundle21 = null;
                Bundle bundle22 = null;
                Bundle bundle23 = null;
                Bundle bundle24 = null;
                Bundle bundle25 = null;
                Bundle bundle26 = null;
                Bundle bundle27 = null;
                Bundle bundle28 = null;
                Bundle bundle29 = null;
                Bundle bundle30 = null;
                Bundle bundle31 = null;
                Bundle bundle32 = null;
                Bundle bundle33 = null;
                Bundle bundle34 = null;
                Bundle bundle35 = null;
                Bundle bundle36 = null;
                Bundle bundle37 = null;
                Bundle bundle38 = null;
                Bundle bundle39 = null;
                Bundle bundle40 = null;
                Bundle bundle41 = null;
                Bundle bundle42 = null;
                Bundle bundle43 = null;
                Bundle bundle44 = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestSync(ISyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 2:
                        parcel.enforceInterface(DESCRIPTOR);
                        int isServiceActivated = isServiceActivated(parcel.readInt());
                        parcel2.writeNoException();
                        parcel2.writeInt(isServiceActivated);
                        return true;
                    case 3:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestServiceActivation(parcel.readInt(), IServiceActivationResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 4:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestServiceDeactivation(parcel.readInt(), IServiceDeactivationResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 5:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestPublicBuddyInfo(parcel.readString(), IPublicBuddyInfoResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 6:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestDeleteAllActivity(IDeleteAllActivityResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 7:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupSync = requestGroupSync(parcel.readString(), IGroupSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupSync);
                        return true;
                    case 8:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupList = requestGroupList(parcel.readString(), IGroupListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupList);
                        return true;
                    case 9:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroup = requestGroup(parcel.readString(), parcel.readString(), IGroupResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroup);
                        return true;
                    case 10:
                        parcel.enforceInterface(DESCRIPTOR);
                        List<Bundle> groupList = getGroupList(parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeTypedList(groupList);
                        return true;
                    case 11:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestShareSync = requestShareSync(parcel.readString(), IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestShareSync);
                        return true;
                    case 12:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareWithPendingIntent = requestShareWithPendingIntent(parcel.readString(), parcel.readString(), parcel.createTypedArrayList(Bundle.CREATOR), IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareWithPendingIntent);
                        return true;
                    case 13:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItem = requestSharedItem(parcel.readString(), parcel.readString(), parcel.readString(), ISharedItemResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItem);
                        return true;
                    case 14:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemDeletion = requestSharedItemDeletion(parcel.readString(), parcel.readString(), parcel.readString(), ISharedItemDeletionResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemDeletion);
                        return true;
                    case 15:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSpace = requestSpace(parcel.readString(), parcel.readString(), ISpaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpace);
                        return true;
                    case 16:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString = parcel.readString();
                        String readString2 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceCreation = requestSpaceCreation(readString, readString2, bundle, ISpaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceCreation);
                        return true;
                    case 17:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSpaceDeletion = requestSpaceDeletion(parcel.readString(), parcel.readString(), ISpaceDeletionResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceDeletion);
                        return true;
                    case 18:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSpaceList = requestSpaceList(parcel.readString(), parcel.readString(), ISpaceListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceList);
                        return true;
                    case 19:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString3 = parcel.readString();
                        String readString4 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle44 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceUpdate = requestSpaceUpdate(readString3, readString4, bundle44, ISpaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceUpdate);
                        return true;
                    case 20:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestAllSpaceList = requestAllSpaceList(parcel.readString(), ISpaceListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestAllSpaceList);
                        return true;
                    case 21:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalGroupImageDownload = requestOriginalGroupImageDownload(parcel.readString(), parcel.readString(), IGroupCoverImageDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalGroupImageDownload);
                        return true;
                    case 22:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSpaceImageDownload = requestOriginalSpaceImageDownload(parcel.readString(), parcel.readString(), ISpaceCoverImageDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSpaceImageDownload);
                        return true;
                    case 23:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSharedContentsDownload = requestOriginalSharedContentsDownload(parcel.readString(), parcel.readString(), parcel.createStringArray(), IContentDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSharedContentsDownload);
                        return true;
                    case 24:
                        parcel.enforceInterface(DESCRIPTOR);
                        int pauseShare = pauseShare(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(pauseShare);
                        return true;
                    case 25:
                        parcel.enforceInterface(DESCRIPTOR);
                        int resumeShare = resumeShare(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(resumeShare);
                        return true;
                    case 26:
                        parcel.enforceInterface(DESCRIPTOR);
                        int cancelShare = cancelShare(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(cancelShare);
                        return true;
                    case 27:
                        parcel.enforceInterface(DESCRIPTOR);
                        int shareStatus = getShareStatus(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(shareStatus);
                        return true;
                    case 28:
                        parcel.enforceInterface(DESCRIPTOR);
                        int shareStatusListener = setShareStatusListener(parcel.readString(), parcel.readString(), IShareStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(shareStatusListener);
                        return true;
                    case 29:
                        parcel.enforceInterface(DESCRIPTOR);
                        clearSpaceUnreadCount(parcel.readString(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 30:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSpaceListSync = requestSpaceListSync(parcel.readString(), IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceListSync);
                        return true;
                    case 31:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemSync = requestSharedItemSync(parcel.readString(), parcel.readString(), IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemSync);
                        return true;
                    case 32:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString5 = parcel.readString();
                        Bundle bundle45 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            bundle43 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestGroupCreation = requestGroupCreation(readString5, bundle45, bundle43, IGroupInvitationResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupCreation);
                        return true;
                    case 33:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupDeletion = requestGroupDeletion(parcel.readString(), parcel.readString(), IGroupRequestResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupDeletion);
                        return true;
                    case 34:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString6 = parcel.readString();
                        String readString7 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle42 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestGroupMemberInvitation = requestGroupMemberInvitation(readString6, readString7, bundle42, IGroupInvitationResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupMemberInvitation);
                        return true;
                    case 35:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupInvitationAcceptance = requestGroupInvitationAcceptance(parcel.readString(), parcel.readString(), IGroupRequestResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupInvitationAcceptance);
                        return true;
                    case 36:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupInvitationRejection = requestGroupInvitationRejection(parcel.readString(), parcel.readString(), IGroupRequestResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupInvitationRejection);
                        return true;
                    case 37:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupMemberList = requestGroupMemberList(parcel.readString(), parcel.readString(), IMemberListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupMemberList);
                        return true;
                    case 38:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupWithInvitationList = requestGroupWithInvitationList(parcel.readString(), IGroupListWithInvitationResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupWithInvitationList);
                        return true;
                    case 39:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupMemberRemoval = requestGroupMemberRemoval(parcel.readString(), parcel.readString(), parcel.readString(), IGroupRequestResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupMemberRemoval);
                        return true;
                    case 40:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestLeave = requestLeave(parcel.readString(), parcel.readString(), IGroupRequestResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestLeave);
                        return true;
                    case 41:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupPendingInvitationCancellation = requestGroupPendingInvitationCancellation(parcel.readString(), parcel.readString(), parcel.readString(), IGroupRequestResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupPendingInvitationCancellation);
                        return true;
                    case 42:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString8 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle41 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestInstantShare = requestInstantShare(readString8, bundle41, parcel.createTypedArrayList(Bundle.CREATOR), IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestInstantShare);
                        return true;
                    case 43:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle40 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Intent intentToDisplay = getIntentToDisplay(bundle40);
                        parcel2.writeNoException();
                        if (intentToDisplay != null) {
                            parcel2.writeInt(1);
                            intentToDisplay.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 44:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle39 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestActivityList(bundle39, IActivityBundlePartialResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 45:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle38 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestActivityDeletion(bundle38, IActivityResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 46:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle37 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle buddyActivityList = getBuddyActivityList(bundle37);
                        parcel2.writeNoException();
                        if (buddyActivityList != null) {
                            parcel2.writeInt(1);
                            buddyActivityList.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 47:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle36 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle buddyActivityListRead = setBuddyActivityListRead(bundle36);
                        parcel2.writeNoException();
                        if (buddyActivityListRead != null) {
                            parcel2.writeInt(1);
                            buddyActivityListRead.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 48:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle35 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestMyActivityPrivacyUpdate(bundle35, IActivityResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 49:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestMyActivityPrivacy(IActivityBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 50:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestActivitySync(IActivityBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 51:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle34 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestActivity(bundle34, IActivityBundlePartialResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 52:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle33 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestFeedback(bundle33, IFeedbackBundlePartialResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 53:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle32 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestCommentList(bundle32, IFeedbackBundlePartialResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 54:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle31 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestCommentCreation(bundle31, IFeedbackBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 55:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle30 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestCommentUpdate(bundle30, IFeedbackBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 56:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle29 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestCommentDeletion(bundle29, IFeedbackBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 57:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle28 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestEmotionUpdate(bundle28, IFeedbackBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 58:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle27 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestEmotionMemberList(bundle27, IFeedbackBundlePartialResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 59:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle26 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle notification = getNotification(bundle26);
                        parcel2.writeNoException();
                        if (notification != null) {
                            parcel2.writeInt(1);
                            notification.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 60:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestSharedItemUpdate = requestSharedItemUpdate(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, ISharedItemUpdateResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestSharedItemUpdate);
                        return true;
                    case 61:
                        parcel.enforceInterface(DESCRIPTOR);
                        int countryTypeForAgreement = getCountryTypeForAgreement();
                        parcel2.writeNoException();
                        parcel2.writeInt(countryTypeForAgreement);
                        return true;
                    case 62:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareWithFileList = requestShareWithFileList(parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IShareResultWithFileListCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareWithFileList);
                        return true;
                    case 63:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSharedContentWithFileListDownload = requestOriginalSharedContentWithFileListDownload(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createStringArrayList(), IContentDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSharedContentWithFileListDownload);
                        return true;
                    case 64:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareUpdateWithUriList = requestShareUpdateWithUriList(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IShareResultWithFileListCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareUpdateWithUriList);
                        return true;
                    case 65:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemList = requestSharedItemList(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), ISharedItemListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemList);
                        return true;
                    case 66:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemListWithFileList = requestSharedItemListWithFileList(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), ISharedItemListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemListWithFileList);
                        return true;
                    case 67:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle25 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle isSomethingNeeded = isSomethingNeeded(bundle25);
                        parcel2.writeNoException();
                        if (isSomethingNeeded != null) {
                            parcel2.writeInt(1);
                            isSomethingNeeded.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 68:
                        parcel.enforceInterface(DESCRIPTOR);
                        Bundle serviceState = getServiceState();
                        parcel2.writeNoException();
                        if (serviceState != null) {
                            parcel2.writeInt(1);
                            serviceState.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 69:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle24 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestActivityImageList(bundle24, IBundleProgressResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 70:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle23 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestProfileImageList(bundle23, IBundleProgressResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 71:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestDelegateAuthorityOfOwner = requestDelegateAuthorityOfOwner(parcel.readString(), parcel.readString(), parcel.readString(), IGroupResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestDelegateAuthorityOfOwner);
                        return true;
                    case 72:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemWithGroupId = requestSharedItemWithGroupId(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), ISharedItemResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemWithGroupId);
                        return true;
                    case 73:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString9 = parcel.readString();
                        String readString10 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle22 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestGroupUpdate = requestGroupUpdate(readString9, readString10, bundle22, IGroupResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupUpdate);
                        return true;
                    case 74:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestActivityChanges(IActivityBundlePartialResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 75:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSharedContentsDownloadWithPath = requestOriginalSharedContentsDownloadWithPath(parcel.readString(), parcel.readString(), parcel.createStringArray(), IContentDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSharedContentsDownloadWithPath);
                        return true;
                    case 76:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemListDeletion = requestSharedItemListDeletion(parcel.readString(), parcel.readString(), parcel.createStringArrayList(), ISharedItemListDeletionResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemListDeletion);
                        return true;
                    case 77:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareWithItemFileList = requestShareWithItemFileList(parcel.readString(), parcel.readString(), parcel.createTypedArrayList(Bundle.CREATOR), IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareWithItemFileList);
                        return true;
                    case 78:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestSharedItemListUpdate = requestSharedItemListUpdate(parcel.readString(), parcel.readString(), parcel.createTypedArrayList(Bundle.CREATOR), IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestSharedItemListUpdate);
                        return true;
                    case 79:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareListUpdateWithItemFileList = requestShareListUpdateWithItemFileList(parcel.readString(), parcel.readString(), parcel.createTypedArrayList(Bundle.CREATOR), IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareListUpdateWithItemFileList);
                        return true;
                    case 80:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSharedContentWithItemFileListDownloadWithPath = requestOriginalSharedContentWithItemFileListDownloadWithPath(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createStringArrayList(), IContentDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSharedContentWithItemFileListDownloadWithPath);
                        return true;
                    case 81:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle21 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle buddyActivityCount = getBuddyActivityCount(bundle21);
                        parcel2.writeNoException();
                        if (buddyActivityCount != null) {
                            parcel2.writeInt(1);
                            buddyActivityCount.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 82:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString11 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle20 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestSharedItemSyncWithResolution(readString11, bundle20, IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 83:
                        parcel.enforceInterface(DESCRIPTOR);
                        Bundle bundle46 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            pendingIntent = (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel);
                        }
                        Bundle requestActivityPosting = requestActivityPosting(bundle46, pendingIntent, IBundleProgressResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (requestActivityPosting != null) {
                            parcel2.writeInt(1);
                            requestActivityPosting.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 84:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle19 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestActivityContentStreamingUrl(bundle19, IActivityBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 85:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle18 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        Bundle requestContentsController = requestContentsController(bundle18);
                        parcel2.writeNoException();
                        if (requestContentsController != null) {
                            parcel2.writeInt(1);
                            requestContentsController.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 86:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle17 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        requestActivityContent(bundle17, IActivityBundleResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 87:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestGroupSyncWithoutImage = requestGroupSyncWithoutImage(parcel.readString(), IGroupSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestGroupSyncWithoutImage);
                        return true;
                    case 88:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString12 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle16 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestShareSyncWithData = requestShareSyncWithData(readString12, bundle16, IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestShareSyncWithData);
                        return true;
                    case 89:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString13 = parcel.readString();
                        String readString14 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle15 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceWithData = requestSpaceWithData(readString13, readString14, bundle15, ISpaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceWithData);
                        return true;
                    case 90:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString15 = parcel.readString();
                        String readString16 = parcel.readString();
                        Bundle bundle47 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            bundle14 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceCreationWithData = requestSpaceCreationWithData(readString15, readString16, bundle47, bundle14, ISpaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceCreationWithData);
                        return true;
                    case 91:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString17 = parcel.readString();
                        String readString18 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle13 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceDeletionWithData = requestSpaceDeletionWithData(readString17, readString18, bundle13, ISpaceDeletionResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceDeletionWithData);
                        return true;
                    case 92:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString19 = parcel.readString();
                        String readString20 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle12 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceListWithData = requestSpaceListWithData(readString19, readString20, bundle12, ISpaceListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceListWithData);
                        return true;
                    case 93:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString21 = parcel.readString();
                        String readString22 = parcel.readString();
                        Bundle bundle48 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            bundle11 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceUpdateWithData = requestSpaceUpdateWithData(readString21, readString22, bundle48, bundle11, ISpaceResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceUpdateWithData);
                        return true;
                    case 94:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString23 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle10 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestAllSpaceListWithData = requestAllSpaceListWithData(readString23, bundle10, ISpaceListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestAllSpaceListWithData);
                        return true;
                    case 95:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString24 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle9 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        clearSpaceUnreadCountWithData(readString24, bundle9, parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    case 96:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString25 = parcel.readString();
                        if (parcel.readInt() != 0) {
                            bundle8 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSpaceListSyncWithData = requestSpaceListSyncWithData(readString25, bundle8, IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSpaceListSyncWithData);
                        return true;
                    case 97:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString26 = parcel.readString();
                        Bundle bundle49 = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.readInt() != 0) {
                            bundle7 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSharedItemSyncWithResolutionWithData = requestSharedItemSyncWithResolutionWithData(readString26, bundle49, bundle7, IShareSyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemSyncWithResolutionWithData);
                        return true;
                    case 98:
                        parcel.enforceInterface(DESCRIPTOR);
                        String readString27 = parcel.readString();
                        String readString28 = parcel.readString();
                        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                        if (parcel.readInt() != 0) {
                            bundle6 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        int requestSharedItemListDeletionWithData = requestSharedItemListDeletionWithData(readString27, readString28, createStringArrayList, bundle6, ISharedItemListDeletionResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemListDeletionWithData);
                        return true;
                    case 99:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareWithItemFileListWithData = requestShareWithItemFileListWithData(parcel.readString(), parcel.readString(), parcel.createTypedArrayList(Bundle.CREATOR), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareWithItemFileListWithData);
                        return true;
                    case 100:
                        parcel.enforceInterface(DESCRIPTOR);
                        String requestShareListUpdateWithItemFileListWithData = requestShareListUpdateWithItemFileListWithData(parcel.readString(), parcel.readString(), parcel.createTypedArrayList(Bundle.CREATOR), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IShareResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeString(requestShareListUpdateWithItemFileListWithData);
                        return true;
                    case 101:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSharedContentWithItemFileListDownloadWithPathWithData = requestOriginalSharedContentWithItemFileListDownloadWithPathWithData(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createStringArrayList(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IContentDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSharedContentWithItemFileListDownloadWithPathWithData);
                        return true;
                    case 102:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestOriginalSharedContentWithFileListDownloadWithData = requestOriginalSharedContentWithFileListDownloadWithData(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createStringArrayList(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, IContentDownloadingResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        parcel2.writeInt(requestOriginalSharedContentWithFileListDownloadWithData);
                        return true;
                    case 103:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestSharedItemListWithFileListWithData = requestSharedItemListWithFileListWithData(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, ISharedItemListResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestSharedItemListWithFileListWithData);
                        return true;
                    case 104:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle5 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        boolean disclaimerAgreementForSocial = setDisclaimerAgreementForSocial(bundle5);
                        parcel2.writeNoException();
                        parcel2.writeInt(disclaimerAgreementForSocial ? 1 : 0);
                        return true;
                    case 105:
                        parcel.enforceInterface(DESCRIPTOR);
                        boolean disclaimerAgreementForSocial2 = getDisclaimerAgreementForSocial();
                        parcel2.writeNoException();
                        parcel2.writeInt(disclaimerAgreementForSocial2 ? 1 : 0);
                        return true;
                    case 106:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle4 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        boolean disclaimerAgreementForService = getDisclaimerAgreementForService(bundle4);
                        parcel2.writeNoException();
                        parcel2.writeInt(disclaimerAgreementForService ? 1 : 0);
                        return true;
                    case 107:
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
                    case 108:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle3 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        boolean isServiceRegistered = isServiceRegistered(bundle3);
                        parcel2.writeNoException();
                        parcel2.writeInt(isServiceRegistered ? 1 : 0);
                        return true;
                    case 109:
                        parcel.enforceInterface(DESCRIPTOR);
                        requestBuddySync(parcel.readInt(), ISyncResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 110:
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bundle2 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                        }
                        getBuddyInfo(bundle2, IBuddyInfoResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    case 111:
                        parcel.enforceInterface(DESCRIPTOR);
                        int requestThumbnailDownload = requestThumbnailDownload(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), IDownloadThumbnailResultCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(requestThumbnailDownload);
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
        public static class Proxy implements IMobileServiceSocial {
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

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestSync(ISyncResultCallback iSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iSyncResultCallback != null ? iSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int isServiceActivated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestServiceActivation(int i, IServiceActivationResultCallback iServiceActivationResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iServiceActivationResultCallback != null ? iServiceActivationResultCallback.asBinder() : null);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestServiceDeactivation(int i, IServiceDeactivationResultCallback iServiceDeactivationResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iServiceDeactivationResultCallback != null ? iServiceDeactivationResultCallback.asBinder() : null);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestPublicBuddyInfo(String str, IPublicBuddyInfoResultCallback iPublicBuddyInfoResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iPublicBuddyInfoResultCallback != null ? iPublicBuddyInfoResultCallback.asBinder() : null);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestDeleteAllActivity(IDeleteAllActivityResultCallback iDeleteAllActivityResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iDeleteAllActivityResultCallback != null ? iDeleteAllActivityResultCallback.asBinder() : null);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupSync(String str, IGroupSyncResultCallback iGroupSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iGroupSyncResultCallback != null ? iGroupSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupList(String str, IGroupListResultCallback iGroupListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iGroupListResultCallback != null ? iGroupListResultCallback.asBinder() : null);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroup(String str, String str2, IGroupResultCallback iGroupResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iGroupResultCallback != null ? iGroupResultCallback.asBinder() : null);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public List<Bundle> getGroupList(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestShareSync(String str, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareWithPendingIntent(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItem(String str, String str2, String str3, ISharedItemResultCallback iSharedItemResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iSharedItemResultCallback != null ? iSharedItemResultCallback.asBinder() : null);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemDeletion(String str, String str2, String str3, ISharedItemDeletionResultCallback iSharedItemDeletionResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iSharedItemDeletionResultCallback != null ? iSharedItemDeletionResultCallback.asBinder() : null);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpace(String str, String str2, ISpaceResultCallback iSpaceResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iSpaceResultCallback != null ? iSpaceResultCallback.asBinder() : null);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceCreation(String str, String str2, Bundle bundle, ISpaceResultCallback iSpaceResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iSpaceResultCallback != null ? iSpaceResultCallback.asBinder() : null);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceDeletion(String str, String str2, ISpaceDeletionResultCallback iSpaceDeletionResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iSpaceDeletionResultCallback != null ? iSpaceDeletionResultCallback.asBinder() : null);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceList(String str, String str2, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iSpaceListResultCallback != null ? iSpaceListResultCallback.asBinder() : null);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceUpdate(String str, String str2, Bundle bundle, ISpaceResultCallback iSpaceResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iSpaceResultCallback != null ? iSpaceResultCallback.asBinder() : null);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestAllSpaceList(String str, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iSpaceListResultCallback != null ? iSpaceListResultCallback.asBinder() : null);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalGroupImageDownload(String str, String str2, IGroupCoverImageDownloadingResultCallback iGroupCoverImageDownloadingResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iGroupCoverImageDownloadingResultCallback != null ? iGroupCoverImageDownloadingResultCallback.asBinder() : null);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSpaceImageDownload(String str, String str2, ISpaceCoverImageDownloadingResultCallback iSpaceCoverImageDownloadingResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iSpaceCoverImageDownloadingResultCallback != null ? iSpaceCoverImageDownloadingResultCallback.asBinder() : null);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSharedContentsDownload(String str, String str2, String[] strArr, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(iContentDownloadingResultCallback != null ? iContentDownloadingResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int pauseShare(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int resumeShare(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int cancelShare(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int getShareStatus(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int setShareStatusListener(String str, String str2, IShareStatusCallback iShareStatusCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iShareStatusCallback != null ? iShareStatusCallback.asBinder() : null);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void clearSpaceUnreadCount(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceListSync(String str, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemSync(String str, String str2, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupCreation(String str, Bundle bundle, Bundle bundle2, IGroupInvitationResultCallback iGroupInvitationResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iGroupInvitationResultCallback != null ? iGroupInvitationResultCallback.asBinder() : null);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupDeletion(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iGroupRequestResultCallback != null ? iGroupRequestResultCallback.asBinder() : null);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupMemberInvitation(String str, String str2, Bundle bundle, IGroupInvitationResultCallback iGroupInvitationResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iGroupInvitationResultCallback != null ? iGroupInvitationResultCallback.asBinder() : null);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupInvitationAcceptance(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iGroupRequestResultCallback != null ? iGroupRequestResultCallback.asBinder() : null);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupInvitationRejection(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iGroupRequestResultCallback != null ? iGroupRequestResultCallback.asBinder() : null);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupMemberList(String str, String str2, IMemberListResultCallback iMemberListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iMemberListResultCallback != null ? iMemberListResultCallback.asBinder() : null);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupWithInvitationList(String str, IGroupListWithInvitationResultCallback iGroupListWithInvitationResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iGroupListWithInvitationResultCallback != null ? iGroupListWithInvitationResultCallback.asBinder() : null);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupMemberRemoval(String str, String str2, String str3, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iGroupRequestResultCallback != null ? iGroupRequestResultCallback.asBinder() : null);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestLeave(String str, String str2, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongBinder(iGroupRequestResultCallback != null ? iGroupRequestResultCallback.asBinder() : null);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupPendingInvitationCancellation(String str, String str2, String str3, IGroupRequestResultCallback iGroupRequestResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iGroupRequestResultCallback != null ? iGroupRequestResultCallback.asBinder() : null);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestInstantShare(String str, Bundle bundle, List<Bundle> list, IShareResultCallback iShareResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Intent getIntentToDisplay(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivityList(Bundle bundle, IActivityBundlePartialResultCallback iActivityBundlePartialResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iActivityBundlePartialResultCallback != null ? iActivityBundlePartialResultCallback.asBinder() : null);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivityDeletion(Bundle bundle, IActivityResultCallback iActivityResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iActivityResultCallback != null ? iActivityResultCallback.asBinder() : null);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle getBuddyActivityList(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle setBuddyActivityListRead(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestMyActivityPrivacyUpdate(Bundle bundle, IActivityResultCallback iActivityResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iActivityResultCallback != null ? iActivityResultCallback.asBinder() : null);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestMyActivityPrivacy(IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iActivityBundleResultCallback != null ? iActivityBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivitySync(IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iActivityBundleResultCallback != null ? iActivityBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivity(Bundle bundle, IActivityBundlePartialResultCallback iActivityBundlePartialResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iActivityBundlePartialResultCallback != null ? iActivityBundlePartialResultCallback.asBinder() : null);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestFeedback(Bundle bundle, IFeedbackBundlePartialResultCallback iFeedbackBundlePartialResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundlePartialResultCallback != null ? iFeedbackBundlePartialResultCallback.asBinder() : null);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestCommentList(Bundle bundle, IFeedbackBundlePartialResultCallback iFeedbackBundlePartialResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundlePartialResultCallback != null ? iFeedbackBundlePartialResultCallback.asBinder() : null);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestCommentCreation(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundleResultCallback != null ? iFeedbackBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestCommentUpdate(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundleResultCallback != null ? iFeedbackBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestCommentDeletion(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundleResultCallback != null ? iFeedbackBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestEmotionUpdate(Bundle bundle, IFeedbackBundleResultCallback iFeedbackBundleResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundleResultCallback != null ? iFeedbackBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestEmotionMemberList(Bundle bundle, IFeedbackBundlePartialResultCallback iFeedbackBundlePartialResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iFeedbackBundlePartialResultCallback != null ? iFeedbackBundlePartialResultCallback.asBinder() : null);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle getNotification(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestSharedItemUpdate(String str, String str2, String str3, Bundle bundle, ISharedItemUpdateResultCallback iSharedItemUpdateResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iSharedItemUpdateResultCallback != null ? iSharedItemUpdateResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int getCountryTypeForAgreement() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareWithFileList(String str, String str2, Bundle bundle, IShareResultWithFileListCallback iShareResultWithFileListCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException {
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
                    obtain.writeStrongBinder(iShareResultWithFileListCallback != null ? iShareResultWithFileListCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSharedContentWithFileListDownload(String str, String str2, String str3, List<String> list, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStringList(list);
                    obtain.writeStrongBinder(iContentDownloadingResultCallback != null ? iContentDownloadingResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareUpdateWithUriList(String str, String str2, String str3, Bundle bundle, IShareResultWithFileListCallback iShareResultWithFileListCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareResultWithFileListCallback != null ? iShareResultWithFileListCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemList(String str, String str2, String str3, String str4, ISharedItemListResultCallback iSharedItemListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStrongBinder(iSharedItemListResultCallback != null ? iSharedItemListResultCallback.asBinder() : null);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemListWithFileList(String str, String str2, String str3, String str4, ISharedItemListResultCallback iSharedItemListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStrongBinder(iSharedItemListResultCallback != null ? iSharedItemListResultCallback.asBinder() : null);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle isSomethingNeeded(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle getServiceState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivityImageList(Bundle bundle, IBundleProgressResultCallback iBundleProgressResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iBundleProgressResultCallback != null ? iBundleProgressResultCallback.asBinder() : null);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestProfileImageList(Bundle bundle, IBundleProgressResultCallback iBundleProgressResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iBundleProgressResultCallback != null ? iBundleProgressResultCallback.asBinder() : null);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestDelegateAuthorityOfOwner(String str, String str2, String str3, IGroupResultCallback iGroupResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iGroupResultCallback != null ? iGroupResultCallback.asBinder() : null);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemWithGroupId(String str, String str2, String str3, String str4, ISharedItemResultCallback iSharedItemResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeStrongBinder(iSharedItemResultCallback != null ? iSharedItemResultCallback.asBinder() : null);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupUpdate(String str, String str2, Bundle bundle, IGroupResultCallback iGroupResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iGroupResultCallback != null ? iGroupResultCallback.asBinder() : null);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivityChanges(IActivityBundlePartialResultCallback iActivityBundlePartialResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iActivityBundlePartialResultCallback != null ? iActivityBundlePartialResultCallback.asBinder() : null);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSharedContentsDownloadWithPath(String str, String str2, String[] strArr, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongBinder(iContentDownloadingResultCallback != null ? iContentDownloadingResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str3);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemListDeletion(String str, String str2, List<String> list, ISharedItemListDeletionResultCallback iSharedItemListDeletionResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeStrongBinder(iSharedItemListDeletionResultCallback != null ? iSharedItemListDeletionResultCallback.asBinder() : null);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareWithItemFileList(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestSharedItemListUpdate(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareListUpdateWithItemFileList(String str, String str2, List<Bundle> list, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list);
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSharedContentWithItemFileListDownloadWithPath(String str, String str2, String str3, List<String> list, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStringList(list);
                    obtain.writeStrongBinder(iContentDownloadingResultCallback != null ? iContentDownloadingResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str4);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle getBuddyActivityCount(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestSharedItemSyncWithResolution(String str, Bundle bundle, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle requestActivityPosting(Bundle bundle, PendingIntent pendingIntent, IBundleProgressResultCallback iBundleProgressResultCallback) throws RemoteException {
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
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    Bundle bundle2 = null;
                    obtain.writeStrongBinder(iBundleProgressResultCallback != null ? iBundleProgressResultCallback.asBinder() : null);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle2 = (Bundle) Bundle.CREATOR.createFromParcel(obtain2);
                    }
                    return bundle2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivityContentStreamingUrl(Bundle bundle, IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iActivityBundleResultCallback != null ? iActivityBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle requestContentsController(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestActivityContent(Bundle bundle, IActivityBundleResultCallback iActivityBundleResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iActivityBundleResultCallback != null ? iActivityBundleResultCallback.asBinder() : null);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestGroupSyncWithoutImage(String str, IGroupSyncResultCallback iGroupSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iGroupSyncResultCallback != null ? iGroupSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestShareSyncWithData(String str, Bundle bundle, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceWithData(String str, String str2, Bundle bundle, ISpaceResultCallback iSpaceResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iSpaceResultCallback != null ? iSpaceResultCallback.asBinder() : null);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceCreationWithData(String str, String str2, Bundle bundle, Bundle bundle2, ISpaceResultCallback iSpaceResultCallback) throws RemoteException {
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
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iSpaceResultCallback != null ? iSpaceResultCallback.asBinder() : null);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceDeletionWithData(String str, String str2, Bundle bundle, ISpaceDeletionResultCallback iSpaceDeletionResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iSpaceDeletionResultCallback != null ? iSpaceDeletionResultCallback.asBinder() : null);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceListWithData(String str, String str2, Bundle bundle, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iSpaceListResultCallback != null ? iSpaceListResultCallback.asBinder() : null);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceUpdateWithData(String str, String str2, Bundle bundle, Bundle bundle2, ISpaceResultCallback iSpaceResultCallback) throws RemoteException {
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
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iSpaceResultCallback != null ? iSpaceResultCallback.asBinder() : null);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestAllSpaceListWithData(String str, Bundle bundle, ISpaceListResultCallback iSpaceListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iSpaceListResultCallback != null ? iSpaceListResultCallback.asBinder() : null);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void clearSpaceUnreadCountWithData(String str, Bundle bundle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str2);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSpaceListSyncWithData(String str, Bundle bundle, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemSyncWithResolutionWithData(String str, Bundle bundle, Bundle bundle2, IShareSyncResultCallback iShareSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareSyncResultCallback != null ? iShareSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemListDeletionWithData(String str, String str2, List<String> list, Bundle bundle, ISharedItemListDeletionResultCallback iSharedItemListDeletionResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iSharedItemListDeletionResultCallback != null ? iSharedItemListDeletionResultCallback.asBinder() : null);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareWithItemFileListWithData(String str, String str2, List<Bundle> list, Bundle bundle, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public String requestShareListUpdateWithItemFileListWithData(String str, String str2, List<Bundle> list, Bundle bundle, IShareResultCallback iShareResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iShareResultCallback != null ? iShareResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSharedContentWithItemFileListDownloadWithPathWithData(String str, String str2, String str3, List<String> list, Bundle bundle, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle2, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStringList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iContentDownloadingResultCallback != null ? iContentDownloadingResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str4);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestOriginalSharedContentWithFileListDownloadWithData(String str, String str2, String str3, List<String> list, Bundle bundle, IContentDownloadingResultCallback iContentDownloadingResultCallback, PendingIntent pendingIntent, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStringList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iContentDownloadingResultCallback != null ? iContentDownloadingResultCallback.asBinder() : null);
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestSharedItemListWithFileListWithData(String str, String str2, String str3, String str4, Bundle bundle, ISharedItemListResultCallback iSharedItemListResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iSharedItemListResultCallback != null ? iSharedItemListResultCallback.asBinder() : null);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public boolean setDisclaimerAgreementForSocial(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(104, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public boolean getDisclaimerAgreementForSocial() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(105, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public boolean getDisclaimerAgreementForService(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(106, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public Bundle getDeviceAuthInfoCached() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public boolean isServiceRegistered(Bundle bundle) throws RemoteException {
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
                    this.mRemote.transact(108, obtain, obtain2, 0);
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

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void requestBuddySync(int i, ISyncResultCallback iSyncResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iSyncResultCallback != null ? iSyncResultCallback.asBinder() : null);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public void getBuddyInfo(Bundle bundle, IBuddyInfoResultCallback iBuddyInfoResultCallback) throws RemoteException {
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
                    obtain.writeStrongBinder(iBuddyInfoResultCallback != null ? iBuddyInfoResultCallback.asBinder() : null);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial
            public int requestThumbnailDownload(String str, String str2, String str3, String str4, String str5, String str6, IDownloadThumbnailResultCallback iDownloadThumbnailResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeStrongBinder(iDownloadThumbnailResultCallback != null ? iDownloadThumbnailResultCallback.asBinder() : null);
                    this.mRemote.transact(111, obtain, obtain2, 0);
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
