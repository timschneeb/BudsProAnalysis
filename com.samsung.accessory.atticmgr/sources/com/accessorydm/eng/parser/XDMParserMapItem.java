package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserMapItem implements XDMWbxml {
    public String m_szSource;
    public String m_szTarget;

    public int xdmParParseMapitem(XDMParser xDMParser, XDMParserMapItem xDMParserMapItem) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(25);
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
                return xdmParParseZeroBitTagCheck;
            } else if (i == 39) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseSource();
                this.m_szSource = xDMParser.m_szSource;
                continue;
            } else if (i != 46) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseTarget();
                this.m_szTarget = xDMParser.m_szTarget;
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        return xdmParParseZeroBitTagCheck;
    }
}
