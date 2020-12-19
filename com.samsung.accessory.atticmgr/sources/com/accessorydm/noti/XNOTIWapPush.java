package com.accessorydm.noti;

import java.io.Serializable;

public class XNOTIWapPush implements Serializable {
    private static final long serialVersionUID = 1;
    public byte[] pBody;
    public XNOTIWapPushInfo tWapPushInfo = new XNOTIWapPushInfo();
}
