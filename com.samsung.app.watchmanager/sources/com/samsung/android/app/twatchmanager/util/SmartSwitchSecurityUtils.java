package com.samsung.android.app.twatchmanager.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SmartSwitchSecurityUtils {
    private static final String TAG = ("tUHM:SmartSwitch:" + SmartSwitchSecurityUtils.class.getSimpleName());
    private Cipher mCipher;
    private SecretKeySpec mKey;
    private String mSessionKey;

    public SmartSwitchSecurityUtils(String str) {
        this.mSessionKey = str;
    }

    private boolean StreamCrypt(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            try {
                instance.update(str.getBytes("UTF-8"));
                byte[] bArr = new byte[16];
                System.arraycopy(instance.digest(), 0, bArr, 0, bArr.length);
                try {
                    this.mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    this.mKey = new SecretKeySpec(bArr, "AES");
                    return true;
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return false;
                } catch (NoSuchPaddingException e2) {
                    e2.printStackTrace();
                    return false;
                }
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            return false;
        }
    }

    private InputStream decryptStream(InputStream inputStream) {
        byte[] bArr = new byte[this.mCipher.getBlockSize()];
        inputStream.read(bArr);
        this.mCipher.init(2, this.mKey, new IvParameterSpec(bArr));
        return new CipherInputStream(inputStream, this.mCipher);
    }

    private OutputStream encryptStream(OutputStream outputStream) {
        byte[] bArr = new byte[this.mCipher.getBlockSize()];
        new SecureRandom().nextBytes(bArr);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        outputStream.write(bArr);
        this.mCipher.init(1, this.mKey, ivParameterSpec);
        return new CipherOutputStream(outputStream, this.mCipher);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.samsung.android.app.twatchmanager.util.SmartSwitchSecurityUtils] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0113 A[Catch:{ IOException -> 0x010f }] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0118 A[Catch:{ IOException -> 0x010f }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0123 A[SYNTHETIC, Splitter:B:110:0x0123] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x012b A[Catch:{ IOException -> 0x0127 }] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0130 A[Catch:{ IOException -> 0x0127 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00b4 A[SYNTHETIC, Splitter:B:53:0x00b4] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00bc A[Catch:{ IOException -> 0x00b8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00c1 A[Catch:{ IOException -> 0x00b8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00d1 A[SYNTHETIC, Splitter:B:68:0x00d1] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00d9 A[Catch:{ IOException -> 0x00d5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00de A[Catch:{ IOException -> 0x00d5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x00ee A[SYNTHETIC, Splitter:B:83:0x00ee] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x00f6 A[Catch:{ IOException -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x00fb A[Catch:{ IOException -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x010b A[SYNTHETIC, Splitter:B:98:0x010b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean DecryptFile(java.io.File r9) {
        /*
        // Method dump skipped, instructions count: 314
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.SmartSwitchSecurityUtils.DecryptFile(java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x011b A[Catch:{ IOException -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0120 A[Catch:{ IOException -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x012b A[SYNTHETIC, Splitter:B:107:0x012b] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0133 A[Catch:{ IOException -> 0x012f }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0138 A[Catch:{ IOException -> 0x012f }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bc A[SYNTHETIC, Splitter:B:53:0x00bc] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00c4 A[Catch:{ IOException -> 0x00c0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00c9 A[Catch:{ IOException -> 0x00c0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00d9 A[SYNTHETIC, Splitter:B:67:0x00d9] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00e1 A[Catch:{ IOException -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00e6 A[Catch:{ IOException -> 0x00dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x00f6 A[SYNTHETIC, Splitter:B:81:0x00f6] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x00fe A[Catch:{ IOException -> 0x00fa }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0103 A[Catch:{ IOException -> 0x00fa }] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0113 A[SYNTHETIC, Splitter:B:95:0x0113] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean EncryptFile(java.io.File r10) {
        /*
        // Method dump skipped, instructions count: 322
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.SmartSwitchSecurityUtils.EncryptFile(java.io.File):boolean");
    }
}
