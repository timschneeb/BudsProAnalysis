package com.samsung.android.fotaprovider.log.cipher;

import android.util.Base64;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.NativeUtil;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt {
    private static final String CRYPTO_KEY_ALGORITHM = NativeUtil.getDataLogKey();
    private static final byte[] debuggingKey = NativeUtil.mealyMachine(5932, 16).getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String str) {
        try {
            return new String(Base64.encode(getEncryptResult(str.getBytes(StandardCharsets.UTF_8)), 2), StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.E("Exception : " + e.toString());
            return "";
        }
    }

    private static byte[] getEncryptResult(byte[] bArr) throws Exception {
        Cipher instance = Cipher.getInstance(CRYPTO_KEY_ALGORITHM);
        instance.init(1, new SecretKeySpec(debuggingKey, CRYPTO_KEY_ALGORITHM));
        return instance.doFinal(bArr);
    }
}
