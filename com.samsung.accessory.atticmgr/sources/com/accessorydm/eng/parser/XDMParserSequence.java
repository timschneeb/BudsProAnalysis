package com.accessorydm.eng.parser;

import com.accessorydm.agent.XDMHandleCmd;
import com.accessorydm.eng.core.XDMLinkedList;
import com.accessorydm.eng.core.XDMWbxml;
import com.samsung.android.fotaprovider.log.Log;
import java.io.IOException;

public class XDMParserSequence extends XDMHandleCmd implements XDMWbxml {
    public XDMParserAdd add;
    public XDMParserAlert alert;
    public XDMParserAtomic atomic;
    public int cmdid;
    public XDMParserCopy copy;
    public XDMParserDelete delete;
    public XDMParserExec exec;
    public XDMParserGet get;
    public int is_noresp;
    public XDMLinkedList itemlist;
    public XDMParserMap map;
    public XDMParserMeta meta;
    public XDMParserReplace replace;
    public XDMParserSync sync;

    public int xdmParParseSequence(XDMParser xDMParser) {
        int xdmParParseCheckElement = xDMParser.xdmParParseCheckElement(36);
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
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                }
                xdmAgentHdlCmdSequenceEnd(xDMParser.userdata);
                return 0;
            } else if (i == 0) {
                xDMParser.xdmParParseReadElement();
                i = xDMParser.xdmParParseReadElement();
                xDMParser.codePage = i;
                continue;
            } else if (i == 8) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.atomic = new XDMParserAtomic();
                this.atomic.xdmParParseAtomic(xDMParser);
                continue;
            } else if (i == 11) {
                i2 = xDMParser.xdmParParseElement(i);
                this.cmdid = Integer.parseInt(xDMParser.m_szParserElement);
                continue;
            } else if (i == 13) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.copy = new XDMParserCopy();
                this.copy.xdmParParseCopy(xDMParser);
                continue;
            } else if (i == 19) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.get = new XDMParserGet();
                this.get.xdmParParseGet(xDMParser);
                continue;
            } else if (i == 24) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.map = new XDMParserMap();
                this.map.xdmParParseMap(xDMParser);
                continue;
            } else if (i == 26) {
                this.meta = new XDMParserMeta();
                i2 = this.meta.xdmParParseMeta(xDMParser);
                this.meta = xDMParser.Meta;
                continue;
            } else if (i == 29) {
                this.is_noresp = xDMParser.xdmParParseBlankElement(i);
                continue;
            } else if (i == 32) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.replace = new XDMParserReplace();
                this.replace.xdmParParseReplace(xDMParser);
                continue;
            } else if (i == 42) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.sync = new XDMParserSync();
                this.sync.xdmParParseSync(xDMParser);
                continue;
            } else if (i == 5) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.add = new XDMParserAdd();
                this.add.xdmParParseAdd(xDMParser);
                continue;
            } else if (i == 6) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.alert = new XDMParserAlert();
                this.alert.xdmParParseAlert(xDMParser);
                continue;
            } else if (i == 16) {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.delete = new XDMParserDelete();
                this.delete.xdmParParseDelete(xDMParser);
                continue;
            } else if (i != 17) {
                i2 = 2;
                continue;
            } else {
                if (z) {
                    xdmAgentHdlCmdSequenceStart(xDMParser.userdata, this);
                    z = false;
                }
                this.exec = new XDMParserExec();
                this.exec.xdmParParseExec(xDMParser);
                continue;
            }
        } while (i2 == 0);
        return i2;
    }
}
