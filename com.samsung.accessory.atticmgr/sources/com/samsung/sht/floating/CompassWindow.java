package com.samsung.sht.floating;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.samsung.sht.log.ShtLog;

public class CompassWindow {
    private static final int MSG_DISMISS = 2;
    private static final int MSG_ON_AZIMUTH_UPDATE = 3;
    private static final int MSG_ON_TEXT_UPDATE = 4;
    private static final int MSG_SHOW = 1;
    private boolean isShowing = false;
    private CompassView mCompassView = null;
    private Context mContext = null;
    private int mFirstX = 0;
    private int mFirstY = 0;
    private LinearLayout mFloatingView = null;
    private MyHandler mHandler = null;
    private int mLastX = 0;
    private int mLastY = 0;
    private WindowManager.LayoutParams mLayoutParams = null;
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        /* class com.samsung.sht.floating.CompassWindow.AnonymousClass1 */

        public boolean onTouch(View view, MotionEvent motionEvent) {
            int i = CompassWindow.this.mLastX - CompassWindow.this.mFirstX;
            int i2 = CompassWindow.this.mLastY - CompassWindow.this.mFirstY;
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                CompassWindow.this.mLastX = (int) motionEvent.getRawX();
                CompassWindow.this.mLastY = (int) motionEvent.getRawY();
                CompassWindow compassWindow = CompassWindow.this;
                compassWindow.mFirstX = compassWindow.mLastX;
                CompassWindow compassWindow2 = CompassWindow.this;
                compassWindow2.mFirstY = compassWindow2.mLastY;
            } else if (actionMasked == 1) {
                view.performClick();
            } else if (actionMasked == 2) {
                int rawX = ((int) motionEvent.getRawX()) - CompassWindow.this.mLastX;
                int rawY = ((int) motionEvent.getRawY()) - CompassWindow.this.mLastY;
                CompassWindow.this.mLastX = (int) motionEvent.getRawX();
                CompassWindow.this.mLastY = (int) motionEvent.getRawY();
                if (Math.abs(i) < 5 && Math.abs(i2) < 5) {
                    CompassWindow.this.touchComsumedByMove = false;
                } else if (motionEvent.getPointerCount() == 1) {
                    CompassWindow.this.mLayoutParams.x += rawX;
                    CompassWindow.this.mLayoutParams.y += rawY;
                    CompassWindow.this.touchComsumedByMove = true;
                    CompassWindow.this.mWindowManager.updateViewLayout(CompassWindow.this.mFloatingView, CompassWindow.this.mLayoutParams);
                } else {
                    CompassWindow.this.touchComsumedByMove = false;
                }
            }
            return CompassWindow.this.touchComsumedByMove;
        }
    };
    private TextView mTextView = null;
    private WindowManager mWindowManager = null;
    private boolean touchComsumedByMove = false;

    public CompassWindow(Context context, Looper looper) {
        this.mContext = context;
        this.mHandler = new MyHandler(looper);
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        this.mFloatingView = new LinearLayout(context);
        this.mFloatingView.setOrientation(1);
        this.mCompassView = new CompassView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(450, 450);
        this.mCompassView.setLayoutParams(layoutParams);
        this.mFloatingView.addView(this.mCompassView);
        this.mTextView = new TextView(context);
        new ViewGroup.LayoutParams(800, 200);
        this.mTextView.setLayoutParams(layoutParams);
        this.mTextView.setBackgroundColor(-1);
        this.mFloatingView.addView(this.mTextView);
        this.mFloatingView.setOnTouchListener(this.mOnTouchListener);
        this.mLayoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams layoutParams2 = this.mLayoutParams;
        layoutParams2.format = -3;
        layoutParams2.flags = 8;
        if (Build.VERSION.SDK_INT >= 26) {
            this.mLayoutParams.type = 2038;
        } else {
            this.mLayoutParams.type = 2005;
        }
        WindowManager.LayoutParams layoutParams3 = this.mLayoutParams;
        layoutParams3.gravity = 17;
        layoutParams3.width = 800;
        layoutParams3.height = 650;
        this.mFloatingView.setLayoutParams(layoutParams3);
    }

    public void show() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleShow() {
        if (Build.VERSION.SDK_INT < 23 || !Settings.canDrawOverlays(this.mContext)) {
            startManageDrawOverlayPermission();
        } else if (!this.isShowing) {
            this.mWindowManager.addView(this.mFloatingView, this.mLayoutParams);
            this.isShowing = true;
        }
    }

    public void dismiss() {
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleDismiss() {
        if (this.isShowing) {
            this.mWindowManager.removeView(this.mFloatingView);
            this.isShowing = false;
        }
    }

    public void setAzimuth(float f) {
        this.mHandler.removeMessages(3);
        this.mHandler.obtainMessage(3, new Float(f)).sendToTarget();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleAzimuthUpdate(float f) {
        this.mCompassView.setAngle(f);
    }

    public void setText(String str) {
        this.mHandler.removeMessages(4);
        this.mHandler.obtainMessage(4, str).sendToTarget();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleTextUpdate(String str) {
        this.mTextView.setText(str);
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    private void startManageDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + this.mContext.getPackageName()));
            intent.setFlags(268435456);
            this.mContext.startActivity(intent);
        }
    }

    /* access modifiers changed from: private */
    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                CompassWindow.this.handleShow();
            } else if (i == 2) {
                CompassWindow.this.handleDismiss();
            } else if (i == 3) {
                CompassWindow.this.handleAzimuthUpdate(((Float) message.obj).floatValue());
            } else if (i != 4) {
                ShtLog.e("CompassWindow : Invalid msg");
            } else {
                CompassWindow.this.handleTextUpdate((String) message.obj);
            }
        }
    }
}
