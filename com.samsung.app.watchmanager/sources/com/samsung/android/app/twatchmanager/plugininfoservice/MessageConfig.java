package com.samsung.android.app.twatchmanager.plugininfoservice;

import com.samsung.android.app.twatchmanager.update.SAGUIDHelper;
import com.samsung.android.app.twatchmanager.update.UpdateManager;
import com.samsung.android.app.twatchmanager.util.GlobalConst;

public class MessageConfig {
    public static final String RETURN_TYPE_BOOLEAN = "boolean";
    public static final String RETURN_TYPE_INT = "int";
    public static final String RETURN_TYPE_JSONARRAY = "jsonArray";
    public static final String RETURN_TYPE_STRING = "string";

    public enum Key {
        SUPPORTED_API_LIST_RESULT("result", MessageConfig.RETURN_TYPE_STRING),
        INSTALLED_PLUGIN_LIST_RESULT("result", MessageConfig.RETURN_TYPE_STRING);
        
        public final String KEY;
        public final String TYPE;

        private Key(String str, String str2) {
            this.KEY = str;
            this.TYPE = str2;
        }
    }

    public enum Type {
        SUPPORTED_API_LIST("MSG_WHAT_GETTING_SUPPORTED_API_LIST", SAGUIDHelper.GUID_REQUEST_ID, "MSG_WHAT_GETTING_SUPPORTED_API_LIST_RESULT", UpdateManager.UPDATE_CHECK_TIMEOUT_PER_REQUESET, MessageConfig.RETURN_TYPE_JSONARRAY),
        INSTALLED_PLUGIN_LIST("MSG_WHAT_GETTING_WATCH_PLUGIN_LIST_INFO", GlobalConst.LAUNCH_MODE_IDLE, "MSG_WHAT_GETTING_WATCH_PLUGIN_LIST_INFO_RESULT", 2001, MessageConfig.RETURN_TYPE_JSONARRAY);
        
        public final int REQUEST_ID;
        public final String REQUEST_MESSAGE_NAME;
        public final int RESPONSE_ID;
        public final String RESPONSE_MESSAGE_NAME;
        public final String RETURN_TYPE;

        private Type(String str, int i, String str2, int i2, String str3) {
            this.REQUEST_MESSAGE_NAME = str;
            this.REQUEST_ID = i;
            this.RESPONSE_MESSAGE_NAME = str2;
            this.RESPONSE_ID = i2;
            this.RETURN_TYPE = str3;
        }
    }
}
