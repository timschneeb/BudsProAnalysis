package com.accessorydm.noti;

public class XNOTIData {
    byte[] pushData;
    int type;

    public XNOTIData(int i, byte[] bArr) {
        this.type = i;
        this.pushData = bArr;
    }
}
