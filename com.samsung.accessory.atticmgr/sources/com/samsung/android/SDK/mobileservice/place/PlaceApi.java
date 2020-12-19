package com.samsung.android.sdk.mobileservice.place;

import android.os.Bundle;
import android.os.RemoteException;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi;
import com.samsung.android.sdk.mobileservice.common.exception.NotAuthorizedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.common.exception.NotSupportedApiException;
import com.samsung.android.sdk.mobileservice.common.result.CommonResultStatus;
import com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;
import java.util.ArrayList;
import java.util.List;

public class PlaceApi extends SeMobileServiceApi {
    public static final String API_NAME = "PlaceApi";
    public static final String SERVICE_NAME = "PlaceService";

    public interface PlaceResultCallback<T> {
        void onResult(PlaceResult<T> placeResult);
    }

    public PlaceApi(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException, NotAuthorizedException, NotSupportedApiException {
        super(seMobileServiceSession, "PlaceApi");
        checkAuthorized(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.sdk.mobileservice.common.api.SeMobileServiceApi
    public String[] getEssentialServiceNames() {
        return new String[]{"PlaceService"};
    }

    public int requestSync(final PlaceResultCallback<Boolean> placeResultCallback) {
        debugLog("requestSync ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            placeResultCallback.onResult(new PlaceResult<>(new CommonResultStatus(200), false));
            return -7;
        }
        try {
            getPlaceService().requestSync(new IPlaceResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.place.PlaceApi.AnonymousClass1 */

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    PlaceApi.this.debugLog("requestSync onSuccess ");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(1), true));
                }

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    PlaceApi placeApi = PlaceApi.this;
                    placeApi.debugLog("requestSync onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(-1, str2, str), false));
                }
            });
            return 1;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public List<Place> getPlaceList() {
        debugLog("getPlaces ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            List<Bundle> placeList = getPlaceService().getPlaceList();
            debugLog("getPlaceList : list size=[" + placeList.size() + "] ");
            for (Bundle bundle : placeList) {
                arrayList.add(new Place(bundle));
            }
            return arrayList;
        } catch (Exception e) {
            secureLog(e);
            return new ArrayList();
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new ArrayList();
        }
    }

    public List<Place> getPlaceList(int i) {
        debugLog("getPlaces ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            List<Bundle> searchPlaces = getPlaceService().searchPlaces(i);
            debugLog("getPlaceList(" + i + ") : list size=[" + searchPlaces.size() + "] ");
            for (Bundle bundle : searchPlaces) {
                arrayList.add(new Place(bundle));
            }
            return arrayList;
        } catch (Exception e) {
            secureLog(e);
            return new ArrayList();
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return new ArrayList();
        }
    }

    public int requestPlaceCreation(PlaceRequest placeRequest, final PlaceResultCallback<Place> placeResultCallback) {
        debugLog("requestPlaceCreation ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            placeResultCallback.onResult(new PlaceResult<>(new CommonResultStatus(200), null));
            return -7;
        }
        try {
            getPlaceService().requestPlaceCreate(placeRequest.writeToBundle(), new IPlaceResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.place.PlaceApi.AnonymousClass2 */

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    Place place = new Place(bundle);
                    PlaceApi placeApi = PlaceApi.this;
                    placeApi.debugLog("requestPlaceCreation onSuccess : name=[" + place.getName() + "], createTime=[" + place.getUpdatedTime() + "]");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(1), place));
                }

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    PlaceApi placeApi = PlaceApi.this;
                    placeApi.debugLog("requestPlaceCreation onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(-1, str2, str), null));
                }
            });
            return 1;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int requestPlaceDeletion(String str, final PlaceResultCallback<Boolean> placeResultCallback) {
        debugLog("requestPlaceDeletion ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            placeResultCallback.onResult(new PlaceResult<>(new CommonResultStatus(200), false));
            return -7;
        }
        try {
            getPlaceService().requestPlaceDelete(str, new IPlaceResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.place.PlaceApi.AnonymousClass3 */

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    PlaceApi.this.debugLog("requestPlaceDeletion onSuccess ");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(1), true));
                }

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    PlaceApi placeApi = PlaceApi.this;
                    placeApi.debugLog("requestPlaceDeletion onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(-1, str2, str), false));
                }
            });
            return 1;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int requestPlaceUpdate(Place place, final PlaceResultCallback<Place> placeResultCallback) {
        debugLog("requestPlaceUpdate ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            placeResultCallback.onResult(new PlaceResult<>(new CommonResultStatus(200), null));
            return -7;
        }
        try {
            getPlaceService().requestPlaceUpdate(place.writeToBundle(), new IPlaceResultCallback.Stub() {
                /* class com.samsung.android.sdk.mobileservice.place.PlaceApi.AnonymousClass4 */

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onSuccess(Bundle bundle) throws RemoteException {
                    Place place = new Place(bundle);
                    PlaceApi placeApi = PlaceApi.this;
                    placeApi.debugLog("requestPlaceUpdate onSuccess : name=[" + place.getName() + "], updateTime=[" + place.getUpdatedTime() + "]");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(1), place));
                }

                @Override // com.samsung.android.sdk.mobileservice.place.IPlaceResultCallback
                public void onFailure(String str, String str2) throws RemoteException {
                    PlaceApi placeApi = PlaceApi.this;
                    placeApi.debugLog("requestPlaceUpdate onFailure : code=[" + str + "], message=[" + str2 + "] ");
                    placeResultCallback.onResult(new PlaceResult(new CommonResultStatus(-1, str2, str), null));
                }
            });
            return 1;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int getCurrentCount() {
        debugLog("getCurrentCount ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            int currentCount = getPlaceService().getCurrentCount();
            debugLog("getCurrentCount : count=[" + currentCount + "] ");
            return currentCount;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public int getMaxCount() {
        debugLog("getMaxCount ");
        if (!isSupportedSemsAgentVersion(1000000000)) {
            return -7;
        }
        try {
            int maxCount = getPlaceService().getMaxCount();
            debugLog("getMaxCount : count=[" + maxCount + "] ");
            return maxCount;
        } catch (Exception e) {
            secureLog(e);
            return -1;
        } catch (OutOfMemoryError unused) {
            verboseLog("OutOfMemoryError occurred");
            return -1;
        }
    }

    public static class PlaceRequest {
        private String mAddress;
        private String mBluetoothMacAddress;
        private String mBluetoothName;
        private double mLatitude;
        private double mLongitude;
        private String mName;
        private int mPlaceType;
        private String mWifiBssId;
        private String mWifiName;

        public PlaceRequest(String str, String str2, double d, double d2, String str3, String str4) {
            this(str, 4, str2, d, d2, str3, str4, null, null);
        }

        public PlaceRequest(int i, String str, double d, double d2, String str2, String str3) {
            this(null, i, str, d, d2, str2, str3, null, null);
        }

        public PlaceRequest(String str, String str2) {
            this(null, 3, null, -1.0d, -1.0d, null, null, str, str2);
        }

        private PlaceRequest(String str, int i, String str2, double d, double d2, String str3, String str4, String str5, String str6) {
            this.mName = str;
            this.mPlaceType = i;
            this.mAddress = str2;
            this.mLatitude = d;
            this.mLongitude = d2;
            this.mWifiName = str3;
            this.mWifiBssId = str4;
            this.mBluetoothName = str5;
            this.mBluetoothMacAddress = str6;
        }

        public String getName() {
            return this.mName;
        }

        public void setName(String str) {
            this.mName = str;
        }

        public int getPlaceType() {
            return this.mPlaceType;
        }

        public void setPlaceType(int i) {
            this.mPlaceType = i;
        }

        public String getAddress() {
            return this.mAddress;
        }

        public void setAddress(String str) {
            this.mAddress = str;
        }

        public double getLatitude() {
            return this.mLatitude;
        }

        public void setLatitude(double d) {
            this.mLatitude = d;
        }

        public double getLongitude() {
            return this.mLongitude;
        }

        public void setLongitude(double d) {
            this.mLongitude = d;
        }

        public String getWifiName() {
            return this.mWifiName;
        }

        public void setWifiName(String str) {
            this.mWifiName = str;
        }

        public String getWifiBssId() {
            return this.mWifiBssId;
        }

        public void setWifiBssId(String str) {
            this.mWifiBssId = str;
        }

        public String getBluetoothName() {
            return this.mBluetoothName;
        }

        public void setBluetoothName(String str) {
            this.mBluetoothName = str;
        }

        public String getBluetoothMacAddress() {
            return this.mBluetoothMacAddress;
        }

        public void setBluetoothMacAddress(String str) {
            this.mBluetoothMacAddress = str;
        }

        /* access modifiers changed from: package-private */
        public Bundle writeToBundle() {
            Bundle bundle = new Bundle();
            bundle.putString("name", this.mName);
            bundle.putInt("category", this.mPlaceType);
            bundle.putString(BuddyContract.Email.ADDRESS, this.mAddress);
            bundle.putDouble("latitude", this.mLatitude);
            bundle.putDouble("longitude", this.mLongitude);
            bundle.putString("wifi_name", this.mWifiName);
            bundle.putString("wifi_bss_id", this.mWifiBssId);
            bundle.putString("bluetooth_name", this.mBluetoothName);
            bundle.putString("bluetooth_mac_address", this.mBluetoothMacAddress);
            return bundle;
        }
    }
}
