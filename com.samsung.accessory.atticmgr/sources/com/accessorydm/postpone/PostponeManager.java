package com.accessorydm.postpone;

import com.accessorydm.db.file.XDBPostPoneAdp;

public class PostponeManager {
    public static void startPostpone(PostponeType postponeType, long j) {
        postponeType.startPostpone(j);
    }

    public static void executePostpone() {
        XDBPostPoneAdp.xdbGetPostponeType().executePostpone();
    }

    public static void cancelPostpone() {
        PostponeType.cancelPostpone();
    }

    public static void stopAlarm() {
        PostponeType.stopAlarm();
    }

    public static void stopAlarmExceptScheduleInstall() {
        if (XDBPostPoneAdp.xdbGetPostponeType() != PostponeType.SCHEDULE_INSTALL) {
            stopAlarm();
        }
    }
}
