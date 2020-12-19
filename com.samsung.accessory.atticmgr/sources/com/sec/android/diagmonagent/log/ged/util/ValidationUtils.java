package com.sec.android.diagmonagent.log.ged.util;

import android.content.Context;
import android.text.TextUtils;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.GEDDatabase;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class ValidationUtils {
    private static final String UPLOAD_VALUE_BLOCKED = "0";

    public static boolean isValidWithPolicy(Context context, Event event) {
        boolean z;
        boolean z2;
        boolean z3;
        File file = new File(context.getFilesDir() + "/" + event.getLogPath());
        List<Event> unreportedAllEvents = GEDDatabase.get(context).getEventDao().getUnreportedAllEvents();
        AppLog.d("Size of eventList : " + unreportedAllEvents.size());
        if (TextUtils.isEmpty(PreferenceUtils.getUploadFileValue(context)) || !"0".equals(PreferenceUtils.getUploadFileValue(context))) {
            z = false;
        } else {
            if (!TextUtils.isEmpty(PreferenceUtils.getUploadFileServiceVersion(context))) {
                try {
                    Iterator it = Arrays.asList(PreferenceUtils.getUploadFileServiceVersion(context).split(",")).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (event.getServiceVersion().matches((String) it.next())) {
                            if (!TextUtils.isEmpty(PreferenceUtils.getUploadFileErrorCode(context))) {
                                List asList = Arrays.asList(PreferenceUtils.getUploadFileErrorCode(context).split(","));
                                if (asList.size() > 0 && event.getErrorCode() != null && asList.contains(event.getErrorCode())) {
                                    AppLog.d("The event is blocked because of Service version & Error code : " + event.getErrorCode());
                                    return false;
                                }
                            } else {
                                AppLog.d("The event is blocked because of Service version : " + event.getServiceVersion());
                                return false;
                            }
                        }
                    }
                } catch (PatternSyntaxException unused) {
                    AppLog.e("PatternSyntaxException occurred");
                }
            }
            if (!TextUtils.isEmpty(PreferenceUtils.getUploadFileErrorCode(context)) && TextUtils.isEmpty(PreferenceUtils.getUploadFileServiceVersion(context))) {
                List asList2 = Arrays.asList(PreferenceUtils.getUploadFileErrorCode(context).split(","));
                if (asList2.size() > 0 && event.getErrorCode() != null && asList2.contains(event.getErrorCode())) {
                    AppLog.d("The event is blocked because of Error code : " + event.getErrorCode());
                    return false;
                }
            }
            if (!TextUtils.isEmpty(PreferenceUtils.getUploadFileServiceVersion(context)) || !TextUtils.isEmpty(PreferenceUtils.getUploadFileErrorCode(context))) {
                z = true;
            } else {
                AppLog.d("The service is blocked by the policy : " + event.getServiceId());
                return false;
            }
        }
        if (!TextUtils.isEmpty(PreferenceUtils.getMaxFileSizeValue(context))) {
            int parseInt = Integer.parseInt(PreferenceUtils.getMaxFileSizeValue(context));
            if (!TextUtils.isEmpty(PreferenceUtils.getMaxFileSizeServiceVersion(context))) {
                try {
                    Iterator it2 = Arrays.asList(PreferenceUtils.getMaxFileSizeServiceVersion(context).split(",")).iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (event.getServiceVersion().matches((String) it2.next())) {
                            if (!TextUtils.isEmpty(PreferenceUtils.getMaxFileSizeErrorCode(context))) {
                                List asList3 = Arrays.asList(PreferenceUtils.getMaxFileSizeErrorCode(context).split(","));
                                if (asList3.size() > 0 && event.getErrorCode() != null && asList3.contains(event.getErrorCode()) && !isValidDataSize(file, parseInt)) {
                                    AppLog.d("File size exceeds the limit for the Service version & Error code : " + event.getErrorCode());
                                    return false;
                                }
                            } else if (!isValidDataSize(file, parseInt)) {
                                AppLog.d("File size exceeds the limit for the Service version : " + event.getServiceVersion());
                                return false;
                            }
                        }
                    }
                } catch (PatternSyntaxException unused2) {
                    AppLog.e("PatternSyntaxException occurred");
                }
            }
            if (!TextUtils.isEmpty(PreferenceUtils.getMaxFileSizeErrorCode(context)) && TextUtils.isEmpty(PreferenceUtils.getMaxFileSizeServiceVersion(context))) {
                List asList4 = Arrays.asList(PreferenceUtils.getMaxFileSizeErrorCode(context).split(","));
                if (asList4.size() > 0 && event.getErrorCode() != null && asList4.contains(event.getErrorCode()) && !isValidDataSize(file, parseInt)) {
                    AppLog.d("File size exceeds the limit for the Error code : " + event.getErrorCode());
                    return false;
                }
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (TextUtils.isEmpty(PreferenceUtils.getMaxFileCountValue(context))) {
            z3 = false;
        } else if (unreportedAllEvents.size() >= Integer.parseInt(PreferenceUtils.getMaxFileCountValue(context))) {
            AppLog.d("File count exceeds the limit for the Service");
            return false;
        } else {
            z3 = true;
        }
        if (!z3 && unreportedAllEvents.size() >= Integer.parseInt(PreferenceUtils.getDefaultMaxFileCount(context))) {
            AppLog.d("File count exceeds the default limit");
            return false;
        } else if (!z2 && !isValidDataSize(file, Integer.parseInt(PreferenceUtils.getDefaultMaxFileSize(context)))) {
            AppLog.d("File size exceeds the default limit");
            return false;
        } else if (z || !"0".equals(PreferenceUtils.getDefaultUploadFile(context))) {
            return true;
        } else {
            AppLog.d("Default upload flag is false");
            return false;
        }
    }

    private static boolean isValidDataSize(File file, int i) {
        long j = ((long) i) * 1024 * 1024;
        if (file.exists()) {
            long length = file.length();
            AppLog.d("Event file size = " + (length / 1048576) + " MB");
            if (length <= j) {
                AppLog.d("Event file size is valid");
                return true;
            }
            AppLog.w("Event file size is invalid, The maximum size is  " + i + "MB");
            return false;
        }
        AppLog.w("File not exist");
        return false;
    }
}
