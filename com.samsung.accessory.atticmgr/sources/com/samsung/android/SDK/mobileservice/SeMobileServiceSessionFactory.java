package com.samsung.android.sdk.mobileservice;

import android.content.Context;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.CommonUtils;
import com.samsung.android.sdk.mobileservice.util.SdkLog;
import java.util.HashSet;

public class SeMobileServiceSessionFactory {
    private static final String TAG = "SeMobileServiceSessionFactory";
    private String mAppId;
    private SeMobileServiceSession.ConnectionResultCallback mConnectionResultCallback;
    private Context mCtx;
    private HashSet<String> mServiceList = new HashSet<>();

    public SeMobileServiceSessionFactory(Context context, SeMobileServiceSession.ConnectionResultCallback connectionResultCallback) {
        this.mCtx = context;
        this.mConnectionResultCallback = connectionResultCallback;
        this.mServiceList.clear();
        this.mServiceList.add("AuthService");
        if (context != null) {
            if (CommonUtils.isStandAloneSamsungAccountSupported(context)) {
                this.mServiceList.add("SocialService");
            }
            String metaData = CommonUtils.getMetaData(context, context.getPackageName(), CommonConstants.META_DATA_APP_ID);
            this.mAppId = metaData == null ? CommonUtils.getMetaData(context, context.getPackageName(), CommonConstants.META_DATA_ACCOUNT_APP_ID) : metaData;
        }
    }

    public SeMobileServiceSessionFactory addService(String str) {
        SdkLog.d(TAG, "add " + str + " in factory");
        this.mServiceList.add(str);
        return this;
    }

    public SeMobileServiceSessionFactory setAppId(String str) {
        SdkLog.d(TAG, "set " + str + " in factory");
        this.mAppId = str;
        return this;
    }

    public SeMobileServiceSession build() {
        Context context = this.mCtx;
        if (context == null) {
            SdkLog.d(TAG, "context is null");
            return null;
        }
        SeMobileServiceSessionImpl seMobileServiceSessionImpl = new SeMobileServiceSessionImpl(context, this.mServiceList, this.mAppId, this.mConnectionResultCallback);
        SdkLog.d(TAG, "build " + SdkLog.getReference(seMobileServiceSessionImpl));
        return seMobileServiceSessionImpl;
    }
}
