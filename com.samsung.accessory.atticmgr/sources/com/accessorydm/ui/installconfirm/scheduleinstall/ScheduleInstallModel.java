package com.accessorydm.ui.installconfirm.scheduleinstall;

import android.text.format.DateFormat;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.postpone.PostponeManager;
import com.accessorydm.postpone.PostponeType;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import com.sec.android.fotaprovider.R;
import java.util.Date;

public class ScheduleInstallModel {
    private static final ScheduleInstallModel instance = new ScheduleInstallModel();
    private final int DEFAULT_HOUR = 2;
    private final int DEFAULT_MINUTE = 0;

    /* access modifiers changed from: package-private */
    public int getDefaultHour() {
        return 2;
    }

    /* access modifiers changed from: package-private */
    public int getDefaultMinute() {
        return 0;
    }

    public static ScheduleInstallModel getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    public void scheduleInstallByTimePicker(int i, int i2) {
        PostponeManager.startPostpone(PostponeType.SCHEDULE_INSTALL, calculateScheduleInstallTimeInMillis(GeneralUtil.convertTimeSetToMillis(i, i2, 0)));
    }

    private long calculateScheduleInstallTimeInMillis(long j) {
        return j > System.currentTimeMillis() ? j : j + 86400000;
    }

    /* access modifiers changed from: package-private */
    public String getTimePickerTitle() {
        return FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_INSTALL_CONFIRM_SCHEDULED_TIMEPICKER_TITLE);
    }

    /* access modifiers changed from: package-private */
    public String getSetTimeToastText() {
        Date date = new Date(XDBPostPoneAdp.xdbGetPostponeTime());
        String format = DateFormat.getLongDateFormat(FotaProviderInitializer.getContext()).format(date);
        String format2 = DateFormat.getTimeFormat(FotaProviderInitializer.getContext()).format(date);
        return String.format(FotaProviderInitializer.getContext().getString(R.string.STR_ACCESSORY_INSTALL_CONFIRM_SCHEDULED_TIMEPICKER_SET_TIME_TOAST_TEXT), format2, format);
    }
}
