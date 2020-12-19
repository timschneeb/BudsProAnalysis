package com.samsung.android.app.twatchmanager.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.CommonInfo;
import com.samsung.android.app.twatchmanager.model.CommonPackageItem;
import com.samsung.android.app.twatchmanager.model.DeviceItem;
import com.samsung.android.app.twatchmanager.model.GearInfo;
import com.samsung.android.app.twatchmanager.model.ModuleInfo;
import com.samsung.android.app.twatchmanager.model.UHMPackageInfo;
import com.samsung.android.app.twatchmanager.util.HandlerThreadUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsRulesBTDevices;
import com.samsung.android.app.twatchmanager.util.IRulesParser;
import com.samsung.android.app.twatchmanager.util.RulesParserFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GearRulesManager {
    private static final String CONTAINER_PACKAGE_SET = "container_package_set";
    private static final String PREF_DEVICE_CONTAINER_MAP = "pref_device_container_map";
    private static final String PREF_DEVICE_PLUGIN_MAP = "pref_device_plugin_map";
    private static final String PREF_GEAR_INFO = "pref_gear_info";
    private static final String RULES_FILE_NAME = "rules.xml";
    private static final String RULES_XML_VERSION = "rules_xml_version";
    private static final String SUPPORT_PACKAGE_SET = "support_package_set";
    private static final String TAG = ("tUHM:" + GearRulesManager.class.getSimpleName());
    private HashMap<String, ArrayList<UHMPackageInfo>> additionalPackageInfoMap;
    private HashMap<String, CommonPackageItem> commonPackageItemMap;
    private HashSet<String> containerPackageSet;
    private HashMap<String, GearInfo> gearInfoMap;
    private HashMap<String, Set<String>> groupGearSetMap;
    private final Context mContext;
    private List<String> mDiscoveryKeywords;
    private ISyncCallback mISyncCallback;
    private IRulesParser mRulesParser;
    private List<String> mSkipDevices;
    private HashSet<String> supportPackageSet;
    private Handler syncHandler;
    private HandlerThread syncHandlerThread;

    public interface ISyncCallback {
        void onSyncComplete(boolean z);
    }

    /* access modifiers changed from: private */
    public static class LazyHolder {
        private static final GearRulesManager INSTANCE = new GearRulesManager();

        private LazyHolder() {
        }
    }

    private GearRulesManager() {
        this.mContext = TWatchManagerApplication.getAppContext();
        String str = TAG;
        Log.d(str, " mContext :" + this.mContext);
    }

    private InputStream getInputStreamFromAsset() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        try {
            return context.getAssets().open(RULES_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GearRulesManager getInstance() {
        Log.d(TAG, "getInstance()");
        return LazyHolder.INSTANCE;
    }

    public static String getLocalRulesFilePath() {
        if (TWatchManagerApplication.getAppContext() == null) {
            return null;
        }
        return TWatchManagerApplication.getAppContext().getFilesDir() + File.separator + RULES_FILE_NAME;
    }

    private String getXMLVersion() {
        return this.mContext.getSharedPreferences(PREF_GEAR_INFO, 0).getString(RULES_XML_VERSION, "0.0");
    }

    private boolean isUpdateNeeded(String str) {
        String str2 = TAG;
        Log.d(str2, "isUpdateNeeded() newVersion: " + str);
        boolean z = false;
        if (str != null) {
            String[] split = str.split("\\.");
            String str3 = TAG;
            Log.d(str3, "isUpdateNeeded() newVersion.lenght: " + split.length);
            if (split.length == 2) {
                String[] split2 = getXMLVersion().split("\\.");
                int i = 0;
                while (true) {
                    if (i >= 2) {
                        break;
                    }
                    String str4 = TAG;
                    Log.d(str4, " isUpdateNeeded()  newVersion[" + i + "]:" + split[i] + " currentVersion[" + i + "]:" + split2[i]);
                    if (Integer.parseInt(split[i]) > Integer.parseInt(split2[i])) {
                        z = true;
                        break;
                    }
                    i++;
                }
            }
        }
        String str5 = TAG;
        Log.d(str5, "isUpdateNeeded() returns:" + z);
        return z;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void onSyncComplete(boolean z) {
        if (this.mISyncCallback != null) {
            this.mISyncCallback.onSyncComplete(z);
            this.mISyncCallback = null;
        }
        if (this.syncHandler != null && !this.syncHandler.hasMessages(0)) {
            this.syncHandler.removeCallbacksAndMessages(null);
            this.syncHandler = null;
            HandlerThreadUtils.close(this.syncHandlerThread);
            this.syncHandlerThread = null;
            Log.d(TAG, "onSyncComplete() cleanup");
        }
    }

    private void savePrefDeviceContainerMap() {
        Log.d(TAG, "savePrefDeviceContainerMap()");
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences(PREF_DEVICE_CONTAINER_MAP, 0).edit();
        for (GearInfo gearInfo : this.gearInfoMap.values()) {
            if (gearInfo.containerPackage != null) {
                edit.putString(gearInfo.deviceName.toUpperCase(Locale.ENGLISH), gearInfo.containerPackage);
            }
        }
        edit.apply();
    }

    private void savePrefDevicePluginMap() {
        Log.d(TAG, "savePrefDevicePluginMap()");
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences(PREF_DEVICE_PLUGIN_MAP, 0).edit();
        for (GearInfo gearInfo : this.gearInfoMap.values()) {
            edit.putString(gearInfo.deviceName.toUpperCase(Locale.ENGLISH), gearInfo.pluginPackage);
        }
        edit.apply();
    }

    private boolean savePreferences(String str) {
        String str2 = TAG;
        Log.d(str2, "savePreferences() xmlVersion:" + str);
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences(PREF_GEAR_INFO, 0).edit();
        HashSet<String> hashSet = this.supportPackageSet;
        if (hashSet != null && hashSet.size() > 0) {
            edit.putStringSet(SUPPORT_PACKAGE_SET, this.supportPackageSet);
        }
        HashSet<String> hashSet2 = this.containerPackageSet;
        if (hashSet2 != null && hashSet2.size() > 0) {
            edit.putStringSet(CONTAINER_PACKAGE_SET, this.containerPackageSet);
        }
        edit.putString(RULES_XML_VERSION, str);
        edit.apply();
        savePrefDeviceContainerMap();
        savePrefDevicePluginMap();
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean startSyncRules() {
        Log.d(TAG, "startSyncRules() starts...");
        InputStream inputStreamFromAsset = getInputStreamFromAsset();
        boolean z = false;
        if (inputStreamFromAsset != null) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamFromAsset);
            try {
                bufferedInputStream.mark(inputStreamFromAsset.available() + 1);
                IRulesParser rulesParser = getRulesParser();
                if (rulesParser != null) {
                    String rulesXMLVersion = rulesParser.getRulesXMLVersion(bufferedInputStream);
                    if (isUpdateNeeded(rulesXMLVersion)) {
                        bufferedInputStream.reset();
                        updateRulesCache(bufferedInputStream);
                        bufferedInputStream.close();
                    }
                    z = processRulesFile(rulesXMLVersion);
                } else {
                    Log.e(TAG, "startSyncRules(), parser is null");
                }
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                bufferedInputStream.close();
            } catch (Throwable th) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        } else {
            Log.e(TAG, " startSyncRules()Input stream null");
        }
        return z;
    }

    private void syncContainerPackageSet() {
        HashSet<String> hashSet = this.containerPackageSet;
        if (hashSet == null || hashSet.size() == 0) {
            this.containerPackageSet = (HashSet) this.mContext.getSharedPreferences(PREF_GEAR_INFO, 0).getStringSet(CONTAINER_PACKAGE_SET, null);
        }
    }

    private void syncSupportPackageSet() {
        HashSet<String> hashSet = this.supportPackageSet;
        if (hashSet == null || hashSet.size() == 0) {
            this.supportPackageSet = (HashSet) this.mContext.getSharedPreferences(PREF_GEAR_INFO, 0).getStringSet(SUPPORT_PACKAGE_SET, null);
        }
    }

    public boolean connectAsAudio(String str) {
        String str2 = TAG;
        Log.d(str2, "connectAsAudio = " + str);
        boolean z = (!isDeviceInfoAvailable() || !isValidDevice(str)) ? false : getGearInfo(str).connectAsAudio;
        String str3 = TAG;
        Log.d(str3, "connectAsAudio result: " + z);
        return z;
    }

    public ArrayList<UHMPackageInfo> getAdditionalPackageList(String str) {
        HashMap<String, ArrayList<UHMPackageInfo>> hashMap;
        ArrayList<UHMPackageInfo> arrayList = (str == null || (hashMap = this.additionalPackageInfoMap) == null) ? null : hashMap.get(str.trim().toUpperCase(Locale.ENGLISH));
        String str2 = TAG;
        Log.d(str2, " getAdditionalPackageList() deviceName: " + str + " return :" + arrayList);
        return arrayList;
    }

    public Set<String> getAllAdditionalPackageNames() {
        HashSet hashSet = new HashSet();
        for (ArrayList<UHMPackageInfo> arrayList : this.additionalPackageInfoMap.values()) {
            Iterator<UHMPackageInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().packageName);
            }
        }
        return hashSet;
    }

    public Set<UHMPackageInfo> getAllAdditionalPackages() {
        HashSet hashSet = new HashSet();
        for (ArrayList<UHMPackageInfo> arrayList : this.additionalPackageInfoMap.values()) {
            Iterator<UHMPackageInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next());
            }
        }
        String str = TAG;
        Log.d(str, "getAllAdditionalPackages() :" + hashSet);
        return hashSet;
    }

    public Map<String, GearInfo> getAllGearInfo() {
        return Collections.unmodifiableMap(this.gearInfoMap);
    }

    public String getContainerPackage(String str) {
        if (isDeviceInfoAvailable() && isValidDevice(str)) {
            return getGearInfo(str).containerPackage;
        }
        if (str != null) {
            return this.mContext.getSharedPreferences(PREF_DEVICE_CONTAINER_MAP, 0).getString(str.toUpperCase(Locale.ENGLISH), null);
        }
        return null;
    }

    public GearInfo getGearInfo(String str) {
        String str2 = TAG;
        Log.d(str2, "getGearInfo()  deviceName:" + str);
        if (str == null) {
            return null;
        }
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        String str3 = TAG;
        Log.d(str3, "getGearInfo()  simpleDeviceName:" + simpleBTNameByName);
        if (simpleBTNameByName == null) {
            return null;
        }
        String trim = simpleBTNameByName.toUpperCase(Locale.ENGLISH).trim();
        HashMap<String, GearInfo> hashMap = this.gearInfoMap;
        if (hashMap != null) {
            return hashMap.get(trim);
        }
        return null;
    }

    public List<String> getGroupDeviceNames(String str) {
        ArrayList arrayList;
        if (str != null) {
            Set<String> set = this.groupGearSetMap.get(str.toUpperCase(Locale.ENGLISH));
            String str2 = TAG;
            Log.d(str2, " getGroupDeviceNames() Complete Map:" + set);
            if (set != null && set.size() > 0) {
                arrayList = new ArrayList(set);
                String str3 = TAG;
                Log.d(str3, " getGroupDeviceNames() groupName:" + str + " returns :" + arrayList);
                return arrayList;
            }
        }
        arrayList = null;
        String str32 = TAG;
        Log.d(str32, " getGroupDeviceNames() groupName:" + str + " returns :" + arrayList);
        return arrayList;
    }

    public String getIcon(String str) {
        GearInfo gearInfo;
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        if (!isDeviceInfoAvailable() || !isValidDevice(simpleBTNameByName) || (gearInfo = getGearInfo(simpleBTNameByName)) == null) {
            return null;
        }
        return gearInfo.iconDrawableName;
    }

    public String getPairingImage(String str) {
        GearInfo gearInfo;
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        if (!isDeviceInfoAvailable() || !isValidDevice(simpleBTNameByName) || (gearInfo = getGearInfo(simpleBTNameByName)) == null) {
            return null;
        }
        return gearInfo.pairingImageName;
    }

    public String getPluginPackage(String str) {
        if (isDeviceInfoAvailable() && isValidDevice(str)) {
            return getGearInfo(str).pluginPackage;
        }
        if (str != null) {
            return this.mContext.getSharedPreferences(PREF_DEVICE_PLUGIN_MAP, 0).getString(str.toUpperCase(Locale.ENGLISH), null);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public IRulesParser getRulesParser() {
        if (this.mRulesParser == null) {
            this.mRulesParser = RulesParserFactory.getParser(2);
        }
        return this.mRulesParser;
    }

    public String getSupportPackage(String str) {
        if (isDeviceInfoAvailable() && isValidDevice(str)) {
            GearInfo gearInfo = getGearInfo(str);
            if (gearInfo != null) {
                String str2 = gearInfo.containerPackage;
                return str2 != null ? str2 : gearInfo.pluginPackage;
            }
        } else if (str != null) {
            String string = this.mContext.getSharedPreferences(PREF_DEVICE_CONTAINER_MAP, 0).getString(str.toUpperCase(Locale.ENGLISH), null);
            return string == null ? this.mContext.getSharedPreferences(PREF_DEVICE_PLUGIN_MAP, 0).getString(str.toUpperCase(Locale.ENGLISH), null) : string;
        }
        return null;
    }

    public Set<String> getSupportPackageSet() {
        syncSupportPackageSet();
        HashSet<String> hashSet = this.supportPackageSet;
        if (hashSet == null) {
            return null;
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public String getSwitchGearIcon(String str) {
        GearInfo gearInfo;
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        if (!isDeviceInfoAvailable() || !isValidDevice(simpleBTNameByName) || (gearInfo = getGearInfo(simpleBTNameByName)) == null) {
            return null;
        }
        String str2 = gearInfo.switchGearTitleIcon;
        return str2 != null ? str2 : gearInfo.group.switchGearTitleIcon;
    }

    public boolean isContainerPackage(String str) {
        syncContainerPackageSet();
        HashSet<String> hashSet = this.containerPackageSet;
        return hashSet != null && hashSet.contains(str);
    }

    public boolean isDeviceInfoAvailable() {
        HashMap<String, GearInfo> hashMap = this.gearInfoMap;
        return hashMap != null && hashMap.size() > 0;
    }

    public boolean isMultiConnectionDevice(String str) {
        boolean z;
        String str2 = TAG;
        Log.d(str2, "isMultiConnectionDevice = " + str);
        if (isDeviceInfoAvailable() && isValidDevice(str)) {
            GearInfo gearInfo = getGearInfo(str);
            if (gearInfo != null) {
                z = gearInfo.supportMultiConnection;
                String str3 = TAG;
                Log.d(str3, "isMultiConnectionDevice result: " + z);
                return z;
            }
            Log.e(TAG, "isMultiConnectionDevice, gear info is null");
        }
        z = false;
        String str32 = TAG;
        Log.d(str32, "isMultiConnectionDevice result: " + z);
        return z;
    }

    public boolean isPackageCanDisable(String str) {
        CommonPackageItem commonPackageItem;
        HashMap<String, CommonPackageItem> hashMap = this.commonPackageItemMap;
        if (hashMap == null || (commonPackageItem = hashMap.get(str)) == null) {
            return true;
        }
        return commonPackageItem.disablePackage;
    }

    public boolean isPossibleSamsungWearable(String str) {
        boolean z = false;
        if (str != null && this.mDiscoveryKeywords != null) {
            str = str.toLowerCase(Locale.ENGLISH);
            Iterator<String> it = this.mDiscoveryKeywords.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (str.contains(it.next().toLowerCase(Locale.ENGLISH))) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        String str2 = TAG;
        Log.d(str2, "isPossibleSamsungWearable() deviceName: " + str + " return: " + z);
        return z;
    }

    public boolean isSkipWearableDevice(String str) {
        boolean z;
        List<String> list;
        if (str != null && (list = this.mSkipDevices) != null) {
            Iterator<String> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (str.equalsIgnoreCase(it.next())) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        z = false;
        String str2 = TAG;
        Log.d(str2, "isSkipWearableDevice() deviceName: " + str + " return: " + z);
        return z;
    }

    public boolean isSupportTablet(String str) {
        String str2 = TAG;
        Log.d(str2, "isSupportTablet = " + str);
        boolean z = (!isDeviceInfoAvailable() || !isValidDevice(str)) ? false : getGearInfo(str).supportTablet;
        String str3 = TAG;
        Log.d(str3, "isSupportTablet result: " + z);
        return z;
    }

    public boolean isValidDevice(String str) {
        String str2 = TAG;
        Log.d(str2, " isValidDevice() deviceName :>>" + str + "<<");
        if (str == null || this.gearInfoMap == null || TextUtils.isEmpty(str)) {
            return false;
        }
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        String str3 = TAG;
        Log.d(str3, " isValidDevice() simpleDeviceName :>>" + simpleBTNameByName);
        boolean containsKey = this.gearInfoMap.containsKey(simpleBTNameByName.toUpperCase(Locale.ENGLISH));
        String str4 = TAG;
        Log.d(str4, "isValidDevice() containsDeviceInfo:" + containsKey);
        return containsKey;
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean processRulesFile(String str) {
        boolean z;
        HashSet<String> hashSet;
        String str2;
        String str3 = TAG;
        Log.d(str3, "processRulesFile()  xmlVersion: " + str);
        if (getRulesParser() != null) {
            List<ModuleInfo> allModuleInfo = getRulesParser().getAllModuleInfo();
            String str4 = TAG;
            Log.d(str4, " moduleInfoList: " + allModuleInfo);
            if (allModuleInfo != null && allModuleInfo.size() > 0) {
                this.gearInfoMap = new HashMap<>(10);
                this.supportPackageSet = new HashSet<>(10);
                this.containerPackageSet = new HashSet<>(10);
                this.additionalPackageInfoMap = new HashMap<>(10);
                this.groupGearSetMap = new HashMap<>();
                for (ModuleInfo moduleInfo : allModuleInfo) {
                    if (moduleInfo.containerPackage != null) {
                        this.containerPackageSet.add(moduleInfo.containerPackage);
                        hashSet = this.supportPackageSet;
                        str2 = moduleInfo.containerPackage;
                    } else {
                        hashSet = this.supportPackageSet;
                        str2 = moduleInfo.pluginPackage;
                    }
                    hashSet.add(str2);
                    List additionalPackageList = moduleInfo.getAdditionalPackageList();
                    List<DeviceItem> deviceList = moduleInfo.getDeviceList();
                    if (deviceList != null && deviceList.size() > 0) {
                        for (DeviceItem deviceItem : deviceList) {
                            GearInfo gearInfo = new GearInfo(deviceItem.deviceName, moduleInfo.containerPackage, moduleInfo.pluginPackage);
                            gearInfo.pluginAppName = moduleInfo.pluginAppName;
                            gearInfo.supportMultiConnection = deviceItem.supportMultiConnection;
                            gearInfo.connectAsAudio = deviceItem.connectAsAudio;
                            gearInfo.supportNonSamsung = deviceItem.supportNonSamsung;
                            gearInfo.supportTablet = deviceItem.supportTablet;
                            gearInfo.hostMinMemory = deviceItem.hostMinMemory;
                            gearInfo.iconDrawableName = deviceItem.iconDrawableName;
                            gearInfo.pairingImageName = deviceItem.pairingImageName;
                            gearInfo.viClass = moduleInfo.viClass;
                            gearInfo.requiresPairing = deviceItem.requiresPairing;
                            gearInfo.supportsBLEOnly = deviceItem.supportsBLEOnly;
                            gearInfo.group = deviceItem.gearGroup;
                            gearInfo.switchGearTitleIcon = deviceItem.switchGearTitleIcon;
                            if (this.groupGearSetMap.containsKey(deviceItem.gearGroup.name.toUpperCase(Locale.ENGLISH))) {
                                this.groupGearSetMap.get(deviceItem.gearGroup.name.toUpperCase(Locale.ENGLISH)).add(deviceItem.deviceName.toUpperCase(Locale.ENGLISH));
                            } else {
                                HashSet hashSet2 = new HashSet();
                                hashSet2.add(deviceItem.deviceName.toUpperCase(Locale.ENGLISH));
                                this.groupGearSetMap.put(deviceItem.gearGroup.name.toUpperCase(Locale.ENGLISH), hashSet2);
                                String str5 = TAG;
                                Log.d(str5, " groupGearSetMap.put(" + deviceItem.gearGroup.name.toUpperCase(Locale.ENGLISH) + " , " + hashSet2);
                            }
                            this.gearInfoMap.put(deviceItem.deviceName.trim().toUpperCase(Locale.ENGLISH), gearInfo);
                            if (additionalPackageList != null) {
                                this.additionalPackageInfoMap.put(deviceItem.deviceName.trim().toUpperCase(Locale.ENGLISH), (ArrayList) additionalPackageList);
                            }
                        }
                    }
                }
                CommonInfo commonInfo = getRulesParser().getCommonInfo();
                if (commonInfo != null) {
                    List<CommonPackageItem> commonPackageItemList = commonInfo.getCommonPackageItemList();
                    this.mDiscoveryKeywords = commonInfo.getDiscoveryKeywords();
                    this.mSkipDevices = commonInfo.getSkipDevices();
                    if (commonPackageItemList != null && commonPackageItemList.size() > 0) {
                        this.commonPackageItemMap = new HashMap<>();
                        for (CommonPackageItem commonPackageItem : commonPackageItemList) {
                            this.commonPackageItemMap.put(commonPackageItem.packageName, commonPackageItem);
                        }
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    boolean savePreferences = savePreferences(str);
                    String str6 = TAG;
                    Log.d(str6, "processRulesFile() save this version :" + str + ", save result :" + savePreferences);
                }
                z = true;
            }
        }
        z = false;
        return z;
    }

    public boolean requestDisconnectAlways(String str) {
        String simpleBTNameByName = HostManagerUtilsRulesBTDevices.getSimpleBTNameByName(str);
        boolean z = (!isDeviceInfoAvailable() || !isValidDevice(simpleBTNameByName)) ? false : getGearInfo(simpleBTNameByName).group.requestDisconnectAlways;
        String str2 = TAG;
        Log.d(str2, "requestDisconnectAlways() deviceName:" + str + " return:" + z);
        return z;
    }

    public void setSyncCallback(ISyncCallback iSyncCallback) {
        this.mISyncCallback = iSyncCallback;
    }

    public synchronized void syncGearInfo(ISyncCallback iSyncCallback) {
        String str = TAG;
        Log.v(str, "syncDeviceInfo ()  syncCallback:" + iSyncCallback);
        this.mISyncCallback = iSyncCallback;
        if (isDeviceInfoAvailable()) {
            onSyncComplete(true);
        } else {
            if (this.syncHandlerThread == null) {
                this.syncHandlerThread = new HandlerThread("SYNC_THREAD", 5);
                this.syncHandlerThread.start();
                this.syncHandler = new Handler(this.syncHandlerThread.getLooper());
            }
            this.syncHandler.post(new Runnable() {
                /* class com.samsung.android.app.twatchmanager.manager.GearRulesManager.AnonymousClass1 */

                public void run() {
                    GearRulesManager.this.onSyncComplete(GearRulesManager.this.startSyncRules());
                }
            });
        }
    }

    public boolean syncGearInfoSynchronously() {
        if (isDeviceInfoAvailable()) {
            return true;
        }
        return startSyncRules();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0045 A[SYNTHETIC, Splitter:B:26:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateRulesCache(java.io.InputStream r5) {
        /*
            r4 = this;
            java.lang.String r0 = com.samsung.android.app.twatchmanager.manager.GearRulesManager.TAG
            java.lang.String r1 = "updateRulesCache() writing to local rules.xml"
            com.samsung.android.app.twatchmanager.log.Log.d(r0, r1)
            java.lang.String r0 = getLocalRulesFilePath()
            if (r0 == 0) goto L_0x004e
            r1 = 0
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0034 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0034 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0034 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0034 }
            r0 = 32768(0x8000, float:4.5918E-41)
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x002f, all -> 0x002c }
        L_0x001d:
            int r1 = r5.read(r0)     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            if (r1 <= 0) goto L_0x0028
            r3 = 0
            r2.write(r0, r3, r1)     // Catch:{ IOException -> 0x002f, all -> 0x002c }
            goto L_0x001d
        L_0x0028:
            r2.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x004e
        L_0x002c:
            r5 = move-exception
            r1 = r2
            goto L_0x0043
        L_0x002f:
            r5 = move-exception
            r1 = r2
            goto L_0x0035
        L_0x0032:
            r5 = move-exception
            goto L_0x0043
        L_0x0034:
            r5 = move-exception
        L_0x0035:
            r5.printStackTrace()     // Catch:{ all -> 0x0032 }
            if (r1 == 0) goto L_0x004e
            r1.close()
            goto L_0x004e
        L_0x003e:
            r5 = move-exception
            r5.printStackTrace()
            goto L_0x004e
        L_0x0043:
            if (r1 == 0) goto L_0x004d
            r1.close()     // Catch:{ IOException -> 0x0049 }
            goto L_0x004d
        L_0x0049:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004d:
            throw r5
        L_0x004e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.manager.GearRulesManager.updateRulesCache(java.io.InputStream):void");
    }

    public String viClass(String str) {
        GearInfo gearInfo;
        String str2 = TAG;
        Log.d(str2, "viClass = " + str);
        String str3 = null;
        if (isDeviceInfoAvailable() && isValidDevice(str) && (gearInfo = getGearInfo(str)) != null) {
            str3 = gearInfo.viClass;
        }
        String str4 = TAG;
        Log.d(str4, "viClass result: " + str3);
        return str3;
    }
}
