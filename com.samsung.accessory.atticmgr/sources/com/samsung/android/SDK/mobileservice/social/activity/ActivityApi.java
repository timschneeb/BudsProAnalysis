package com.samsung.android.sdk.mobileservice.social.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.ErrorCodeConvertor;
import com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.BooleanResult;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.IActivityResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.IDeleteAllActivityResultCallback;
import com.samsung.android.sdk.mobileservice.social.activity.result.ActivityResult;
import com.samsung.android.sdk.mobileservice.social.common.IBundleProgressResultCallback;
import com.samsung.android.sdk.mobileservice.social.group.provider.GroupContract;
import java.util.ArrayList;
import java.util.List;

public class ActivityApi extends SeMobileServiceApi {
    public static final String API_NAME = "ActivityApi";
    private static final int MAX_SIZE_FOR_REQUEST_ACTIVITYLIST = 5;
    public static final int PRIVACY_TYPE_ALL = 0;
    public static final int PRIVACY_TYPE_CONTACTS = 2;
    public static final int PRIVACY_TYPE_NOT_SELECTED = 3;
    public static final int PRIVACY_TYPE_PRIVATE = 1;
    private final int ACTIVITY_TYPE_ALL = -1;

    public interface ActivityDownloadResultCallback<T> {
        void onPartialResult(T t);

        void onResult(ActivityResult<T> activityResult);
    }

    public interface ActivityImageListResultCallback<T> {
        void onProgress(T t);

        void onResult(ActivityResult<List<T>> activityResult);
    }

    public interface ActivityResultCallback<T> {
        void onResult(ActivityResult<T> activityResult);
    }

    public interface ClearMyActivityResultCallback {
        void onResult(BooleanResult booleanResult);
    }

    private boolean isResultSuccess(CommonResultStatus commonResultStatus) {
        return commonResultStatus == null;
    }

    public ActivityApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "ActivityApi");
        checkAuthorized(0, 1);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        return new String[]{"SocialService"};
    }

    public int requestMyActivityClear(final ClearMyActivityResultCallback clearMyActivityResultCallback) {
        debugLog("requestMyActivityClear started");
        try {
            getSocialService().requestDeleteAllActivity(new IDeleteAllActivityResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IDeleteAllActivityResultCallback
                public void onSuccess() throws RemoteException {
                    ActivityApi.this.debugLog("requestMyActivityClear onSuccess");
                    ClearMyActivityResultCallback clearMyActivityResultCallback = clearMyActivityResultCallback;
                    if (clearMyActivityResultCallback != null) {
                        clearMyActivityResultCallback.onResult(new BooleanResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IDeleteAllActivityResultCallback
                public void onFailure(int i, String str) throws RemoteException {
                    ActivityApi.this.debugLog("requestMyActivityClear onFailure");
                    if (clearMyActivityResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(i);
                        ActivityApi activityApi = ActivityApi.this;
                        activityApi.debugLog("requestDeleteAllActivity onFailure (" + convertErrorcode + ", " + i + ", " + str + ")");
                        clearMyActivityResultCallback.onResult(new BooleanResult(new CommonResultStatus(convertErrorcode, str, Integer.toString(i)), false));
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

    public int requestActivityList(String str, long j, ActivityDownloadResultCallback<List<Activity>> activityDownloadResultCallback) {
        debugLog("requestActivityList : timestamp:" + j);
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        return requestActivityList(str, j, 5, activityDownloadResultCallback);
    }

    public int requestActivityList(String str, long j, int i, final ActivityDownloadResultCallback<List<Activity>> activityDownloadResultCallback) {
        debugLog("requestActivityList : timestamp:" + j + ", limit:" + i);
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (TextUtils.isEmpty(str)) {
            debugLog("guid is empty");
            return -1;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("guid", str);
            if (j > 0) {
                bundle.putLong("timestamp", j);
            }
            if (i <= 0) {
                i = 5;
            }
            bundle.putInt("limit", i);
            getSocialService().requestActivityList(bundle, new IActivityBundlePartialResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass2 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivityList onSuccess");
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), ActivityApi.this.bundleToActivityList(bundle)));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestActivityList onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onPartialResult(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivityList onPartialResult");
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onPartialResult(ActivityApi.this.bundleToActivityList(bundle));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    public int requestActivity(String str, String str2, ActivityDownloadResultCallback<Activity> activityDownloadResultCallback) {
        debugLog("requestActivity : activityId=[" + str2 + "]");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        return requestActivity(str, str2, 2, activityDownloadResultCallback);
    }

    public int requestActivity(String str, String str2, int i, final ActivityDownloadResultCallback<Activity> activityDownloadResultCallback) {
        debugLog("requestActivity : activityId=[" + str2 + "]");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            debugLog("guid or activityId is empty");
            return -1;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("guid", str);
            bundle.putString("activityId", str2);
            bundle.putInt("activityType", i);
            getSocialService().requestActivity(bundle, new IActivityBundlePartialResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass3 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivity onSuccess");
                    ActivityDownloadResultCallback activityDownloadResultCallback = activityDownloadResultCallback;
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), ActivityApi.this.bundleToActivity(bundle)));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestActivity onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onPartialResult(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivity onPartialResult");
                    ActivityDownloadResultCallback activityDownloadResultCallback = activityDownloadResultCallback;
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onPartialResult(ActivityApi.this.bundleToActivity(bundle));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    public int requestActivityImageList(List<ActivityRequest> list, final ActivityImageListResultCallback<ActivityImage> activityImageListResultCallback) {
        debugLog("requestActivityImageList");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (ActivityRequest activityRequest : list) {
                Bundle bundle = new Bundle();
                bundle.putString("guid", activityRequest.getGuid());
                bundle.putString("activityId", activityRequest.getActivityId());
                bundle.putInt("activityType", activityRequest.getActivityType());
                arrayList.add(bundle);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList("requestInfo", arrayList);
            getSocialService().requestActivityImageList(bundle2, new IBundleProgressResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass4 */

                @Override // com.samsung.android.sdk.mobileservice.social.common.IBundleProgressResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivityImageList onSuccess");
                    ActivityImageListResultCallback activityImageListResultCallback = activityImageListResultCallback;
                    if (activityImageListResultCallback != null) {
                        activityImageListResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), ActivityApi.this.bundleToActivityImageList(bundle)));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.common.IBundleProgressResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestActivityImageList onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityImageListResultCallback != null) {
                        activityImageListResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.common.IBundleProgressResultCallback
                public void onProgress(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivityImageList onProgress");
                    ActivityImageListResultCallback activityImageListResultCallback = activityImageListResultCallback;
                    if (activityImageListResultCallback != null) {
                        activityImageListResultCallback.onProgress(ActivityApi.this.bundleToActivityImage(bundle));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            debugLog("requestActivityImageList fail");
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            debugLog("requestActivityImageList fail");
            secureLog(e2);
            return -1;
        }
    }

    public int requestActivityContent(ActivityContentRequest activityContentRequest, final ActivityResultCallback<Uri> activityResultCallback) {
        debugLog("requestActivityContent");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_10_5)) {
            return -7;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("guid", activityContentRequest.getGuid());
            bundle.putString("activityId", activityContentRequest.getActivityId());
            bundle.putInt("activityType", activityContentRequest.getActivityType());
            bundle.putString("hash", activityContentRequest.getContentHash());
            getSocialService().requestActivityContent(bundle, new IActivityBundleResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass5 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivityContent onSuccess");
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), ActivityApi.this.bundleToUri(bundle, "contentUri")));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestActivityContent onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            debugLog("requestActivityContent fail");
            secureLog(e2);
            return -1;
        }
    }

    public ActivityResult<List<Activity>> getBuddyActivityList(long j, int i) {
        debugLog("getBuddyActivityList : timestamp=[" + j + "] limit=[" + i + "]");
        return getBuddyActivityList(null, -1, 3, j, i);
    }

    public ActivityResult<List<Activity>> getBuddyActivityList(int i, int i2, long j, int i3) {
        debugLog("getBuddyActivityList : activityType=[" + i + "] readType=[" + i2 + "] timestamp=[" + j + "] limit=[" + i3 + "]");
        return getBuddyActivityList(null, i, i2, j, i3);
    }

    public ActivityResult<List<Activity>> getBuddyActivityList(String str, int i, int i2, long j, int i3) {
        debugLog("getBuddyActivityList : activityType=[" + i + "] readType=[" + i2 + "] timestamp=[" + j + "] limit=[" + i3 + "]");
        ActivityResult<List<Activity>> errorActivityResult = getErrorActivityResult(1000000000);
        if (errorActivityResult != null) {
            return errorActivityResult;
        }
        try {
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(str)) {
                bundle.putString("guid", str);
            }
            if (i != -1) {
                bundle.putInt("activityType", i);
            }
            bundle.putInt("readType", i2);
            if (j > 0) {
                bundle.putLong("timestamp", j);
            }
            if (i3 > 0) {
                bundle.putInt("limit", i3);
            }
            Bundle buddyActivityList = getSocialService().getBuddyActivityList(bundle);
            CommonResultStatus bundleToResult = bundleToResult(buddyActivityList);
            if (isResultSuccess(bundleToResult)) {
                debugLog("getBuddyActivityList success");
                return new ActivityResult<>(new CommonResultStatus(1), bundleToActivityList(buddyActivityList));
            }
            debugLog("getBuddyActivityList fail");
            return new ActivityResult<>(bundleToResult, null);
        } catch (NotConnectedException e) {
            debugLog("getBuddyActivityList fail");
            secureLog(e);
            return new ActivityResult<>(new CommonResultStatus(-8), null);
        } catch (Exception e2) {
            debugLog("getBuddyActivityList fail");
            secureLog(e2);
            return new ActivityResult<>(new CommonResultStatus(-1), null);
        }
    }

    public int getBuddyActivityCount() {
        debugLog("getBuddyActivityCount");
        return getBuddyActivityCount(null, -1, 3);
    }

    public int getBuddyActivityCount(int i, int i2) {
        debugLog("getBuddyActivityCount : activityType=[" + i + "] readType=[" + i2 + "]");
        return getBuddyActivityCount(null, i, i2);
    }

    public int getBuddyActivityCount(String str, int i, int i2) {
        debugLog("getBuddyActivityCount : activityType=[" + i + "] readType=[" + i2 + "]");
        if (!isSupportedSemsAgentVersion(CommonConstants.SupportedApiMinVersion.VERSION_10_1)) {
            return -7;
        }
        try {
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(str)) {
                bundle.putString("guid", str);
            }
            if (i != -1) {
                bundle.putInt("activityType", i);
            }
            bundle.putInt("readType", i2);
            Bundle buddyActivityCount = getSocialService().getBuddyActivityCount(bundle);
            if (isResultSuccess(bundleToResult(buddyActivityCount))) {
                int i3 = buddyActivityCount.getInt("totalCount", -1);
                debugLog("getBuddyActivityCount success - totalCount:" + i3);
                return i3;
            }
            debugLog("getBuddyActivityCount fail");
            return -1;
        } catch (NotConnectedException e) {
            debugLog("getBuddyActivityCount fail");
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            debugLog("getBuddyActivityCount fail");
            secureLog(e2);
            return -1;
        }
    }

    public int setAllBuddyActivityListRead() {
        debugLog("setAllBuddyActivityListRead");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            if (isResultSuccess(bundleToResult(getSocialService().setBuddyActivityListRead(new Bundle())))) {
                debugLog("setAllBuddyActivityListRead success");
                return 1;
            }
            debugLog("setAllBuddyActivityListRead fail");
            return -1;
        } catch (NotConnectedException e) {
            debugLog("setAllBuddyActivityListRead fail");
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            debugLog("setAllBuddyActivityListRead fail");
            secureLog(e2);
            return -1;
        }
    }

    public int setBuddyActivityListRead(List<ActivityRequest> list) {
        debugLog("setBuddyActivityListRead");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            for (ActivityRequest activityRequest : list) {
                Bundle bundle = new Bundle();
                bundle.putString("guid", activityRequest.getGuid());
                bundle.putString("activityId", activityRequest.getActivityId());
                arrayList.add(bundle);
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList("requestInfo", arrayList);
            if (isResultSuccess(bundleToResult(getSocialService().setBuddyActivityListRead(bundle2)))) {
                debugLog("setBuddyActivityListRead success");
                return 1;
            }
            debugLog("setBuddyActivityListRead fail");
            return -1;
        } catch (NotConnectedException e) {
            debugLog("setBuddyActivityListRead fail");
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            debugLog("setBuddyActivityListRead fail");
            secureLog(e2);
            return -1;
        }
    }

    public int requestMyActivityDeletion(String str, ActivityResultCallback<Boolean> activityResultCallback) {
        return requestMyActivityDeletion(str, 2, activityResultCallback);
    }

    public int requestMyActivityDeletion(String str, int i, final ActivityResultCallback<Boolean> activityResultCallback) {
        debugLog("requestMyActivityDeletion : activityId=[" + str + "]");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        if (TextUtils.isEmpty(str)) {
            debugLog("activityId is empty");
            return -1;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("activityId", str);
            bundle.putInt("activityType", i);
            getSocialService().requestActivityDeletion(bundle, new IActivityResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass6 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityResultCallback
                public void onSuccess() throws RemoteException {
                    ActivityApi.this.debugLog("requestMyActivityDeletion onSuccess");
                    ActivityResultCallback activityResultCallback = activityResultCallback;
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi.this.debugLog("requestMyActivityDeletion onFailure");
                    if (activityResultCallback != null) {
                        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
                        ActivityApi activityApi = ActivityApi.this;
                        activityApi.debugLog("requestMyActivityDeletion onFailure (" + convertErrorcode + ", " + j + ", " + str + ")");
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(convertErrorcode, str, Long.toString(j)), false));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    public int requestMyActivityPrivacyTypeUpdate(int i, int i2, final ActivityResultCallback<Boolean> activityResultCallback) {
        debugLog("requestMyActivityPrivacyTypeUpdate");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("privacyType", i);
            bundle.putInt("oldPrivacyType", i2);
            getSocialService().requestMyActivityPrivacyUpdate(bundle, new IActivityResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass7 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityResultCallback
                public void onSuccess() throws RemoteException {
                    ActivityApi.this.debugLog("requestMyActivityPrivacyTypeUpdate onSuccess");
                    ActivityResultCallback activityResultCallback = activityResultCallback;
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), true));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestMyActivityPrivacyTypeUpdate onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), false));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    public int requestMyActivityPrivacyType(final ActivityResultCallback<Integer> activityResultCallback) {
        debugLog("requestMyActivityPrivacyType");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            getSocialService().requestMyActivityPrivacy(new IActivityBundleResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass8 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestMyActivityPrivacyType onSuccess");
                    if (activityResultCallback != null) {
                        Integer num = null;
                        if (bundle != null) {
                            num = Integer.valueOf(bundle.getInt("privacyType"));
                        }
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), num));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestMyActivityPrivacyType onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), null));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    public int requestActivitySync(final ActivityDownloadResultCallback<Boolean> activityDownloadResultCallback) {
        debugLog("requestActivitySync with ActivityDownloadResultCallback");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            getSocialService().requestActivityChanges(new IActivityBundlePartialResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass9 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivitySync onSuccess");
                    ActivityDownloadResultCallback activityDownloadResultCallback = activityDownloadResultCallback;
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), Boolean.TRUE));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestActivitySync onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), Boolean.FALSE));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundlePartialResultCallback
                public void onPartialResult(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivitySync onPartialResult");
                    ActivityDownloadResultCallback activityDownloadResultCallback = activityDownloadResultCallback;
                    if (activityDownloadResultCallback != null) {
                        activityDownloadResultCallback.onPartialResult(Boolean.TRUE);
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    public int requestActivitySync(final ActivityResultCallback<Boolean> activityResultCallback) {
        debugLog("requestActivitySync");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            getSocialService().requestActivitySync(new IActivityBundleResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.social.activity.ActivityApi.AnonymousClass10 */

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    ActivityApi.this.debugLog("requestActivitySync onSuccess");
                    ActivityResultCallback activityResultCallback = activityResultCallback;
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(1), Boolean.TRUE));
                    }
                }

                @Override // com.samsung.android.sdk.mobileservice.social.activity.IActivityBundleResultCallback
                public void onFailure(long j, String str) throws RemoteException {
                    ActivityApi activityApi = ActivityApi.this;
                    activityApi.debugLog("requestActivitySync onFailure : code=[" + j + "], message=[" + str + "]");
                    if (activityResultCallback != null) {
                        activityResultCallback.onResult(new ActivityResult(new CommonResultStatus(ErrorCodeConvertor.convertErrorcode(j), str, Long.toString(j)), Boolean.FALSE));
                    }
                }
            });
            return 1;
        } catch (NotConnectedException e) {
            secureLog(e);
            return -8;
        } catch (Exception e2) {
            secureLog(e2);
            return -1;
        }
    }

    private CommonResultStatus bundleToResult(Bundle bundle) {
        if (bundle == null) {
            debugLog("bundle is null : bundleToResult");
            return null;
        }
        long j = bundle.getLong("errorCode", -1);
        if (j == -1) {
            debugLog("not error : bundleToResult");
            return null;
        }
        String string = bundle.getString("errorMessage", null);
        int convertErrorcode = ErrorCodeConvertor.convertErrorcode(j);
        return new CommonResultStatus(convertErrorcode, string, Long.toString((long) convertErrorcode));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private List<Activity> bundleToActivityList(Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        if (bundle == null) {
            debugLog("bundle is null : bundleToActivityList");
            return arrayList;
        }
        ArrayList<Bundle> parcelableArrayList = bundle.getParcelableArrayList("activities");
        if (parcelableArrayList != null) {
            for (Bundle bundle2 : parcelableArrayList) {
                arrayList.add(bundleToActivity(bundle2));
            }
        }
        debugLog("bundleToActivityList size : " + arrayList.size());
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Activity bundleToActivity(Bundle bundle) {
        int i;
        int i2;
        if (bundle == null) {
            debugLog("bundle is null : bundleToActivity");
            return null;
        }
        String string = bundle.getString("activityId");
        String string2 = bundle.getString("statusMessage", null);
        String string3 = bundle.getString("memo", string2);
        String string4 = bundle.getString("activityType", null);
        if (string4 != null && TextUtils.equals(string4, "post")) {
            i2 = 4;
        } else if (!TextUtils.isEmpty(string3) || !TextUtils.isEmpty(string2)) {
            i = 1;
            return new Activity(string, string3, i, bundle.getLong(GroupContract.Group.CREATED_TIME, 0), bundle.getLong("modifiedTime", 0), bundle.getString("owner"), bundle.getString("ownerName"), bundleToUri(bundle, "activityImageContentUri"), bundleToUri(bundle, "profileImageContentUri"), bundle.getString("meta", null), bundleToContentInfoList(bundle));
        } else {
            i2 = 2;
        }
        i = i2;
        return new Activity(string, string3, i, bundle.getLong(GroupContract.Group.CREATED_TIME, 0), bundle.getLong("modifiedTime", 0), bundle.getString("owner"), bundle.getString("ownerName"), bundleToUri(bundle, "activityImageContentUri"), bundleToUri(bundle, "profileImageContentUri"), bundle.getString("meta", null), bundleToContentInfoList(bundle));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private List<ActivityImage> bundleToActivityImageList(Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        if (bundle == null) {
            debugLog("bundle is null : bundleToActivityImageList");
            return arrayList;
        }
        ArrayList<Bundle> parcelableArrayList = bundle.getParcelableArrayList("images");
        if (parcelableArrayList != null) {
            for (Bundle bundle2 : parcelableArrayList) {
                arrayList.add(bundleToActivityImage(bundle2));
            }
        }
        debugLog("bundleToActivityImageList size : " + arrayList.size());
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private ActivityImage bundleToActivityImage(Bundle bundle) {
        if (bundle == null) {
            debugLog("bundle is null : bundleToActivityImage");
            return null;
        }
        return new ActivityImage(bundle.getString("guid"), bundle.getString("activityId"), bundleToUri(bundle, "profileImageContentUri"), bundleToUri(bundle, "activityImageContentUri"));
    }

    private List<ContentInfo> bundleToContentInfoList(Bundle bundle) {
        ArrayList arrayList = new ArrayList();
        if (bundle == null) {
            debugLog("bundle is null : bundleToContentInfoList");
            return arrayList;
        }
        ArrayList<Bundle> parcelableArrayList = bundle.getParcelableArrayList("contentsInfo");
        if (parcelableArrayList != null) {
            for (Bundle bundle2 : parcelableArrayList) {
                arrayList.add(bundleToContentInfo(bundle2));
            }
        }
        debugLog("bundleToContentInfoList size : " + arrayList.size());
        return arrayList;
    }

    private ContentInfo bundleToContentInfo(Bundle bundle) {
        if (bundle != null) {
            return new ContentInfo(bundle.getString("name"), bundle.getString("hash"), bundle.getLong("size", 0), bundle.getString("mime"), bundleToUri(bundle, "contentUri"));
        }
        debugLog("bundle is null : bundleToActivityContents");
        return null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Uri bundleToUri(Bundle bundle, String str) {
        if (bundle == null) {
            debugLog("bundle is null : bundleToUri");
            return null;
        }
        String string = bundle.getString(str, null);
        if (!TextUtils.isEmpty(string)) {
            return Uri.parse(string);
        }
        return null;
    }

    private <T> ActivityResult<T> getErrorActivityResult(int i) {
        if (!isSupportedSemsAgentVersion(i)) {
            return new ActivityResult<>(new CommonResultStatus(-7), null);
        }
        return null;
    }

    public static class ActivityRequest {
        private String mActivityId;
        private int mActivityType;
        private String mGuid;

        public ActivityRequest(String str, String str2) {
            this.mGuid = str;
            this.mActivityId = str2;
            this.mActivityType = 2;
        }

        public ActivityRequest(String str, String str2, int i) {
            this.mGuid = str;
            this.mActivityId = str2;
            this.mActivityType = i;
        }

        public String getGuid() {
            return this.mGuid;
        }

        public String getActivityId() {
            return this.mActivityId;
        }

        public int getActivityType() {
            return this.mActivityType;
        }
    }

    public static class ActivityContentRequest extends ActivityRequest {
        private String mHash;

        public ActivityContentRequest(String str, String str2, int i, String str3) {
            super(str, str2, i);
            this.mHash = str3;
        }

        public String getContentHash() {
            return this.mHash;
        }
    }
}
