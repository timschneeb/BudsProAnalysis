package com.accessorydm.eng.core;

import android.util.Base64;
import java.nio.charset.Charset;

public class XDMBase64 {
    public static String xdmBase64Encode(String str) {
        return Base64.encodeToString(str.getBytes(Charset.defaultCharset()), 2);
    }

    public static String xdmBase64Encode(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public static byte[] xdmBase64Decode(String str) {
        return Base64.decode(str.getBytes(Charset.defaultCharset()), 2);
    }

    public static byte[] xdmBase64Decode(byte[] bArr) {
        return Base64.decode(bArr, 2);
    }
}
