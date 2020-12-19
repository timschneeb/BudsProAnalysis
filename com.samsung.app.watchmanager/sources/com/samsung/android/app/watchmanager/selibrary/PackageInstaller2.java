package com.samsung.android.app.watchmanager.selibrary;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.util.Log;
import com.samsung.android.app.watchmanager.libinterface.AbstractPackageInstaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PackageInstaller2 extends AbstractPackageInstaller {
    private static final String INTENT_NAME = "tUHM.install_complete";
    private static final String TAG = ("tUHM:" + PackageInstaller2.class.getSimpleName());
    private PackageInstallerListener mListener;

    /* access modifiers changed from: package-private */
    public class PackageInstallerListener extends BroadcastReceiver {
        boolean install = true;

        PackageInstallerListener() {
        }

        /* access modifiers changed from: package-private */
        public void install(boolean z) {
            this.install = z;
        }

        public void onReceive(Context context, Intent intent) {
            PackageInstaller2.this.destroyReceiver();
            String str = PackageInstaller2.TAG;
            Log.d(str, "onReceive [" + intent + "], install [" + this.install + "]");
            int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
            String stringExtra = intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE");
            String stringExtra2 = intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME");
            String str2 = PackageInstaller2.TAG;
            Log.d(str2, "PackageInstallerCallback: result [" + intExtra + "], message [" + stringExtra + "], packageName [" + stringExtra2 + "]");
            if (intExtra == -1) {
                Log.d(PackageInstaller2.TAG, "STATUS_PENDING_USER_ACTION");
            } else if (intExtra != 0) {
                Log.e(PackageInstaller2.TAG, "Install failed.");
                if (this.install) {
                    PackageInstaller2.this.notifyPackageInstalled(stringExtra2, intExtra);
                } else {
                    PackageInstaller2.this.notifyPackageUninstalled(stringExtra2, intExtra);
                }
            } else {
                Log.d(PackageInstaller2.TAG, "*** STATUS_SUCCESS ***");
                if (this.install) {
                    PackageInstaller2.this.notifyPackageInstalled(stringExtra2, 1);
                } else {
                    PackageInstaller2.this.notifyPackageUninstalled(stringExtra2, 1);
                }
            }
        }
    }

    public PackageInstaller2(Context context) {
        super(context);
    }

    private IntentSender createIntentSender(Context context, int i) {
        return PendingIntent.getBroadcast(context, i, new Intent(INTENT_NAME), 0).getIntentSender();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void destroyReceiver() {
        this.mContext.unregisterReceiver(getListener());
        this.mListener = null;
    }

    private InputStream getInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException");
            e.printStackTrace();
            return null;
        }
    }

    private InputStream getInputStream(String str) {
        return getInputStream(new File(str));
    }

    private PackageInstallerListener getListener() {
        if (this.mListener == null) {
            this.mListener = new PackageInstallerListener();
        }
        return this.mListener;
    }

    private void installPackage_(Context context, InputStream inputStream, String str) {
        try {
            installPackage__(context, inputStream, str);
            getListener().install(true);
            this.mContext.registerReceiver(getListener(), new IntentFilter(INTENT_NAME), null, null);
        } catch (IOException e) {
            Log.e(TAG, "IOException");
            e.printStackTrace();
        }
    }

    private void installPackage__(Context context, InputStream inputStream, String str) {
        if (inputStream != null) {
            PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
            PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
            sessionParams.setAppPackageName(str);
            int createSession = packageInstaller.createSession(sessionParams);
            PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
            OutputStream openWrite = openSession.openWrite(str, 0, -1);
            byte[] bArr = new byte[65536];
            long j = 0;
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    openWrite.write(bArr, 0, read);
                    j += (long) read;
                } else {
                    String str2 = TAG;
                    Log.w(str2, "wrote totally " + j + " bytes");
                    openSession.fsync(openWrite);
                    inputStream.close();
                    openWrite.close();
                    openSession.commit(createIntentSender(context, createSession));
                    Log.w(TAG, "commit called");
                    return;
                }
            }
        }
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public int getStorageErrorCode() {
        return 6;
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(Uri uri, String str) {
        Log.e(TAG, "not necessary to implement");
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(File file, String str) {
        installPackage_(this.mContext, getInputStream(file), str);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(String str, String str2) {
        installPackage(str, null, str2);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void installPackage(String str, String str2, String str3) {
        installPackage_(this.mContext, getInputStream(str), str3);
    }

    @Override // com.samsung.android.app.watchmanager.libinterface.IInstaller
    public void uninstallPackage(String str) {
        this.mContext.getPackageManager().getPackageInstaller().uninstall(str, createIntentSender(this.mContext, 0));
        getListener().install(false);
        this.mContext.registerReceiver(getListener(), new IntentFilter(INTENT_NAME), null, null);
    }
}
