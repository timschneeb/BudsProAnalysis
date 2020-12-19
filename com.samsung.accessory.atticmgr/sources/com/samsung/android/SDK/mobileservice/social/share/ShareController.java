package com.samsung.android.sdk.mobileservice.social.share;

import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial;
import com.samsung.android.sdk.mobileservice.social.share.IShareStatusCallback;
import com.samsung.android.sdk.mobileservice.util.SdkLog;

public class ShareController {
    public static final int SHARE_COMPLETE = 2;
    public static final int SHARE_IN_PROGRESS = 100;
    public static final int SHARE_NONE = -1;
    public static final int SHARE_PAUSED = 1;
    private static final String TAG = "ShareController";
    private final ShareControllerApiPicker mApi;
    private ShareStatusListener mListener;
    private String mRequestId;

    /* access modifiers changed from: package-private */
    public interface ShareControllerApiPicker {
        String getAppId();

        String getReference();

        IMobileServiceSocial getSocialService() throws NotConnectedException;
    }

    public interface ShareStatusListener {
        void onComplete(ShareSnapshot shareSnapshot);

        void onPause(ShareSnapshot shareSnapshot);

        void onResume(ShareSnapshot shareSnapshot);
    }

    ShareController(ShareControllerApiPicker shareControllerApiPicker, String str) {
        SdkLog.d(TAG, "ShareController " + shareControllerApiPicker.getReference());
        this.mApi = shareControllerApiPicker;
        this.mRequestId = str;
    }

    public int pause() {
        SdkLog.d(TAG, "pause : mRequestId=[" + this.mRequestId + "] " + SdkLog.getReference(this.mListener));
        if (this.mApi.getAppId() == null) {
            SdkLog.d(TAG, "app id is null " + this.mApi.getReference());
            return -1;
        }
        try {
            this.mApi.getSocialService().pauseShare(this.mApi.getAppId(), this.mRequestId);
            return 1;
        } catch (RemoteException e) {
            SdkLog.s(e);
            return -1;
        } catch (NotConnectedException e2) {
            SdkLog.s(e2);
            return -8;
        }
    }

    public int resume() {
        SdkLog.d(TAG, "resume : mRequestId=[" + this.mRequestId + "] " + SdkLog.getReference(this.mListener));
        if (this.mApi.getAppId() == null) {
            SdkLog.d(TAG, "app id is null " + this.mApi.getReference());
            return -1;
        }
        try {
            this.mApi.getSocialService().resumeShare(this.mApi.getAppId(), this.mRequestId);
            return 1;
        } catch (RemoteException e) {
            SdkLog.s(e);
            return -1;
        } catch (NotConnectedException e2) {
            SdkLog.s(e2);
            return -8;
        }
    }

    public int cancel() {
        SdkLog.d(TAG, "cancel : mRequestId=[" + this.mRequestId + "] " + SdkLog.getReference(this.mListener));
        if (this.mApi.getAppId() == null) {
            SdkLog.d(TAG, "app id is null " + this.mApi.getReference());
            return -1;
        }
        try {
            this.mApi.getSocialService().cancelShare(this.mApi.getAppId(), this.mRequestId);
            return 1;
        } catch (RemoteException e) {
            SdkLog.s(e);
            return -1;
        } catch (NotConnectedException e2) {
            SdkLog.s(e2);
            return -8;
        }
    }

    public int getStatus() {
        if (this.mApi.getAppId() == null) {
            SdkLog.d(TAG, "app id is null " + this.mApi.getReference());
            return -1;
        }
        try {
            int shareStatus = this.mApi.getSocialService().getShareStatus(this.mApi.getAppId(), this.mRequestId);
            SdkLog.d(TAG, "getStatus : status=[" + shareStatus + "], mRequestId=[" + this.mRequestId + "] " + SdkLog.getReference(this.mListener));
            return shareStatus;
        } catch (RemoteException e) {
            SdkLog.s(e);
            return -1;
        } catch (NotConnectedException e2) {
            SdkLog.s(e2);
            return -8;
        }
    }

    public int setShareStatusListener(ShareStatusListener shareStatusListener) {
        SdkLog.d(TAG, "setShareStatusListener : mRequestId=[" + this.mRequestId + "] " + SdkLog.getReference(this.mListener));
        if (this.mApi.getAppId() == null) {
            SdkLog.d(TAG, "app id is null " + this.mApi.getReference());
            return -1;
        } else if (shareStatusListener == null || this.mListener != null) {
            this.mListener = shareStatusListener;
            return 1;
        } else {
            AnonymousClass1 r1 = new IShareStatusCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.share.ShareController.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareStatusCallback
                public void onPause(Bundle bundle) throws RemoteException {
                    SdkLog.d(ShareController.TAG, "setShareStatusListener onPause : mRequestId=[" + ShareController.this.mRequestId + "] " + SdkLog.getReference(ShareController.this.mListener));
                    if (ShareController.this.mListener != null) {
                        ShareController.this.mListener.onPause(new ShareSnapshot(bundle.getLong("totalBytes", 0), bundle.getLong("totalBytesTransferred", 0), bundle.getInt("totalFileCount", 0), bundle.getInt("totalFileCountTransferred", 0), bundle.getLong("currentFileBytes", 0), bundle.getLong("currentFileBytesTransferred", 0), bundle.getInt("currentFileIndex", 0)));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareStatusCallback
                public void onResume(Bundle bundle) throws RemoteException {
                    SdkLog.d(ShareController.TAG, "setShareStatusListener onResume : mRequestId=[" + ShareController.this.mRequestId + "] " + SdkLog.getReference(ShareController.this.mListener));
                    if (ShareController.this.mListener != null) {
                        ShareController.this.mListener.onResume(new ShareSnapshot(bundle.getLong("totalBytes", 0), bundle.getLong("totalBytesTransferred", 0), bundle.getInt("totalFileCount", 0), bundle.getInt("totalFileCountTransferred", 0), bundle.getLong("currentFileBytes", 0), bundle.getLong("currentFileBytesTransferred", 0), bundle.getInt("currentFileIndex", 0)));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.share.IShareStatusCallback
                public void onComplete(Bundle bundle) throws RemoteException {
                    SdkLog.d(ShareController.TAG, "setShareStatusListener onComplete : mRequestId=[" + ShareController.this.mRequestId + "] " + SdkLog.getReference(ShareController.this.mListener));
                    if (ShareController.this.mListener != null) {
                        ShareController.this.mListener.onComplete(new ShareSnapshot(bundle.getLong("totalBytes", 0), bundle.getLong("totalBytesTransferred", 0), bundle.getInt("totalFileCount", 0), bundle.getInt("totalFileCountTransferred", 0), bundle.getLong("currentFileBytes", 0), bundle.getLong("currentFileBytesTransferred", 0), bundle.getInt("currentFileIndex", 0)));
                    }
                }
            };
            this.mListener = shareStatusListener;
            try {
                this.mApi.getSocialService().setShareStatusListener(this.mApi.getAppId(), this.mRequestId, r1);
                return 1;
            } catch (RemoteException e) {
                SdkLog.s(e);
                return -1;
            } catch (NotConnectedException e2) {
                SdkLog.s(e2);
                return -8;
            }
        }
    }
}
