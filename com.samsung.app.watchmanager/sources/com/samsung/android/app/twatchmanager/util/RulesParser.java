package com.samsung.android.app.twatchmanager.util;

import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.CommonInfo;
import com.samsung.android.app.twatchmanager.model.CommonPackageItem;
import com.samsung.android.app.twatchmanager.model.DeviceItem;
import com.samsung.android.app.twatchmanager.model.ModuleInfo;
import com.samsung.android.app.twatchmanager.model.UHMPackageInfo;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

public class RulesParser extends BaseRulesParser {
    private static final String TAG = "RulesParser";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE = "additional-packages";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE_ITEM = "package";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE_ITEM_CHECK_FOR_UPDATE_ATTRIBUTE = "checkForUpdate";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE_ITEM_INSTALLER_PACKAGE_ATTRIBUTE = "installerPackage";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE_ITEM_ONLY_FOR_NONSAMSUNG_ATTRIBUTE = "onlyForNonSamsung";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE_ITEM_OPTIONAL_ATTRIBUTE = "optional";
    protected static final String XML_TAG_ADDITIONAL_PACKAGE_ITEM_REQUIRED_FOR_NONSAMSUNG_ATTRIBUTE = "requiredForNonSamsung";
    protected static final String XML_TAG_COMMON_INFO = "common-info";
    protected static final String XML_TAG_COMMON_INFO_DISCOVERY_KEYWORD = "discovery-keyword";
    protected static final String XML_TAG_COMMON_INFO_DISCOVERY_KEYWORD_ITEM = "item";
    protected static final String XML_TAG_COMMON_INFO_PACKAGES = "packages";
    protected static final String XML_TAG_COMMON_INFO_PACKAGE_ITEM = "item";
    protected static final String XML_TAG_COMMON_INFO_PACKAGE_ITEM_DISABLE_PACKAGE_ATTRIBUTE = "disablePackage";
    protected static final String XML_TAG_COMMON_INFO_SKIP_DEVCE = "skip-device";
    protected static final String XML_TAG_COMMON_INFO_SKIP_DEVCE_ITEM = "item";
    protected static final String XML_TAG_DEVICES = "devices";
    protected static final String XML_TAG_DEVICE_ITEM = "item";
    protected static final String XML_TAG_DEVICE_ITEM_ICON_ATTRIBUTE = "icon";
    protected static final String XML_TAG_DEVICE_ITEM_REQUIRES_PAIRING_ATTRIBUTE = "requiresPairing";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORTS_ONLY_BLE = "supportsBLEOnly";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORT_CONNECT_AS_AUDIO_ATTRIBUTE = "connectAudio";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORT_HOSTMINMEMORY_ATTRIBUTE = "hostMinMemory";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORT_MULTICONNECTION_ATTRIBUTE = "supportMultiConnection";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORT_NONSAMSUNG_ATTRIBUTE = "supportNonSamsung";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORT_PAIRING_IMAGE = "pairingImageName";
    protected static final String XML_TAG_DEVICE_ITEM_SUPPORT_TABLET_ATTRIBUTE = "supportTablet";
    protected static final String XML_TAG_DEVICE_ITEM_SWITCH_GEAR_TITLE_ICON = "switchGearTitleIcon";
    protected static final String XML_TAG_GEAR_INFO = "gear-info";
    protected static final String XML_TAG_GEAR_INFO_VERSION_ATTRIBUTE = "version";
    protected static final String XML_TAG_MODULE_INFO = "module-info";
    protected static final String XML_TAG_MODULE_INFO_CONTAINER_ATTRIBUTE = "containerName";
    protected static final String XML_TAG_MODULE_INFO_PLUGIN_APP_ATTRIBUTE = "pluginAppName";
    protected static final String XML_TAG_MODULE_INFO_PLUGIN_ATTRIBUTE = "pluginName";
    protected static final String XML_TAG_MODULE_INFO_VI_ATTRIBUTE = "viClass";
    protected CommonInfo mCommonInfo;
    protected ArrayList<ModuleInfo> mModuleInfoList;

    public RulesParser(int i) {
        super(i);
    }

    @Override // com.samsung.android.app.twatchmanager.util.IRulesParser, com.samsung.android.app.twatchmanager.util.BaseRulesParser
    public List<ModuleInfo> getAllModuleInfo() {
        ArrayList<ModuleInfo> arrayList = this.mModuleInfoList;
        if (arrayList == null || arrayList.size() == 0) {
            parseAndStoreModuleInfo();
        }
        return this.mModuleInfoList;
    }

    @Override // com.samsung.android.app.twatchmanager.util.IRulesParser
    public CommonInfo getCommonInfo() {
        ArrayList<ModuleInfo> arrayList = this.mModuleInfoList;
        if (arrayList == null || arrayList.size() == 0) {
            parseAndStoreModuleInfo();
        }
        return this.mCommonInfo;
    }

    /* access modifiers changed from: protected */
    public UHMPackageInfo parseAdditionalPackageItem(XmlPullParser xmlPullParser) {
        Log.d(TAG, "parseAdditionalPackageItem");
        boolean parseBoolean = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_ADDITIONAL_PACKAGE_ITEM_OPTIONAL_ATTRIBUTE));
        boolean parseBoolean2 = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_ADDITIONAL_PACKAGE_ITEM_REQUIRED_FOR_NONSAMSUNG_ATTRIBUTE));
        boolean parseBoolean3 = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_ADDITIONAL_PACKAGE_ITEM_CHECK_FOR_UPDATE_ATTRIBUTE));
        boolean parseBoolean4 = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_ADDITIONAL_PACKAGE_ITEM_ONLY_FOR_NONSAMSUNG_ATTRIBUTE));
        String attributeValue = xmlPullParser.getAttributeValue(null, XML_TAG_ADDITIONAL_PACKAGE_ITEM_INSTALLER_PACKAGE_ATTRIBUTE);
        if (attributeValue == null) {
            attributeValue = TWatchManagerApplication.getAppContext().getPackageName();
        }
        if (xmlPullParser.next() == 4) {
            return new UHMPackageInfo(xmlPullParser.getText(), parseBoolean, parseBoolean2, attributeValue, parseBoolean3, parseBoolean4);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void parseAdditionalPackages(XmlPullParser xmlPullParser, ModuleInfo moduleInfo) {
        UHMPackageInfo parseAdditionalPackageItem;
        Log.d(TAG, " parseAdditionalPackages() : ");
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (XML_TAG_ADDITIONAL_PACKAGE_ITEM.equalsIgnoreCase(xmlPullParser.getName()) && (parseAdditionalPackageItem = parseAdditionalPackageItem(xmlPullParser)) != null) {
                    moduleInfo.addAdditionalPackage(parseAdditionalPackageItem);
                }
            } else if (next == 3 && XML_TAG_ADDITIONAL_PACKAGE.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a8 A[SYNTHETIC, Splitter:B:35:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b3 A[SYNTHETIC, Splitter:B:40:0x00b3] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseAndStoreModuleInfo() {
        /*
        // Method dump skipped, instructions count: 189
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.util.RulesParser.parseAndStoreModuleInfo():void");
    }

    /* access modifiers changed from: protected */
    public CommonInfo parseCommonInfo(XmlPullParser xmlPullParser) {
        Log.d(TAG, "parserCommonInfo() ");
        CommonInfo commonInfo = new CommonInfo();
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (XML_TAG_COMMON_INFO_PACKAGES.equalsIgnoreCase(name)) {
                    parseCommonInfoPackages(xmlPullParser, commonInfo);
                } else if (XML_TAG_COMMON_INFO_DISCOVERY_KEYWORD.equalsIgnoreCase(name)) {
                    parseCommonInfoDiscoveryKeyword(xmlPullParser, commonInfo);
                } else if (XML_TAG_COMMON_INFO_SKIP_DEVCE.equalsIgnoreCase(name)) {
                    parseCommonInfoSkipDevice(xmlPullParser, commonInfo);
                }
            } else if (next == 3 && XML_TAG_COMMON_INFO.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
        return commonInfo;
    }

    /* access modifiers changed from: protected */
    public void parseCommonInfoDiscoveryKeyword(XmlPullParser xmlPullParser, CommonInfo commonInfo) {
        String text;
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(xmlPullParser.getName()) && xmlPullParser.next() == 4 && (text = xmlPullParser.getText()) != null) {
                    commonInfo.addDiscoveryKeyword(text);
                }
            } else if (next == 3 && XML_TAG_COMMON_INFO_DISCOVERY_KEYWORD.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseCommonInfoPackages(XmlPullParser xmlPullParser, CommonInfo commonInfo) {
        CommonPackageItem parseCommonPackageItem;
        Log.d(TAG, "parseCommonInfoPackages()");
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(xmlPullParser.getName()) && (parseCommonPackageItem = parseCommonPackageItem(xmlPullParser)) != null) {
                    commonInfo.addCommonPackageInfo(parseCommonPackageItem);
                }
            } else if (next == 3 && XML_TAG_COMMON_INFO_PACKAGES.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseCommonInfoSkipDevice(XmlPullParser xmlPullParser, CommonInfo commonInfo) {
        String text;
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(xmlPullParser.getName()) && xmlPullParser.next() == 4 && (text = xmlPullParser.getText()) != null) {
                    commonInfo.addSkipDevice(text);
                }
            } else if (next == 3 && XML_TAG_COMMON_INFO_SKIP_DEVCE.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public CommonPackageItem parseCommonPackageItem(XmlPullParser xmlPullParser) {
        String text;
        Log.d(TAG, "parseCommonPackageItem() ");
        CommonPackageItem commonPackageItem = new CommonPackageItem();
        String attributeValue = xmlPullParser.getAttributeValue(null, XML_TAG_COMMON_INFO_PACKAGE_ITEM_DISABLE_PACKAGE_ATTRIBUTE);
        if (attributeValue != null) {
            commonPackageItem.disablePackage = Boolean.parseBoolean(attributeValue);
        }
        if (xmlPullParser.next() != 4 || (text = xmlPullParser.getText()) == null) {
            return null;
        }
        commonPackageItem.packageName = text;
        return commonPackageItem;
    }

    /* access modifiers changed from: protected */
    public DeviceItem parseDeviceItem(XmlPullParser xmlPullParser) {
        String text;
        Log.d(TAG, "parseDeviceItem()");
        DeviceItem deviceItem = new DeviceItem();
        deviceItem.supportNonSamsung = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORT_NONSAMSUNG_ATTRIBUTE));
        deviceItem.supportMultiConnection = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORT_MULTICONNECTION_ATTRIBUTE));
        deviceItem.connectAsAudio = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORT_CONNECT_AS_AUDIO_ATTRIBUTE));
        String attributeValue = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORT_HOSTMINMEMORY_ATTRIBUTE);
        if (attributeValue != null) {
            deviceItem.hostMinMemory = Integer.parseInt(attributeValue);
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_ICON_ATTRIBUTE);
        if (attributeValue2 != null) {
            deviceItem.iconDrawableName = attributeValue2;
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORT_PAIRING_IMAGE);
        if (attributeValue3 != null) {
            deviceItem.pairingImageName = attributeValue3;
        }
        String attributeValue4 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORT_TABLET_ATTRIBUTE);
        if (attributeValue4 != null) {
            deviceItem.supportTablet = Boolean.parseBoolean(attributeValue4);
        }
        String attributeValue5 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_REQUIRES_PAIRING_ATTRIBUTE);
        if (attributeValue5 != null) {
            deviceItem.requiresPairing = Boolean.parseBoolean(attributeValue5);
        }
        String attributeValue6 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SUPPORTS_ONLY_BLE);
        if (attributeValue6 != null) {
            deviceItem.supportsBLEOnly = Boolean.parseBoolean(attributeValue6);
        }
        String attributeValue7 = xmlPullParser.getAttributeValue(null, XML_TAG_DEVICE_ITEM_SWITCH_GEAR_TITLE_ICON);
        if (attributeValue7 != null) {
            deviceItem.switchGearTitleIcon = attributeValue7;
        }
        if (xmlPullParser.next() != 4 || (text = xmlPullParser.getText()) == null) {
            return null;
        }
        deviceItem.deviceName = text;
        return deviceItem;
    }

    /* access modifiers changed from: protected */
    public void parseDevices(XmlPullParser xmlPullParser, ModuleInfo moduleInfo) {
        DeviceItem parseDeviceItem;
        Log.d(TAG, "parseDevices()");
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (ResourceRulesParser.XML_TAG_GROUP_IMAGES_ITEM.equalsIgnoreCase(xmlPullParser.getName()) && (parseDeviceItem = parseDeviceItem(xmlPullParser)) != null) {
                    moduleInfo.addDevice(parseDeviceItem);
                }
            } else if (next == 3 && XML_TAG_DEVICES.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseGearInfo(XmlPullParser xmlPullParser) {
        Log.d(TAG, "parseGearInfo()");
        this.mModuleInfoList = new ArrayList<>();
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (XML_TAG_MODULE_INFO.equalsIgnoreCase(name)) {
                    ModuleInfo parserModuleInfo = parserModuleInfo(xmlPullParser);
                    String str = TAG;
                    Log.d(str, "parseGearInfo() ModuleInfo :" + parserModuleInfo);
                    if (parserModuleInfo != null) {
                        this.mModuleInfoList.add(parserModuleInfo);
                    }
                } else if (XML_TAG_COMMON_INFO.equalsIgnoreCase(name)) {
                    this.mCommonInfo = parseCommonInfo(xmlPullParser);
                }
            } else if (next == 3 && XML_TAG_GEAR_INFO.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public ModuleInfo parserModuleInfo(XmlPullParser xmlPullParser) {
        Log.d(TAG, "parserModuleInfo() ");
        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.containerPackage = xmlPullParser.getAttributeValue(null, XML_TAG_MODULE_INFO_CONTAINER_ATTRIBUTE);
        moduleInfo.viClass = xmlPullParser.getAttributeValue(null, XML_TAG_MODULE_INFO_VI_ATTRIBUTE);
        moduleInfo.pluginPackage = xmlPullParser.getAttributeValue(null, XML_TAG_MODULE_INFO_PLUGIN_ATTRIBUTE);
        moduleInfo.pluginAppName = xmlPullParser.getAttributeValue(null, XML_TAG_MODULE_INFO_PLUGIN_APP_ATTRIBUTE);
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                if (XML_TAG_DEVICES.equalsIgnoreCase(name)) {
                    parseDevices(xmlPullParser, moduleInfo);
                } else if (XML_TAG_ADDITIONAL_PACKAGE.equalsIgnoreCase(name)) {
                    parseAdditionalPackages(xmlPullParser, moduleInfo);
                }
            } else if (next == 3 && XML_TAG_MODULE_INFO.equalsIgnoreCase(xmlPullParser.getName())) {
                z = true;
            }
        }
        return moduleInfo;
    }
}
