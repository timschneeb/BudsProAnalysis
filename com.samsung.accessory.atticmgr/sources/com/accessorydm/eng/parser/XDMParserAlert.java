package com.accessorydm.eng.parser;

import com.accessorydm.agent.XDMHandleCmd;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserAlert extends XDMHandleCmd implements XDMWbxml {
    public int cmdid;
    public XDMParserCred cred;
    public int is_noresp = 0;
    public XDMList itemlist = null;
    public String m_szCorrelator = null;
    public String m_szData;

    public int xdmParParseAlert(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(6);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xDMParser.xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseZeroBitTagCheck;
        }
        int i = -1;
        do {
            try {
                i = xDMParser.xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i == 1) {
                xDMParser.xdmParParseReadElement();
                xdmAgentHdlCmdAlert(xDMParser.userdata, this);
                return 0;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                xDMParser.codePage = i;
                continue;
            } else if (i == 11) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i == 20) {
                this.itemlist = xDMParser.xdmParParseItemlist(this.itemlist);
                continue;
            } else if (i == 29) {
                this.is_noresp = xDMParser.xdmParParseBlankElement(i);
                continue;
            } else if (i == 14) {
                this.cred = new XDMParserCred();
                xdmParParseZeroBitTagCheck = this.cred.xdmParParseCred(xDMParser);
                this.cred = xDMParser.Cred;
                continue;
            } else if (i != 15) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szData = xDMParser.m_szParserElement;
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        return xdmParParseZeroBitTagCheck;
    }
}
