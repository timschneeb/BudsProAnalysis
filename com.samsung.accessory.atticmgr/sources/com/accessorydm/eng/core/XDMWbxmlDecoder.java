package com.accessorydm.eng.core;

import com.accessorydm.eng.parser.XDMParser;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class XDMWbxmlDecoder implements XDMWbxml {
    public String m_szStringT = null;
    protected byte[] wbxbuff = null;
    public int wbxindex = 0;

    public void xdmWbxDecInit(byte[] bArr, int i) {
        this.wbxbuff = bArr;
        this.wbxindex = i;
    }

    public void xdmWbxDecParseStartdoc(XDMParser xDMParser) {
        try {
            xDMParser.version = xdmWbxDecReadBufferByte();
            xDMParser.puid = xdmWbxDecReadBufferMbUINT32();
            if (xDMParser.puid == 0) {
                xdmWbxDecReadBufferMbUINT32();
            }
            xDMParser.charset = xdmWbxDecReadBufferMbUINT32();
            xDMParser.m_szStringtable = xdmWbxDecParseStringtable();
            this.m_szStringT = new String(xDMParser.m_szStringtable);
        } catch (IOException e) {
            Log.E(e.toString());
        }
    }

    public String xdmWbxDecParseStr_t() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (int xdmWbxDecReadBufferMbUINT32 = xdmWbxDecReadBufferMbUINT32(); this.m_szStringT.charAt(xdmWbxDecReadBufferMbUINT32) != 0; xdmWbxDecReadBufferMbUINT32++) {
                byteArrayOutputStream.write(this.m_szStringT.charAt(xdmWbxDecReadBufferMbUINT32));
            }
        } catch (IOException e) {
            Log.E(e.toString());
        }
        return new String(byteArrayOutputStream.toByteArray(), Charset.defaultCharset());
    }

    public String xdmWbxDecParseStr_i() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int xdmWbxDecReadBufferByte = xdmWbxDecReadBufferByte();
            if (xdmWbxDecReadBufferByte == 0) {
                String str = new String(byteArrayOutputStream.toByteArray(), Charset.defaultCharset());
                byteArrayOutputStream.close();
                return str;
            } else if (xdmWbxDecReadBufferByte != -1) {
                byteArrayOutputStream.write(xdmWbxDecReadBufferByte);
            } else {
                throw new IOException("Unexpected EOF wbxdec_parse_str_i");
            }
        }
    }

    public String xdmWbxDecParseExtension(int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (i == 195) {
            try {
                int xdmWbxDecReadBufferMbUINT32 = xdmWbxDecReadBufferMbUINT32();
                for (int i2 = 0; i2 < xdmWbxDecReadBufferMbUINT32; i2++) {
                    byteArrayOutputStream.write(xdmWbxDecReadBufferByte());
                }
                return new String(byteArrayOutputStream.toByteArray(), Charset.defaultCharset());
            } catch (IOException e) {
                Log.E(e.toString());
            }
        }
        return null;
    }

    public String xdmWbxDecParseStringtable() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int xdmWbxDecReadBufferMbUINT32 = xdmWbxDecReadBufferMbUINT32();
            for (int i = 0; i < xdmWbxDecReadBufferMbUINT32; i++) {
                byteArrayOutputStream.write(xdmWbxDecReadBufferByte());
            }
            return new String(byteArrayOutputStream.toByteArray(), Charset.defaultCharset());
        } catch (IOException e) {
            Log.E(e.toString());
            return null;
        }
    }

    public int xdmWbxDecReadBufferMbUINT32() throws IOException {
        int xdmWbxDecReadBufferByte;
        int i = 0;
        for (int i2 = 0; i2 < 5 && (xdmWbxDecReadBufferByte = xdmWbxDecReadBufferByte()) >= 0; i2++) {
            i = (i << 7) | (xdmWbxDecReadBufferByte & 127);
            if ((xdmWbxDecReadBufferByte & 128) == 0) {
                return i;
            }
        }
        return 0;
    }

    public int xdmWbxDecReadBufferByte() throws IOException {
        byte[] bArr = this.wbxbuff;
        int i = this.wbxindex;
        this.wbxindex = i + 1;
        int i2 = bArr[i] & 255;
        if (i2 != -1) {
            return i2;
        }
        throw new IOException("Unexpected EOF wbxdec_buffer_read_byte");
    }
}
