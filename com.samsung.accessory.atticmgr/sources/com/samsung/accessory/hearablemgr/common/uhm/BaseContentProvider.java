package com.samsung.accessory.hearablemgr.common.uhm;

import android.net.Uri;

public class BaseContentProvider {
    public static final Uri APP_CONTENT_URI = Uri.parse("content://com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider/Apps");
    public static final String APP_NAME = "app_name";
    public static final String AUTHORITY = "com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider";
    public static final String DEVICE_BT_ID = "bt_id";
    public static final String DEVICE_CONNECTED = "connected";
    public static final Uri DEVICE_CONTENT_URI = Uri.parse("content://com.samsung.android.uhm.framework.appregistry.BaseContentProvider.provider/Device");
    public static final String DEVICE_FEATURE_NECKLETAUTOCONNECTION = "necklet_auto_connection";
    public static final String DEVICE_FIXED_NAME = "device_fixed_name";
    public static final String DEVICE_NAME = "device_name";
    public static final String LAST_LAUNCH = "last_launch";
    public static final String PACKAGE_NAME = "package_name";
    public static final String UPDATE_CANCEL_COUNT = "update_cancel_count";
    public static final String VERSION = "version";
}
