package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserCred implements XDMWbxml {
    public String m_szData;
    public XDMParserMeta meta;

    public int xdmParParseCred(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(14);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
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
                xDMParser.Cred = this;
                return xdmParParseCheckElement;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                xDMParser.codePage = i;
                continue;
            } else if (i == 15) {
                xdmParParseCheckElement = xDMParser.xdmParParseElement(i);
                this.m_szData = xDMParser.m_szParserElement;
                continue;
            } else if (i != 26) {
                xdmParParseCheckElement = 2;
                continue;
            } else {
                this.meta = new XDMParserMeta();
                xdmParParseCheckElement = this.meta.xdmParParseMeta(xDMParser);
                this.meta = xDMParser.Meta;
                continue;
            }
        } while (xdmParParseCheckElement == 0);
        return xdmParParseCheckElement;
    }
}
