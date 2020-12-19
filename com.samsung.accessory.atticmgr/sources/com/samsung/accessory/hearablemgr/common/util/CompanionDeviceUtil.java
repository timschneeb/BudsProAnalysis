package com.samsung.accessory.hearablemgr.common.util;

import android.app.Activity;
import android.companion.AssociationRequest;
import android.companion.BluetoothDeviceFilter;
import android.companion.CompanionDeviceManager;
import android.content.ComponentName;
import android.content.IntentSender;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.internal.view.SupportMenu;
import com.samsung.accessory.hearablemgr.Application;
import java.util.Iterator;
import seccompat.android.util.Log;

public class CompanionDeviceUtil {
    public static final int REQUEST_CODE_ASSOCIATE = (CompanionDeviceUtil.class.hashCode() & SupportMenu.USER_MASK);
    private static final String TAG = "Attic_CompanionDeviceUtil";

    public static boolean isSupport() {
        boolean hasSystemFeature = Build.VERSION.SDK_INT >= 26 ? Application.getContext().getPackageManager().hasSystemFeature("android.software.companion_device_setup") : false;
        Log.d(TAG, "isSupport() : " + hasSystemFeature);
        return hasSystemFeature;
    }

    public static boolean isCompanionDevice(String str) {
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 26 && isSupport()) {
            Iterator<String> it = ((CompanionDeviceManager) Application.getContext().getSystemService("companiondevice")).getAssociations().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                Log.d(TAG, "association : " + next);
                if (next.equalsIgnoreCase(str)) {
                    z = true;
                    break;
                }
            }
        } else {
            Log.w(TAG, "isCompanionDevice() : Not supported");
        }
        Log.d(TAG, "isCompanionDevice(" + BluetoothUtil.privateAddress(str) + ") : " + z);
        return z;
    }

    public static void associate(final Activity activity, final String str, final ResponseCallback responseCallback) {
        if (Build.VERSION.SDK_INT < 26 || !isSupport()) {
            Log.w(TAG, "associate() : Not supported");
            if (responseCallback != null) {
                responseCallback.setExtraObject("NOT_SUPPORT");
            }
        } else if (!TextUtils.isEmpty(str)) {
            AssociationRequest build = new AssociationRequest.Builder().addDeviceFilter(new BluetoothDeviceFilter.Builder().setAddress(str).build()).setSingleDevice(true).build();
            Log.d(TAG, "associate()");
            ((CompanionDeviceManager) activity.getSystemService("companiondevice")).associate(build, new CompanionDeviceManager.Callback() {
                /* class com.samsung.accessory.hearablemgr.common.util.CompanionDeviceUtil.AnonymousClass1 */

                public void onDeviceFound(IntentSender intentSender) {
                    Log.d(CompanionDeviceUtil.TAG, "onDeviceFound(" + BluetoothUtil.privateAddress(str) + " )");
                    try {
                        activity.startIntentSenderForResult(intentSender, CompanionDeviceUtil.REQUEST_CODE_ASSOCIATE, null, 0, 0, 0);
                        if (responseCallback != null) {
                            responseCallback.onResponse(null);
                        }
                    } catch (IntentSender.SendIntentException e) {
                        ResponseCallback responseCallback = responseCallback;
                        if (responseCallback != null) {
                            responseCallback.onResponse(e.toString());
                        }
                    }
                }

                public void onFailure(CharSequence charSequence) {
                    Log.d(CompanionDeviceUtil.TAG, "onFailure(" + BluetoothUtil.privateAddress(str) + " ) : " + charSequence.toString());
                    ResponseCallback responseCallback = responseCallback;
                    if (responseCallback != null) {
                        responseCallback.onResponse(charSequence.toString());
                    }
                }
            }, null);
        } else if (responseCallback != null) {
            responseCallback.setExtraObject("ADDRESS_NULL");
        }
    }

    public static void disassociate(String str) {
        Log.d(TAG, "disassociate(" + BluetoothUtil.privateAddress(str) + ")");
        if (Build.VERSION.SDK_INT < 26 || !isSupport()) {
            Log.w(TAG, "disassociate() : Not supported");
        } else {
            ((CompanionDeviceManager) Application.getContext().getSystemService("companiondevice")).disassociate(str);
        }
    }

    private static boolean isEnabledListenerPackage() {
        boolean contains = NotificationManagerCompat.getEnabledListenerPackages(Application.getContext()).contains(Application.getContext().getPackageName());
        Log.d(TAG, "isEnabledListenerPackage() : " + contains);
        return contains;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean hasNotificationAccess(android.content.ComponentName r3) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            java.lang.String r1 = "Attic_CompanionDeviceUtil"
            r2 = 26
            if (r0 < r2) goto L_0x0038
            boolean r0 = isSupport()
            if (r0 == 0) goto L_0x0038
            android.content.Context r0 = com.samsung.accessory.hearablemgr.Application.getContext()
            java.lang.String r2 = "companiondevice"
            java.lang.Object r0 = r0.getSystemService(r2)
            android.companion.CompanionDeviceManager r0 = (android.companion.CompanionDeviceManager) r0
            boolean r3 = r0.hasNotificationAccess(r3)     // Catch:{ all -> 0x0023 }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x0023 }
            goto L_0x0039
        L_0x0023:
            r3 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "hasNotificationAccess() : Exception : "
            r0.append(r2)
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            seccompat.android.util.Log.e(r1, r3)
        L_0x0038:
            r3 = 0
        L_0x0039:
            if (r3 != 0) goto L_0x0043
            boolean r3 = isEnabledListenerPackage()
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
        L_0x0043:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "hasNotificationAccess() : "
            r0.append(r2)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            seccompat.android.util.Log.d(r1, r0)
            boolean r3 = r3.booleanValue()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.common.util.CompanionDeviceUtil.hasNotificationAccess(android.content.ComponentName):boolean");
    }

    public static void requestNotificationAccess(ComponentName componentName) {
        Log.d(TAG, "requestNotificationAccess()");
        if (Build.VERSION.SDK_INT < 26 || !isSupport()) {
            Log.w(TAG, "requestNotificationAccess() : Not supported");
        } else {
            ((CompanionDeviceManager) Application.getContext().getSystemService("companiondevice")).requestNotificationAccess(componentName);
        }
    }
}
