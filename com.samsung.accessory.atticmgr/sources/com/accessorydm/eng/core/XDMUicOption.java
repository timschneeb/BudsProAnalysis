package com.accessorydm.eng.core;

import com.accessorydm.interfaces.XUICInterface;

public class XDMUicOption implements XUICInterface {
    public int UICType;
    public int appId;
    public XDMText defaultResponse = new XDMText();
    public int echoType;
    public int inputType;
    public String m_szUicMenuTitle;
    public int maxDT;
    public int maxLen;
    public int minDT;
    public long progrCurSize;
    public long progrMaxSize;
    public int progrType;
    public XDMText text = new XDMText();
    public String[] uicMenuList = new String[32];
    public int uicMenuNumbers;

    XDMUicOption() {
    }
}
