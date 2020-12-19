package com.samsung.android.fotaprovider.log.cipher;

import android.util.Base64;
import com.samsung.android.fotaprovider.log.Log;
import java.nio.charset.Charset;

public class AESWithRSA {
    private static AES aes;
    private static byte[] encryptedRSAKey;

    static {
        try {
            aes = new AES();
            encryptedRSAKey = new RSA().encrypt(aes.getKey());
        } catch (Exception e) {
            Log.printStackTrace(e);
        }
    }

    public static String encrypt(String str) throws Exception {
        return new String(Base64.encode(encrypt(str.getBytes(Charset.defaultCharset())), 2), Charset.defaultCharset());
    }

    private static byte[] encrypt(byte[] bArr) throws Exception {
        return CipherUtils.mergeBytes(encryptedRSAKey, aes.encrypt(bArr));
    }
}
