package com.accessorydm.tp;

import com.accessorydm.db.file.XDBUrlInfo;
import com.accessorydm.interfaces.XTPInterface;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;

public class XTPHttpUtil implements XTPInterface {
    public static String xtpHttpParsePath(String str) {
        int indexOf = str.indexOf("://");
        return str.substring(indexOf + str.substring(indexOf + 3).indexOf(47) + 3);
    }

    public static int xtpHttpGetConnectType(String str) {
        String substring = str.substring(0, 5);
        if (substring.equals("http:")) {
            return 2;
        }
        if (substring.equals(HttpNetworkInterface.XTP_NETWORK_TYPE_HTTPS)) {
            return 1;
        }
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002d A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int xtpHttpExchangeProtocolType(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 3213448(0x310888, float:4.503E-39)
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x001b
            r1 = 99617003(0x5f008eb, float:2.2572767E-35)
            if (r0 == r1) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r0 = "https"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0025
            r4 = r2
            goto L_0x0026
        L_0x001b:
            java.lang.String r0 = "http"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0025
            r4 = r3
            goto L_0x0026
        L_0x0025:
            r4 = -1
        L_0x0026:
            if (r4 == 0) goto L_0x002d
            if (r4 == r3) goto L_0x002b
            return r2
        L_0x002b:
            r4 = 2
            return r4
        L_0x002d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.tp.XTPHttpUtil.xtpHttpExchangeProtocolType(java.lang.String):int");
    }

    public static XDBUrlInfo xtpURLParser(String str) {
        String str2;
        String str3;
        XDBUrlInfo xDBUrlInfo = new XDBUrlInfo();
        if (str.startsWith("https://")) {
            str3 = str.substring(8, str.length());
            str2 = HttpNetworkInterface.XTP_NETWORK_TYPE_HTTPS;
        } else if (str.startsWith("http://")) {
            str3 = str.substring(7, str.length());
            str2 = HttpNetworkInterface.XTP_NETWORK_TYPE_HTTP;
        } else {
            xDBUrlInfo.pURL = "http://";
            return xDBUrlInfo;
        }
        int indexOf = str3.indexOf(47);
        String substring = indexOf != -1 ? str3.substring(indexOf, str3.length()) : "";
        String[] split = str3.split("/");
        String str4 = split[0];
        String[] split2 = split[0].split(":");
        int i = 80;
        if (split2.length >= 2) {
            i = Integer.valueOf(split2[1]).intValue();
            str4 = split2[0];
        } else if (xtpHttpExchangeProtocolType(str2) == 1) {
            i = 443;
        }
        xDBUrlInfo.pURL = str;
        xDBUrlInfo.pAddress = str4;
        xDBUrlInfo.pPath = substring;
        xDBUrlInfo.pProtocol = str2;
        xDBUrlInfo.nPort = i;
        return xDBUrlInfo;
    }
}
