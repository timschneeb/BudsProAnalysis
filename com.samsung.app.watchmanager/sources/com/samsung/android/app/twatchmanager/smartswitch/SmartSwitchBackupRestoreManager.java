package com.samsung.android.app.twatchmanager.smartswitch;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.util.SmartSwitchSecurityUtils;
import java.io.File;

public class SmartSwitchBackupRestoreManager {
    private static final int BACKUP_RESPONSE = 1001;
    private static final int RESTORE_RESPONSE = 1002;
    private static final String TAG = ("tUHM:SmartSwitch:" + SmartSwitchBackupRestoreManager.class.getSimpleName());
    private int mAction;
    private Context mContext;
    private int mErrorCode;
    private String mExportSessionTime;
    private Intent mIntent;
    private int mReqSize;
    private int mResult;
    private String mSavePath;
    private SmartSwitchSecurityUtils mSecuUtil = new SmartSwitchSecurityUtils(this.mSessionKey);
    private int mSecurityLevel;
    private String mSessionKey;
    private String mSource;

    public SmartSwitchBackupRestoreManager(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
        this.mSavePath = intent.getStringExtra(SmartSwitchConstants.SAVE_PATH);
        this.mSource = intent.getStringExtra(SmartSwitchConstants.SOURCE);
        this.mSessionKey = intent.getStringExtra(SmartSwitchConstants.SESSION_KEY);
        this.mExportSessionTime = intent.getStringExtra(SmartSwitchConstants.EXPORT_SESSION_TIME);
        this.mAction = intent.getIntExtra(SmartSwitchConstants.ACTION, -1);
        this.mSecurityLevel = intent.getIntExtra(SmartSwitchConstants.SECURITY_LEVEL, -1);
    }

    private boolean EncryptAndCopyFile() {
        File file = new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME);
        File file2 = new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME_ENCRYPTED);
        File file3 = new File(this.mSavePath + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME_ENCRYPTED);
        if (!this.mSecuUtil.EncryptFile(file)) {
            setResponse(1, 1, 0);
            Log.e(TAG, "Failed to encrypt zip file!");
            return false;
        }
        FileUtils.deleteAllFiles(file3.getAbsolutePath());
        int copyFile = FileUtils.copyFile(file2, file3);
        if (copyFile != 0) {
            setResponse(1, copyFile, 0);
            String str = TAG;
            Log.e(str, "Failed to copy encrypt file to designated path! : " + copyFile);
            return false;
        }
        FileUtils.deleteFile(file2.getAbsolutePath());
        FileUtils.deleteDirectory(SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH);
        return true;
    }

    private void backup() {
        Log.d(TAG, "backup()");
        copyBackupFiles();
        FileUtils.fileZip(SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH, SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator, SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME);
        if (EncryptAndCopyFile()) {
            setResponse(0, 0, 0);
        }
        sendSmartSwitchResponse(1001);
    }

    private boolean copyAndDecryptFile() {
        File file = new File(this.mSavePath + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME_ENCRYPTED);
        File file2 = new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME_ENCRYPTED);
        FileUtils.makeDir(SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH);
        String str = TAG;
        Log.d(str, "copyAndDecryptFile()::srcPath = " + file);
        Log.d(TAG, "copyAndDecryptFile()::desPath = /data/data/com.samsung.android.app.watchmanager/smartswitch");
        int copyFile = FileUtils.copyFile(file, file2);
        if (copyFile != 0) {
            setResponse(1, copyFile, 0);
            String str2 = TAG;
            Log.e(str2, "Failed to copy encrypt file from designated path! : " + copyFile);
            return false;
        } else if (!this.mSecuUtil.DecryptFile(file2)) {
            setResponse(1, 1, 0);
            Log.e(TAG, "Failed to decrypt file!");
            return false;
        } else {
            FileUtils.deleteFile(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME_ENCRYPTED);
            return true;
        }
    }

    private void copyBackupFiles() {
        Log.d(TAG, "copyBackupFiles()");
        FileUtils.makeDir(SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH);
        FileUtils.copyDirectory(new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH_FILES), new File("/data/data/com.samsung.android.app.watchmanager/smartswitch/files"));
        FileUtils.copyDirectory(new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH_DATA_BASE), new File("/data/data/com.samsung.android.app.watchmanager/smartswitch/databases"));
        FileUtils.copyDirectory(new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH_SHARED_PREF), new File("/data/data/com.samsung.android.app.watchmanager/smartswitch/shared_prefs"));
    }

    private void copyRestoreFiles() {
        FileUtils.makeDir(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH_FILES + File.separator + "smartswitch");
        File file = new File(SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH + File.separator + "files" + File.separator + "backup_files");
        int copyDirectory = FileUtils.copyDirectory(file, new File(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH_FILES + File.separator + "smartswitch" + File.separator + "backup_files"));
        if (copyDirectory != 0) {
            setResponse(1, copyDirectory, 0);
            String str = TAG;
            Log.e(str, "Failed to copy file to destination path! : " + copyDirectory);
        } else {
            setResponse(0, 0, 0);
        }
        FileUtils.deleteDirectory(SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH);
    }

    private void restore() {
        Log.d(TAG, "restore()");
        if (copyAndDecryptFile()) {
            setResponse(0, 0, 0);
        }
        if (!FileUtils.fileUnZip(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME, SmartSwitchConstants.WATCH_MANAGER_DATA_SMART_SWITCH_BACKUP_PATH)) {
            setResponse(1, 1, 0);
            Log.e(TAG, "Failed to unzip file!");
        }
        FileUtils.deleteFile(SmartSwitchConstants.WATCH_MANAGER_DATA_PATH + File.separator + SmartSwitchConstants.WATCH_MANAGER_BACKUP_FILE_NAME);
        copyRestoreFiles();
        sendSmartSwitchResponse(1002);
    }

    private void setResponse(int i, int i2, int i3) {
        this.mResult = i;
        this.mErrorCode = i2;
        this.mReqSize = i3;
    }

    public boolean permissionCheck(Context context) {
        String[] strArr = SmartSwitchConstants.PERMISSIONS;
        for (String str : strArr) {
            if (Build.VERSION.SDK_INT >= 23 && context.checkSelfPermission(str) != 0) {
                return false;
            }
        }
        return true;
    }

    public void sendSmartSwitchResponse(int i) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("sendSmartSwitchResponse()::");
        sb.append(i == 1001 ? "BACKUP_RESPONSE" : "RESTORE_RESPONSE");
        Log.d(str, sb.toString());
        Intent intent = new Intent(i == 1001 ? SmartSwitchConstants.RESPONSE_BACKUP_TO_SMART_SWITCH : SmartSwitchConstants.RESPONSE_RESTORE_TO_SMART_SWITCH);
        intent.putExtra(SmartSwitchConstants.RESULT, this.mResult);
        intent.putExtra(SmartSwitchConstants.ERR_CODE, this.mErrorCode);
        intent.putExtra(SmartSwitchConstants.REQ_SIZE, this.mReqSize);
        intent.putExtra(SmartSwitchConstants.SOURCE, this.mSource);
        if (i == 1001) {
            intent.putExtra(SmartSwitchConstants.EXPORT_SESSION_TIME, this.mExportSessionTime);
        }
        this.mContext.sendBroadcast(intent, SmartSwitchConstants.SMART_SWITCH_PERMISSION);
    }

    public void startBackup() {
        Log.d(TAG, "startBackup()");
        if (!permissionCheck(this.mContext)) {
            setResponse(1, 4, 0);
            sendSmartSwitchResponse(1001);
            return;
        }
        backup();
    }

    public void startRestore() {
        Log.d(TAG, "startRestore()");
        if (!permissionCheck(this.mContext)) {
            setResponse(1, 4, 0);
            sendSmartSwitchResponse(1002);
            return;
        }
        restore();
    }
}
