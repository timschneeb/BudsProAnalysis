package com.samsung.android.app.twatchmanager.util;

import com.samsung.android.app.twatchmanager.contentprovider.BaseContentProvider;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.ModuleInfo;
import java.io.InputStream;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class BaseRulesParser implements IRulesParser {
    private static final String TAG = "BaseRulesParser";
    protected int mMajorVersion;

    protected BaseRulesParser(int i) {
        this.mMajorVersion = i;
    }

    @Override // com.samsung.android.app.twatchmanager.util.IRulesParser
    public abstract List<ModuleInfo> getAllModuleInfo();

    @Override // com.samsung.android.app.twatchmanager.util.IRulesParser
    public String getRulesXMLVersion(InputStream inputStream) {
        Log.d(TAG, "getRulesXMLVersion()");
        String str = null;
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(inputStream, null);
            int eventType = newPullParser.getEventType();
            while (true) {
                if (eventType == 1) {
                    break;
                }
                if (eventType == 2) {
                    String name = newPullParser.getName();
                    String str2 = TAG;
                    Log.d(str2, " tagName :" + name);
                    if ("gear-info".equalsIgnoreCase(name)) {
                        String attributeValue = newPullParser.getAttributeValue(null, BaseContentProvider.VERSION);
                        String str3 = TAG;
                        Log.d(str3, "getRulesXMLVersion() tempVersion:" + attributeValue);
                        if (attributeValue != null) {
                            String[] split = attributeValue.split("\\.");
                            if (split.length != 2) {
                                continue;
                            } else if (this.mMajorVersion == Integer.parseInt(split[0])) {
                                str = attributeValue;
                                break;
                            } else {
                                skip(newPullParser);
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                eventType = newPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str4 = TAG;
        Log.d(str4, "getRulesXMLVersion() MajorVersion: " + this.mMajorVersion + " return version:" + str);
        return str;
    }

    /* access modifiers changed from: protected */
    public void skip(XmlPullParser xmlPullParser) {
        if (xmlPullParser.getEventType() == 2) {
            int i = 1;
            while (i != 0) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    i++;
                } else if (next == 3) {
                    i--;
                }
            }
            return;
        }
        throw new IllegalStateException();
    }
}
