package com.accessorydm.eng.core;

public class XDMEvent {
    public static void XDMSetEvent(Object obj, Object obj2) {
        XDMMsg.xdmSendUIMessage(obj2, obj, null);
    }
}
