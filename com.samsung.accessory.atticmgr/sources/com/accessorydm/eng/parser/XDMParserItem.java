package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserItem implements XDMWbxml {
    public XDMParserPcdata data;
    public String m_szSource;
    public String m_szTarget;
    public XDMParserMeta meta;
    public int moredata;

    public int xdmParParseItem(XDMParser xDMParser, XDMParserItem xDMParserItem) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(20);
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
                Log.E(e.toString());
            }
            if (i == 1) {
                xDMParser.xdmParParseReadElement();
                Log.H("xdmParParseItem res  = " + xdmParParseZeroBitTagCheck);
                return xdmParParseZeroBitTagCheck;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                xDMParser.codePage = i;
                continue;
            } else if (i == 15) {
                this.data = new XDMParserPcdata();
                xdmParParseZeroBitTagCheck = this.data.xdmParParsePcdata(xDMParser, i);
                continue;
            } else if (i == 26) {
                this.meta = new XDMParserMeta();
                xdmParParseZeroBitTagCheck = this.meta.xdmParParseMeta(xDMParser);
                this.meta = xDMParser.Meta;
                continue;
            } else if (i == 39) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseSource();
                this.m_szSource = xDMParser.m_szSource;
                continue;
            } else if (i == 46) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseTarget();
                this.m_szTarget = xDMParser.m_szTarget;
                continue;
            } else if (i != 52) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                this.moredata = xDMParser.xdmParParseBlankElement(i);
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        return xdmParParseZeroBitTagCheck;
    }
}
