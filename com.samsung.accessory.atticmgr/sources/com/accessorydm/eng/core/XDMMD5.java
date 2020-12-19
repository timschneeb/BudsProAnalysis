package com.accessorydm.eng.core;

import com.samsung.android.fotaprovider.log.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XDMMD5 {
    private Object MD5Sum;

    public final String xdmComputeMD5Credentials(String str, String str2, byte[] bArr) {
        String concat = str.concat(":").concat(str2);
        try {
            this.MD5Sum = MessageDigest.getInstance("MD5");
            ((MessageDigest) this.MD5Sum).reset();
        } catch (NoSuchAlgorithmException e) {
            Log.E(e.toString());
        }
        String concat2 = XDMBase64.xdmBase64Encode(((MessageDigest) this.MD5Sum).digest(concat.getBytes())).concat(":");
        int length = concat2.getBytes().length;
        byte[] bArr2 = new byte[(bArr.length + length)];
        System.arraycopy(concat2.getBytes(), 0, bArr2, 0, length);
        System.arraycopy(bArr, 0, bArr2, length, bArr.length);
        return XDMBase64.xdmBase64Encode(((MessageDigest) this.MD5Sum).digest(bArr2));
    }
}
