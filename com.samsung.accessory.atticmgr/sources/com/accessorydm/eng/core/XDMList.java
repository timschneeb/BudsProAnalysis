package com.accessorydm.eng.core;

public class XDMList {
    public Object item;
    public XDMList next;

    public static XDMList xdmListAppend(XDMList xDMList, XDMList xDMList2, Object obj) {
        XDMList xDMList3 = new XDMList();
        xDMList3.item = obj;
        xDMList3.next = null;
        if (xDMList == null) {
            return xDMList3;
        }
        if (xDMList2 == null) {
            xDMList2 = xDMList;
            while (true) {
                XDMList xDMList4 = xDMList2.next;
                if (xDMList4 == null) {
                    break;
                }
                xDMList2 = xDMList4;
            }
        }
        xDMList2.next = xDMList3;
        return xDMList;
    }

    public XDMList xdmListAppend2(XDMList xDMList, Object obj) {
        XDMList xDMList2 = new XDMList();
        xDMList2.item = obj;
        xDMList2.next = null;
        if (xDMList == null) {
            return xDMList2;
        }
        XDMList xDMList3 = xDMList;
        while (true) {
            XDMList xDMList4 = xDMList3.next;
            if (xDMList4 == null) {
                xDMList3.next = xDMList2;
                return xDMList;
            }
            xDMList3 = xDMList4;
        }
    }

    public static Object xdmListGetItem(XDMList xDMList) {
        if (xDMList == null) {
            return null;
        }
        return xDMList.item;
    }

    public static XDMList xdmListGetItemPtr(XDMList xDMList) {
        if (xDMList == null) {
            return null;
        }
        return xDMList.next;
    }

    public static XDMText xdmListCreateText(int i, Object obj) {
        XDMText xDMText = new XDMText();
        if (obj != null) {
            xDMText.text = String.valueOf(obj);
            xDMText.len = xDMText.text.length();
            xDMText.size = xDMText.len;
        } else {
            xDMText.text = "";
            xDMText.size = i;
            xDMText.len = 0;
        }
        return xDMText;
    }

    public static XDMText xdmListAppendStrText(XDMText xDMText, String str) {
        int length = str.length();
        XDMText xdmListVerifyTextSize = xdmListVerifyTextSize(xDMText, xDMText.len + length);
        xdmListVerifyTextSize.text = String.valueOf(xdmListVerifyTextSize.text) + str;
        xdmListVerifyTextSize.len = xdmListVerifyTextSize.len + length;
        return xdmListVerifyTextSize;
    }

    public static XDMText xdmListCopyStrText(XDMText xDMText, String str) {
        int length = str.length();
        XDMText xdmListVerifyTextSize = xdmListVerifyTextSize(xDMText, xDMText.len + length);
        xdmListVerifyTextSize.text = str;
        xdmListVerifyTextSize.len = length;
        return xdmListVerifyTextSize;
    }

    public static XDMText xdmListVerifyTextSize(XDMText xDMText, int i) {
        if (xDMText.size < i) {
            String str = xDMText.text;
            xDMText.text = "";
            xDMText.size = i;
            xDMText.text = str;
        }
        return xDMText;
    }

    public static XDMText xdmListAppendText(XDMText xDMText, XDMText xDMText2) {
        XDMText xdmListVerifyTextSize = xdmListVerifyTextSize(xDMText, xDMText.len + xDMText2.len);
        xdmListVerifyTextSize.text = String.valueOf(xdmListVerifyTextSize.text) + xDMText2.text;
        xdmListVerifyTextSize.len = xdmListVerifyTextSize.len + xDMText2.len;
        return xdmListVerifyTextSize;
    }
}
