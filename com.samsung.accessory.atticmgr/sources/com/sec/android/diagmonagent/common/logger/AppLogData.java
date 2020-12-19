package com.sec.android.diagmonagent.common.logger;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.samsung.context.sdk.samsunganalytics.BuildConfig;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AppLogData implements IAppLogData {
    private static final String APP_TAG = "DIAGMON_SDK";
    private static final int LOG_FILE_LIMIT = 300000;
    private static final String LOG_FILE_NAME = "DIAGMON_SDK.log";
    private final Date mDate = new Date();
    private FileHandler mFileHandler;
    private final SimpleDateFormat mFormatter = new SimpleDateFormat("MM/dd HH:mm:ss.SSS", Locale.getDefault());
    private Logger mLogger;
    private String mMessagePrefix;

    public AppLogData(Context context) {
        initFileLogging(context);
    }

    @Override // com.sec.android.diagmonagent.common.logger.IAppLogData
    public int v(String str, String str2) {
        try {
            makeAdditionalData(str);
            printToFile("[v]", APP_TAG + this.mMessagePrefix + str2);
            return Log.v(APP_TAG + this.mMessagePrefix, str2);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getMessage());
            return -1;
        }
    }

    @Override // com.sec.android.diagmonagent.common.logger.IAppLogData
    public int d(String str, String str2) {
        try {
            makeAdditionalData(str);
            printToFile("[d]", APP_TAG + this.mMessagePrefix + str2);
            return Log.d(APP_TAG + this.mMessagePrefix, str2);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getMessage());
            return -1;
        }
    }

    @Override // com.sec.android.diagmonagent.common.logger.IAppLogData
    public int i(String str, String str2) {
        try {
            makeAdditionalData(str);
            printToFile("[i]", APP_TAG + this.mMessagePrefix + str2);
            return Log.i(APP_TAG + this.mMessagePrefix, str2);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getMessage());
            return -1;
        }
    }

    @Override // com.sec.android.diagmonagent.common.logger.IAppLogData
    public int w(String str, String str2) {
        try {
            makeAdditionalData(str);
            printToFile("[w]", APP_TAG + this.mMessagePrefix + str2);
            return Log.w(APP_TAG + this.mMessagePrefix, str2);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getMessage());
            return -1;
        }
    }

    @Override // com.sec.android.diagmonagent.common.logger.IAppLogData
    public int e(String str, String str2) {
        try {
            makeAdditionalData(str);
            printToFile("[e]", APP_TAG + this.mMessagePrefix + str2);
            return Log.e(APP_TAG + this.mMessagePrefix, str2);
        } catch (Exception e) {
            Log.e(APP_TAG, e.getMessage());
            return -1;
        }
    }

    @Override // com.sec.android.diagmonagent.common.logger.IAppLogData
    public int printBundle(String str, Bundle bundle) {
        if (bundle != null) {
            try {
                if (!bundle.isEmpty()) {
                    makeAdditionalData(str);
                    for (String str2 : bundle.keySet()) {
                        printToFile(APP_TAG, this.mMessagePrefix + "\"" + str2 + "\" : \"" + bundle.get(str2) + "\"");
                    }
                    return 0;
                }
            } catch (Exception e) {
                Log.e(APP_TAG, e.getMessage());
            }
        }
        return -1;
    }

    private void initFileLogging(Context context) {
        this.mLogger = Logger.getLogger(APP_TAG);
        this.mLogger.setLevel(Level.ALL);
        this.mLogger.setUseParentHandlers(false);
        File file = new File(String.valueOf(context.getDir(com.samsung.android.fotaprovider.log.Log.LOGFILE_PATH, 0)));
        if (file.exists() || file.mkdir()) {
            try {
                this.mFileHandler = new FileHandler(file.getPath() + File.separator + LOG_FILE_NAME, LOG_FILE_LIMIT, 1, true);
                this.mFileHandler.setFormatter(new Formatter() {
                    /* class com.sec.android.diagmonagent.common.logger.AppLogData.AnonymousClass1 */

                    public String format(LogRecord logRecord) {
                        return logRecord.getMessage() + "\n";
                    }
                });
                this.mLogger.addHandler(this.mFileHandler);
            } catch (IOException e) {
                Log.e(APP_TAG, e.getLocalizedMessage());
            }
        }
    }

    private void makeAdditionalData(String str) {
        this.mMessagePrefix = "[" + BuildConfig.VERSION_CODE + "]" + "[" + str + "]" + " ";
    }

    private void printToFile(String str, String str2) {
        if (this.mLogger != null) {
            this.mDate.setTime(System.currentTimeMillis());
            Logger logger = this.mLogger;
            logger.info(str + " " + this.mFormatter.format(this.mDate) + ":" + str2);
        }
    }
}
