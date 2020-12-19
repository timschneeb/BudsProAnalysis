package com.samsung.android.app.twatchmanager.log;

import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/* access modifiers changed from: package-private */
public class MLogger {
    private static int MAX_ARRAY_BUFF = 51200;
    private static int MAX_FILE_SIZE = 3145728;
    public static final String TAG = ("tUHM:" + MLogger.class.getSimpleName());
    private static Pattern btAddressPattern = Pattern.compile("([0-9A-Fa-f]{2}[:-]){4}");
    private static String mExportDir = "/log/GearLog/";
    private static String mFileName = "dumpState-UHM.log";
    private static String mInternalDir = "/data/data/com.samsung.android.app.watchmanager/files/";
    private static String[] mMsgLog = null;
    private static int mbCounter = 0;
    private static boolean mbRingBuffStart = false;

    static {
        init();
    }

    MLogger() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x009c A[SYNTHETIC, Splitter:B:25:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a4 A[Catch:{ IOException -> 0x00a0 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void copyToSdcard() {
        /*
        // Method dump skipped, instructions count: 178
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.log.MLogger.copyToSdcard():void");
    }

    private static File createLogFile() {
        File file = new File(mInternalDir, mFileName);
        try {
            if (file.length() >= ((long) MAX_FILE_SIZE) || mbRingBuffStart) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized boolean dumpLog() {
        RandomAccessFile randomAccessFile;
        FileChannel fileChannel;
        IOException e;
        synchronized (MLogger.class) {
            int i = mbCounter;
            File createLogFile = createLogFile();
            if (createLogFile != null) {
                try {
                    randomAccessFile = new RandomAccessFile(createLogFile, "rw");
                } catch (IOException e2) {
                    e = e2;
                    fileChannel = null;
                    randomAccessFile = null;
                }
            } else {
                randomAccessFile = null;
            }
            if (randomAccessFile != null) {
                try {
                    fileChannel = randomAccessFile.getChannel();
                } catch (IOException e3) {
                    e = e3;
                    fileChannel = null;
                }
            } else {
                fileChannel = null;
            }
            if (fileChannel != null) {
                try {
                    fileChannel.position(fileChannel.size());
                    if (mbRingBuffStart) {
                        writeBuffToFile(null, fileChannel, i, MAX_ARRAY_BUFF);
                    }
                    writeBuffToFile(null, fileChannel, 0, i);
                    initializeCounter();
                    if (fileChannel != null) {
                        fileChannel.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e4) {
                    e = e4;
                    initializeCounter();
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            e.printStackTrace();
                        }
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    return false;
                }
            }
            return true;
        }
    }

    private static synchronized void fillBuff(String str) {
        synchronized (MLogger.class) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            String str2 = simpleDateFormat.format(date) + "\t" + str;
            if (mbCounter >= MAX_ARRAY_BUFF) {
                mbRingBuffStart = true;
                mbCounter = 0;
            }
            mMsgLog[mbCounter] = str2;
            mbCounter++;
        }
    }

    public static int getCounter() {
        if (mMsgLog[0] == null) {
            return 0;
        }
        return mbCounter;
    }

    public static final void info(String str) {
        try {
            fillBuff(str + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        initializeBuff();
    }

    private static void initializeBuff() {
        mMsgLog = new String[MAX_ARRAY_BUFF];
    }

    private static void initializeCounter() {
        mbCounter = 0;
        mbRingBuffStart = false;
        mMsgLog[0] = null;
    }

    private static void setFilePermissions(File file) {
        if (file.exists()) {
            if (!file.setReadable(true, false)) {
                Log.w(TAG, "setPermissions() : setReadable FAIL");
            }
            if (!file.setWritable(true, false)) {
                Log.w(TAG, "setPermissions() : setWritable FAIL");
            }
            if (!file.setExecutable(true, false)) {
                Log.w(TAG, "setPermissions() : setWritable FAIL");
                return;
            }
            return;
        }
        Log.w(TAG, "setPermissions() : file not exist");
    }

    private static void writeBuffToFile(ByteBuffer byteBuffer, FileChannel fileChannel, int i, int i2) {
        while (i < i2) {
            if (!TextUtils.isEmpty(mMsgLog[i])) {
                String[] strArr = mMsgLog;
                strArr[i] = btAddressPattern.matcher(strArr[i]).replaceAll("##:##:##:##:");
                try {
                    byteBuffer = ByteBuffer.wrap(mMsgLog[i].getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (byteBuffer != null) {
                try {
                    fileChannel.write(byteBuffer);
                    mMsgLog[i] = null;
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            i++;
        }
    }
}
