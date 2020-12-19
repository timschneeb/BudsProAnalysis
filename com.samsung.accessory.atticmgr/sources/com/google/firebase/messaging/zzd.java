package com.google.firebase.messaging;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_messaging.zzk;
import com.google.android.gms.internal.firebase_messaging.zzn;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.io.Closeable;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;

/* access modifiers changed from: package-private */
public final class zzd implements Closeable {
    private final URL url;
    private Task<Bitmap> zzea;
    private volatile InputStream zzeb;

    public static zzd zzo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new zzd(new URL(str));
        } catch (MalformedURLException unused) {
            String valueOf = String.valueOf(str);
            Log.w("FirebaseMessaging", valueOf.length() != 0 ? "Not downloading image, bad URL: ".concat(valueOf) : new String("Not downloading image, bad URL: "));
            return null;
        }
    }

    private zzd(URL url2) {
        this.url = url2;
    }

    public final void zza(Executor executor) {
        this.zzea = Tasks.call(executor, new zze(this));
    }

    public final Task<Bitmap> getTask() {
        return (Task) Preconditions.checkNotNull(this.zzea);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a0, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a1, code lost:
        zza(r3, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a4, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a7, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a8, code lost:
        if (r0 != null) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00aa, code lost:
        zza(r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ad, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.Bitmap zzat() throws java.io.IOException {
        /*
        // Method dump skipped, instructions count: 212
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zzd.zzat():android.graphics.Bitmap");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        zzk.zza(this.zzeb);
    }

    private static /* synthetic */ void zza(Throwable th, InputStream inputStream) {
        if (th != null) {
            try {
                inputStream.close();
            } catch (Throwable th2) {
                zzn.zza(th, th2);
            }
        } else {
            inputStream.close();
        }
    }
}
