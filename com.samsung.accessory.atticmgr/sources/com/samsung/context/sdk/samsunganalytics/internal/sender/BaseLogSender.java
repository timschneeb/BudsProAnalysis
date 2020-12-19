package com.samsung.context.sdk.samsunganalytics.internal.sender;

import android.content.Context;
import android.text.TextUtils;
import com.accessorydm.interfaces.XDBInterface;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.Executor;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.Map;

public abstract class BaseLogSender implements LogSender {
    protected Configuration configuration;
    protected Context context;
    protected DeviceInfo deviceInfo;
    protected Executor executor = SingleThreadExecutor.getInstance();
    protected Manager manager;

    public BaseLogSender(Context context2, Configuration configuration2) {
        this.context = context2.getApplicationContext();
        this.configuration = configuration2;
        this.deviceInfo = new DeviceInfo(context2);
        this.manager = Manager.getInstance(context2, configuration2);
    }

    /* access modifiers changed from: protected */
    public Map<String, String> setCommonParamToLog(Map<String, String> map) {
        if (PolicyUtils.getSenderType() < 2) {
            map.put("la", this.deviceInfo.getLanguage());
            if (!TextUtils.isEmpty(this.deviceInfo.getMcc())) {
                map.put(XDBInterface.XDM_SQL_ACCESSORY_MCC, this.deviceInfo.getMcc());
            }
            if (!TextUtils.isEmpty(this.deviceInfo.getMnc())) {
                map.put("mnc", this.deviceInfo.getMnc());
            }
            map.put("dm", this.deviceInfo.getDeviceModel());
            map.put("auid", this.configuration.getDeviceId());
            map.put("do", this.deviceInfo.getAndroidVersion());
            map.put("av", this.deviceInfo.getAppVersionName());
            map.put("uv", this.configuration.getVersion());
            map.put("at", String.valueOf(this.configuration.getAuidType()));
            map.put("fv", this.deviceInfo.getFirmwareVersion());
            map.put("tid", this.configuration.getTrackingId());
        }
        map.put("tz", this.deviceInfo.getTimeZoneOffset());
        return map;
    }

    /* access modifiers changed from: protected */
    public String makeBodyString(Map<String, String> map) {
        return Utils.makeDelimiterString(map, Utils.Depth.ONE_DEPTH);
    }

    /* access modifiers changed from: protected */
    public void insert(Map<String, String> map) {
        this.manager.insert(new SimpleLog(map.get("t"), Long.valueOf(map.get("ts")).longValue(), makeBodyString(setCommonParamToLog(map)), getLogType(map)));
    }

    /* access modifiers changed from: protected */
    public LogType getLogType(Map<String, String> map) {
        return Utils.getTypeForServer(map.get("t"));
    }
}
