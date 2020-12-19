package com.accessorydm.eng.parser;

import com.accessorydm.agent.XDMHandleCmd;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserSyncheader extends XDMHandleCmd implements XDMWbxml {
    public XDMParserCred cred;
    public int is_noresp;
    public String m_szLocname;
    public String m_szRespUri;
    public String m_szSessionId;
    public String m_szSource;
    public String m_szTarget;
    public String m_szVerdtd;
    public String m_szVerproto;
    public XDMParserMeta meta;
    public int msgid;

    public int xdmParParseSyncheader(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(44);
        if (xdmParParseCheckElement != 0) {
            Log.E("not WBXML_ERR_OK");
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
                Log.E("xdmParParseCurrentElement error = " + e.toString());
            }
            if (i == 1) {
                xDMParser.xdmParParseReadElement();
                Log.H("xdmParParseSyncheader verproto : " + this.m_szVerproto);
                Log.H("xdmParParseSyncheader sessionid : " + this.m_szSessionId);
                Log.H("xdmParParseSyncheader msgid : " + this.msgid);
                Log.H("xdmParParseSyncheader is_noresp : " + this.is_noresp);
                xdmAgentHdlCmdSyncHdr(xDMParser.userdata, this);
                return 0;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                xDMParser.codePage = i;
                continue;
            } else if (i == 14) {
                this.cred = new XDMParserCred();
                xdmParParseZeroBitTagCheck = this.cred.xdmParParseCred(xDMParser);
                this.cred = xDMParser.Cred;
                continue;
            } else if (i == 29) {
                this.is_noresp = xDMParser.xdmParParseBlankElement(i);
                continue;
            } else if (i == 33) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szRespUri = xDMParser.m_szParserElement;
                continue;
            } else if (i == 37) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szSessionId = xDMParser.m_szParserElement;
                continue;
            } else if (i == 39) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseSource();
                this.m_szSource = xDMParser.m_szTarget;
                continue;
            } else if (i == 46) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseTarget();
                this.m_szTarget = xDMParser.m_szSource;
                continue;
            } else if (i == 26) {
                this.meta = new XDMParserMeta();
                xdmParParseZeroBitTagCheck = this.meta.xdmParParseMeta(xDMParser);
                this.meta = xDMParser.Meta;
                continue;
            } else if (i == 27) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.msgid = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i == 49) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szVerdtd = xDMParser.m_szParserElement;
                continue;
            } else if (i != 50) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szVerproto = xDMParser.m_szParserElement;
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        Log.E("not  WBXML_ERR_OK");
        return xdmParParseZeroBitTagCheck;
    }
}
