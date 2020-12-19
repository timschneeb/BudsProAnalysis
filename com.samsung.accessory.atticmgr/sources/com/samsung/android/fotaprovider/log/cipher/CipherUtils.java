package com.samsung.android.fotaprovider.log.cipher;

/* access modifiers changed from: package-private */
public class CipherUtils {
    CipherUtils() {
    }

    static byte[] mergeBytes(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}
