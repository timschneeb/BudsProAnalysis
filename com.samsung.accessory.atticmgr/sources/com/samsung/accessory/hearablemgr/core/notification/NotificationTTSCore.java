package com.samsung.accessory.hearablemgr.core.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import com.samsung.accessory.atticmgr.R;
import com.samsung.accessory.hearablemgr.Application;
import com.samsung.accessory.hearablemgr.common.preference.PreferenceKey;
import com.samsung.accessory.hearablemgr.common.preference.Preferences;
import com.samsung.accessory.hearablemgr.common.util.Util;
import com.samsung.android.fotaagent.update.UpdateInterface;
import java.util.LinkedList;
import java.util.Locale;
import seccompat.android.util.Log;

public class NotificationTTSCore {
    public static final String ACTION_CHANGE_THEME = "com.samsung.android.theme.themecenter.THEME_APPLY";
    private static final String TAG = "Attic_NotificationTTSCore";
    public static final int TTS_START = 0;
    public static final int TTS_STOP = 1;
    private String PARAM_UTTERANCE_ID_ALARM = "tts_with_a2dp_alarm";
    private String PARAM_UTTERANCE_ID_CALL = "tts_with_sco_call";
    private String PARAM_UTTERANCE_ID_MUSIC = "tts_with_a2dp_music";
    private String PARAM_UTTERANCE_ID_SLIENCE = "tts_with_slience";
    private TextToSpeech.OnInitListener initAlarmTTSListener = new TextToSpeech.OnInitListener() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass6 */

        public void onInit(int i) {
            if (i == 0) {
                if (Preferences.getString(PreferenceKey.NOTIFICATION_LANGUAGE, null) == null && NotificationTTSCore.this.mTTS != null) {
                    Locale tTSLanguage = NotificationTTSCoreUtil.getTTSLanguage(NotificationTTSCore.this.mTTS);
                    if (tTSLanguage != null) {
                        Log.d(NotificationTTSCore.TAG, "initAlarmTTSListener, set Phone TTS Lang = " + tTSLanguage.getLanguage());
                        Preferences.putString(PreferenceKey.NOTIFICATION_LANGUAGE, tTSLanguage.getLanguage());
                    } else {
                        Log.e(NotificationTTSCore.TAG, "initAlarmTTSListener, locale is null ");
                    }
                }
                NotificationTTSCore notificationTTSCore = NotificationTTSCore.this;
                notificationTTSCore.speakAlarm(notificationTTSCore.mAlarmMsg);
            }
        }
    };
    private TextToSpeech.OnInitListener initCallTTSListener = new TextToSpeech.OnInitListener() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass8 */

        public void onInit(int i) {
            if (i == 0) {
                if (Preferences.getString(PreferenceKey.NOTIFICATION_LANGUAGE, null) == null && NotificationTTSCore.this.mTTS != null) {
                    Locale tTSLanguage = NotificationTTSCoreUtil.getTTSLanguage(NotificationTTSCore.this.mTTS);
                    if (tTSLanguage != null) {
                        Log.d(NotificationTTSCore.TAG, "initCallTTSListener, set Phone TTS Lang = " + tTSLanguage.getLanguage());
                        Preferences.putString(PreferenceKey.NOTIFICATION_LANGUAGE, tTSLanguage.getLanguage());
                    } else {
                        Log.e(NotificationTTSCore.TAG, "initCallTTSListener, locale is null ");
                    }
                }
                NotificationTTSCore notificationTTSCore = NotificationTTSCore.this;
                notificationTTSCore.speakCall(notificationTTSCore.mCallMsg);
            }
        }
    };
    private NotificationMessage mAlarmMsg = null;
    protected AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass1 */

        public void onAudioFocusChange(int i) {
            Log.d(NotificationTTSCore.TAG, "focus = " + i);
            if (i == -2 || i == -1) {
                NotificationTTSCore.this.stopTTS(false);
            }
        }
    };
    private AudioManager mAudioManager;
    private NotificationMessage mCallMsg = null;
    private int mCallState = 0;
    private BroadcastReceiver mChangeTTS = new BroadcastReceiver() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass3 */

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(NotificationTTSCore.TAG, "action:" + action);
            if (action.equals(NotificationConstants.ACTION_CHANGE_TTS)) {
                if (NotificationTTSCore.this.mTTS.stop() == -1) {
                    Log.d(NotificationTTSCore.TAG, "mTTS.stop = -1");
                    NotificationTTSCore.this.release();
                    NotificationTTSCore notificationTTSCore = NotificationTTSCore.this;
                    notificationTTSCore.makeTTS(notificationTTSCore.mContext);
                }
            } else if (action.equals(NotificationTTSCore.ACTION_CHANGE_THEME)) {
                Log.d(NotificationTTSCore.TAG, "change theme");
                NotificationTTSCore.this.release();
                NotificationTTSCore notificationTTSCore2 = NotificationTTSCore.this;
                notificationTTSCore2.makeTTS(notificationTTSCore2.mContext);
            }
        }
    };
    private Context mContext;
    private NotificationMessage mCurMessage = null;
    private Handler mHandler = new Handler() {
        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass2 */

        public void handleMessage(Message message) {
            Log.d(NotificationTTSCore.TAG, "mTTSControlHandler() : " + message.what);
            int i = message.what;
            if (i == 0) {
                Log.d(NotificationTTSCore.TAG, "TTS_START");
                NotificationTTSCore.this.speakTTS();
            } else if (i == 1) {
                Log.d(NotificationTTSCore.TAG, "TTS_STOP");
                removeMessages(0);
                if (NotificationTTSCore.this.isSpeaking()) {
                    NotificationTTSCore.this.stopTTS(true);
                }
            }
        }
    };
    private LinkedList<NotificationMessage> mMsgQueue = new LinkedList<>();
    private Bundle mNeedRingtoneMute;
    private Bundle mParamsAlarm;
    private Bundle mParamsCall;
    private Bundle mParamsMusic;
    private Bundle mParamsSlience;
    private NotificationSpeakListener mSpeakCallBack = null;
    private TextToSpeech mTTS = null;
    private int mprevCallState = 0;
    private final Object syncObj = new Object();

    public void setCallState(int i) {
        this.mprevCallState = this.mCallState;
        this.mCallState = i;
    }

    public int getprevCallState() {
        return this.mprevCallState;
    }

    public NotificationTTSCore(Context context, NotificationSpeakListener notificationSpeakListener, int i) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationConstants.ACTION_CHANGE_TTS);
        intentFilter.addAction(ACTION_CHANGE_THEME);
        context.registerReceiver(this.mChangeTTS, intentFilter);
        Log.d(TAG, "TTSCore()");
        this.mSpeakCallBack = notificationSpeakListener;
        this.mContext = context;
        this.mCallState = i;
        this.mParamsMusic = new Bundle();
        this.mParamsMusic.putInt("streamType", 3);
        this.mParamsMusic.putString("utteranceId", this.PARAM_UTTERANCE_ID_MUSIC);
        this.mParamsAlarm = new Bundle();
        this.mParamsAlarm.putInt("streamType", 3);
        this.mParamsAlarm.putString("utteranceId", this.PARAM_UTTERANCE_ID_ALARM);
        this.mParamsCall = new Bundle();
        this.mParamsCall.putInt("streamType", 0);
        this.mParamsCall.putString("utteranceId", this.PARAM_UTTERANCE_ID_CALL);
        this.mParamsSlience = new Bundle();
        this.mParamsSlience.putInt("streamType", 3);
        this.mParamsSlience.putString("utteranceId", this.PARAM_UTTERANCE_ID_SLIENCE);
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        makeTTS(context);
    }

    public void release() {
        Log.d(TAG, "release()");
        if (this.mTTS != null) {
            this.mSpeakCallBack.VoiceNotificationSpeakCompleted(NotificationMessage.TYPE_NORMAL);
            Log.d(TAG, "release()::mTTS.reset() and mTTS.shutdown()");
            reset();
            this.mTTS.shutdown();
            this.mTTS = null;
        }
    }

    public boolean isSpeaking() {
        TextToSpeech textToSpeech = this.mTTS;
        return textToSpeech != null && textToSpeech.isSpeaking();
    }

    public boolean isQueueEmpty() {
        LinkedList<NotificationMessage> linkedList = this.mMsgQueue;
        return linkedList == null || linkedList.size() <= 0;
    }

    public void insert(NotificationMessage notificationMessage, boolean z) {
        Log.d(TAG, "insert()");
        if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true)) {
            synchronized (this.syncObj) {
                if (z) {
                    this.mMsgQueue.push(notificationMessage);
                } else {
                    this.mMsgQueue.add(notificationMessage);
                }
            }
        }
    }

    public void stopTTS(boolean z) {
        Log.d(TAG, "stopTTS():clearAll = " + z);
        Log.d(TAG, "mTTS :" + this.mTTS);
        TextToSpeech textToSpeech = this.mTTS;
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        if (z) {
            reset();
        }
    }

    public void reset() {
        LinkedList<NotificationMessage> linkedList = this.mMsgQueue;
        if (linkedList != null && linkedList.size() > 0) {
            synchronized (this.syncObj) {
                Log.d(TAG, "reset(), mMsgQueue is clear");
                this.mMsgQueue.clear();
            }
        }
    }

    public int getMsgQsize() {
        LinkedList<NotificationMessage> linkedList = this.mMsgQueue;
        if (linkedList != null) {
            return linkedList.size();
        }
        return 0;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public void makeTTS(Context context) {
        Log.d(TAG, "makeTTS()");
        TextToSpeech textToSpeech = this.mTTS;
        if (textToSpeech == null || textToSpeech.getVoices() == null) {
            Log.d(TAG, "mTTS is null");
            this.mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass4 */

                public void onInit(int i) {
                    Log.d(NotificationTTSCore.TAG, "onInit()");
                    if (i == 0) {
                        if (Preferences.getString(PreferenceKey.NOTIFICATION_LANGUAGE, null) == null && NotificationTTSCore.this.mTTS != null) {
                            Locale tTSLanguage = NotificationTTSCoreUtil.getTTSLanguage(NotificationTTSCore.this.mTTS);
                            Log.d(NotificationTTSCore.TAG, "onInit():getTTSLanguage(mTTS) = " + tTSLanguage);
                            if (tTSLanguage != null) {
                                Log.d(NotificationTTSCore.TAG, "onInit():locale.getLanguage() = " + tTSLanguage.getLanguage());
                                Preferences.putString(PreferenceKey.NOTIFICATION_LANGUAGE, tTSLanguage.getLanguage());
                            } else {
                                Log.e(NotificationTTSCore.TAG, "onInit():locale is null");
                            }
                        }
                        if (NotificationTTSCore.this.mTTS != null) {
                            NotificationTTSCore.this.speakTTS();
                            return;
                        }
                        return;
                    }
                    Log.d(NotificationTTSCore.TAG, "onInit(), Could not initialize TextToSpeech.");
                }
            });
            return;
        }
        speakTTS();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void speakTTS() {
        int i;
        String notificationMessageString;
        if (!Application.getCoreService().isConnected()) {
            Log.d(TAG, "speakTTS:: Not connected");
            return;
        }
        Log.d(TAG, "speakTTS():isSpeaking = " + isSpeaking());
        if (this.mCallState == 0 && !isSpeaking()) {
            Log.d(TAG, "mMsgQueue size = " + this.mMsgQueue.size());
            if (this.mMsgQueue.size() > 0) {
                synchronized (this.syncObj) {
                    i = 0;
                    this.mCurMessage = this.mMsgQueue.get(0);
                    try {
                        this.mMsgQueue.remove(0);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
                if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_ENABLE, true) && (notificationMessageString = NotificationTTSCoreUtil.getNotificationMessageString(this.mCurMessage, this.mContext, this.mTTS)) != null) {
                    this.mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass5 */

                        public void onDone(String str) {
                            Log.d(NotificationTTSCore.TAG, "speakTTS()::onDone()");
                            if (str.equals(NotificationTTSCore.this.PARAM_UTTERANCE_ID_SLIENCE)) {
                                Log.d(NotificationTTSCore.TAG, "onDone() - slience");
                                return;
                            }
                            int abandonAudioFocus = NotificationTTSCore.this.mAudioManager.abandonAudioFocus(NotificationTTSCore.this.mAudioFocusListener);
                            Log.d(NotificationTTSCore.TAG, "onDone():abandonAudioFocus = " + abandonAudioFocus);
                            Log.d(NotificationTTSCore.TAG, "mMsgQueue Size = " + NotificationTTSCore.this.mMsgQueue.size() + ", mCallState = " + NotificationTTSCore.this.mCallState);
                            if (NotificationTTSCore.this.mMsgQueue.size() <= 0 || NotificationTTSCore.this.mCallState != 0) {
                                NotificationTTSCore.this.mSpeakCallBack.VoiceNotificationSpeakCompleted(NotificationMessage.TYPE_NORMAL);
                                return;
                            }
                            Log.d(NotificationTTSCore.TAG, "onDone(), send message TTS_START");
                            NotificationTTSCore.this.mHandler.sendEmptyMessage(0);
                        }

                        public void onStop(String str, boolean z) {
                            Log.d(NotificationTTSCore.TAG, "speakTTS()::onStop()");
                            onDone(str);
                        }

                        public void onError(String str) {
                            onDone(str);
                        }

                        public void onStart(String str) {
                            Log.d(NotificationTTSCore.TAG, "speakTTS()::onStart()");
                            Log.d(NotificationTTSCore.TAG, "onStart():isSpeaking =" + NotificationTTSCore.this.isSpeaking());
                            if (!str.equals(NotificationTTSCore.this.PARAM_UTTERANCE_ID_SLIENCE)) {
                                NotificationTTSCore.this.mSpeakCallBack.VoiceNotificationSpeakStarted(NotificationMessage.TYPE_NORMAL, NotificationTTSCore.this.mCurMessage.getPkgName());
                            }
                        }
                    });
                    if (seccompat.android.media.AudioManager.proxysemIsRecordActive(this.mAudioManager, -1)) {
                        Log.d(TAG, "using VR");
                        return;
                    }
                    int requestAudioFocus = this.mAudioManager.requestAudioFocus(this.mAudioFocusListener, 3, 3);
                    if (requestAudioFocus == 1) {
                        this.mTTS.addSpeech(",....", this.mContext.getPackageName(), R.raw.slience_500ms);
                        this.mTTS.speak(",....", 1, this.mParamsSlience, this.PARAM_UTTERANCE_ID_SLIENCE);
                        i = this.mTTS.speak(notificationMessageString, 1, this.mParamsMusic, this.PARAM_UTTERANCE_ID_MUSIC);
                        Log.d(TAG, "speakTTS(), AUDIOFOCUS_REQUEST_GRANTED");
                    } else if (requestAudioFocus == 0) {
                        Log.d(TAG, "speakTTS(), AUDIOFOCUS_REQUEST_FAILED");
                    }
                    Log.d(TAG, "speakTTS():mTTS.speak = " + i);
                    return;
                }
                return;
            }
            Log.d(TAG, "speakTTS(), queue is emplty, so send complete spp msg");
        }
    }

    public void makeAlarm(Context context, NotificationMessage notificationMessage) {
        Log.d(TAG, "makeAlarm()");
        this.mAlarmMsg = notificationMessage;
        TextToSpeech textToSpeech = this.mTTS;
        if (textToSpeech == null || textToSpeech.getVoices() == null) {
            this.mTTS = new TextToSpeech(context, this.initAlarmTTSListener);
        } else {
            speakAlarm(this.mAlarmMsg);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void speakAlarm(NotificationMessage notificationMessage) {
        Log.d(TAG, "speakAlarm(), message = " + notificationMessage.toString() + ", isSpeaking = " + isSpeaking());
        TextToSpeech textToSpeech = this.mTTS;
        if (textToSpeech != null) {
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass7 */

                public void onDone(String str) {
                    if (str.equals(NotificationTTSCore.this.PARAM_UTTERANCE_ID_SLIENCE)) {
                        Log.d(NotificationTTSCore.TAG, "speakAlarm()::onDone() - slience");
                        return;
                    }
                    Log.d(NotificationTTSCore.TAG, "speakAlarm()::onDone()");
                    Log.d(NotificationTTSCore.TAG, "onDone(), abandonAudioFocus");
                    NotificationTTSCore.this.mAudioManager.abandonAudioFocus(NotificationTTSCore.this.mAudioFocusListener);
                    NotificationTTSCore.this.mSpeakCallBack.VoiceNotificationSpeakCompleted(NotificationMessage.TYPE_ALARM);
                }

                public void onStop(String str, boolean z) {
                    Log.d(NotificationTTSCore.TAG, "speakAlarm()::onStop()");
                    onDone(str);
                }

                public void onError(String str) {
                    onDone(str);
                }

                public void onStart(String str) {
                    Log.d(NotificationTTSCore.TAG, "speakAlarm()::onStart()");
                    if (!str.equals(NotificationTTSCore.this.PARAM_UTTERANCE_ID_SLIENCE)) {
                        NotificationTTSCore.this.mSpeakCallBack.VoiceNotificationSpeakStarted(NotificationMessage.TYPE_ALARM, NotificationConstants.ALARM_PACKAGENAME);
                    }
                }
            });
            String notificationMessageString = NotificationTTSCoreUtil.getNotificationMessageString(notificationMessage, this.mContext, this.mTTS);
            if (notificationMessageString != null && !notificationMessageString.equals("")) {
                if (seccompat.android.media.AudioManager.proxysemIsRecordActive(this.mAudioManager, -1)) {
                    Log.d(TAG, "using VR");
                    return;
                }
                int requestAudioFocus = this.mAudioManager.requestAudioFocus(this.mAudioFocusListener, 3, 2);
                if (requestAudioFocus == 1) {
                    this.mTTS.addSpeech(",....", this.mContext.getPackageName(), R.raw.slience_500ms);
                    this.mTTS.speak(",....", 1, this.mParamsSlience, this.PARAM_UTTERANCE_ID_SLIENCE);
                    this.mTTS.speak(notificationMessageString, 1, this.mParamsAlarm, this.PARAM_UTTERANCE_ID_ALARM);
                    Log.d(TAG, "speakAlarm(), AUDIOFOCUS_REQUEST_GRANTED");
                } else if (requestAudioFocus == 0) {
                    Log.d(TAG, "speakAlarm(), AUDIOFOCUS_REQUEST_FAILED");
                }
            }
        }
    }

    public void makeCall(Context context, NotificationMessage notificationMessage) {
        Log.d(TAG, "makeCall()");
        this.mCallMsg = notificationMessage;
        if (this.mCallState != 1) {
            Log.d(TAG, "mCallState is not ringing");
            return;
        }
        TextToSpeech textToSpeech = this.mTTS;
        if (textToSpeech == null || textToSpeech.getVoices() == null) {
            this.mTTS = new TextToSpeech(context, this.initCallTTSListener);
        } else {
            speakCall(this.mCallMsg);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void speakCall(NotificationMessage notificationMessage) {
        Log.d(TAG, "speakCall():message = " + notificationMessage.toString() + ", isSpeaking = " + isSpeaking());
        TextToSpeech textToSpeech = this.mTTS;
        if (textToSpeech != null) {
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                /* class com.samsung.accessory.hearablemgr.core.notification.NotificationTTSCore.AnonymousClass9 */

                public void onDone(String str) {
                    Log.d(NotificationTTSCore.TAG, "speakCall()::onDone()");
                    if (seccompat.android.media.AudioManager.proxyIsStreamMute(NotificationTTSCore.this.mAudioManager, 2) && NotificationTTSCore.this.mNeedRingtoneMute != null && NotificationTTSCore.this.mNeedRingtoneMute.getBoolean(str)) {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d(NotificationTTSCore.TAG, "mNeedRingtoneMute.get(utteranceId)= " + NotificationTTSCore.this.mNeedRingtoneMute.get(str));
                        Log.d(NotificationTTSCore.TAG, "Incoming Call TTS DONE. need STREAM_RING mute false");
                        NotificationTTSCore.this.mAudioManager.adjustStreamVolume(2, 100, 0);
                        NotificationTTSCore.this.mNeedRingtoneMute.remove(str);
                        Log.d(NotificationTTSCore.TAG, "onDone::mNeedRingtoneMute= " + NotificationTTSCore.this.mNeedRingtoneMute);
                    }
                    NotificationTTSCore.this.mSpeakCallBack.VoiceNotificationSpeakCompleted(NotificationMessage.TYPE_CALL);
                }

                public void onStop(String str, boolean z) {
                    Log.d(NotificationTTSCore.TAG, "speakCall()::onStop()");
                    onDone(str);
                }

                public void onError(String str) {
                    onDone(str);
                }

                public void onStart(String str) {
                    Log.d(NotificationTTSCore.TAG, "speakCall()::onStart()");
                    if (NotificationTTSCore.this.mNeedRingtoneMute == null) {
                        NotificationTTSCore.this.mNeedRingtoneMute = new Bundle();
                    }
                    if (!seccompat.android.media.AudioManager.proxyIsStreamMute(NotificationTTSCore.this.mAudioManager, 2)) {
                        if (Build.VERSION.SDK_INT >= 26 && Util.isChinaModel()) {
                            try {
                                Thread.sleep(UpdateInterface.HOLDING_AFTER_BT_CONNECTED);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (Build.VERSION.SDK_INT >= 23) {
                            Bundle call = NotificationTTSCore.this.mContext.getContentResolver().call(Uri.parse("content://com.android.phone.callsettings"), "get_voice_control_incoming", (String) null, (Bundle) null);
                            if (call != null) {
                                if (call.getInt("voice_control_incoming") == 0) {
                                    Log.d(NotificationTTSCore.TAG, "Incoming Call TTS START. STREAM_RING mute true");
                                    NotificationTTSCore.this.mNeedRingtoneMute.putBoolean(str, true);
                                    NotificationTTSCore.this.mAudioManager.adjustStreamVolume(2, -100, 0);
                                } else if (call.getInt("voice_control_incoming") == 1) {
                                    NotificationTTSCore.this.mNeedRingtoneMute.putBoolean(str, false);
                                    Log.d(NotificationTTSCore.TAG, "do not StreamMute");
                                }
                            }
                        } else if (Settings.System.getInt(NotificationTTSCore.this.mContext.getContentResolver(), "voice_input_control_incomming_calls", 0) == 1) {
                            NotificationTTSCore.this.mNeedRingtoneMute.putBoolean(str, false);
                            Log.d(NotificationTTSCore.TAG, "do not StreamMute");
                        } else {
                            Log.d(NotificationTTSCore.TAG, "Incoming Call TTS START. STREAM_RING mute true");
                            NotificationTTSCore.this.mNeedRingtoneMute.putBoolean(str, true);
                            NotificationTTSCore.this.mAudioManager.adjustStreamVolume(2, -100, 0);
                        }
                    }
                    NotificationTTSCore.this.mSpeakCallBack.VoiceNotificationSpeakStarted(NotificationMessage.TYPE_CALL, NotificationConstants.INCOMING_CALL_PACKAGENAME);
                }
            });
            this.mCallMsg = notificationMessage;
            String notificationMessageString = NotificationTTSCoreUtil.getNotificationMessageString(this.mCallMsg, this.mContext, this.mTTS);
            if (notificationMessageString != null && !notificationMessageString.equals("")) {
                if (Preferences.getBoolean(PreferenceKey.NOTIFICATION_CALL_REPEAT, true)) {
                    notificationMessageString = notificationMessageString + ". " + notificationMessageString;
                }
                if (this.mCallState == 1) {
                    Log.d(TAG, "mCallState is ringing");
                    this.mTTS.speak(notificationMessageString, 0, this.mParamsCall, this.PARAM_UTTERANCE_ID_CALL);
                    return;
                }
                Log.d(TAG, "mCallState is not ringing, so do not start CALL TTS");
            }
        }
    }
}
