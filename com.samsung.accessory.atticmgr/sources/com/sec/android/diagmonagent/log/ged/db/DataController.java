package com.sec.android.diagmonagent.log.ged.db;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.dao.ServiceDao;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.scheduler.GEDScheduler;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.ValidationUtils;
import com.sec.android.diagmonagent.log.provider.utils.BundleContract;

public class DataController {
    public static void sendSRObj(Context context, Bundle bundle) {
        try {
            if (validValues(bundle)) {
                ServiceDao serviceDao = GEDDatabase.get(context).getServiceDao();
                ServiceInfo serviceInfo = serviceDao.getServiceInfo();
                ServiceInfo makeServiceInfo = makeServiceInfo(bundle);
                makeServiceInfo.setServiceAgreeType("S");
                if (serviceInfo == null) {
                    makeServiceInfo.setDeviceId(DeviceUtils.generateRandomDeviceId(makeServiceInfo.getDeviceId()));
                    if (TextUtils.isEmpty(makeServiceInfo.getDeviceId())) {
                        AppLog.w("device id must not be empty");
                        return;
                    } else {
                        AppLog.i("register new service");
                        serviceDao.insert(makeServiceInfo);
                    }
                } else if (!serviceInfo.getServiceId().equals(makeServiceInfo.getServiceId())) {
                    Log.w(DeviceUtils.TAG, "different from registered service id");
                    AppLog.w("different from registered service id");
                    return;
                } else if (isAvailableService(serviceInfo.getStatus())) {
                    if (TextUtils.isEmpty(makeServiceInfo.getDeviceId())) {
                        makeServiceInfo.setDeviceId(serviceInfo.getDeviceId());
                    }
                    if (needToUpdateService(serviceInfo, makeServiceInfo)) {
                        serviceDao.update(makeServiceInfo);
                        AppLog.i("update service");
                    } else {
                        AppLog.i("service doesn't need to update");
                    }
                } else {
                    return;
                }
                if (!GEDScheduler.isGEDJobServiceRegistered(context)) {
                    AppLog.i("Register GED job service");
                    GEDScheduler.registerJob(context);
                    return;
                }
                AppLog.i("GED job service is already registered");
            }
        } catch (Exception e) {
            AppLog.e("failed to set configuration: " + e);
        }
    }

    public static boolean eventReport(Context context, Bundle bundle, String str) {
        try {
            ServiceInfo serviceInfo = GEDDatabase.get(context).getServiceDao().getServiceInfo();
            if (serviceInfo == null) {
                AppLog.w("setConfiguration should be called first");
                return false;
            } else if (!validValues(bundle)) {
                return false;
            } else {
                Event makeEvent = makeEvent(bundle);
                makeEvent.setServiceAgreeType("S");
                if (!serviceInfo.getServiceId().equals(makeEvent.getServiceId())) {
                    AppLog.w("service id is different");
                    return false;
                } else if (!isAvailableService(serviceInfo.getStatus())) {
                    return false;
                } else {
                    if (TextUtils.isEmpty(makeEvent.getDeviceId())) {
                        makeEvent.setDeviceId(serviceInfo.getDeviceId());
                    }
                    String zipWithMetaFile = DeviceUtils.zipWithMetaFile(context, str, makeEvent.getDeviceId(), makeEvent.getExtension(), makeEvent.getMemory(), makeEvent.getStorage());
                    if (TextUtils.isEmpty(zipWithMetaFile)) {
                        return false;
                    }
                    makeEvent.setLogPath(zipWithMetaFile);
                    if (!ValidationUtils.isValidWithPolicy(context, makeEvent)) {
                        DeviceUtils.deleteFiles(context.getFilesDir().getAbsolutePath() + "/" + zipWithMetaFile);
                        return false;
                    }
                    AppLog.i("insert event");
                    GEDDatabase.get(context).getEventDao().insert(makeEvent);
                    return true;
                }
            }
        } catch (Exception e) {
            AppLog.e("failed to send event report " + e);
            return false;
        }
    }

    private static boolean validValues(Bundle bundle) {
        String string = bundle.getString("serviceAgreeType", "");
        if (!"G".equals(string)) {
            AppLog.w("invalid service agree type: " + string);
            return false;
        }
        String string2 = bundle.getString("sdkType", "");
        if ("G".equals(string2)) {
            return true;
        }
        AppLog.w("Invalid sdk type: " + string2);
        return false;
    }

    private static boolean isAvailableService(int i) {
        if (i != 2 && i != 3) {
            return true;
        }
        AppLog.w("Not available service: " + i);
        return false;
    }

    private static boolean needToUpdateService(ServiceInfo serviceInfo, ServiceInfo serviceInfo2) {
        String serviceVersion = serviceInfo2.getServiceVersion();
        String sdkVersion = serviceInfo2.getSdkVersion();
        String deviceId = serviceInfo2.getDeviceId();
        if (TextUtils.isEmpty(serviceVersion) || TextUtils.isEmpty(sdkVersion) || TextUtils.isEmpty(deviceId)) {
            return false;
        }
        if (!serviceVersion.equals(serviceInfo.getServiceVersion()) || !sdkVersion.equals(serviceInfo.getSdkVersion()) || !deviceId.equals(serviceInfo.getDeviceId())) {
            return true;
        }
        return false;
    }

    private static ServiceInfo makeServiceInfo(Bundle bundle) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId(bundle.getString("serviceId", ""));
        serviceInfo.setTrackingId(bundle.getString("trackingId", ""));
        serviceInfo.setDeviceId(bundle.getString("deviceId", ""));
        serviceInfo.setServiceVersion(bundle.getString("serviceVersion", ""));
        serviceInfo.setServiceAgreeType(bundle.getString("serviceAgreeType", ""));
        serviceInfo.setSdkVersion(bundle.getString("sdkVersion", ""));
        serviceInfo.setSdkType(bundle.getString("sdkType", ""));
        serviceInfo.setTimestamp(System.currentTimeMillis());
        return serviceInfo;
    }

    private static Event makeEvent(Bundle bundle) {
        Event event = new Event();
        event.setServiceId(bundle.getString("serviceId", ""));
        event.setDeviceId(bundle.getString("deviceId", ""));
        event.setServiceVersion(bundle.getString("serviceVersion", ""));
        event.setServiceAgreeType(bundle.getString("serviceAgreeType", ""));
        event.setSdkVersion(bundle.getString("sdkVersion", ""));
        event.setSdkType(bundle.getString("sdkType", ""));
        event.setServiceDefinedKey(bundle.getString("serviceDefinedKey", ""));
        event.setErrorCode(bundle.getString("errorCode", ""));
        event.setDescription(bundle.getString(BundleContract.DESCRIPTION, ""));
        event.setRelayClientVersion(bundle.getString("relayClientVersion", ""));
        event.setRelayClientType(bundle.getString("relayClientType", ""));
        event.setExtension(bundle.getString("extension", ""));
        event.setNetworkMode(bundle.getBoolean(BundleContract.NETWORK_MODE, true));
        event.setMemory(bundle.getString("memory", ""));
        event.setStorage(bundle.getString("storage", ""));
        event.setTimestamp(System.currentTimeMillis());
        return event;
    }

    public static String getDeviceId(Context context) {
        try {
            ServiceInfo serviceInfo = GEDDatabase.get(context).getServiceDao().getServiceInfo();
            if (serviceInfo == null) {
                return "";
            }
            if ("S".equals(serviceInfo.getServiceAgreeType())) {
                return serviceInfo.getDeviceId();
            }
            AppLog.d("You can't use getDeviceId");
            return "";
        } catch (Exception e) {
            AppLog.e("failed to get device id " + e);
            return "";
        }
    }

    public static void showAllDatabase(Context context) {
        GEDDatabase.get(context).getServiceDao().printAllServiceInfo();
        GEDDatabase.get(context).getEventDao().printAllEvent();
        GEDDatabase.get(context).getResultDao().printAllResult();
    }
}
