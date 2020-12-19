package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserSync implements XDMWbxml {
    public int cmdid;
    public XDMParserCred cred;
    public boolean is_noresp;
    public boolean is_noresults;
    public String m_szSource;
    public String m_szTarget;
    public XDMParserMeta meta;
    public int numofchanges;

    public int xdmParParseSync(XDMParser xDMParser) {
        Log.I("xdmParParseSync");
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(42);
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
        int i2 = xdmParParseZeroBitTagCheck;
        boolean z = true;
        do {
            try {
                i = xDMParser.xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i == 1) {
                xDMParser.xdmParParseReadElement();
                return 0;
            }
            switch (i) {
                case 0:
                    xDMParser.xdmParParseReadElement();
                    i = xDMParser.xdmParParseReadElement();
                    xDMParser.codePage = i;
                    continue;
                case 5:
                    Log.I("xdmParParseSync : WBXML_TAG_ADD");
                    if (z) {
                        z = false;
                    }
                    i2 = new XDMParserAdd().xdmParParseAdd(xDMParser);
                    continue;
                case 8:
                    if (z) {
                        z = false;
                    }
                    i2 = new XDMParserAtomic().xdmParParseAtomic(xDMParser);
                    continue;
                case 11:
                    i2 = xDMParser.xdmParParseElement(i);
                    this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                    continue;
                case 13:
                    if (z) {
                        z = false;
                    }
                    i2 = new XDMParserCopy().xdmParParseCopy(xDMParser);
                    continue;
                case 14:
                    this.cred = new XDMParserCred();
                    i2 = this.cred.xdmParParseCred(xDMParser);
                    this.cred = xDMParser.Cred;
                    continue;
                case 16:
                    if (z) {
                        z = false;
                    }
                    i2 = new XDMParserDelete().xdmParParseDelete(xDMParser);
                    continue;
                case 26:
                    this.meta = new XDMParserMeta();
                    i2 = this.meta.xdmParParseMeta(xDMParser);
                    this.meta = xDMParser.Meta;
                    continue;
                case 29:
                    if (xDMParser.xdmParParseBlankElement(i) == 1) {
                        this.is_noresp = true;
                        continue;
                    } else {
                        this.is_noresp = false;
                        continue;
                    }
                case 30:
                    if (xDMParser.xdmParParseBlankElement(i) == 1) {
                        this.is_noresults = true;
                        continue;
                    } else {
                        this.is_noresults = false;
                        continue;
                    }
                case 32:
                    if (z) {
                        z = false;
                    }
                    i2 = new XDMParserReplace().xdmParParseReplace(xDMParser);
                    continue;
                case 36:
                    if (z) {
                        z = false;
                    }
                    i2 = new XDMParserSequence().xdmParParseSequence(xDMParser);
                    continue;
                case 39:
                    i2 = xDMParser.xdmParParseElement(i);
                    this.m_szSource = xDMParser.m_szSource;
                    continue;
                case 46:
                    i2 = xDMParser.xdmParParseElement(i);
                    this.m_szTarget = xDMParser.m_szTarget;
                    continue;
                case 51:
                    i2 = xDMParser.xdmParParseElement(i);
                    this.numofchanges = Integer.parseInt(xDMParser.m_szParserElement);
                    continue;
                default:
                    i2 = 2;
                    continue;
            }
        } while (i2 == 0);
        return i2;
    }
}
