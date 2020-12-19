package com.samsung.accessory.hearablemgr.core.service.message;

import android.widget.Toast;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.util.ByteUtil;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import seccompat.android.util.Log;

public class MsgUsageReport extends Msg {
    private static final int LENGTH_KEY = 5;
    public static final byte RESPONSE_ERROR = 1;
    public static final byte RESPONSE_SUCCESS = 0;
    private static final String TAG = "Attic_MsgUsageReport";
    public byte responseCode;
    public Map<String, Long> usageReport;

    MsgUsageReport(byte[] bArr) {
        super(bArr);
        this.usageReport = new HashMap();
        ByteBuffer recvDataByteBuffer = getRecvDataByteBuffer();
        int u8 = ByteUtil.toU8(recvDataByteBuffer.get());
        if (recvDataByteBuffer.remaining() != u8 * 9) {
            this.responseCode = 1;
            Log.e(TAG, "MsgUsageReport : Buffer length error !!!");
            if (Application.DEBUG_MODE) {
                Toast.makeText(Application.getContext(), "MsgUsageReport : Buffer length error !!!", 1).show();
            } else {
                return;
            }
        }
        byte[] bArr2 = new byte[5];
        for (int i = 0; i < u8; i++) {
            recvDataByteBuffer.get(bArr2);
            String bufferKeyToString = bufferKeyToString(bArr2);
            long intToU32 = ByteUtil.intToU32(recvDataByteBuffer.getInt());
            Log.d(TAG, "key = " + bufferKeyToString + ", value = " + intToU32);
            this.usageReport.put(bufferKeyToString, Long.valueOf(intToU32));
        }
        this.responseCode = 0;
    }

    public MsgUsageReport(byte b) {
        super(MsgID.USAGE_REPORT, true);
        this.responseCode = b;
    }

    @Override // com.samsung.accessory.hearablemgr.core.service.message.Msg
    public byte[] getData() {
        return new byte[]{this.responseCode};
    }

    private String bufferKeyToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            if (b == 0) {
                break;
            }
            sb.append((char) b);
        }
        return sb.toString();
    }
}
