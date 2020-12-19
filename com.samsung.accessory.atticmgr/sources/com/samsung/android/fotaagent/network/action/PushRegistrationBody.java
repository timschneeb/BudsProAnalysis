package com.samsung.android.fotaagent.network.action;

import android.text.TextUtils;
import android.util.Xml;
import com.accessorydm.db.file.AccessoryInfoAdapter;
import com.accessorydm.tp.urlconnect.HttpNetworkInterface;
import com.samsung.android.fotaprovider.log.Log;
import java.io.StringWriter;
import org.xmlpull.v1.XmlSerializer;

class PushRegistrationBody {
    private static final String TAG_DEVICEID = "deviceID";
    private static final String TAG_IPPUSHINFO = "PushInfoVO";
    private static final String TAG_PUSHTYPE = "pushType";
    private static final String TAG_REGISTERID = "registrationID";

    PushRegistrationBody() {
    }

    static String get(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
                XmlSerializer newSerializer = Xml.newSerializer();
                StringWriter stringWriter = new StringWriter();
                newSerializer.setOutput(stringWriter);
                newSerializer.startDocument(HttpNetworkInterface.XTP_HTTP_UTF8, true);
                newSerializer.startTag("", TAG_IPPUSHINFO);
                newSerializer.startTag("", TAG_DEVICEID);
                newSerializer.text(new AccessoryInfoAdapter().getDeviceId());
                newSerializer.endTag("", TAG_DEVICEID);
                newSerializer.startTag("", TAG_PUSHTYPE);
                if (!TextUtils.isEmpty(str)) {
                    newSerializer.text("FCM");
                } else {
                    newSerializer.text("SPP");
                }
                newSerializer.endTag("", TAG_PUSHTYPE);
                newSerializer.startTag("", TAG_REGISTERID);
                if (!TextUtils.isEmpty(str)) {
                    newSerializer.text(str);
                } else {
                    newSerializer.text(str2);
                }
                newSerializer.endTag("", TAG_REGISTERID);
                newSerializer.endTag("", TAG_IPPUSHINFO);
                newSerializer.endDocument();
                return stringWriter.toString();
            }
            Log.W("No push ID to register");
            return null;
        } catch (Exception e) {
            Log.printStackTrace(e);
            return null;
        }
    }
}
