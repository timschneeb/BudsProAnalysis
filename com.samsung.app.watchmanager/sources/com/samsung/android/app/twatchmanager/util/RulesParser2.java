package com.samsung.android.app.twatchmanager.util;

import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.DeviceItem;
import com.samsung.android.app.twatchmanager.model.GearGroup;
import com.samsung.android.app.twatchmanager.model.ModuleInfo;
import org.xmlpull.v1.XmlPullParser;

public class RulesParser2 extends RulesParser {
    private static final String TAG = "RulesParser2";
    protected static final String XML_TAG_DEVICE_GROUP = "group";
    protected static final String XML_TAG_DEVICE_GROUP_DOWNLOAD_INSTALL_LAYOUT_ATTRIBUTE = "downloadInstallLayout";
    protected static final String XML_TAG_DEVICE_GROUP_MAX_API_LEVEL = "maxAPILevel";
    protected static final String XML_TAG_DEVICE_GROUP_NAME_ATTRIBUTE = "name";
    protected static final String XML_TAG_DEVICE_GROUP_REQUEST_DISCONNECT_ALWAYS = "requestDisconnectAlways";
    protected static final String XML_TAG_DEVICE_GROUP_RESET_OPTION = "resetOption";
    protected static final String XML_TAG_DEVICE_GROUP_VI_FRAGMENT_ATTRIBUTE = "viFragment";
    protected static final String XML_TAG_DEVICE_GROUP_WEARABLE_TYPE = "wearableType";
    protected static final String XML_TAG_DEVICE_ITEM_SWITCH_GEAR_TITLE_ICON = "switchGearTitleIcon";

    public RulesParser2(int i) {
        super(i);
    }

    /* access modifiers changed from: protected */
    @Override // com.samsung.android.app.twatchmanager.util.RulesParser
    public void parseDevices(XmlPullParser xmlPullParser, ModuleInfo moduleInfo) {
        Log.d(TAG, "parseDevices()");
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if ("group".equals(xmlPullParser.getName())) {
                    parseGroup(xmlPullParser, moduleInfo);
                }
            } else if (next == 3 && "devices".equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseGroup(XmlPullParser xmlPullParser, ModuleInfo moduleInfo) {
        GearGroup gearGroup = new GearGroup();
        gearGroup.name = xmlPullParser.getAttributeValue(null, "name");
        String attributeValue = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_GROUP_DOWNLOAD_INSTALL_LAYOUT_ATTRIBUTE);
        if (attributeValue != null) {
            gearGroup.downloadInstallLayout = attributeValue;
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_GROUP_WEARABLE_TYPE);
        if (attributeValue2 != null) {
            gearGroup.wearableType = attributeValue2;
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_GROUP_MAX_API_LEVEL);
        if (attributeValue3 != null) {
            gearGroup.maxAPILevel = Integer.valueOf(attributeValue3).intValue();
        }
        boolean parseBoolean = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_GROUP_RESET_OPTION));
        if (!parseBoolean) {
            gearGroup.resetOption = parseBoolean;
        }
        gearGroup.requestDisconnectAlways = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_GROUP_REQUEST_DISCONNECT_ALWAYS));
        String attributeValue4 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SWITCH_GEAR_TITLE_ICON);
        if (attributeValue4 != null) {
            gearGroup.switchGearTitleIcon = attributeValue4;
        }
        moduleInfo.addGroup(gearGroup);
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(xmlPullParser.getName())) {
                    DeviceItem parseDeviceItem = parseDeviceItem(xmlPullParser);
                    parseDeviceItem.gearGroup = gearGroup;
                    if (parseDeviceItem != null) {
                        moduleInfo.addDevice(parseDeviceItem);
                    }
                }
            } else if (next == 3 && "group".equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }
}
