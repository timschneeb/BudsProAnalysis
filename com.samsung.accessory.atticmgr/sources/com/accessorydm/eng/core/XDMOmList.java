package com.accessorydm.eng.core;

import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;

public class XDMOmList implements XDMInterface {
    public Object data;
    public XDMOmList next;

    public static String xdmOmGetFormatString(int i) {
        switch (i) {
            case 1:
                return "b64";
            case 2:
                return "bin";
            case 3:
                return "bool";
            case 4:
                return "chr";
            case 5:
                return "int";
            case 6:
                return "node";
            case 7:
                return "null";
            case 8:
                return "xml";
            case 9:
                return "float";
            case 10:
                return BuddyContract.Event.DATE;
            case 11:
                return XDBInterface.XDM_SQL_DB_POLLING_TIME;
            default:
                return null;
        }
    }

    public static void xdmOmDeleteAclList(XDMOmList xDMOmList) {
        while (xDMOmList != null) {
            xDMOmList = xDMOmList.next;
        }
    }

    public void xdmOmDeleteMimeList(XDMOmList xDMOmList) {
        while (xDMOmList != null) {
            XDMOmList xDMOmList2 = xDMOmList.next;
            xDMOmList.data = null;
            xDMOmList = xDMOmList2;
        }
    }

    public static int xdmOmGetFormatFromString(String str) {
        if ("b64".compareTo(str) == 0) {
            return 1;
        }
        if ("bin".compareTo(str) == 0) {
            return 2;
        }
        if ("bool".compareTo(str) == 0) {
            return 3;
        }
        if ("chr".compareTo(str) == 0) {
            return 4;
        }
        if ("int".compareTo(str) == 0) {
            return 5;
        }
        if ("node".compareTo(str) == 0) {
            return 6;
        }
        if ("null".compareTo(str) == 0) {
            return 7;
        }
        if ("xml".compareTo(str) == 0) {
            return 8;
        }
        if ("float".compareTo(str) == 0) {
            return 9;
        }
        if (XDBInterface.XDM_SQL_DB_POLLING_TIME.compareTo(str) == 0) {
            return 11;
        }
        return BuddyContract.Event.DATE.compareTo(str) == 0 ? 10 : 12;
    }
}
