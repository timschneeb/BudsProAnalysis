package com.accessorydm.eng.parser;

import com.accessorydm.agent.XDMHandleCmd;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserStatus extends XDMHandleCmd implements XDMWbxml {
    public XDMParserMeta chal = null;
    public int cmdid;
    public XDMParserCred cred = null;
    public XDMList itemlist = null;
    public String m_szCmd;
    public String m_szCmdRef;
    public String m_szData;
    public String m_szMsgRef;
    public XDMList sourceref;
    public XDMList targetref;

    public int xdmParParseStatus(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(41);
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
        while (true) {
            try {
                i = xDMParser.xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i == 1) {
                xDMParser.xdmParParseReadElement();
                Log.I("xdmParParseStatus WBXML_END");
                Log.I("WBXML_TAG_CMDID cmdid =" + this.cmdid);
                Log.I("WBXML_TAG_MSGREF msgref =" + this.m_szMsgRef);
                Log.I("WBXML_TAG_CMDREF cmdref =" + this.m_szCmdRef);
                Log.I("WBXML_TAG_Cmd cmd =" + this.m_szCmd);
                Log.I("WBXML_TAG_DATA data =" + this.m_szData);
                xdmAgentHdlCmdStatus(xDMParser.userdata, this);
                return 0;
            } else if (i == 41) {
                xDMParser.xdmParParseReadElement();
            } else {
                if (i == 0) {
                    xDMParser.xdmParParseReadElement();
                    i = xDMParser.xdmParParseReadElement();
                    xDMParser.codePage = i;
                } else if (i == 20) {
                    this.itemlist = xDMParser.xdmParParseItemlist(this.itemlist);
                } else if (i == 28) {
                    xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                    this.m_szMsgRef = xDMParser.m_szParserElement;
                } else if (i == 40) {
                    this.sourceref = new XDMList();
                    this.sourceref = xDMParser.xdmParParseElelist(i, this.sourceref);
                } else if (i == 47) {
                    this.targetref = new XDMList();
                    this.targetref = xDMParser.xdmParParseElelist(i, this.targetref);
                } else if (i == 14) {
                    this.cred = new XDMParserCred();
                    xdmParParseZeroBitTagCheck = this.cred.xdmParParseCred(xDMParser);
                    this.cred = xDMParser.Cred;
                } else if (i != 15) {
                    switch (i) {
                        case 9:
                            this.chal = new XDMParserMeta();
                            xdmParParseZeroBitTagCheck = xDMParser.xdmParParseChal();
                            this.chal = xDMParser.Chal;
                            break;
                        case 10:
                            xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                            this.m_szCmd = xDMParser.m_szParserElement;
                            break;
                        case 11:
                            xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                            this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                            break;
                        case 12:
                            xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                            this.m_szCmdRef = xDMParser.m_szParserElement;
                            break;
                        default:
                            Log.E("WBXML_ERR_UNKNOWN_ELEMENT !!!!!!!");
                            xdmParParseZeroBitTagCheck = 2;
                            break;
                    }
                } else {
                    xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                    this.m_szData = xDMParser.m_szParserElement;
                }
                if (xdmParParseZeroBitTagCheck != 0) {
                    return xdmParParseZeroBitTagCheck;
                }
            }
        }
    }
}
