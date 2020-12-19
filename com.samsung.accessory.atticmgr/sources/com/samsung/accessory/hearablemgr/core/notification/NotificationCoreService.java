package com.samsung.accessory.hearablemgr.core.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Handler;
import android.os.Looper;
import android.provider.CallLog;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.accessorydm.interfaces.XCommonInterface;
import com.accessorydm.interfaces.XDBInterface;
import com.accessorydm.interfaces.XDMInterface;
import com.google.android.gms.common.ConnectionResult;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.permission.PermissionManager;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.accessory.hearablemgr.common.util.WorkerHandler;
import com.samsung.accessory.hearablemgr.core.EarBudsInfo;
import com.samsung.accessory.hearablemgr.core.bigdata.SA;
import com.samsung.accessory.hearablemgr.core.bigdata.SamsungAnalyticsUtil;
import com.samsung.accessory.hearablemgr.core.notification.receiver.AlarmReceiver;
import com.samsung.accessory.hearablemgr.core.notification.receiver.CalendarReceiver;
import com.samsung.accessory.hearablemgr.core.notification.receiver.PackageManagerReceiver;
import com.samsung.accessory.hearablemgr.core.service.CoreService;
import com.samsung.accessory.hearablemgr.core.service.message.MsgID;
import com.samsung.accessory.hearablemgr.core.service.message.MsgSimple;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.cover.Scover;
import com.samsung.android.sdk.cover.ScoverManager;
import com.samsung.android.sdk.cover.ScoverState;
import com.samsung.android.sdk.mobileservice.social.buddy.provider.BuddyContract;
import seccompat.android.util.Log;

public class NotificationCoreService implements NotificationSpeakListener {
    private static final String TAG = "Attic_NotificationCoreService";
    private final ContentObserver interruptionsModeObserver = new ContentObserver(new Handler()) {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass7 */

        public void onChange(boolean z) {
            super.onChange(z);
            Log.d(NotificationCoreService.TAG, "interruptionsModeObserver");
            if (Settings.Global.getInt(NotificationCoreService.this.mContext.getContentResolver(), NotificationConstants.ZEN_MODE, NotificationConstants.ZEN_MODE_OFF) != NotificationConstants.ZEN_MODE_OFF) {
                NotificationCoreService.this.mIsInterruptMode = false;
            } else {
                NotificationCoreService.this.mIsInterruptMode = true;
            }
            Log.d(NotificationCoreService.TAG, "mIsInterruptMode = " + NotificationCoreService.this.mIsInterruptMode);
        }
    };
    private boolean isCarModeCheck = false;
    private boolean isCoverOpen = true;
    private boolean isEarJackConnected = false;
    private BroadcastReceiver mAlarmReceiver = new AlarmReceiver();
    private BroadcastReceiver mCalendarReceiver = new CalendarReceiver();
    private final BroadcastReceiver mCarModeCheckReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass8 */

        public void onReceive(Context context, Intent intent) {
            if (NotificationConstants.ACTION_CAR_MODE_CHECK.equals(intent.getAction())) {
                int i = intent.getExtras().getInt("car_mode_state");
                Log.d(NotificationCoreService.TAG, "CarModeCheckReceiver CarMode = " + i);
                if (i == 0) {
                    NotificationCoreService.this.isCarModeCheck = false;
                } else {
                    NotificationCoreService.this.isCarModeCheck = true;
                }
            }
        }
    };
    private final BroadcastReceiver mConnectedReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass11 */

        /* JADX WARNING: Removed duplicated region for block: B:12:0x0046  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0075  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r7, android.content.Intent r8) {
            /*
            // Method dump skipped, instructions count: 173
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass11.onReceive(android.content.Context, android.content.Intent):void");
        }
    };
    private Context mContext;
    ScoverManager.StateListener mCoverStateListener = new ScoverManager.StateListener() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass10 */

        @Override // com.samsung.android.sdk.cover.ScoverManager.StateListener
        public void onCoverStateChanged(ScoverState scoverState) {
            Log.d(NotificationCoreService.TAG, "cover = " + scoverState.getSwitchState());
            if (scoverState.getSwitchState()) {
                NotificationCoreService.this.setIsCoverOpen(true);
            } else {
                NotificationCoreService.this.setIsCoverOpen(false);
            }
        }
    };
    private final BroadcastReceiver mEarJackReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass9 */

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
                Log.d(NotificationCoreService.TAG, "ACTION_HEADSET_PLUG");
                if (intent.hasExtra("state")) {
                    int intExtra = intent.getIntExtra("state", 0);
                    Log.d(NotificationCoreService.TAG, "mReceiver() : state - " + intExtra);
                    if (intExtra == 1) {
                        NotificationCoreService.this.isEarJackConnected = true;
                    } else if (intExtra == 0) {
                        NotificationCoreService.this.isEarJackConnected = false;
                    }
                }
            }
        }
    };
    private boolean mIsInterruptMode = true;
    private String mMissedCallTime = "";
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass5 */

        public void onCallStateChanged(int i, final String str) {
            Log.i(NotificationCoreService.TAG, "onCallStateChanged()");
            if (i == 0) {
                Log.d(NotificationCoreService.TAG, "Call State:: CALL_STATE_IDLE");
                if (NotificationCoreService.this.mTTSCore != null) {
                    NotificationCoreService.this.mTTSCore.setCallState(0);
                    if (NotificationCoreService.this.mTTSCore.isSpeaking()) {
                        Log.d(NotificationCoreService.TAG, "Idle State:: during incoming call, so force to done");
                        NotificationCoreService.this.mTTSCore.stopTTS(false);
                    }
                    if (!NotificationCoreService.this.mTTSCore.isQueueEmpty()) {
                        new Handler().postDelayed(new Runnable() {
                            /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass5.AnonymousClass1 */

                            public void run() {
                                NotificationCoreService.this.mTTSCore.makeTTS(NotificationCoreService.this.mContext);
                            }
                        }, XCommonInterface.WAKE_LOCK_TIMEOUT);
                    }
                } else {
                    return;
                }
            } else if (i == 1) {
                Log.d(NotificationCoreService.TAG, "Call State:: CALL_STATE_RINGING");
                if (NotificationCoreService.this.mTTSCore != null) {
                    NotificationCoreService.this.mTTSCore.setCallState(1);
                    if (NotificationCoreService.this.mTTSCore.isSpeaking()) {
                        NotificationCoreService.this.mTTSCore.stopTTS(false);
                    }
                    if (str == null) {
                        str = "";
                    }
                    if (NotificationCoreService.this.mTTSCore.getprevCallState() == 2) {
                        Log.d(NotificationCoreService.TAG, "Ringing State:: in case of 2nd call, TTS do not start in parent device.");
                    } else {
                        new Handler().post(new Runnable() {
                            /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass5.AnonymousClass2 */

                            public void run() {
                                String str;
                                String str2;
                                String str3 = NotificationConstants.INCOMING_CALL_PACKAGENAME;
                                String string = NotificationCoreService.this.mContext.getResources().getString(R.string.notification_incoming_call);
                                String str4 = str;
                                Log.d(NotificationCoreService.TAG, "MESSAGE_SPEAK_CALL_TTS_appName" + string);
                                if (!NotificationUtil.getInBandRingtone()) {
                                    Log.d(NotificationCoreService.TAG, "not support IBR");
                                } else if (NotificationUtil.checkAllStatus(str3)) {
                                    if (NotificationUtil.getAppNotificationDetails(str3).equals(NotificationConstants.NOTIFICATION_TYPE_SUMMARY)) {
                                        str2 = string;
                                        str = null;
                                    } else {
                                        Log.d(NotificationCoreService.TAG, "details, removed app name");
                                        str = str4;
                                        str2 = null;
                                    }
                                    Long valueOf = Long.valueOf(System.currentTimeMillis());
                                    if (!NotificationUtil.isSupportSpeakCallerName()) {
                                        NotificationMessage notificationMessage = new NotificationMessage(NotificationMessage.TYPE_CALL, str3, str2, str, null, valueOf.longValue());
                                        Intent intent = new Intent(NotificationConstants.ACTION_UPDATE_VN_MESSAGE);
                                        intent.putExtra(NotificationConstants.VN_MESSAGE, notificationMessage);
                                        NotificationCoreService.this.handleVoiceNotification(intent);
                                    }
                                }
                            }
                        });
                    }
                } else {
                    return;
                }
            } else if (i == 2) {
                Log.d(NotificationCoreService.TAG, "Call State:: CALL_STATE_OFFHOOK");
                if (NotificationCoreService.this.mTTSCore != null) {
                    NotificationCoreService.this.mTTSCore.setCallState(2);
                    if (NotificationCoreService.this.mTTSCore.isSpeaking()) {
                        NotificationCoreService.this.mTTSCore.stopTTS(false);
                    }
                } else {
                    return;
                }
            }
            Intent intent = new Intent(CoreService.ACTION_MSG_ID_CALL_STATE);
            intent.putExtra(CoreService.ACTION_MSG_ID_CALL_STATE, i);
            Util.sendPermissionBroadcast(NotificationCoreService.this.mContext, intent);
        }
    };
    private ScoverManager mScoverManager = null;
    private NotificationTTSCore mTTSCore;
    private final BroadcastReceiver mVNMessageReceiver = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            Log.e(NotificationCoreService.TAG, " mVNMessageReceiver action");
            NotificationCoreService.this.handleVoiceNotification(intent);
        }
    };
    private WorkerHandler mWorkerHandler;
    private final ContentObserver missedCallNotificationObserver = new ContentObserver(this.mWorkerHandler) {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass6 */

        public void onChange(boolean z) {
            super.onChange(z);
            Log.d(NotificationCoreService.TAG, "onChange: " + z);
            if (!Application.getCoreService().isConnected()) {
                Log.d(NotificationCoreService.TAG, "not connected");
            } else if (!PermissionManager.isPermissionGranted(NotificationCoreService.this.mContext, "android.permission.READ_CALL_LOG")) {
                Log.d(NotificationCoreService.TAG, "no permission");
            } else {
                try {
                    Cursor query = NotificationCoreService.this.mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, "new = 1", null, "date DESC limit 1;");
                    int columnIndex = query.getColumnIndex("type");
                    int columnIndex2 = query.getColumnIndex(BuddyContract.Event.DATE);
                    int columnIndex3 = query.getColumnIndex(XDBInterface.XDM_SQL_DB_PROFILELIST_UICRESULTKEEP_NUMBER);
                    query.moveToFirst();
                    String string = query.getString(columnIndex);
                    String string2 = query.getString(columnIndex2);
                    final String string3 = query.getString(columnIndex3);
                    Log.d(NotificationCoreService.TAG, string2 + ", " + NotificationCoreService.this.mMissedCallTime);
                    if (string2 == null || NotificationCoreService.this.mMissedCallTime == null || !string2.equals(NotificationCoreService.this.mMissedCallTime)) {
                        if (3 == Integer.parseInt(String.valueOf(string))) {
                            Log.d(NotificationCoreService.TAG, "Missed Call");
                            NotificationCoreService.this.mMissedCallTime = string2;
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass6.AnonymousClass1 */

                                public void run() {
                                    if (NotificationUtil.checkAllStatus(NotificationConstants.MISSED_CALL_PACKAGENAME)) {
                                        NotificationMessage notificationMessage = new NotificationMessage(NotificationMessage.TYPE_NORMAL, NotificationConstants.MISSED_CALL_PACKAGENAME, NotificationCoreService.this.mContext.getResources().getString(R.string.notification_missed_call), string3, null, System.currentTimeMillis());
                                        Intent intent = new Intent();
                                        intent.putExtra(NotificationConstants.VN_MESSAGE, notificationMessage);
                                        NotificationCoreService.this.handleVoiceNotification(intent);
                                    }
                                }
                            }, 1500);
                        } else {
                            Log.d(NotificationCoreService.TAG, "Not missed call " + string + "    " + 3);
                        }
                        query.close();
                        return;
                    }
                    Log.d(NotificationCoreService.TAG, "no missed call");
                    query.close();
                } catch (SecurityException unused) {
                    Log.d(NotificationCoreService.TAG, "Permission is not granted");
                } catch (CursorIndexOutOfBoundsException unused2) {
                    Log.d(NotificationCoreService.TAG, "There is no data");
                } catch (Exception unused3) {
                    Log.d(NotificationCoreService.TAG, "missed call observer exception");
                }
            }
        }
    };
    private TelephonyManager telephony = null;

    public NotificationCoreService(Context context) {
        this.mContext = context;
        onCreate();
        NotificationUtil.initSettingDefaultApps();
    }

    private void onCreate() {
        Log.d(TAG, "onCreate()");
        this.mWorkerHandler = WorkerHandler.createWorkerHandler("missedcall_db_check_worker");
        checkDefaultDndMode();
        if (Util.isSamsungDevice()) {
            NotificationUtil.enableNotificationService(true);
        }
        this.telephony = (TelephonyManager) this.mContext.getSystemService(XDMInterface.XDM_DEVDETAIL_DEFAULT_DEVTYPE);
        this.telephony.listen(this.mPhoneStateListener, 32);
        this.mTTSCore = new NotificationTTSCore(this.mContext, this, this.telephony.getCallState());
        registerScoverCallback();
        registerReceiver();
        PackageManagerReceiver.registerReceivers(this.mContext);
        SamsungAnalyticsUtil.setStatusInt(SA.Status.NOTIFICATION_ON, Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true) ? 1 : 0);
        Log.d(TAG, "onCreate()_end");
    }

    public NotificationTTSCore getNotificationTTSCore() {
        return this.mTTSCore;
    }

    private void checkDefaultDndMode() {
        if (Settings.Global.getInt(this.mContext.getContentResolver(), NotificationConstants.ZEN_MODE, NotificationConstants.ZEN_MODE_OFF) != NotificationConstants.ZEN_MODE_OFF) {
            this.mIsInterruptMode = false;
        } else {
            this.mIsInterruptMode = true;
        }
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        unRegisterReceiver();
        this.mScoverManager.unregisterListener(this.mCoverStateListener);
        this.mWorkerHandler.quit();
        NotificationTTSCore notificationTTSCore = this.mTTSCore;
        if (notificationTTSCore != null) {
            notificationTTSCore.release();
        }
        this.mTTSCore = null;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationConstants.ACTION_UPDATE_VN_MESSAGE);
        this.mContext.registerReceiver(this.mVNMessageReceiver, intentFilter, "com.samsung.accessory.atticmgr.permission.SIGNATURE", null);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(NotificationConstants.ACTION_CAR_MODE_CHECK);
        this.mContext.registerReceiver(this.mCarModeCheckReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction(NotificationConstants.CALENDAR_SEND_ALERTINFO_ACTION);
        intentFilter3.addAction(NotificationConstants.CALENDAR_ACTION_TASK_ALARM);
        intentFilter3.addAction("android.intent.action.EVENT_REMINDER");
        this.mContext.registerReceiver(this.mCalendarReceiver, intentFilter3);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("com.samsung.sec.android.clockpackage.alarm.ALARM_ALERT");
        intentFilter4.addAction("com.samsung.sec.android.clockpackage.alarm.ALARM_STARTED_IN_ALERT");
        intentFilter4.addAction("com.samsung.sec.android.clockpackage.alarm.ALARM_STOPPED_IN_ALERT");
        this.mContext.registerReceiver(this.mAlarmReceiver, intentFilter4);
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter5.addAction("android.media.AUDIO_BECOMING_NOISY");
        this.mContext.registerReceiver(this.mEarJackReceiver, intentFilter5);
        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction(CoreService.ACTION_DEVICE_CONNECTED);
        intentFilter6.addAction(CoreService.ACTION_DEVICE_DISCONNECTED);
        this.mContext.registerReceiver(this.mConnectedReceiver, intentFilter6);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor(NotificationConstants.ZEN_MODE), false, this.interruptionsModeObserver);
        registerMissedCallObserver();
    }

    public void registerMissedCallObserver() {
        try {
            if (PermissionManager.isPermissionGranted(this.mContext, "android.permission.READ_CALL_LOG")) {
                this.mContext.getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, false, this.missedCallNotificationObserver);
                this.telephony.listen(this.mPhoneStateListener, 0);
                this.telephony.listen(this.mPhoneStateListener, 32);
                Log.d(TAG, "register MissedCallObserver");
                Cursor query = this.mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, "type = 3", null, "date DESC limit 1;");
                int columnIndex = query.getColumnIndex(BuddyContract.Event.DATE);
                query.moveToFirst();
                this.mMissedCallTime = query.getString(columnIndex);
                query.close();
            }
        } catch (SecurityException unused) {
            Log.d(TAG, "Permission is not granted");
        } catch (CursorIndexOutOfBoundsException unused2) {
            Log.d(TAG, "There is no data");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "missed call observer exception");
        }
    }

    private void unRegisterReceiver() {
        this.mContext.unregisterReceiver(this.mVNMessageReceiver);
        this.mContext.unregisterReceiver(this.mCarModeCheckReceiver);
        this.mContext.unregisterReceiver(this.mCalendarReceiver);
        this.mContext.unregisterReceiver(this.mAlarmReceiver);
        this.mContext.unregisterReceiver(this.mEarJackReceiver);
        this.mContext.unregisterReceiver(this.mConnectedReceiver);
        this.mContext.getContentResolver().unregisterContentObserver(this.interruptionsModeObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.missedCallNotificationObserver);
        PackageManagerReceiver.unregisterReceivers(this.mContext);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleVoiceNotification(Intent intent) {
        Log.d(TAG, "status check dnd = " + this.mIsInterruptMode + " carmode = " + this.isCarModeCheck + " earjack = " + this.isEarJackConnected);
        if (!this.mIsInterruptMode || this.isCarModeCheck || this.isEarJackConnected) {
            return;
        }
        if (!Application.getCoreService().isConnected()) {
            Log.d(TAG, "handleVoiceNotification:: Not connected");
        } else if (!NotificationUtil.isActiveDevice()) {
            Log.d(TAG, "handleVoiceNotification:: Not ActiveDevice");
        } else {
            final NotificationMessage notificationMessage = (NotificationMessage) intent.getSerializableExtra(NotificationConstants.VN_MESSAGE);
            int intExtra = intent.getIntExtra(NotificationConstants.VN_USAGE, 0);
            if (notificationMessage != null) {
                notificationMessage.log();
                EarBudsInfo earBudsInfo = Application.getCoreService().getEarBudsInfo();
                if (notificationMessage.getType() == 4866 && !earBudsInfo.wearingR && !earBudsInfo.wearingL) {
                    Log.d(TAG, "handleVoiceNotification:: none wearing");
                } else if (this.mTTSCore != null) {
                    Log.d(TAG, " appName: " + notificationMessage.getAppName());
                    if (notificationMessage.getType() == 4864) {
                        new Handler().postDelayed(new Runnable() {
                            /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass2 */

                            public void run() {
                                if (NotificationCoreService.this.mTTSCore == null || NotificationCoreService.this.telephony.getCallState() != 0) {
                                    Log.d(NotificationCoreService.TAG, "alarm, call state is not idle");
                                } else {
                                    NotificationCoreService.this.mTTSCore.makeAlarm(NotificationCoreService.this.mContext, notificationMessage);
                                }
                            }
                        }, 1000);
                    } else if (notificationMessage.getType() == 4866) {
                        new Handler().postDelayed(new Runnable() {
                            /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass3 */

                            public void run() {
                                Log.d(NotificationCoreService.TAG, " mCurCallTTSTh start");
                                if (NotificationCoreService.this.mTTSCore != null && NotificationCoreService.this.telephony.getCallState() == 1) {
                                    NotificationCoreService.this.mTTSCore.makeCall(NotificationCoreService.this.mContext, notificationMessage);
                                }
                            }
                        }, 2000);
                    } else {
                        this.mTTSCore.insert(notificationMessage, false);
                        Log.d(TAG, this.mTTSCore.getMsgQsize() + " , " + this.mTTSCore + " , ");
                        int i = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
                        if (intExtra == 6) {
                            i = 4000;
                        }
                        Log.d(TAG, "delay = " + i);
                        if (this.mTTSCore.getMsgQsize() > 0 && this.mTTSCore != null && this.telephony.getCallState() == 0) {
                            Log.d(TAG, "MESSAGE_SPEAK_TTS after 1500 ");
                            new Handler().postDelayed(new Runnable() {
                                /* class com.samsung.accessory.hearablemgr.core.notification.NotificationCoreService.AnonymousClass4 */

                                public void run() {
                                    if (NotificationCoreService.this.mTTSCore != null) {
                                        NotificationCoreService.this.mTTSCore.makeTTS(NotificationCoreService.this.mContext);
                                    }
                                }
                            }, (long) i);
                        }
                    }
                }
            }
        }
    }

    @Override // com.samsung.accessory.hearablemgr.core.notification.NotificationSpeakListener
    public void VoiceNotificationSpeakStarted(int i, String str) {
        Log.d(TAG, "VoiceNotificationSpeakStarted() : " + i);
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.VOICE_NOTI_STATUS, (byte) 1));
    }

    @Override // com.samsung.accessory.hearablemgr.core.notification.NotificationSpeakListener
    public void VoiceNotificationSpeakCompleted(int i) {
        Log.d(TAG, "VoiceNotificationSpeakCompleted() : " + i);
        Application.getCoreService().sendSppMessage(new MsgSimple(MsgID.VOICE_NOTI_STATUS, (byte) 0));
    }

    public void registerScoverCallback() {
        try {
            new Scover().initialize(this.mContext);
            this.mScoverManager = new ScoverManager(this.mContext);
            ScoverState coverState = this.mScoverManager.getCoverState();
            if (coverState != null) {
                setIsCoverOpen(coverState.getSwitchState());
            }
            this.mScoverManager.registerListener(this.mCoverStateListener);
        } catch (IllegalArgumentException unused) {
            setIsCoverOpen(true);
            Log.d(TAG, "IllegalArgumentException Exception");
        } catch (SsdkUnsupportedException unused2) {
            setIsCoverOpen(true);
            Log.d(TAG, "SsdkUnsupportedException Exception");
        } catch (Exception unused3) {
            setIsCoverOpen(true);
            Log.d(TAG, "Exception Exception");
        }
    }

    public void setIsCoverOpen(boolean z) {
        this.isCoverOpen = z;
    }

    public boolean getIsCoverOpen() {
        return this.isCoverOpen;
    }

    private void deleteNotificationDB() {
        boolean z;
        try {
            z = this.mContext.deleteDatabase("_notification.db");
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        Log.d(TAG, "notification db check = " + z);
    }
}
