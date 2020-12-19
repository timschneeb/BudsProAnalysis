package com.samsung.accessory.hearablemgr.core.notification;

import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.uhm.UhmFwUtil;

public class NotificationConstants {
    public static final String ACTION_CAR_MODE_CHECK = "com.sec.android.automotive.drivelink.carmodechanged";
    public static final String ACTION_CHANGE_TTS = "com.samsung.accessory.atticmgr.ACTION_CHANGE_TTS";
    public static final String ACTION_NOTIFICATION_LIST_UPDATE = "android.intent.action.NOTIFICATION_LIST_UPDATE";
    public static final String ACTION_NOTIFICATION_SETTING_UPDATE = "android.intent.action.NOTIFICATION_SETTTING_UPDATE";
    public static final String ACTION_UPDATE_VN_MESSAGE = "com.samsung.accessory.atticmgr.service.UPDATE_VN_EVENT";
    public static String ALARM_PACKAGENAME = "com.sec.android.app.clockpackage";
    public static final String CALENDAR_ACTION_TASK_ALARM = "com.android.calendar.ACTION_TASK_ALARM";
    public static String CALENDAR_PACKAGENAME = "com.android.calendar";
    public static final String CALENDAR_SEND_ALERTINFO_ACTION = "com.android.calendar.SEND_ALERTINFO_ACTION";
    public static final String DUAL = "_DUAL";
    public static String EMAIL_PACKAGENAME = "com.android.email";
    public static String INCOMING_CALL_PACKAGENAME = "incoming call";
    public static Integer[] KNOX_USERID = {95, 96, 97, 98, 99};
    public static String MESSAGE_PACKAGENAME = "com.android.mms";
    public static String MISSED_CALL_PACKAGENAME = "missed call";
    public static final int MSG_RETRIEVE_APPNAME_UPDATE = 3;
    public static final int MSG_RETRIEVE_CHANGE_STATE = 0;
    public static final int MSG_RETRIEVE_LIST_COMPLETE = 1;
    public static final int MSG_RETRIEVE_LIST_UPDATE = 2;
    public static String NOTIFICATION_TYPE_DETAIL = "detail";
    public static String NOTIFICATION_TYPE_OFF = "off";
    public static String NOTIFICATION_TYPE_ON = "on";
    public static String NOTIFICATION_TYPE_SUMMARY = "summary";
    public static final String PEOPLE_STRIPE_APP_PACKAGENAME = "com.samsung.android.service.peoplestripe";
    public static final String PREFERENCE_VN_APP_DETAIL = "vn_detail_";
    public static final String PREFERENCE_VN_APP_ENABLE = "vn_enable_";
    public static final String VN_MESSAGE = "vn_extra_msg";
    public static final String VN_USAGE = "vn_extra_usage";
    public static String ZEN_MODE = "zen_mode";
    public static int ZEN_MODE_OFF;
    public static String[] exceptionList = {MESSAGE_PACKAGENAME, "com.android.email", "com.kddi.android.cmail", "com.samsung.android.email.ui"};
    public static String[] excludeApps = {UhmFwUtil.UHM_PACKAGE_NAME, UhmFwUtil.UHM_PACKAGE_NAME_RIZE, UhmFwUtil.PACKAGE_NAME_HOST_MANAGER, "com.samsung.android.app.watchmanagerstub", "com.sec.keystringscreen", "com.android.development", "com.sec.android.quickmemo", "com.sec.android.app.popupcalculator", "com.sec.android.app.camera", "com.android.chrome", "com.android.contacts", "com.samsung.contacts", "com.samsung.android.contacts", "com.android.phone", "com.android.server.telecom", "com.sec.android.gallery3d", "com.google.android.apps.books", GooglePlayServicesUtilLight.GOOGLE_PLAY_GAMES_PACKAGE, "com.google.android.videos", "com.google.android.music", "com.google.android.gms", "com.samsung.groupcast", "com.google.android.apps.magazines", "com.android.vending", "com.google.android.apps.docs", "com.dropbox.android", "com.sec.android.app.sbrowser", "com.sec.kidsplat.installer", "com.sec.android.app.kidshome", "com.google.android.apps.maps", "com.sec.android.app.music", "com.sec.android.app.myfiles", "com.sec.penup", "com.samsung.android.snote", "com.vlingo.midas", "com.sec.android.app.samsungapps", "com.samsung.everglades.video", "com.samsung.android.app.pinboard", "com.android.settings", "com.sec.android.app.voicenote", "com.sec.android.app.voicerecorder", "co.kr.pluu.appinfo", "com.google.android.youtube", "com.sec.android.app.dmb", "com.diotek.diodict4.EDictionary", "com.sec.android.wallet", "com.sec.android.widgetapp.ap.hero.accuweather", "com.sec.android.GeoLookout", "com.samsung.android.app.memo", "com.kddi.disasterapp", "com.skt.skaf.l001f00006", "com.kddi.market", "com.sec.android.voltesettings", "com.pinsightmedia.vpl.Amazon", "com.skt.prod.tphonelite", "com.android.dialer", "com.sonyericsson.organizer", "com.nttdocomo.android.schedulememo", "com.samsung.android.app.advsounddetector", "com.google.android.dialer", "com.samsung.android.dialer"};
    public static String[] mAlertApps = {"incoming call", "missed call", "com.sec.android.app.clockpackage", "com.android.calendar", "com.android.mms", "com.android.email"};
    public static int[] mAlertApps_Name = {R.string.notification_incoming_call, R.string.notification_missed_call, R.string.notification_alarm, R.string.notification_schedule, R.string.notification_messages, R.string.notification_email};
}
