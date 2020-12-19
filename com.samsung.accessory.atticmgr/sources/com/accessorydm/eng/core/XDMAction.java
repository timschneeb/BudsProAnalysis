package com.accessorydm.eng.core;

import com.samsung.android.fotaprovider.log.Log;
import java.util.LinkedList;

public class XDMAction {
    public int CmdID;
    public int MsgID;
    public LinkedList<Object> sourceList = new LinkedList<>();
    public String szCmdName;

    public void xdmCreateAction(String str, boolean z) {
        this.szCmdName = str;
        if (!z) {
            this.sourceList = null;
        }
    }

    public XDMAction xdmFindAction(int i, int i2, String str) {
        new XDMAction();
        Log.I("wsFindAction actionList size : " + 0);
        return null;
    }
}
