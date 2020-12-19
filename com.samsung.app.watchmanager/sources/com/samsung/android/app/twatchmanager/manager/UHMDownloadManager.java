package com.samsung.android.app.twatchmanager.manager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.Settings;
import com.samsung.android.app.twatchmanager.TWatchManagerApplication;
import com.samsung.android.app.twatchmanager.log.Log;
import com.samsung.android.app.twatchmanager.model.InstallPack;
import com.samsung.android.app.twatchmanager.model.UHMPackageInfo;
import com.samsung.android.app.twatchmanager.update.UpdateCheckTask;
import com.samsung.android.app.twatchmanager.util.GlobalConst;
import com.samsung.android.app.twatchmanager.util.HostManagerUtils;
import com.samsung.android.app.twatchmanager.util.HostManagerUtilsNetwork;
import com.samsung.android.app.twatchmanager.util.InstallationUtils;
import com.samsung.android.app.twatchmanager.util.UpdateUtil;
import com.samsung.android.app.watchmanager.setupwizard.HMConnectFragmentUIHelper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class UHMDownloadManager {
    private static final String DOWNLOAD_SERVER_URL = "https://vas.samsungapps.com/stub/stubDownload.as";
    private static final String FIRST_VERSION_NAME = "1.0.0";
    private static final String MCC_NOT_SUPPORTED = "1002";
    private static final String NEED_TO_SUBSTRING = "SAMSUNG-";
    public static final String PATH_FOR_EXTERNAL_STORAGE = (Environment.getExternalStorageDirectory().getPath() + File.separator + "Gear");
    private static final int RETRY_MAX_LIMIT = 3;
    private static final String TAG = ("tUHM:" + UHMDownloadManager.class.getSimpleName());
    private static UHMDownloadManager mHMDownloadManager;
    private volatile boolean cancel;
    private final Object downloadMonitor;
    private volatile boolean isRunning;
    private final Context mContext;
    private HandlerThread mDownloadThread;
    private final Handler mDownloadThreadHandler;
    private WeakReference<Handler> mListenerHandlerWeaKRef;
    private String pluginPackageName;

    /* access modifiers changed from: package-private */
    public static class DownloadCheckResponse {
        final String appId;
        final long contentSize;
        final String downloadURL;
        final String resultCode;
        final String signature;
        final int versionCode;

        public DownloadCheckResponse(String str, String str2, String str3, String str4, long j, int i) {
            this.appId = str;
            this.resultCode = str2;
            this.downloadURL = str3;
            this.signature = str4;
            this.contentSize = j;
            this.versionCode = i;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DownloadCheckResponse)) {
                return false;
            }
            return ((DownloadCheckResponse) obj).appId.equalsIgnoreCase(this.appId);
        }

        public int hashCode() {
            return this.appId.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("appId :");
            sb.append(this.appId);
            sb.append(" resultCode:");
            sb.append(this.resultCode);
            sb.append(" downloadURL:");
            sb.append(this.downloadURL);
            sb.append(" signature exist:");
            String str = this.signature;
            sb.append(str != null && !str.isEmpty());
            sb.append(" contentSize:");
            sb.append(this.contentSize);
            return sb.toString();
        }
    }

    /* access modifiers changed from: private */
    public class DownloadRunnable implements Runnable {
        ArrayList<UHMPackageInfo> packageInfoList;

        public DownloadRunnable(ArrayList<UHMPackageInfo> arrayList) {
            this.packageInfoList = arrayList;
        }

        public void run() {
            boolean z;
            Log.d(UHMDownloadManager.TAG, "DownloadRunnable()");
            UHMDownloadManager.this.isRunning = true;
            ArrayList<UHMPackageInfo> arrayList = new ArrayList<>(this.packageInfoList.size());
            Iterator<UHMPackageInfo> it = this.packageInfoList.iterator();
            while (it.hasNext()) {
                UHMPackageInfo next = it.next();
                boolean isExistPackage = HostManagerUtils.isExistPackage(UHMDownloadManager.this.mContext, next.packageName);
                if (isExistPackage) {
                    HostManagerUtils.enableApplication(UHMDownloadManager.this.mContext, next.packageName);
                }
                if (next.checkForUpdate || !isExistPackage) {
                    arrayList.add(next);
                }
            }
            if (arrayList.size() == 0) {
                Log.d(UHMDownloadManager.TAG, "packages already installed. No need to download");
                UHMDownloadManager.this.sendEmptyMessage(InstallationUtils.STATUS_DOWNLOAD_NOT_REQUIRED);
                return;
            }
            this.packageInfoList = arrayList;
            String str = UHMDownloadManager.TAG;
            Log.d(str, "Package name for Downloading:" + arrayList);
            if (HostManagerUtilsNetwork.isNetworkAvailable(UHMDownloadManager.this.mContext)) {
                Log.d(UHMDownloadManager.TAG, "network is available. Starting download!");
                UHMDownloadManager.this.startDownloadInternal(this.packageInfoList);
                return;
            }
            Message obtainMessage = UHMDownloadManager.this.mDownloadThreadHandler.obtainMessage(InstallationUtils.STATUS_NO_NETWORK, 0, 0);
            Iterator<UHMPackageInfo> it2 = this.packageInfoList.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (!it2.next().isOptional) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (z) {
                obtainMessage.arg1 = 0;
            } else {
                obtainMessage.arg1 = 1;
            }
            UHMDownloadManager.this.sendMessage(obtainMessage);
        }
    }

    private static class LazyHolder {
        private static final UHMDownloadManager INSTANCE = new UHMDownloadManager();

        private LazyHolder() {
        }
    }

    private UHMDownloadManager() {
        this.downloadMonitor = new Object();
        this.cancel = false;
        this.mContext = TWatchManagerApplication.getAppContext();
        this.mDownloadThread = new HandlerThread("DOWNLOAD_THREAD", 5);
        this.mDownloadThread.start();
        this.mDownloadThreadHandler = new Handler(this.mDownloadThread.getLooper());
    }

    private String getDefaultMccURL(String str) {
        int indexOf;
        String str2 = TAG;
        Log.d(str2, "getDefaultMccURL() :" + str);
        if (str == null || (indexOf = str.indexOf("mcc=")) == -1) {
            return null;
        }
        String substring = str.substring(indexOf, str.indexOf(38, indexOf));
        String str3 = TAG;
        Log.d(str3, "getDefaultMccURL() extracted : " + substring);
        String replace = str.replace(substring, "mcc=000");
        String str4 = TAG;
        Log.d(str4, "getDefaultMccURL() return :" + replace);
        return replace;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0001 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.ArrayList<com.samsung.android.app.twatchmanager.manager.UHMDownloadManager.DownloadCheckResponse> getDownloadMetaData(java.lang.String r7) {
        /*
        // Method dump skipped, instructions count: 214
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.manager.UHMDownloadManager.getDownloadMetaData(java.lang.String):java.util.ArrayList");
    }

    public static String getDownloadPath(Context context) {
        Log.d(TAG, " getDownloadPath() ");
        if (context == null) {
            Log.d(TAG, "context is null, can't get path for storing providers.");
            return null;
        } else if (HostManagerUtils.isSamsungDevice()) {
            String absolutePath = context.getDir(GlobalConst.PLUGIN_DOWNLOAD_FOLDER, 0).getAbsolutePath();
            try {
                InstallationUtils.changeFilePermission(absolutePath, InstallationUtils.PERMISSIONS_GLOBAL);
                return absolutePath;
            } catch (Exception e) {
                e.printStackTrace();
                return absolutePath;
            }
        } else {
            Log.d(TAG, "Internal memory not supported");
            File file = new File(PATH_FOR_EXTERNAL_STORAGE);
            String absolutePath2 = file.getAbsolutePath();
            if (file.exists()) {
                return absolutePath2;
            }
            boolean mkdirs = file.mkdirs();
            String absolutePath3 = file.getAbsolutePath();
            String str = TAG;
            Log.d(str, absolutePath3 + " External folder created: " + mkdirs);
            return absolutePath3;
        }
    }

    private String getFileName(String str) {
        if (str == null) {
            return null;
        }
        return str + ".apk";
    }

    private HttpURLConnection getHttpURLConnection(String str) {
        HttpURLConnection httpURLConnection;
        IOException e;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setReadTimeout(60000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Connection", "close");
                httpURLConnection.connect();
            } catch (IOException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e = e3;
            httpURLConnection = null;
            e.printStackTrace();
            return httpURLConnection;
        }
        return httpURLConnection;
    }

    public static UHMDownloadManager getInstance() {
        Log.d(TAG, "getInstance()");
        return LazyHolder.INSTANCE;
    }

    private void nullAndVoidHandler(Handler handler) {
        String str = TAG;
        Log.d(str, "nullAndVoidHandler(" + handler + ")");
        if (handler != null) {
            releaseThread(this.mDownloadThread);
            handler.removeCallbacksAndMessages(null);
            return;
        }
        Log.d(TAG, "Requested operation can't be carried out on null handler.");
    }

    private DownloadCheckResponse parseAppInfo(XmlPullParser xmlPullParser, String str) {
        String str2 = "";
        String str3 = str2;
        long j = 0;
        int i = 0;
        int eventType = xmlPullParser.getEventType();
        String str4 = str3;
        String str5 = str4;
        boolean z = false;
        while (!z) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("appId") && xmlPullParser.next() == 4) {
                    str4 = xmlPullParser.getText();
                    Log.d(TAG, "downloadCheck(), appId = " + str4);
                }
                if (name.equals("resultCode") && xmlPullParser.next() == 4) {
                    str5 = xmlPullParser.getText();
                    Log.d(TAG, "downloadCheck(), resultCode = " + str5);
                }
                if (name.equals("downloadURI") && xmlPullParser.next() == 4) {
                    str2 = xmlPullParser.getText();
                    Log.d(TAG, "downloadCheck(), DownloadURI = " + str2);
                }
                if (name.equals("signature") && xmlPullParser.next() == 4) {
                    str3 = xmlPullParser.getText();
                    String str6 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Signature exist? : ");
                    sb.append(str3 != null && !str3.isEmpty());
                    Log.d(str6, sb.toString());
                }
                if (name.equals("contentSize") && xmlPullParser.next() == 4) {
                    j = Long.parseLong(xmlPullParser.getText());
                    Log.d(TAG, "contentSize : " + j);
                }
                if (name.equals("versionCode") && xmlPullParser.next() == 4) {
                    i = Integer.parseInt(xmlPullParser.getText());
                    Log.d(TAG, "versionCode : " + i);
                }
            } else if (eventType == 3) {
                if (str.equalsIgnoreCase(xmlPullParser.getName())) {
                    z = true;
                }
                eventType = xmlPullParser.next();
            }
            eventType = xmlPullParser.next();
        }
        Log.d(TAG, "DownloadCheckResponse appId =" + str4 + " resultCode=" + str5 + " downloadURL=" + str2);
        if (str4.trim().isEmpty() || str5.trim().isEmpty()) {
            return null;
        }
        return new DownloadCheckResponse(str4, str5, str2, str3, j, i);
    }

    private ArrayList<DownloadCheckResponse> parserDownloadResponse(InputStream inputStream) {
        DownloadCheckResponse downloadCheckResponse;
        ArrayList<DownloadCheckResponse> arrayList = new ArrayList<>();
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(inputStream, null);
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                if (eventType == 2) {
                    String name = newPullParser.getName();
                    if ("appId".equalsIgnoreCase(name)) {
                        downloadCheckResponse = parseAppInfo(newPullParser, "result");
                    } else if ("appInfo".equalsIgnoreCase(name)) {
                        newPullParser.next();
                        downloadCheckResponse = parseAppInfo(newPullParser, "appInfo");
                    } else {
                        downloadCheckResponse = null;
                    }
                    if (downloadCheckResponse != null) {
                        arrayList.add(downloadCheckResponse);
                    }
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | XmlPullParserException e2) {
            e2.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    private void releaseThread(HandlerThread handlerThread) {
        String str = TAG;
        Log.d(str, "releaseThread(" + handlerThread + ")");
        if (handlerThread != null) {
            handlerThread.interrupt();
        } else {
            Log.d(TAG, "Requested operation can't be carried out on null thread.");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendEmptyMessage(int i) {
        sendMessage(this.mDownloadThreadHandler.obtainMessage(i));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendMessage(Message message) {
        Handler handler;
        synchronized (this.downloadMonitor) {
            switch (message.what) {
                case InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_FAILED /*{ENCODED_INT: 708}*/:
                case InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_SUCCESS /*{ENCODED_INT: 709}*/:
                case InstallationUtils.STATUS_NO_NETWORK /*{ENCODED_INT: 711}*/:
                case InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_URL_NOT_AVAILABLE /*{ENCODED_INT: 713}*/:
                case InstallationUtils.STATUS_DOWNLOAD_NOT_REQUIRED /*{ENCODED_INT: 714}*/:
                case InstallationUtils.STATUS_INSUFFICIENT_STORAGE /*{ENCODED_INT: 716}*/:
                    this.isRunning = false;
                    break;
            }
            if (!(this.mListenerHandlerWeaKRef == null || (handler = this.mListenerHandlerWeaKRef.get()) == null || this.cancel)) {
                Message obtainMessage = handler.obtainMessage();
                obtainMessage.what = message.what;
                obtainMessage.obj = message.obj;
                obtainMessage.arg1 = message.arg1;
                obtainMessage.arg2 = message.arg2;
                obtainMessage.setData(message.getData());
                handler.sendMessage(obtainMessage);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01e4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01e5, code lost:
        r2 = r0;
        r16 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01ea, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01eb, code lost:
        r22 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01ed, code lost:
        r7 = r8;
        r6 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0201, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0205, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0206, code lost:
        r2 = r0;
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x020b, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x020c, code lost:
        r17 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0220, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0224, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0228, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:?, code lost:
        r16.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0243, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0247, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x024b, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0156, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0157, code lost:
        r7 = r8;
        r6 = r9;
        r12 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x015f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0176, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0178, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0179, code lost:
        r22 = r7;
        r10 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0185, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0186, code lost:
        r22 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01c5, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01c7, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01c8, code lost:
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01cb, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01cc, code lost:
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01cf, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01d0, code lost:
        r7 = r8;
        r6 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01d5, code lost:
        r10 = r10;
        r12 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01da, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01db, code lost:
        r22 = r7;
        r7 = r8;
        r6 = r9;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01e4 A[ExcHandler: all (r0v32 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:32:0x010e] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0205 A[ExcHandler: all (r0v23 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:18:0x0098] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x021c A[SYNTHETIC, Splitter:B:124:0x021c] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0224 A[Catch:{ IOException -> 0x0220 }] */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x023f A[SYNTHETIC, Splitter:B:135:0x023f] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0247 A[Catch:{ IOException -> 0x0243 }] */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x022b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.ArrayList<com.samsung.android.app.twatchmanager.model.InstallPack> startDownloadFile(java.util.ArrayList<com.samsung.android.app.twatchmanager.manager.UHMDownloadManager.DownloadCheckResponse> r26) {
        /*
        // Method dump skipped, instructions count: 699
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.twatchmanager.manager.UHMDownloadManager.startDownloadFile(java.util.ArrayList):java.util.ArrayList");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startDownloadInternal(ArrayList<UHMPackageInfo> arrayList) {
        long j;
        ArrayList<DownloadCheckResponse> arrayList2;
        int size;
        ArrayList<DownloadCheckResponse> downloadMetaData;
        Log.d(TAG, " startDownloadInternal() packageList:" + arrayList);
        int size2 = arrayList.size();
        StringBuilder sb = new StringBuilder();
        HashMap hashMap = new HashMap(size2);
        for (int i = 0; i < size2; i++) {
            UHMPackageInfo uHMPackageInfo = arrayList.get(i);
            sb.append(uHMPackageInfo.packageName);
            if (i != size2 - 1) {
                sb.append("@");
            }
            hashMap.put(uHMPackageInfo.packageName, uHMPackageInfo);
        }
        String downloadCheckServerURL = getDownloadCheckServerURL(sb.toString());
        if (downloadCheckServerURL == null || (downloadMetaData = getDownloadMetaData(downloadCheckServerURL)) == null) {
            j = 0;
            arrayList2 = null;
        } else {
            int size3 = downloadMetaData.size();
            j = 0;
            arrayList2 = null;
            for (int i2 = 0; i2 < size3; i2++) {
                DownloadCheckResponse downloadCheckResponse = downloadMetaData.get(i2);
                UHMPackageInfo uHMPackageInfo2 = downloadCheckResponse != null ? (UHMPackageInfo) hashMap.get(downloadCheckResponse.appId) : null;
                Log.d(TAG, " UHMPackageInfo:{ " + uHMPackageInfo2 + " } downloadMetaData:{" + downloadCheckResponse + " }");
                if (!(downloadCheckResponse == null || uHMPackageInfo2 == null)) {
                    if (downloadCheckResponse.resultCode.equals("1")) {
                        if (uHMPackageInfo2.checkForUpdate && HostManagerUtils.isExistPackage(this.mContext, uHMPackageInfo2.packageName)) {
                            if (InstallationUtils.getVersionCode(uHMPackageInfo2.packageName) >= downloadCheckResponse.versionCode) {
                                Log.d(TAG, "Installed version of " + uHMPackageInfo2.packageName + " is higher. lets skip its download");
                            } else {
                                Log.d(TAG, "Higher version of " + uHMPackageInfo2.packageName + " available. Lets update");
                            }
                        }
                        if (uHMPackageInfo2.packageName.equalsIgnoreCase(this.pluginPackageName)) {
                            Log.d(TAG, "sending plugin name and size to UI = " + downloadCheckResponse.contentSize);
                            Message obtainMessage = this.mDownloadThreadHandler.obtainMessage(InstallationUtils.STATUS_SET_PLUGIN_INFO);
                            Bundle bundle = new Bundle();
                            bundle.putLong(HMConnectFragmentUIHelper.PLUGIN_DOWNLOAD_SIZE, downloadCheckResponse.contentSize);
                            obtainMessage.setData(bundle);
                            sendMessage(obtainMessage);
                        }
                        j += downloadCheckResponse.contentSize;
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>(size2);
                        }
                        arrayList2.add(downloadCheckResponse);
                    } else if (downloadCheckResponse.resultCode.equals(UpdateCheckTask.RESULT_CANT_FIND_APP) && !uHMPackageInfo2.isOptional) {
                        Log.d(TAG, "STATUS_ESSENTIAL_DOWNLOAD_URL_NOT_AVAILABLE");
                        sendEmptyMessage(InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_URL_NOT_AVAILABLE);
                        return;
                    }
                }
            }
        }
        Log.d(TAG, "downloadMetaDataList :" + arrayList2);
        if (arrayList2 != null) {
            Log.d(TAG, "downloadMetaDataList size:" + arrayList2.size());
        }
        if (!HostManagerUtils.hasEnoughStorage(this.mContext, j)) {
            int i3 = (int) (j / 1048576);
            Log.d(TAG, "startDownloadInternal() sizeInMB:" + i3);
            sendMessage(this.mDownloadThreadHandler.obtainMessage(InstallationUtils.STATUS_INSUFFICIENT_STORAGE, i3, 0));
            return;
        }
        int i4 = 0;
        if (arrayList2 != null && arrayList2.size() > 0) {
            Log.d(TAG, "STATUS_ESSENTIAL_DOWNLOAD_START");
            sendEmptyMessage(InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_START);
            ArrayList<InstallPack> startDownloadFile = startDownloadFile(arrayList2);
            if (startDownloadFile != null && (size = startDownloadFile.size()) == arrayList2.size()) {
                while (i4 < size) {
                    InstallPack installPack = startDownloadFile.get(i4);
                    String str = arrayList2.get(i4).signature;
                    if (str != null) {
                        installPack.signature = str;
                    }
                    i4++;
                }
                Message obtainMessage2 = this.mDownloadThreadHandler.obtainMessage();
                obtainMessage2.obj = startDownloadFile;
                Log.d(TAG, "STATUS_ESSENTIAL_DOWNLOAD_SUCCESS");
                obtainMessage2.what = InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_SUCCESS;
                sendMessage(obtainMessage2);
                return;
            }
        } else if (arrayList2 == null || arrayList2.size() == 0) {
            Iterator<UHMPackageInfo> it = arrayList.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (!it.next().isOptional) {
                        i4 = 1;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (i4 == 0) {
                sendEmptyMessage(InstallationUtils.STATUS_DOWNLOAD_NOT_REQUIRED);
                return;
            }
        }
        Log.e(TAG, " startDownloadInternal() Download failed !!!");
        sendEmptyMessage(InstallationUtils.STATUS_ESSENTIAL_DOWNLOAD_FAILED);
    }

    /* access modifiers changed from: package-private */
    public String getDownloadCheckServerURL(String str) {
        String str2;
        String str3;
        String str4 = Build.MODEL;
        if (str4.contains(NEED_TO_SUBSTRING)) {
            str4 = str4.replaceFirst(NEED_TO_SUBSTRING, "");
        }
        String pd = UpdateUtil.getPD();
        if (pd.equals("1")) {
            str3 = "000";
            str2 = "00";
        } else if (HostManagerUtilsNetwork.isNetworkAvailable(this.mContext)) {
            str3 = HostManagerUtilsNetwork.getMCC(this.mContext);
            str2 = HostManagerUtilsNetwork.getMNC(this.mContext);
        } else {
            Log.d(TAG, "Internet connection failed.");
            return null;
        }
        Log.d(TAG, "mcc = getDownloadCheckServerURL.getMCC()-->" + str3);
        Log.d(TAG, "mnc = getDownloadCheckServerURL.getMNC()-->" + str2);
        StringBuilder sb = new StringBuilder();
        sb.append((DOWNLOAD_SERVER_URL + "?appId=" + str) + "&encImei=" + Settings.Secure.getString(this.mContext.getContentResolver(), "android_id"));
        sb.append("&deviceId=");
        if (!HostManagerUtils.isSamsungDevice()) {
            str4 = HostManagerUtils.getResolution(this.mContext);
        }
        sb.append(str4);
        return (((((sb.toString() + "&mcc=" + str3) + "&mnc=" + str2) + "&csc=" + HostManagerUtilsNetwork.getCSC()) + "&sdkVer=" + String.valueOf(Build.VERSION.SDK_INT)) + "&pd=" + pd) + "&callerId=com.samsung.android.app.watchmanager";
    }

    public void setListenerHandler(Handler handler) {
        synchronized (this.downloadMonitor) {
            this.mListenerHandlerWeaKRef = new WeakReference<>(handler);
        }
    }

    public void setPluginPackage(String str) {
        this.pluginPackageName = str;
    }

    public void startDownload(String str) {
        String str2 = TAG;
        Log.d(str2, "startDownload package :" + str);
        if (str != null) {
            ArrayList<UHMPackageInfo> arrayList = new ArrayList<>(1);
            arrayList.add(new UHMPackageInfo(str, false));
            startDownload(arrayList);
        }
    }

    public void startDownload(ArrayList<UHMPackageInfo> arrayList) {
        if (arrayList != null) {
            synchronized (this.downloadMonitor) {
                String str = TAG;
                Log.d(str, "startDownload packageInfoList :" + arrayList);
                if (!this.isRunning) {
                    this.cancel = false;
                    this.mDownloadThreadHandler.post(new DownloadRunnable(arrayList));
                }
            }
        }
    }

    public void stopDownload() {
        synchronized (this.downloadMonitor) {
            this.cancel = true;
            this.isRunning = false;
            nullAndVoidHandler(this.mDownloadThreadHandler);
        }
    }
}
