package com.samsung.android.sdk.mobileservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.android.sdk.mobileservice.SeMobileServiceBindManager;
import com.samsung.android.sdk.mobileservice.SeMobileServiceSession;
import com.samsung.android.sdk.mobileservice.auth.IMobileServiceAuth;
import com.samsung.android.sdk.mobileservice.common.CommonConstants;
import com.samsung.android.sdk.mobileservice.common.CommonUtils;
import com.samsung.android.sdk.mobileservice.common.exception.NotConnectedException;
import com.samsung.android.sdk.mobileservice.place.IMobileServicePlace;
import com.samsung.android.sdk.mobileservice.profile.IMobileServiceProfile;
import com.samsung.android.sdk.mobileservice.social.IMobileServiceSocial;
import com.samsung.android.sdk.mobileservice.util.SdkLog;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SeMobileServiceSessionImpl implements SeMobileServiceSession, BindChangeListener {
    private static final int CONNECTION_TIMEOUT = 20000;
    private static final int MSG_FAILURE = 2;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_TIMEOUT = 100;
    private static final String TAG = "SeMobileServiceSession";
    private HashSet<String> mAddedServices = new HashSet<>();
    private String mAppId;
    private SeMobileServiceBindManager mBindManager;
    private Handler mConnectionCallbackHandler = new Handler(Looper.getMainLooper()) {
        /* class com.samsung.android.sdk.mobileservice.SeMobileServiceSessionImpl.AnonymousClass1 */

        public void handleMessage(Message message) {
            Message obtain = Message.obtain(message);
            int i = obtain.what;
            boolean z = true;
            if (i != 1) {
                if (i == 2) {
                    int i2 = obtain.arg1;
                    if (SeMobileServiceSessionImpl.this.mConnectionResultCallback != null) {
                        SeMobileServiceSessionImpl.this.mConnectionResultCallback.onFailure(i2);
                    }
                } else if (i == 100) {
                    SeMobileServiceSessionImpl.this.onConnectFail(SessionErrorCode.CAUSE_CONNECT_TIMEOUT.getValue());
                }
            } else if (SeMobileServiceSessionImpl.this.mConnectionResultCallback != null) {
                SessionErrorCode agentStatus = SeMobileServiceSessionImpl.this.mBindManager.getAgentStatus();
                if (agentStatus == SessionErrorCode.NO_PROBLEM) {
                    HashMap<String, Integer> hashMap = new HashMap<>();
                    Iterator it = SeMobileServiceSessionImpl.this.mAddedServices.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        int serviceStatus = SeMobileServiceSessionImpl.this.getVersionExchangeInfoOnSession().getServiceStatus(str);
                        hashMap.put(str, Integer.valueOf(serviceStatus));
                        if (serviceStatus != 0) {
                            z = false;
                        }
                    }
                    synchronized (this) {
                        SeMobileServiceSessionImpl.this.mConnectionResultCallback.onSuccess(hashMap, z);
                    }
                    return;
                }
                SeMobileServiceSessionImpl.this.onConnectFail(agentStatus.getValue());
            }
        }
    };
    private SeMobileServiceSession.ConnectionResultCallback mConnectionResultCallback = null;
    private Context mContext;
    private boolean mIsStandAloneSA;
    private SeMobileServiceSession.OnAgentUpdatedListener mOnAgentUpdatedListener = null;
    private BroadcastReceiver mReceiver = null;
    private SeMobileServiceSession.ServiceConnectionListener mServiceConnectionListener = null;
    private VersionExchangeInfo mVersionExchangeInfo = null;

    SeMobileServiceSessionImpl(Context context, HashSet<String> hashSet, String str, SeMobileServiceSession.ConnectionResultCallback connectionResultCallback) {
        this.mContext = context.getApplicationContext();
        this.mConnectionResultCallback = connectionResultCallback;
        this.mAddedServices.addAll(hashSet);
        this.mAppId = str;
        this.mIsStandAloneSA = CommonUtils.isStandAloneSamsungAccountSupported(this.mContext);
        this.mBindManager = SeMobileServiceBindManager.get(this.mAppId, this.mIsStandAloneSA);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private VersionExchangeInfo getVersionExchangeInfoOnSession() {
        VersionExchangeInfo versionExchangeInfo = this.mVersionExchangeInfo;
        if (versionExchangeInfo != null) {
            return versionExchangeInfo;
        }
        SdkLog.d(TAG, "getVersionExchangeInfoOnSession: mVersionExchangeInfo is null");
        return this.mBindManager.mEmptyVersionExchangeInfo;
    }

    private void sendSuccessCallback() {
        if (this.mConnectionCallbackHandler.hasMessages(100)) {
            this.mConnectionCallbackHandler.removeMessages(100);
            Handler handler = this.mConnectionCallbackHandler;
            handler.sendMessage(handler.obtainMessage(1));
        }
    }

    private void sendErrorCallback(int i) {
        this.mConnectionCallbackHandler.removeMessages(100);
        Handler handler = this.mConnectionCallbackHandler;
        handler.sendMessage(handler.obtainMessage(2, i, 0));
    }

    private void setCallbackTimeout() {
        this.mConnectionCallbackHandler.removeMessages(100);
        Handler handler = this.mConnectionCallbackHandler;
        handler.sendMessageDelayed(handler.obtainMessage(100), 20000);
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public void connect() {
        SdkLog.d(TAG, "connect " + SdkLog.getReference(this));
        SessionErrorCode sessionErrorCode = SessionErrorCode.NO_PROBLEM;
        SessionErrorCode isAgentInstalled = isAgentInstalled();
        if (isAgentInstalled != SessionErrorCode.NO_PROBLEM) {
            sendErrorCallback(isAgentInstalled.getValue());
            SdkLog.d(TAG, "MobileService agent is not installed." + SdkLog.getReference(this));
            return;
        }
        registerAgentUpdateReceiver();
        connectInternal();
    }

    private void registerAgentUpdateReceiver() {
        if (this.mReceiver == null) {
            this.mReceiver = new SesPackageEventReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiver(this.mReceiver, intentFilter);
        }
    }

    private void unregisterAgentUpdateReceiver() {
        BroadcastReceiver broadcastReceiver = this.mReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mReceiver = null;
        }
    }

    private Set<String> getCommonServices() {
        HashSet hashSet = new HashSet();
        hashSet.add(CommonConstants.SERVICE_NAME_COMMON);
        if (this.mIsStandAloneSA) {
            hashSet.add(CommonConstants.SERVICE_NAME_SA_COMMON);
        }
        return hashSet;
    }

    private synchronized void connectInternal() {
        SdkLog.d(TAG, "connectInternal" + SdkLog.getReference(this));
        setCallbackTimeout();
        this.mBindManager.addBindChangeListener(this);
        if (this.mBindManager.needExchangeInfoInit()) {
            Set<String> commonServices = getCommonServices();
            if (this.mBindManager.bindServices(this.mContext, commonServices, this).size() != commonServices.size()) {
                onConnectFail(SessionErrorCode.CAUSE_AGENT_NOT_AVAILABLE.getValue());
                return;
            }
        }
        this.mBindManager.register(this.mAddedServices, this);
        if (isConnectedAll()) {
            onConnectComplete();
        } else {
            this.mBindManager.bindServices(this.mContext, this.mAddedServices, this);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onConnectFail(int i) {
        SdkLog.d(TAG, "onConnectFail : " + i + ":" + SdkLog.getReference(this));
        disconnectInternal();
        sendErrorCallback(i);
    }

    private synchronized void disconnectInternal() {
        SdkLog.d(TAG, "disconnectInternal " + SdkLog.getReference(this));
        this.mBindManager.removeBindChangeListener(this);
        HashSet hashSet = new HashSet(getCommonServices());
        hashSet.addAll(this.mAddedServices);
        this.mBindManager.unbindServices(this.mContext, hashSet, this);
    }

    private boolean isFirstConnect(SeMobileServiceBindManager.BindState bindState, SeMobileServiceBindManager.BindState bindState2) {
        return bindState == SeMobileServiceBindManager.BindState.BINDING && bindState2 == SeMobileServiceBindManager.BindState.BOUND;
    }

    private boolean isConnectedAll() {
        return this.mBindManager.isConnectedAll(this.mAddedServices, this);
    }

    @Override // com.samsung.android.sdk.mobileservice.BindChangeListener
    public void onBindChanged(String str, SeMobileServiceBindManager.BindState bindState, SeMobileServiceBindManager.BindState bindState2) {
        if (this.mAddedServices.contains(str)) {
            onAddedSvcBindChanged(str, bindState, bindState2);
        }
        if (bindState2 == SeMobileServiceBindManager.BindState.BOUND) {
            onConnectComplete();
        }
    }

    private void onConnectComplete() {
        if (!isConnectedAll()) {
            SdkLog.d(TAG, "onConnectComplete : not connected all " + SdkLog.getReference(this));
        } else if (this.mBindManager.needExchangeInfoInit()) {
            SdkLog.d(TAG, "onConnectComplete : need exchange info " + SdkLog.getReference(this));
        } else {
            SdkLog.d(TAG, "onConnectComplete" + SdkLog.getReference(this));
            this.mVersionExchangeInfo = this.mBindManager.getVersionExchangeInfo();
            sendSuccessCallback();
        }
    }

    private void onAddedSvcBindChanged(String str, SeMobileServiceBindManager.BindState bindState, SeMobileServiceBindManager.BindState bindState2) {
        if (!isFirstConnect(bindState, bindState2)) {
            int i = bindState2 == SeMobileServiceBindManager.BindState.BOUND ? 1 : -1;
            SeMobileServiceSession.ServiceConnectionListener serviceConnectionListener = this.mServiceConnectionListener;
            if (serviceConnectionListener != null) {
                serviceConnectionListener.onChanged(i, str);
            }
        }
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public void reconnect() {
        SdkLog.d(TAG, "reconnect " + SdkLog.getReference(this));
        if (isConnectedAll()) {
            SdkLog.d(TAG, "reconnect : already connected" + SdkLog.getReference(this));
            return;
        }
        connectInternal();
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public void disconnect() {
        SdkLog.d(TAG, "disconnect " + SdkLog.getReference(this));
        try {
            unregisterAgentUpdateReceiver();
        } catch (Exception unused) {
            SdkLog.d(TAG, "receiver is not registered. " + SdkLog.getReference(this));
        }
        this.mVersionExchangeInfo = null;
        disconnectInternal();
        SdkLog.d(TAG, "disconnect done " + SdkLog.getReference(this));
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public boolean isAddedService(String str) {
        return this.mAddedServices.contains(str);
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public boolean isServiceConnected(String str) {
        return this.mBindManager.getServiceHandler(str).isBound(this);
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public boolean isSupportedApi(String str) {
        return getSeMobileServiceSupportApiVersion(str) > 0;
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public boolean isSessionConnected() {
        return isConnectedAll();
    }

    public Context getContext() {
        return this.mContext;
    }

    public IMobileServiceAuth getAuthService() throws NotConnectedException {
        return this.mBindManager.getAuthServiceHandler().getService(this);
    }

    public IMobileServiceProfile getProfileService() throws NotConnectedException {
        return this.mBindManager.getProfileServiceHandler().getService(this);
    }

    public IMobileServicePlace getPlaceService() throws NotConnectedException {
        return this.mBindManager.getPlaceServiceHandler().getService(this);
    }

    public IMobileServiceSocial getSocialService() throws NotConnectedException {
        return this.mBindManager.getSocialServiceHandler().getService(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        com.samsung.android.sdk.mobileservice.util.SdkLog.d(com.samsung.android.sdk.mobileservice.SeMobileServiceSessionImpl.TAG, "getSocialService() return null! " + com.samsung.android.sdk.mobileservice.util.SdkLog.getReference(r5));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005c, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0073, code lost:
        r2 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0073 A[ExcHandler: RemoteException | NullPointerException (e java.lang.Throwable), Splitter:B:1:0x0003] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getAuthorized() {
        /*
        // Method dump skipped, instructions count: 154
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.mobileservice.SeMobileServiceSessionImpl.getAuthorized():int");
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public void setSessionListener(SeMobileServiceSession.ServiceConnectionListener serviceConnectionListener) {
        this.mServiceConnectionListener = serviceConnectionListener;
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public void setOnAgentUpdatedListener(SeMobileServiceSession.OnAgentUpdatedListener onAgentUpdatedListener) {
        this.mOnAgentUpdatedListener = onAgentUpdatedListener;
    }

    public int getSeMobileServiceSupportApiVersion(String str) {
        return getVersionExchangeInfoOnSession().getApiVersion(str);
    }

    private SessionErrorCode isAgentInstalled() {
        if (!SeMobileService.isAgentInstalled(getContext())) {
            return SessionErrorCode.CAUSE_AGENT_NOT_INSTALLED;
        }
        if (!this.mIsStandAloneSA || SeMobileService.isSaAgentInstalled(getContext())) {
            return SessionErrorCode.NO_PROBLEM;
        }
        return SessionErrorCode.CAUSE_AGENT_NOT_INSTALLED;
    }

    /* access modifiers changed from: private */
    public class SesPackageEventReceiver extends BroadcastReceiver {
        private SesPackageEventReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                SdkLog.d(SeMobileServiceSessionImpl.TAG, "onReceive - receive android.intent.action.PACKAGE_ADDED");
                Uri data = intent.getData();
                if (data != null) {
                    String schemeSpecificPart = data.getSchemeSpecificPart();
                    if ("com.samsung.android.mobileservice".equals(schemeSpecificPart) || (SeMobileServiceSessionImpl.this.mIsStandAloneSA && CommonUtils.SAMSUNG_ACCOUNT_PACKAGE_NAME.equals(schemeSpecificPart))) {
                        SdkLog.d(SeMobileServiceSessionImpl.TAG, "onReceive - package is " + schemeSpecificPart);
                        if (SeMobileServiceSessionImpl.this.mOnAgentUpdatedListener != null) {
                            SeMobileServiceSessionImpl.this.mOnAgentUpdatedListener.onAgentUpdated();
                        }
                        SeMobileServiceSessionImpl.this.mIsStandAloneSA = CommonUtils.isStandAloneSamsungAccountSupported(context);
                        SeMobileServiceSessionImpl.this.mBindManager.reset(SeMobileServiceSessionImpl.this.mIsStandAloneSA);
                    }
                }
            }
        }
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public int getServiceStatus(String str) {
        return getVersionExchangeInfoOnSession().getServiceStatus(str);
    }

    public String getAppId() {
        return this.mAppId;
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public long getLatestAgentVersionInGalaxyApps() {
        SdkLog.d(TAG, "getLatestSaAgentVersionInGalaxyApps");
        return getVersionExchangeInfoOnSession().getAgentLastestVersionInGalaxyApps();
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public long getLatestSaAgentVersionInGalaxyApps() {
        SdkLog.d(TAG, "getLatestSaAgentVersionInGalaxyApps");
        return getVersionExchangeInfoOnSession().getSaAgentLastestVersionInGalaxyApps();
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public long getSamsungExperienceServiceAgentVersion() {
        SdkLog.d(TAG, "getSamsungExperienceServiceAgentVersion");
        return (long) getVersionExchangeInfoOnSession().getSesVersion();
    }

    @Override // com.samsung.android.sdk.mobileservice.SeMobileServiceSession
    public long getSamsungAccountAgentVersion() {
        SdkLog.d(TAG, "getSamsungAccountAgentVersion");
        return (long) getVersionExchangeInfoOnSession().getSaAgentVersion();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
    }

    public boolean isSupportedSemsAgentVersion(int i) {
        SdkLog.d(TAG, "isSupportedSemsAgentVersion");
        boolean z = getVersionExchangeInfoOnSession().getSesVersion() >= i;
        if (!z) {
            SdkLog.d(TAG, "isSupportedSemsAgentVersion: not support version");
        }
        return z;
    }

    public boolean isSupportedSaAgentVersion(int i) {
        SdkLog.d(TAG, "isSupportedSaAgentVersion");
        boolean z = getVersionExchangeInfoOnSession().getSaAgentVersion() >= i;
        if (!z) {
            SdkLog.d(TAG, "isSupportedSaAgentVersion: not support version");
        }
        return z;
    }
}
