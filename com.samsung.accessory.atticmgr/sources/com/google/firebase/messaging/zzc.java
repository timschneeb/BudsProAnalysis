package com.google.firebase.messaging;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Tasks;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class zzc {
    private final Context zzag;
    private final Bundle zzcm;
    private final Executor zzdy;
    private final zzb zzdz;

    public zzc(Context context, Bundle bundle, Executor executor) {
        this.zzdy = executor;
        this.zzag = context;
        this.zzcm = bundle;
        this.zzdz = new zzb(context, context.getPackageName());
    }

    /* access modifiers changed from: package-private */
    public final boolean zzas() {
        boolean z;
        if ("1".equals(zzb.zza(this.zzcm, "gcm.n.noui"))) {
            return true;
        }
        if (!((KeyguardManager) this.zzag.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            if (!PlatformVersion.isAtLeastLollipop()) {
                SystemClock.sleep(10);
            }
            int myPid = Process.myPid();
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.zzag.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningAppProcessInfo next = it.next();
                    if (next.pid == myPid) {
                        if (next.importance == 100) {
                            z = true;
                        }
                    }
                }
            }
        }
        z = false;
        if (z) {
            return false;
        }
        zzd zzo = zzd.zzo(zzb.zza(this.zzcm, "gcm.n.image"));
        if (zzo != null) {
            zzo.zza(this.zzdy);
        }
        zza zzf = this.zzdz.zzf(this.zzcm);
        NotificationCompat.Builder builder = zzf.zzds;
        if (zzo != null) {
            try {
                Bitmap bitmap = (Bitmap) Tasks.await(zzo.getTask(), 5, TimeUnit.SECONDS);
                builder.setLargeIcon(bitmap);
                builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));
            } catch (ExecutionException unused) {
            } catch (InterruptedException unused2) {
                Log.w("FirebaseMessaging", "Interrupted while downloading image, showing notification without it");
                zzo.close();
                Thread.currentThread().interrupt();
            } catch (TimeoutException unused3) {
                Log.w("FirebaseMessaging", "Failed to download image in time, showing notification without it");
                zzo.close();
            }
        }
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            Log.d("FirebaseMessaging", "Showing notification");
        }
        ((NotificationManager) this.zzag.getSystemService("notification")).notify(zzf.tag, 0, zzf.zzds.build());
        return true;
    }
}
