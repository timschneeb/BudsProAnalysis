package com.accessorydm.eng.core;

import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class XDMWbxmlEncoder implements XDMWbxml {
    private static ByteArrayOutputStream out;
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    ByteArrayOutputStream stringTableBuf = new ByteArrayOutputStream();

    public boolean xdmWbxEncEndDocument() {
        return true;
    }

    public void xdmWbxEncInit(ByteArrayOutputStream byteArrayOutputStream) {
        out = byteArrayOutputStream;
    }

    public boolean xdmWbxEncStartDocument(int i, int i2, String str, int i3) {
        if (!xdmWbxEncAppendByte(2) || !xdmWbxEncAppendMbUINT32(i)) {
            return false;
        }
        if ((i != 0 || xdmWbxEncAppendMbUINT32(0)) && xdmWbxEncAppendMbUINT32(i2) && xdmWbxEncAppendMbUINT32(i3) && xdmWbxEncAppendToBuffer(str)) {
            return true;
        }
        return false;
    }

    public boolean xdmWbxEncStartElement(int i, boolean z) {
        if (z) {
            i |= 64;
        }
        return xdmWbxEncAppendByte(i);
    }

    public boolean xdmWbxEncEndElement() {
        return xdmWbxEncAppendByte(1);
    }

    public boolean xdmWbxEncAddSwitchpage(int i) {
        if (xdmWbxEncAppendByte(0) && xdmWbxEncAppendByte(i)) {
            return true;
        }
        return false;
    }

    public boolean xdmWbxEncAddContent(String str) {
        if (!xdmWbxEncAppendByte(3) || !xdmWbxEncAppendToBuffer(str)) {
            return false;
        }
        out.write(0);
        return true;
    }

    public boolean xdmWbxEncAddOpaque(char[] cArr, int i) throws IOException {
        if (!(xdmWbxEncAppendByte(XDMWbxml.WBXML_OPAQUE) && xdmWbxEncAppendMbUINT32(i))) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            out.write(cArr[i2]);
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public ByteArrayOutputStream xdmWbxEncGetBuffer() {
        return out;
    }

    public static int xdmWbxEncGetBufferSize() {
        return out.size();
    }

    public boolean xdmWbxEncAppendToBuffer(String str) {
        try {
            out.write(str.getBytes(Charset.defaultCharset()));
            return true;
        } catch (IOException e) {
            Log.E(e.toString());
            return true;
        }
    }

    public boolean xdmWbxEncAppendByte(int i) {
        out.write(i);
        return true;
    }

    public boolean xdmWbxEncAppendMbUINT32(int i) {
        int i2;
        byte[] bArr = new byte[5];
        int i3 = i;
        int i4 = 0;
        while (true) {
            i2 = i4 + 1;
            bArr[i4] = (byte) (i3 & 127);
            i3 >>= 7;
            if (i3 == 0) {
                break;
            }
            i4 = i2;
        }
        while (i2 > 1) {
            i2--;
            out.write(bArr[i2] | MsgID.SET_AMBIENT_MODE);
        }
        out.write(bArr[0]);
        return true;
    }
}
