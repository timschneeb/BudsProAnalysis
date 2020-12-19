package com.samsung.accessory.hearablemgr.core.notification;

public interface NotificationSpeakListener {
    void VoiceNotificationSpeakCompleted(int i);

    void VoiceNotificationSpeakStarted(int i, String str);
}
