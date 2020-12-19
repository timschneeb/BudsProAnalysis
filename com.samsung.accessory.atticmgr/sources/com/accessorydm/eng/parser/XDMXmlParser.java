package com.accessorydm.eng.parser;

import com.accessorydm.eng.core.XDMDDFXmlHandler;
import com.accessorydm.eng.core.XDMDDXmlDataSet;
import com.accessorydm.eng.core.XDMDDXmlHandler;
import com.samsung.android.fotaprovider.log.Log;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XDMXmlParser {
    public XDMDDXmlDataSet xdmXmlParserDownloadDescriptor(byte[] bArr) throws Exception {
        Log.I("");
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        XDMDDXmlHandler xDMDDXmlHandler = new XDMDDXmlHandler();
        newPullParser.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                xDMDDXmlHandler.startTag(newPullParser.getName());
            } else if (eventType == 3) {
                xDMDDXmlHandler.endTag(newPullParser.getName());
            } else if (eventType == 4) {
                xDMDDXmlHandler.text(newPullParser);
            }
        }
        return xDMDDXmlHandler.xdmDDXmlGetParsedData();
    }

    public void xdmXmlParserDDF(String str) {
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            XDMDDFXmlHandler xDMDDFXmlHandler = new XDMDDFXmlHandler();
            newPullParser.setInput(new InputStreamReader(new ByteArrayInputStream(str.toString().getBytes(Charset.defaultCharset()))));
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 2) {
                    xDMDDFXmlHandler.startTag(newPullParser.getName());
                } else if (eventType == 3) {
                    xDMDDFXmlHandler.endTag(newPullParser.getName());
                } else if (eventType == 4) {
                    xDMDDFXmlHandler.text(newPullParser);
                }
            }
        } catch (Exception e) {
            Log.E(e.toString());
        }
    }
}
