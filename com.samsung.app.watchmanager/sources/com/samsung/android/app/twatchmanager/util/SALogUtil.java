package com.samsung.android.app.twatchmanager.util;

import c.b.b.a.a.e;
import c.b.b.a.a.g;
import c.b.b.a.a.h;
import com.samsung.android.app.twatchmanager.log.Log;
import java.util.HashMap;

public class SALogUtil {
    public static final String SA_LOG_EVENT_CANCEL_INSTALL = "5108";
    public static final String SA_LOG_EVENT_CONTACT_US = "5101";
    public static final String SA_LOG_EVENT_DEVICE_LIST_MINGES_NOT_HERE = "5127";
    public static final String SA_LOG_EVENT_DEVICE_LIST_SCAN_FOR_NEARBY = "5128";
    public static final String SA_LOG_EVENT_GM_PAIRING_CANCEL = "5125";
    public static final String SA_LOG_EVENT_GM_PAIRING_OK = "5126";
    public static final String SA_LOG_EVENT_MINES_NOT_HERE = "5124";
    public static final String SA_LOG_EVENT_OK_CLICK = "5146";
    public static final String SA_LOG_EVENT_PAIRING_CANCEL = "5104";
    public static final String SA_LOG_EVENT_PAIRING_CANCEL_CONFIRM_PASSKEY = "5147";
    public static final String SA_LOG_EVENT_PAIRING_OK = "5105";
    public static final String SA_LOG_EVENT_PAIRING_OK_CONFIRM_PASSKEY = "5601";
    public static final String SA_LOG_EVENT_PICK_ITEM = "5123";
    public static final String SA_LOG_EVENT_SELECT_DEVICE = "5106";
    public static final String SA_LOG_EVENT_START_JOURNEY = "5122";
    public static final String SA_LOG_SCREEN_PAIRING = "516";
    public static final String SA_LOG_SCREEN_PAIRING_CONFIRM_PASSKEY = "561";
    public static final String SA_LOG_SCREEN_PERMISSION_GUIDE = "550";
    public static final String SA_LOG_SCREEN_PICK_GEAR = "515";
    public static final String SA_LOG_SCREEN_SETUP_DEVICE_LIST = "512";
    public static final String SA_LOG_SCREEN_SETUP_DEVICE_LIST_NO_GEAR = "513";
    public static final String SA_LOG_SCREEN_SETUP_INSTALL_PLUGIN = "514";
    public static final String SA_LOG_SCREEN_WELCOME = "510";
    public static final String SA_TRACKING_ID = "703-399-564897";
    public static final String SA_WATCHMANMAGER_UI_VER = "1.8.1";
    public static final String TAG = ("tUHM:" + SALogUtil.class.getSimpleName());

    public static void insertSALog(String str) {
        String str2 = TAG;
        Log.d(str2, "screenID = " + str);
        if (str != null) {
            h.a().a(((g) new g().a(str)).a());
        }
    }

    public static void insertSALog(String str, String str2) {
        String str3 = TAG;
        Log.d(str3, "screenID = " + str + " / event = " + str2);
        if (str != null) {
            h a2 = h.a();
            e eVar = (e) new e().a(str);
            eVar.b(str2);
            a2.a(eVar.a());
        }
    }

    public static void insertSALog(String str, String str2, String str3) {
        String str4 = TAG;
        Log.d(str4, "screenID = " + str + " / eventID = " + str2 + " / eventName = " + str3);
        if (str != null) {
            try {
                h a2 = h.a();
                e eVar = (e) new e().a(str);
                eVar.b(str2);
                a2.a(eVar.a());
            } catch (Exception e) {
                String str5 = TAG;
                Log.e(str5, "error: " + e);
                e.printStackTrace();
            }
        }
    }

    public static void insertSALog(String str, String str2, String str3, String str4) {
        String str5 = TAG;
        Log.d(str5, "screenID = " + str + " / eventID = " + str2 + " / eventName = " + str3 + " / detail = " + str4);
        if (str != null) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("det", str4);
                h a2 = h.a();
                e eVar = (e) new e().a(str);
                eVar.b(str2);
                a2.a(((e) eVar.a(hashMap)).a());
            } catch (Exception e) {
                String str6 = TAG;
                Log.e(str6, "error: " + e);
                e.printStackTrace();
            }
        }
    }
}
