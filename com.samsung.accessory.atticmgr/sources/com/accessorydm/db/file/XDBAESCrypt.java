package com.accessorydm.db.file;

import android.text.TextUtils;
import android.util.Base64;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.InsecureSHA1PRNGKeyDerivator;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class XDBAESCrypt {
    private static final String CRYPTO_KEY_ALGORITHM = "AES";
    private static final int CRYPTO_KEY_SIZE = 128;
    public static final String CRYPTO_SEED_PASSWORD = "smldm";

    public static byte[] xdbEncryptor(String str, String str2) {
        try {
            return xdbGetCryptionResult(str.getBytes(Charset.defaultCharset()), 1, str2);
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static String xdbEncryptorStrBase64(String str, String str2) {
        try {
            byte[] xdbGetCryptionResult = xdbGetCryptionResult(str.getBytes(Charset.defaultCharset()), 1, str2);
            if (xdbGetCryptionResult != null) {
                return Base64.encodeToString(xdbGetCryptionResult, 0);
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static byte[] xdbEncryptor(String str) {
        try {
            return xdbGetCryptionResult(str.getBytes(Charset.defaultCharset()), 1, null);
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    public static String xdbDecryptor(byte[] bArr, String str) {
        try {
            byte[] xdbGetCryptionResult = xdbGetCryptionResult(bArr, 2, str);
            if (xdbGetCryptionResult != null) {
                return new String(xdbGetCryptionResult, Charset.defaultCharset());
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    public static String xdbDecryptorStrBase64(String str, String str2) {
        try {
            byte[] xdbGetCryptionResult = xdbGetCryptionResult(Base64.decode(str, 0), 2, str2);
            if (xdbGetCryptionResult != null) {
                return new String(xdbGetCryptionResult, Charset.defaultCharset());
            }
            return "";
        } catch (Exception e) {
            Log.E(e.toString());
            return "";
        }
    }

    private static byte[] xdbGetCryptionResult(byte[] bArr, int i, String str) {
        byte[] bArr2;
        try {
            if (!TextUtils.isEmpty(str)) {
                SecretKey deriveKey = deriveKey(str, 16);
                Cipher instance = Cipher.getInstance(CRYPTO_KEY_ALGORITHM);
                instance.init(i, deriveKey);
                bArr2 = instance.doFinal(bArr);
            } else if (i == 0) {
                return null;
            } else {
                Cipher instance2 = Cipher.getInstance(CRYPTO_KEY_ALGORITHM);
                instance2.init(i, new SecretKeySpec(xdbMealyMachine(5932, 16).getBytes(Charset.defaultCharset()), CRYPTO_KEY_ALGORITHM));
                bArr2 = instance2.doFinal(bArr);
            }
            return bArr2;
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    private static String xdbMealyMachine(int i, int i2) {
        byte[] bArr = new byte[i2];
        int[][] iArr = {new int[]{11, 0}, new int[]{0, 4}, new int[]{8, 15}, new int[]{11, 2}, new int[]{0, 3}, new int[]{9, 0}, new int[]{15, 0}, new int[]{0, 0}, new int[]{5, 0}, new int[]{0, 0}, new int[]{0, 0}, new int[]{1, 6}, new int[]{0, 0}, new int[]{3, 13}, new int[]{0, 0}, new int[]{2, 13}};
        char[][] cArr = {new char[]{'s', '3'}, new char[]{'v', 'n'}, new char[]{'1', '9'}, new char[]{'m', '0'}, new char[]{'e', 'c'}, new char[]{'3', 'B'}, new char[]{'7', 'N'}, new char[]{'k', '2'}, new char[]{'2', 'C'}, new char[]{'a', 'C'}, new char[]{'J', '2'}, new char[]{'y', 'l'}, new char[]{'8', 'd'}, new char[]{'1', '0'}, new char[]{'A', '^'}, new char[]{'7', '0'}};
        int i3 = i;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i3 & 1;
            i3 >>= 1;
            bArr[i5] = (byte) cArr[i4][i6];
            i4 = iArr[i4][i6];
        }
        return new String(bArr, Charset.defaultCharset());
    }

    private static SecretKey deriveKey(String str, int i) {
        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(str.getBytes(StandardCharsets.US_ASCII), i), CRYPTO_KEY_ALGORITHM);
    }
}
