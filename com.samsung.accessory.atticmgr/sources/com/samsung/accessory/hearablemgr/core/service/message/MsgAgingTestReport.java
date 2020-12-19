package com.samsung.accessory.hearablemgr.core.service.message;

import seccompat.android.util.Log;

public class MsgAgingTestReport extends Msg {
    private static final String TAG = "Attic_MsgAgingTestReport";
    public byte[] agingTestReport;

    public MsgAgingTestReport() {
        super((byte) MsgID.AGING_TEST_REPORT);
    }

    public MsgAgingTestReport(byte[] bArr) {
        super(bArr);
        this.agingTestReport = getRecvData();
        StringBuilder sb = new StringBuilder();
        byte[] bArr2 = this.agingTestReport;
        int length = bArr2.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X ", Byte.valueOf(bArr2[i])));
        }
        Log.d(TAG, "MsgAgingTestReport : " + sb.toString());
    }
}
