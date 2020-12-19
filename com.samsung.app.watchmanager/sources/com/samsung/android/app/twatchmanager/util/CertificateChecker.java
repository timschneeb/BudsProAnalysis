package com.samsung.android.app.twatchmanager.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

public class CertificateChecker {
    private static final String TAG = ("tUHM:" + CertificateChecker.class.getSimpleName());
    private static volatile CertificateChecker sChecker;
    private Context mContext;

    private CertificateChecker(Context context) {
        this.mContext = context;
    }

    public static CertificateChecker get() {
        synchronized (CertificateChecker.class) {
            if (sChecker == null) {
                sChecker = new CertificateChecker(TWatchManagerApplication.getAppContext());
            }
        }
        return sChecker;
    }

    private byte[][] getAPKCertificateBytes(String str) {
        PackageManager packageManager;
        PackageInfo packageArchiveInfo;
        String str2 = TAG;
        Log.d(str2, "getAPKCertificateBytes(" + str + ")");
        Context context = this.mContext;
        if (!(context == null || (packageManager = context.getPackageManager()) == null || (packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 64)) == null)) {
            Signature[] signatureArr = packageArchiveInfo.signatures;
            int length = signatureArr == null ? 0 : signatureArr.length;
            if (length > 0) {
                byte[][] bArr = new byte[length][];
                for (int i = 0; i < length; i++) {
                    bArr[i] = signatureArr[i].toByteArray();
                }
                return bArr;
            }
        }
        return null;
    }

    private String getCertStringFromCertificate(Certificate certificate) {
        byte[] signature;
        X509Certificate x509Certificate = (X509Certificate) certificate;
        if (x509Certificate == null || (signature = x509Certificate.getSignature()) == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : signature) {
            stringBuffer.append((int) b2);
        }
        return stringBuffer.toString();
    }

    private byte[][] getInstalledPackageCertificateBytes(String str) {
        PackageInfo packageInfo;
        String str2 = TAG;
        Log.d(str2, "getInstalledPackageCertificateBytes(" + str + ")");
        byte[][] bArr = null;
        if (this.mContext != null && !TextUtils.isEmpty(str)) {
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                if (!(packageManager == null || (packageInfo = packageManager.getPackageInfo(str, 64)) == null)) {
                    Signature[] signatureArr = packageInfo.signatures;
                    int length = signatureArr == null ? 0 : signatureArr.length;
                    if (length > 0) {
                        bArr = new byte[length][];
                        for (int i = 0; i < length; i++) {
                            bArr[i] = signatureArr[i].toByteArray();
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bArr;
    }

    private Certificate loadCertificates(byte[] bArr) {
        if (bArr != null) {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
        }
        return null;
    }

    public boolean checkSignature(String str, String str2) {
        Log.d(TAG, "checkSignature(" + str + ", " + str2 + ")");
        String trim = str2 == null ? null : str2.trim();
        ArrayList arrayList = new ArrayList();
        try {
            byte[][] installedPackageCertificateBytes = getInstalledPackageCertificateBytes(str);
            if (installedPackageCertificateBytes != null) {
                for (byte[] bArr : installedPackageCertificateBytes) {
                    arrayList.add(getCertStringFromCertificate(loadCertificates(bArr)));
                }
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        int size = arrayList.size();
        return (trim != null && arrayList.contains(trim)) || ((size == 0 || (size == 1 && TextUtils.isEmpty((CharSequence) arrayList.get(0)))) && TextUtils.isEmpty(trim));
    }

    public boolean matchSignature(String str, String str2) {
        X509Certificate x509Certificate;
        Log.d(TAG, "matchSignature() starts..." + str);
        Context appContext = TWatchManagerApplication.getAppContext();
        if (UpdateUtil.isLocalUpdateTestModeEnabled() || InstallationUtils.isLocalInstallation()) {
            return true;
        }
        boolean z = false;
        if (appContext != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                PackageInfo packageArchiveInfo = appContext.getPackageManager().getPackageArchiveInfo(str, 64);
                if (!(packageArchiveInfo == null || (x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageArchiveInfo.signatures[0].toByteArray()))) == null)) {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (byte b2 : x509Certificate.getSignature()) {
                        stringBuffer.append((int) b2);
                    }
                    if (str2.equals(stringBuffer.toString())) {
                        z = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "matchSignature() ends ... isSignatureMatch = " + z);
        return z;
    }

    public boolean matchSignatureLegacy(String str, String str2) {
        PackageManager.NameNotFoundException e;
        String str3 = TAG;
        Log.d(str3, "matchSignature(" + str + ", " + str2 + ")");
        boolean z = false;
        if (this.mContext != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                if (packageManager == null) {
                    Log.e(TAG, "pm is null");
                    return false;
                }
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 64);
                Signature[] signatureArr = null;
                Signature[] signatureArr2 = packageArchiveInfo == null ? null : packageArchiveInfo.signatures;
                PackageInfo packageInfo = packageManager.getPackageInfo(str2, 64);
                if (packageInfo != null) {
                    signatureArr = packageInfo.signatures;
                }
                if ((signatureArr2 == null || signatureArr2.length == 0) && packageInfo != null && (signatureArr == null || signatureArr.length == 0)) {
                    try {
                        String str4 = TAG;
                        Log.d(str4, "check the signature doesn't exist case, result : " + true);
                        z = true;
                    } catch (PackageManager.NameNotFoundException e2) {
                        e = e2;
                        z = true;
                        e.printStackTrace();
                        String str5 = TAG;
                        Log.d(str5, "matchSignature()-->result = " + z);
                        return z;
                    }
                } else if (signatureArr2 == null || signatureArr2.length != 1) {
                    String str6 = TAG;
                    Log.d(str6, "Given apk source is not valid for package: " + str2);
                } else if (str2.equals(packageArchiveInfo.packageName)) {
                    Log.d(TAG, "Downloaded package may have valid signature.");
                    if (signatureArr != null && signatureArr.length == 1) {
                        Log.d(TAG, "Installed package may have valid signature.");
                        boolean equals = signatureArr[0].equals(signatureArr2[0]);
                        try {
                            String str7 = TAG;
                            Log.d(str7, "compare the signatures, result : " + equals);
                            z = equals;
                        } catch (PackageManager.NameNotFoundException e3) {
                            z = equals;
                            e = e3;
                            e.printStackTrace();
                            String str52 = TAG;
                            Log.d(str52, "matchSignature()-->result = " + z);
                            return z;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e4) {
                e = e4;
                e.printStackTrace();
                String str522 = TAG;
                Log.d(str522, "matchSignature()-->result = " + z);
                return z;
            }
        }
        String str5222 = TAG;
        Log.d(str5222, "matchSignature()-->result = " + z);
        return z;
    }

    public boolean matchSignatureWithDifferentInstalledPackage(String str, String str2) {
        PackageManager.NameNotFoundException e;
        String str3 = TAG;
        Log.d(str3, "matchSignatureWithDifferentInstalledPackage(" + str + ", " + str2 + ")");
        boolean z = false;
        if (this.mContext != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                if (packageManager == null) {
                    Log.e(TAG, "pm is null");
                    return false;
                }
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 64);
                Signature[] signatureArr = null;
                Signature[] signatureArr2 = packageArchiveInfo == null ? null : packageArchiveInfo.signatures;
                PackageInfo packageInfo = packageManager.getPackageInfo(str2, 64);
                if (packageInfo != null) {
                    signatureArr = packageInfo.signatures;
                }
                boolean z2 = true;
                if (signatureArr2 == null || signatureArr2.length != 1) {
                    if (!((signatureArr2 == null || signatureArr2.length == 0) && packageInfo != null && (signatureArr == null || signatureArr.length == 0))) {
                        z2 = false;
                    }
                    try {
                        String str4 = TAG;
                        Log.d(str4, "check the signature doesn't exist case, result : " + z2);
                        z = z2;
                    } catch (PackageManager.NameNotFoundException e2) {
                        e = e2;
                        z = z2;
                        e.printStackTrace();
                        String str5 = TAG;
                        Log.d(str5, "matchSignatureWithDifferentInstalledPackage()-->result = " + z);
                        return z;
                    }
                } else {
                    Log.d(TAG, "Downloaded package may have valid signature.");
                    if (signatureArr != null && signatureArr.length == 1) {
                        Log.d(TAG, "Installed package may have valid signature.");
                        boolean equals = signatureArr[0].equals(signatureArr2[0]);
                        try {
                            String str6 = TAG;
                            Log.d(str6, "compare the signatures, result : " + equals);
                            z = equals;
                        } catch (PackageManager.NameNotFoundException e3) {
                            z = equals;
                            e = e3;
                            e.printStackTrace();
                            String str52 = TAG;
                            Log.d(str52, "matchSignatureWithDifferentInstalledPackage()-->result = " + z);
                            return z;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e4) {
                e = e4;
                e.printStackTrace();
                String str522 = TAG;
                Log.d(str522, "matchSignatureWithDifferentInstalledPackage()-->result = " + z);
                return z;
            }
        }
        String str5222 = TAG;
        Log.d(str5222, "matchSignatureWithDifferentInstalledPackage()-->result = " + z);
        return z;
    }

    public boolean verifySignature(String str, String str2) {
        Log.d(TAG, "verifySignature(" + str + ", " + str2 + ")");
        String trim = str2 == null ? null : str2.trim();
        ArrayList arrayList = new ArrayList();
        try {
            byte[][] aPKCertificateBytes = getAPKCertificateBytes(str);
            if (aPKCertificateBytes != null) {
                for (byte[] bArr : aPKCertificateBytes) {
                    arrayList.add(getCertStringFromCertificate(loadCertificates(bArr)));
                }
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        int size = arrayList.size();
        return (trim != null && arrayList.contains(trim)) || ((size == 0 || (size == 1 && TextUtils.isEmpty((CharSequence) arrayList.get(0)))) && TextUtils.isEmpty(trim));
    }
}
