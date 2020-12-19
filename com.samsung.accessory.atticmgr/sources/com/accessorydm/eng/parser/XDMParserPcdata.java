package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMWbxml;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class XDMParserPcdata implements XDMWbxml, XDMInterface {
    public XDMParserAnchor anchor;
    public char[] data;
    public int size;
    public boolean skipstatus = false;
    public int type;

    public int xdmParParsePcdata(XDMParser xDMParser, int i) {
        Log.I("xdmParParsePcdata");
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(i);
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
        try {
            int xdmWbxDecReadBufferByte = xDMParser.xdmWbxDecReadBufferByte();
            if (xdmWbxDecReadBufferByte == 3) {
                xdmString2pcdata(xDMParser.xdmWbxDecParseStr_i());
            } else if (xdmWbxDecReadBufferByte == 131) {
                xdmString2pcdata(xDMParser.xdmWbxDecParseStr_t());
            } else if (xdmWbxDecReadBufferByte == 195) {
                String xdmWbxDecParseExtension = xDMParser.xdmWbxDecParseExtension(xdmWbxDecReadBufferByte);
                this.type = 1;
                this.size = xdmWbxDecParseExtension.length();
                xdmString2pcdata(xdmWbxDecParseExtension);
            } else if (xdmWbxDecReadBufferByte == 0) {
                int xdmParParseReadElement = xDMParser.xdmParParseReadElement();
                if (xdmParParseZeroBitTagCheck != 0) {
                    return xdmParParseZeroBitTagCheck;
                }
                xDMParser.codePage = xdmParParseReadElement;
                int xdmParParseCurrentElement = xDMParser.xdmParParseCurrentElement();
                do {
                    if (xDMParser.codePage == 1 && xdmParParseCurrentElement == 5) {
                        this.anchor = new XDMParserAnchor();
                        int xdmParParseAnchor = this.anchor.xdmParParseAnchor(xDMParser);
                        if (xdmParParseAnchor != 0) {
                            return xdmParParseAnchor;
                        }
                    } else if (xDMParser.codePage == 1 && xdmParParseCurrentElement == 13) {
                        int xdmParParseMem = new XDMParserMem().xdmParParseMem(xDMParser);
                        if (xdmParParseMem != 0) {
                            return xdmParParseMem;
                        }
                    } else if (xdmParParseCurrentElement == 0) {
                        xDMParser.xdmParParseReadElement();
                        xDMParser.xdmParParseReadElement();
                    }
                    xdmParParseCurrentElement = xDMParser.xdmParParseCurrentElement();
                } while (xdmParParseCurrentElement != 1);
            } else {
                xDMParser.wbxindex = xDMParser.wbxindex - 1;
                int xdmParParseSkipElement = xDMParser.xdmParParseSkipElement();
                if (xdmParParseSkipElement != 0) {
                    return xdmParParseSkipElement;
                }
                this.type = 2;
                this.size = 0;
                this.data = null;
            }
            int xdmParParseCheckElement2 = xDMParser.xdmParParseCheckElement(1);
            if (xdmParParseCheckElement2 != 0) {
                return xdmParParseCheckElement2;
            }
            return 0;
        } catch (IOException e) {
            Log.E(e.toString());
        }
    }

    public void xdmString2pcdata(String str) throws UnsupportedEncodingException {
        this.type = 0;
        this.size = str.length();
        char[] charArray = str.toCharArray();
        this.data = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            this.data[i] = charArray[i];
        }
    }

    public XDMParserPcdata xdmParGetPcdata() {
        XDMParserPcdata xDMParserPcdata = new XDMParserPcdata();
        xDMParserPcdata.type = this.type;
        xDMParserPcdata.data = this.data;
        xDMParserPcdata.size = this.size;
        xDMParserPcdata.anchor = this.anchor;
        return xDMParserPcdata;
    }

    public int xdmParGetPcdataType() {
        return this.type;
    }

    public char[] xdmParGetPcdataData() {
        return this.data;
    }

    public int xdmParGetPcdataSize() {
        return this.size;
    }

    public XDMParserAnchor xdmParGetPcdataAnchor() {
        return this.anchor;
    }
}
