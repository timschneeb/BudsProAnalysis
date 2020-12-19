package com.google.firebase.iid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.util.Log;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.accessorydm.interfaces.XDMInterface;
import com.sec.android.diagmonagent.log.ged.db.status.ERStatus;
import java.util.ArrayDeque;
import java.util.Queue;

public final class zzaw {
    private static zzaw zzdf;
    private String zzdg = null;
    private Boolean zzdh = null;
    private Boolean zzdi = null;
    private final Queue<Intent> zzdj = new ArrayDeque();

    public static synchronized zzaw zzak() {
        zzaw zzaw;
        synchronized (zzaw.class) {
            if (zzdf == null) {
                zzdf = new zzaw();
            }
            zzaw = zzdf;
        }
        return zzaw;
    }

    private zzaw() {
    }

    public final Intent zzal() {
        return this.zzdj.poll();
    }

    public final int zzc(Context context, Intent intent) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Starting service");
        }
        this.zzdj.offer(intent);
        Intent intent2 = new Intent("com.google.firebase.MESSAGING_EVENT");
        intent2.setPackage(context.getPackageName());
        return zzd(context, intent2);
    }

    private final int zzd(Context context, Intent intent) {
        ComponentName componentName;
        String zze = zze(context, intent);
        if (zze != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(zze);
                Log.d("FirebaseInstanceId", valueOf.length() != 0 ? "Restricting intent to a specific service: ".concat(valueOf) : new String("Restricting intent to a specific service: "));
            }
            intent.setClassName(context.getPackageName(), zze);
        }
        try {
            if (zzd(context)) {
                componentName = WakefulBroadcastReceiver.startWakefulService(context, intent);
            } else {
                componentName = context.startService(intent);
                Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
            }
            if (componentName != null) {
                return -1;
            }
            Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
            return 404;
        } catch (SecurityException e) {
            Log.e("FirebaseInstanceId", "Error while delivering the message to the serviceIntent", e);
            return ERStatus.PERMISSION_DENIED;
        } catch (IllegalStateException e2) {
            String valueOf2 = String.valueOf(e2);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 45);
            sb.append("Failed to start service while in background: ");
            sb.append(valueOf2);
            Log.e("FirebaseInstanceId", sb.toString());
            return ERStatus.ERROR_CODE_UNAUTHORIZED;
        }
    }

    private final synchronized String zze(Context context, Intent intent) {
        if (this.zzdg != null) {
            return this.zzdg;
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
            return null;
        }
        ServiceInfo serviceInfo = resolveService.serviceInfo;
        if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
            String str = serviceInfo.packageName;
            String str2 = serviceInfo.name;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 94 + String.valueOf(str2).length());
            sb.append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ");
            sb.append(str);
            sb.append("/");
            sb.append(str2);
            Log.e("FirebaseInstanceId", sb.toString());
            return null;
        }
        if (serviceInfo.name.startsWith(XDMInterface.XDM_BASE_PATH)) {
            String valueOf = String.valueOf(context.getPackageName());
            String valueOf2 = String.valueOf(serviceInfo.name);
            this.zzdg = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else {
            this.zzdg = serviceInfo.name;
        }
        return this.zzdg;
    }

    /* access modifiers changed from: package-private */
    public final boolean zzd(Context context) {
        if (this.zzdh == null) {
            this.zzdh = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
        }
        if (!this.zzdh.booleanValue() && Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Missing Permission: android.permission.WAKE_LOCK this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
        }
        return this.zzdh.booleanValue();
    }

    /* access modifiers changed from: package-private */
    public final boolean zze(Context context) {
        if (this.zzdi == null) {
            this.zzdi = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0);
        }
        if (!this.zzdh.booleanValue() && Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Missing Permission: android.permission.ACCESS_NETWORK_STATE this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
        }
        return this.zzdi.booleanValue();
    }
}
