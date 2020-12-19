package com.accessorydm.postpone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.accessorydm.XDMDmUtils;
import com.accessorydm.db.file.XDBFumoAdp;
import com.accessorydm.db.file.XDBPostPoneAdp;
import com.accessorydm.db.file.XDBPostPoneInfo;
import com.accessorydm.eng.core.XDMEvent;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XFOTAInterface;
import com.accessorydm.interfaces.XUIEventInterface;
import com.accessorydm.receiver.XDMAlarmReceiver;
import com.accessorydm.ui.notification.XUINotificationManager;
import com.accessorydm.ui.notification.manager.NotificationType;
import com.samsung.accessory.fotaprovider.AccessoryController;
import com.samsung.android.fotaprovider.FotaProviderInitializer;
import com.samsung.android.fotaprovider.log.Log;
import com.samsung.android.fotaprovider.util.GeneralUtil;
import java.util.Calendar;
import java.util.GregorianCalendar;

public enum PostponeType {
    NONE(0) {
        @Override // com.accessorydm.postpone.PostponeType
        public NotificationType getNotificationType() {
            return null;
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void setValuesForStart(XDBPostPoneInfo xDBPostPoneInfo) {
            Log.W("do nothing");
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void executePostpone() {
            Log.W("postpone is none, reset postpone");
            cancelPostpone();
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void doExecute() {
            Log.W("postpone is none, reset postpone");
            cancelPostpone();
        }
    },
    INSTALL_BACK_KEY(3) {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void setValuesForStart(XDBPostPoneInfo xDBPostPoneInfo) {
            XDBPostPoneAdp.xdbSetPostPoneInfo(xDBPostPoneInfo);
            XDBFumoAdp.xdbSetFUMOStatus(XFOTAInterface.XDL_STATE_POSTPONE_TO_UPDATE);
            XUINotificationManager.getInstance().xuiSetIndicator(getNotificationType());
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void doExecute() {
            Log.I("");
            XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
        }

        @Override // com.accessorydm.postpone.PostponeType
        public NotificationType getNotificationType() {
            return NotificationType.XUI_INDICATOR_UPDATE_BACK_KEY_POSTPONE;
        }
    },
    SCHEDULE_INSTALL(7) {
        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void setValuesForStart(XDBPostPoneInfo xDBPostPoneInfo) {
            XDBPostPoneAdp.xdbSetPostPoneInfo(xDBPostPoneInfo);
            XDBFumoAdp.xdbSetFUMOStatus(XFOTAInterface.XDL_STATE_POSTPONE_TO_UPDATE);
            XUINotificationManager.getInstance().xuiSetIndicator(getNotificationType());
        }

        /* access modifiers changed from: package-private */
        @Override // com.accessorydm.postpone.PostponeType
        public void doExecute() {
            if (AccessoryController.getInstance().getConnectionController().isConnected()) {
                Log.I("BT is connected, so start schedule install");
                XDMEvent.XDMSetEvent(null, XUIEventInterface.DL_UIEVENT.XUI_DL_UPDATE_CONFIRM);
                return;
            }
            Log.I("BT is disconnected, so postpone schedule install");
            long calculateNextScheduleInstallTime = calculateNextScheduleInstallTime(XDBPostPoneAdp.xdbGetPostponeTime());
            XDBPostPoneAdp.xdbSetPostponeTime(calculateNextScheduleInstallTime);
            PostponeType.startAlarm(calculateNextScheduleInstallTime);
            XUINotificationManager.getInstance().xuiSetIndicator(NotificationType.XUI_INDICATOR_UPDATE_POSTPONE_SCHEDULE_INSTALL);
        }

        @Override // com.accessorydm.postpone.PostponeType
        public NotificationType getNotificationType() {
            return NotificationType.XUI_INDICATOR_UPDATE_SCHEDULE_INSTALL;
        }

        private long calculateNextScheduleInstallTime(long j) {
            Calendar convertMillisToCalendar = GeneralUtil.convertMillisToCalendar(j);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            convertMillisToCalendar.set(gregorianCalendar.get(1), gregorianCalendar.get(2), gregorianCalendar.get(5));
            return convertMillisToCalendar.getTimeInMillis() + 86400000;
        }
    };
    
    private final int status;

    /* access modifiers changed from: package-private */
    public abstract void doExecute();

    public abstract NotificationType getNotificationType();

    /* access modifiers changed from: package-private */
    public abstract void setValuesForStart(XDBPostPoneInfo xDBPostPoneInfo);

    private PostponeType(int i) {
        this.status = i;
    }

    public static PostponeType valueOf(int i) {
        PostponeType[] values = values();
        for (PostponeType postponeType : values) {
            if (postponeType.status == i) {
                return postponeType;
            }
        }
        Log.W("no match status: " + i);
        return NONE;
    }

    public int getStatus() {
        return this.status;
    }

    /* access modifiers changed from: package-private */
    public void startPostpone(long j) {
        XDBPostPoneInfo xdbGetPostPoneInfo = XDBPostPoneAdp.xdbGetPostPoneInfo();
        if (xdbGetPostPoneInfo == null) {
            Log.W("postpone data from database is null. return");
            return;
        }
        Log.I("start postpone: " + this + "(" + this.status + "), " + GeneralUtil.convertMillisToDateTime(j));
        xdbGetPostPoneInfo.setPostponeTime(j);
        xdbGetPostPoneInfo.setPostponeStatus(this.status);
        setValuesForStart(xdbGetPostPoneInfo);
        startAlarm(j);
    }

    /* access modifiers changed from: package-private */
    public void executePostpone() {
        Log.I("execute postpone: " + this + "(" + this.status + ")");
        stopAlarm();
        if (XDBPostPoneAdp.xdbGetPostponeTime() <= System.currentTimeMillis()) {
            doExecute();
        } else {
            restartAlarm();
        }
    }

    static void cancelPostpone() {
        Log.I("");
        stopAlarm();
        XDBPostPoneInfo xdbGetPostPoneInfo = XDBPostPoneAdp.xdbGetPostPoneInfo();
        if (xdbGetPostPoneInfo != null) {
            xdbGetPostPoneInfo.setPostponeStatus(NONE.getStatus());
            xdbGetPostPoneInfo.setPostponeTime(0);
            xdbGetPostPoneInfo.setWifiPostponeRetryCount(0);
            XDBPostPoneAdp.xdbSetPostPoneInfo(xdbGetPostPoneInfo);
        }
    }

    /* access modifiers changed from: private */
    public static void startAlarm(long j) {
        AlarmManager alarmManager = (AlarmManager) XDMDmUtils.getInstance().xdmGetServiceManager(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager == null) {
            Log.E("AlarmManager is null!!");
            return;
        }
        Log.I("start postpone alarm");
        Intent intent = new Intent(getContext(), XDMAlarmReceiver.class);
        intent.setAction(XCommonInterface.XCOMMON_INTENT_POSTPONE_ACTION);
        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 0, intent, 134217728);
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(0, j, broadcast);
        } else {
            alarmManager.setExact(0, j, broadcast);
        }
    }

    static void stopAlarm() {
        AlarmManager alarmManager = (AlarmManager) XDMDmUtils.getInstance().xdmGetServiceManager(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager == null) {
            Log.E("AlarmManager is null!!");
            return;
        }
        Log.I("stop postpone alarm");
        Intent intent = new Intent(getContext(), XDMAlarmReceiver.class);
        intent.setAction(XCommonInterface.XCOMMON_INTENT_POSTPONE_ACTION);
        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 0, intent, 134217728);
        if (broadcast != null) {
            alarmManager.cancel(broadcast);
            broadcast.cancel();
        }
    }

    private void restartAlarm() {
        Log.I("");
        startAlarm(XDBPostPoneAdp.xdbGetPostponeTime());
        NotificationType notificationType = getNotificationType();
        if (notificationType != null) {
            XUINotificationManager.getInstance().xuiSetIndicator(notificationType);
        }
    }

    private static Context getContext() {
        return FotaProviderInitializer.getContext();
    }
}
