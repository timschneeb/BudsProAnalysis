package com.accessorydm.eng.parser;

import com.accessorydm.agent.XDMHandleCmd;
import com.accessorydm.eng.core.XDMLinkedList;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserAtomic extends XDMHandleCmd implements XDMWbxml {
    public int cmdid;
    public int is_noresp;
    public XDMLinkedList itemlist;
    public XDMParserMeta meta;

    public int xdmParParseAtomic(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(8);
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
                if (z) {
                    xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                }
                xdmAgentHdlCmdAtomicEnd(xDMParser.userdata);
                return 0;
            }
            switch (i) {
                case 0:
                    xDMParser.xdmParParseReadElement();
                    i = xDMParser.xdmParParseReadElement();
                    xDMParser.codePage = i;
                    continue;
                case 5:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserAdd().xdmParParseAdd(xDMParser);
                    continue;
                case 8:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = xdmParParseAtomic(xDMParser);
                    continue;
                case 11:
                    i2 = xDMParser.xdmParParseElement(i);
                    this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                    continue;
                case 13:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserCopy().xdmParParseCopy(xDMParser);
                    continue;
                case 16:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserDelete().xdmParParseDelete(xDMParser);
                    continue;
                case 17:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserExec().xdmParParseExec(xDMParser);
                    continue;
                case 24:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserMap().xdmParParseMap(xDMParser);
                    continue;
                case 26:
                    this.meta = new XDMParserMeta();
                    i2 = this.meta.xdmParParseMeta(xDMParser);
                    this.meta = xDMParser.Meta;
                    continue;
                case 29:
                    this.is_noresp = xDMParser.xdmParParseBlankElement(i);
                    continue;
                case 32:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserReplace().xdmParParseReplace(xDMParser);
                    continue;
                case 36:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserSequence().xdmParParseSequence(xDMParser);
                    continue;
                case 42:
                    if (z) {
                        xdmAgentHdlCmdAtomicStart(xDMParser.userdata, this);
                        z = false;
                    }
                    i2 = new XDMParserSync().xdmParParseSync(xDMParser);
                    continue;
                default:
                    i2 = 2;
                    continue;
            }
        } while (i2 == 0);
        return i2;
    }
}
