package com.samsung.android.fotaagent.network;

import java.io.StringReader;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

public class Xpath {
    public static String getResult(String str, String str2) throws XPathExpressionException {
        StringReader stringReader = new StringReader(str);
        try {
            return XPathFactory.newInstance().newXPath().evaluate(str2, new InputSource(stringReader));
        } finally {
            stringReader.close();
        }
    }
}
