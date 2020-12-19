package com.accessorydm.db.sql;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.accessorydm.db.XDMDbManager;
import com.accessorydm.db.file.XDBAESCrypt;
import com.accessorydm.db.file.XDBAccXNodeInfo;
import com.accessorydm.db.file.XDBAgentInfo;
import com.accessorydm.db.file.XDBFumoInfo;
import com.accessorydm.db.file.XDBPostPoneInfo;
import com.accessorydm.db.file.XDBProfileInfo;
import com.accessorydm.db.file.XDBProfileListInfo;
import com.accessorydm.db.file.XDBResyncModeInfo;
import com.accessorydm.db.file.XDBSimInfo;
import com.accessorydm.db.file.XDBUserDBException;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.android.fotaagent.polling.PollingInfo;
import com.samsung.android.fotaprovider.log.Log;

public class XDMDbSqlQuery implements XDBInterface {
    public static void xdmDbFullReset() {
        Log.I("xdmDbFullReset");
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS profile");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS network");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS profilelist");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS fumo");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS postpone");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS siminfo");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS polling");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS accxlistnode");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS resyncmode");
            xdmDbGetWritableDatabase.execSQL("DROP TABLE IF EXISTS DmAgnetInfo");
            XNOTIDbSqlQuery.xnotiDbSqlDrop(xdmDbGetWritableDatabase);
            XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlDrop(xdmDbGetWritableDatabase);
            XDMRegistrationDbSqlQuery.deleteDBRegistration(xdmDbGetWritableDatabase);
            XDMLastUpdateDbSqlQuery.deleteDBLastUpdate(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
        }
        try {
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_PROFILE_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_NETWORK_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_PROFILELIST_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_FUMO_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_POSTPONE_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_SIMINFO_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_POLLING_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_ACCXLISTNODE_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_RESYNCMODE_CREATE);
            xdmDbGetWritableDatabase.execSQL(XDBInterface.DATABASE_DM_AGENT_INFO_CREATE);
            XNOTIDbSqlQuery.xnotiDbSqlCreate(xdmDbGetWritableDatabase);
            XDMAccessoryDbSqlQuery.xdmAccessoryDbSqlCreate(xdmDbGetWritableDatabase);
            XDMRegistrationDbSqlQuery.createDBRegistration(xdmDbGetWritableDatabase);
            XDMLastUpdateDbSqlQuery.createDBLastUpdate(xdmDbGetWritableDatabase);
        } catch (SQLException e2) {
            Log.E(e2.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
    }

    public static long xdmDbInsertProfileRow(XDBProfileInfo xDBProfileInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put("protocol", xDBProfileInfo.Protocol);
            contentValues.put("serverport", Integer.valueOf(xDBProfileInfo.ServerPort));
            contentValues.put("serverurl", XDBAESCrypt.xdbEncryptorStrBase64(xDBProfileInfo.ServerUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put("serverip", xDBProfileInfo.ServerIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PATH, xDBProfileInfo.Path);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PROTOCOL_ORG, xDBProfileInfo.Protocol_Org);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERPORT_ORG, Integer.valueOf(xDBProfileInfo.ServerPort_Org));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERURL_ORG, XDBAESCrypt.xdbEncryptorStrBase64(xDBProfileInfo.ServerUrl_Org, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERIP_ORG, xDBProfileInfo.ServerIP_Org);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PATH_ORG, xDBProfileInfo.Path_Org);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_CHANGEDPROTOCOL, Boolean.valueOf(xDBProfileInfo.bChangedProtocol));
            contentValues.put("obextype", Integer.valueOf(xDBProfileInfo.ObexType));
            contentValues.put("authtype", Integer.valueOf(xDBProfileInfo.AuthType));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERAUTHTYPE, Integer.valueOf(xDBProfileInfo.nServerAuthType));
            contentValues.put("appid", xDBProfileInfo.AppID);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_AUTHLEVEL, xDBProfileInfo.AuthLevel);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERAUTHLEVEL, xDBProfileInfo.ServerAuthLevel);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PREFCONREF, xDBProfileInfo.PrefConRef);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_USERNAME, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.UserName, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PASSWORD, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.Password, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERID, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.ServerID, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERPWD, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.ServerPwd, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_CLIENTNONCE, xDBProfileInfo.ClientNonce);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERNONCE, xDBProfileInfo.ServerNonce);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERNONCEFORMAT, Integer.valueOf(xDBProfileInfo.ServerNonceFormat));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_CLIENTNONCEFORMAT, Integer.valueOf(xDBProfileInfo.ClientNonceFormat));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PROFILENAME, xDBProfileInfo.ProfileName);
            contentValues.put("networkconnname", xDBProfileInfo.NetworkConnName);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_NETWORKCONNINDEX, Integer.valueOf(xDBProfileInfo.nNetworkConnIndex));
            contentValues.put("magicnumber", Integer.valueOf(xDBProfileInfo.MagicNumber));
            j = xdmDbGetWritableDatabase.insert("profile", null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    public static void xdmDbUpdateProfileRow(long j, XDBProfileInfo xDBProfileInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put("protocol", xDBProfileInfo.Protocol);
            contentValues.put("serverport", Integer.valueOf(xDBProfileInfo.ServerPort));
            contentValues.put("serverurl", XDBAESCrypt.xdbEncryptorStrBase64(xDBProfileInfo.ServerUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put("serverip", xDBProfileInfo.ServerIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PATH, xDBProfileInfo.Path);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PROTOCOL_ORG, xDBProfileInfo.Protocol_Org);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERPORT_ORG, Integer.valueOf(xDBProfileInfo.ServerPort_Org));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERURL_ORG, XDBAESCrypt.xdbEncryptorStrBase64(xDBProfileInfo.ServerUrl_Org, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERIP_ORG, xDBProfileInfo.ServerIP_Org);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PATH_ORG, xDBProfileInfo.Path_Org);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_CHANGEDPROTOCOL, Boolean.valueOf(xDBProfileInfo.bChangedProtocol));
            contentValues.put("obextype", Integer.valueOf(xDBProfileInfo.ObexType));
            contentValues.put("authtype", Integer.valueOf(xDBProfileInfo.AuthType));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERAUTHTYPE, Integer.valueOf(xDBProfileInfo.nServerAuthType));
            contentValues.put("appid", xDBProfileInfo.AppID);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_AUTHLEVEL, xDBProfileInfo.AuthLevel);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERAUTHLEVEL, xDBProfileInfo.ServerAuthLevel);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PREFCONREF, xDBProfileInfo.PrefConRef);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_USERNAME, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.UserName, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PASSWORD, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.Password, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERID, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.ServerID, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERPWD, XDBAESCrypt.xdbEncryptor(xDBProfileInfo.ServerPwd, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_CLIENTNONCE, xDBProfileInfo.ClientNonce);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERNONCE, xDBProfileInfo.ServerNonce);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_SERVERNONCEFORMAT, Integer.valueOf(xDBProfileInfo.ServerNonceFormat));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_CLIENTNONCEFORMAT, Integer.valueOf(xDBProfileInfo.ClientNonceFormat));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_PROFILENAME, xDBProfileInfo.ProfileName);
            contentValues.put("networkconnname", xDBProfileInfo.NetworkConnName);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILE_NETWORKCONNINDEX, Integer.valueOf(xDBProfileInfo.nNetworkConnIndex));
            contentValues.put("magicnumber", Integer.valueOf(xDBProfileInfo.MagicNumber));
            xdmDbGetWritableDatabase.update("profile", contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x01ac, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x01ae, code lost:
        if (r4 != null) goto L_0x01b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x01b4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x01b5, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x01b9, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBProfileInfo xdmDbFetchProfileRow(long r42) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 462
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchProfileRow(long):com.accessorydm.db.file.XDBProfileInfo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009d, code lost:
        if (r3 != null) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a4, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a8, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsProfileRow(long r42) {
        /*
        // Method dump skipped, instructions count: 188
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsProfileRow(long):boolean");
    }

    public static long xdmDbInsertProfileListRow(XDBProfileListInfo xDBProfileListInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put("networkconnname", xDBProfileListInfo.m_szNetworkConnName);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROXYINDEX, Integer.valueOf(xDBProfileListInfo.nProxyIndex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROFILEINDEX, Integer.valueOf(xDBProfileListInfo.Profileindex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROFILENAME1, xDBProfileListInfo.ProfileName[0]);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROFILENAME2, xDBProfileListInfo.ProfileName[1]);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_SESSIONID, xDBProfileListInfo.m_szSessionID);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIEVENT, Integer.valueOf(xDBProfileListInfo.nNotiEvent));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIOPMODE, Integer.valueOf(xDBProfileListInfo.nNotiOpMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIJOBID, Integer.valueOf(xDBProfileListInfo.nNotiJobId));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_DESTORYNOTITIME, Long.valueOf(xDBProfileListInfo.nDestoryNotiTime));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESYNCMODE, Integer.valueOf(xDBProfileListInfo.nNotiReSyncMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_DDFPARSERNODEINDEX, Integer.valueOf(xDBProfileListInfo.nDDFParserNodeIndex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_SKIPDEVDISCOVERY, Boolean.valueOf(xDBProfileListInfo.bSkipDevDiscovery));
            contentValues.put("magicnumber", Integer.valueOf(xDBProfileListInfo.MagicNumber));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESUMESTATE_SESSIONSAVESTATE, Integer.valueOf(xDBProfileListInfo.NotiResumeState.nSessionSaveState));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESUMESTATE_NOTIUIEVENT, Integer.valueOf(xDBProfileListInfo.NotiResumeState.nNotiUiEvent));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESUMESTATE_NOTIRETRYCOUNT, Integer.valueOf(xDBProfileListInfo.NotiResumeState.nNotiRetryCount));
            contentValues.put("status", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.eStatus));
            contentValues.put("appid", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.appId));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_UICTYPE, Integer.valueOf(xDBProfileListInfo.tUicResultKeep.UICType));
            contentValues.put("result", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.result));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_NUMBER, Integer.valueOf(xDBProfileListInfo.tUicResultKeep.number));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_TEXT, xDBProfileListInfo.tUicResultKeep.m_szText);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_LEN, Integer.valueOf(xDBProfileListInfo.tUicResultKeep.nLen));
            contentValues.put("size", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.nSize));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_WIFIONLY, Boolean.valueOf(xDBProfileListInfo.bWifiOnly));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_AUTOUPDATE, Boolean.valueOf(xDBProfileListInfo.bAutoUpdate));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PUSHMESSAGE, Boolean.valueOf(xDBProfileListInfo.bPushMessage));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_SAVE_DELTAFILE_INDEX, Integer.valueOf(xDBProfileListInfo.nSaveDeltaFileIndex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_DEVICE_REGISTER, Integer.valueOf(xDBProfileListInfo.nDeviceRegister));
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_PROFILELIST_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    public static void xdmDbUpdateProfileListRow(long j, XDBProfileListInfo xDBProfileListInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put("networkconnname", xDBProfileListInfo.m_szNetworkConnName);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROXYINDEX, Integer.valueOf(xDBProfileListInfo.nProxyIndex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROFILEINDEX, Integer.valueOf(xDBProfileListInfo.Profileindex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROFILENAME1, xDBProfileListInfo.ProfileName[0]);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PROFILENAME2, xDBProfileListInfo.ProfileName[1]);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_SESSIONID, xDBProfileListInfo.m_szSessionID);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIEVENT, Integer.valueOf(xDBProfileListInfo.nNotiEvent));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIOPMODE, Integer.valueOf(xDBProfileListInfo.nNotiOpMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIJOBID, Integer.valueOf(xDBProfileListInfo.nNotiJobId));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_DESTORYNOTITIME, Long.valueOf(xDBProfileListInfo.nDestoryNotiTime));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESYNCMODE, Integer.valueOf(xDBProfileListInfo.nNotiReSyncMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_DDFPARSERNODEINDEX, Integer.valueOf(xDBProfileListInfo.nDDFParserNodeIndex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_SKIPDEVDISCOVERY, Boolean.valueOf(xDBProfileListInfo.bSkipDevDiscovery));
            contentValues.put("magicnumber", Integer.valueOf(xDBProfileListInfo.MagicNumber));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESUMESTATE_SESSIONSAVESTATE, Integer.valueOf(xDBProfileListInfo.NotiResumeState.nSessionSaveState));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESUMESTATE_NOTIUIEVENT, Integer.valueOf(xDBProfileListInfo.NotiResumeState.nNotiUiEvent));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_NOTIRESUMESTATE_NOTIRETRYCOUNT, Integer.valueOf(xDBProfileListInfo.NotiResumeState.nNotiRetryCount));
            contentValues.put("status", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.eStatus));
            contentValues.put("appid", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.appId));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_UICTYPE, Integer.valueOf(xDBProfileListInfo.tUicResultKeep.UICType));
            contentValues.put("result", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.result));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_NUMBER, Integer.valueOf(xDBProfileListInfo.tUicResultKeep.number));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_TEXT, xDBProfileListInfo.tUicResultKeep.m_szText);
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_LEN, Integer.valueOf(xDBProfileListInfo.tUicResultKeep.nLen));
            contentValues.put("size", Integer.valueOf(xDBProfileListInfo.tUicResultKeep.nSize));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_WIFIONLY, Boolean.valueOf(xDBProfileListInfo.bWifiOnly));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_AUTOUPDATE, Boolean.valueOf(xDBProfileListInfo.bAutoUpdate));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_PUSHMESSAGE, Boolean.valueOf(xDBProfileListInfo.bPushMessage));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_SAVE_DELTAFILE_INDEX, Integer.valueOf(xDBProfileListInfo.nSaveDeltaFileIndex));
            contentValues.put(XDBInterface.XDM_SQL_DB_PROFILELIST_DEVICE_REGISTER, Integer.valueOf(xDBProfileListInfo.nDeviceRegister));
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_PROFILELIST_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x01bc, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01be, code lost:
        if (r3 != null) goto L_0x01c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x01c4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01c5, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01c9, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBProfileListInfo xdmDbFetchProfileListRow(long r42) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 478
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchProfileListRow(long):com.accessorydm.db.file.XDBProfileListInfo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009d, code lost:
        if (r3 != null) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a4, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a8, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsProfileListRow(long r42) {
        /*
        // Method dump skipped, instructions count: 188
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsProfileListRow(long):boolean");
    }

    public static long xdmDbInsertFUMORow(XDBFumoInfo xDBFumoInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put("protocol", xDBFumoInfo.m_szProtocol);
            contentValues.put("obextype", Integer.valueOf(xDBFumoInfo.ObexType));
            contentValues.put("authtype", Integer.valueOf(xDBFumoInfo.AuthType));
            contentValues.put("serverport", Integer.valueOf(xDBFumoInfo.ServerPort));
            contentValues.put("serverurl", XDBAESCrypt.xdbEncryptorStrBase64(xDBFumoInfo.ServerUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put("serverip", xDBFumoInfo.ServerIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADPROTOCOL, xDBFumoInfo.m_szObjectDownloadProtocol);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADURL, XDBAESCrypt.xdbEncryptorStrBase64(xDBFumoInfo.m_szObjectDownloadUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADIP, xDBFumoInfo.m_szObjectDownloadIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADPORT, Integer.valueOf(xDBFumoInfo.nObjectDownloadPort));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYPROTOCOL, xDBFumoInfo.m_szStatusNotifyProtocol);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYURL, XDBAESCrypt.xdbEncryptorStrBase64(xDBFumoInfo.m_szStatusNotifyUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYIP, xDBFumoInfo.m_szStatusNotifyIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYPORT, Integer.valueOf(xDBFumoInfo.nStatusNotifyPort));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_REPORTURI, xDBFumoInfo.m_szReportURI);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTSIZE, Long.valueOf(xDBFumoInfo.nObjectSize));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_FFSWRITESIZE, Integer.valueOf(xDBFumoInfo.nFFSWriteSize));
            contentValues.put("status", Integer.valueOf(xDBFumoInfo.nStatus));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNODENAME, xDBFumoInfo.m_szStatusNodeName);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_RESULTCODE, xDBFumoInfo.ResultCode);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UPDATEMECHANISM, Integer.valueOf(xDBFumoInfo.nUpdateMechanism));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_DOWNLOADMODE, Boolean.valueOf(xDBFumoInfo.nDownloadMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CORRELATOR, xDBFumoInfo.Correlator);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CONTENTTYPE, xDBFumoInfo.m_szContentType);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_ACCEPTTYPE, xDBFumoInfo.m_szAcceptType);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UPDATEWAIT, Boolean.valueOf(xDBFumoInfo.bUpdateWait));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_INITIATED_TYPE, Integer.valueOf(xDBFumoInfo.nInitiatedType));
            contentValues.put("description", xDBFumoInfo.szDescription);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OPTIONALUPDATE, Boolean.valueOf(xDBFumoInfo.m_bOptionalUpdate));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OPTIONALCANCEL, Boolean.valueOf(xDBFumoInfo.m_bOptionalCancel));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_DOWNLOAD_RESULTCODE, xDBFumoInfo.szDownloadResultCode);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_WIFIONLYDOWNLOAD, Boolean.valueOf(xDBFumoInfo.m_bWifiOnlyDownload));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CHECKROOTING, Boolean.valueOf(xDBFumoInfo.m_bCheckRooting));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CURRENT_DOWNLOADMODE, Integer.valueOf(xDBFumoInfo.nCurrentDownloadMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_BIGDELTA_DOWNLOAD, Boolean.valueOf(xDBFumoInfo.m_bBigDeltaDownload));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_COPY_RETRY_COUNT, Integer.valueOf(xDBFumoInfo.nCopyRetryCount));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_NETCONNTYPE, xDBFumoInfo.szNetworkConnType);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_LOWBATTERY_RETRY_COUNT, Integer.valueOf(xDBFumoInfo.nLowBatteryRetryCount));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTHASH, xDBFumoInfo.szObjectHash);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UPDATE_FW, xDBFumoInfo.szUpdateFWVer);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UIMODE, Integer.valueOf(xDBFumoInfo.nUiMode));
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_FUMO_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:56:0x022c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x022e, code lost:
        if (r4 != null) goto L_0x0230;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0234, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0235, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0239, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBFumoInfo xdmDbFetchFUMORow(long r53) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 590
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchFUMORow(long):com.accessorydm.db.file.XDBFumoInfo");
    }

    public static void xdmDbUpdateFUMORow(long j, XDBFumoInfo xDBFumoInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put("protocol", xDBFumoInfo.m_szProtocol);
            contentValues.put("obextype", Integer.valueOf(xDBFumoInfo.ObexType));
            contentValues.put("authtype", Integer.valueOf(xDBFumoInfo.AuthType));
            contentValues.put("serverport", Integer.valueOf(xDBFumoInfo.ServerPort));
            contentValues.put("serverurl", XDBAESCrypt.xdbEncryptorStrBase64(xDBFumoInfo.ServerUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put("serverip", xDBFumoInfo.ServerIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADPROTOCOL, xDBFumoInfo.m_szObjectDownloadProtocol);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADURL, XDBAESCrypt.xdbEncryptorStrBase64(xDBFumoInfo.m_szObjectDownloadUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADIP, xDBFumoInfo.m_szObjectDownloadIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTDOWNLOADPORT, Integer.valueOf(xDBFumoInfo.nObjectDownloadPort));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYPROTOCOL, xDBFumoInfo.m_szStatusNotifyProtocol);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYURL, XDBAESCrypt.xdbEncryptorStrBase64(xDBFumoInfo.m_szStatusNotifyUrl, XDBAESCrypt.CRYPTO_SEED_PASSWORD));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYIP, xDBFumoInfo.m_szStatusNotifyIP);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNOTIFYPORT, Integer.valueOf(xDBFumoInfo.nStatusNotifyPort));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_REPORTURI, xDBFumoInfo.m_szReportURI);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTSIZE, Long.valueOf(xDBFumoInfo.nObjectSize));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_FFSWRITESIZE, Integer.valueOf(xDBFumoInfo.nFFSWriteSize));
            contentValues.put("status", Integer.valueOf(xDBFumoInfo.nStatus));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_STATUSNODENAME, xDBFumoInfo.m_szStatusNodeName);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_RESULTCODE, xDBFumoInfo.ResultCode);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UPDATEMECHANISM, Integer.valueOf(xDBFumoInfo.nUpdateMechanism));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_DOWNLOADMODE, Boolean.valueOf(xDBFumoInfo.nDownloadMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CORRELATOR, xDBFumoInfo.Correlator);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CONTENTTYPE, xDBFumoInfo.m_szContentType);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_ACCEPTTYPE, xDBFumoInfo.m_szAcceptType);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UPDATEWAIT, Boolean.valueOf(xDBFumoInfo.bUpdateWait));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_INITIATED_TYPE, Integer.valueOf(xDBFumoInfo.nInitiatedType));
            contentValues.put("description", xDBFumoInfo.szDescription);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OPTIONALUPDATE, Boolean.valueOf(xDBFumoInfo.m_bOptionalUpdate));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OPTIONALCANCEL, Boolean.valueOf(xDBFumoInfo.m_bOptionalCancel));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_DOWNLOAD_RESULTCODE, xDBFumoInfo.szDownloadResultCode);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_WIFIONLYDOWNLOAD, Boolean.valueOf(xDBFumoInfo.m_bWifiOnlyDownload));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CHECKROOTING, Boolean.valueOf(xDBFumoInfo.m_bCheckRooting));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_CURRENT_DOWNLOADMODE, Integer.valueOf(xDBFumoInfo.nCurrentDownloadMode));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_BIGDELTA_DOWNLOAD, Boolean.valueOf(xDBFumoInfo.m_bBigDeltaDownload));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_COPY_RETRY_COUNT, Integer.valueOf(xDBFumoInfo.nCopyRetryCount));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_NETCONNTYPE, xDBFumoInfo.szNetworkConnType);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_LOWBATTERY_RETRY_COUNT, Integer.valueOf(xDBFumoInfo.nLowBatteryRetryCount));
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_OBJECTHASH, xDBFumoInfo.szObjectHash);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UPDATE_FW, xDBFumoInfo.szUpdateFWVer);
            contentValues.put(XDBInterface.XDM_SQL_DB_FUMO_UIMODE, Integer.valueOf(xDBFumoInfo.nUiMode));
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_FUMO_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b3, code lost:
        if (r3 != null) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ba, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00be, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsFUMORow(long r53) {
        /*
        // Method dump skipped, instructions count: 210
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsFUMORow(long):boolean");
    }

    public static long xdmDbInsertPostPoneRow(XDBPostPoneInfo xDBPostPoneInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_CURRENTTIME, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_ENDTIME, Long.valueOf(xDBPostPoneInfo.getPostponeTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_AFTERDOWNLOADBATTERYSTATUS, (Boolean) false);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONETIME, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONECOUNT, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONEMAXCOUNT, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONEDOWNLOAD, Integer.valueOf(xDBPostPoneInfo.getPostponeStatus()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_CURRENTVERSION, "");
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_AUTOINSTALL, (Boolean) false);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_FORCE, Integer.valueOf(xDBPostPoneInfo.getForceInstall()));
            contentValues.put(XDBInterface.XDM_SQL_DB_WIFI_POSTPONE_COUNT, Integer.valueOf(xDBPostPoneInfo.getWifiPostponeRetryCount()));
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_POSTPONE_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009f, code lost:
        if (r2 != null) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a6, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00aa, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBPostPoneInfo xdmDbFetchPostPoneRow(long r23) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 192
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchPostPoneRow(long):com.accessorydm.db.file.XDBPostPoneInfo");
    }

    public static void xdmDbUpdatePostPoneRow(long j, XDBPostPoneInfo xDBPostPoneInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_CURRENTTIME, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_ENDTIME, Long.valueOf(xDBPostPoneInfo.getPostponeTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_AFTERDOWNLOADBATTERYSTATUS, (Boolean) false);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONETIME, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONECOUNT, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONEMAXCOUNT, (Integer) 0);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_POSTPONEDOWNLOAD, Integer.valueOf(xDBPostPoneInfo.getPostponeStatus()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_CURRENTVERSION, "");
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_AUTOINSTALL, (Boolean) false);
            contentValues.put(XDBInterface.XDM_SQL_DB_POSTPONE_FORCE, Integer.valueOf(xDBPostPoneInfo.getForceInstall()));
            contentValues.put(XDBInterface.XDM_SQL_DB_WIFI_POSTPONE_COUNT, Integer.valueOf(xDBPostPoneInfo.getWifiPostponeRetryCount()));
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_POSTPONE_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0073, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0075, code lost:
        if (r3 != null) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007c, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0080, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsPostPoneRow(long r23) {
        /*
        // Method dump skipped, instructions count: 148
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsPostPoneRow(long):boolean");
    }

    public static long xdmDbInsertSimInfoRow(XDBSimInfo xDBSimInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_SIMINFO_IMSI, xDBSimInfo.m_szIMSI);
            contentValues.put(XDBInterface.XDM_SQL_DB_SIMINFO_IMSI2, xDBSimInfo.m_szIMSI2);
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_SIMINFO_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0070, code lost:
        if (r13 != null) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0076, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0077, code lost:
        r14.addSuppressed(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007a, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBSimInfo xdmDbFetchSimInfoRow(long r13) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 143
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchSimInfoRow(long):com.accessorydm.db.file.XDBSimInfo");
    }

    public static void xdmDbUpdateSimInfoRow(long j, XDBSimInfo xDBSimInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_SIMINFO_IMSI, xDBSimInfo.m_szIMSI);
            contentValues.put(XDBInterface.XDM_SQL_DB_SIMINFO_IMSI2, xDBSimInfo.m_szIMSI2);
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_SIMINFO_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0059, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005a, code lost:
        if (r13 != null) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0060, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0061, code lost:
        r14.addSuppressed(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0064, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsSimInfoRow(long r13) {
        /*
        // Method dump skipped, instructions count: 120
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsSimInfoRow(long):boolean");
    }

    public static long xdmDbInsertPollingRow(PollingInfo pollingInfo) {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_ORIGINAL_URL, pollingInfo.getOriginPreUrl());
            contentValues.put("url", pollingInfo.getPreUrl());
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_PERIODUNIT, pollingInfo.getPeriodUnit());
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_PERIOD, Integer.valueOf(pollingInfo.getPeriod()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_TIME, Integer.valueOf(pollingInfo.getTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_RANGE, Integer.valueOf(pollingInfo.getRange()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_CYCLEOFDEVICEHEARTBEAT, Integer.valueOf(pollingInfo.getHeartBeatPeriod()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_SERVICEURL, pollingInfo.getHeartBeatUrl());
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_NEXTPOLLINGTIME, Long.valueOf(pollingInfo.getNextPollingTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_NEXTREPORTTIME, Long.valueOf(pollingInfo.getNextHeartBeatTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_VERSIONFILENAME, pollingInfo.getVersionFileName());
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_POLLING_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d9, code lost:
        if (r3 != null) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00df, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e0, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e4, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.samsung.android.fotaagent.polling.PollingInfo xdmDbFetchPollingRow(long r23) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 249
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchPollingRow(long):com.samsung.android.fotaagent.polling.PollingInfo");
    }

    public static void xdmDbUpdatePollingRow(long j, PollingInfo pollingInfo) throws XDBUserDBException {
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_ORIGINAL_URL, pollingInfo.getOriginPreUrl());
            contentValues.put("url", pollingInfo.getPreUrl());
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_PERIODUNIT, pollingInfo.getPeriodUnit());
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_PERIOD, Integer.valueOf(pollingInfo.getPeriod()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_TIME, Integer.valueOf(pollingInfo.getTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_RANGE, Integer.valueOf(pollingInfo.getRange()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_CYCLEOFDEVICEHEARTBEAT, Integer.valueOf(pollingInfo.getHeartBeatPeriod()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_SERVICEURL, pollingInfo.getHeartBeatUrl());
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_NEXTPOLLINGTIME, Long.valueOf(pollingInfo.getNextPollingTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_NEXTREPORTTIME, Long.valueOf(pollingInfo.getNextHeartBeatTime()));
            contentValues.put(XDBInterface.XDM_SQL_DB_POLLING_VERSIONFILENAME, pollingInfo.getVersionFileName());
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_POLLING_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0073, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0075, code lost:
        if (r3 != null) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007c, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0080, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsPollingRow(long r23) {
        /*
        // Method dump skipped, instructions count: 148
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsPollingRow(long):boolean");
    }

    public static void xdmDbInsertAccXListNodeRow(XDBAccXNodeInfo xDBAccXNodeInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_ACCXLISTNODE_ACCOUNT, xDBAccXNodeInfo.m_szAccount);
            contentValues.put(XDBInterface.XDM_SQL_DB_ACCXLISTNODE_APPADDR, xDBAccXNodeInfo.m_szAppAddr);
            contentValues.put(XDBInterface.XDM_SQL_DB_ACCXLISTNODE_APPADDRPORT, xDBAccXNodeInfo.m_szAppAddrPort);
            contentValues.put(XDBInterface.XDM_SQL_DB_ACCXLISTNODE_CLIENTAPPAUTH, xDBAccXNodeInfo.m_szClientAppAuth);
            contentValues.put(XDBInterface.XDM_SQL_DB_ACCXLISTNODE_SERVERAPPAUTH, xDBAccXNodeInfo.m_szServerAppAuth);
            contentValues.put(XDBInterface.XDM_SQL_DB_ACCXLISTNODE_TOCONREF, xDBAccXNodeInfo.m_szToConRef);
            xdmDbGetWritableDatabase.insert(XDBInterface.XDB_ACCXLISTNODE_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0066, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0068, code lost:
        if (r3 != null) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006f, code lost:
        r0.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0073, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsAccXListNodeRow(long r18) {
        /*
        // Method dump skipped, instructions count: 135
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsAccXListNodeRow(long):boolean");
    }

    public static long xdmDbInsertResyncModeRow(XDBResyncModeInfo xDBResyncModeInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_RESYNCMODE_NONCERESYNCMODE, Boolean.valueOf(xDBResyncModeInfo.nNoceResyncMode));
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_RESYNCMODE_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006c, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006d, code lost:
        if (r13 != null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0073, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0074, code lost:
        r14.addSuppressed(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0077, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBResyncModeInfo xdmDbFetchResyncModeRow(long r13) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 140
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbFetchResyncModeRow(long):com.accessorydm.db.file.XDBResyncModeInfo");
    }

    public static void xdmDbUpdateResyncModeRow(long j, XDBResyncModeInfo xDBResyncModeInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_RESYNCMODE_NONCERESYNCMODE, Boolean.valueOf(xDBResyncModeInfo.nNoceResyncMode));
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_RESYNCMODE_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0058, code lost:
        if (r12 != null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r12.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005e, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005f, code lost:
        r13.addSuppressed(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0062, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbExistsResyncModeRow(long r12) {
        /*
        // Method dump skipped, instructions count: 118
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbExistsResyncModeRow(long):boolean");
    }

    public static long xdmDbSqlAgentInfoInsertRow(XDBAgentInfo xDBAgentInfo) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        long j = -1;
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return -1;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_AGENT_INFO_AGENT_TYPE, Integer.valueOf(xDBAgentInfo.m_nAgentType));
            j = xdmDbGetWritableDatabase.insert(XDBInterface.XDB_DM_AGENT_INFO_TABLE, null, contentValues);
        } catch (SQLException e) {
            Log.E(e.toString());
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
        XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0066, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0067, code lost:
        if (r13 != null) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006d, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006e, code lost:
        r14.addSuppressed(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.accessorydm.db.file.XDBAgentInfo xdmDbSqlAgentInfoFetchRow(long r13) throws com.accessorydm.db.file.XDBUserDBException {
        /*
        // Method dump skipped, instructions count: 134
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbSqlAgentInfoFetchRow(long):com.accessorydm.db.file.XDBAgentInfo");
    }

    public static void xdmDbSqlAgentInfoUpdateRow(long j, XDBAgentInfo xDBAgentInfo) throws XDBUserDBException {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase xdmDbGetWritableDatabase = XDMDbManager.xdmDbGetWritableDatabase();
        if (xdmDbGetWritableDatabase == null) {
            Log.E("db is null");
            return;
        }
        try {
            contentValues.put(XDBInterface.XDM_SQL_DB_AGENT_INFO_AGENT_TYPE, Integer.valueOf(xDBAgentInfo.m_nAgentType));
            xdmDbGetWritableDatabase.update(XDBInterface.XDB_DM_AGENT_INFO_TABLE, contentValues, "rowid=" + j, null);
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
        } catch (SQLException e) {
            Log.E(e.toString());
            throw new XDBUserDBException(1);
        } catch (Throwable th) {
            XDMDbManager.xdmDbCloseSQLiteDatabase(xdmDbGetWritableDatabase);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0058, code lost:
        if (r12 != null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r12.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005e, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005f, code lost:
        r13.addSuppressed(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0062, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmDbSqlAgentInfoExistsRow(long r12) {
        /*
        // Method dump skipped, instructions count: 118
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbSqlAgentInfoExistsRow(long):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
        if (r1 != null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0051, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0052, code lost:
        r2.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0055, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int xdmDbsqlGetFUMOStatus() {
        /*
        // Method dump skipped, instructions count: 124
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmDbsqlGetFUMOStatus():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
        if (r1 != null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0051, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0052, code lost:
        r2.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0055, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int xdmdbsqlGetFumoInitType() {
        /*
        // Method dump skipped, instructions count: 104
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmdbsqlGetFumoInitType():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
        if (r1 != null) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0051, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0052, code lost:
        r2.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0055, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int xdmdbsqlGetNotiSaveState() {
        /*
        // Method dump skipped, instructions count: 104
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmdbsqlGetNotiSaveState():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004b, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004c, code lost:
        if (r14 != null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0052, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
        r0.addSuppressed(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0056, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean xdmdbsqlGetExistsTable(java.lang.String r14) {
        /*
        // Method dump skipped, instructions count: 105
        */
        throw new UnsupportedOperationException("Method not decompiled: com.accessorydm.db.sql.XDMDbSqlQuery.xdmdbsqlGetExistsTable(java.lang.String):boolean");
    }
}
