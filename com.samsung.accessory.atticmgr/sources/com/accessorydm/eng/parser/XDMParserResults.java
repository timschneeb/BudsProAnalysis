package com.accessorydm.eng.parser;

import com.accessorydm.adapter.XDMDevinfAdapter;
import com.accessorydm.eng.core.XDMEncoder;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMWbxml;
import com.accessorydm.interfaces.XDMInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XDMParserResults implements XDMWbxml, XDMInterface {
    public int cmdid;
    public XDMList itemlist = null;
    public String m_szCmdRef;
    public String m_szMsgRef;
    public String m_szSourceRef;
    public String m_szTargetRef;
    public XDMParserMeta meta;

    public int xdmParParseResults(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(34);
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
                xDMParser.codePage = i;
                continue;
            } else if (i == 20) {
                this.itemlist = xDMParser.xdmParParseItemlist(this.itemlist);
                continue;
            } else if (i == 26) {
                this.meta = new XDMParserMeta();
                xdmParParseZeroBitTagCheck = this.meta.xdmParParseMeta(xDMParser);
                this.meta = xDMParser.Meta;
                continue;
            } else if (i == 28) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szMsgRef = xDMParser.m_szParserElement;
                continue;
            } else if (i == 40) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szSourceRef = xDMParser.m_szParserElement;
                continue;
            } else if (i == 47) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szTargetRef = xDMParser.m_szParserElement;
                continue;
            } else if (i == 11) {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i != 12) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                xdmParParseZeroBitTagCheck = xDMParser.xdmParParseElement(i);
                this.m_szCmdRef = xDMParser.m_szParserElement;
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        return xdmParParseZeroBitTagCheck;
    }

    public XDMParserResults xdmBuildResultsCommand(XDMDevinfAdapter xDMDevinfAdapter) {
        XDMParserItem xDMParserItem = new XDMParserItem();
        XDMEncoder xDMEncoder = new XDMEncoder();
        int[] iArr = new int[2];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XDMParserPcdata xDMParserPcdata = new XDMParserPcdata();
        byte[] xdmEncDevinf2Opaque = xDMEncoder.xdmEncDevinf2Opaque(byteArrayOutputStream, xDMDevinfAdapter, iArr);
        xDMParserPcdata.type = 1;
        xDMParserPcdata.size = iArr[0];
        xDMParserPcdata.data = new char[xdmEncDevinf2Opaque.length];
        for (int i = 0; i < xdmEncDevinf2Opaque.length; i++) {
            xDMParserPcdata.data[i] = (char) xdmEncDevinf2Opaque[i];
        }
        xDMParserItem.data = xDMParserPcdata;
        this.itemlist = new XDMList();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.E(e.toString());
        }
        return this;
    }
}
