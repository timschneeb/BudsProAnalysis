package com.samsung.accessory.hearablemgr.core.service.message;

import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import com.samsung.accessory.hearablemgr.common.util.CRC16;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import seccompat.android.util.Log;

public class Msg {
    public static final byte EOM = -35;
    public static final int INDEX_HEADER = 1;
    public static final int INDEX_PAYLOAD_START = 3;
    public static final int INDEX_SOM = 0;
    public static final int LENGTH_CRC = 2;
    public static final int LENGTH_EOM = 1;
    public static final int LENGTH_HEADER = 2;
    public static final int LENGTH_MSG_ID = 1;
    public static final int LENGTH_SOM = 1;
    public static final byte PARAM_OFF = 0;
    public static final byte PARAM_ON = 1;
    public static final byte RESULT_FAILURE = 1;
    public static final byte RESULT_SUCCESS = 0;
    public static final byte SC_PARAM_OFF = 1;
    public static final byte SC_PARAM_ON = 0;
    public static final byte SOM = -3;
    private static final String TAG = "Attic_Msg";
    public static final boolean TYPE_REQUEST = false;
    public static final boolean TYPE_RESPONSE = true;
    public byte[] bufRecvFrame;
    public boolean fragment;
    public byte id;
    public int payloadLength;
    public boolean typeRes;

    public static boolean checkExtended(byte b) {
        return (b & 16) != 0;
    }

    public static int getPayloadLength(int i) {
        return i & 1023;
    }

    private static boolean isFragment(int i) {
        return (i & 8192) != 0;
    }

    private static boolean isResponse(int i) {
        return (i & 4096) != 0;
    }

    public byte[] getData() {
        return null;
    }

    public int getPayloadStartIndex() {
        return 3;
    }

    public Msg(byte b) {
        this.id = b;
    }

    public Msg(byte b, boolean z) {
        this.id = b;
        this.typeRes = z;
    }

    public Msg(byte[] bArr) {
        this.bufRecvFrame = bArr;
        int u8 = (ByteUtil.toU8(this.bufRecvFrame[2]) << 8) + ByteUtil.toU8(this.bufRecvFrame[1]);
        this.fragment = isFragment(u8);
        this.typeRes = isResponse(u8);
        this.payloadLength = getPayloadLength(u8);
        this.id = this.bufRecvFrame[3];
        Log.v(TAG, "MsgPopcorn(" + String.format(ByteUtil.HEX_FORMAT, Byte.valueOf(this.id)) + ") : " + this.fragment + ":" + this.typeRes + ":" + this.payloadLength);
    }

    public byte[] getRecvData() {
        byte[] bArr = this.bufRecvFrame;
        if (bArr == null) {
            return null;
        }
        return Arrays.copyOfRange(bArr, getDataStartIndex(), (this.bufRecvFrame.length - 2) - 1);
    }

    public ByteBuffer getRecvDataByteBuffer() {
        byte[] bArr = this.bufRecvFrame;
        if (bArr == null) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr, getDataStartIndex(), ((this.bufRecvFrame.length - 2) - 1) - getDataStartIndex());
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap;
    }

    public int getDataStartIndex() {
        return getPayloadStartIndex() + 1;
    }

    public byte[] toByteArray() {
        int i;
        byte b = this.id;
        byte[] data = getData();
        if (data == null) {
            i = 0;
        } else {
            i = data.length;
        }
        int i2 = i + 1 + 2;
        int i3 = i2 + 3 + 1;
        byte[] bArr = new byte[i3];
        bArr[0] = -3;
        bArr[i3 - 1] = EOM;
        byte[] createHeader = createHeader(i2);
        bArr[1] = createHeader[0];
        bArr[2] = createHeader[1];
        byte[] bArr2 = new byte[i2];
        bArr2[0] = b;
        if (data != null) {
            System.arraycopy(data, 0, bArr2, 1, data.length);
        }
        int crc16_ccitt = CRC16.crc16_ccitt(bArr2, bArr2.length - 2);
        bArr2[bArr2.length - 2] = (byte) (crc16_ccitt & 255);
        bArr2[bArr2.length - 1] = (byte) ((crc16_ccitt >> 8) & 255);
        System.arraycopy(bArr2, 0, bArr, 3, bArr2.length);
        return bArr;
    }

    private byte[] createHeader(int i) {
        byte[] fromShort = ByteUtil.fromShort(i & 1023);
        if (this.fragment) {
            fromShort[1] = (byte) (fromShort[1] | 32);
        }
        if (this.typeRes) {
            fromShort[1] = (byte) (fromShort[1] | 16);
        }
        return fromShort;
    }

    private static boolean checkCRC(byte[] bArr) {
        if (bArr.length < 2) {
            return false;
        }
        byte b = bArr[bArr.length - 1];
        bArr[bArr.length - 1] = bArr[bArr.length - 2];
        bArr[bArr.length - 2] = b;
        if (CRC16.crc16_ccitt(bArr, bArr.length) != 0) {
            return false;
        }
        return true;
    }

    public static Msg createMsg(byte[] bArr) {
        if (!checkCRC(Arrays.copyOfRange(bArr, 3, bArr.length - 1))) {
            Log.e(TAG, "createMsg() : CRC error !!!");
            return null;
        }
        byte b = bArr[3];
        if (b == -84) {
            return new MsgSetFmmConfig(bArr);
        }
        if (b == -83) {
            return new MsgGetFmmConfig(bArr);
        }
        if (!(b == -62 || b == -61)) {
            if (b == 96) {
                return new MsgStatusUpdated(bArr);
            }
            if (b == 97) {
                return new MsgExtendedStatusUpdated(bArr);
            }
            switch (b) {
                case -120:
                    return new MsgManagerInfo(bArr);
                case -118:
                    return new MsgSetInBandRingtone(bArr);
                case -111:
                    return new MsgTouchUpdated(bArr);
                case -109:
                    return new MsgTouchPadOther(bArr);
                case -107:
                    return new MsgOutsideDoubleTap(bArr);
                case -98:
                    return new MsgCheckTheFitOfEarbudsResult(bArr);
                case -95:
                case -91:
                    break;
                case -93:
                    return new MsgMuteEarbudStatusUpdated(bArr);
                case -76:
                    return new MsgFotaDeviceInfoSwVersion(bArr);
                case 34:
                    return new MsgDebugSKU(bArr);
                case 38:
                    return new MsgDebugData(bArr);
                case 41:
                    return new MsgDebugSerialNumber(bArr);
                case 74:
                    return new MsgAgingTestReport(bArr);
                case 80:
                    return new MsgReset(bArr);
                case 99:
                    return new MsgVersionInfo(bArr);
                case 119:
                    return new MsgNoiseControlsUpdate(bArr);
                default:
                    switch (b) {
                        case -102:
                            return new MsgVoiceWakeUpEvent(bArr);
                        case -101:
                            return new MsgNoiseReductionModeUpdated(bArr);
                        case -100:
                            return new MsgVoiceWakeUpListeningStatus(bArr);
                        default:
                            switch (b) {
                                case -71:
                                    return new MsgFotaResult(bArr);
                                case -70:
                                    return new MsgFotaEmergency(bArr);
                                case -69:
                                    return new MsgFotaSession(bArr);
                                case -68:
                                    return new MsgFotaControl(bArr);
                                case -67:
                                    return new MsgFotaDownloadData(bArr);
                                case -66:
                                    return new MsgFotaUpdated(bArr);
                                default:
                                    switch (b) {
                                        case 49:
                                            return new MsgLogCoredumpDataSize(bArr);
                                        case 50:
                                            return new MsgLogCoredumpData(bArr);
                                        case 51:
                                            return new MsgLogCoredumpComplete(bArr);
                                        case 52:
                                            return new MsgLogTraceStart(bArr);
                                        case 53:
                                            return new MsgLogTraceData(bArr);
                                        case 54:
                                            return new MsgLogTraceComplete(bArr);
                                        case 55:
                                            return new MsgLogRoleSwitch(bArr);
                                        case 56:
                                            return new MsgLogCoredumpTransmissionDone(bArr);
                                        case 57:
                                            return new MsgLogTraceTransmissionDone(bArr);
                                        case 58:
                                            return new MsgLogSessionOpen(bArr);
                                        case 59:
                                            return new MsgLogSessionClose(bArr);
                                        default:
                                            switch (b) {
                                                case 64:
                                                    return new MsgUsageReport(bArr);
                                                case 65:
                                                    return new MsgMeteringReport(bArr);
                                                case 66:
                                                    return new MsgUniversalAcknowledgement(bArr);
                                                default:
                                                    Log.e(TAG, "createMsg() : return null (" + String.format(ByteUtil.HEX_FORMAT, Byte.valueOf(bArr[3])) + ")");
                                                    return null;
                                            }
                                    }
                            }
                    }
            }
        }
        return new Msg(bArr);
    }
}
