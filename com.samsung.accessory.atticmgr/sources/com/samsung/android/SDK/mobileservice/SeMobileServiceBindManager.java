package com.samsung.android.sdk.mobileservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.CommonUtils;
import com.samsung.android.sdk.mobileservice.common.ICommonService;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.place.IMobileServicePlace;
import com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile;
import com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial;
import com.samsung.android.sdk.mobileservice.util.SdkLog;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* access modifiers changed from: package-private */
public class SeMobileServiceBindManager implements BindChangeListener {
    private static final String BIND_CLASS_NAME_AUTH = "com.osp.app.signin.service.SemsAidlService";
    private static final String BIND_CLASS_NAME_COMMON = "com.samsung.android.mobileservice.commonservice.CommonService";
    private static final String BIND_CLASS_NAME_COMMON_FOR_SA = "com.samsung.android.samsungaccount.mobileservice.commonservice.CommonService";
    private static final String BIND_CLASS_NAME_SOCIAL = "com.samsung.android.mobileservice.social.MobileServiceSocialService";
    private static final String BIND_PACKAGE_NAME = "com.samsung.android.mobileservice";
    private static final String BIND_REQUEST_SERVICE_AUTH = "com.samsung.android.mobileservice.auth.REQUEST_SERVICE";
    private static final String BIND_REQUEST_SERVICE_COMMON = "com.samsung.android.mobileservice.common.REQUEST_SERVICE";
    private static final String BIND_REQUEST_SERVICE_PLACE = "com.samsung.android.mobileservice.place.REQUEST_SERVICE";
    private static final String BIND_REQUEST_SERVICE_PROFILE = "com.samsung.android.mobileservice.profile.REQUEST_SERVICE";
    private static final String EXTRA_APP_ID = "app_id";
    private static final long MIN_AGENT_SUPPORT_VERSION = 0;
    private static final String TAG = "SeMobileServiceBindManager";
    private static Map<String, SeMobileServiceBindManager> sMap = new HashMap();
    private Set<BindChangeListener> bindChangeListeners = new HashSet();
    private final String mAppId;
    private ServiceHandler<IMobileServiceAuth> mAuthServiceHandler = null;
    private ServiceHandler<ICommonService> mCommonServiceHandler = null;
    final VersionExchangeInfo mEmptyVersionExchangeInfo = new VersionExchangeInfo();
    private boolean mIsStandAloneSA;
    private ServiceHandler<IMobileServicePlace> mPlaceServiceHandler = null;
    private ServiceHandler<IMobileServiceProfile> mProfileServiceHandler = null;
    private ServiceHandler<ICommonService> mSaCommonServiceHandler = null;
    private ServiceHandler<IMobileServiceSocial> mSocialServiceHandler = null;
    private HashSet<String> mSupportedServices = new HashSet<>();
    private ServiceHandler<IMobileServiceSocial> mUnknownServiceHandler = new ServiceHandler<IMobileServiceSocial>() {
        /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass1 */

        /* access modifiers changed from: package-private */
        @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
        public IMobileServiceSocial asInterface(IBinder iBinder) {
            return null;
        }
    };
    private VersionExchangeInfo mVersionExchangeInfo = null;

    static synchronized SeMobileServiceBindManager get(String str, boolean z) {
        SeMobileServiceBindManager seMobileServiceBindManager;
        synchronized (SeMobileServiceBindManager.class) {
            seMobileServiceBindManager = sMap.get(str);
            if (seMobileServiceBindManager == null) {
                seMobileServiceBindManager = new SeMobileServiceBindManager(str, z);
                Map<String, SeMobileServiceBindManager> map = sMap;
                map.put("" + str, seMobileServiceBindManager);
            }
        }
        return seMobileServiceBindManager;
    }

    private SeMobileServiceBindManager(String str, boolean z) {
        this.mAppId = str;
        this.mIsStandAloneSA = z;
    }

    /* access modifiers changed from: package-private */
    public void reset(boolean z) {
        SdkLog.d(TAG, "reset");
        this.mIsStandAloneSA = z;
        this.mVersionExchangeInfo = null;
        this.mSupportedServices.clear();
    }

    private String[] getSaServiceNames() {
        return new String[]{"AuthService", "ProfileService", "PlaceService"};
    }

    private String[] getSocialServiceNames() {
        return new String[]{"SocialService"};
    }

    private String[] getAllServiceNames() {
        return new String[]{"AuthService", "ProfileService", "PlaceService", "SocialService"};
    }

    /* access modifiers changed from: package-private */
    public synchronized ServiceHandler<?> getServiceHandler(String str) {
        if (str == null) {
            str = "";
        }
        char c = 65535;
        switch (str.hashCode()) {
            case -1348797491:
                if (str.equals("AuthService")) {
                    c = 2;
                    break;
                }
                break;
            case -207817778:
                if (str.equals("PlaceService")) {
                    c = 3;
                    break;
                }
                break;
            case 153034988:
                if (str.equals("ProfileService")) {
                    c = 4;
                    break;
                }
                break;
            case 836900345:
                if (str.equals(CommonConstants.SERVICE_NAME_SA_COMMON)) {
                    c = 1;
                    break;
                }
                break;
            case 1266397800:
                if (str.equals("SocialService")) {
                    c = 5;
                    break;
                }
                break;
            case 2024019467:
                if (str.equals(CommonConstants.SERVICE_NAME_COMMON)) {
                    c = 0;
                    break;
                }
                break;
        }
        if (c == 0) {
            return getCommonServiceHandler();
        } else if (c == 1) {
            return getSaCommonServiceHandler();
        } else if (c == 2) {
            return getAuthServiceHandler();
        } else if (c == 3) {
            return getPlaceServiceHandler();
        } else if (c == 4) {
            return getProfileServiceHandler();
        } else if (c != 5) {
            return this.mUnknownServiceHandler;
        } else {
            return getSocialServiceHandler();
        }
    }

    private synchronized ServiceHandler<ICommonService> getCommonServiceHandler() {
        if (this.mCommonServiceHandler == null) {
            this.mCommonServiceHandler = new ServiceHandler<ICommonService>(this.mAppId, CommonConstants.SERVICE_NAME_COMMON, BIND_PACKAGE_NAME, BIND_CLASS_NAME_COMMON, BIND_REQUEST_SERVICE_COMMON, this) {
                /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass2 */

                /* access modifiers changed from: package-private */
                @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
                public ICommonService asInterface(IBinder iBinder) {
                    return ICommonService.Stub.asInterface(iBinder);
                }
            };
        }
        return this.mCommonServiceHandler;
    }

    private synchronized ServiceHandler<ICommonService> getSaCommonServiceHandler() {
        if (this.mSaCommonServiceHandler == null) {
            this.mSaCommonServiceHandler = new ServiceHandler<ICommonService>(this.mAppId, CommonConstants.SERVICE_NAME_SA_COMMON, CommonUtils.SAMSUNG_ACCOUNT_PACKAGE_NAME, BIND_CLASS_NAME_COMMON_FOR_SA, BIND_REQUEST_SERVICE_COMMON, this) {
                /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass3 */

                /* access modifiers changed from: package-private */
                @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
                public ICommonService asInterface(IBinder iBinder) {
                    return ICommonService.Stub.asInterface(iBinder);
                }
            };
        }
        return this.mSaCommonServiceHandler;
    }

    private String getSamsungAccountPackageName() {
        return this.mIsStandAloneSA ? CommonUtils.SAMSUNG_ACCOUNT_PACKAGE_NAME : BIND_PACKAGE_NAME;
    }

    /* access modifiers changed from: package-private */
    public synchronized ServiceHandler<IMobileServiceAuth> getAuthServiceHandler() {
        if (this.mAuthServiceHandler == null) {
            this.mAuthServiceHandler = new RequesterServiceHandler<IMobileServiceAuth>(this.mAppId, "AuthService", getSamsungAccountPackageName(), BIND_CLASS_NAME_AUTH, BIND_REQUEST_SERVICE_AUTH, this) {
                /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass4 */

                /* access modifiers changed from: package-private */
                @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
                public IMobileServiceAuth asInterface(IBinder iBinder) {
                    return IMobileServiceAuth.Stub.asInterface(iBinder);
                }
            };
        }
        return this.mAuthServiceHandler;
    }

    /* access modifiers changed from: package-private */
    public synchronized ServiceHandler<IMobileServiceProfile> getProfileServiceHandler() {
        if (this.mProfileServiceHandler == null) {
            this.mProfileServiceHandler = new RequesterServiceHandler<IMobileServiceProfile>(this.mAppId, "ProfileService", getSamsungAccountPackageName(), BIND_CLASS_NAME_AUTH, BIND_REQUEST_SERVICE_PROFILE, this) {
                /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass5 */

                /* access modifiers changed from: package-private */
                @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
                public IMobileServiceProfile asInterface(IBinder iBinder) {
                    return IMobileServiceProfile.Stub.asInterface(iBinder);
                }
            };
        }
        return this.mProfileServiceHandler;
    }

    /* access modifiers changed from: package-private */
    public synchronized ServiceHandler<IMobileServicePlace> getPlaceServiceHandler() {
        if (this.mPlaceServiceHandler == null) {
            this.mPlaceServiceHandler = new RequesterServiceHandler<IMobileServicePlace>(this.mAppId, "PlaceService", getSamsungAccountPackageName(), BIND_CLASS_NAME_AUTH, BIND_REQUEST_SERVICE_PLACE, this) {
                /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass6 */

                /* access modifiers changed from: package-private */
                @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
                public IMobileServicePlace asInterface(IBinder iBinder) {
                    return IMobileServicePlace.Stub.asInterface(iBinder);
                }
            };
        }
        return this.mPlaceServiceHandler;
    }

    /* access modifiers changed from: package-private */
    public synchronized ServiceHandler<IMobileServiceSocial> getSocialServiceHandler() {
        if (this.mSocialServiceHandler == null) {
            this.mSocialServiceHandler = new RequesterServiceHandler<IMobileServiceSocial>(this.mAppId, "SocialService", BIND_PACKAGE_NAME, BIND_CLASS_NAME_SOCIAL, null, this) {
                /* class com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.AnonymousClass7 */

                /* access modifiers changed from: package-private */
                @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
                public IMobileServiceSocial asInterface(IBinder iBinder) {
                    return IMobileServiceSocial.Stub.asInterface(iBinder);
                }
            };
        }
        return this.mSocialServiceHandler;
    }

    /* access modifiers changed from: package-private */
    public synchronized void addBindChangeListener(BindChangeListener bindChangeListener) {
        if (bindChangeListener != null) {
            this.bindChangeListeners.add(bindChangeListener);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void removeBindChangeListener(BindChangeListener bindChangeListener) {
        if (bindChangeListener != null) {
            this.bindChangeListeners.remove(bindChangeListener);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isConnectedAll(Collection<String> collection, SeMobileServiceSession seMobileServiceSession) {
        SdkLog.d(TAG, "isConnectedAll");
        if (needExchangeInfoInit()) {
            SdkLog.d(TAG, "isConnectedAll : needExchangeInfoInit");
            return false;
        }
        for (String str : collection) {
            if (TextUtils.isEmpty(str) || !this.mSupportedServices.contains(str)) {
                SdkLog.d(TAG, "isConnectedAll : not support service : " + str);
            } else if (!getServiceHandler(str).isBound(seMobileServiceSession)) {
                SdkLog.d(TAG, str + "isConnectedAll : is not bound " + str);
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void register(Collection<String> collection, SeMobileServiceSession seMobileServiceSession) {
        if (collection != null) {
            for (String str : collection) {
                getServiceHandler(str).register(seMobileServiceSession);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Set<String> bindServices(Context context, Collection<String> collection, SeMobileServiceSession seMobileServiceSession) {
        HashSet hashSet = new HashSet();
        if (collection == null) {
            return hashSet;
        }
        for (String str : collection) {
            ServiceHandler<?> serviceHandler = getServiceHandler(str);
            if (serviceHandler == null) {
                SdkLog.d(TAG, "unknown service name : " + str);
            } else if (serviceHandler.bindService(context, seMobileServiceSession)) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    /* access modifiers changed from: package-private */
    public void unbindServices(Context context, Collection<String> collection, SeMobileServiceSession seMobileServiceSession) {
        if (collection != null) {
            for (String str : collection) {
                ServiceHandler<?> serviceHandler = getServiceHandler(str);
                if (serviceHandler == null) {
                    SdkLog.d(TAG, "unbindServices : unknown service name : " + str);
                } else if (serviceHandler.unbindable(seMobileServiceSession)) {
                    serviceHandler.unbindService(context, seMobileServiceSession);
                }
            }
            boolean z = false;
            ServiceHandler[] serviceHandlerArr = {this.mAuthServiceHandler, this.mPlaceServiceHandler, this.mProfileServiceHandler, this.mSocialServiceHandler};
            int length = serviceHandlerArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                }
                ServiceHandler serviceHandler2 = serviceHandlerArr[i];
                if (!(serviceHandler2 == null || serviceHandler2.bindState == BindState.UNBOUND)) {
                    break;
                }
                i++;
            }
            if (z) {
                SdkLog.d(TAG, "all services unbound");
                reset(this.mIsStandAloneSA);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.sdk.mobileservice.BindChangeListener
    public void onBindChanged(String str, BindState bindState, BindState bindState2) {
        if ((TextUtils.equals(CommonConstants.SERVICE_NAME_COMMON, str) || TextUtils.equals(CommonConstants.SERVICE_NAME_SA_COMMON, str)) && bindState2 == BindState.BOUND) {
            onCommonSvcBound(getServiceHandler(str));
        }
        for (BindChangeListener bindChangeListener : this.bindChangeListeners) {
            if (bindChangeListener != null) {
                bindChangeListener.onBindChanged(str, bindState, bindState2);
            }
        }
    }

    private void onCommonSvcBound(ServiceHandler<? extends ICommonService> serviceHandler) {
        try {
            SdkLog.d(TAG, "do migration as first connection : " + serviceHandler.getServiceName());
            boolean doMigration = ((ICommonService) serviceHandler.getService(null)).doMigration();
            SdkLog.d(TAG, "migration result : " + doMigration);
            exchangeConfiguration();
        } catch (Exception e) {
            SdkLog.s(e);
        }
    }

    private void exchangeConfiguration() {
        SdkLog.d(TAG, "exchangeConfiguration");
        if (!needExchangeInfoInit()) {
            SdkLog.d(TAG, "exchangeConfiguration : already set");
        } else if (!getCommonServiceHandler().isBound(null) || (this.mIsStandAloneSA && !getSaCommonServiceHandler().isBound(null))) {
            SdkLog.d(TAG, "exchangeConfiguration : common service not ready");
        } else {
            VersionExchangeInfo versionExchangeInfo = new VersionExchangeInfo();
            versionExchangeInfo.setSdkVersion(SeMobileService.getSdkVersionCode());
            versionExchangeInfo.setAppId(this.mAppId);
            versionExchangeInfo.addService(getAllServiceNames());
            Bundle bundle = versionExchangeInfo.toBundle();
            try {
                versionExchangeInfo.put(getCommonServiceHandler().getService(null).exchangeConfiguration(bundle), this.mIsStandAloneSA, new String[0]);
                if (this.mIsStandAloneSA) {
                    versionExchangeInfo.put(getSaCommonServiceHandler().getService(null).exchangeConfiguration(bundle), true, getSocialServiceNames());
                }
                String[] allServiceNames = getAllServiceNames();
                for (String str : allServiceNames) {
                    int serviceVersion = versionExchangeInfo.getServiceVersion(str);
                    int serviceStatus = versionExchangeInfo.getServiceStatus(str);
                    if (serviceVersion != 0 && serviceStatus == 0) {
                        this.mSupportedServices.add(str);
                    }
                    SdkLog.d(TAG, "requested service:" + str + "[" + serviceVersion + "], status: " + serviceStatus);
                }
                this.mVersionExchangeInfo = versionExchangeInfo;
            } catch (Exception unused) {
                getVersionExchangeInfo().clear();
                SdkLog.d(TAG, "error during version exchange.");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Set<String> getUnsupportedServices() {
        return this.mSupportedServices;
    }

    /* access modifiers changed from: package-private */
    public boolean needExchangeInfoInit() {
        boolean z = this.mVersionExchangeInfo == null;
        SdkLog.d(TAG, "needExchangeInfoInit :" + z);
        return z;
    }

    /* access modifiers changed from: package-private */
    public VersionExchangeInfo getVersionExchangeInfo() {
        VersionExchangeInfo versionExchangeInfo = this.mVersionExchangeInfo;
        return versionExchangeInfo == null ? this.mEmptyVersionExchangeInfo : versionExchangeInfo;
    }

    /* access modifiers changed from: package-private */
    public SessionErrorCode getAgentStatus() {
        VersionExchangeInfo versionExchangeInfo = this.mVersionExchangeInfo;
        if (versionExchangeInfo == null) {
            return SessionErrorCode.CAUSE_AGENT_NOT_AVAILABLE;
        }
        SessionErrorCode checkExchangeInfo = checkExchangeInfo("SES", versionExchangeInfo.getAgentStatus(), (long) versionExchangeInfo.getSesVersion());
        if (checkExchangeInfo != SessionErrorCode.NO_PROBLEM) {
            return checkExchangeInfo;
        }
        if (this.mIsStandAloneSA) {
            return checkExchangeInfo("SA", versionExchangeInfo.getSaAgentStatus(), (long) versionExchangeInfo.getSaAgentVersion());
        }
        return SessionErrorCode.NO_PROBLEM;
    }

    private SessionErrorCode checkExchangeInfo(String str, int i, long j) {
        SdkLog.d(TAG, str + " version:" + j);
        if (i == 3) {
            SdkLog.d(TAG, "Force update is needed for old agent");
            return SessionErrorCode.CAUSE_AGENT_FORCE_UPDATE_REQUIRED;
        } else if (i == 2) {
            SdkLog.d(TAG, "SDK version " + SeMobileService.getSdkVersionCode() + " is lower than min " + str + " required SDK version");
            return SessionErrorCode.CAUSE_SDK_OLD_VERSION;
        } else if (j < 0) {
            SdkLog.d(TAG, str + " version " + j + " is lower than min required version " + 0L);
            return SessionErrorCode.CAUSE_AGENT_OLD_VERSION;
        } else if (i == 4) {
            SdkLog.d(TAG, "Agent is not available");
            return SessionErrorCode.CAUSE_AGENT_NOT_AVAILABLE;
        } else if (i == 0 || i == 1) {
            return SessionErrorCode.NO_PROBLEM;
        } else {
            SdkLog.d(TAG, "Agent error status is not defined.");
            return SessionErrorCode.CAUSE_UNDEFINED;
        }
    }

    /* access modifiers changed from: package-private */
    public enum BindState {
        UNBOUND,
        BINDING,
        BOUND;

        public boolean isBound() {
            return this == BOUND;
        }
    }

    static abstract class RequesterServiceHandler<T extends IInterface> extends ServiceHandler<T> {
        private RequesterServiceHandler(String str, String str2, String str3, String str4, String str5, BindChangeListener bindChangeListener) {
            super(str, str2, str3, str4, str5, bindChangeListener);
        }

        /* access modifiers changed from: package-private */
        @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
        public boolean isBound(SeMobileServiceSession seMobileServiceSession) {
            return super.isBound(seMobileServiceSession) && this.bindRequestSet.contains(seMobileServiceSession);
        }

        /* access modifiers changed from: package-private */
        @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
        public boolean unbindable(SeMobileServiceSession seMobileServiceSession) {
            return super.unbindable(seMobileServiceSession) && this.bindRequestSet.contains(seMobileServiceSession);
        }

        /* access modifiers changed from: package-private */
        @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager.ServiceHandler
        public T getService(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException {
            if (this.service != null && this.bindRequestSet.contains(seMobileServiceSession)) {
                return (T) this.service;
            }
            throw new NotConnectedException(this.serviceName + " is not connected");
        }
    }

    /* access modifiers changed from: package-private */
    public static abstract class ServiceHandler<T extends IInterface> implements ServiceConnection {
        private static final String TAG = "ServiceHandler";
        private final String action;
        private final String appId;
        Set<SeMobileServiceSession> bindRequestSet;
        private BindState bindState;
        private final BindChangeListener listener;
        private boolean needUnbindBeforeBinding;
        private final String packageName;
        T service;
        private final String serviceClassName;
        final String serviceName;

        /* access modifiers changed from: package-private */
        public abstract T asInterface(IBinder iBinder);

        private ServiceHandler() {
            this("", EnvironmentCompat.MEDIA_UNKNOWN, "", "", "", null);
        }

        private ServiceHandler(String str, String str2, String str3, String str4, String str5, BindChangeListener bindChangeListener) {
            this.service = null;
            this.bindState = BindState.UNBOUND;
            this.needUnbindBeforeBinding = false;
            this.bindRequestSet = new HashSet();
            this.appId = str;
            this.serviceName = str2;
            this.packageName = str3;
            this.serviceClassName = str4;
            this.action = str5;
            this.listener = bindChangeListener;
        }

        /* access modifiers changed from: package-private */
        public String getServiceName() {
            return this.serviceName;
        }

        /* access modifiers changed from: package-private */
        public T getService(SeMobileServiceSession seMobileServiceSession) throws NotConnectedException {
            T t = this.service;
            if (t != null) {
                return t;
            }
            throw new NotConnectedException(this.serviceName + " is not connected");
        }

        /* access modifiers changed from: package-private */
        public boolean isBound(SeMobileServiceSession seMobileServiceSession) {
            return this.bindState.isBound() && this.service != null;
        }

        /* access modifiers changed from: package-private */
        public boolean unbindable(SeMobileServiceSession seMobileServiceSession) {
            return this.bindState != BindState.UNBOUND;
        }

        /* access modifiers changed from: package-private */
        public void register(SeMobileServiceSession seMobileServiceSession) {
            this.bindRequestSet.add(seMobileServiceSession);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean bindService(Context context, SeMobileServiceSession seMobileServiceSession) {
            if (context == null) {
                log("bindService fail : context is null");
                return false;
            } else if (TextUtils.isEmpty(this.packageName)) {
                log("bindService fail : unknown service");
                return false;
            } else {
                register(seMobileServiceSession);
                if (this.bindState != BindState.UNBOUND) {
                    log("bindService : already requested : " + this.bindState);
                    if (this.bindState != BindState.BOUND || this.service != null) {
                        return true;
                    }
                    log("state is bound but service is null.");
                }
                log("bindService");
                if (this.needUnbindBeforeBinding) {
                    log("unbind first because of garbage connection");
                    try {
                        context.unbindService(this);
                    } catch (Exception e) {
                        SdkLog.e(TAG, e.getMessage());
                    }
                    this.needUnbindBeforeBinding = false;
                }
                setBindState(BindState.BINDING);
                Intent intent = new Intent();
                if (!TextUtils.isEmpty(this.action)) {
                    intent.setAction(this.action);
                }
                intent.putExtra("app_id", this.appId);
                intent.setClassName(this.packageName, this.serviceClassName);
                boolean bindService = context.bindService(intent, this, 1);
                if (!bindService) {
                    log("bindService : request fail");
                    this.bindRequestSet.remove(seMobileServiceSession);
                }
                return bindService;
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void unbindService(Context context, SeMobileServiceSession seMobileServiceSession) {
            if (context == null) {
                log("unbindService fail : context is null");
            } else if (TextUtils.isEmpty(this.packageName)) {
                log("unbindService fail : unknown service");
            } else {
                this.bindRequestSet.remove(seMobileServiceSession);
                if (this.bindState == BindState.UNBOUND) {
                    log("unbindService : not bound");
                } else if (!this.bindRequestSet.isEmpty()) {
                    log("request unbindService but other sessions are still remain. Don't unbind.");
                } else {
                    log("unbindService");
                    try {
                        context.unbindService(this);
                    } catch (Exception e) {
                        SdkLog.e(TAG, e.getMessage());
                    }
                    setBindState(BindState.UNBOUND);
                }
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            log("onServiceConnected");
            if (iBinder == null) {
                log("IBinder is null");
                setBindState(BindState.UNBOUND);
            } else if (this.bindState == BindState.UNBOUND) {
                log("service state is unbound.");
            } else {
                this.service = asInterface(iBinder);
                setBindState(BindState.BOUND);
                this.needUnbindBeforeBinding = false;
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            log("onServiceDisconnected");
            this.needUnbindBeforeBinding = true;
            setBindState(BindState.UNBOUND);
        }

        private void setBindState(BindState bindState2) {
            log("setBindState : " + this.bindState + " to " + bindState2);
            if (bindState2 == BindState.UNBOUND) {
                this.service = null;
            }
            BindState bindState3 = this.bindState;
            this.bindState = bindState2;
            BindChangeListener bindChangeListener = this.listener;
            if (bindChangeListener != null) {
                bindChangeListener.onBindChanged(this.serviceName, bindState3, bindState2);
            }
        }

        private void log(String str) {
            SdkLog.d(TAG, str + " : " + this.serviceName);
        }
    }
}
