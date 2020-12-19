package com.samsung.android.app.twatchmanager.util;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;

public class ShowButtonBackgroundSettingObserver extends ContentObserver {
    private static final String SHOW_BUTTON_BACKGROUND = "show_button_background";
    private final ContentResolver mContentResolver;
    private OnSettingValueChangeListener mOnSettingValueChangeListener;

    public interface OnSettingValueChangeListener {
        void onChange(boolean z);
    }

    public ShowButtonBackgroundSettingObserver(ContentResolver contentResolver) {
        super(new Handler());
        this.mContentResolver = contentResolver;
    }

    private void startObserving() {
        boolean z = false;
        this.mContentResolver.registerContentObserver(Settings.System.getUriFor(SHOW_BUTTON_BACKGROUND), false, this);
        if (Settings.System.getInt(this.mContentResolver, SHOW_BUTTON_BACKGROUND, 0) != 0) {
            z = true;
        }
        onChange(z);
    }

    private void stopObserving() {
        this.mContentResolver.unregisterContentObserver(this);
    }

    public void onChange(boolean z) {
        super.onChange(z);
        OnSettingValueChangeListener onSettingValueChangeListener = this.mOnSettingValueChangeListener;
        if (onSettingValueChangeListener != null) {
            onSettingValueChangeListener.onChange(z);
        }
    }

    public void setOnContentChangeListener(OnSettingValueChangeListener onSettingValueChangeListener) {
        this.mOnSettingValueChangeListener = onSettingValueChangeListener;
        if (this.mOnSettingValueChangeListener != null) {
            startObserving();
        } else {
            stopObserving();
        }
    }
}
