package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserMap implements XDMWbxml {
    public int cmdid;
    public XDMParserCred cred;
    public XDMList itemlist;
    public String m_szSource;
    public String m_szTarget;
    public XDMParserMeta meta;

    public int xdmParParseMap(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(24);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xDMParser.xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
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
                return 0;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                if (xdmParParseZeroBitTagCheck != 0) {
                    return xdmParParseZeroBitTagCheck;
                }
                xDMParser.codePage = i;
                continue;
            } else if (i == 11) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i == 14) {
                this.cred = new XDMParserCred();
                xdmParParseZeroBitTagCheck = this.cred.xdmParParseCred(xDMParser);
                this.cred = xDMParser.Cred;
                continue;
            } else if (i == 39) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseTarget();
                this.m_szSource = xDMParser.m_szSource;
                continue;
            } else if (i == 46) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseTarget();
                this.m_szTarget = xDMParser.m_szTarget;
                continue;
            } else if (i == 25) {
                this.itemlist = xDMParser.xdmParParseMapitemlist(this.itemlist);
                continue;
            } else if (i != 26) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                this.meta = new XDMParserMeta();
                xdmParParseZeroBitTagCheck = this.meta.xdmParParseMeta(xDMParser);
                this.meta = xDMParser.Meta;
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        return xdmParParseZeroBitTagCheck;
    }
}
