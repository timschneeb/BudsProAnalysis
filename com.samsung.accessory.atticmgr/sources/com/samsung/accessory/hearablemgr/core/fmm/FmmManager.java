package com.samsung.accessory.hearablemgr.core.fmm;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;
import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.core.fmm.FmmModels;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConfig;
import com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants;
import com.samsung.accessory.hearablemgr.core.service.message.Msg;
import java.nio.ByteBuffer;
import java.util.Base64;
import seccompat.android.util.Log;

public class FmmManager {
    public static final String INTI_VALUE = "";
    private static final String TAG = (Application.TAG_ + FmmManager.class.getSimpleName());
    public static FmmConfig fmmConfig;

    public FmmManager() {
        fmmConfig = new FmmConfig();
    }

    public static void handleResponse(final Context context, final Intent intent) {
        new Handler().post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.fmm.FmmManager.AnonymousClass1 */

            /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x00b5, code lost:
                if (r0.equals(com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConstants.Operation.CONNECTION_CHECK) != false) goto L_0x00e5;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                // Method dump skipped, instructions count: 448
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.fmm.FmmManager.AnonymousClass1.run():void");
            }
        });
    }

    public static void sendAction(final Context context, final String str) {
        new Handler().post(new Runnable() {
            /* class com.samsung.accessory.hearablemgr.core.fmm.FmmManager.AnonymousClass2 */

            /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
            public void run() {
                char c;
                String str = str;
                switch (str.hashCode()) {
                    case -1881281404:
                        if (str.equals(FmmConstants.Operation.REMOVE)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -779858787:
                        if (str.equals(FmmConstants.Operation.STATUS_CHANGE)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -290559266:
                        if (str.equals(FmmConstants.Operation.CONNECTION)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1809891874:
                        if (str.equals(FmmConstants.Operation.RING_STATUS)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0) {
                    Log.d(FmmManager.TAG, "FMM Operation : REMOVE");
                    new FmmModels.DeviceInfoModel(FmmConstants.Operation.REMOVE, UhmFwUtil.getLastLaunchDeviceId()).send(context);
                } else if (c == 1) {
                    boolean isConnected = Application.getCoreService().isConnected();
                    Log.d(FmmManager.TAG, "FMM Operation : CONNECTION : " + isConnected);
                    new FmmModels.ConnectionModel(Application.getCoreService().getEarBudsInfo().address, isConnected).send(context);
                } else if (c == 2) {
                    Log.d(FmmManager.TAG, "FMM Operation : RING_STATUS");
                    new FmmModels.RingStatusModel(Application.getCoreService().getEarBudsInfo().address).send(context);
                } else if (c == 3) {
                    Log.d(FmmManager.TAG, "FMM Operation : STATUS_CHANGE");
                    new FmmModels.StatusChangeModel(Application.getCoreService().getEarBudsInfo().address).send(context);
                }
            }
        });
    }

    public static void responseSetDeviceInfo(Context context) {
        Log.d(TAG, "responseSetDeviceInfo");
        new FmmModels.SetDeviceInfoModel(Application.getCoreService().getEarBudsInfo().address).send(context);
    }

    public static void responseGetDeviceInfo(Context context, ByteBuffer byteBuffer) {
        Log.d(TAG, "responseGetDeviceInfo");
        sendGetFmmConfig(byteBuffer, fmmConfig);
        new FmmModels.getDeviceInfoModel(FmmConstants.Operation.GET_DEVICE_INFO, Application.getCoreService().getEarBudsInfo().address, fmmConfig).send(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r0v43 */
    /* JADX WARN: Type inference failed for: r0v47 */
    /* JADX WARN: Type inference failed for: r0v73 */
    /* JADX WARN: Type inference failed for: r0v76 */
    /* JADX WARN: Type inference failed for: r0v81 */
    /* JADX WARN: Type inference failed for: r0v82 */
    /* JADX WARN: Type inference failed for: r12v34 */
    /* JADX WARN: Type inference failed for: r12v35 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void sendSetFmmConfig(com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConfig r16) {
        /*
        // Method dump skipped, instructions count: 638
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.fmm.FmmManager.sendSetFmmConfig(com.samsung.accessory.hearablemgr.core.fmm.utils.FmmConfig):void");
    }

    public static void sendGetFmmConfig(ByteBuffer byteBuffer, FmmConfig fmmConfig2) {
        StringBuilder sb = new StringBuilder();
        byte[] array = byteBuffer.array();
        int length = array.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X ", Byte.valueOf(array[i])));
        }
        Log.d(TAG, "GET_FMM_CONFIG : " + sb.toString());
        FmmConfig.revision = byteBuffer.get();
        byte b = byteBuffer.get();
        boolean z = ByteUtil.valueOfLeft(b) == 1;
        boolean z2 = ByteUtil.valueOfRight(b) == 1;
        String str = "Y";
        String str2 = "";
        if (z) {
            try {
                byte b2 = byteBuffer.get();
                if (b2 != -1) {
                    FmmConfig.left_findingSupport = b2 == 1 ? str : FmmConstants.NOT_SUPPORT;
                } else {
                    FmmConfig.left_findingSupport = str2;
                }
                byte b3 = byteBuffer.get();
                if (b3 != -1) {
                    FmmConfig.left_e2e = b3 == 1 ? str : FmmConstants.NOT_SUPPORT;
                } else {
                    FmmConfig.left_e2e = str2;
                }
                byte[] bArr = new byte[16];
                byteBuffer.get(bArr);
                StringBuilder sb2 = new StringBuilder();
                int length2 = bArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    sb2.append(String.format("%02X ", Byte.valueOf(bArr[i2])));
                }
                Log.d(TAG, "GET_FMM_CONFIG left_secret_key : " + sb2.toString());
                if (FmmConfig.revision < 3) {
                    FmmConfig.left_secretKey = isSupportedValue(bArr) ? encode(bArr) : str2;
                } else {
                    byte[] makeXOR = makeXOR(bArr);
                    FmmConfig.left_secretKey = isSupportedValue(makeXOR) ? encode(makeXOR) : str2;
                }
                int i3 = byteBuffer.getInt();
                if (i3 == 0) {
                    i3 = -1;
                }
                FmmConfig.left_maxN = i3;
                FmmConfig.left_region = byteBuffer.get();
                byte b4 = byteBuffer.get();
                if (FmmConfig.revision < 2) {
                    byte[] bArr2 = new byte[26];
                    byteBuffer.get(bArr2);
                    FmmConfig.left_fmmToken = new String(bArr2, 0, Math.max(Math.min((int) b4, 26), 0));
                } else {
                    byte[] bArr3 = new byte[31];
                    byteBuffer.get(bArr3);
                    FmmConfig.left_fmmToken = new String(bArr3, 0, Math.max(Math.min((int) b4, 31), 0));
                    byte[] bArr4 = new byte[16];
                    byteBuffer.get(bArr4);
                    if (FmmConfig.revision < 3) {
                        FmmConfig.left_iv = isSupportedValue(bArr4) ? encode(bArr4) : str2;
                    } else {
                        byte[] makeXOR2 = makeXOR(bArr4);
                        FmmConfig.left_iv = isSupportedValue(makeXOR2) ? encode(makeXOR2) : str2;
                    }
                }
                byte[] bArr5 = new byte[11];
                byteBuffer.get(bArr5);
                FmmConfig.left_sn = isSupportedValue(bArr5) ? new String(bArr5) : str2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (z2) {
            byte b5 = byteBuffer.get();
            if (b5 != -1) {
                FmmConfig.right_findingSupport = b5 == 1 ? str : FmmConstants.NOT_SUPPORT;
            } else {
                FmmConfig.right_findingSupport = str2;
            }
            byte b6 = byteBuffer.get();
            if (b6 != -1) {
                if (b6 != 1) {
                    str = FmmConstants.NOT_SUPPORT;
                }
                FmmConfig.right_e2e = str;
            } else {
                FmmConfig.right_e2e = str2;
            }
            byte[] bArr6 = new byte[16];
            byteBuffer.get(bArr6);
            if (FmmConfig.revision < 3) {
                FmmConfig.right_secretKey = isSupportedValue(bArr6) ? encode(bArr6) : str2;
            } else {
                byte[] makeXOR3 = makeXOR(bArr6);
                FmmConfig.right_secretKey = isSupportedValue(makeXOR3) ? encode(makeXOR3) : str2;
            }
            int i4 = byteBuffer.getInt();
            if (i4 == 0) {
                i4 = -1;
            }
            FmmConfig.right_maxN = i4;
            FmmConfig.right_region = byteBuffer.get();
            byte b7 = byteBuffer.get();
            if (FmmConfig.revision < 2) {
                byte[] bArr7 = new byte[26];
                byteBuffer.get(bArr7);
                FmmConfig.right_fmmToken = new String(bArr7, 0, Math.max(Math.min((int) b7, 26), 0));
            } else {
                byte[] bArr8 = new byte[31];
                byteBuffer.get(bArr8);
                FmmConfig.right_fmmToken = new String(bArr8, 0, Math.max(Math.min((int) b7, 31), 0));
                byte[] bArr9 = new byte[16];
                byteBuffer.get(bArr9);
                if (FmmConfig.revision < 3) {
                    FmmConfig.right_iv = isSupportedValue(bArr9) ? encode(bArr9) : str2;
                } else {
                    byte[] makeXOR4 = makeXOR(bArr9);
                    FmmConfig.right_iv = isSupportedValue(makeXOR4) ? encode(makeXOR4) : str2;
                }
            }
            byte[] bArr10 = new byte[11];
            byteBuffer.get(bArr10);
            if (isSupportedValue(bArr10)) {
                str2 = new String(bArr10);
            }
            FmmConfig.right_sn = str2;
        }
        FmmConfig.printFmmConfing();
    }

    private static byte[] decode(String str) {
        byte[] bArr;
        if (Build.VERSION.SDK_INT >= 26) {
            bArr = Base64.getDecoder().decode(str.getBytes());
        } else {
            bArr = android.util.Base64.decode(str.getBytes(), 0);
        }
        String str2 = TAG;
        Log.d(str2, "decode size : " + bArr.length);
        String str3 = TAG;
        Log.d(str3, "decode data : " + new String(bArr));
        return bArr;
    }

    private static String encode(byte[] bArr) {
        String str;
        if (Build.VERSION.SDK_INT >= 26) {
            str = Base64.getEncoder().encodeToString(bArr);
        } else {
            str = android.util.Base64.encodeToString(bArr, 0);
        }
        String str2 = TAG;
        Log.d(str2, "encode data : " + str);
        return str;
    }

    private static boolean isSupportedValue(byte[] bArr) {
        for (byte b : bArr) {
            if (b != -1) {
                return true;
            }
        }
        return false;
    }

    private static byte[] makeXOR(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = (byte) (bArr[i] ^ Msg.SOM);
        }
        return bArr2;
    }
}
