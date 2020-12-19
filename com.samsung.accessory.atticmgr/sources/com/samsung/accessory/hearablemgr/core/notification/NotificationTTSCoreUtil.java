package com.samsung.accessory.hearablemgr.core.notification;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.text.format.DateFormat;
import android.widget.Toast;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.android.SDK.routine.Constants;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import seccompat.android.util.Log;

public class NotificationTTSCoreUtil {
    private static final String TAG = "Attic_NotificationTTSCoreUtil";

    static String getNotificationMessageString(NotificationMessage notificationMessage, Context context, TextToSpeech textToSpeech) {
        String str;
        String str2;
        String str3;
        String[] strArr = new String[3];
        Resources resources = context.getResources();
        String str4 = null;
        if (!isAvailableTTS(context, textToSpeech)) {
            Log.d(TAG, "Not available locales for TTS");
            Toast.makeText(context, resources.getString(R.string.no_tts_voice_data), 0).show();
            return null;
        }
        Log.v(TAG, "available locales for TTS");
        strArr[0] = notificationMessage.getAppName();
        Log.d(TAG, "VN msg.getAppName() = " + strArr[0]);
        strArr[1] = notificationMessage.getMain();
        strArr[2] = notificationMessage.getBody();
        int type = notificationMessage.getType();
        String str5 = "";
        if (type != 4869) {
            switch (type) {
                case NotificationMessage.TYPE_ALARM:
                    if (notificationMessage.getWhen() != 0) {
                        int when = ((int) notificationMessage.getWhen()) / 100;
                        int when2 = ((int) notificationMessage.getWhen()) % 100;
                        Calendar instance = Calendar.getInstance();
                        String format = DateFormat.getTimeFormat(context).format(new Date(instance.get(1), instance.get(2) + 1, instance.get(5), when, when2));
                        str5 = (notificationMessage.getBody() == null || notificationMessage.getBody().equals(str5)) ? " " + String.format(resources.getString(R.string.vn_alarm_string), format) : " " + String.format(resources.getString(R.string.vn_alarm_string2), notificationMessage.getBody(), format);
                    }
                    String str6 = strArr[0] + str5;
                    Log.d(TAG, "VN TYPE_ALARM.");
                    return str6;
                case NotificationMessage.TYPE_SCHEDULE:
                    if (notificationMessage.getMain() == null && notificationMessage.getWhen() == 0) {
                        Log.d(TAG, "read app name");
                        String str7 = strArr[0];
                        Log.d(TAG, "VN TYPE_SCHEDULE.");
                        return str7;
                    }
                    if (notificationMessage.getMain() == null || notificationMessage.getMain().equals(str5)) {
                        str = String.format(resources.getString(R.string.vn_schedule_string), getCalendarTimeString(context, notificationMessage.getWhen(), textToSpeech));
                    } else {
                        str = String.format(resources.getString(R.string.vn_schedule_with_title_string), notificationMessage.getMain(), getCalendarTimeString(context, notificationMessage.getWhen(), textToSpeech));
                    }
                    Log.d(TAG, "VN TYPE_SCHEDULE.");
                    return str;
                case NotificationMessage.TYPE_CALL:
                    String str8 = strArr[1];
                    if (str8 != null) {
                        if (str8.length() == 0 || str8.equals(str5)) {
                            Log.d(TAG, "VN TYPE_CALL. number is blank");
                            str3 = resources.getString(R.string.notification_call_unknown);
                        } else if (str8.equals("PRIVATE NUMBER")) {
                            str3 = resources.getString(R.string.notification_call_privatenum);
                        } else {
                            String contactName = getContactName(context, str8);
                            if (contactName == null || contactName.equals(str5)) {
                                Log.d(TAG, "VN TYPE_CALL. number has no contact name");
                                str3 = arrangeCaller(context, str8);
                            } else {
                                str3 = contactName;
                            }
                        }
                        str2 = String.format(resources.getString(R.string.vn_call_string), str3);
                    } else {
                        str2 = strArr[0];
                    }
                    Log.d(TAG, "VN TYPE_CALL.");
                    return str2;
                default:
                    if (strArr[0].equals("TTS_simpleText")) {
                        return strArr[1];
                    }
                    return str5;
            }
        } else {
            if (notificationMessage.getPkgName().equals(NotificationConstants.MISSED_CALL_PACKAGENAME)) {
                String str9 = strArr[1];
                if (NotificationUtil.getAppNotificationDetails(NotificationConstants.MISSED_CALL_PACKAGENAME).equals(NotificationConstants.NOTIFICATION_TYPE_DETAIL)) {
                    strArr[0] = null;
                    String contactName2 = getContactName(context, str9);
                    if (contactName2 == null || contactName2.equals(str5)) {
                        Log.d(TAG, "VN TYPE_MISSEDCALL. number has no contact name");
                        contactName2 = arrangeCaller(context, str9);
                    }
                    str4 = context.getString(R.string.vn_missedcall_string, contactName2);
                } else {
                    strArr[0] = context.getResources().getString(R.string.notification_missed_call);
                }
                strArr[1] = str4;
            }
            for (int i = 0; i < 3; i++) {
                if (strArr[i] != null) {
                    str5 = str5 + ", " + strArr[i];
                }
            }
            Log.d(TAG, "VN TYPE_NORMAL");
            return str5;
        }
    }

    static boolean isAvailableTTS(Context context, TextToSpeech textToSpeech) {
        Locale tTSLanguage = getTTSLanguage(textToSpeech);
        Log.d(TAG, "getNotificationMessageString():getTTSLanguage(mTTS) = " + getTTSLanguage(textToSpeech));
        if (tTSLanguage == null) {
            tTSLanguage = context.getResources().getConfiguration().locale;
            Log.d(TAG, "if TTS locale is null, set System Language to TTS Language. locale = " + tTSLanguage);
        }
        Preferences.putString(PreferenceKey.NOTIFICATION_LANGUAGE, tTSLanguage.getLanguage());
        Log.d(TAG, "getNotificationMessageString():locale.getLanguage() = " + tTSLanguage.getLanguage());
        Log.d(TAG, "TTS engine : " + textToSpeech.getDefaultEngine() + ", set lang result = " + textToSpeech.setLanguage(tTSLanguage) + " lang available result = " + textToSpeech.isLanguageAvailable(tTSLanguage));
        boolean z = textToSpeech.setLanguage(tTSLanguage) >= 0;
        if ((!Util.isSamsungDevice() || !"com.samsung.SMT".equals(textToSpeech.getDefaultEngine())) && textToSpeech.isLanguageAvailable(tTSLanguage) < 1) {
            return false;
        }
        return z;
    }

    static Locale getTTSLanguage(TextToSpeech textToSpeech) {
        try {
            if (Build.VERSION.SDK_INT <= 21) {
                return textToSpeech.getDefaultLanguage();
            }
            if (textToSpeech.getDefaultVoice() == null) {
                return null;
            }
            return textToSpeech.getDefaultVoice().getLocale();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getContactName(Context context, String str) {
        int columnIndex;
        Cursor cursor = null;
        if (str == null) {
            return null;
        }
        Log.d(TAG, "getContactName()");
        String str2 = "";
        try {
            Cursor query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{Constants.EXTRA_ID, "display_name"}, null, null, null);
            if (!(query == null || !query.moveToFirst() || (columnIndex = query.getColumnIndex("display_name")) == -1)) {
                str2 = query.getString(columnIndex);
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception unused) {
            if (0 != 0) {
                cursor.close();
            }
        }
        return str2;
    }

    private static String arrangeCaller(Context context, String str) {
        Log.d(TAG, "arrangeCaller()");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) == '-' || str.charAt(i) == '+')) {
                sb.append(str.charAt(i));
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private static String getCalendarTimeString(Context context, long j, TextToSpeech textToSpeech) {
        if (textToSpeech == null || getTTSLanguage(textToSpeech) == null) {
            return "";
        }
        return DateFormat.getTimeFormat(context).format(new Date(j));
    }
}
