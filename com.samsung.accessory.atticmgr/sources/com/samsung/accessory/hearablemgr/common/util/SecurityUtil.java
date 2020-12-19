package com.samsung.accessory.hearablemgr.common.util;

import android.os.Build;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityUtil {
    private static final String CHARSET = "UTF-8";
    private static final String KEY_SINATURE_ALGORITHM = "SHA256withRSA";
    public static final String PublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKBh4gR4gvhI41XDLkhWvWuiGSfxdBu/pJzBFtHTE4Nc8f0OKKK0orO/uZL9nk8mmxiagKbwOW8kZHA7GQ5V5NECAwEAAQ==";

    public static PublicKey loadPublicKey(String str) throws GeneralSecurityException {
        byte[] bArr;
        if (Build.VERSION.SDK_INT >= 26) {
            bArr = Base64.getDecoder().decode(str.getBytes());
        } else {
            bArr = android.util.Base64.decode(str.getBytes(), 0);
        }
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr));
    }

    public static boolean verify(String str, String str2) throws Exception {
        byte[] bArr;
        PublicKey loadPublicKey = loadPublicKey(PublicKey);
        Signature instance = Signature.getInstance(KEY_SINATURE_ALGORITHM);
        instance.initVerify(loadPublicKey);
        instance.update(str.getBytes("UTF-8"));
        if (Build.VERSION.SDK_INT >= 26) {
            bArr = Base64.getDecoder().decode(str2);
        } else {
            bArr = android.util.Base64.decode(str2, 0);
        }
        return instance.verify(bArr);
    }
}
