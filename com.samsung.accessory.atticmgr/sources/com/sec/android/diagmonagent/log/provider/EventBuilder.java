package com.sec.android.diagmonagent.log.provider;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.provider.utils.DiagMonUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class EventBuilder {
    private Context mContext;
    private String mDescription = "";
    private String mErrorCode = "";
    private JSONObject mExtData = null;
    public boolean mIsCalledNetworkMode = false;
    private String mLogPath = "";
    private boolean mNetworkMode = true;
    private String mRelayClientType = "";
    private String mRelayClientVer = "";
    private String mServiceDefinedKey = "";

    public EventBuilder(Context context) {
        this.mContext = context;
    }

    public JSONObject getMemory() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("VM", getVmMemory());
            jSONObject.put("NATIVE", getNativeMemory());
            AppLog.d(jSONObject.toString());
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    private JSONObject getVmMemory() {
        Runtime runtime = Runtime.getRuntime();
        long j = runtime.totalMemory() >> 20;
        long freeMemory = runtime.freeMemory() >> 20;
        long maxMemory = runtime.maxMemory() >> 20;
        AppLog.d("[VM] TotalMemory : " + j + " FreeMemory : " + freeMemory + " maxMemory : " + maxMemory);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("TOTAL", j);
            jSONObject.put("FREE", freeMemory);
            jSONObject.put("MAX", maxMemory);
        } catch (JSONException e) {
            AppLog.e(e.getMessage());
        }
        return jSONObject;
    }

    private JSONObject getNativeMemory() {
        long nativeHeapFreeSize = Debug.getNativeHeapFreeSize() >> 20;
        long nativeHeapSize = Debug.getNativeHeapSize() >> 20;
        long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize() >> 20;
        AppLog.d("[NativeHeap] nativeHeapSize : " + nativeHeapSize + " nativeHeapFree : " + nativeHeapFreeSize + " nativeHeapAllocatedSize : " + nativeHeapAllocatedSize);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("HEAP_SIZE", nativeHeapSize);
            jSONObject.put("HEAP_FREE", nativeHeapFreeSize);
            jSONObject.put("HEAD_ALLOC", nativeHeapAllocatedSize);
        } catch (JSONException e) {
            AppLog.e(e.getMessage());
        }
        return jSONObject;
    }

    public JSONObject getInternalStorageSize() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("TOTAL", getTotalInternalStorageSize() >> 20);
            jSONObject.put("FREE", getAvailableInternalStorageSize() >> 20);
        } catch (JSONException e) {
            AppLog.e(e.getMessage());
        }
        return jSONObject;
    }

    private static long getTotalInternalStorageSize() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
    }

    public static long getAvailableInternalStorageSize() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
    }

    public String getLogPath() {
        return this.mLogPath;
    }

    public EventBuilder setLogPath(String str) {
        this.mLogPath = str;
        return this;
    }

    public EventBuilder setServiceDefinedKey(String str) {
        if (DiagMonUtil.checkDMA(this.mContext) != 1 || TextUtils.isEmpty(str) || !str.contains("/")) {
            this.mServiceDefinedKey = str;
            return this;
        }
        AppLog.w("delimiter is included : " + str);
        return this;
    }

    public String getServiceDefinedKey() {
        return this.mServiceDefinedKey;
    }

    public EventBuilder setErrorCode(String str) {
        this.mErrorCode = str;
        return this;
    }

    public String getErrorCode() {
        return this.mErrorCode;
    }

    public EventBuilder setNetworkMode(boolean z) {
        this.mIsCalledNetworkMode = true;
        this.mNetworkMode = z;
        return this;
    }

    public boolean getNetworkMode() {
        return this.mNetworkMode;
    }

    public EventBuilder setDescription(String str) {
        this.mDescription = str;
        return this;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public EventBuilder setRelayClientVer(String str) {
        this.mRelayClientVer = str;
        return this;
    }

    public String getRelayClientVer() {
        return this.mRelayClientVer;
    }

    public EventBuilder setRelayClientType(String str) {
        this.mRelayClientType = str;
        return this;
    }

    public String getRelayClientType() {
        return this.mRelayClientType;
    }

    public EventBuilder setExtData(JSONObject jSONObject) {
        this.mExtData = jSONObject;
        return this;
    }

    public String getExtData() {
        JSONObject jSONObject = this.mExtData;
        if (jSONObject == null) {
            return "";
        }
        return jSONObject.toString();
    }
}
