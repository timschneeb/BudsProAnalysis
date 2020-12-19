package com.samsung.accessory.hearablemgr.module.aboutgalaxywearable;

import com.samsung.accessory.hearablemgr.Application;

public class VerificationDeviceInfoConvert {
    public static StringBuilder serialNumberConvert(StringBuilder sb) {
        sb.append("[Left SN] :");
        sb.append(" " + Application.getCoreService().getEarBudsInfo().serialNumber_left);
        sb.append(" (" + Application.getCoreService().getEarBudsInfo().sku_left + ")");
        sb.append("\n");
        sb.append("[Right SN] :");
        sb.append(" " + Application.getCoreService().getEarBudsInfo().serialNumber_right);
        sb.append(" (" + Application.getCoreService().getEarBudsInfo().sku_right + ")");
        return sb;
    }
}
