package com.samsung.android.fotaprovider.log.cipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/* access modifiers changed from: package-private */
public class AES {
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_LENGTH = 128;
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    private Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
    private Key key = generateKey();
    private SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);

    AES() throws NoSuchAlgorithmException, NoSuchPaddingException {
    }

    private Key generateKey() throws NoSuchAlgorithmException {
        KeyGenerator instance = KeyGenerator.getInstance(KEY_ALGORITHM);
        instance.init(128, this.secureRandom);
        return instance.generateKey();
    }

    /* access modifiers changed from: package-private */
    public byte[] getKey() {
        return this.key.getEncoded();
    }

    /* access modifiers changed from: package-private */
    public synchronized byte[] encrypt(byte[] bArr) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] bArr2;
        bArr2 = new byte[this.cipher.getBlockSize()];
        this.secureRandom.nextBytes(bArr2);
        this.cipher.init(1, this.key, new IvParameterSpec(bArr2));
        return CipherUtils.mergeBytes(bArr2, this.cipher.doFinal(bArr));
    }
}
