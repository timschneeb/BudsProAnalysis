package com.samsung.android.sdk.bixby2.provider;

import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import androidx.work.WorkRequest;
import com.accessorydm.interfaces.XCommonInterface;
import com.samsung.android.fotaagent.update.UpdateInterface;
import com.samsung.android.sdk.bixby2.BuildConfig;
import com.samsung.android.sdk.bixby2.LogUtil;
import com.samsung.android.sdk.bixby2.Sbixby;
import com.samsung.android.sdk.bixby2.action.ActionHandler;
import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import com.samsung.android.sdk.bixby2.receiver.ApplicationTriggerReceiver;
import com.samsung.android.sdk.bixby2.state.StateHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CapsuleProvider extends ContentProvider {
    private static final String ACTION_APPLICATION_TRIGGER = "com.samsung.android.sdk.bixby2.ACTION_APPLICATION_TRIGGER";
    private static final int ACTION_EXECUTION_FAILURE = -1;
    private static final int ACTION_EXECUTION_SUCCESS = 0;
    private static final int ACTION_EXECUTION_TIMEOUT = 30000;
    private static final int ACTION_NOT_IMPLEMENTED = -2;
    private static final String APP_CONTENT = "result";
    private static final String PENDING_INTENT = "pending_intent";
    private static final String STATUS_CODE = "status_code";
    private static final String STATUS_MESSAGE = "status_message";
    private static final String TAG = (CapsuleProvider.class.getSimpleName() + "_" + BuildConfig.VERSION_NAME);
    private static final int WAIT_FOR_HANDLER_TIMEOUT = 3000;
    private static Map<String, ActionHandler> actionMap = new HashMap();
    private static String mActionId = null;
    private static Signature mBixbyAgentSignature = new Signature(Base64.decode("MIIE1DCCA7ygAwIBAgIJANIJlaecDarWMA0GCSqGSIb3DQEBBQUAMIGiMQswCQYDVQQGEwJLUjEUMBIGA1UECBMLU291dGggS29yZWExEzARBgNVBAcTClN1d29uIENpdHkxHDAaBgNVBAoTE1NhbXN1bmcgQ29ycG9yYXRpb24xDDAKBgNVBAsTA0RNQzEVMBMGA1UEAxMMU2Ftc3VuZyBDZXJ0MSUwIwYJKoZIhvcNAQkBFhZhbmRyb2lkLm9zQHNhbXN1bmcuY29tMB4XDTExMDYyMjEyMjUxMloXDTM4MTEwNzEyMjUxMlowgaIxCzAJBgNVBAYTAktSMRQwEgYDVQQIEwtTb3V0aCBLb3JlYTETMBEGA1UEBxMKU3V3b24gQ2l0eTEcMBoGA1UEChMTU2Ftc3VuZyBDb3Jwb3JhdGlvbjEMMAoGA1UECxMDRE1DMRUwEwYDVQQDEwxTYW1zdW5nIENlcnQxJTAjBgkqhkiG9w0BCQEWFmFuZHJvaWQub3NAc2Ftc3VuZy5jb20wggEgMA0GCSqGSIb3DQEBAQUAA4IBDQAwggEIAoIBAQDJhjhKPh8vsgZnDnjvIyIVwNJvRaInKNuZpE2hHDWsM6cf4HHEotaCWptMiLMz7ZbzxebGZtYPPulMSQiFq8+NxmD3B6q8d+rT4tDYrugQjBXNJg8uhQQsKNLyktqjxtoMe/I5HbeEGq3o/fDJ0N7893Ek5tLeCp4NLadGw2cOT/zchbcBu0dEhhuW/3MR2jYDxaEDNuVf+jS0NT7tyF9RAV4VGMZ+MJ45+HY5/xeBB/EJzRhBGmB38mlktuY/inC5YZ2wQwajI8Gh0jr4Z+GfFPVw/+Vz0OOgwrMGMqrsMXM4CZS+HjQeOpC9LkthVIH0bbOeqDgWRI7DX+sXNcHzAgEDo4IBCzCCAQcwHQYDVR0OBBYEFJMsOvcLYnoMdhC1oOdCfWz66j8eMIHXBgNVHSMEgc8wgcyAFJMsOvcLYnoMdhC1oOdCfWz66j8eoYGopIGlMIGiMQswCQYDVQQGEwJLUjEUMBIGA1UECBMLU291dGggS29yZWExEzARBgNVBAcTClN1d29uIENpdHkxHDAaBgNVBAoTE1NhbXN1bmcgQ29ycG9yYXRpb24xDDAKBgNVBAsTA0RNQzEVMBMGA1UEAxMMU2Ftc3VuZyBDZXJ0MSUwIwYJKoZIhvcNAQkBFhZhbmRyb2lkLm9zQHNhbXN1bmcuY29tggkA0gmVp5wNqtYwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQUFAAOCAQEAMpYB/kDgNqSobMXUndjBtUFZmOcmN1OLDUMDaaxRUw9jqs6MAZoaZmFqLxuyxfq9bzEyYfOA40cWI/BT2ePFP1/W0ZZdewAOTcJEwbJ+L+mjI/8Hf1LEZ16GJHqoARhxN+MMm78BxWekKZ20vwslt9cQenuB7hAvcv9HlQFk4mdS4RTEL4udKkLnMIiX7GQOoZJO0Tq76dEgkSti9JJkk6htuUwLRvRMYWHVjC9kgWSJDFEt+yjULIVb9HDb7i2raWDK0E6B9xUl3tRs3Q81n5nEYNufAH2WzoO0shisLYLEjxJgjUaXM/BaM3VZRmnMv4pJVUTWxXAek2nAjIEBWA==", 0));
    private static boolean mIsAppInitialized = false;
    private static final boolean mIsUserBuild = "user".equals(Build.TYPE);
    private static boolean mWaitForHandler = false;
    private static Object sWaitLock = new Object();
    private Object sActionExecutionLock = new Object();

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return "actionUri";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public static void setAppInitialized(boolean z) {
        synchronized (sWaitLock) {
            if (!mIsAppInitialized && z) {
                mIsAppInitialized = z;
                LogUtil.i(TAG, "releasing initialize wait lock.");
                sWaitLock.notify();
            }
        }
        new Timer().schedule(new TimerTask() {
            /* class com.samsung.android.sdk.bixby2.provider.CapsuleProvider.AnonymousClass1 */

            public void run() {
                boolean unused = CapsuleProvider.mWaitForHandler = false;
            }
        }, UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
    }

    public static void addActionHandler(String str, ActionHandler actionHandler) {
        synchronized (sWaitLock) {
            if (actionMap.get(str) == null) {
                actionMap.put(str, actionHandler);
                if (mActionId != null && mActionId.equals(str)) {
                    String str2 = TAG;
                    LogUtil.i(str2, "handler added: " + str);
                    sWaitLock.notify();
                }
            }
        }
    }

    public static void removeActionHandler(String str) {
        if (actionMap.containsKey(str)) {
            actionMap.remove(str);
            String str2 = TAG;
            LogUtil.d(str2, "action handler corresponding to action : " + str + " is removed");
        }
    }

    public static void removeAllActionHandlers() {
        actionMap.clear();
        LogUtil.i(TAG, "Removed all action handlers");
    }

    private boolean isCallerAllowed() {
        if (!mIsUserBuild) {
            return true;
        }
        int callingUid = Binder.getCallingUid();
        PackageManager packageManager = getContext().getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
        if (packagesForUid == null) {
            LogUtil.e(TAG, "packages is null");
            return false;
        }
        for (String str : packagesForUid) {
            if ("com.samsung.android.bixby.agent".equals(str)) {
                try {
                    Signature[] signatureArr = packageManager.getPackageInfo(str, 64).signatures;
                    if (signatureArr != null && signatureArr.length > 0 && mBixbyAgentSignature.equals(signatureArr[0])) {
                        return true;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        LogUtil.e(TAG, "Not allowed to access capsule provider. package (s): " + Arrays.toString(packagesForUid));
        return false;
    }

    public boolean onCreate() {
        LogUtil.i(TAG, "onCreate");
        mWaitForHandler = true;
        return true;
    }

    private void executeProcessTriggerReceiver() {
        if (getContext() != null) {
            ApplicationTriggerReceiver applicationTriggerReceiver = new ApplicationTriggerReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_APPLICATION_TRIGGER);
            getContext().registerReceiver(applicationTriggerReceiver, intentFilter);
            LogUtil.i(TAG, "ApplicationTriggerReceiver registered");
            Intent intent = new Intent();
            intent.setAction(ACTION_APPLICATION_TRIGGER);
            intent.addFlags(268435456);
            getContext().sendBroadcast(intent);
        }
    }

    private void waitForAppInitialization() {
        synchronized (sWaitLock) {
            if (!mIsAppInitialized) {
                try {
                    sWaitLock.wait(XCommonInterface.WAKE_LOCK_TIMEOUT);
                } catch (InterruptedException e) {
                    LogUtil.e(TAG, "interrupted exception");
                    e.printStackTrace();
                }
            }
        }
    }

    public Bundle call(String str, String str2, Bundle bundle) {
        LogUtil.i(TAG, "call()");
        String str3 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("call(): method --> ");
        sb.append(str);
        sb.append(" args --> ");
        sb.append(str2);
        sb.append(" extras --> ");
        sb.append(bundle != null ? bundle.toString() : null);
        LogUtil.d(str3, sb.toString());
        if (!isCallerAllowed()) {
            throw new SecurityException("not allowed to access capsule provider.");
        } else if (!TextUtils.isEmpty(str)) {
            if (!mIsAppInitialized) {
                executeProcessTriggerReceiver();
            }
            waitForAppInitialization();
            if (!mIsAppInitialized) {
                LogUtil.e(TAG, "App initialization error.");
                return updateStatus(-1, "Initialization Failure..");
            } else if (str.equals(StateHandler.ACTION_GET_APP_STATE)) {
                Sbixby.getInstance();
                String appState = Sbixby.getStateHandler().getAppState(getContext());
                if (appState == null) {
                    return null;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString(StateHandler.KEY_APP_STATE, appState);
                return bundle2;
            } else if (bundle != null) {
                return executeAction(str, bundle);
            } else {
                throw new IllegalArgumentException("action params are EMPTY.");
            }
        } else {
            throw new IllegalArgumentException("method is null or empty. pass valid action name.");
        }
    }

    private Bundle updateStatus(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS_CODE, i);
        if (TextUtils.isEmpty(str) && i == -1) {
            str = "Failed to execute action.";
            LogUtil.e(TAG, str);
        }
        bundle.putString("status_message", str);
        return bundle;
    }

    private ActionHandler getActionHandler(String str) throws InterruptedException {
        ActionHandler actionHandler = actionMap.get(str);
        synchronized (sWaitLock) {
            if (actionHandler == null) {
                if (mWaitForHandler) {
                    mActionId = str;
                    sWaitLock.wait(UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
                    actionHandler = actionMap.get(str);
                }
            }
        }
        return actionHandler;
    }

    private synchronized Bundle executeAction(final String str, final Bundle bundle) {
        try {
            LogUtil.i(TAG, "executeAction()");
            final ActionHandler actionHandler = getActionHandler(str);
            if (actionHandler == null) {
                LogUtil.e(TAG, "Handler not found!!..");
                return updateStatus(-2, "Action handler not found");
            } else if (bundle == null || !bundle.containsKey(ActionHandler.ACTION_TYPE)) {
                LogUtil.e(TAG, "params missing");
                return updateStatus(-1, "params missing..");
            } else {
                final CapsuleResponseCallback capsuleResponseCallback = new CapsuleResponseCallback();
                Thread thread = new Thread(new Runnable() {
                    /* class com.samsung.android.sdk.bixby2.provider.CapsuleProvider.AnonymousClass2 */

                    public void run() {
                        actionHandler.executeAction(CapsuleProvider.this.getContext(), str, bundle, capsuleResponseCallback);
                    }
                });
                thread.start();
                synchronized (this.sActionExecutionLock) {
                    if (!capsuleResponseCallback.actionExecuted) {
                        this.sActionExecutionLock.wait(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
                    }
                    if (capsuleResponseCallback.actionExecuted) {
                        Bundle resultBundle = capsuleResponseCallback.getResultBundle();
                        if (resultBundle != null) {
                            return resultBundle;
                        }
                    } else {
                        LogUtil.e(TAG, "timeout occurred..");
                        capsuleResponseCallback.setActionTimedOut(true);
                        thread.interrupt();
                    }
                    return updateStatus(-1, "action execution timed out");
                }
            }
        } catch (Exception e) {
            String str2 = TAG;
            LogUtil.e(str2, "Unable to execute action." + e.toString());
            e.printStackTrace();
            return updateStatus(-1, e.toString());
        }
    }

    /* access modifiers changed from: private */
    public class CapsuleResponseCallback implements ResponseCallback {
        private boolean actionExecuted;
        private boolean actionTimedOut;
        private Bundle resultBundle;

        public CapsuleResponseCallback() {
            this.resultBundle = new Bundle();
            this.actionExecuted = false;
            this.actionTimedOut = false;
            this.actionExecuted = false;
            this.actionTimedOut = false;
        }

        @Override // com.samsung.android.sdk.bixby2.action.ResponseCallback
        public void onComplete(String str) {
            LogUtil.i(CapsuleProvider.TAG, "onComplete()");
            synchronized (CapsuleProvider.this.sActionExecutionLock) {
                if (!this.actionTimedOut) {
                    if (!this.actionExecuted) {
                        LogUtil.i(CapsuleProvider.TAG, "Action Execution Success");
                        this.resultBundle.putInt(CapsuleProvider.STATUS_CODE, 0);
                        this.resultBundle.putString("result", str);
                        String str2 = CapsuleProvider.TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("action result: ");
                        if (str == null) {
                            str = null;
                        }
                        sb.append(str);
                        LogUtil.d(str2, sb.toString());
                        this.actionExecuted = true;
                        CapsuleProvider.this.sActionExecutionLock.notify();
                    }
                }
            }
        }

        @Override // com.samsung.android.sdk.bixby2.action.ResponseCallback
        public void onComplete(String str, PendingIntent pendingIntent) {
            synchronized (CapsuleProvider.this.sActionExecutionLock) {
                if (!this.actionTimedOut) {
                    if (!this.actionExecuted) {
                        this.resultBundle.putParcelable(CapsuleProvider.PENDING_INTENT, pendingIntent);
                        LogUtil.i(CapsuleProvider.TAG, "action result: pending intent");
                    }
                    onComplete(str);
                }
            }
        }

        public Bundle getResultBundle() {
            return this.resultBundle;
        }

        public void setActionTimedOut(boolean z) {
            this.actionTimedOut = z;
        }
    }
}
