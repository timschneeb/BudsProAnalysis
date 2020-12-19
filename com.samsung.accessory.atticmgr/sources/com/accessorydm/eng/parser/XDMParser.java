package com.accessorydm.eng.parser;

import android.text.TextUtils;
import com.accessorydm.eng.core.XDMList;
import com.accessorydm.eng.core.XDMWbxmlDecoder;
import com.accessorydm.eng.core.XDMWorkspace;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class XDMParser extends XDMWbxmlDecoder {
    public XDMParserMeta Chal = null;
    public XDMParserCred Cred = null;
    public XDMParserMeta Meta = null;
    public XDMParserItem _pItem = null;
    public XDMParserMapItem _pMapitem = null;
    public int charset;
    public int codePage;
    public ByteArrayInputStream in;
    public String m_szParserElement = null;
    public String m_szSource = null;
    public String m_szStringtable;
    public String m_szTarget = null;
    public int puid;
    public int stsize;
    public Object userdata;
    public int version;

    public int xdmParDevinfo() {
        return 0;
    }

    public XDMParser(byte[] bArr) {
        this.wbxbuff = null;
        this.wbxbuff = bArr;
    }

    public XDMParser() {
        this.wbxbuff = null;
    }

    public void xdmParParseInit(XDMParser xDMParser, Object obj) {
        xDMParser.codePage = 0;
        xDMParser.userdata = obj;
    }

    public int xdmParParse() {
        Log.I("xdmParParse");
        if (this.wbxbuff == null) {
            return 0;
        }
        this.wbxindex = 0;
        xdmWbxDecInit(this.wbxbuff, this.wbxindex);
        xdmWbxDecParseStartdoc(this);
        int i = -1;
        try {
            i = xdmParParseCurrentElement();
        } catch (IOException e) {
            Log.E(e.toString());
        }
        if (i != 45) {
            Log.E("not WBXML_TAG_SYNCML");
            return 2;
        }
        try {
            return xdmParParseSyncml();
        } catch (Exception e2) {
            Log.E(e2.toString());
            return 1;
        }
    }

    public int xdmParParseSyncml() {
        int xdmParParseCheckElement = xdmParParseCheckElement(45);
        if (xdmParParseCheckElement != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xdmParParseZeroBitTagCheck();
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
                i = xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i == 1) {
                return 0;
            }
            if (i == 0) {
                xdmParParseReadElement();
                i = xdmParParseReadElement();
                continue;
            } else if (i == 43) {
                xdmParParseZeroBitTagCheck = xdmParParseSyncbody();
                continue;
            } else if (i != 44) {
                xdmParParseZeroBitTagCheck = 2;
                continue;
            } else {
                xdmParParseZeroBitTagCheck = new XDMParserSyncheader().xdmParParseSyncheader(this);
                continue;
            }
        } while (xdmParParseZeroBitTagCheck == 0);
        return xdmParParseZeroBitTagCheck;
    }

    public int xdmParParseReadElement() {
        try {
            return xdmWbxDecReadBufferByte() & 63 & 127;
        } catch (IOException e) {
            Log.E(e.toString());
            return -1;
        }
    }

    public int xdmParParseSyncbody() {
        int xdmParParseCheckElement = xdmParParseCheckElement(43);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseZeroBitTagCheck;
        }
        int i = -1;
        int i2 = xdmParParseZeroBitTagCheck;
        int i3 = 0;
        do {
            try {
                i = xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i == 1) {
                xdmParParseReadElement();
                Log.I("xdmParParseSyncbody end tmp = " + i3);
                xdmHdlCmdSyncEnd(this.userdata, i3);
                return 0;
            } else if (i == 0) {
                xdmParParseReadElement();
                i = xdmParParseReadElement();
                this.codePage = i;
                continue;
            } else if (i == 8) {
                i2 = new XDMParserAtomic().xdmParParseAtomic(this);
                continue;
            } else if (i == 13) {
                i2 = new XDMParserCopy().xdmParParseCopy(this);
                continue;
            } else if (i == 24) {
                i2 = new XDMParserMap().xdmParParseMap(this);
                continue;
            } else if (i == 34) {
                i2 = new XDMParserResults().xdmParParseResults(this);
                continue;
            } else if (i == 36) {
                i2 = new XDMParserSequence().xdmParParseSequence(this);
                continue;
            } else if (i == 5) {
                i2 = new XDMParserAdd().xdmParParseAdd(this);
                continue;
            } else if (i == 6) {
                i2 = new XDMParserAlert().xdmParParseAlert(this);
                continue;
            } else if (i == 31) {
                i2 = new XDMParserPut().xdmParParsePut(this);
                continue;
            } else if (i == 32) {
                i2 = new XDMParserReplace().xdmParParseReplace(this);
                continue;
            } else if (i == 41) {
                i2 = new XDMParserStatus().xdmParParseStatus(this);
                continue;
            } else if (i != 42) {
                switch (i) {
                    case 16:
                        i2 = new XDMParserDelete().xdmParParseDelete(this);
                        continue;
                    case 17:
                        i2 = new XDMParserExec().xdmParParseExec(this);
                        continue;
                    case 18:
                        i3 = xdmParParseBlankElement(i);
                        continue;
                    case 19:
                        i2 = new XDMParserGet().xdmParParseGet(this);
                        continue;
                    default:
                        i2 = 2;
                        continue;
                }
            } else {
                i2 = new XDMParserSync().xdmParParseSync(this);
                continue;
            }
        } while (i2 == 0);
        return i2;
    }

    public void xdmHdlCmdSyncEnd(Object obj, int i) {
        XDMWorkspace xDMWorkspace = (XDMWorkspace) obj;
        if (i > 0) {
            xDMWorkspace.isFinal = true;
            return;
        }
        Log.I("didn't catch FINAL");
        xDMWorkspace.isFinal = false;
    }

    public XDMList xdmParParseItemlist(XDMList xDMList) {
        int i = 0;
        while (true) {
            try {
                i = xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i != 20) {
                return xDMList;
            }
            this._pItem = new XDMParserItem();
            XDMParserItem xDMParserItem = this._pItem;
            if (xDMParserItem.xdmParParseItem(this, xDMParserItem) != 0) {
                return null;
            }
            xDMList = XDMList.xdmListAppend(xDMList, null, this._pItem);
        }
    }

    public XDMList xdmParParseMapitemlist(XDMList xDMList) {
        int i = -1;
        while (true) {
            try {
                i = xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i != 25) {
                return xDMList;
            }
            this._pMapitem = new XDMParserMapItem();
            XDMParserMapItem xDMParserMapItem = this._pMapitem;
            if (xDMParserMapItem.xdmParParseMapitem(this, xDMParserMapItem) != 0) {
                return null;
            }
            xDMList = XDMList.xdmListAppend(xDMList, null, this._pMapitem);
        }
    }

    public int xdmParParseTarget() {
        int xdmParParseCheckElement = xdmParParseCheckElement(46);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseZeroBitTagCheck;
        }
        int xdmParParseElement = xdmParParseElement(23);
        if (xdmParParseElement != 0) {
            return xdmParParseElement;
        }
        String str = this.m_szParserElement;
        try {
            if (xdmParParseCurrentElement() == 22) {
                xdmParParseSkipElement();
            }
        } catch (IOException e) {
            Log.E(e.toString());
        }
        int xdmParParseCheckElement2 = xdmParParseCheckElement(1);
        if (xdmParParseCheckElement2 != 0) {
            return xdmParParseCheckElement2;
        }
        if (!TextUtils.isEmpty(str)) {
            this.m_szTarget = String.valueOf(str.toCharArray());
        } else {
            this.m_szTarget = null;
        }
        return xdmParParseCheckElement2;
    }

    public int xdmParParseSource() {
        int xdmParParseCheckElement = xdmParParseCheckElement(39);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseZeroBitTagCheck;
        }
        int xdmParParseElement = xdmParParseElement(23);
        if (xdmParParseElement != 0) {
            return xdmParParseElement;
        }
        String str = this.m_szParserElement;
        try {
            if (xdmParParseCurrentElement() == 22) {
                xdmParParseSkipElement();
            }
        } catch (IOException e) {
            Log.E(e.toString());
        }
        int xdmParParseCheckElement2 = xdmParParseCheckElement(1);
        if (xdmParParseCheckElement2 != 0) {
            return xdmParParseCheckElement2;
        }
        if (!TextUtils.isEmpty(str)) {
            this.m_szSource = String.valueOf(str.toCharArray());
        } else {
            this.m_szSource = null;
        }
        return xdmParParseCheckElement2;
    }

    public String xdmParParseContent() {
        try {
            int xdmWbxDecReadBufferByte = xdmWbxDecReadBufferByte();
            if (xdmWbxDecReadBufferByte == 3) {
                return xdmWbxDecParseStr_i();
            }
            if (xdmWbxDecReadBufferByte == 131) {
                return xdmWbxDecParseStr_t();
            }
            if (xdmWbxDecReadBufferByte == 195) {
                return xdmWbxDecParseExtension(xdmWbxDecReadBufferByte);
            }
            this.wbxindex--;
            return xdmParParseSkipElement() != 0 ? null : null;
        } catch (IOException e) {
            Log.E(e.toString());
            return null;
        }
    }

    public XDMList xdmParParseElelist(int i, XDMList xDMList) {
        int i2 = -1;
        while (true) {
            try {
                i2 = xdmParParseCurrentElement();
            } catch (IOException e) {
                Log.E(e.toString());
            }
            if (i2 != i) {
                return xDMList;
            }
            if (xdmParParseElement(i) != 0) {
                return null;
            }
            xDMList = XDMList.xdmListAppend(null, null, this.m_szParserElement);
        }
    }

    public int xdmParParseChal() {
        XDMParserMeta xDMParserMeta = new XDMParserMeta();
        int xdmParParseCheckElement = xdmParParseCheckElement(9);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseZeroBitTagCheck;
        }
        int xdmParParseMeta = xDMParserMeta.xdmParParseMeta(this);
        if (xdmParParseMeta != 0) {
            return xdmParParseMeta;
        }
        this.Chal = this.Meta;
        int xdmParParseCheckElement2 = xdmParParseCheckElement(1);
        if (xdmParParseCheckElement2 != 0) {
        }
        return xdmParParseCheckElement2;
    }

    public int xdmParParseElement(int i) {
        String str = new String("");
        this.m_szParserElement = "";
        int xdmParParseCheckElement = xdmParParseCheckElement(i);
        if (xdmParParseCheckElement != 0) {
            return xdmParParseCheckElement;
        }
        int xdmParParseZeroBitTagCheck = xdmParParseZeroBitTagCheck();
        if (xdmParParseZeroBitTagCheck == 8) {
            return 0;
        }
        if (xdmParParseZeroBitTagCheck != 0) {
            Log.E("not WBXML_ERR_OK");
            return xdmParParseZeroBitTagCheck;
        }
        int xdmParParseSkipLiteralElement = xdmParParseSkipLiteralElement();
        if (xdmParParseSkipLiteralElement != 0) {
            return xdmParParseSkipLiteralElement;
        }
        while (true) {
            this.m_szParserElement = str.concat(xdmParParseContent());
            try {
                if (xdmWbxDecReadBufferByte() != 131) {
                    this.wbxindex--;
                    break;
                }
                this.m_szParserElement = str.concat(xdmWbxDecParseStr_t());
                if (xdmWbxDecReadBufferByte() == 1) {
                    this.wbxindex--;
                    break;
                }
                this.wbxindex--;
            } catch (IOException e) {
                Log.E(e.toString());
            }
        }
        int xdmParParseCheckElement2 = xdmParParseCheckElement(1);
        if (xdmParParseCheckElement2 != 0) {
        }
        return xdmParParseCheckElement2;
    }

    public int xdmParParseBlankElement(int i) {
        int xdmParParseCheckElement;
        try {
            boolean z = (xdmParParseCurrentElement() & 64) != 0;
            int xdmParParseCheckElement2 = xdmParParseCheckElement(i);
            if (xdmParParseCheckElement2 != 0) {
                return xdmParParseCheckElement2;
            }
            if (!z || (xdmParParseCheckElement = xdmParParseCheckElement(1)) == 0) {
                return 1;
            }
            return xdmParParseCheckElement;
        } catch (IOException e) {
            Log.E(e.toString());
        }
    }

    public int xdmParParseCurrentElement() throws IOException {
        int i = this.wbxbuff[this.wbxindex] & 255;
        if (i >= 0) {
            return i & 63 & 127;
        }
        throw new IOException("Unexpected EOF");
    }

    public int xdmParParseCheckElement(int i) {
        if (i == xdmParParseReadElement()) {
            return 0;
        }
        Log.E("xdmParParseReadElement is WBXML_ERR_UNKNOWN_ELEMENT");
        return 2;
    }

    public int xdmParParseZeroBitTagCheck() {
        int i;
        byte[] bArr = this.wbxbuff;
        int i2 = this.wbxindex - 1;
        this.wbxindex = i2;
        int i3 = bArr[i2] & 255;
        if (i3 < 0) {
            return 1;
        }
        int i4 = i3 & 63 & 127;
        if (i4 < 5 || i4 > 60 || (i3 & 64) != 0) {
            i = 0;
        } else {
            Log.E("WBXML_ERR_ZEROBIT_TAG");
            i = 8;
        }
        this.wbxindex++;
        return i;
    }

    public int xdmParParseSkipElement() {
        int i = 0;
        while (true) {
            try {
                int xdmParParseCurrentElement = xdmParParseCurrentElement();
                if (xdmParParseCurrentElement == 0) {
                    xdmWbxDecReadBufferByte();
                    xdmWbxDecReadBufferByte();
                } else if (xdmParParseCurrentElement == 1) {
                    xdmWbxDecReadBufferByte();
                    i--;
                    if (i == 0) {
                        break;
                    }
                } else {
                    if (!(xdmParParseCurrentElement == 3 || xdmParParseCurrentElement == 131)) {
                        if (xdmParParseCurrentElement != 195) {
                            xdmWbxDecReadBufferByte();
                            i++;
                        }
                    }
                    xdmParParseContent();
                }
            } catch (IOException e) {
                Log.E(e.toString());
            }
        }
        while (xdmParParseCurrentElement() == 0) {
            xdmWbxDecReadBufferByte();
            xdmWbxDecReadBufferByte();
        }
        return 0;
    }

    public int xdmParParseSkipLiteralElement() {
        try {
            if (xdmParParseCurrentElement() != 4) {
                return 0;
            }
            do {
            } while (xdmWbxDecReadBufferByte() != 1);
            return 0;
        } catch (IOException e) {
            Log.E(e.toString());
            return 0;
        }
    }
}
