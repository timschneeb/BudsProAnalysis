package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserAnchor implements XDMWbxml {
    public String m_szLast;
    public String m_szNext;

    public int xdmParParseAnchor(XDMParser xDMParser) {
        Log.I("xdmParParseAnchor");
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(5);
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
        int xdmParParseSkipLiteralElement = xDMParser.xdmParParseSkipLiteralElement();
        if (xdmParParseSkipLiteralElement != 0) {
            return xdmParParseSkipLiteralElement;
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
                return xdmParParseSkipLiteralElement;
            } else if (i == 10) {
                xdmParParseSkipLiteralElement = xDMParser.xdmParParseElement(i);
                this.m_szLast = xDMParser.m_szParserElement;
                continue;
            } else if (i != 15) {
                xdmParParseSkipLiteralElement = 2;
                continue;
            } else {
                xdmParParseSkipLiteralElement = xDMParser.xdmParParseElement(i);
                this.m_szNext = xDMParser.m_szParserElement;
                continue;
            }
        } while (xdmParParseSkipLiteralElement == 0);
        return xdmParParseSkipLiteralElement;
    }

    public XDMParserAnchor xdmParGetMetinfAnchor() {
        XDMParserAnchor xDMParserAnchor = new XDMParserAnchor();
        xDMParserAnchor.m_szLast = this.m_szLast;
        xDMParserAnchor.m_szNext = this.m_szNext;
        return xDMParserAnchor;
    }
}
