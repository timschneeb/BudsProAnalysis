package com.samsung.android.app.twatchmanager.util;

import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.manager.GearRulesManager;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

public class ResourceRulesParser {
    private static final String TAG = "ResourceRulesParser";
    public static final String XML_TAG_GROUP = "group";
    public static final String XML_TAG_GROUP_ALIAS_NAME = "aliasName";
    public static final String XML_TAG_GROUP_DISPLAY_ORDER = "displayOrder";
    public static final String XML_TAG_GROUP_IMAGES_ITEM = "item";
    public static final String XML_TAG_GROUP_IMAGES_MULTI_ITEM = "multi-item";
    public static final String XML_TAG_GROUP_NAME = "name";
    public static final String XML_TAG_RESOURCE_INFO = "resource-info";
    public static final String XML_TAG_RULES = "rules";
    protected ArrayList<GroupInfo> mGroupInfoList;
    private int mMajorVersion;

    public ResourceRulesParser(int i) {
        this.mMajorVersion = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040 A[SYNTHETIC, Splitter:B:14:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseAndStoreResourceInfo() {
        /*
        // Method dump skipped, instructions count: 117
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.ResourceRulesParser.parseAndStoreResourceInfo():void");
    }

    private void parseAttributes(XmlPullParser xmlPullParser, HashMap<String, String> hashMap) {
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            hashMap.put(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
        }
    }

    private void parseGroups(XmlPullParser xmlPullParser) {
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (XML_TAG_GROUP.equalsIgnoreCase(xmlPullParser.getName())) {
                    GroupInfo groupInfo = new GroupInfo();
                    groupInfo.name = xmlPullParser.getAttributeValue(null, XML_TAG_GROUP_NAME);
                    if (!GearRulesManager.getInstance().isDeviceInfoAvailable()) {
                        Log.e(TAG, "deviceInfo is not available, need to parse xml");
                        GearRulesManager.getInstance().syncGearInfoSynchronously();
                    }
                    String attributeValue = xmlPullParser.getAttributeValue(null, XML_TAG_GROUP_DISPLAY_ORDER);
                    if (attributeValue != null) {
                        groupInfo.displayOrder = Integer.valueOf(Integer.parseInt(attributeValue));
                    }
                    groupInfo.aliasName = xmlPullParser.getAttributeValue(null, XML_TAG_GROUP_ALIAS_NAME);
                    if (groupInfo.aliasName != null) {
                        String str = TAG;
                        Log.d(str, "B> info.aliasName:" + groupInfo.aliasName);
                        groupInfo.aliasName = groupInfo.aliasName.replace("\\n", System.getProperty("line.separator"));
                        String str2 = TAG;
                        Log.d(str2, "A> info.aliasName:" + groupInfo.aliasName);
                    }
                    groupInfo.images = new HashMap<>();
                    parseImageItem(xmlPullParser, groupInfo);
                    if (!HostManagerUtils.isTablet() || GearRulesManager.getInstance().getGearInfo(groupInfo.name).supportTablet) {
                        this.mGroupInfoList.add(groupInfo);
                    }
                }
            } else if (next == 3 && XML_TAG_RESOURCE_INFO.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    private void parseImageItem(XmlPullParser xmlPullParser, GroupInfo groupInfo) {
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(name)) {
                    parseSingleItem(xmlPullParser, groupInfo);
                } else if (XML_TAG_GROUP_IMAGES_MULTI_ITEM.equalsIgnoreCase(name)) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    parseAttributes(xmlPullParser, hashMap);
                    parseMultiItem(xmlPullParser, groupInfo, hashMap);
                }
            } else if (next == 3 && XML_TAG_GROUP.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    private void parseMultiItem(XmlPullParser xmlPullParser, GroupInfo groupInfo, HashMap<String, String> hashMap) {
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(xmlPullParser.getName())) {
                    parseSingleItemInternal(xmlPullParser, groupInfo, new HashMap<>(hashMap));
                }
            } else if (next == 3 && XML_TAG_GROUP_IMAGES_MULTI_ITEM.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    private void parseSingleItem(XmlPullParser xmlPullParser, GroupInfo groupInfo) {
        parseSingleItemInternal(xmlPullParser, groupInfo, new HashMap<>());
    }

    private void parseSingleItemInternal(XmlPullParser xmlPullParser, GroupInfo groupInfo, HashMap<String, String> hashMap) {
        parseAttributes(xmlPullParser, hashMap);
        if (xmlPullParser.next() == 4) {
            GroupInfo.ImageInfo imageInfo = new GroupInfo.ImageInfo();
            imageInfo.name = xmlPullParser.getText();
            imageInfo.attributes = hashMap;
            String str = imageInfo.attributes.get(GroupInfo.ImageInfo.ATTR_TYPE);
            if (!TextUtils.isEmpty(str)) {
                ArrayList<GroupInfo.ImageInfo> arrayList = groupInfo.images.get(str);
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                    groupInfo.images.put(str, arrayList);
                }
                arrayList.add(imageInfo);
            }
        }
    }

    private void skip(XmlPullParser xmlPullParser) {
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

    public ArrayList<GroupInfo> getAllResourceInfo() {
        ArrayList<GroupInfo> arrayList = this.mGroupInfoList;
        if (arrayList == null || arrayList.isEmpty()) {
            Log.d(TAG, "getAllResourceInfo() data is empty, start to parse rules");
            parseAndStoreResourceInfo();
        }
        return this.mGroupInfoList;
    }

    public ArrayList<GroupInfo> getGroupInfoList() {
        return this.mGroupInfoList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getRulesXMLVersion(java.io.InputStream r9) {
        /*
        // Method dump skipped, instructions count: 159
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.ResourceRulesParser.getRulesXMLVersion(java.io.InputStream):java.lang.String");
    }
}
