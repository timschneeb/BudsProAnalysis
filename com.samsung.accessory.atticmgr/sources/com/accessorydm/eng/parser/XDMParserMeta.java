package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserMeta implements XDMWbxml {
    public XDMParserAnchor anchor;
    public String m_szEmi;
    public String m_szFormat;
    public String m_szMark;
    public String m_szNextNonce;
    public String m_szType;
    public String m_szVersion;
    public int maxmsgsize;
    public int maxobjsize;
    public XDMParserMem mem;
    public int size;

    public int xdmParParseMeta(XDMParser xDMParser) {
        int i;
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(26);
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
        try {
            i = xDMParser.xdmParParseCurrentElement();
        } catch (IOException e) {
            Log.E(e.toString());
            i = -1;
        }
        if (i == 1) {
            xDMParser.xdmParParseReadElement();
            return xdmParParseZeroBitTagCheck;
        }
        int xdmParParseCheckElement2 = xDMParser.xdmParParseCheckElement(0);
        if (xdmParParseCheckElement2 != 0) {
            return xdmParParseCheckElement2;
        }
        int xdmParParseCheckElement3 = xDMParser.xdmParParseCheckElement(1);
        if (xdmParParseCheckElement3 != 0) {
            return xdmParParseCheckElement3;
        }
        xDMParser.codePage = 1;
        do {
            try {
                i = xDMParser.xdmParParseCurrentElement();
            } catch (IOException e2) {
                Log.E(e2.toString());
            }
            if (i == 1) {
                xDMParser.xdmParParseReadElement();
                xDMParser.Meta = this;
                return xdmParParseCheckElement3;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                xDMParser.codePage = i;
                continue;
            } else if (i == 16) {
                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                this.m_szNextNonce = xDMParser.m_szParserElement;
                continue;
            } else if (i == 5) {
                this.anchor = new XDMParserAnchor();
                this.anchor.xdmParParseAnchor(xDMParser);
                continue;
            } else if (i == 6) {
                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                this.m_szEmi = xDMParser.m_szParserElement;
                continue;
            } else if (i != 7) {
                switch (i) {
                    case 11:
                        xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                        this.m_szMark = xDMParser.m_szParserElement;
                        continue;
                    case 12:
                        xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                        this.maxmsgsize = Integer.parseInt(xDMParser.m_szParserElement);
                        continue;
                    case 13:
                        this.mem = new XDMParserMem();
                        this.mem.xdmParParseMem(xDMParser);
                        continue;
                    default:
                        switch (i) {
                            case 18:
                                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                                this.size = Integer.valueOf(xDMParser.m_szParserElement).intValue();
                                continue;
                            case 19:
                                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                                this.m_szType = xDMParser.m_szParserElement;
                                continue;
                            case 20:
                                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                                this.m_szVersion = xDMParser.m_szParserElement;
                                continue;
                            case 21:
                                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                                this.maxobjsize = Integer.parseInt(xDMParser.m_szParserElement);
                                continue;
                            default:
                                xdmParParseCheckElement3 = 2;
                                continue;
                                continue;
                        }
                }
            } else {
                xdmParParseCheckElement3 = xDMParser.xdmParParseElement(i);
                this.m_szFormat = xDMParser.m_szParserElement;
                continue;
            }
        } while (xdmParParseCheckElement3 == 0);
        return xdmParParseCheckElement3;
    }
}
