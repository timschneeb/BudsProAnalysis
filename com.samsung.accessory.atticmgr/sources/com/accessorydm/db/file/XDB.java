package com.accessorydm.db.file;

import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.XDMServiceManager;
import com.accessorydm.adapter.XDMTargetAdapter;
import com.accessorydm.agent.XDMAgent;
import com.accessorydm.db.sql.XDMAccessoryDbSqlQuery;
import com.accessorydm.db.sql.XDMDbSqlQuery;
import com.accessorydm.db.sql.XDMLastUpdateDbSqlQuery;
import com.accessorydm.db.sql.XDMRegistrationDbSqlQuery;
import com.accessorydm.eng.core.XDMAuth;
import com.accessorydm.eng.core.XDMBase64;
import com.accessorydm.eng.core.XDMMsg;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.accessorydm.interfaces.XEventInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XUICInterface;
import com.accessorydm.tp.XTPAdapter;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.android.fotaagent.polling.PollingInfo;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.type.DeviceType;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Locale;

public class XDB implements Serializable, XDMInterface, XDBInterface, XFOTAInterface, XUICInterface {
    private static final int FFS_OWNER_SYNCML = 240;
    public static final int PROFILELISTMAGIC = 3783;
    public static final int PROFILEMAGIC = 2355;
    private static XDBFileParam[] XDMFileParam = null;
    public static final String XDM_FFS_DELTA_FILE_EXTENTION_ = "update.zip";
    public static final String XDM_FFS_FILE_EXTENTION = ".cfg";
    public static XDBAdapter dbadapter = null;
    private static final long serialVersionUID = 1;

    /* access modifiers changed from: package-private */
    public enum XDMFileParameter {
        FileObjectTreeInfo(0, 256),
        FileObjectData(1, InputDeviceCompat.SOURCE_KEYBOARD),
        FileFirmwareData(2, 258),
        FileLargeObjectData(3, 259),
        FileBootstrapWbxml(4, 260),
        FileTndsXmlData(5, 261),
        FileMax(6, 262);
        
        private final int nFileId;
        private final int nIndex;

        private XDMFileParameter(int i, int i2) {
            this.nIndex = i;
            this.nFileId = i2;
        }

        /* access modifiers changed from: package-private */
        public int Index() {
            return this.nIndex;
        }

        /* access modifiers changed from: package-private */
        public int FileId() {
            return this.nFileId;
        }
    }

    public static boolean xdbInit() {
        try {
            XDMFileParameter[] values = XDMFileParameter.values();
            XDMFileParam = new XDBFileParam[XDMFileParameter.FileMax.Index()];
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                XDMFileParameter xDMFileParameter = values[i];
                if (xDMFileParameter.Index() == XDMFileParameter.FileMax.Index()) {
                    break;
                }
                XDMFileParam[xDMFileParameter.Index()] = new XDBFileParam();
                XDMFileParam[xDMFileParameter.Index()].FileID = xDMFileParameter.FileId();
                i++;
            }
            dbadapter = new XDBAdapter();
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static int xdbAppendFile(int i, byte[] bArr) {
        int i2;
        Exception e;
        try {
            i2 = xdbFileOpen(i);
            if (i2 != 0) {
                try {
                    i2 = xdbAdpAppFileCreate(i, bArr);
                    if (i2 != 0) {
                        Log.E("Create FAILED");
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.E(e.toString());
                    return i2;
                }
            } else {
                i2 = xdbAdpAppendFileWrite(i, bArr);
                if (i2 != 0) {
                    Log.E("Append FAILED");
                }
            }
        } catch (Exception e3) {
            e = e3;
            i2 = 0;
            Log.E(e.toString());
            return i2;
        }
        return i2;
    }

    private static int xdbFileOpen(int i) {
        if (i <= 0) {
            return 5;
        }
        try {
            try {
                new DataInputStream(new FileInputStream(xdbFileGetNameFromCallerID(i))).close();
                return 0;
            } catch (Exception e) {
                Log.E(e.toString());
                return 0;
            }
        } catch (FileNotFoundException e2) {
            Log.E(e2.toString());
            return 3;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x004f A[SYNTHETIC, Splitter:B:31:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x006e A[SYNTHETIC, Splitter:B:41:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x008e A[SYNTHETIC, Splitter:B:51:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x009c A[SYNTHETIC, Splitter:B:56:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int xdbAdpAppendFileWrite(int r4, byte[] r5) {
        /*
        // Method dump skipped, instructions count: 171
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.file.XDB.xdbAdpAppendFileWrite(int, byte[]):int");
    }

    public static long xdbGetFileSize(int i) {
        if (i <= 0) {
            return -1;
        }
        try {
            return dbadapter.xdbFileGetSize(xdbFileGetNameFromCallerID(i));
        } catch (Exception e) {
            Log.E(e.toString());
            return 0;
        }
    }

    public static int xdbDeleteFile(int i) {
        return xdbAdpFileDelete(null, i);
    }

    public static boolean xdbDeleteFile(String str) {
        return XDBAdapter.xdbFileDelete(str);
    }

    public static void xdbFullResetAll() {
        xdbFullResetFFS();
        xdbInit();
        XTPAdapter.xtpAdpSetIsConnected(false);
        XDMAgent.xdmAgentSetSyncMode(0);
        XDBFumoAdp.xdbSetUiMode(0);
        XDMAgent.xdmAgentTpSetRetryCount(0);
        xdbAdpDeltaAllClear();
        XDMDbSqlQuery.xdmDbFullReset();
        xdmDbInit();
        XDMServiceManager.getInstance().xdmStopService();
    }

    public static void xdbFullResetFFS() {
        int Index = XDMFileParameter.FileMax.Index();
        if (XDMFileParam != null) {
            for (int i = 0; i < Index; i++) {
                XDBFileParam xDBFileParam = XDMFileParam[i];
                if (xDBFileParam != null && xdbAdpFileExists(null, xDBFileParam.FileID) == 0 && xdbAdpFileDelete(null, xDBFileParam.FileID) == 0) {
                    xDBFileParam.nSize = 0;
                }
            }
        }
    }

    public static boolean xdmVerifyCheckDBTable() {
        Log.I("");
        try {
            if (TextUtils.isEmpty(XDBProfileAdp.xdbGetDeviceId())) {
                for (int i = 0; i < DBTableList.length; i++) {
                    if (!XDMDbSqlQuery.xdmdbsqlGetExistsTable(DBTableList[i])) {
                        Log.I("[ Not Exist Table : " + DBTableList[i] + " ] & DB Reset");
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static boolean xdmDbInit() {
        Log.I("xdmDbInit");
        if (!XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlExistsRow(1) && XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlInsertRow(new XDBAccessoryInfo()) < 0) {
            return false;
        }
        if (!XDMRegistrationDbSqlQuery.existRegistrationInfo(1) && XDMRegistrationDbSqlQuery.insertRegistrationInfo(new XDBRegistrationInfo()) < 0) {
            return false;
        }
        if (!XDMLastUpdateDbSqlQuery.existLastUpdateInfo(1) && XDMLastUpdateDbSqlQuery.insertLastUpdateInfo(new XDBLastUpdateInfo()) < 0) {
            return false;
        }
        int i = 0;
        while (i < 2) {
            int i2 = i + 1;
            if (!XDMDbSqlQuery.xdmDbExistsProfileRow((long) i2) && XDMDbSqlQuery.xdmDbInsertProfileRow(xdbInitProfileInfo(i + PROFILEMAGIC)) < 0) {
                return false;
            }
            i = i2;
        }
        if (!XDMDbSqlQuery.xdmDbExistsProfileListRow(1) && XDMDbSqlQuery.xdmDbInsertProfileListRow(xdbInitProfileListInfo(PROFILELISTMAGIC)) < 0) {
            return false;
        }
        if (!XDMDbSqlQuery.xdmDbExistsFUMORow(1) && XDMDbSqlQuery.xdmDbInsertFUMORow(xdbInitDMFUMOInfo()) < 0) {
            return false;
        }
        if (!XDMDbSqlQuery.xdmDbExistsPostPoneRow(1) && XDMDbSqlQuery.xdmDbInsertPostPoneRow(new XDBPostPoneInfo()) < 0) {
            return false;
        }
        if (!XDMDbSqlQuery.xdmDbExistsSimInfoRow(1) && XDMDbSqlQuery.xdmDbInsertSimInfoRow(new XDBSimInfo()) < 0) {
            return false;
        }
        if (!XDMDbSqlQuery.xdmDbExistsPollingRow(1) && XDMDbSqlQuery.xdmDbInsertPollingRow(new PollingInfo()) < 0) {
            return false;
        }
        if (!XDMDbSqlQuery.xdmDbExistsResyncModeRow(1) && XDMDbSqlQuery.xdmDbInsertResyncModeRow(new XDBResyncModeInfo()) < 0) {
            return false;
        }
        if (!XDMDbSqlQuery.xdmDbSqlAgentInfoExistsRow(1) && XDMDbSqlQuery.xdmDbSqlAgentInfoInsertRow(new XDBAgentInfo()) < 0) {
            return false;
        }
        Log.I("xdmDbInit Finish");
        return true;
    }

    public static String xdbFileGetNameFromCallerID(int i) {
        if (i == XDMFileParameter.FileFirmwareData.FileId()) {
            return String.format(Locale.US, "%s/%s/%s", XDMTargetAdapter.FOTA_DIR_PATH, XDMTargetAdapter.AFOTA_DIR_PATH, XDM_FFS_DELTA_FILE_EXTENTION_);
        }
        long j = (long) (i + 2400000);
        return String.format(Locale.US, "%s%d%s", XDMDmUtils.getInstance().xdmGetAccessorydmPath(), Long.valueOf(j), XDM_FFS_FILE_EXTENTION);
    }

    public static int xdbAdpAppFileCreate(int i, byte[] bArr) {
        return !dbadapter.xdbFileCreateWrite(xdbFileGetNameFromCallerID(i), bArr) ? 5 : 0;
    }

    public static int xdbAdpFileExists(String str, int i) {
        if (i > 0) {
            str = xdbFileGetNameFromCallerID(i);
        } else if (TextUtils.isEmpty(str)) {
            return 2;
        }
        return dbadapter.xdbFileExists(str);
    }

    public static boolean xdbAdpDeltaDelete(String str) {
        return XDBAdapter.xdbFileDelete(str);
    }

    public static void xdbAdpDeltaAllClear() {
        Log.I("");
        if (!TextUtils.isEmpty(XDMTargetAdapter.FOTA_DIR_PATH)) {
            xdbAdpDeltaDelete(String.format(Locale.US, "%s/%s/%s", XDMTargetAdapter.FOTA_DIR_PATH, XDMTargetAdapter.AFOTA_DIR_PATH, XDM_FFS_DELTA_FILE_EXTENTION_));
        }
    }

    public static int xdbAdpFileDelete(String str, int i) {
        int i2;
        if (i > 0) {
            str = xdbFileGetNameFromCallerID(i);
        } else if (TextUtils.isEmpty(str)) {
            Log.E("pszFileName is NULL");
            return 2;
        }
        try {
            i2 = dbadapter.xdbFileRemove(str);
        } catch (Exception e) {
            Log.E(e.toString());
            i2 = 0;
        }
        Log.I("xdbAdpFileDelete Result : " + i2);
        return i2;
    }

    public static boolean xdbAdpDeltaFolderCreate() {
        return XDBAdapter.xdbFolderCreate(XDMTargetAdapter.FOTA_DIR_PATH.concat("/").concat(XDMTargetAdapter.AFOTA_DIR_PATH));
    }

    public static boolean xdbReadFile(int i, int i2, int i3, byte[] bArr) {
        try {
            return dbadapter.xdbFileRead(xdbFileGetNameFromCallerID(i), bArr, i2, i3);
        } catch (Exception e) {
            Log.E(e.toString());
            return false;
        }
    }

    public static Object xdbReadFile(int i, int i2, int i3) {
        byte[] bArr = new byte[i3];
        try {
            dbadapter.xdbFileRead(xdbFileGetNameFromCallerID(i), bArr, i2, i3);
        } catch (Exception e) {
            Log.E(e.toString());
        }
        return bArr;
    }

    public static boolean xdbWriteFile(int i, int i2, Object obj) {
        return XDBAdapter.xdbFileWrite(xdbFileGetNameFromCallerID(i), i2, obj);
    }

    public static int xdbGetFileIdObjectData() {
        return XDMFileParameter.FileObjectData.FileId();
    }

    public static int xdbGetFileIdObjectTreeInfo() {
        return XDMFileParameter.FileObjectTreeInfo.FileId();
    }

    public static int xdbGetFileIdFirmwareData() {
        return XDMFileParameter.FileFirmwareData.FileId();
    }

    public static int xdbGetFileIdLargeObjectData() {
        return XDMFileParameter.FileLargeObjectData.FileId();
    }

    public static int xdbGetFileIdBootstrap() {
        return XDMFileParameter.FileBootstrapWbxml.FileId();
    }

    public static int xdbGetFileIdTNDS() {
        return XDMFileParameter.FileTndsXmlData.FileId();
    }

    public static String xdbGetServerUrl(int i) {
        String str;
        if (i != 1) {
            str = XDBProfileAdp.xdbGetServerUrl();
        } else {
            XDBFumoInfo xdbGetFumoInfo = XDBFumoAdp.xdbGetFumoInfo();
            if (xdbGetFumoInfo == null) {
                str = "";
            } else if (xdbGetFumoInfo.nStatus == 40 || xdbGetFumoInfo.nStatus == 20 || xdbGetFumoInfo.nStatus == 230) {
                str = xdbGetFumoInfo.m_szStatusNotifyUrl;
            } else if (xdbGetFumoInfo.nStatus == 10) {
                str = xdbGetFumoInfo.ServerUrl;
            } else {
                str = xdbGetFumoInfo.m_szObjectDownloadUrl;
            }
        }
        Log.H("xdbGetServerUrl : " + str);
        return str;
    }

    public static String xdbGetServerAddress(int i) {
        if (i != 1) {
            return XDBProfileAdp.xdbGetServerAddress();
        }
        XDBFumoInfo xdbGetFumoInfo = XDBFumoAdp.xdbGetFumoInfo();
        if (xdbGetFumoInfo == null) {
            return "";
        }
        if (xdbGetFumoInfo.nStatus == 40 || xdbGetFumoInfo.nStatus == 20 || xdbGetFumoInfo.nStatus == 230) {
            return xdbGetFumoInfo.m_szStatusNotifyIP;
        }
        if (xdbGetFumoInfo.nStatus == 10) {
            return xdbGetFumoInfo.ServerIP;
        }
        return xdbGetFumoInfo.m_szObjectDownloadIP;
    }

    public static int xdbGetServerPort(int i) {
        if (i != 1) {
            return XDBProfileAdp.xdbGetServerPort();
        }
        XDBFumoInfo xdbGetFumoInfo = XDBFumoAdp.xdbGetFumoInfo();
        if (xdbGetFumoInfo == null) {
            return 0;
        }
        if (xdbGetFumoInfo.nStatus == 40 || xdbGetFumoInfo.nStatus == 20 || xdbGetFumoInfo.nStatus == 230) {
            return xdbGetFumoInfo.nStatusNotifyPort;
        }
        if (xdbGetFumoInfo.nStatus == 10) {
            return xdbGetFumoInfo.ServerPort;
        }
        return xdbGetFumoInfo.nObjectDownloadPort;
    }

    public static boolean xdbCheckProfileListExist() {
        XDBProfileListInfo xdbGetProfileList = XDBProfileListAdp.xdbGetProfileList();
        if (xdbGetProfileList == null) {
            return false;
        }
        for (int i = 0; i < 2; i++) {
            if (!TextUtils.isEmpty(xdbGetProfileList.ProfileName[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean xdbGetWifiOnlyFlag() {
        boolean isWifiOnlySettings = DeviceType.get().isWifiOnlySettings(XDMDmUtils.getContext());
        Log.I("Wifi_Only_flag : " + isWifiOnlySettings);
        return isWifiOnlySettings;
    }

    public static boolean xdbGetWifiAutoDownloadFlag() {
        boolean isWifiAutoDownloadSettings = DeviceType.get().isWifiAutoDownloadSettings(XDMDmUtils.getContext());
        Log.I("Wifi_Auto_Download_flag : " + isWifiAutoDownloadSettings);
        return isWifiAutoDownloadSettings;
    }

    public static int xdbSetActiveProfileIndexByServerID(String str) {
        Log.I("");
        if (TextUtils.isEmpty(str)) {
            Log.E("ServerID is NULL");
            return 0;
        }
        int xdbGetProfileIndex = XDBProfileListAdp.xdbGetProfileIndex();
        if (str.equals(XDBProfileAdp.xdbGetServerID())) {
            return xdbGetProfileIndex;
        }
        for (int i = 0; i < 2; i++) {
            XDBProfileInfo xdbGetProfileInfo = XDBProfileAdp.xdbGetProfileInfo(i);
            if (xdbGetProfileInfo != null && xdbGetProfileInfo.ServerID.equals(str)) {
                XDBProfileListAdp.xdbSetProfileIndex(i);
                return i;
            }
        }
        return xdbGetProfileIndex;
    }

    public static boolean xdbCheckActiveProfileIndexByServerID(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.E("ServerID is NULL");
            return false;
        } else if (str.equals(XDBProfileAdp.xdbGetServerID())) {
            return true;
        } else {
            for (int i = 0; i < 2; i++) {
                XDBProfileInfo xdbGetProfileInfo = XDBProfileAdp.xdbGetProfileInfo(i);
                if (xdbGetProfileInfo != null && xdbGetProfileInfo.ServerID.equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static String xdbGetNotiDigest(String str, int i, byte[] bArr, int i2) {
        if (TextUtils.isEmpty(str)) {
            Log.E("pServerID is NULL");
            return null;
        }
        int xdbSetActiveProfileIndexByServerID = xdbSetActiveProfileIndexByServerID(str);
        try {
            XDBProfileInfo xdbGetProfileInfo = XDBProfileAdp.xdbGetProfileInfo(xdbSetActiveProfileIndexByServerID);
            String str2 = xdbGetProfileInfo.ServerID;
            String str3 = xdbGetProfileInfo.ServerPwd;
            String str4 = xdbGetProfileInfo.ServerNonce;
            if (TextUtils.isEmpty(str4)) {
                return null;
            }
            Log.H("nActive = " + xdbSetActiveProfileIndexByServerID);
            Log.H("szServerNonce = " + str4);
            byte[] xdmBase64Decode = XDMBase64.xdmBase64Decode(str4);
            return XDMAuth.xdmAuthMakeDigest(i, str2, str3, xdmBase64Decode, xdmBase64Decode.length, bArr, (long) i2);
        } catch (Exception e) {
            Log.E(e.toString());
            return null;
        }
    }

    private static XDBProfileInfo xdbInitProfileInfo(int i) {
        return XDBFactoryBootstrap.xdbFBGetFactoryBootstrapData(i - 2355);
    }

    private static XDBProfileListInfo xdbInitProfileListInfo(int i) {
        XDBProfileListInfo xDBProfileListInfo = new XDBProfileListInfo();
        for (int i2 = 0; i2 < 2; i2++) {
            try {
                String xdbGetProfileName = XDBProfileAdp.xdbGetProfileName(i2);
                if (TextUtils.isEmpty(xdbGetProfileName)) {
                    xdbGetProfileName = XDBProfileAdp.xdbGetServerID();
                    if (TextUtils.isEmpty(xdbGetProfileName)) {
                        xdbGetProfileName = "";
                    }
                }
                xDBProfileListInfo.ProfileName[i2] = xdbGetProfileName;
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
        xDBProfileListInfo.Profileindex = 1;
        xDBProfileListInfo.MagicNumber = i;
        xDBProfileListInfo.m_szNetworkConnName = XDMInterface.XDM_CONNECTION_NAME;
        return xDBProfileListInfo;
    }

    private static XDBFumoInfo xdbInitDMFUMOInfo() {
        XDBFumoInfo xDBFumoInfo = new XDBFumoInfo();
        xDBFumoInfo.ServerUrl = "http://";
        xDBFumoInfo.ServerIP = "0.0.0.0";
        xDBFumoInfo.ResultCode = "";
        xDBFumoInfo.ServerPort = 80;
        xDBFumoInfo.nDownloadMode = true;
        xDBFumoInfo.m_szProtocol = HttpNetworkInterface.XTP_NETWORK_TYPE_HTTP;
        xDBFumoInfo.nUiMode = 0;
        return xDBFumoInfo;
    }

    public static XDBProfileInfo[] xdbGetReadProfileListInfo() {
        XDBProfileInfo[] xDBProfileInfoArr = new XDBProfileInfo[2];
        for (int i = 0; i < 2; i++) {
            try {
                xDBProfileInfoArr[i] = XDBProfileAdp.xdbGetProfileInfo(i);
            } catch (Exception e) {
                Log.E(e.toString());
            }
        }
        return xDBProfileInfoArr;
    }

    public static void xdbSqlFailAbort() {
        Log.I("");
        XDMMsg.xdmSendMessage(XEventInterface.XEVENT.XEVENT_DM_ABORT, XDMMsg.xdmCreateAbortMessage(250, false), null);
    }
}
