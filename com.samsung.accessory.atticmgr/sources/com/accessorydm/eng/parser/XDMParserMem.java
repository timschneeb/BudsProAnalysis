package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserMem implements XDMWbxml {
    public int free;
    public int freeid;
    public String m_szShared;

    public int xdmParParseMem(XDMParser xDMParser) {
        int i;
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(13);
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
        int xdmParParseSkipLiteralElement = xDMParser.xdmParParseSkipLiteralElement();
        if (xdmParParseSkipLiteralElement != 0) {
            return xdmParParseSkipLiteralElement;
        }
        int i2 = -1;
        do {
            try {
                i2 = xDMParser.xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i2 == 1) {
                xDMParser.xdmParParseReadElement();
                return 0;
            } else if (i2 == 8) {
                i = xDMParser.xdmParParseElement(i2);
                this.freeid = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i2 == 9) {
                i = xDMParser.xdmParParseElement(i2);
                this.free = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i2 != 17) {
                i = 2;
                continue;
            } else {
                i = xDMParser.xdmParParseElement(i2);
                this.m_szShared = xDMParser.m_szParserElement;
                continue;
            }
        } while (i == 0);
        return i;
    }
}
