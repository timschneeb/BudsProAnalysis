package com.samsung.android.fotaprovider.log.cipher;

import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

class RSA {
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String KEY_ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4hCCVWWB2/kpBfkG/hhaHT/TeyND99Fj4owwfEk+vNKmP6WAmq5QA32ctQY4MvXDHDRssjQ9Y+Bxg1c4ciaQ9StgpMAnn4epuO+p6q+aHnRNHeMLOkqDHwfMfyzMq57cK/Az/yfVDjNwZrDxZ4lsE+RtTX8QUtvbdwU8O4H+KoT352mlC1kgrNTK8aJEwaEVnpD5LFG9gvWx/insJO7dr7cjk2fyj1d/aYRe2+mjSkaQX9QlYVW+COnAcRnIg9XDIOyW5tGtepFYX6w/621dQT6eoferwegH54glfIf78/GJMUUBwN/FPmOgN3siHZIZH4iv51XfOXyAb0KHlb3UswIDAQAB";
    private Cipher rsaCipher;

    RSA() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        KeyFactory instance = KeyFactory.getInstance(KEY_ALGORITHM);
        byte[] decode = Base64.decode(PUBLIC_KEY, 2);
        if (decode != null) {
            PublicKey generatePublic = instance.generatePublic(new X509EncodedKeySpec(decode));
            this.rsaCipher = Cipher.getInstance(CIPHER_ALGORITHM);
            this.rsaCipher.init(1, generatePublic);
            return;
        }
        throw new InvalidKeyException("Key BASE64 decoding failed");
    }

    /* access modifiers changed from: package-private */
    public synchronized byte[] encrypt(byte[] bArr) throws IllegalBlockSizeException, BadPaddingException {
        return this.rsaCipher.doFinal(bArr);
    }
}
