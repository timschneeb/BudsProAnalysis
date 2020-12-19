package com.accessorydm.eng.core;

import android.text.TextUtils;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.android.fotaprovider.log.Log;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class XDMAuth implements XDMInterface {
    private static final int SHA_KEY_PAD_LEN = 64;
    private static final int SHA_KEY_PAD_LEN_ = 64;

    public static String xdmAuthAAuthType2String(int i) {
        return i != 0 ? i != 1 ? i != 2 ? XDMInterface.AUTH_TYPE_NONE : XDMInterface.AUTH_TYPE_HMAC : XDMInterface.AUTH_TYPE_DIGEST : XDMInterface.AUTH_TYPE_BASIC;
    }

    public static String xdmAuthCredType2String(int i) {
        if (i == 0) {
            return XDMInterface.CRED_TYPE_BASIC;
        }
        if (i == 1) {
            return XDMInterface.CRED_TYPE_MD5;
        }
        if (i == 2) {
            return XDMInterface.CRED_TYPE_HMAC;
        }
        Log.E("Not Support Auth Type");
        return null;
    }

    public static int xdmAuthCredString2Type(String str) {
        if (XDMInterface.CRED_TYPE_BASIC.compareTo(str) == 0) {
            return 0;
        }
        if (XDMInterface.CRED_TYPE_MD5.compareTo(str) == 0) {
            return 1;
        }
        return XDMInterface.CRED_TYPE_HMAC.compareTo(str) == 0 ? 2 : -1;
    }

    public static int xdmAuthAAuthtring2Type(String str) {
        if (XDMInterface.AUTH_TYPE_BASIC.compareTo(str) == 0) {
            return 0;
        }
        if (XDMInterface.AUTH_TYPE_DIGEST.compareTo(str) == 0) {
            return 1;
        }
        return XDMInterface.AUTH_TYPE_HMAC.compareTo(str) == 0 ? 2 : -1;
    }

    public static String xdmAuthMakeDigest(int i, String str, String str2, byte[] bArr, int i2, byte[] bArr2, long j) {
        MessageDigest messageDigest;
        if (i != 0) {
            if (i != 1) {
                if (i != 2 && i != 3) {
                    Log.I("Not Support Auth Type");
                    return null;
                } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || bArr == null || i2 <= 0 || bArr2 == null || j <= 0) {
                    Log.E("userName or passWord or nonce or nonceLength or packetBody or bodyLength is NULL");
                    return null;
                }
            } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || bArr == null || i2 <= 0) {
                Log.E("userName or passWord or nonce or nonceLength is NULL");
                return null;
            }
        } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.E("userName or passWord is NULL");
            return null;
        }
        if (i == 0) {
            String concat = str.concat(":").concat(str2);
            String xdmBase64Encode = XDMBase64.xdmBase64Encode(concat);
            Log.H("XDM_CRED_TYPE_BASIC cred:" + concat + " ret:" + xdmBase64Encode);
            return xdmBase64Encode;
        } else if (i == 1) {
            String xdmComputeMD5Credentials = new XDMMD5().xdmComputeMD5Credentials(str, str2, bArr);
            Log.H("XDM_CRED_TYPE_MD5 nonce= " + new String(bArr, Charset.defaultCharset()) + " ret= " + xdmComputeMD5Credentials);
            return xdmComputeMD5Credentials;
        } else if (i != 2 && i != 3) {
            return null;
        } else {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                Log.E(e.toString());
                messageDigest = null;
            }
            if (messageDigest == null) {
                return null;
            }
            String concat2 = str.concat(":").concat(str2);
            messageDigest.reset();
            String concat3 = XDMBase64.xdmBase64Encode(messageDigest.digest(concat2.getBytes())).concat(":").concat(new String(bArr, Charset.defaultCharset())).concat(":").concat(XDMBase64.xdmBase64Encode(messageDigest.digest(bArr2)));
            if (i == 2) {
                return XDMBase64.xdmBase64Encode(messageDigest.digest(concat3.getBytes()));
            }
            return new String(messageDigest.digest(concat3.getBytes()), Charset.defaultCharset());
        }
    }

    public static String xdmAuthMakeDigestSHA1(int i, byte[] bArr, int i2, byte[] bArr2) {
        MessageDigest messageDigest;
        byte[] bArr3 = new byte[64];
        byte[] bArr4 = new byte[64];
        if (i != 4) {
            Log.E("Not Support Auth Type.");
            return null;
        } else if (bArr == null || bArr2 == null) {
            return null;
        } else {
            if (i2 <= 64) {
                for (int i3 = 0; i3 < i2; i3++) {
                    bArr3[i3] = bArr[i3];
                }
            }
            for (int i4 = 0; i4 < 64; i4++) {
                bArr4[i4] = bArr3[i4];
            }
            for (int i5 = 0; i5 < 64; i5++) {
                bArr3[i5] = (byte) (bArr3[i5] ^ MsgID.LOG_TRACE_COMPLETE);
                bArr4[i5] = (byte) (bArr4[i5] ^ 92);
            }
            try {
                messageDigest = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                Log.E(e.toString());
                messageDigest = null;
            }
            if (messageDigest == null) {
                return null;
            }
            messageDigest.update(bArr3);
            messageDigest.update(bArr2);
            byte[] digest = messageDigest.digest();
            messageDigest.update(bArr4);
            messageDigest.update(digest);
            String xdmLibBytesToHexString = XDMMem.xdmLibBytesToHexString(messageDigest.digest());
            return !TextUtils.isEmpty(xdmLibBytesToHexString) ? xdmLibBytesToHexString.toUpperCase(Locale.US) : xdmLibBytesToHexString;
        }
    }
}
