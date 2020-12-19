package com.samsung.android.app.twatchmanager.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.GroupInfo;
import com.samsung.android.app.twatchmanager.util.HandlerThreadUtils;
import com.samsung.android.app.twatchmanager.util.ResourceRulesParser;
import com.samsung.android.app.twatchmanager.util.UIUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class ResourceRulesManager {
    public static final int EF_SERVER_SYNC = 2;
    public static final int LOCAL_SYNC = 1;
    private static final String PREF_RESOURCE_INFO = "pref_resource_info";
    private static final String RULES_DIRECTORY = "rules";
    private static final String RULES_FILE_NAME = "resources.xml";
    private static final String RULES_XML_VERSION = "rules_xml_version";
    private static final int SERVER_SYNC_TIME_LIMIT = 5000;
    private static final String TAG = ("tUHM:" + ResourceRulesManager.class.getSimpleName());
    private static ResourceRulesManager mInstance = null;
    private boolean isSync;
    private Context mContext;
    private ISyncCallback mISyncCallback;
    LinkedHashMap<String, GroupInfo.ImageInfo>[] mImageMapArray;
    private ResourceRulesParser mParser;
    private Handler syncHandler;
    private HandlerThread syncHandlerThread;
    private int syncReqCount;

    public interface ISyncCallback {
        void onSyncComplete(int i, boolean z);
    }

    private ResourceRulesManager() {
        this.mImageMapArray = null;
        this.mParser = null;
        this.mContext = null;
        this.syncReqCount = 0;
        this.isSync = false;
        this.mContext = TWatchManagerApplication.getAppContext();
    }

    private InputStream getInputStreamFromAsset() {
        try {
            return this.mContext.getAssets().open(RULES_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized ResourceRulesManager getInstance() {
        ResourceRulesManager resourceRulesManager;
        synchronized (ResourceRulesManager.class) {
            if (mInstance == null) {
                mInstance = new ResourceRulesManager();
            }
            resourceRulesManager = mInstance;
        }
        return resourceRulesManager;
    }

    public static String getLocalRulesFilePath() {
        return TWatchManagerApplication.getAppContext().getFilesDir() + File.separator + "rules" + File.separator + RULES_FILE_NAME;
    }

    private String getXMLVersion(InputStream inputStream) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(PREF_RESOURCE_INFO, 0);
        if (sharedPreferences.contains(RULES_XML_VERSION)) {
            return sharedPreferences.getString(RULES_XML_VERSION, "0.0");
        }
        Log.d(TAG, "getXMLVersion() set default version from asset");
        String str = null;
        if (inputStream != null) {
            str = getRulesParser().getRulesXMLVersion(inputStream);
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(str)) {
            str = "0.0";
        }
        String str2 = TAG;
        Log.d(str2, "getXMLVersion() current version is " + str);
        return str;
    }

    private boolean isUpdateNeeded(String str, InputStream inputStream) {
        Log.d(TAG, "isUpdateNeeded() starts");
        String xMLVersion = getXMLVersion(inputStream);
        String str2 = TAG;
        Log.d(str2, "isUpdateNeeded() newVersion : " + str + " curVersion : " + xMLVersion);
        boolean z = false;
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("\\.");
            String[] split2 = xMLVersion.split("\\.");
            if (split.length == 2 && split2.length == 2) {
                try {
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split2[0]);
                    int parseInt3 = Integer.parseInt(split[1]);
                    int parseInt4 = Integer.parseInt(split2[1]);
                    String str3 = TAG;
                    Log.d(str3, "isUpdateNeeded() check Major version, newVersion : " + parseInt + " appVersion : " + 2);
                    if (2 == parseInt && (parseInt > parseInt2 || parseInt3 > parseInt4)) {
                        z = true;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        String str4 = TAG;
        Log.d(str4, "isUpdateNeeded() returns:" + z);
        return z;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void onSyncComplete(int i, boolean z) {
        Log.d(TAG, "onSyncComplete() starts, isSuccess : " + z);
        if (this.mISyncCallback != null) {
            this.mISyncCallback.onSyncComplete(i, z);
            this.mISyncCallback = null;
        }
        this.syncReqCount--;
        if (this.syncReqCount == 0 && this.syncHandler != null) {
            this.syncHandler.removeCallbacksAndMessages(null);
            this.syncHandler = null;
            HandlerThreadUtils.close(this.syncHandlerThread);
            this.syncHandlerThread = null;
        }
        if (z) {
            this.isSync = true;
        }
    }

    private boolean processRulesFile() {
        Log.d(TAG, "processRulesFile() starts");
        ArrayList<GroupInfo> allResourceInfo = getRulesParser().getAllResourceInfo();
        if (allResourceInfo == null || allResourceInfo.isEmpty()) {
            Log.d(TAG, "processRulesFile() there is no infoList, return false");
            return false;
        }
        initImageMapArray();
        return true;
    }

    private void savePreferences(String str) {
        String str2 = TAG;
        Log.d(str2, "savePreferences() xmlVersion:" + str);
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences(PREF_RESOURCE_INFO, 0).edit();
        edit.putString(RULES_XML_VERSION, str);
        edit.apply();
    }

    private void setImageBasedOnSWDP(ArrayList<GroupInfo.ImageInfo> arrayList, GroupInfo.InfoType infoType) {
        LinkedHashMap<String, GroupInfo.ImageInfo> linkedHashMap;
        String str;
        int i = this.mContext.getResources().getConfiguration().smallestScreenWidthDp;
        if (UIUtils.isLandScapeMode(this.mContext)) {
            i = -1;
        }
        Iterator<GroupInfo.ImageInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            GroupInfo.ImageInfo next = it.next();
            try {
                int parseInt = Integer.parseInt(next.attributes.get(GroupInfo.ImageInfo.ATTR_SWDP));
                if (parseInt == i) {
                    String str2 = TAG;
                    Log.d(str2, "setImageBasedOnSWDP() found image, swdp : " + parseInt + " platfromSWDP : " + i);
                    linkedHashMap = this.mImageMapArray[infoType.toValue()];
                    str = next.name;
                } else if (parseInt != -1 && parseInt < i) {
                    String str3 = TAG;
                    Log.d(str3, "setImageBasedOnSWDP() found image, swdp : " + parseInt + " platfromSWDP : " + i);
                    linkedHashMap = this.mImageMapArray[infoType.toValue()];
                    str = next.name;
                }
                linkedHashMap.put(str, next);
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void syncFromEFServer() {
        Log.d(TAG, "syncFromEFServer() is not used");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void syncFromLocal() {
        FileInputStream fileInputStream;
        String rulesXMLVersion;
        Log.d(TAG, "syncFromLocal() start the local sync");
        try {
            fileInputStream = new FileInputStream(getLocalRulesFilePath());
        } catch (IOException e) {
            e.printStackTrace();
            fileInputStream = null;
        }
        if (!ruleIsDownloaded()) {
            Log.d(TAG, "syncFromLocal() resource rule file doesn't exist in the download folder");
            updateRulesFromAsset();
            if (!(fileInputStream == null || (rulesXMLVersion = getRulesParser().getRulesXMLVersion(fileInputStream)) == null)) {
                savePreferences(rulesXMLVersion);
            }
        } else if (fileInputStream != null) {
            String rulesXMLVersion2 = getRulesParser().getRulesXMLVersion(getInputStreamFromAsset());
            if (isUpdateNeeded(rulesXMLVersion2, fileInputStream)) {
                Log.d(TAG, "syncFromLocal() downloaded resources.xml is older version than asset version");
                updateRulesFromAsset();
                savePreferences(rulesXMLVersion2);
            }
        }
        onSyncComplete(1, isResourceInfoAvailable() || processRulesFile());
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0081 A[SYNTHETIC, Splitter:B:40:0x0081] */
    /* JADX WARNING: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateRulesFromAsset() {
        /*
        // Method dump skipped, instructions count: 146
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.manager.ResourceRulesManager.updateRulesFromAsset():void");
    }

    public List<GroupInfo> getGearGroupInfo() {
        return getRulesParser().getAllResourceInfo();
    }

    public GroupInfo.ImageInfo getImageInfoByGroupName(GroupInfo.InfoType infoType, String str) {
        List<GroupInfo> gearGroupInfo = getGearGroupInfo();
        if (gearGroupInfo == null) {
            return null;
        }
        for (GroupInfo groupInfo : gearGroupInfo) {
            if (!TextUtils.isEmpty(str) && TextUtils.equals(groupInfo.getName(), str)) {
                return groupInfo.getGroupImageInfo(infoType);
            }
        }
        return null;
    }

    public GroupInfo.ImageInfo getImageInfoFromName(GroupInfo.InfoType infoType, String str) {
        LinkedHashMap<String, GroupInfo.ImageInfo>[] linkedHashMapArr = this.mImageMapArray;
        if (linkedHashMapArr != null) {
            return linkedHashMapArr[infoType.toValue()].get(str);
        }
        return null;
    }

    public ArrayList<GroupInfo.ImageInfo> getImageListByType(GroupInfo.InfoType infoType) {
        ArrayList<GroupInfo.ImageInfo> arrayList = new ArrayList<>();
        LinkedHashMap<String, GroupInfo.ImageInfo>[] linkedHashMapArr = this.mImageMapArray;
        if (linkedHashMapArr != null) {
            arrayList.addAll(linkedHashMapArr[infoType.toValue()].values());
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public ResourceRulesParser getRulesParser() {
        if (this.mParser == null) {
            this.mParser = new ResourceRulesParser(2);
        }
        return this.mParser;
    }

    public void initImageMapArray() {
        LinkedHashMap<String, GroupInfo.ImageInfo>[] linkedHashMapArr = this.mImageMapArray;
        int i = 0;
        if (linkedHashMapArr == null) {
            this.mImageMapArray = new LinkedHashMap[GroupInfo.InfoType.values().length];
            int length = this.mImageMapArray.length;
            while (i < length) {
                this.mImageMapArray[i] = new LinkedHashMap<>();
                i++;
            }
        } else {
            int length2 = linkedHashMapArr.length;
            while (i < length2) {
                linkedHashMapArr[i].clear();
                i++;
            }
        }
        Iterator<GroupInfo> it = this.mParser.getGroupInfoList().iterator();
        while (it.hasNext()) {
            GroupInfo next = it.next();
            for (String str : next.images.keySet()) {
                try {
                    GroupInfo.InfoType valueOf = GroupInfo.InfoType.valueOf(str.toUpperCase(Locale.ENGLISH));
                    ArrayList<GroupInfo.ImageInfo> arrayList = next.images.get(str);
                    String str2 = TAG;
                    Log.d(str2, "initImageMapArray() imgInfoArray : " + arrayList.size());
                    if (arrayList.size() == 1) {
                        GroupInfo.ImageInfo groupImageInfo = next.getGroupImageInfo(valueOf);
                        this.mImageMapArray[valueOf.toValue()].put(groupImageInfo.name, groupImageInfo);
                    } else if (arrayList.size() > 1) {
                        setImageBasedOnSWDP(arrayList, valueOf);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isResourceInfoAvailable() {
        boolean z = this.isSync && this.mImageMapArray != null;
        String str = TAG;
        Log.d(str, "isResourceInfoAvailable() result : " + z);
        return z;
    }

    public boolean ruleIsDownloaded() {
        return new File(getLocalRulesFilePath()).exists();
    }

    public synchronized void syncGearInfo(final int i, final ISyncCallback iSyncCallback) {
        Log.d(TAG, "syncGearInfo ()  syncType : " + i);
        if (this.syncHandlerThread == null) {
            Log.d(TAG, "syncGearInfo () init syncHandlerThread");
            this.syncHandlerThread = new HandlerThread("RESOURCE_SYNC_THREAD", 5);
            this.syncHandlerThread.start();
            this.syncHandler = new Handler(this.syncHandlerThread.getLooper());
        }
        this.syncReqCount++;
        Log.d(TAG, "syncGearInfo () post ... syncRequestCount : " + this.syncReqCount);
        this.syncHandler.post(new Runnable() {
            /* class com.samsung.android.app.twatchmanager.manager.ResourceRulesManager.AnonymousClass1 */

            public void run() {
                boolean isResourceInfoAvailable = ResourceRulesManager.this.isResourceInfoAvailable();
                String str = ResourceRulesManager.TAG;
                Log.d(str, "syncHandler.post() start to sync ... synced : " + isResourceInfoAvailable);
                ResourceRulesManager.this.mISyncCallback = iSyncCallback;
                if (isResourceInfoAvailable) {
                    ResourceRulesManager.this.onSyncComplete(i, true);
                    return;
                }
                int i = i;
                if (i == 1) {
                    ResourceRulesManager.this.syncFromLocal();
                } else if (i == 2) {
                    ResourceRulesManager.this.syncFromEFServer();
                } else {
                    Log.d(ResourceRulesManager.TAG, "syncGearInfo() unKnown sync type");
                }
            }
        });
        if (i == 2) {
            Log.d(TAG, "syncGearInfo() set a timeout handler for EF Server sync");
            this.syncHandler.postDelayed(new Runnable() {
                /* class com.samsung.android.app.twatchmanager.manager.ResourceRulesManager.AnonymousClass2 */

                public void run() {
                    Log.d(ResourceRulesManager.TAG, "syncGearInfo() timeout occured, trigger sync fail callback");
                    ResourceRulesManager.this.onSyncComplete(2, false);
                }
            }, 5000);
        }
    }
}
