package com.accessorydm.noti;

import java.io.Serializable;

public class XNOTIWapPushInfo implements Serializable {
    private static final long serialVersionUID = 1;
    public int nBodyLen;
    public int nContentType;
    public int nHeaderLen;
    public int nMACLen;
    public int nSEC;
    public byte[] szMAC;
}
